package nirmalya.aathithya.webmodule.employee.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReimbrusementOtherModel {
	private String otherReimbrusementId;
	private String date;
	private String travellingPurpose;
	private String typeReimbrusement;
	private String expenseDate;
	private String descExpense;
	private Double expenseAmount;
	
	private String docName;
	private String referenceNo;
	private String createdBy;
	private String updatedBy;
	private String createdOn;
	private String updatedOn;
	
	
	private String approveStatus;
	private String approvedDate;
	private String approvedBy;
	private String rejectDate;
	private String rejectedBy;
	private String approveComment;
	
	public ReimbrusementOtherModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOtherReimbrusementId() {
		return otherReimbrusementId;
	}
	public void setOtherReimbrusementId(String otherReimbrusementId) {
		this.otherReimbrusementId = otherReimbrusementId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTravellingPurpose() {
		return travellingPurpose;
	}
	public void setTravellingPurpose(String travellingPurpose) {
		this.travellingPurpose = travellingPurpose;
	}
	public String getTypeReimbrusement() {
		return typeReimbrusement;
	}
	public void setTypeReimbrusement(String typeReimbrusement) {
		this.typeReimbrusement = typeReimbrusement;
	}
	public String getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(String expenseDate) {
		this.expenseDate = expenseDate;
	}
	public String getDescExpense() {
		return descExpense;
	}
	public void setDescExpense(String descExpense) {
		this.descExpense = descExpense;
	}
	
	
	public Double getExpenseAmount() {
		return expenseAmount;
	}
	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getRejectDate() {
		return rejectDate;
	}
	public void setRejectDate(String rejectDate) {
		this.rejectDate = rejectDate;
	}
	public String getRejectedBy() {
		return rejectedBy;
	}
	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
	public String getApproveComment() {
		return approveComment;
	}
	public void setApproveComment(String approveComment) {
		this.approveComment = approveComment;
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
