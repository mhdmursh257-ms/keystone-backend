package com.key_stone.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.key_stone.Service.DashboardService;
import com.key_stone.dto.DashboardSummaryDTO;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
	
	@Autowired
	private DashboardService dashboardService;
	
	// get high-level summary for manager & dispatcher
	@GetMapping("/summary")
	@PreAuthorize("hasAnyRole('MANAGER', 'DISPATCHER')")
	public ResponseEntity<DashboardSummaryDTO> getDashboardSummary() {
		return ResponseEntity.ok(dashboardService.getDashboardMetrics());
	}
	
}
