package nirmalya.aathithya.webmodule.productionplan.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FemtoBlendingWebModel {

	private String packId;
	private String cldFrom;
	private String cldTo;

	private String slno;
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

	public FemtoBlendingWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPackId() {
		return packId;
	}

	public void setPackId(String packId) {
		this.packId = packId;
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

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
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
