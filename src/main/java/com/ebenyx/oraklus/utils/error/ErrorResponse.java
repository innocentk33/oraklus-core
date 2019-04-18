package com.ebenyx.oraklus.utils.error;

import com.ebenyx.oraklus.utils.error.ErrorMessage;
import lombok.*;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse<T> {

	@Getter @Setter
	private Boolean error;

	@Getter @Setter
	private List<ErrorMessage> errors;

	@Getter @Setter
	private T entity;

}

