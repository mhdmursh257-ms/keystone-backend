package com.key_stone.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.key_stone.Entity.Site;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
	List<Site> findByCustomerId(long customerId);
}
