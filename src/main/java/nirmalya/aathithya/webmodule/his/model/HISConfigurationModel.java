package nirmalya.aathithya.webmodule.his.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HISConfigurationModel {

////Bed Category model
	
	private String bedcat;
	private String name4;
	private String description4;
	private String status4;
	private String org4;
	private String div4;

	/// Discharge Type model

	private String distype;
	private String disname;
	private String disdescription;
	private String disstatus;
	private String org2;
	private String div2;
	
	//Discharge Destination model
	
	private String disDes;
	private String desName;
	private String desDescription;
	private String desSatus;
	private String org3;
	private String div3;
	
	//Concession Category model
	
		private String conCat;
		private String conName;
		private String conDescription;
		private String conStatus;
		private String org1;
		private String div1;
		
	//Pathology Test Type model
		
			private String testType;
			private String pathName;
			private String pathDes;
			private String pathStatus;
			private String org5;
			private String div5;
			
			
			//Group mstr model
			
			private String group;
			private String groupName;
			private String groupDes;
			private String groupStatus;
			private String org6;
			private String div6;
			
			
           //Sub Group mstr model
			
			private String subGroup;
			private String groupId;
			private String subName;
			private String subDes;
			private String subStatus;
			private String org7;
			private String div7;
			private String sgroupName;
			
			
			//Surgery model
			
			private String surgeryId;
			private String surgeryName;
			private String surgeryDes;
			private String surgeryStatus;
			private String org8;
			private String div8;
			

	public HISConfigurationModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBedcat() {
		return bedcat;
	}

	public void setBedcat(String bedcat) {
		this.bedcat = bedcat;
	}

	public String getName4() {
		return name4;
	}

	public void setName4(String name4) {
		this.name4 = name4;
	}

	public String getDescription4() {
		return description4;
	}

	public void setDescription4(String description4) {
		this.description4 = description4;
	}

	public String getStatus4() {
		return status4;
	}

	public void setStatus4(String status4) {
		this.status4 = status4;
	}

	public String getOrg4() {
		return org4;
	}

	public void setOrg4(String org4) {
		this.org4 = org4;
	}

	public String getDiv4() {
		return div4;
	}

	public void setDiv4(String div4) {
		this.div4 = div4;
	}

	public String getDistype() {
		return distype;
	}

	public void setDistype(String distype) {
		this.distype = distype;
	}

	public String getDisname() {
		return disname;
	}

	public void setDisname(String disname) {
		this.disname = disname;
	}

	public String getDisdescription() {
		return disdescription;
	}

	public void setDisdescription(String disdescription) {
		this.disdescription = disdescription;
	}

	public String getDisstatus() {
		return disstatus;
	}

	public void setDisstatus(String disstatus) {
		this.disstatus = disstatus;
	}

	public String getOrg2() {
		return org2;
	}

	public void setOrg2(String org2) {
		this.org2 = org2;
	}

	public String getDiv2() {
		return div2;
	}

	public void setDiv2(String div2) {
		this.div2 = div2;
	}

	public String getDisDes() {
		return disDes;
	}

	public void setDisDes(String disDes) {
		this.disDes = disDes;
	}

	public String getDesName() {
		return desName;
	}

	public void setDesName(String desName) {
		this.desName = desName;
	}

	public String getDesDescription() {
		return desDescription;
	}

	public void setDesDescription(String desDescription) {
		this.desDescription = desDescription;
	}

	public String getDesSatus() {
		return desSatus;
	}

	public void setDesSatus(String desSatus) {
		this.desSatus = desSatus;
	}

	public String getOrg3() {
		return org3;
	}

	public void setOrg3(String org3) {
		this.org3 = org3;
	}

	public String getDiv3() {
		return div3;
	}

	public void setDiv3(String div3) {
		this.div3 = div3;
	}
	

	public String getConCat() {
		return conCat;
	}

	public void setConCat(String conCat) {
		this.conCat = conCat;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	public String getConDescription() {
		return conDescription;
	}

	public void setConDescription(String conDescription) {
		this.conDescription = conDescription;
	}

	public String getConStatus() {
		return conStatus;
	}

	public void setConStatus(String conStatus) {
		this.conStatus = conStatus;
	}

	public String getOrg1() {
		return org1;
	}

	public void setOrg1(String org1) {
		this.org1 = org1;
	}

	public String getDiv1() {
		return div1;
	}

	public void setDiv1(String div1) {
		this.div1 = div1;
	}
	

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public String getPathDes() {
		return pathDes;
	}

	public void setPathDes(String pathDes) {
		this.pathDes = pathDes;
	}

	public String getPathStatus() {
		return pathStatus;
	}

	public void setPathStatus(String pathStatus) {
		this.pathStatus = pathStatus;
	}

	public String getOrg5() {
		return org5;
	}

	public void setOrg5(String org5) {
		this.org5 = org5;
	}

	public String getDiv5() {
		return div5;
	}

	public void setDiv5(String div5) {
		this.div5 = div5;
	}
	

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDes() {
		return groupDes;
	}

	public void setGroupDes(String groupDes) {
		this.groupDes = groupDes;
	}

	public String getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	public String getOrg6() {
		return org6;
	}

	public void setOrg6(String org6) {
		this.org6 = org6;
	}

	public String getDiv6() {
		return div6;
	}

	public void setDiv6(String div6) {
		this.div6 = div6;
	}
	

	public String getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public String getSubDes() {
		return subDes;
	}

	public void setSubDes(String subDes) {
		this.subDes = subDes;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getOrg7() {
		return org7;
	}

	public void setOrg7(String org7) {
		this.org7 = org7;
	}

	public String getDiv7() {
		return div7;
	}

	public void setDiv7(String div7) {
		this.div7 = div7;
	}
	
	

	public String getSurgeryId() {
		return surgeryId;
	}

	public void setSurgeryId(String surgeryId) {
		this.surgeryId = surgeryId;
	}

	public String getSurgeryName() {
		return surgeryName;
	}

	public void setSurgeryName(String surgeryName) {
		this.surgeryName = surgeryName;
	}

	public String getSurgeryDes() {
		return surgeryDes;
	}

	public void setSurgeryDes(String surgeryDes) {
		this.surgeryDes = surgeryDes;
	}

	public String getSurgeryStatus() {
		return surgeryStatus;
	}

	public void setSurgeryStatus(String surgeryStatus) {
		this.surgeryStatus = surgeryStatus;
	}

	public String getOrg8() {
		return org8;
	}

	public void setOrg8(String org8) {
		this.org8 = org8;
	}

	public String getDiv8() {
		return div8;
	}

	public void setDiv8(String div8) {
		this.div8 = div8;
	}

	public String getSgroupName() {
		return sgroupName;
	}

	public void setSgroupName(String sgroupName) {
		this.sgroupName = sgroupName;
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