package nirmalya.aathithya.webmodule.asset.model;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;

public class AssetPropertyModel {

	private String locationId;
	private String locationName;
	private String locationCode;
	private String locationType;
	private String locCountry;
	private String locOwnership;
	private String locState;
	private String locCity;
	private String locStreet;
	private String locWidth;
	private String locHeight;
	private String locLength;
	private String locArea;
	private String locVirtual;
	private String locStatus;
	private String createdBy;
	private String fileLocation;
	private String createdDate;
	private List<DropDownModel> stateList = new ArrayList<DropDownModel>();
	private List<DropDownModel> cityList = new ArrayList<DropDownModel>();
	private String floorId;
	private Integer floorSlNo;
	private BigInteger locCount;
	private BigInteger floorCount;
	private String organization;
	private String orgDivision;
	
	private String floorHeight;
	private String floorWidth;
	private String floorLength;
	
	private String roomHeight;
	private String roomWidth;
	private String roomLength;
	
	private String locDescription;
	private String locPincode;
	private String locSdate;
	private String locEdate;
	private String locRent;
	private String locPdate;
	
	private List<AssetDocumentModal> documentList;
	
	public AssetPropertyModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getLocCountry() {
		return locCountry;
	}

	public void setLocCountry(String locCountry) {
		this.locCountry = locCountry;
	}

	public String getLocState() {
		return locState;
	}

	public void setLocState(String locState) {
		this.locState = locState;
	}

	public String getLocCity() {
		return locCity;
	}

	public void setLocCity(String locCity) {
		this.locCity = locCity;
	}

	public String getLocStreet() {
		return locStreet;
	}

	public void setLocStreet(String locStreet) {
		this.locStreet = locStreet;
	}

	public String getLocVirtual() {
		return locVirtual;
	}

	public void setLocVirtual(String locVirtual) {
		this.locVirtual = locVirtual;
	}

	public String getLocStatus() {
		return locStatus;
	}

	public void setLocStatus(String locStatus) {
		this.locStatus = locStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public List<DropDownModel> getStateList() {
		return stateList;
	}

	public void setStateList(List<DropDownModel> stateList) {
		this.stateList = stateList;
	}

	public List<DropDownModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<DropDownModel> cityList) {
		this.cityList = cityList;
	}

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public Integer getFloorSlNo() {
		return floorSlNo;
	}

	public void setFloorSlNo(Integer floorSlNo) {
		this.floorSlNo = floorSlNo;
	}

	public BigInteger getLocCount() {
		return locCount;
	}

	public void setLocCount(BigInteger locCount) {
		this.locCount = locCount;
	}

	public BigInteger getFloorCount() {
		return floorCount;
	}

	public void setFloorCount(BigInteger floorCount) {
		this.floorCount = floorCount;
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

	public String getLocWidth() {
		return locWidth;
	}

	public void setLocWidth(String locWidth) {
		this.locWidth = locWidth;
	}

	public String getLocHeight() {
		return locHeight;
	}

	public void setLocHeight(String locHeight) {
		this.locHeight = locHeight;
	}
	public String getLocLength() {
		return locLength;
	}

	public void setLocLength(String locLength) {
		this.locLength = locLength;
	}

	public String getLocOwnership() {
		return locOwnership;
	}

	public void setLocOwnership(String locOwnership) {
		this.locOwnership = locOwnership;
	}

	public String getFloorHeight() {
		return floorHeight;
	}

	public void setFloorHeight(String floorHeight) {
		this.floorHeight = floorHeight;
	}

	public String getFloorWidth() {
		return floorWidth;
	}

	public void setFloorWidth(String floorWidth) {
		this.floorWidth = floorWidth;
	}

	public String getFloorLength() {
		return floorLength;
	}

	public void setFloorLength(String floorLength) {
		this.floorLength = floorLength;
	}

	public String getRoomHeight() {
		return roomHeight;
	}

	public void setRoomHeight(String roomHeight) {
		this.roomHeight = roomHeight;
	}

	public String getRoomWidth() {
		return roomWidth;
	}

	public void setRoomWidth(String roomWidth) {
		this.roomWidth = roomWidth;
	}

	public String getRoomLength() {
		return roomLength;
	}

	public void setRoomLength(String roomLength) {
		this.roomLength = roomLength;
	}

	public String getLocDescription() {
		return locDescription;
	}

	public void setLocDescription(String locDescription) {
		this.locDescription = locDescription;
	}

	public String getLocPincode() {
		return locPincode;
	}

	public void setLocPincode(String locPincode) {
		this.locPincode = locPincode;
	}

	public String getLocSdate() {
		return locSdate;
	}

	public void setLocSdate(String locSdate) {
		this.locSdate = locSdate;
	}

	public String getLocEdate() {
		return locEdate;
	}

	public void setLocEdate(String locEdate) {
		this.locEdate = locEdate;
	}

	public String getLocRent() {
		return locRent;
	}

	public void setLocRent(String locRent) {
		this.locRent = locRent;
	}

	public String getLocPdate() {
		return locPdate;
	}

	public void setLocPdate(String locPdate) {
		this.locPdate = locPdate;
	}


	public List<AssetDocumentModal> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<AssetDocumentModal> documentList) {
		this.documentList = documentList;
	}

	public String getLocArea() {
		return locArea;
	}

	public void setLocArea(String locArea) {
		this.locArea = locArea;
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
