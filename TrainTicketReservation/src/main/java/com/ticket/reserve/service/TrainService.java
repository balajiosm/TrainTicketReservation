package com.ticket.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.reserve.entity.Train;
import com.ticket.reserve.repository.TrainRepository;

@Service
public class TrainService {
	
	@Autowired
	private TrainRepository trainRepository;
	
	public Train saveTrainDetails(Train trainDetails) {
		return trainRepository.save(trainDetails);
	}

}
