package com.saolasoft.websocket.api.dto;

public class WebSocketDTOGet {
	
	private String application;
	private Boolean useUrl;
	private String studyUUID;
	private String seriesUUID;
	private String session2D;
	
	public WebSocketDTOGet(String application, Boolean useUrl, String studyUUID, String seriesUUID, String session2D) {
		this.application = application;
		this.useUrl = useUrl;
		this.studyUUID = studyUUID;
		this.seriesUUID = seriesUUID;
		this.session2D = session2D;
	}
	
	public String getApplication() {
		return this.application;
	}
	
	public boolean getUseUrl() {
		return this.useUrl;
	}
	
	public String getStudyUUID() {
		return this.studyUUID;
	}
	
	public String getSeriesUUID() {
		return this.seriesUUID;
	}
	
	public String getSession2D() {
		return this.session2D;
	}
}
