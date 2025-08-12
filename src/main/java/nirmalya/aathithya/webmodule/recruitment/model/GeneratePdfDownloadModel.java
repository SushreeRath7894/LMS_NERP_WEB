package nirmalya.aathithya.webmodule.recruitment.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GeneratePdfDownloadModel {

	

	private String empId;
	private String fname;
	private String lname;
	private String basic;
	private String hra;
	private String addAll;
	private String total1;
	private String lta;
	private String medical;
	private String total2;
	private String variable;
	private String total3;
	private String empEpf;
	private String gratuity;
	private String inc;
	private String total4;
	private String monthlyCompensation;
	private String yearlyCompensation;
	private String costtoCompany;
	
	
	public GeneratePdfDownloadModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getEmpId() {
		return empId;
	}


	public void setEmpId(String empId) {
		this.empId = empId;
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getBasic() {
		return basic;
	}


	public void setBasic(String basic) {
		this.basic = basic;
	}


	public String getHra() {
		return hra;
	}


	public void setHra(String hra) {
		this.hra = hra;
	}


	public String getAddAll() {
		return addAll;
	}


	public void setAddAll(String addAll) {
		this.addAll = addAll;
	}


	public String getTotal1() {
		return total1;
	}


	public void setTotal1(String total1) {
		this.total1 = total1;
	}


	public String getLta() {
		return lta;
	}


	public void setLta(String lta) {
		this.lta = lta;
	}


	public String getMedical() {
		return medical;
	}


	public void setMedical(String medical) {
		this.medical = medical;
	}


	public String getTotal2() {
		return total2;
	}


	public void setTotal2(String total2) {
		this.total2 = total2;
	}


	public String getVariable() {
		return variable;
	}


	public void setVariable(String variable) {
		this.variable = variable;
	}


	public String getTotal3() {
		return total3;
	}


	public void setTotal3(String total3) {
		this.total3 = total3;
	}


	public String getEmpEpf() {
		return empEpf;
	}


	public void setEmpEpf(String empEpf) {
		this.empEpf = empEpf;
	}


	public String getGratuity() {
		return gratuity;
	}


	public void setGratuity(String gratuity) {
		this.gratuity = gratuity;
	}


	public String getInc() {
		return inc;
	}


	public void setInc(String inc) {
		this.inc = inc;
	}


	public String getTotal4() {
		return total4;
	}


	public void setTotal4(String total4) {
		this.total4 = total4;
	}


	public String getMonthlyCompensation() {
		return monthlyCompensation;
	}


	public void setMonthlyCompensation(String monthlyCompensation) {
		this.monthlyCompensation = monthlyCompensation;
	}


	public String getYearlyCompensation() {
		return yearlyCompensation;
	}


	public void setYearlyCompensation(String yearlyCompensation) {
		this.yearlyCompensation = yearlyCompensation;
	}


	public String getCosttoCompany() {
		return costtoCompany;
	}


	public void setCosttoCompany(String costtoCompany) {
		this.costtoCompany = costtoCompany;
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
