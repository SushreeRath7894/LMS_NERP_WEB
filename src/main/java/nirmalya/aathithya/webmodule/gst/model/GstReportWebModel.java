package nirmalya.aathithya.webmodule.gst.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GstReportWebModel {

	private String gstInNo;
	private String month;
	private String year;
	private String companyName;
	private String totalTaxableValue;
	private String integratedTax;
	private String centralTax;
	private String stateTax;
	private String cess;
	private String placeOfSupply;
	private String totalTaxVal;
	private String amtIntegratedTax;

	private String nonGstInterState;
	private String nonGstIntraState;

	private String interStateSupplies;
	private String intraStateSupplies;

	private String integratedTcsTax;
	private String centralTcsTax;
	private String stateTcsTax;

	private String integratedTdsTax;
	private String centralTdsTax;
	private String stateTdsTax;

	public GstReportWebModel() {
		super();
	}

	public String getGstInNo() {
		return gstInNo;
	}

	public void setGstInNo(String gstInNo) {
		this.gstInNo = gstInNo;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTotalTaxableValue() {
		return totalTaxableValue;
	}

	public void setTotalTaxableValue(String totalTaxableValue) {
		this.totalTaxableValue = totalTaxableValue;
	}

	public String getIntegratedTax() {
		return integratedTax;
	}

	public void setIntegratedTax(String integratedTax) {
		this.integratedTax = integratedTax;
	}

	public String getCentralTax() {
		return centralTax;
	}

	public void setCentralTax(String centralTax) {
		this.centralTax = centralTax;
	}

	public String getStateTax() {
		return stateTax;
	}

	public void setStateTax(String stateTax) {
		this.stateTax = stateTax;
	}

	public String getCess() {
		return cess;
	}

	public void setCess(String cess) {
		this.cess = cess;
	}

	public String getPlaceOfSupply() {
		return placeOfSupply;
	}

	public void setPlaceOfSupply(String placeOfSupply) {
		this.placeOfSupply = placeOfSupply;
	}

	public String getTotalTaxVal() {
		return totalTaxVal;
	}

	public void setTotalTaxVal(String totalTaxVal) {
		this.totalTaxVal = totalTaxVal;
	}

	public String getAmtIntegratedTax() {
		return amtIntegratedTax;
	}

	public void setAmtIntegratedTax(String amtIntegratedTax) {
		this.amtIntegratedTax = amtIntegratedTax;
	}

	public String getNonGstInterState() {
		return nonGstInterState;
	}

	public void setNonGstInterState(String nonGstInterState) {
		this.nonGstInterState = nonGstInterState;
	}

	public String getNonGstIntraState() {
		return nonGstIntraState;
	}

	public void setNonGstIntraState(String nonGstIntraState) {
		this.nonGstIntraState = nonGstIntraState;
	}

	public String getInterStateSupplies() {
		return interStateSupplies;
	}

	public void setInterStateSupplies(String interStateSupplies) {
		this.interStateSupplies = interStateSupplies;
	}

	public String getIntraStateSupplies() {
		return intraStateSupplies;
	}

	public void setIntraStateSupplies(String intraStateSupplies) {
		this.intraStateSupplies = intraStateSupplies;
	}

	public String getIntegratedTcsTax() {
		return integratedTcsTax;
	}

	public void setIntegratedTcsTax(String integratedTcsTax) {
		this.integratedTcsTax = integratedTcsTax;
	}

	public String getCentralTcsTax() {
		return centralTcsTax;
	}

	public void setCentralTcsTax(String centralTcsTax) {
		this.centralTcsTax = centralTcsTax;
	}

	public String getStateTcsTax() {
		return stateTcsTax;
	}

	public void setStateTcsTax(String stateTcsTax) {
		this.stateTcsTax = stateTcsTax;
	}

	public String getIntegratedTdsTax() {
		return integratedTdsTax;
	}

	public void setIntegratedTdsTax(String integratedTdsTax) {
		this.integratedTdsTax = integratedTdsTax;
	}

	public String getCentralTdsTax() {
		return centralTdsTax;
	}

	public void setCentralTdsTax(String centralTdsTax) {
		this.centralTdsTax = centralTdsTax;
	}

	public String getStateTdsTax() {
		return stateTdsTax;
	}

	public void setStateTdsTax(String stateTdsTax) {
		this.stateTdsTax = stateTdsTax;
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
