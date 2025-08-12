package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LmrLogPdfModel {
	
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
	
	//Part B
	
	private String sbdSts;
	private String noBags;
	private String qtySugar;

	private String slno;
	private String batchNoNDVP;
	private String packNoNDVP;
	private String netWtNDVP;
	private String bbNoNDVP;
	private String batchNoAALP;
	private String packNoAALP;
	private String netWtAALP;
	private String bbNoAALP;
	private String oprtSign;
	
	
	//Blending 
	
	private String cldFrom;
	private String cldTo;
	//private String slno;
	private String rmName;
	private String quantity;
	private String lot1;
	private String lot2;
	private String lot3;
	private String lot4;
	private String lot5;
	private String lot6;
	private String lot7;

	private String type;
	private String tolerance;
	
	// Weighing Scale
	
	//private String slno;
	private String time;
	private String wScale;
	private String eqipRange;
	private String lc;
	private String stdWt;
	private String actWt;
	private String acptLimit;
	private String status;
	private String sign;
	
	// Area Line Clearance
	
	
	private String changeOver;
	private String coFrom;
	private String coTo;

	//private String slno;
	private String area;
	private String parameter;
	private String acceptance1;
	private String acceptance2;  
	private String acceptance3;

	private String slnoSub;
	private String areaSub;
	private String vitaminRoom;
	private String dumping;
	private String sh;
	private String ribbon;
	private String wh;
	private String rmSilo;
	
	//private String time;
	private String mixingTime;
	
	public LmrLogPdfModel() {
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

	public String getSbdSts() {
		return sbdSts;
	}

	public void setSbdSts(String sbdSts) {
		this.sbdSts = sbdSts;
	}

	public String getNoBags() {
		return noBags;
	}

	public void setNoBags(String noBags) {
		this.noBags = noBags;
	}

	public String getQtySugar() {
		return qtySugar;
	}

	public void setQtySugar(String qtySugar) {
		this.qtySugar = qtySugar;
	}

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}

	public String getBatchNoNDVP() {
		return batchNoNDVP;
	}

	public void setBatchNoNDVP(String batchNoNDVP) {
		this.batchNoNDVP = batchNoNDVP;
	}

	public String getPackNoNDVP() {
		return packNoNDVP;
	}

	public void setPackNoNDVP(String packNoNDVP) {
		this.packNoNDVP = packNoNDVP;
	}

	public String getNetWtNDVP() {
		return netWtNDVP;
	}

	public void setNetWtNDVP(String netWtNDVP) {
		this.netWtNDVP = netWtNDVP;
	}

	public String getBbNoNDVP() {
		return bbNoNDVP;
	}

	public void setBbNoNDVP(String bbNoNDVP) {
		this.bbNoNDVP = bbNoNDVP;
	}

	public String getBatchNoAALP() {
		return batchNoAALP;
	}

	public void setBatchNoAALP(String batchNoAALP) {
		this.batchNoAALP = batchNoAALP;
	}

	public String getPackNoAALP() {
		return packNoAALP;
	}

	public void setPackNoAALP(String packNoAALP) {
		this.packNoAALP = packNoAALP;
	}

	public String getNetWtAALP() {
		return netWtAALP;
	}

	public void setNetWtAALP(String netWtAALP) {
		this.netWtAALP = netWtAALP;
	}

	public String getBbNoAALP() {
		return bbNoAALP;
	}

	public void setBbNoAALP(String bbNoAALP) {
		this.bbNoAALP = bbNoAALP;
	}

	public String getOprtSign() {
		return oprtSign;
	}

	public void setOprtSign(String oprtSign) {
		this.oprtSign = oprtSign;
	}

	public String getCldFrom() {
		return cldFrom;
	}

	public void setCldFrom(String cldFrom) {
		this.cldFrom = cldFrom;
	}

	public String getCldTo() {
		return cldTo;
	}

	public void setCldTo(String cldTo) {
		this.cldTo = cldTo;
	}

	public String getRmName() {
		return rmName;
	}

	public void setRmName(String rmName) {
		this.rmName = rmName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getLot1() {
		return lot1;
	}

	public void setLot1(String lot1) {
		this.lot1 = lot1;
	}

	public String getLot2() {
		return lot2;
	}

	public void setLot2(String lot2) {
		this.lot2 = lot2;
	}

	public String getLot3() {
		return lot3;
	}

	public void setLot3(String lot3) {
		this.lot3 = lot3;
	}

	public String getLot4() {
		return lot4;
	}

	public void setLot4(String lot4) {
		this.lot4 = lot4;
	}

	public String getLot5() {
		return lot5;
	}

	public void setLot5(String lot5) {
		this.lot5 = lot5;
	}

	public String getLot6() {
		return lot6;
	}

	public void setLot6(String lot6) {
		this.lot6 = lot6;
	}

	public String getLot7() {
		return lot7;
	}

	public void setLot7(String lot7) {
		this.lot7 = lot7;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTolerance() {
		return tolerance;
	}

	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getwScale() {
		return wScale;
	}

	public void setwScale(String wScale) {
		this.wScale = wScale;
	}

	public String getEqipRange() {
		return eqipRange;
	}

	public void setEqipRange(String eqipRange) {
		this.eqipRange = eqipRange;
	}

	public String getLc() {
		return lc;
	}

	public void setLc(String lc) {
		this.lc = lc;
	}

	public String getStdWt() {
		return stdWt;
	}

	public void setStdWt(String stdWt) {
		this.stdWt = stdWt;
	}

	public String getActWt() {
		return actWt;
	}

	public void setActWt(String actWt) {
		this.actWt = actWt;
	}

	public String getAcptLimit() {
		return acptLimit;
	}

	public void setAcptLimit(String acptLimit) {
		this.acptLimit = acptLimit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getChangeOver() {
		return changeOver;
	}

	public void setChangeOver(String changeOver) {
		this.changeOver = changeOver;
	}

	public String getCoFrom() {
		return coFrom;
	}

	public void setCoFrom(String coFrom) {
		this.coFrom = coFrom;
	}

	public String getCoTo() {
		return coTo;
	}

	public void setCoTo(String coTo) {
		this.coTo = coTo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getAcceptance1() {
		return acceptance1;
	}

	public void setAcceptance1(String acceptance1) {
		this.acceptance1 = acceptance1;
	}

	public String getAcceptance2() {
		return acceptance2;
	}

	public void setAcceptance2(String acceptance2) {
		this.acceptance2 = acceptance2;
	}

	public String getAcceptance3() {
		return acceptance3;
	}

	public void setAcceptance3(String acceptance3) {
		this.acceptance3 = acceptance3;
	}

	public String getSlnoSub() {
		return slnoSub;
	}

	public void setSlnoSub(String slnoSub) {
		this.slnoSub = slnoSub;
	}

	public String getAreaSub() {
		return areaSub;
	}

	public void setAreaSub(String areaSub) {
		this.areaSub = areaSub;
	}

	public String getVitaminRoom() {
		return vitaminRoom;
	}

	public void setVitaminRoom(String vitaminRoom) {
		this.vitaminRoom = vitaminRoom;
	}

	public String getDumping() {
		return dumping;
	}

	public void setDumping(String dumping) {
		this.dumping = dumping;
	}

	public String getSh() {
		return sh;
	}

	public void setSh(String sh) {
		this.sh = sh;
	}

	public String getRibbon() {
		return ribbon;
	}

	public void setRibbon(String ribbon) {
		this.ribbon = ribbon;
	}

	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}

	public String getRmSilo() {
		return rmSilo;
	}

	public void setRmSilo(String rmSilo) {
		this.rmSilo = rmSilo;
	}
	
	public String getMixingTime() {
		return mixingTime;
	}

	public void setMixingTime(String mixingTime) {
		this.mixingTime = mixingTime;
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
