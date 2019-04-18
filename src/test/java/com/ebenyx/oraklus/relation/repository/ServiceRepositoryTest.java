package com.ebenyx.oraklus.relation.repository;

import com.ebenyx.oraklus.relation.entity.Service;
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
public class ServiceRepositoryTest {

	@Autowired
	private ServiceRepository serviceRepository;

	Service service1;
	Service service2;

	@Before
	public void before(){
		serviceRepository.deleteAll();

		service1 = new Service();
		assertNotNull(service1);
		assertNull(service1.getId());
		//assertNotNull(service1.get..);

		service2 = new Service();
		assertNotNull(service2);
		assertNull(service2.getId());
		//assertNotNull(service2.get..);
	}

	@Test
	public void test(){
		// Test save
		service1 = serviceRepository.save(service1);
		assertNotNull(service1);
		assertNotNull(service1.getId());
		assertNotNull(service1.getCreateDate());
		//assertEquals("", service1.get...);

		service2 = serviceRepository.save(service2);
		assertNotNull(service2);
		assertNotNull(service2.getId());
		assertNotNull(service2.getCreateDate());
		//assertEquals("", service2.get...);

		// Test find by id
		serviceRepository.findById(service1.getId()).ifPresent(service -> {
			assertNotNull(service);
			assertNotNull(service.getId());
			//assertEquals("", service.get...);
		});

		serviceRepository.findById(service2.getId()).ifPresent(service -> {
			assertNotNull(service);
			assertNotNull(service.getId());
			//assertEquals("", service.get...);
		});

		// Test find by id
		service1 = serviceRepository.findById(service1.getId()).get();
		assertNotNull(service1);
		assertNotNull(service1.getId());
		//assertEquals("", service1.get...);

		// Test update
		serviceRepository.findById(service2.getId()).ifPresent(service -> {
			//service2.set...;
			service2 = serviceRepository.save(service2);

			assertNotNull(service2);
			assertNotNull(service2.getId());
			assertNotNull(service2.getUpdateDate());
			// assertEquals("", service2.get...());
		});

		// Test find all
		List <Service> services = serviceRepository.findAll();
		assertNotNull(services);
		assertEquals(2, services.size());

		// Test delete
		serviceRepository.delete(service1);
		long total = serviceRepository.count();
		assertEquals(1, total);
	}

	@After
	public void destroy() {
		serviceRepository.deleteAll();
	}
}
