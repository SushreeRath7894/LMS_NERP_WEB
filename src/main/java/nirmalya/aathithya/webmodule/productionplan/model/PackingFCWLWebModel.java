package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PackingFCWLWebModel {
	private String packId;
	private String id;
	private String pmCarton;
	private String packedCarton;
	private String skuNo;
	private String pmWeight;
	private String bulkWeight;
	private String jsonData1;
	private String jsonData2;
	private String jsonData3;
	private String jsonData4;
	private String createdBy;
    private String organization;
	private String orgDivision;
	public String getPackId() {
		return packId;
	}
	public void setPackId(String packId) {
		this.packId = packId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPmCarton() {
		return pmCarton;
	}
	public void setPmCarton(String pmCarton) {
		this.pmCarton = pmCarton;
	}
	public String getPackedCarton() {
		return packedCarton;
	}
	public void setPackedCarton(String packedCarton) {
		this.packedCarton = packedCarton;
	}
	public String getSkuNo() {
		return skuNo;
	}
	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}
	public String getPmWeight() {
		return pmWeight;
	}
	public void setPmWeight(String pmWeight) {
		this.pmWeight = pmWeight;
	}
	public String getBulkWeight() {
		return bulkWeight;
	}
	public void setBulkWeight(String bulkWeight) {
		this.bulkWeight = bulkWeight;
	}
	public String getJsonData1() {
		return jsonData1;
	}
	public void setJsonData1(String jsonData1) {
		this.jsonData1 = jsonData1;
	}
	public String getJsonData2() {
		return jsonData2;
	}
	public void setJsonData2(String jsonData2) {
		this.jsonData2 = jsonData2;
	}
	public String getJsonData3() {
		return jsonData3;
	}
	public void setJsonData3(String jsonData3) {
		this.jsonData3 = jsonData3;
	}
	public String getJsonData4() {
		return jsonData4;
	}
	public void setJsonData4(String jsonData4) {
		this.jsonData4 = jsonData4;
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
