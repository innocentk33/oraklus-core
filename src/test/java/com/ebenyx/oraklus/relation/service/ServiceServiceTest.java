package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.relation.entity.Service;
import com.ebenyx.oraklus.relation.json.request.ServiceJsonRequest;
import com.ebenyx.oraklus.utils.Constants;
import com.ebenyx.oraklus.utils.error.ErrorResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class ServiceServiceTest {

	@Autowired
	private ServiceService serviceService;

	ServiceJsonRequest serviceJsonRequest;

	@Before
	public void setUp() {
		serviceJsonRequest = new ServiceJsonRequest();
	}

	@Test
	public void test(){
		// Test before save
		ErrorResponse <Service> errorResponse = serviceService.beforeSave(serviceJsonRequest);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test save
		Service service = errorResponse.getEntity();
		service = serviceService.save(service);
		assertNotNull(service);
		assertNotNull(service.getId());
		assertNotNull(service.getCreateDate());
		//assertEquals("", service.get...);

		// Test find one
		service = serviceService.findOne(service.getId());
		assertNotNull(service);
		assertNotNull(service.getId());
		//assertEquals("", service.get...);

		// Test page all
		Page <Service> services = serviceService.load(PageRequest.of(0, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), null);
		assertNotNull(services);
		assertNotNull(services.getContent());
		assertEquals(1, services.getTotalElements());

		// Test before delete
		errorResponse = serviceService.beforeDelete(service);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test delete
		serviceService.delete(service);
	}

	@After
	public void destroy() {

	}
}
