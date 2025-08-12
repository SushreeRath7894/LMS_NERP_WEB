package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PipelineSalesTeamModel {
	private String teamId;
	private String teamName;
	private String teamLeader;
	private String teamEmail;
	private String teamEmailAcceptance;
	private String teamAssign;
	private Boolean teamStatus;
	private Boolean teamPipeLine;
	private String createdBy;
	private String action;

	public PipelineSalesTeamModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}

	public String getTeamEmail() {
		return teamEmail;
	}

	public void setTeamEmail(String teamEmail) {
		this.teamEmail = teamEmail;
	}

	public String getTeamEmailAcceptance() {
		return teamEmailAcceptance;
	}

	public void setTeamEmailAcceptance(String teamEmailAcceptance) {
		this.teamEmailAcceptance = teamEmailAcceptance;
	}

	public String getTeamAssign() {
		return teamAssign;
	}

	public void setTeamAssign(String teamAssign) {
		this.teamAssign = teamAssign;
	}

	public Boolean getTeamStatus() {
		return teamStatus;
	}

	public void setTeamStatus(Boolean teamStatus) {
		this.teamStatus = teamStatus;
	}

	public Boolean getTeamPipeLine() {
		return teamPipeLine;
	}

	public void setTeamPipeLine(Boolean teamPipeLine) {
		this.teamPipeLine = teamPipeLine;
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
