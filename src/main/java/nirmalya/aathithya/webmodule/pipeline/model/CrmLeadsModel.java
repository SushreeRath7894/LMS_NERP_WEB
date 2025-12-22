package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

public class CrmLeadsModel {

	private String leadId;
	private String leadOwner;
	private String leadOwnerId;
	private String company;
	private String firstName;
	private String lastName;
	private String title;
	private String email;
	private String phone;
	private String fax;
	private String mobile;
	private String website;
	private String leadSource;
	private String leadStatus;
	private String industry;
	private String noOfEmp;
	private String annualRevenue;
	private String ratings;
	private String tags;
	private String skypeId;
	private String secondaryEmail;
	private String twitter;
	private String searchConvertedAccount;
	private String searchConvertedContact;
	private String searchConvertedDeal;
	private String searchCreatedTime;
	private String searchLastActivityTime;
	private String searchLeadConversionTime;
	private String searchLeadName;
	private String searchModifiedBy;
	private String searchModifiedTime;
	private String searchSalutation;
	//private String searchStreet;
	private String searchModifiedByTag;
	private String searchUnsubscribedMode;
	private String searchUnsubscribedTime;
	
	private String id;
	private String mailContent;
	
	private String country;
	private String countryId;
	private String states;
	private String stateId;
	private String city;
	private String addressStreet;
	private String zip;
	
	private String description;
	private String createdBy;
	private String imageName;
	private String taskSubject;
	private String taskDueDate;
	private String taskPriority;
	private String reminderYesOrNo;
	private String reminderDate;
	private String reminderTime;
	private String taskAlertBy;
	private String repeateYesOrNo;
	
	private String taskOwner;
	private String taskLead;
	private String taskContactName;
	private String contactId;
	private String taskAccountName;
	private String accountId;
	private String taskStatus;
	private String createdTime;
	
	private String fromEmail;
	private String toMail;
	private String mailSubject;
	private String docnoid;
	private String attachment;
	private String mailDescription;
	private String commentck;
	private String docName;
	
	private String macroName;
	private String macroDescription;
	private String emailType;
	private String fieldName;
	private String fieldValue;
	
	private String tagsName;
	private String isOverWrite;
	
	
	private String campaignType;
	private String campaignName;
	private String campaignStatus;
	private String startDate;
	private String endDate;
	private String campExpectRevenue;
	private String emailOpt;
	private String campaignId;
	private String campaignOwner;
	private String budgetedCost;
	private String actualCost;
	private String expectedResponse;
	private String numberSent;
	private String campdescription;
	
	private String leadName;
	private String ownerName;
	private String titleName;
	private String notedesc;
	private String noteDocName;
	private String noteDoc;
	private String leadImage;
	private String ownerImage;
	
	private String noteDocLink;
	private String leadImageLink;
	private String ownerImageLink;
	private String createdDate;
	private String updatedDate;
	
	private String accountName;
	private String contactName;
	
	private String dealCheck;
	private String dealAmount;
	private String dealName;
	private String dealClosingDate;
	private String dealStage;
	private String dealCampaignSource;
	private String dealContactRole;
	private String contactOwner;
	private String contactOwnerImage;
	private String contactOwnerLink;
	
	private String accountOwner;
	private String accountOwnerImage;
	private String accountOwnerImageLink;
	private String dealOwner;
	private String dealOwnerImage;
	private String dealOwnerImageLink;
	private String dealId;
	
	private String quoteName;
	private String quoteOwner;
	private String quoteOwnerImage;
	private String quoteOwnerImageLink;
	
	private String soName;
	private String soOwner;
	private String soOwnerImage;
	private String soOwnerImageLink;
	
	private String poName;
	private String poOwner;
	private String poOwnerImage;
	private String poOwnerImageLink;
	
	private String invName;
	private String invOwner;
	private String invOwnerImage;
	private String invOwnerImageLink;
	private String productId;
	private String productName;
	private String productStatus;
	private String pageType;
	private String probability;
	private String referenceContact;
	
