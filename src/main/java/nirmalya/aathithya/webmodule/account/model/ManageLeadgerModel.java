package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ManageLeadgerModel {

	private String leadgerId;
	private String ledgername;
	private String undergroup;
	private String lname;
	private String address;
	private String leadgercountry;
	private String leadgerstate;
	private String pincode;
	private String panitn;
	private String openinbalanceDate;
	private String groupId;
	private String groupName;

	private String organization;
	private String orgDivision;

	private String ledgerEmail;
	private String ledgerAddress;
	private String ledgerType;
	private String ledgerTypeName;

	public String getLedgerAddress() {
		return ledgerAddress;
	}

	public void setLedgerAddress(String ledgerAddress) {
		this.ledgerAddress = ledgerAddress;
	}

	private String ledgerAddress1;
	private String ledgerAddress2;
	private String ledgerAddress3;
	private String ledgerCountry;
	private String ledgerState;
	private String ledgerPinCode;
	private String ledgerMobile;
	private String ledgerPan;
	private String ledgerGst;

	public ManageLeadgerModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getLeadgerId() {
		return leadgerId;
	}

	public void setLeadgerId(String leadgerId) {
		this.leadgerId = leadgerId;
	}

	public String getLedgername() {
		return ledgername;
	}

	public void setLedgername(String ledgername) {
		this.ledgername = ledgername;
	}

	public String getUndergroup() {
		return undergroup;
	}

	public void setUndergroup(String undergroup) {
		this.undergroup = undergroup;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLeadgercountry() {
		return leadgercountry;
	}

	public void setLeadgercountry(String leadgercountry) {
		this.leadgercountry = leadgercountry;
	}

	public String getLeadgerstate() {
		return leadgerstate;
	}

	public void setLeadgerstate(String leadgerstate) {
		this.leadgerstate = leadgerstate;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPanitn() {
		return panitn;
	}

	public void setPanitn(String panitn) {
		this.panitn = panitn;
	}

	public String getOpeninbalanceDate() {
		return openinbalanceDate;
	}

	public void setOpeninbalanceDate(String openinbalanceDate) {
		this.openinbalanceDate = openinbalanceDate;
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

	public String getLedgerEmail() {
		return ledgerEmail;
	}

	public void setLedgerEmail(String ledgerEmail) {
		this.ledgerEmail = ledgerEmail;
	}

	public String getLedgerAddress1() {
		return ledgerAddress1;
	}

	public void setLedgerAddress1(String ledgerAddress1) {
		this.ledgerAddress1 = ledgerAddress1;
	}

	public String getLedgerAddress2() {
		return ledgerAddress2;
	}

	public void setLedgerAddress2(String ledgerAddress2) {
		this.ledgerAddress2 = ledgerAddress2;
	}

	public String getLedgerAddress3() {
		return ledgerAddress3;
	}

	public void setLedgerAddress3(String ledgerAddress3) {
		this.ledgerAddress3 = ledgerAddress3;
	}

	public String getLedgerCountry() {
		return ledgerCountry;
	}

	public void setLedgerCountry(String ledgerCountry) {
		this.ledgerCountry = ledgerCountry;
	}

	public String getLedgerState() {
		return ledgerState;
	}

	public void setLedgerState(String ledgerState) {
		this.ledgerState = ledgerState;
	}

	public String getLedgerPinCode() {
		return ledgerPinCode;
	}

	public void setLedgerPinCode(String ledgerPinCode) {
		this.ledgerPinCode = ledgerPinCode;
	}

	public String getLedgerMobile() {
		return ledgerMobile;
	}

	public void setLedgerMobile(String ledgerMobile) {
		this.ledgerMobile = ledgerMobile;
	}

	public String getLedgerPan() {
		return ledgerPan;
	}

	public void setLedgerPan(String ledgerPan) {
		this.ledgerPan = ledgerPan;
	}

	public String getLedgerGst() {
		return ledgerGst;
	}

	public void setLedgerGst(String ledgerGst) {
		this.ledgerGst = ledgerGst;
	}

	public String getLedgerType() {
		return ledgerType;
	}

	public void setLedgerType(String ledgerType) {
		this.ledgerType = ledgerType;
	}

	public String getLedgerTypeName() {
		return ledgerTypeName;
	}

	public void setLedgerTypeName(String ledgerTypeName) {
		this.ledgerTypeName = ledgerTypeName;
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
