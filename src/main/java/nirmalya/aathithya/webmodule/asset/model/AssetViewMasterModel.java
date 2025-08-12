package nirmalya.aathithya.webmodule.asset.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.training.model.ManageTrainingDocumentModel;

public class AssetViewMasterModel {
	private String edate;
	private String pdate;
	private String sdate;
	private String assetId;
	private String assetcat;
	private String assetname;
	private String assetmodel;
	private String assetcode;
	private String assetsubcat;
	private String assettype;
	private String lifespan;
	private String purchaseno;
	private String qcId;
	private String remark;
	private String warrantyid;
	private String serviceprovider;
	private String insurancename;
	private String insuranceno;
	private String isdate;
	private String iedate;
	private String wstatus;
	private String owntype;
	private String category;
	private String fileName;
	private String documentURL;
	private String documentFileBase;
	private String createdBy;
	private String organization;
	private String orgDivision;

	private String assetPrice;
	private String assetDescription;
	private String type;
	private String spareterm;
	private String qtySpare;
	private String roomId;
	
	private String locationSts;
	private String existSts;
	private String allocationId;
	private String activeId;
	private String assetSts;
	
	private String capacity;
	private String unit;

	private List<AssetDocumentModal> documentList;
	private List<AssetViewMasterModel> warrantyList;
	private List<AssetViewMasterModel> insuranceList;
	
	private String assetid;
	private String rdate;
	private String docName;
	private String imgName;
	private String imgUrl;
	private String complianceId;
	private List<AssetViewMasterModel> complianceList;
	private String csdate;
	private String crdate;
	private String warId;
	private String slNoId;
	
	private String poliAlloc;
	private String assetemp;
	private String assetGrp;
	private String assigndate;
	private String policyId;
	private List<AssetViewMasterModel> policyDataList;

	public AssetViewMasterModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAssetSts() {
		return assetSts;
	}

	public void setAssetSts(String assetSts) {
		this.assetSts = assetSts;
	}

	public List<AssetViewMasterModel> getWarrantyList() {
		return warrantyList;
	}

	public void setWarrantyList(List<AssetViewMasterModel> warrantyList) {
		this.warrantyList = warrantyList;
	}

	public List<AssetViewMasterModel> getInsuranceList() {
		return insuranceList;
	}

	public void setInsuranceList(List<AssetViewMasterModel> insuranceList) {
		this.insuranceList = insuranceList;
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocumentFileBase() {
		return documentFileBase;
	}

	public void setDocumentFileBase(String documentFileBase) {
		this.documentFileBase = documentFileBase;
	}

	public String getWstatus() {
		return wstatus;
	}

	public void setWstatus(String wstatus) {
		this.wstatus = wstatus;
	}

	public String getInsuranceno() {
		return insuranceno;
	}

	public void setInsuranceno(String insuranceno) {
		this.insuranceno = insuranceno;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getInsurancename() {
		return insurancename;
	}

	public void setInsurancename(String insurancename) {
		this.insurancename = insurancename;
	}

	public String getIsdate() {
		return isdate;
	}

	public void setIsdate(String isdate) {
		this.isdate = isdate;
	}

	public String getIedate() {
		return iedate;
	}

	public void setIedate(String iedate) {
		this.iedate = iedate;
	}

	public String getQcId() {
		return qcId;
	}

	public void setQcId(String qcId) {
		this.qcId = qcId;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetcat() {
		return assetcat;
	}

	public void setAssetcat(String assetcat) {
		this.assetcat = assetcat;
	}

	public String getAssetname() {
		return assetname;
	}

	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}

	public String getAssetsubcat() {
		return assetsubcat;
	}

	public void setAssetsubcat(String assetsubcat) {
		this.assetsubcat = assetsubcat;
	}

	public String getAssettype() {
		return assettype;
	}

	public void setAssettype(String assettype) {
		this.assettype = assettype;
	}

	public String getLifespan() {
		return lifespan;
	}

	public void setLifespan(String lifespan) {
		this.lifespan = lifespan;
	}

	public String getPurchaseno() {
		return purchaseno;
	}

	public void setPurchaseno(String purchaseno) {
		this.purchaseno = purchaseno;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWarrantyid() {
		return warrantyid;
	}

	public void setWarrantyid(String warrantyid) {
		this.warrantyid = warrantyid;
	}

	public String getServiceprovider() {
		return serviceprovider;
	}

	public void setServiceprovider(String serviceprovider) {
		this.serviceprovider = serviceprovider;
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

	public String getAssetPrice() {
		return assetPrice;
	}

	public void setAssetPrice(String assetPrice) {
		this.assetPrice = assetPrice;
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpareterm() {
		return spareterm;
	}

	public void setSpareterm(String spareterm) {
		this.spareterm = spareterm;
	}

	public String getQtySpare() {
		return qtySpare;
	}

	public void setQtySpare(String qtySpare) {
		this.qtySpare = qtySpare;
	}

	public String getAssetmodel() {
		return assetmodel;
	}

	public void setAssetmodel(String assetmodel) {
		this.assetmodel = assetmodel;
	}

	public String getAssetcode() {
		return assetcode;
	}

	public void setAssetcode(String assetcode) {
		this.assetcode = assetcode;
	}

	public String getOwntype() {
		return owntype;
	}

	public void setOwntype(String owntype) {
		this.owntype = owntype;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	
	public String getLocationSts() {
		return locationSts;
	}

	public void setLocationSts(String locationSts) {
		this.locationSts = locationSts;
	}

	public String getExistSts() {
		return existSts;
	}

	public void setExistSts(String existSts) {
		this.existSts = existSts;
	}

	public String getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(String allocationId) {
		this.allocationId = allocationId;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	
	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getAssetid() {
		return assetid;
	}

	public void setAssetid(String assetid) {
		this.assetid = assetid;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getComplianceId() {
		return complianceId;
	}

	public void setComplianceId(String complianceId) {
		this.complianceId = complianceId;
	}

	public List<AssetViewMasterModel> getComplianceList() {
		return complianceList;
	}

	public void setComplianceList(List<AssetViewMasterModel> complianceList) {
		this.complianceList = complianceList;
	}

	public String getCsdate() {
		return csdate;
	}

	public void setCsdate(String csdate) {
		this.csdate = csdate;
	}

	public String getCrdate() {
		return crdate;
	}

	public void setCrdate(String crdate) {
		this.crdate = crdate;
	}

	public String getWarId() {
		return warId;
	}

	public void setWarId(String warId) {
		this.warId = warId;
	}

	public String getSlNoId() {
		return slNoId;
	}

	public void setSlNoId(String slNoId) {
		this.slNoId = slNoId;
	}

	public String getPoliAlloc() {
		return poliAlloc;
	}

	public void setPoliAlloc(String poliAlloc) {
		this.poliAlloc = poliAlloc;
	}

	public String getAssetemp() {
		return assetemp;
	}

	public void setAssetemp(String assetemp) {
		this.assetemp = assetemp;
	}

	public String getAssetGrp() {
		return assetGrp;
	}

	public void setAssetGrp(String assetGrp) {
		this.assetGrp = assetGrp;
	}

	public String getAssigndate() {
		return assigndate;
	}

	public void setAssigndate(String assigndate) {
		this.assigndate = assigndate;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public List<AssetViewMasterModel> getPolicyDataList() {
		return policyDataList;
	}

	public void setPolicyDataList(List<AssetViewMasterModel> policyDataList) {
		this.policyDataList = policyDataList;
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
