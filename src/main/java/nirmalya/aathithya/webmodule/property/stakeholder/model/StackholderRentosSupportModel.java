package nirmalya.aathithya.webmodule.property.stakeholder.model;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StackholderRentosSupportModel {
	private String rentId;
	private String propertyno;
	private String tenant;
	private String month;
	private String price;
	
	public String getRentId() {
		return rentId;
	}

	public void setRentId(String rentId) {
		this.rentId = rentId;
	}
	
	public String getPropertyno() {
		return propertyno;
	}
	public void setPropertyno(String propertyno) {
		this.propertyno = propertyno;
	}
	public String getTenant() {
		return tenant;
	}
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
