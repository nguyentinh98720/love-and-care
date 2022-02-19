package com.tinhnv.model.donate;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class TinyInfoDonation {

	private int accountId;
	private BigDecimal contributed;
	
	public TinyInfoDonation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TinyInfoDonation(int accountId, BigDecimal contributed) {
		super();
		this.accountId = accountId;
		this.contributed = contributed;
	}



	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getContributed() {
		return contributed;
	}

	public void setContributed(BigDecimal contributed) {
		this.contributed = contributed;
	}
	
}
