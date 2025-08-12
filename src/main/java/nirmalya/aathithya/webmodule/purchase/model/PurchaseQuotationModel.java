package nirmalya.aathithya.webmodule.purchase.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryRfqVendorModel;

public class PurchaseQuotationModel {
	private String vendorId;
	private String vendorName;
	private String rfqId;
	private String reqNo;
	private String desc;
	private String reqType;
	private String reqPrior;
	private String receiveDate;

	private String itemId;
	private Integer slNo;
	private String itemName;
	private String hsnCode;
	private Double quantity;
	private Double unitPrice;
	private Double lineTotal;
	private String sku;
	private String createdBy;
	private String updatedOn;
	private String unit;
	private String unitName;
	private String organization;
	private String orgDivision;
	private String locationId;
	private String locationName;
	private String expertizeId;
	private String expertizeName;
	private Double reqSent;
	private Double candidates;
	private Double closed;
	private String indentId;
	private String project;
	private String projectName;

	// for pdf.

	private String quantity1;
	private String unitPrice1;
	private String lineTotal1;
	private String aprroveStatus;

	private String orgAddress;
	private String orgEmail;
	private String orgPhone;
	private String orgImage;
	private String quantitys;
	private String unitPrices;
	private String lineTotals;
	private List<InventoryRfqVendorModel> vendorList;

	// Email

	private String vendorNames;
	private String vendorMails;
	private String vendorCc;
	private String vendorBcc;
	private String vendorMsg;
	private String key;
	private String url;
	private String mailStatus;

	public PurchaseQuotationModel() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getRfqId() {
		return rfqId;
	}

	public void setRfqId(String rfqId) {
		this.rfqId = rfqId;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqPrior() {
		return reqPrior;
	}

	public void setReqPrior(String reqPrior) {
		this.reqPrior = reqPrior;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getSlNo() {
		return slNo;
	}

	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
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

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
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

	public List<InventoryRfqVendorModel> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<InventoryRfqVendorModel> vendorList) {
		this.vendorList = vendorList;
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

	public String getExpertizeId() {
		return expertizeId;
	}

	public void setExpertizeId(String expertizeId) {
		this.expertizeId = expertizeId;
	}

	public String getExpertizeName() {
		return expertizeName;
	}

	public void setExpertizeName(String expertizeName) {
		this.expertizeName = expertizeName;
	}

	public Double getReqSent() {
		return reqSent;
	}

	public void setReqSent(Double reqSent) {
		this.reqSent = reqSent;
	}

	public Double getCandidates() {
		return candidates;
	}

	public void setCandidates(Double candidates) {
		this.candidates = candidates;
	}

	public Double getClosed() {
		return closed;
	}

	public void setClosed(Double closed) {
		this.closed = closed;
	}

	public String getIndentId() {
		return indentId;
	}

	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	// For Pdf.

	public String getQuantity1() {
		return quantity1;
	}

	public void setQuantity1(String quantity1) {
		this.quantity1 = quantity1;
	}

	public String getUnitPrice1() {
		return unitPrice1;
	}

	public void setUnitPrice1(String unitPrice1) {
		this.unitPrice1 = unitPrice1;
	}

	public String getLineTotal1() {
		return lineTotal1;
	}

	public void setLineTotal1(String lineTotal1) {
		this.lineTotal1 = lineTotal1;
	}

	public String getAprroveStatus() {
		return aprroveStatus;
	}

	public void setAprroveStatus(String aprroveStatus) {
		this.aprroveStatus = aprroveStatus;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}

	public String getOrgImage() {
		return orgImage;
	}

	public void setOrgImage(String orgImage) {
		this.orgImage = orgImage;
	}

	public String getQuantitys() {
		return quantitys;
	}

	public void setQuantitys(String quantitys) {
		this.quantitys = quantitys;
	}

	public String getUnitPrices() {
		return unitPrices;
	}

	public void setUnitPrices(String unitPrices) {
		this.unitPrices = unitPrices;
	}

	public String getLineTotals() {
		return lineTotals;
	}

	public void setLineTotals(String lineTotals) {
		this.lineTotals = lineTotals;
	}

	// Email

	public String getVendorNames() {
		return vendorNames;
	}

	public void setVendorNames(String vendorNames) {
		this.vendorNames = vendorNames;
	}

	public String getVendorMails() {
		return vendorMails;
	}

	public void setVendorMails(String vendorMails) {
		this.vendorMails = vendorMails;
	}

	public String getVendorCc() {
		return vendorCc;
	}

	public void setVendorCc(String vendorCc) {
		this.vendorCc = vendorCc;
	}

	public String getVendorBcc() {
		return vendorBcc;
	}

	public void setVendorBcc(String vendorBcc) {
		this.vendorBcc = vendorBcc;
	}

	public String getVendorMsg() {
		return vendorMsg;
	}

	public void setVendorMsg(String vendorMsg) {
		this.vendorMsg = vendorMsg;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
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
