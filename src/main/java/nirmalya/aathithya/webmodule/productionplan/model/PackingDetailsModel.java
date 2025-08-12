package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PackingDetailsModel {

	private String processId;
	private String mixerNo;
	private String brandNo;
	private String mcNo;
	private String mcId;
	private String itemsku;
	private String itemName;
	private String pQtySack;
	private String pQtyMt;
	private String sapBook;
	private String qHoldRQty;
	private String packingRemark;

	private String organization;
	private String orgDivision;

	public PackingDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getMixerNo() {
		return mixerNo;
	}

	public void setMixerNo(String mixerNo) {
		this.mixerNo = mixerNo;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getMcNo() {
		return mcNo;
	}

	public void setMcNo(String mcNo) {
		this.mcNo = mcNo;
	}

	public String getItemsku() {
		return itemsku;
	}

	public void setItemsku(String itemsku) {
		this.itemsku = itemsku;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getpQtySack() {
		return pQtySack;
	}

	public void setpQtySack(String pQtySack) {
		this.pQtySack = pQtySack;
	}

	public String getpQtyMt() {
		return pQtyMt;
	}

	public void setpQtyMt(String pQtyMt) {
		this.pQtyMt = pQtyMt;
	}

	public String getSapBook() {
		return sapBook;
	}

	public void setSapBook(String sapBook) {
		this.sapBook = sapBook;
	}

	public String getqHoldRQty() {
		return qHoldRQty;
	}

	public void setqHoldRQty(String qHoldRQty) {
		this.qHoldRQty = qHoldRQty;
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

	public String getPackingRemark() {
		return packingRemark;
	}

	public void setPackingRemark(String packingRemark) {
		this.packingRemark = packingRemark;
	}

	public String getMcId() {
		return mcId;
	}

	public void setMcId(String mcId) {
		this.mcId = mcId;
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
