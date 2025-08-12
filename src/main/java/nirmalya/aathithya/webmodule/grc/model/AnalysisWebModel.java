package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AnalysisWebModel {
	private String identificationId;
	private String analysisId;
	private String projectId;
	private String projectName;
	private String identificationApplyDate;
	private String desc;
	private String riskId;
	private String riskName;
	private String category;
	private String source;
	private String remark;
	private String status;
	private String createdOn;
	private String createdBy;
	private String creationDate;
	private String OrganizationName;
	private String OrganizationDivision;
	private String riskAnalysis;
	private String impactAnalysis;
	private String owner;
	private String probability;
	private String impact;
	private Integer slNo;
	private String impactAnalysisId;
	private String analysisDate;
	
	
	public String getIdentificationId() {
		return identificationId;
	}


	public void setIdentificationId(String identificationId) {
		this.identificationId = identificationId;
	}


	public String getAnalysisId() {
		return analysisId;
	}


	public void setAnalysisId(String analysisId) {
		this.analysisId = analysisId;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getIdentificationApplyDate() {
		return identificationApplyDate;
	}


	public void setIdentificationApplyDate(String identificationApplyDate) {
		this.identificationApplyDate = identificationApplyDate;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getRiskId() {
		return riskId;
	}


	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}


	public String getRiskName() {
		return riskName;
	}


	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}


	public String getOrganizationName() {
		return OrganizationName;
	}


	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}


	public String getOrganizationDivision() {
		return OrganizationDivision;
	}


	public void setOrganizationDivision(String organizationDivision) {
		OrganizationDivision = organizationDivision;
	}


	public String getRiskAnalysis() {
		return riskAnalysis;
	}


	public void setRiskAnalysis(String riskAnalysis) {
		this.riskAnalysis = riskAnalysis;
	}


	public String getImpactAnalysis() {
		return impactAnalysis;
	}


	public void setImpactAnalysis(String impactAnalysis) {
		this.impactAnalysis = impactAnalysis;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getProbability() {
		return probability;
	}


	public void setProbability(String probability) {
		this.probability = probability;
	}


	public String getImpact() {
		return impact;
	}


	public void setImpact(String impact) {
		this.impact = impact;
	}


	public Integer getSlNo() {
		return slNo;
	}


	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}


	public String getImpactAnalysisId() {
		return impactAnalysisId;
	}


	public void setImpactAnalysisId(String impactAnalysisId) {
		this.impactAnalysisId = impactAnalysisId;
	}


	public String getAnalysisDate() {
		return analysisDate;
	}


	public void setAnalysisDate(String analysisDate) {
		this.analysisDate = analysisDate;
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
