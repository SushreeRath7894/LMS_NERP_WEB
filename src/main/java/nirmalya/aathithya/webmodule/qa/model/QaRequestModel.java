package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QaRequestModel {
	
	private String id;
	private String testRes;
	private String paramid;
	private String task;
	private String sku;
	private String status;
	private String refObjData;
	private String createdBy;
	private String orgName;
	private String orgDiv;
	
	
	/*-------------- ORI FOOD --------------*/
	
	private String requestId; 
	private String itemid;
	private String sampleAmt;
	private String resStatus;
	private String remark;
	
	public QaRequestModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestRes() {
		return testRes;
	}

	public void setTestRes(String testRes) {
		this.testRes = testRes;
	}

	public String getParamid() {
		return paramid;
	}

	public void setParamid(String paramid) {
		this.paramid = paramid;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRefObjData() {
		return refObjData;
	}

	public void setRefObjData(String refObjData) {
		this.refObjData = refObjData;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDiv() {
		return orgDiv;
	}

	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getSampleAmt() {
		return sampleAmt;
	}

	public void setSampleAmt(String sampleAmt) {
		this.sampleAmt = sampleAmt;
	}

	public String getResStatus() {
		return resStatus;
	}

	public void setResStatus(String resStatus) {
		this.resStatus = resStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
