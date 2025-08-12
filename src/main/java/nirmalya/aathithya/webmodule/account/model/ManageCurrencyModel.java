package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManageCurrencyModel {
	
	private String currencyid;
	private String formalname;
	private String currencysymbol;
	private String currencycode;
	private String decimalplaces;
	private String amountmillions;
	private String saymbolamount;
	private String spaceamountsymbol;
	private String placesamountwords;
	
	
	
	public ManageCurrencyModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCurrencysymbol() {
		return currencysymbol;
	}
	public void setCurrencysymbol(String currencysymbol) {
		this.currencysymbol = currencysymbol;
	}
	public String getCurrencyid() {
		return currencyid;
	}
	public void setCurrencyid(String currencyid) {
		this.currencyid = currencyid;
	}
	public String getFormalname() {
		return formalname;
	}
	public void setFormalname(String formalname) {
		this.formalname = formalname;
	}
	public String getCurrencycode() {
		return currencycode;
	}
	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}
	public String getDecimalplaces() {
		return decimalplaces;
	}
	public void setDecimalplaces(String decimalplaces) {
		this.decimalplaces = decimalplaces;
	}
	public String getAmountmillions() {
		return amountmillions;
	}
	public void setAmountmillions(String amountmillions) {
		this.amountmillions = amountmillions;
	}
	public String getSaymbolamount() {
		return saymbolamount;
	}
	public void setSaymbolamount(String saymbolamount) {
		this.saymbolamount = saymbolamount;
	}
	public String getSpaceamountsymbol() {
		return spaceamountsymbol;
	}
	public void setSpaceamountsymbol(String spaceamountsymbol) {
		this.spaceamountsymbol = spaceamountsymbol;
	}
	public String getPlacesamountwords() {
		return placesamountwords;
	}
	public void setPlacesamountwords(String placesamountwords) {
		this.placesamountwords = placesamountwords;
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
