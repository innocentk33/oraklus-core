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

import com.ebenyx.oraklus.relation.entity.Service;
import com.ebenyx.oraklus.relation.json.request.ServiceJsonRequest;
import com.ebenyx.oraklus.relation.repository.ServiceRepository;
import com.ebenyx.oraklus.relation.service.ServiceService;

import com.ebenyx.oraklus.utils.error.ErrorMessage;

import com.ebenyx.oraklus.utils.error.ErrorPattern;

import com.ebenyx.oraklus.utils.error.ErrorResponse;

/**
 * The ServiceService class ...
 * @see ServiceService
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@Transactional
@Service("serviceService")
public class ServiceServiceImpl implements ServiceService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ServiceRepository serviceRepository;

	@Autowired
	public ServiceServiceImpl(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}

	ErrorPattern<Service> ep;

	/**
	 * <p>This method check and validate service properties before saving service</p>
	 * @param serviceJsonRequest contains service parameters to check and validate
	 * @see ErrorResponse
	 * @see Service
	 * @see ServiceJsonRequest
	 * @return the checking and validation response
	 * @since 1.0
	 */

	@Override
	public ErrorResponse <Service> beforeSave(ServiceJsonRequest serviceJsonRequest){
		List <ErrorMessage> errors = new ArrayList <>();
		ep = new ErrorPattern <>();

		Service service = new Service();

		if (serviceJsonRequest == null){
			logger.warn("Service save error : service null");
			errors = ep.getError(0, "service can not be null", "object service can not be null", errors);
		} else {

		}
		return ep.errorResultBuilder(errors, service);
	}

	/**
	 * <p>This method persist service properties in database</p>
	 * @param service contains service parameters to check and validate
	 * @return the service persist in database
	 * @see Service
	 * @since 1.0
	 */

	@Override
	public Service save(Service service) {
		if (service == null){
			logger.warn("Service save failed");
			throw new ValidationException("service can not be null");
		}

		service = serviceRepository.save(service);
		logger.info("Service save successfully", service);
		return service;
	}

	/**
	 * <p>This method check service before deleting</p>
	 * @param service to check
	 * @see Service
	 * @return the checking response
	 * @see ErrorResponse
	 * @since 1.0
	 */

	@Override
	public ErrorResponse<Service> beforeDelete(Service service){
		List<ErrorMessage> errors = new ArrayList<>();
		ep = new ErrorPattern<>();
		if(service == null){
			errors = ep.getError(0, "Service not exist", "Service not exist", errors);
		}
		return ep.errorResultBuilder(errors, service);
	}

	/**
	 * <p>This method delete service in database</p>
	 * @param service to delete
	 * @see Service
	 * @since 1.0
	 */

	@Override
	public void delete(Service service) {
		if (service == null){
			logger.warn("Service delete failed");
			throw new ValidationException("service can not be null");
		}

		serviceRepository.delete(service);
		logger.info("Service delete successfully", service);
	}

	/**
	 * <p>This method find service by id in database</p>
	 * @param id the service id in database
	 * @return the service found in database
	 * @see Service
	 * @since 1.0
	 */

	@Override
	public Service findOne(Long id) {
		if (id == null){
			logger.warn("Service find by id failed");
			throw new ValidationException("id can not be null");
		}

		Optional<Service> service = serviceRepository.findById(id);
		if(service.isPresent()){
			logger.info("Service find by id successfully", service);
			return service.get();
		} else {
			logger.info("Service find by id not exist", service);
			return null;
		}
	}

	/**
	 * <p>This method page services in database</p>
	 * @return services page found in database
	 * @see Service
	 * @since 1.0
	 */

	@Override
		public Page <Service> load(Pageable pageable, String keyword) {
		Page<Service> pages;
		if(keyword == null || keyword.equals(""))
			pages = serviceRepository.findAll(pageable);
		else
			pages = null;
		logger.info("All services found successfully", pages.getTotalPages());
		return pages;
	}
}
