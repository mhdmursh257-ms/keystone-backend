package com.key_stone.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.key_stone.Entity.UserAuth;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth,Long>{

	Optional<UserAuth>findByUserEmail(String userEmail);
}
