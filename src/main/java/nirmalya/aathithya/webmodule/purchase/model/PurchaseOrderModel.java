package nirmalya.aathithya.webmodule.purchase.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

public class PurchaseOrderModel {

	private String vendorId;
	private String vendorName;
	private String poId;
	private String qutValidDate;
	private String reference;
	private String paymentTermId;
	private String carrierId;
	private String qutDescription;
	private String itemId;
	private Integer slNo;
	private String itemName;
	private String hsnCode;
	private Double quantity;
	private Double unitPrice;
	private Double discount;
	private Double gstRate;
	private Double lineTotal;
	private Double subTotal;
	private Double qIGST;
	private Double qCGST;
	private Double qSGST;
	private Double grandTotal;
	private Boolean taxType;
	private String sku;
	private Double itemIgst;
	private Double itemCgst;
	private Double itemSgst;
	private String poActive;
	private Double adjustment;
	private String tcsId;
	private Double tcsAmount;
	private String tcs;
	private String terms;
	private String qutActive;
	private String createdBy;
	private String updatedOn;
	private String organization;
	private String orgDivision;
	private String shippingDetails;
	private String orgDetails;
	private String custDetails;
	private String unit;
	private String unitName;
	private Double taxableAmt;
	private Double totalTaxableAmt;
	private Double pendingQuantity;
	private Double receivingQuantity;
	private Double receivedQuantity;
	private String description;
	private String grandTotalInWords;
	private String custGSTNo;
	private String invoiceStatus;
	private String quotationId;
	private String grnId;
	private String grnDate;
	private String grnStatus;
	private String sapPOId;
	private String totalQty;
	private String received;
	private String pending;
	private String receivingQty;
	private String varifyStatus;
	private String appoveStatus;
	private Double pendingQty;
	private String annextureId;
	private String itemDesc;
	private String descWork;
	private String termDelivery;

	private String vehicle_no;
	private String driver_name;
	private String driver_mobile;
	private String challan_no;
	private String challan_dt;
	private String gatePass;

	private String plant;
	private String docDate;
	private String purchaseDoc;
	private String item;
	private String vendorORsupllyingPlant;
	private String material;
	private String shortText;
	private String oUnit;
	private Double netPrice;
	private Double orderQuantity;
	private String toBeDel;
	private String agreement;
	private String per;
	private String rel;
	private String itemQty;
	private String brandName;
	private String packaging;
	private String makeSpec;

	private String newSubTotal;
	private String newGrandTotal;
	private String project;
	private String projectName;
	private String closeStatus;
	private String modelSize;

	private String orgAddress;
	private String orgEmail;
	private String orgPhone;
	private String orgImage;
	List<InventoryVendorDocumentModel> documentList;

	private String headerList;
	private String itemList;
	private String annexureName;

	private String advance;
	
	private String vndrTds;

	private String discountType;
	
	// For Pdf.
	private String quantity1;
	private String unitPrice1;
	private String lineTotal1;
	private String subTotal1;
	private String grandTotal1;
	private String itemIgst1;
	private String itemCgst1;
	private String itemSgst1;
	private String adjustment1;
	private String tcsAmount1;
	
	
	
     private String vendorApproveStatus;
	
	private String vendorDCSts;
	private String venndorInvcSts;
	
	private String tcstype;
	private String tds;
	private String tdstype;
	private String tdsAmount;
	// For Pdf.
	

	public PurchaseOrderModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNewSubTotal() {
		return newSubTotal;
	}

	public void setNewSubTotal(String newSubTotal) {
		this.newSubTotal = newSubTotal;
	}

	public String getNewGrandTotal() {
		return newGrandTotal;
	}

