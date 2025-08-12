package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VbarModel {
	private String vb1arId;
	private String resultStatus;
	private String nameSign;
	private String verifiedBy;
	private String refNo;
	private String issueNo;
	private String date;
	private String dateOfIssue;
	private String rId;
	private String bId;
	private String product;
	private String batchNo;
	private String weightofSample;
	private String constantReading;
	private String calculation;
	private String resulsMg;
	private String organization;
	private String orgDivision;
	private String createdBy;

	private String vbcarId;
	private String dateOfSampling;
	private String dyeFactor;
	private String dateOfAnalysis;
	private String weightOfStandardUsed;
	private String blank;
	private String materialStatus;
	private String sampleWeight;
	private String titrationValue;
	private String observation;
	private String productTypeField;

	public VbarModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getVb1arId() {
		return vb1arId;
	}

	public void setVb1arId(String vb1arId) {
		this.vb1arId = vb1arId;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getNameSign() {
		return nameSign;
	}

	public void setNameSign(String nameSign) {
		this.nameSign = nameSign;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getrId() {
		return rId;
	}

	public void setrId(String rId) {
		this.rId = rId;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getWeightofSample() {
		return weightofSample;
	}

	public void setWeightofSample(String weightofSample) {
		this.weightofSample = weightofSample;
	}

	public String getConstantReading() {
		return constantReading;
	}

	public void setConstantReading(String constantReading) {
		this.constantReading = constantReading;
	}

	public String getCalculation() {
		return calculation;
	}

	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}

	public String getResulsMg() {
		return resulsMg;
	}

	public void setResulsMg(String resulsMg) {
		this.resulsMg = resulsMg;
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

	public String getVbcarId() {
		return vbcarId;
	}

	public void setVbcarId(String vbcarId) {
		this.vbcarId = vbcarId;
	}

	public String getDateOfSampling() {
		return dateOfSampling;
	}

	public void setDateOfSampling(String dateOfSampling) {
		this.dateOfSampling = dateOfSampling;
	}

	public String getDyeFactor() {
		return dyeFactor;
	}

	public void setDyeFactor(String dyeFactor) {
		this.dyeFactor = dyeFactor;
	}

	public String getDateOfAnalysis() {
		return dateOfAnalysis;
	}

	public void setDateOfAnalysis(String dateOfAnalysis) {
		this.dateOfAnalysis = dateOfAnalysis;
	}

	public String getWeightOfStandardUsed() {
		return weightOfStandardUsed;
	}

	public void setWeightOfStandardUsed(String weightOfStandardUsed) {
		this.weightOfStandardUsed = weightOfStandardUsed;
	}

	public String getBlank() {
		return blank;
	}

	public void setBlank(String blank) {
		this.blank = blank;
	}

	public String getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(String materialStatus) {
		this.materialStatus = materialStatus;
	}

	public String getSampleWeight() {
		return sampleWeight;
	}

	public void setSampleWeight(String sampleWeight) {
		this.sampleWeight = sampleWeight;
	}

	public String getTitrationValue() {
		return titrationValue;
	}

	public void setTitrationValue(String titrationValue) {
		this.titrationValue = titrationValue;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getProductTypeField() {
		return productTypeField;
	}

	public void setProductTypeField(String productTypeField) {
		this.productTypeField = productTypeField;
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
