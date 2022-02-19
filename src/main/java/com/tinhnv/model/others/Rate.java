package com.tinhnv.model.others;

import java.math.BigDecimal;

public class Rate {

	private BigDecimal VND;
	private BigDecimal USD;

	public Rate(BigDecimal vND, BigDecimal uSD) {
		super();
		VND = vND;
		USD = uSD;
	}

	public BigDecimal getVND() {
		return VND;
	}

	public void setVND(BigDecimal vND) {
		VND = vND;
	}

	public BigDecimal getUSD() {
		return USD;
	}

	public void setUSD(BigDecimal uSD) {
		USD = uSD;
	}

}
