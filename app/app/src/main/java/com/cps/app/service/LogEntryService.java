package com.cps.app.service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cps.app.model.LogEntry;
import com.cps.app.repo.LogEntryRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogEntryService {
    
    private final LogEntryRepository logRepository;
    
    public LogEntryService(LogEntryRepository logRepository) {
        this.logRepository = logRepository;
    }
    
    public void log(String level, String message, String loggerName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    String username = "anonymous";
	    if (authentication != null && authentication.isAuthenticated()) {
	        username = authentication.getName();
	    }
		LogEntry log = new LogEntry(level, message, loggerName, timestamp, username);
		
        logRepository.save(log);
        
        // Also print to console
        System.out.printf("[%s] %s: %s%n", timestamp, level, message);
    }
    
    public void info(String message, String loggerName) {
        log("INFO", message, loggerName);
    }
    
    public void error(String message, String loggerName) {
        log("ERROR", message, loggerName);
    }
    
    public void warn(String message, String loggerName) {
        log("WARN", message, loggerName);
    }
    
    public void debug(String message, String loggerName) {
        log("DEBUG", message, loggerName);
    }
    
}
	  
