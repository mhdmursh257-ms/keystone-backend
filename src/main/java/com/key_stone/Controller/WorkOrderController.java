package com.key_stone.Controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.key_stone.Entity.WorkOrder;
import com.key_stone.Entity.WorkOrderStatusHistory;
import com.key_stone.Repository.WorkOrderRepository;
import com.key_stone.Repository.WorkOrderStatusHistoryRepository;
import com.key_stone.Service.InvoicePdfService;
import com.key_stone.Service.WorkOrderService;
import com.key_stone.dto.AssignTechnicianRequest;
import com.key_stone.dto.StatusTransitionRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/work-orders")
public class WorkOrderController {

	@Autowired
	private WorkOrderService workOrderService;
	
	@Autowired
	private WorkOrderRepository workOrderRepository;
	
	@Autowired
	private WorkOrderStatusHistoryRepository historyRepository;
	
	@Autowired
	private InvoicePdfService invoicePdfService;
	
	// new workorder
	@PostMapping
	@PreAuthorize("hasAnyRole('MANAGER', 'DISPATCHER', 'ADMIN')")
	public ResponseEntity<WorkOrder> createWorkOrder(@Valid @RequestBody WorkOrder workOrder) {
		WorkOrder created = workOrderRepository.save(workOrder);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
	
	// get all workorders
	@GetMapping
	@PreAuthorize("hasAnyRole('MANAGER', 'DISPATCHER', 'ADMIN')")
	public ResponseEntity<Page<WorkOrder>> getAllWorkOrders(Pageable pageable) {
		return ResponseEntity.ok(workOrderRepository.findAll(pageable));
	}
	
	// get workorder by id
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('MANAGER', 'DISPATCHER', 'TECHNICIAN', 'ADMIN')")
	public ResponseEntity<WorkOrder> getWorkOrderById(@PathVariable Long id) {
		return workOrderRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	// update existing work order
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('MANAGER', 'DISPATCHER', 'ADMIN')")
	public ResponseEntity<WorkOrder> updateWorkOrder(
			@PathVariable Long id, 
			@Valid @RequestBody WorkOrder workOrderDetails) {
		return workOrderRepository.findById(id).map(existingOrder -> {
			existingOrder.setPriority(workOrderDetails.getPriority());
			existingOrder.setStatus(workOrderDetails.getStatus());
			existingOrder.setDescription(workOrderDetails.getDescription());
			existingOrder.setSite(workOrderDetails.getSite());
			WorkOrder updatedOrder = workOrderRepository.save(existingOrder);
			return ResponseEntity.ok(updatedOrder);
		}).orElse(ResponseEntity.notFound().build());
	}

	// delete workorder 
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	public ResponseEntity<Void> deleteWorkOrder(@PathVariable Long id) {
		return workOrderRepository.findById(id).map(workOrder -> {
			workOrderRepository.delete(workOrder);
			return ResponseEntity.noContent().<Void>build();
		}).orElse(ResponseEntity.notFound().build());
	}
	
	// get status history
	@GetMapping("/{id}/history")
	@PreAuthorize("hasAnyRole('MANAGER', 'DISPATCHER', 'TECHNICIAN', 'ADMIN')")
	public ResponseEntity<List<WorkOrderStatusHistory>> getWorkOrderHistory(@PathVariable Long id) {
		return ResponseEntity.ok(historyRepository.findByWorkOrderIdOrderByChangedAtAsc(id));
	}
	
	// assign workorder to technician
	@PostMapping("/{id}/assign")
	@PreAuthorize("hasAnyRole('MANAGER', 'DISPATCHER', 'ADMIN')")
	public ResponseEntity<WorkOrder> assignTechnician(
			@PathVariable Long id,
			@Valid @RequestBody AssignTechnicianRequest request,
			@RequestParam Long dispatcherId) {
		
		WorkOrder updated = workOrderService.assignTechnician(id, request.getTechnicianId(), dispatcherId);
		return ResponseEntity.ok(updated);
	}
	
	// transition workorder status
	@PostMapping("/{id}/status")
	@PreAuthorize("hasAnyRole('MANAGER', 'DISPATCHER', 'TECHNICIAN', 'ADMIN')")
	public ResponseEntity<WorkOrder> transitionStatus(
			@PathVariable Long id,
			@Valid @RequestBody StatusTransitionRequest request,
			@RequestParam Long userId) {
		 
		WorkOrder updated = workOrderService.transitionStatus(id, request.getTargetStatus(), userId, request.getNotes());
		return ResponseEntity.ok(updated);
	}
	
	// unauthenticated tracking url
	@GetMapping("/track/{token}")
	public ResponseEntity<WorkOrder> trackWorkOrderPublicly(@PathVariable String token) {
		return workOrderRepository.findByTrackingToken(token)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	// download pdf invoice
	@GetMapping("/{id}/invoice")
	@PreAuthorize("hasAnyRole('MANAGER', 'CUSTOMER', 'ADMIN')")
	public ResponseEntity<InputStreamResource> downloadInvoice(@PathVariable Long id) {
		ByteArrayInputStream pdfStream = invoicePdfService.generateInvoicePdf(id);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=invoice_" + id + ".pdf");
		
		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdfStream));
	}
}
