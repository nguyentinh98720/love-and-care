package com.tinhnv.model.donate.donateMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinhnv.model.donate.Donate;

public class DonateHistoryMapper implements RowMapper<Donate> {

	@Override
	public Donate mapRow(ResultSet result, int rowNum) throws SQLException {
		Donate donate = new Donate();
		donate.setDonateId(result.getInt("MAHD"));
		donate.setEventId(result.getInt("MACT"));
		donate.setEventTitle(result.getString("TIEUDECT"));
		donate.setMoney(result.getBigDecimal("SOTIEN"));
		donate.setDate(result.getDate("NGAY"));
		donate.setMessage(result.getString("TINNHAN"));
		donate.setSecret(result.getBoolean("ANDANH"));
		
		return donate;
	}

}
