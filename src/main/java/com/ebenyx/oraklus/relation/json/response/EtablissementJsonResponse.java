package com.ebenyx.oraklus.relation.json.response;

import lombok.Getter;
import java.time.LocalDateTime;

import java.io.Serializable;

import com.ebenyx.oraklus.relation.entity.Etablissement;
import com.ebenyx.oraklus.utils.DateUtils;

/**
 * Etablissement response body class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
public class EtablissementJsonResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	private final Long id;

	@Getter
	private final LocalDateTime createDate;

	@Getter
	private final String formatCreateDate;

	@Getter
	private final LocalDateTime lastEditDate;

	@Getter
	private final String formatLastEditDate;

	public EtablissementJsonResponse(Etablissement etablissement) {
		this.id = etablissement.getId();
		this.createDate = etablissement.getCreateDate();
		this.formatCreateDate = DateUtils.dateToString(etablissement.getCreateDate());
		this.lastEditDate = etablissement.getUpdateDate();
		this.formatLastEditDate = DateUtils.dateToString(etablissement.getUpdateDate());
	}

}