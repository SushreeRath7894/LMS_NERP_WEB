package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
public class SalaryRevisionModel {

	private String editId;
	private String empId;
	private String name;
	private String fatherName;
	private String pdesg;
	private String ndesg;
	private String band;
	private String dept;
	private String subdept;
	private String createdBy;
	private String organization;
	private String orgDivision;
	private String joiningDate;
	private String effectiveFromDate;
	private String effectiveToDate;
	private String ctc;
	private String basic;
	private String providentFund;
	private String da;
	private String tds;
	private String hra;
	private String esi;
	private String convAllow;
	private String pTax;
	private String specialallowance;
	private String salAdv;
	private String skillDev;
	private String wFund;
	private String medAllow;
	private String insAmt;
	private String washAllow;
	private String lic;
	private String bonus;
	private String socy;
	private String overTime;
	private String fine;
	private String misc;
	private String damage;
	private String otherEarn;
	private String otherDeduct;
	private String totalEarn;
	private String totalDeduct;
	private String netPay;
	private String doj;
	private String updatedDate;
	private String salary_difference;
	

//	private String mBonus;
//	private String pfWages;
//	private String pensionWage;
//	private String wageChecking;
//	private String ptwage;
//	private String epsEmployer;
//	private String edliWage;
	private String esicWage;
//	private String yGratuity;
//	private String mGratuity;
	private String mEmployerPf;
	private String totalContribution;
	private String status;
	public SalaryRevisionModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSalary_difference() {
		return salary_difference;
	}
	public void setSalary_difference(String salary_difference) {
		this.salary_difference = salary_difference;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPdesg() {
		return pdesg;
	}
	public void setPdesg(String pdesg) {
		this.pdesg = pdesg;
	}
	public String getNdesg() {
		return ndesg;
	}
	public void setNdesg(String ndesg) {
		this.ndesg = ndesg;
	}
	
	public String getBand() {
		return band;
	}
	public void setBand(String band) {
		this.band = band;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public  void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getEditId() {
		return editId;
	}
	public void setEditId(String editId) {
		this.editId = editId;
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
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getSubdept() {
		return subdept;
	}
	public void setSubdept(String subdept) {
		this.subdept = subdept;
	}
	
	public String getCtc() {
		return ctc;
	}
	public void setCtc(String ctc) {
		this.ctc = ctc;
	}

	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}
	public String getEffectiveFromDate() {
		return effectiveFromDate;
	}
	public void setEffectiveFromDate(String effectiveFromDate) {
		this.effectiveFromDate = effectiveFromDate;
	}
	public String getEffectiveToDate() {
		return effectiveToDate;
	}
	public void setEffectiveToDate(String effectiveToDate) {
		this.effectiveToDate = effectiveToDate;
	}
	public String getBasic() {
		return basic;
	}
	public void setBasic(String basic) {
		this.basic = basic;
	}
	public String getProvidentFund() {
		return providentFund;
	}
	public void setProvidentFund(String providentFund) {
		this.providentFund = providentFund;
	}
	public String getDa() {
		return da;
	}
	public void setDa(String da) {
		this.da = da;
	}
	public String getTds() {
		return tds;
	}
	public void setTds(String tds) {
		this.tds = tds;
	}
	public String getHra() {
		return hra;
	}
	public void setHra(String hra) {
		this.hra = hra;
	}
	public String getEsi() {
		return esi;
	}
	public void setEsi(String esi) {
		this.esi = esi;
	}
	public String getConvAllow() {
		return convAllow;
	}
	public void setConvAllow(String convAllow) {
		this.convAllow = convAllow;
	}
	public String getpTax() {
		return pTax;
	}
	public void setpTax(String pTax) {
		this.pTax = pTax;
	}
	public String getSpecialallowance() {
		return specialallowance;
	}
	public void setSpecialallowance(String specialallowance) {
		this.specialallowance = specialallowance;
	}
	public String getSalAdv() {
		return salAdv;
	}
	public void setSalAdv(String salAdv) {
		this.salAdv = salAdv;
	}
	public String getSkillDev() {
		return skillDev;
	}
	public void setSkillDev(String skillDev) {
		this.skillDev = skillDev;
	}
	public String getwFund() {
		return wFund;
	}
	public void setwFund(String wFund) {
		this.wFund = wFund;
	}
	public String getMedAllow() {
		return medAllow;
	}
	public void setMedAllow(String medAllow) {
		this.medAllow = medAllow;
	}
	public String getInsAmt() {
		return insAmt;
	}
	public void setInsAmt(String insAmt) {
		this.insAmt = insAmt;
	}
	public String getWashAllow() {
		return washAllow;
	}
	public void setWashAllow(String washAllow) {
		this.washAllow = washAllow;
	}
	public String getLic() {
		return lic;
	}
	public void setLic(String lic) {
		this.lic = lic;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getSocy() {
		return socy;
	}
	public void setSocy(String socy) {
		this.socy = socy;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public String getFine() {
		return fine;
	}
	public void setFine(String fine) {
		this.fine = fine;
	}
	public String getMisc() {
		return misc;
	}
	public void setMisc(String misc) {
		this.misc = misc;
	}
	public String getDamage() {
		return damage;
	}
	public void setDamage(String damage) {
		this.damage = damage;
	}
	public String getOtherEarn() {
		return otherEarn;
	}
	public void setOtherEarn(String otherEarn) {
		this.otherEarn = otherEarn;
	}
	public String getOtherDeduct() {
		return otherDeduct;
	}
	public void setOtherDeduct(String otherDeduct) {
		this.otherDeduct = otherDeduct;
	}
	public String getTotalEarn() {
		return totalEarn;
	}
	public void setTotalEarn(String totalEarn) {
		this.totalEarn = totalEarn;
	}
	public String getTotalDeduct() {
		return totalDeduct;
	}
	public void setTotalDeduct(String totalDeduct) {
		this.totalDeduct = totalDeduct;
	}
	public String getNetPay() {
		return netPay;
	}
	public void setNetPay(String netPay) {
		this.netPay = netPay;
	}
	public String getEsicWage() {
		return esicWage;
	}
	public void setEsicWage(String esicWage) {
		this.esicWage = esicWage;
	}
	public String getmEmployerPf() {
		return mEmployerPf;
	}
	public void setmEmployerPf(String mEmployerPf) {
		this.mEmployerPf = mEmployerPf;
	}
	public String getTotalContribution() {
		return totalContribution;
	}
	public void setTotalContribution(String totalContribution) {
		this.totalContribution = totalContribution;
	}
	
	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
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
