package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventNotificationModel {
	private String notiSend;
	private String notiAction;
	private String notiInterval;
	private String nitoUnit;
	private String notiRule;
	private String notiSent;
	
	public String getNotiSend() {
		return notiSend;
	}

	public void setNotiSend(String notiSend) {
		this.notiSend = notiSend;
	}

	public String getNotiAction() {
		return notiAction;
	}

	public void setNotiAction(String notiAction) {
		this.notiAction = notiAction;
	}

	public String getNotiInterval() {
		return notiInterval;
	}

	public void setNotiInterval(String notiInterval) {
		this.notiInterval = notiInterval;
	}

	public String getNitoUnit() {
		return nitoUnit;
	}

	public void setNitoUnit(String nitoUnit) {
		this.nitoUnit = nitoUnit;
	}

	public String getNotiRule() {
		return notiRule;
	}

	public void setNotiRule(String notiRule) {
		this.notiRule = notiRule;
	}

	public String getNotiSent() {
		return notiSent;
	}

	public void setNotiSent(String notiSent) {
		this.notiSent = notiSent;
	}

	@Override
	public String toString() {
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr;
		try {
			jsonStr = mapperObj.writeValueAsString(this);
		} catch (IOException ex) {

			jsonStr = ex.toString();
		}
		return jsonStr;

	}
}
