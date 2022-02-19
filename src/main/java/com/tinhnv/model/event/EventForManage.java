package com.tinhnv.model.event;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class EventForManage {
	private int id;
	private String title;
	private Date startDate;
	private Date endDate;
	private BigDecimal purpose;
	private BigDecimal achievement;
	private boolean status;
	
	public EventForManage() {
		super();
	}

	public EventForManage(int id, String title, Date startDate, Date endDate, BigDecimal purpose, BigDecimal achievement,
			boolean status) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.purpose = purpose;
		this.achievement = achievement;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getPurpose() {
		return purpose;
	}

	public void setPurpose(BigDecimal purpose) {
		this.purpose = purpose;
	}

	public BigDecimal getAchievement() {
		return achievement;
	}

	public void setAchievement(BigDecimal achievement) {
		this.achievement = achievement;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "{id: " + id + ", title: " + title + ", status: " + status + ", purpose: " + purpose + ", achievement: " + achievement + ", start date: " + startDate + ", end date: " + endDate + "}";
	}
}
