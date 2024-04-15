package com.ticket.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.reserve.entity.User;
import com.ticket.reserve.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}

}
