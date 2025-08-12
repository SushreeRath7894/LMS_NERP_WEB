package nirmalya.aathithya.webmodule.trial.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IssueHistoryWebModel {

	private String issuergnumber;
	private String issuergname;
	private String ticketname;
	private String vehiclename;
	private String description;
	private String fromDate;
	private String phonenumber;
	private String vendorname;
	private String createdBy;
	private String docName;
	private String docUrl;

	public String getDocUrl() {
		return docUrl;
	}

	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getVendorname() {
		return vendorname;
	}

	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	public String getIssuergnumber() {
		return issuergnumber;
	}

	public void setIssuergnumber(String issuergnumber) {
		this.issuergnumber = issuergnumber;
	}

	public String getIssuergname() {
		return issuergname;
	}

	public void setIssuergname(String issuergname) {
		this.issuergname = issuergname;
	}

	public String getTicketname() {
		return ticketname;
	}

	public void setTicketname(String ticketname) {
		this.ticketname = ticketname;
	}

	public String getVehiclename() {
		return vehiclename;
	}

	public void setVehiclename(String vehiclename) {
		this.vehiclename = vehiclename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
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
