package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HorlicksAnalysisWebModel {
	private String horlicksId;
	private String refNo;
	private String issueNo;
	private String issuedDate;
	private String approveStatus;
	
	private String ironCont;
	private String bno1;
	private String to1;
	private String bno2;
	private String spec1;
	private String blank1;
	private String param;
	private String bno3;
	private String ressult1;
	
	private String date;
	private String slNo;
	private String sku;
	private String signatureOfMicrobiologist;
	private String appearance;
	private String tasteFlavour;
	private String odourClean;
	private String consistency;
	private String singleMpiece;
	private String bdSpecification;
	private String phSpecification;
	private String totalProtin;
	private String moistureSpecification;
	private String vitaminResults;
	private String sign;
	
	private String createdBy;
	private String organization;
	private String orgDivision;
	private String serialNo;
	private String productTypeField;
	List<HorlicksAnalysisDetailsOneWebModel> horlicksAnalysisOne;
	
	public HorlicksAnalysisWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getHorlicksId() {
		return horlicksId;
	}
	public void setHorlicksId(String horlicksId) {
		this.horlicksId = horlicksId;
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
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getIronCont() {
		return ironCont;
	}
	public void setIronCont(String ironCont) {
		this.ironCont = ironCont;
	}
	public String getBno1() {
		return bno1;
	}
	public void setBno1(String bno1) {
		this.bno1 = bno1;
	}
	public String getTo1() {
		return to1;
	}
	public void setTo1(String to1) {
		this.to1 = to1;
	}
	public String getBno2() {
		return bno2;
	}
	public void setBno2(String bno2) {
		this.bno2 = bno2;
	}
	public String getSpec1() {
		return spec1;
	}
	public void setSpec1(String spec1) {
		this.spec1 = spec1;
	}
	public String getBlank1() {
		return blank1;
	}
	public void setBlank1(String blank1) {
		this.blank1 = blank1;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getBno3() {
		return bno3;
	}
	public void setBno3(String bno3) {
		this.bno3 = bno3;
	}
	public String getRessult1() {
		return ressult1;
	}
	public void setRessult1(String ressult1) {
		this.ressult1 = ressult1;
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
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSignatureOfMicrobiologist() {
		return signatureOfMicrobiologist;
	}
	public void setSignatureOfMicrobiologist(String signatureOfMicrobiologist) {
		this.signatureOfMicrobiologist = signatureOfMicrobiologist;
	}
	public String getAppearance() {
		return appearance;
	}
	public void setAppearance(String appearance) {
		this.appearance = appearance;
	}
	public String getTasteFlavour() {
		return tasteFlavour;
	}
	public void setTasteFlavour(String tasteFlavour) {
		this.tasteFlavour = tasteFlavour;
	}
	public String getOdourClean() {
		return odourClean;
	}
	public void setOdourClean(String odourClean) {
		this.odourClean = odourClean;
	}
	public String getConsistency() {
		return consistency;
	}
	public void setConsistency(String consistency) {
		this.consistency = consistency;
	}
	public String getSingleMpiece() {
		return singleMpiece;
	}
	public void setSingleMpiece(String singleMpiece) {
		this.singleMpiece = singleMpiece;
	}
	public String getBdSpecification() {
		return bdSpecification;
	}
	public void setBdSpecification(String bdSpecification) {
		this.bdSpecification = bdSpecification;
	}
	public String getPhSpecification() {
		return phSpecification;
	}
	public void setPhSpecification(String phSpecification) {
		this.phSpecification = phSpecification;
	}
	public String getTotalProtin() {
		return totalProtin;
	}
	public void setTotalProtin(String totalProtin) {
		this.totalProtin = totalProtin;
	}
	public String getMoistureSpecification() {
		return moistureSpecification;
	}
	public void setMoistureSpecification(String moistureSpecification) {
		this.moistureSpecification = moistureSpecification;
	}
	public String getVitaminResults() {
		return vitaminResults;
	}
	public void setVitaminResults(String vitaminResults) {
		this.vitaminResults = vitaminResults;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
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
	
	
	public List<HorlicksAnalysisDetailsOneWebModel> getHorlicksAnalysisOne() {
		return horlicksAnalysisOne;
	}
	public void setHorlicksAnalysisOne(List<HorlicksAnalysisDetailsOneWebModel> horlicksAnalysisOne) {
		this.horlicksAnalysisOne = horlicksAnalysisOne;
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
