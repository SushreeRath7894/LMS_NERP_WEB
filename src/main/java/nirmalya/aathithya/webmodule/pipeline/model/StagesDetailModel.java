package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StagesDetailModel {

	private List<String> dateList = new ArrayList<String>();
	private String fromStageList;
	private String toStageList;
	private String createdDateList;
	private Integer pipelineType;
	private String createdBy;
	private String createdOn;
	private Boolean status;
	private String id;
	public StagesDetailModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFromStageList() {
		return fromStageList;
	}

	public void setFromStageList(String fromStageList) {
		this.fromStageList = fromStageList;
	}

	public String getToStageList() {
		return toStageList;
	}

	public void setToStageList(String toStageList) {
		this.toStageList = toStageList;
	}

	public String getCreatedDateList() {
		return createdDateList;
	}

	public void setCreatedDateList(String createdDateList) {
		this.createdDateList = createdDateList;
	}

	public List<String> getDateList() {
		return dateList;
	}

	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}

	public Integer getPipelineType() {
		return pipelineType;
	}

	public void setPipelineType(Integer pipelineType) {
		this.pipelineType = pipelineType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
