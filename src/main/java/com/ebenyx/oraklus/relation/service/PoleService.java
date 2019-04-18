package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.utils.error.ErrorResponse;
import com.ebenyx.oraklus.relation.entity.Pole;
import com.ebenyx.oraklus.relation.json.request.PoleJsonRequest;

import org.springframework.data.domain.Page;;
import org.springframework.data.domain.Pageable;;

public interface PoleService {

	ErrorResponse<Pole> beforeSave(PoleJsonRequest poleJsonRequest);

	Pole save(Pole pole);

	ErrorResponse<Pole> beforeDelete(Pole pole);

	void delete(Pole pole);

	Pole findOne(Long id);

	Page<Pole> load(Pageable pageable, String keyword);

}