package com.saolasoft.websocket.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saolasoft.websocket.api.dto.WebSocketDTOGet;
import com.saolasoft.websocket.api.service.session3d.Session3D;
import com.saolasoft.websocket.api.service.session3d.SessionManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

@RestController
public class Session3DAPI {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private JsonNode jsonNode = null;
//	private SessionManager sessionManager = null;
	
	@PostMapping("/getwslink")
    public String getwslink(@RequestBody WebSocketDTOGet payload) throws IOException {
		if (this.jsonNode == null) {
			ObjectMapper objectMapper = new ObjectMapper();
			this.jsonNode = objectMapper.readTree(new File("config.json"));
		}
		
//		if (this.sessionManager == null) {
//			this.sessionManager = new SessionManager(this.jsonNode);
//		}
//		
//		// Create new session 3d
//		Session3D session3d = this.sessionManager.createSession(payload);
//		if (session3d != null) {
//			logger.info("id: " + session3d.getId());
//		}
//		logger.info("null");
		
		String a = this.jsonNode.get("configuration").get("log_dir").asText();
		
		logger.info("h");
		
		return String.format("Hello %s!", payload.getApplication());
    }
}
