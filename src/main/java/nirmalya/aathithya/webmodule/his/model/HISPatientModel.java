package nirmalya.aathithya.webmodule.his.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HISPatientModel {

	private String patientId;
	private String patientType;
	private String fName;
	private String mName;
	private String lName;
	private String dateOfBirth;
	private String age;
	private String mobNo;
	private String altMobNo;
	private String email;
	private String gender;
	private String nationality;
	private String country;
	private String states;
	private String dist;
	private String city;
	private String address;
	private String zipCode;
	private String recommended;
	private String patientStatus;
	private String ipdId;

	// working details
	private String occupation;
	private String officeName;
	private String officeAddress;
	private String income;

	// patient dependent details
	private String depName;
	private String depRelation;
	private String depMobNo;

	// department and doctor details
	private String department;
	private String doctor;
	private String docFee;
	private String avlSlot;
	private String dateOfAppointment;
	private String description;

	private String organization;
	private String orgDivision;
	private String createdBy;
	private String pName;

	private String docOpdId;
	private String slotDate;
	private String slotStartTime;
	private String slotEndTime;
	private String slotIntervals;

	private String bed;
	private String ward;
	private String pImgName;

	private String insurance;
	private String cash;
	private String insuranceName;
	private String dignosys;

	// for modal

	private String pTypeModal;
	private String sal;
	private String firstName;
	private String middleName;
	private String lastName;
	private String dob;
	private String ageModal;
	private String genderModal;
	private String maritialStatusModal;
	private String religionModal;
	private String nationalityModal;
	private String addressModal;
	private String cityModal;
	private String countryModal;
	private String statesModal;
	private String distModal;
	private String zipCodeModal;
	private String mobNoModal;
	private String contactNoModal;
	private String emailModal;
	private String recommendedModal;
	private String occupationModal;
	private String officeNameModal;
	private String incomeModal;
	private String pStatus;
	private String officeAddressModal;

	private String organizationModal;
	private String orgDivisionModal;
	private String createdByModal;
	// private String pName;

	private String departmentModal;
	private String doctorModal;
	private String dateOfAppointmentModal;
	private String descriptionModal;

	private String docFeeModal;
	private String avlSlotModal;

	private String bedModal;
	private String wardModal;

	private List<HISPatientModel> payList;
	private String receiverBankBranch;
	private String receiverBankName;
	private String transactionNumber;
	private String bankSelect;
	private String chequeAccountNumber;
	private String chequeBankBranch;
	private String chequeBankName;
	private String chequeNo;
	private String paymentMode;
	private String payRemarks;
	private String bankSelectPayment;
	private String payAmount;
	private String vendorId;
	private String methodOfAdj;
	private String receiverUpiTransactionID;
	private String receiverOnlineUpiID;
	private String onlineUpiID;
	private String receiverAccountNumber;
	private String receiverIfscCode;
	private String doctorFees;
	private String ambulanceFees;
	private String contactNo;

	private String productId;
	private String productName;

	// treatment
	private String treatmentIds;
	private String medicinename;
	private String dosage;
	private String frequency;
	private String duration;
	private String instruction;
	private String treatmentTypes;

	// vital
	private String vitalId;
	private String bodyTemp;
	private String heartRate;
	private String respRate;
	private String bloodPres;
	private String weight;
	private String height;
	private String bmi;
	private String vitalStatus;

	// test

	private String testIds;
	private String testName;
	private String testCategory;

	// diet

	private String dietId;
	private String category;
	private String itemId;
	private String note;
	private String lunch;
	private String itemId1;
	private String note1;
	private String dinner;
	private String itemId2;
	private String note2;
	private String remark;

	// ot

	private String otId;
	private String procedureName;
	private String summary;
	private String fromDate;
	private String toDate;

	private String slno;

	public HISPatientModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getDocFee() {
		return docFee;
	}

	public void setDocFee(String docFee) {
		this.docFee = docFee;
	}

	public String getAvlSlot() {
		return avlSlot;
	}

	public void setAvlSlot(String avlSlot) {
		this.avlSlot = avlSlot;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getDateOfAppointment() {
		return dateOfAppointment;
	}

	public void setDateOfAppointment(String dateOfAppointment) {
		this.dateOfAppointment = dateOfAppointment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getPatientStatus() {
		return patientStatus;
	}

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public void setPatientStatus(String patientStatus) {
		this.patientStatus = patientStatus;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRecommended() {
		return recommended;
	}

	public void setRecommended(String recommended) {
		this.recommended = recommended;
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

	public String getSlotStartTime() {
		return slotStartTime;
	}

	public void setSlotStartTime(String slotStartTime) {
		this.slotStartTime = slotStartTime;
	}

	public String getSlotEndTime() {
		return slotEndTime;
	}

	public void setSlotEndTime(String slotEndTime) {
		this.slotEndTime = slotEndTime;
	}

	public String getSlotDate() {
		return slotDate;
	}

	public void setSlotDate(String slotDate) {
		this.slotDate = slotDate;
	}

	public String getSlotIntervals() {
		return slotIntervals;
	}

	public void setSlotIntervals(String slotIntervals) {
		this.slotIntervals = slotIntervals;
	}

	public String getDocOpdId() {
		return docOpdId;
	}

	public void setDocOpdId(String docOpdId) {
		this.docOpdId = docOpdId;
	}

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getpTypeModal() {
		return pTypeModal;
	}

	public void setpTypeModal(String pTypeModal) {
		this.pTypeModal = pTypeModal;
	}

	public String getSal() {
		return sal;
	}

	public void setSal(String sal) {
		this.sal = sal;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAgeModal() {
		return ageModal;
	}

	public void setAgeModal(String ageModal) {
		this.ageModal = ageModal;
	}

	public String getGenderModal() {
		return genderModal;
	}

	public void setGenderModal(String genderModal) {
		this.genderModal = genderModal;
	}

	public String getMaritialStatusModal() {
		return maritialStatusModal;
	}

	public void setMaritialStatusModal(String maritialStatusModal) {
		this.maritialStatusModal = maritialStatusModal;
	}

	public String getReligionModal() {
		return religionModal;
	}

	public void setReligionModal(String religionModal) {
		this.religionModal = religionModal;
	}

	public String getNationalityModal() {
		return nationalityModal;
	}

	public void setNationalityModal(String nationalityModal) {
		this.nationalityModal = nationalityModal;
	}

	public String getAddressModal() {
		return addressModal;
	}

	public void setAddressModal(String addressModal) {
		this.addressModal = addressModal;
	}

	public String getCityModal() {
		return cityModal;
	}

	public void setCityModal(String cityModal) {
		this.cityModal = cityModal;
	}

	public String getCountryModal() {
		return countryModal;
	}

	public void setCountryModal(String countryModal) {
		this.countryModal = countryModal;
	}

	public String getStatesModal() {
		return statesModal;
	}

	public void setStatesModal(String statesModal) {
		this.statesModal = statesModal;
	}

	public String getDistModal() {
		return distModal;
	}

	public void setDistModal(String distModal) {
		this.distModal = distModal;
	}

	public String getZipCodeModal() {
		return zipCodeModal;
	}

	public void setZipCodeModal(String zipCodeModal) {
		this.zipCodeModal = zipCodeModal;
	}

	public String getMobNoModal() {
		return mobNoModal;
	}

	public void setMobNoModal(String mobNoModal) {
		this.mobNoModal = mobNoModal;
	}

	public String getContactNoModal() {
		return contactNoModal;
	}

	public void setContactNoModal(String contactNoModal) {
		this.contactNoModal = contactNoModal;
	}

	public String getEmailModal() {
		return emailModal;
	}

	public void setEmailModal(String emailModal) {
		this.emailModal = emailModal;
	}

	public String getRecommendedModal() {
		return recommendedModal;
	}

	public void setRecommendedModal(String recommendedModal) {
		this.recommendedModal = recommendedModal;
	}

	public String getOccupationModal() {
		return occupationModal;
	}

	public void setOccupationModal(String occupationModal) {
		this.occupationModal = occupationModal;
	}

	public String getOfficeNameModal() {
		return officeNameModal;
	}

	public void setOfficeNameModal(String officeNameModal) {
		this.officeNameModal = officeNameModal;
	}

	public String getIncomeModal() {
		return incomeModal;
	}

	public void setIncomeModal(String incomeModal) {
		this.incomeModal = incomeModal;
	}

	public String getpStatus() {
		return pStatus;
	}

	public void setpStatus(String pStatus) {
		this.pStatus = pStatus;
	}

	public String getOfficeAddressModal() {
		return officeAddressModal;
	}

	public void setOfficeAddressModal(String officeAddressModal) {
		this.officeAddressModal = officeAddressModal;
	}

	public String getOrganizationModal() {
		return organizationModal;
	}

	public void setOrganizationModal(String organizationModal) {
		this.organizationModal = organizationModal;
	}

	public String getOrgDivisionModal() {
		return orgDivisionModal;
	}

	public void setOrgDivisionModal(String orgDivisionModal) {
		this.orgDivisionModal = orgDivisionModal;
	}

	public String getCreatedByModal() {
		return createdByModal;
	}

	public void setCreatedByModal(String createdByModal) {
		this.createdByModal = createdByModal;
	}

	public String getDepartmentModal() {
		return departmentModal;
	}

	public void setDepartmentModal(String departmentModal) {
		this.departmentModal = departmentModal;
	}

	public String getDoctorModal() {
		return doctorModal;
	}

	public void setDoctorModal(String doctorModal) {
		this.doctorModal = doctorModal;
	}

	public String getDateOfAppointmentModal() {
		return dateOfAppointmentModal;
	}

	public void setDateOfAppointmentModal(String dateOfAppointmentModal) {
		this.dateOfAppointmentModal = dateOfAppointmentModal;
	}

	public String getDescriptionModal() {
		return descriptionModal;
	}

	public void setDescriptionModal(String descriptionModal) {
		this.descriptionModal = descriptionModal;
	}

	public String getDocFeeModal() {
		return docFeeModal;
	}

	public void setDocFeeModal(String docFeeModal) {
		this.docFeeModal = docFeeModal;
	}

	public String getAvlSlotModal() {
		return avlSlotModal;
	}

	public void setAvlSlotModal(String avlSlotModal) {
		this.avlSlotModal = avlSlotModal;
	}

	public String getBedModal() {
		return bedModal;
	}

	public void setBedModal(String bedModal) {
		this.bedModal = bedModal;
	}

	public String getWardModal() {
		return wardModal;
	}

	public void setWardModal(String wardModal) {
		this.wardModal = wardModal;
	}

	public String getpImgName() {
		return pImgName;
	}

	public void setpImgName(String pImgName) {
		this.pImgName = pImgName;
	}

	public String getReceiverBankBranch() {
		return receiverBankBranch;
	}

	public void setReceiverBankBranch(String receiverBankBranch) {
		this.receiverBankBranch = receiverBankBranch;
	}

	public String getReceiverBankName() {
		return receiverBankName;
	}

	public void setReceiverBankName(String receiverBankName) {
		this.receiverBankName = receiverBankName;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getBankSelect() {
		return bankSelect;
	}

	public void setBankSelect(String bankSelect) {
		this.bankSelect = bankSelect;
	}

	public String getChequeAccountNumber() {
		return chequeAccountNumber;
	}

	public void setChequeAccountNumber(String chequeAccountNumber) {
		this.chequeAccountNumber = chequeAccountNumber;
	}

	public String getChequeBankBranch() {
		return chequeBankBranch;
	}

	public void setChequeBankBranch(String chequeBankBranch) {
		this.chequeBankBranch = chequeBankBranch;
	}

	public String getChequeBankName() {
		return chequeBankName;
	}

	public void setChequeBankName(String chequeBankName) {
		this.chequeBankName = chequeBankName;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPayRemarks() {
		return payRemarks;
	}

	public void setPayRemarks(String payRemarks) {
		this.payRemarks = payRemarks;
	}

	public String getBankSelectPayment() {
		return bankSelectPayment;
	}

	public void setBankSelectPayment(String bankSelectPayment) {
		this.bankSelectPayment = bankSelectPayment;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getMethodOfAdj() {
		return methodOfAdj;
	}

	public void setMethodOfAdj(String methodOfAdj) {
		this.methodOfAdj = methodOfAdj;
	}

	public String getReceiverUpiTransactionID() {
		return receiverUpiTransactionID;
	}

	public void setReceiverUpiTransactionID(String receiverUpiTransactionID) {
		this.receiverUpiTransactionID = receiverUpiTransactionID;
	}

	public String getReceiverOnlineUpiID() {
		return receiverOnlineUpiID;
	}

	public void setReceiverOnlineUpiID(String receiverOnlineUpiID) {
		this.receiverOnlineUpiID = receiverOnlineUpiID;
	}

	public String getOnlineUpiID() {
		return onlineUpiID;
	}

	public void setOnlineUpiID(String onlineUpiID) {
		this.onlineUpiID = onlineUpiID;
	}

	public String getReceiverAccountNumber() {
		return receiverAccountNumber;
	}

	public void setReceiverAccountNumber(String receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}

	public String getReceiverIfscCode() {
		return receiverIfscCode;
	}

	public void setReceiverIfscCode(String receiverIfscCode) {
		this.receiverIfscCode = receiverIfscCode;
	}

	public List<HISPatientModel> getPayList() {
		return payList;
	}

	public void setPayList(List<HISPatientModel> payList) {
		this.payList = payList;
	}

	public String getAltMobNo() {
		return altMobNo;
	}

	public void setAltMobNo(String altMobNo) {
		this.altMobNo = altMobNo;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepRelation() {
		return depRelation;
	}

	public void setDepRelation(String depRelation) {
		this.depRelation = depRelation;
	}

	public String getDepMobNo() {
		return depMobNo;
	}

	public void setDepMobNo(String depMobNo) {
		this.depMobNo = depMobNo;
	}

	public String getDoctorFees() {
		return doctorFees;
	}

	public void setDoctorFees(String doctorFees) {
		this.doctorFees = doctorFees;
	}

	public String getAmbulanceFees() {
		return ambulanceFees;
	}

	public void setAmbulanceFees(String ambulanceFees) {
		this.ambulanceFees = ambulanceFees;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public String getDignosys() {
		return dignosys;
	}

	public void setDignosys(String dignosys) {
		this.dignosys = dignosys;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTreatmentIds() {
		return treatmentIds;
	}

	public void setTreatmentIds(String treatmentIds) {
		this.treatmentIds = treatmentIds;
	}

	public String getMedicinename() {
		return medicinename;
	}

	public void setMedicinename(String medicinename) {
		this.medicinename = medicinename;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getTreatmentTypes() {
		return treatmentTypes;
	}

	public void setTreatmentTypes(String treatmentTypes) {
		this.treatmentTypes = treatmentTypes;
	}

	public String getVitalId() {
		return vitalId;
	}

	public void setVitalId(String vitalId) {
		this.vitalId = vitalId;
	}

	public String getBodyTemp() {
		return bodyTemp;
	}

	public void setBodyTemp(String bodyTemp) {
		this.bodyTemp = bodyTemp;
	}

	public String getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}

	public String getRespRate() {
		return respRate;
	}

	public void setRespRate(String respRate) {
		this.respRate = respRate;
	}

	public String getBloodPres() {
		return bloodPres;
	}

	public void setBloodPres(String bloodPres) {
		this.bloodPres = bloodPres;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getBmi() {
		return bmi;
	}

	public void setBmi(String bmi) {
		this.bmi = bmi;
	}

	public String getIpdId() {
		return ipdId;
	}

	public void setIpdId(String ipdId) {
		this.ipdId = ipdId;
	}

	public String getTestIds() {
		return testIds;
	}

	public void setTestIds(String testIds) {
		this.testIds = testIds;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestCategory() {
		return testCategory;
	}

	public void setTestCategory(String testCategory) {
		this.testCategory = testCategory;
	}

	public String getDietId() {
		return dietId;
	}

	public void setDietId(String dietId) {
		this.dietId = dietId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLunch() {
		return lunch;
	}

	public void setLunch(String lunch) {
		this.lunch = lunch;
	}

	public String getItemId1() {
		return itemId1;
	}

	public void setItemId1(String itemId1) {
		this.itemId1 = itemId1;
	}

	public String getNote1() {
		return note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	public String getDinner() {
		return dinner;
	}

	public void setDinner(String dinner) {
		this.dinner = dinner;
	}

	public String getItemId2() {
		return itemId2;
	}

	public void setItemId2(String itemId2) {
		this.itemId2 = itemId2;
	}

	public String getNote2() {
		return note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOtId() {
		return otId;
	}

	public void setOtId(String otId) {
		this.otId = otId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}
	
	

	public String getVitalStatus() {
		return vitalStatus;
	}

	public void setVitalStatus(String vitalStatus) {
		this.vitalStatus = vitalStatus;
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
