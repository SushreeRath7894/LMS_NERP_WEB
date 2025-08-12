package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MasterWarehouseModel {

	private String wId;
	private String wName;
	private String wTypeId;
	private String wCountryId;
	private String wStateId;
	private String wCity;
	private String wAddress;
	private String wVirtual;
	private String wStatus;
	private String createdBy;
	private String org;
	private String orgDiv;
	private String wImg;
	private String fileupload;
	private String wDeptId;
	
	private String offId;
	private String empId;
	private String employeeName;
	private String offDays;
	private String date;
	

	public MasterWarehouseModel() {
		super();
	}

	public String getwId() {
		return wId;
	}

	public void setwId(String wId) {
		this.wId = wId;
	}

	public String getwName() {
		return wName;
	}

	public void setwName(String wName) {
		this.wName = wName;
	}

	public String getwTypeId() {
		return wTypeId;
	}

	public void setwTypeId(String wTypeId) {
		this.wTypeId = wTypeId;
	}

	public String getwCountryId() {
		return wCountryId;
	}

	public void setwCountryId(String wCountryId) {
		this.wCountryId = wCountryId;
	}

	public String getwStateId() {
		return wStateId;
	}

	public void setwStateId(String wStateId) {
		this.wStateId = wStateId;
	}

	public String getwCity() {
		return wCity;
	}

	public void setwCity(String wCity) {
		this.wCity = wCity;
	}

	public String getwAddress() {
		return wAddress;
	}

	public void setwAddress(String wAddress) {
		this.wAddress = wAddress;
	}

	public String getwVirtual() {
		return wVirtual;
	}

	public void setwVirtual(String wVirtual) {
		this.wVirtual = wVirtual;
	}

	public String getwStatus() {
		return wStatus;
	}

	public void setwStatus(String wStatus) {
		this.wStatus = wStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getOrgDiv() {
		return orgDiv;
	}

	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}

	public String getwImg() {
		return wImg;
	}

	public void setwImg(String wImg) {
		this.wImg = wImg;
	}

	public String getFileupload() {
		return fileupload;
	}

	public void setFileupload(String fileupload) {
		this.fileupload = fileupload;
	}

	public String getwDeptId() {
		return wDeptId;
	}

	public void setwDeptId(String wDeptId) {
		this.wDeptId = wDeptId;
	}

	public String getOffId() {
		return offId;
	}

	public void setOffId(String offId) {
		this.offId = offId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getOffDays() {
		return offDays;
	}

	public void setOffDays(String offDays) {
		this.offDays = offDays;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
