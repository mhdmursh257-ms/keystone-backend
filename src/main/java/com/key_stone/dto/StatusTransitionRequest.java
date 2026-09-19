package com.key_stone.dto;

import com.key_stone.Enum.WorkOrderStatus;

import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
public class StatusTransitionRequest {

	@NotNull(message="Target status is required")
	private WorkOrderStatus targetStatus;
	private String notes;

	public WorkOrderStatus getTargetStatus() {
		return targetStatus;
	}

	public void setTargetStatus(WorkOrderStatus targetStatus) {
		this.targetStatus = targetStatus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
