package com.cps.app.dto.response;

import java.util.List;

import com.cps.app.model.LogEntry;

public class LogResponse {
    private List<LogEntry> logs;
    private Long latestId;
    
    
    
    
	public LogResponse() {
		super();
	}

	public LogResponse(List<LogEntry> logs, Long latestId) {
		super();
		this.logs = logs;
		this.latestId = latestId;
	}

	public List<LogEntry> getLogs() {
		return logs;
	}

	public void setLogs(List<LogEntry> logs) {
		this.logs = logs;
	}

	public Long getLatestId() {
		return latestId;
	}

	public void setLatestId(Long latestId) {
		this.latestId = latestId;
	}
    
	
    
}
