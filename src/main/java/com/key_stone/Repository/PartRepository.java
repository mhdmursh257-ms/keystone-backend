package com.key_stone.Repository;

import com.key_stone.Entity.Part;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long>{
	
}
