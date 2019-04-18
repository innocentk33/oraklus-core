package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.relation.entity.Pole;
import com.ebenyx.oraklus.relation.json.request.PoleJsonRequest;
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
public class PoleServiceTest {

	@Autowired
	private PoleService poleService;

	PoleJsonRequest poleJsonRequest;

	@Before
	public void setUp() {
		poleJsonRequest = new PoleJsonRequest();
	}

	@Test
	public void test(){
		// Test before save
		ErrorResponse <Pole> errorResponse = poleService.beforeSave(poleJsonRequest);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test save
		Pole pole = errorResponse.getEntity();
		pole = poleService.save(pole);
		assertNotNull(pole);
		assertNotNull(pole.getId());
		assertNotNull(pole.getCreateDate());
		//assertEquals("", pole.get...);

		// Test find one
		pole = poleService.findOne(pole.getId());
		assertNotNull(pole);
		assertNotNull(pole.getId());
		//assertEquals("", pole.get...);

		// Test page all
		Page <Pole> poles = poleService.load(PageRequest.of(0, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), null);
		assertNotNull(poles);
		assertNotNull(poles.getContent());
		assertEquals(1, poles.getTotalElements());

		// Test before delete
		errorResponse = poleService.beforeDelete(pole);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test delete
		poleService.delete(pole);
	}

	@After
	public void destroy() {

	}
}
