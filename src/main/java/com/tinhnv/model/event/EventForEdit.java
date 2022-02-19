package com.tinhnv.model.event;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class EventForEdit extends EventForManage {

	private String detail;
	
	public EventForEdit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventForEdit(int id, String title, Date startDate, Date endDate, BigDecimal purpose, BigDecimal achievement,
			boolean status, String detail) {
		super(id, title, startDate, endDate, purpose, achievement, status);
		this.detail = detail;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
