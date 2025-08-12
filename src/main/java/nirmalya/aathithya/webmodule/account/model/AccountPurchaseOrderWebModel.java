package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountPurchaseOrderWebModel {
	private String purchaseId;
	private String totalAmount;
	private String purchaseDate;
	private String buyerName;
	private String buyerNameId;
	private String sellerName;
	private String sellerNameId;
	private String customerType;

	private String subTotal;
	private String sgst;
	private String cgst;
	private String igst;
	private String grandTotal;
	
	private String voucherId;
	private String voucherDate;
	private String invoiceDate;
	private String sapId;
	
	private String totalQuantUnit;
	private String totalQuantKg;
	private String totalQuantCase;
	private String totalItemCount;
	
	private String vType;
	private String vClass;
	
	private String tdsValue;
	private String netReceivable;
	private String tdsRate;
	private String tcsValue;
	
	private String particularName;
	private String vendorName;
	private String tdsAmount;
	private String paymentStatus;
	private String grnId;
	private String invoiceId;
	private String orgName;
	private String orgDiv;
	
	

	List<AccountPurchaseProductWebModel> productList;

	public AccountPurchaseOrderWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerNameId() {
		return buyerNameId;
	}

	public void setBuyerNameId(String buyerNameId) {
		this.buyerNameId = buyerNameId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerNameId() {
		return sellerNameId;
	}

	public void setSellerNameId(String sellerNameId) {
		this.sellerNameId = sellerNameId;
	}

	public List<AccountPurchaseProductWebModel> getProductList() {
		return productList;
	}

	public void setProductList(List<AccountPurchaseProductWebModel> productList) {
		this.productList = productList;
	}

	public String getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public String getSgst() {
		return sgst;
	}

	public void setSgst(String sgst) {
		this.sgst = sgst;
	}

	public String getCgst() {
		return cgst;
	}

	public void setCgst(String cgst) {
		this.cgst = cgst;
	}

	public String getIgst() {
		return igst;
	}

	public void setIgst(String igst) {
		this.igst = igst;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}

	
	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public String getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getSapId() {
		return sapId;
	}

	public void setSapId(String sapId) {
		this.sapId = sapId;
	}

	public String getTotalQuantUnit() {
		return totalQuantUnit;
	}

	public void setTotalQuantUnit(String totalQuantUnit) {
		this.totalQuantUnit = totalQuantUnit;
	}

	public String getTotalQuantKg() {
		return totalQuantKg;
	}

	public void setTotalQuantKg(String totalQuantKg) {
		this.totalQuantKg = totalQuantKg;
	}

	public String getTotalQuantCase() {
		return totalQuantCase;
	}

	public void setTotalQuantCase(String totalQuantCase) {
		this.totalQuantCase = totalQuantCase;
	}

	public String getTotalItemCount() {
		return totalItemCount;
	}

	public void setTotalItemCount(String totalItemCount) {
		this.totalItemCount = totalItemCount;
	}

	public String getvType() {
		return vType;
	}

	public void setvType(String vType) {
		this.vType = vType;
	}

	public String getvClass() {
		return vClass;
	}

	public void setvClass(String vClass) {
		this.vClass = vClass;
	}

	
	public String getTdsValue() {
		return tdsValue;
	}

	public void setTdsValue(String tdsValue) {
		this.tdsValue = tdsValue;
	}

	public String getNetReceivable() {
		return netReceivable;
	}

	public void setNetReceivable(String netReceivable) {
		this.netReceivable = netReceivable;
	}

	public String getTdsRate() {
		return tdsRate;
	}

	public void setTdsRate(String tdsRate) {
		this.tdsRate = tdsRate;
	}
	
	
	

	public String getTcsValue() {
		return tcsValue;
	}

	public void setTcsValue(String tcsValue) {
		this.tcsValue = tcsValue;
	}

	public String getParticularName() {
		return particularName;
	}

	public void setParticularName(String particularName) {
		this.particularName = particularName;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getTdsAmount() {
		return tdsAmount;
	}

	public void setTdsAmount(String tdsAmount) {
		this.tdsAmount = tdsAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getGrnId() {
		return grnId;
	}

	public void setGrnId(String grnId) {
		this.grnId =grnId;
	}
	
	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
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
