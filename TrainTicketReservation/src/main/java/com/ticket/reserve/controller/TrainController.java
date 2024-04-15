package com.ticket.reserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.reserve.entity.Train;
import com.ticket.reserve.service.TrainService;

@RestController
@RequestMapping("/api/v1")
public class TrainController {
	
	@Autowired
	private TrainService trainService;
	
	@PostMapping("/registerTrain")
	public ResponseEntity<Train> saveUserDetails(@RequestBody Train trainDetails) {
        Train newTrain = trainService.saveTrainDetails(trainDetails);
        return ResponseEntity.ok(newTrain);
    }

}
