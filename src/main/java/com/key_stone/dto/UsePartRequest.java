package com.key_stone.dto;

import javax.validation.constraints.*;

public class UsePartRequest {

	@NotNull(message = "Part ID is required")
	private Long partId;
	
	@NotNull(message = "Quantity is required")
	@Positive(message = "Quantity must be greater than zero")
	private Integer quantity;

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
