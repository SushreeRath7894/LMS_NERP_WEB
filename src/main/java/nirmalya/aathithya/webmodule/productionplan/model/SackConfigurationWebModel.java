package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SackConfigurationWebModel {

	private String sackId;
	private String productId;
	private String sku;
	private String caseConfig;
	private String packSize;
	private String totalMt;
	private String remark;

	private String organization;
	private String orgDivision;
	private String createdBy;

	public SackConfigurationWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSackId() {
		return sackId;
	}

	public void setSackId(String sackId) {
		this.sackId = sackId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getCaseConfig() {
		return caseConfig;
	}

	public void setCaseConfig(String caseConfig) {
		this.caseConfig = caseConfig;
	}

	public String getPackSize() {
		return packSize;
	}

	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}

	public String getTotalMt() {
		return totalMt;
	}

	public void setTotalMt(String totalMt) {
		this.totalMt = totalMt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
