package nirmalya.aathithya.webmodule.user.model;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nirmalya Labs
 *
 */
public class User {

	private String user;
	private String userName;
	private String userPassword;
	private String userMobile;
	private String userEmail;
	private String userParent;
	private String userAddress;
	private String userState;
	private String userCountry;
	private String userDist;
	private String userPin;
	private Boolean userStatus;
	private Date userCreatedOn;
	private Date userUpdatedOn;
	

	private String roleDashboard;
	private List<String> roles;
	private String userType;
	private String dateFormat;
	private String dateFormatId;
	private String dateFormatJS;
	private String vendorId;
	private String managerId;
	private String organization;
	private String orgDivision;
	private String orgLogo;
	private String profileImg;
	private String imeiNo;
	
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public User() {
		super();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserParent() {
		return userParent;
	}

	public void setUserParent(String userParent) {
		this.userParent = userParent;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getUserCountry() {
		return userCountry;
	}

	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}

	public String getUserDist() {
		return userDist;
	}

	public void setUserDist(String userDist) {
		this.userDist = userDist;
	}

	public String getUserPin() {
		return userPin;
	}

	public void setUserPin(String userPin) {
		this.userPin = userPin;
	}

	public Boolean getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Boolean userStatus) {
		this.userStatus = userStatus;
	}

	public Date getUserCreatedOn() {
		return userCreatedOn;
	}

	public void setUserCreatedOn(Date userCreatedOn) {
		this.userCreatedOn = userCreatedOn;
	}

	public Date getUserUpdatedOn() {
		return userUpdatedOn;
	}

	public void setUserUpdatedOn(Date userUpdatedOn) {
		this.userUpdatedOn = userUpdatedOn;
	}
	
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public String getRoleDashboard() {
		return roleDashboard;
	}

	public void setRoleDashboard(String roleDashboard) {
		this.roleDashboard = roleDashboard;
	}

	
	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getDateFormatId() {
		return dateFormatId;
	}

	public void setDateFormatId(String dateFormatId) {
		this.dateFormatId = dateFormatId;
	}

	public String getDateFormatJS() {
		return dateFormatJS;
	}

	public void setDateFormatJS(String dateFormatJS) {
		this.dateFormatJS = dateFormatJS;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

 

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
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

	public String getOrgLogo() {
		return orgLogo;
	}

	public void setOrgLogo(String orgLogo) {
		this.orgLogo = orgLogo;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	/**
	 * Overrides toString method for converting class to string and back 
	**/
	@Override
	public String toString() {
		ObjectMapper  mapperObj=new ObjectMapper();
		String jsonStr;
		try{
			jsonStr=mapperObj.writeValueAsString(this);
		}catch(IOException ex){
			
			jsonStr=ex.toString();
		}
		return jsonStr;
	}
	
}
