package com.tinhnv.model.others;

public class CurrencyExchangeRate {

	private String rate;
	private String lastTimeUpdate;

	public CurrencyExchangeRate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CurrencyExchangeRate(String rate, String lastTimeUpdate) {
		super();
		this.rate = rate;
		this.lastTimeUpdate = lastTimeUpdate;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getLastTimeUpdate() {
		return lastTimeUpdate;
	}

	public void setLastTimeUpdate(String lastTimeUpdate) {
		this.lastTimeUpdate = lastTimeUpdate;
	}
}
