package com.example.service;

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
	


}
