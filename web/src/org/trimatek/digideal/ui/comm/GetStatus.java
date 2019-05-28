package org.trimatek.digideal.ui.comm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.trimatek.digideal.ui.Config;
import org.trimatek.digideal.ui.model.Status;

import com.google.gson.Gson;

public class GetStatus {

	private final static Logger logger = Logger.getLogger(GetStatus.class.getName());

	public static Status exec(String id) {
		StringBuffer response = new StringBuffer();
		Status status = null;
		try {
			URL url = new URL(Config.getValue("DIGIDEAL_STATUS_URL") + "?id=" + id);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			if (con.getResponseCode() != 500) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				status = new Gson().fromJson(response.toString(), Status.class);
				logger.log(Level.INFO, "Status received");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Status could not be received");
		}
		return status;
	}

}
