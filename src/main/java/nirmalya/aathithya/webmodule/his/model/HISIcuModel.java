package nirmalya.aathithya.webmodule.his.model;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
public class HISIcuModel {

	private String icu;
	private String icuName;
	private String propertyFloorType;
	private String icuDes;
	private String icuStatus;
	private String icuOrg;
	private String icuDiv;
	
	
	public HISIcuModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String getIcu() {
		return icu;
	}

	public void setIcu(String icu) {
		this.icu = icu;
	}


	public String getIcuName() {
		return icuName;
	}
	public void setIcuName(String icuName) {
		this.icuName = icuName;
	}
	
	
	public String getPropertyFloorType() {
		return propertyFloorType;
	}

	public void setPropertyFloorType(String propertyFloorType) {
		this.propertyFloorType = propertyFloorType;
	}

	public String getIcuDes() {
		return icuDes;
	}
	public void setIcuDes(String icuDes) {
		this.icuDes = icuDes;
	}
	public String getIcuStatus() {
		return icuStatus;
	}
	public void setIcuStatus(String icuStatus) {
		this.icuStatus = icuStatus;
	}
	public String getIcuOrg() {
		return icuOrg;
	}
	public void setIcuOrg(String icuOrg) {
		this.icuOrg = icuOrg;
	}
	public String getIcuDiv() {
		return icuDiv;
	}
	public void setIcuDiv(String icuDiv) {
		this.icuDiv = icuDiv;
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
