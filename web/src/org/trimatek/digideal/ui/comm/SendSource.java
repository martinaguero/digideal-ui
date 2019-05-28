package org.trimatek.digideal.ui.comm;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.trimatek.digideal.ui.Config;
import org.trimatek.digideal.ui.model.Source;
import org.trimatek.digideal.ui.utils.SourceBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SendSource {

	private Source source;
	protected final static Logger logger = Logger.getLogger(SendSource.class.getName());

	Runnable sendSource = () -> {
		try {
			URL url = new URL(Config.getValue("DIGIDEAL_SOURCE_URL"));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.addRequestProperty("Content-Type", "application/" + "POST");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String sourceSerial = gson.toJson(SourceBuilder.formatToGo(source));
			con.setRequestProperty("Content-Length", Integer.toString(sourceSerial.length()));
			logger.log(Level.INFO, "Ready to send contract: " + source.getName());
			con.getOutputStream().write(sourceSerial.getBytes("UTF8"));
			int responseCode = con.getResponseCode();
			logger.log(Level.INFO, "Response Code : " + responseCode);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while sending contract. Message: " + e.getMessage());
		}
	};

	public static void exec(Source source) {
		SendSource ss = new SendSource();
		ss.source = source;
		new Thread(ss.sendSource).start();
	}

}
