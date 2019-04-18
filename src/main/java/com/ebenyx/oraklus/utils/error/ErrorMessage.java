package com.ebenyx.oraklus.utils.error;

import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

	@Getter @Setter
	private int code;

	@Getter @Setter
	private String message;

	@Getter @Setter
	private String description;

}

