package com.example.SpringRest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringRest.model.Items;
import com.example.SpringRest.repository.ItemRepositoryInterface;


@Service
public class ItemService {
	
	@Autowired
	ItemRepositoryInterface itr;
	
	public Optional<Items> get(int itemId)
	{
		return itr.findById(itemId);
	}
	
	public List<Items> getAll(){
				
		return (List<Items>) itr.findAll();
	}
	
	public Items update(Items item) {
		return itr.save(item);
	}
	
	public void delete(Items item) {
		itr.delete(item);
	}
	
	public void deleteAll() {
		itr.deleteAll();
	}

}
