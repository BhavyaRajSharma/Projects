package com.example.myBank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myBank.repository.beans.AccountInfo;
import com.example.myBank.repository.dao.AccountDAO;

@Service
public class AccountService {
	
	@Autowired
	AccountDAO accountDAO;
	
	public AccountInfo setUpPaymentAccout(long id) {
		AccountInfo accountInfo=new AccountInfo(id, 500);
		accountDAO.insert(accountInfo);
		return accountInfo;
	}
	
	public AccountInfo updateBalance(long id, long balance) {
		AccountInfo accountInfo=new AccountInfo(id,balance);
		accountDAO.update(accountInfo);
		return accountInfo;
	}
	
	public AccountInfo getAccountInfo(long id) {
		return accountDAO.findById(id);
	}
}
