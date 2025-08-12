package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PurchaseRegisterExcelModel {
	
	private String voucherDate;
	private String voucherType;
	private String voucherNo;
	private String particulars;
	private String gstIn;
	private String quantity;
	private String unitRate;
	  
	

	public PurchaseRegisterExcelModel() {
		super();
	}
	
	


	public String getVoucherDate() {
		return voucherDate;
	}




	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}




	public String getVoucherType() {
		return voucherType;
	}




	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}




	public String getVoucherNo() {
		return voucherNo;
	}




	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}




	public String getParticulars() {
		return particulars;
	}




	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}




	public String getGstIn() {
		return gstIn;
	}




	public void setGstIn(String gstIn) {
		this.gstIn = gstIn;
	}




	public String getQuantity() {
		return quantity;
	}




	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}




	public String getUnitRate() {
		return unitRate;
	}




	public void setUnitRate(String unitRate) {
		this.unitRate = unitRate;
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
