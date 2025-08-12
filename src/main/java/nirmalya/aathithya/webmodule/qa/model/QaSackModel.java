package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QaSackModel {

	private String sackId;
	private String slNo;
	private String length1;
	private String width1;
	private String weight1;
	private String gsm1;

	private String ppbag;
	private String pdate;
	private String supplier;
	private String invoice;
	private String grnno;
	private String invoice1;
	private String droptest;
	private String alkalitest;
	private String inktest;
	private String threadwarp;
	private String threadweft;
	private String length;
	private String width;
	private String gsm;
	private String weight;
	private String rejectionqa;
	private String remarks;

	private String createdBy;
	private String organization;
	private String orgDivision;

	private String reqid;
	private String sku;
	private String testRes;

	public QaSackModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String getRejectionqa() {
		return rejectionqa;
	}


	public void setRejectionqa(String rejectionqa) {
		this.rejectionqa = rejectionqa;
	}


	public String getSackId() {
		return sackId;
	}

	public void setSackId(String sackId) {
		this.sackId = sackId;
	}

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public String getLength1() {
		return length1;
	}

	public void setLength1(String length1) {
		this.length1 = length1;
	}

	public String getWidth1() {
		return width1;
	}

	public void setWidth1(String width1) {
		this.width1 = width1;
	}

	public String getWeight1() {
		return weight1;
	}

	public void setWeight1(String weight1) {
		this.weight1 = weight1;
	}

	public String getGsm1() {
		return gsm1;
	}

	public void setGsm1(String gsm1) {
		this.gsm1 = gsm1;
	}

	public String getPpbag() {
		return ppbag;
	}

	public void setPpbag(String ppbag) {
		this.ppbag = ppbag;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getDroptest() {
		return droptest;
	}

	public void setDroptest(String droptest) {
		this.droptest = droptest;
	}

	public String getAlkalitest() {
		return alkalitest;
	}

	public void setAlkalitest(String alkalitest) {
		this.alkalitest = alkalitest;
	}

	public String getInktest() {
		return inktest;
	}

	public void setInktest(String inktest) {
		this.inktest = inktest;
	}

	public String getThreadwarp() {
		return threadwarp;
	}

	public void setThreadwarp(String threadwarp) {
		this.threadwarp = threadwarp;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getGsm() {
		return gsm;
	}

	public void setGsm(String gsm) {
		this.gsm = gsm;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getInvoice1() {
		return invoice1;
	}

	public void setInvoice1(String invoice1) {
		this.invoice1 = invoice1;
	}

	public String getGrnno() {
		return grnno;
	}

	public void setGrnno(String grnno) {
		this.grnno = grnno;
	}

	public String getThreadweft() {
		return threadweft;
	}

	public void setThreadweft(String threadweft) {
		this.threadweft = threadweft;
	}

	public String getReqid() {
		return reqid;
	}

	public void setReqid(String reqid) {
		this.reqid = reqid;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getTestRes() {
		return testRes;
	}

	public void setTestRes(String testRes) {
		this.testRes = testRes;
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
