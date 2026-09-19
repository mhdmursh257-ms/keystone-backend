package com.key_stone.Entity;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "time_logs")
public class TimeLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "work_order_id", nullable = false)
	private Long workOrderId;
	
	@Column(name = "technician_id", nullable = false)
	private Long technicianId;
	
	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;
	
	@Column(name = "end_time")
	private LocalDateTime endTime;
	
	@Column(name = "duration_minutes")
	private Long durationMinutes;
	
	@Column(columnDefinition = "TEXT")
	private String comments;
	
	public TimeLog() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public Long getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(Long technicianId) {
		this.technicianId = technicianId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Long getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(Long durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
