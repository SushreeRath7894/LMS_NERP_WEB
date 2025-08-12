package nirmalya.aathithya.webmodule.samudyamproduction.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

import java.util.List;

public class MouldMasterModel {
	private String moldid;
	private String projectName;
	private String items;
	private String pattern;
	private String moldType;
	private String date;
	private String size;
	private String th;
	private String fromDate;
	private String toDate;
	private String costingteam;
	private String desc;

	private String checkingid;
	private String checkingname;
	private String statusid;
	private String status;
	private String remarks;

	private String createdBy;
	private String organization;
	private String orgDivision;

	private List<InventoryVendorDocumentModel> documentList;
	private List<CheckingDetailsModel> activity;

	public MouldMasterModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMoldid() {
		return moldid;
	}

	public void setMoldid(String moldid) {
		this.moldid = moldid;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTh() {
		return th;
	}

	public void setTh(String th) {
		this.th = th;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getCostingteam() {
		return costingteam;
	}

	public void setCostingteam(String costingteam) {
		this.costingteam = costingteam;
	}

	public String getCheckingid() {
		return checkingid;
	}

	public void setCheckingid(String checkingid) {
		this.checkingid = checkingid;
	}

	public String getCheckingname() {
		return checkingname;
	}

	public void setCheckingname(String checkingname) {
		this.checkingname = checkingname;
	}

	public String getStatusid() {
		return statusid;
	}

	public void setStatusid(String statusid) {
		this.statusid = statusid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public List<InventoryVendorDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<InventoryVendorDocumentModel> documentList) {
		this.documentList = documentList;
	}

	public List<CheckingDetailsModel> getActivity() {
		return activity;
	}

	public void setActivity(List<CheckingDetailsModel> activity) {
		this.activity = activity;
	}
 
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getMoldType() {
		return moldType;
	}

	public void setMoldType(String moldType) {
		this.moldType = moldType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
