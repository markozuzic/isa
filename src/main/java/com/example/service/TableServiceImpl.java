package com.example.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Manager;
import com.example.model.Restaurant;
import com.example.model.TableRestaurant;
import com.example.repository.RestaurantRepository;
import com.example.repository.TableRepository;

@Service
public class TableServiceImpl implements TableService {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private TableRepository tableRepository;
	
	@Override
	public String createTable(TableRestaurant newTable) {
		Manager m = (Manager) httpSession.getAttribute("manager");
		Restaurant r = restaurantRepository.findOne(m.getRestaurantId());
		newTable.setRestaurant(r.getId());
		
		if (tableRepository.findOne(newTable.getTableNumber()) == null) {
			tableRepository.save(newTable);
			return "OK";
		}
		return "Id error";
	}

	@Override
	public List<TableRestaurant> findByRestaurant(long id) {
		return tableRepository.findByRestaurant(id);
	}

	@Override
	public List<TableRestaurant> findByRestaurant() {
		Manager m = (Manager) httpSession.getAttribute("manager");
		Restaurant r = restaurantRepository.findOne(m.getId());
		
		return tableRepository.findByRestaurant(r.getId());
	}

	@Override
	public String deleteTable(long id) {
		TableRestaurant tr = tableRepository.findOne(id);
		
		if (true) { //provera rezervisanosti
			tableRepository.delete(tr);
			return "OK";
		}
		return null;
	}

}
