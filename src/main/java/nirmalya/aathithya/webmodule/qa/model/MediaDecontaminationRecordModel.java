package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MediaDecontaminationRecordModel {
	 private String mediaId;
	 private String year;
	 private String refNo;
	 private String issueNo;
	 private String issuedDate;
	 
	 
	 private String num;
	 private String date;
	 private String startTimeOfAutoclave;
	 private String timeOfReached;
	 private String cycleOverTime;
	 private String sterilizationIndicatorStrip;
	 private String quantityOfMedia;
	 private String signatureOfMicrobiologist;
	 private String qaManager;
	
	 private String createdBy;
	 private String organization;
	 private String orgDivision;
	 private String fileUpload;
	 private String fileName;
	private String documentURL;
	private String documentFileBase;
		
	public MediaDecontaminationRecordModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTimeOfAutoclave() {
		return startTimeOfAutoclave;
	}

	public void setStartTimeOfAutoclave(String startTimeOfAutoclave) {
		this.startTimeOfAutoclave = startTimeOfAutoclave;
	}

	public String getTimeOfReached() {
		return timeOfReached;
	}

	public void setTimeOfReached(String timeOfReached) {
		this.timeOfReached = timeOfReached;
	}

	public String getCycleOverTime() {
		return cycleOverTime;
	}

	public void setCycleOverTime(String cycleOverTime) {
		this.cycleOverTime = cycleOverTime;
	}

	public String getSterilizationIndicatorStrip() {
		return sterilizationIndicatorStrip;
	}

	public void setSterilizationIndicatorStrip(String sterilizationIndicatorStrip) {
		this.sterilizationIndicatorStrip = sterilizationIndicatorStrip;
	}

	public String getQuantityOfMedia() {
		return quantityOfMedia;
	}

	public void setQuantityOfMedia(String quantityOfMedia) {
		this.quantityOfMedia = quantityOfMedia;
	}

	public String getSignatureOfMicrobiologist() {
		return signatureOfMicrobiologist;
	}

	public void setSignatureOfMicrobiologist(String signatureOfMicrobiologist) {
		this.signatureOfMicrobiologist = signatureOfMicrobiologist;
	}

	public String getQaManager() {
		return qaManager;
	}

	public void setQaManager(String qaManager) {
		this.qaManager = qaManager;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(String fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}

	public String getDocumentFileBase() {
		return documentFileBase;
	}

	public void setDocumentFileBase(String documentFileBase) {
		this.documentFileBase = documentFileBase;
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
