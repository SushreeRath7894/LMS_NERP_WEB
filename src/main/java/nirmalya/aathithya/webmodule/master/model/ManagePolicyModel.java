package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManagePolicyModel {

	private String policyNo;
	private String policyDate;
	private String policyRemarks;
	private String isActive;
	private String dept;
	private List<ManagePolicyRolesModel> rules;
	private String organization;
	private String orgDivision;
	private String createdBy;

	public ManagePolicyModel() {
		super();
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyDate() {
		return policyDate;
	}

	public void setPolicyDate(String policyDate) {
		this.policyDate = policyDate;
	}

	public String getPolicyRemarks() {
		return policyRemarks;
	}

	public void setPolicyRemarks(String policyRemarks) {
		this.policyRemarks = policyRemarks;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public List<ManagePolicyRolesModel> getRules() {
		return rules;
	}

	public void setRules(List<ManagePolicyRolesModel> rules) {
		this.rules = rules;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
