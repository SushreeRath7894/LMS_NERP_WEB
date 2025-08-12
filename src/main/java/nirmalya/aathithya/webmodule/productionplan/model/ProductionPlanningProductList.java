package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductionPlanningProductList {

	private String date;
	private String shift;
	private String proid;
	private String productid;
	private String productname;
	private String qtyid;
	private String qtyval;

	private String machineid;
	private String machineHour;

	private String shiftStatus;
	private String productStatus;
	private String productlist1;
	private String resourseList;
	
	
	public String getShiftStatus() {
		return shiftStatus;
	}

	public void setShiftStatus(String shiftStatus) {
		this.shiftStatus = shiftStatus;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getProductlist1() {
		return productlist1;
	}

	public void setProductlist1(String productlist1) {
		this.productlist1 = productlist1;
	}

	public String getResourseList() {
		return resourseList;
	}

	public void setResourseList(String resourseList) {
		this.resourseList = resourseList;
	}

	public String getQtyid() {
		return qtyid;
	}

	public void setQtyid(String qtyid) {
		this.qtyid = qtyid;
	}

	public String getQtyval() {
		return qtyval;
	}

	public void setQtyval(String qtyval) {
		this.qtyval = qtyval;
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

	public String getProid() {
		return proid;
	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getMachineid() {
		return machineid;
	}

	public void setMachineid(String machineid) {
		this.machineid = machineid;
	}

	public String getMachineHour() {
		return machineHour;
	}

	public void setMachineHour(String machineHour) {
		this.machineHour = machineHour;
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
