package com.tinhnv.model.others;

import java.util.Date;

public class CurrencyRateResponse {

	private boolean success;
	private long timestamp;
	private String base;
	private Date date;
	private Rate rates;

	public CurrencyRateResponse(boolean success, long timestamp, String base, Date date, Rate rates) {
		super();
		this.success = success;
		this.timestamp = timestamp;
		this.base = base;
		this.date = date;
		this.rates = rates;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Rate getRates() {
		return rates;
	}

	public void setRates(Rate rates) {
		this.rates = rates;
	}

}
