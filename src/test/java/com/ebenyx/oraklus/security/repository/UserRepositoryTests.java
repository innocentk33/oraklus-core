package com.ebenyx.oraklus.security.repository;

import com.ebenyx.oraklus.security.entity.Authority;
import com.ebenyx.oraklus.security.entity.AuthorityName;
import com.ebenyx.oraklus.security.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	User user1;
	User user2;

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Before
	public void setUp() {
		userRepository.deleteAll();
		authorityRepository.deleteAll();

		Authority authority = new Authority(null, AuthorityName.ROLE_ADMIN, null);
		authority = authorityRepository.save(authority);
		List<Authority> authorities = new ArrayList <>();
		authorities.add(authority);

		user1 = new User(null, "boris", passwordEncoder.encode("*****"), null, null, "Brice-Boris BEDA", "brice.beda@gmail.com", true, LocalDateTime.now(), null, null, null, null, null, null, authorities);
		assertNotNull(user1);
		assertNull(user1.getId());
		assertNotNull(user1.getUsername());

		user2 = new User(null, "elodie", passwordEncoder.encode("*****"), null, null, "Elodie Mireille BOUSSOU", "elodie.boussou@gmail.com", true, LocalDateTime.now(), null, null, null, null, null, null, authorities);
		assertNotNull(user2);
		assertNull(user2.getId());
		assertNotNull(user2.getUsername());
	}

	@Test
	public void test(){
		// Test save
		user1 = userRepository.save(user1);
		assertNotNull(user1);
		assertNotNull(user1.getId());
		assertNotNull(user1.getLastPasswordResetDate());
		assertEquals("boris", user1.getUsername());
		assertEquals("Brice-Boris BEDA", user1.getFullName());
		assertEquals("brice.beda@gmail.com", user1.getEmail());
		assertEquals(true, user1.getEnabled());

		user2 = userRepository.save(user2);
		assertNotNull(user2);
		assertNotNull(user2.getId());
		assertNotNull(user2.getLastPasswordResetDate());
		assertEquals("elodie", user2.getUsername());
		assertEquals("Elodie Mireille BOUSSOU", user2.getFullName());
		assertEquals("elodie.boussou@gmail.com", user2.getEmail());
		assertEquals(true, user2.getEnabled());

		// Test find by id
		userRepository.findById(user1.getId()).ifPresent(user -> {
			assertNotNull(user);
			assertNotNull(user.getId());
			assertEquals("boris", user.getUsername());
		});

		userRepository.findById(user2.getId()).ifPresent(user -> {
			assertNotNull(user);
			assertNotNull(user.getId());
			assertEquals("elodie", user.getUsername());
		});

		// Test find by id
		user1 = userRepository.findById(user1.getId()).get();
		assertNotNull(user1);
		assertNotNull(user1.getId());
		// Test find by username
		user2 = userRepository.findByUsername("elodie");
		assertNotNull(user2);
		assertNotNull(user2.getId());

		// Test find by id is not and username
		user2 = userRepository.findByIdIsNotAndUsername(user1.getId(), "elodie");
		assertNotNull(user2);
		assertNotNull(user2.getId());

		// Test find by email
		user2 = userRepository.findByEmail("elodie.boussou@gmail.com");
		assertNotNull(user2);
		assertNotNull(user2.getId());

		// Test find by id is not and email
		user2 = userRepository.findByIdIsNotAndEmail(user1.getId(), "elodie.boussou@gmail.com");
		assertNotNull(user2);
		assertNotNull(user2.getId());

		// Test update
		userRepository.findById(user1.getId()).ifPresent(user -> {
			user1.setPassword(passwordEncoder.encode("*****"));
			user1.setUsername("lorie");
			user1.setFullName("Lorie Marie-Archange BEDA");
			user1.setEmail("lorie.beda@gmail.com");
			user1.setEnabled(false);
			user1.setLastPasswordResetDate(LocalDateTime.now());
			user1 = userRepository.save(user1);

			assertNotNull(user);
			assertNotNull(user.getId());
			assertEquals("lorie", user1.getUsername());
			assertEquals("Lorie Marie-Archange BEDA", user1.getFullName());
			assertEquals("lorie.beda@gmail.com", user1.getEmail());
			assertEquals(false, user1.getEnabled());
		});

		// Test find all
		List<User> users = userRepository.findAll();
		assertNotNull(users);
		assertEquals(2, users.size());

		// Test delete
		userRepository.delete(user1);
		long total = userRepository.count();
		assertEquals(1, total);
	}

	@After
	public void destroy() {
		userRepository.deleteAll();
		authorityRepository.deleteAll();
	}
 }
