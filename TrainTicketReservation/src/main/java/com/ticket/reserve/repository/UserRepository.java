package com.ticket.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.reserve.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
}