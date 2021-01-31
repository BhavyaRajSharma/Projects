package com.example.myBank.services;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.myBank.repository.beans.CustomerInfo;
import com.example.myBank.repository.dao.CustomerInfoDAO;

@Service
public class CustomerInfoService {

	@Autowired
	CustomerInfoDAO customerInfoDAO;
	
	
	
	public CustomerInfo addCustomer(String name, String email, int phoneNo, String password) {
		CustomerInfo customerInfo=new CustomerInfo(name,email,phoneNo,password);
		customerInfoDAO.insert(customerInfo);
		return customerInfo;
	}
	

	public List<CustomerInfo> addJointCustomer(String name, String email, int phoneNo, String password) throws Exception {
		List<CustomerInfo> result=new ArrayList<>();
		CustomerInfo customerInfo1=new CustomerInfo(name,email,phoneNo,password);
		customerInfoDAO.insert(customerInfo1);
		result.add(customerInfo1);
		if(true)
		throw new Exception("");
		CustomerInfo customerInfo2=new CustomerInfo(name,email,phoneNo,password);
		customerInfoDAO.insert(customerInfo2);
		result.add(customerInfo2);
		return result;
	}

	public List<CustomerInfo> getAllUsers() {
		return customerInfoDAO.findAll();
	}
	
	public CustomerInfo getUser(long id) {
		return customerInfoDAO.findById(id);
	}
	
	public String deleteUser(long id) {
		customerInfoDAO.deleteById(id);
		return "Deleted!";
	}
	
}
