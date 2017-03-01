package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.DemandItem;
import com.example.repository.DemandItemRepository;

@Service
public class DemandItemServiceImp implements DemandItemService {
	
	@Autowired
	private DemandItemRepository demandItemRepository;

	@Override
	public DemandItem create(DemandItem newDemandItem) {
		List<DemandItem> demands = demandItemRepository.findByName(newDemandItem.getName());
		if (demands.isEmpty()) {
			return demandItemRepository.save(newDemandItem);
		}
		return newDemandItem;
	}

	

}
