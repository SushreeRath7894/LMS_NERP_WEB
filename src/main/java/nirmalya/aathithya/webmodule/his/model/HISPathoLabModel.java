package nirmalya.aathithya.webmodule.his.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;


public class HISPathoLabModel {
	private String bloddSampleId;
	private String orderId;
	private String testName;
	private String testId;
	private String qrCode;
	private String patientId;
	private String org;
	private String div;
	private String createdBy;
	private String createdOn;
	private String skuId;
	private String skuName;
	private String doctNotes;
	
	private String grpID;
	private String grpName;
	private String unitId;
	private String actualValue;
	private String range;
	private String customerId;
	
	
	private int age;
    private String appId;
    private String email;
    private String pName;
    private String custId;
    private String gender;
    private String mobile;
    private String address;
    private String referedBy;
    private String reportDate;
    private String registerDate;
    
    private String ref;
    private String notes;
    private String result;
    private String testNames;
    private String unitName;
    private String groupNames;
	
	
	
	
	private List<HISPathoLabModel> bloodDataList;
	
	

	public String getBloddSampleId() {
		return bloddSampleId;
	}



	public void setBloddSampleId(String bloddSampleId) {
		this.bloddSampleId = bloddSampleId;
	}



	public String getOrg() {
		return org;
	}



	public void setOrg(String org) {
		this.org = org;
	}



	public String getDiv() {
		return div;
	}



	public void setDiv(String div) {
		this.div = div;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}





	public List<HISPathoLabModel> getBloodDataList() {
		return bloodDataList;
	}



	public void setBloodDataList(List<HISPathoLabModel> bloodDataList) {
		this.bloodDataList = bloodDataList;
	}



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getTestName() {
		return testName;
	}



	public void setTestName(String testName) {
		this.testName = testName;
	}



	public String getTestId() {
		return testId;
	}



	public void setTestId(String testId) {
		this.testId = testId;
	}



	public String getQrCode() {
		return qrCode;
	}



	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}



	public String getPatientId() {
		return patientId;
	}



	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}



	public String getSkuId() {
		return skuId;
	}



	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}



	public String getSkuName() {
		return skuName;
	}



	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}



	public String getDoctNotes() {
		return doctNotes;
	}



	public void setDoctNotes(String doctNotes) {
		this.doctNotes = doctNotes;
	}



	public String getGrpID() {
		return grpID;
	}



	public void setGrpID(String grpID) {
		this.grpID = grpID;
	}



	public String getGrpName() {
		return grpName;
	}



	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}



	public String getUnitId() {
		return unitId;
	}



	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}



	public String getActualValue() {
		return actualValue;
	}



	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}



	public String getRange() {
		return range;
	}



	public void setRange(String range) {
		this.range = range;
	}



	public String getCustomerId() {
		return customerId;
	}



	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public String getAppId() {
		return appId;
	}



	public void setAppId(String appId) {
		this.appId = appId;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getpName() {
		return pName;
	}



	public void setpName(String pName) {
		this.pName = pName;
	}



	public String getCustId() {
		return custId;
	}



	public void setCustId(String custId) {
		this.custId = custId;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getReferedBy() {
		return referedBy;
	}



	public void setReferedBy(String referedBy) {
		this.referedBy = referedBy;
	}



	public String getReportDate() {
		return reportDate;
	}



	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}



	public String getRegisterDate() {
		return registerDate;
	}



	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}



	public String getRef() {
		return ref;
	}



	public void setRef(String ref) {
		this.ref = ref;
	}



	public String getNotes() {
		return notes;
	}



	public void setNotes(String notes) {
		this.notes = notes;
	}



	public String getResult() {
		return result;
	}



	public void setResult(String result) {
		this.result = result;
	}



	public String getTestNames() {
		return testNames;
	}



	public void setTestNames(String testNames) {
		this.testNames = testNames;
	}



	public String getUnitName() {
		return unitName;
	}



	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}



	public String getGroupNames() {
		return groupNames;
	}



	public void setGroupNames(String groupNames) {
		this.groupNames = groupNames;
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
