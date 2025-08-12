package nirmalya.aathithya.webmodule.his.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HISTreatmentModel {

	private String medId;
	private String medName;
	private String medType;

	private String testId;
	private String testName;
	private String testType;
	private String testGroup;
	private String dosage;

	private String treatMentId;
	private String id;
	private String morningCheck;
	private String noonCheck;
	private String nightCheck;
	private String duration;
	private String remarks;
	private String createdBy;
	private String organization;
	private String orgDivision;
	private String remarksTests;

	public String getMedId() {
		return medId;
	}

	public void setMedId(String medId) {
		this.medId = medId;
	}

	public String getMedName() {
		return medName;
	}

	public void setMedName(String medName) {
		this.medName = medName;
	}

	public String getMedType() {
		return medType;
	}

	public void setMedType(String medType) {
		this.medType = medType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMorningCheck() {
		return morningCheck;
	}

	public void setMorningCheck(String morningCheck) {
		this.morningCheck = morningCheck;
	}

	public String getNoonCheck() {
		return noonCheck;
	}

	public void setNoonCheck(String noonCheck) {
		this.noonCheck = noonCheck;
	}

	public String getNightCheck() {
		return nightCheck;
	}

	public void setNightCheck(String nightCheck) {
		this.nightCheck = nightCheck;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
	}

	public String getTreatMentId() {
		return treatMentId;
	}

	public void setTreatMentId(String treatMentId) {
		this.treatMentId = treatMentId;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getTestGroup() {
		return testGroup;
	}

	public void setTestGroup(String testGroup) {
		this.testGroup = testGroup;
	}

	public String getRemarksTests() {
		return remarksTests;
	}

	public void setRemarksTests(String remarksTests) {
		this.remarksTests = remarksTests;
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
