package nirmalya.aathithya.webmodule.procurment.model;

import java.util.List;

public class InventoryVendorBidParentModel {
	private String rfqId;
	private String vendorId;
	private String vendorName;
	private String additionalDetails;
	private String vendorDetails;
	private String history;
	List<InventoryVendorBidModel> vendorBidDetails;

	public String getRfqId() {
		return rfqId;
	}

	public void setRfqId(String rfqId) {
		this.rfqId = rfqId;
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

	public String getAdditionalDetails() {
		return additionalDetails;
	}

	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}

	public String getVendorDetails() {
		return vendorDetails;
	}

	public void setVendorDetails(String vendorDetails) {
		this.vendorDetails = vendorDetails;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public List<InventoryVendorBidModel> getVendorBidDetails() {
		return vendorBidDetails;
	}

	public void setVendorBidDetails(List<InventoryVendorBidModel> vendorBidDetails) {
		this.vendorBidDetails = vendorBidDetails;
	}

}
