package nirmalya.aathithya.webmodule.employee.model;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManageEmployeeModel {
	private String employeeId;
	private String fileEmployeeimg;

	private String firstName;
	private String lastName;
	private String gender;
	private String dob;
	private String bloodGroup;
	private String maritalStatus;
	private String nationality;
	private String fatherName;
	private String motherName;
	private String mobileNo;
	private String personalMail;
	private String workMail;
	private String createdBy;
	private String updatedBy;
	private String createdon;
	private String updatedOn;
	private String panno;
	private String epfno;
	private String esicno;
	private String bandid;
	private String joiningdate;
	private String mrgdate;
	private String aadhaar;
	private String password;
	private String offerLetterId;
	private MultipartFile mulFile;

	private String genderName;
	private String bloodGroupName;
	private String maritalStatusName;
	private String nationalityName;
	private String department;
	private String subDepartment;
	private String designation;
	private String manager;
	private String organization;
	private String orgDivision;
	private String qrCode;

	private String docType;
	private String extension;
	private String userid;
	private String filetype;
	private String corpOfficeLocation;
	private String workOfficeLocation;
	private String empMobile;
	private String empDesignation;
	private String bandname;
	private String stafftype;
	private String ememobilenoid;
	private String employedBy;
	private String logo;
	private String sign;
	private String stamp;
	
	private String saveType;
	private String transferId;
	private String spouseName;
	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public ManageEmployeeModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmemobilenoid() {
		return ememobilenoid;
	}

	public void setEmemobilenoid(String ememobilenoid) {
		this.ememobilenoid = ememobilenoid;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFileEmployeeimg() {
		return fileEmployeeimg;
	}

	public void setFileEmployeeimg(String fileEmployeeimg) {
		this.fileEmployeeimg = fileEmployeeimg;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPersonalMail() {
		return personalMail;
	}

	public void setPersonalMail(String personalMail) {
		this.personalMail = personalMail;
	}

	public String getWorkMail() {
		return workMail;
	}

	public void setWorkMail(String workMail) {
		this.workMail = workMail;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedon() {
		return createdon;
	}

	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getPanno() {
		return panno;
	}

	public void setPanno(String panno) {
		this.panno = panno;
	}

	public String getEpfno() {
		return epfno;
	}

	public void setEpfno(String epfno) {
		this.epfno = epfno;
	}

	public String getEsicno() {
		return esicno;
	}

	public void setEsicno(String esicno) {
		this.esicno = esicno;
	}

	public String getBandid() {
		return bandid;
	}

	public void setBandid(String bandid) {
		this.bandid = bandid;
	}

	public String getJoiningdate() {
		return joiningdate;
	}

	public void setJoiningdate(String joiningdate) {
		this.joiningdate = joiningdate;
	}

	public String getMrgdate() {
		return mrgdate;
	}

	public void setMrgdate(String mrgdate) {
		this.mrgdate = mrgdate;
	}

	public String getAadhaar() {
		return aadhaar;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOfferLetterId() {
		return offerLetterId;
	}

	public void setOfferLetterId(String offerLetterId) {
		this.offerLetterId = offerLetterId;
	}

	public MultipartFile getMulFile() {
		return mulFile;
	}

	public void setMulFile(MultipartFile mulFile) {
		this.mulFile = mulFile;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getBloodGroupName() {
		return bloodGroupName;
	}

	public void setBloodGroupName(String bloodGroupName) {
		this.bloodGroupName = bloodGroupName;
	}

	public String getMaritalStatusName() {
		return maritalStatusName;
	}

	public void setMaritalStatusName(String maritalStatusName) {
		this.maritalStatusName = maritalStatusName;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(String subDepartment) {
		this.subDepartment = subDepartment;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
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

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getCorpOfficeLocation() {
		return corpOfficeLocation;
	}

	public void setCorpOfficeLocation(String corpOfficeLocation) {
		this.corpOfficeLocation = corpOfficeLocation;
	}

	public String getWorkOfficeLocation() {
		return workOfficeLocation;
	}

	public void setWorkOfficeLocation(String workOfficeLocation) {
		this.workOfficeLocation = workOfficeLocation;
	}

	public String getEmpMobile() {
		return empMobile;
	}

	public void setEmpMobile(String empMobile) {
		this.empMobile = empMobile;
	}

	public String getEmpDesignation() {
		return empDesignation;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	public String getBandname() {
		return bandname;
	}

	public void setBandname(String bandname) {
		this.bandname = bandname;
	}

	public String getStafftype() {
		return stafftype;
	}

	public void setStafftype(String stafftype) {
		this.stafftype = stafftype;
	}

	public String getEmployedBy() {
		return employedBy;
	}

	public void setEmployedBy(String employedBy) {
		this.employedBy = employedBy;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	
	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
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
