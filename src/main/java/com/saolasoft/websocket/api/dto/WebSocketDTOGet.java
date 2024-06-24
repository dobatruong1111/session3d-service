package com.saolasoft.websocket.api.dto;

public class WebSocketDTOGet {
	
	private String id;
	private String sessionUrl;
	
	public WebSocketDTOGet(String id, String sessionUrl) {
		this.id = id;
		this.sessionUrl = sessionUrl;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getSessionUrl() {
		return this.sessionUrl;
	}
}
