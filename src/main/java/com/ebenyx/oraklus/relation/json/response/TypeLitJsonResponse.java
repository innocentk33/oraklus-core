package com.ebenyx.oraklus.relation.json.response;

import lombok.Getter;
import java.time.LocalDateTime;

import java.io.Serializable;

import com.ebenyx.oraklus.relation.entity.TypeLit;
import com.ebenyx.oraklus.utils.DateUtils;

/**
 * TypeLit response body class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
public class TypeLitJsonResponse implements Serializable {

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

	public TypeLitJsonResponse(TypeLit typeLit) {
		this.id = typeLit.getId();
		this.createDate = typeLit.getCreateDate();
		this.formatCreateDate = DateUtils.dateToString(typeLit.getCreateDate());
		this.lastEditDate = typeLit.getUpdateDate();
		this.formatLastEditDate = DateUtils.dateToString(typeLit.getUpdateDate());
	}

}