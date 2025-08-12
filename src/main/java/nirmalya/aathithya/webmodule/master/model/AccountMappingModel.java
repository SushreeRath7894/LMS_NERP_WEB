package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountMappingModel {
private String  mappingId;
private String costCenterId;
private String chartOfAcId;
private String costcenterCode;
private String caCode;
private String createdBy;

public AccountMappingModel() {
	super();
	// TODO Auto-generated constructor stub
}
public String getMappingId() {
	return mappingId;
}
public void setMappingId(String mappingId) {
	this.mappingId = mappingId;
}
public String getCostCenterId() {
	return costCenterId;
}
public void setCostCenterId(String costCenterId) {
	this.costCenterId = costCenterId;
}
public String getChartOfAcId() {
	return chartOfAcId;
}
public void setChartOfAcId(String chartOfAcId) {
	this.chartOfAcId = chartOfAcId;
}
public String getCostcenterCode() {
	return costcenterCode;
}
public void setCostcenterCode(String costcenterCode) {
	this.costcenterCode = costcenterCode;
}
public String getCaCode() {
	return caCode;
}
public void setCaCode(String caCode) {
	this.caCode = caCode;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
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
