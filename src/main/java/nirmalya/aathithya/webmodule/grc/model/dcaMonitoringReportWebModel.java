package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class dcaMonitoringReportWebModel {
	
	private String monitorId;
	private String date;
	private String month;
	private String remarks;
	private String inputDate;
	
	private String empId;
	private String designation;
	private String month1;
	private String month2;
	private String month3;
	private String month4;
	private String month5;
	private String month6;
	private String month7;
	private String month8;
	private String month9;
	private String month10;
	private String month11;
	private String month12;
	private String totaldeepcompliance;
	private String deepcompliance;
	private String compliancestatus;
	
	
	private List<dcaMonitoringReportWebModel> grid1List;
	
	
	private String createdBy;
	private String organization;
	private String orgDivision;
	public String getMonitorId() {
		return monitorId;
	}
	public void setMonitorId(String monitorId) {
		this.monitorId = monitorId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	public String getInputDate() {
		return inputDate;
	}
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getMonth1() {
		return month1;
	}
	public void setMonth1(String month1) {
		this.month1 = month1;
	}
	public String getMonth2() {
		return month2;
	}
	public void setMonth2(String month2) {
		this.month2 = month2;
	}
	public String getMonth3() {
		return month3;
	}
	public void setMonth3(String month3) {
		this.month3 = month3;
	}
	public String getMonth4() {
		return month4;
	}
	public void setMonth4(String month4) {
		this.month4 = month4;
	}
	public String getMonth5() {
		return month5;
	}
	public void setMonth5(String month5) {
		this.month5 = month5;
	}
	public String getMonth6() {
		return month6;
	}
	public void setMonth6(String month6) {
		this.month6 = month6;
	}
	public String getMonth7() {
		return month7;
	}
	public void setMonth7(String month7) {
		this.month7 = month7;
	}
	public String getMonth8() {
		return month8;
	}
	public void setMonth8(String month8) {
		this.month8 = month8;
	}
	public String getMonth9() {
		return month9;
	}
	public void setMonth9(String month9) {
		this.month9 = month9;
	}
	public String getMonth10() {
		return month10;
	}
	public void setMonth10(String month10) {
		this.month10 = month10;
	}
	public String getMonth11() {
		return month11;
	}
	public void setMonth11(String month11) {
		this.month11 = month11;
	}
	public String getMonth12() {
		return month12;
	}
	public void setMonth12(String month12) {
		this.month12 = month12;
	}
	
	
	
	public String getTotaldeepcompliance() {
		return totaldeepcompliance;
	}
	public void setTotaldeepcompliance(String totaldeepcompliance) {
		this.totaldeepcompliance = totaldeepcompliance;
	}
	public String getDeepcompliance() {
		return deepcompliance;
	}
	public void setDeepcompliance(String deepcompliance) {
		this.deepcompliance = deepcompliance;
	}
	public String getCompliancestatus() {
		return compliancestatus;
	}
	public void setCompliancestatus(String compliancestatus) {
		this.compliancestatus = compliancestatus;
	}
	public List<dcaMonitoringReportWebModel> getGrid1List() {
		return grid1List;
	}
	public void setGrid1List(List<dcaMonitoringReportWebModel> grid1List) {
		this.grid1List = grid1List;
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
