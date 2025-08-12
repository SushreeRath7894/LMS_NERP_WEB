package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataSetAccountTree {


private String groupId;

private String groupName;

private String levelName;

private String parentName;

private String parentId;

private String orgName;

private String orgDivision;

private String amount;

private String childAmount;

private String totalcreditAmount;

private String totalDebitAmount;

private String natureOfGroup;

private String subChildAmount;
public String getSubChildDebitAmount() {
	return subChildDebitAmount;
}



public void setSubChildDebitAmount(String subChildDebitAmount) {
	this.subChildDebitAmount = subChildDebitAmount;
}



public String getSubChildCreditAmount() {
	return subChildCreditAmount;
}



public void setSubChildCreditAmount(String subChildCreditAmount) {
	this.subChildCreditAmount = subChildCreditAmount;
}



private String subChildDebitAmount;
private String subChildCreditAmount;

public DataSetAccountTree() {
super();
// TODO Auto-generated constructor stub
}



public String getParentId() {
return parentId;
}



public void setParentId(String parentId) {
this.parentId = parentId;
}



public String getGroupId() {
return groupId;
}



public void setGroupId(String groupId) {
this.groupId = groupId;
}



public String getGroupName() {
return groupName;
}



public void setGroupName(String groupName) {
this.groupName = groupName;
}



public String getLevelName() {
return levelName;
}



public void setLevelName(String levelName) {
this.levelName = levelName;
}



public String getParentName() {
return parentName;
}



public void setParentName(String parentName) {
this.parentName = parentName;
}



public String getOrgName() {
	return orgName;
}



public void setOrgName(String orgName) {
	this.orgName = orgName;
}



public String getOrgDivision() {
	return orgDivision;
}



public void setOrgDivision(String orgDivision) {
	this.orgDivision = orgDivision;
}



public String getAmount() {
	return amount;
}



public void setAmount(String amount) {
	this.amount = amount;
}



public String getChildAmount() {
	return childAmount;
}



public void setChildAmount(String childAmount) {
	this.childAmount = childAmount;
}



public String getTotalcreditAmount() {
	return totalcreditAmount;
}



public void setTotalcreditAmount(String totalcreditAmount) {
	this.totalcreditAmount = totalcreditAmount;
}



public String getTotalDebitAmount() {
	return totalDebitAmount;
}



public void setTotalDebitAmount(String totalDebitAmount) {
	this.totalDebitAmount = totalDebitAmount;
}



public String getNatureOfGroup() {
	return natureOfGroup;
}



public void setNatureOfGroup(String natureOfGroup) {
	this.natureOfGroup = natureOfGroup;
}



public String getSubChildAmount() {
	return subChildAmount;
}



public void setSubChildAmount(String subChildAmount) {
	this.subChildAmount = subChildAmount;
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



