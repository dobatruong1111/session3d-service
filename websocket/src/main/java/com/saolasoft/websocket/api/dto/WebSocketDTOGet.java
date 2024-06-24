package com.saolasoft.websocket.api.dto;

public class WebSocketDTOGet {
	
	private String id;
	private String secret;
	private String sessionUrl;
	
	public WebSocketDTOGet(String id, String secret, String sessionUrl) {
		this.id = id;
		this.secret = secret;
		this.sessionUrl = sessionUrl;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getSecret() {
		return this.secret;
	}
	
	public String getSessionUrl() {
		return this.sessionUrl;
	}
}
