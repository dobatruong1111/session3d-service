package com.saolasoft.websocket.api.service.session3d;

import java.util.ArrayList;

public class Session3D {
	
	private String id;
	private String host;
	private int port;
	private String secret;
	private String sessionUrl;
	private ArrayList<String> cmd;
	
	public Session3D(String id, String host, int port, String secret, String sessionUrl, ArrayList<String> cmd) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.secret = secret;
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
	
	public String getSecret() {
		return this.secret;
	}
	
	public String getSessionUrl() {
		return this.sessionUrl;
	}
	
	public ArrayList<String> getCmd() {
		return this.cmd;
	}
}
