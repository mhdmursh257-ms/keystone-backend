package com.key_stone.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.key_stone.Entity.Site;
import com.key_stone.Repository.SiteRepository;

@RestController
@RequestMapping("/api")
public class CustomerSiteController {
	
	@Autowired
	private SiteRepository siteRepository;
		
	// create a new site
	@PostMapping("/sites")
	@PreAuthorize("hasAnyRole('MANAGER','DISPATCHER')")
	public ResponseEntity<Site> createSite(@RequestBody Site site) {
		return ResponseEntity.ok(siteRepository.save(site));
	}
		
	//get all sites for specific
	@GetMapping("/customers/{customerId}/sites")
	@PreAuthorize("hasAnyRole('MANAGER', 'DISPATCHER', 'CUSTOMER')")
	public ResponseEntity<List<Site>> getSitesByCustomer(@PathVariable Long customerId) {
		return ResponseEntity.ok(siteRepository.findByCustomerId(customerId));
	}
}
