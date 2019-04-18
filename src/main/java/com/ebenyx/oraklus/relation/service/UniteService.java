package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.utils.error.ErrorResponse;
import com.ebenyx.oraklus.relation.entity.Unite;
import com.ebenyx.oraklus.relation.json.request.UniteJsonRequest;

import org.springframework.data.domain.Page;;
import org.springframework.data.domain.Pageable;;

public interface UniteService {

	ErrorResponse<Unite> beforeSave(UniteJsonRequest uniteJsonRequest);

	Unite save(Unite unite);

	ErrorResponse<Unite> beforeDelete(Unite unite);

	void delete(Unite unite);

	Unite findOne(Long id);

	Page<Unite> load(Pageable pageable, String keyword);

}