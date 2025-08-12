package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EntityMasterModel {

	private String organization;
	private String orgDivision;
	
////////Cost Nature Type////////
	
	

	private String costnatureId;
	private String costnatureName;
	private String costnatureDesc;
	private String costnatureStatus;
	private String costnatureCreatedOn;
	private String costnatureCreatedBy;
	private String costnatureModifiedBy;
	private String costnatureUpdatedOn;

////////Cost Level Type////////
	private String costLabourId;
	private String costLabourName;
	private String costLabourDesc;
	private String costLabourStatus;
	private String costLabourCreatedOn;
	private String costLabourCreatedBy;
	private String costLabourModifiedBy;
	private String costLabourUpdatedOn;

////////Vendor Catagory Type////////

	private String vendorCategoryId;
	private String vendorCategoryName;
	private String vendorCategoryDesc;
	private String vendorCategoryStatus;
	private String vendorCategoryCreatedOn;
	private String vendorCategoryCreatedBy;
	private String vendorCategoryModifiedBy;
	private String vendorCategoryUpdatedOn;

////////Location Type////////

	private String locationTypeId;
	private String locationTypeName;
	private String locationTypeDesc;
	private String locationTypeStatus;
	private String locationTypeCreatedOn;
	private String locationTypeCreatedBy;
	private String locationTypeModifiedBy;
	private String locationTypeUpdatedOn;

////////Room Type////////

	private String roomTypeId;
	private String roomTypeName;
	private String roomTypeDesc;
	private String roomTypeStatus;
	private String roomTypeCreatedOn;
	private String roomTypeCreatedBy;
	private String roomTypeModifiedBy;
	private String roomTypeUpdatedOn;

	// Vendor type Master

	private String vendorTypeId;
	private String vendorCategoryTypeId;
	private String vendorCategoryTypeName;
	private String vendorTypeName;
	private String vendorTypeDesc;
	private String vendorTypeStatus;
	private String vendorTypeCreatedOn;
	private String vendorTypeCreatedBy;
	private String vendorTypeModifiedBy;
	private String vendorTypeUpdatedOn;

	public String getCostnatureId() {
		return costnatureId;
	}

	public void setCostnatureId(String costnatureId) {
		this.costnatureId = costnatureId;
	}

	public String getCostnatureName() {
		return costnatureName;
	}

	public void setCostnatureName(String costnatureName) {
		this.costnatureName = costnatureName;
	}

	public String getCostnatureDesc() {
		return costnatureDesc;
	}

	public void setCostnatureDesc(String costnatureDesc) {
		this.costnatureDesc = costnatureDesc;
	}

	public String getCostnatureStatus() {
		return costnatureStatus;
	}

	public void setCostnatureStatus(String costnatureStatus) {
		this.costnatureStatus = costnatureStatus;
	}

	public String getCostnatureCreatedOn() {
		return costnatureCreatedOn;
	}

	public void setCostnatureCreatedOn(String costnatureCreatedOn) {
		this.costnatureCreatedOn = costnatureCreatedOn;
	}

	public String getCostnatureCreatedBy() {
		return costnatureCreatedBy;
	}

	public void setCostnatureCreatedBy(String costnatureCreatedBy) {
		this.costnatureCreatedBy = costnatureCreatedBy;
	}

	public String getCostnatureUpdatedOn() {
		return costnatureUpdatedOn;
	}

	public void setCostnatureUpdatedOn(String costnatureUpdatedOn) {
		this.costnatureUpdatedOn = costnatureUpdatedOn;
	}

	public String getCostnatureModifiedBy() {
		return costnatureModifiedBy;
	}

	public void setCostnatureModifiedBy(String costnatureModifiedBy) {
		this.costnatureModifiedBy = costnatureModifiedBy;
	}

	public String getCostLabourId() {
		return costLabourId;
	}

	public void setCostLabourId(String costLabourId) {
		this.costLabourId = costLabourId;
	}

	public String getCostLabourName() {
		return costLabourName;
	}

	public void setCostLabourName(String costLabourName) {
		this.costLabourName = costLabourName;
	}

	public String getCostLabourDesc() {
		return costLabourDesc;
	}

	public void setCostLabourDesc(String costLabourDesc) {
		this.costLabourDesc = costLabourDesc;
	}

	public String getCostLabourStatus() {
		return costLabourStatus;
	}

	public void setCostLabourStatus(String costLabourStatus) {
		this.costLabourStatus = costLabourStatus;
	}

	public String getCostLabourCreatedOn() {
		return costLabourCreatedOn;
	}

	public void setCostLabourCreatedOn(String costLabourCreatedOn) {
		this.costLabourCreatedOn = costLabourCreatedOn;
	}

	public String getCostLabourCreatedBy() {
		return costLabourCreatedBy;
	}

	public void setCostLabourCreatedBy(String costLabourCreatedBy) {
		this.costLabourCreatedBy = costLabourCreatedBy;
	}

	public String getCostLabourModifiedBy() {
		return costLabourModifiedBy;
	}

	public void setCostLabourModifiedBy(String costLabourModifiedBy) {
		this.costLabourModifiedBy = costLabourModifiedBy;
	}

	public String getCostLabourUpdatedOn() {
		return costLabourUpdatedOn;
	}

	public void setCostLabourUpdatedOn(String costLabourUpdatedOn) {
		this.costLabourUpdatedOn = costLabourUpdatedOn;
	}

	public String getVendorCategoryId() {
		return vendorCategoryId;
	}

	public void setVendorCategoryId(String vendorCategoryId) {
		this.vendorCategoryId = vendorCategoryId;
	}

	public String getVendorCategoryName() {
		return vendorCategoryName;
	}

	public void setVendorCategoryName(String vendorCategoryName) {
		this.vendorCategoryName = vendorCategoryName;
	}

	public String getVendorCategoryDesc() {
		return vendorCategoryDesc;
	}

	public void setVendorCategoryDesc(String vendorCategoryDesc) {
		this.vendorCategoryDesc = vendorCategoryDesc;
	}

	public String getVendorCategoryStatus() {
		return vendorCategoryStatus;
	}

	public void setVendorCategoryStatus(String vendorCategoryStatus) {
		this.vendorCategoryStatus = vendorCategoryStatus;
	}

	public String getVendorCategoryCreatedOn() {
		return vendorCategoryCreatedOn;
	}

	public void setVendorCategoryCreatedOn(String vendorCategoryCreatedOn) {
		this.vendorCategoryCreatedOn = vendorCategoryCreatedOn;
	}

	public String getVendorCategoryCreatedBy() {
		return vendorCategoryCreatedBy;
	}

	public void setVendorCategoryCreatedBy(String vendorCategoryCreatedBy) {
		this.vendorCategoryCreatedBy = vendorCategoryCreatedBy;
	}

	public String getVendorCategoryModifiedBy() {
		return vendorCategoryModifiedBy;
	}

	public void setVendorCategoryModifiedBy(String vendorCategoryModifiedBy) {
		this.vendorCategoryModifiedBy = vendorCategoryModifiedBy;
	}

	public String getVendorCategoryUpdatedOn() {
		return vendorCategoryUpdatedOn;
	}

	public void setVendorCategoryUpdatedOn(String vendorCategoryUpdatedOn) {
		this.vendorCategoryUpdatedOn = vendorCategoryUpdatedOn;
	}

	public String getLocationTypeId() {
		return locationTypeId;
	}

	public void setLocationTypeId(String locationTypeId) {
		this.locationTypeId = locationTypeId;
	}

	public String getLocationTypeName() {
		return locationTypeName;
	}

	public void setLocationTypeName(String locationTypeName) {
		this.locationTypeName = locationTypeName;
	}

	public String getLocationTypeDesc() {
		return locationTypeDesc;
	}

	public void setLocationTypeDesc(String locationTypeDesc) {
		this.locationTypeDesc = locationTypeDesc;
	}

	public String getLocationTypeStatus() {
		return locationTypeStatus;
	}

	public void setLocationTypeStatus(String locationTypeStatus) {
		this.locationTypeStatus = locationTypeStatus;
	}

	public String getLocationTypeCreatedOn() {
		return locationTypeCreatedOn;
	}

	public void setLocationTypeCreatedOn(String locationTypeCreatedOn) {
		this.locationTypeCreatedOn = locationTypeCreatedOn;
	}

	public String getLocationTypeCreatedBy() {
		return locationTypeCreatedBy;
	}

	public void setLocationTypeCreatedBy(String locationTypeCreatedBy) {
		this.locationTypeCreatedBy = locationTypeCreatedBy;
	}

	public String getLocationTypeModifiedBy() {
		return locationTypeModifiedBy;
	}

	public void setLocationTypeModifiedBy(String locationTypeModifiedBy) {
		this.locationTypeModifiedBy = locationTypeModifiedBy;
	}

	public String getLocationTypeUpdatedOn() {
		return locationTypeUpdatedOn;
	}

	public void setLocationTypeUpdatedOn(String locationTypeUpdatedOn) {
		this.locationTypeUpdatedOn = locationTypeUpdatedOn;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public String getRoomTypeDesc() {
		return roomTypeDesc;
	}

	public void setRoomTypeDesc(String roomTypeDesc) {
		this.roomTypeDesc = roomTypeDesc;
	}

	public String getRoomTypeStatus() {
		return roomTypeStatus;
	}

	public void setRoomTypeStatus(String roomTypeStatus) {
		this.roomTypeStatus = roomTypeStatus;
	}

	public String getRoomTypeCreatedOn() {
		return roomTypeCreatedOn;
	}

	public void setRoomTypeCreatedOn(String roomTypeCreatedOn) {
		this.roomTypeCreatedOn = roomTypeCreatedOn;
	}

	public String getRoomTypeCreatedBy() {
		return roomTypeCreatedBy;
	}

	public void setRoomTypeCreatedBy(String roomTypeCreatedBy) {
		this.roomTypeCreatedBy = roomTypeCreatedBy;
	}

	public String getRoomTypeModifiedBy() {
		return roomTypeModifiedBy;
	}

	public void setRoomTypeModifiedBy(String roomTypeModifiedBy) {
		this.roomTypeModifiedBy = roomTypeModifiedBy;
	}

	public String getRoomTypeUpdatedOn() {
		return roomTypeUpdatedOn;
	}

	public void setRoomTypeUpdatedOn(String roomTypeUpdatedOn) {
		this.roomTypeUpdatedOn = roomTypeUpdatedOn;
	}

	public String getVendorTypeId() {
		return vendorTypeId;
	}

	public void setVendorTypeId(String vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}

	public String getVendorCategoryTypeId() {
		return vendorCategoryTypeId;
	}

	public void setVendorCategoryTypeId(String vendorCategoryTypeId) {
		this.vendorCategoryTypeId = vendorCategoryTypeId;
	}

	public String getVendorCategoryTypeName() {
		return vendorCategoryTypeName;
	}

	public void setVendorCategoryTypeName(String vendorCategoryTypeName) {
		this.vendorCategoryTypeName = vendorCategoryTypeName;
	}

	public String getVendorTypeName() {
		return vendorTypeName;
	}

	public void setVendorTypeName(String vendorTypeName) {
		this.vendorTypeName = vendorTypeName;
	}

	public String getVendorTypeDesc() {
		return vendorTypeDesc;
	}

	public void setVendorTypeDesc(String vendorTypeDesc) {
		this.vendorTypeDesc = vendorTypeDesc;
	}

	public String getVendorTypeStatus() {
		return vendorTypeStatus;
	}

	public void setVendorTypeStatus(String vendorTypeStatus) {
		this.vendorTypeStatus = vendorTypeStatus;
	}

	public String getVendorTypeCreatedOn() {
		return vendorTypeCreatedOn;
	}

	public void setVendorTypeCreatedOn(String vendorTypeCreatedOn) {
		this.vendorTypeCreatedOn = vendorTypeCreatedOn;
	}

	public String getVendorTypeCreatedBy() {
		return vendorTypeCreatedBy;
	}

	public void setVendorTypeCreatedBy(String vendorTypeCreatedBy) {
		this.vendorTypeCreatedBy = vendorTypeCreatedBy;
	}

	public String getVendorTypeModifiedBy() {
		return vendorTypeModifiedBy;
	}

	public void setVendorTypeModifiedBy(String vendorTypeModifiedBy) {
		this.vendorTypeModifiedBy = vendorTypeModifiedBy;
	}

	public String getVendorTypeUpdatedOn() {
		return vendorTypeUpdatedOn;
	}

	public void setVendorTypeUpdatedOn(String vendorTypeUpdatedOn) {
		this.vendorTypeUpdatedOn = vendorTypeUpdatedOn;
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
