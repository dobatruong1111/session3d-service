package com.saolasoft.websocket.api_bo.service.session3d;

import java.util.ArrayList;

public class Resource {
	
	private ArrayList<Integer> available;
	private ArrayList<Integer> used;
	
	public Resource(ArrayList<Integer> available, ArrayList<Integer> used) {
		this.available = available;
		this.used = used;
	}
	
	public ArrayList<Integer> getAvailableResource() {
		return this.available;
	}
	
	public ArrayList<Integer> getUsedAvailable() {
		return this.used;
	}
}
