package com.key_stone.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.key_stone.Entity.TimeLog;
import com.key_stone.Repository.TimeLogRepository;

@Service
public class TimeLogService {

	@Autowired
	private TimeLogRepository timeLogRepository;
	
	// start work timer
	@Transactional
	public TimeLog startTimeLog(Long workOrderId, Long technicianId, String comments) {
		// prevent multiple active sessions
		timeLogRepository.findByWorkOrderIdAndTechnicianIdAndEndTimeIsNull(workOrderId, technicianId)
			.ifPresent(existing -> {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Work timer is already running for this Work Order.");
			});
		
		TimeLog log = new TimeLog();
		log.setWorkOrderId(workOrderId);
		log.setTechnicianId(technicianId);
		log.setStartTime(LocalDateTime.now());
		log.setComments(comments);
		
		return timeLogRepository.save(log);
	}
	
	// stop work timer & calculate duration
	@Transactional
	public TimeLog stopTimeLog(Long timeLogId) {
		TimeLog log = timeLogRepository.findById(timeLogId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TIme Log session not found."));
		
		if (log.getEndTime() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This time log session has already been stopped.");
		}
		
		LocalDateTime endTime = LocalDateTime.now();
		log.setEndTime(endTime);
		
		// auto duration calculation in min.
		long minutes = Duration.between(log.getStartTime(), endTime).toMinutes();
		log.setDurationMinutes(minutes);
		
		return timeLogRepository.save(log);
	}
	
	public List<TimeLog> getTimeLogsForWorkOrder(Long workOrderId) {
		return timeLogRepository.findByWorkOrderId(workOrderId);
	}
}
