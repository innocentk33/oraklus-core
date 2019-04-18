package com.ebenyx.oraklus.relation.repository;

import com.ebenyx.oraklus.relation.entity.TypeLit;
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
public class TypeLitRepositoryTest {

	@Autowired
	private TypeLitRepository typeLitRepository;

	TypeLit typeLit1;
	TypeLit typeLit2;

	@Before
	public void before(){
		typeLitRepository.deleteAll();

		typeLit1 = new TypeLit();
		assertNotNull(typeLit1);
		assertNull(typeLit1.getId());
		//assertNotNull(typeLit1.get..);

		typeLit2 = new TypeLit();
		assertNotNull(typeLit2);
		assertNull(typeLit2.getId());
		//assertNotNull(typeLit2.get..);
	}

	@Test
	public void test(){
		// Test save
		typeLit1 = typeLitRepository.save(typeLit1);
		assertNotNull(typeLit1);
		assertNotNull(typeLit1.getId());
		assertNotNull(typeLit1.getCreateDate());
		//assertEquals("", typeLit1.get...);

		typeLit2 = typeLitRepository.save(typeLit2);
		assertNotNull(typeLit2);
		assertNotNull(typeLit2.getId());
		assertNotNull(typeLit2.getCreateDate());
		//assertEquals("", typeLit2.get...);

		// Test find by id
		typeLitRepository.findById(typeLit1.getId()).ifPresent(typeLit -> {
			assertNotNull(typeLit);
			assertNotNull(typeLit.getId());
			//assertEquals("", typeLit.get...);
		});

		typeLitRepository.findById(typeLit2.getId()).ifPresent(typeLit -> {
			assertNotNull(typeLit);
			assertNotNull(typeLit.getId());
			//assertEquals("", typeLit.get...);
		});

		// Test find by id
		typeLit1 = typeLitRepository.findById(typeLit1.getId()).get();
		assertNotNull(typeLit1);
		assertNotNull(typeLit1.getId());
		//assertEquals("", typeLit1.get...);

		// Test update
		typeLitRepository.findById(typeLit2.getId()).ifPresent(typeLit -> {
			//typeLit2.set...;
			typeLit2 = typeLitRepository.save(typeLit2);

			assertNotNull(typeLit2);
			assertNotNull(typeLit2.getId());
			assertNotNull(typeLit2.getUpdateDate());
			// assertEquals("", typeLit2.get...());
		});

		// Test find all
		List <TypeLit> typeLits = typeLitRepository.findAll();
		assertNotNull(typeLits);
		assertEquals(2, typeLits.size());

		// Test delete
		typeLitRepository.delete(typeLit1);
		long total = typeLitRepository.count();
		assertEquals(1, total);
	}

	@After
	public void destroy() {
		typeLitRepository.deleteAll();
	}
}
