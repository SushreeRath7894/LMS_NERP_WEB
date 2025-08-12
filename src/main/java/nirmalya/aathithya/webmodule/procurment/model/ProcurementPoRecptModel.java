package nirmalya.aathithya.webmodule.procurment.model;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProcurementPoRecptModel {

	private String month;
	private BigInteger immediate;
	private BigInteger general;
	private BigInteger high;

	private List<String> monthList = new ArrayList<String>();

	private List<BigInteger> imdList = new ArrayList<BigInteger>();
	private List<BigInteger> generalList = new ArrayList<BigInteger>();
	private List<BigInteger> highList = new ArrayList<BigInteger>();

	public ProcurementPoRecptModel() {
		super();
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<String> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
	}

	public BigInteger getImmediate() {
		return immediate;
	}

	public void setImmediate(BigInteger immediate) {
		this.immediate = immediate;
	}

	public BigInteger getGeneral() {
		return general;
	}

	public void setGeneral(BigInteger general) {
		this.general = general;
	}

	public BigInteger getHigh() {
		return high;
	}

	public void setHigh(BigInteger high) {
		this.high = high;
	}

	public List<BigInteger> getImdList() {
		return imdList;
	}

	public void setImdList(List<BigInteger> imdList) {
		this.imdList = imdList;
	}

	public List<BigInteger> getGeneralList() {
		return generalList;
	}

	public void setGeneralList(List<BigInteger> generalList) {
		this.generalList = generalList;
	}

	public List<BigInteger> getHighList() {
		return highList;
	}

	public void setHighList(List<BigInteger> highList) {
		this.highList = highList;
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
