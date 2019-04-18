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

import com.ebenyx.oraklus.relation.entity.Statut;
import com.ebenyx.oraklus.relation.json.request.StatutJsonRequest;
import com.ebenyx.oraklus.relation.repository.StatutRepository;
import com.ebenyx.oraklus.relation.service.StatutService;

import com.ebenyx.oraklus.utils.error.ErrorMessage;

import com.ebenyx.oraklus.utils.error.ErrorPattern;

import com.ebenyx.oraklus.utils.error.ErrorResponse;

/**
 * The StatutService class ...
 * @see StatutService
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@Transactional
@Service("statutService")
public class StatutServiceImpl implements StatutService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final StatutRepository statutRepository;

	@Autowired
	public StatutServiceImpl(StatutRepository statutRepository) {
		this.statutRepository = statutRepository;
	}

	ErrorPattern<Statut> ep;

	/**
	 * <p>This method check and validate statut properties before saving statut</p>
	 * @param statutJsonRequest contains statut parameters to check and validate
	 * @see ErrorResponse
	 * @see Statut
	 * @see StatutJsonRequest
	 * @return the checking and validation response
	 * @since 1.0
	 */

	@Override
	public ErrorResponse <Statut> beforeSave(StatutJsonRequest statutJsonRequest){
		List <ErrorMessage> errors = new ArrayList <>();
		ep = new ErrorPattern <>();

		Statut statut = new Statut();

		if (statutJsonRequest == null){
			logger.warn("Statut save error : statut null");
			errors = ep.getError(0, "statut can not be null", "object statut can not be null", errors);
		} else {

		}
		return ep.errorResultBuilder(errors, statut);
	}

	/**
	 * <p>This method persist statut properties in database</p>
	 * @param statut contains statut parameters to check and validate
	 * @return the statut persist in database
	 * @see Statut
	 * @since 1.0
	 */

	@Override
	public Statut save(Statut statut) {
		if (statut == null){
			logger.warn("Statut save failed");
			throw new ValidationException("statut can not be null");
		}

		statut = statutRepository.save(statut);
		logger.info("Statut save successfully", statut);
		return statut;
	}

	/**
	 * <p>This method check statut before deleting</p>
	 * @param statut to check
	 * @see Statut
	 * @return the checking response
	 * @see ErrorResponse
	 * @since 1.0
	 */

	@Override
	public ErrorResponse<Statut> beforeDelete(Statut statut){
		List<ErrorMessage> errors = new ArrayList<>();
		ep = new ErrorPattern<>();
		if(statut == null){
			errors = ep.getError(0, "Statut not exist", "Statut not exist", errors);
		}
		return ep.errorResultBuilder(errors, statut);
	}

	/**
	 * <p>This method delete statut in database</p>
	 * @param statut to delete
	 * @see Statut
	 * @since 1.0
	 */

	@Override
	public void delete(Statut statut) {
		if (statut == null){
			logger.warn("Statut delete failed");
			throw new ValidationException("statut can not be null");
		}

		statutRepository.delete(statut);
		logger.info("Statut delete successfully", statut);
	}

	/**
	 * <p>This method find statut by id in database</p>
	 * @param id the statut id in database
	 * @return the statut found in database
	 * @see Statut
	 * @since 1.0
	 */

	@Override
	public Statut findOne(Long id) {
		if (id == null){
			logger.warn("Statut find by id failed");
			throw new ValidationException("id can not be null");
		}

		Optional<Statut> statut = statutRepository.findById(id);
		if(statut.isPresent()){
			logger.info("Statut find by id successfully", statut);
			return statut.get();
		} else {
			logger.info("Statut find by id not exist", statut);
			return null;
		}
	}

	/**
	 * <p>This method page statuts in database</p>
	 * @return statuts page found in database
	 * @see Statut
	 * @since 1.0
	 */

	@Override
		public Page <Statut> load(Pageable pageable, String keyword) {
		Page<Statut> pages;
		if(keyword == null || keyword.equals(""))
			pages = statutRepository.findAll(pageable);
		else
			pages = null;
		logger.info("All statuts found successfully", pages.getTotalPages());
		return pages;
	}
}
