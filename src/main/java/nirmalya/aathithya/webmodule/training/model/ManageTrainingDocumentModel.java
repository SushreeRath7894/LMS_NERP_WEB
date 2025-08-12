package nirmalya.aathithya.webmodule.training.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManageTrainingDocumentModel {

	private String documnentName;
	private String fileName;
	private String categoryId;
	private List<String> documentFile = new ArrayList<String>();
	private String action;
	private String imageNameEdit;
	private String createdBy;
	private String studyMaterialId;
	private String documentURL;

	public ManageTrainingDocumentModel() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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

	public String getStudyMaterialId() {
		return studyMaterialId;
	}

	public void setStudyMaterialId(String studyMaterialId) {
		this.studyMaterialId = studyMaterialId;
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
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
