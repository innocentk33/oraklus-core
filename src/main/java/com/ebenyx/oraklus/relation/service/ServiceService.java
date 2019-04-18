package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.utils.error.ErrorResponse;
import com.ebenyx.oraklus.relation.entity.Service;
import com.ebenyx.oraklus.relation.json.request.ServiceJsonRequest;

import org.springframework.data.domain.Page;;
import org.springframework.data.domain.Pageable;;

public interface ServiceService {

	ErrorResponse<Service> beforeSave(ServiceJsonRequest serviceJsonRequest);

	Service save(Service service);

	ErrorResponse<Service> beforeDelete(Service service);

	void delete(Service service);

	Service findOne(Long id);

	Page<Service> load(Pageable pageable, String keyword);

}