package com.example.demoMVCApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demoMVCApp.model.Items;

@Service
public class ItemService {
	
	@Autowired
	RestTemplate rst;
	
	public Items get(int itemId)
	{
		Items item = rst.getForObject("http://localhost:8080/api/items/"+itemId, Items.class);
		System.out.println("Item name is " + item.getItemName());
		return item;
	}
	
	public List<Items> getAll(){
		
		ResponseEntity<List<Items>> response = rst.exchange("http://localhost:8080/api/items", HttpMethod.GET,null,new ParameterizedTypeReference<List<Items>>() {});
		List<Items> items = response.getBody();
		System.out.println("Item size is " + items.size());
		return items;
	}
	
	public Items update(Items item) {
		return rst.postForObject("http://localhost:8080/api/items", item, Items.class);
	}
	
	public void delete(Items item) {
		rst.delete("http://localhost:8080/api/items?id="+item.getItemId());
	}

}
	