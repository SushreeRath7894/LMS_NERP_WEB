package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManagePolicyRolesModel {

	public String policyParameter;
	public String policyFromDays;
	public String policyToDays;
	public String policyGracePeriod;
	public String rowId;

	public String getPolicyParameter() {
		return policyParameter;
	}

	public void setPolicyParameter(String policyParameter) {
		this.policyParameter = policyParameter;
	}

	public String getPolicyFromDays() {
		return policyFromDays;
	}

	public void setPolicyFromDays(String policyFromDays) {
		this.policyFromDays = policyFromDays;
	}

	public String getPolicyToDays() {
		return policyToDays;
	}

	public void setPolicyToDays(String policyToDays) {
		this.policyToDays = policyToDays;
	}

	public String getPolicyGracePeriod() {
		return policyGracePeriod;
	}

	public void setPolicyGracePeriod(String policyGracePeriod) {
		this.policyGracePeriod = policyGracePeriod;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
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
