package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PipelineLostReasonModel {
	private String lostReason;
	private String lostReasonDes;
	private Boolean lostReasonStatus;
	private String createdBy;
	private String action;
	private String status;
	public PipelineLostReasonModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLostReason() {
		return lostReason;
	}

	public void setLostReason(String lostReason) {
		this.lostReason = lostReason;
	}

	public String getLostReasonDes() {
		return lostReasonDes;
	}

	public void setLostReasonDes(String lostReasonDes) {
		this.lostReasonDes = lostReasonDes;
	}

	public Boolean getLostReasonStatus() {
		return lostReasonStatus;
	}

	public void setLostReasonStatus(Boolean lostReasonStatus) {
		this.lostReasonStatus = lostReasonStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
