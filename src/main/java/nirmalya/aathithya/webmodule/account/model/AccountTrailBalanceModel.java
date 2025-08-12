package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountTrailBalanceModel {
	
	private String start_date;
	private String end_date;
	private String org;
	private String org_div;
	private String group_id;
	
	
	public AccountTrailBalanceModel() {
		super();
	}


	public String getStart_date() {
		return start_date;
	}


	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}


	public String getEnd_date() {
		return end_date;
	}


	public void setEnd_date(String end_date) {
		this.end_date = end_date;
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


	public String getGroup_id() {
		return group_id;
	}


	public void setGroup_id(String group_id) {
		this.group_id = group_id;
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
