package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StockAttributeModel {

	private String sizeId;
	private String binId;
	private String stockSize;
	private String stockQty;
	
	


	public String getSizeId() {
		return sizeId;
	}




	public void setSizeId(String sizeId) {
		this.sizeId = sizeId;
	}




	public String getBinId() {
		return binId;
	}




	public void setBinId(String binId) {
		this.binId = binId;
	}




	public String getStockSize() {
		return stockSize;
	}




	public void setStockSize(String stockSize) {
		this.stockSize = stockSize;
	}




	public String getStockQty() {
		return stockQty;
	}




	public void setStockQty(String stockQty) {
		this.stockQty = stockQty;
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
