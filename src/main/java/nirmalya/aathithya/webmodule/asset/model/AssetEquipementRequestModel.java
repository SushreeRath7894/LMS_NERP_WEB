package nirmalya.aathithya.webmodule.asset.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AssetEquipementRequestModel {
	private String requestid;
	private String ticketid;
	private String requestedQty;
	private String assetcat;
	private String assetscat;
	private String stockQty;
	private String remark;
	private String approveQty;
	private String assetid;
	
	private String createdBy;
	private String organization;
	private String orgDivision;


	private List<AssetEquipementRequestModel> requestList;

	public AssetEquipementRequestModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAssetid() {
		return assetid;
	}


	public void setAssetid(String assetid) {
		this.assetid = assetid;
	}




	public String getRequestid() {
		return requestid;
	}




	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}




	public String getTicketid() {
		return ticketid;
	}




	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}




	public String getRequestedQty() {
		return requestedQty;
	}




	public void setRequestedQty(String requestedQty) {
		this.requestedQty = requestedQty;
	}




	public String getAssetcat() {
		return assetcat;
	}




	public void setAssetcat(String assetcat) {
		this.assetcat = assetcat;
	}




	public String getAssetscat() {
		return assetscat;
	}




	public void setAssetscat(String assetscat) {
		this.assetscat = assetscat;
	}




	public String getStockQty() {
		return stockQty;
	}




	public void setStockQty(String stockQty) {
		this.stockQty = stockQty;
	}




	public String getRemark() {
		return remark;
	}




	public void setRemark(String remark) {
		this.remark = remark;
	}




	public String getApproveQty() {
		return approveQty;
	}




	public void setApproveQty(String approveQty) {
		this.approveQty = approveQty;
	}




	public List<AssetEquipementRequestModel> getRequestList() {
		return requestList;
	}




	public void setRequestList(List<AssetEquipementRequestModel> requestList) {
		this.requestList = requestList;
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
