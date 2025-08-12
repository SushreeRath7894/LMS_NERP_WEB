package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReferenceProductModel {

	private String brandId;
	private String brandName;
	private String brandOrder;
	private String brandCode;
	private String brandDesc;
	private String brandStatus;
	private String brandCreatedBy;
	private String brandModifiedBy;
	private String brandCreatedOn;
	private String brandUpdatedOn;

	// product Type

	private String productId;
	private String productOrder;
	private String productName;
	private String productCode;
	private String productDesc;
	private String productStatus;
	private String productCreatedBy;
	private String productModifiedBy;
	private String productCreatedOn;
	private String productUpdatedOn;
	
	// insurance Type

	private String insuranceId;
	private String insuranceName;
	private String insuranceDesc;
	private String insuranceStatus;
	private String insuranceCreatedBy;
	private String insuranceModifiedBy;
	private String insuranceCreatedOn;
	private String insuranceUpdatedOn;

	// insurance Provider Type

	private String insurancePrvdrId;
	private String insurancePrvdrName;
	private String insurancePrName;
	private String insurancePrId;
	// private String insurancePrvdrName;
	private String insuranceAmount;
	private String insurancefrmdt;
	private String insurancetodt;
	private String insurancePrvdrCreatedBy;
	private String insurancePrvdrModifiedBy;
	private String insurancePrvdrCreatedOn;
	private String insurancePrvdrUpdatedOn;

	// transport Type

	private String transportId;
	private String transportName;
	private String transportDesc;
	private String transportCreatedBy;
	private String transportModifiedBy;
	private String transportCreatedOn;
	private String transportUpdatedOn;

	// variation Type

	private String variationId;
	private String variationName;
	private String variationDesc;
	private String variationStatus;
	private String variationCreatedBy;
	private String variationModifiedBy;
	private String variationCreatedOn;
	private String variationUpdatedOn;
	
	private String org;
	private String orgDiv;

	public ReferenceProductModel() {
		super(); 
	}

	public ReferenceProductModel(String brandOrder, String brandCode, String brandName, 
			String brandStatus, String brandCreatedBy) {
		super();
		this.brandName = brandName;
		this.brandOrder = brandOrder;
		this.brandCode = brandCode; 
		this.brandStatus = brandStatus;
		this.brandCreatedBy = brandCreatedBy;
	}

	public ReferenceProductModel(String productName, String productDesc, String productStatus,
			String productCreatedBy) {
		super();
		this.productName = productName;
		this.productDesc = productDesc;
		this.productStatus = productStatus;
		this.productCreatedBy = productCreatedBy;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandOrder() {
		return brandOrder;
	}

	public void setBrandOrder(String brandOrder) {
		this.brandOrder = brandOrder;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandDesc() {
		return brandDesc;
	}

	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}

	public String getBrandStatus() {
		return brandStatus;
	}

	public void setBrandStatus(String brandStatus) {
		this.brandStatus = brandStatus;
	}

	public String getBrandCreatedBy() {
		return brandCreatedBy;
	}

	public void setBrandCreatedBy(String brandCreatedBy) {
		this.brandCreatedBy = brandCreatedBy;
	}

	public String getBrandModifiedBy() {
		return brandModifiedBy;
	}

	public void setBrandModifiedBy(String brandModifiedBy) {
		this.brandModifiedBy = brandModifiedBy;
	}

	public String getBrandCreatedOn() {
		return brandCreatedOn;
	}

	public void setBrandCreatedOn(String brandCreatedOn) {
		this.brandCreatedOn = brandCreatedOn;
	}

	public String getBrandUpdatedOn() {
		return brandUpdatedOn;
	}

	public void setBrandUpdatedOn(String brandUpdatedOn) {
		this.brandUpdatedOn = brandUpdatedOn;
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

	public String getProductOrder() {
		return productOrder;
	}

	public void setProductOrder(String productOrder) {
		this.productOrder = productOrder;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getProductCreatedBy() {
		return productCreatedBy;
	}

	public void setProductCreatedBy(String productCreatedBy) {
		this.productCreatedBy = productCreatedBy;
	}

	public String getProductModifiedBy() {
		return productModifiedBy;
	}

	public void setProductModifiedBy(String productModifiedBy) {
		this.productModifiedBy = productModifiedBy;
	}

	public String getProductCreatedOn() {
		return productCreatedOn;
	}

	public void setProductCreatedOn(String productCreatedOn) {
		this.productCreatedOn = productCreatedOn;
	}

	public String getProductUpdatedOn() {
		return productUpdatedOn;
	}

	public void setProductUpdatedOn(String productUpdatedOn) {
		this.productUpdatedOn = productUpdatedOn;
	}

	public String getVariationId() {
		return variationId;
	}

	public void setVariationId(String variationId) {
		this.variationId = variationId;
	}

	public String getVariationName() {
		return variationName;
	}

	public void setVariationName(String variationName) {
		this.variationName = variationName;
	}

	public String getVariationDesc() {
		return variationDesc;
	}

	public void setVariationDesc(String variationDesc) {
		this.variationDesc = variationDesc;
	}

	public String getVariationStatus() {
		return variationStatus;
	}

	public void setVariationStatus(String variationStatus) {
		this.variationStatus = variationStatus;
	}

	public String getVariationCreatedBy() {
		return variationCreatedBy;
	}

	public void setVariationCreatedBy(String variationCreatedBy) {
		this.variationCreatedBy = variationCreatedBy;
	}

	public String getVariationModifiedBy() {
		return variationModifiedBy;
	}

	public void setVariationModifiedBy(String variationModifiedBy) {
		this.variationModifiedBy = variationModifiedBy;
	}

	public String getVariationCreatedOn() {
		return variationCreatedOn;
	}

	public void setVariationCreatedOn(String variationCreatedOn) {
		this.variationCreatedOn = variationCreatedOn;
	}

	public String getVariationUpdatedOn() {
		return variationUpdatedOn;
	}

	public void setVariationUpdatedOn(String variationUpdatedOn) {
		this.variationUpdatedOn = variationUpdatedOn;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getOrgDiv() {
		return orgDiv;
	}

	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}
	

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public String getInsuranceDesc() {
		return insuranceDesc;
	}

	public void setInsuranceDesc(String insuranceDesc) {
		this.insuranceDesc = insuranceDesc;
	}

	public String getInsuranceStatus() {
		return insuranceStatus;
	}

	public void setInsuranceStatus(String insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}

	public String getInsuranceCreatedBy() {
		return insuranceCreatedBy;
	}

	public void setInsuranceCreatedBy(String insuranceCreatedBy) {
		this.insuranceCreatedBy = insuranceCreatedBy;
	}

	public String getInsuranceModifiedBy() {
		return insuranceModifiedBy;
	}

	public void setInsuranceModifiedBy(String insuranceModifiedBy) {
		this.insuranceModifiedBy = insuranceModifiedBy;
	}

	public String getInsuranceCreatedOn() {
		return insuranceCreatedOn;
	}

	public void setInsuranceCreatedOn(String insuranceCreatedOn) {
		this.insuranceCreatedOn = insuranceCreatedOn;
	}

	public String getInsuranceUpdatedOn() {
		return insuranceUpdatedOn;
	}

	public void setInsuranceUpdatedOn(String insuranceUpdatedOn) {
		this.insuranceUpdatedOn = insuranceUpdatedOn;
	}
	
	public String getInsurancePrvdrId() {
		return insurancePrvdrId;
	}

	public void setInsurancePrvdrId(String insurancePrvdrId) {
		this.insurancePrvdrId = insurancePrvdrId;
	}

	public String getInsurancePrvdrName() {
		return insurancePrvdrName;
	}

	public void setInsurancePrvdrName(String insurancePrvdrName) {
		this.insurancePrvdrName = insurancePrvdrName;
	}

	public String getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getInsurancefrmdt() {
		return insurancefrmdt;
	}

	public void setInsurancefrmdt(String insurancefrmdt) {
		this.insurancefrmdt = insurancefrmdt;
	}

	public String getInsurancetodt() {
		return insurancetodt;
	}

	public void setInsurancetodt(String insurancetodt) {
		this.insurancetodt = insurancetodt;
	}

	public String getInsurancePrvdrCreatedBy() {
		return insurancePrvdrCreatedBy;
	}

	public void setInsurancePrvdrCreatedBy(String insurancePrvdrCreatedBy) {
		this.insurancePrvdrCreatedBy = insurancePrvdrCreatedBy;
	}

	public String getInsurancePrvdrModifiedBy() {
		return insurancePrvdrModifiedBy;
	}

	public void setInsurancePrvdrModifiedBy(String insurancePrvdrModifiedBy) {
		this.insurancePrvdrModifiedBy = insurancePrvdrModifiedBy;
	}

	public String getInsurancePrvdrCreatedOn() {
		return insurancePrvdrCreatedOn;
	}

	public void setInsurancePrvdrCreatedOn(String insurancePrvdrCreatedOn) {
		this.insurancePrvdrCreatedOn = insurancePrvdrCreatedOn;
	}

	public String getInsurancePrvdrUpdatedOn() {
		return insurancePrvdrUpdatedOn;
	}

	public void setInsurancePrvdrUpdatedOn(String insurancePrvdrUpdatedOn) {
		this.insurancePrvdrUpdatedOn = insurancePrvdrUpdatedOn;
	}
	

	public String getInsurancePrName() {
		return insurancePrName;
	}

	public void setInsurancePrName(String insurancePrName) {
		this.insurancePrName = insurancePrName;
	}
	

	public String getInsurancePrId() {
		return insurancePrId;
	}

	public void setInsurancePrId(String insurancePrId) {
		this.insurancePrId = insurancePrId;
	}

	public String getTransportId() {
		return transportId;
	}

	public void setTransportId(String transportId) {
		this.transportId = transportId;
	}

	public String getTransportName() {
		return transportName;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}

	public String getTransportDesc() {
		return transportDesc;
	}

	public void setTransportDesc(String transportDesc) {
		this.transportDesc = transportDesc;
	}

	public String getTransportCreatedBy() {
		return transportCreatedBy;
	}

	public void setTransportCreatedBy(String transportCreatedBy) {
		this.transportCreatedBy = transportCreatedBy;
	}

	public String getTransportModifiedBy() {
		return transportModifiedBy;
	}

	public void setTransportModifiedBy(String transportModifiedBy) {
		this.transportModifiedBy = transportModifiedBy;
	}

	public String getTransportCreatedOn() {
		return transportCreatedOn;
	}

	public void setTransportCreatedOn(String transportCreatedOn) {
		this.transportCreatedOn = transportCreatedOn;
	}

	public String getTransportUpdatedOn() {
		return transportUpdatedOn;
	}

	public void setTransportUpdatedOn(String transportUpdatedOn) {
		this.transportUpdatedOn = transportUpdatedOn;
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
