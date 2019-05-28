package org.trimatek.digideal.ui.model;

import java.math.BigDecimal;

public enum Currencies {
	USD(10), BRL(40), BTC(0.0015), EUR(10);

	private BigDecimal min;

	Currencies(double value) {
		min = BigDecimal.valueOf(value);
	}

	public BigDecimal min() {
		return min;
	}

}
