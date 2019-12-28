package org.trimatek.digideal.ui.beans;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

import org.trimatek.digideal.ui.comm.GetRates;
import org.trimatek.digideal.ui.model.BtcRates;
import org.trimatek.digideal.ui.utils.Tools;

public class CommonView {

	protected static Logger logger;
	static {
		InputStream inputStream = CommonView.class.getResourceAsStream("/logging.properties");
		if (null != inputStream) {
			try {
				LogManager.getLogManager().readConfiguration(inputStream);

			} catch (IOException e) {
				Logger.getGlobal().log(Level.SEVERE, "init logging system", e);
			}
		}
	}
	private Locale locale;
	protected BigDecimal feeInBtc;

	public CommonView() {
		updateBtc();
		logger = Logger.getLogger(CommonView.class.getCanonicalName());
		logger.log(Level.INFO, "Ready logging");
	}

	public Locale getLocale() {
		if (locale == null) {
			locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		}
		return locale;
	}
	
	public void updateBtc() {
		GetRates.exec();
	}
	
	private BigDecimal getFeeInBtc() {
		if (feeInBtc == null) {
			long feeInSts = Tools.calcTxFee(Tools.calcTxBytes(1, 2));
			feeInBtc = Tools.satoshiToBtc(feeInSts);
		}
		return feeInBtc;
	}

	public String getFee() {		
		return getFeeInBtc().toString();
	}

	public String getFeeInUsd() {
		return getFeeInBtc().divide(BtcRates.instance().getUSD(), 2, RoundingMode.CEILING).toString();
	}

}
