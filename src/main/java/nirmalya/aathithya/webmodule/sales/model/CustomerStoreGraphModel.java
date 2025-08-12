package nirmalya.aathithya.webmodule.sales.model;

public class CustomerStoreGraphModel {
	private String cityname;
	private Double invoiceAmt;
	private Double salesOrderAmt;
	
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public Double getInvoiceAmt() {
		return invoiceAmt;
	}
	public void setInvoiceAmt(Double invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	public Double getSalesOrderAmt() {
		return salesOrderAmt;
	}
	public void setSalesOrderAmt(Double salesOrderAmt) {
		this.salesOrderAmt = salesOrderAmt;
	}
	
}
