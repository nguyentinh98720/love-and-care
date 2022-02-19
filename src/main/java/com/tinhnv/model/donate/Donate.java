package com.tinhnv.model.donate;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class Donate {
	private int donateId;
	private int eventId;
	private String eventTitle;
	private int accountId;
	private String accountName;
	private BigDecimal money;
	private Date date;
	private String message;
	private boolean secret;
	
	public Donate() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Donate(int eventId, int accountId, BigDecimal money, Date date, String message, boolean secret) {
		super();
		this.eventId = eventId;
		this.accountId = accountId;
		this.money = money;
		this.date = date;
		this.message = message;
		this.secret = secret;
	}

	public Donate(int donateId, String eventTitle, String accountName, BigDecimal money, Date date, String message,
			boolean secret) {
		super();
		this.donateId = donateId;
		this.eventTitle = eventTitle;
		this.accountName = accountName;
		this.money = money;
		this.date = date;
		this.message = message;
		this.secret = secret;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getDonateId() {
		return donateId;
	}

	public void setDonateId(int donateId) {
		this.donateId = donateId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSecret() {
		return secret;
	}

	public void setSecret(boolean secret) {
		this.secret = secret;
	}
}
