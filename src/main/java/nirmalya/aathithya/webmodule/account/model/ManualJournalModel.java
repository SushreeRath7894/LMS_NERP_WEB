/**
 * 
 */
package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nirmalya Labs
 *
 */
public class ManualJournalModel {	
	private String journalVoucher;
	private String costCenter;
	private String dateJournal;	
	private String journalNo;
	private String referenceNo;
	private String currency;
	private String journalType;
	private String description;	
	
	private String recurringYesOrNo;	
	private String profileName;
	private String startsOn;
	private String endsOn;
	private String repeatEvery;
	private String neverExpire;
	
	private String fromAccountSubGroup;
	private String fromCustomerName;
	private Double fromAmount;
	private String toAccountSubGroup;
	private String toCustomerName;
	private Double toAmount;
	private Double totalAmount;
	private Boolean active;
	private String createdOn;
	private String createdBy;
	private Boolean transactionType;
	private Byte status;
	private String action;
	private Integer currentStageNo;
	private Integer approverStageNo;
	private Integer currentLevelNo;
	private Integer approverLevelNo;
	private Byte approveStatus;
	private String approveStatusName;
	private String customerId;
	private String customerName;
	
	
	
	
	public String getCustomerId() {
		return customerId;
	}



	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	

	public String getRecurringYesOrNo() {
		return recurringYesOrNo;
	}



	public void setRecurringYesOrNo(String recurringYesOrNo) {
		this.recurringYesOrNo = recurringYesOrNo;
	}



	public String getProfileName() {
		return profileName;
	}



	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}



	public String getStartsOn() {
		return startsOn;
	}



	public void setStartsOn(String startsOn) {
		this.startsOn = startsOn;
	}



	public String getEndsOn() {
		return endsOn;
	}



	public void setEndsOn(String endsOn) {
		this.endsOn = endsOn;
	}



	public String getRepeatEvery() {
		return repeatEvery;
	}



	public void setRepeatEvery(String repeatEvery) {
		this.repeatEvery = repeatEvery;
	}



	public String getNeverExpire() {
		return neverExpire;
	}



	public void setNeverExpire(String neverExpire) {
		this.neverExpire = neverExpire;
	}



	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public String getJournalVoucher() {
		return journalVoucher;
	}



	public void setJournalVoucher(String journalVoucher) {
		this.journalVoucher = journalVoucher;
	}



	public String getCostCenter() {
		return costCenter;
	}



	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}



	public String getDateJournal() {
		return dateJournal;
	}



	public void setDateJournal(String dateJournal) {
		this.dateJournal = dateJournal;
	}



	public String getJournalNo() {
		return journalNo;
	}



	public void setJournalNo(String journalNo) {
		this.journalNo = journalNo;
	}



	public String getReferenceNo() {
		return referenceNo;
	}



	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}



	public String getCurrency() {
		return currency;
	}



	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public String getJournalType() {
		return journalType;
	}



	public void setJournalType(String journalType) {
		this.journalType = journalType;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getFromAccountSubGroup() {
		return fromAccountSubGroup;
	}



	public void setFromAccountSubGroup(String fromAccountSubGroup) {
		this.fromAccountSubGroup = fromAccountSubGroup;
	}



	public String getFromCustomerName() {
		return fromCustomerName;
	}



	public void setFromCustomerName(String fromCustomerName) {
		this.fromCustomerName = fromCustomerName;
	}



	public Double getFromAmount() {
		return fromAmount;
	}



	public void setFromAmount(Double fromAmount) {
		this.fromAmount = fromAmount;
	}



	public String getToAccountSubGroup() {
		return toAccountSubGroup;
	}



	public void setToAccountSubGroup(String toAccountSubGroup) {
		this.toAccountSubGroup = toAccountSubGroup;
	}



	public String getToCustomerName() {
		return toCustomerName;
	}



	public void setToCustomerName(String toCustomerName) {
		this.toCustomerName = toCustomerName;
	}



	public Double getToAmount() {
		return toAmount;
	}



	public void setToAmount(Double toAmount) {
		this.toAmount = toAmount;
	}



	public Double getTotalAmount() {
		return totalAmount;
	}



	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}



	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
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



	public Boolean getTransactionType() {
		return transactionType;
	}



	public void setTransactionType(Boolean transactionType) {
		this.transactionType = transactionType;
	}



	public Byte getStatus() {
		return status;
	}



	public void setStatus(Byte status) {
		this.status = status;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}



	public Integer getCurrentStageNo() {
		return currentStageNo;
	}



	public void setCurrentStageNo(Integer currentStageNo) {
		this.currentStageNo = currentStageNo;
	}



	public Integer getApproverStageNo() {
		return approverStageNo;
	}



	public void setApproverStageNo(Integer approverStageNo) {
		this.approverStageNo = approverStageNo;
	}



	public Integer getCurrentLevelNo() {
		return currentLevelNo;
	}



	public void setCurrentLevelNo(Integer currentLevelNo) {
		this.currentLevelNo = currentLevelNo;
	}



	public Integer getApproverLevelNo() {
		return approverLevelNo;
	}



	public void setApproverLevelNo(Integer approverLevelNo) {
		this.approverLevelNo = approverLevelNo;
	}



	public Byte getApproveStatus() {
		return approveStatus;
	}



	public void setApproveStatus(Byte approveStatus) {
		this.approveStatus = approveStatus;
	}



	public String getApproveStatusName() {
		return approveStatusName;
	}



	public void setApproveStatusName(String approveStatusName) {
		this.approveStatusName = approveStatusName;
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
