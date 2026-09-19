package com.key_stone.Repository;

import com.key_stone.Entity.PartUsage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartUsageRepository extends JpaRepository<PartUsage, Long>{
	List<PartUsage> findByWorkOrderId(Long workOrderId);
}
