package com.ebenyx.oraklus.relation.repository;

import com.ebenyx.oraklus.relation.entity.Pole;
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
public class PoleRepositoryTest {

	@Autowired
	private PoleRepository poleRepository;

	Pole pole1;
	Pole pole2;

	@Before
	public void before(){
		poleRepository.deleteAll();

		pole1 = new Pole();
		assertNotNull(pole1);
		assertNull(pole1.getId());
		//assertNotNull(pole1.get..);

		pole2 = new Pole();
		assertNotNull(pole2);
		assertNull(pole2.getId());
		//assertNotNull(pole2.get..);
	}

	@Test
	public void test(){
		// Test save
		pole1 = poleRepository.save(pole1);
		assertNotNull(pole1);
		assertNotNull(pole1.getId());
		assertNotNull(pole1.getCreateDate());
		//assertEquals("", pole1.get...);

		pole2 = poleRepository.save(pole2);
		assertNotNull(pole2);
		assertNotNull(pole2.getId());
		assertNotNull(pole2.getCreateDate());
		//assertEquals("", pole2.get...);

		// Test find by id
		poleRepository.findById(pole1.getId()).ifPresent(pole -> {
			assertNotNull(pole);
			assertNotNull(pole.getId());
			//assertEquals("", pole.get...);
		});

		poleRepository.findById(pole2.getId()).ifPresent(pole -> {
			assertNotNull(pole);
			assertNotNull(pole.getId());
			//assertEquals("", pole.get...);
		});

		// Test find by id
		pole1 = poleRepository.findById(pole1.getId()).get();
		assertNotNull(pole1);
		assertNotNull(pole1.getId());
		//assertEquals("", pole1.get...);

		// Test update
		poleRepository.findById(pole2.getId()).ifPresent(pole -> {
			//pole2.set...;
			pole2 = poleRepository.save(pole2);

			assertNotNull(pole2);
			assertNotNull(pole2.getId());
			assertNotNull(pole2.getUpdateDate());
			// assertEquals("", pole2.get...());
		});

		// Test find all
		List <Pole> poles = poleRepository.findAll();
		assertNotNull(poles);
		assertEquals(2, poles.size());

		// Test delete
		poleRepository.delete(pole1);
		long total = poleRepository.count();
		assertEquals(1, total);
	}

	@After
	public void destroy() {
		poleRepository.deleteAll();
	}
}
