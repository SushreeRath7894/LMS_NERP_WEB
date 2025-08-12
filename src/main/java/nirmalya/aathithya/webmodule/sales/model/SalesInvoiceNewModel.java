package nirmalya.aathithya.webmodule.sales.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

public class SalesInvoiceNewModel {
	private String sapInvoiceId;
	private String commercialInvNo;
	private String poOrSo;
	private String billingDate;
	private String sendingPlant;
	private String receivingPlant;
	private String indicator;
	private String sourceState;
	private String stateCode;
	private String destinationState;
	private String materialCode;
	private String materialDescription;
	private String hsn;
	private String priceLot;
	private String deliveryDoc;
	private String uom;
	private String caseConfig;
	private Double quantityInCase;
	private Double netWeight;
	private String weigtUnit;
	private Double basicPrice;
	private Double netValue;
	private Double cgstValue;
	private Double igstValue;
	private Double sgstValue;
	private Double totalValue;

	private String saleInvoice;
	private String salesOrder;
	private String salesOrderId;
	private String quotationId;
	private String qutActive;
	private String qutCreatedBy;
	private String qutUpdatedOn;
	private String itemId;
	private Integer slNo;
	private String itemName;
	private String hsnCode;
	private Double quantity;
	private Double quantitynew;
	private String unit;
	private String unitName;
	private Double taxableAmt;
	private Double totalTaxableAmt;
	private String organization;
	private String orgDivision;

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
	private String poNo;
	private String storeId;
	private String salesActive;

	private String paymentId;
	private String sOrder;
	private String sInvoice;
	private String paymodeId;
	private String payDate;
	private Double totalAmnt;
	private Double paidAmnt;
	private String tdN;
	private String ckn;
	private String outStdAmt;
	private String custId;
	private String custName;
	private String rVoucher;
	private Boolean taxTypepay;
	private Double gstRatepay;
	private Double subTotalpay;
	private Double qIGSTpay;
	private Double qCGSTpay;
	private Double qSGSTpay;
	private Double grandTotalpay;
	private Double adjustment;

	private String tcs;
	private String salesPersonId;
	private String salesPerson;
	private String spName;
	private String paymentTermId;
	private String qutDescription;
	private String terms;
	private String subject;
	private String invoiceDate;
	private String dueDate;
	private String status;
	private String grandTotalInWords;
	private String ebillNo;
	private String ebillDate;
	private String challanId;
	private String challanDate;
	private String noOfItem;
	private Double totalFreightCharges;
	private Double total;
	private String referenceId;
	private String referenceDate;
	private String description;
	private String tMode;
	private String vehicleNo;
	private String transporterId;
	private String transporterName;
	private String lrNumber;
	private String dateOfSupply;
	private String placeOfSupply;
	private String poId;
	private String poDate;
	private String gstNo;
	private String billingAddress;
	private String billingState;
	private String billingMobileNo;
	private String shippingAddress;
	private String shippingState;
	private String shippingMobileNo;
	private String itemDesc;
	private Double fcGstRate;

	private Double fcGstAmnt;
	private Double totalFCharges;
	private Double noOfItems;
	private Double totalTaxableValue;
	private Double totalIgstAmount;
	private Double totalCgstAmount;
	private Double totalSgstAmount;
	private Double grandTotalTaxAmount;
	private Double taxAmountGst;
	private String orgAddress;
	private String orgEmail;
	private String orgPhone;
	private String orgImage;
	private String orgGstNo;
	private String orgCinNo;
	private String shippingHiddenId;
	private String projectId;
	private String projectName;
	private String categoryName;
	private String categoryId;
	private String paymentTerm;
	private String sacCode;
	private Double currVal;
	private String projectcategoryName;
	private String projectcategoryId;
	private String shippingDetails;
	private String orgDetails;
	private String custDetails;
	private String rejectStatus;
	private String challanNo;
	private String eWayBillNo;
	private String eWayBillDate;
	private String shipmentNumber;
	private String shipmentType;
	private String doNumber;

	private String taxInvoiceOF;

	// Keys For Sales Invoice Pdf.

	private String quantity1;
	private String unitPrice1;
	private String lineTotal1;
	private String gstRate1;
	private String taxableAmt1;
	private String itemIgst1;
	private String itemCgst1;
	private String itemSgst1;
	private String total1;
	private String qIGST1;
	private String qCGST1;
	private String qSGST1;
	private String grandTotalTaxAmount1;
	private String taxAmountGst1;
	private String adjustment1;
	private String grandTotal1;

