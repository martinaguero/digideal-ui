package org.trimatek.digideal.ui.comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetFeePrediction {

	private final static Logger logger = Logger.getLogger(GetFeePrediction.class.getName());

	public static String exec(String source) {
		StringBuffer response = new StringBuffer();
		try {
			URL url = new URL(source);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			if (con.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				logger.log(Level.INFO, "New fee prediction received");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "New fee prediction could not be received");
		}
		return response.toString();
	}

}
