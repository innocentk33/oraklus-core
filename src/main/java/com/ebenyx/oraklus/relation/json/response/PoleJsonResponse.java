package com.ebenyx.oraklus.relation.json.response;

import lombok.Getter;
import java.time.LocalDateTime;

import java.io.Serializable;

import com.ebenyx.oraklus.relation.entity.Pole;
import com.ebenyx.oraklus.utils.DateUtils;

/**
 * Pole response body class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
public class PoleJsonResponse implements Serializable {

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

	public PoleJsonResponse(Pole pole) {
		this.id = pole.getId();
		this.createDate = pole.getCreateDate();
		this.formatCreateDate = DateUtils.dateToString(pole.getCreateDate());
		this.lastEditDate = pole.getUpdateDate();
		this.formatLastEditDate = DateUtils.dateToString(pole.getUpdateDate());
	}

}