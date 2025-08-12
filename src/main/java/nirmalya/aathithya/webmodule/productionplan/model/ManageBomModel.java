package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManageBomModel {
	private String bomid;
	
	private String itemName;
	private String itemId;
	private String description;
	private String subItem;
	private String itemUnitId;
	private String itemSubId;
	private String actualQunt;
	private String standardQunt;
	private String scrapQunt;
	private int slnoId;
	private String organization;
	private String orgDivision;
	private String createdBy;
	public ManageBomModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getBomid() {
		return bomid;
	}
	public void setBomid(String bomid) {
		this.bomid = bomid;
	}

	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubItem() {
		return subItem;
	}
	public void setSubItem(String subItem) {
		this.subItem = subItem;
	}
	public String getItemSubId() {
		return itemSubId;
	}
	public void setItemSubId(String itemSubId) {
		this.itemSubId = itemSubId;
	}
	
	public String getItemUnitId() {
		return itemUnitId;
	}
	public void setItemUnitId(String itemUnitId) {
		this.itemUnitId = itemUnitId;
	}
	public String getActualQunt() {
		return actualQunt;
	}
	public void setActualQunt(String actualQunt) {
		this.actualQunt = actualQunt;
	}
	public String getStandardQunt() {
		return standardQunt;
	}
	public void setStandardQunt(String standardQunt) {
		this.standardQunt = standardQunt;
	}
	public String getScrapQunt() {
		return scrapQunt;
	}
	public void setScrapQunt(String scrapQunt) {
		this.scrapQunt = scrapQunt;
	}
	public int getSlnoId() {
		return slnoId;
	}
	public void setSlnoId(int slnoId) {
		this.slnoId = slnoId;
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
