package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PlanningProductionVariantModel {
	private String itemid;
	private String itemname;
	private String qty;
	private String status;
	
	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	private String date;
	private String shift;
	private String shiftStatus;
	private String productlist;
	private String resourseList;
	
	
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

	public String getShiftStatus() {
		return shiftStatus;
	}

	public void setShiftStatus(String shiftStatus) {
		this.shiftStatus = shiftStatus;
	}

	public String getProductlist() {
		return productlist;
	}

	public void setProductlist(String productlist) {
		this.productlist = productlist;
	}

	public String getResourseList() {
		return resourseList;
	}

	public void setResourseList(String resourseList) {
		this.resourseList = resourseList;
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
