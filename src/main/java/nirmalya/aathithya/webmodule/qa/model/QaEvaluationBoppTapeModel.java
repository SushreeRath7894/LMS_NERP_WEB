package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QaEvaluationBoppTapeModel {

	private String type;
	private String createdBy;
	private String organization;
	private String orgDivision;

	private String num;
	private String num1;
	private String num2;
	private String num3;
	private String num4;
	private String num5;

	private String evalutionId;
	private String refNo;
	private String issueNo;
	private String issuedDate;

	private String challanNo;
	private String receiptDate;
	private String sku;
	private String specificationNo;

	private String grrNo;
	private String samplingDate;
	private String supplier;
	private String quantityRcvd;

	private String checkingDate;
	private String quantityChkd;
	private String specification;
	private String parameter;

	private String rStatus;
	private String cCompliance;

	private String product;
	private String avg;

	private String sampleId;
	private String ctrNo;
	private String ctr1;
	private String ctr2;
	private String ctr3;
	private String ctr4;
	private String ctr5;
	private String ctr6;
	private String ctr7;
	private String ctr8;

	private String ctr9;
	private String ctr10;
	private String ctr11;
	private String ctr12;
	private String ctr13;
	private String ctr14;
	private String ctr15;
	private String ctr16;

	private String ctr17;
	private String ctr18;
	private String ctr19;
	private String ctr20;
	private String ctr21;
	private String ctr22;
	private String ctr23;
	private String ctr24;

	private String ctr25;
	private String ctr26;
	private String ctr27;
	private String ctr28;
	private String ctr29;
	private String ctr30;

	private String gridSlNo;
	private String paramSlNo;
	private String parmName;
	private String col1;
	private String col2;
	private String col3;
	private String col4;
	private String col5;
	private String col6;

	private String sampleDate;
	private String remarks;
	private String reviewedBy;
	private String analyzedBy;
	private String resultBy;

	private String mediaId;
	private String mediaDate;
	private String colNo;
	private String media;
	private String batchCode;
	private String expiryDate;
	private String qtyMedia;
	private String finalpH;
	private String beforeAdj;
	private String afterAdj;
	private String disQty;
	private String startTime;
	private String reachedTime;
	private String cycleTime;
	private String sterilization;
	private String indicator;

	private String imgName;
	private String imgUrl;
	private String fileUpload;

	private MultipartFile mulFile;
	private String docName;
	
	private String productTypeField;

	public QaEvaluationBoppTapeModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getNum1() {
		return num1;
	}

	public void setNum1(String num1) {
		this.num1 = num1;
	}

	public String getNum2() {
		return num2;
	}

	public void setNum2(String num2) {
		this.num2 = num2;
	}

	public String getNum3() {
		return num3;
	}

	public void setNum3(String num3) {
		this.num3 = num3;
	}

	public String getNum4() {
		return num4;
	}

	public void setNum4(String num4) {
		this.num4 = num4;
	}

	public String getNum5() {
		return num5;
	}

	public void setNum5(String num5) {
		this.num5 = num5;
	}

	public String getEvalutionId() {
		return evalutionId;
	}

	public void setEvalutionId(String evalutionId) {
		this.evalutionId = evalutionId;
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

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getrStatus() {
		return rStatus;
	}

	public void setrStatus(String rStatus) {
		this.rStatus = rStatus;
	}

	public String getcCompliance() {
		return cCompliance;
	}

	public void setcCompliance(String cCompliance) {
		this.cCompliance = cCompliance;
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getCtrNo() {
		return ctrNo;
	}

	public void setCtrNo(String ctrNo) {
		this.ctrNo = ctrNo;
	}

	public String getCtr1() {
		return ctr1;
	}

	public void setCtr1(String ctr1) {
		this.ctr1 = ctr1;
	}

	public String getCtr2() {
		return ctr2;
	}

	public void setCtr2(String ctr2) {
		this.ctr2 = ctr2;
	}

	public String getCtr3() {
		return ctr3;
	}

	public void setCtr3(String ctr3) {
		this.ctr3 = ctr3;
	}

	public String getCtr4() {
		return ctr4;
	}

	public void setCtr4(String ctr4) {
		this.ctr4 = ctr4;
	}

	public String getCtr5() {
		return ctr5;
	}

	public void setCtr5(String ctr5) {
		this.ctr5 = ctr5;
	}

	public String getCtr6() {
		return ctr6;
	}

	public void setCtr6(String ctr6) {
		this.ctr6 = ctr6;
	}

	public String getCtr7() {
		return ctr7;
	}

	public void setCtr7(String ctr7) {
		this.ctr7 = ctr7;
	}

	public String getCtr8() {
		return ctr8;
	}

	public void setCtr8(String ctr8) {
		this.ctr8 = ctr8;
	}

	public String getCtr9() {
		return ctr9;
	}

	public void setCtr9(String ctr9) {
		this.ctr9 = ctr9;
	}

	public String getCtr10() {
		return ctr10;
	}

	public void setCtr10(String ctr10) {
		this.ctr10 = ctr10;
	}

	public String getCtr11() {
		return ctr11;
	}

	public void setCtr11(String ctr11) {
		this.ctr11 = ctr11;
	}

	public String getCtr12() {
		return ctr12;
	}

	public void setCtr12(String ctr12) {
		this.ctr12 = ctr12;
	}

	public String getCtr13() {
		return ctr13;
	}

	public void setCtr13(String ctr13) {
		this.ctr13 = ctr13;
	}

	public String getCtr14() {
		return ctr14;
	}

	public void setCtr14(String ctr14) {
		this.ctr14 = ctr14;
	}

	public String getCtr15() {
		return ctr15;
	}

	public void setCtr15(String ctr15) {
		this.ctr15 = ctr15;
	}

	public String getCtr16() {
		return ctr16;
	}

	public void setCtr16(String ctr16) {
		this.ctr16 = ctr16;
	}

	public String getCtr17() {
		return ctr17;
	}

	public void setCtr17(String ctr17) {
		this.ctr17 = ctr17;
	}

	public String getCtr18() {
		return ctr18;
	}

	public void setCtr18(String ctr18) {
		this.ctr18 = ctr18;
	}

	public String getCtr19() {
		return ctr19;
	}

	public void setCtr19(String ctr19) {
		this.ctr19 = ctr19;
	}

	public String getCtr20() {
		return ctr20;
	}

	public void setCtr20(String ctr20) {
		this.ctr20 = ctr20;
	}

	public String getCtr21() {
		return ctr21;
	}

	public void setCtr21(String ctr21) {
		this.ctr21 = ctr21;
	}

	public String getCtr22() {
		return ctr22;
	}

	public void setCtr22(String ctr22) {
		this.ctr22 = ctr22;
	}

	public String getCtr23() {
		return ctr23;
	}

	public void setCtr23(String ctr23) {
		this.ctr23 = ctr23;
	}

	public String getCtr24() {
		return ctr24;
	}

	public void setCtr24(String ctr24) {
		this.ctr24 = ctr24;
	}

	public String getCtr25() {
		return ctr25;
	}

	public void setCtr25(String ctr25) {
		this.ctr25 = ctr25;
	}

	public String getCtr26() {
		return ctr26;
	}

	public void setCtr26(String ctr26) {
		this.ctr26 = ctr26;
	}

	public String getCtr27() {
		return ctr27;
	}

	public void setCtr27(String ctr27) {
		this.ctr27 = ctr27;
	}

	public String getCtr28() {
		return ctr28;
	}

	public void setCtr28(String ctr28) {
		this.ctr28 = ctr28;
	}

	public String getCtr29() {
		return ctr29;
	}

	public void setCtr29(String ctr29) {
		this.ctr29 = ctr29;
	}

	public String getCtr30() {
		return ctr30;
	}

	public void setCtr30(String ctr30) {
		this.ctr30 = ctr30;
	}

	public String getGridSlNo() {
		return gridSlNo;
	}

	public void setGridSlNo(String gridSlNo) {
		this.gridSlNo = gridSlNo;
	}

	public String getParamSlNo() {
		return paramSlNo;
	}

	public void setParamSlNo(String paramSlNo) {
		this.paramSlNo = paramSlNo;
	}

	public String getParmName() {
		return parmName;
	}

	public void setParmName(String parmName) {
		this.parmName = parmName;
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

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getSampleDate() {
		return sampleDate;
	}

	public void setSampleDate(String sampleDate) {
		this.sampleDate = sampleDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReviewedBy() {
		return reviewedBy;
	}

	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	public String getAnalyzedBy() {
		return analyzedBy;
	}

	public void setAnalyzedBy(String analyzedBy) {
		this.analyzedBy = analyzedBy;
	}

	public String getResultBy() {
		return resultBy;
	}

	public void setResultBy(String resultBy) {
		this.resultBy = resultBy;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaDate() {
		return mediaDate;
	}

	public void setMediaDate(String mediaDate) {
		this.mediaDate = mediaDate;
	}

	public String getColNo() {
		return colNo;
	}

	public void setColNo(String colNo) {
		this.colNo = colNo;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getQtyMedia() {
		return qtyMedia;
	}

	public void setQtyMedia(String qtyMedia) {
		this.qtyMedia = qtyMedia;
	}

	public String getFinalpH() {
		return finalpH;
	}

	public void setFinalpH(String finalpH) {
		this.finalpH = finalpH;
	}

	public String getBeforeAdj() {
		return beforeAdj;
	}

	public void setBeforeAdj(String beforeAdj) {
		this.beforeAdj = beforeAdj;
	}

	public String getAfterAdj() {
		return afterAdj;
	}

	public void setAfterAdj(String afterAdj) {
		this.afterAdj = afterAdj;
	}

	public String getDisQty() {
		return disQty;
	}

	public void setDisQty(String disQty) {
		this.disQty = disQty;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getReachedTime() {
		return reachedTime;
	}

	public void setReachedTime(String reachedTime) {
		this.reachedTime = reachedTime;
	}

	public String getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(String cycleTime) {
		this.cycleTime = cycleTime;
	}

	public String getSterilization() {
		return sterilization;
	}

	public void setSterilization(String sterilization) {
		this.sterilization = sterilization;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public String getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(String fileUpload) {
		this.fileUpload = fileUpload;
	}

	public MultipartFile getMulFile() {
		return mulFile;
	}

	public void setMulFile(MultipartFile mulFile) {
		this.mulFile = mulFile;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getProductTypeField() {
		return productTypeField;
	}

	public void setProductTypeField(String productTypeField) {
		this.productTypeField = productTypeField;
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
