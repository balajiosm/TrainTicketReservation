package com.ticket.reserve.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticket.reserve.entity.Purchase;
import com.ticket.reserve.entity.Train;
import com.ticket.reserve.entity.User;
import com.ticket.reserve.exception.NoModificationAllowedException;
import com.ticket.reserve.exception.NoReceiptFoundException;
import com.ticket.reserve.exception.NoSpaceInTrainException;
import com.ticket.reserve.exception.NoUserFoundException;
import com.ticket.reserve.model.BookingRequestDTO;
import com.ticket.reserve.model.BookingResponseDTO;
import com.ticket.reserve.model.UserDTO;
import com.ticket.reserve.model.UserSeat;
import com.ticket.reserve.repository.PurchaseRepository;
import com.ticket.reserve.repository.TrainRepository;
import com.ticket.reserve.repository.UserRepository;


@Service
public class BookingService {

	private static final int SECTION_SEAT_COUNT = 50;
	private static List<Integer> seatListA;
	private static List<Integer> seatListB;

	static {
		seatListA = new ArrayList<Integer>();
		seatListB = new ArrayList<Integer>();
		for(int i=0; i < SECTION_SEAT_COUNT; i++) {
			seatListA.add(i+1);
			seatListB.add(i+1);
		}
		Collections.shuffle(seatListA);
		Collections.shuffle(seatListB);
	}

	@Autowired
	TrainRepository trainRepository;
	
	@Autowired
	PurchaseRepository purchaseRepository;
	
	@Autowired
	UserRepository userRepository;

	public BookingResponseDTO purchaseTicket(BookingRequestDTO purchaseRequest) throws NoSpaceInTrainException{
		Train trainInfo = null;
		Random rand = new Random();
		BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
		Optional<Train> trainInfoOpt = trainRepository.findById(purchaseRequest.getTrainId());
		Optional<User> userInfoOpt = userRepository.findById(purchaseRequest.getUserId());
		if(trainInfoOpt.isPresent() && userInfoOpt.isPresent()) {
			trainInfo = trainInfoOpt.get();
			int totalSeat = trainInfo.getTotalSeats();
			if(totalSeat > 0) {//booking and allocating seat starts
				int randNum = rand.nextInt(2);
				String seatNo = getSeatNoSectionWise(randNum);//get Seat Number
				if(seatNo.isEmpty()) {
					throw new NoSpaceInTrainException("No space in train to book ticket!!!");
				}
				Purchase newPurchase = new Purchase();
				newPurchase.setTrainId(purchaseRequest.getTrainId());
				newPurchase.setUserId(purchaseRequest.getUserId());
				newPurchase.setSeatNo(seatNo);
				purchaseRepository.save(newPurchase);//save purchase/transaction details
			}
			totalSeat = totalSeat - 1;
			trainInfo.setTotalSeats(totalSeat);
			trainRepository.save(trainInfo);//decrement the seat by one and update the train detail
			//set response
			User userEntity = userInfoOpt.get();
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
			bookingResponseDTO.setFromStation(trainInfo.getFromStation());
			bookingResponseDTO.setToStation(trainInfo.getToStation());
			bookingResponseDTO.setPricePaid(trainInfo.getTicketPrice());
			bookingResponseDTO.setUserDTO(userDTO);
		}
		return bookingResponseDTO;

	}
	
	public BookingResponseDTO getUserReceiptDetails(String userId) throws NoReceiptFoundException {
		Long userIdentity = Long.valueOf(userId);
		Optional<Purchase> purchaseOpt = purchaseRepository.findByUserId(userIdentity);//check if user has any receipts
		BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
		if(purchaseOpt.isPresent()) {
			Purchase purchase = purchaseOpt.get();
			Optional<Train> trainInfoOpt = trainRepository.findById(purchase.getTrainId());
			Optional<User> userInfoOpt = userRepository.findById(purchase.getUserId());
			bookingResponseDTO.setFromStation(trainInfoOpt.get().getFromStation());
			bookingResponseDTO.setToStation(trainInfoOpt.get().getToStation());
			bookingResponseDTO.setPricePaid(trainInfoOpt.get().getTicketPrice());
			User userEntity = userInfoOpt.get();
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
			bookingResponseDTO.setUserDTO(userDTO);
		}
		else {
			throw new NoReceiptFoundException("No receipt for this user yet !!!!");
		}
		return bookingResponseDTO;
		
	}
	
