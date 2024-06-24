package com.saolasoft.websocket.api.service.session3d.model;

public class Session {
	
	private String id;
	private String host;
	private int port;
	private String sessionUrl;
	private String cmd;
	
	public Session(String id, String host, int port, String sessionUrl, String cmd) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.sessionUrl = sessionUrl;
		this.cmd = cmd;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public String getSessionUrl() {
		return this.sessionUrl;
	}
	
	public String getCmd() {
		return this.cmd;
	}
}
