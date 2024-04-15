package com.ticket.reserve.model;

import com.ticket.reserve.entity.User;

public class UserSeat {
	
	private User user;
	private String seatNo;
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	
	

}
