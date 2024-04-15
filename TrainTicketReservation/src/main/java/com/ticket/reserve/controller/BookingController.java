package com.ticket.reserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.reserve.model.BookingRequestDTO;
import com.ticket.reserve.model.BookingResponseDTO;
import com.ticket.reserve.service.BookingService;

@RestController
@RequestMapping("/api/v1")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/submitPurchaseForTicket")
	public ResponseEntity<BookingResponseDTO> purchaseTicket(@RequestBody BookingRequestDTO purchaseRequest) {
		BookingResponseDTO bookingResponseDTO = bookingService.purchaseTicket(purchaseRequest);
		return ResponseEntity.ok(bookingResponseDTO);
	}
	

}
