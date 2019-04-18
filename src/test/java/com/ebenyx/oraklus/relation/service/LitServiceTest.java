package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.relation.entity.Lit;
import com.ebenyx.oraklus.relation.json.request.LitJsonRequest;
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
public class LitServiceTest {

	@Autowired
	private LitService litService;

	LitJsonRequest litJsonRequest;

	@Before
	public void setUp() {
		litJsonRequest = new LitJsonRequest();
	}

	@Test
	public void test(){
		// Test before save
		ErrorResponse <Lit> errorResponse = litService.beforeSave(litJsonRequest);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test save
		Lit lit = errorResponse.getEntity();
		lit = litService.save(lit);
		assertNotNull(lit);
		assertNotNull(lit.getId());
		assertNotNull(lit.getCreateDate());
		//assertEquals("", lit.get...);

		// Test find one
		lit = litService.findOne(lit.getId());
		assertNotNull(lit);
		assertNotNull(lit.getId());
		//assertEquals("", lit.get...);

		// Test page all
		Page <Lit> lits = litService.load(PageRequest.of(0, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), null);
		assertNotNull(lits);
		assertNotNull(lits.getContent());
		assertEquals(1, lits.getTotalElements());

		// Test before delete
		errorResponse = litService.beforeDelete(lit);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test delete
		litService.delete(lit);
	}

	@After
	public void destroy() {

	}
}
