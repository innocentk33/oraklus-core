package com.ebenyx.oraklus.security.repository;

import com.ebenyx.oraklus.security.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	List<Authority> findAll();
}
