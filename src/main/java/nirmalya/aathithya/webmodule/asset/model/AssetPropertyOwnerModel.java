package nirmalya.aathithya.webmodule.asset.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AssetPropertyOwnerModel {

	private String type;
	private String ownerId;
	private String ownerName;
	private String ownerCountry;
	private String ownerState;
	private String ownerCity;
	private String ownerStreet;
	private String ownerPincode;
	private String ownerContact;
	private String ownerEmail;
	private String locationId;
	
	private String rentSDate;
	private String rentEDate;
	private String rentSecDeposit;
	private String rentRentPMonth;
	private String rentBankName;
	private String rentIFSC;
	private String rentAcNo;
	private String ownerStatus;
	
	
	private String createdBy;
	private String organization;
	private String orgDivision;


	private List<AssetDocumentModal> documentList;

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerCountry() {
		return ownerCountry;
	}

	public void setOwnerCountry(String ownerCountry) {
		this.ownerCountry = ownerCountry;
	}

	public String getOwnerState() {
		return ownerState;
	}

	public void setOwnerState(String ownerState) {
		this.ownerState = ownerState;
	}

	public String getOwnerCity() {
		return ownerCity;
	}

	public void setOwnerCity(String ownerCity) {
		this.ownerCity = ownerCity;
	}

	public String getOwnerStreet() {
		return ownerStreet;
	}

	public void setOwnerStreet(String ownerStreet) {
		this.ownerStreet = ownerStreet;
	}

	public String getOwnerPincode() {
		return ownerPincode;
	}

	public void setOwnerPincode(String ownerPincode) {
		this.ownerPincode = ownerPincode;
	}

	public String getOwnerContact() {
		return ownerContact;
	}

	public void setOwnerContact(String ownerContact) {
		this.ownerContact = ownerContact;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
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

	public List<AssetDocumentModal> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<AssetDocumentModal> documentList) {
		this.documentList = documentList;
	}

	public String getRentSDate() {
		return rentSDate;
	}

	public void setRentSDate(String rentSDate) {
		this.rentSDate = rentSDate;
	}

	public String getRentEDate() {
		return rentEDate;
	}

	public void setRentEDate(String rentEDate) {
		this.rentEDate = rentEDate;
	}

	public String getRentSecDeposit() {
		return rentSecDeposit;
	}

	public void setRentSecDeposit(String rentSecDeposit) {
		this.rentSecDeposit = rentSecDeposit;
	}

	public String getRentRentPMonth() {
		return rentRentPMonth;
	}

	public void setRentRentPMonth(String rentRentPMonth) {
		this.rentRentPMonth = rentRentPMonth;
	}

	public String getRentBankName() {
		return rentBankName;
	}

	public void setRentBankName(String rentBankName) {
		this.rentBankName = rentBankName;
	}

	public String getRentIFSC() {
		return rentIFSC;
	}

	public void setRentIFSC(String rentIFSC) {
		this.rentIFSC = rentIFSC;
	}

	public String getRentAcNo() {
		return rentAcNo;
	}

	public void setRentAcNo(String rentAcNo) {
		this.rentAcNo = rentAcNo;
	}

	public AssetPropertyOwnerModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOwnerStatus() {
		return ownerStatus;
	}

	public void setOwnerStatus(String ownerStatus) {
		this.ownerStatus = ownerStatus;
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
