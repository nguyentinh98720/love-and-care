package com.tinhnv.model.event.eventMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinhnv.model.event.EventForManage;

public class EventManageMapper implements RowMapper<EventForManage> {

	@Override
	public EventForManage mapRow(ResultSet result, int rowNum) throws SQLException {
		EventForManage event = new EventForManage();
		event.setId(result.getInt("MACT"));
		event.setTitle(result.getString("TIEUDECT"));
		event.setStartDate(result.getDate("NGAYBATDAU"));
		event.setEndDate(result.getDate("NGAYKETTHUC"));
		event.setPurpose(result.getBigDecimal("MUCTIEU"));
		event.setAchievement(result.getBigDecimal("UNGHO"));
		event.setStatus(result.getBoolean("TRANGTHAICT"));
		return event;
	}
	
}
