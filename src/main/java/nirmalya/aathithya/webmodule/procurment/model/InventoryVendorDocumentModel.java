package nirmalya.aathithya.webmodule.procurment.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;


public class InventoryVendorDocumentModel {

	private String documnentName;
	private String desc;
	private String fileName;
	private String vendorRfqId;
	private List<String> documentFile = new ArrayList<String>();
	private String action;
	private String imageNameEdit;
	private String createdBy;
	private String fileNameLink;
	private List<DropDownModel> drop = new ArrayList<DropDownModel>();
	
	
	// Multiple document Ag Grid Add

		private String slno;
		private String isEditDoc;
		private String docName;
		private String docType;
		private String dociURL;
		private String docView;
	
	

	public InventoryVendorDocumentModel() {
		super();
	}

	public String getDocumnentName() {
		return documnentName;
	}

	public void setDocumnentName(String documnentName) {
		this.documnentName = documnentName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getVendorRfqId() {
		return vendorRfqId;
	}

	public void setVendorRfqId(String vendorRfqId) {
		this.vendorRfqId = vendorRfqId;
	}

	public List<String> getDocumentFile() {
		return documentFile;
	}

	public void setDocumentFile(List<String> documentFile) {
		this.documentFile = documentFile;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getImageNameEdit() {
		return imageNameEdit;
	}

	public void setImageNameEdit(String imageNameEdit) {
		this.imageNameEdit = imageNameEdit;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<DropDownModel> getDrop() {
		return drop;
	}

	public void setDrop(List<DropDownModel> drop) {
		this.drop = drop;
	}

	public String getFileNameLink() {
		return fileNameLink;
	}

	public void setFileNameLink(String fileNameLink) {
		this.fileNameLink = fileNameLink;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	// Multiple Doc Upload Ag Grid
	
	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}

	public String getIsEditDoc() {
		return isEditDoc;
	}

	public void setIsEditDoc(String isEditDoc) {
		this.isEditDoc = isEditDoc;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDociURL() {
		return dociURL;
	}

	public void setDociURL(String dociURL) {
		this.dociURL = dociURL;
	}

	public String getDocView() {
		return docView;
	}

	public void setDocView(String docView) {
		this.docView = docView;
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
