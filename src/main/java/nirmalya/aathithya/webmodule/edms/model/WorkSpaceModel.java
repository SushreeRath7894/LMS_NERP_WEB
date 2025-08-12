package nirmalya.aathithya.webmodule.edms.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WorkSpaceModel {
	private String workspaceId;
	private String parentFolderName;
	private String newFolderName;
	private String accessType;
	private String owner;
	private String defaultTags;
	private String description; 
	private String folderPath;
	private String parentFolderId;
	private String orgName;
	private String orgDivision;
	private String accessTypeName;
	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	private List<workSpaceChildModel> accessControl;
	private String OrganizationName;
	private String OrganizationDivision;
	private String createdBy;
	private String createdOn;
	private String parentId;
	private String parentFolderPath;
	
	

	public WorkSpaceModel() {
		super();
		// TODO Auto-generated constructor stub
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

	public List<workSpaceChildModel> getAccessControl() {
		return accessControl;
	}

	public void setAccessControl(List<workSpaceChildModel> accessControl) {
		this.accessControl = accessControl;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getParentFolderName() {
		return parentFolderName;
	}

	public void setParentFolderName(String parentFolderName) {
		this.parentFolderName = parentFolderName;
	}

	public String getNewFolderName() {
		return newFolderName;
	}

	public void setNewFolderName(String newFolderName) {
		this.newFolderName = newFolderName;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDefaultTags() {
		return defaultTags;
	}

	public void setDefaultTags(String defaultTags) {
		this.defaultTags = defaultTags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	

	public String getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(String parentFolderId) {
		this.parentFolderId = parentFolderId;
	}
	

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
	}
	

	public String getAccessTypeName() {
		return accessTypeName;
	}

	public void setAccessTypeName(String accessTypeName) {
		this.accessTypeName = accessTypeName;
	}
	
	

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	

	public String getParentFolderPath() {
		return parentFolderPath;
	}

	public void setParentFolderPath(String parentFolderPath) {
		this.parentFolderPath = parentFolderPath;
	}

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
