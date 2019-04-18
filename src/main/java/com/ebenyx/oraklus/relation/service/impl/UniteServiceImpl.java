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

import com.ebenyx.oraklus.relation.entity.Unite;
import com.ebenyx.oraklus.relation.json.request.UniteJsonRequest;
import com.ebenyx.oraklus.relation.repository.UniteRepository;
import com.ebenyx.oraklus.relation.service.UniteService;

import com.ebenyx.oraklus.utils.error.ErrorMessage;

import com.ebenyx.oraklus.utils.error.ErrorPattern;

import com.ebenyx.oraklus.utils.error.ErrorResponse;

/**
 * The UniteService class ...
 * @see UniteService
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@Transactional
@Service("uniteService")
public class UniteServiceImpl implements UniteService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final UniteRepository uniteRepository;

	@Autowired
	public UniteServiceImpl(UniteRepository uniteRepository) {
		this.uniteRepository = uniteRepository;
	}

	ErrorPattern<Unite> ep;

	/**
	 * <p>This method check and validate unite properties before saving unite</p>
	 * @param uniteJsonRequest contains unite parameters to check and validate
	 * @see ErrorResponse
	 * @see Unite
	 * @see UniteJsonRequest
	 * @return the checking and validation response
	 * @since 1.0
	 */

	@Override
	public ErrorResponse <Unite> beforeSave(UniteJsonRequest uniteJsonRequest){
		List <ErrorMessage> errors = new ArrayList <>();
		ep = new ErrorPattern <>();

		Unite unite = new Unite();

		if (uniteJsonRequest == null){
			logger.warn("Unite save error : unite null");
			errors = ep.getError(0, "unite can not be null", "object unite can not be null", errors);
		} else {

		}
		return ep.errorResultBuilder(errors, unite);
	}

	/**
	 * <p>This method persist unite properties in database</p>
	 * @param unite contains unite parameters to check and validate
	 * @return the unite persist in database
	 * @see Unite
	 * @since 1.0
	 */

	@Override
	public Unite save(Unite unite) {
		if (unite == null){
			logger.warn("Unite save failed");
			throw new ValidationException("unite can not be null");
		}

		unite = uniteRepository.save(unite);
		logger.info("Unite save successfully", unite);
		return unite;
	}

	/**
	 * <p>This method check unite before deleting</p>
	 * @param unite to check
	 * @see Unite
	 * @return the checking response
	 * @see ErrorResponse
	 * @since 1.0
	 */

	@Override
	public ErrorResponse<Unite> beforeDelete(Unite unite){
		List<ErrorMessage> errors = new ArrayList<>();
		ep = new ErrorPattern<>();
		if(unite == null){
			errors = ep.getError(0, "Unite not exist", "Unite not exist", errors);
		}
		return ep.errorResultBuilder(errors, unite);
	}

	/**
	 * <p>This method delete unite in database</p>
	 * @param unite to delete
	 * @see Unite
	 * @since 1.0
	 */

	@Override
	public void delete(Unite unite) {
		if (unite == null){
			logger.warn("Unite delete failed");
			throw new ValidationException("unite can not be null");
		}

		uniteRepository.delete(unite);
		logger.info("Unite delete successfully", unite);
	}

	/**
	 * <p>This method find unite by id in database</p>
	 * @param id the unite id in database
	 * @return the unite found in database
	 * @see Unite
	 * @since 1.0
	 */

	@Override
	public Unite findOne(Long id) {
		if (id == null){
			logger.warn("Unite find by id failed");
			throw new ValidationException("id can not be null");
		}

		Optional<Unite> unite = uniteRepository.findById(id);
		if(unite.isPresent()){
			logger.info("Unite find by id successfully", unite);
			return unite.get();
		} else {
			logger.info("Unite find by id not exist", unite);
			return null;
		}
	}

	/**
	 * <p>This method page unites in database</p>
	 * @return unites page found in database
	 * @see Unite
	 * @since 1.0
	 */

	@Override
		public Page <Unite> load(Pageable pageable, String keyword) {
		Page<Unite> pages;
		if(keyword == null || keyword.equals(""))
			pages = uniteRepository.findAll(pageable);
		else
			pages = null;
		logger.info("All unites found successfully", pages.getTotalPages());
		return pages;
	}
}
