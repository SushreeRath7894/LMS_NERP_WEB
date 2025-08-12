package nirmalya.aathithya.webmodule.his.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModelEmp;

public class HISPrescriptionWebModel {

	private String appointmentId;
	private String hospitalName;
	private String docName;
	private String docAddress;
	private String docMobile;
	private String docSpeciality;
	private String docQualification;

	private String patientId;
	private String patientName;
	private String gender;
	private String apointmentId;
	private String patientDob;
	private String patientAddress;
	private String prescriptionDate;
	private String patientAge;
	private String patientMob;
	private String prescriptionId;
	private String patientImage;

	private String prescriptionMedName;
	private String prescriptionMedType;
	private String prescriptionMedDay;
	private String prescriptionMedNoon;
	private String prescriptionMedNight;
	private String prescriptionMedDuration;
	private String prescriptionDosage;
	private String prescriptionStartDate;
	private String prescriptionTestName;
	private String prescriptionTestGroup;
	private String tick;
	private String cross;

	List<DropDownModelEmp> testData;

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocAddress() {
		return docAddress;
	}

	public void setDocAddress(String docAddress) {
		this.docAddress = docAddress;
	}

	public String getDocMobile() {
		return docMobile;
	}

	public void setDocMobile(String docMobile) {
		this.docMobile = docMobile;
	}

	public String getDocSpeciality() {
		return docSpeciality;
	}

	public void setDocSpeciality(String docSpeciality) {
		this.docSpeciality = docSpeciality;
	}

	public String getDocQualification() {
		return docQualification;
	}

	public void setDocQualification(String docQualification) {
		this.docQualification = docQualification;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getApointmentId() {
		return apointmentId;
	}

	public void setApointmentId(String apointmentId) {
		this.apointmentId = apointmentId;
	}

	public String getPatientDob() {
		return patientDob;
	}

	public void setPatientDob(String patientDob) {
		this.patientDob = patientDob;
	}

	public String getPatientAddress() {
		return patientAddress;
	}

	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}

	public String getPrescriptionDate() {
		return prescriptionDate;
	}

	public void setPrescriptionDate(String prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}

	public String getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(String patientAge) {
		this.patientAge = patientAge;
	}

	public String getPatientMob() {
		return patientMob;
	}

	public void setPatientMob(String patientMob) {
		this.patientMob = patientMob;
	}

	public String getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(String prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public String getPatientImage() {
		return patientImage;
	}

	public void setPatientImage(String patientImage) {
		this.patientImage = patientImage;
	}

	public String getPrescriptionMedName() {
		return prescriptionMedName;
	}

	public void setPrescriptionMedName(String prescriptionMedName) {
		this.prescriptionMedName = prescriptionMedName;
	}

	public String getPrescriptionMedType() {
		return prescriptionMedType;
	}

	public void setPrescriptionMedType(String prescriptionMedType) {
		this.prescriptionMedType = prescriptionMedType;
	}

	public String getPrescriptionMedDay() {
		return prescriptionMedDay;
	}

	public void setPrescriptionMedDay(String prescriptionMedDay) {
		this.prescriptionMedDay = prescriptionMedDay;
	}

	public String getPrescriptionMedNoon() {
		return prescriptionMedNoon;
	}

	public void setPrescriptionMedNoon(String prescriptionMedNoon) {
		this.prescriptionMedNoon = prescriptionMedNoon;
	}

	public String getPrescriptionMedNight() {
		return prescriptionMedNight;
	}

	public void setPrescriptionMedNight(String prescriptionMedNight) {
		this.prescriptionMedNight = prescriptionMedNight;
	}

	public String getPrescriptionDosage() {
		return prescriptionDosage;
	}

	public void setPrescriptionDosage(String prescriptionDosage) {
		this.prescriptionDosage = prescriptionDosage;
	}

	public String getPrescriptionTestName() {
		return prescriptionTestName;
	}

	public void setPrescriptionTestName(String prescriptionTestName) {
		this.prescriptionTestName = prescriptionTestName;
	}

	public String getPrescriptionTestGroup() {
		return prescriptionTestGroup;
	}

	public void setPrescriptionTestGroup(String prescriptionTestGroup) {
		this.prescriptionTestGroup = prescriptionTestGroup;
	}

	public String getPrescriptionMedDuration() {
		return prescriptionMedDuration;
	}

	public void setPrescriptionMedDuration(String prescriptionMedDuration) {
		this.prescriptionMedDuration = prescriptionMedDuration;
	}

	public String getPrescriptionStartDate() {
		return prescriptionStartDate;
	}

	public void setPrescriptionStartDate(String prescriptionStartDate) {
		this.prescriptionStartDate = prescriptionStartDate;
	}

	public String getTick() {
		return tick;
	}

	public void setTick(String tick) {
		this.tick = tick;
	}

	public String getCross() {
		return cross;
	}

	public void setCross(String cross) {
		this.cross = cross;
	}

	public List<DropDownModelEmp> getTestData() {
		return testData;
	}

	public void setTestData(List<DropDownModelEmp> testData) {
		this.testData = testData;
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
