package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProcurementStockDetailsModel {

	private String month;
	private Double requisition;
	private Double stockTransit;
	private Double stockHand;
	
	private List<String> monthList = new ArrayList<String>();

	private List<Double> reqList = new ArrayList<Double>();
	private List<Double> stockTList = new ArrayList<Double>();
	private List<Double> stockHList = new ArrayList<Double>();

	public ProcurementStockDetailsModel() {
		super();
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getRequisition() {
		return requisition;
	}

	public void setRequisition(Double requisition) {
		this.requisition = requisition;
	}

	public Double getStockTransit() {
		return stockTransit;
	}

	public void setStockTransit(Double stockTransit) {
		this.stockTransit = stockTransit;
	}

	public Double getStockHand() {
		return stockHand;
	}

	public void setStockHand(Double stockHand) {
		this.stockHand = stockHand;
	}

	public List<Double> getReqList() {
		return reqList;
	}

	public void setReqList(List<Double> reqList) {
		this.reqList = reqList;
	}

	public List<Double> getStockTList() {
		return stockTList;
	}

	public void setStockTList(List<Double> stockTList) {
		this.stockTList = stockTList;
	}

	public List<Double> getStockHList() {
		return stockHList;
	}

	public void setStockHList(List<Double> stockHList) {
		this.stockHList = stockHList;
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
