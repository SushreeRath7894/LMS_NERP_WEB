package nirmalya.aathithya.webmodule.master.model;

import java.math.BigInteger;

public class CostCenterModel {
	private String ccId;
	private String ccName;
	private String ccCode;
	private String ccrProfitStatus;

	private String createdBy;
	private String level;
	private String ccNature;
	private String ccLabourType;
	private String parentId;
	private BigInteger nodeCount;

public CostCenterModel() {
	super();
	// TODO Auto-generated constructor stub
}
public String getCcId() {
	return ccId;
}
public void setCcId(String ccId) {
	this.ccId = ccId;
}
public String getCcName() {
	return ccName;
}
public void setCcName(String ccName) {
	this.ccName = ccName;
}
public String getCcCode() {
	return ccCode;
}
public void setCcCode(String ccCode) {
	this.ccCode = ccCode;
}
public String getCcrProfitStatus() {
	return ccrProfitStatus;
}
public void setCcrProfitStatus(String ccrProfitStatus) {
	this.ccrProfitStatus = ccrProfitStatus;
}
public String getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
public String getLevel() {
	return level;
}
public void setLevel(String level) {
	this.level = level;
}
public String getCcNature() {
	return ccNature;
}
public void setCcNature(String ccNature) {
	this.ccNature = ccNature;
}
public String getCcLabourType() {
	return ccLabourType;
}
public void setCcLabourType(String ccLabourType) {
	this.ccLabourType = ccLabourType;
}
public String getParentId() {
	return parentId;
}
public void setParentId(String parentId) {
	this.parentId = parentId;
}
public BigInteger getNodeCount() {
	return nodeCount;
}
public void setNodeCount(BigInteger nodeCount) {
	this.nodeCount = nodeCount;
}


}
