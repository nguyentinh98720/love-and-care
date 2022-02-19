package com.tinhnv.model.donate;

import java.math.BigDecimal;

import com.tinhnv.model.others.CurrencyExchangeRate;

public class DonateForPaypal {

	private int eventId;
	private int accountId;
	private BigDecimal money;
	private String message;
	private boolean secret;
	private CurrencyExchangeRate rate;

	public DonateForPaypal() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public DonateForPaypal(int eventId, int accountId, BigDecimal money, String message, boolean secret,
			CurrencyExchangeRate rate) {
		super();
		this.eventId = eventId;
		this.accountId = accountId;
		this.money = money;
		this.message = message;
		this.secret = secret;
		this.rate = rate;
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

	public CurrencyExchangeRate getRate() {
		return rate;
	}

	public void setRate(CurrencyExchangeRate rate) {
		this.rate = rate;
	}
}
