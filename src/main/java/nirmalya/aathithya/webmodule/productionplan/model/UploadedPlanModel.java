package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UploadedPlanModel {
	private String planId;
	private String week;
	private String description;
	private String basepack;
	private String itemId;
	private String itemName;
	private Double quantity;
	private String createdBy;
	private String organization;
	private String orgDivision;
	private String approveStatus;
	private String boomName;
	private String boomId;
	private String unit;
	private String unitName;
	private List<String> bomList = new ArrayList<String>();
	private List<String> bomeNameList = new ArrayList<String>();
	
	public UploadedPlanModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBasepack() {
		return basepack;
	}
	public void setBasepack(String basepack) {
		this.basepack = basepack;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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
	
	
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	
	
	public String getBoomName() {
		return boomName;
	}
	public void setBoomName(String boomName) {
		this.boomName = boomName;
	}
	public List<String> getBomList() {
		return bomList;
	}
	public void setBomList(List<String> bomList) {
		this.bomList = bomList;
	}
	
	
	public String getBoomId() {
		return boomId;
	}
	public void setBoomId(String boomId) {
		this.boomId = boomId;
	}
	
	public List<String> getBomeNameList() {
		return bomeNameList;
	}
	public void setBomeNameList(List<String> bomeNameList) {
		this.bomeNameList = bomeNameList;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
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
