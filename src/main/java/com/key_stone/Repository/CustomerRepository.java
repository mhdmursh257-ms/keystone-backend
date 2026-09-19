package com.key_stone.Repository;

import com.key_stone.Entity.UserAuth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<UserAuth, Long> {
	Optional<UserAuth> findByUserEmail(String userEmail);
}
