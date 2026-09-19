package com.key_stone.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.key_stone.Entity.TimeLog;
import com.key_stone.Service.TimeLogService;
import com.key_stone.dto.StartTimeLogRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/work-orders/{workOrderId}/time-logs")
public class TimeLogController {
	
	@Autowired
	private TimeLogService timeLogService;
	
	// start timer 
	@PostMapping("/start")
	@PreAuthorize("hasAnyRole('MANAGER', 'TECHNICIAN')")
	public ResponseEntity<TimeLog> startTimeLog(
			@PathVariable Long workOrderId,
			@Valid @RequestBody StartTimeLogRequest request) {
		
		TimeLog log = timeLogService.startTimeLog(workOrderId, request.getTechnicianId(), request.getComments());
		return ResponseEntity.ok(log);
	}
	
	// stop timer
	@PostMapping("/stop/{timeLogId}")
	@PreAuthorize("hasAnyRole('MANAGER', 'TECHNICIAN')")
	public ResponseEntity<TimeLog> stopTimeLog(
			@PathVariable Long workOrderId,
			@PathVariable Long timeLogId) {
		
		TimeLog updated = timeLogService.stopTimeLog(timeLogId);
		return ResponseEntity.ok(updated);
	}
	
	// view all for specific order
	@GetMapping
	@PreAuthorize("hasAnyRole('MANAGER', 'DISPATCHER', 'TECHNICIAN')")
	public ResponseEntity<List<TimeLog>> getTimeLogs(@PathVariable Long workOrderId) {
		return ResponseEntity.ok(timeLogService.getTimeLogsForWorkOrder(workOrderId));
	}
}
