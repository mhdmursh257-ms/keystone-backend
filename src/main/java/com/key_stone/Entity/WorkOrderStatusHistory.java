package com.key_stone.Entity;

import java.time.LocalDateTime;

import com.key_stone.Enum.WorkOrderStatus;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="work_order_status_history")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderStatusHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="work_order_id", nullable=false)
	private Long workOrderId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="from_status")
	private WorkOrderStatus fromStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name="to_status", nullable=false)
	private WorkOrderStatus toStatus;
	
	@Column(name="changed_by_user_id", nullable=false)
	private Long changedByUserId;
	
	@Column(name="changed_at", nullable=false)
	private LocalDateTime changedAt;
	
	@Column(columnDefinition="TEXT")
	private String notes;
	
	@PrePersist
	protected void onCreate() {
		this.changedAt=LocalDateTime.now();
	}

	public void setWorkOrderId(Long workOrderId) {
		// TODO Auto-generated method stub
		this.workOrderId = workOrderId;
	}

	public void setFromStatus(WorkOrderStatus fromStatus) {
		// TODO Auto-generated method stub
		this.fromStatus = fromStatus;
	}

	public void setToStatus(WorkOrderStatus toStatus) {
		// TODO Auto-generated method stub
		this.toStatus = toStatus;
	}

	public void setChangedByUserId(Long changedByUserId) {
		// TODO Auto-generated method stub
		this.changedByUserId = changedByUserId;
	}

	public void setChangedAt(LocalDateTime changedAt) {
		// TODO Auto-generated method stub
		this.changedAt = changedAt;
	}

	public void setNotes(String notes) {
		// TODO Auto-generated method stub
		this.notes = notes;
	}
	
}
