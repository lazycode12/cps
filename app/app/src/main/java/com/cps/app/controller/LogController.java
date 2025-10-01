package com.cps.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cps.app.model.LogEntry;
import com.cps.app.repo.LogEntryRepository;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    
    private final LogEntryRepository logRepository;
    
    public LogController(LogEntryRepository logRepository) {
        this.logRepository = logRepository;
    }
    
    @GetMapping("")
    public ResponseEntity<List<LogEntry>> getAllLogs(
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        List<LogEntry> logs = logRepository.findAll();
      
        
        // Simple pagination
        int start = Math.min(page * size, logs.size());
        int end = Math.min((page + 1) * size, logs.size());
        
        if (start >= logs.size()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        
        List<LogEntry> paginatedLogs = logs.subList(start, end);
        return ResponseEntity.ok(paginatedLogs);
    }
    
}
