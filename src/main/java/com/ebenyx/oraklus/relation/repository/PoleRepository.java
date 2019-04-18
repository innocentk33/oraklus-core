package com.ebenyx.oraklus.relation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ebenyx.oraklus.relation.entity.Pole;

public interface PoleRepository extends JpaRepository<Pole, Long> {

	List<Pole> findAll();

	Page<Pole> findAll(Pageable pageable);

}