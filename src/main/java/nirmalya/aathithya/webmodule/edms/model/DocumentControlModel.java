package nirmalya.aathithya.webmodule.edms.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

public class DocumentControlModel {

    private String docid;
    private String documentName;
    private String fileName; 
    private String imageNameEdit;
    private String description;
    private String tags;
    private String docControl;
    private List<String> documentFile = new ArrayList<String>();
    private String folderName;
    private String workspacePath;
    private String date;
    private List<DocumentControlModel> documentList;
    private List<String> documentFileAccessImage = new ArrayList<String>();
    private String documentUrl;
    private String linkName;
    private String linkUrl;
    private String version;
    private String organization;
    private String orgDivision;
    private String createdBy;
    private String accessBy;
    private String fileNameAccessImage;
    private String imageNameEditAccessImage;
    
    private String empId;
    private String empName;
    private String empEmail;  
    private String filePath;
    private String groupId;
    private String subject;
    private String originalFileName;
    private String folderPathNameId;
    private String originalDocFile;
    private String originalFileNameAccessImage;
    private String tagFolder;
    private String status;
    private String id;
    private String docUniqueId;
    private String folderId;
    private String folderPath;
    
    List<InventoryVendorDocumentModel> documentList1;
    private List<DocumentControlModel> accessEmployeeList;

    public DocumentControlModel() {
		super();
		// TODO Auto-generated constructor stub
	}

     public String getDocid() {
        return docid;
    }
    public void setDocid(String docid) {
        this.docid = docid;
    }
    public String getDocumentName() {
        return documentName;
    }
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getImageNameEdit() {
        return imageNameEdit;
    }
    public void setImageNameEdit(String imageNameEdit) {
        this.imageNameEdit = imageNameEdit;
    }

     public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    
    public List<String> getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(List<String> documentFile) {
        this.documentFile = documentFile;
    }
    

    public String getDocControl() {
		return docControl;
	}

	public void setDocControl(String docControl) {
		this.docControl = docControl;
	}
	

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getWorkspacePath() {
		return workspacePath;
	}

	public void setWorkspacePath(String workspacePath) {
		this.workspacePath = workspacePath;
	}
	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public List<DocumentControlModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentControlModel> documentList) {
		this.documentList = documentList;
	}

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	

	public String getAccessBy() {
		return accessBy;
	}

	public void setAccessBy(String accessBy) {
		this.accessBy = accessBy;
	}
	
	

	public String getFileNameAccessImage() {
		return fileNameAccessImage;
	}

	public void setFileNameAccessImage(String fileNameAccessImage) {
		this.fileNameAccessImage = fileNameAccessImage;
	}
	
	

	public String getImageNameEditAccessImage() {
		return imageNameEditAccessImage;
	}

	public void setImageNameEditAccessImage(String imageNameEditAccessImage) {
		this.imageNameEditAccessImage = imageNameEditAccessImage;
	}
	
	


	public List<String> getDocumentFileAccessImage() {
		return documentFileAccessImage;
	}

	public void setDocumentFileAccessImage(List<String> documentFileAccessImage) {
		this.documentFileAccessImage = documentFileAccessImage;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<InventoryVendorDocumentModel> getDocumentList1() {
		return documentList1;
	}

	public void setDocumentList1(List<InventoryVendorDocumentModel> documentList1) {
		this.documentList1 = documentList1;
	}
	
	

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	
	

	public String getFolderPathNameId() {
		return folderPathNameId;
	}

	public void setFolderPathNameId(String folderPathNameId) {
		this.folderPathNameId = folderPathNameId;
	}
	
	

	public String getOriginalDocFile() {
		return originalDocFile;
	}

	public void setOriginalDocFile(String originalDocFile) {
		this.originalDocFile = originalDocFile;
	}
	
	

	public String getOriginalFileNameAccessImage() {
		return originalFileNameAccessImage;
	}

	public void setOriginalFileNameAccessImage(String originalFileNameAccessImage) {
		this.originalFileNameAccessImage = originalFileNameAccessImage;
	}
	
	

	public String getTagFolder() {
		return tagFolder;
	}

	public void setTagFolder(String tagFolder) {
		this.tagFolder = tagFolder;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

	public List<DocumentControlModel> getAccessEmployeeList() {
		return accessEmployeeList;
	}

	public void setAccessEmployeeList(List<DocumentControlModel> accessEmployeeList) {
		this.accessEmployeeList = accessEmployeeList;
	}
	
	

	public String getDocUniqueId() {
		return docUniqueId;
	}

	public void setDocUniqueId(String docUniqueId) {
		this.docUniqueId = docUniqueId;
	}
	
	

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
	

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
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
