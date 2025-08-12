package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PayrollModel {
	private String empId;
	private String empName;
	private String fatherName;
	private String bankAccountName;
	private String bankAccount;
	private String panNo;
	private String uanNo;
	private String esicNo;
	private String bandId;
	private String deptId;
	private String subDeptId;
	private String desig;
	private String dept;
	private String subDept;
	private String desigName;
	private String financialYr;
	private String fromDate;
	private String toDate;
	private String workDay;
	private String workingDay;

	private String basic;
	private String hra;
	private String addAll;
	private String lta;
	private String medical;
	private String specialAllowance;
	private String otherAllow;
	private String conve;
	private String washAllow;
	private String skillDev;
	private String bonus;
	private String reward;
	private String foodReim;
	private String arear;
	private String overTime;
	private String davda;
	private String miscEarning;
	private String totalEarning;

	private String empEPF;
	private String empESI;
	private String profTax;
	private String incTax;
	private String advance;
	private String welfund;
	private String lic;
	private String insurance;
	private String otherpenamnt;
	private String other;
	private String socy;
	private String totalDeduction;
	private String netPay;

	private String compEPF;
	private String compESI;

	private String annualSalary;
	private String salary;
	private String approveStatus;
	private String remarks;
	private String approvedBy;
	private String paymentStatus;
	private String present;
	private String leave;
	private String offday;
	private String attendance;
	private String adminCharge;
	private String edliCharge;
	private String total;
	private String days;
	
	public PayrollModel() {
		super();
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

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getUanNo() {
		return uanNo;
	}

	public void setUanNo(String uanNo) {
		this.uanNo = uanNo;
	}

	public String getBandId() {
		return bandId;
	}

	public void setBandId(String bandId) {
		this.bandId = bandId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getSubDeptId() {
		return subDeptId;
	}

	public void setSubDeptId(String subDeptId) {
		this.subDeptId = subDeptId;
	}

	public String getDesig() {
		return desig;
	}

	public void setDesig(String desig) {
		this.desig = desig;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getSubDept() {
		return subDept;
	}

	public void setSubDept(String subDept) {
		this.subDept = subDept;
	}

	public String getDesigName() {
		return desigName;
	}

	public void setDesigName(String desigName) {
		this.desigName = desigName;
	}

	public String getFinancialYr() {
		return financialYr;
	}

	public void setFinancialYr(String financialYr) {
		this.financialYr = financialYr;
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

	public String getWorkDay() {
		return workDay;
	}

	public void setWorkDay(String workDay) {
		this.workDay = workDay;
	}

	public String getWorkingDay() {
		return workingDay;
	}

	public void setWorkingDay(String workingDay) {
		this.workingDay = workingDay;
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

	public String getSpecialAllowance() {
		return specialAllowance;
	}

	public void setSpecialAllowance(String specialAllowance) {
		this.specialAllowance = specialAllowance;
	}

	public String getOtherAllow() {
		return otherAllow;
	}

	public void setOtherAllow(String otherAllow) {
		this.otherAllow = otherAllow;
	}

	public String getConve() {
		return conve;
	}

	public void setConve(String conve) {
		this.conve = conve;
	}

	public String getWashAllow() {
		return washAllow;
	}

	public void setWashAllow(String washAllow) {
		this.washAllow = washAllow;
	}

	public String getSkillDev() {
		return skillDev;
	}

	public void setSkillDev(String skillDev) {
		this.skillDev = skillDev;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getFoodReim() {
		return foodReim;
	}

	public void setFoodReim(String foodReim) {
		this.foodReim = foodReim;
	}

	public String getArear() {
		return arear;
	}

	public void setArear(String arear) {
		this.arear = arear;
	}

	public String getOverTime() {
		return overTime;
	}

	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}

	public String getDavda() {
		return davda;
	}

	public void setDavda(String davda) {
		this.davda = davda;
	}

	public String getMiscEarning() {
		return miscEarning;
	}

	public void setMiscEarning(String miscEarning) {
		this.miscEarning = miscEarning;
	}

	public String getTotalEarning() {
		return totalEarning;
	}

	public void setTotalEarning(String totalEarning) {
		this.totalEarning = totalEarning;
	}

	public String getEmpEPF() {
		return empEPF;
	}

	public void setEmpEPF(String empEPF) {
		this.empEPF = empEPF;
	}

	public String getEmpESI() {
		return empESI;
	}

	public void setEmpESI(String empESI) {
		this.empESI = empESI;
	}

	public String getProfTax() {
		return profTax;
	}

	public void setProfTax(String profTax) {
		this.profTax = profTax;
	}

	public String getIncTax() {
		return incTax;
	}

	public void setIncTax(String incTax) {
		this.incTax = incTax;
	}

	public String getAdvance() {
		return advance;
	}

	public void setAdvance(String advance) {
		this.advance = advance;
	}

	public String getWelfund() {
		return welfund;
	}

	public void setWelfund(String welfund) {
		this.welfund = welfund;
	}

	public String getLic() {
		return lic;
	}

	public void setLic(String lic) {
		this.lic = lic;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getOtherpenamnt() {
		return otherpenamnt;
	}

	public void setOtherpenamnt(String otherpenamnt) {
		this.otherpenamnt = otherpenamnt;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getSocy() {
		return socy;
	}

	public void setSocy(String socy) {
		this.socy = socy;
	}

	public String getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(String totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public String getNetPay() {
		return netPay;
	}

	public void setNetPay(String netPay) {
		this.netPay = netPay;
	}

	public String getCompEPF() {
		return compEPF;
	}

	public void setCompEPF(String compEPF) {
		this.compEPF = compEPF;
	}

	public String getCompESI() {
		return compESI;
	}

	public void setCompESI(String compESI) {
		this.compESI = compESI;
	}

	public String getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(String annualSalary) {
		this.annualSalary = annualSalary;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPresent() {
		return present;
	}

	public void setPresent(String present) {
		this.present = present;
	}

	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public String getOffday() {
		return offday;
	}

	public void setOffday(String offday) {
		this.offday = offday;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	public String getAdminCharge() {
		return adminCharge;
	}

	public void setAdminCharge(String adminCharge) {
		this.adminCharge = adminCharge;
	}

	public String getEdliCharge() {
		return edliCharge;
	}

	public void setEdliCharge(String edliCharge) {
		this.edliCharge = edliCharge;
	}

	public String getEsicNo() {
		return esicNo;
	}

	public void setEsicNo(String esicNo) {
		this.esicNo = esicNo;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
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
