package com.ebenyx.oraklus.relation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ebenyx.oraklus.relation.entity.Lit;

public interface LitRepository extends JpaRepository<Lit, Long> {

	List<Lit> findAll();

	Page<Lit> findAll(Pageable pageable);

}