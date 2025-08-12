package nirmalya.aathithya.webmodule.grc.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class noiseMonitorReportModel {
	
	private String monitorId;
	private String date;
	private String month;
	private String remarks;
	private String inputDate;
	
	private String slNo1;
	private String location1;
	private String persons1;
	private String avghours1;
	private String daytime1;
	private String nighttime1;
	private String maximumpermissible1;
	
	
	private List<noiseMonitorReportModel> grid1List;

	private String slNo2;
	private String location2;
	private String persons2;
	private String avghours2;
	private String daytime2;
	private String nighttime2;
	private String maximumpermissible2;
	
	private List<noiseMonitorReportModel>grid2List;

	private String createdBy;
	private String organization;
	private String orgDivision;
	
	
	public String getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(String monitorId) {
		this.monitorId = monitorId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getSlNo1() {
		return slNo1;
	}

	public void setSlNo1(String slNo1) {
		this.slNo1 = slNo1;
	}

	public String getLocation1() {
		return location1;
	}

	public void setLocation1(String location1) {
		this.location1 = location1;
	}

	public String getPersons1() {
		return persons1;
	}

	public void setPersons1(String persons1) {
		this.persons1 = persons1;
	}

	public String getAvghours1() {
		return avghours1;
	}

	public void setAvghours1(String avghours1) {
		this.avghours1 = avghours1;
	}

	public String getDaytime1() {
		return daytime1;
	}

	public void setDaytime1(String daytime1) {
		this.daytime1 = daytime1;
	}

	public String getNighttime1() {
		return nighttime1;
	}

	public void setNighttime1(String nighttime1) {
		this.nighttime1 = nighttime1;
	}

	public String getMaximumpermissible1() {
		return maximumpermissible1;
	}

	public void setMaximumpermissible1(String maximumpermissible1) {
		this.maximumpermissible1 = maximumpermissible1;
	}

	public List<noiseMonitorReportModel> getGrid1List() {
		return grid1List;
	}

	public void setGrid1List(List<noiseMonitorReportModel> grid1List) {
		this.grid1List = grid1List;
	}

	public String getSlNo2() {
		return slNo2;
	}

	public void setSlNo2(String slNo2) {
		this.slNo2 = slNo2;
	}

	public String getLocation2() {
		return location2;
	}

	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	public String getPersons2() {
		return persons2;
	}

	public void setPersons2(String persons2) {
		this.persons2 = persons2;
	}

	public String getAvghours2() {
		return avghours2;
	}

	public void setAvghours2(String avghours2) {
		this.avghours2 = avghours2;
	}

	public String getDaytime2() {
		return daytime2;
	}

	public void setDaytime2(String daytime2) {
		this.daytime2 = daytime2;
	}

	public String getNighttime2() {
		return nighttime2;
	}

	public void setNighttime2(String nighttime2) {
		this.nighttime2 = nighttime2;
	}

	public String getMaximumpermissible2() {
		return maximumpermissible2;
	}

	public void setMaximumpermissible2(String maximumpermissible2) {
		this.maximumpermissible2 = maximumpermissible2;
	}

	public List<noiseMonitorReportModel> getGrid2List() {
		return grid2List;
	}

	public void setGrid2List(List<noiseMonitorReportModel> grid2List) {
		this.grid2List = grid2List;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
