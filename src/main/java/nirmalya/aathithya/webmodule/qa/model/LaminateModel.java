package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LaminateModel {

	private String laminateId;
	private String pdate;
	private String mrp;
	private String pkd;
	private String invoiceQty;
	private String grnNo;
	private String suppliers;
	private String lamiFfsRoll;
	private String invoiceDate;
	private String performanceTest;
	private String pouchesTest;
	private String laminatationTest;
	private String colourTest;
	private String printQualityTest;
	private String odourinTest;
	private String heatEnduranceTest;
	private String dartImpact;
	private String tearStrengthCd;
	private String tearStrengthMd;
	private String dynamicface;
	private String dynamicreverse;
	private String cofstatic;
	private String staticface;
	private String tensilcd;
	private String strengthmd;
	private String afterpacking;
	private String sealStrength;
	private String laminationStrengh;
	private String avgLength;
	private String avgWidth;
	private String avgThicknes;
	private String avgGsm;
	private String avgWeight;
	private String rejection;
	private String remarks;
	private String lengthmm;
	private String widthmm;
	private String weightgms;
	private String gsmgm;
	private String thicknessMicron;
	private String organization;
	private String orgDivision;
	private String createdBy;

	private String reqid;
	private String sku;
	private String testRes;
	private String slNo;

	public LaminateModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLaminateId() {
		return laminateId;
	}

	public void setLaminateId(String laminateId) {
		this.laminateId = laminateId;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getMrp() {
		return mrp;
	}

	public void setMrp(String mrp) {
		this.mrp = mrp;
	}

	public String getPkd() {
		return pkd;
	}

	public void setPkd(String pkd) {
		this.pkd = pkd;
	}

	public String getInvoiceQty() {
		return invoiceQty;
	}

	public void setInvoiceQty(String invoiceQty) {
		this.invoiceQty = invoiceQty;
	}

	public String getGrnNo() {
		return grnNo;
	}

	public void setGrnNo(String grnNo) {
		this.grnNo = grnNo;
	}

	public String getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(String suppliers) {
		this.suppliers = suppliers;
	}

	public String getLamiFfsRoll() {
		return lamiFfsRoll;
	}

	public void setLamiFfsRoll(String lamiFfsRoll) {
		this.lamiFfsRoll = lamiFfsRoll;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getPerformanceTest() {
		return performanceTest;
	}

	public void setPerformanceTest(String performanceTest) {
		this.performanceTest = performanceTest;
	}

	public String getPouchesTest() {
		return pouchesTest;
	}

	public void setPouchesTest(String pouchesTest) {
		this.pouchesTest = pouchesTest;
	}

	public String getLaminatationTest() {
		return laminatationTest;
	}

	public void setLaminatationTest(String laminatationTest) {
		this.laminatationTest = laminatationTest;
	}

	public String getColourTest() {
		return colourTest;
	}

	public void setColourTest(String colourTest) {
		this.colourTest = colourTest;
	}

	public String getPrintQualityTest() {
		return printQualityTest;
	}

	public void setPrintQualityTest(String printQualityTest) {
		this.printQualityTest = printQualityTest;
	}

	public String getOdourinTest() {
		return odourinTest;
	}

	public void setOdourinTest(String odourinTest) {
		this.odourinTest = odourinTest;
	}

	public String getHeatEnduranceTest() {
		return heatEnduranceTest;
	}

	public void setHeatEnduranceTest(String heatEnduranceTest) {
		this.heatEnduranceTest = heatEnduranceTest;
	}

	public String getDartImpact() {
		return dartImpact;
	}

	public void setDartImpact(String dartImpact) {
		this.dartImpact = dartImpact;
	}

	public String getTearStrengthCd() {
		return tearStrengthCd;
	}

	public void setTearStrengthCd(String tearStrengthCd) {
		this.tearStrengthCd = tearStrengthCd;
	}

	public String getTearStrengthMd() {
		return tearStrengthMd;
	}

	public void setTearStrengthMd(String tearStrengthMd) {
		this.tearStrengthMd = tearStrengthMd;
	}

	public String getSealStrength() {
		return sealStrength;
	}

	public void setSealStrength(String sealStrength) {
		this.sealStrength = sealStrength;
	}

	public String getLaminationStrengh() {
		return laminationStrengh;
	}

	public void setLaminationStrengh(String laminationStrengh) {
		this.laminationStrengh = laminationStrengh;
	}

	public String getAvgLength() {
		return avgLength;
	}

	public void setAvgLength(String avgLength) {
		this.avgLength = avgLength;
	}

	public String getAvgWidth() {
		return avgWidth;
	}

	public void setAvgWidth(String avgWidth) {
		this.avgWidth = avgWidth;
	}

	public String getAvgThicknes() {
		return avgThicknes;
	}

	public void setAvgThicknes(String avgThicknes) {
		this.avgThicknes = avgThicknes;
	}

	public String getAvgGsm() {
		return avgGsm;
	}

	public void setAvgGsm(String avgGsm) {
		this.avgGsm = avgGsm;
	}

	public String getAvgWeight() {
		return avgWeight;
	}

	public void setAvgWeight(String avgWeight) {
		this.avgWeight = avgWeight;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLengthmm() {
		return lengthmm;
	}

	public void setLengthmm(String lengthmm) {
		this.lengthmm = lengthmm;
	}

	public String getWidthmm() {
		return widthmm;
	}

	public void setWidthmm(String widthmm) {
		this.widthmm = widthmm;
	}

	public String getWeightgms() {
		return weightgms;
	}

	public void setWeightgms(String weightgms) {
		this.weightgms = weightgms;
	}

	public String getGsmgm() {
		return gsmgm;
	}

	public void setGsmgm(String gsmgm) {
		this.gsmgm = gsmgm;
	}

	public String getThicknessMicron() {
		return thicknessMicron;
	}

	public void setThicknessMicron(String thicknessMicron) {
		this.thicknessMicron = thicknessMicron;
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

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}
	

	public String getDynamicface() {
		return dynamicface;
	}

	public void setDynamicface(String dynamicface) {
		this.dynamicface = dynamicface;
	}

	public String getDynamicreverse() {
		return dynamicreverse;
	}

	public void setDynamicreverse(String dynamicreverse) {
		this.dynamicreverse = dynamicreverse;
	}

	public String getCofstatic() {
		return cofstatic;
	}

	public void setCofstatic(String cofstatic) {
		this.cofstatic = cofstatic;
	}

	public String getTensilcd() {
		return tensilcd;
	}

	public void setTensilcd(String tensilcd) {
		this.tensilcd = tensilcd;
	}

	public String getStrengthmd() {
		return strengthmd;
	}

	public void setStrengthmd(String strengthmd) {
		this.strengthmd = strengthmd;
	}

	public String getAfterpacking() {
		return afterpacking;
	}

	public void setAfterpacking(String afterpacking) {
		this.afterpacking = afterpacking;
	}

	public String getRejection() {
		return rejection;
	}

	public void setRejection(String rejection) {
		this.rejection = rejection;
	}
	

	public String getStaticface() {
		return staticface;
	}

	public void setStaticface(String staticface) {
		this.staticface = staticface;
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
