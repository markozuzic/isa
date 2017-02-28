package com.example.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Reservation;
import com.example.model.Restaurant;
import com.example.model.TableRestaurant;
import com.example.service.ReservationService;

@RestController
public class ReservationController {
	
	
	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping(
			value = "/reservation/getAllReservations",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Reservation> getAllReservations(){
	    return reservationService.getAllReservations();
	}
	
	
	@RequestMapping(
			value = "/reservation/getReservation/{reserid}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Reservation getReservation(@PathVariable("reserid") Long reservationId){
	    return reservationService.getReservation(reservationId);
	}
	
	@RequestMapping(
			value = "/reservation/setTables/{restid}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String setChosenTables(@RequestBody String value, @PathVariable("restid") Long id){
	    return  reservationService.setTables(value, id);
	}
	
	@RequestMapping(
			value = "/reservation/setDateTime/{rid}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String setDateTime(@RequestBody String value, @PathVariable("rid") Long rid){
		return reservationService.createReservation(value, rid); 
	}
	
	@RequestMapping(
			value = "/reservation/getAvailableTables/{reserid}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<TableRestaurant> getAvailableTables(@PathVariable("reserid") Long reservationId){
		return reservationService.getAvailableTables(reservationId);
	}
	
	
	@RequestMapping(
			value = "/reservation/getRestaurant/{reserid}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Restaurant getRestaurantForReservation(@PathVariable("reserid") Long reservationId){
		return reservationService.getRestaurantForReservation(reservationId);
	}
	
	@RequestMapping(
			value = "/reservation/inviteFriend/{reserid}/{friendid}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String inviteFriend(@PathVariable("reserid") Long reservationId, @PathVariable("friendid") Long friendId){
		return reservationService.inviteFriend(reservationId, friendId);
	}
	
	@RequestMapping(
			value = "/reservation/acceptInvitation/{reserid}/{friendid}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String acceptInvitation(@PathVariable("reserid") Long reservationId, @PathVariable("friendid") Long friendId){
		return reservationService.acceptInvitation(reservationId, friendId);
	}
	
	@RequestMapping(
			value = "/reservation/declineInvitation/{reserid}/{friendid}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String declineInvitation(@PathVariable("reserid") Long reservationId, @PathVariable("friendid") Long friendId){
		return reservationService.declineInvitation(reservationId, friendId);
	}
	
	@RequestMapping(
			value = "/reservation/cancel/{reserid}",
			method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String cancelReservation(@PathVariable("reserid") Long reservationId){
		return reservationService.cancelReservation(reservationId);
	}
}

