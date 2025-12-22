package nirmalya.aathithya.webmodule.sales.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;

public class QuotationNewModel {
	private String quotationNo;
	private String quotationId;
	private String draftId;
	private String qutName;
	private String custId;
	private String customerName;
	private String custName;
	private String custGSTNo;
	private String qutValidDate;
	private String qutDescription;
	private String qutActive;
	private String qutCreatedBy;
	private String qutUpdatedOn;
	private String itemId;
	private String itemName;
	private Double quantity;
	private Double unitPrice;
	private Double discount;
	private Double gstRate;
	private Double lineTotal;
	private Double subTotal;
	private Double qIGST;
	private Double qCGST;
	private Double qSGST;
	private Double grandTotal;
	private String tcs;
	private Double adjustment;
	private String active;
	private Boolean taxType;
	private String qItmCode;
	private String sku;
	private Double itemIgst;
	private Double itemCgst;
	private Double itemSgst;
	private String poNo;
	private String poDate;
	private String filePoSale;
	private String salesOrder;
	private String sOrderDate;
	private String organization;
	private String orgDivision;
	private String quotationDate;
	private String reference;
	private String salesPerson;
	private String salespersonId;
	private String spName;
	private String spGender;
	private String dobid;
	private String mobilenoid;
	private String personalmailid;
	private String addressid_;
	private String createdBy;
	private String updatedOn;
	private String dealName;
	private String document;
	private String docName;
	private String tcsId;
	private String tcsName;
	private String collection;
	private String rateId;
	private String taxName;
	private String terms;
	private Double tcsAmount;
	private String scopeMatrixDesc;
	private String scopeMatrixSlNo;
	private String scopeMatrixRemarks;
	private String subject;
	private String scopeid;
	private String matrixSamudyam;
	private String matrixClient;
	private String version;
	private String sumTotal;
	private String lastVersion;
	private String customerAddress;
	private String saleOrderDate;
	private String saleOrder;
	private String saleOrderId;
	private String saleOrderStatus;
	private String thicknessInMM;
	private String itemDesc;
	private String hsnCode;
	private String orderType;
	private List<ScopeMatrixwebModel> scopematrix;
	private List<ItemDetailswebModel> itemList;
	private String termCondition;
	private String sizeInMM;
	private String unit;
	private Double totalAmount;
	private String unitName;
	private Double taxableAmt;
	List<InventoryVendorDocumentModel> documentList;
	private String approveStatus;
	// private int SlNo;

	private String poStatus;
	private String billingAddress;
	private String billingState;
	private String billingCountry;
	private String billingMobileNo;
	private String billingStreet1;
	private String billingStreet2;
	private String billingCity;
	private String billingZipcode;
	private String quotType;
	private String orgAddress;
	private String orgEmail;
	private String orgPhone;
	private String orgImage;
	private String shippingHiddenId;
	private String userId;
	private String orgGstNo;
	private String orgCinNo;
	private String companyName;
	private String workedAt;
	private String project;
	private String projectName;
	private String unitPrices;
	private String lineTotals;
	private String quantitys;
	private String customerNames;
	private String customerCns;
	private String customerMails;
	private String customerCc;
	private String customerBcc;
	private String customerMsg;
	private String url;
	private String bomList;
	private String itemDetails;
	private String parentItemId;
	private String parentSkuId;
	private String parentSkuName;
	private String level;
	private String type;
	private String slNo;
	private String custPan;
	private String endCustName;

	private List<BomMaster> bomDetails = new ArrayList<BomMaster>();

	public QuotationNewModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getQuotationNo() {
		return quotationNo;
	}

	public void setQuotationNo(String quotationNo) {
		this.quotationNo = quotationNo;
	}

