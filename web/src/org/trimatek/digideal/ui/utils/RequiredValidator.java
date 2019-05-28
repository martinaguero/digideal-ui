package org.trimatek.digideal.ui.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.trimatek.digideal.ui.Context;

@FacesValidator("org.trimatek.digideal.ui.utils.RequiredValidator")
public class RequiredValidator implements Validator {

	private String locale;

	public RequiredValidator() {
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value.toString().length() < Context.STATUS_SERIAL_MIN) {
			FacesMessage msg = new FacesMessage(null, Tools.read("error_input_required", locale));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

}
