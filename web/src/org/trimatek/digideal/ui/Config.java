package org.trimatek.digideal.ui;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

public class Config {

	private Properties props;
	private static Config INSTANCE;
	private static Logger logger;

	private Config() throws IOException {
		props = new Properties();
		props.load(Config.class.getResourceAsStream("/config.properties"));
	}

	private static Config getConfig() {
		if (INSTANCE == null) {
			try {
				INSTANCE = new Config();
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
			}
		}
		return INSTANCE;
	}

	private Properties getProps() {
		return props;
	}

	public static String getValue(String key) {
		return getConfig().getProps().get(key).toString();
	}
	
	public static String getWatermarkPath() throws IOException {
		return FacesContext.getCurrentInstance()
                .getExternalContext().getRealPath(Context.WATERMARK_SOURCE);
	}

}
