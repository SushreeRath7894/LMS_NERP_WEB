package nirmalya.aathithya.webmodule.his.model;

public class HISBedMasterModel {

	private String bedId;
	private String bedName;
	private String propertyFloorType;
	private String ward;

	private String bedCharge;
	// private Double bedCharge;
	private String bedDescription;
	private String status;
	private String organization;
	private String orgDivision;
	private String createdBy;
	private String wardId;
	private String bedAvailability;

	public HISBedMasterModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getWardId() {
		return wardId;
	}

	public void setWardId(String wardId) {
		this.wardId = wardId;
	}

	public String getBedId() {
		return bedId;
	}

	public void setBedId(String bedId) {
		this.bedId = bedId;
	}

	public String getBedName() {
		return bedName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getPropertyFloorType() {
		return propertyFloorType;
	}

	public void setPropertyFloorType(String propertyFloorType) {
		this.propertyFloorType = propertyFloorType;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	/*
	 * public Double getBedCharge() { return bedCharge; }
	 * 
	 * 
	 * public void setBedCharge(Double bedCharge) { this.bedCharge = bedCharge; }
	 */
	public String getBedCharge() {
		return bedCharge;
	}

	public void setBedCharge(String bedCharge) {
		this.bedCharge = bedCharge;
	}

	public String getBedDescription() {
		return bedDescription;
	}

	public void setBedDescription(String bedDescription) {
		this.bedDescription = bedDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getBedAvailability() {
		return bedAvailability;
	}

	public void setBedAvailability(String bedAvailability) {
		this.bedAvailability = bedAvailability;
	}

}
