package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import nirmalya.aathithya.webmodule.account.controller.WebPurchaseItemModel;

public class AccountCreditNoteWebModel {
	private String creditNoteId;
	private String partyLedgerId;
	private String partyLedger;
	private String salesLedgerId;
	private String salesLedger;
	private String prtyLedgerCurBal;
	private String salesLedgerCurBal;
	private String creditNoteDate;
	private String orderNumber;
	private String qSGST;
	private String qCGST;
	private String qIGST;
	private String grandTotal;
	private String subTotal;
	private String totalItem;
	private String createdBy;
	private String createdOn;
	private String costCenter;
	private String description;
	private String currentBalance;
	private String organization;
	private String orgDivision;
	List<ItemShoukeenModel> itemattribute;
	List<WebPurchaseItemModel> debitItemAttribute;
	private String categoryId;
	private String categoryName;
	private String price;
	private Boolean taxType;
	private String amount;
	
	public AccountCreditNoteWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCreditNoteId() {
		return creditNoteId;
	}
	public void setCreditNoteId(String creditNoteId) {
		this.creditNoteId = creditNoteId;
	}
	public String getPartyLedgerId() {
		return partyLedgerId;
	}
	public void setPartyLedgerId(String partyLedgerId) {
		this.partyLedgerId = partyLedgerId;
	}
	public String getPartyLedger() {
		return partyLedger;
	}
	public void setPartyLedger(String partyLedger) {
		this.partyLedger = partyLedger;
	}
	public String getSalesLedgerId() {
		return salesLedgerId;
	}
	public void setSalesLedgerId(String salesLedgerId) {
		this.salesLedgerId = salesLedgerId;
	}
	public String getSalesLedger() {
		return salesLedger;
	}
	public void setSalesLedger(String salesLedger) {
		this.salesLedger = salesLedger;
	}
	public String getPrtyLedgerCurBal() {
		return prtyLedgerCurBal;
	}
	public void setPrtyLedgerCurBal(String prtyLedgerCurBal) {
		this.prtyLedgerCurBal = prtyLedgerCurBal;
	}
	public String getSalesLedgerCurBal() {
		return salesLedgerCurBal;
	}
	public void setSalesLedgerCurBal(String salesLedgerCurBal) {
		this.salesLedgerCurBal = salesLedgerCurBal;
	}
	public String getCreditNoteDate() {
		return creditNoteDate;
	}
	public void setCreditNoteDate(String creditNoteDate) {
		this.creditNoteDate = creditNoteDate;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getqSGST() {
		return qSGST;
	}
	public void setqSGST(String qSGST) {
		this.qSGST = qSGST;
	}
	public String getqCGST() {
		return qCGST;
	}
	public void setqCGST(String qCGST) {
		this.qCGST = qCGST;
	}
	public String getqIGST() {
		return qIGST;
	}
	public void setqIGST(String qIGST) {
		this.qIGST = qIGST;
	}
	public String getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public String getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(String totalItem) {
		this.totalItem = totalItem;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public List<ItemShoukeenModel> getItemattribute() {
		return itemattribute;
	}
	public void setItemattribute(List<ItemShoukeenModel> itemattribute) {
		this.itemattribute = itemattribute;
	}
	
	public List<WebPurchaseItemModel> getDebitItemAttribute() {
		return debitItemAttribute;
	}
	public void setDebitItemAttribute(List<WebPurchaseItemModel> debitItemAttribute) {
		this.debitItemAttribute = debitItemAttribute;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Boolean getTaxType() {
		return taxType;
	}
	public void setTaxType(Boolean taxType) {
		this.taxType = taxType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