	public List<UserSeat> getUserSeatDetails(String section) {
		List<Purchase> purchaseList = purchaseRepository.findBySeatNoStartsWith(section);
		List<UserSeat> userSeatLists = new ArrayList<UserSeat>();
		for(Purchase purchase : purchaseList) {
			Optional<User> userOpt = userRepository.findById(purchase.getUserId());
			if(userOpt.isPresent()) {
				UserSeat userSeat = new UserSeat();
				userSeat.setUser(userOpt.get());
				userSeat.setSeatNo(purchase.getSeatNo());
				userSeatLists.add(userSeat);
			}
		}
		return userSeatLists;

	}
	
	@Transactional
	public void deleteUser(Long userId) throws  NoReceiptFoundException, NoUserFoundException {
		Optional<Purchase> purchaseOpt = purchaseRepository.findByUserId(userId);//check if user has any purchases
		if(purchaseOpt.isPresent()) {
			Purchase purchase = purchaseOpt.get();
			String seatNoStr = purchase.getSeatNo();
			int seatNo = Integer.parseInt(seatNoStr.substring(1));
			Optional<Train> trainInfoOpt = trainRepository.findById(purchase.getTrainId());
			purchaseRepository.deleteByUserId(userId);
			//upon success of deleting, increment seat count by 1 in train table below
			//add this to seat list to denote that this seat is vacant now
			if(seatNoStr.startsWith("A")) {
				seatListA.add(seatNo);
			}
			else
			{
				seatListB.add(seatNo);
			}
			Train trainInfo = trainInfoOpt.get();
			trainInfo.setTotalSeats(trainInfo.getTotalSeats() + 1);
			trainRepository.save(trainInfo);//increment the seat by one and update the train detail
		}
		else {
			throw new NoUserFoundException("User Purchase details not Present hence cannot delete !!!!");
		}
	}
	
	public UserSeat modifyUserSeat(UserSeat userSeat) throws NoModificationAllowedException, NoUserFoundException {
		String newSeatNo=userSeat.getSeatNo();
		int newSeatNoInt = Integer.parseInt(newSeatNo.substring(1));
		Optional<Purchase> purchaseOptByUser = purchaseRepository.findByUserId(userSeat.getUser().getUserId());
		Optional<Purchase> purchaseOptByNewSeat = purchaseRepository.findBySeatNo(newSeatNo);
		if(purchaseOptByNewSeat.isPresent()) {
			throw new NoModificationAllowedException("User cannot modify the seat since seat is not vacant !!!!");
		}
		else if(purchaseOptByUser.isEmpty()) {
			throw new NoUserFoundException("User Purchase details not Present hence cannot modify !!!!");
		}
		else {
			System.out.println("Yes seat is vacant and we can modify now");
			Purchase purchase = purchaseOptByUser.get();
			String oldSeatNo = purchase.getSeatNo();
			int oldSeatNoInt = Integer.parseInt(oldSeatNo.substring(1));
			purchase.setSeatNo(newSeatNo);//setting new seat no for that user
			purchaseRepository.save(purchase);
			if(oldSeatNo.startsWith("A")) {
				seatListA.add(oldSeatNoInt);
			}
			else
			{
				seatListB.add(oldSeatNoInt);
			}
			
			if(newSeatNo.startsWith("A")) {
				seatListA.remove(newSeatNoInt);
			}
			else
			{
				seatListB.remove(newSeatNoInt);
			}
		}
		return userSeat;
	}

	private String getSeatNoSectionWise(int randNum) {
		String sectionSeatNo="";
		switch (randNum) {
		case 0:
			if(!seatListA.isEmpty()) {
				sectionSeatNo = "A" + seatListA.get(0);//always take the first element as seat 
				seatListA.remove(0);//and remove it
				break;
			}
		case 1:
			if(!seatListB.isEmpty()) {
				sectionSeatNo = "B" + seatListB.get(0); 
				seatListB.remove(0);
			}
			break;
		default:
			break;
		}
		return sectionSeatNo;

	}

}
