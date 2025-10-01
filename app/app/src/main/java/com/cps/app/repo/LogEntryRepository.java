package com.cps.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cps.app.model.LogEntry;

import java.util.List;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
	
    List<LogEntry> findAllByOrderByTimestampDesc();
    List<LogEntry> findByLoggerNameContainingIgnoreCaseOrderByTimestampDesc(String loggerName);
    
}
