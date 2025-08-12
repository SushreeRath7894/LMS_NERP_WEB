package nirmalya.aathithya.webmodule.purchase.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GrnReturnExcelModel {

	private String invoiceNo;
	private String poNo;
	private String grDate;
	private String vendorName;
	private String materialCode;
	private String materialDesc;
	private String materialQty;
	private String materialUom;
	private String grNo;
	private String transporterName;
	private String plant;
	private String totalCost;
	private String poItem;
	private String vendorId;
	private String localIsToggle;
	private String transporterId;
	private String docType;
	private String flDoc;
	private String mvtType;
	private String batch;
	private String basicValue;
	private String truckNo;
	private String materialType;
	private String poDocDate;
	private String frtBefTax;
	private String freight;
	private String igstPercent;
	private String igstAmount;
	private String cgstPercent;
	private String cgstAmount;
	private String sgstPercent;
	private String sgstAmount;
	private String invoiceDate;
	private String vendorGst;
	private String vendorRegionId;
	private String vendorRegionName;
	private String bpCode;
	private String bpName;
	private String supplierStateId;
	private String recPlantStateCode;
	private String hsnCode;
	private String goodsServiceToggle;
	private String createdBy;
	private String organization;
	private String orgDivision;
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public String getGrDate() {
		return grDate;
	}
	public void setGrDate(String grDate) {
		this.grDate = grDate;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public String getMaterialQty() {
		return materialQty;
	}
	public void setMaterialQty(String materialQty) {
		this.materialQty = materialQty;
	}
	public String getMaterialUom() {
		return materialUom;
	}
	public void setMaterialUom(String materialUom) {
		this.materialUom = materialUom;
	}
	public String getGrNo() {
		return grNo;
	}
	public void setGrNo(String grNo) {
		this.grNo = grNo;
	}
	public String getTransporterName() {
		return transporterName;
	}
	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public String getPoItem() {
		return poItem;
	}
	public void setPoItem(String poItem) {
		this.poItem = poItem;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getLocalIsToggle() {
		return localIsToggle;
	}
	public void setLocalIsToggle(String localIsToggle) {
		this.localIsToggle = localIsToggle;
	}
	public String getTransporterId() {
		return transporterId;
	}
	public void setTransporterId(String transporterId) {
		this.transporterId = transporterId;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getFlDoc() {
		return flDoc;
	}
	public void setFlDoc(String flDoc) {
		this.flDoc = flDoc;
	}
	public String getMvtType() {
		return mvtType;
	}
	public void setMvtType(String mvtType) {
		this.mvtType = mvtType;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getBasicValue() {
		return basicValue;
	}
	public void setBasicValue(String basicValue) {
		this.basicValue = basicValue;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getPoDocDate() {
		return poDocDate;
	}
	public void setPoDocDate(String poDocDate) {
		this.poDocDate = poDocDate;
	}
	public String getFrtBefTax() {
		return frtBefTax;
	}
	public void setFrtBefTax(String frtBefTax) {
		this.frtBefTax = frtBefTax;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getIgstPercent() {
		return igstPercent;
	}
	public void setIgstPercent(String igstPercent) {
		this.igstPercent = igstPercent;
	}
	public String getIgstAmount() {
		return igstAmount;
	}
	public void setIgstAmount(String igstAmount) {
		this.igstAmount = igstAmount;
	}
	public String getSgstPercent() {
		return sgstPercent;
	}
	public void setSgstPercent(String sgstPercent) {
		this.sgstPercent = sgstPercent;
	}
	public String getSgstAmount() {
		return sgstAmount;
	}
	public void setSgstAmount(String sgstAmount) {
		this.sgstAmount = sgstAmount;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getVendorGst() {
		return vendorGst;
	}
	public void setVendorGst(String vendorGst) {
		this.vendorGst = vendorGst;
	}
	public String getVendorRegionId() {
		return vendorRegionId;
	}
	public void setVendorRegionId(String vendorRegionId) {
		this.vendorRegionId = vendorRegionId;
	}
	public String getVendorRegionName() {
		return vendorRegionName;
	}
	public void setVendorRegionName(String vendorRegionName) {
		this.vendorRegionName = vendorRegionName;
	}
	public String getBpCode() {
		return bpCode;
	}
	public void setBpCode(String bpCode) {
		this.bpCode = bpCode;
	}
	public String getBpName() {
		return bpName;
	}
	public void setBpName(String bpName) {
		this.bpName = bpName;
	}
	public String getSupplierStateId() {
		return supplierStateId;
	}
	public void setSupplierStateId(String supplierStateId) {
		this.supplierStateId = supplierStateId;
	}
	public String getRecPlantStateCode() {
		return recPlantStateCode;
	}
	public void setRecPlantStateCode(String recPlantStateCode) {
		this.recPlantStateCode = recPlantStateCode;
	}
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}
	public String getGoodsServiceToggle() {
		return goodsServiceToggle;
	}
	public void setGoodsServiceToggle(String goodsServiceToggle) {
		this.goodsServiceToggle = goodsServiceToggle;
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
	public String getTruckNo() {
		return truckNo;
	}
	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}
	public String getCgstPercent() {
		return cgstPercent;
	}
	public void setCgstPercent(String cgstPercent) {
		this.cgstPercent = cgstPercent;
	}
	public String getCgstAmount() {
		return cgstAmount;
	}
	public void setCgstAmount(String cgstAmount) {
		this.cgstAmount = cgstAmount;
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