	private String tcsId;

	private String irnNumber;

	private String tcsValue;
	private String tcsType;
	private Double tcsAmount;

	private String tdsValue;
	private String tdsType;
	private String tdsAmount;

	List<InventoryVendorDocumentModel> documentList;
	List<SalesInvoiceMultipleWayBillCh> challanBillList;

	private String challanIds;
	private String dateofSupply;
	private String destination;
	private String otherreference;
	private String saleDeliverysales;
	private String subtotal;
	private String type;

	private String saleDeliverysales1;
	private String custId1;
	private String custName1;
	private String soId;
	private String salesRId;

	private String expectedShipmentDate1;
	private String deliveryTerm1;
	private String deliveryMode1;
	private String piRemarks;

	public SalesInvoiceNewModel() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getTdN() {
		return tdN;
	}

	public void setTdN(String tdN) {
		this.tdN = tdN;
	}

	public String getCkn() {
		return ckn;
	}

	public void setCkn(String ckn) {
		this.ckn = ckn;
	}

	public String getSaleInvoice() {
		return saleInvoice;
	}

	public void setSaleInvoice(String saleInvoice) {
		this.saleInvoice = saleInvoice;
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

	public Double getQuantitynew() {
		return quantitynew;
	}

	public void setQuantitynew(Double quantitynew) {
		this.quantitynew = quantitynew;
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

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getSalesActive() {
		return salesActive;
	}

	public void setSalesActive(String salesActive) {
		this.salesActive = salesActive;
	}

	public String getrVoucher() {
		return rVoucher;
	}

	public void setrVoucher(String rVoucher) {
		this.rVoucher = rVoucher;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getsOrder() {
		return sOrder;
	}

	public void setsOrder(String sOrder) {
		this.sOrder = sOrder;
	}

	public String getsInvoice() {
		return sInvoice;
	}

	public void setsInvoice(String sInvoice) {
		this.sInvoice = sInvoice;
	}

	public String getPaymodeId() {
		return paymodeId;
	}

	public void setPaymodeId(String paymodeId) {
		this.paymodeId = paymodeId;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public Double getTotalAmnt() {
		return totalAmnt;
	}

	public void setTotalAmnt(Double totalAmnt) {
		this.totalAmnt = totalAmnt;
	}

	public Double getPaidAmnt() {
		return paidAmnt;
	}

	public void setPaidAmnt(Double paidAmnt) {
		this.paidAmnt = paidAmnt;
	}

	public String getOutStdAmt() {
		return outStdAmt;
	}

	public void setOutStdAmt(String outStdAmt) {
		this.outStdAmt = outStdAmt;
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

	public Boolean getTaxTypepay() {
		return taxTypepay;
	}

	public void setTaxTypepay(Boolean taxTypepay) {
		this.taxTypepay = taxTypepay;
	}

	public Double getGstRatepay() {
		return gstRatepay;
	}

	public void setGstRatepay(Double gstRatepay) {
		this.gstRatepay = gstRatepay;
	}

	public Double getSubTotalpay() {
		return subTotalpay;
	}

	public void setSubTotalpay(Double subTotalpay) {
		this.subTotalpay = subTotalpay;
	}

	public Double getqIGSTpay() {
		return qIGSTpay;
	}

	public void setqIGSTpay(Double qIGSTpay) {
		this.qIGSTpay = qIGSTpay;
	}

	public Double getqCGSTpay() {
		return qCGSTpay;
	}

	public void setqCGSTpay(Double qCGSTpay) {
		this.qCGSTpay = qCGSTpay;
	}

	public Double getqSGSTpay() {
		return qSGSTpay;
	}

	public void setqSGSTpay(Double qSGSTpay) {
		this.qSGSTpay = qSGSTpay;
	}

	public Double getGrandTotalpay() {
		return grandTotalpay;
	}

	public void setGrandTotalpay(Double grandTotalpay) {
		this.grandTotalpay = grandTotalpay;
	}

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public List<InventoryVendorDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<InventoryVendorDocumentModel> documentList) {
		this.documentList = documentList;
	}

	public String getTcsId() {
		return tcsId;
	}

	public void setTcsId(String tcsId) {
		this.tcsId = tcsId;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getChallanId() {
		return challanId;
	}

	public void setChallanId(String challanId) {
		this.challanId = challanId;
	}

	public String getChallanDate() {
		return challanDate;
	}

	public void setChallanDate(String challanDate) {
		this.challanDate = challanDate;
	}

	public String getNoOfItem() {
		return noOfItem;
	}

	public void setNoOfItem(String noOfItem) {
		this.noOfItem = noOfItem;
	}

	public List<SalesInvoiceMultipleWayBillCh> getChallanBillList() {
		return challanBillList;
	}

	public void setChallanBillList(List<SalesInvoiceMultipleWayBillCh> challanBillList) {
		this.challanBillList = challanBillList;
	}

	public Double getTotalFreightCharges() {
		return totalFreightCharges;
	}

	public void setTotalFreightCharges(Double totalFreightCharges) {
		this.totalFreightCharges = totalFreightCharges;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String gettMode() {
		return tMode;
	}

	public void settMode(String tMode) {
		this.tMode = tMode;
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

	public String getDateOfSupply() {
		return dateOfSupply;
	}

	public void setDateOfSupply(String dateOfSupply) {
		this.dateOfSupply = dateOfSupply;
	}

	public String getPlaceOfSupply() {
		return placeOfSupply;
	}

	public void setPlaceOfSupply(String placeOfSupply) {
		this.placeOfSupply = placeOfSupply;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public String getBillingMobileNo() {
		return billingMobileNo;
	}

	public void setBillingMobileNo(String billingMobileNo) {
		this.billingMobileNo = billingMobileNo;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}

	public String getShippingMobileNo() {
		return shippingMobileNo;
	}

	public void setShippingMobileNo(String shippingMobileNo) {
		this.shippingMobileNo = shippingMobileNo;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getFcGstRate() {
		return fcGstRate;
	}

	public void setFcGstRate(Double fcGstRate) {
		this.fcGstRate = fcGstRate;
	}

	public Double getFcGstAmnt() {
		return fcGstAmnt;
	}

	public void setFcGstAmnt(Double fcGstAmnt) {
		this.fcGstAmnt = fcGstAmnt;
	}

	public Double getTotalFCharges() {
		return totalFCharges;
	}

	public void setTotalFCharges(Double totalFCharges) {
		this.totalFCharges = totalFCharges;
	}

	public Double getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(Double noOfItems) {
		this.noOfItems = noOfItems;
	}

	public Double getTotalTaxableValue() {
		return totalTaxableValue;
	}

	public void setTotalTaxableValue(Double totalTaxableValue) {
		this.totalTaxableValue = totalTaxableValue;
	}

	public Double getTotalIgstAmount() {
		return totalIgstAmount;
	}

	public void setTotalIgstAmount(Double totalIgstAmount) {
		this.totalIgstAmount = totalIgstAmount;
	}

	public Double getTotalCgstAmount() {
		return totalCgstAmount;
	}

	public void setTotalCgstAmount(Double totalCgstAmount) {
		this.totalCgstAmount = totalCgstAmount;
	}

	public Double getTotalSgstAmount() {
		return totalSgstAmount;
	}

	public void setTotalSgstAmount(Double totalSgstAmount) {
		this.totalSgstAmount = totalSgstAmount;
	}

	public Double getGrandTotalTaxAmount() {
		return grandTotalTaxAmount;
	}

	public void setGrandTotalTaxAmount(Double grandTotalTaxAmount) {
		this.grandTotalTaxAmount = grandTotalTaxAmount;
	}

	public Double getTaxAmountGst() {
		return taxAmountGst;
	}

	public void setTaxAmountGst(Double taxAmountGst) {
		this.taxAmountGst = taxAmountGst;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
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

	public String getOrgGstNo() {
		return orgGstNo;
	}

	public void setOrgGstNo(String orgGstNo) {
		this.orgGstNo = orgGstNo;
	}

	public String getOrgCinNo() {
		return orgCinNo;
	}

	public void setOrgCinNo(String orgCinNo) {
		this.orgCinNo = orgCinNo;
	}

	public String getShippingHiddenId() {
		return shippingHiddenId;
	}

	public void setShippingHiddenId(String shippingHiddenId) {
		this.shippingHiddenId = shippingHiddenId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getSacCode() {
		return sacCode;
	}

	public void setSacCode(String sacCode) {
		this.sacCode = sacCode;
	}

	public Double getCurrVal() {
		return currVal;
	}

	public void setCurrVal(Double currVal) {
		this.currVal = currVal;
	}

	public String getProjectcategoryName() {
		return projectcategoryName;
	}

	public void setProjectcategoryName(String projectcategoryName) {
		this.projectcategoryName = projectcategoryName;
	}

	public String getProjectcategoryId() {
		return projectcategoryId;
	}

	public void setProjectcategoryId(String projectcategoryId) {
		this.projectcategoryId = projectcategoryId;
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

	public String getRejectStatus() {
		return rejectStatus;
	}

	public void setRejectStatus(String rejectStatus) {
		this.rejectStatus = rejectStatus;
	}

	public String getSapInvoiceId() {
		return sapInvoiceId;
	}

	public void setSapInvoiceId(String sapInvoiceId) {
		this.sapInvoiceId = sapInvoiceId;
	}

	public String getCommercialInvNo() {
		return commercialInvNo;
	}

	public void setCommercialInvNo(String commercialInvNo) {
		this.commercialInvNo = commercialInvNo;
	}

	public String getPoOrSo() {
		return poOrSo;
	}

	public void setPoOrSo(String poOrSo) {
		this.poOrSo = poOrSo;
	}

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	public String getSendingPlant() {
		return sendingPlant;
	}

	public void setSendingPlant(String sendingPlant) {
		this.sendingPlant = sendingPlant;
	}

	public String getReceivingPlant() {
		return receivingPlant;
	}

	public void setReceivingPlant(String receivingPlant) {
		this.receivingPlant = receivingPlant;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public String getSourceState() {
		return sourceState;
	}

	public void setSourceState(String sourceState) {
		this.sourceState = sourceState;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getDestinationState() {
		return destinationState;
	}

	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public String getHsn() {
		return hsn;
	}

	public void setHsn(String hsn) {
		this.hsn = hsn;
	}

	public String getPriceLot() {
		return priceLot;
	}

	public void setPriceLot(String priceLot) {
		this.priceLot = priceLot;
	}

	public String getDeliveryDoc() {
		return deliveryDoc;
	}

	public void setDeliveryDoc(String deliveryDoc) {
		this.deliveryDoc = deliveryDoc;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getCaseConfig() {
		return caseConfig;
	}

	public void setCaseConfig(String caseConfig) {
		this.caseConfig = caseConfig;
	}

	public Double getQuantityInCase() {
		return quantityInCase;
	}

	public void setQuantityInCase(Double quantityInCase) {
		this.quantityInCase = quantityInCase;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public String getWeigtUnit() {
		return weigtUnit;
	}

	public void setWeigtUnit(String weigtUnit) {
		this.weigtUnit = weigtUnit;
	}

	public Double getBasicPrice() {
		return basicPrice;
	}

	public void setBasicPrice(Double basicPrice) {
		this.basicPrice = basicPrice;
	}

	public Double getNetValue() {
		return netValue;
	}

	public void setNetValue(Double netValue) {
		this.netValue = netValue;
	}

	public Double getCgstValue() {
		return cgstValue;
	}

	public void setCgstValue(Double cgstValue) {
		this.cgstValue = cgstValue;
	}

	public Double getIgstValue() {
		return igstValue;
	}

	public void setIgstValue(Double igstValue) {
		this.igstValue = igstValue;
	}

	public Double getSgstValue() {
		return sgstValue;
	}

	public void setSgstValue(Double sgstValue) {
		this.sgstValue = sgstValue;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public String geteWayBillNo() {
		return eWayBillNo;
	}

	public void seteWayBillNo(String eWayBillNo) {
		this.eWayBillNo = eWayBillNo;
	}

	public String geteWayBillDate() {
		return eWayBillDate;
	}

	public void seteWayBillDate(String eWayBillDate) {
		this.eWayBillDate = eWayBillDate;
	}

	public String getShipmentNumber() {
		return shipmentNumber;
	}

	public void setShipmentNumber(String shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}

	public String getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}

	public String getDoNumber() {
		return doNumber;
	}

	public void setDoNumber(String doNumber) {
		this.doNumber = doNumber;
	}

	// For Sales Invoice Pdf.

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

	public String getGstRate1() {
		return gstRate1;
	}

	public void setGstRate1(String gstRate1) {
		this.gstRate1 = gstRate1;
	}

	public String getTaxableAmt1() {
		return taxableAmt1;
	}

	public void setTaxableAmt1(String taxableAmt1) {
		this.taxableAmt1 = taxableAmt1;
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

	public String getTotal1() {
		return total1;
	}

	public void setTotal1(String total1) {
		this.total1 = total1;
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

	public String getGrandTotalTaxAmount1() {
		return grandTotalTaxAmount1;
	}

	public void setGrandTotalTaxAmount1(String grandTotalTaxAmount1) {
		this.grandTotalTaxAmount1 = grandTotalTaxAmount1;
	}

	public String getTaxAmountGst1() {
		return taxAmountGst1;
	}

	public void setTaxAmountGst1(String taxAmountGst1) {
		this.taxAmountGst1 = taxAmountGst1;
	}

	public String getAdjustment1() {
		return adjustment1;
	}

	public void setAdjustment1(String adjustment1) {
		this.adjustment1 = adjustment1;
	}

	public String getGrandTotal1() {
		return grandTotal1;
	}

	public void setGrandTotal1(String grandTotal1) {
		this.grandTotal1 = grandTotal1;
	}

	public String getTaxInvoiceOF() {
		return taxInvoiceOF;
	}

	public void setTaxInvoiceOF(String taxInvoiceOF) {
		this.taxInvoiceOF = taxInvoiceOF;
	}

	public String getIrnNumber() {
		return irnNumber;
	}

	public void setIrnNumber(String irnNumber) {
		this.irnNumber = irnNumber;
	}

	public String getTcsValue() {
		return tcsValue;
	}

	public void setTcsValue(String tcsValue) {
		this.tcsValue = tcsValue;
	}

	public String getTcsType() {
		return tcsType;
	}

	public void setTcsType(String tcsType) {
		this.tcsType = tcsType;
	}

	public Double getTcsAmount() {
		return tcsAmount;
	}

	public void setTcsAmount(Double tcsAmount) {
		this.tcsAmount = tcsAmount;
	}

	public String getTdsValue() {
		return tdsValue;
	}

	public void setTdsValue(String tdsValue) {
		this.tdsValue = tdsValue;
	}

	public String getTdsType() {
		return tdsType;
	}

	public void setTdsType(String tdsType) {
		this.tdsType = tdsType;
	}

	public String getTdsAmount() {
		return tdsAmount;
	}

	public void setTdsAmount(String tdsAmount) {
		this.tdsAmount = tdsAmount;
	}

	public String getChallanIds() {
		return challanIds;
	}

	public void setChallanIds(String challanIds) {
		this.challanIds = challanIds;
	}

	public String getDateofSupply() {
		return dateofSupply;
	}

	public void setDateofSupply(String dateofSupply) {
		this.dateofSupply = dateofSupply;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getOtherreference() {
		return otherreference;
	}

	public void setOtherreference(String otherreference) {
		this.otherreference = otherreference;
	}

	public String getSaleDeliverysales() {
		return saleDeliverysales;
	}

	public void setSaleDeliverysales(String saleDeliverysales) {
		this.saleDeliverysales = saleDeliverysales;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSaleDeliverysales1() {
		return saleDeliverysales1;
	}

	public void setSaleDeliverysales1(String saleDeliverysales1) {
		this.saleDeliverysales1 = saleDeliverysales1;
	}

	public String getCustId1() {
		return custId1;
	}

	public void setCustId1(String custId1) {
		this.custId1 = custId1;
	}

	public String getCustName1() {
		return custName1;
	}

	public void setCustName1(String custName1) {
		this.custName1 = custName1;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getSalesRId() {
		return salesRId;
	}

	public void setSalesRId(String salesRId) {
		this.salesRId = salesRId;
	}

	public String getExpectedShipmentDate1() {
		return expectedShipmentDate1;
	}

	public void setExpectedShipmentDate1(String expectedShipmentDate1) {
		this.expectedShipmentDate1 = expectedShipmentDate1;
	}

	public String getDeliveryTerm1() {
		return deliveryTerm1;
	}

	public void setDeliveryTerm1(String deliveryTerm1) {
		this.deliveryTerm1 = deliveryTerm1;
	}

	public String getDeliveryMode1() {
		return deliveryMode1;
	}

	public void setDeliveryMode1(String deliveryMode1) {
		this.deliveryMode1 = deliveryMode1;
	}

	public String getPiRemarks() {
		return piRemarks;
	}

	public void setPiRemarks(String piRemarks) {
		this.piRemarks = piRemarks;
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
