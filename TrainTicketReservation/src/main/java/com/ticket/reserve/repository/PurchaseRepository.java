package com.ticket.reserve.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ticket.reserve.entity.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
	
	Optional<Purchase> findByUserId(Long userId);
	
	List<Purchase> findBySeatNoStartsWith(String section);
	
	Long deleteByUserId(Long userId);
	
	Optional<Purchase> findBySeatNo(String seatNo);

}
