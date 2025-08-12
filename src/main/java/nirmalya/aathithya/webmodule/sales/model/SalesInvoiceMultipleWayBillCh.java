package nirmalya.aathithya.webmodule.sales.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SalesInvoiceMultipleWayBillCh {
	private String invoice;
	private String challan;
	private String challanDate;
	private String wayBill;
	private String wayBillDate;
	private String tMode;
	private String vehicleNo;
	private String transporterId;
	private String transporterName;
	private String lrNumber;
	public SalesInvoiceMultipleWayBillCh() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getChallan() {
		return challan;
	}
	public void setChallan(String challan) {
		this.challan = challan;
	}
	public String getChallanDate() {
		return challanDate;
	}
	public void setChallanDate(String challanDate) {
		this.challanDate = challanDate;
	}
	public String getWayBill() {
		return wayBill;
	}
	public void setWayBill(String wayBill) {
		this.wayBill = wayBill;
	}
	public String getWayBillDate() {
		return wayBillDate;
	}
	public void setWayBillDate(String wayBillDate) {
		this.wayBillDate = wayBillDate;
	}
	
	public String gettMode() {
		return tMode;
	}
	public void settMode(String tMode) {
		this.tMode = tMode;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getTransporterId() {
		return transporterId;
	}
	public void setTransporterId(String transporterId) {
		this.transporterId = transporterId;
	}
	public String getTransporterName() {
		return transporterName;
	}
	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}
	public String getLrNumber() {
		return lrNumber;
	}
	public void setLrNumber(String lrNumber) {
		this.lrNumber = lrNumber;
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
