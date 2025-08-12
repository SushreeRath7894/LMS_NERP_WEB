package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;
import java.math.BigInteger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DemoTreeModel {
	
	private String demoId;
	private String demoName;
	private String demoDesc;
	private String demoStatus;
	private String parentId;
	private String createdBy;
	private String demoLevel;
	private String parentName;
	private BigInteger nodeCount;
	
	public BigInteger getNodeCount() {
		return nodeCount;
	}
	public void setNodeCount(BigInteger nodeCount) {
		this.nodeCount = nodeCount;
	}
	public String getDemoId() {
		return demoId;
	}
	public void setDemoId(String demoId) {
		this.demoId = demoId;
	}
	public String getDemoName() {
		return demoName;
	}
	public void setDemoName(String demoName) {
		this.demoName = demoName;
	}
	public String getDemoDesc() {
		return demoDesc;
	}
	public void setDemoDesc(String demoDesc) {
		this.demoDesc = demoDesc;
	}
	public String getDemoStatus() {
		return demoStatus;
	}
	public void setDemoStatus(String demoStatus) {
		this.demoStatus = demoStatus;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getDemoLevel() {
		return demoLevel;
	}
	public void setDemoLevel(String demoLevel) {
		this.demoLevel = demoLevel;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
