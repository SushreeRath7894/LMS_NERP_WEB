package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WeighingScaleWebModel {

	private String packId;

	private String slno;
	private String time;
	private String wScale;
	private String eqipRange;
	private String lc;
	private String stdWt;
	private String actWt;
	private String acptLimit;
	private String status;
	private String sign;

	public WeighingScaleWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
	}

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
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
