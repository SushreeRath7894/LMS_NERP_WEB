package nirmalya.aathithya.webmodule.sales.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerGraphModel {
	private String month;
	private String customer;
	private Integer totalInvoice;
	private Integer totalQuotation;
	private String store;
	private Double totalInvoiceAmt;

	private List<String> monthList = new ArrayList<String>();
	private List<String> storeList = new ArrayList<String>();

	private List<Integer> totalInvoiceList = new ArrayList<Integer>();
	private List<Integer> totalQuotationList = new ArrayList<Integer>();
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getTotalInvoice() {
		return totalInvoice;
	}
	public void setTotalInvoice(Integer totalInvoice) {
		this.totalInvoice = totalInvoice;
	}
	public Integer getTotalQuotation() {
		return totalQuotation;
	}
	public void setTotalQuotation(Integer totalQuotation) {
		this.totalQuotation = totalQuotation;
	}
	public List<String> getMonthList() {
		return monthList;
	}
	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
	}
	public List<Integer> getTotalInvoiceList() {
		return totalInvoiceList;
	}
	public void setTotalInvoiceList(List<Integer> totalInvoiceList) {
		this.totalInvoiceList = totalInvoiceList;
	}
	public List<Integer> getTotalQuotationList() {
		return totalQuotationList;
	}
	public void setTotalQuotationList(List<Integer> totalQuotationList) {
		this.totalQuotationList = totalQuotationList;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public List<String> getStoreList() {
		return storeList;
	}
	public void setStoreList(List<String> storeList) {
		this.storeList = storeList;
	}
	public Double getTotalInvoiceAmt() {
		return totalInvoiceAmt;
	}
	public void setTotalInvoiceAmt(Double totalInvoiceAmt) {
		this.totalInvoiceAmt = totalInvoiceAmt;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	
}
