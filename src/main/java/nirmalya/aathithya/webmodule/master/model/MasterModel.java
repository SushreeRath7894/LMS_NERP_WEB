package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MasterModel {

	private String categoryId;
	private String categoryOrder;
	private String categoryName;
	private String categoryStatus;
	private String subCategoryId;
	private String subCategoryOrder;
	private String subCategoryName;
	private String subCategoryStatus;
	private String variantId;
	private String variantOrder;
	private String variantName;
	private String variantStatus;
	private String organization;
	private String orgDivision;
	private String createdBy;
	private String type;
	
	private String holidayId;
	private String holidayName;
	private String year;
	private String fromDate;
	private String toDate;
	private String totalLeave;
	
	private String modeId;
	private String pModeName;
	private String pDesc;
	
	private String sacId;
	private String sacName;
	private String sacGst;
	private String serviceName;
	private String fDate;

	public MasterModel() {
		super();
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryOrder() {
		return categoryOrder;
	}

	public void setCategoryOrder(String categoryOrder) {
		this.categoryOrder = categoryOrder;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryStatus() {
		return categoryStatus;
	}

	public void setCategoryStatus(String categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryOrder() {
		return subCategoryOrder;
	}

	public void setSubCategoryOrder(String subCategoryOrder) {
		this.subCategoryOrder = subCategoryOrder;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getSubCategoryStatus() {
		return subCategoryStatus;
	}

	public void setSubCategoryStatus(String subCategoryStatus) {
		this.subCategoryStatus = subCategoryStatus;
	}

	public String getVariantId() {
		return variantId;
	}

	public void setVariantId(String variantId) {
		this.variantId = variantId;
	}

	public String getVariantOrder() {
		return variantOrder;
	}

	public void setVariantOrder(String variantOrder) {
		this.variantOrder = variantOrder;
	}

	public String getVariantName() {
		return variantName;
	}

	public void setVariantName(String variantName) {
		this.variantName = variantName;
	}

	public String getVariantStatus() {
		return variantStatus;
	}

	public void setVariantStatus(String variantStatus) {
		this.variantStatus = variantStatus;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTotalLeave() {
		return totalLeave;
	}

	public void setTotalLeave(String totalLeave) {
		this.totalLeave = totalLeave;
	}

	public String getModeId() {
		return modeId;
	}

	public void setModeId(String modeId) {
		this.modeId = modeId;
	}

	public String getpModeName() {
		return pModeName;
	}

	public void setpModeName(String pModeName) {
		this.pModeName = pModeName;
	}

	public String getpDesc() {
		return pDesc;
	}

	public void setpDesc(String pDesc) {
		this.pDesc = pDesc;
	}

	public String getSacId() {
		return sacId;
	}

	public void setSacId(String sacId) {
		this.sacId = sacId;
	}

	public String getSacName() {
		return sacName;
	}

	public void setSacName(String sacName) {
		this.sacName = sacName;
	}

	public String getSacGst() {
		return sacGst;
	}

	public void setSacGst(String sacGst) {
		this.sacGst = sacGst;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getfDate() {
		return fDate;
	}

	public void setfDate(String fDate) {
		this.fDate = fDate;
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
