package nirmalya.aathithya.webmodule.asset.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AssetStockRequestWebModel {

	private String requestid;
	private String ticketid;
	private String approveQty;
	private String assetId;
	private String remark;
	private String requestedQty;
	private String stockQty;

	private List<AssetStockRequestWebModel> requestList;
	
	private String createdBy;
	private String organization;
	private String orgDivision;

	public AssetStockRequestWebModel() {
		super();
		// TODO Auto-generated constructor stub
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


	public String getApproveQty() {
		return approveQty;
	}


	public void setApproveQty(String approveQty) {
		this.approveQty = approveQty;
	}


	public String getAssetId() {
		return assetId;
	}


	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getRequestedQty() {
		return requestedQty;
	}


	public void setRequestedQty(String requestedQty) {
		this.requestedQty = requestedQty;
	}


	public String getStockQty() {
		return stockQty;
	}


	public void setStockQty(String stockQty) {
		this.stockQty = stockQty;
	}


	public List<AssetStockRequestWebModel> getRequestList() {
		return requestList;
	}


	public void setRequestList(List<AssetStockRequestWebModel> requestList) {
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
