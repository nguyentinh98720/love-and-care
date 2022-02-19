package com.tinhnv.model.donate.donateMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinhnv.model.donate.TinyInfoDonation;

public class TinyInfoDonationMapper implements RowMapper<TinyInfoDonation> {

	@Override
	public TinyInfoDonation mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new TinyInfoDonation(rs.getInt("MATK"), rs.getBigDecimal("SOTIEN"));
	}

}
