package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PipelineCampaignModel {

	private String campaign;
	private String campaignName;
	private String campaignResponsible;
	private String campaignTag;
	private String createdBy;
	private String action;

	public PipelineCampaignModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getCampaignResponsible() {
		return campaignResponsible;
	}

	public void setCampaignResponsible(String campaignResponsible) {
		this.campaignResponsible = campaignResponsible;
	}

	public String getCampaignTag() {
		return campaignTag;
	}

	public void setCampaignTag(String campaignTag) {
		this.campaignTag = campaignTag;
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
