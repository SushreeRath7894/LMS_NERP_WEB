package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PipelineTagModel {
	private String tagId;
	private String tagName;
	private String tagDesc;
	private Boolean tagActive;
	private String tagCreatedBy;
	private String action;
	private String status;
	public PipelineTagModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagDesc() {
		return tagDesc;
	}
	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}
	public Boolean getTagActive() {
		return tagActive;
	}
	public void setTagActive(Boolean tagActive) {
		this.tagActive = tagActive;
	}
	public String getTagCreatedBy() {
		return tagCreatedBy;
	}
	public void setTagCreatedBy(String tagCreatedBy) {
		this.tagCreatedBy = tagCreatedBy;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
