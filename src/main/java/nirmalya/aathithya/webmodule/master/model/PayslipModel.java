package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PayslipModel {
	private String employeeNo;
	private String month;
	private String fromDate;
	private String toDate;
	
	private String location;
	private String name;
	private String department;
	private String bankName;
	private String designation;
	private String bankAccountno;
	private String daysinMonth;
	private String esicNo;
	private String lop;
	private String pfuan;
	private String effectiveWorkdays;
	private String panNo;
	private String dob;
	
	private String basic;
	private String hra;
	private String additionalallowance;
	private String medical;
	private String lta;
	private String variablepay;
	private String totalearning;
	private String pf;
	private String proftax;
	private String incometax;
	private String esic;
	private String bonus;
	private String others;
	private String totalDeductions;
	private String totalpay;
	private String conve;
	private String advance;
	private String organization;
	private String orgaDivision;
	
	private String orgLogo;
	private String orgSign;
	private String orgStamp;
	
	private String otherAllow;
	private String arear;
	private String reward;
	private String lic;
	private String wFund;
	private String penalty;
	private String otherDeduct;
	private String medicalAllow;
	private String washAllow;
	private String skillAllow;
	private String specialAllow;
	private String doj;
	private String pfmId;
	private String weekOff;
	private String gender;
	
	public PayslipModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getBankAccountno() {
		return bankAccountno;
	}
	public void setBankAccountno(String bankAccountno) {
		this.bankAccountno = bankAccountno;
	}
	public String getDaysinMonth() {
		return daysinMonth;
	}
	public void setDaysinMonth(String daysinMonth) {
		this.daysinMonth = daysinMonth;
	}
	public String getEsicNo() {
		return esicNo;
	}
	public void setEsicNo(String esicNo) {
		this.esicNo = esicNo;
	}
	public String getLop() {
		return lop;
	}
	public void setLop(String lop) {
		this.lop = lop;
	}
	public String getPfuan() {
		return pfuan;
	}
	public void setPfuan(String pfuan) {
		this.pfuan = pfuan;
	}
	public String getEffectiveWorkdays() {
		return effectiveWorkdays;
	}
	public void setEffectiveWorkdays(String effectiveWorkdays) {
		this.effectiveWorkdays = effectiveWorkdays;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
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
	public String getAdditionalallowance() {
		return additionalallowance;
	}
	public void setAdditionalallowance(String additionalallowance) {
		this.additionalallowance = additionalallowance;
	}
	public String getMedical() {
		return medical;
	}
	public void setMedical(String medical) {
		this.medical = medical;
	}
	public String getLta() {
		return lta;
	}
	public void setLta(String lta) {
		this.lta = lta;
	}
	public String getVariablepay() {
		return variablepay;
	}
	public void setVariablepay(String variablepay) {
		this.variablepay = variablepay;
	}
	public String getTotalearning() {
		return totalearning;
	}
	public void setTotalearning(String totalearning) {
		this.totalearning = totalearning;
	}
	public String getPf() {
		return pf;
	}
	public void setPf(String pf) {
		this.pf = pf;
	}
	public String getProftax() {
		return proftax;
	}
	public void setProftax(String proftax) {
		this.proftax = proftax;
	}
	public String getIncometax() {
		return incometax;
	}
	public void setIncometax(String incometax) {
		this.incometax = incometax;
	}
	public String getEsic() {
		return esic;
	}
	public void setEsic(String esic) {
		this.esic = esic;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	public String getTotalDeductions() {
		return totalDeductions;
	}
	public void setTotalDeductions(String totalDeductions) {
		this.totalDeductions = totalDeductions;
	}
	public String getTotalpay() {
		return totalpay;
	}
	public void setTotalpay(String totalpay) {
		this.totalpay = totalpay;
	}
	
	public String getConve() {
		return conve;
	}
	public void setConve(String conve) {
		this.conve = conve;
	}
	
	public String getAdvance() {
		return advance;
	}
	public void setAdvance(String advance) {
		this.advance = advance;
	}
	
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getOrgaDivision() {
		return orgaDivision;
	}
	public void setOrgaDivision(String orgaDivision) {
		this.orgaDivision = orgaDivision;
	}
	
	public String getOrgLogo() {
		return orgLogo;
	}
	public void setOrgLogo(String orgLogo) {
		this.orgLogo = orgLogo;
	}
	public String getOrgSign() {
		return orgSign;
	}
	public void setOrgSign(String orgSign) {
		this.orgSign = orgSign;
	}
	public String getOrgStamp() {
		return orgStamp;
	}
	public void setOrgStamp(String orgStamp) {
		this.orgStamp = orgStamp;
	}
	public String getOtherAllow() {
		return otherAllow;
	}
	public void setOtherAllow(String otherAllow) {
		this.otherAllow = otherAllow;
	}
	public String getArear() {
		return arear;
	}
	public void setArear(String arear) {
		this.arear = arear;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public String getLic() {
		return lic;
	}
	public void setLic(String lic) {
		this.lic = lic;
	}
	public String getwFund() {
		return wFund;
	}
	public void setwFund(String wFund) {
		this.wFund = wFund;
	}
	public String getPenalty() {
		return penalty;
	}
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}
	public String getOtherDeduct() {
		return otherDeduct;
	}
	public void setOtherDeduct(String otherDeduct) {
		this.otherDeduct = otherDeduct;
	}
	public String getMedicalAllow() {
		return medicalAllow;
	}
	public void setMedicalAllow(String medicalAllow) {
		this.medicalAllow = medicalAllow;
	}
	public String getWashAllow() {
		return washAllow;
	}
	public void setWashAllow(String washAllow) {
		this.washAllow = washAllow;
	}
	public String getSkillAllow() {
		return skillAllow;
	}
	public void setSkillAllow(String skillAllow) {
		this.skillAllow = skillAllow;
	}
	public String getSpecialAllow() {
		return specialAllow;
	}
	public void setSpecialAllow(String specialAllow) {
		this.specialAllow = specialAllow;
	}
	
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	
	
	public String getPfmId() {
		return pfmId;
	}
	public void setPfmId(String pfmId) {
		this.pfmId = pfmId;
	}
	public String getWeekOff() {
		return weekOff;
	}
	public void setWeekOff(String weekOff) {
		this.weekOff = weekOff;
	}
	
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