	private String organization;
	private String orgDivision;
	
	private String noteId;
	
	// Pagination
	private String totalPageno;
	private String totalOrderById;
	
	private String createdOn;
	private String UpdatedOn;
	private String ConvertedToLead;
	
	private String ccMail;
	private String bccMail;
	private String customerId;
	
	private String productBrand;
	private String productCategories;
	
	private String modifyBy;
	private Boolean adminApprvStatus;
	private String projectName;
	private String statusUpdatedFrom;
	private String leadStatusId;
	private String titleId;
	private String convertSts;
	private String countryName;
	private String stateName;
	private String reason;
	private String leadPan;
	private String leadGst;

	private List<InventoryVendorDocumentModel> documentList;

	
	public String getLeadPan() {
		return leadPan;
	}

	public void setLeadPan(String leadPan) {
		this.leadPan = leadPan;
	}

	public String getLeadGst() {
		return leadGst;
	}

	public void setLeadGst(String leadGst) {
		this.leadGst = leadGst;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getConvertSts() {
		return convertSts;
	}

	public void setConvertSts(String convertSts) {
		this.convertSts = convertSts;
	}
	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getLeadStatusId() {
		return leadStatusId;
	}

	public void setLeadStatusId(String leadStatusId) {
		this.leadStatusId = leadStatusId;
	}

	public String getStatusUpdatedFrom() {
		return statusUpdatedFrom;
	}

	public void setStatusUpdatedFrom(String statusUpdatedFrom) {
		this.statusUpdatedFrom = statusUpdatedFrom;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Boolean getAdminApprvStatus() {
		return adminApprvStatus;
	}

	public void setAdminApprvStatus(Boolean adminApprvStatus) {
		this.adminApprvStatus = adminApprvStatus;
	}
	
	
	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getProductCategories() {
		return productCategories;
	}

	public void setProductCategories(String productCategories) {
		this.productCategories = productCategories;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCcMail() {
		return ccMail;
	}

	public void setCcMail(String ccMail) {
		this.ccMail = ccMail;
	}

	public String getBccMail() {
		return bccMail;
	}

	public void setBccMail(String bccMail) {
		this.bccMail = bccMail;
	}

	public CrmLeadsModel(Object leadId,Object leadName,Object phone,Object email,Object company,Object leadSource,Object leadOwner,
			Object createdDate,Object firstName,Object lastName,Object title,Object mobile,Object fax,Object website,Object leadStatus,
			Object industry,Object noOfEmp,Object annualRevenue,Object ratings,Object createdOn,Object UpdatedOn,Object addressStreet,
			Object city,Object states,Object country,Object description,Object skypeId,Object emailOpt,Object secondaryEmail,Object twitter,
			Object ConvertedToLead,String organization,String orgDivision,String createdBy) {
		this.leadId = (String) leadId;
		this.leadName = (String)  leadName;
		this.phone = (String)  phone;
		this.email = (String)  email;
		this.company = (String) company;
		this.leadSource = (String) leadSource;
		this.leadOwner = (String) leadOwner;
		this.createdDate = (String) createdDate;
		this.firstName = (String) firstName;
		this.lastName = (String) lastName;
		this.title = (String) title;
		this.mobile = (String) mobile;
		this.fax = (String) fax;
		this.website = (String) website;
		this.leadStatus = (String) leadStatus;
		this.industry = (String) industry;
		this.noOfEmp = (String) noOfEmp;
		this.annualRevenue = (String) annualRevenue;
		this.ratings = (String) ratings;
		this.createdOn = (String) createdOn;
		this.UpdatedOn = (String) UpdatedOn;
		this.addressStreet = (String) addressStreet;
		this.city = (String) city;
		this.states = (String) states;
		this.country = (String) country;
		this.description = (String) description;
		this.skypeId = (String) skypeId;
		this.emailOpt = (String) emailOpt;
		this.secondaryEmail = (String) secondaryEmail;
		this.twitter = (String) twitter;
		this.ConvertedToLead = (String) ConvertedToLead;
		this.organization = (String) organization;
		this.orgDivision = (String) orgDivision;
		this.createdBy = (String) createdBy;
		 
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

	public String getTotalPageno() {
		return totalPageno;
	}

	public void setTotalPageno(String totalPageno) {
		this.totalPageno = totalPageno;
	}

	public String getTotalOrderById() {
		return totalOrderById;
	}

	public void setTotalOrderById(String totalOrderById) {
		this.totalOrderById = totalOrderById;
	}

	//List<InventoryVendorDocumentModel> documentList;
	
	
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getReferenceContact() {
		return referenceContact;
	}

	public void setReferenceContact(String referenceContact) {
		this.referenceContact = referenceContact;
	}

	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getQuoteName() {
		return quoteName;
	}

	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}

	public String getQuoteOwner() {
		return quoteOwner;
	}

	public void setQuoteOwner(String quoteOwner) {
		this.quoteOwner = quoteOwner;
	}

	public String getQuoteOwnerImage() {
		return quoteOwnerImage;
	}

	public void setQuoteOwnerImage(String quoteOwnerImage) {
		this.quoteOwnerImage = quoteOwnerImage;
	}

	public String getQuoteOwnerImageLink() {
		return quoteOwnerImageLink;
	}

	public void setQuoteOwnerImageLink(String quoteOwnerImageLink) {
		this.quoteOwnerImageLink = quoteOwnerImageLink;
	}

	public String getSoName() {
		return soName;
	}

	public void setSoName(String soName) {
		this.soName = soName;
	}

	public String getSoOwner() {
		return soOwner;
	}

	public void setSoOwner(String soOwner) {
		this.soOwner = soOwner;
	}

	public String getSoOwnerImage() {
		return soOwnerImage;
	}

	public void setSoOwnerImage(String soOwnerImage) {
		this.soOwnerImage = soOwnerImage;
	}

	public String getSoOwnerImageLink() {
		return soOwnerImageLink;
	}

	public void setSoOwnerImageLink(String soOwnerImageLink) {
		this.soOwnerImageLink = soOwnerImageLink;
	}

	public String getPoName() {
		return poName;
	}

	public void setPoName(String poName) {
		this.poName = poName;
	}

	public String getPoOwner() {
		return poOwner;
	}

	public void setPoOwner(String poOwner) {
		this.poOwner = poOwner;
	}

	public String getPoOwnerImage() {
		return poOwnerImage;
	}

	public void setPoOwnerImage(String poOwnerImage) {
		this.poOwnerImage = poOwnerImage;
	}

	public String getPoOwnerImageLink() {
		return poOwnerImageLink;
	}

	public void setPoOwnerImageLink(String poOwnerImageLink) {
		this.poOwnerImageLink = poOwnerImageLink;
	}

	public String getInvName() {
		return invName;
	}

	public void setInvName(String invName) {
		this.invName = invName;
	}

	public String getInvOwner() {
		return invOwner;
	}

	public void setInvOwner(String invOwner) {
		this.invOwner = invOwner;
	}

	public String getInvOwnerImage() {
		return invOwnerImage;
	}

	public void setInvOwnerImage(String invOwnerImage) {
		this.invOwnerImage = invOwnerImage;
	}

	public String getInvOwnerImageLink() {
		return invOwnerImageLink;
	}

	public void setInvOwnerImageLink(String invOwnerImageLink) {
		this.invOwnerImageLink = invOwnerImageLink;
	}

	public String getAccountOwnerImageLink() {
		return accountOwnerImageLink;
	}

	public void setAccountOwnerImageLink(String accountOwnerImageLink) {
		this.accountOwnerImageLink = accountOwnerImageLink;
	}

	public String getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}

	public String getAccountOwnerImage() {
		return accountOwnerImage;
	}

	public void setAccountOwnerImage(String accountOwnerImage) {
		this.accountOwnerImage = accountOwnerImage;
	}

	public String getDealOwner() {
		return dealOwner;
	}

	public void setDealOwner(String dealOwner) {
		this.dealOwner = dealOwner;
	}

	public String getDealOwnerImage() {
		return dealOwnerImage;
	}

	public void setDealOwnerImage(String dealOwnerImage) {
		this.dealOwnerImage = dealOwnerImage;
	}

	public String getDealOwnerImageLink() {
		return dealOwnerImageLink;
	}

	public void setDealOwnerImageLink(String dealOwnerImageLink) {
		this.dealOwnerImageLink = dealOwnerImageLink;
	}

	public String getContactOwner() {
		return contactOwner;
	}

	public void setContactOwner(String contactOwner) {
		this.contactOwner = contactOwner;
	}

	public String getContactOwnerLink() {
		return contactOwnerLink;
	}

	public void setContactOwnerLink(String contactOwnerLink) {
		this.contactOwnerLink = contactOwnerLink;
	}

	public String getContactOwnerImage() {
		return contactOwnerImage;
	}

	public void setContactOwnerImage(String contactOwnerImage) {
		this.contactOwnerImage = contactOwnerImage;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getCommentck() {
		return commentck;
	}

	public void setCommentck(String commentck) {
		this.commentck = commentck;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public String getTaskLead() {
		return taskLead;
	}

	public void setTaskLead(String taskLead) {
		this.taskLead = taskLead;
	}

	public String getTaskContactName() {
		return taskContactName;
	}

	public void setTaskContactName(String taskContactName) {
		this.taskContactName = taskContactName;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getTaskAccountName() {
		return taskAccountName;
	}

	public void setTaskAccountName(String taskAccountName) {
		this.taskAccountName = taskAccountName;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getDealCheck() {
		return dealCheck;
	}

	public void setDealCheck(String dealCheck) {
		this.dealCheck = dealCheck;
	}

	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealClosingDate() {
		return dealClosingDate;
	}

	public void setDealClosingDate(String dealClosingDate) {
		this.dealClosingDate = dealClosingDate;
	}

	public String getDealStage() {
		return dealStage;
	}

	public void setDealStage(String dealStage) {
		this.dealStage = dealStage;
	}

	public String getDealCampaignSource() {
		return dealCampaignSource;
	}

	public void setDealCampaignSource(String dealCampaignSource) {
		this.dealCampaignSource = dealCampaignSource;
	}

	public String getDealContactRole() {
		return dealContactRole;
	}

	public void setDealContactRole(String dealContactRole) {
		this.dealContactRole = dealContactRole;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getLeadOwnerId() {
		return leadOwnerId;
	}

	public void setLeadOwnerId(String leadOwnerId) {
		this.leadOwnerId = leadOwnerId;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getNoteDocLink() {
		return noteDocLink;
	}

	public void setNoteDocLink(String noteDocLink) {
		this.noteDocLink = noteDocLink;
	}

	public String getLeadImageLink() {
		return leadImageLink;
	}

	public void setLeadImageLink(String leadImageLink) {
		this.leadImageLink = leadImageLink;
	}

	public String getOwnerImageLink() {
		return ownerImageLink;
	}

	public void setOwnerImageLink(String ownerImageLink) {
		this.ownerImageLink = ownerImageLink;
	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getNotedesc() {
		return notedesc;
	}

	public void setNotedesc(String notedesc) {
		this.notedesc = notedesc;
	}

	public String getNoteDocName() {
		return noteDocName;
	}

	public void setNoteDocName(String noteDocName) {
		this.noteDocName = noteDocName;
	}

	public String getNoteDoc() {
		return noteDoc;
	}

	public void setNoteDoc(String noteDoc) {
		this.noteDoc = noteDoc;
	}

	public String getLeadImage() {
		return leadImage;
	}

	public void setLeadImage(String leadImage) {
		this.leadImage = leadImage;
	}

	public String getOwnerImage() {
		return ownerImage;
	}

	public void setOwnerImage(String ownerImage) {
		this.ownerImage = ownerImage;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getEmailOpt() {
		return emailOpt;
	}

	public void setEmailOpt(String emailOpt) {
		this.emailOpt = emailOpt;
	}

	public String getCampaignType() {
		return campaignType;
	}

	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getCampaignStatus() {
		return campaignStatus;
	}

	public void setCampaignStatus(String campaignStatus) {
		this.campaignStatus = campaignStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCampExpectRevenue() {
		return campExpectRevenue;
	}

	public void setCampExpectRevenue(String campExpectRevenue) {
		this.campExpectRevenue = campExpectRevenue;
	}

	public String getTagsName() {
		return tagsName;
	}

	public void setTagsName(String tagsName) {
		this.tagsName = tagsName;
	}

	public String getIsOverWrite() {
		return isOverWrite;
	}

	public void setIsOverWrite(String isOverWrite) {
		this.isOverWrite = isOverWrite;
	}

	public String getMacroName() {
		return macroName;
	}

	public void setMacroName(String macroName) {
		this.macroName = macroName;
	}

	public String getMacroDescription() {
		return macroDescription;
	}

	public void setMacroDescription(String macroDescription) {
		this.macroDescription = macroDescription;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getMailDescription() {
		return mailDescription;
	}

	public void setMailDescription(String mailDescription) {
		this.mailDescription = mailDescription;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getToMail() {
		return toMail;
	}

	public void setToMail(String toMail) {
		this.toMail = toMail;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getDocnoid() {
		return docnoid;
	}

	public void setDocnoid(String docnoid) {
		this.docnoid = docnoid;
	}

	
	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getTaskDueDate() {
		return taskDueDate;
	}

	public void setTaskDueDate(String taskDueDate) {
		this.taskDueDate = taskDueDate;
	}

	public String getTaskSubject() {
		return taskSubject;
	}

	public void setTaskSubject(String taskSubject) {
		this.taskSubject = taskSubject;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}

	public String getReminderYesOrNo() {
		return reminderYesOrNo;
	}

	public void setReminderYesOrNo(String reminderYesOrNo) {
		this.reminderYesOrNo = reminderYesOrNo;
	}

	public String getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(String reminderDate) {
		this.reminderDate = reminderDate;
	}

	public String getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(String reminderTime) {
		this.reminderTime = reminderTime;
	}

	public String getTaskAlertBy() {
		return taskAlertBy;
	}

	public void setTaskAlertBy(String taskAlertBy) {
		this.taskAlertBy = taskAlertBy;
	}

	public String getRepeateYesOrNo() {
		return repeateYesOrNo;
	}

	public void setRepeateYesOrNo(String repeateYesOrNo) {
		this.repeateYesOrNo = repeateYesOrNo;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public CrmLeadsModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public String getLeadOwner() {
		return leadOwner;
	}
	public void setLeadOwner(String leadOwner) {
		this.leadOwner = leadOwner;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getLeadSource() {
		return leadSource;
	}
	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}
	public String getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getNoOfEmp() {
		return noOfEmp;
	}
	public void setNoOfEmp(String noOfEmp) {
		this.noOfEmp = noOfEmp;
	}
	public String getAnnualRevenue() {
		return annualRevenue;
	}
	public void setAnnualRevenue(String annualRevenue) {
		this.annualRevenue = annualRevenue;
	}
	public String getRatings() {
		return ratings;
	}
	public void setRatings(String ratings) {
		this.ratings = ratings;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getSkypeId() {
		return skypeId;
	}
	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}
	public String getSecondaryEmail() {
		return secondaryEmail;
	}
	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	
	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddressStreet() {
		return addressStreet;
	}
	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignOwner() {
		return campaignOwner;
	}

	public void setCampaignOwner(String campaignOwner) {
		this.campaignOwner = campaignOwner;
	}

	public String getBudgetedCost() {
		return budgetedCost;
	}

	public void setBudgetedCost(String budgetedCost) {
		this.budgetedCost = budgetedCost;
	}

	public String getActualCost() {
		return actualCost;
	}

	public void setActualCost(String actualCost) {
		this.actualCost = actualCost;
	}

	public String getExpectedResponse() {
		return expectedResponse;
	}

	public void setExpectedResponse(String expectedResponse) {
		this.expectedResponse = expectedResponse;
	}

	public String getNumberSent() {
		return numberSent;
	}

	public void setNumberSent(String numberSent) {
		this.numberSent = numberSent;
	}

	public String getCampdescription() {
		return campdescription;
	}

	public void setCampdescription(String campdescription) {
		this.campdescription = campdescription;
	}

	public String getSearchConvertedAccount() {
		return searchConvertedAccount;
	}

	public void setSearchConvertedAccount(String searchConvertedAccount) {
		this.searchConvertedAccount = searchConvertedAccount;
	}

	public String getSearchConvertedContact() {
		return searchConvertedContact;
	}

	public void setSearchConvertedContact(String searchConvertedContact) {
		this.searchConvertedContact = searchConvertedContact;
	}

	public String getSearchConvertedDeal() {
		return searchConvertedDeal;
	}

	public void setSearchConvertedDeal(String searchConvertedDeal) {
		this.searchConvertedDeal = searchConvertedDeal;
	}

	public String getSearchCreatedTime() {
		return searchCreatedTime;
	}

	public void setSearchCreatedTime(String searchCreatedTime) {
		this.searchCreatedTime = searchCreatedTime;
	}

	public String getSearchLastActivityTime() {
		return searchLastActivityTime;
	}

	public void setSearchLastActivityTime(String searchLastActivityTime) {
		this.searchLastActivityTime = searchLastActivityTime;
	}

	public String getSearchLeadConversionTime() {
		return searchLeadConversionTime;
	}

	public void setSearchLeadConversionTime(String searchLeadConversionTime) {
		this.searchLeadConversionTime = searchLeadConversionTime;
	}

	public String getSearchLeadName() {
		return searchLeadName;
	}

	public void setSearchLeadName(String searchLeadName) {
		this.searchLeadName = searchLeadName;
	}

	public String getSearchModifiedBy() {
		return searchModifiedBy;
	}

	public void setSearchModifiedBy(String searchModifiedBy) {
		this.searchModifiedBy = searchModifiedBy;
	}

	public String getSearchModifiedTime() {
		return searchModifiedTime;
	}

	public void setSearchModifiedTime(String searchModifiedTime) {
		this.searchModifiedTime = searchModifiedTime;
	}

	public String getSearchSalutation() {
		return searchSalutation;
	}

	public void setSearchSalutation(String searchSalutation) {
		this.searchSalutation = searchSalutation;
	}

	public String getSearchModifiedByTag() {
		return searchModifiedByTag;
	}

	public void setSearchModifiedByTag(String searchModifiedByTag) {
		this.searchModifiedByTag = searchModifiedByTag;
	}

	public String getSearchUnsubscribedMode() {
		return searchUnsubscribedMode;
	}

	public void setSearchUnsubscribedMode(String searchUnsubscribedMode) {
		this.searchUnsubscribedMode = searchUnsubscribedMode;
	}

	public String getSearchUnsubscribedTime() {
		return searchUnsubscribedTime;
	}

	public void setSearchUnsubscribedTime(String searchUnsubscribedTime) {
		this.searchUnsubscribedTime = searchUnsubscribedTime;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	
	
	public List<InventoryVendorDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<InventoryVendorDocumentModel> documentList) {
		this.documentList = documentList;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedOn() {
		return UpdatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		UpdatedOn = updatedOn;
	}

	public String getConvertedToLead() {
		return ConvertedToLead;
	}

	public void setConvertedToLead(String convertedToLead) {
		ConvertedToLead = convertedToLead;
	}
	

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
