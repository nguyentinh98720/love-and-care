package com.tinhnv.model.others;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class DonateStatistics {

	private int numEventJoin;
	private int numDonateAction;
	private BigDecimal totalMoneySpent;

	public DonateStatistics() {
		super();
	}

	public DonateStatistics(int numEventJoin, int numDonateAction, BigDecimal totalMoneySpent) {
		super();
		this.numEventJoin = numEventJoin;
		this.numDonateAction = numDonateAction;
		this.totalMoneySpent = totalMoneySpent;
	}

	public int getNumEventJoin() {
		return numEventJoin;
	}

	public void setNumEventJoin(int numEventJoin) {
		this.numEventJoin = numEventJoin;
	}

	public int getNumDonateAction() {
		return numDonateAction;
	}

	public void setNumDonateAction(int numDonateAction) {
		this.numDonateAction = numDonateAction;
	}

	public BigDecimal getTotalMoneySpent() {
		return totalMoneySpent;
	}

	public void setTotalMoneySpent(BigDecimal totalMoneySpent) {
		this.totalMoneySpent = totalMoneySpent;
	}

}
