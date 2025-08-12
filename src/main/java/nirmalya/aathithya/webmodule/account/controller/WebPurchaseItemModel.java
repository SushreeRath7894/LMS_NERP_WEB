package nirmalya.aathithya.webmodule.account.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebPurchaseItemModel {
	
	private Integer slNo;
	private String productId;
	private String productName;
	private String quantity;
	private String unitPrice;
	private String discount;
	private String gstRate;
	private String lineTotal;
	private String cgst;
	private String sgst;
	private String hsnCode;
	private String categoryId;
	private String categoryName;
	private String itemIgst;
	private String taxableAmt;
	private String itemCgst;
	private String itemSgst;
	public WebPurchaseItemModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getSlNo() {
		return slNo;
	}
	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
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
	public String getCgst() {
		return cgst;
	}
	public void setCgst(String cgst) {
		this.cgst = cgst;
	}
	public String getSgst() {
		return sgst;
	}
	public void setSgst(String sgst) {
		this.sgst = sgst;
	}
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
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
	
	public String getItemIgst() {
		return itemIgst;
	}
	public void setItemIgst(String itemIgst) {
		this.itemIgst = itemIgst;
	}
	public String getTaxableAmt() {
		return taxableAmt;
	}
	public void setTaxableAmt(String taxableAmt) {
		this.taxableAmt = taxableAmt;
	}
	public String getItemCgst() {
		return itemCgst;
	}
	public void setItemCgst(String itemCgst) {
		this.itemCgst = itemCgst;
	}
	public String getItemSgst() {
		return itemSgst;
	}
	public void setItemSgst(String itemSgst) {
		this.itemSgst = itemSgst;
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
