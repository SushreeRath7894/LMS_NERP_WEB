package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QaWcrModel {
	
	
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	private String s0;
	private String s1;
	private String s2;
	private String s3;
	private String s4;
	private String s5;
	private String s6;
	private String s7;
	private String s8;
	private String s9;
	private String s10;
	private String s11;
	
	private String leakers1;
	private String leakers2;
	private String leakers3;
	private String leakers4;
	private String leakers5;
	private String leakers6;
	private String leakers7;
	private String leakers8;
	private String leakers9;
	private String leakers10;
	
	
	
	private String noOfPouch1;
	private String noOfPouch2;
	private String noOfPouch3;
	private String noOfPouch4;
	private String noOfPouch5;
	private String noOfPouch6;
	private String noOfPouch7;
	private String noOfPouch8;
	private String noOfPouch9;
	private String noOfPouch10;
	
	private String wcrId;
	
	private String pdate;
	private String shift;
	private String production;
	private String packing;
	
	
	private String minwt;
	private String maxwt;
	private String avgwt;
	private String sd;
	
	private String red;
	private String amber;
	
	private String details;
	private String type;
	
	private String sampleSizePouch;
	private String sampleSizePowder;
	
	
	private String stt;
	
	
	
	public QaWcrModel() {
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


	public String getS0() {
		return s0;
	}


	public void setS0(String s0) {
		this.s0 = s0;
	}


	public String getS1() {
		return s1;
	}


	public void setS1(String s1) {
		this.s1 = s1;
	}


	public String getS2() {
		return s2;
	}


	public void setS2(String s2) {
		this.s2 = s2;
	}


	public String getS3() {
		return s3;
	}


	public void setS3(String s3) {
		this.s3 = s3;
	}


	public String getS4() {
		return s4;
	}


	public void setS4(String s4) {
		this.s4 = s4;
	}


	public String getS5() {
		return s5;
	}


	public void setS5(String s5) {
		this.s5 = s5;
	}


	public String getS6() {
		return s6;
	}


	public void setS6(String s6) {
		this.s6 = s6;
	}


	public String getS7() {
		return s7;
	}


	public void setS7(String s7) {
		this.s7 = s7;
	}


	public String getS8() {
		return s8;
	}


	public void setS8(String s8) {
		this.s8 = s8;
	}


	public String getS9() {
		return s9;
	}


	public void setS9(String s9) {
		this.s9 = s9;
	}


	public String getS10() {
		return s10;
	}


	public void setS10(String s10) {
		this.s10 = s10;
	}


	public String getS11() {
		return s11;
	}


	public void setS11(String s11) {
		this.s11 = s11;
	}


	public String getLeakers1() {
		return leakers1;
	}


	public void setLeakers1(String leakers1) {
		this.leakers1 = leakers1;
	}


	public String getLeakers2() {
		return leakers2;
	}


	public void setLeakers2(String leakers2) {
		this.leakers2 = leakers2;
	}


	public String getLeakers3() {
		return leakers3;
	}


	public void setLeakers3(String leakers3) {
		this.leakers3 = leakers3;
	}


	public String getLeakers4() {
		return leakers4;
	}


	public void setLeakers4(String leakers4) {
		this.leakers4 = leakers4;
	}


	public String getLeakers5() {
		return leakers5;
	}


	public void setLeakers5(String leakers5) {
		this.leakers5 = leakers5;
	}


	public String getLeakers6() {
		return leakers6;
	}


	public void setLeakers6(String leakers6) {
		this.leakers6 = leakers6;
	}


	public String getLeakers7() {
		return leakers7;
	}


	public void setLeakers7(String leakers7) {
		this.leakers7 = leakers7;
	}


	public String getLeakers8() {
		return leakers8;
	}


	public void setLeakers8(String leakers8) {
		this.leakers8 = leakers8;
	}


	public String getLeakers9() {
		return leakers9;
	}


	public void setLeakers9(String leakers9) {
		this.leakers9 = leakers9;
	}


	public String getLeakers10() {
		return leakers10;
	}


	public void setLeakers10(String leakers10) {
		this.leakers10 = leakers10;
	}


	public String getNoOfPouch1() {
		return noOfPouch1;
	}


	public void setNoOfPouch1(String noOfPouch1) {
		this.noOfPouch1 = noOfPouch1;
	}


	public String getNoOfPouch2() {
		return noOfPouch2;
	}


	public void setNoOfPouch2(String noOfPouch2) {
		this.noOfPouch2 = noOfPouch2;
	}


	public String getNoOfPouch3() {
		return noOfPouch3;
	}


	public void setNoOfPouch3(String noOfPouch3) {
		this.noOfPouch3 = noOfPouch3;
	}


	public String getNoOfPouch4() {
		return noOfPouch4;
	}


	public void setNoOfPouch4(String noOfPouch4) {
		this.noOfPouch4 = noOfPouch4;
	}


	public String getNoOfPouch5() {
		return noOfPouch5;
	}


	public void setNoOfPouch5(String noOfPouch5) {
		this.noOfPouch5 = noOfPouch5;
	}


	public String getNoOfPouch6() {
		return noOfPouch6;
	}


	public void setNoOfPouch6(String noOfPouch6) {
		this.noOfPouch6 = noOfPouch6;
	}


	public String getNoOfPouch7() {
		return noOfPouch7;
	}


	public void setNoOfPouch7(String noOfPouch7) {
		this.noOfPouch7 = noOfPouch7;
	}


	public String getNoOfPouch8() {
		return noOfPouch8;
	}


	public void setNoOfPouch8(String noOfPouch8) {
		this.noOfPouch8 = noOfPouch8;
	}


	public String getNoOfPouch9() {
		return noOfPouch9;
	}


	public void setNoOfPouch9(String noOfPouch9) {
		this.noOfPouch9 = noOfPouch9;
	}


	public String getNoOfPouch10() {
		return noOfPouch10;
	}


	public void setNoOfPouch10(String noOfPouch10) {
		this.noOfPouch10 = noOfPouch10;
	}


	public String getWcrId() {
		return wcrId;
	}


	public void setWcrId(String wcrId) {
		this.wcrId = wcrId;
	}


	public String getPdate() {
		return pdate;
	}


	public void setPdate(String pdate) {
		this.pdate = pdate;
	}


	public String getShift() {
		return shift;
	}


	public void setShift(String shift) {
		this.shift = shift;
	}


	public String getProduction() {
		return production;
	}


	public void setProduction(String production) {
		this.production = production;
	}


	public String getPacking() {
		return packing;
	}


	public void setPacking(String packing) {
		this.packing = packing;
	}


	public String getMinwt() {
		return minwt;
	}


	public void setMinwt(String minwt) {
		this.minwt = minwt;
	}


	public String getMaxwt() {
		return maxwt;
	}


	public void setMaxwt(String maxwt) {
		this.maxwt = maxwt;
	}


	public String getAvgwt() {
		return avgwt;
	}


	public void setAvgwt(String avgwt) {
		this.avgwt = avgwt;
	}


	public String getSd() {
		return sd;
	}


	public void setSd(String sd) {
		this.sd = sd;
	}


	public String getRed() {
		return red;
	}


	public void setRed(String red) {
		this.red = red;
	}


	public String getAmber() {
		return amber;
	}


	public void setAmber(String amber) {
		this.amber = amber;
	}

	
	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	public String getSampleSizePouch() {
		return sampleSizePouch;
	}


	public void setSampleSizePouch(String sampleSizePouch) {
		this.sampleSizePouch = sampleSizePouch;
	}


	public String getSampleSizePowder() {
		return sampleSizePowder;
	}


	public void setSampleSizePowder(String sampleSizePowder) {
		this.sampleSizePowder = sampleSizePowder;
	}
	
	public String getStt() {
		return stt;
	}


	public void setStt(String stt) {
		this.stt = stt;
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
