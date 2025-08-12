package nirmalya.aathithya.webmodule.trial.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StationwiseRefuellingModel {

	private String slId;
	private String vehicleRegId;
	private String fuelTypeId;
	private String quantityId;
	private String unitPriceId;
	private String fuelStationId;
	private Double ododmeterId;
	private String requisitionId;
	private Double lastOdodmeterId;
	
	
	
	
	
	public Double getLastOdodmeterId() {
		return lastOdodmeterId;
	}
	public void setLastOdodmeterId(Double lastOdodmeterId) {
		this.lastOdodmeterId = lastOdodmeterId;
	}
	public String getSlId() {
		return slId;
	}
	public void setSlId(String slId) {
		this.slId = slId;
	}
	public String getVehicleRegId() {
		return vehicleRegId;
	}
	public void setVehicleRegId(String vehicleRegId) {
		this.vehicleRegId = vehicleRegId;
	}
	public String getFuelTypeId() {
		return fuelTypeId;
	}
	public void setFuelTypeId(String fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}
	public String getQuantityId() {
		return quantityId;
	}
	public void setQuantityId(String quantityId) {
		this.quantityId = quantityId;
	}
	public String getUnitPriceId() {
		return unitPriceId;
	}
	public void setUnitPriceId(String unitPriceId) {
		this.unitPriceId = unitPriceId;
	}
	public String getFuelStationId() {
		return fuelStationId;
	}
	public void setFuelStationId(String fuelStationId) {
		this.fuelStationId = fuelStationId;
	}
	public Double getOdodmeterId() {
		return ododmeterId;
	}
	public void setOdodmeterId(Double ododmeterId) {
		this.ododmeterId = ododmeterId;
	}
	public String getRequisitionId() {
		return requisitionId;
	}
	public void setRequisitionId(String requisitionId) {
		this.requisitionId = requisitionId;
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
