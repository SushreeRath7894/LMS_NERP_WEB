package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class VitAModel {
	private String refNo;
	private String issueNo;
	private String issueDate;
	private String packingDate;
	private String vitAId;
	private String product;
	private String sku;
	private String batchNo;
	private String weightofSample;
	private String aid;
	private String bid;
	private String cid ;
	private String did;
	private String calculation;
	private String resultsMg;
    private String organization;
	private String orgDivision;
	private String createdBy;
	private String productTypeField;
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
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getPackingDate() {
		return packingDate;
	}
	public void setPackingDate(String packingDate) {
		this.packingDate = packingDate;
	}
	public String getVitAId() {
		return vitAId;
	}
	public void setVitAId(String vitAId) {
		this.vitAId = vitAId;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
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
	
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getCalculation() {
		return calculation;
	}
	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}
	public String getResultsMg() {
		return resultsMg;
	}
	public void setResultsMg(String resultsMg) {
		this.resultsMg = resultsMg;
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
