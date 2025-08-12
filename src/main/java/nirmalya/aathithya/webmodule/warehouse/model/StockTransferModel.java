package nirmalya.aathithya.webmodule.warehouse.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StockTransferModel {

	private String createdBy;
	private String organization;
	private String orgDivision;

	private String bindata;
	private String updatebindata;
	private String allocationId;
	private String quantity;

	public StockTransferModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getBindata() {
		return bindata;
	}

	public void setBindata(String bindata) {
		this.bindata = bindata;
	}

	public String getUpdatebindata() {
		return updatebindata;
	}

	public void setUpdatebindata(String updatebindata) {
		this.updatebindata = updatebindata;
	}

	public String getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(String allocationId) {
		this.allocationId = allocationId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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
