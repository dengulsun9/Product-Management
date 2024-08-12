package com.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.product.entity.Product;
import com.product.repository.ProductRepo;

@Controller
public class ProductController {

	@Autowired
	private ProductRepo repo;
	
	
	@GetMapping("/")
	public String LoadForm()
	{
		
		return "Home";
	}
	
	@GetMapping("/form")
	public String LoadForm(Model model)
	{
		model.addAttribute("product",new Product());
		return "index";
	}
	
	
	@PostMapping("/product")
	public String saveProduct(@ModelAttribute Product product,Model model)
	{
		
		repo.save(product);
		
		if(product.getProductId()!=null)
		{
			model.addAttribute("msg","Product Saved");
			
		}
		
		return "index";
	}
	
	
	@GetMapping("/products")
	public String getAllProducts(Model model)
	{
		List<Product> all = repo.findAll();
		
		model.addAttribute("products", all);
		
		return "data";
		
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam Long pid,Model model)
	{
		Optional<Product> byId = repo.findById(pid);
		if(byId.isPresent())
		{
			Product product = byId.get();
			model.addAttribute("product", product);
		}
		
		return "index";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam Long pid,Model model)
	{
		repo.deleteById(pid);
		model.addAttribute("msg", "Product Deleted Successfully");
		model.addAttribute("products", repo.findAll());
		
		return "data";
		
	}
	
	
	
	
}
