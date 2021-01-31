package com.example.myBank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myBank.repository.beans.CustomerInfo;
import com.example.myBank.services.CustomerInfoService;
import com.example.myBank.services.MyBankService;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerInfoService customerInfoService;
	
	
	@GetMapping("/insert")
	public CustomerInfo insert(@RequestParam String name, @RequestParam String email, @RequestParam int phoneNo, @RequestParam String password){

		return customerInfoService.addCustomer(name,email,phoneNo,password);	
	}

	@GetMapping("/insertDouble")
	public List<CustomerInfo> insertDouble(@RequestParam String name, @RequestParam String email, @RequestParam int phoneNo, @RequestParam String password) throws Exception{
		return customerInfoService.addJointCustomer(name,email,phoneNo,password);  	
	}

	@GetMapping("/users")
	public List<CustomerInfo> getAllUsers(){
		return customerInfoService.getAllUsers();
	}
	
	
	@GetMapping("/users/{id}")
	public CustomerInfo getAllUsers(@PathVariable long id){
		return customerInfoService.getUser(id);
	}
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable long id){
		return customerInfoService.deleteUser(id);
	}
	
	@GetMapping("/show")
	public String getString(){
		return "hello";
	}
	
}