	public String getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}

	public String getQutName() {
		return qutName;
	}

	public void setQutName(String qutName) {
		this.qutName = qutName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustGSTNo() {
		return custGSTNo;
	}

	public void setCustGSTNo(String custGSTNo) {
		this.custGSTNo = custGSTNo;
	}

	public String getQutValidDate() {
		return qutValidDate;
	}

	public void setQutValidDate(String qutValidDate) {
		this.qutValidDate = qutValidDate;
	}

	public String getQutDescription() {
		return qutDescription;
	}

	public void setQutDescription(String qutDescription) {
		this.qutDescription = qutDescription;
	}

	public String getQutActive() {
		return qutActive;
	}

	public void setQutActive(String qutActive) {
		this.qutActive = qutActive;
	}

	public String getQutCreatedBy() {
		return qutCreatedBy;
	}

	public void setQutCreatedBy(String qutCreatedBy) {
		this.qutCreatedBy = qutCreatedBy;
	}

	public String getQutUpdatedOn() {
		return qutUpdatedOn;
	}

	public void setQutUpdatedOn(String qutUpdatedOn) {
		this.qutUpdatedOn = qutUpdatedOn;
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

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getGstRate() {
		return gstRate;
	}

	public void setGstRate(Double gstRate) {
		this.gstRate = gstRate;
	}

	public Double getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Double lineTotal) {
		this.lineTotal = lineTotal;
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

	public Double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getTcs() {
		return tcs;
	}

	public void setTcs(String tcs) {
		this.tcs = tcs;
	}

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Boolean getTaxType() {
		return taxType;
	}

	public void setTaxType(Boolean taxType) {
		this.taxType = taxType;
	}

	public String getqItmCode() {
		return qItmCode;
	}

	public void setqItmCode(String qItmCode) {
		this.qItmCode = qItmCode;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getPoDate() {
		return poDate;
	}

	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}

	public String getFilePoSale() {
		return filePoSale;
	}

	public void setFilePoSale(String filePoSale) {
		this.filePoSale = filePoSale;
	}

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

	public String getsOrderDate() {
		return sOrderDate;
	}

	public void setsOrderDate(String sOrderDate) {
		this.sOrderDate = sOrderDate;
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

	public String getQuotationDate() {
		return quotationDate;
	}

	public void setQuotationDate(String quotationDate) {
		this.quotationDate = quotationDate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(String salesPerson) {
		this.salesPerson = salesPerson;
	}

	public String getSalespersonId() {
		return salespersonId;
	}

	public void setSalespersonId(String salespersonId) {
		this.salespersonId = salespersonId;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getSpGender() {
		return spGender;
	}

	public void setSpGender(String spGender) {
		this.spGender = spGender;
	}

	public String getDobid() {
		return dobid;
	}

	public void setDobid(String dobid) {
		this.dobid = dobid;
	}

	public String getMobilenoid() {
		return mobilenoid;
	}

	public void setMobilenoid(String mobilenoid) {
		this.mobilenoid = mobilenoid;
	}

	public String getPersonalmailid() {
		return personalmailid;
	}

	public void setPersonalmailid(String personalmailid) {
		this.personalmailid = personalmailid;
	}

	public String getAddressid_() {
		return addressid_;
	}

	public void setAddressid_(String addressid_) {
		this.addressid_ = addressid_;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getTcsId() {
		return tcsId;
	}

	public void setTcsId(String tcsId) {
		this.tcsId = tcsId;
	}

	public String getTcsName() {
		return tcsName;
	}

	public void setTcsName(String tcsName) {
		this.tcsName = tcsName;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public Double getTcsAmount() {
		return tcsAmount;
	}

	public void setTcsAmount(Double tcsAmount) {
		this.tcsAmount = tcsAmount;
	}

	public String getScopeMatrixDesc() {
		return scopeMatrixDesc;
	}

	public void setScopeMatrixDesc(String scopeMatrixDesc) {
		this.scopeMatrixDesc = scopeMatrixDesc;
	}

	public String getScopeMatrixSlNo() {
		return scopeMatrixSlNo;
	}

	public void setScopeMatrixSlNo(String scopeMatrixSlNo) {
		this.scopeMatrixSlNo = scopeMatrixSlNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getScopeid() {
		return scopeid;
	}

	public void setScopeid(String scopeid) {
		this.scopeid = scopeid;
	}

	public String getMatrixSamudyam() {
		return matrixSamudyam;
	}

	public void setMatrixSamudyam(String matrixSamudyam) {
		this.matrixSamudyam = matrixSamudyam;
	}

	public String getMatrixClient() {
		return matrixClient;
	}

	public void setMatrixClient(String matrixClient) {
		this.matrixClient = matrixClient;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(String sumTotal) {
		this.sumTotal = sumTotal;
	}

	public String getLastVersion() {
		return lastVersion;
	}

	public void setLastVersion(String lastVersion) {
		this.lastVersion = lastVersion;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getSaleOrderDate() {
		return saleOrderDate;
	}

	public void setSaleOrderDate(String saleOrderDate) {
		this.saleOrderDate = saleOrderDate;
	}

	public String getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(String saleOrder) {
		this.saleOrder = saleOrder;
	}

	public String getSaleOrderId() {
		return saleOrderId;
	}

	public void setSaleOrderId(String saleOrderId) {
		this.saleOrderId = saleOrderId;
	}

	public String getSaleOrderStatus() {
		return saleOrderStatus;
	}

	public void setSaleOrderStatus(String saleOrderStatus) {
		this.saleOrderStatus = saleOrderStatus;
	}

	public String getThicknessInMM() {
		return thicknessInMM;
	}

	public void setThicknessInMM(String thicknessInMM) {
		this.thicknessInMM = thicknessInMM;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public List<ScopeMatrixwebModel> getScopematrix() {
		return scopematrix;
	}

	public void setScopematrix(List<ScopeMatrixwebModel> scopematrix) {
		this.scopematrix = scopematrix;
	}

	public List<ItemDetailswebModel> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemDetailswebModel> itemList) {
		this.itemList = itemList;
	}

	public String getTermCondition() {
		return termCondition;
	}

	public void setTermCondition(String termCondition) {
		this.termCondition = termCondition;
	}

	public String getSizeInMM() {
		return sizeInMM;
	}

	public void setSizeInMM(String sizeInMM) {
		this.sizeInMM = sizeInMM;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(Double taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public List<InventoryVendorDocumentModel> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<InventoryVendorDocumentModel> documentList) {
		this.documentList = documentList;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

//	public int getSlNo() {
//		return SlNo;
//	}
//
//	public void setSlNo(int slNo) {
//		SlNo = slNo;
//	}

	public String getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public String getBillingCountry() {
		return billingCountry;
	}

	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}

	public String getBillingMobileNo() {
		return billingMobileNo;
	}

	public void setBillingMobileNo(String billingMobileNo) {
		this.billingMobileNo = billingMobileNo;
	}

	public String getDraftId() {
		return draftId;
	}

	public void setDraftId(String draftId) {
		this.draftId = draftId;
	}

	public String getBillingStreet1() {
		return billingStreet1;
	}

	public void setBillingStreet1(String billingStreet1) {
		this.billingStreet1 = billingStreet1;
	}

	public String getBillingStreet2() {
		return billingStreet2;
	}

	public void setBillingStreet2(String billingStreet2) {
		this.billingStreet2 = billingStreet2;
	}

	public String getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingZipcode() {
		return billingZipcode;
	}

	public void setBillingZipcode(String billingZipcode) {
		this.billingZipcode = billingZipcode;
	}

	public String getScopeMatrixRemarks() {
		return scopeMatrixRemarks;
	}

	public void setScopeMatrixRemarks(String scopeMatrixRemarks) {
		this.scopeMatrixRemarks = scopeMatrixRemarks;
	}

	public String getQuotType() {
		return quotType;
	}

	public void setQuotType(String quotType) {
		this.quotType = quotType;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}

	public String getOrgImage() {
		return orgImage;
	}

	public void setOrgImage(String orgImage) {
		this.orgImage = orgImage;
	}

	public String getShippingHiddenId() {
		return shippingHiddenId;
	}

	public void setShippingHiddenId(String shippingHiddenId) {
		this.shippingHiddenId = shippingHiddenId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgGstNo() {
		return orgGstNo;
	}

	public void setOrgGstNo(String orgGstNo) {
		this.orgGstNo = orgGstNo;
	}

	public String getOrgCinNo() {
		return orgCinNo;
	}

	public void setOrgCinNo(String orgCinNo) {
		this.orgCinNo = orgCinNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWorkedAt() {
		return workedAt;
	}

	public void setWorkedAt(String workedAt) {
		this.workedAt = workedAt;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUnitPrices() {
		return unitPrices;
	}

	public void setUnitPrices(String unitPrices) {
		this.unitPrices = unitPrices;
	}

	public String getLineTotals() {
		return lineTotals;
	}

	public void setLineTotals(String lineTotals) {
		this.lineTotals = lineTotals;
	}

	public String getQuantitys() {
		return quantitys;
	}

	public void setQuantitys(String quantitys) {
		this.quantitys = quantitys;
	}

	public String getCustomerNames() {
		return customerNames;
	}

	public void setCustomerNames(String customerNames) {
		this.customerNames = customerNames;
	}

	public String getCustomerCns() {
		return customerCns;
	}

	public void setCustomerCns(String customerCns) {
		this.customerCns = customerCns;
	}

	public String getCustomerMails() {
		return customerMails;
	}

	public void setCustomerMails(String customerMails) {
		this.customerMails = customerMails;
	}

	public String getCustomerCc() {
		return customerCc;
	}

	public void setCustomerCc(String customerCc) {
		this.customerCc = customerCc;
	}

	public String getCustomerBcc() {
		return customerBcc;
	}

	public void setCustomerBcc(String customerBcc) {
		this.customerBcc = customerBcc;
	}

	public String getCustomerMsg() {
		return customerMsg;
	}

	public void setCustomerMsg(String customerMsg) {
		this.customerMsg = customerMsg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<BomMaster> getBomDetails() {
		return bomDetails;
	}

	public void setBomDetails(List<BomMaster> bomDetails) {
		this.bomDetails = bomDetails;
	}

	public String getBomList() {
		return bomList;
	}

	public void setBomList(String bomList) {
		this.bomList = bomList;
	}

	public String getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(String itemDetails) {
		this.itemDetails = itemDetails;
	}

	public String getParentItemId() {
		return parentItemId;
	}

	public void setParentItemId(String parentItemId) {
		this.parentItemId = parentItemId;
	}

	public String getParentSkuId() {
		return parentSkuId;
	}

	public void setParentSkuId(String parentSkuId) {
		this.parentSkuId = parentSkuId;
	}

	public String getParentSkuName() {
		return parentSkuName;
	}

	public void setParentSkuName(String parentSkuName) {
		this.parentSkuName = parentSkuName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSlNo() {
		return slNo;
	}

	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}

	public String getCustPan() {
		return custPan;
	}

	public void setCustPan(String custPan) {
		this.custPan = custPan;
	}

	public String getEndCustName() {
		return endCustName;
	}

	public void setEndCustName(String endCustName) {
		this.endCustName = endCustName;
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
