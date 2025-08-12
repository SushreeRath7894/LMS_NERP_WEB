package nirmalya.aathithya.webmodule.sales.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

public class DeliveryChallanModel {
	private String saleDeliveryChallan;
	private String deliveryChallanDate;
	private String saleInvoice;
	private String saleInvoiceId;
	private String poId;
	private String salesOrder;
	private String salesOrderId;
	private String quotationId;
	private String qutActive;
	private String qutCreatedBy;
	private String qutUpdatedOn;
	private String itemId;
	private Integer slNo;
	private String itemName;
	private String itemDesc;
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
	private String salesActive;
	private String organization;
	private String orgDivision;
	private String custId;
	private String custName;

	private Double adjustment;
	private String tcsId;
	private Double tcsAmount;
	private String tcs;
	private String salesPersonId;
	private String salesPerson;
	private String spName;
	private String paymentTermId;
	private String qutDescription;
	private String terms;

	private String invoiceDate;

	private String challanType;
	private String reference;
	private String salePackageId;
	private String soDate;
	private String hsnCode;
	private String unit;
	private String unitName;
	private String ebillNo;
	private String ebillDate;
	private Double taxableAmt;
	private Double totalTaxableAmt;
	private String grandTotalInWords;
	private Double totalQuantity;
	private String shipmentId;
	private String shipmentStatus;
	private String invoiceStatus;
	private String tMode;
	private String freight;
	private Double freigtCharge;
	private Double freigtTaxRate;
	private Double total;
	private String referenceId;
	private String referenceDate;
	private String vehicleNo;
	private String transporterId;
	private String transporterName;
	private String transporterGst;
	private String lrNumber;

	private Double fcGstAmnt;
	private Double totalFreightCharges;
	private String carrier;
	private String tracking;
	private Double rtnQut;
	private Double receivedQut;
	private Double pendingQut;
	private String orgAddress;
	private String orgEmail;
	private String orgPhone;
	private String orgImage;
	private String shippingHiddenId;
	private String orgGstNo;
	private String eoe;

	// For Pdf.

	private String quantity1;
	private String unitPrice1;
	private String gstRate1;
	private String lineTotal1;
	private String subTotal1;
	private String grandTotal1;
	private String qIGST1;
	private String qCGST1;
	private String qSGST1;
	private String itemIgst1;
	private String itemCgst1;
	private String itemSgst1;
	private String tcsAmount1;
	private String taxableAmt1;
	private String totalQuantity1;
	private String totalTaxableAmt1;
	private String dcRemarks;
	private String dcComment;
	private String dcDesc;
	private String itemSlNo;
	private String chQty;
	private String packedQty;
	private String pendingQty;

	List<InventoryVendorDocumentModel> documentList;

	public DeliveryChallanModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSaleDeliveryChallan() {
		return saleDeliveryChallan;
	}

	public void setSaleDeliveryChallan(String saleDeliveryChallan) {
		this.saleDeliveryChallan = saleDeliveryChallan;
	}

	public String getSaleInvoice() {
		return saleInvoice;
	}

	public void setSaleInvoice(String saleInvoice) {
		this.saleInvoice = saleInvoice;
	}

	public String getSaleInvoiceId() {
		return saleInvoiceId;
	}

	public void setSaleInvoiceId(String saleInvoiceId) {
		this.saleInvoiceId = saleInvoiceId;
	}

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

