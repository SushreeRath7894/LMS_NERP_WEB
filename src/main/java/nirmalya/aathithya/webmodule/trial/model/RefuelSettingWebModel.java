package nirmalya.aathithya.webmodule.trial.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RefuelSettingWebModel {
	
private String rfsettingid;
	
	private String vehicleregno;
	private String drivername;
	private String driverphone;
	private String fueltype;
	private String idDate;
	private String rflimit;
	private String stationname;
	private String place;
	
	private Double budget;
	private Double maxunit;
	private Double cpercent;
	private Double kpunit;
	private Double odometerday;
	private Double odorefueling;
	private Double lreading;
	private Double lastunit;
	private Double utaken;
	
	private String createdBy;
	private String docName;
	private String scapply;
	
	private String docUrl;
	
	
	public String getRfsettingid() {
		return rfsettingid;
	}


	public void setRfsettingid(String rfsettingid) {
		this.rfsettingid = rfsettingid;
	}


	public String getVehicleregno() {
		return vehicleregno;
	}

	public void setVehicleregno(String vehicleregno) {
		this.vehicleregno = vehicleregno;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}


	public String getDriverphone() {
		return driverphone;
	}

	public void setDriverphone(String driverphone) {
		this.driverphone = driverphone;
	}

	public String getFueltype() {
		return fueltype;
	}


	public void setFueltype(String fueltype) {
		this.fueltype = fueltype;
	}


	public String getIdDate() {
		return idDate;
	}


	public void setIdDate(String idDate) {
		this.idDate = idDate;
	}


	public String getRflimit() {
		return rflimit;
	}


	public void setRflimit(String rflimit) {
		this.rflimit = rflimit;
	}


	public String getStationname() {
		return stationname;
	}



	public void setStationname(String stationname) {
		this.stationname = stationname;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public Double getBudget() {
		return budget;
	}


	public void setBudget(Double budget) {
		this.budget = budget;
	}


	public Double getMaxunit() {
		return maxunit;
	}

	public void setMaxunit(Double maxunit) {
		this.maxunit = maxunit;
	}


	public Double getCpercent() {
		return cpercent;
	}


	public void setCpercent(Double cpercent) {
		this.cpercent = cpercent;
	}


	public Double getKpunit() {
		return kpunit;
	}


	public void setKpunit(Double kpunit) {
		this.kpunit = kpunit;
	}


	public Double getOdometerday() {
		return odometerday;
	}


	public void setOdometerday(Double odometerday) {
		this.odometerday = odometerday;
	}


	public Double getOdorefueling() {
		return odorefueling;
	}


	public void setOdorefueling(Double odorefueling) {
		this.odorefueling = odorefueling;
	}


	public Double getLreading() {
		return lreading;
	}


	public void setLreading(Double lreading) {
		this.lreading = lreading;
	}


	public Double getLastunit() {
		return lastunit;
	}


	public void setLastunit(Double lastunit) {
		this.lastunit = lastunit;
	}

	public Double getUtaken() {
		return utaken;
	}

	public void setUtaken(Double utaken) {
		this.utaken = utaken;
	}


	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getDocName() {
		return docName;
	}


	public void setDocName(String docName) {
		this.docName = docName;
	}


	public String getScapply() {
		return scapply;
	}


	public void setScapply(String scapply) {
		this.scapply = scapply;
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


	public String getDocUrl() {
		return docUrl;
	}


	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

}
