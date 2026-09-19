package com.key_stone.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.key_stone.Entity.Part;
import com.key_stone.Entity.PartUsage;
import com.key_stone.Exception.InsufficientStockException;
import com.key_stone.Repository.PartRepository;
import com.key_stone.Repository.PartUsageRepository;

@Service
public class InventoryService {
	
	@Autowired
	private PartRepository partRepository;
	
	@Autowired
	private PartUsageRepository partUsageRepository;
	
	// Add or update part data
	public Part savePart(Part part) {
		return partRepository.save(part);
	}
	
	public List<Part> getAllParts() {
		return partRepository.findAll();
	}
	
	// part usage deduction
	@Transactional
	public PartUsage usePartInWorkOrder(Long workOrderId, Long partId, Integer quantity) {
		Part part = partRepository.findById(partId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Part not found"));
		
		// Stock sufficiently check
		if (part.getStockQuantity() < quantity) {
			throw new InsufficientStockException("Insufficient stock for part: " + part.getName()
					+ ". Available: " + part.getStockQuantity() + ", Requested: " + quantity);
		}
		
		// deduct stock
		part.setStockQuantity(part.getStockQuantity() - quantity);
		partRepository.save(part);
		
		// record usage
		PartUsage usage = new PartUsage();
		usage.setWorkOrderId(workOrderId);
		usage.setPart(part);
		usage.setQuantityUsed(quantity);
		usage.setTotalCost(part.getUnitPrice() * quantity);
		
		return partUsageRepository.save(usage);
	}
	
	public List<PartUsage> getPartsUsedByWorkOrder(Long workOrderId) {
		return partUsageRepository.findByWorkOrderId(workOrderId);
	}
}
