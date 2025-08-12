package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductionPlanningTotalmanpowerList {
	private String date;
	private String shift;
	private String opr;
	private String tcn;
	private String hpr;
	private String mtn;
	private String oth;
	private String tpowerid;

	public String getTpowerid() {
		return tpowerid;
	}

	public void setTpowerid(String tpowerid) {
		this.tpowerid = tpowerid;
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

	public String getOpr() {
		return opr;
	}

	public void setOpr(String opr) {
		this.opr = opr;
	}

	public String getTcn() {
		return tcn;
	}

	public void setTcn(String tcn) {
		this.tcn = tcn;
	}

	public String getHpr() {
		return hpr;
	}

	public void setHpr(String hpr) {
		this.hpr = hpr;
	}

	public String getMtn() {
		return mtn;
	}

	public void setMtn(String mtn) {
		this.mtn = mtn;
	}

	public String getOth() {
		return oth;
	}

	public void setOth(String oth) {
		this.oth = oth;
	}

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
