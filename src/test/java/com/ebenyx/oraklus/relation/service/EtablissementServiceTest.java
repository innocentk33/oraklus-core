package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.relation.entity.Etablissement;
import com.ebenyx.oraklus.relation.json.request.EtablissementJsonRequest;
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
public class EtablissementServiceTest {

	@Autowired
	private EtablissementService etablissementService;

	EtablissementJsonRequest etablissementJsonRequest;

	@Before
	public void setUp() {
		etablissementJsonRequest = new EtablissementJsonRequest();
	}

	@Test
	public void test(){
		// Test before save
		ErrorResponse <Etablissement> errorResponse = etablissementService.beforeSave(etablissementJsonRequest);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test save
		Etablissement etablissement = errorResponse.getEntity();
		etablissement = etablissementService.save(etablissement);
		assertNotNull(etablissement);
		assertNotNull(etablissement.getId());
		assertNotNull(etablissement.getCreateDate());
		//assertEquals("", etablissement.get...);

		// Test find one
		etablissement = etablissementService.findOne(etablissement.getId());
		assertNotNull(etablissement);
		assertNotNull(etablissement.getId());
		//assertEquals("", etablissement.get...);

		// Test page all
		Page <Etablissement> etablissements = etablissementService.load(PageRequest.of(0, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_SORT_DIRECTION, "createDate"), null);
		assertNotNull(etablissements);
		assertNotNull(etablissements.getContent());
		assertEquals(1, etablissements.getTotalElements());

		// Test before delete
		errorResponse = etablissementService.beforeDelete(etablissement);
		assertNotNull(errorResponse);
		assertEquals(errorResponse.getError(), false);

		// Test delete
		etablissementService.delete(etablissement);
	}

	@After
	public void destroy() {

	}
}
