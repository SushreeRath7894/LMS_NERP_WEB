package nirmalya.aathithya.webmodule.user.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SelfServiceActivityModel {

	private String moduleId;
	private String moduleKeyName;
	private String functionId;
	private String functionKeyName;
	private String activityId;
	private String activityKeyName;
	private String activityURL;
	private String activityLogo;

	public SelfServiceActivityModel() {
		super();
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleKeyName() {
		return moduleKeyName;
	}

	public void setModuleKeyName(String moduleKeyName) {
		this.moduleKeyName = moduleKeyName;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFunctionKeyName() {
		return functionKeyName;
	}

	public void setFunctionKeyName(String functionKeyName) {
		this.functionKeyName = functionKeyName;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityKeyName() {
		return activityKeyName;
	}

	public void setActivityKeyName(String activityKeyName) {
		this.activityKeyName = activityKeyName;
	}

	public String getActivityURL() {
		return activityURL;
	}

	public void setActivityURL(String activityURL) {
		this.activityURL = activityURL;
	}

	public String getActivityLogo() {
		return activityLogo;
	}

	public void setActivityLogo(String activityLogo) {
		this.activityLogo = activityLogo;
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
