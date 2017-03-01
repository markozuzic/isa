package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Demand;
import com.example.model.DemandItem;
import com.example.model.Manager;
import com.example.repository.DemandItemRepository;
import com.example.repository.DemandRepository;

@Service
public class DemandServiceImp implements DemandService {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private DemandRepository demandRepository;
	
	@Autowired
	private DemandItemRepository demandItemRepository;

	@Override
	public List<Demand> getAllDemands() {
		Manager m = (Manager) httpSession.getAttribute("manager");
		
		return demandRepository.findByRestaurantId(m.getRestaurantId());
	}

	@Override
	public String create(String idList, Demand d) {
		
		String[] tokens = idList.split(",");
		long restaurantId = ((Manager) httpSession.getAttribute("manager")).getRestaurantId();
		Demand demand = new Demand(d.getBeginDate(), d.getEndDate(), restaurantId);
		demandRepository.save(demand);
			
		ArrayList<DemandItem> diList = new ArrayList<DemandItem>();
		for (String name : tokens) {
			DemandItem di = demandItemRepository.findByName(name).get(0);

			diList.add(di);
		}
		
		demand.setListOfItems(diList);
		demandRepository.save(demand);
		return "OK";
	}

}
