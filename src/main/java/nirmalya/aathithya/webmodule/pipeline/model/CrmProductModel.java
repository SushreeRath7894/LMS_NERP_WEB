package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CrmProductModel {
	private String productId;
	private String productOwner;
	private String productName;
	private String productCode;
	private String pageType;
	private String productVendor;
	private String productActive;
	private String productManufacturer;
	private String productCategory;
	private String salesStartDate;
	private String salesEndDate;
	private String supportStartDate;
	private String supportEndDate;
	private String unitPrice;
	private String commissionRate;
	private String tax;
	private String taxable;
	private String usageUnit;
	private String qtyOrdered;
	private String qtyInStock;
	private String reorderLevel;
	private String handler;
	private String qtyInDemand;
	
	private String description;
	private String createdBy;
	private String imageName;
	private String productImageLink;
	private String createdDate;
	private String updatedDate;
	private String action;
	
	private String leadId;
	private String contactId;
	private String accountId;
	private String dealId;
	
	
	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}


	public String getContactId() {
		return contactId;
	}


	public void setContactId(String contactId) {
		this.contactId = contactId;
	}


	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public String getDealId() {
		return dealId;
	}


	public void setDealId(String dealId) {
		this.dealId = dealId;
	}


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getProductOwner() {
		return productOwner;
	}


	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}


	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}





	public String getPageType() {
		return pageType;
	}





	public void setPageType(String pageType) {
		this.pageType = pageType;
	}





	public String getProductVendor() {
		return productVendor;
	}





	public void setProductVendor(String productVendor) {
		this.productVendor = productVendor;
	}





	public String getProductActive() {
		return productActive;
	}





	public void setProductActive(String productActive) {
		this.productActive = productActive;
	}





	public String getProductManufacturer() {
		return productManufacturer;
	}





	public void setProductManufacturer(String productManufacturer) {
		this.productManufacturer = productManufacturer;
	}





	public String getProductCategory() {
		return productCategory;
	}





	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}





	public String getSalesStartDate() {
		return salesStartDate;
	}





	public void setSalesStartDate(String salesStartDate) {
		this.salesStartDate = salesStartDate;
	}





	public String getSalesEndDate() {
		return salesEndDate;
	}





	public void setSalesEndDate(String salesEndDate) {
		this.salesEndDate = salesEndDate;
	}





	public String getSupportStartDate() {
		return supportStartDate;
	}





	public void setSupportStartDate(String supportStartDate) {
		this.supportStartDate = supportStartDate;
	}





	public String getSupportEndDate() {
		return supportEndDate;
	}





	public void setSupportEndDate(String supportEndDate) {
		this.supportEndDate = supportEndDate;
	}





	public String getUnitPrice() {
		return unitPrice;
	}





	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}





	public String getCommissionRate() {
		return commissionRate;
	}





	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}





	public String getTax() {
		return tax;
	}





	public void setTax(String tax) {
		this.tax = tax;
	}





	public String getTaxable() {
		return taxable;
	}





	public void setTaxable(String taxable) {
		this.taxable = taxable;
	}





	public String getUsageUnit() {
		return usageUnit;
	}





	public void setUsageUnit(String usageUnit) {
		this.usageUnit = usageUnit;
	}





	public String getQtyOrdered() {
		return qtyOrdered;
	}





	public void setQtyOrdered(String qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}





	public String getQtyInStock() {
		return qtyInStock;
	}





	public void setQtyInStock(String qtyInStock) {
		this.qtyInStock = qtyInStock;
	}





	public String getReorderLevel() {
		return reorderLevel;
	}





	public void setReorderLevel(String reorderLevel) {
		this.reorderLevel = reorderLevel;
	}





	public String getHandler() {
		return handler;
	}





	public void setHandler(String handler) {
		this.handler = handler;
	}





	public String getQtyInDemand() {
		return qtyInDemand;
	}





	public void setQtyInDemand(String qtyInDemand) {
		this.qtyInDemand = qtyInDemand;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	}





	public String getCreatedBy() {
		return createdBy;
	}





	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}





	public String getImageName() {
		return imageName;
	}





	public void setImageName(String imageName) {
		this.imageName = imageName;
	}





	public String getProductImageLink() {
		return productImageLink;
	}





	public void setProductImageLink(String productImageLink) {
		this.productImageLink = productImageLink;
	}





	public String getCreatedDate() {
		return createdDate;
	}





	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}





	public String getUpdatedDate() {
		return updatedDate;
	}





	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}





	public String getAction() {
		return action;
	}





	public void setAction(String action) {
		this.action = action;
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
