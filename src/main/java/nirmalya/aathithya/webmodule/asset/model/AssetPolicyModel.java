package nirmalya.aathithya.webmodule.asset.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AssetPolicyModel {

	private String policyid;
	private String catid;
	private String assetsubcat;
	private String frequency;
	private String policyName;
	private String priority;
	private String description;
	private String assigndate;

	private String taskId;
	private String taskType;
	private String taskUOM;
	private String minRange;
	private String maxRange;
	
	private List<AssetPolicyModel> policyList;
	
	private String groupid;
	private String groupName;
	private String assetList;

	private String createdBy;
	private String organization;
	private String orgDivision;
	
	private String occRate;
	private String occSdate;
	private String occEdate;
	
	private String type;
	private String childType;
	
	private String logbookId;
	private String date;
	private String shift;
	private String powerCutTime;
	private String dghour1;
	private String mtReading1;
	private String dghour2;
	private String mtReading2;
	private String stock;
	private String remark;
	private String fromtime;
	private String totime;
	private String uploadBy;
	private String saltconsumption;
	private String waterreading;
	private String kwh;
	private String kvah;

	public AssetPolicyModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getTaskId() {
		return taskId;
	}


	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public String getAssigndate() {
		return assigndate;
	}


	public void setAssigndate(String assigndate) {
		this.assigndate = assigndate;
	}


	public List<AssetPolicyModel> getPolicyList() {
		return policyList;
	}


	public void setPolicyList(List<AssetPolicyModel> policyList) {
		this.policyList = policyList;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public String getPolicyid() {
		return policyid;
	}


	public void setPolicyid(String policyid) {
		this.policyid = policyid;
	}


	public String getCatid() {
		return catid;
	}


	public void setCatid(String catid) {
		this.catid = catid;
	}


	public String getAssetsubcat() {
		return assetsubcat;
	}


	public void setAssetsubcat(String assetsubcat) {
		this.assetsubcat = assetsubcat;
	}


	public String getFrequency() {
		return frequency;
	}


	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}


	public String getPolicyName() {
		return policyName;
	}


	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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

	public String getTaskType() {
		return taskType;
	}


	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}


	public String getTaskUOM() {
		return taskUOM;
	}


	public void setTaskUOM(String taskUOM) {
		this.taskUOM = taskUOM;
	}


	public String getMinRange() {
		return minRange;
	}


	public void setMinRange(String minRange) {
		this.minRange = minRange;
	}


	public String getMaxRange() {
		return maxRange;
	}


	public void setMaxRange(String maxRange) {
		this.maxRange = maxRange;
	}


	public String getGroupid() {
		return groupid;
	}


	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public String getAssetList() {
		return assetList;
	}


	public void setAssetList(String assetList) {
		this.assetList = assetList;
	}


	public String getOccRate() {
		return occRate;
	}


	public void setOccRate(String occRate) {
		this.occRate = occRate;
	}


	public String getOccSdate() {
		return occSdate;
	}


	public void setOccSdate(String occSdate) {
		this.occSdate = occSdate;
	}


	public String getOccEdate() {
		return occEdate;
	}


	public void setOccEdate(String occEdate) {
		this.occEdate = occEdate;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getChildType() {
		return childType;
	}


	public void setChildType(String childType) {
		this.childType = childType;
	}


	public String getLogbookId() {
		return logbookId;
	}


	public void setLogbookId(String logbookId) {
		this.logbookId = logbookId;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getShift() {
		return shift;
	}


	public void setShift(String shift) {
		this.shift = shift;
	}


	public String getPowerCutTime() {
		return powerCutTime;
	}


	public void setPowerCutTime(String powerCutTime) {
		this.powerCutTime = powerCutTime;
	}


	public String getDghour1() {
		return dghour1;
	}


	public void setDghour1(String dghour1) {
		this.dghour1 = dghour1;
	}


	public String getMtReading1() {
		return mtReading1;
	}


	public void setMtReading1(String mtReading1) {
		this.mtReading1 = mtReading1;
	}


	public String getDghour2() {
		return dghour2;
	}


	public void setDghour2(String dghour2) {
		this.dghour2 = dghour2;
	}


	public String getMtReading2() {
		return mtReading2;
	}


	public void setMtReading2(String mtReading2) {
		this.mtReading2 = mtReading2;
	}


	public String getStock() {
		return stock;
	}


	public void setStock(String stock) {
		this.stock = stock;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getFromtime() {
		return fromtime;
	}


	public void setFromtime(String fromtime) {
		this.fromtime = fromtime;
	}


	public String getTotime() {
		return totime;
	}


	public void setTotime(String totime) {
		this.totime = totime;
	}
	
	


	public String getUploadBy() {
		return uploadBy;
	}


	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}


	public String getSaltconsumption() {
		return saltconsumption;
	}


	public void setSaltconsumption(String saltconsumption) {
		this.saltconsumption = saltconsumption;
	}


	public String getWaterreading() {
		return waterreading;
	}


	public void setWaterreading(String waterreading) {
		this.waterreading = waterreading;
	}


	public String getKwh() {
		return kwh;
	}


	public void setKwh(String kwh) {
		this.kwh = kwh;
	}


	public String getKvah() {
		return kvah;
	}


	public void setKvah(String kvah) {
		this.kvah = kvah;
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
