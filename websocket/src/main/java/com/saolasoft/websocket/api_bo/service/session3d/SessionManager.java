package com.saolasoft.websocket.api_bo.service.session3d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.saolasoft.websocket.api_bo.dto.WebSocketDTOGet;

public class SessionManager {
	
	private Map<String, Session3D> sessions;
	private JsonNode config;
	private ResourceManager resources;
	private JsonNode sanitize;
	// test
	private Map<String, Object> options;
	
	public SessionManager(JsonNode config) {
		this.sessions = new HashMap<>();
		this.config = config;
		this.resources = new ResourceManager((ArrayNode) config.get("resources"));
		this.sanitize = config.get("configuration").get("sanitize");
		// test
		this.options = new HashMap<>();
	}
	
	public Session3D createSession(WebSocketDTOGet options) {
		
		this.options.put("application", options.getApplication());
		this.options.put("useUrl", options.getUseUrl());
		this.options.put("studyUUID", options.getStudyUUID());
		this.options.put("seriesUUID", options.getSeriesUUID());
		this.options.put("session2D", options.getSession2D());
		
		String resource = this.resources.getNextResource();
		if (!resource.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String id = uuid.toString();
			this.options.put("id", id);
			
			String host = resource.split(":")[0];
			this.options.put("host", host);
			
			int port = Integer.parseInt(resource.split(":")[1]);
			this.options.put("port", port);
			
			String secret = "";
			this.options.put("secret", secret);
			
			String sessionUrl = this.config.get("configuration").get("sessionURL").asText().replace("${id}", id);
			this.options.put("sessionUrl", sessionUrl);
			
			ArrayNode cmd = (ArrayNode) this.config.get("apps").get(options.getApplication()).get("cmd");
			ArrayList<String> resultList = new ArrayList<String>();
			for (int i = 0; i < cmd.size(); ++i) {
				String temp = cmd.get(i).asText();
				if (temp.contains("$")) {
					for (String key: this.options.keySet()) {
						temp = temp.replace(String.format("${/s}", key), this.options.get(key).toString());
					}
				}
				resultList.add(temp);
			}
			this.options.put("cmd", resultList);
			
			Session3D session = new Session3D(id, host, port, secret, sessionUrl, resultList);
			this.sessions.put(id, session);
			
			return session;
		}
		return null;
	}
}
