package org.trimatek.digideal.ui.beans;

import java.util.logging.Level;

import org.trimatek.digideal.ui.Config;
import org.trimatek.digideal.ui.Context;
import org.trimatek.digideal.ui.comm.GetStatus;
import org.trimatek.digideal.ui.model.Status;
import org.trimatek.digideal.ui.utils.Tools;

import com.captcha.botdetect.web.jsf.SimpleJsfCaptcha;

public class StatusView extends CommonView {

	private String id;
	private String idStyle;
	private Status status;
	private SimpleJsfCaptcha captcha;
	private String captchaCode;
	private String captchaCodeStyle;
	private String result;
	private String dialogHeight;
	private String dialogWidth;

	public void onLoad() {
		status = null;
	}

	public StatusView() {
		resetFields();
	}

	public void resetFields() {
		idStyle = Context.REQUIRED_FIELD;
		captchaCodeStyle = Context.REQUIRED_FIELD;
		setId("");
		setCaptchaCode("");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdStyle() {
		return idStyle;
	}

	public void setIdStyle(String idStyle) {
		this.idStyle = idStyle;
	}

	public boolean getAcceptDisabled() {
		return idStyle == null ? false : true;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public SimpleJsfCaptcha getCaptcha() {
		return captcha;
	}

	public void setCaptcha(SimpleJsfCaptcha captcha) {
		this.captcha = captcha;
	}

	public String getCaptchaCode() {
		return captchaCode;
	}

	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}

	public String getCaptchaCodeStyle() {
		return captchaCodeStyle;
	}

	public void setCaptchaCodeStyle(String captchaCodeStyle) {
		this.captchaCodeStyle = captchaCodeStyle;
	}

	public void acceptAction() {
		status = null;
		if (captcha.validate(captchaCode)) {
			status = GetStatus.exec(getId());
			logger.log(Level.INFO, "Status received");
			if (status.getResult() == 200) {
				resetFields();
				setStatus(normalize(status));
				setDialogHeight(Context.STATUS_DIALOG_HEIGHT);
				setDialogWidth(Context.STATUS_DIALOG_WIDTH);
			} else {
				setResult(Tools.read("status_id_fail", getLocale().toString()));
			}
		} else {
			status = new Status();
			status.setId(getId());
			status.setResult(-1);
			setResult(Tools.read("status_captcha_fail", getLocale().toString()));
			logger.log(Level.INFO, "Status captcha error");
		}
		setDialogHeight("");
		setDialogWidth("");
	}

	public void validateId() {
		if (getId().length() < Context.STATUS_SERIAL_MIN) {
			idStyle = Context.REQUIRED_FIELD;
		} else {
			idStyle = null;
		}
	}

	public void validateCaptchaCode() {
		if (getCaptchaCode() != null) {
			captchaCodeStyle = null;
		} else {
			captchaCodeStyle = Context.REQUIRED_FIELD;
		}
	}

	public String getRenderProgressbar() {
		return status == null ? "true" : "false";
	}

	public boolean getBtnRetryRendered() {
		return getStatus() != null && getStatus().getResult() != 200 ? true : false;
	}

	public boolean getBtnContinueRendered() {
		return getStatus() != null && getStatus().getResult() == 200 ? true : false;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	private Status normalize(Status status) {
		status.setRunning(Tools.read("status_" + status.getRunning(), getLocale().toString()));
		if (status.getComments() == null) {
			status.setStatus(Tools.read("status_" + status.getStatus(), getLocale().toString()));
		} else {
			status.setStatus(Tools.read("status_error_" + status.getStatus(), getLocale().toString()));
		}
		return status;
	}

	public String getTxTrackUrl() {
		return Config.getValue("BTC_TX_TRACK_TESTNET");
	}

	public String getDialogHeight() {
		return dialogHeight;
	}

	public void setDialogHeight(String dialogHeight) {
		this.dialogHeight = dialogHeight;
	}

	public String getDialogWidth() {
		return dialogWidth;
	}

	public void setDialogWidth(String dialogWidth) {
		this.dialogWidth = dialogWidth;
	}

	public String getStatusNumber() {
		return Integer.valueOf(status.getStatus()) + 1 + "";
	}

}
