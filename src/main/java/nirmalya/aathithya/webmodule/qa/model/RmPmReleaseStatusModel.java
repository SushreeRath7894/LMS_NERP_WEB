package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RmPmReleaseStatusModel {
	private String rmPmId;
	private String refNo;
	private String issueNo;
	private String issuedDate;
	private String verifiedBy;

	private String num;
	private String dateOfReceipt;
	private String material;
	private String supplier;
	private String invoiceNo;
	private String invoiceDated;
	private String quantityReceived;
	private String grrNo;
	private String materialCode;
	private String dateOfMfg;
	private String sampleQty;
	private String samplerName;
	private String disposeOffPostAnalysis;
	private String analysisStatus;
	private String dateOfRelease;
	private String approvedBy;

	private String createdBy;
	private String organization;
	private String orgDivision;
	private String serialNo;
	private String productTypeField;

	public RmPmReleaseStatusModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getRmPmId() {
		return rmPmId;
	}

	public void setRmPmId(String rmPmId) {
		this.rmPmId = rmPmId;
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

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getVerifiedBy() {
		return verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getDateOfReceipt() {
		return dateOfReceipt;
	}

	public void setDateOfReceipt(String dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceDated() {
		return invoiceDated;
	}

	public void setInvoiceDated(String invoiceDated) {
		this.invoiceDated = invoiceDated;
	}

	public String getQuantityReceived() {
		return quantityReceived;
	}

	public void setQuantityReceived(String quantityReceived) {
		this.quantityReceived = quantityReceived;
	}

	public String getGrrNo() {
		return grrNo;
	}

	public void setGrrNo(String grrNo) {
		this.grrNo = grrNo;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getDateOfMfg() {
		return dateOfMfg;
	}

	public void setDateOfMfg(String dateOfMfg) {
		this.dateOfMfg = dateOfMfg;
	}

	public String getSampleQty() {
		return sampleQty;
	}

	public void setSampleQty(String sampleQty) {
		this.sampleQty = sampleQty;
	}

	public String getSamplerName() {
		return samplerName;
	}

	public void setSamplerName(String samplerName) {
		this.samplerName = samplerName;
	}

	public String getDisposeOffPostAnalysis() {
		return disposeOffPostAnalysis;
	}

	public void setDisposeOffPostAnalysis(String disposeOffPostAnalysis) {
		this.disposeOffPostAnalysis = disposeOffPostAnalysis;
	}

	public String getAnalysisStatus() {
		return analysisStatus;
	}

	public void setAnalysisStatus(String analysisStatus) {
		this.analysisStatus = analysisStatus;
	}

	public String getDateOfRelease() {
		return dateOfRelease;
	}

	public void setDateOfRelease(String dateOfRelease) {
		this.dateOfRelease = dateOfRelease;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
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

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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
