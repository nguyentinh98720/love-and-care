package com.tinhnv.model.account.accountMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinhnv.model.account.AccountForManage;

public class AccountMapperNowImg implements RowMapper<AccountForManage>{

	@Override
	public AccountForManage mapRow(ResultSet result, int rowNum) throws SQLException {
		AccountForManage account = new AccountForManage();
		account.setId(result.getInt("MATK"));
		account.setEmail(result.getString("EMAIL"));
		account.setPassword(result.getString("MATKHAU"));
		account.setFirstName(result.getString("TEN"));
		account.setLastName(result.getString("HO"));
		account.setPhoneNumber(result.getString("SODT"));
		account.setAddress(result.getString("DIACHI"));
		account.setRole(result.getString("ROLE"));
		account.setStatus(result.getBoolean("TRANGTHAI"));
		account.setSaltPass(result.getString("KHOAMK"));
		return account;
	}
}
