package com.example.myBank.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myBank.repository.beans.AccountInfo;
import com.example.myBank.repository.beans.CustomerInfo;
import com.example.myBank.repository.beans.TransactionInfo;
import com.example.myBank.services.MyBankService;

@RestController
public class MyBankController {
	@Autowired
	MyBankService myBankService;
	
	@GetMapping("/add")
	public List<Object> addNewMember(@RequestParam String name, @RequestParam String email, @RequestParam int phoneNo, @RequestParam String password){

		return myBankService.creatNewAccount(name,email,phoneNo,password);	
	}
	
	@GetMapping("/transcation/{transactionId}")
	public TransactionInfo getTransactionInfo(@PathVariable long transactionId) {
		return myBankService.getTransaction(transactionId);
	}
	@GetMapping("/customer/{customerId}")
	public CustomerInfo getCustomerInfo(@PathVariable long customerId) {
		return myBankService.getCustomerInfo(customerId);
	}
	@GetMapping("/account/{accountId}")
	public AccountInfo getAccountInfo(@PathVariable long accountId) {
		return myBankService.getAccountInfo(accountId);
	}
	
	@GetMapping("/customerDetails/{accountId}")
	public List<Object> showCustomerDetails(@PathVariable long accountId){
		return myBankService.getInfo(accountId);
	}
	
	@GetMapping("/paymentGateway")
	public List<Object> payAmount(@RequestParam long senderAccountId,@RequestParam long recieverAccountId,@RequestParam long amount){
		List<Object> result=new ArrayList<Object>();
		result.add(myBankService.doTransaction(senderAccountId, recieverAccountId, amount));
		result.add(myBankService.getAccountInfo(senderAccountId));
		return result;
	}
}
