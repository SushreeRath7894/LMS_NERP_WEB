package nirmalya.aathithya.webmodule.gatepass.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ShiftRegisterReportModel {
	private String registerId;
	private String pdate;
	private String shift;
	private Integer slNo;
	private String category;
	private String manPower;
	private String approvestatus;
	private String desc;
	private String createdBy;
	private String organization;
	private String orgDivision;

	private String inputType;
	private String addManPower;
	private String manpowerNo;

	public ShiftRegisterReportModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public Integer getSlNo() {
		return slNo;
	}

	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getManPower() {
		return manPower;
	}

	public void setManPower(String manPower) {
		this.manPower = manPower;
	}

	public String getApprovestatus() {
		return approvestatus;
	}

	public void setApprovestatus(String approvestatus) {
		this.approvestatus = approvestatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getAddManPower() {
		return addManPower;
	}

	public void setAddManPower(String addManPower) {
		this.addManPower = addManPower;
	}

	public String getManpowerNo() {
		return manpowerNo;
	}

	public void setManpowerNo(String manpowerNo) {
		this.manpowerNo = manpowerNo;
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
