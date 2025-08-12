package nirmalya.aathithya.webmodule.employee.model;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReimbursementModel {

	private String reimbursementReqId;
	private String requisitionId;
	private String empName;
	private String empID1;
	private String reqType;
	private String reqTypeName;
	private String applyDate;
	private String travellingPurpose;
	private String expenseDate1;
	private String descExpense;
	private String eAmount;
	private String docName1;
	private String referenceNo1;
	
	
	private String empId;
	private String placeName;
	private String fromDate;
	private String toDate;
	private String purpose;
	private String advanceReq;
	private String advanceAmount;
	private String aamttPaid;
	private String approveStatus;
	private String slNo;
	private String reimTypeId;
	private String reimTypeName;
	private String reimPolicyId;
	private String reimPolicyName;
	private String expenseDate;
	private String expenseDesc;
	private String expenseAmount;
	private String referenceNo;
	private String documentName;
	private String docName;
	private String approvedDate;
	private String approvedBy;
	private String rejectDate;
	private String rejectedBy;
	private String approveComment;
	private String organization;
	private String orgDivision;
	private MultipartFile mulFile;
    private String docType;
    private String extension;
    private String userid;
    private String filetype;
	
	//private List<ReimbursementDocumentModel> documentList;
	
	private String createdBy;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String total;
	private String module;
	private String component;
	private String subcomponent;
	public ReimbursementModel() {
		super();
	}
	
	public String getEmpID1() {
		return empID1;
	}


	public void setEmpID1(String empID1) {
		this.empID1 = empID1;
	}


	public String getEmpId() {
		return empId;
	}


	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getReqType() {
		return reqType;
	}


	public void setReqType(String reqType) {
		this.reqType = reqType;
	}


	public String getReqTypeName() {
		return reqTypeName;
	}

	public void setReqTypeName(String reqTypeName) {
		this.reqTypeName = reqTypeName;
	}

	public String getApplyDate() {
		return applyDate;
	}


	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}


	public String getTravellingPurpose() {
		return travellingPurpose;
	}


	public void setTravellingPurpose(String travellingPurpose) {
		this.travellingPurpose = travellingPurpose;
	}


	public String getExpenseDate1() {
		return expenseDate1;
	}


	public void setExpenseDate1(String expenseDate1) {
		this.expenseDate1 = expenseDate1;
	}


	public String getDescExpense() {
		return descExpense;
	}


	public void setDescExpense(String descExpense) {
		this.descExpense = descExpense;
	}


	public String geteAmount() {
		return eAmount;
	}


	public void seteAmount(String eAmount) {
		this.eAmount = eAmount;
	}


	public String getDocName1() {
		return docName1;
	}


	public void setDocName1(String docName1) {
		this.docName1 = docName1;
	}


	public String getReferenceNo1() {
		return referenceNo1;
	}


	public void setReferenceNo1(String referenceNo1) {
		this.referenceNo1 = referenceNo1;
	}


	public String getReimbursementReqId() {
		return reimbursementReqId;
	}

	public String getRequisitionId() {
		return requisitionId;
	}


	public void setRequisitionId(String requisitionId) {
		this.requisitionId = requisitionId;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getSlNo() {
		return slNo;
	}


	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}


	public void setReimbursementReqId(String reimbursementReqId) {
		this.reimbursementReqId = reimbursementReqId;
	}

	public String getPlaceName() {
		return placeName;
	}


	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}


	public String getFromDate() {
		return fromDate;
	}


	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
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


	public String getToDate() {
		return toDate;
	}


	public void setToDate(String toDate) {
		this.toDate = toDate;
	}


	public String getPurpose() {
		return purpose;
	}


	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	public String getAdvanceReq() {
		return advanceReq;
	}


	public void setAdvanceReq(String advanceReq) {
		this.advanceReq = advanceReq;
	}


	
	public String getAdvanceAmount() {
		return advanceAmount;
	}


	public void setAdvanceAmount(String advanceAmount) {
		this.advanceAmount = advanceAmount;
	}


	public String getApproveStatus() {
		return approveStatus;
	}


	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	
	


	public String getDocName() {
		return docName;
	}


	public void setDocName(String docName) {
		this.docName = docName;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getUpdatedOn() {
		return updatedOn;
	}


	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}


	
	public String getReimTypeId() {
		return reimTypeId;
	}


	public void setReimTypeId(String reimTypeId) {
		this.reimTypeId = reimTypeId;
	}


	public String getReimTypeName() {
		return reimTypeName;
	}


	public void setReimTypeName(String reimTypeName) {
		this.reimTypeName = reimTypeName;
	}


	public String getReimPolicyId() {
		return reimPolicyId;
	}


	public void setReimPolicyId(String reimPolicyId) {
		this.reimPolicyId = reimPolicyId;
	}


	public String getReimPolicyName() {
		return reimPolicyName;
	}


	public void setReimPolicyName(String reimPolicyName) {
		this.reimPolicyName = reimPolicyName;
	}


	public String getExpenseDate() {
		return expenseDate;
	}


	public void setExpenseDate(String expenseDate) {
		this.expenseDate = expenseDate;
	}


	public String getExpenseDesc() {
		return expenseDesc;
	}


	public void setExpenseDesc(String expenseDesc) {
		this.expenseDesc = expenseDesc;
	}


	public String getExpenseAmount() {
		return expenseAmount;
	}


	public void setExpenseAmount(String expenseAmount) {
		this.expenseAmount = expenseAmount;
	}


	public String getDocumentName() {
		return documentName;
	}


	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}


	public String getReferenceNo() {
		return referenceNo;
	}


	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}


	public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
	}


	public String getModule() {
		return module;
	}


	public void setModule(String module) {
		this.module = module;
	}


	public String getComponent() {
		return component;
	}


	public void setComponent(String component) {
		this.component = component;
	}


	public String getSubcomponent() {
		return subcomponent;
	}


	public void setSubcomponent(String subcomponent) {
		this.subcomponent = subcomponent;
	}


	public String getAamttPaid() {
		return aamttPaid;
	}


	public void setAamttPaid(String aamttPaid) {
		this.aamttPaid = aamttPaid;
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

	public MultipartFile getMulFile() {
		return mulFile;
	}

	public void setMulFile(MultipartFile mulFile) {
		this.mulFile = mulFile;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
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
