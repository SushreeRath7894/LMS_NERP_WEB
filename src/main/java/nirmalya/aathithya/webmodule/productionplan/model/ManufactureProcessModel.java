package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManufactureProcessModel {
	
	private String processing_id;
	private String shift;
	private String date;
	private String remark;
	
	private String mixerId;
	private String mixerNo;
	private String brandName;
	private String brandId;
	
	private String batchSize;
	private String rpQtyadd;
	private String totalBatch;
	private String tQtyIncRp;
	private String pRemark;
	
	private String planId;
	private String selectedItemId;
	
	
	private String CreatedBy;
	private String organization;
	private String orgDivision;
	
	private List<PackingDetailsModel> packingData;

	public ManufactureProcessModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getProcessing_id() {
		return processing_id;
	}

	public void setProcessing_id(String processing_id) {
		this.processing_id = processing_id;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMixerNo() {
		return mixerNo;
	}
	public void setMixerNo(String mixerNo) {
		this.mixerNo = mixerNo;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(String batchSize) {
		this.batchSize = batchSize;
	}
	public String getRpQtyadd() {
		return rpQtyadd;
	}
	public void setRpQtyadd(String rpQtyadd) {
		this.rpQtyadd = rpQtyadd;
	}
	public String getTotalBatch() {
		return totalBatch;
	}
	public void setTotalBatch(String totalBatch) {
		this.totalBatch = totalBatch;
	}
	public String gettQtyIncRp() {
		return tQtyIncRp;
	}
	public void settQtyIncRp(String tQtyIncRp) {
		this.tQtyIncRp = tQtyIncRp;
	}
	public String getpRemark() {
		return pRemark;
	}
	public void setpRemark(String pRemark) {
		this.pRemark = pRemark;
	}
	
	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
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
	
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getSelectedItemId() {
		return selectedItemId;
	}

	public void setSelectedItemId(String selectedItemId) {
		this.selectedItemId = selectedItemId;
	}
	
	public String getMixerId() {
		return mixerId;
	}

	public void setMixerId(String mixerId) {
		this.mixerId = mixerId;
	}
	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	
	public List<PackingDetailsModel> getPackingData() {
		return packingData;
	}

	public void setPackingData(List<PackingDetailsModel> packingData) {
		this.packingData = packingData;
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
