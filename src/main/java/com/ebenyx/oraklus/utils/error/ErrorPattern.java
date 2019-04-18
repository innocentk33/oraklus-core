package com.ebenyx.oraklus.utils.error;

import com.ebenyx.oraklus.utils.RestApiResponse;

import org.slf4j.Logger;
import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class ErrorPattern<T> {;

	RestApiResponse<T> response;

	Exception ex;

	int code;

	String message;

	String description;

	Logger logger;

	public ErrorMessage getError(int code, String message, String description){
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode(code);
		errorMessage.setMessage(message);
		errorMessage.setDescription(description);
		return errorMessage;
	}

	public List<ErrorMessage> getError(int code, String message, String description, List<ErrorMessage> errors){
		errors.add(getError(code, message, description));
		return errors;
	}

	public RestApiResponse<T> trace(){
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode(code);
		errorMessage.setMessage(message);
		errorMessage.setDescription(description);
		ex.printStackTrace();
		response.setResult(false);
		response.setStatus(false);
		response.setRow(null);
		response.setRows(null);
		response.setMessage(message);
		response.setTotal(0);
		logger.error(message);
		return response;
	}

	public ErrorResponse<T> errorResultBuilder(List<ErrorMessage> errors, T t){
		ErrorResponse<T> errorResponse = new ErrorResponse<>();
		if(errors.isEmpty()){
			errorResponse.setError(false);
			errorResponse.setEntity(t);
		} else {
			errorResponse.setError(true);
			errorResponse.setEntity(null);
			errorResponse.setErrors(errors);
		}
		return errorResponse;
	}

}

