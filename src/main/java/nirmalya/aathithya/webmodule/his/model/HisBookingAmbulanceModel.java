package nirmalya.aathithya.webmodule.his.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HisBookingAmbulanceModel {

	private String bookingId;
	private String patientId;
	private String patientName;
	private String age;
	private String mobNo;
	private String isdate;
	private String time;
	private String mobNo2;
	private String countryid;
	private String stateid;
	private String dist;
	private String cityid;
	private String pincode;
	private String add;
	private String reasonambulance;
	private String org;
	private String div;
	private String createdBy;
	private String createdOn;
	private String status;
	private String gender;
	private String typeAmb;
	private String reqAmb;
	private String amublanceNo;
	private String driverId;
	private String type;
	private String dependantList;
	private String department;
	private String email;
	private String occupation;
	private String officeName;
	private String income;
	private String officeAddress;
	private String recommended;
	private String description;
	private String nationality;
	private String insuranceName;
	private String diagnosis;

	private List<HisBookingAmbulanceListModel> ambulancelist;

	public HisBookingAmbulanceModel() {
		super();
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getIsdate() {
		return isdate;
	}

	public void setIsdate(String isdate) {
		this.isdate = isdate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCountryid() {
		return countryid;
	}

	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}

	public String getStateid() {
		return stateid;
	}

	public void setStateid(String stateid) {
		this.stateid = stateid;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMobNo2() {
		return mobNo2;
	}

	public void setMobNo2(String mobNo2) {
		this.mobNo2 = mobNo2;
	}

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getReasonambulance() {
		return reasonambulance;
	}

	public void setReasonambulance(String reasonambulance) {
		this.reasonambulance = reasonambulance;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTypeAmb() {
		return typeAmb;
	}

	public void setTypeAmb(String typeAmb) {
		this.typeAmb = typeAmb;
	}

	public String getReqAmb() {
		return reqAmb;
	}

	public void setReqAmb(String reqAmb) {
		this.reqAmb = reqAmb;
	}

	public List<HisBookingAmbulanceListModel> getAmbulancelist() {
		return ambulancelist;
	}

	public void setAmbulancelist(List<HisBookingAmbulanceListModel> ambulancelist) {
		this.ambulancelist = ambulancelist;
	}

	public String getAmublanceNo() {
		return amublanceNo;
	}

	public void setAmublanceNo(String amublanceNo) {
		this.amublanceNo = amublanceNo;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDependantList() {
		return dependantList;
	}

	public void setDependantList(String dependantList) {
		this.dependantList = dependantList;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getRecommended() {
		return recommended;
	}

	public void setRecommended(String recommended) {
		this.recommended = recommended;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
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
