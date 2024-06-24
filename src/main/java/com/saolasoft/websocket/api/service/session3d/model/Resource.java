package com.saolasoft.websocket.api.service.session3d.model;

import java.util.List;

public class Resource {
	
	private List<Integer> available;
	private List<Integer> used;
	
	public Resource(List<Integer> available, List<Integer> used) {
		this.available = available;
		this.used = used;
	}
	
	public List<Integer> getAvailableResource() {
		return this.available;
	}
	
	public List<Integer> getUsedAvailable() {
		return this.used;
	}
}
