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

import com.ebenyx.oraklus.relation.entity.Etablissement;
import com.ebenyx.oraklus.relation.json.request.EtablissementJsonRequest;
import com.ebenyx.oraklus.relation.repository.EtablissementRepository;
import com.ebenyx.oraklus.relation.service.EtablissementService;

import com.ebenyx.oraklus.utils.error.ErrorMessage;

import com.ebenyx.oraklus.utils.error.ErrorPattern;

import com.ebenyx.oraklus.utils.error.ErrorResponse;

/**
 * The EtablissementService class ...
 * @see EtablissementService
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@Transactional
@Service("etablissementService")
public class EtablissementServiceImpl implements EtablissementService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final EtablissementRepository etablissementRepository;

	@Autowired
	public EtablissementServiceImpl(EtablissementRepository etablissementRepository) {
		this.etablissementRepository = etablissementRepository;
	}

	ErrorPattern<Etablissement> ep;

	/**
	 * <p>This method check and validate etablissement properties before saving etablissement</p>
	 * @param etablissementJsonRequest contains etablissement parameters to check and validate
	 * @see ErrorResponse
	 * @see Etablissement
	 * @see EtablissementJsonRequest
	 * @return the checking and validation response
	 * @since 1.0
	 */

	@Override
	public ErrorResponse <Etablissement> beforeSave(EtablissementJsonRequest etablissementJsonRequest){
		List <ErrorMessage> errors = new ArrayList <>();
		ep = new ErrorPattern <>();

		Etablissement etablissement = new Etablissement();

		if (etablissementJsonRequest == null){
			logger.warn("Etablissement save error : etablissement null");
			errors = ep.getError(0, "etablissement can not be null", "object etablissement can not be null", errors);
		} else {

		}
		return ep.errorResultBuilder(errors, etablissement);
	}

	/**
	 * <p>This method persist etablissement properties in database</p>
	 * @param etablissement contains etablissement parameters to check and validate
	 * @return the etablissement persist in database
	 * @see Etablissement
	 * @since 1.0
	 */

	@Override
	public Etablissement save(Etablissement etablissement) {
		if (etablissement == null){
			logger.warn("Etablissement save failed");
			throw new ValidationException("etablissement can not be null");
		}

		etablissement = etablissementRepository.save(etablissement);
		logger.info("Etablissement save successfully", etablissement);
		return etablissement;
	}

	/**
	 * <p>This method check etablissement before deleting</p>
	 * @param etablissement to check
	 * @see Etablissement
	 * @return the checking response
	 * @see ErrorResponse
	 * @since 1.0
	 */

	@Override
	public ErrorResponse<Etablissement> beforeDelete(Etablissement etablissement){
		List<ErrorMessage> errors = new ArrayList<>();
		ep = new ErrorPattern<>();
		if(etablissement == null){
			errors = ep.getError(0, "Etablissement not exist", "Etablissement not exist", errors);
		}
		return ep.errorResultBuilder(errors, etablissement);
	}

	/**
	 * <p>This method delete etablissement in database</p>
	 * @param etablissement to delete
	 * @see Etablissement
	 * @since 1.0
	 */

	@Override
	public void delete(Etablissement etablissement) {
		if (etablissement == null){
			logger.warn("Etablissement delete failed");
			throw new ValidationException("etablissement can not be null");
		}

		etablissementRepository.delete(etablissement);
		logger.info("Etablissement delete successfully", etablissement);
	}

	/**
	 * <p>This method find etablissement by id in database</p>
	 * @param id the etablissement id in database
	 * @return the etablissement found in database
	 * @see Etablissement
	 * @since 1.0
	 */

	@Override
	public Etablissement findOne(Long id) {
		if (id == null){
			logger.warn("Etablissement find by id failed");
			throw new ValidationException("id can not be null");
		}

		Optional<Etablissement> etablissement = etablissementRepository.findById(id);
		if(etablissement.isPresent()){
			logger.info("Etablissement find by id successfully", etablissement);
			return etablissement.get();
		} else {
			logger.info("Etablissement find by id not exist", etablissement);
			return null;
		}
	}

	/**
	 * <p>This method page etablissements in database</p>
	 * @return etablissements page found in database
	 * @see Etablissement
	 * @since 1.0
	 */

	@Override
		public Page <Etablissement> load(Pageable pageable, String keyword) {
		Page<Etablissement> pages;
		if(keyword == null || keyword.equals(""))
			pages = etablissementRepository.findAll(pageable);
		else
			pages = null;
		logger.info("All etablissements found successfully", pages.getTotalPages());
		return pages;
	}
}
