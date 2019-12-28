package org.trimatek.digideal.ui.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.trimatek.digideal.ui.Config;
import org.trimatek.digideal.ui.Context;
import org.trimatek.digideal.ui.comm.GetFeePrediction;

public class Tools {

	// public final static ResourceBundle msg =
	// ResourceBundle.getBundle(Config.MESSAGES_BUNDLE);

	public static String read(String key, String lang) {
		return ResourceBundle.getBundle(Context.MESSAGES_BUNDLE, Locale.forLanguageTag(lang)).getString(key);
	}

	public static List<String> getFieldNames(Field[] fields) {
		List<String> fieldNames = new ArrayList<>();
		for (Field field : fields)
			fieldNames.add(field.getName());
		return fieldNames;
	}

	public static boolean isNumeric(String strNum) {
		return strNum.matches("-?\\d+(\\.\\d+)?");
	}

	public static int calcTxBytes(int inputs, int outputs) {
		return ((inputs * 148) + (outputs * 34) + 10);
	}

	public static int calcTxFee(int bytes) {
		String result = GetFeePrediction.exec(Config.getValue("BTC_FEE_URL"));
		if (result == null) {
			result = GetFeePrediction.exec(Config.getValue("BTC_FEE_HIST_URL"));
		}
		return result != null ? bytes * Integer.valueOf(result) : Integer.valueOf(Config.getValue("STS_PER_BYTE"));
	}

	public static BigDecimal satoshiToBtc(Long sts) {
		return new BigDecimal(sts).divide(new BigDecimal(Context.COIN_VALUE), Context.DEFAULT_SCALE,
				RoundingMode.CEILING);
	}

}
