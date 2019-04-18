package com.ebenyx.oraklus.relation.json.response;

import lombok.Getter;
import java.time.LocalDateTime;

import java.io.Serializable;

import com.ebenyx.oraklus.relation.entity.Unite;
import com.ebenyx.oraklus.utils.DateUtils;

/**
 * Unite response body class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
public class UniteJsonResponse implements Serializable {

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

	public UniteJsonResponse(Unite unite) {
		this.id = unite.getId();
		this.createDate = unite.getCreateDate();
		this.formatCreateDate = DateUtils.dateToString(unite.getCreateDate());
		this.lastEditDate = unite.getUpdateDate();
		this.formatLastEditDate = DateUtils.dateToString(unite.getUpdateDate());
	}

}