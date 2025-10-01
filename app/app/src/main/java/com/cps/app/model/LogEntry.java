package com.cps.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "log_entries")
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String level;
    
    @Column(nullable = false)
    private String message;
    
    @Column(nullable = false)
    private String loggerName;
    
    @Column(nullable = false)
    private String timestamp;
    
    @Column(name="user")
    private String user;
    
    // Constructors
    public LogEntry() {}
    
    public LogEntry(String level, String message, String loggerName, String timestamp, String user) {
        this.level = level;
        this.message = message;
        this.loggerName = loggerName;
        this.timestamp = timestamp;
        this.user = user;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getLogger() { return loggerName; }
    public void setLogger(String logger) { this.loggerName = logger; }
    
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

	public String getLoggerName() {
		return loggerName;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
    
}
