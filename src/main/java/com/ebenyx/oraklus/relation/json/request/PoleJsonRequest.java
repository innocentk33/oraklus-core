package com.ebenyx.oraklus.relation.json.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Pole request body class ...
 * @author Brice-Boris BEDA
 * @version 1.0, 16/04/2019
 */
@NoArgsConstructor
@AllArgsConstructor
public class PoleJsonRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter @Setter
	private Long id;

}