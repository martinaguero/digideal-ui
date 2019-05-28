package org.trimatek.digideal.ui.model;

import java.lang.reflect.Field;
import java.util.List;

import org.trimatek.digideal.ui.utils.Tools;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;

public class Address {

	private String STREET_NUMBER;
	private String ROUTE;
	private String ADMINISTRATIVE_AREA_LEVEL_1; // ciudad
	private String COUNTRY;
	private String POSTAL_CODE;

	private Address() {
	}

	public String getSTREET_NUMBER() {
		return STREET_NUMBER;
	}

	public String getROUTE() {
		return ROUTE;
	}

	public String getADMINISTRATIVE_AREA_LEVEL_1() {
		return ADMINISTRATIVE_AREA_LEVEL_1;
	}

	public String getCOUNTRY() {
		return COUNTRY;
	}

	public String getPOSTAL_CODE() {
		return POSTAL_CODE;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (ROUTE != null) {
			sb.append(ROUTE);
		}
		if (STREET_NUMBER != null) {
			sb.append(" " + STREET_NUMBER);
		}
		if (POSTAL_CODE != null) {
			sb.append(", " + POSTAL_CODE);
		}
		if (ADMINISTRATIVE_AREA_LEVEL_1 != null) {
			sb.append(", " + ADMINISTRATIVE_AREA_LEVEL_1);
		}
		if (COUNTRY != null) {
			sb.append(", " + COUNTRY);
		}
		return sb.toString();
	}

	public static Address build(AddressComponent[] ac)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Address a = new Address();
		List<String> fields = Tools.getFieldNames(a.getClass().getDeclaredFields());
		for (AddressComponent c : ac) {
			for (AddressComponentType type : c.types) {
				if (fields.contains(type.name())) {
					Field f = a.getClass().getDeclaredField(type.name());
					f.setAccessible(true);
					f.set(a, c.shortName);
				}
			}
		}
		return a;
	}

}
