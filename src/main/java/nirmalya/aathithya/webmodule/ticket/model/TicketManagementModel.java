package nirmalya.aathithya.webmodule.ticket.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.maintenance.model.AllotedMaintenanceModel;


public class TicketManagementModel {

	private String ticketId;
	private String assignId;
	private String empId;
	private String date;
	private String time;
	private String empName;
	private String dept;
	private String ticketType;
	private String ticketCategory;
	private String ticketSubCategory;
	private String assetList;
	private String ticketPriority;
	private String description;
	private String locType;
	private String latitude;
	private String longitude;
	private String address;
	private String empAddress;
	private String assignType;
	private String assignTypeId;
	private String status;
	private String resultDescription;
	private String equipement;
	
	private String actualDate;
	private String actualTime;
	private String actualCost;

	private String createdBy;
	private String organization;
	private String orgDivision;
	private String ticketSource;

	private List<TicketDocumentManagementModel> documentList;
	private List<AllotedMaintenanceModel> equipementList;

	
	private String assetid;
	private String sdate;
	private String rdate;
	private String docName;
	private String imgName;
	private String imgUrl;
	private String complianceId;
	private List<TicketManagementModel> documentData;
	
	private String actionTaken;
	private String assignTo;
	private String currentTime;
	
	
	public String getAssetList() {
		return assetList;
	}

	public void setAssetList(String assetList) {
		this.assetList = assetList;
	}

	public TicketManagementModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAssignId() {
		return assignId;
	}

	public void setAssignId(String assignId) {
		this.assignId = assignId;
	}

	public String getResultDescription() {
		return resultDescription;
	}

	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public String getAssignTypeId() {
		return assignTypeId;
	}

	public void setAssignTypeId(String assignTypeId) {
		this.assignTypeId = assignTypeId;
	}

	public String getTicketId() {
		return ticketId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getTicketCategory() {
		return ticketCategory;
	}

	public void setTicketCategory(String ticketCategory) {
		this.ticketCategory = ticketCategory;
	}

	public String getTicketSubCategory() {
		return ticketSubCategory;
	}

	public void setTicketSubCategory(String ticketSubCategory) {
		this.ticketSubCategory = ticketSubCategory;
	}

	public String getTicketPriority() {
		return ticketPriority;
	}

	public void setTicketPriority(String ticketPriority) {
		this.ticketPriority = ticketPriority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocType() {
		return locType;
	}

	public void setLocType(String locType) {
		this.locType = locType;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	public List<TicketDocumentManagementModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<TicketDocumentManagementModel> documentList) {
		this.documentList = documentList;
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
	

	public String getEquipement() {
		return equipement;
	}

	public void setEquipement(String equipement) {
		this.equipement = equipement;
	}

	public List<AllotedMaintenanceModel> getEquipementList() {
		return equipementList;
	}

	public void setEquipementList(List<AllotedMaintenanceModel> equipementList) {
		this.equipementList = equipementList;
	}

	public String getActualDate() {
		return actualDate;
	}

	public void setActualDate(String actualDate) {
		this.actualDate = actualDate;
	}

	public String getActualTime() {
		return actualTime;
	}

	public void setActualTime(String actualTime) {
		this.actualTime = actualTime;
	}

	public String getActualCost() {
		return actualCost;
	}

	public void setActualCost(String actualCost) {
		this.actualCost = actualCost;
	}

	public String getTicketSource() {
		return ticketSource;
	}

	public void setTicketSource(String ticketSource) {
		this.ticketSource = ticketSource;
	}

	public String getAssetid() {
		return assetid;
	}

	public void setAssetid(String assetid) {
		this.assetid = assetid;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getComplianceId() {
		return complianceId;
	}

	public void setComplianceId(String complianceId) {
		this.complianceId = complianceId;
	}

	public List<TicketManagementModel> getDocumentData() {
		return documentData;
	}

	public void setDocumentData(List<TicketManagementModel> documentData) {
		this.documentData = documentData;
	}
	
	

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}
	
	

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	
	
	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
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
