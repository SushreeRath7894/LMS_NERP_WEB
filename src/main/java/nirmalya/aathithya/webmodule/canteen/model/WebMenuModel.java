package nirmalya.aathithya.webmodule.canteen.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebMenuModel {
	
	private String itemId;
	private String itemName;
	private String comboName;
	private String price;
	private String allPrice;
	private String categry;
	private String subcategry;
	private String variant;
	private String status;
	private String comboId;
	private String organization;
	private String orgDivision;
	
	private List<WebMenuModel> itemList;
	
	
	
	public WebMenuModel() {
		super();
		// TODO Auto-generated constructor stub
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


	public String getAllPrice() {
		return allPrice;
	}
	public void setAllPrice(String allPrice) {
		this.allPrice = allPrice;
	}
	public String getComboName() {
		return comboName;
	}
	public void setComboName(String comboName) {
		this.comboName = comboName;
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
	public String getvariant() {
		return variant;
	}
	public void setvariant(String variant) {
		this.variant = variant;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComboId() {
		return comboId;
	}
	public void setComboId(String comboId) {
		this.comboId = comboId;
	}
	public List<WebMenuModel> getItemList() {
		return itemList;
	}
	public void setItemList(List<WebMenuModel> itemList) {
		this.itemList = itemList;
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
