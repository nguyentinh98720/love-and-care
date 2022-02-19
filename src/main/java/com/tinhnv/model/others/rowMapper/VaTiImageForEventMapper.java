package com.tinhnv.model.others.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinhnv.model.others.VaTiImage;

public class VaTiImageForEventMapper implements RowMapper<VaTiImage> {

	@Override
	public VaTiImage mapRow(ResultSet rs, int rowNum) throws SQLException {
		VaTiImage image = new VaTiImage();
		image.setArr(rs.getBytes("HINHANH"));
		image.setType(rs.getString("TYPE"));
		return image;
	}

}
