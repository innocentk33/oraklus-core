package com.ebenyx.oraklus.relation.repository;

import com.ebenyx.oraklus.relation.entity.Etablissement;
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
public class EtablissementRepositoryTest {

	@Autowired
	private EtablissementRepository etablissementRepository;

	Etablissement etablissement1;
	Etablissement etablissement2;

	@Before
	public void before(){
		etablissementRepository.deleteAll();

		etablissement1 = new Etablissement();
		assertNotNull(etablissement1);
		assertNull(etablissement1.getId());
		//assertNotNull(etablissement1.get..);

		etablissement2 = new Etablissement();
		assertNotNull(etablissement2);
		assertNull(etablissement2.getId());
		//assertNotNull(etablissement2.get..);
	}

	@Test
	public void test(){
		// Test save
		etablissement1 = etablissementRepository.save(etablissement1);
		assertNotNull(etablissement1);
		assertNotNull(etablissement1.getId());
		assertNotNull(etablissement1.getCreateDate());
		//assertEquals("", etablissement1.get...);

		etablissement2 = etablissementRepository.save(etablissement2);
		assertNotNull(etablissement2);
		assertNotNull(etablissement2.getId());
		assertNotNull(etablissement2.getCreateDate());
		//assertEquals("", etablissement2.get...);

		// Test find by id
		etablissementRepository.findById(etablissement1.getId()).ifPresent(etablissement -> {
			assertNotNull(etablissement);
			assertNotNull(etablissement.getId());
			//assertEquals("", etablissement.get...);
		});

		etablissementRepository.findById(etablissement2.getId()).ifPresent(etablissement -> {
			assertNotNull(etablissement);
			assertNotNull(etablissement.getId());
			//assertEquals("", etablissement.get...);
		});

		// Test find by id
		etablissement1 = etablissementRepository.findById(etablissement1.getId()).get();
		assertNotNull(etablissement1);
		assertNotNull(etablissement1.getId());
		//assertEquals("", etablissement1.get...);

		// Test update
		etablissementRepository.findById(etablissement2.getId()).ifPresent(etablissement -> {
			//etablissement2.set...;
			etablissement2 = etablissementRepository.save(etablissement2);

			assertNotNull(etablissement2);
			assertNotNull(etablissement2.getId());
			assertNotNull(etablissement2.getUpdateDate());
			// assertEquals("", etablissement2.get...());
		});

		// Test find all
		List <Etablissement> etablissements = etablissementRepository.findAll();
		assertNotNull(etablissements);
		assertEquals(2, etablissements.size());

		// Test delete
		etablissementRepository.delete(etablissement1);
		long total = etablissementRepository.count();
		assertEquals(1, total);
	}

	@After
	public void destroy() {
		etablissementRepository.deleteAll();
	}
}
