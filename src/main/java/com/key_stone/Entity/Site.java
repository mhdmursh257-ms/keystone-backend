package com.key_stone.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Entity
@Table(name="sites")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Site {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	@NotBlank(message = "Site address is required")
	@Column(nullable=false, columnDefinition="TEXT")
	private String address;
	
	@Column(nullable=false)
	private Long customerId;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	} 
	
}
