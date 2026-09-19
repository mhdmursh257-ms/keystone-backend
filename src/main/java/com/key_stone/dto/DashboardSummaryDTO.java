package com.key_stone.dto;

import java.util.Map;

public class DashboardSummaryDTO {
	
	private long totalWorkOrders;
	private Map<String, Long> statusBreakdown;
	private long totalSlaBreached;
	private double slaBreachPercentage;
	private double totalPartsCost;
	
	public DashboardSummaryDTO() {}

	public long getTotalWorkOrders() {
		return totalWorkOrders;
	}

	public void setTotalWorkOrders(long totalWorkOrders) {
		this.totalWorkOrders = totalWorkOrders;
	}

	public Map<String, Long> getStatusBreakdown() {
		return statusBreakdown;
	}

	public void setStatusBreakdown(Map<String, Long> statusBreakdown) {
		this.statusBreakdown = statusBreakdown;
	}

	public long getTotalSlaBreached() {
		return totalSlaBreached;
	}

	public void setTotalSlaBreached(long totalSlaBreached) {
		this.totalSlaBreached = totalSlaBreached;
	}

	public double getSlaBreachPercentage() {
		return slaBreachPercentage;
	}

	public void setSlaBreachPercentage(double slaBreachPercentage) {
		this.slaBreachPercentage = slaBreachPercentage;
	}

	public double getTotalPartsCost() {
		return totalPartsCost;
	}

	public void setTotalPartsCost(double totalPartsCost) {
		this.totalPartsCost = totalPartsCost;
	}

}
