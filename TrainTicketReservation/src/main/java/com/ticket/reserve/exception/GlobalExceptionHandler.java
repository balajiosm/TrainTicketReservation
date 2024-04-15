package com.ticket.reserve.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value
			= NoSpaceInTrainException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public @ResponseBody ErrorResponse handleException(NoSpaceInTrainException ex)
	{
		return new ErrorResponse(
				HttpStatus.CONFLICT.value(), ex.getMessage());
	}

	@ExceptionHandler({NoReceiptFoundException.class, NoUserFoundException.class, NoModificationAllowedException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorResponse handleException(Exception ex)
	{
		return new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}
	
}