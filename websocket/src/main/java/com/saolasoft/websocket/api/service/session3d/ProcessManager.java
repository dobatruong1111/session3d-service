package com.saolasoft.websocket.api.service.session3d;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class ProcessManager {
	
	private JsonNode config;
	private String logDirectory;
	private Map<String, Object> processes = new HashMap<>();
	
	public ProcessManager(JsonNode config, String logDirectory ) {
		this.config = config;
		this.logDirectory = config.get("configuration").get("log_dir").asText();
	}
	
	public String getLogFilePath(String id) {
		return String.format("%s%s%s.txt", this.logDirectory, "/", id);
	}
}
