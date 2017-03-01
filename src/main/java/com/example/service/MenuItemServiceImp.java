package com.example.service;

import javax.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.Manager;
import com.example.model.MenuItem;
import com.example.model.Restaurant;
import com.example.repository.MenuItemRepository;
import com.example.repository.RestaurantRepository;

@Service
public class MenuItemServiceImp implements MenuItemService {

	@Autowired
	private MenuItemRepository menuItemRepository;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private RestaurantRepository restaurantRepository;

	@Override
	public MenuItem createMenuItem(MenuItem newMenuItem) {
		Manager m = (Manager) httpSession.getAttribute("manager");
		Restaurant r = restaurantRepository.findOne(m.getRestaurantId());
		
		if (menuItemRepository.findOne(newMenuItem.getId()) == null) {
			MenuItem mi = menuItemRepository.save(newMenuItem);
			r.getMenu().add(mi);
			restaurantRepository.save(r);
			return mi;
		}
		return null;
	}
	
	@Override
	public MenuItem createDrinkItem(MenuItem newMenuItem) {
		Manager m = (Manager) httpSession.getAttribute("manager");
		Restaurant r = restaurantRepository.findOne(m.getRestaurantId());
		
		if (menuItemRepository.findOne(newMenuItem.getId()) == null) {
			MenuItem mi = menuItemRepository.save(newMenuItem);
			r.getDrinks().add(mi);
			restaurantRepository.save(r);
			return mi;
		}
		return null;
	}

	@Override
	public String removeDrinkItem(Long id) {
		MenuItem mi = menuItemRepository.findOne(id);
		Manager m = (Manager) httpSession.getAttribute("manager");
		Restaurant r = restaurantRepository.findOne(m.getRestaurantId());
		
		if (r.getDrinks().remove(mi)) {
			menuItemRepository.delete(mi);
			restaurantRepository.save(r);
			return "OK";
		}
		return "Error";
	}
	
	@Override
	public String removeMenuItem(Long id) {
		Manager m = (Manager) httpSession.getAttribute("manager");
		Restaurant r = restaurantRepository.findOne(m.getRestaurantId());
		MenuItem mi = menuItemRepository.findOne(id);
		
		if (r.getMenu().remove(mi)) {
			menuItemRepository.delete(mi);
			restaurantRepository.save(r);
			return "OK";
		}
		return "Error";
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
