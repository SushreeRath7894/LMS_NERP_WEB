package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LmrLogWebModel {

	private String packId;
	private String shift;
	private String date;
	private String remark;
	private String productId;
	private String organization;
	private String orgDivision;
	private String createdBy;
	private String lineNo;
	private String batchNo;

	List<PartBIngredientWebModel> partBIngrediant;
	List<FemtoBlendingWebModel> femtoBlending;
	List<WeighingScaleWebModel> weighingScale;
	List<AreaLineClearanceWebModel> areaLineClearance;
	List<AreaLineClearanceWebModel> areaLineClearanceSub;

	public LmrLogWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public List<PartBIngredientWebModel> getPartBIngrediant() {
		return partBIngrediant;
	}

	public void setPartBIngrediant(List<PartBIngredientWebModel> partBIngrediant) {
		this.partBIngrediant = partBIngrediant;
	}

	public List<FemtoBlendingWebModel> getFemtoBlending() {
		return femtoBlending;
	}

	public void setFemtoBlending(List<FemtoBlendingWebModel> femtoBlending) {
		this.femtoBlending = femtoBlending;
	}

	public List<WeighingScaleWebModel> getWeighingScale() {
		return weighingScale;
	}

	public void setWeighingScale(List<WeighingScaleWebModel> weighingScale) {
		this.weighingScale = weighingScale;
	}

	public List<AreaLineClearanceWebModel> getAreaLineClearance() {
		return areaLineClearance;
	}

	public void setAreaLineClearance(List<AreaLineClearanceWebModel> areaLineClearance) {
		this.areaLineClearance = areaLineClearance;
	}

	public List<AreaLineClearanceWebModel> getAreaLineClearanceSub() {
		return areaLineClearanceSub;
	}

	public void setAreaLineClearanceSub(List<AreaLineClearanceWebModel> areaLineClearanceSub) {
		this.areaLineClearanceSub = areaLineClearanceSub;
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