	public void setNewGrandTotal(String newGrandTotal) {
		this.newGrandTotal = newGrandTotal;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public String getAnnextureId() {
		return annextureId;
	}

	public void setAnnextureId(String annextureId) {
		this.annextureId = annextureId;
	}

	public String getQutValidDate() {
		return qutValidDate;
	}

	public void setQutValidDate(String qutValidDate) {
		this.qutValidDate = qutValidDate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getPaymentTermId() {
		return paymentTermId;
	}

	public void setPaymentTermId(String paymentTermId) {
		this.paymentTermId = paymentTermId;
	}

	public String getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}

	public String getQutDescription() {
		return qutDescription;
	}

	public void setQutDescription(String qutDescription) {
		this.qutDescription = qutDescription;
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

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getGstRate() {
		return gstRate;
	}

	public void setGstRate(Double gstRate) {
		this.gstRate = gstRate;
	}

	public Double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getqIGST() {
		return qIGST;
	}

	public void setqIGST(Double qIGST) {
		this.qIGST = qIGST;
	}

	public Double getqCGST() {
		return qCGST;
	}

	public void setqCGST(Double qCGST) {
		this.qCGST = qCGST;
	}

	public Double getqSGST() {
		return qSGST;
	}

	public void setqSGST(Double qSGST) {
		this.qSGST = qSGST;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Boolean getTaxType() {
		return taxType;
	}

	public void setTaxType(Boolean taxType) {
		this.taxType = taxType;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Double getItemIgst() {
		return itemIgst;
	}

	public void setItemIgst(Double itemIgst) {
		this.itemIgst = itemIgst;
	}

	public Double getItemCgst() {
		return itemCgst;
	}

	public void setItemCgst(Double itemCgst) {
		this.itemCgst = itemCgst;
	}

	public Double getItemSgst() {
		return itemSgst;
	}

	public void setItemSgst(Double itemSgst) {
		this.itemSgst = itemSgst;
	}

	public String getPoActive() {
		return poActive;
	}

	public void setPoActive(String poActive) {
		this.poActive = poActive;
	}

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public String getTcsId() {
		return tcsId;
	}

	public void setTcsId(String tcsId) {
		this.tcsId = tcsId;
	}

	public Double getTcsAmount() {
		return tcsAmount;
	}

	public void setTcsAmount(Double tcsAmount) {
		this.tcsAmount = tcsAmount;
	}

	public String getTcs() {
		return tcs;
	}

	public void setTcs(String tcs) {
		this.tcs = tcs;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getQutActive() {
		return qutActive;
	}

	public void setQutActive(String qutActive) {
		this.qutActive = qutActive;
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

	public List<InventoryVendorDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<InventoryVendorDocumentModel> documentList) {
		this.documentList = documentList;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getShippingDetails() {
		return shippingDetails;
	}

	public void setShippingDetails(String shippingDetails) {
		this.shippingDetails = shippingDetails;
	}

	public String getOrgDetails() {
		return orgDetails;
	}

	public void setOrgDetails(String orgDetails) {
		this.orgDetails = orgDetails;
	}

	public String getCustDetails() {
		return custDetails;
	}

	public void setCustDetails(String custDetails) {
		this.custDetails = custDetails;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getPendingQuantity() {
		return pendingQuantity;
	}

	public void setPendingQuantity(Double pendingQuantity) {
		this.pendingQuantity = pendingQuantity;
	}

	public Double getReceivingQuantity() {
		return receivingQuantity;
	}

	public void setReceivingQuantity(Double receivingQuantity) {
		this.receivingQuantity = receivingQuantity;
	}

	public Double getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(Double receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getDescWork() {
		return descWork;
	}

	public void setDescWork(String descWork) {
		this.descWork = descWork;
	}

	public String getTermDelivery() {
		return termDelivery;
	}

	public void setTermDelivery(String termDelivery) {
		this.termDelivery = termDelivery;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(Double taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public Double getTotalTaxableAmt() {
		return totalTaxableAmt;
	}

	public void setTotalTaxableAmt(Double totalTaxableAmt) {
		this.totalTaxableAmt = totalTaxableAmt;
	}

	public String getGrandTotalInWords() {
		return grandTotalInWords;
	}

	public void setGrandTotalInWords(String grandTotalInWords) {
		this.grandTotalInWords = grandTotalInWords;
	}

	public String getCustGSTNo() {
		return custGSTNo;
	}

	public void setCustGSTNo(String custGSTNo) {
		this.custGSTNo = custGSTNo;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}

	public String getGrnId() {
		return grnId;
	}

	public void setGrnId(String grnId) {
		this.grnId = grnId;
	}

	public String getGrnDate() {
		return grnDate;
	}

	public void setGrnDate(String grnDate) {
		this.grnDate = grnDate;
	}

	public String getGrnStatus() {
		return grnStatus;
	}

	public void setGrnStatus(String grnStatus) {
		this.grnStatus = grnStatus;
	}

	public String getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(String totalQty) {
		this.totalQty = totalQty;
	}

	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public String getPending() {
		return pending;
	}

	public void setPending(String pending) {
		this.pending = pending;
	}

	public String getReceivingQty() {
		return receivingQty;
	}

	public void setReceivingQty(String receivingQty) {
		this.receivingQty = receivingQty;
	}

	public String getVarifyStatus() {
		return varifyStatus;
	}

	public void setVarifyStatus(String varifyStatus) {
		this.varifyStatus = varifyStatus;
	}

	public String getAppoveStatus() {
		return appoveStatus;
	}

	public void setAppoveStatus(String appoveStatus) {
		this.appoveStatus = appoveStatus;
	}

	public Double getPendingQty() {
		return pendingQty;
	}

	public void setPendingQty(Double pendingQty) {
		this.pendingQty = pendingQty;
	}

	public String getVehicle_no() {
		return vehicle_no;
	}

	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public String getDriver_mobile() {
		return driver_mobile;
	}

	public void setDriver_mobile(String driver_mobile) {
		this.driver_mobile = driver_mobile;
	}

	public String getChallan_no() {
		return challan_no;
	}

	public void setChallan_no(String challan_no) {
		this.challan_no = challan_no;
	}

	public String getChallan_dt() {
		return challan_dt;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public String getMakeSpec() {
		return makeSpec;
	}

	public void setMakeSpec(String makeSpec) {
		this.makeSpec = makeSpec;
	}

	public void setChallan_dt(String challan_dt) {
		this.challan_dt = challan_dt;
	}

	public String getGatePass() {
		return gatePass;
	}

	public void setGatePass(String gatePass) {
		this.gatePass = gatePass;
	}

	public String getSapPOId() {
		return sapPOId;
	}

	public void setSapPOId(String sapPOId) {
		this.sapPOId = sapPOId;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getDocDate() {
		return docDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public String getPurchaseDoc() {
		return purchaseDoc;
	}

	public void setPurchaseDoc(String purchaseDoc) {
		this.purchaseDoc = purchaseDoc;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getVendorORsupllyingPlant() {
		return vendorORsupllyingPlant;
	}

	public void setVendorORsupllyingPlant(String vendorORsupllyingPlant) {
		this.vendorORsupllyingPlant = vendorORsupllyingPlant;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getShortText() {
		return shortText;
	}

	public void setShortText(String shortText) {
		this.shortText = shortText;
	}

	public String getoUnit() {
		return oUnit;
	}

	public void setoUnit(String oUnit) {
		this.oUnit = oUnit;
	}

	public Double getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(Double netPrice) {
		this.netPrice = netPrice;
	}

	public Double getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Double orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String getToBeDel() {
		return toBeDel;
	}

	public void setToBeDel(String toBeDel) {
		this.toBeDel = toBeDel;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public String getPer() {
		return per;
	}

	public void setPer(String per) {
		this.per = per;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getItemQty() {
		return itemQty;
	}

	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	public String getCloseStatus() {
		return closeStatus;
	}

	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}

	public String getModelSize() {
		return modelSize;
	}

	public void setModelSize(String modelSize) {
		this.modelSize = modelSize;
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

	public String getHeaderList() {
		return headerList;
	}

	public void setHeaderList(String headerList) {
		this.headerList = headerList;
	}

	public String getItemList() {
		return itemList;
	}

	public void setItemList(String itemList) {
		this.itemList = itemList;
	}

	public String getAnnexureName() {
		return annexureName;
	}

	public void setAnnexureName(String annexureName) {
		this.annexureName = annexureName;
	}

	public String getAdvance() {
		return advance;
	}

	public void setAdvance(String advance) {
		this.advance = advance;
	}
	
	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
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

	public String getSubTotal1() {
		return subTotal1;
	}

	public void setSubTotal1(String subTotal1) {
		this.subTotal1 = subTotal1;
	}

	public String getGrandTotal1() {
		return grandTotal1;
	}

	public void setGrandTotal1(String grandTotal1) {
		this.grandTotal1 = grandTotal1;
	}

	public String getItemIgst1() {
		return itemIgst1;
	}

	public void setItemIgst1(String itemIgst1) {
		this.itemIgst1 = itemIgst1;
	}

	public String getItemCgst1() {
		return itemCgst1;
	}

	public void setItemCgst1(String itemCgst1) {
		this.itemCgst1 = itemCgst1;
	}

	public String getItemSgst1() {
		return itemSgst1;
	}

	public void setItemSgst1(String itemSgst1) {
		this.itemSgst1 = itemSgst1;
	}

	public String getAdjustment1() {
		return adjustment1;
	}

	public void setAdjustment1(String adjustment1) {
		this.adjustment1 = adjustment1;
	}

	public String getTcsAmount1() {
		return tcsAmount1;
	}

	public void setTcsAmount1(String tcsAmount1) {
		this.tcsAmount1 = tcsAmount1;
	}

	public String getVndrTds() {
		return vndrTds;
	}

	public void setVndrTds(String vndrTds) {
		this.vndrTds = vndrTds;
	}

	public String getVendorApproveStatus() {
		return vendorApproveStatus;
	}

	public void setVendorApproveStatus(String vendorApproveStatus) {
		this.vendorApproveStatus = vendorApproveStatus;
	}

	public String getVendorDCSts() {
		return vendorDCSts;
	}

	public void setVendorDCSts(String vendorDCSts) {
		this.vendorDCSts = vendorDCSts;
	}

	public String getVenndorInvcSts() {
		return venndorInvcSts;
	}

	public void setVenndorInvcSts(String venndorInvcSts) {
		this.venndorInvcSts = venndorInvcSts;
	}

	public String getTcstype() {
		return tcstype;
	}

	public void setTcstype(String tcstype) {
		this.tcstype = tcstype;
	}

	public String getTds() {
		return tds;
	}

	public void setTds(String tds) {
		this.tds = tds;
	}

	public String getTdstype() {
		return tdstype;
	}

	public void setTdstype(String tdstype) {
		this.tdstype = tdstype;
	}

	public String getTdsAmount() {
		return tdsAmount;
	}

	public void setTdsAmount(String tdsAmount) {
		this.tdsAmount = tdsAmount;
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
