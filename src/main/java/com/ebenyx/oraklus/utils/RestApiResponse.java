package com.ebenyx.oraklus.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import com.ebenyx.oraklus.utils.error.ErrorMessage;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class RestApiResponse<T> {

	@Getter @Setter
	@JsonProperty("row") private T row;

	@Getter @Setter
	@JsonProperty("rows") private List<T> rows;

	@Getter @Setter
	@JsonProperty("total") private long total;

	@Getter @Setter
	@JsonProperty("result") private Object result;

	@Getter @Setter
	@JsonProperty("status") private boolean status;

	@Getter @Setter
	@JsonProperty("message") private String message;

	@Getter @Setter
	@JsonProperty("otherText") private String otherText;

	@Getter @Setter
	@JsonProperty("error") private Boolean error;

	@Getter @Setter
	@JsonProperty("errors") private List<ErrorMessage> errors;

}

