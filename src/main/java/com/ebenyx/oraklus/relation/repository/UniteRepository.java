package com.ebenyx.oraklus.relation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ebenyx.oraklus.relation.entity.Unite;

public interface UniteRepository extends JpaRepository<Unite, Long> {

	List<Unite> findAll();

	Page<Unite> findAll(Pageable pageable);

}