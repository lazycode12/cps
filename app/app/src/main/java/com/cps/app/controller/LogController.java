package com.cps.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cps.app.model.LogEntry;
import com.cps.app.service.LogEntryService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/logs")
public class LogController {
    
    @Autowired
    private LogEntryService logEntryService;
    
    @GetMapping("")
    public ResponseEntity<Page<LogEntry>> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        Page<LogEntry> logs = logEntryService.getAllLogs(page, size);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/level/{level}")
    public ResponseEntity<Page<LogEntry>> getLogsByLevel(
            @PathVariable String level,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        Page<LogEntry> logs = logEntryService.getLogsByLevel(level, page, size);
        return ResponseEntity.ok(logs);
    }
    
    @GetMapping("/time-range")
    public ResponseEntity<Page<LogEntry>> getLogsByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        Page<LogEntry> logs = logEntryService.getLogsByTimeRange(start, end, page, size);
        return ResponseEntity.ok(logs);
    }
    
    @PostMapping("")
    public ResponseEntity<LogEntry> createLog(@RequestBody LogEntry logEntry) {
        LogEntry savedLog = logEntryService.saveLog(logEntry);
        return ResponseEntity.ok(savedLog);
    }
}
