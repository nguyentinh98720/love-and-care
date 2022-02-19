package com.tinhnv.model.others.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinhnv.model.others.ImgSource;

public class ImgSourceMapper implements RowMapper<ImgSource>{

	@Override
	public ImgSource mapRow(ResultSet result, int rowNum) throws SQLException {
		ImgSource img = new ImgSource();
		img.setArr(result.getBytes("IMAGE"));
		img.setType(result.getString("IMGTYPE"));
		
		return img;
	}

}
