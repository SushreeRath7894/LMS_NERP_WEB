package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmpRoleModel {

	private String userId;
	private String type;
	private String organization;
	private String orgDivision;
	private String requisitionName;
	private String comment;
	private String dateid;
	private List<String> userRole = new ArrayList<String>();
	private String selfType;

	public EmpRoleModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getUserRole() {
		return userRole;
	}

	public void setUserRole(List<String> userRole) {
		this.userRole = userRole;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getRequisitionName() {
		return requisitionName;
	}

	public void setRequisitionName(String requisitionName) {
		this.requisitionName = requisitionName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDateid() {
		return dateid;
	}

	public void setDateid(String dateid) {
		this.dateid = dateid;
	}

	public String getSelfType() {
		return selfType;
	}

	public void setSelfType(String selfType) {
		this.selfType = selfType;
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
