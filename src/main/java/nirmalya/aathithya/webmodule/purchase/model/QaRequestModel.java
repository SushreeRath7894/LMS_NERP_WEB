package nirmalya.aathithya.webmodule.purchase.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QaRequestModel {
	
	private String invoice_id;
	private String purchase_order;
	private String vechile_no;
	private String driver_name;
	private String driver_mobile;
	private String sku_id;
	private String hsn_no;
	private String item_name;
	private String quantity;
	private String unit;
	private String vendor_name;
	private String challan_no;
	private String organization;
	private String orgDivision;

	public QaRequestModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(String invoice_id) {
		this.invoice_id = invoice_id;
	}

	public String getPurchase_order() {
		return purchase_order;
	}

	public void setPurchase_order(String purchase_order) {
		this.purchase_order = purchase_order;
	}

	public String getVechile_no() {
		return vechile_no;
	}

	public void setVechile_no(String vechile_no) {
		this.vechile_no = vechile_no;
	}

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public String getDriver_mobile() {
		return driver_mobile;
	}

	public void setDriver_mobile(String driver_mobile) {
		this.driver_mobile = driver_mobile;
	}

	public String getSku_id() {
		return sku_id;
	}

	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}

	public String getHsn_no() {
		return hsn_no;
	}

	public void setHsn_no(String hsn_no) {
		this.hsn_no = hsn_no;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public String getChallan_no() {
		return challan_no;
	}

	public void setChallan_no(String challan_no) {
		this.challan_no = challan_no;
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
