package nirmalya.aathithya.webmodule.asset.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AssetMasterDataWebModel {
	private String assetId;
	private String assetName;
	private String asssetInfo;
	private  String assetInfoStatus;
	


	private String categoryId;
	private String assetCategory;
	 
	private String assetCatStatus;
	
	private String dummyId; 
	private String dummyAsset;
	private String dummyAst;
	private String dummyAssetStatus;
	
	
	private String ownershipId;
	private String ownerName;
	private String ownerShip;
	private String ownerAssetStatus;
	
	private String assetSubCatId;
	private String assetCat;
	private String assetsubCatName;
	private String assetSubCatStatus;
	
	private String assetSpId;
	private String assetSpCatName;
	private String assetSpCatStatus;
	
	private String spSubCatId;
	private String spCatName;
	private String spSubCatName;
	private String spSubCatStatus;
	
	
	private String orgName;
	private String orgDiv;
	private String createdBy;
	

	
	public AssetMasterDataWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}


 

	public String getAssetName() {
		return assetName;
	}



	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}



	public String getAsssetInfo() {
		return asssetInfo;
	}



	public void setAsssetInfo(String asssetInfo) {
		this.asssetInfo = asssetInfo;
	}
	public String getAssetInfoStatus() {
		return assetInfoStatus;
	}



	public void setAssetInfoStatus(String assetInfoStatus) {
		this.assetInfoStatus = assetInfoStatus;
	}

	
	public String getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}



	public String getAssetCategory() {
		return assetCategory;
	}



	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}



	 
	public String getAssetCatStatus() {
		return assetCatStatus;
	}



	public void setAssetCatStatus(String assetCatStatus) {
		this.assetCatStatus = assetCatStatus;
	}


	public String getDummyId() {
		return dummyId;
	}



	public void setDummyId(String dummyId) {
		this.dummyId = dummyId;
	}


	public String getDummyAsset() {
		return dummyAsset;
	}



	public void setDummyAsset(String dummyAsset) {
		this.dummyAsset = dummyAsset;
	}



	public String getDummyAst() {
		return dummyAst;
	}



	public void setDummyAst(String dummyAst) {
		this.dummyAst = dummyAst;
	}
	
	public String getDummyAssetStatus() {
		return dummyAssetStatus;
	}



	public void setDummyAssetStatus(String dummyAssetStatus) {
		this.dummyAssetStatus = dummyAssetStatus;
	}

	public String getOwnershipId() {
		return ownershipId;
	}



	public void setOwnershipId(String ownershipId) {
		this.ownershipId = ownershipId;
	}



	public String getOwnerName() {
		return ownerName;
	}



	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}



	public String getOwnerShip() {
		return ownerShip;
	}



	public void setOwnerShip(String ownerShip) {
		this.ownerShip = ownerShip;
	}




	public String getOwnerAssetStatus() {
		return ownerAssetStatus;
	}



	public void setOwnerAssetStatus(String ownerAssetStatus) {
		this.ownerAssetStatus = ownerAssetStatus;
	}







	public String getOrgName() {
		return orgName;
	}



	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}



	public String getOrgDiv() {
		return orgDiv;
	}



	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}

	public String getAssetSubCatId() {
		return assetSubCatId;
	}



	public void setAssetSubCatId(String assetSubCatId) {
		this.assetSubCatId = assetSubCatId;
	}



	public String getAssetCat() {
		return assetCat;
	}



	public void setAssetCat(String assetCat) {
		this.assetCat = assetCat;
	}



	public String getAssetsubCatName() {
		return assetsubCatName;
	}



	public void setAssetsubCatName(String assetsubCatName) {
		this.assetsubCatName = assetsubCatName;
	}



	public String getAssetSubCatStatus() {
		return assetSubCatStatus;
	}



	public void setAssetSubCatStatus(String assetSubCatStatus) {
		this.assetSubCatStatus = assetSubCatStatus;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	
	
	
	public String getAssetSpId() {
		return assetSpId;
	}



	public void setAssetSpId(String assetSpId) {
		this.assetSpId = assetSpId;
	}



	public String getAssetSpCatName() {
		return assetSpCatName;
	}



	public void setAssetSpCatName(String assetSpCatName) {
		this.assetSpCatName = assetSpCatName;
	}



	public String getAssetSpCatStatus() {
		return assetSpCatStatus;
	}



	public void setAssetSpCatStatus(String assetSpCatStatus) {
		this.assetSpCatStatus = assetSpCatStatus;
	}

	
	


	public String getSpSubCatId() {
		return spSubCatId;
	}



	public void setSpSubCatId(String spSubCatId) {
		this.spSubCatId = spSubCatId;
	}



	public String getSpCatName() {
		return spCatName;
	}



	public void setSpCatName(String spCatName) {
		this.spCatName = spCatName;
	}



	public String getSpSubCatName() {
		return spSubCatName;
	}



	public void setSpSubCatName(String spSubCatName) {
		this.spSubCatName = spSubCatName;
	}



	 


	public String getSpSubCatStatus() {
		return spSubCatStatus;
	}



	public void setSpSubCatStatus(String spSubCatStatus) {
		this.spSubCatStatus = spSubCatStatus;
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
