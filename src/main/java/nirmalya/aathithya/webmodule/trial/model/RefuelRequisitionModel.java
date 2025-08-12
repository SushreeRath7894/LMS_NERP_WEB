package nirmalya.aathithya.webmodule.trial.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RefuelRequisitionModel {
	
	private String refuelReqId;
	private String vehicleRegno;
	private String fuelType;
	private Double currentOdometer;
	private String quantity;
	private String fuelStationId;
	private String fuelStationName;
	private Double lastOdometer;
	private String status;
	private String createdBy;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;

	public String getRefuelReqId() {
		return refuelReqId;
	}

	public void setRefuelReqId(String refuelReqId) {
		this.refuelReqId = refuelReqId;
	}

	public String getVehicleRegno() {
		return vehicleRegno;
	}

	public void setVehicleRegno(String vehicleRegno) {
		this.vehicleRegno = vehicleRegno;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public Double getCurrentOdometer() {
		return currentOdometer;
	}

	public void setCurrentOdometer(Double currentOdometer) {
		this.currentOdometer = currentOdometer;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getFuelStationId() {
		return fuelStationId;
	}

	public void setFuelStationId(String fuelStationId) {
		this.fuelStationId = fuelStationId;
	}

	public String getFuelStationName() {
		return fuelStationName;
	}

	public void setFuelStationName(String fuelStationName) {
		this.fuelStationName = fuelStationName;
	}

	public Double getLastOdometer() {
		return lastOdometer;
	}

	public void setLastOdometer(Double lastOdometer) {
		this.lastOdometer = lastOdometer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
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
