package org.trimatek.digideal.ui.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.trimatek.digideal.ui.Context;

@FacesValidator("org.trimatek.digideal.ui.utils.MessageValidator")
public class MessageValidator implements Validator {

	private String locale;

	public MessageValidator() {
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value.toString().length() <= Context.CONTACT_MESSAGE_MIN
				|| value.toString().length() >= Context.CONTACT_MESSAGE_MAX) {
			FacesMessage msg = new FacesMessage(null, Tools.read("error_message_length", locale) + " ["
					+ Context.CONTACT_MESSAGE_MIN + "," + Context.CONTACT_MESSAGE_MAX + "] " + Tools.read("error_message_characters", locale));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

}
