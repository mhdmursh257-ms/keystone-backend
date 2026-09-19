package com.key_stone.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.key_stone.Entity.PartUsage;
import com.key_stone.Entity.WorkOrder;
import com.key_stone.Repository.PartUsageRepository;
import com.key_stone.Repository.WorkOrderRepository;
import com.key_stone.dto.DashboardSummaryDTO;

@Service
public class DashboardService {
	
	@Autowired
	private WorkOrderRepository workOrderRepository;
	
	@Autowired
	private PartUsageRepository partUsageRepository;
	
	public DashboardSummaryDTO getDashboardMetrics() {
		List<WorkOrder> allWorkOrders = workOrderRepository.findAll();
		long totalOrders = allWorkOrders.size();
		
		// status breakdown
		Map<String, Long> statusMap = allWorkOrders.stream()
				.collect(Collectors.groupingBy(wo -> wo.getStatus().name(), Collectors.counting()));
		
		// sla breach calculation
		long slaBreachedCount = allWorkOrders.stream()
				.filter(wo -> Boolean.TRUE.equals(wo.getIsSlaBreached())).count();
		
		double breachPercentage = totalOrders > 0 ? ((double) slaBreachedCount / totalOrders) * 100 : 0.0;
		
		// total parts cost across all orders
		List<PartUsage> usages = partUsageRepository.findAll();
		double totalPartsCost = usages.stream()
				.mapToDouble(pu -> pu.getTotalCost() != null ? pu.getTotalCost() : 0.0).sum();
				
		DashboardSummaryDTO summary = new DashboardSummaryDTO();
		summary.setTotalWorkOrders(totalOrders);
		summary.setStatusBreakdown(statusMap);
		summary.setTotalSlaBreached(slaBreachedCount);
		summary.setSlaBreachPercentage(Math.round(breachPercentage));
		summary.setTotalPartsCost(totalPartsCost);
		
		return summary;
	}
}
