package nirmalya.aathithya.webmodule.trial.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ManageFuelWebModel {
	
	
	private String fsid;
	private String vehicleregno;
	private String fsname;
	private String fscontact;
	private String vendorname;
	private String fromDate;
	private String toDate;
	private String startodo;
	private String endodo;
	private String totalododiff;
	private String fueltype;
	private String rfunit;
	private String uprice;
	private String createdBy;
	
	public String getFsid() {
		return fsid;
	}

	public void setFsid(String fsid) {
		this.fsid = fsid;
	}

	public String getVehicleregno() {
		return vehicleregno;
	}

	public void setVehicleregno(String vehicleregno) {
		this.vehicleregno = vehicleregno;
	}

	public String getFsname() {
		return fsname;
	}

	public void setFsname(String fsname) {
		this.fsname = fsname;
	}

	public String getFscontact() {
		return fscontact;
	}

	public void setFscontact(String fscontact) {
		this.fscontact = fscontact;
	}

	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getStartodo() {
		return startodo;
	}

	public void setStartodo(String startodo) {
		this.startodo = startodo;
	}

	public String getEndodo() {
		return endodo;
	}

	public void setEndodo(String endodo) {
		this.endodo = endodo;
	}

	public String getTotalododiff() {
		return totalododiff;
	}

	public void setTotalododiff(String totalododiff) {
		this.totalododiff = totalododiff;
	}

	public String getFueltype() {
		return fueltype;
	}

	public void setFueltype(String fueltype) {
		this.fueltype = fueltype;
	}

	public String getRfunit() {
		return rfunit;
	}

	public void setRfunit(String rfunit) {
		this.rfunit = rfunit;
	}

	public String getUprice() {
		return uprice;
	}

	public void setUprice(String uprice) {
		this.uprice = uprice;
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
