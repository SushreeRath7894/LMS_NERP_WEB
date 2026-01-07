package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PipelineLogModel {
	private String pipelineLogId;
	private String pipelineLogBody;
	private String pipelineLogCreatedOn;
	private String pipelineLogCreatedBy;
	private String pipeline;
	public String getPipelineLogId() {
		return pipelineLogId;
	}
	public void setPipelineLogId(String pipelineLogId) {
		this.pipelineLogId = pipelineLogId;
	}
	public String getPipelineLogBody() {
		return pipelineLogBody;
	}
	public void setPipelineLogBody(String pipelineLogBody) {
		this.pipelineLogBody = pipelineLogBody;
	}
	public String getPipelineLogCreatedOn() {
		return pipelineLogCreatedOn;
	}
	public void setPipelineLogCreatedOn(String pipelineLogCreatedOn) {
		this.pipelineLogCreatedOn = pipelineLogCreatedOn;
	}
	public String getPipelineLogCreatedBy() {
		return pipelineLogCreatedBy;
	}
	public void setPipelineLogCreatedBy(String pipelineLogCreatedBy) {
		this.pipelineLogCreatedBy = pipelineLogCreatedBy;
	}
	public String getPipeline() {
		return pipeline;
	}
	public void setPipeline(String pipeline) {
		this.pipeline = pipeline;
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
