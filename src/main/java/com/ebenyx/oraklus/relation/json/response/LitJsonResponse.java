package com.ebenyx.oraklus.relation.json.response;

import lombok.Getter;
import java.time.LocalDateTime;

import java.io.Serializable;

import com.ebenyx.oraklus.relation.entity.Lit;
import com.ebenyx.oraklus.utils.DateUtils;

/**
 * Lit response body class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
public class LitJsonResponse implements Serializable {

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

	public LitJsonResponse(Lit lit) {
		this.id = lit.getId();
		this.createDate = lit.getCreateDate();
		this.formatCreateDate = DateUtils.dateToString(lit.getCreateDate());
		this.lastEditDate = lit.getUpdateDate();
		this.formatLastEditDate = DateUtils.dateToString(lit.getUpdateDate());
	}

}