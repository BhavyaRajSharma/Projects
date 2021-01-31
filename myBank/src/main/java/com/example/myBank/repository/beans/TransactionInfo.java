package com.example.myBank.repository.beans;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TransactionInfo {
	
	public TransactionInfo(long tRANSACTION_DEBUT_ACCOUNT, long tRANSACTION_CREDIT_ACCOUNT, long tRANSACTION_AMOUNT,
			Date tRANSACTION_DATE) {
		super();
		TRANSACTION_DEBUT_ACCOUNT = tRANSACTION_DEBUT_ACCOUNT;
		TRANSACTION_CREDIT_ACCOUNT = tRANSACTION_CREDIT_ACCOUNT;
		TRANSACTION_AMOUNT = tRANSACTION_AMOUNT;
		TRANSACTION_DATE = tRANSACTION_DATE;
	}
	public TransactionInfo(long tRANSACTION_ID, long tRANSACTION_DEBUT_ACCOUNT, long tRANSACTION_CREDIT_ACCOUNT,
			long tRANSACTION_AMOUNT, Date tRANSACTION_DATE) {
		super();
		TRANSACTION_ID = tRANSACTION_ID;
		TRANSACTION_DEBUT_ACCOUNT = tRANSACTION_DEBUT_ACCOUNT;
		TRANSACTION_CREDIT_ACCOUNT = tRANSACTION_CREDIT_ACCOUNT;
		TRANSACTION_AMOUNT = tRANSACTION_AMOUNT;
		TRANSACTION_DATE = tRANSACTION_DATE;
	}
	public TransactionInfo() {
		// TODO Auto-generated constructor stub
	}
	public long getTRANSACTION_ID() {
		return TRANSACTION_ID;
	}
	public void setTRANSACTION_ID(long tRANSACTION_ID) {
		TRANSACTION_ID = tRANSACTION_ID;
	}
	public long getTRANSACTION_DEBUT_ACCOUNT() {
		return TRANSACTION_DEBUT_ACCOUNT;
	}
	public void setTRANSACTION_DEBUT_ACCOUNT(long tRANSACTION_DEBUT_ACCOUNT) {
		TRANSACTION_DEBUT_ACCOUNT = tRANSACTION_DEBUT_ACCOUNT;
	}
	public long getTRANSACTION_CREDIT_ACCOUNT() {
		return TRANSACTION_CREDIT_ACCOUNT;
	}
	public void setTRANSACTION_CREDIT_ACCOUNT(long tRANSACTION_CREDIT_ACCOUNT) {
		TRANSACTION_CREDIT_ACCOUNT = tRANSACTION_CREDIT_ACCOUNT;
	}
	public Date getTRANSACTION_DATE() {
		return TRANSACTION_DATE;
	}
	public void setTRANSACTION_DATE(Date tRANSACTION_DATE) {
		TRANSACTION_DATE = tRANSACTION_DATE;
	}
	public long getTRANSACTION_AMOUNT() {
		return TRANSACTION_AMOUNT;
	}
	public void setTRANSACTION_AMOUNT(long tRANSACTION_AMOUNT) {
		TRANSACTION_AMOUNT = tRANSACTION_AMOUNT;
	}
	@Id
	@GeneratedValue
	private long TRANSACTION_ID=Instant.now().toEpochMilli();
	private long TRANSACTION_DEBUT_ACCOUNT;
	private long TRANSACTION_CREDIT_ACCOUNT;
	private long TRANSACTION_AMOUNT;
	private Date TRANSACTION_DATE;
}
