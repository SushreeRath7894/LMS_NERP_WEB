package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TimeSheetAllProjectDetailsModel {
	private String janHour;
	private String febHour;
	private String marchHour;
	private String aprilHour;
	private String mayHour;
	private String juneHour;
	private String julyHour;
	private String augHour;
	private String sepHour;
	private String octHour;
	private String novHour;
	private String decHour;
	private String totalHour;
	private String empId;
	private String empName;
	private String empStatus;
	
	public TimeSheetAllProjectDetailsModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getJanHour() {
		return janHour;
	}
	
	public void setJanHour(String janHour) {
		this.janHour = janHour;
	}
	public String getFebHour() {
		return febHour;
	}
	public void setFebHour(String febHour) {
		this.febHour = febHour;
	}
	public String getMarchHour() {
		return marchHour;
	}
	public void setMarchHour(String marchHour) {
		this.marchHour = marchHour;
	}
	public String getAprilHour() {
		return aprilHour;
	}
	public void setAprilHour(String aprilHour) {
		this.aprilHour = aprilHour;
	}
	public String getMayHour() {
		return mayHour;
	}
	public void setMayHour(String mayHour) {
		this.mayHour = mayHour;
	}
	public String getJuneHour() {
		return juneHour;
	}
	public void setJuneHour(String juneHour) {
		this.juneHour = juneHour;
	}
	public String getJulyHour() {
		return julyHour;
	}
	public void setJulyHour(String julyHour) {
		this.julyHour = julyHour;
	}
	public String getAugHour() {
		return augHour;
	}
	public void setAugHour(String augHour) {
		this.augHour = augHour;
	}
	public String getSepHour() {
		return sepHour;
	}
	public void setSepHour(String sepHour) {
		this.sepHour = sepHour;
	}
	public String getOctHour() {
		return octHour;
	}
	public void setOctHour(String octHour) {
		this.octHour = octHour;
	}
	public String getNovHour() {
		return novHour;
	}
	public void setNovHour(String novHour) {
		this.novHour = novHour;
	}
	public String getDecHour() {
		return decHour;
	}
	public void setDecHour(String decHour) {
		this.decHour = decHour;
	}
	public String getTotalHour() {
		return totalHour;
	}
	public void setTotalHour(String totalHour) {
		this.totalHour = totalHour;
	}
	
	public String getEmpId() {
		return empId;
	}


	public void setEmpId(String empId) {
		this.empId = empId;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getEmpStatus() {
		return empStatus;
	}


	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
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
