package com.ebenyx.oraklus.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ebenyx.oraklus.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	User findByIdIsNotAndUsername(Long id, String username);

	User findByEmail(String email);

	User findByIdIsNotAndEmail(Long id, String email);
}
