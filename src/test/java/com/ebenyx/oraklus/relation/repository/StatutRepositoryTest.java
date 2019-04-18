package com.ebenyx.oraklus.relation.repository;

import com.ebenyx.oraklus.relation.entity.Statut;
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
public class StatutRepositoryTest {

	@Autowired
	private StatutRepository statutRepository;

	Statut statut1;
	Statut statut2;

	@Before
	public void before(){
		statutRepository.deleteAll();

		statut1 = new Statut();
		assertNotNull(statut1);
		assertNull(statut1.getId());
		//assertNotNull(statut1.get..);

		statut2 = new Statut();
		assertNotNull(statut2);
		assertNull(statut2.getId());
		//assertNotNull(statut2.get..);
	}

	@Test
	public void test(){
		// Test save
		statut1 = statutRepository.save(statut1);
		assertNotNull(statut1);
		assertNotNull(statut1.getId());
		assertNotNull(statut1.getCreateDate());
		//assertEquals("", statut1.get...);

		statut2 = statutRepository.save(statut2);
		assertNotNull(statut2);
		assertNotNull(statut2.getId());
		assertNotNull(statut2.getCreateDate());
		//assertEquals("", statut2.get...);

		// Test find by id
		statutRepository.findById(statut1.getId()).ifPresent(statut -> {
			assertNotNull(statut);
			assertNotNull(statut.getId());
			//assertEquals("", statut.get...);
		});

		statutRepository.findById(statut2.getId()).ifPresent(statut -> {
			assertNotNull(statut);
			assertNotNull(statut.getId());
			//assertEquals("", statut.get...);
		});

		// Test find by id
		statut1 = statutRepository.findById(statut1.getId()).get();
		assertNotNull(statut1);
		assertNotNull(statut1.getId());
		//assertEquals("", statut1.get...);

		// Test update
		statutRepository.findById(statut2.getId()).ifPresent(statut -> {
			//statut2.set...;
			statut2 = statutRepository.save(statut2);

			assertNotNull(statut2);
			assertNotNull(statut2.getId());
			assertNotNull(statut2.getUpdateDate());
			// assertEquals("", statut2.get...());
		});

		// Test find all
		List <Statut> statuts = statutRepository.findAll();
		assertNotNull(statuts);
		assertEquals(2, statuts.size());

		// Test delete
		statutRepository.delete(statut1);
		long total = statutRepository.count();
		assertEquals(1, total);
	}

	@After
	public void destroy() {
		statutRepository.deleteAll();
	}
}
