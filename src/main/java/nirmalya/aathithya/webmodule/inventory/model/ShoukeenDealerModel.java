package nirmalya.aathithya.webmodule.inventory.model;

import java.io.IOException;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ShoukeenDealerModel {

	private String dealerId;
	private String dealerName;
	private String dealerMobile;
	private String dealerEmail;
	private String dealerPassword;
	private String legalName;

	private String storeName;
	private String productCategory;
	private String country;
	private String states;
	private String city;
	private String address1;
	private String address2;
	private String pinCode;

	private String isTaxable;
	private String taxStateGst;
	private String provisionalGSTINNo;
	private String panNumber;
	private String buyProductFrom;
	private String annualTurnOver;
	private String annualSell;
	private String sellOnOtherWebsiteYesOrNo;

	private String otherWebsiteName;
	private String productCategoryWishToSell;
	private String description;
	private String isAgreeWithTermCondition;
	private String imageName;

	private String createdDate;
	private String createdTime;
	private String updatedDate;
	private String createdBy;
	private String action;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerMobile() {
		return dealerMobile;
	}

	public void setDealerMobile(String dealerMobile) {
		this.dealerMobile = dealerMobile;
	}

	public String getDealerEmail() {
		return dealerEmail;
	}

	public void setDealerEmail(String dealerEmail) {
		this.dealerEmail = dealerEmail;
	}

	public String getDealerPassword() {
		return dealerPassword;
	}

	public void setDealerPassword(String dealerPassword) {
		this.dealerPassword = dealerPassword;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getIsTaxable() {
		return isTaxable;
	}

	public void setIsTaxable(String isTaxable) {
		this.isTaxable = isTaxable;
	}

	public String getTaxStateGst() {
		return taxStateGst;
	}

	public void setTaxStateGst(String taxStateGst) {
		this.taxStateGst = taxStateGst;
	}

	public String getProvisionalGSTINNo() {
		return provisionalGSTINNo;
	}

	public void setProvisionalGSTINNo(String provisionalGSTINNo) {
		this.provisionalGSTINNo = provisionalGSTINNo;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getBuyProductFrom() {
		return buyProductFrom;
	}

	public void setBuyProductFrom(String buyProductFrom) {
		this.buyProductFrom = buyProductFrom;
	}

	public String getAnnualTurnOver() {
		return annualTurnOver;
	}

	public void setAnnualTurnOver(String annualTurnOver) {
		this.annualTurnOver = annualTurnOver;
	}

	public String getAnnualSell() {
		return annualSell;
	}

	public void setAnnualSell(String annualSell) {
		this.annualSell = annualSell;
	}

	public String getSellOnOtherWebsiteYesOrNo() {
		return sellOnOtherWebsiteYesOrNo;
	}

	public void setSellOnOtherWebsiteYesOrNo(String sellOnOtherWebsiteYesOrNo) {
		this.sellOnOtherWebsiteYesOrNo = sellOnOtherWebsiteYesOrNo;
	}

	public String getOtherWebsiteName() {
		return otherWebsiteName;
	}

	public void setOtherWebsiteName(String otherWebsiteName) {
		this.otherWebsiteName = otherWebsiteName;
	}

	public String getProductCategoryWishToSell() {
		return productCategoryWishToSell;
	}

	public void setProductCategoryWishToSell(String productCategoryWishToSell) {
		this.productCategoryWishToSell = productCategoryWishToSell;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsAgreeWithTermCondition() {
		return isAgreeWithTermCondition;
	}

	public void setIsAgreeWithTermCondition(String isAgreeWithTermCondition) {
		this.isAgreeWithTermCondition = isAgreeWithTermCondition;
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
