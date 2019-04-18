package com.ebenyx.oraklus.security.service;

import com.ebenyx.oraklus.security.entity.User;

import com.ebenyx.oraklus.utils.error.ErrorResponse;

import java.util.List;

public interface UserService {

	ErrorResponse <User> beforeSave(User user);

	User save(User user);

	ErrorResponse <User> beforeDelete(User user);

	void delete(User user);

	User findOne(Long id);

	User findByUsername(String username);

	List<User> findAll();

	List<User> search(String keyword);
}
