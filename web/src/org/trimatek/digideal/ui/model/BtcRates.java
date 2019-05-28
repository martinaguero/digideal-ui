package org.trimatek.digideal.ui.model;

import java.math.BigDecimal;

public class BtcRates {

	private BigDecimal USD;
	private BigDecimal BRL;
	private BigDecimal EUR;
	private static BtcRates INSTANCE;

	private BtcRates() {
	}

	public static BtcRates instance() {
		if (INSTANCE == null) {
			INSTANCE = new BtcRates();
		}
		return INSTANCE;
	}

	public void setUSD(BigDecimal usd) {
		USD = usd;
	}

	public void setBRL(BigDecimal brl) {
		BRL = brl;
	}

	public void setEUR(BigDecimal eur) {
		EUR = eur;
	}

	public BigDecimal getBRL() {
		return BRL;
	}

	public BigDecimal getUSD() {
		return USD;
	}

	public BigDecimal getEUR() {
		return EUR;
	}

}
