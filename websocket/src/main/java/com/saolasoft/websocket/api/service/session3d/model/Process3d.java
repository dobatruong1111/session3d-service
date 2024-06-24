package com.saolasoft.websocket.api.service.session3d.model;

public class Process3d {

	private String host;
	private int port;
	private String processId;
	
	public Process3d(String host, int port, String processId) {
		this.host = host;
		this.port = port;
		this.processId = processId;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public String getProcessId() {
		return this.processId;
	}
}
