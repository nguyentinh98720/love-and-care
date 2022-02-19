package com.tinhnv.model.others.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinhnv.model.others.ImgEvent;

public class ImgEventMapper implements RowMapper<ImgEvent> {

	@Override
	public ImgEvent mapRow(ResultSet result, int rowNum) throws SQLException {
		ImgEvent img = new ImgEvent();
		img.setId(result.getInt("MAANH"));
		img.setEventId(result.getInt("MACT"));
		img.setArray(result.getBytes("HINHANH"));
		img.setType(result.getString("TYPE"));
		return img;
	}

}
