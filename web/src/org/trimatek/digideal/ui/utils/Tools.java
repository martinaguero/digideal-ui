package org.trimatek.digideal.ui.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.trimatek.digideal.ui.Context;

public class Tools {

	//public final static ResourceBundle msg = ResourceBundle.getBundle(Config.MESSAGES_BUNDLE);
	
	public static String read(String key, String lang) {
		return ResourceBundle.getBundle(Context.MESSAGES_BUNDLE,Locale.forLanguageTag(lang)).getString(key);
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

}
