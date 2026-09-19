package com.key_stone.Entity;

import javax.persistence.*;

@Entity
@Table(name = "part_usages")
public class PartUsage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "work_order_id", nullable = false)
	private Long workOrderId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "part_id", nullable = false)
	private Part part;
	
	@Column(nullable = false)
	private Integer quantityUsed;
	
	@Column(nullable = false)
	private Double totalCost;
	
	public PartUsage() {}

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

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public Integer getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	
}
