package com.key_stone.Service;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.key_stone.Entity.UserAuth;
import com.key_stone.Entity.WorkOrder;
import com.key_stone.Entity.WorkOrderStatusHistory;
import com.key_stone.Enum.WorkOrderStatus;
import com.key_stone.Repository.UserAuthRepository;
import com.key_stone.Repository.WorkOrderRepository;
import com.key_stone.Repository.WorkOrderStatusHistoryRepository;

@Service
public class WorkOrderService {

	@Autowired
	private WorkOrderRepository workOrderRepository;
	
	@Autowired
	private WorkOrderStatusHistoryRepository historyRepository;
	
	@Autowired
	private UserAuthRepository userAuthRepository;
	
	private static final Map<WorkOrderStatus, EnumSet<WorkOrderStatus>> ALLOWED_TRANSITIONS = new HashMap<>();
	
	static {
		ALLOWED_TRANSITIONS.put(WorkOrderStatus.NEW, EnumSet.of(WorkOrderStatus.ASSIGNED, WorkOrderStatus.CANCELLED));
		ALLOWED_TRANSITIONS.put(WorkOrderStatus.ASSIGNED, EnumSet.of(WorkOrderStatus.IN_PROGRESS, WorkOrderStatus.CANCELLED));
		ALLOWED_TRANSITIONS.put(WorkOrderStatus.IN_PROGRESS, EnumSet.of(WorkOrderStatus.ON_HOLD, WorkOrderStatus.COMPLETED));
		ALLOWED_TRANSITIONS.put(WorkOrderStatus.ON_HOLD, EnumSet.of(WorkOrderStatus.IN_PROGRESS));
		ALLOWED_TRANSITIONS.put(WorkOrderStatus.COMPLETED, EnumSet.of(WorkOrderStatus.CLOSED));
		ALLOWED_TRANSITIONS.put(WorkOrderStatus.CLOSED, EnumSet.noneOf(WorkOrderStatus.class));
		ALLOWED_TRANSITIONS.put(WorkOrderStatus.CANCELLED, EnumSet.noneOf(WorkOrderStatus.class));
	}
	
	@Transactional
	public WorkOrder createWorkOrder(WorkOrder workOrder) {
		if (workOrder.getAssignedTo() != null && workOrder.getAssignedTo().getId() != null && workOrder.getAssignedTo().getId() > 0) {
			UserAuth existingUser = userAuthRepository.findById(workOrder.getAssignedTo().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned user not found"));
			workOrder.setAssignedTo(existingUser);
		} else {
			workOrder.setAssignedTo(null);
		}

		if (workOrder.getTrackingToken() == null || workOrder.getTrackingToken().trim().isEmpty()) {
			workOrder.setTrackingToken(UUID.randomUUID().toString());
		}
		
		WorkOrder savedOrder = workOrderRepository.save(workOrder);

		// Initial audit log for NEW state
		WorkOrderStatusHistory history = new WorkOrderStatusHistory();
		history.setWorkOrderId(savedOrder.getId());
		history.setFromStatus(null);
		history.setToStatus(savedOrder.getStatus() != null ? savedOrder.getStatus() : WorkOrderStatus.NEW);
		history.setChangedByUserId(1L);
		history.setChangedAt(LocalDateTime.now());
		history.setNotes("Work order created");
		historyRepository.save(history);

		return savedOrder;
	}
	
	@Transactional
	public WorkOrder transitionStatus(Long workOrderId, WorkOrderStatus targetStatus, Long updatedByUserId, String notes) {
		WorkOrder workOrder = workOrderRepository.findById(workOrderId)
			 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Order not found"));

        WorkOrderStatus currentStatus = workOrder.getStatus();

        // validate state transition
        EnumSet<WorkOrderStatus> validNextStates = ALLOWED_TRANSITIONS.get(currentStatus);
        if (validNextStates == null || !validNextStates.contains(targetStatus)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, 
                    "Illegal state transition from " + currentStatus + " to " + targetStatus);
        }

        // update status
        workOrder.setStatus(targetStatus);
        
        if (workOrder.getTrackingToken() == null) {
            workOrder.setTrackingToken(UUID.randomUUID().toString());
        }

        WorkOrder updatedWorkOrder = workOrderRepository.save(workOrder);

        // append to audit log
        WorkOrderStatusHistory history = new WorkOrderStatusHistory();
        history.setWorkOrderId(workOrder.getId());
        history.setFromStatus(currentStatus);
        history.setToStatus(targetStatus);
        history.setChangedByUserId(updatedByUserId);
        history.setChangedAt(LocalDateTime.now());
        history.setNotes(notes);
        
        historyRepository.save(history);

        return updatedWorkOrder;
    }

    @Transactional
    public WorkOrder assignTechnician(Long workOrderId, Long techId, Long dispatcherId) {
        WorkOrder workOrder = workOrderRepository.findById(workOrderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Work Order not found"));

        UserAuth tech = userAuthRepository.findById(techId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Technician not found"));

        workOrder.setAssignedTo(tech);
        
        if (workOrder.getStatus() == WorkOrderStatus.NEW) {
            return transitionStatus(workOrderId, WorkOrderStatus.ASSIGNED, dispatcherId, "Assigned to tech: " + tech.getUserName());
        } else {
            // Log assignment update event if status is not NEW
            WorkOrderStatusHistory history = new WorkOrderStatusHistory();
            history.setWorkOrderId(workOrder.getId());
            history.setFromStatus(workOrder.getStatus());
            history.setToStatus(workOrder.getStatus());
            history.setChangedByUserId(dispatcherId);
            history.setChangedAt(LocalDateTime.now());
            history.setNotes("Re-assigned to: " + tech.getUserName());
            historyRepository.save(history);
        }

        return workOrderRepository.save(workOrder);
	}
    
}