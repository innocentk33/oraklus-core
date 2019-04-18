package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.relation.entity.Unite;
import com.ebenyx.oraklus.relation.json.request.UniteJsonRequest;
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
public class UniteServiceTest {

	@Autowired
	private UniteService uniteService;

	UniteJsonRequest uniteJsonRequest;

	@Before
	public void setUp() {
		uniteJsonRequest = new UniteJsonRequest();
	}

	@Test
	public void test(){
		// Test before save
		ErrorResponse <Unite> errorResponse = uniteService.beforeSave(uniteJsonRequest);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test save
		Unite unite = errorResponse.getEntity();
		unite = uniteService.save(unite);
		assertNotNull(unite);
		assertNotNull(unite.getId());
		assertNotNull(unite.getCreateDate());
		//assertEquals("", unite.get...);

		// Test find one
		unite = uniteService.findOne(unite.getId());
		assertNotNull(unite);
		assertNotNull(unite.getId());
		//assertEquals("", unite.get...);

		// Test page all
		Page <Unite> unites = uniteService.load(PageRequest.of(0, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), null);
		assertNotNull(unites);
		assertNotNull(unites.getContent());
		assertEquals(1, unites.getTotalElements());

		// Test before delete
		errorResponse = uniteService.beforeDelete(unite);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test delete
		uniteService.delete(unite);
	}

	@After
	public void destroy() {

	}
}
