package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EvaluationOfDisplayCartonsModel {

	private String createdBy;
	private String organization;
	private String orgDivision;

	private String evalutionId;
	private String qaRequestedId;
	private String refNo;
	private String issueNo;
	private String issuedDate;

	private String challanNo;
	private String receiptDate;
	private String sku;
	private String product;
	private String specificationNo;

	private String grrNo;
	private String samplingDate;
	private String supplier;
	private String quantityRcvd;

	private String checkingDate;
	private String quantityChkd;
	private String remark;

	private String parmId;
	private String param;
	private String spec;
	private String col1;
	private String col2;
	private String col3;
	private String col4;
	private String col5;
	private String avg;
	
	private String imgName;
	private String imgUrl;

	List<EvaluationOfDisplayCartonsModel> itemDtls;

	public EvaluationOfDisplayCartonsModel() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getEvalutionId() {
		return evalutionId;
	}

	public void setEvalutionId(String evalutionId) {
		this.evalutionId = evalutionId;
	}

	public String getQaRequestedId() {
		return qaRequestedId;
	}

	public void setQaRequestedId(String qaRequestedId) {
		this.qaRequestedId = qaRequestedId;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSpecificationNo() {
		return specificationNo;
	}

	public void setSpecificationNo(String specificationNo) {
		this.specificationNo = specificationNo;
	}

	public String getGrrNo() {
		return grrNo;
	}

	public void setGrrNo(String grrNo) {
		this.grrNo = grrNo;
	}

	public String getSamplingDate() {
		return samplingDate;
	}

	public void setSamplingDate(String samplingDate) {
		this.samplingDate = samplingDate;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getQuantityRcvd() {
		return quantityRcvd;
	}

	public void setQuantityRcvd(String quantityRcvd) {
		this.quantityRcvd = quantityRcvd;
	}

	public String getCheckingDate() {
		return checkingDate;
	}

	public void setCheckingDate(String checkingDate) {
		this.checkingDate = checkingDate;
	}

	public String getQuantityChkd() {
		return quantityChkd;
	}

	public void setQuantityChkd(String quantityChkd) {
		this.quantityChkd = quantityChkd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParmId() {
		return parmId;
	}

	public void setParmId(String parmId) {
		this.parmId = parmId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public List<EvaluationOfDisplayCartonsModel> getItemDtls() {
		return itemDtls;
	}

	public void setItemDtls(List<EvaluationOfDisplayCartonsModel> itemDtls) {
		this.itemDtls = itemDtls;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
