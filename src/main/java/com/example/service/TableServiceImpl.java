package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.TableRestaurant;
import com.example.repository.TableRepository;

@Service
public class TableServiceImpl implements TableService {
	
	@Autowired
	private TableRepository tableRepository;
	
	@Override
	public String createTable(TableRestaurant newTable) {
		if (tableRepository.findOne(newTable.getTableNumber()) == null) {
			tableRepository.save(newTable);
			return "OK";
		}
		return "Id error";
	}

}
