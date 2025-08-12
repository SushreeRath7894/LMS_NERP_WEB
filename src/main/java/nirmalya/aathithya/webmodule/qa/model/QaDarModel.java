package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QaDarModel {
	
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	private String type;
	
	private String darId;
	private String date;
	private String shift ;
	private String product1;
	private String molecularWeight;
	private String molarityHyamine;
	
	private String vct;
	private String uct;
	private String ph;
	private String time;
	private String dfr;
	private String bd;
	private String wInsoluble;
	private String retention12;
	private String retention60;
	private String passing85;

	private String volHyamine11;
	private String volHyamine12;
	private String volHyamine13;
	private String ad11;
	private String ad12;
	private String ad13;
	private String avgAD1;
	private String totalAD1;
	private String rod1;
	
	private String ad21;
	private String ad22;
	private String ad23;
	private String volHyamine21;
	private String volHyamine22;
	private String volHyamine23;
	private String avgAD2;
	private String totalAD2;
	private String rod2;
	
	private String sampleWt1;
	private String sampleWt2;
	private String sampleWt3;
	private String sampleWt4;
	
	private String sku1;
	private String sku2;
	private String sku3;
	
	
	private String time1;
	private String batchNo;
	private String sampleHymine;
	private String sampleAd;
	private String weightOfDisc;
	private String weightOfTotal;
	private String weightOfLoss;
	private String sampleMc;
	
	
	private String compositeHymine;
	private String compositeAd;
	private String compositeWeightOfDisc;
	private String compositeWeightOfTotal;
	private String compositeWeightLoss;
	private String compositeMc;
	private String compositeBd;
	private String compositeDfr;
	private String compositePh;
	private String compositeCompatibility;
	private String compositeRetention;
	private String compositeAppearance;
	private String compositeColour;
	private String compositeOdour;
	private String compositeVisualCue;
	
	
	private String mcNo;
	private String mcName;
	
	
	private String alkalinity;
	private String zeolity;
	private String av50;
	private String slopeFactor;
	private String sampleWt5;
	private String abs;
	
	private List<String> machineNumber;
	
	private String sampleWtForMC;
	private String compositeSampleWtForMC;
	

	
	public QaDarModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	


	public List<String> getMachineNumber() {
		return machineNumber;
	}





	public void setMachineNumber(List<String> machineNumber) {
		this.machineNumber = machineNumber;
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
	
	
	
	public String getDarId() {
		return darId;
	}


	public void setDarId(String darId) {
		this.darId = darId;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getShift() {
		return shift;
	}


	public void setShift(String shift) {
		this.shift = shift;
	}


	public String getProduct1() {
		return product1;
	}


	public void setProduct1(String product1) {
		this.product1 = product1;
	}


	public String getMolecularWeight() {
		return molecularWeight;
	}


	public void setMolecularWeight(String molecularWeight) {
		this.molecularWeight = molecularWeight;
	}


	public String getMolarityHyamine() {
		return molarityHyamine;
	}


	public void setMolarityHyamine(String molarityHyamine) {
		this.molarityHyamine = molarityHyamine;
	}


	public String getVct() {
		return vct;
	}


	public void setVct(String vct) {
		this.vct = vct;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getDfr() {
		return dfr;
	}


	public void setDfr(String dfr) {
		this.dfr = dfr;
	}


	public String getBd() {
		return bd;
	}


	public void setBd(String bd) {
		this.bd = bd;
	}


	public String getwInsoluble() {
		return wInsoluble;
	}


	public void setwInsoluble(String wInsoluble) {
		this.wInsoluble = wInsoluble;
	}


	public String getRetention12() {
		return retention12;
	}


	public void setRetention12(String retention12) {
		this.retention12 = retention12;
	}


	public String getRetention60() {
		return retention60;
	}


	public void setRetention60(String retention60) {
		this.retention60 = retention60;
	}


	public String getPassing85() {
		return passing85;
	}


	public void setPassing85(String passing85) {
		this.passing85 = passing85;
	}


	public String getVolHyamine11() {
		return volHyamine11;
	}


	public void setVolHyamine11(String volHyamine11) {
		this.volHyamine11 = volHyamine11;
	}


	public String getVolHyamine12() {
		return volHyamine12;
	}


	public void setVolHyamine12(String volHyamine12) {
		this.volHyamine12 = volHyamine12;
	}


	public String getVolHyamine13() {
		return volHyamine13;
	}


	public void setVolHyamine13(String volHyamine13) {
		this.volHyamine13 = volHyamine13;
	}


	public String getAd11() {
		return ad11;
	}


	public void setAd11(String ad11) {
		this.ad11 = ad11;
	}


	public String getAd12() {
		return ad12;
	}


	public void setAd12(String ad12) {
		this.ad12 = ad12;
	}


	public String getAd13() {
		return ad13;
	}


	public void setAd13(String ad13) {
		this.ad13 = ad13;
	}


	public String getAvgAD1() {
		return avgAD1;
	}


	public void setAvgAD1(String avgAD1) {
		this.avgAD1 = avgAD1;
	}


	public String getTotalAD1() {
		return totalAD1;
	}


	public void setTotalAD1(String totalAD1) {
		this.totalAD1 = totalAD1;
	}


	public String getRod1() {
		return rod1;
	}


	public void setRod1(String rod1) {
		this.rod1 = rod1;
	}


	public String getAd21() {
		return ad21;
	}


	public void setAd21(String ad21) {
		this.ad21 = ad21;
	}


	public String getAd22() {
		return ad22;
	}


	public void setAd22(String ad22) {
		this.ad22 = ad22;
	}


	public String getAd23() {
		return ad23;
	}


	public void setAd23(String ad23) {
		this.ad23 = ad23;
	}


	public String getVolHyamine21() {
		return volHyamine21;
	}


	public void setVolHyamine21(String volHyamine21) {
		this.volHyamine21 = volHyamine21;
	}


	public String getVolHyamine22() {
		return volHyamine22;
	}


	public void setVolHyamine22(String volHyamine22) {
		this.volHyamine22 = volHyamine22;
	}


	public String getVolHyamine23() {
		return volHyamine23;
	}


	public void setVolHyamine23(String volHyamine23) {
		this.volHyamine23 = volHyamine23;
	}


	public String getAvgAD2() {
		return avgAD2;
	}


	public void setAvgAD2(String avgAD2) {
		this.avgAD2 = avgAD2;
	}


	public String getTotalAD2() {
		return totalAD2;
	}


	public void setTotalAD2(String totalAD2) {
		this.totalAD2 = totalAD2;
	}


	public String getRod2() {
		return rod2;
	}


	public void setRod2(String rod2) {
		this.rod2 = rod2;
	}


	public String getSampleWt1() {
		return sampleWt1;
	}


	public void setSampleWt1(String sampleWt1) {
		this.sampleWt1 = sampleWt1;
	}


	public String getSampleWt2() {
		return sampleWt2;
	}


	public void setSampleWt2(String sampleWt2) {
		this.sampleWt2 = sampleWt2;
	}


	public String getSampleWt3() {
		return sampleWt3;
	}


	public void setSampleWt3(String sampleWt3) {
		this.sampleWt3 = sampleWt3;
	}


	public String getSampleWt4() {
		return sampleWt4;
	}


	public void setSampleWt4(String sampleWt4) {
		this.sampleWt4 = sampleWt4;
	}


	public String getSku1() {
		return sku1;
	}


	public void setSku1(String sku1) {
		this.sku1 = sku1;
	}


	public String getSku2() {
		return sku2;
	}


	public void setSku2(String sku2) {
		this.sku2 = sku2;
	}


	public String getSku3() {
		return sku3;
	}


	public void setSku3(String sku3) {
		this.sku3 = sku3;
	}


	public String getTime1() {
		return time1;
	}


	public void setTime1(String time1) {
		this.time1 = time1;
	}


	public String getBatchNo() {
		return batchNo;
	}


	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}


	public String getSampleHymine() {
		return sampleHymine;
	}


	public void setSampleHymine(String sampleHymine) {
		this.sampleHymine = sampleHymine;
	}


	public String getSampleAd() {
		return sampleAd;
	}


	public void setSampleAd(String sampleAd) {
		this.sampleAd = sampleAd;
	}


	public String getWeightOfDisc() {
		return weightOfDisc;
	}


	public void setWeightOfDisc(String weightOfDisc) {
		this.weightOfDisc = weightOfDisc;
	}


	public String getWeightOfTotal() {
		return weightOfTotal;
	}


	public void setWeightOfTotal(String weightOfTotal) {
		this.weightOfTotal = weightOfTotal;
	}


	public String getWeightOfLoss() {
		return weightOfLoss;
	}


	public void setWeightOfLoss(String weightOfLoss) {
		this.weightOfLoss = weightOfLoss;
	}


	public String getSampleMc() {
		return sampleMc;
	}


	public void setSampleMc(String sampleMc) {
		this.sampleMc = sampleMc;
	}


	public String getCompositeHymine() {
		return compositeHymine;
	}


	public void setCompositeHymine(String compositeHymine) {
		this.compositeHymine = compositeHymine;
	}


	public String getCompositeAd() {
		return compositeAd;
	}


	public void setCompositeAd(String compositeAd) {
		this.compositeAd = compositeAd;
	}


	public String getCompositeWeightOfDisc() {
		return compositeWeightOfDisc;
	}


	public void setCompositeWeightOfDisc(String compositeWeightOfDisc) {
		this.compositeWeightOfDisc = compositeWeightOfDisc;
	}


	public String getCompositeWeightOfTotal() {
		return compositeWeightOfTotal;
	}


	public void setCompositeWeightOfTotal(String compositeWeightOfTotal) {
		this.compositeWeightOfTotal = compositeWeightOfTotal;
	}


	public String getCompositeWeightLoss() {
		return compositeWeightLoss;
	}


	public void setCompositeWeightLoss(String compositeWeightLoss) {
		this.compositeWeightLoss = compositeWeightLoss;
	}


	public String getCompositeMc() {
		return compositeMc;
	}


	public void setCompositeMc(String compositeMc) {
		this.compositeMc = compositeMc;
	}


	public String getCompositeBd() {
		return compositeBd;
	}


	public void setCompositeBd(String compositeBd) {
		this.compositeBd = compositeBd;
	}


	public String getCompositeDfr() {
		return compositeDfr;
	}


	public void setCompositeDfr(String compositeDfr) {
		this.compositeDfr = compositeDfr;
	}


	public String getCompositeCompatibility() {
		return compositeCompatibility;
	}


	public void setCompositeCompatibility(String compositeCompatibility) {
		this.compositeCompatibility = compositeCompatibility;
	}


	public String getCompositeRetention() {
		return compositeRetention;
	}


	public void setCompositeRetention(String compositeRetention) {
		this.compositeRetention = compositeRetention;
	}


	public String getCompositeAppearance() {
		return compositeAppearance;
	}


	public void setCompositeAppearance(String compositeAppearance) {
		this.compositeAppearance = compositeAppearance;
	}


	public String getCompositeColour() {
		return compositeColour;
	}


	public void setCompositeColour(String compositeColour) {
		this.compositeColour = compositeColour;
	}


	public String getCompositeOdour() {
		return compositeOdour;
	}


	public void setCompositeOdour(String compositeOdour) {
		this.compositeOdour = compositeOdour;
	}


	public String getCompositeVisualCue() {
		return compositeVisualCue;
	}


	public void setCompositeVisualCue(String compositeVisualCue) {
		this.compositeVisualCue = compositeVisualCue;
	}
	

	public String getMcNo() {
		return mcNo;
	}


	public void setMcNo(String mcNo) {
		this.mcNo = mcNo;
	}
	
	


	public String getMcName() {
		return mcName;
	}




	public void setMcName(String mcName) {
		this.mcName = mcName;
	}




	public String getUct() {
		return uct;
	}




	public void setUct(String uct) {
		this.uct = uct;
	}




	public String getPh() {
		return ph;
	}




	public void setPh(String ph) {
		this.ph = ph;
	}




	public String getCompositePh() {
		return compositePh;
	}




	public void setCompositePh(String compositePh) {
		this.compositePh = compositePh;
	}




	public String getAlkalinity() {
		return alkalinity;
	}




	public void setAlkalinity(String alkalinity) {
		this.alkalinity = alkalinity;
	}




	public String getZeolity() {
		return zeolity;
	}




	public void setZeolity(String zeolity) {
		this.zeolity = zeolity;
	}




	public String getAv50() {
		return av50;
	}




	public void setAv50(String av50) {
		this.av50 = av50;
	}




	public String getSlopeFactor() {
		return slopeFactor;
	}




	public void setSlopeFactor(String slopeFactor) {
		this.slopeFactor = slopeFactor;
	}




	public String getSampleWt5() {
		return sampleWt5;
	}




	public void setSampleWt5(String sampleWt5) {
		this.sampleWt5 = sampleWt5;
	}




	public String getAbs() {
		return abs;
	}




	public void setAbs(String abs) {
		this.abs = abs;
	}




	public String getSampleWtForMC() {
		return sampleWtForMC;
	}





	public void setSampleWtForMC(String sampleWtForMC) {
		this.sampleWtForMC = sampleWtForMC;
	}





	public String getCompositeSampleWtForMC() {
		return compositeSampleWtForMC;
	}





	public void setCompositeSampleWtForMC(String compositeSampleWtForMC) {
		this.compositeSampleWtForMC = compositeSampleWtForMC;
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
