package com.tinhnv.model.donate.donateMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinhnv.model.donate.Donate;

public class DonateMapperForDashboard implements RowMapper<Donate> {

	@Override
	public Donate mapRow(ResultSet result, int rowNum) throws SQLException {
		Donate donate = new Donate();
		donate.setAccountName(result.getString("TENTK"));
		donate.setEventTitle(result.getString("TIEUDECT"));
		donate.setDate(result.getDate("NGAY"));
		donate.setMessage(result.getString("TINNHAN"));
		donate.setMoney(result.getBigDecimal("SOTIEN"));
		donate.setSecret(result.getBoolean("ANDANH"));
		donate.setEventId(result.getInt("MACT"));
		return donate;
	}

}
