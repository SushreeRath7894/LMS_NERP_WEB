package nirmalya.aathithya.webmodule.sales.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

public class SalesOrderNewModel {
	private String quotationId;
	private String poId;
	private String qutName;
	private String custId;
	private String custName;
	private String custGSTNo;
	private String qutValidDate;
	private String qutDescription;
	private String qutActive;
	private String qutCreatedBy;
	private String qutUpdatedOn;
	private String itemId;
	private Integer slNo;
	private String itemName;
	private String hsnCode;
	private Double quantity;
	private String unit;
	private String unitName;
	private Double taxableAmt;
	private Double unitPrice;
	private Double discount;
	private Double gstRate;
	private Double lineTotal;
	private Double subTotal;
	private Double qIGST;
	private Double qCGST;
	private Double qSGST;
	private Double grandTotal;
	private String active;
	private Boolean taxType;
	private String qItmCode;
	private String sku;
	private String salesOrder;
	private String salesOrderId;
	private String storeId;
	private String orderReceiveDate;
	private String orderReceiveTime;
	private String salesActive;
	private Double itemIgst;
	private Double itemCgst;
	private Double itemSgst;
	private String organization;
	private String orgDivision;
	private String expectedShipmentDate;
	private String salesPaymentTerm;
	private String paymentTermId;
	private String deliveryMethodId;
	private String salesPersonId;
	private String salesPerson;
	private String spName;
	private String reference;
	private Double tcsAmount;
	private String tcs;
	private Double adjustment;
	private String terms;
	private String tcsId;
	private String tcsRate;
	private String invoice;
	private String invoiceDate;
	private String invoiceStatus;
	private String orderStatus;
	private String packQut;
	private String packedQut;
	private String pendingQut;
	private String sizeInMM;
	private String thicknessInMM;
	private String itemDesc;
	private String orderType;
	private String itemRemarks;
	private Double noOfItem;
	private String blockeOrder;
	private String approveStatus;
	private String shippingHiddenId;
	private String project;
	private String pan;
	private String gstNo;

	private String poNo;

	private String type;
	private String paymentTerm;
	private String dueDate;
	private String dateOfSupply;
	private String vehicleNo;
	private String transporterId;
	private String transporterName;
	private String lrNumber;
	private String tMode;
	private String salesRId;
	private String saleDeliverysales1;
	private String deliveryMode;
	private String deliveryTerm;
	private String soRemarks;
	private String soRef;
	private String endCustomerName;
	private String version;
	private String ourRefNo;

	List<InventoryVendorDocumentModel> documentList;

