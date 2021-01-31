package com.example.myBank.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myBank.repository.beans.TransactionInfo;
import com.example.myBank.repository.dao.TransactionDAO;

@Service
public class TransactionService {
	@Autowired
	TransactionDAO transactionDAO;
	
	
	
	public TransactionInfo addTransaction(long debitAccount, long creditAccount, long amount, Date transactionDate) {
		TransactionInfo transactionInfo=new TransactionInfo(debitAccount,creditAccount,amount,transactionDate);
		transactionDAO.insert(transactionInfo);
		return transactionInfo;
	}
	public List<TransactionInfo> getAllTransactions() {
		return transactionDAO.findAll();
	}
	
	public TransactionInfo getTransaction(long transactionId) {
		return transactionDAO.findById(transactionId);
	}
	
	public List<TransactionInfo> getAllTransactionForPerson(long accountId) {
		List<TransactionInfo> result =new ArrayList<TransactionInfo>();
		result.addAll(transactionDAO.findAllTransactionForReciever(accountId));
		result.addAll(transactionDAO.findAllTransactionForSender(accountId));
		Collections.sort(result, new Comparator<TransactionInfo>() {
			  public int compare(TransactionInfo o1, TransactionInfo o2) {
			      return o1.getTRANSACTION_DATE().compareTo(o2.getTRANSACTION_DATE());
			  }
			});
		return result;
	}
}
