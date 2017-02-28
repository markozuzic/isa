package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Manager;
import com.example.model.MenuItem;
import com.example.repository.MenuItemRepository;

@Service
public class MenuItemServiceImp implements MenuItemService {

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Override
	public String createMenuItem(MenuItem newMenuItem) {
		if (menuItemRepository.findOne(newMenuItem.getId()) == null) {
			menuItemRepository.save(newMenuItem);
			return "OK";
		}
		return "Id error";
	}

	

	@Override
	public List<MenuItem> getMenuItemsMeals(String type) {
		return menuItemRepository.findByType("meal");
	}

	@Override
	public List<MenuItem> getMenuItemsDrinks(String type) {
		return menuItemRepository.findByType("drinks");
	}




}