	public SalesOrderNewModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}

	public String getQutName() {
		return qutName;
	}

	public void setQutName(String qutName) {
		this.qutName = qutName;
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

	public String getCustGSTNo() {
		return custGSTNo;
	}

	public void setCustGSTNo(String custGSTNo) {
		this.custGSTNo = custGSTNo;
	}

	public String getQutValidDate() {
		return qutValidDate;
	}

	public void setQutValidDate(String qutValidDate) {
		this.qutValidDate = qutValidDate;
	}

	public String getQutDescription() {
		return qutDescription;
	}

	public void setQutDescription(String qutDescription) {
		this.qutDescription = qutDescription;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Boolean getTaxType() {
		return taxType;
	}

	public void setTaxType(Boolean taxType) {
		this.taxType = taxType;
	}

	public String getqItmCode() {
		return qItmCode;
	}

	public void setqItmCode(String qItmCode) {
		this.qItmCode = qItmCode;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getOrderReceiveDate() {
		return orderReceiveDate;
	}

	public void setOrderReceiveDate(String orderReceiveDate) {
		this.orderReceiveDate = orderReceiveDate;
	}

	public String getOrderReceiveTime() {
		return orderReceiveTime;
	}

	public void setOrderReceiveTime(String orderReceiveTime) {
		this.orderReceiveTime = orderReceiveTime;
	}

	public String getSalesActive() {
		return salesActive;
	}

	public void setSalesActive(String salesActive) {
		this.salesActive = salesActive;
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

	public String getExpectedShipmentDate() {
		return expectedShipmentDate;
	}

	public void setExpectedShipmentDate(String expectedShipmentDate) {
		this.expectedShipmentDate = expectedShipmentDate;
	}

	public String getPaymentTermId() {
		return paymentTermId;
	}

	public void setPaymentTermId(String paymentTermId) {
		this.paymentTermId = paymentTermId;
	}

	public String getDeliveryMethodId() {
		return deliveryMethodId;
	}

	public void setDeliveryMethodId(String deliveryMethodId) {
		this.deliveryMethodId = deliveryMethodId;
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

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
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

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getTcsId() {
		return tcsId;
	}

	public void setTcsId(String tcsId) {
		this.tcsId = tcsId;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<InventoryVendorDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<InventoryVendorDocumentModel> documentList) {
		this.documentList = documentList;
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

	public String getTcsRate() {
		return tcsRate;
	}

	public void setTcsRate(String tcsRate) {
		this.tcsRate = tcsRate;
	}

	public String getPackQut() {
		return packQut;
	}

	public void setPackQut(String packQut) {
		this.packQut = packQut;
	}

	public String getPackedQut() {
		return packedQut;
	}

	public void setPackedQut(String packedQut) {
		this.packedQut = packedQut;
	}

	public String getPendingQut() {
		return pendingQut;
	}

	public void setPendingQut(String pendingQut) {
		this.pendingQut = pendingQut;
	}

	public String getSizeInMM() {
		return sizeInMM;
	}

	public void setSizeInMM(String sizeInMM) {
		this.sizeInMM = sizeInMM;
	}

	public String getThicknessInMM() {
		return thicknessInMM;
	}

	public void setThicknessInMM(String thicknessInMM) {
		this.thicknessInMM = thicknessInMM;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public String getItemRemarks() {
		return itemRemarks;
	}

	public void setItemRemarks(String itemRemarks) {
		this.itemRemarks = itemRemarks;
	}

	public Double getNoOfItem() {
		return noOfItem;
	}

	public void setNoOfItem(Double noOfItem) {
		this.noOfItem = noOfItem;
	}

	public String getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getBlockeOrder() {
		return blockeOrder;
	}

	public void setBlockeOrder(String blockeOrder) {
		this.blockeOrder = blockeOrder;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getShippingHiddenId() {
		return shippingHiddenId;
	}

	public void setShippingHiddenId(String shippingHiddenId) {
		this.shippingHiddenId = shippingHiddenId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getDateOfSupply() {
		return dateOfSupply;
	}

	public void setDateOfSupply(String dateOfSupply) {
		this.dateOfSupply = dateOfSupply;
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

	public String gettMode() {
		return tMode;
	}

	public void settMode(String tMode) {
		this.tMode = tMode;
	}

	public String getSalesRId() {
		return salesRId;
	}

	public void setSalesRId(String salesRId) {
		this.salesRId = salesRId;
	}

	public String getSaleDeliverysales1() {
		return saleDeliverysales1;
	}

	public void setSaleDeliverysales1(String saleDeliverysales1) {
		this.saleDeliverysales1 = saleDeliverysales1;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getDeliveryTerm() {
		return deliveryTerm;
	}

	public void setDeliveryTerm(String deliveryTerm) {
		this.deliveryTerm = deliveryTerm;
	}

	public String getSoRemarks() {
		return soRemarks;
	}

	public void setSoRemarks(String soRemarks) {
		this.soRemarks = soRemarks;
	}

	public String getSoRef() {
		return soRef;
	}

	public void setSoRef(String soRef) {
		this.soRef = soRef;
	}

	public String getEndCustomerName() {
		return endCustomerName;
	}

	public void setEndCustomerName(String endCustomerName) {
		this.endCustomerName = endCustomerName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSalesPaymentTerm() {
		return salesPaymentTerm;
	}

	public void setSalesPaymentTerm(String salesPaymentTerm) {
		this.salesPaymentTerm = salesPaymentTerm;
	}

	public String getOurRefNo() {
		return ourRefNo;
	}

	public void setOurRefNo(String ourRefNo) {
		this.ourRefNo = ourRefNo;
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
