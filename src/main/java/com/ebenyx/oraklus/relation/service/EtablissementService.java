package com.ebenyx.oraklus.relation.service;

import com.ebenyx.oraklus.utils.error.ErrorResponse;
import com.ebenyx.oraklus.relation.entity.Etablissement;
import com.ebenyx.oraklus.relation.json.request.EtablissementJsonRequest;

import org.springframework.data.domain.Page;;
import org.springframework.data.domain.Pageable;;

public interface EtablissementService {

	ErrorResponse<Etablissement> beforeSave(EtablissementJsonRequest etablissementJsonRequest);

	Etablissement save(Etablissement etablissement);

	ErrorResponse<Etablissement> beforeDelete(Etablissement etablissement);

	void delete(Etablissement etablissement);

	Etablissement findOne(Long id);

	Page<Etablissement> load(Pageable pageable, String keyword);

}