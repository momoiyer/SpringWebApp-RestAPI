package com.example.demoMVCApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demoMVCApp.model.Items;
import com.example.demoMVCApp.service.ItemService;

@Controller
@RequestMapping("/inventory")
public class ItemController {
	
	@Autowired
	ItemService its;
	
	@RequestMapping(value = {"/all","/"})
	public String getAll(Model m)
	{
		List<Items> items = its.getAll();
		m.addAttribute("items",items);
		m.addAttribute("heading","Inventory List");
		return "itemView";
	}


	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model m)
	{
		Items items = its.get(id);
		m.addAttribute("item",items);
		m.addAttribute("heading","Edit Inventory List");
		return "editView";
	}
	
	@RequestMapping("/update")
	public String edit(Model m, Items item)
	{
		its.update(item);
		List<Items> items = its.getAll();
		m.addAttribute("items",items);
		m.addAttribute("heading","Inventory List");
		return "redirect:/inventory/all";
	}
	
	@RequestMapping("/add")
	public String add(Model m)
	{
		Items i=new Items();		
		m.addAttribute("item",i);
		m.addAttribute("heading","Add New Item");
		return "editView";
	}
	

	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, Model m)
	{
		Items item = its.get(id);
		its.delete(item);
		List<Items> items = its.getAll();
		m.addAttribute("items",items);
		m.addAttribute("heading","Inventory List");
		return "redirect:/inventory/all";
	}
}
