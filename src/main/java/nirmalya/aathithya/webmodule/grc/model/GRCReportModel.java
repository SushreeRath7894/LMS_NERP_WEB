package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GRCReportModel {

	private String fitForUse;
	private String reviewDate;
	private String reviewDept;
	private String reviewName;
	private String reviewDesgn;
	private String reviewDoneBy;
	private String inspDesgn;
	private String inspectionDate;
	private String inspectionDept;
	private String inspectionName;
	private String inspectionDoneBy;
	private String remark;
	private String result;
	private String remarks;
	private String taskName;
	private String document;
	private String category;
	private String categoryId;
	
	private String slNo;
	private String monthName;
	private String observation;
	private String dateOfInspection;
	private String orgDiv;
	private String orgAddress;
	private String location;
	private String locationId;
	private String actionPlan;
	
	private String year;
	private String month;
	private String sbo;
	private String hi;
	private String empId;
	private String empName;
	private String reportId;
	private String sl_no;
	private String items;
	private String frequency;
	private String valid;
	private String status;
	
	private String createdBy;
	private String organization;
	private String orgDivision;



	private List<String> taskList;

	public GRCReportModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}


	public String getFitForUse() {
		return fitForUse;
	}

	public void setFitForUse(String fitForUse) {
		this.fitForUse = fitForUse;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getReviewDept() {
		return reviewDept;
	}

	public void setReviewDept(String reviewDept) {
		this.reviewDept = reviewDept;
	}

	public String getReviewName() {
		return reviewName;
	}

	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}

	public String getReviewDesgn() {
		return reviewDesgn;
	}

	public void setReviewDesgn(String reviewDesgn) {
		this.reviewDesgn = reviewDesgn;
	}

	public String getReviewDoneBy() {
		return reviewDoneBy;
	}

	public void setReviewDoneBy(String reviewDoneBy) {
		this.reviewDoneBy = reviewDoneBy;
	}

	public String getInspDesgn() {
		return inspDesgn;
	}

	public void setInspDesgn(String inspDesgn) {
		this.inspDesgn = inspDesgn;
	}

	public String getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(String inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public String getInspectionDept() {
		return inspectionDept;
	}

	public void setInspectionDept(String inspectionDept) {
		this.inspectionDept = inspectionDept;
	}

	public String getInspectionName() {
		return inspectionName;
	}

	public void setInspectionName(String inspectionName) {
		this.inspectionName = inspectionName;
	}

	public String getInspectionDoneBy() {
		return inspectionDoneBy;
	}

	public void setInspectionDoneBy(String inspectionDoneBy) {
		this.inspectionDoneBy = inspectionDoneBy;
	}


	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getDateOfInspection() {
		return dateOfInspection;
	}

	public void setDateOfInspection(String dateOfInspection) {
		this.dateOfInspection = dateOfInspection;
	}

	public String getOrgDiv() {
		return orgDiv;
	}

	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getActionPlan() {
		return actionPlan;
	}

	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getSbo() {
		return sbo;
	}

	public void setSbo(String sbo) {
		this.sbo = sbo;
	}

	public String getHi() {
		return hi;
	}

	public void setHi(String hi) {
		this.hi = hi;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public List<String> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<String> taskList) {
		this.taskList = taskList;
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

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getSl_no() {
		return sl_no;
	}

	public void setSl_no(String sl_no) {
		this.sl_no = sl_no;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
