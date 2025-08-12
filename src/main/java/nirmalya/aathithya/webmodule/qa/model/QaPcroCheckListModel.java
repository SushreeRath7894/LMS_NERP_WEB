package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QaPcroCheckListModel {
	
	
	
	private String pcroCheckListId;
	private String SL1;
	private String v1;
	private String v2;
	private String v3;
	private String v4;
	private String v5;
	private String v6;
	private String v7;
	private String v8;
	private String v9;
	private String v10;
	

	private String packingDate;
	private String shift;
	private String product;
	private String lineNo;
	private String batchNo;
	private String sachet;
	private String bib_csp;
	private String netWeight;
	private String lsl;
	private String usl;
	private String slNo;
	private String refNo;
	private String issueDate;
	private String issueNo;
	
	

	
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	
	
	public QaPcroCheckListModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	
	
	public String getSL1() {
		return SL1;
	}

	public void setSL1(String SL1) {
		this.SL1 = SL1;
	}

	public String getPcroCheckListId() {
		return pcroCheckListId;
	}

	public void setPcroCheckListId(String pcroCheckListId) {
		this.pcroCheckListId = pcroCheckListId;
	}


	public String getV1() {
		return v1;
	}

	public void setV1(String v1) {
		this.v1 = v1;
	}

	public String getV2() {
		return v2;
	}

	public void setV2(String v2) {
		this.v2 = v2;
	}

	public String getV3() {
		return v3;
	}

	public void setV3(String v3) {
		this.v3 = v3;
	}

	public String getV4() {
		return v4;
	}

	public void setV4(String v4) {
		this.v4 = v4;
	}

	public String getV5() {
		return v5;
	}

	public void setV5(String v5) {
		this.v5 = v5;
	}

	public String getV6() {
		return v6;
	}

	public void setV6(String v6) {
		this.v6 = v6;
	}

	public String getV7() {
		return v7;
	}

	public void setV7(String v7) {
		this.v7 = v7;
	}

	public String getV8() {
		return v8;
	}

	public void setV8(String v8) {
		this.v8 = v8;
	}

	public String getV9() {
		return v9;
	}

	public void setV9(String v9) {
		this.v9 = v9;
	}

	public String getV10() {
		return v10;
	}

	public void setV10(String v10) {
		this.v10 = v10;
	}

	public String getPackingDate() {
		return packingDate;
	}

	public void setPackingDate(String packingDate) {
		this.packingDate = packingDate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	
	



	public String getSachet() {
		return sachet;
	}







	public void setSachet(String sachet) {
		this.sachet = sachet;
	}







	public String getBib_csp() {
		return bib_csp;
	}

	public void setBib_csp(String bib_csp) {
		this.bib_csp = bib_csp;
	}

	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getLsl() {
		return lsl;
	}

	public void setLsl(String lsl) {
		this.lsl = lsl;
	}

	public String getUsl() {
		return usl;
	}

	public void setUsl(String usl) {
		this.usl = usl;
	}
	
	
	

	public String getSlNo() {
		return slNo;
	}







	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}







	public String getRefNo() {
		return refNo;
	}







	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}







	public String getIssueDate() {
		return issueDate;
	}







	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}







	public String getIssueNo() {
		return issueNo;
	}







	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
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
