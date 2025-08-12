package nirmalya.aathithya.webmodule.canteen.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CanteenComboModel {
	
	private String itemId;
	private String itemName;
	private String price;
	private String categry;
	private String subcategry;
	private String variant;
	private String status;
	
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCategry() {
		return categry;
	}
	public void setCategry(String categry) {
		this.categry = categry;
	}
	public String getSubcategry() {
		return subcategry;
	}
	public void setSubcategry(String subcategry) {
		this.subcategry = subcategry;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
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
