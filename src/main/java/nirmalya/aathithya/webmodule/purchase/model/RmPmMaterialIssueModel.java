package nirmalya.aathithya.webmodule.purchase.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RmPmMaterialIssueModel {

	private String slNo;
	private String itemId;
	private String sku;
	private String itemName;
	private String hsnCode;
	private String model;
	private String quantity;
	private String unit;
	private String unitName;
	private String desc;
	private String receiveDate;
	private String reqId;
	private String slipId;

	private String createdBy;
	private String organization;
	private String orgDivision;

	private String quantityIssued;
	private String grnNo;
	private String materialIssueDate;

	List<RmPmMaterialIssueModel> details;

	public RmPmMaterialIssueModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getSlipId() {
		return slipId;
	}

	public void setSlipId(String slipId) {
		this.slipId = slipId;
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

	public List<RmPmMaterialIssueModel> getDetails() {
		return details;
	}

	public void setDetails(List<RmPmMaterialIssueModel> details) {
		this.details = details;
	}

	public String getQuantityIssued() {
		return quantityIssued;
	}

	public void setQuantityIssued(String quantityIssued) {
		this.quantityIssued = quantityIssued;
	}

	public String getGrnNo() {
		return grnNo;
	}

	public void setGrnNo(String grnNo) {
		this.grnNo = grnNo;
	}

	public String getMaterialIssueDate() {
		return materialIssueDate;
	}

	public void setMaterialIssueDate(String materialIssueDate) {
		this.materialIssueDate = materialIssueDate;
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
