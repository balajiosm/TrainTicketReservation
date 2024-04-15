package com.ticket.reserve.exception;

public class NoModificationAllowedException extends RuntimeException {
	
	private String message;
	 
    public NoModificationAllowedException() {}
 
    public NoModificationAllowedException(String msg)
    {
        super(msg);
        this.message = msg;
    }

}
