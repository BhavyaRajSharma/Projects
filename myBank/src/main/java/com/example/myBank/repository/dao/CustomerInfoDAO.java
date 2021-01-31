package com.example.myBank.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.myBank.repository.beans.CustomerInfo;

@Repository
public class CustomerInfoDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	class CustomerInfoMapper implements RowMapper<CustomerInfo>{
		@Override
		public CustomerInfo mapRow(ResultSet rs,int rowNum) throws SQLException {
			CustomerInfo customerInfo = new CustomerInfo();
			customerInfo.setId(rs.getLong("CUSTOMER_ID"));
			customerInfo.setName(rs.getString("CUSTOMER_NAME"));
			customerInfo.setEmail(rs.getString("CUSTOMER_EMAIL"));
			customerInfo.setPhoneNo(rs.getInt("CUSTOMER_PHONENO"));
			customerInfo.setPassword(rs.getString("CUSTOMER_PASSWORD"));
			return customerInfo;
		}
	}
	
	public List<CustomerInfo> findAll(){
		return jdbcTemplate.query("select * from CUSTOMER_INFO",new CustomerInfoMapper());
	}
	public CustomerInfo findById(long id) {
		return jdbcTemplate.queryForObject("select * from CUSTOMER_INFO where CUSTOMER_ID="+id, new CustomerInfoMapper());
	}
	

	public int deleteById(long id) {
		return jdbcTemplate.update("delete from CUSTOMER_INFO where CUSTOMER_ID=?", new Object[] { id });
	}

	public int insert(CustomerInfo customerInfo) {
		return jdbcTemplate.update("insert into CUSTOMER_INFO (CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_PHONENO, CUSTOMER_PASSWORD) " + "values(?,  ?, ?, ?,?)",
				new Object[] { customerInfo.getId(), customerInfo.getName(), customerInfo.getEmail(),
						customerInfo.getPhoneNo(), customerInfo.getPassword()});
	}

	public int update(CustomerInfo customerInfo) {
		return jdbcTemplate.update("update CUSTOMER_INFO " + " set CUSTOMER_NAME = ?, CUSTOMER_EMAIL = ?, CUSTOMER_PHONENO = ?, CUSTOMER_PASSWORD=? " + " where CUSTOMER_ID = ?",
				new Object[] { customerInfo.getName(), customerInfo.getEmail(), customerInfo.getPhoneNo(),customerInfo.getPassword(),
				customerInfo.getId() });
	}
}
