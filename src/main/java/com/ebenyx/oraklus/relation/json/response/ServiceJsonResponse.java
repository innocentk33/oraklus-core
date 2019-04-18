package com.ebenyx.oraklus.relation.json.response;

import lombok.Getter;
import java.time.LocalDateTime;

import java.io.Serializable;

import com.ebenyx.oraklus.relation.entity.Service;
import com.ebenyx.oraklus.utils.DateUtils;

/**
 * Service response body class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
public class ServiceJsonResponse implements Serializable {

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

	public ServiceJsonResponse(Service service) {
		this.id = service.getId();
		this.createDate = service.getCreateDate();
		this.formatCreateDate = DateUtils.dateToString(service.getCreateDate());
		this.lastEditDate = service.getUpdateDate();
		this.formatLastEditDate = DateUtils.dateToString(service.getUpdateDate());
	}

}