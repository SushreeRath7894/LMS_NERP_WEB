package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PipelineSmsModel {
	private String pipelineSmsId;
	private String pipelineSmsTo;
	private String pipelineSmsBody;
	private String pipelineSmsCreatedOn;
	private String pipelinSmsCreatedBy;
	private String pipeline;

	public PipelineSmsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPipelineSmsId() {
		return pipelineSmsId;
	}

	public void setPipelineSmsId(String pipelineSmsId) {
		this.pipelineSmsId = pipelineSmsId;
	}

	public String getPipelineSmsTo() {
		return pipelineSmsTo;
	}

	public void setPipelineSmsTo(String pipelineSmsTo) {
		this.pipelineSmsTo = pipelineSmsTo;
	}

	public String getPipelineSmsBody() {
		return pipelineSmsBody;
	}

	public void setPipelineSmsBody(String pipelineSmsBody) {
		this.pipelineSmsBody = pipelineSmsBody;
	}

	public String getPipelineSmsCreatedOn() {
		return pipelineSmsCreatedOn;
	}

	public void setPipelineSmsCreatedOn(String pipelineSmsCreatedOn) {
		this.pipelineSmsCreatedOn = pipelineSmsCreatedOn;
	}

	public String getPipelinSmsCreatedBy() {
		return pipelinSmsCreatedBy;
	}

	public void setPipelinSmsCreatedBy(String pipelinSmsCreatedBy) {
		this.pipelinSmsCreatedBy = pipelinSmsCreatedBy;
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
