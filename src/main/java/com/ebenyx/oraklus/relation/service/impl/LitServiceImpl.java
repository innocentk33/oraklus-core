package com.ebenyx.oraklus.relation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ebenyx.oraklus.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebenyx.oraklus.relation.entity.Lit;
import com.ebenyx.oraklus.relation.json.request.LitJsonRequest;
import com.ebenyx.oraklus.relation.repository.LitRepository;
import com.ebenyx.oraklus.relation.service.LitService;

import com.ebenyx.oraklus.utils.error.ErrorMessage;

import com.ebenyx.oraklus.utils.error.ErrorPattern;

import com.ebenyx.oraklus.utils.error.ErrorResponse;

/**
 * The LitService class ...
 * @see LitService
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@Transactional
@Service("litService")
public class LitServiceImpl implements LitService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final LitRepository litRepository;

	@Autowired
	public LitServiceImpl(LitRepository litRepository) {
		this.litRepository = litRepository;
	}

	ErrorPattern<Lit> ep;

	/**
	 * <p>This method check and validate lit properties before saving lit</p>
	 * @param litJsonRequest contains lit parameters to check and validate
	 * @see ErrorResponse
	 * @see Lit
	 * @see LitJsonRequest
	 * @return the checking and validation response
	 * @since 1.0
	 */

	@Override
	public ErrorResponse <Lit> beforeSave(LitJsonRequest litJsonRequest){
		List <ErrorMessage> errors = new ArrayList <>();
		ep = new ErrorPattern <>();

		Lit lit = new Lit();

		if (litJsonRequest == null){
			logger.warn("Lit save error : lit null");
			errors = ep.getError(0, "lit can not be null", "object lit can not be null", errors);
		} else {

		}
		return ep.errorResultBuilder(errors, lit);
	}

	/**
	 * <p>This method persist lit properties in database</p>
	 * @param lit contains lit parameters to check and validate
	 * @return the lit persist in database
	 * @see Lit
	 * @since 1.0
	 */

	@Override
	public Lit save(Lit lit) {
		if (lit == null){
			logger.warn("Lit save failed");
			throw new ValidationException("lit can not be null");
		}

		lit = litRepository.save(lit);
		logger.info("Lit save successfully", lit);
		return lit;
	}

	/**
	 * <p>This method check lit before deleting</p>
	 * @param lit to check
	 * @see Lit
	 * @return the checking response
	 * @see ErrorResponse
	 * @since 1.0
	 */

	@Override
	public ErrorResponse<Lit> beforeDelete(Lit lit){
		List<ErrorMessage> errors = new ArrayList<>();
		ep = new ErrorPattern<>();
		if(lit == null){
			errors = ep.getError(0, "Lit not exist", "Lit not exist", errors);
		}
		return ep.errorResultBuilder(errors, lit);
	}

	/**
	 * <p>This method delete lit in database</p>
	 * @param lit to delete
	 * @see Lit
	 * @since 1.0
	 */

	@Override
	public void delete(Lit lit) {
		if (lit == null){
			logger.warn("Lit delete failed");
			throw new ValidationException("lit can not be null");
		}

		litRepository.delete(lit);
		logger.info("Lit delete successfully", lit);
	}

	/**
	 * <p>This method find lit by id in database</p>
	 * @param id the lit id in database
	 * @return the lit found in database
	 * @see Lit
	 * @since 1.0
	 */

	@Override
	public Lit findOne(Long id) {
		if (id == null){
			logger.warn("Lit find by id failed");
			throw new ValidationException("id can not be null");
		}

		Optional<Lit> lit = litRepository.findById(id);
		if(lit.isPresent()){
			logger.info("Lit find by id successfully", lit);
			return lit.get();
		} else {
			logger.info("Lit find by id not exist", lit);
			return null;
		}
	}

	/**
	 * <p>This method page lits in database</p>
	 * @return lits page found in database
	 * @see Lit
	 * @since 1.0
	 */

	@Override
		public Page <Lit> load(Pageable pageable, String keyword) {
		Page<Lit> pages;
		if(keyword == null || keyword.equals(""))
			pages = litRepository.findAll(pageable);
		else
			pages = null;
		logger.info("All lits found successfully", pages.getTotalPages());
		return pages;
	}
}
