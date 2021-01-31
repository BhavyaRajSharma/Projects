package com.example.myBank.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.myBank.repository.beans.AccountInfo;
import com.example.myBank.repository.beans.CustomerInfo;
import com.example.myBank.repository.beans.TransactionInfo;

@Service
public class MyBankService {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	CustomerInfoService customerInfoService;
	
	@Autowired
	TransactionService transactionService;
	
	public List<Object> creatNewAccount(String name, String email, int phoneNo, String password) {
		List<Object> result= new ArrayList<>();
		CustomerInfo customerInfo= customerInfoService.addCustomer(name, email, phoneNo, password);
		AccountInfo accountInfo = accountService.setUpPaymentAccout(customerInfo.getId());
		TransactionInfo transactionInfo=transactionService.addTransaction(0, customerInfo.getId(), 500, new Date());
		result.add(customerInfo);
		result.add(accountInfo);
		result.add(transactionInfo);
		return result;
	}
	
	public TransactionInfo getTransaction(long transactionId) {
		TransactionInfo transactionInfo=transactionService.getTransaction(transactionId);
		return transactionInfo!=null?transactionInfo:new TransactionInfo();
	}
	
	public CustomerInfo getCustomerInfo(long customerId) {
		return customerInfoService.getUser(customerId);
	}
	
	public AccountInfo getAccountInfo(long accountId) {
		return accountService.getAccountInfo(accountId);
	}

	@Transactional
	public TransactionInfo doTransaction(long senderAccountId, long recieverAccountId, long amount) {
		TransactionInfo transactionInfo=new TransactionInfo();
		AccountInfo accountInfo; 
		try {
			accountInfo = accountService.getAccountInfo(senderAccountId);
			if(accountInfo.getACCOUNT_BALANCE()<amount) {
				return transactionInfo;
			}
			accountService.updateBalance(senderAccountId, accountInfo.getACCOUNT_BALANCE()-amount);
			accountInfo=accountService.getAccountInfo(recieverAccountId);
			accountService.updateBalance(recieverAccountId, accountInfo.getACCOUNT_BALANCE()+amount);
			transactionInfo=transactionService.addTransaction(senderAccountId, recieverAccountId, amount, new Date());
		}
		finally{
			return transactionInfo;
		}
		
	}
	
	public List<Object> getInfo(long id){
		List<Object> result=new ArrayList<Object>();
		CustomerInfo customerInfo=customerInfoService.getUser(id);
		AccountInfo accountInfo=accountService.getAccountInfo(id);
		List<TransactionInfo> transactionInfos=transactionService.getAllTransactionForPerson(id);
		result.add(customerInfo);
		result.add(accountInfo);
		result.add(transactionInfos);
		return result;
	}
}
