package org.trimatek.digideal.ui.utils;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.trimatek.digideal.ui.model.BtcRates;
import org.trimatek.digideal.ui.model.Currencies;

public class CurrencyValidator {

	public static String validateQuantity(String quantity, String selectedCurrency, String locale) {
		if (Validators.validateQuantity(quantity, Tools.read("error_quantity", locale),
				Tools.read("error_incorrect", locale))) {
			if (Currencies.BTC.name().equals(selectedCurrency)) {
				return quantity;
			} else if (Currencies.USD.name().equals(selectedCurrency)) {
				BigDecimal dollars = new BigDecimal(quantity);
				if (dollars.compareTo(Currencies.USD.min()) < 0) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									Tools.read("currency_amount", locale) + " " + Currencies.USD.name(),
									Tools.read("currency_min", locale) + " " + Currencies.USD.min() + " "
											+ Tools.read("currency_required", locale)));
				} else {
					return toBtcString(dollars, BtcRates.instance().getUSD());
				}
			} else if (Currencies.EUR.name().equals(selectedCurrency)) {
				BigDecimal euros = new BigDecimal(quantity);
				if (euros.compareTo(Currencies.EUR.min()) < 0) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									Tools.read("currency_amount", locale) + " " + Currencies.EUR.name(),
									Tools.read("currency_min", locale) + " " + Currencies.EUR.min() + " "
											+ Tools.read("currency_required", locale)));
				} else {
					return toBtcString(euros, BtcRates.instance().getEUR());
				}
			} else if (Currencies.BRL.name().equals(selectedCurrency)) {
				BigDecimal reais = new BigDecimal(quantity);
				if (reais.compareTo(Currencies.BRL.min()) < 0) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									Tools.read("currency_amount", locale) + " " + Currencies.BRL.name(),
									Tools.read("currency_min", locale) + " " + Currencies.BRL.min() + " "
											+ Tools.read("currency_required", locale)));
				} else {
					return toBtcString(reais, BtcRates.instance().getBRL());
				}
			}
		}
		return null;
	}

	private static String toBtcString(BigDecimal quantity, BigDecimal rate) {
		BigDecimal result = quantity.multiply(rate);
		return result.setScale(8, BigDecimal.ROUND_HALF_EVEN).toPlainString();
	}

}
