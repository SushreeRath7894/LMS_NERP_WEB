package nirmalya.aathithya.webmodule.training.model;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ManageTrainingDetailsModel {

	private String categoryId;
	private String categoryName;
	private String categoryDesc;
	private String categoryStatus;
	private String parentId;
	private String createdBy;
	private String catLevel;
	private String parentName;
	private String organization;
	private String orgDivision;
	private BigInteger nodeCount;
	private String textDescription;
	private String textHeading;
	private String timeSpent;
	private String textContent;
	private String materialId;
	private String materialStatus;
	private String studyMaterialType;
	private String contentStatus;
	private BigInteger allotSts;

	private List<ManageTrainingDocumentModel> documentList;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public String getCategoryStatus() {
		return categoryStatus;
	}

	public void setCategoryStatus(String categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCatLevel() {
		return catLevel;
	}

	public void setCatLevel(String catLevel) {
		this.catLevel = catLevel;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public BigInteger getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(BigInteger nodeCount) {
		this.nodeCount = nodeCount;
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

	public String getTextDescription() {
		return textDescription;
	}

	public void setTextDescription(String textDescription) {
		this.textDescription = textDescription;
	}

	public String getTextHeading() {
		return textHeading;
	}

	public void setTextHeading(String textHeading) {
		this.textHeading = textHeading;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(String timeSpent) {
		this.timeSpent = timeSpent;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(String materialStatus) {
		this.materialStatus = materialStatus;
	}

	public String getStudyMaterialType() {
		return studyMaterialType;
	}

	public void setStudyMaterialType(String studyMaterialType) {
		this.studyMaterialType = studyMaterialType;
	}

	public List<ManageTrainingDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<ManageTrainingDocumentModel> documentList) {
		this.documentList = documentList;
	}

	public String getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(String contentStatus) {
		this.contentStatus = contentStatus;
	}
	


	public BigInteger getAllotSts() {
		return allotSts;
	}

	public void setAllotSts(BigInteger allotSts) {
		this.allotSts = allotSts;
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
