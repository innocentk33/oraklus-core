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

import com.ebenyx.oraklus.relation.entity.Pole;
import com.ebenyx.oraklus.relation.json.request.PoleJsonRequest;
import com.ebenyx.oraklus.relation.repository.PoleRepository;
import com.ebenyx.oraklus.relation.service.PoleService;

import com.ebenyx.oraklus.utils.error.ErrorMessage;

import com.ebenyx.oraklus.utils.error.ErrorPattern;

import com.ebenyx.oraklus.utils.error.ErrorResponse;

/**
 * The PoleService class ...
 * @see PoleService
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@Transactional
@Service("poleService")
public class PoleServiceImpl implements PoleService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final PoleRepository poleRepository;

	@Autowired
	public PoleServiceImpl(PoleRepository poleRepository) {
		this.poleRepository = poleRepository;
	}

	ErrorPattern<Pole> ep;

	/**
	 * <p>This method check and validate pole properties before saving pole</p>
	 * @param poleJsonRequest contains pole parameters to check and validate
	 * @see ErrorResponse
	 * @see Pole
	 * @see PoleJsonRequest
	 * @return the checking and validation response
	 * @since 1.0
	 */

	@Override
	public ErrorResponse <Pole> beforeSave(PoleJsonRequest poleJsonRequest){
		List <ErrorMessage> errors = new ArrayList <>();
		ep = new ErrorPattern <>();

		Pole pole = new Pole();

		if (poleJsonRequest == null){
			logger.warn("Pole save error : pole null");
			errors = ep.getError(0, "pole can not be null", "object pole can not be null", errors);
		} else {

		}
		return ep.errorResultBuilder(errors, pole);
	}

	/**
	 * <p>This method persist pole properties in database</p>
	 * @param pole contains pole parameters to check and validate
	 * @return the pole persist in database
	 * @see Pole
	 * @since 1.0
	 */

	@Override
	public Pole save(Pole pole) {
		if (pole == null){
			logger.warn("Pole save failed");
			throw new ValidationException("pole can not be null");
		}

		pole = poleRepository.save(pole);
		logger.info("Pole save successfully", pole);
		return pole;
	}

	/**
	 * <p>This method check pole before deleting</p>
	 * @param pole to check
	 * @see Pole
	 * @return the checking response
	 * @see ErrorResponse
	 * @since 1.0
	 */

	@Override
	public ErrorResponse<Pole> beforeDelete(Pole pole){
		List<ErrorMessage> errors = new ArrayList<>();
		ep = new ErrorPattern<>();
		if(pole == null){
			errors = ep.getError(0, "Pole not exist", "Pole not exist", errors);
		}
		return ep.errorResultBuilder(errors, pole);
	}

	/**
	 * <p>This method delete pole in database</p>
	 * @param pole to delete
	 * @see Pole
	 * @since 1.0
	 */

	@Override
	public void delete(Pole pole) {
		if (pole == null){
			logger.warn("Pole delete failed");
			throw new ValidationException("pole can not be null");
		}

		poleRepository.delete(pole);
		logger.info("Pole delete successfully", pole);
	}

	/**
	 * <p>This method find pole by id in database</p>
	 * @param id the pole id in database
	 * @return the pole found in database
	 * @see Pole
	 * @since 1.0
	 */

	@Override
	public Pole findOne(Long id) {
		if (id == null){
			logger.warn("Pole find by id failed");
			throw new ValidationException("id can not be null");
		}

		Optional<Pole> pole = poleRepository.findById(id);
		if(pole.isPresent()){
			logger.info("Pole find by id successfully", pole);
			return pole.get();
		} else {
			logger.info("Pole find by id not exist", pole);
			return null;
		}
	}

	/**
	 * <p>This method page poles in database</p>
	 * @return poles page found in database
	 * @see Pole
	 * @since 1.0
	 */

	@Override
		public Page <Pole> load(Pageable pageable, String keyword) {
		Page<Pole> pages;
		if(keyword == null || keyword.equals(""))
			pages = poleRepository.findAll(pageable);
		else
			pages = null;
		logger.info("All poles found successfully", pages.getTotalPages());
		return pages;
	}
}
