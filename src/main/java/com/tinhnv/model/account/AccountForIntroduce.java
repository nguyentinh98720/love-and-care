package com.tinhnv.model.account;

import java.math.BigDecimal;

import com.tinhnv.model.others.VaTiImage;

public class AccountForIntroduce extends AccountForManage {

	private int numEventJoined;
	private BigDecimal totalMoneySpend;
	private int numDonateAction;
	
	public AccountForIntroduce() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AccountForIntroduce(int id, String firstName, String lastName, String phoneNumber, String address,
			String role, VaTiImage avatar, boolean status) {
		super(id, firstName, lastName, phoneNumber, address, role, avatar, status);
		// TODO Auto-generated constructor stub
	}

	public int getNumEventJoined() {
		return numEventJoined;
	}

	public void setNumEventJoined(int numEventJoined) {
		this.numEventJoined = numEventJoined;
	}

	public BigDecimal getTotalMoneySpend() {
		return totalMoneySpend;
	}

	public void setTotalMoneySpend(BigDecimal totalMoneySpend) {
		this.totalMoneySpend = totalMoneySpend;
	}

	public int getNumDonateAction() {
		return numDonateAction;
	}

	public void setNumDonateAction(int numDonateAction) {
		this.numDonateAction = numDonateAction;
	}
}
