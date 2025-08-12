package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManageShopFloorModel {

	private String createdBy;
	private String organization;
	private String orgDivision;

	private String floorId;
	private String date;
	private String shift;
	private String remark;

	private String mcId;
	private String sku;
	private String pouchReading;
	private String trail;
	private String leaker;
	private String damage;
	private String bdLog;
	private String bdDuration;
	private String bdFromDt;
	private String bdFromTime;
	private String bdToDt;
	private String bdToTime;
	private String bdMcId;
	private String bdSku;
	private String mcRemark;

	List<ManageShopFloorModel> itemDtls;

	public ManageShopFloorModel() {
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

	public String getFloorId() {
		return floorId;
	}

	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMcId() {
		return mcId;
	}

	public void setMcId(String mcId) {
		this.mcId = mcId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getPouchReading() {
		return pouchReading;
	}

	public void setPouchReading(String pouchReading) {
		this.pouchReading = pouchReading;
	}

	public String getTrail() {
		return trail;
	}

	public void setTrail(String trail) {
		this.trail = trail;
	}

	public String getLeaker() {
		return leaker;
	}

	public void setLeaker(String leaker) {
		this.leaker = leaker;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}

	public String getBdLog() {
		return bdLog;
	}

	public void setBdLog(String bdLog) {
		this.bdLog = bdLog;
	}

	public String getBdDuration() {
		return bdDuration;
	}

	public void setBdDuration(String bdDuration) {
		this.bdDuration = bdDuration;
	}

	public List<ManageShopFloorModel> getItemDtls() {
		return itemDtls;
	}

	public void setItemDtls(List<ManageShopFloorModel> itemDtls) {
		this.itemDtls = itemDtls;
	}

	public String getBdFromDt() {
		return bdFromDt;
	}

	public void setBdFromDt(String bdFromDt) {
		this.bdFromDt = bdFromDt;
	}

	public String getBdFromTime() {
		return bdFromTime;
	}

	public void setBdFromTime(String bdFromTime) {
		this.bdFromTime = bdFromTime;
	}

	public String getBdToDt() {
		return bdToDt;
	}

	public void setBdToDt(String bdToDt) {
		this.bdToDt = bdToDt;
	}

	public String getBdToTime() {
		return bdToTime;
	}

	public void setBdToTime(String bdToTime) {
		this.bdToTime = bdToTime;
	}

	public String getBdMcId() {
		return bdMcId;
	}

	public void setBdMcId(String bdMcId) {
		this.bdMcId = bdMcId;
	}

	public String getBdSku() {
		return bdSku;
	}

	public String getMcRemark() {
		return mcRemark;
	}

	public void setMcRemark(String mcRemark) {
		this.mcRemark = mcRemark;
	}

	public void setBdSku(String bdSku) {
		this.bdSku = bdSku;
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
