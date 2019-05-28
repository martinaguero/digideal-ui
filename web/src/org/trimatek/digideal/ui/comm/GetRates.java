package org.trimatek.digideal.ui.comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.trimatek.digideal.ui.Config;
import org.trimatek.digideal.ui.model.BtcRates;

public class GetRates {

	protected final static Logger logger = Logger.getLogger(GetRates.class.getName());

	Runnable updateRates = () -> {
		try {
			URL obj = new URL(Config.getValue("BTC_PRICE_URL"));
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			if (con.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					if (inputLine.startsWith("USD")) {
						BtcRates.instance().setUSD(new BigDecimal(inputLine.substring(inputLine.indexOf("=") + 1)));
					} else if (inputLine.startsWith("BRL")) {
						BtcRates.instance().setBRL(new BigDecimal(inputLine.substring(inputLine.indexOf("=") + 1)));
					} else if (inputLine.startsWith("EUR")) {
						BtcRates.instance().setEUR(new BigDecimal(inputLine.substring(inputLine.indexOf("=") + 1)));
					}
				}
				in.close();
				logger.log(Level.INFO, "Rates updated ");
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	};

	public static void exec() {
		GetRates gr = new GetRates();
		new Thread(gr.updateRates).start();
	}

}
