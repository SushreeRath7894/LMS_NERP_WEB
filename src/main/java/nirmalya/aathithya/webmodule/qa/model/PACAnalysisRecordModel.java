package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PACAnalysisRecordModel {
	
	private String pcaAnalysisId;
	private String date ;
	private String nEDTA ;
	private String nh2So4 ;
	private String ml ;
	private String productTypeField ;
	
	private String product1 ;
	private String batchNo1 ;
	private String netWtG1 ;
	private String h2so4ml1 ;
	private String pCal1 ;
	
	private String product2 ;
	private String batchNo2 ;
	private String netWtG2 ;
	private String h2so4ml2 ;
	private String pCal2 ;
	
	private String product3 ;
	private String batchNo3;
	private String netWtG3 ;
	private String h2so4ml3 ;
	private String pCal3;
	
	private String product4 ;
	private String batchNo4; 
	private String netWtG4; 
	private String h2so4ml4;
	private String pCal4 ;
	
	private String product5;
	private String batchNo5 ;
	private String netWtG5 ;
	private String h2so4ml5 ;
	private String pCal5 ;
	
	private String product6 ;
	private String batchNo6;
	private String netWtG6;
	private String h2so4ml6;
	private String pCal6;
	
	private String rStatus ;
	
	private String createdBy;
	private String createdOn;
	private String approvedDate;
	private String approvedBy;
	private String organization;
	private String orgDivision;
	
	

public PACAnalysisRecordModel() {
	super();
	// TODO Auto-generated constructor stub
}

public String getPcaAnalysisId() {
	return pcaAnalysisId;
}

public void setPcaAnalysisId(String pcaAnalysisId) {
	this.pcaAnalysisId = pcaAnalysisId;
}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

public String getnEDTA() {
	return nEDTA;
}

public void setnEDTA(String nEDTA) {
	this.nEDTA = nEDTA;
}

public String getNh2So4() {
	return nh2So4;
}

public void setNh2So4(String nh2So4) {
	this.nh2So4 = nh2So4;
}

public String getMl() {
	return ml;
}

public void setMl(String ml) {
	this.ml = ml;
}

public String getProductTypeField() {
	return productTypeField;
}

public void setProductTypeField(String productTypeField) {
	this.productTypeField = productTypeField;
}

public String getProduct1() {
	return product1;
}

public void setProduct1(String product1) {
	this.product1 = product1;
}

public String getBatchNo1() {
	return batchNo1;
}

public void setBatchNo1(String batchNo1) {
	this.batchNo1 = batchNo1;
}

public String getNetWtG1() {
	return netWtG1;
}

public void setNetWtG1(String netWtG1) {
	this.netWtG1 = netWtG1;
}

public String getH2so4ml1() {
	return h2so4ml1;
}

public void setH2so4ml1(String h2so4ml1) {
	this.h2so4ml1 = h2so4ml1;
}

public String getpCal1() {
	return pCal1;
}

public void setpCal1(String pCal1) {
	this.pCal1 = pCal1;
}

public String getProduct2() {
	return product2;
}

public void setProduct2(String product2) {
	this.product2 = product2;
}

public String getBatchNo2() {
	return batchNo2;
}

public void setBatchNo2(String batchNo2) {
	this.batchNo2 = batchNo2;
}

public String getNetWtG2() {
	return netWtG2;
}

public void setNetWtG2(String netWtG2) {
	this.netWtG2 = netWtG2;
}

public String getH2so4ml2() {
	return h2so4ml2;
}

public void setH2so4ml2(String h2so4ml2) {
	this.h2so4ml2 = h2so4ml2;
}

public String getpCal2() {
	return pCal2;
}

public void setpCal2(String pCal2) {
	this.pCal2 = pCal2;
}

public String getProduct3() {
	return product3;
}

public void setProduct3(String product3) {
	this.product3 = product3;
}

public String getBatchNo3() {
	return batchNo3;
}

public void setBatchNo3(String batchNo3) {
	this.batchNo3 = batchNo3;
}

public String getNetWtG3() {
	return netWtG3;
}

public void setNetWtG3(String netWtG3) {
	this.netWtG3 = netWtG3;
}

public String getH2so4ml3() {
	return h2so4ml3;
}

public void setH2so4ml3(String h2so4ml3) {
	this.h2so4ml3 = h2so4ml3;
}

public String getpCal3() {
	return pCal3;
}

public void setpCal3(String pCal3) {
	this.pCal3 = pCal3;
}

public String getProduct4() {
	return product4;
}

public void setProduct4(String product4) {
	this.product4 = product4;
}

public String getBatchNo4() {
	return batchNo4;
}

public void setBatchNo4(String batchNo4) {
	this.batchNo4 = batchNo4;
}

public String getNetWtG4() {
	return netWtG4;
}

public void setNetWtG4(String netWtG4) {
	this.netWtG4 = netWtG4;
}

public String getH2so4ml4() {
	return h2so4ml4;
}

public void setH2so4ml4(String h2so4ml4) {
	this.h2so4ml4 = h2so4ml4;
}

public String getpCal4() {
	return pCal4;
}

public void setpCal4(String pCal4) {
	this.pCal4 = pCal4;
}

public String getProduct5() {
	return product5;
}

public void setProduct5(String product5) {
	this.product5 = product5;
}

public String getBatchNo5() {
	return batchNo5;
}

public void setBatchNo5(String batchNo5) {
	this.batchNo5 = batchNo5;
}

public String getNetWtG5() {
	return netWtG5;
}

public void setNetWtG5(String netWtG5) {
	this.netWtG5 = netWtG5;
}

public String getH2so4ml5() {
	return h2so4ml5;
}

public void setH2so4ml5(String h2so4ml5) {
	this.h2so4ml5 = h2so4ml5;
}

public String getpCal5() {
	return pCal5;
}

public void setpCal5(String pCal5) {
	this.pCal5 = pCal5;
}

public String getProduct6() {
	return product6;
}

public void setProduct6(String product6) {
	this.product6 = product6;
}

public String getBatchNo6() {
	return batchNo6;
}

public void setBatchNo6(String batchNo6) {
	this.batchNo6 = batchNo6;
}

public String getNetWtG6() {
	return netWtG6;
}

public void setNetWtG6(String netWtG6) {
	this.netWtG6 = netWtG6;
}

public String getH2so4ml6() {
	return h2so4ml6;
}

public void setH2so4ml6(String h2so4ml6) {
	this.h2so4ml6 = h2so4ml6;
}

public String getpCal6() {
	return pCal6;
}

public void setpCal6(String pCal6) {
	this.pCal6 = pCal6;
}

public String getrStatus() {
	return rStatus;
}

public void setrStatus(String rStatus) {
	this.rStatus = rStatus;
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

public String getApprovedDate() {
	return approvedDate;
}

public void setApprovedDate(String approvedDate) {
	this.approvedDate = approvedDate;
}

public String getApprovedBy() {
	return approvedBy;
}

public void setApprovedBy(String approvedBy) {
	this.approvedBy = approvedBy;
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
