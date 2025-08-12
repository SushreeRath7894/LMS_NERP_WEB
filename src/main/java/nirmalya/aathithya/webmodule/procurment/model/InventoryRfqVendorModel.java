package nirmalya.aathithya.webmodule.procurment.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class InventoryRfqVendorModel {

	private String vendorId;
	private String vendorName;
	private String locationId;
	private String locationName;
	private String expertizeId;
	private String expertizeName;
	private Double reqSent;
	private Double candidates;
	private Double closed;
	private String createdOn;
	private String createdBy;
	private String assignStatus;

	
	public InventoryRfqVendorModel() {
		super();
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getLocationId() {
		return locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getExpertizeId() {
		return expertizeId;
	}

	public void setExpertizeId(String expertizeId) {
		this.expertizeId = expertizeId;
	}

	public String getExpertizeName() {
		return expertizeName;
	}

	public void setExpertizeName(String expertizeName) {
		this.expertizeName = expertizeName;
	}

	public Double getReqSent() {
		return reqSent;
	}

	public void setReqSent(Double reqSent) {
		this.reqSent = reqSent;
	}

	public Double getCandidates() {
		return candidates;
	}

	public void setCandidates(Double candidates) {
		this.candidates = candidates;
	}

	public Double getClosed() {
		return closed;
	}

	public void setClosed(Double closed) {
		this.closed = closed;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getAssignStatus() {
		return assignStatus;
	}

	public void setAssignStatus(String assignStatus) {
		this.assignStatus = assignStatus;
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
