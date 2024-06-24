package com.saolasoft.websocket.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.saolasoft.websocket.api.dto.WebSocketDTOCreate;
import com.saolasoft.websocket.api.dto.WebSocketDTOGet;
import com.saolasoft.websocket.api.response.APIResponse;
import com.saolasoft.websocket.api.service.session3d.SessionService;
import com.saolasoft.websocket.api.service.session3d.model.Session;
import com.saolasoft.websocket.config.AppConfigProperties;
import com.saolasoft.websocket.config.ConfigProperties;

@RestController
public class SessionAPI {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ConfigProperties configProperties;
	
	@Autowired
	AppConfigProperties appConfigProperties;
	
	@Autowired
	private SessionService sessionService;
	
	@PostMapping("/websocketlink")
    public APIResponse<WebSocketDTOGet> getwslink(@RequestBody WebSocketDTOCreate payload) {
		if (this.sessionService.getConfigProperties() == null) {
			this.sessionService.setConfigProperties(configProperties);
		}
		if (this.sessionService.getAppConfigProperties() == null) {
			this.sessionService.setAppConfigProperties(appConfigProperties);
		}
		
		APIResponse<WebSocketDTOGet> response = this.sessionService.handlePost(payload);
		return response;
    }
	
	@GetMapping("/session")
	public APIResponse<Session> getSession(@RequestParam String id) {
		if (this.sessionService.getConfigProperties() == null) {
			this.sessionService.setConfigProperties(configProperties);
		}
		if (this.sessionService.getAppConfigProperties() == null) {
			this.sessionService.setAppConfigProperties(appConfigProperties);
		}
		
		APIResponse<Session> response = this.sessionService.handleGet(id);
		return response;
	}
	
	@DeleteMapping("/session")
	public APIResponse<String> deleteSession(@RequestParam String id) {
		if (this.sessionService.getConfigProperties() == null) {
			this.sessionService.setConfigProperties(configProperties);
		}
		if (this.sessionService.getAppConfigProperties() == null) {
			this.sessionService.setAppConfigProperties(appConfigProperties);
		}
		
		APIResponse<String> response = this.sessionService.handleDelete(id);
		return response;
	}
}
