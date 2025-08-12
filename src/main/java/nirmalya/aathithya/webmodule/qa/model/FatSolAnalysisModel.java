package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FatSolAnalysisModel {

	private String fatSolAnalysisId;
	private String date;
	private String product1;
	private String batchNo1;
	private String netWtG1;
	private String tWtCFlsk1;
	private String gWtCFlsk1;
	private String netWeightG1;
	private String gWtCFlskOven1;
	private String fatSlAIA1;

	private String product2;
	private String batchNo2;
	private String netWtG2;
	private String tWtCFlsk2;
	private String gWtCFlsk2;
	private String netWeightG2;
	private String gWtCFlskOven2;
	private String fatSlAIA2;

	private String product3;
	private String batchNo3;
	private String netWtG3;
	private String tWtCFlsk3;
	private String gWtCFlsk3;
	private String netWeightG3;
	private String gWtCFlskOven3;
	private String fatSlAIA3;

	private String product4;
	private String batchNo4;
	private String netWtG4;
	private String tWtCFlsk4;
	private String gWtCFlsk4;
	private String netWeightG4;
	private String gWtCFlskOven4;
	private String fatSlAIA4;

	private String rStatus;

	private String createdBy;
	private String createdOn;
	private String approvedDate;
	private String approvedBy;
	private String organization;
	private String orgDivision;

	private String refNo;
	private String issueNo;
	private String dateOfIssue;
	private String recordType1;
	private String recordType2;
	private String recordType3;
	private String recordType4;
	private String productTypeField;

	public FatSolAnalysisModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFatSolAnalysisId() {
		return fatSolAnalysisId;
	}

	public void setFatSolAnalysisId(String fatSolAnalysisId) {
		this.fatSolAnalysisId = fatSolAnalysisId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String gettWtCFlsk1() {
		return tWtCFlsk1;
	}

	public void settWtCFlsk1(String tWtCFlsk1) {
		this.tWtCFlsk1 = tWtCFlsk1;
	}

	public String getgWtCFlsk1() {
		return gWtCFlsk1;
	}

	public void setgWtCFlsk1(String gWtCFlsk1) {
		this.gWtCFlsk1 = gWtCFlsk1;
	}

	public String getNetWeightG1() {
		return netWeightG1;
	}

	public void setNetWeightG1(String netWeightG1) {
		this.netWeightG1 = netWeightG1;
	}

	public String getgWtCFlskOven1() {
		return gWtCFlskOven1;
	}

	public void setgWtCFlskOven1(String gWtCFlskOven1) {
		this.gWtCFlskOven1 = gWtCFlskOven1;
	}

	public String getFatSlAIA1() {
		return fatSlAIA1;
	}

	public void setFatSlAIA1(String fatSlAIA1) {
		this.fatSlAIA1 = fatSlAIA1;
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

	public String gettWtCFlsk2() {
		return tWtCFlsk2;
	}

	public void settWtCFlsk2(String tWtCFlsk2) {
		this.tWtCFlsk2 = tWtCFlsk2;
	}

	public String getgWtCFlsk2() {
		return gWtCFlsk2;
	}

	public void setgWtCFlsk2(String gWtCFlsk2) {
		this.gWtCFlsk2 = gWtCFlsk2;
	}

	public String getNetWeightG2() {
		return netWeightG2;
	}

	public void setNetWeightG2(String netWeightG2) {
		this.netWeightG2 = netWeightG2;
	}

	public String getgWtCFlskOven2() {
		return gWtCFlskOven2;
	}

	public void setgWtCFlskOven2(String gWtCFlskOven2) {
		this.gWtCFlskOven2 = gWtCFlskOven2;
	}

	public String getFatSlAIA2() {
		return fatSlAIA2;
	}

	public void setFatSlAIA2(String fatSlAIA2) {
		this.fatSlAIA2 = fatSlAIA2;
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

	public String gettWtCFlsk3() {
		return tWtCFlsk3;
	}

	public void settWtCFlsk3(String tWtCFlsk3) {
		this.tWtCFlsk3 = tWtCFlsk3;
	}

	public String getgWtCFlsk3() {
		return gWtCFlsk3;
	}

	public void setgWtCFlsk3(String gWtCFlsk3) {
		this.gWtCFlsk3 = gWtCFlsk3;
	}

	public String getNetWeightG3() {
		return netWeightG3;
	}

	public void setNetWeightG3(String netWeightG3) {
		this.netWeightG3 = netWeightG3;
	}

	public String getgWtCFlskOven3() {
		return gWtCFlskOven3;
	}

	public void setgWtCFlskOven3(String gWtCFlskOven3) {
		this.gWtCFlskOven3 = gWtCFlskOven3;
	}

	public String getFatSlAIA3() {
		return fatSlAIA3;
	}

	public void setFatSlAIA3(String fatSlAIA3) {
		this.fatSlAIA3 = fatSlAIA3;
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

	public String gettWtCFlsk4() {
		return tWtCFlsk4;
	}

	public void settWtCFlsk4(String tWtCFlsk4) {
		this.tWtCFlsk4 = tWtCFlsk4;
	}

	public String getgWtCFlsk4() {
		return gWtCFlsk4;
	}

	public void setgWtCFlsk4(String gWtCFlsk4) {
		this.gWtCFlsk4 = gWtCFlsk4;
	}

	public String getNetWeightG4() {
		return netWeightG4;
	}

	public void setNetWeightG4(String netWeightG4) {
		this.netWeightG4 = netWeightG4;
	}

	public String getgWtCFlskOven4() {
		return gWtCFlskOven4;
	}

	public void setgWtCFlskOven4(String gWtCFlskOven4) {
		this.gWtCFlskOven4 = gWtCFlskOven4;
	}

	public String getFatSlAIA4() {
		return fatSlAIA4;
	}

	public void setFatSlAIA4(String fatSlAIA4) {
		this.fatSlAIA4 = fatSlAIA4;
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

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getRecordType1() {
		return recordType1;
	}

	public void setRecordType1(String recordType1) {
		this.recordType1 = recordType1;
	}

	public String getRecordType2() {
		return recordType2;
	}

	public void setRecordType2(String recordType2) {
		this.recordType2 = recordType2;
	}

	public String getRecordType3() {
		return recordType3;
	}

	public void setRecordType3(String recordType3) {
		this.recordType3 = recordType3;
	}

	public String getRecordType4() {
		return recordType4;
	}

	public void setRecordType4(String recordType4) {
		this.recordType4 = recordType4;
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
