package nirmalya.aathithya.webmodule.sales.model;


import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemDetailswebModel {
	private Integer slNo;

	private String itemId;
	private String sizeInMM;
	private String unitMeasurement;
	private String totalAmount;
	private String itemName;
	private String quantity;
	private Double unitPrice;
	private String sku;
	
	


	public Integer getSlNo() {
		return slNo;
	}


	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}


	public ItemDetailswebModel() {
		super();

	}
	
	
	public String getItemId() {
		return itemId;
	}


	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public String getSizeInMM() {
		return sizeInMM;
	}


	public void setSizeInMM(String sizeInMM) {
		this.sizeInMM = sizeInMM;
	}


	public String getUnitMeasurement() {
		return unitMeasurement;
	}


	public void setUnitMeasurement(String unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}


	public String getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getQuantity() {
		return quantity;
	}


	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}


	public Double getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
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
