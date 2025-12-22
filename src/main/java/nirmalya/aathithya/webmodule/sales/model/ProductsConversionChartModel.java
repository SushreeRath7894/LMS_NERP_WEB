package nirmalya.aathithya.webmodule.sales.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductsConversionChartModel {
	private String productId;
	private String productHsn;
	private String productSku;
	private String productName;
	private String productBrand;
	private String productCategory;
	private String orgName;
	private String orgDiv;
	private String createdBy;
	private String packSize;
	private String pouchBag;
	private String costPerMT;
	private String costPerKG;
	private String costPerBag;
	private String status;
	private String description;
	private String perEaToGm;
	private String perBagToGm;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductHsn() {
		return productHsn;
	}
	public void setProductHsn(String productHsn) {
		this.productHsn = productHsn;
	}
	public String getProductSku() {
		return productSku;
	}
	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getPackSize() {
		return packSize;
	}
	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}
	public String getPouchBag() {
		return pouchBag;
	}
	public void setPouchBag(String pouchBag) {
		this.pouchBag = pouchBag;
	}
	public String getCostPerMT() {
		return costPerMT;
	}
	public void setCostPerMT(String costPerMT) {
		this.costPerMT = costPerMT;
	}
	public String getCostPerKG() {
		return costPerKG;
	}
	public void setCostPerKG(String costPerKG) {
		this.costPerKG = costPerKG;
	}
	public String getCostPerBag() {
		return costPerBag;
	}
	public void setCostPerBag(String costPerBag) {
		this.costPerBag = costPerBag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPerEaToGm() {
		return perEaToGm;
	}
	public void setPerEaToGm(String perEaToGm) {
		this.perEaToGm = perEaToGm;
	}
	public String getPerBagToGm() {
		return perBagToGm;
	}
	public void setPerBagToGm(String perBagToGm) {
		this.perBagToGm = perBagToGm;
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
