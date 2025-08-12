package nirmalya.aathithya.webmodule.qa.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HorlicksAnalysisDetailsOneWebModel {

	private String ironCont;
	private String bno1;
	private String to1;
	private String bno2;
	private String spec1;
	private String blank1;
	private String param;
	private String bno3;
	private String ressult1;
	
	public HorlicksAnalysisDetailsOneWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getIronCont() {
		return ironCont;
	}


	public void setIronCont(String ironCont) {
		this.ironCont = ironCont;
	}


	public String getBno1() {
		return bno1;
	}


	public void setBno1(String bno1) {
		this.bno1 = bno1;
	}


	public String getTo1() {
		return to1;
	}


	public void setTo1(String to1) {
		this.to1 = to1;
	}


	public String getBno2() {
		return bno2;
	}


	public void setBno2(String bno2) {
		this.bno2 = bno2;
	}


	public String getSpec1() {
		return spec1;
	}


	public void setSpec1(String spec1) {
		this.spec1 = spec1;
	}


	public String getBlank1() {
		return blank1;
	}


	public void setBlank1(String blank1) {
		this.blank1 = blank1;
	}


	public String getParam() {
		return param;
	}


	public void setParam(String param) {
		this.param = param;
	}


	public String getBno3() {
		return bno3;
	}


	public void setBno3(String bno3) {
		this.bno3 = bno3;
	}


	public String getRessult1() {
		return ressult1;
	}


	public void setRessult1(String ressult1) {
		this.ressult1 = ressult1;
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
