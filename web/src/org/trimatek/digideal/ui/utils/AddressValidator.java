package org.trimatek.digideal.ui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.trimatek.digideal.ui.Context;

@FacesValidator("org.trimatek.digideal.ui.utils.AddressValidator")
public class AddressValidator implements Validator {
	
	private Pattern pattern;
	private Matcher matcher;
	private String locale;

	public AddressValidator() {
		pattern = Pattern.compile(Context.BTC_ADDRESS_REGEX);
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		matcher = pattern.matcher(value.toString());
		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage(null,
					Tools.read("status_address", locale) + " " + Tools.read("error_incorrect", locale));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

}
