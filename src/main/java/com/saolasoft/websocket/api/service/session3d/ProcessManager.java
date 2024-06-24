package com.saolasoft.websocket.api.service.session3d;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.saolasoft.websocket.config.AppConfigProperties;
import com.saolasoft.websocket.config.ConfigProperties;
import com.saolasoft.websocket.api.service.session3d.model.Session;
import java.lang.Process;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProcessManager {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ConfigProperties configProperties;
	private AppConfigProperties appConfigProperties;
	
	private Map<String, Process> processes;
	
	public ProcessManager() {
		this.processes = new HashMap<>();
	}
	
	public void setConfigProperties(ConfigProperties configProperties) {
		this.configProperties = configProperties;
	}
	
	public void setAppConfigProperties(AppConfigProperties appConfigProperties) {
		this.appConfigProperties = appConfigProperties;
	}
	
	private String getLogFilePath(String id) {
		return String.format("%s%s%s.txt", this.configProperties.getLogPath(), "/", id);
	}
	
	public Process startProcess(Session session) {
		// Create output log file
		String logFilePath = this.getLogFilePath(session.getId());
		
		try {
			// Set the command to run
			ProcessBuilder builder = new ProcessBuilder(session.getCmd().split(" "));
			
			// Write to log file
			File logFile = new File(logFilePath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}
			builder.redirectOutput(ProcessBuilder.Redirect.appendTo(logFile));
			builder.redirectError(ProcessBuilder.Redirect.appendTo(logFile));
			
			// Start the process
			Process process = builder.start();
			
			// Wait for the process to complete
			//int exitCode = process.waitFor();
			
			// Save process
			this.processes.put(session.getId(), process);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		return this.processes.get(session.getId());
	}
	
	public void stopProcess(String id) {
		Process process = this.processes.get(id);
		try {
			process.destroy();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public boolean isRunning(String id) {
		return this.processes.get(id).isAlive();
	}
	
	public List<String> listEndedProcess() {
		List<String> sessionToRelease = new ArrayList<>();
		for (String id: this.processes.keySet()) {
			if (!this.processes.get(id).isAlive()) {
				sessionToRelease.add(id);
			}
		}
		return sessionToRelease;
	}
	
	public boolean isReady(Session session, int count) {
		// The process has to be running to be ready
		if (!this.isRunning(session.getId()) && count < 60) {
			return false;
		}
		
		// Give up after 60 seconds if still not running
		if (!this.isRunning(session.getId())) {
			return true;
		}
		
		// Check the output for ready line
		String readyLine = this.appConfigProperties.getViewer().getReadyLine();
		boolean ready = false;
		try {
			String logFilePath = this.getLogFilePath(session.getId());
			String content = Files.readString(Paths.get(logFilePath));
			if (content.contains(readyLine)) {
				ready = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return ready;
	}
}
