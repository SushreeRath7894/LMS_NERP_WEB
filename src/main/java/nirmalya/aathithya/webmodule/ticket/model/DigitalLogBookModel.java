package nirmalya.aathithya.webmodule.ticket.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.maintenance.model.AllotedMaintenanceModel;

public class DigitalLogBookModel {
	//ELCB
	private String elcbId;
	private String checkinDate;
	private String elcbRating;
	private String tripingTime;
	private String dueDate;
	private String remark;
	
	private String createdBy;
	private String organization;
	private String orgDivision;
	
	//WASTE OIL TRACK
	private String trackId;
	private String date;
	private String source;
	private String spentQnt;
	private String openingStock;
	private String disposedQnt;
	private String closingStock;
	
	
	private String equiType;
	private String equiName;
	
	private List<DigitalLogBookModel> grid1List;
	private List<DigitalLogBookModel> grid2List;
	
	private String completionId;
	private String dept;
	private String nonRoutineArea;
	private String type;
	private String duration;
	private String description;
	private String activityArea;
	
	private String slNo1;
	private String desc1;
	private String remark1;
	
	private String slNo2;
	private String desc2;
	private String remark2;
	
	private String time;
	private String uploadBy;
	
	private String ndate;
	private String year;
	private String month;
	private String illuminationId;
	
	private String levelinlux;
	private String correction;
	private String valueaftercorrection;
	private String dateofCorrection;
	private String electricianSign;
	private String icSign;
	
	
	public DigitalLogBookModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public String getElcbId() {
		return elcbId;
	}




	public void setElcbId(String elcbId) {
		this.elcbId = elcbId;
	}




	public String getCheckinDate() {
		return checkinDate;
	}




	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}




	public String getElcbRating() {
		return elcbRating;
	}




	public void setElcbRating(String elcbRating) {
		this.elcbRating = elcbRating;
	}




	public String getTripingTime() {
		return tripingTime;
	}




	public void setTripingTime(String tripingTime) {
		this.tripingTime = tripingTime;
	}




	public String getDueDate() {
		return dueDate;
	}




	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}




	public String getRemark() {
		return remark;
	}




	public void setRemark(String remark) {
		this.remark = remark;
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




	public String getDate() {
		return date;
	}




	public void setDate(String date) {
		this.date = date;
	}




	public String getSource() {
		return source;
	}




	public void setSource(String source) {
		this.source = source;
	}




	public String getSpentQnt() {
		return spentQnt;
	}




	public void setSpentQnt(String spentQnt) {
		this.spentQnt = spentQnt;
	}




	public String getOpeningStock() {
		return openingStock;
	}




	public void setOpeningStock(String openingStock) {
		this.openingStock = openingStock;
	}




	public String getDisposedQnt() {
		return disposedQnt;
	}




	public void setDisposedQnt(String disposedQnt) {
		this.disposedQnt = disposedQnt;
	}




	public String getClosingStock() {
		return closingStock;
	}




	public void setClosingStock(String closingStock) {
		this.closingStock = closingStock;
	}




	public String getTrackId() {
		return trackId;
	}




	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}




	public List<DigitalLogBookModel> getGrid1List() {
		return grid1List;
	}




	public void setGrid1List(List<DigitalLogBookModel> grid1List) {
		this.grid1List = grid1List;
	}




	public List<DigitalLogBookModel> getGrid2List() {
		return grid2List;
	}




	public void setGrid2List(List<DigitalLogBookModel> grid2List) {
		this.grid2List = grid2List;
	}




	public String getCompletionId() {
		return completionId;
	}




	public void setCompletionId(String completionId) {
		this.completionId = completionId;
	}




	public String getDept() {
		return dept;
	}




	public void setDept(String dept) {
		this.dept = dept;
	}




	public String getNonRoutineArea() {
		return nonRoutineArea;
	}




	public void setNonRoutineArea(String nonRoutineArea) {
		this.nonRoutineArea = nonRoutineArea;
	}




	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public String getDuration() {
		return duration;
	}




	public void setDuration(String duration) {
		this.duration = duration;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public String getActivityArea() {
		return activityArea;
	}




	public void setActivityArea(String activityArea) {
		this.activityArea = activityArea;
	}




	public String getSlNo1() {
		return slNo1;
	}




	public void setSlNo1(String slNo1) {
		this.slNo1 = slNo1;
	}




	public String getDesc1() {
		return desc1;
	}




	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}




	public String getRemark1() {
		return remark1;
	}




	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}




	public String getSlNo2() {
		return slNo2;
	}




	public void setSlNo2(String slNo2) {
		this.slNo2 = slNo2;
	}




	public String getDesc2() {
		return desc2;
	}




	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}




	public String getRemark2() {
		return remark2;
	}




	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}




	public String getTime() {
		return time;
	}




	public void setTime(String time) {
		this.time = time;
	}


	


	public String getUploadBy() {
		return uploadBy;
	}




	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}
	
	
	
	
	public String getEquiType() {
		return equiType;
	}




	public void setEquiType(String equiType) {
		this.equiType = equiType;
	}




	public String getEquiName() {
		return equiName;
	}




	public void setEquiName(String equiName) {
		this.equiName = equiName;
	}




	public String getNdate() {
		return ndate;
	}




	public void setNdate(String ndate) {
		this.ndate = ndate;
	}




	public String getYear() {
		return year;
	}




	public void setYear(String year) {
		this.year = year;
	}




	public String getMonth() {
		return month;
	}




	public void setMonth(String month) {
		this.month = month;
	}




	public String getIlluminationId() {
		return illuminationId;
	}




	public void setIlluminationId(String illuminationId) {
		this.illuminationId = illuminationId;
	}




	public String getLevelinlux() {
		return levelinlux;
	}




	public void setLevelinlux(String levelinlux) {
		this.levelinlux = levelinlux;
	}




	public String getCorrection() {
		return correction;
	}




	public void setCorrection(String correction) {
		this.correction = correction;
	}




	public String getValueaftercorrection() {
		return valueaftercorrection;
	}




	public void setValueaftercorrection(String valueaftercorrection) {
		this.valueaftercorrection = valueaftercorrection;
	}




	public String getDateofCorrection() {
		return dateofCorrection;
	}




	public void setDateofCorrection(String dateofCorrection) {
		this.dateofCorrection = dateofCorrection;
	}




	public String getElectricianSign() {
		return electricianSign;
	}




	public void setElectricianSign(String electricianSign) {
		this.electricianSign = electricianSign;
	}




	public String getIcSign() {
		return icSign;
	}




	public void setIcSign(String icSign) {
		this.icSign = icSign;
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
