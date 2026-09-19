package com.key_stone.Entity;

import javax.persistence.*;

@Entity
@Table(name = "parts")
public class Part {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false)
	private String partNumber;
	
	@Column(nullable = false)
	private Integer stockQuantity;
	
	@Column(nullable = false)
	private Double unitPrice;
	
	public Part() {}
	
	public Part(Long id, String name, String partNumber, Integer stockQuantity, Double unitPrice) {
		this.id = id;
		this.name = name;
		this.partNumber = partNumber;
		this.stockQuantity = stockQuantity;
		this.unitPrice = unitPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}
