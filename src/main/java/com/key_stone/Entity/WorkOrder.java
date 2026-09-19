package com.key_stone.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.key_stone.Enum.Priority;
import com.key_stone.Enum.WorkOrderStatus;

import javax.persistence.*;
import lombok.*;


@Entity
@Table(name="work_orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WorkOrder {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String code;
	
	@Column(nullable=false)
	private String title;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Priority priority;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private WorkOrderStatus status;
	
	@Column(name="sla_due_at")
	private LocalDateTime slaDueAt;
	
	@Column(name="is_sla_breached")
	private Boolean isSlaBreached = false;
	
	@Column(name="tracking_token", unique=true)
	private String trackingToken;
	
	@Column(name="customer_id", nullable=false)
	private Long customerId;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="site_id", nullable=false)
	private Site site;
	
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="assigned_tech_id")
	private UserAuth assignedTo;
	
	@Column(name="created_at", updatable=false)
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt=LocalDateTime.now();
		this.updatedAt=LocalDateTime.now();
		if(this.status == null) {
			this.status = WorkOrderStatus.NEW;
		}
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt=LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public WorkOrderStatus getStatus() {
		return status;
	}

	public void setStatus(WorkOrderStatus status) {
		this.status = status;
	}

	public LocalDateTime getSlaDueAt() {
		return slaDueAt;
	}

	public void setSlaDueAt(LocalDateTime slaDueAt) {
		this.slaDueAt = slaDueAt;
	}

	public Boolean getIsSlaBreached() {
		return isSlaBreached;
	}

	public void setIsSlaBreached(Boolean isSlaBreached) {
		this.isSlaBreached = isSlaBreached;
	}

	public String getTrackingToken() {
		return trackingToken;
	}

	public void setTrackingToken(String trackingToken) {
		this.trackingToken = trackingToken;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public UserAuth getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(UserAuth assignedTo) {
		this.assignedTo = assignedTo;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
