package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
public class IncidentRegisterModel {
	
	private String incidentId;
	private String date;
	private String apstatus;
	private String approveby;
	private String slNo;
	private String dateOfIssue;
	private String shift;
	private String machine;
	private String incident;
	private String rejected;
	private String quantity;
	private String remark;
	private String signature;
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	
	
	private List<IncidentRegisterModel> item_data;

	

	public IncidentRegisterModel() {
		super();
	}



	public String getIncidentId() {
		return incidentId;
	}



	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}



	public String getApstatus() {
		return apstatus;
	}



	public void setApstatus(String apstatus) {
		this.apstatus = apstatus;
	}



	public String getApproveby() {
		return approveby;
	}



	public void setApproveby(String approveby) {
		this.approveby = approveby;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getSlNo() {
		return slNo;
	}



	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}



	public String getDateOfIssue() {
		return dateOfIssue;
	}



	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}



	public String getShift() {
		return shift;
	}



	public void setShift(String shift) {
		this.shift = shift;
	}



	public String getMachine() {
		return machine;
	}



	public void setMachine(String machine) {
		this.machine = machine;
	}



	public String getIncident() {
		return incident;
	}



	public void setIncident(String incident) {
		this.incident = incident;
	}



	public String getRejected() {
		return rejected;
	}



	public void setRejected(String rejected) {
		this.rejected = rejected;
	}



	public String getQuantity() {
		return quantity;
	}



	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getSignature() {
		return signature;
	}



	public void setSignature(String signature) {
		this.signature = signature;
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
	
	public List<IncidentRegisterModel> getItem_data() {
		return item_data;
	}



	public void setItem_data(List<IncidentRegisterModel> item_data) {
		this.item_data = item_data;
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
