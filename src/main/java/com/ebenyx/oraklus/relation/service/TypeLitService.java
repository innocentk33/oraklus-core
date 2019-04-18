package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.utils.error.ErrorResponse;
import com.ebenyx.oraklus.relation.entity.TypeLit;
import com.ebenyx.oraklus.relation.json.request.TypeLitJsonRequest;

import org.springframework.data.domain.Page;;
import org.springframework.data.domain.Pageable;;

public interface TypeLitService {

	ErrorResponse<TypeLit> beforeSave(TypeLitJsonRequest typeLitJsonRequest);

	TypeLit save(TypeLit typeLit);

	ErrorResponse<TypeLit> beforeDelete(TypeLit typeLit);

	void delete(TypeLit typeLit);

	TypeLit findOne(Long id);

	Page<TypeLit> load(Pageable pageable, String keyword);

}