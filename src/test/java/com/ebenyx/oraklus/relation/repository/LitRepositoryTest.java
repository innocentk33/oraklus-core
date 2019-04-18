package com.ebenyx.oraklus.relation.repository;

import com.ebenyx.oraklus.relation.entity.Lit;
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
public class LitRepositoryTest {

	@Autowired
	private LitRepository litRepository;

	Lit lit1;
	Lit lit2;

	@Before
	public void before(){
		litRepository.deleteAll();

		lit1 = new Lit();
		assertNotNull(lit1);
		assertNull(lit1.getId());
		//assertNotNull(lit1.get..);

		lit2 = new Lit();
		assertNotNull(lit2);
		assertNull(lit2.getId());
		//assertNotNull(lit2.get..);
	}

	@Test
	public void test(){
		// Test save
		lit1 = litRepository.save(lit1);
		assertNotNull(lit1);
		assertNotNull(lit1.getId());
		assertNotNull(lit1.getCreateDate());
		//assertEquals("", lit1.get...);

		lit2 = litRepository.save(lit2);
		assertNotNull(lit2);
		assertNotNull(lit2.getId());
		assertNotNull(lit2.getCreateDate());
		//assertEquals("", lit2.get...);

		// Test find by id
		litRepository.findById(lit1.getId()).ifPresent(lit -> {
			assertNotNull(lit);
			assertNotNull(lit.getId());
			//assertEquals("", lit.get...);
		});

		litRepository.findById(lit2.getId()).ifPresent(lit -> {
			assertNotNull(lit);
			assertNotNull(lit.getId());
			//assertEquals("", lit.get...);
		});

		// Test find by id
		lit1 = litRepository.findById(lit1.getId()).get();
		assertNotNull(lit1);
		assertNotNull(lit1.getId());
		//assertEquals("", lit1.get...);

		// Test update
		litRepository.findById(lit2.getId()).ifPresent(lit -> {
			//lit2.set...;
			lit2 = litRepository.save(lit2);

			assertNotNull(lit2);
			assertNotNull(lit2.getId());
			assertNotNull(lit2.getUpdateDate());
			// assertEquals("", lit2.get...());
		});

		// Test find all
		List <Lit> lits = litRepository.findAll();
		assertNotNull(lits);
		assertEquals(2, lits.size());

		// Test delete
		litRepository.delete(lit1);
		long total = litRepository.count();
		assertEquals(1, total);
	}

	@After
	public void destroy() {
		litRepository.deleteAll();
	}
}
