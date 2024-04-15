package com.ticket.reserve.exception;

public class NoReceiptFoundException extends RuntimeException {
	
	private String message;
	 
    public NoReceiptFoundException() {}
 
    public NoReceiptFoundException(String msg)
    {
        super(msg);
        this.message = msg;
    }

}
