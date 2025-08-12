package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AreaLineClearanceWebModel {

	private String packId;
	
	private String changeOver;
	private String coFrom;
	private String coTo;

	private String slno;
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
	
	
	private String time;
	private String mixingTime;

	public AreaLineClearanceWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
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

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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
