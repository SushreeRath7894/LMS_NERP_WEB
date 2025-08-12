package nirmalya.aathithya.webmodule.user.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nirmalya Labs
 *
 */
public class Activity {

	private String name;
	private String activity;
	private String activityId;
	private String activityKeyName;
	private String act_id;
	private String func_id;
	private String mod_id;
	private String user_id;
	private String org;
	private String org_div;
	private String activityLogo;
	private String slno;
	private String actionId;
	private String actionName;

	public Activity() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
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

	public String getAct_id() {
		return act_id;
	}

	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}

	public String getFunc_id() {
		return func_id;
	}

	public void setFunc_id(String func_id) {
		this.func_id = func_id;
	}

	public String getMod_id() {
		return mod_id;
	}

	public void setMod_id(String mod_id) {
		this.mod_id = mod_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getOrg_div() {
		return org_div;
	}

	public void setOrg_div(String org_div) {
		this.org_div = org_div;
	}

	public String getActivityLogo() {
		return activityLogo;
	}

	public void setActivityLogo(String activityLogo) {
		this.activityLogo = activityLogo;
	}

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * Overrides toString method for converting class to string and back
	 **/
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
