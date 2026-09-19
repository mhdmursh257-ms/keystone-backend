package com.key_stone.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.key_stone.Entity.TimeLog;

@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Long>{
	List<TimeLog> findByWorkOrderId(Long workOrderId);
	
	Optional<TimeLog> findByWorkOrderIdAndTechnicianIdAndEndTimeIsNull(Long workOrderId, Long technicianId);
}
