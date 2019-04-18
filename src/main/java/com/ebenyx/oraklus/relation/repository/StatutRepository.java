package com.ebenyx.oraklus.relation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ebenyx.oraklus.relation.entity.Statut;

public interface StatutRepository extends JpaRepository<Statut, Long> {

	List<Statut> findAll();

	Page<Statut> findAll(Pageable pageable);

}