package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountCustomerOrderModel {

	private String custOrderId;
	private String invoiceId;
	private String custId;
	private String custName;
	private String custMobile;
	private String custAddress;
	private String custEmail;
	private String dealerId;
	private String dealerName;
	private String custDesc;
	private String status;
	private String itemId;
	private String itemName;
	private String itemQty;
	private String itemUnitPrice;
	private String itemDiscount;
	private String itemGst;
	private String itemLineTotal;
	private String itemTotalAmount;
	private String totalItem;

	private String paymentStatus;

	private String promoCode;
	private Double adjustment;
	private String shippingCharges;
	private String createdBy;

	private Integer slNo;
	private String sku;
	private String quantity;
	private String unitPrice;
	private String discount;
	private String gstRate;
	private String lineTotal;
	private Boolean taxType;
	private String storeId;
	private Double subTotal;

	private Double qIGST;
	private Double qCGST;
	private Double qSGST;
	private String grandTotal;
	private String outstandingAmnt;
	private String salesOrder;

	private Double itemIgst;
	private Double itemCgst;
	private Double itemSgst;

	private String categoryId;
	private String categoryName;

	private String paymentId;
	private String transcationId;
	private String productDimesion;

	/*
	 * List<ItemShoukeenModel> itemattribute; List<InvoiceShoukeenModel>
	 * invoiceattribute;
	 */

	private String deliveryBoyId;
	private String deliveryBoyName;
	private String deliveryBoyStatus;

	private String invoiceDate;
	private String invoiceTime;

	private String size;

	private String productDimensionId;
	private String productDimension;
	private String currency;

	private String docName;
	private String paymentMode;
	private String paymentModeName;
	private String payAmount;
	private String chequeNo;
	private String chequeDate;
	private String accountNo;
	private String branchName;
	private String bankName;
	private String transactionNo;
	private String utrNo;
	private String paymentOrder;
	private String createdOn;
	private String createdTime;

	private Double outstandingAmount;
	private Double totalPaidAmount;
	private Double totalOrderAmount;

	private String cashReceivedBy;
	private String chequeReceivedBy;
	private String chequeDropDate;
	private String onlineReceivedBy;

	private String isPaidUpi;
	private String upiId;

	/// Type

	// deliveryBoy
	private String cancelReason;
	private String cancelNotes;
	private String cancelledBy;

	// admin
	private String cancelReasonAdmin;
	private String cancelNotesAdmin;

	// DPartner
	private String cancelReasonDPartner;
	private String cancelNotesDPartner;

	private String deliveredBy;
	private String deliveryStatus;
	private String ratingId;
	private String feedbackNote;

	// deliveryBoy
	private BigInteger totalShipment;
	private BigInteger pendingShipment;
	private BigInteger deliveredShipment;
	private BigInteger cancelledShipment;

	// deliveryPartner
	private BigInteger dPartnerTotal;
	private BigInteger dPartnerAssigned;
	private BigInteger dPartnerPending;
	private BigInteger dPartnerCancelled;
	private BigInteger dPartnerDelivered;

	// admin
	private BigInteger adminTotalOrder;
	private BigInteger adminOrderPlaced;
	private BigInteger adminPackedShipment;
	private BigInteger adminShipped;
	private BigInteger adminDBoy;
	private BigInteger adminDelivered;
	private BigInteger adminCancelled;

	//List<InventoryVendorDocumentModel> documentList;
	private String receivingQuantity;

	private String distDiscount;
	private String approvalStatus;
	private String paymentApprovedBy;

	private String grandTotalDiscount;

	private MultipartFile multipleFile;
	private String docType;
	private String fileName;
	private String extension;
	private String fileType;
	private byte[] bytes;
	private String documnentName;
	private String paymentRamarkBy;
	private String remarkId;
	private String remarkSubject;
	private String remarkNotes;

	private String replacedOrderId;
	private String paymentReceivedBy;

	private String qutActive;
	private String saleInvoiceId;
	private String saleDeliveryChallan;
	// Pagination
	private String totalPageno;
	private String totalOrderById;
	private String grandTotals;

	private String orderStatus;

	private String dealerPassword;
	private String legalName;

	private String storeName;
	private String productCategory;
	private String dealerCategory;

	private String country;
	private String states;
	private String city;
	private String locality;
	private String address1;
	private String address2;
	private String pinCode;

	private String isTaxable;
	private String taxStateGst;
	private String provisionalGSTINNo;
	private String panNumber;
	private String buyProductFrom;
	private String annualTurnOver;
	private String annualSell;
	private String sellOnOtherWebsiteYesOrNo;

	private String otherWebsiteName;
	private String productCategoryWishToSell;
	private String description;
	private String isAgreeWithTermCondition;
	private String imageName;

	private String createdDate;
	private String updatedDate;

	private String dealerPhone;
	private String districtId;
	private String districtName;
	private String landmark;
	private String openingTime;
	private String closingTime;
	private String primaryMobileNo;
	private String secondaryMobileNo;
	private String primaryPhoneNo;
	private String secondaryPhoneNo;

	private String stateName;

	private String storeName1;
	private String dealerCategory1;
	private String country1;
	private String states1;
	private String districtName1;
	private String city1;
	private String locality1;
	private String address3;
	private String address4;
	private String openingTime1;
	private String closingTime1;
	private String landmark1;
	private String pinCode1;
	private String mobileNo;
	private String mobileNo1;

	private String countryName;
	private String countryName1;
	private String cityName;
	private String cityName1;
	private String stateName1;
	private String taxStateGstName;

	private String districtName2;
	private String districtName3;
	private String orderId;
	private String productId;

	private String subTotals;

	private String couponCode;
	private String couponDiscPer;
	private String couponDiscAmt;

	private String dealerCode;

	private String replaceStatus;

	private String remark;

	private String remarks;

	private String creditLimit;
	private String storeLocation;

	private String orderApprovedBy;
	private String toggleRegularCustom;

	private String dealerAdminId;
	private String dealerAdminName;
	private String dealerAdminEmail;
	private String dealerAdminMobile;
	//List<OfferedProductWebModel> offeredItem;

	private String dealerBankName;
	private String dealerBranchName;
	private String dealerIfscCode;
	private String dealerAccountNo;
	private String dealerUpiId;

	/* for Download Dealer Pdf */
	private String DealerPdfName;
	private String DealerPdfMobileNo;
	private String DealerPdfAddress;
	private String DealerPdfInvoiceId;
	private String DealerPayAmmountPdf;
	private String DealerPdfPaymentMode;
	private String OrderPdfItem;

	private String deaChequeReceivedBy;
	private String deaChequeDropDate;
	private String deaChequeNo;
	private String deaChequeDate;
	private String employeeId;
	private String paymentTerm;
	private String tds;
	private String sapId;
	

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getPaymentRamarkBy() {
		return paymentRamarkBy;
	}

	public String getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(String remarkId) {
		this.remarkId = remarkId;
	}

	public String getRemarkSubject() {
		return remarkSubject;
	}

	public void setRemarkSubject(String remarkSubject) {
		this.remarkSubject = remarkSubject;
	}

	public String getRemarkNotes() {
		return remarkNotes;
	}

	public void setRemarkNotes(String remarkNotes) {
		this.remarkNotes = remarkNotes;
	}

	public void setPaymentRamarkBy(String paymentRamarkBy) {
		this.paymentRamarkBy = paymentRamarkBy;
	}

	public MultipartFile getMultipleFile() {
		return multipleFile;
	}

	public void setMultipleFile(MultipartFile multipleFile) {
		this.multipleFile = multipleFile;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getFileName() {
		return fileName;
	}

	public String getDocumnentName() {
		return documnentName;
	}

	public void setDocumnentName(String documnentName) {
		this.documnentName = documnentName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getGrandTotalDiscount() {
		return grandTotalDiscount;
	}

	public void setGrandTotalDiscount(String grandTotalDiscount) {
		this.grandTotalDiscount = grandTotalDiscount;
	}

	public String getDistDiscount() {
		return distDiscount;
	}

	public void setDistDiscount(String distDiscount) {
		this.distDiscount = distDiscount;
	}

	/*
	 * public List<InventoryVendorDocumentModel> getDocumentList() { return
	 * documentList; }
	 * 
	 * public void setDocumentList(List<InventoryVendorDocumentModel> documentList)
	 * { this.documentList = documentList; }
	 */

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getPaymentApprovedBy() {
		return paymentApprovedBy;
	}

	public void setPaymentApprovedBy(String paymentApprovedBy) {
		this.paymentApprovedBy = paymentApprovedBy;
	}

	public String getReceivingQuantity() {
		return receivingQuantity;
	}

	public void setReceivingQuantity(String receivingQuantity) {
		this.receivingQuantity = receivingQuantity;
	}

	public String getCancelNotes() {
		return cancelNotes;
	}

	public void setCancelNotes(String cancelNotes) {
		this.cancelNotes = cancelNotes;
	}

	public String getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(String cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	public String getCancelReasonAdmin() {
		return cancelReasonAdmin;
	}

	public void setCancelReasonAdmin(String cancelReasonAdmin) {
		this.cancelReasonAdmin = cancelReasonAdmin;
	}

	public String getCancelNotesAdmin() {
		return cancelNotesAdmin;
	}

	public void setCancelNotesAdmin(String cancelNotesAdmin) {
		this.cancelNotesAdmin = cancelNotesAdmin;
	}

	public String getCancelReasonDPartner() {
		return cancelReasonDPartner;
	}

	public void setCancelReasonDPartner(String cancelReasonDPartner) {
		this.cancelReasonDPartner = cancelReasonDPartner;
	}

	public String getCancelNotesDPartner() {
		return cancelNotesDPartner;
	}

	public void setCancelNotesDPartner(String cancelNotesDPartner) {
		this.cancelNotesDPartner = cancelNotesDPartner;
	}

	public String getDeliveredBy() {
		return deliveredBy;
	}

	public void setDeliveredBy(String deliveredBy) {
		this.deliveredBy = deliveredBy;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getRatingId() {
		return ratingId;
	}

	public void setRatingId(String ratingId) {
		this.ratingId = ratingId;
	}

	public String getFeedbackNote() {
		return feedbackNote;
	}

	public void setFeedbackNote(String feedbackNote) {
		this.feedbackNote = feedbackNote;
	}

	public BigInteger getTotalShipment() {
		return totalShipment;
	}

	public void setTotalShipment(BigInteger totalShipment) {
		this.totalShipment = totalShipment;
	}

	public BigInteger getPendingShipment() {
		return pendingShipment;
	}

	public void setPendingShipment(BigInteger pendingShipment) {
		this.pendingShipment = pendingShipment;
	}

	public BigInteger getDeliveredShipment() {
		return deliveredShipment;
	}

	public void setDeliveredShipment(BigInteger deliveredShipment) {
		this.deliveredShipment = deliveredShipment;
	}

	public BigInteger getCancelledShipment() {
		return cancelledShipment;
	}

	public void setCancelledShipment(BigInteger cancelledShipment) {
		this.cancelledShipment = cancelledShipment;
	}

	public BigInteger getdPartnerTotal() {
		return dPartnerTotal;
	}

	public void setdPartnerTotal(BigInteger dPartnerTotal) {
		this.dPartnerTotal = dPartnerTotal;
	}

	public BigInteger getdPartnerAssigned() {
		return dPartnerAssigned;
	}

	public void setdPartnerAssigned(BigInteger dPartnerAssigned) {
		this.dPartnerAssigned = dPartnerAssigned;
	}

	public BigInteger getdPartnerPending() {
		return dPartnerPending;
	}

	public void setdPartnerPending(BigInteger dPartnerPending) {
		this.dPartnerPending = dPartnerPending;
	}

	public BigInteger getdPartnerCancelled() {
		return dPartnerCancelled;
	}

	public void setdPartnerCancelled(BigInteger dPartnerCancelled) {
		this.dPartnerCancelled = dPartnerCancelled;
	}

	public BigInteger getdPartnerDelivered() {
		return dPartnerDelivered;
	}

	public void setdPartnerDelivered(BigInteger dPartnerDelivered) {
		this.dPartnerDelivered = dPartnerDelivered;
	}

	public BigInteger getAdminTotalOrder() {
		return adminTotalOrder;
	}

	public void setAdminTotalOrder(BigInteger adminTotalOrder) {
		this.adminTotalOrder = adminTotalOrder;
	}

	public BigInteger getAdminOrderPlaced() {
		return adminOrderPlaced;
	}

	public void setAdminOrderPlaced(BigInteger adminOrderPlaced) {
		this.adminOrderPlaced = adminOrderPlaced;
	}

	public BigInteger getAdminPackedShipment() {
		return adminPackedShipment;
	}

	public void setAdminPackedShipment(BigInteger adminPackedShipment) {
		this.adminPackedShipment = adminPackedShipment;
	}

	public BigInteger getAdminShipped() {
		return adminShipped;
	}

	public void setAdminShipped(BigInteger adminShipped) {
		this.adminShipped = adminShipped;
	}

	public BigInteger getAdminDBoy() {
		return adminDBoy;
	}

	public void setAdminDBoy(BigInteger adminDBoy) {
		this.adminDBoy = adminDBoy;
	}

	public BigInteger getAdminDelivered() {
		return adminDelivered;
	}

	public void setAdminDelivered(BigInteger adminDelivered) {
		this.adminDelivered = adminDelivered;
	}

	public BigInteger getAdminCancelled() {
		return adminCancelled;
	}

	public void setAdminCancelled(BigInteger adminCancelled) {
		this.adminCancelled = adminCancelled;
	}

	private String userType;

	public String getIsPaidUpi() {
		return isPaidUpi;
	}

	public void setIsPaidUpi(String isPaidUpi) {
		this.isPaidUpi = isPaidUpi;
	}

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	public String getOnlineReceivedBy() {
		return onlineReceivedBy;
	}

	public void setOnlineReceivedBy(String onlineReceivedBy) {
		this.onlineReceivedBy = onlineReceivedBy;
	}

	public String getChequeDropDate() {
		return chequeDropDate;
	}

	public void setChequeDropDate(String chequeDropDate) {
		this.chequeDropDate = chequeDropDate;
	}

	public String getCashReceivedBy() {
		return cashReceivedBy;
	}

	public String getChequeReceivedBy() {
		return chequeReceivedBy;
	}

	public void setChequeReceivedBy(String chequeReceivedBy) {
		this.chequeReceivedBy = chequeReceivedBy;
	}

	public void setCashReceivedBy(String cashReceivedBy) {
		this.cashReceivedBy = cashReceivedBy;
	}

	public Double getTotalOrderAmount() {
		return totalOrderAmount;
	}

	public void setTotalOrderAmount(Double totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}

	public Double getTotalPaidAmount() {
		return totalPaidAmount;
	}

	public void setTotalPaidAmount(Double totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}

	public Double getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(Double outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public String getPaymentOrder() {
		return paymentOrder;
	}

	public void setPaymentOrder(String paymentOrder) {
		this.paymentOrder = paymentOrder;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getUtrNo() {
		return utrNo;
	}

	public void setUtrNo(String utrNo) {
		this.utrNo = utrNo;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getOutstandingAmnt() {
		return outstandingAmnt;
	}

	public void setOutstandingAmnt(String outstandingAmnt) {
		this.outstandingAmnt = outstandingAmnt;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	/*
	 * public String getDistributorId() { return distributorId; }
	 * 
	 * public void setDistributorId(String distributorId) { this.distributorId =
	 * distributorId; }
	 * 
	 * public String getDistributorName() { return distributorName; }
	 * 
	 * public void setDistributorName(String distributorName) { this.distributorName
	 * = distributorName; }
	 * 
	 * public String getDistributorMobile() { return distributorMobile; }
	 * 
	 * public void setDistributorMobile(String distributorMobile) {
	 * this.distributorMobile = distributorMobile; }
	 * 
	 * public String getDistributorEmail() { return distributorEmail; }
	 * 
	 * public void setDistributorEmail(String distributorEmail) {
	 * this.distributorEmail = distributorEmail; }
	 * 
	 * public String getDistributorAddress() { return distributorAddress; }
	 * 
	 * public void setDistributorAddress(String distributorAddress) {
	 * this.distributorAddress = distributorAddress; }
	 * 
	 * public String getDealerAddress() { return dealerAddress; }
	 * 
	 * public void setDealerAddress(String dealerAddress) { this.dealerAddress =
	 * dealerAddress; }
	 * 
	 * public String getDealerMobile() { return dealerMobile; }
	 * 
	 * public void setDealerMobile(String dealerMobile) { this.dealerMobile =
	 * dealerMobile; }
	 * 
	 * public String getDealerEmail() { return dealerEmail; }
	 * 
	 * public void setDealerEmail(String dealerEmail) { this.dealerEmail =
	 * dealerEmail; }
	 */

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getProductDimesion() {
		return productDimesion;
	}

	public void setProductDimesion(String productDimesion) {
		this.productDimesion = productDimesion;
	}

	public String getProductDimensionId() {
		return productDimensionId;
	}

	public void setProductDimensionId(String productDimensionId) {
		this.productDimensionId = productDimensionId;
	}

	public String getProductDimension() {
		return productDimension;
	}

	public void setProductDimension(String productDimension) {
		this.productDimension = productDimension;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getDeliveryBoyName() {
		return deliveryBoyName;
	}

	public void setDeliveryBoyName(String deliveryBoyName) {
		this.deliveryBoyName = deliveryBoyName;
	}

	public String getDeliveryBoyId() {
		return deliveryBoyId;
	}

	public void setDeliveryBoyId(String deliveryBoyId) {
		this.deliveryBoyId = deliveryBoyId;
	}

	public String getDeliveryBoyStatus() {
		return deliveryBoyStatus;
	}

	public void setDeliveryBoyStatus(String deliveryBoyStatus) {
		this.deliveryBoyStatus = deliveryBoyStatus;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getTranscationId() {
		return transcationId;
	}

	public void setTranscationId(String transcationId) {
		this.transcationId = transcationId;
	}

	/*
	 * public List<ItemShoukeenModel> getItemattribute() { return itemattribute; }
	 * 
	 * public void setItemattribute(List<ItemShoukeenModel> itemattribute) {
	 * this.itemattribute = itemattribute; }
	 */

	public Integer getSlNo() {
		return slNo;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getGstRate() {
		return gstRate;
	}

	public void setGstRate(String gstRate) {
		this.gstRate = gstRate;
	}

	public String getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(String lineTotal) {
		this.lineTotal = lineTotal;
	}

	public String getCustEmail() {
		return custEmail;
	}

	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(String totalItem) {
		this.totalItem = totalItem;
	}

	public String getCustOrderId() {
		return custOrderId;
	}

	public void setCustOrderId(String custOrderId) {
		this.custOrderId = custOrderId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getCustDesc() {
		return custDesc;
	}

	public void setCustDesc(String custDesc) {
		this.custDesc = custDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemQty() {
		return itemQty;
	}

	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}

	public String getItemUnitPrice() {
		return itemUnitPrice;
	}

	public void setItemUnitPrice(String itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public String getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(String itemDiscount) {
		this.itemDiscount = itemDiscount;
	}

	public String getItemGst() {
		return itemGst;
	}

	public void setItemGst(String itemGst) {
		this.itemGst = itemGst;
	}

	public String getItemLineTotal() {
		return itemLineTotal;
	}

	public void setItemLineTotal(String itemLineTotal) {
		this.itemLineTotal = itemLineTotal;
	}

	public String getItemTotalAmount() {
		return itemTotalAmount;
	}

	public void setItemTotalAmount(String itemTotalAmount) {
		this.itemTotalAmount = itemTotalAmount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentModeName() {
		return paymentModeName;
	}

	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public String getShippingCharges() {
		return shippingCharges;
	}

	public void setShippingCharges(String shippingCharges) {
		this.shippingCharges = shippingCharges;
	}

	public Boolean getTaxType() {
		return taxType;
	}

	public void setTaxType(Boolean taxType) {
		this.taxType = taxType;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getqIGST() {
		return qIGST;
	}

	public void setqIGST(Double qIGST) {
		this.qIGST = qIGST;
	}

	public Double getqCGST() {
		return qCGST;
	}

	public void setqCGST(Double qCGST) {
		this.qCGST = qCGST;
	}

	public Double getqSGST() {
		return qSGST;
	}

	public void setqSGST(Double qSGST) {
		this.qSGST = qSGST;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	public Double getItemIgst() {
		return itemIgst;
	}

	public void setItemIgst(Double itemIgst) {
		this.itemIgst = itemIgst;
	}

	public Double getItemCgst() {
		return itemCgst;
	}

	public void setItemCgst(Double itemCgst) {
		this.itemCgst = itemCgst;
	}

	public Double getItemSgst() {
		return itemSgst;
	}

	public void setItemSgst(Double itemSgst) {
		this.itemSgst = itemSgst;
	}

	/*
	 * public List<InvoiceShoukeenModel> getInvoiceattribute() { return
	 * invoiceattribute; }
	 * 
	 * public void setInvoiceattribute(List<InvoiceShoukeenModel> invoiceattribute)
	 * { this.invoiceattribute = invoiceattribute; }
	 */

	public String getReplacedOrderId() {
		return replacedOrderId;
	}

	public void setReplacedOrderId(String replacedOrderId) {
		this.replacedOrderId = replacedOrderId;
	}

	public String getPaymentReceivedBy() {
		return paymentReceivedBy;
	}

	public void setPaymentReceivedBy(String paymentReceivedBy) {
		this.paymentReceivedBy = paymentReceivedBy;
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

	public String getQutActive() {
		return qutActive;
	}

	public void setQutActive(String qutActive) {
		this.qutActive = qutActive;
	}

	public String getSaleInvoiceId() {
		return saleInvoiceId;
	}

	public void setSaleInvoiceId(String saleInvoiceId) {
		this.saleInvoiceId = saleInvoiceId;
	}

	public String getSaleDeliveryChallan() {
		return saleDeliveryChallan;
	}

	public void setSaleDeliveryChallan(String saleDeliveryChallan) {
		this.saleDeliveryChallan = saleDeliveryChallan;
	}

	public String getGrandTotals() {
		return grandTotals;
	}

	public void setGrandTotals(String grandTotals) {
		this.grandTotals = grandTotals;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getDealerPassword() {
		return dealerPassword;
	}

	public void setDealerPassword(String dealerPassword) {
		this.dealerPassword = dealerPassword;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getDealerCategory() {
		return dealerCategory;
	}

	public void setDealerCategory(String dealerCategory) {
		this.dealerCategory = dealerCategory;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getIsTaxable() {
		return isTaxable;
	}

	public void setIsTaxable(String isTaxable) {
		this.isTaxable = isTaxable;
	}

	public String getTaxStateGst() {
		return taxStateGst;
	}

	public void setTaxStateGst(String taxStateGst) {
		this.taxStateGst = taxStateGst;
	}

	public String getProvisionalGSTINNo() {
		return provisionalGSTINNo;
	}

	public void setProvisionalGSTINNo(String provisionalGSTINNo) {
		this.provisionalGSTINNo = provisionalGSTINNo;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getBuyProductFrom() {
		return buyProductFrom;
	}

	public void setBuyProductFrom(String buyProductFrom) {
		this.buyProductFrom = buyProductFrom;
	}

	public String getAnnualTurnOver() {
		return annualTurnOver;
	}

	public void setAnnualTurnOver(String annualTurnOver) {
		this.annualTurnOver = annualTurnOver;
	}

	public String getAnnualSell() {
		return annualSell;
	}

	public void setAnnualSell(String annualSell) {
		this.annualSell = annualSell;
	}

	public String getSellOnOtherWebsiteYesOrNo() {
		return sellOnOtherWebsiteYesOrNo;
	}

	public void setSellOnOtherWebsiteYesOrNo(String sellOnOtherWebsiteYesOrNo) {
		this.sellOnOtherWebsiteYesOrNo = sellOnOtherWebsiteYesOrNo;
	}

	public String getOtherWebsiteName() {
		return otherWebsiteName;
	}

	public void setOtherWebsiteName(String otherWebsiteName) {
		this.otherWebsiteName = otherWebsiteName;
	}

	public String getProductCategoryWishToSell() {
		return productCategoryWishToSell;
	}

	public void setProductCategoryWishToSell(String productCategoryWishToSell) {
		this.productCategoryWishToSell = productCategoryWishToSell;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsAgreeWithTermCondition() {
		return isAgreeWithTermCondition;
	}

	public void setIsAgreeWithTermCondition(String isAgreeWithTermCondition) {
		this.isAgreeWithTermCondition = isAgreeWithTermCondition;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getDealerPhone() {
		return dealerPhone;
	}

	public void setDealerPhone(String dealerPhone) {
		this.dealerPhone = dealerPhone;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}

	public String getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(String closingTime) {
		this.closingTime = closingTime;
	}

	public String getPrimaryMobileNo() {
		return primaryMobileNo;
	}

	public void setPrimaryMobileNo(String primaryMobileNo) {
		this.primaryMobileNo = primaryMobileNo;
	}

	public String getSecondaryMobileNo() {
		return secondaryMobileNo;
	}

	public void setSecondaryMobileNo(String secondaryMobileNo) {
		this.secondaryMobileNo = secondaryMobileNo;
	}

	public String getPrimaryPhoneNo() {
		return primaryPhoneNo;
	}

	public void setPrimaryPhoneNo(String primaryPhoneNo) {
		this.primaryPhoneNo = primaryPhoneNo;
	}

	public String getSecondaryPhoneNo() {
		return secondaryPhoneNo;
	}

	public void setSecondaryPhoneNo(String secondaryPhoneNo) {
		this.secondaryPhoneNo = secondaryPhoneNo;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStoreName1() {
		return storeName1;
	}

	public void setStoreName1(String storeName1) {
		this.storeName1 = storeName1;
	}

	public String getDealerCategory1() {
		return dealerCategory1;
	}

	public void setDealerCategory1(String dealerCategory1) {
		this.dealerCategory1 = dealerCategory1;
	}

	public String getCountry1() {
		return country1;
	}

	public void setCountry1(String country1) {
		this.country1 = country1;
	}

	public String getStates1() {
		return states1;
	}

	public void setStates1(String states1) {
		this.states1 = states1;
	}

	public String getDistrictName1() {
		return districtName1;
	}

	public void setDistrictName1(String districtName1) {
		this.districtName1 = districtName1;
	}

	public String getCity1() {
		return city1;
	}

	public void setCity1(String city1) {
		this.city1 = city1;
	}

	public String getLocality1() {
		return locality1;
	}

	public void setLocality1(String locality1) {
		this.locality1 = locality1;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getOpeningTime1() {
		return openingTime1;
	}

	public void setOpeningTime1(String openingTime1) {
		this.openingTime1 = openingTime1;
	}

	public String getClosingTime1() {
		return closingTime1;
	}

	public void setClosingTime1(String closingTime1) {
		this.closingTime1 = closingTime1;
	}

	public String getLandmark1() {
		return landmark1;
	}

	public void setLandmark1(String landmark1) {
		this.landmark1 = landmark1;
	}

	public String getPinCode1() {
		return pinCode1;
	}

	public void setPinCode1(String pinCode1) {
		this.pinCode1 = pinCode1;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getMobileNo1() {
		return mobileNo1;
	}

	public void setMobileNo1(String mobileNo1) {
		this.mobileNo1 = mobileNo1;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryName1() {
		return countryName1;
	}

	public void setCountryName1(String countryName1) {
		this.countryName1 = countryName1;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName1() {
		return cityName1;
	}

	public void setCityName1(String cityName1) {
		this.cityName1 = cityName1;
	}

	public String getStateName1() {
		return stateName1;
	}

	public void setStateName1(String stateName1) {
		this.stateName1 = stateName1;
	}

	public String getTaxStateGstName() {
		return taxStateGstName;
	}

	public void setTaxStateGstName(String taxStateGstName) {
		this.taxStateGstName = taxStateGstName;
	}

	public String getDistrictName2() {
		return districtName2;
	}

	public void setDistrictName2(String districtName2) {
		this.districtName2 = districtName2;
	}

	public String getDistrictName3() {
		return districtName3;
	}

	public void setDistrictName3(String districtName3) {
		this.districtName3 = districtName3;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSubTotals() {
		return subTotals;
	}

	public void setSubTotals(String subTotals) {
		this.subTotals = subTotals;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCouponDiscPer() {
		return couponDiscPer;
	}

	public void setCouponDiscPer(String couponDiscPer) {
		this.couponDiscPer = couponDiscPer;
	}

	public String getCouponDiscAmt() {
		return couponDiscAmt;
	}

	public void setCouponDiscAmt(String couponDiscAmt) {
		this.couponDiscAmt = couponDiscAmt;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getReplaceStatus() {
		return replaceStatus;
	}

	public void setReplaceStatus(String replaceStatus) {
		this.replaceStatus = replaceStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getOrderApprovedBy() {
		return orderApprovedBy;
	}

	public String getToggleRegularCustom() {
		return toggleRegularCustom;
	}

	public void setToggleRegularCustom(String toggleRegularCustom) {
		this.toggleRegularCustom = toggleRegularCustom;
	}

	public void setOrderApprovedBy(String orderApprovedBy) {
		this.orderApprovedBy = orderApprovedBy;
	}

	public String getDealerAdminId() {
		return dealerAdminId;
	}

	public void setDealerAdminId(String dealerAdminId) {
		this.dealerAdminId = dealerAdminId;
	}

	public String getDealerAdminName() {
		return dealerAdminName;
	}

	public void setDealerAdminName(String dealerAdminName) {
		this.dealerAdminName = dealerAdminName;
	}

	public String getDealerAdminEmail() {
		return dealerAdminEmail;
	}

	public void setDealerAdminEmail(String dealerAdminEmail) {
		this.dealerAdminEmail = dealerAdminEmail;
	}

	public String getDealerAdminMobile() {
		return dealerAdminMobile;
	}

	public void setDealerAdminMobile(String dealerAdminMobile) {
		this.dealerAdminMobile = dealerAdminMobile;
	}

	/*
	 * public List<OfferedProductWebModel> getOfferedItem() { return offeredItem; }
	 */

	public String getStoreLocation() {
		return storeLocation;
	}

	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}

	/*
	 * public void setOfferedItem(List<OfferedProductWebModel> offeredItem) {
	 * this.offeredItem = offeredItem; }
	 */

	public String getDealerBankName() {
		return dealerBankName;
	}

	public void setDealerBankName(String dealerBankName) {
		this.dealerBankName = dealerBankName;
	}

	public String getDealerBranchName() {
		return dealerBranchName;
	}

	public void setDealerBranchName(String dealerBranchName) {
		this.dealerBranchName = dealerBranchName;
	}

	public String getDealerIfscCode() {
		return dealerIfscCode;
	}

	public void setDealerIfscCode(String dealerIfscCode) {
		this.dealerIfscCode = dealerIfscCode;
	}

	public String getDealerAccountNo() {
		return dealerAccountNo;
	}

	public void setDealerAccountNo(String dealerAccountNo) {
		this.dealerAccountNo = dealerAccountNo;
	}

	public String getDealerUpiId() {
		return dealerUpiId;
	}

	public void setDealerUpiId(String dealerUpiId) {
		this.dealerUpiId = dealerUpiId;
	}

	public String getDealerPdfName() {
		return DealerPdfName;
	}

	public void setDealerPdfName(String dealerPdfName) {
		DealerPdfName = dealerPdfName;
	}

	public String getDealerPdfMobileNo() {
		return DealerPdfMobileNo;
	}

	public void setDealerPdfMobileNo(String dealerPdfMobileNo) {
		DealerPdfMobileNo = dealerPdfMobileNo;
	}

	public String getDealerPdfAddress() {
		return DealerPdfAddress;
	}

	public void setDealerPdfAddress(String dealerPdfAddress) {
		DealerPdfAddress = dealerPdfAddress;
	}

	public String getDealerPdfInvoiceId() {
		return DealerPdfInvoiceId;
	}

	public void setDealerPdfInvoiceId(String dealerPdfInvoiceId) {
		DealerPdfInvoiceId = dealerPdfInvoiceId;
	}

	public String getDealerPayAmmountPdf() {
		return DealerPayAmmountPdf;
	}

	public void setDealerPayAmmountPdf(String dealerPayAmmountPdf) {
		DealerPayAmmountPdf = dealerPayAmmountPdf;
	}

	public String getDealerPdfPaymentMode() {
		return DealerPdfPaymentMode;
	}

	public void setDealerPdfPaymentMode(String dealerPdfPaymentMode) {
		DealerPdfPaymentMode = dealerPdfPaymentMode;
	}

	public String getOrderPdfItem() {
		return OrderPdfItem;
	}

	public void setOrderPdfItem(String orderPdfItem) {
		OrderPdfItem = orderPdfItem;
	}

	public String getDeaChequeReceivedBy() {
		return deaChequeReceivedBy;
	}

	public void setDeaChequeReceivedBy(String deaChequeReceivedBy) {
		this.deaChequeReceivedBy = deaChequeReceivedBy;
	}

	public String getDeaChequeDropDate() {
		return deaChequeDropDate;
	}

	public void setDeaChequeDropDate(String deaChequeDropDate) {
		this.deaChequeDropDate = deaChequeDropDate;
	}

	public String getDeaChequeNo() {
		return deaChequeNo;
	}

	public void setDeaChequeNo(String deaChequeNo) {
		this.deaChequeNo = deaChequeNo;
	}

	public String getDeaChequeDate() {
		return deaChequeDate;
	}

	public void setDeaChequeDate(String deaChequeDate) {
		this.deaChequeDate = deaChequeDate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getTds() {
		return tds;
	}

	public void setTds(String tds) {
		this.tds = tds;
	}

	
	
	
	public String getSapId() {
		return sapId;
	}

	public void setSapId(String sapId) {
		this.sapId = sapId;
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
