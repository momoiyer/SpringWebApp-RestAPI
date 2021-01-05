package com.example.SpringRest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.SpringRest.model.Items;
import com.example.SpringRest.service.ItemService;


@Controller
@ResponseBody

@RequestMapping("/api")
public class ItemsController {

	@Autowired
	ItemService its;
	
	@GetMapping(value= {"/items"})
	@Cacheable(value="allItems")
	public List<Items> getAll()
	{
		List<Items> items = its.getAll();
		System.out.println("GETALL : Items retrieved " + items.size());
		return items;
	}

	@GetMapping(value= {"/items/{id}"})
	@Cacheable(value="items",key="#id")
	public Items get(@PathVariable("id") Integer id)
	{
		System.out.println("Getting item ID " + id);
		Optional<Items> items = its.get(id);		
		return items.get();
	}
	
	@PostMapping("/items")
	@Caching(
		put= {@CachePut(value="items",key="#result.itemId")},
		evict= {@CacheEvict(value="allItems",allEntries=true)}
	)
	public Items add(@RequestBody Items item)
	{
		System.out.println("Inserting Item!" );
		return its.update(item);
	}
	
	@PutMapping("/items")
	@Caching(
			put= {@CachePut(value="items",key="#result.itemId")},
			evict= {@CacheEvict(value="allItems",allEntries=true)}
	)
	public Items edit(@RequestBody Items item)
	{
		System.out.println("Updating Item!" );
		return its.update(item);
	}
	
	@DeleteMapping("/items")
	@Caching(
			evict= {
					@CacheEvict(value="items",key="#id"),
					@CacheEvict(value="allItems",allEntries=true)}
	)
	public String delete(@RequestParam(value="id") Integer id)
	{
		Optional<Items> i = its.get(id);
		its.delete(i.get());
		return "Item id " + id + " deleted successfully!";
	}
	
	//Delete all items
	@DeleteMapping("/deleteAll")
	@Caching(
			evict= {
					@CacheEvict(value="items",allEntries=true),
					@CacheEvict(value="allItems",allEntries=true)}
	)
	public String deleteAll()
	{
		System.out.println("Deleting all items!");
		its.deleteAll();
		return "All items deleted successfully";
	}
	
	//Clear cache
	@GetMapping("/clearCache")
	@Caching(
			evict= {
					@CacheEvict(value="items",allEntries=true),
					@CacheEvict(value="allItems",allEntries=true)}
	)
	public String clearCache() {
		return "Cleared cache successfully";
	}
}
