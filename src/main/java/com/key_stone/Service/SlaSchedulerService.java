package com.key_stone.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.key_stone.Entity.WorkOrder;
import com.key_stone.Enum.WorkOrderStatus;
import com.key_stone.Repository.WorkOrderRepository;

@Service
public class SlaSchedulerService {
	
	@Autowired
	private WorkOrderRepository workOrderRepository;
	
	// runs every 15 mins
	@Scheduled(cron = "0 */15 * * * *")
	@Transactional
	public void chechAndFlagSlaBreaches() {
		List<WorkOrderStatus> completedStatuses = Arrays.asList(WorkOrderStatus.COMPLETED, WorkOrderStatus.CLOSED, WorkOrderStatus.CANCELLED);
		
		// fetch active jobs where sla hasn't been flagged yet
		List<WorkOrder> activeOrders = workOrderRepository.findByStatusNotInAndIsSlaBreachedFalse(completedStatuses);
		
		LocalDateTime now = LocalDateTime.now();
		
		for (WorkOrder wo : activeOrders) {
			if(wo.getSlaDueAt() != null && wo.getSlaDueAt().isBefore(now)) {
				wo.setIsSlaBreached(true);
				workOrderRepository.save(wo);
				System.out.println("ALERT: SLA Breahed for Work Order: " + wo.getCode());
			}
		}
	}
}
