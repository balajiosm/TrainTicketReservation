package com.ticket.reserve.exception;

public class NoUserFoundException extends RuntimeException {
	
	private String message;
	 
    public NoUserFoundException() {}
 
    public NoUserFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }

}
