package com.ticket.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.reserve.entity.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train,Long> {

}
