package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectCropModel {

	private String cropId;
	private String cropName;
	private String cropDescription;
	private String cropStatus;
	private String createdBy;
	private String OrganizationName;
	private String OrganizationDivision;
	private String cropProcessId;
	private String activityId;
	private String taskId;
	private String variantId;
	private String qty;
	private String cropProcessDescription;
	private String amount;
	private String duration;
	private String unit;

	public String getCropId() {
		return cropId;
	}

	public void setCropId(String cropId) {
		this.cropId = cropId;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getCropDescription() {
		return cropDescription;
	}

	public void setCropDescription(String cropDescription) {
		this.cropDescription = cropDescription;
	}

	public String getCropStatus() {
		return cropStatus;
	}

	public void setCropStatus(String cropStatus) {
		this.cropStatus = cropStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getCropProcessId() {
		return cropProcessId;
	}

	public void setCropProcessId(String cropProcessId) {
		this.cropProcessId = cropProcessId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getCropProcessDescription() {
		return cropProcessDescription;
	}

	public void setCropProcessDescription(String cropProcessDescription) {
		this.cropProcessDescription = cropProcessDescription;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getVariantId() {
		return variantId;
	}

	public void setVariantId(String variantId) {
		this.variantId = variantId;
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
