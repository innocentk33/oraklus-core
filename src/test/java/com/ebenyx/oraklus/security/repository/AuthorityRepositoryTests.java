package com.ebenyx.oraklus.security.repository;

import com.ebenyx.oraklus.security.entity.Authority;
import com.ebenyx.oraklus.security.entity.AuthorityName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class AuthorityRepositoryTests {

	@Autowired
	private AuthorityRepository authorityRepository;

	Authority authority1;
	Authority authority2;

	@Before
	public void setUp() {
		authorityRepository.deleteAll();

		authority1 = new Authority(null, AuthorityName.ROLE_ADMIN, null);
		assertNotNull(authority1);
		assertNull(authority1.getId());
		assertNotNull(authority1.getName());

		authority2 = new Authority(null, AuthorityName.ROLE_USER, null);
		assertNotNull(authority2);
		assertNull(authority2.getId());
		assertNotNull(authority2.getName());
	}

	@Test
	public void test(){
		// Test save
		authority1 = authorityRepository.save(authority1);
		assertNotNull(authority1);
		assertNotNull(authority1.getId());
		assertEquals(AuthorityName.ROLE_ADMIN, authority1.getName());

		authority2 = authorityRepository.save(authority2);
		assertNotNull(authority2);
		assertNotNull(authority2.getId());
		assertEquals(AuthorityName.ROLE_USER, authority2.getName());

		// Test find one
		authorityRepository.findById(authority1.getId()).ifPresent(authority -> {
			assertNotNull(authority);
			assertNotNull(authority.getId());
			assertEquals(AuthorityName.ROLE_ADMIN, authority.getName());
		});

		authorityRepository.findById(authority2.getId()).ifPresent(authority -> {
			assertNotNull(authority);
			assertNotNull(authority.getId());
			assertEquals(AuthorityName.ROLE_USER, authority.getName());
		});

		// Test update
		authorityRepository.findById(authority1.getId()).ifPresent(bank -> {
			authority1.setName(AuthorityName.ROLE_USER);
			authority1 = authorityRepository.save(authority1);

			assertNotNull(bank);
			assertNotNull(bank.getId());
			assertEquals(AuthorityName.ROLE_USER, authority1.getName());
		});

		// Test find all
		List<Authority> authorities = authorityRepository.findAll();
		assertNotNull(authorities);
		assertEquals(2, authorities.size());

		// Test delete
		authorityRepository.delete(authority1);
		long total = authorityRepository.count();
		assertEquals(1, total);
	}

	@After
	public void destroy() {
		authorityRepository.deleteAll();
	}
}
