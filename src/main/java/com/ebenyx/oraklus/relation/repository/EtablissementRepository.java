package com.ebenyx.oraklus.relation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ebenyx.oraklus.relation.entity.Etablissement;

public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {

	List<Etablissement> findAll();

	Page<Etablissement> findAll(Pageable pageable);

}