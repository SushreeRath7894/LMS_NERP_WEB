package nirmalya.aathithya.webmodule.employee.model;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

	
public class InsuranceAndNomineeWebModel {

	private String insuId;
	private String empId;
	private String empName;
	private String empDob;
	private String dept;
	private String empMob;
	private String email;
	private String nomId;
	private String nomName;
	private String nomDob;
	private String nomRelation;
	private String nomPerOfShare;
	private String dclrId;
	private String decName;
	private String decDob;
	private String decRelation;
	private String organization;
	private String orgDivision;
	private String financialYr;
	private String subDept;
	private String totalShare;
	
	
	private List<InsuranceAndNomineeWebModel> activity;
	private List<InsuranceAndNomineeWebModel> notification;
	
	
	
	
	public String getTotalShare() {
		return totalShare;
	}

	public void setTotalShare(String totalShare) {
		this.totalShare = totalShare;
	}

	public String getSubDept() {
		return subDept;
	}

	public void setSubDept(String subDept) {
		this.subDept = subDept;
	}

	public String getFinancialYr() {
		return financialYr;
	}

	public void setFinancialYr(String financialYr) {
		this.financialYr = financialYr;
	}

	public String getInsuId() {
		return insuId;
	}

	public void setInsuId(String insuId) {
		this.insuId = insuId;
	}

	public List<InsuranceAndNomineeWebModel> getActivity() {
		return activity;
	}

	public void setActivity(List<InsuranceAndNomineeWebModel> activity) {
		this.activity = activity;
	}

	public List<InsuranceAndNomineeWebModel> getNotification() {
		return notification;
	}

	public void setNotification(List<InsuranceAndNomineeWebModel> notification) {
		this.notification = notification;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}



	public String getEmpName() {
		return empName;
	}



	public void setEmpName(String empName) {
		this.empName = empName;
	}



	public String getEmpDob() {
		return empDob;
	}



	public void setEmpDob(String empDob) {
		this.empDob = empDob;
	}



	public String getDept() {
		return dept;
	}



	public void setDept(String dept) {
		this.dept = dept;
	}



	public String getEmpMob() {
		return empMob;
	}



	public void setEmpMob(String empMob) {
		this.empMob = empMob;
	}



	public String getNomId() {
		return nomId;
	}



	public void setNomId(String nomId) {
		this.nomId = nomId;
	}



	public String getNomName() {
		return nomName;
	}



	public void setNomName(String nomName) {
		this.nomName = nomName;
	}



	public String getNomDob() {
		return nomDob;
	}



	public void setNomDob(String nomDob) {
		this.nomDob = nomDob;
	}



	public String getNomRelation() {
		return nomRelation;
	}



	public void setNomRelation(String nomRelation) {
		this.nomRelation = nomRelation;
	}



	public String getNomPerOfShare() {
		return nomPerOfShare;
	}



	public void setNomPerOfShare(String nomPerOfShare) {
		this.nomPerOfShare = nomPerOfShare;
	}



	public String getDclrId() {
		return dclrId;
	}



	public void setDclrId(String dclrId) {
		this.dclrId = dclrId;
	}



	public String getDecName() {
		return decName;
	}



	public void setDecName(String decName) {
		this.decName = decName;
	}



	public String getDecDob() {
		return decDob;
	}



	public void setDecDob(String decDob) {
		this.decDob = decDob;
	}



	public String getDecRelation() {
		return decRelation;
	}



	public void setDecRelation(String decRelation) {
		this.decRelation = decRelation;
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

