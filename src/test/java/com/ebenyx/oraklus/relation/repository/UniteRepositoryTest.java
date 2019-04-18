package com.ebenyx.oraklus.relation.repository;

import com.ebenyx.oraklus.relation.entity.Unite;
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
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UniteRepositoryTest {

	@Autowired
	private UniteRepository uniteRepository;

	Unite unite1;
	Unite unite2;

	@Before
	public void before(){
		uniteRepository.deleteAll();

		unite1 = new Unite();
		assertNotNull(unite1);
		assertNull(unite1.getId());
		//assertNotNull(unite1.get..);

		unite2 = new Unite();
		assertNotNull(unite2);
		assertNull(unite2.getId());
		//assertNotNull(unite2.get..);
	}

	@Test
	public void test(){
		// Test save
		unite1 = uniteRepository.save(unite1);
		assertNotNull(unite1);
		assertNotNull(unite1.getId());
		assertNotNull(unite1.getCreateDate());
		//assertEquals("", unite1.get...);

		unite2 = uniteRepository.save(unite2);
		assertNotNull(unite2);
		assertNotNull(unite2.getId());
		assertNotNull(unite2.getCreateDate());
		//assertEquals("", unite2.get...);

		// Test find by id
		uniteRepository.findById(unite1.getId()).ifPresent(unite -> {
			assertNotNull(unite);
			assertNotNull(unite.getId());
			//assertEquals("", unite.get...);
		});

		uniteRepository.findById(unite2.getId()).ifPresent(unite -> {
			assertNotNull(unite);
			assertNotNull(unite.getId());
			//assertEquals("", unite.get...);
		});

		// Test find by id
		unite1 = uniteRepository.findById(unite1.getId()).get();
		assertNotNull(unite1);
		assertNotNull(unite1.getId());
		//assertEquals("", unite1.get...);

		// Test update
		uniteRepository.findById(unite2.getId()).ifPresent(unite -> {
			//unite2.set...;
			unite2 = uniteRepository.save(unite2);

			assertNotNull(unite2);
			assertNotNull(unite2.getId());
			assertNotNull(unite2.getUpdateDate());
			// assertEquals("", unite2.get...());
		});

		// Test find all
		List <Unite> unites = uniteRepository.findAll();
		assertNotNull(unites);
		assertEquals(2, unites.size());

		// Test delete
		uniteRepository.delete(unite1);
		long total = uniteRepository.count();
		assertEquals(1, total);
	}

	@After
	public void destroy() {
		uniteRepository.deleteAll();
	}
}
