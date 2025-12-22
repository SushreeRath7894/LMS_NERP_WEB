package nirmalya.aathithya.webmodule.sales.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SalesPackagesModel {
	private String salePackageId;
    private String custId;
	private String custName;
	private String salesOrder;
	private String poId;
	private String salesOrderId;
	private String quotationId;
	private String saleInvoiceId;
	
	private String qutActive;
	private String qutCreatedBy;
	private String qutUpdatedOn;
	private String packageSlip;
	private String packagingDate;
	private String carrier;
	private String tracking;
	private String status;
	private String shipmentDate;
	private Double ordered;
	private String packed;
	private Double quantityOfPack;
	private String internalNotes;
	
	private String itemId;
	private String itemName;
	private String itemDesc;
	private String hsnCode;
	private Double quantity;
	private String unit;
	private String unitName;
	private String sku;
	private String skuName;
	private String returnQuantity;
	private String orgName;
	private String orgDiv;


	 private String packItemName;
	 private String packTotalQut;
	 private String packUnit;
	 private String packQut;
	 private String packingQut;
	 private String packType;
	 private String packName;
	 private String packDate;
	 private String packDesc;
	 private String packedQut;
     private String pendingQut;
     private  String salesShipmentId ;
     private  String shippingHiddenId ;
	 
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
 	private Double itemIgst;
 	private Double itemCgst;
 	
 	private Double itemSgst;
 	private Double adjustment;
 	 private String tcsId;
 	
 	private Double tcsAmount;
 	private String tcs;
	private Integer slNo;
	 private String dchallan;
	 private String dchallanStatus;
	 private Double taxableAmt;
	 private Double freigtTaxRate;
	 private Double noOfItem;
	public SalesPackagesModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSalePackageId() {
		return salePackageId;
	}
	public void setSalePackageId(String salePackageId) {
		this.salePackageId = salePackageId;
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
	public String getSaleInvoiceId() {
		return saleInvoiceId;
	}
	public void setSaleInvoiceId(String saleInvoiceId) {
		this.saleInvoiceId = saleInvoiceId;
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
	public String getPackageSlip() {
		return packageSlip;
	}
	public void setPackageSlip(String packageSlip) {
		this.packageSlip = packageSlip;
	}
	public String getPackagingDate() {
		return packagingDate;
	}
	public void setPackagingDate(String packagingDate) {
		this.packagingDate = packagingDate;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShipmentDate() {
		return shipmentDate;
	}
	public void setShipmentDate(String shipmentDate) {
		this.shipmentDate = shipmentDate;
	}
	public Double getOrdered() {
		return ordered;
	}
	public void setOrdered(Double ordered) {
		this.ordered = ordered;
	}
	public String getPacked() {
		return packed;
	}
	public void setPacked(String packed) {
		this.packed = packed;
	}
	public Double getQuantityOfPack() {
		return quantityOfPack;
	}
	public void setQuantityOfPack(Double quantityOfPack) {
		this.quantityOfPack = quantityOfPack;
	}
	public String getInternalNotes() {
		return internalNotes;
	}
	public void setInternalNotes(String internalNotes) {
		this.internalNotes = internalNotes;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
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
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getReturnQuantity() {
		return returnQuantity;
	}
	public void setReturnQuantity(String returnQuantity) {
		this.returnQuantity = returnQuantity;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgDiv() {
		return orgDiv;
	}
	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}
	public String getPackItemName() {
		return packItemName;
	}
	public void setPackItemName(String packItemName) {
		this.packItemName = packItemName;
	}
	public String getPackTotalQut() {
		return packTotalQut;
	}
	public void setPackTotalQut(String packTotalQut) {
		this.packTotalQut = packTotalQut;
	}
	public String getPackUnit() {
		return packUnit;
	}
	public void setPackUnit(String packUnit) {
		this.packUnit = packUnit;
	}
	public String getPackQut() {
		return packQut;
	}
	public void setPackQut(String packQut) {
		this.packQut = packQut;
	}
	public String getPackingQut() {
		return packingQut;
	}
	public void setPackingQut(String packingQut) {
		this.packingQut = packingQut;
	}
	public String getPackType() {
		return packType;
	}
	public void setPackType(String packType) {
		this.packType = packType;
	}
	public String getPackDate() {
		return packDate;
	}
	public void setPackDate(String packDate) {
		this.packDate = packDate;
	}
	public String getPackDesc() {
		return packDesc;
	}
	public void setPackDesc(String packDesc) {
		this.packDesc = packDesc;
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
	public String getSalesShipmentId() {
		return salesShipmentId;
	}
	public void setSalesShipmentId(String salesShipmentId) {
		this.salesShipmentId = salesShipmentId;
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
	public Integer getSlNo() {
		return slNo;
	}
	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}
	public String getDchallan() {
		return dchallan;
	}
	public void setDchallan(String dchallan) {
		this.dchallan = dchallan;
	}
	public String getDchallanStatus() {
		return dchallanStatus;
	}
	public void setDchallanStatus(String dchallanStatus) {
		this.dchallanStatus = dchallanStatus;
	}
	
	public Double getTaxableAmt() {
		return taxableAmt;
	}
	public void setTaxableAmt(Double taxableAmt) {
		this.taxableAmt = taxableAmt;
	}
	
	
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	
	
	
	public Double getFreigtTaxRate() {
		return freigtTaxRate;
	}
	public void setFreigtTaxRate(Double freigtTaxRate) {
		this.freigtTaxRate = freigtTaxRate;
	}
	
	
	public Double getNoOfItem() {
		return noOfItem;
	}
	public void setNoOfItem(Double noOfItem) {
		this.noOfItem = noOfItem;
	}
	public String getPoId() {
		return poId;
	}
	public void setPoId(String poId) {
		this.poId = poId;
	}
	
	
	public String getShippingHiddenId() {
		return shippingHiddenId;
	}
	public void setShippingHiddenId(String shippingHiddenId) {
		this.shippingHiddenId = shippingHiddenId;
	}
	
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
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
