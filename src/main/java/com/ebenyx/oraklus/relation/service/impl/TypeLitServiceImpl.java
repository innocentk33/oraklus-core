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

import com.ebenyx.oraklus.relation.entity.TypeLit;
import com.ebenyx.oraklus.relation.json.request.TypeLitJsonRequest;
import com.ebenyx.oraklus.relation.repository.TypeLitRepository;
import com.ebenyx.oraklus.relation.service.TypeLitService;

import com.ebenyx.oraklus.utils.error.ErrorMessage;

import com.ebenyx.oraklus.utils.error.ErrorPattern;

import com.ebenyx.oraklus.utils.error.ErrorResponse;

/**
 * The TypeLitService class ...
 * @see TypeLitService
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@Transactional
@Service("typeLitService")
public class TypeLitServiceImpl implements TypeLitService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final TypeLitRepository typeLitRepository;

	@Autowired
	public TypeLitServiceImpl(TypeLitRepository typeLitRepository) {
		this.typeLitRepository = typeLitRepository;
	}

	ErrorPattern<TypeLit> ep;

	/**
	 * <p>This method check and validate typeLit properties before saving typeLit</p>
	 * @param typeLitJsonRequest contains typeLit parameters to check and validate
	 * @see ErrorResponse
	 * @see TypeLit
	 * @see TypeLitJsonRequest
	 * @return the checking and validation response
	 * @since 1.0
	 */

	@Override
	public ErrorResponse <TypeLit> beforeSave(TypeLitJsonRequest typeLitJsonRequest){
		List <ErrorMessage> errors = new ArrayList <>();
		ep = new ErrorPattern <>();

		TypeLit typeLit = new TypeLit();

		if (typeLitJsonRequest == null){
			logger.warn("TypeLit save error : typeLit null");
			errors = ep.getError(0, "typeLit can not be null", "object typeLit can not be null", errors);
		} else {

		}
		return ep.errorResultBuilder(errors, typeLit);
	}

	/**
	 * <p>This method persist typeLit properties in database</p>
	 * @param typeLit contains typeLit parameters to check and validate
	 * @return the typeLit persist in database
	 * @see TypeLit
	 * @since 1.0
	 */

	@Override
	public TypeLit save(TypeLit typeLit) {
		if (typeLit == null){
			logger.warn("TypeLit save failed");
			throw new ValidationException("typeLit can not be null");
		}

		typeLit = typeLitRepository.save(typeLit);
		logger.info("TypeLit save successfully", typeLit);
		return typeLit;
	}

	/**
	 * <p>This method check typeLit before deleting</p>
	 * @param typeLit to check
	 * @see TypeLit
	 * @return the checking response
	 * @see ErrorResponse
	 * @since 1.0
	 */

	@Override
	public ErrorResponse<TypeLit> beforeDelete(TypeLit typeLit){
		List<ErrorMessage> errors = new ArrayList<>();
		ep = new ErrorPattern<>();
		if(typeLit == null){
			errors = ep.getError(0, "TypeLit not exist", "TypeLit not exist", errors);
		}
		return ep.errorResultBuilder(errors, typeLit);
	}

	/**
	 * <p>This method delete typeLit in database</p>
	 * @param typeLit to delete
	 * @see TypeLit
	 * @since 1.0
	 */

	@Override
	public void delete(TypeLit typeLit) {
		if (typeLit == null){
			logger.warn("TypeLit delete failed");
			throw new ValidationException("typeLit can not be null");
		}

		typeLitRepository.delete(typeLit);
		logger.info("TypeLit delete successfully", typeLit);
	}

	/**
	 * <p>This method find typeLit by id in database</p>
	 * @param id the typeLit id in database
	 * @return the typeLit found in database
	 * @see TypeLit
	 * @since 1.0
	 */

	@Override
	public TypeLit findOne(Long id) {
		if (id == null){
			logger.warn("TypeLit find by id failed");
			throw new ValidationException("id can not be null");
		}

		Optional<TypeLit> typeLit = typeLitRepository.findById(id);
		if(typeLit.isPresent()){
			logger.info("TypeLit find by id successfully", typeLit);
			return typeLit.get();
		} else {
			logger.info("TypeLit find by id not exist", typeLit);
			return null;
		}
	}

	/**
	 * <p>This method page typeLits in database</p>
	 * @return typeLits page found in database
	 * @see TypeLit
	 * @since 1.0
	 */

	@Override
		public Page <TypeLit> load(Pageable pageable, String keyword) {
		Page<TypeLit> pages;
		if(keyword == null || keyword.equals(""))
			pages = typeLitRepository.findAll(pageable);
		else
			pages = null;
		logger.info("All typeLits found successfully", pages.getTotalPages());
		return pages;
	}
}
