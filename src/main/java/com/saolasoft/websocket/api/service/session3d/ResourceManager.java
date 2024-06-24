package com.saolasoft.websocket.api.service.session3d;

import com.saolasoft.websocket.api.service.session3d.model.Resource;
import com.saolasoft.websocket.config.ConfigProperties;
import com.saolasoft.websocket.config.model.ResourceProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceManager {
	
	private ConfigProperties configProperties;
	private Map<String, Resource> resources;
	
	public ResourceManager() {
		this.resources = new HashMap<>();
	}
	
	public void setConfigProperties(ConfigProperties configProperties) {
		this.configProperties = configProperties;
		List<ResourceProperty> resourceList = this.configProperties.getResources();
		for(int i = 0; i < resourceList.size(); ++i) {
			
			String host = resourceList.get(i).getHost();
			
			List<Integer> portRange = resourceList.get(i).getPortRange();
			
			List<Integer> portList = new ArrayList<>();
;			for (int v = portRange.get(0); v <= portRange.get(1); ++v) {
				portList.add(v);
			}
			
			if (!this.resources.containsKey(host)) {
				List<Integer> used = new ArrayList<Integer>();
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
				int index = this.resources.get(host).getUsedAvailable().indexOf(port);
				this.resources.get(host).getUsedAvailable().remove(index);
			}
			if (!this.resources.get(host).getAvailableResource().contains(port)) {
				this.resources.get(host).getAvailableResource().add(port);
			}
		}
	}
}
