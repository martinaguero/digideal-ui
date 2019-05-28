package org.trimatek.digideal.ui;

import java.text.SimpleDateFormat;

public class Context {
	
	public final static String REQUIRED_FIELD = "background-color:#ffffcc;";
	public final static String GOOGLE_GEO_API_KEY = "AIzaSyAcLmRFJceRjP-Wkg8NQ2XOm-6cvML8A8E";
	public final static String MESSAGES_BUNDLE = "org.trimatek.digideal.ui.messages";
	public final static String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public final static String NAME_REGEX = "^[^\\d]+$";
	public final static String BTC_ADDRESS_REGEX = "^[123mn][1-9A-HJ-NP-Za-km-z]{26,35}";
	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	public final static int CONTACT_MESSAGE_MIN = 20;
	public final static int CONTACT_MESSAGE_MAX = 500;
	public final static int STATUS_SERIAL_MIN = 6;
	public final static String STATUS_DIALOG_HEIGHT = "440px";
	public final static String STATUS_DIALOG_WIDTH = "440px";
	public final static String WATERMARK_SOURCE = "./resources/watermark.pdf";

}
