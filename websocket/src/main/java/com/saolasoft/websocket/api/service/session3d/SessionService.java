package com.saolasoft.websocket.api.service.session3d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.saolasoft.websocket.Session3dRepository;
import com.saolasoft.websocket.api.dto.WebSocketDTOCreate;
import com.saolasoft.websocket.api.response.APIResponse;
import com.saolasoft.websocket.api.response.APIResponseHeader;
import com.saolasoft.websocket.api.service.session3d.model.Session;
import com.saolasoft.websocket.config.AppConfigProperties;
import com.saolasoft.websocket.config.ConfigProperties;
import com.saolasoft.websocket.api.dto.WebSocketDTOGet;
import com.saolasoft.websocket.api.service.session3d.ProcessManager;
import java.lang.Process;
import java.util.List;
import java.time.LocalTime;
import java.time.Duration;
import java.lang.Thread;

@Service
public class SessionService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ConfigProperties configProperties = null;
	private AppConfigProperties appConfigProperties = null;
	
	private SessionManager sessionManager;
	private ProcessManager processManager;
	
	private Session3dRepository session3dRepository;
	
	public SessionService(Session3dRepository session3dRepository) {
		this.sessionManager = new SessionManager();
		this.processManager = new ProcessManager();
		this.session3dRepository = session3dRepository;
	}
	
	public ConfigProperties getConfigProperties() {
		return this.configProperties;
	}
	
	public AppConfigProperties getAppConfigProperties() {
		return this.appConfigProperties;
	}
	
	public void setConfigProperties(ConfigProperties configProperties) {
		this.configProperties = configProperties;
		this.sessionManager.setConfigProperties(configProperties);
		this.processManager.setConfigProperties(configProperties);
	}
	
	public void setAppConfigProperties(AppConfigProperties appConfigProperties) {
		this.appConfigProperties = appConfigProperties;
		this.sessionManager.setAppConfigProperties(appConfigProperties);
		this.processManager.setAppConfigProperties(appConfigProperties);
	}
	
	public APIResponse<WebSocketDTOGet> handlePost(WebSocketDTOCreate payload) {
		// Try to free any available resource
		List<String> idToFree = this.processManager.listEndedProcess();
		for (String id: idToFree) {
			this.sessionManager.deleteSession(id);
			this.processManager.stopProcess(id);
		}
		
		// Create new session
		Session session = this.sessionManager.createSession(payload);
		// No resource available
		if (session == null) {
			APIResponseHeader header = new APIResponseHeader(503, "All the resources are currently taken");
			APIResponse<WebSocketDTOGet> response = new APIResponse<>(header, null);
			return response;
		}
		// Save session in repository
		this.session3dRepository.saveSession(session);
		logger.info(String.format("Session: id=%s, host=%s, port=%d, cmd=%s", session.getId(), session.getHost(), session.getPort(), session.getCmd()));
		
		// Start process
		Process process = this.processManager.startProcess(session);
		if (process == null) {
			APIResponseHeader header = new APIResponseHeader(503, "The process did not properly start");
			APIResponse<WebSocketDTOGet> response = new APIResponse<>(header, null);
			return response;
		}
		logger.info(String.format("Process: processId=%d", process.pid()));
		
		// Wait for session to be ready
		LocalTime startTime = LocalTime.now();
		int count = 0;
		while (true) {
			if (this.processManager.isReady(session, count)) {
				APIResponseHeader header = new APIResponseHeader(200, "Ok");
				APIResponse<WebSocketDTOGet> response = new APIResponse<>(header, new WebSocketDTOGet(session.getId(), session.getSecret(), session.getSessionUrl()));
				return response;
			}
			
			Duration elapsedTime = Duration.between(startTime, LocalTime.now());
			if (elapsedTime.getSeconds() > this.configProperties.getTimeout()) {
				this.sessionManager.deleteSession(session.getId());
				this.processManager.stopProcess(session.getId());
				APIResponseHeader header = new APIResponseHeader(503, "Session did not start before timeout expired. Check session logs");
				APIResponse<WebSocketDTOGet> response = new APIResponse<>(header, null);
				return response; 
			}
			
			try {
				// Sleep 1 second
				Thread.sleep(1000);
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			
			count += 1;
		}
	}
	
	public APIResponse<Session> handleGet(String id) {
		Session session = this.sessionManager.getSession(id);
		if (session == null) {
			APIResponseHeader header = new APIResponseHeader(400, String.format("No session with id: %s", id));
			APIResponse<Session> response = new APIResponse<>(header, null);
			return response;
		}
		
		APIResponseHeader header = new APIResponseHeader(200, "Ok");
		APIResponse<Session> response = new APIResponse<>(header, session);
		return response;
	}
	
	public APIResponse<String> handleDelete(String id) {
		Session session = this.sessionManager.getSession(id);
		if (session == null) {
			APIResponseHeader header = new APIResponseHeader(400, String.format("No session with id: %s", id));
			APIResponse<String> response = new APIResponse<>(header, null);
			return response;
		}
		
		// Remove session
		this.sessionManager.deleteSession(id);
		// Stop process
		this.processManager.stopProcess(id);
		
		APIResponseHeader header = new APIResponseHeader(200, String.format("Deleted session with id: %s", id));
		APIResponse<String> response = new APIResponse<>(header, null);
		return response;
	}
}
