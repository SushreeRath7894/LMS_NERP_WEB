package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectProdDetailsModel {
	
	private String skuId;
	private String skuName;
	private String uomId;
	private String uomANme;
	
	
	
	
	public ProjectProdDetailsModel() {
		super();
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getUomId() {
		return uomId;
	}
	public void setUomId(String uomId) {
		this.uomId = uomId;
	}
	public String getUomANme() {
		return uomANme;
	}
	public void setUomANme(String uomANme) {
		this.uomANme = uomANme;
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
