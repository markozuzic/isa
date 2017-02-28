package com.example.service;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Reservation;
import com.example.model.Restaurant;
import com.example.model.TableRestaurant;

public interface ReservationService {

	public List<Reservation> getAllReservations();
	
	public String createReservation(String value, Long rid);
	
	public String setTables(String value, Long reservationId);
	
	public void addTable(Long reservationId, TableRestaurant table);
	
	public ArrayList<TableRestaurant> getAvailableTables(Long reservationId);

	public Reservation getReservation(Long reservationId);
	
	public Restaurant getRestaurantForReservation(Long reservationId);

	public String inviteFriend(Long reservationId, Long friendId);
	
	public String acceptInvitation(Long reservationId, Long friendId);
	
	public String declineInvitation(Long reservationId, Long friendId);

	public String cancelReservation(Long reservationId);
}
