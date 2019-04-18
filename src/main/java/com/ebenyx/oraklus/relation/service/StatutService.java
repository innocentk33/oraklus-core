package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.utils.error.ErrorResponse;
import com.ebenyx.oraklus.relation.entity.Statut;
import com.ebenyx.oraklus.relation.json.request.StatutJsonRequest;

import org.springframework.data.domain.Page;;
import org.springframework.data.domain.Pageable;;

public interface StatutService {

	ErrorResponse<Statut> beforeSave(StatutJsonRequest statutJsonRequest);

	Statut save(Statut statut);

	ErrorResponse<Statut> beforeDelete(Statut statut);

	void delete(Statut statut);

	Statut findOne(Long id);

	Page<Statut> load(Pageable pageable, String keyword);

}