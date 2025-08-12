package nirmalya.aathithya.webmodule.employee.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExitFinancialSettelmentModel {

	private String financeId;
	private String clearanceId;
	private String employeeId;
	private String empName;
	private String empDepartment;
	private String manager;
	private Double salary;
	private String noticePeriod;
	private Double bonus;
	private Double other;
	private String comment;
	private String recovery;
	private String action;
	private String empDepartmentName;

	private String exitId;
	private String settlementId;
	private String basicAmount;
	private String hraAmount;
	private String conAllowanceAmount;
	private String washAllowanceAmount;
	private String totalEarning;
	private String epfAmount;
	private String esicAmount;
	private String professionalTax;
	private String salaryAdvance;
	private String totalDeduction;
	private String netAmount;
	private String bonusAmount;
	private String leaveAmount;
	private String otherAmount;
	private String totalAmount;
	private String financeStatus;
	private String createdBy;
	private String organization;
	private String orgDivision;

	public ExitFinancialSettelmentModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getClearanceId() {
		return clearanceId;
	}

	public void setClearanceId(String clearanceId) {
		this.clearanceId = clearanceId;
	}

	public String getFinanceId() {
		return financeId;
	}

	public void setFinanceId(String financeId) {
		this.financeId = financeId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpDepartment() {
		return empDepartment;
	}

	public void setEmpDepartment(String empDepartment) {
		this.empDepartment = empDepartment;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public Double getOther() {
		return other;
	}

	public void setOther(Double other) {
		this.other = other;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRecovery() {
		return recovery;
	}

	public void setRecovery(String recovery) {
		this.recovery = recovery;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getEmpDepartmentName() {
		return empDepartmentName;
	}

	public void setEmpDepartmentName(String empDepartmentName) {
		this.empDepartmentName = empDepartmentName;
	}

	public String getExitId() {
		return exitId;
	}

	public void setExitId(String exitId) {
		this.exitId = exitId;
	}

	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}

	public String getBasicAmount() {
		return basicAmount;
	}

	public void setBasicAmount(String basicAmount) {
		this.basicAmount = basicAmount;
	}

	public String getHraAmount() {
		return hraAmount;
	}

	public void setHraAmount(String hraAmount) {
		this.hraAmount = hraAmount;
	}

	public String getConAllowanceAmount() {
		return conAllowanceAmount;
	}

	public void setConAllowanceAmount(String conAllowanceAmount) {
		this.conAllowanceAmount = conAllowanceAmount;
	}

	public String getWashAllowanceAmount() {
		return washAllowanceAmount;
	}

	public void setWashAllowanceAmount(String washAllowanceAmount) {
		this.washAllowanceAmount = washAllowanceAmount;
	}

	public String getTotalEarning() {
		return totalEarning;
	}

	public void setTotalEarning(String totalEarning) {
		this.totalEarning = totalEarning;
	}

	public String getEpfAmount() {
		return epfAmount;
	}

	public void setEpfAmount(String epfAmount) {
		this.epfAmount = epfAmount;
	}

	public String getEsicAmount() {
		return esicAmount;
	}

	public void setEsicAmount(String esicAmount) {
		this.esicAmount = esicAmount;
	}

	public String getProfessionalTax() {
		return professionalTax;
	}

	public void setProfessionalTax(String professionalTax) {
		this.professionalTax = professionalTax;
	}

	public String getSalaryAdvance() {
		return salaryAdvance;
	}

	public void setSalaryAdvance(String salaryAdvance) {
		this.salaryAdvance = salaryAdvance;
	}

	public String getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(String totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(String bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public String getLeaveAmount() {
		return leaveAmount;
	}

	public void setLeaveAmount(String leaveAmount) {
		this.leaveAmount = leaveAmount;
	}

	public String getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(String otherAmount) {
		this.otherAmount = otherAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
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
