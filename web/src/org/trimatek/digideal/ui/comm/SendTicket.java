package org.trimatek.digideal.ui.comm;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.trimatek.digideal.ui.Config;
import org.trimatek.digideal.ui.model.Ticket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SendTicket {

	private Ticket ticket;
	protected final static Logger logger = Logger.getLogger(SendTicket.class.getName());

	Runnable send = () -> {
		try {
			URL url = new URL(Config.getValue("DIGIDEAL_TICKET_URL"));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.addRequestProperty("Content-Type", "application/" + "POST");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String ticketSerial = gson.toJson(ticket);
			con.setRequestProperty("Content-Length", Integer.toString(ticketSerial.length()));
			logger.log(Level.INFO, "Ready to send ticket from deal: " + ticket.getDealId());
			con.getOutputStream().write(ticketSerial.getBytes("UTF8"));
			int responseCode = con.getResponseCode();
			logger.log(Level.INFO, "Response Code : " + responseCode);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while sending ticket. Message: " + e.getMessage());
		}
	};

	public static void exec(Ticket ticket) {
		SendTicket st = new SendTicket();
		st.ticket = ticket;
		new Thread(st.send).start();
	}

}
