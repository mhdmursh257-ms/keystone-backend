package com.key_stone.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.key_stone.Entity.Part;
import com.key_stone.Entity.PartUsage;
import com.key_stone.Service.InventoryService;
import com.key_stone.dto.UsePartRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;
	
	// All inventory parts
	@GetMapping("/parts")
	@PreAuthorize("hasAnyRole('MANAGER', 'DISPATCHER', 'TECHNICIAN')")
	public ResponseEntity<List<Part>> getAllParts() {
		return ResponseEntity.ok(inventoryService.getAllParts());
	}
	
	// Add new part
	@PostMapping("/parts")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<Part> createPart(@RequestBody Part part) {
		return ResponseEntity.ok(inventoryService.savePart(part));
	}
	
	// Log part usage agnst a workorder
	@PostMapping("/work-orders/{workOrderId}/parts")
	@PreAuthorize("hasAnyRole('MANAGER', 'TECHNICIAN')")
	public ResponseEntity<PartUsage> usePart(
			@PathVariable Long workOrderId,
			@Valid @RequestBody UsePartRequest request) {
		
		PartUsage usage = inventoryService.usePartInWorkOrder(workOrderId, request.getPartId(), request.getQuantity());
		return ResponseEntity.ok(usage);
	}
	
	@GetMapping("/work-orders/{workOrderId}/parts")
	@PreAuthorize("hasAnyrole('MANAGER', 'DISPATCHER', 'TECHNICIAN')")
	public ResponseEntity<List<PartUsage>>  getWorkOrderParts(@PathVariable Long workOrderId) {
		return ResponseEntity.ok(inventoryService.getPartsUsedByWorkOrder(workOrderId));
	}
	
}