	public String getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}

	public String getQutActive() {
		return qutActive;
	}

	public void setQutActive(String qutActive) {
		this.qutActive = qutActive;
	}

	public String getQutCreatedBy() {
		return qutCreatedBy;
	}

	public void setQutCreatedBy(String qutCreatedBy) {
		this.qutCreatedBy = qutCreatedBy;
	}

	public String getQutUpdatedOn() {
		return qutUpdatedOn;
	}

	public void setQutUpdatedOn(String qutUpdatedOn) {
		this.qutUpdatedOn = qutUpdatedOn;
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

	public String getSalesActive() {
		return salesActive;
	}

	public void setSalesActive(String salesActive) {
		this.salesActive = salesActive;
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

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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

	public String getSalesPersonId() {
		return salesPersonId;
	}

	public void setSalesPersonId(String salesPersonId) {
		this.salesPersonId = salesPersonId;
	}

	public String getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getPaymentTermId() {
		return paymentTermId;
	}

	public void setPaymentTermId(String paymentTermId) {
		this.paymentTermId = paymentTermId;
	}

	public String getQutDescription() {
		return qutDescription;
	}

	public void setQutDescription(String qutDescription) {
		this.qutDescription = qutDescription;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getChallanType() {
		return challanType;
	}

	public void setChallanType(String challanType) {
		this.challanType = challanType;
	}

	public List<InventoryVendorDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<InventoryVendorDocumentModel> documentList) {
		this.documentList = documentList;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getSalePackageId() {
		return salePackageId;
	}

	public void setSalePackageId(String salePackageId) {
		this.salePackageId = salePackageId;
	}

	public String getSoDate() {
		return soDate;
	}

	public void setSoDate(String soDate) {
		this.soDate = soDate;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
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

	public String getEbillNo() {
		return ebillNo;
	}

	public void setEbillNo(String ebillNo) {
		this.ebillNo = ebillNo;
	}

	public String getEbillDate() {
		return ebillDate;
	}

	public void setEbillDate(String ebillDate) {
		this.ebillDate = ebillDate;
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

	public Double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	public String getDeliveryChallanDate() {
		return deliveryChallanDate;
	}

	public void setDeliveryChallanDate(String deliveryChallanDate) {
		this.deliveryChallanDate = deliveryChallanDate;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String gettMode() {
		return tMode;
	}

	public void settMode(String tMode) {
		this.tMode = tMode;
	}

	public Double getFreigtCharge() {
		return freigtCharge;
	}

	public void setFreigtCharge(Double freigtCharge) {
		this.freigtCharge = freigtCharge;
	}

	public Double getFreigtTaxRate() {
		return freigtTaxRate;
	}

	public void setFreigtTaxRate(Double freigtTaxRate) {
		this.freigtTaxRate = freigtTaxRate;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(String referenceDate) {
		this.referenceDate = referenceDate;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getTransporterId() {
		return transporterId;
	}

	public void setTransporterId(String transporterId) {
		this.transporterId = transporterId;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getLrNumber() {
		return lrNumber;
	}

	public void setLrNumber(String lrNumber) {
		this.lrNumber = lrNumber;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public Double getTotalFreightCharges() {
		return totalFreightCharges;
	}

	public void setTotalFreightCharges(Double totalFreightCharges) {
		this.totalFreightCharges = totalFreightCharges;
	}

	public Double getFcGstAmnt() {
		return fcGstAmnt;
	}

	public void setFcGstAmnt(Double fcGstAmnt) {
		this.fcGstAmnt = fcGstAmnt;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getTracking() {
		return tracking;
	}

	public void setTracking(String tracking) {
		this.tracking = tracking;
	}

	public Double getRtnQut() {
		return rtnQut;
	}

	public void setRtnQut(Double rtnQut) {
		this.rtnQut = rtnQut;
	}

	public Double getReceivedQut() {
		return receivedQut;
	}

	public void setReceivedQut(Double receivedQut) {
		this.receivedQut = receivedQut;
	}

	public Double getPendingQut() {
		return pendingQut;
	}

	public void setPendingQut(Double pendingQut) {
		this.pendingQut = pendingQut;
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

	public String getShippingHiddenId() {
		return shippingHiddenId;
	}

	public void setShippingHiddenId(String shippingHiddenId) {
		this.shippingHiddenId = shippingHiddenId;
	}

	public String getOrgGstNo() {
		return orgGstNo;
	}

	public void setOrgGstNo(String orgGstNo) {
		this.orgGstNo = orgGstNo;
	}

	public String getEoe() {
		return eoe;
	}

	public void setEoe(String eoe) {
		this.eoe = eoe;
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

	public String getGstRate1() {
		return gstRate1;
	}

	public void setGstRate1(String gstRate1) {
		this.gstRate1 = gstRate1;
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

	public String getqIGST1() {
		return qIGST1;
	}

	public void setqIGST1(String qIGST1) {
		this.qIGST1 = qIGST1;
	}

	public String getqCGST1() {
		return qCGST1;
	}

	public void setqCGST1(String qCGST1) {
		this.qCGST1 = qCGST1;
	}

	public String getqSGST1() {
		return qSGST1;
	}

	public void setqSGST1(String qSGST1) {
		this.qSGST1 = qSGST1;
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

	public String getTcsAmount1() {
		return tcsAmount1;
	}

	public void setTcsAmount1(String tcsAmount1) {
		this.tcsAmount1 = tcsAmount1;
	}

	public String getTaxableAmt1() {
		return taxableAmt1;
	}

	public void setTaxableAmt1(String taxableAmt1) {
		this.taxableAmt1 = taxableAmt1;
	}

	public String getTotalQuantity1() {
		return totalQuantity1;
	}

	public void setTotalQuantity1(String totalQuantity1) {
		this.totalQuantity1 = totalQuantity1;
	}

	public String getTotalTaxableAmt1() {
		return totalTaxableAmt1;
	}

	public void setTotalTaxableAmt1(String totalTaxableAmt1) {
		this.totalTaxableAmt1 = totalTaxableAmt1;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getTransporterGst() {
		return transporterGst;
	}

	public void setTransporterGst(String transporterGst) {
		this.transporterGst = transporterGst;
	}

	public String getDcRemarks() {
		return dcRemarks;
	}

	public void setDcRemarks(String dcRemarks) {
		this.dcRemarks = dcRemarks;
	}

	public String getDcComment() {
		return dcComment;
	}

	public void setDcComment(String dcComment) {
		this.dcComment = dcComment;
	}

	public String getDcDesc() {
		return dcDesc;
	}

	public void setDcDesc(String dcDesc) {
		this.dcDesc = dcDesc;
	}

	public String getItemSlNo() {
		return itemSlNo;
	}

	public void setItemSlNo(String itemSlNo) {
		this.itemSlNo = itemSlNo;
	}

	public String getChQty() {
		return chQty;
	}

	public void setChQty(String chQty) {
		this.chQty = chQty;
	}

	public String getPackedQty() {
		return packedQty;
	}

	public void setPackedQty(String packedQty) {
		this.packedQty = packedQty;
	}

	public String getPendingQty() {
		return pendingQty;
	}

	public void setPendingQty(String pendingQty) {
		this.pendingQty = pendingQty;
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
