package com.ebenyx.oraklus.relation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ebenyx.oraklus.relation.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {

	List<Service> findAll();

	Page<Service> findAll(Pageable pageable);

}