package com.ticket.reserve.exception;

public class NoSpaceInTrainException extends RuntimeException {
	
	private String message;
	 
    public NoSpaceInTrainException() {}
 
    public NoSpaceInTrainException(String msg)
    {
        super(msg);
        this.message = msg;
    }

}
