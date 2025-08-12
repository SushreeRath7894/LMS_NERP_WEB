package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PartBIngredientWebModel {

	private String packId;
	private String shift;
	private String date;
	private String batchNo;
	
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

	// List<ManageShopFloorModel> itemDtls;

	public PartBIngredientWebModel() {
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
