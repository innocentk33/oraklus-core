package com.ebenyx.oraklus.relation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ebenyx.oraklus.relation.entity.TypeLit;

public interface TypeLitRepository extends JpaRepository<TypeLit, Long> {

	List<TypeLit> findAll();

	Page<TypeLit> findAll(Pageable pageable);

}