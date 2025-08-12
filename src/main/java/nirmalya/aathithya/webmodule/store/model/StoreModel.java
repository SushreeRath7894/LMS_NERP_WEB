package nirmalya.aathithya.webmodule.store.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StoreModel {
	private String locationId;
	private String locationName;
	private String locationCode;
	private String locationType;
	public StoreModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getLocationId() {
		return locationId;
	}


	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}


	public String getLocationName() {
		return locationName;
	}


	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}


	public String getLocationCode() {
		return locationCode;
	}


	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}


	public String getLocationType() {
		return locationType;
	}


	public void setLocationType(String locationType) {
		this.locationType = locationType;
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
