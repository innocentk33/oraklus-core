package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.relation.entity.Statut;
import com.ebenyx.oraklus.relation.json.request.StatutJsonRequest;
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
public class StatutServiceTest {

	@Autowired
	private StatutService statutService;

	StatutJsonRequest statutJsonRequest;

	@Before
	public void setUp() {
		statutJsonRequest = new StatutJsonRequest();
	}

	@Test
	public void test(){
		// Test before save
		ErrorResponse <Statut> errorResponse = statutService.beforeSave(statutJsonRequest);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test save
		Statut statut = errorResponse.getEntity();
		statut = statutService.save(statut);
		assertNotNull(statut);
		assertNotNull(statut.getId());
		assertNotNull(statut.getCreateDate());
		//assertEquals("", statut.get...);

		// Test find one
		statut = statutService.findOne(statut.getId());
		assertNotNull(statut);
		assertNotNull(statut.getId());
		//assertEquals("", statut.get...);

		// Test page all
		Page <Statut> statuts = statutService.load(PageRequest.of(0, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), null);
		assertNotNull(statuts);
		assertNotNull(statuts.getContent());
		assertEquals(1, statuts.getTotalElements());

		// Test before delete
		errorResponse = statutService.beforeDelete(statut);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test delete
		statutService.delete(statut);
	}

	@After
	public void destroy() {

	}
}
