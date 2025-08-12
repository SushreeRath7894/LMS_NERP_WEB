package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import java.math.BigInteger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OrganizationalMasterModel {

	private String organizationalId;
	private String organizationalName;
	private String assignEmployee;
	private String organizationalStatus;
	private String parentId;
	private String createdBy;
	private String catLevel;
	private String parentName;
	private String parentEmployee;
	private BigInteger nodeCount;
	private String orgName;
	private String orgDivision;

	public String getOrganizationalId() {
		return organizationalId;
	}

	public void setOrganizationalId(String organizationalId) {
		this.organizationalId = organizationalId;
	}

	public String getOrganizationalName() {
		return organizationalName;
	}

	public void setOrganizationalName(String organizationalName) {
		this.organizationalName = organizationalName;
	}

 

	public String getAssignEmployee() {
		return assignEmployee;
	}

	public void setAssignEmployee(String assignEmployee) {
		this.assignEmployee = assignEmployee;
	}

	public String getParentEmployee() {
		return parentEmployee;
	}

	public void setParentEmployee(String parentEmployee) {
		this.parentEmployee = parentEmployee;
	}

	public String getOrganizationalStatus() {
		return organizationalStatus;
	}

	public void setOrganizationalStatus(String organizationalStatus) {
		this.organizationalStatus = organizationalStatus;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCatLevel() {
		return catLevel;
	}

	public void setCatLevel(String catLevel) {
		this.catLevel = catLevel;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public BigInteger getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(BigInteger nodeCount) {
		this.nodeCount = nodeCount;
	}


	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
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
