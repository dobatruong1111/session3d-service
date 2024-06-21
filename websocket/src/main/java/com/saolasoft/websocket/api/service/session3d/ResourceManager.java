package com.saolasoft.websocket.api.service.session3d;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.saolasoft.websocket.api.service.session3d.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
	
	private Map<String, Resource> resources;
	
	public ResourceManager(ArrayNode resourceList) {
		this.resources = new HashMap<>();
		
		for(int i = 0; i < resourceList.size(); ++i) {
			String host = resourceList.get(i).get("host").asText();
			ArrayNode portRange = (ArrayNode) resourceList.get(i).get("port_range");
			
			ArrayList<Integer> portList = new ArrayList<Integer>();
			for (int v = portRange.get(0).asInt(); v <= portRange.get(1).asInt(); ++v) {
				portList.add(v);
			}
			
			if (!this.resources.containsKey(host)) {
				ArrayList<Integer> used = new ArrayList<Integer>();
				this.resources.put(host, new Resource(portList, used));
			} else {
				this.resources.get(host).getAvailableResource().addAll(portList);
			}
		}
	}
	
	public String getNextResource() {
		String winner = null;
		int availibilityCount = 0;
		
		for (String host: this.resources.keySet()) {
			if (availibilityCount < this.resources.get(host).getAvailableResource().size()) {
				availibilityCount = this.resources.get(host).getAvailableResource().size();
				winner = host;
			}
		}
		
		if (winner != null) {
			int size = this.resources.get(winner).getAvailableResource().size();
			int port = this.resources.get(winner).getAvailableResource().get(size - 1);
			this.resources.get(winner).getAvailableResource().remove(size - 1);
			this.resources.get(winner).getUsedAvailable().add(port);
			return String.format("%s:%d", winner, port);
		}
		return "";
	}
	
	public void freeResource(String host, int port) {
		if (this.resources.containsKey(host)) {
			if (this.resources.get(host).getUsedAvailable().contains(port)) {
				this.resources.get(host).getUsedAvailable().remove(port);
			}
			if (!this.resources.get(host).getAvailableResource().contains(port)) {
				this.resources.get(host).getAvailableResource().add(port);
			}
		}
	}
}
