package com.key_stone.dto;

import javax.validation.constraints.NotNull;

public class StartTimeLogRequest {

	@NotNull(message = "Technician ID is required")
	private Long technicianId;
	
	private String comments;

	public Long getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(Long technicianId) {
		this.technicianId = technicianId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
