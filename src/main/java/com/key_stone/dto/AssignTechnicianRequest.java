package com.key_stone.dto;

import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
public class AssignTechnicianRequest {

	@NotNull(message="Technician ID is required")
	private Long technicianId;

	public Long getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(Long technicianId) {
		this.technicianId = technicianId;
	}
	
}
