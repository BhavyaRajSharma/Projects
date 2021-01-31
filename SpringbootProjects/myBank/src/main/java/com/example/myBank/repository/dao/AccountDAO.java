package com.example.myBank.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.myBank.repository.beans.AccountInfo;

@Repository
public class AccountDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	class AccountInfoMapper implements RowMapper<AccountInfo>{
		@Override
		public AccountInfo mapRow(ResultSet rs,int rowNum) throws SQLException {
			AccountInfo accountInfo = new AccountInfo();
			accountInfo.setACCOUNT_NUMBER(rs.getLong("ACCOUNT_ID"));
			accountInfo.setACCOUNT_BALANCE(rs.getLong("ACCOUNT_BALANCE"));
			return accountInfo;
		}
	}
	
	public int insert(AccountInfo accountInfo) {
		return jdbcTemplate.update("insert into ACCOUNT_INFO (ACCOUNT_ID, ACCOUNT_BALANCE) " + "values(?,?)",
				new Object[] { accountInfo.getACCOUNT_NUMBER(), accountInfo.getACCOUNT_BALANCE()});
	}
	
	public AccountInfo findById(long id) {
		return jdbcTemplate.queryForObject("select * from ACCOUNT_INFO where ACCOUNT_ID="+id, new AccountInfoMapper());
	}
	
	public int update(AccountInfo accountInfo) {
		return jdbcTemplate.update("update ACCOUNT_INFO " + " set ACCOUNT_BALANCE = ?" + " where ACCOUNT_ID = ?",
				new Object[] { accountInfo.getACCOUNT_BALANCE(), accountInfo.getACCOUNT_NUMBER()});
	}


}
