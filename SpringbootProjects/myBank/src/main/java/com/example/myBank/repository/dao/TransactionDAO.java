package com.example.myBank.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.myBank.repository.beans.TransactionInfo;

@Repository
public class TransactionDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	class TransactionInfoMapper implements RowMapper<TransactionInfo> {
		@Override
		public TransactionInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			TransactionInfo transactionInfo = new TransactionInfo();
			transactionInfo.setTRANSACTION_ID(rs.getLong("TRANSACTION_ID"));
			transactionInfo.setTRANSACTION_DEBUT_ACCOUNT(rs.getLong("TRANSACTION_DEBUT_ACCOUNT"));
			transactionInfo.setTRANSACTION_CREDIT_ACCOUNT(rs.getLong("TRANSACTION_CREDIT_ACCOUNT"));
			transactionInfo.setTRANSACTION_AMOUNT(rs.getLong("TRANSACTION_AMOUNT"));
			transactionInfo.setTRANSACTION_DATE(rs.getDate("TRANSACTION_DATE"));
			return transactionInfo;
		}
	}

	public TransactionInfo findById(long id) {
		try {
			return jdbcTemplate.queryForObject("select * from TRANSACTION_HISTORY where TRANSACTION_ID=" + id,
					new TransactionInfoMapper());
		} catch (Exception ex) {
			return null;
		}
	}

	

	public List<TransactionInfo> findAllTransactionForSender(long senderId) {
		return jdbcTemplate.query("select * from TRANSACTION_HISTORY where TRANSACTION_DEBUT_ACCOUNT=" + senderId,
				new TransactionInfoMapper());
	}

	public List<TransactionInfo> findAllTransactionForReciever(long recieverId) {
		return jdbcTemplate.query("select * from TRANSACTION_HISTORY where TRANSACTION_CREDIT_ACCOUNT=" + recieverId,
				new TransactionInfoMapper());
	}

	public List<TransactionInfo> findAll() {
		return jdbcTemplate.query("select * from TRANSACTION_HISTORY", new TransactionInfoMapper());
	}

	public int deleteById(long id) {
		return jdbcTemplate.update("delete from TRANSACTION_HISTORY where TRANSACTION_ID=?", new Object[] { id });
	}

	public int insert(TransactionInfo transactionInfo) {
		return jdbcTemplate.update(
				"insert into TRANSACTION_HISTORY (TRANSACTION_ID, TRANSACTION_DEBUT_ACCOUNT, TRANSACTION_CREDIT_ACCOUNT, TRANSACTION_DATE,TRANSACTION_AMOUNT) "
						+ "values(?,  ?, ?, ?,?)",
				new Object[] { transactionInfo.getTRANSACTION_ID(), transactionInfo.getTRANSACTION_DEBUT_ACCOUNT(),
						transactionInfo.getTRANSACTION_CREDIT_ACCOUNT(), transactionInfo.getTRANSACTION_DATE(),transactionInfo.getTRANSACTION_AMOUNT() });
	}

}
