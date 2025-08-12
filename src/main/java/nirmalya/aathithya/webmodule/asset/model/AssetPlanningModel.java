package nirmalya.aathithya.webmodule.asset.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AssetPlanningModel {
	private String planId;
	private String spaceId;
	private String spaceName;
	private String propertyId;
	private String floorId;
	private String assetCategory;
	private String assetSubcategory;
	private String definiteQty;
	private String requiredQty;
	private String approvedQty;
	private String approvedSts;
	
	private String createdBy;
	private String organization;
	private String orgDivision;


	private List<AssetPlanningModel> planList;

	public AssetPlanningModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getPlanId() {
		return planId;
	}


	public void setPlanId(String planId) {
		this.planId = planId;
	}


	public String getSpaceId() {
		return spaceId;
	}


	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}


	public String getSpaceName() {
		return spaceName;
	}


	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}


	public String getPropertyId() {
		return propertyId;
	}


	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}


	public String getFloorId() {
		return floorId;
	}


	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}


	public String getAssetCategory() {
		return assetCategory;
	}


	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}


	public String getAssetSubcategory() {
		return assetSubcategory;
	}


	public void setAssetSubcategory(String assetSubcategory) {
		this.assetSubcategory = assetSubcategory;
	}


	public String getDefiniteQty() {
		return definiteQty;
	}


	public void setDefiniteQty(String definiteQty) {
		this.definiteQty = definiteQty;
	}


	public String getRequiredQty() {
		return requiredQty;
	}


	public void setRequiredQty(String requiredQty) {
		this.requiredQty = requiredQty;
	}


	public String getApprovedQty() {
		return approvedQty;
	}


	public void setApprovedQty(String approvedQty) {
		this.approvedQty = approvedQty;
	}


	public String getApprovedSts() {
		return approvedSts;
	}


	public void setApprovedSts(String approvedSts) {
		this.approvedSts = approvedSts;
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


	public List<AssetPlanningModel> getPlanList() {
		return planList;
	}


	public void setPlanList(List<AssetPlanningModel> planList) {
		this.planList = planList;
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
