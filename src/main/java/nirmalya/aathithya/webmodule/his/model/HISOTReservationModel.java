package nirmalya.aathithya.webmodule.his.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HISOTReservationModel {

	private String otReserveId;
	private String ipdId;
	private String patientId;
	private String pName;

	private String dateOfOpe;
	private String duration;
	private String group;
	private String subGroup;
	private String surgery;
	private String otTable;
	private String procedure;
	private String department;

	private String doctor;
	private String instruction;

	private String createdBy;
	private String orgName;
	private String orgDiv;

	private List<String> roleList = new ArrayList<String>();
	private String roleName;

	private List<String> roleNameList = new ArrayList<String>();

	public HISOTReservationModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOtReserveId() {
		return otReserveId;
	}

	public void setOtReserveId(String otReserveId) {
		this.otReserveId = otReserveId;
	}

	public String getIpdId() {
		return ipdId;
	}

	public void setIpdId(String ipdId) {
		this.ipdId = ipdId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getDateOfOpe() {
		return dateOfOpe;
	}

	public void setDateOfOpe(String dateOfOpe) {
		this.dateOfOpe = dateOfOpe;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}

	public String getSurgery() {
		return surgery;
	}

	public void setSurgery(String surgery) {
		this.surgery = surgery;
	}

	public String getOtTable() {
		return otTable;
	}

	public void setOtTable(String otTable) {
		this.otTable = otTable;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
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

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgDiv() {
		return orgDiv;
	}

	public void setOrgDiv(String orgDiv) {
		this.orgDiv = orgDiv;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<String> getRoleNameList() {
		return roleNameList;
	}

	public void setRoleNameList(List<String> roleNameList) {
		this.roleNameList = roleNameList;
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
