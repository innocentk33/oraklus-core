package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.relation.entity.TypeLit;
import com.ebenyx.oraklus.relation.json.request.TypeLitJsonRequest;
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
public class TypeLitServiceTest {

	@Autowired
	private TypeLitService typeLitService;

	TypeLitJsonRequest typeLitJsonRequest;

	@Before
	public void setUp() {
		typeLitJsonRequest = new TypeLitJsonRequest();
	}

	@Test
	public void test(){
		// Test before save
		ErrorResponse <TypeLit> errorResponse = typeLitService.beforeSave(typeLitJsonRequest);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test save
		TypeLit typeLit = errorResponse.getEntity();
		typeLit = typeLitService.save(typeLit);
		assertNotNull(typeLit);
		assertNotNull(typeLit.getId());
		assertNotNull(typeLit.getCreateDate());
		//assertEquals("", typeLit.get...);

		// Test find one
		typeLit = typeLitService.findOne(typeLit.getId());
		assertNotNull(typeLit);
		assertNotNull(typeLit.getId());
		//assertEquals("", typeLit.get...);

		// Test page all
		Page <TypeLit> typeLits = typeLitService.load(PageRequest.of(0, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), null);
		assertNotNull(typeLits);
		assertNotNull(typeLits.getContent());
		assertEquals(1, typeLits.getTotalElements());

		// Test before delete
		errorResponse = typeLitService.beforeDelete(typeLit);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test delete
		typeLitService.delete(typeLit);
	}

	@After
	public void destroy() {

	}
}
