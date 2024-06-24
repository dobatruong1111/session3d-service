package com.saolasoft.websocket.api.service.session3d;

import com.saolasoft.websocket.api.dto.WebSocketDTOCreate;
import com.saolasoft.websocket.api.service.session3d.model.Session;
import com.saolasoft.websocket.config.AppConfigProperties;
import com.saolasoft.websocket.config.ConfigProperties;
import java.util.*;

public class SessionManager {
	
	private ConfigProperties configProperties;
	private AppConfigProperties appConfigProperties;
	
	private Map<String, Session> sessions;
	private ResourceManager resources;
	
	public SessionManager() {
		this.sessions = new HashMap<>();
		this.resources = new ResourceManager();
	}
	
	public void setConfigProperties(ConfigProperties configProperties) {
		this.configProperties = configProperties;
		this.resources.setConfigProperties(configProperties);
	}
	
	public void setAppConfigProperties(AppConfigProperties appConfigProperties) {
		this.appConfigProperties = appConfigProperties;
	}
	
	public Session createSession(WebSocketDTOCreate options) {
		String resource = this.resources.getNextResource();
		
		if (!resource.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String id = uuid.toString();
			
			String host = resource.split(":")[0];
			
			int port = Integer.parseInt(resource.split(":")[1]);
			
			String sessionUrl = String.format(this.configProperties.getSessionUrl(), id);
			
			List<String> cmd = this.appConfigProperties.getViewer().getCmd();
			String replacedCmd = String.format(String.join(" ", cmd), host, port);
			
			Session session = new Session(id, host, port, sessionUrl, replacedCmd);
			this.sessions.put(id, session);
			return session;
		}
		return null;
	}
	
	public Session getSession(String id) {
		return this.sessions.containsKey(id) ? this.sessions.get(id) : null;
	}
	
	public void deleteSession(String id) {
		if (this.sessions.containsKey(id)) {
			String host = this.sessions.get(id).getHost();
			int port = this.sessions.get(id).getPort();
			this.resources.freeResource(host, port);
			this.sessions.remove(id);
		}
	}
}
