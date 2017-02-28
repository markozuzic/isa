package com.example.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.OrderR;
import com.example.model.Reservation;
import com.example.model.Restaurant;
import com.example.model.TableRestaurant;
import com.example.model.User;
import com.example.model.Visit;
import com.example.repository.OrderRepository;
import com.example.repository.ReservationRepository;
import com.example.repository.RestaurantRepository;
import com.example.repository.TableRepository;
import com.example.repository.UserRepository;
import com.example.repository.VisitRepository;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class ReservationServiceImpl implements ReservationService{

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private TableRepository tableRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VisitRepository visitRepository;
	
	@Autowired 
	private HttpSession httpSession;
	
	@Autowired 
	private OrderRepository orderRepository;
	
	@Override
	public Reservation getReservation(Long reservationId) {
		return reservationRepository.findOne(reservationId);
	}
	
	@Override
	public List<Reservation> getAllReservations() {
		return reservationRepository.findAll(null).getContent();
	}

	@Override
	public String createReservation(String value, Long rid) {
	    String tokens[] = value.split(",");
	    String dateString = tokens[0];
	    String durationString = tokens[1]; 
	    try {
			Date datetime = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(dateString);
			int duration = Integer.parseInt(durationString);	
			User user = (User)httpSession.getAttribute("user");
			Reservation r = new Reservation(rid, datetime, duration, user.getId());
			Reservation rdb = reservationRepository.save(r);
			return ""+rdb.getId();
		} catch (ParseException e) {
			return "ParseError";
		}
	}

	@Override
	public String setTables(String value, Long reservationId) {
		String tokens[] = value.split(",");
		Reservation r = reservationRepository.findOne(reservationId);
		for (String tableId : tokens) {
	    	int id = Integer.parseInt(tableId);
			TableRestaurant table = tableRepository.findOne(id);
			r.getTables().add(table);
		}
		reservationRepository.save(r);
		User user = userRepository.findOne(r.getUserId());
		Restaurant restaurant = restaurantRepository.findOne(r.getRestaurantId());
		Visit visit = new Visit(user, r, restaurant.getName());
		visitRepository.save(visit);
		return "OK";
	}

	@Override
	public void addTable(Long reservationId, TableRestaurant table) {
		Reservation r = reservationRepository.findOne(reservationId);
		r.getTables().add(table);
		reservationRepository.save(r);
	}

	@Override
	public ArrayList<TableRestaurant> getAvailableTables(Long reservationId) {
		Reservation reservation = reservationRepository.findOne(reservationId);
		List<Reservation> allReservations = reservationRepository.findAll(null).getContent();
		ArrayList<TableRestaurant> availableTables = new ArrayList<TableRestaurant>();
		availableTables.addAll(tableRepository.findByRestaurant(reservation.getRestaurantId()));
		ArrayList<TableRestaurant> unavailableTables = new ArrayList<TableRestaurant>();
		Calendar calendar = Calendar.getInstance();

	    calendar.setTime( reservation.getDateTime() );
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    
	    Date dateNewMidnight = calendar.getTime();
		
		for (Reservation rDB : allReservations) {
			if (rDB.getRestaurantId() == reservation.getRestaurantId()) {
				Date dateDBStart = rDB.getDateTime(); 
				calendar.setTime(dateDBStart);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				Date dateDBMidnight = calendar.getTime();

				if (dateDBMidnight.compareTo(dateNewMidnight) == 0) {
					calendar.setTime(rDB.getDateTime());
					calendar.add(Calendar.HOUR_OF_DAY, rDB.getLength());
					Date dateDBEnd = calendar.getTime();
					Date dateNewStart = reservation.getDateTime();
					
					if (dateDBStart.before(dateNewStart)) {
						if (dateDBEnd.after(dateNewStart)) {
							unavailableTables.addAll(rDB.getTables());
							availableTables.removeAll(rDB.getTables());
						}
					} else if (dateNewStart.before(dateDBStart)) {
						calendar.setTime(dateNewStart);
						calendar.add(Calendar.HOUR_OF_DAY, reservation.getLength());
						Date dateNewEnd = calendar.getTime();
						
						if (dateNewEnd.after(dateDBStart)) {
							unavailableTables.addAll(rDB.getTables());
							availableTables.removeAll(rDB.getTables());
						}
					}
				}
			}
			
		}
		
		ArrayList<TableRestaurant> retVal = new ArrayList<TableRestaurant>();
		for(TableRestaurant tr : unavailableTables) {
			tr.setAvailable(false);
			retVal.add(tr);
		}
		for(TableRestaurant tr : availableTables) {
			tr.setAvailable(true);
			retVal.add(tr);
		}
		
		
		return retVal;
	}

	@Override
	public Restaurant getRestaurantForReservation(Long reservationId) {
		Long restaurantId = reservationRepository.findOne(reservationId).getRestaurantId();
		return restaurantRepository.findOne(restaurantId);
	}

	@Override
	public String inviteFriend(Long reservationId, Long friendId) {
		Reservation reservation = reservationRepository.findOne(reservationId);
		User sender = (User)httpSession.getAttribute("user");
		User friend = userRepository.findOne(friendId);
		sendInvitation(sender, friend, reservation);
		return "OK";
	}

	@Autowired
    private JavaMailSender javaMailSender;

	private void sendInvitation(User sender, User friend, Reservation reservation) {
		
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            //helper.setTo(friend.getEmail());
            helper.setTo("anja.stef@gmail.com");
            helper.setReplyTo("someone@localhost");
            helper.setFrom("someone@localhost");
            helper.setSubject("Pozivnica");
            helper.setText(sender.getName()+" "+sender.getSurname()+" Vas je pozvao u restoran!"
            			+ "Kliknite na link da saznate vise o tome."
            			+ "http://localhost:8080/#/invitation/"+reservation.getId()+"/"+friend.getId());
            //helper.setText("http://localhost:8080/#/activateAccount/" + emailAdress+ "/" + code );
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {}
        javaMailSender.send(mail);
    }

	@Override
	public String acceptInvitation(Long reservationId, Long friendId) {
		Reservation reservation = reservationRepository.findOne(reservationId);
		User user = userRepository.findOne(reservation.getUserId());
		User friend = userRepository.findOne(friendId);
		Restaurant restaurant =  restaurantRepository.findOne(reservation.getRestaurantId());
		Visit visit = new Visit(friend, reservation, restaurant.getName());
		visitRepository.save(visit);
		notifyAboutInvitation(user.getEmail(), friend, true);
		return "OK";
	}

	@Override
	public String declineInvitation(Long reservationId, Long friendId) {
		Reservation reservation = reservationRepository.findOne(reservationId);
		User user = userRepository.findOne(reservation.getUserId());
		User friend = userRepository.findOne(friendId);
		notifyAboutInvitation(user.getEmail(), friend, false);
		return "OK";
	}
	
	private void notifyAboutInvitation(String userEmail, User friend, boolean accepted){
		MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            //helper.setTo(friend.getEmail()); user.getEmail
            helper.setTo("anja.stef@gmail.com");
            helper.setReplyTo("someone@localhost");
            helper.setFrom("someone@localhost");
            helper.setSubject("Pozivnica");
            if(accepted){
            	helper.setText(friend.getName()+" "+friend.getSurname()+" je prihvatio Vas poziv!");
            } else {
            	helper.setText(friend.getName()+" "+friend.getSurname()+" nije prihvatio Vas poziv.");
            }
            
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {}
        javaMailSender.send(mail);
	}

	@Override
	public String cancelReservation(Long reservationId) {
		Reservation r = reservationRepository.findOne(reservationId);
		List<Visit> visits = visitRepository.findByReservationId(reservationId);
		for (Visit visit : visits) {
			List<OrderR> orders = orderRepository.findByVisit(visit);
			for (OrderR orderR : orders) {
				orderRepository.delete(orderR);
			}
			visitRepository.delete(visit);
		}
		reservationRepository.delete(r);
		return "OK";
	}
	
}
