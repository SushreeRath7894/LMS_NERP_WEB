package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountPurchaseProductWebModel {
	private Integer slNo;
	private String purchaseId;
	private String productId;
	private String productName;
	private String qunatity;
	private String unit;
	private String unitPrice;
	private String discount;
	private String amount;
	private String gstRate;
	private String cgst;
	private String sgst;
	private String igst;
	private String taxAbleAmount;
	private String hsnCode;
	private String itemWeightKg;
	private String itemWeightCase;
	private String ledgerId;
	private String ledgerName;
	private String invItemId;
	private String voucherId;

	public AccountPurchaseProductWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
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

	public String getQunatity() {
		return qunatity;
	}

	public void setQunatity(String qunatity) {
		this.qunatity = qunatity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getGstRate() {
		return gstRate;
	}

	public void setGstRate(String gstRate) {
		this.gstRate = gstRate;
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

	public String getIgst() {
		return igst;
	}

	public void setIgst(String igst) {
		this.igst = igst;
	}

	public String getTaxAbleAmount() {
		return taxAbleAmount;
	}

	public void setTaxAbleAmount(String taxAbleAmount) {
		this.taxAbleAmount = taxAbleAmount;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public Integer getSlNo() {
		return slNo;
	}

	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}

	public String getItemWeightKg() {
		return itemWeightKg;
	}

	public void setItemWeightKg(String itemWeightKg) {
		this.itemWeightKg = itemWeightKg;
	}

	public String getItemWeightCase() {
		return itemWeightCase;
	}

	public void setItemWeightCase(String itemWeightCase) {
		this.itemWeightCase = itemWeightCase;
	}

	public String getLedgerId() {
		return ledgerId;
	}

	public void setLedgerId(String ledgerId) {
		this.ledgerId = ledgerId;
	}

	public String getLedgerName() {
		return ledgerName;
	}

	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}

	public String getInvItemId() {
		return invItemId;
	}

	public void setInvItemId(String invItemId) {
		this.invItemId = invItemId;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
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
