package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BudgetPlanMasterModel {

	private String id;
	private String year;
	private Double jan;
	private Double feb;
	private Double mar;
	private Double apr;
	private Double may;
	private Double jun;
	private Double jul;
	private Double aug;
	private Double sep;
	private Double oct;
	private Double nov;
	private Double dec;
	private Double total;
	private String createdBy;
	private List<String> monthList = new ArrayList<String>();
	
	public BudgetPlanMasterModel() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Double getJan() {
		return jan;
	}

	public void setJan(Double jan) {
		this.jan = jan;
	}

	public Double getFeb() {
		return feb;
	}

	public void setFeb(Double feb) {
		this.feb = feb;
	}

	public Double getMar() {
		return mar;
	}

	public void setMar(Double mar) {
		this.mar = mar;
	}

	public Double getApr() {
		return apr;
	}

	public void setApr(Double apr) {
		this.apr = apr;
	}

	public Double getMay() {
		return may;
	}

	public void setMay(Double may) {
		this.may = may;
	}

	public Double getJun() {
		return jun;
	}

	public void setJun(Double jun) {
		this.jun = jun;
	}

	public Double getJul() {
		return jul;
	}

	public void setJul(Double jul) {
		this.jul = jul;
	}

	public Double getAug() {
		return aug;
	}

	public void setAug(Double aug) {
		this.aug = aug;
	}

	public Double getSep() {
		return sep;
	}

	public void setSep(Double sep) {
		this.sep = sep;
	}

	public Double getOct() {
		return oct;
	}

	public void setOct(Double oct) {
		this.oct = oct;
	}

	public Double getNov() {
		return nov;
	}

	public void setNov(Double nov) {
		this.nov = nov;
	}

	public Double getDec() {
		return dec;
	}

	public void setDec(Double dec) {
		this.dec = dec;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public List<String> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
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
