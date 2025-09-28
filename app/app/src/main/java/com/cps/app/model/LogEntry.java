package com.cps.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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
    private String logger;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    // Constructors
    public LogEntry() {}
    
    public LogEntry(String level, String message, String logger, LocalDateTime timestamp) {
        this.level = level;
        this.message = message;
        this.logger = logger;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getLogger() { return logger; }
    public void setLogger(String logger) { this.logger = logger; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
