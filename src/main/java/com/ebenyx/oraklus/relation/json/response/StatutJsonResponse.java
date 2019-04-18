package com.ebenyx.oraklus.relation.json.response;

import lombok.Getter;
import java.time.LocalDateTime;

import java.io.Serializable;

import com.ebenyx.oraklus.relation.entity.Statut;
import com.ebenyx.oraklus.utils.DateUtils;

/**
 * Statut response body class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
public class StatutJsonResponse implements Serializable {

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

	public StatutJsonResponse(Statut statut) {
		this.id = statut.getId();
		this.createDate = statut.getCreateDate();
		this.formatCreateDate = DateUtils.dateToString(statut.getCreateDate());
		this.lastEditDate = statut.getUpdateDate();
		this.formatLastEditDate = DateUtils.dateToString(statut.getUpdateDate());
	}

}