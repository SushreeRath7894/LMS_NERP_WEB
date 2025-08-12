package nirmalya.aathithya.webmodule.weight.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebWeightBridgeModel {

	private String userId;
	private String slipNo;
	private String entryId;
	private String vehicleId;
	private String driverName;
	private String driverNo;
	// entry
	private String weightCarry;
	private String weightUnit;
	private String weightRemark;
	private String entryDate;
	private String entryTime;
	private String entryDateTime;
	// exit
	private String exitWeightCarry;
	private String exitWeightUnit;
	private String exitWeightRemark;
	private String exitDate;
	private String exitTime;
	private String exitDateTime;
	private String status;
	private String registerId;
	private String totalDuration;
	private String OrganizationName;
	private String OrganizationDivision;
	private String approvalStatus;
	private String exitApprovalStatus;
	private String pendingCount;
	private String totalCount;
	private String weightDifference;

	private String weighCharge;

	private String totalPageno;

	private String totalExit;
	private String totalSlip;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverNo() {
		return driverNo;
	}

	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}

	public String getWeightCarry() {
		return weightCarry;
	}

	public void setWeightCarry(String weightCarry) {
		this.weightCarry = weightCarry;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getWeightRemark() {
		return weightRemark;
	}

	public void setWeightRemark(String weightRemark) {
		this.weightRemark = weightRemark;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getEntryDateTime() {
		return entryDateTime;
	}

	public void setEntryDateTime(String entryDateTime) {
		this.entryDateTime = entryDateTime;
	}

	public String getExitWeightCarry() {
		return exitWeightCarry;
	}

	public void setExitWeightCarry(String exitWeightCarry) {
		this.exitWeightCarry = exitWeightCarry;
	}

	public String getExitWeightUnit() {
		return exitWeightUnit;
	}

	public void setExitWeightUnit(String exitWeightUnit) {
		this.exitWeightUnit = exitWeightUnit;
	}

	public String getExitWeightRemark() {
		return exitWeightRemark;
	}

	public void setExitWeightRemark(String exitWeightRemark) {
		this.exitWeightRemark = exitWeightRemark;
	}

	public String getExitDate() {
		return exitDate;
	}

	public void setExitDate(String exitDate) {
		this.exitDate = exitDate;
	}

	public String getExitTime() {
		return exitTime;
	}

	public void setExitTime(String exitTime) {
		this.exitTime = exitTime;
	}

	public String getExitDateTime() {
		return exitDateTime;
	}

	public void setExitDateTime(String exitDateTime) {
		this.exitDateTime = exitDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(String totalDuration) {
		this.totalDuration = totalDuration;
	}

	public String getOrganizationName() {
		return OrganizationName;
	}

	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}

	public String getOrganizationDivision() {
		return OrganizationDivision;
	}

	public void setOrganizationDivision(String organizationDivision) {
		OrganizationDivision = organizationDivision;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getExitApprovalStatus() {
		return exitApprovalStatus;
	}

	public void setExitApprovalStatus(String exitApprovalStatus) {
		this.exitApprovalStatus = exitApprovalStatus;
	}

	public String getPendingCount() {
		return pendingCount;
	}

	public void setPendingCount(String pendingCount) {
		this.pendingCount = pendingCount;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getWeightDifference() {
		return weightDifference;
	}

	public void setWeightDifference(String weightDifference) {
		this.weightDifference = weightDifference;
	}

	public String getWeighCharge() {
		return weighCharge;
	}

	public void setWeighCharge(String weighCharge) {
		this.weighCharge = weighCharge;
	}

	public String getSlipNo() {
		return slipNo;
	}

	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	public String getTotalPageno() {
		return totalPageno;
	}

	public void setTotalPageno(String totalPageno) {
		this.totalPageno = totalPageno;
	}

	public String getTotalExit() {
		return totalExit;
	}

	public void setTotalExit(String totalExit) {
		this.totalExit = totalExit;
	}

	public String getTotalSlip() {
		return totalSlip;
	}

	public void setTotalSlip(String totalSlip) {
		this.totalSlip = totalSlip;
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
