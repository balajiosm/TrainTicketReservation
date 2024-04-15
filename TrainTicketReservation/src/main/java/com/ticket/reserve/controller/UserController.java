package com.ticket.reserve.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.reserve.entity.User;
import com.ticket.reserve.model.BookingResponseDTO;
import com.ticket.reserve.model.UserDTO;
import com.ticket.reserve.model.UserSeat;
import com.ticket.reserve.service.BookingService;
import com.ticket.reserve.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/saveUser")
	public ResponseEntity<UserDTO> saveUserDetails(@RequestBody UserDTO userDetails) {
		User user = new User();
		BeanUtils.copyProperties(userDetails, user);
        User newUser = userService.saveUser(user);
        BeanUtils.copyProperties(newUser, userDetails);
        return ResponseEntity.ok(userDetails);
    }
	
	@GetMapping("/getUserReceiptByTransaction")
	public BookingResponseDTO getUserReceipt(@RequestParam(name = "id") String userId) {
		BookingResponseDTO response = bookingService.getUserReceiptDetails(userId);
        return response;
    }
	
	@GetMapping("/getUserSeatBySection")
	public List<UserSeat> getUsersAndSeatDetails(@RequestParam(name = "section") String section) {
		List<UserSeat> userSeatLists = bookingService.getUserSeatDetails(section);
        return userSeatLists;
    }
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> deleteUserFromTrain(@PathVariable("id") long id) {
		bookingService.deleteUser(id);
		return ResponseEntity.status( HttpStatus.OK ).body ( "Deleted" );
    }
	
	@PutMapping("/modifyUserSeat")
	public UserSeat modifyUserSeat(@RequestBody UserSeat userSeat) {
		UserSeat us = bookingService.modifyUserSeat(userSeat);
		return us;
    }

}
