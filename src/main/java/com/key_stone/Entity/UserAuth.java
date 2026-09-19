package com.key_stone.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.key_stone.Enum.Role;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="user_auth")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserAuth {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	   
	@Column(nullable=false)
	private String userName;
	
	@Column(nullable=false, unique=true)
	private String userEmail;
	
	@Column(nullable=false)
	private String password;
	
	private String phone;
	
	@Enumerated(EnumType.STRING)
	private Role role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}