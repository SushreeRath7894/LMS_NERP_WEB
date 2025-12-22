package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CrmDealModel {
	private String dealId;
	private String dealLeadSource;
	private String campaignSource;
	private String contactName;
	private String dealAccountName;
	private String dealAmount;
	private String dealClosingDate;
	private String dealName;
	private String dealOwner;
	private String dealStage;
	private String dealType;
	private String description;
	private String expectedRevenue;
	private String nextStep;
	private String probability;
	private String createdDate;
	private String createdTime;
	private String ownerImage;
	private String action;
	private String createdBy;

	private String stageId;
	private String stageDuration;
	private String updatedOn;
	private String updatedBy;
	private String preStageName;
	private String stageName;

	private String accountId;
	private String campaignSourceId;
	private String contactId;

	private String organization;
	private String orgDivision;
	private String stageSummary;
	private String previousStage;
	
	public String getPreviousStage() {
		return previousStage;
	}

	public void setPreviousStage(String previousStage) {
		this.previousStage = previousStage;
	}

	public String getStageSummary() {
		return stageSummary;
	}

	public void setStageSummary(String stageSummary) {
		this.stageSummary = stageSummary;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getOwnerImage() {
		return ownerImage;
	}

	public void setOwnerImage(String ownerImage) {
		this.ownerImage = ownerImage;
	}

	public String getPreStageName() {
		return preStageName;
	}

	public void setPreStageName(String preStageName) {
		this.preStageName = preStageName;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCampaignSourceId() {
		return campaignSourceId;
	}

	public void setCampaignSourceId(String campaignSourceId) {
		this.campaignSourceId = campaignSourceId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public String getStageDuration() {
		return stageDuration;
	}

	public void setStageDuration(String stageDuration) {
		this.stageDuration = stageDuration;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getDealLeadSource() {
		return dealLeadSource;
	}

	public void setDealLeadSource(String dealLeadSource) {
		this.dealLeadSource = dealLeadSource;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getCampaignSource() {
		return campaignSource;
	}

	public void setCampaignSource(String campaignSource) {
		this.campaignSource = campaignSource;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getDealAccountName() {
		return dealAccountName;
	}

	public void setDealAccountName(String dealAccountName) {
		this.dealAccountName = dealAccountName;
	}

	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getDealClosingDate() {
		return dealClosingDate;
	}

	public void setDealClosingDate(String dealClosingDate) {
		this.dealClosingDate = dealClosingDate;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealOwner() {
		return dealOwner;
	}

	public void setDealOwner(String dealOwner) {
		this.dealOwner = dealOwner;
	}

	public String getDealStage() {
		return dealStage;
	}

	public void setDealStage(String dealStage) {
		this.dealStage = dealStage;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(String expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
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
