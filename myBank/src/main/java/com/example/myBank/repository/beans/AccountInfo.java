package com.example.myBank.repository.beans;

import java.time.Instant;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class AccountInfo {
	
	public long getACCOUNT_NUMBER() {
		return ACCOUNT_NUMBER;
	}

	public void setACCOUNT_NUMBER(long aCCOUNT_NUMBER) {
		ACCOUNT_NUMBER = aCCOUNT_NUMBER;
	}

	public long getACCOUNT_BALANCE() {
		return ACCOUNT_BALANCE;
	}

	public void setACCOUNT_BALANCE(long aCCOUNT_BALANCE) {
		ACCOUNT_BALANCE = aCCOUNT_BALANCE;
	}

	public AccountInfo(long aCCOUNT_NUMBER, long aCCOUNT_BALANCE) {
		super();
		ACCOUNT_NUMBER = aCCOUNT_NUMBER;
		ACCOUNT_BALANCE = aCCOUNT_BALANCE;
	}

	public AccountInfo() {
		super();
	}

	@Id
	@GeneratedValue
	private long ACCOUNT_NUMBER=Instant.now().toEpochMilli();;
	
	private long ACCOUNT_BALANCE=0;
}
