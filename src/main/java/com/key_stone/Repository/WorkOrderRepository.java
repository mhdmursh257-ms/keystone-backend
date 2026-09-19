package com.key_stone.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.key_stone.Entity.WorkOrder;
import com.key_stone.Enum.WorkOrderStatus;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long>{

	Page<WorkOrder> findByCustomerId(Long customerId, Pageable pageable);
	
	Page<WorkOrder> findByAssignedTo_Id(Long technicianId, Pageable pageable);
	
	Page<WorkOrder> findByStatus(WorkOrderStatus status, Pageable pageable);
	
	Optional<WorkOrder> findByTrackingToken(String trackingToken);
	
	List<WorkOrder> findByStatusNotInAndIsSlaBreachedFalse(List<WorkOrderStatus> terminalStatuses);
	
}
