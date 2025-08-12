package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PackingLotManufactureWebModel {

	private String packId;
	private String manufactureId;
	private String mrp;
	private String usedBy;
	private String packStarttime ;
	private String packEndTime;
	private String unitsPacked;
	private String cldPacked;
	private String bulkused;
	private String batchsize;
	private String createdBy;
    private String organization;
	private String orgDivision;

	public String getPackId() {
		return packId;
	}
	public void setPackId(String packId) {
		this.packId = packId;
	}
	public String getManufactureId() {
		return manufactureId;
	}
	public void setManufactureId(String manufactureId) {
		this.manufactureId = manufactureId;
	}
	public String getMrp() {
		return mrp;
	}
	public void setMrp(String mrp) {
		this.mrp = mrp;
	}
	public String getUsedBy() {
		return usedBy;
	}
	public void setUsedBy(String usedBy) {
		this.usedBy = usedBy;
	}
	public String getPackStarttime() {
		return packStarttime;
	}
	public void setPackStarttime(String packStarttime) {
		this.packStarttime = packStarttime;
	}
	public String getPackEndTime() {
		return packEndTime;
	}
	public void setPackEndTime(String packEndTime) {
		this.packEndTime = packEndTime;
	}
	public String getUnitsPacked() {
		return unitsPacked;
	}
	public void setUnitsPacked(String unitsPacked) {
		this.unitsPacked = unitsPacked;
	}
	public String getCldPacked() {
		return cldPacked;
	}
	public void setCldPacked(String cldPacked) {
		this.cldPacked = cldPacked;
	}
	public String getBulkused() {
		return bulkused;
	}
	public void setBulkused(String bulkused) {
		this.bulkused = bulkused;
	}
	public String getBatchsize() {
		return batchsize;
	}
	public void setBatchsize(String batchsize) {
		this.batchsize = batchsize;
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
