package nirmalya.aathithya.webmodule.communication.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManageCommunicationModel {

	private String regNo;
	private String dispatchNo;
	private String dispatchDate;
	private String receiverName;
	private String receiverAddress;
	private String country;
	private String state;
	private String city;
	private String receiverPin;
	private String receiverSubject;
	private String modeOfDelivery;
	private String receiptNo;
	private String receiptDate;
	private String receiptAmount;
	private String receiverRemarks;
	private String date;
	private String dueDate;

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	private String senderName;
	private String senderAddress;
	private String senderPin;
	private String senderSubject;
	private String senderRefNo;
	private String dateRef;
	private String departmentId;
	private String senderSection;
	private String senderRemarks;
	private List<CommunicationDocumentModel> documentList;

	private String createdBy;
	private String organization;
	private String orgDivision;

	public ManageCommunicationModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public List<CommunicationDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CommunicationDocumentModel> documentList) {
		this.documentList = documentList;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getSenderPin() {
		return senderPin;
	}

	public void setSenderPin(String senderPin) {
		this.senderPin = senderPin;
	}

	public String getSenderSubject() {
		return senderSubject;
	}

	public void setSenderSubject(String senderSubject) {
		this.senderSubject = senderSubject;
	}

	public String getSenderRefNo() {
		return senderRefNo;
	}

	public void setSenderRefNo(String senderRefNo) {
		this.senderRefNo = senderRefNo;
	}

	public String getDateRef() {
		return dateRef;
	}

	public void setDateRef(String dateRef) {
		this.dateRef = dateRef;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getSenderSection() {
		return senderSection;
	}

	public void setSenderSection(String senderSection) {
		this.senderSection = senderSection;
	}

	public String getSenderRemarks() {
		return senderRemarks;
	}

	public void setSenderRemarks(String senderRemarks) {
		this.senderRemarks = senderRemarks;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getDispatchNo() {
		return dispatchNo;
	}

	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	public String getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(String dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getReceiverPin() {
		return receiverPin;
	}

	public void setReceiverPin(String receiverPin) {
		this.receiverPin = receiverPin;
	}

	public String getReceiverSubject() {
		return receiverSubject;
	}

	public void setReceiverSubject(String receiverSubject) {
		this.receiverSubject = receiverSubject;
	}

	public String getModeOfDelivery() {
		return modeOfDelivery;
	}

	public void setModeOfDelivery(String modeOfDelivery) {
		this.modeOfDelivery = modeOfDelivery;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(String receiverAmount) {
		this.receiptAmount = receiverAmount;
	}

	public String getReceiverRemarks() {
		return receiverRemarks;
	}

	public void setReceiverRemarks(String receiverRemarks) {
		this.receiverRemarks = receiverRemarks;
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
