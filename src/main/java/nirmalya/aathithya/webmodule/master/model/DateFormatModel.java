package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DateFormatModel {
	
	private String dfId;
	private String dfType;
	private String dfJS;
	
	public DateFormatModel() {
		super();
	}

	public String getDfId() {
		return dfId;
	}

	public void setDfId(String dfId) {
		this.dfId = dfId;
	}

	public String getDfType() {
		return dfType;
	}

	public void setDfType(String dfType) {
		this.dfType = dfType;
	}

	public String getDfJS() {
		return dfJS;
	}

	public void setDfJS(String dfJS) {
		this.dfJS = dfJS;
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
