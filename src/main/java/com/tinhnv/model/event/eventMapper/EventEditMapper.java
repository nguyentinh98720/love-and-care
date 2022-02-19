package com.tinhnv.model.event.eventMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.tinhnv.model.event.EventForEdit;

public class EventEditMapper implements RowMapper<EventForEdit> {

	@Override
	public EventForEdit mapRow(ResultSet result, int rowNum) throws SQLException {
		EventForEdit event = new EventForEdit();
		event.setId(result.getInt("MACT"));
		event.setTitle(result.getString("TIEUDECT").trim());
		event.setStartDate(result.getDate("NGAYBATDAU"));
		event.setEndDate(result.getDate("NGAYKETTHUC"));
		event.setPurpose(result.getBigDecimal("MUCTIEU"));
		event.setAchievement(result.getBigDecimal("UNGHO"));
		event.setStatus(result.getBoolean("TRANGTHAICT"));
		event.setDetail(result.getString("NOIDUNGCT").trim());
		return event;
	}

}
