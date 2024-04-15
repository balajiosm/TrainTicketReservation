package com.ticket.reserve.model;

public class BookingResponseDTO {
	
	private String fromStation;
	private String toStation;
	private UserDTO userDTO;
	private Double pricePaid;
	
	public String getFromStation() {
		return fromStation;
	}
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}
	public String getToStation() {
		return toStation;
	}
	public void setToStation(String toStation) {
		this.toStation = toStation;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public Double getPricePaid() {
		return pricePaid;
	}
	public void setPricePaid(Double pricePaid) {
		this.pricePaid = pricePaid;
	}

}
