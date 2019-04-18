package com.ebenyx.oraklus.security.service;

import com.ebenyx.oraklus.security.entity.Authority;
import com.ebenyx.oraklus.security.entity.AuthorityName;
import com.ebenyx.oraklus.security.entity.User;
import com.ebenyx.oraklus.security.repository.AuthorityRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UserServiceTests {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityRepository authorityRepository;

	User user;

	@Before
	public void setUp() {
		Authority authority = new Authority(null, AuthorityName.ROLE_ADMIN, null);
		authority = authorityRepository.save(authority);
		List<Authority> authorities = new ArrayList <>();
		authorities.add(authority);

		user = new User();
		user.setUsername("bedab");
		user.setPassword("*****");
		user.setFullName("Brice-Boris BEDA");
		user.setEmail("brice.beda@gmail.com");
		user.setEnabled(true);
		user.setLastPasswordResetDate(LocalDateTime.now());
		user.setAuthorities(authorities);
	}

	@Test
	public void test(){
		// Test save
		user = userService.save(user);
		assertNotNull(user);
		assertNotNull(user.getId());
		assertNotNull(user.getLastPasswordResetDate());
		assertEquals("bedab", user.getUsername());
		assertEquals("Brice-Boris BEDA", user.getFullName());
		assertEquals("brice.beda@gmail.com", user.getEmail());
		assertEquals(true, user.getEnabled());

		// Test find one
		user = userService.findOne(user.getId());
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals("bedab", user.getUsername());

		// Test find all
		List<User> users = userService.findAll();
		assertNotNull(users);
		assertEquals(1, users.size());

		// Test delete
		userService.delete(user);

		// Test find by username
		user = userService.findByUsername(user.getUsername());
		assertNull(user);
	}

	@After
	public void tearDown() {
		authorityRepository.deleteAll();
	}
}
