package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.utils.error.ErrorResponse;
import com.ebenyx.oraklus.relation.entity.Lit;
import com.ebenyx.oraklus.relation.json.request.LitJsonRequest;

import org.springframework.data.domain.Page;;
import org.springframework.data.domain.Pageable;;

public interface LitService {

	ErrorResponse<Lit> beforeSave(LitJsonRequest litJsonRequest);

	Lit save(Lit lit);

	ErrorResponse<Lit> beforeDelete(Lit lit);

	void delete(Lit lit);

	Lit findOne(Long id);

	Page<Lit> load(Pageable pageable, String keyword);

}