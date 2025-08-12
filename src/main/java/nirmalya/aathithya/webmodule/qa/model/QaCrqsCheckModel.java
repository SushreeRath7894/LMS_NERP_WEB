package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QaCrqsCheckModel {

	private String createdBy;
	private String organization;
	private String orgDivision;

	public QaCrqsCheckModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String slno;
	private String property;
	private String type;
	private String red1;
	private String amber1;

	private String red2;
	private String amber2;

	private String red3;
	private String amber3;

	private String red4;
	private String amber4;

	private String red5;
	private String amber5;
	
	private String crqscheckId;
	private String formtype;
	private String refno;
	private String issueno;
	private String issuedate;
	private String dop;
	private String sku;
	private String lineno;
	private String batchno;
	
	private String nodefectobserved1 ;
	private String nodefectobserved2 ;
	private String nodefectobserved3;
	private String nodefectobserved4;
	private String nodefectobserved5 ;
	private String nodefectobserved6;
	private String nodefectobserved7;
	private String nodefectobserved8 ;
	private String nodefectobserved9;
	private String nodefectobserved10;
	
	private String defectpercent1;
	private String defectpercent2;
	private String defectpercent3;
	private String defectpercent4;
	private String defectpercent5;
	private String defectpercent6;
	private String defectpercent7;
	private String defectpercent8;
	private String defectpercent9;
	private String defectpercent10;

	
	private String totalsamplechecked1;
	private String totalsamplechecked2;
	private String totalsamplechecked3 ;
	private String totalsamplechecked4;
	private String totalsamplechecked5;

	private String qcstatus1;
	private String qcstatus2 ;
	
	private String totaldefectvlta;
	private String totalquantityvltb ;
	private String rft ;
	
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


	public String getSlno() {
		return slno;
	}


	public void setSlno(String slno) {
		this.slno = slno;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getProperty() {
		return property;
	}


	public void setProperty(String property) {
		this.property = property;
	}


	public String getRed1() {
		return red1;
	}


	public void setRed1(String red1) {
		this.red1 = red1;
	}


	public String getAmber1() {
		return amber1;
	}


	public void setAmber1(String amber1) {
		this.amber1 = amber1;
	}


	public String getRed2() {
		return red2;
	}


	public void setRed2(String red2) {
		this.red2 = red2;
	}


	public String getAmber2() {
		return amber2;
	}


	public void setAmber2(String amber2) {
		this.amber2 = amber2;
	}


	public String getRed3() {
		return red3;
	}


	public void setRed3(String red3) {
		this.red3 = red3;
	}


	public String getAmber3() {
		return amber3;
	}


	public void setAmber3(String amber3) {
		this.amber3 = amber3;
	}


	public String getRed4() {
		return red4;
	}


	public void setRed4(String red4) {
		this.red4 = red4;
	}


	public String getAmber4() {
		return amber4;
	}


	public void setAmber4(String amber4) {
		this.amber4 = amber4;
	}


	public String getRed5() {
		return red5;
	}


	public void setRed5(String red5) {
		this.red5 = red5;
	}


	public String getAmber5() {
		return amber5;
	}


	public void setAmber5(String amber5) {
		this.amber5 = amber5;
	}


	public String getCrqscheckId() {
		return crqscheckId;
	}


	public void setCrqscheckId(String crqscheckId) {
		this.crqscheckId = crqscheckId;
	}


	public String getFormtype() {
		return formtype;
	}


	public void setFormtype(String formtype) {
		this.formtype = formtype;
	}


	public String getRefno() {
		return refno;
	}


	public void setRefno(String refno) {
		this.refno = refno;
	}


	public String getIssueno() {
		return issueno;
	}


	public void setIssueno(String issueno) {
		this.issueno = issueno;
	}


	public String getIssuedate() {
		return issuedate;
	}


	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}


	public String getDop() {
		return dop;
	}


	public void setDop(String dop) {
		this.dop = dop;
	}


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
	}


	public String getLineno() {
		return lineno;
	}


	public void setLineno(String lineno) {
		this.lineno = lineno;
	}


	public String getBatchno() {
		return batchno;
	}


	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}


	public String getNodefectobserved1() {
		return nodefectobserved1;
	}


	public void setNodefectobserved1(String nodefectobserved1) {
		this.nodefectobserved1 = nodefectobserved1;
	}


	public String getNodefectobserved2() {
		return nodefectobserved2;
	}


	public void setNodefectobserved2(String nodefectobserved2) {
		this.nodefectobserved2 = nodefectobserved2;
	}


	public String getNodefectobserved3() {
		return nodefectobserved3;
	}


	public void setNodefectobserved3(String nodefectobserved3) {
		this.nodefectobserved3 = nodefectobserved3;
	}


	public String getNodefectobserved4() {
		return nodefectobserved4;
	}


	public void setNodefectobserved4(String nodefectobserved4) {
		this.nodefectobserved4 = nodefectobserved4;
	}


	public String getNodefectobserved5() {
		return nodefectobserved5;
	}


	public void setNodefectobserved5(String nodefectobserved5) {
		this.nodefectobserved5 = nodefectobserved5;
	}


	public String getNodefectobserved6() {
		return nodefectobserved6;
	}


	public void setNodefectobserved6(String nodefectobserved6) {
		this.nodefectobserved6 = nodefectobserved6;
	}


	public String getNodefectobserved7() {
		return nodefectobserved7;
	}


	public void setNodefectobserved7(String nodefectobserved7) {
		this.nodefectobserved7 = nodefectobserved7;
	}


	public String getNodefectobserved8() {
		return nodefectobserved8;
	}


	public void setNodefectobserved8(String nodefectobserved8) {
		this.nodefectobserved8 = nodefectobserved8;
	}


	public String getNodefectobserved9() {
		return nodefectobserved9;
	}


	public void setNodefectobserved9(String nodefectobserved9) {
		this.nodefectobserved9 = nodefectobserved9;
	}


	public String getNodefectobserved10() {
		return nodefectobserved10;
	}


	public void setNodefectobserved10(String nodefectobserved10) {
		this.nodefectobserved10 = nodefectobserved10;
	}


	public String getDefectpercent1() {
		return defectpercent1;
	}


	public void setDefectpercent1(String defectpercent1) {
		this.defectpercent1 = defectpercent1;
	}


	public String getDefectpercent2() {
		return defectpercent2;
	}


	public void setDefectpercent2(String defectpercent2) {
		this.defectpercent2 = defectpercent2;
	}


	public String getDefectpercent3() {
		return defectpercent3;
	}


	public void setDefectpercent3(String defectpercent3) {
		this.defectpercent3 = defectpercent3;
	}


	public String getDefectpercent4() {
		return defectpercent4;
	}


	public void setDefectpercent4(String defectpercent4) {
		this.defectpercent4 = defectpercent4;
	}


	public String getDefectpercent5() {
		return defectpercent5;
	}


	public void setDefectpercent5(String defectpercent5) {
		this.defectpercent5 = defectpercent5;
	}


	public String getDefectpercent6() {
		return defectpercent6;
	}


	public void setDefectpercent6(String defectpercent6) {
		this.defectpercent6 = defectpercent6;
	}


	public String getDefectpercent7() {
		return defectpercent7;
	}


	public void setDefectpercent7(String defectpercent7) {
		this.defectpercent7 = defectpercent7;
	}


	public String getDefectpercent8() {
		return defectpercent8;
	}


	public void setDefectpercent8(String defectpercent8) {
		this.defectpercent8 = defectpercent8;
	}


	public String getDefectpercent9() {
		return defectpercent9;
	}


	public void setDefectpercent9(String defectpercent9) {
		this.defectpercent9 = defectpercent9;
	}


	public String getDefectpercent10() {
		return defectpercent10;
	}


	public void setDefectpercent10(String defectpercent10) {
		this.defectpercent10 = defectpercent10;
	}


	public String getTotalsamplechecked1() {
		return totalsamplechecked1;
	}


	public void setTotalsamplechecked1(String totalsamplechecked1) {
		this.totalsamplechecked1 = totalsamplechecked1;
	}


	public String getTotalsamplechecked2() {
		return totalsamplechecked2;
	}


	public void setTotalsamplechecked2(String totalsamplechecked2) {
		this.totalsamplechecked2 = totalsamplechecked2;
	}


	public String getTotalsamplechecked3() {
		return totalsamplechecked3;
	}


	public void setTotalsamplechecked3(String totalsamplechecked3) {
		this.totalsamplechecked3 = totalsamplechecked3;
	}


	public String getTotalsamplechecked4() {
		return totalsamplechecked4;
	}


	public void setTotalsamplechecked4(String totalsamplechecked4) {
		this.totalsamplechecked4 = totalsamplechecked4;
	}


	public String getTotalsamplechecked5() {
		return totalsamplechecked5;
	}


	public void setTotalsamplechecked5(String totalsamplechecked5) {
		this.totalsamplechecked5 = totalsamplechecked5;
	}


	public String getQcstatus1() {
		return qcstatus1;
	}


	public void setQcstatus1(String qcstatus1) {
		this.qcstatus1 = qcstatus1;
	}


	public String getQcstatus2() {
		return qcstatus2;
	}


	public void setQcstatus2(String qcstatus2) {
		this.qcstatus2 = qcstatus2;
	}


	public String getTotaldefectvlta() {
		return totaldefectvlta;
	}


	public void setTotaldefectvlta(String totaldefectvlta) {
		this.totaldefectvlta = totaldefectvlta;
	}


	public String getTotalquantityvltb() {
		return totalquantityvltb;
	}


	public void setTotalquantityvltb(String totalquantityvltb) {
		this.totalquantityvltb = totalquantityvltb;
	}


	public String getRft() {
		return rft;
	}


	public void setRft(String rft) {
		this.rft = rft;
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
