package com.cps.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cps.app.model.LogEntry;
import com.cps.app.repo.LogEntryRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogEntryService {
    
    @Autowired
    private LogEntryRepository logEntryRepository;
    
    public Page<LogEntry> getAllLogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return logEntryRepository.findAll(pageable);
    }
    
    public Page<LogEntry> getLogsByLevel(String level, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return logEntryRepository.findByLevel(level, pageable);
    }
    
    public Page<LogEntry> getLogsByTimeRange(LocalDateTime start, LocalDateTime end, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return logEntryRepository.findByTimestampBetween(start, end, pageable);
    }
    
    public LogEntry saveLog(LogEntry logEntry) {
        return logEntryRepository.save(logEntry);
    }
}
