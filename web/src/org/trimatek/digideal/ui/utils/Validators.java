package org.trimatek.digideal.ui.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.trimatek.digideal.ui.Context;
import org.trimatek.digideal.ui.model.Address;

public class Validators {

	public static boolean validateEmail(String target, String summary, String message) {
		if (target != null && !target.matches(Context.EMAIL_REGEX)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, summary, message));
			return false;
		}
		return true;
	}

	public static boolean validateName(String target, String summary, String message, int minWords) {
		if (target == null || !target.matches(Context.NAME_REGEX) || target.split(" ").length < minWords) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, summary, message));
			return false;
		}
		return true;
	}

	public static boolean validateQuantity(String target, String summary, String message) {
		if (target == null || !Tools.isNumeric(target)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, summary, message));
			return false;
		}
		return true;
	}
	
	public static boolean validateAddress(String target, String summary, String message) {
		if (target == null || !target.matches(Context.BTC_ADDRESS_REGEX)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, summary, message));
			return false;
		}
		return true;
	}

	public static String validateAddress(Address address) {
		StringBuilder sb = new StringBuilder();
		String locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
		if (address == null) {
			sb.append("<br/>");
			sb.append(Tools.read("error_address_invalid",locale));
		} else {
			if (address.getROUTE() == null) {
				sb.append("<br/>");
				sb.append(Tools.read("error_street_not_found",locale));
			}
			if (address.getSTREET_NUMBER() == null) {
				sb.append("<br/>");
				sb.append(Tools.read("error_street_number_not_found",locale));
			}
			if (address.getPOSTAL_CODE() == null) {
				sb.append("<br/>");
				sb.append(Tools.read("error_postal_code_not_found",locale));
			}
			if (address.getADMINISTRATIVE_AREA_LEVEL_1() == null) {
				sb.append("<br/>");
				sb.append(Tools.read("error_city_not_found",locale));
			}
			if (address.getCOUNTRY() == null) {
				sb.append("<br/>");
				sb.append(Tools.read("error_country_not_found",locale));
			}
		}
		return sb.length() > 0 ? Tools.read("error_address_not_parseable",locale) + sb.toString() : "";
	}

	public static boolean validateSentenceLength(String target, String summary, String message, int minWords) {
		if (target == null || target.split(" ").length < minWords) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, summary, message));
			return false;
		}
		return true;
	}

	public static String normalize(String source) {
		String result = source.replace(" ", "_").toLowerCase();
		result = Normalizer.normalize(result, Form.NFD);
		result = result.replaceAll("[^\\p{ASCII}]", "");
		return result.contains("@") ? result : "@" + result;
	}

}
