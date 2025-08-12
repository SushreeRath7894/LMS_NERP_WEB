package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemShoukeenModel {

	private Integer slNo;
	private String sku;
	private String quantity;
	private String unitPrice;
	private String discount;
	private String gstRate;
	private String lineTotal;
	private Boolean taxType;
	private String storeId;
	private Double subTotal;

	private Double qIGST;
	private Double qCGST;
	private Double qSGST;
	private Double grandTotal;
	private String salesOrder;

	private Double itemIgst;
	private Double itemCgst;
	private Double itemSgst;

	private String categoryId;
	private String categoryName;
	private String productId;
	private String productName;
	private String productDimension;
	private String itemUnitPrice;

	private String paymentId;
	private String custId;
	private String orderedId;

	private String totalQnty;
	private String dealerId;
	private Integer replaceBtnStatus;

	private String amountWithoutGst;
	private String distPercent;
	private String distAmount;
	private String distributorDiscAmnt;

	private String dealerCode;
	private String toggleRegularCustom;

	private String replacedStatus;
	
	private String hsnCode;
	private String extraDiscount;

	public ItemShoukeenModel() {
		super();
	}

	public ItemShoukeenModel(Object categoryId, Object categoryName, Object quantity, Object itemUnitPrice,
			Object discount, Object gstRate, Object lineTotal, Object productDimension, Object productId,
			Object extraDiscount) {
		super();

		this.categoryId = (String) categoryId;
		this.categoryName = (String) categoryName;
		this.quantity = (String) quantity;
		this.itemUnitPrice = (String) itemUnitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.productDimension = (String) productDimension;
		this.productId = (String) productId;
		this.extraDiscount = (String) extraDiscount;

	}

	public ItemShoukeenModel(Object categoryId, Object categoryName, Object quantity, Object itemUnitPrice,
			Object discount, Object gstRate, Object lineTotal, Object productDimension, Object dealerCode) {
		super();

		this.categoryId = (String) categoryId;
		this.categoryName = (String) categoryName;
		this.quantity = (String) quantity;
		this.itemUnitPrice = (String) itemUnitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.productDimension = (String) productDimension;
		this.dealerCode = (String) dealerCode;

	}
	
	//dealer invoice with toggle regular or custom
	public ItemShoukeenModel(Object categoryId, Object categoryName, Object quantity, Object itemUnitPrice,
			Object discount, Object gstRate, Object lineTotal, Object productDimension, Object dealerCode,
			Object distAmount, Object toggleRegularCustom, Object qIGST) {
		super();

		this.categoryId = (String) categoryId;
		this.categoryName = (String) categoryName;
		this.quantity = (String) quantity;
		this.itemUnitPrice = (String) itemUnitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.productDimension = (String) productDimension;
		this.dealerCode = (String) dealerCode;
		this.toggleRegularCustom = (String) toggleRegularCustom;
	}

	public ItemShoukeenModel(Object categoryId, Object categoryName, Object quantity, Object itemUnitPrice,
			Object discount, Object gstRate, Object lineTotal, Object productDimension, Object productId,
			Object productName, Object itemCgst, Object itemSgst, Object replaceBtnStatus) {
		super();

		this.categoryId = (String) categoryId;
		this.categoryName = (String) categoryName;
		this.quantity = (String) quantity;
		this.itemUnitPrice = (String) itemUnitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.productDimension = (String) productDimension;
		this.productId = (String) productId;
		this.productName = (String) productName;
		this.itemCgst = (Double) itemCgst;
		this.itemSgst = (Double) itemSgst;
		this.replaceBtnStatus = (Integer) replaceBtnStatus;

	}

	// new

	public ItemShoukeenModel(Object categoryId, Object categoryName, Object quantity, Object itemUnitPrice,
			Object discount, Object gstRate, Object lineTotal, Object productDimension, Object productId,
			Object productName, Object itemCgst, Object itemSgst, Object replaceBtnStatus, Object dealerCode,
			Object distAmount) {
		super();

		this.categoryId = (String) categoryId;
		this.categoryName = (String) categoryName;
		this.quantity = (String) quantity;
		this.itemUnitPrice = (String) itemUnitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.productDimension = (String) productDimension;
		this.productId = (String) productId;
		this.productName = (String) productName;
		this.itemCgst = (Double) itemCgst;
		this.itemSgst = (Double) itemSgst;
		this.replaceBtnStatus = (Integer) replaceBtnStatus;
		this.dealerCode = (String) dealerCode;

	}
	
	//modified item model for dealer includes toggle regular or custom
	public ItemShoukeenModel(Object categoryId, Object categoryName, Object quantity, Object itemUnitPrice,
			Object discount, Object gstRate, Object lineTotal, Object productDimension, Object productId,
			Object productName, Object itemCgst, Object itemSgst, Object replaceBtnStatus, Object dealerCode,
		     Object extraDiscount,Object toggleRegularCustom,Object qIGST) {
		super();

		this.categoryId = (String) categoryId;
		this.categoryName = (String) categoryName;
		this.quantity = (String) quantity;
		this.itemUnitPrice = (String) itemUnitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.productDimension = (String) productDimension;
		this.productId = (String) productId;
		this.productName = (String) productName;
		this.itemCgst = (Double) itemCgst;
		this.itemSgst = (Double) itemSgst;
		this.replaceBtnStatus = (Integer) replaceBtnStatus;
		this.dealerCode = (String) dealerCode;
		this.extraDiscount = (String) extraDiscount;
		this.toggleRegularCustom = (String) toggleRegularCustom;
		this.qIGST = (Double) qIGST;

	}

	public ItemShoukeenModel(Object categoryId, Object categoryName, Object quantity, Object itemUnitPrice,
			Object discount, Object gstRate, Object lineTotal, Object productDimension, Object itemCgst,
			Object itemSgst, Object invoiceId) {
		super();

		this.categoryId = (String) categoryId;
		this.categoryName = (String) categoryName;
		this.quantity = (String) quantity;
		this.itemUnitPrice = (String) itemUnitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.productDimension = (String) productDimension;
		this.itemCgst = (Double) itemCgst;
		this.itemSgst = (Double) itemSgst;

	}

	///

	public ItemShoukeenModel(Object categoryId, Object categoryName, Object quantity, Object itemUnitPrice,
			Object discount, Object gstRate, Object lineTotal, Object productDimension, Object itemCgst,
			Object itemSgst, Object amountWithoutGst, Object distPercent, Object distAmount, Object distributorDiscAmnt,
			Object dealerCode, Object replaceBtnStatus) {
		super();

		this.categoryId = (String) categoryId;
		this.categoryName = (String) categoryName;
		this.quantity = (String) quantity;
		this.itemUnitPrice = (String) itemUnitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.productDimension = (String) productDimension;
		this.itemCgst = (Double) itemCgst;
		this.itemSgst = (Double) itemSgst;

		this.amountWithoutGst = (String) amountWithoutGst;
		this.distPercent = (String) distPercent;
		this.distAmount = (String) distAmount;
		this.distributorDiscAmnt = (String) distributorDiscAmnt;
		this.dealerCode = (String) dealerCode;
		this.replaceBtnStatus = (Integer) replaceBtnStatus;
	}
	
	//modified item model for distributor includes toggle regular or custom
	public ItemShoukeenModel(Object categoryId, Object categoryName, Object quantity, Object itemUnitPrice,
			Object discount, Object gstRate, Object lineTotal, Object productDimension, Object itemCgst,
			Object itemSgst, Object amountWithoutGst, Object distPercent, Object distAmount, Object distributorDiscAmnt,
			Object dealerCode, Object replaceBtnStatus, Object toggleRegularCustom, Object qIGST) {
		super();

		this.categoryId = (String) categoryId;
		this.categoryName = (String) categoryName;
		this.quantity = (String) quantity;
		this.itemUnitPrice = (String) itemUnitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.productDimension = (String) productDimension;
		this.itemCgst = (Double) itemCgst;
		this.itemSgst = (Double) itemSgst;

		this.amountWithoutGst = (String) amountWithoutGst;
		this.distPercent = (String) distPercent;
		this.distAmount = (String) distAmount;
		this.distributorDiscAmnt = (String) distributorDiscAmnt;
		this.dealerCode = (String) dealerCode;
		this.replaceBtnStatus = (Integer) replaceBtnStatus;
		this.toggleRegularCustom = (String) toggleRegularCustom;
	}

	public ItemShoukeenModel(Object categoryId, Object categoryName, Object quantity, Object itemUnitPrice,
			Object discount, Object gstRate, Object lineTotal, Object productDimension, Object dealerCode,
			Object amountWithoutGst, Object distPercent, Object distAmount, Object distributorDiscAmnt,
			Object productId) {
		super();

		this.categoryId = (String) categoryId;
		this.categoryName = (String) categoryName;
		this.quantity = (String) quantity;
		this.itemUnitPrice = (String) itemUnitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.productDimension = (String) productDimension;
		this.dealerCode = (String) dealerCode;

		this.amountWithoutGst = (String) amountWithoutGst;
		this.distPercent = (String) distPercent;
		this.distAmount = (String) distAmount;
		this.distributorDiscAmnt = (String) distributorDiscAmnt;

	}
	
	public ItemShoukeenModel(Object categoryId, Object categoryName, Object quantity, Object itemUnitPrice,
			Object discount, Object gstRate, Object lineTotal, Object productDimension, Object dealerCode,
			Object amountWithoutGst, Object distPercent, Object distAmount, Object distributorDiscAmnt,
			Object productId, Object toggleRegularCustom, Object qIGST, Object storeId, Object replaceBtnStatus, Object replacedStatus) {
		super();

		this.categoryId = (String) categoryId;
		this.categoryName = (String) categoryName;
		this.quantity = (String) quantity;
		this.itemUnitPrice = (String) itemUnitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.productDimension = (String) productDimension;
		this.dealerCode = (String) dealerCode;

		this.amountWithoutGst = (String) amountWithoutGst;
		this.distPercent = (String) distPercent;
		this.distAmount = (String) distAmount;
		this.distributorDiscAmnt = (String) distributorDiscAmnt;
		this.toggleRegularCustom = (String) toggleRegularCustom;
	}
	
	public ItemShoukeenModel(Object productId, Object productName,Object quantity, Object unitPrice, Object discount, Object gstRate, Object lineTotal,
			Double qCGST, Double qSGST,Object hsnCode) {
		super();
		this.productId = (String) productId;
		this.productName = (String) productName;
		this.quantity = (String) quantity;
		this.unitPrice = (String) unitPrice;
		this.discount = (String) discount;
		this.gstRate = (String) gstRate;
		this.lineTotal = (String) lineTotal;
		this.qCGST = qCGST;
		this.qSGST = qSGST;
		this.hsnCode = (String) hsnCode;
	}

	public Integer isReplaceBtnStatus() {
		return replaceBtnStatus;
	}

	public void setReplaceBtnStatus(Integer replaceBtnStatus) {
		this.replaceBtnStatus = replaceBtnStatus;
	}

	public String getDistributorDiscAmnt() {
		return distributorDiscAmnt;
	}

	public void setDistributorDiscAmnt(String distributorDiscAmnt) {
		this.distributorDiscAmnt = distributorDiscAmnt;
	}

	public String getAmountWithoutGst() {
		return amountWithoutGst;
	}

	public void setAmountWithoutGst(String amountWithoutGst) {
		this.amountWithoutGst = amountWithoutGst;
	}

	public String getDistPercent() {
		return distPercent;
	}

	public void setDistPercent(String distPercent) {
		this.distPercent = distPercent;
	}

	public String getDistAmount() {
		return distAmount;
	}

	public void setDistAmount(String distAmount) {
		this.distAmount = distAmount;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOrderedId() {
		return orderedId;
	}

	public void setOrderedId(String orderedId) {
		this.orderedId = orderedId;
	}

	public String getTotalQnty() {
		return totalQnty;
	}

	public void setTotalQnty(String totalQnty) {
		this.totalQnty = totalQnty;
	}

	public String getItemUnitPrice() {
		return itemUnitPrice;
	}

	public void setItemUnitPrice(String itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public String getProductDimension() {
		return productDimension;
	}

	public void setProductDimension(String productDimension) {
		this.productDimension = productDimension;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getSlNo() {
		return slNo;
	}

	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getGstRate() {
		return gstRate;
	}

	public void setGstRate(String gstRate) {
		this.gstRate = gstRate;
	}

	public String getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(String lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Boolean getTaxType() {
		return taxType;
	}

	public void setTaxType(Boolean taxType) {
		this.taxType = taxType;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
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

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getReplacedStatus() {
		return replacedStatus;
	}

	public void setReplacedStatus(String replacedStatus) {
		this.replacedStatus = replacedStatus;
	}

	public String getToggleRegularCustom() {
		return toggleRegularCustom;
	}

	public void setToggleRegularCustom(String toggleRegularCustom) {
		this.toggleRegularCustom = toggleRegularCustom;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getExtraDiscount() {
		return extraDiscount;
	}

	public void setExtraDiscount(String extraDiscount) {
		this.extraDiscount = extraDiscount;
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

