package nirmalya.aathithya.webmodule.pipeline.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PipelineMeetingModel {
	private String meetingScheduledId;
	private String activityTypeName;
	private String pipelineId;
	private String meetingType;
	private String meetingSummary;
	private String meetingDate;
	private String meetingFromTime;
	private String meetingToTime;
	private Boolean meetingStatus;
	private List<String> meetingAttendies = new ArrayList<String>();
	private String meetingAttendie;
	private Integer meetingAttendieStatus;
	private String meetingCreatedOn;
	private String meetingCreatedBy;
	private String meetingDuration;
	private String meetingTag;
	private String meetingReminder;
	private String meetingLocation;
	private String meetingDesc;
	private Boolean recurrentStatus;
	private Byte repetationNo;
	private String repetationUnit;
	private String repetationUntillEndDate;
	private String repetationUntill;
	private String repetationUntillUnit;
	private String privacy;
	private String showTime;
	private Boolean allDayStatus;
	private Boolean meetingDoneStatus;
	public PipelineMeetingModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public String getMeetingType() {
		return meetingType;
	}



	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}



	public String getMeetingScheduledId() {
		return meetingScheduledId;
	}

	public void setMeetingScheduledId(String meetingScheduledId) {
		this.meetingScheduledId = meetingScheduledId;
	}

	public String getActivityTypeName() {
		return activityTypeName;
	}

	public void setActivityTypeName(String activityTypeName) {
		this.activityTypeName = activityTypeName;
	}

	public String getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(String pipelineId) {
		this.pipelineId = pipelineId;
	}

	public String getMeetingSummary() {
		return meetingSummary;
	}

	public void setMeetingSummary(String meetingSummary) {
		this.meetingSummary = meetingSummary;
	}

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getMeetingFromTime() {
		return meetingFromTime;
	}

	public void setMeetingFromTime(String meetingFromTime) {
		this.meetingFromTime = meetingFromTime;
	}

	public String getMeetingToTime() {
		return meetingToTime;
	}

	public void setMeetingToTime(String meetingToTime) {
		this.meetingToTime = meetingToTime;
	}

	public Boolean getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(Boolean meetingStatus) {
		this.meetingStatus = meetingStatus;
	}

	public String getMeetingCreatedOn() {
		return meetingCreatedOn;
	}

	public void setMeetingCreatedOn(String meetingCreatedOn) {
		this.meetingCreatedOn = meetingCreatedOn;
	}

	public String getMeetingCreatedBy() {
		return meetingCreatedBy;
	}

	public void setMeetingCreatedBy(String meetingCreatedBy) {
		this.meetingCreatedBy = meetingCreatedBy;
	}

	public List<String> getMeetingAttendies() {
		return meetingAttendies;
	}

	public void setMeetingAttendies(List<String> meetingAttendies) {
		this.meetingAttendies = meetingAttendies;
	}

	public Integer getMeetingAttendieStatus() {
		return meetingAttendieStatus;
	}

	public void setMeetingAttendieStatus(Integer meetingAttendieStatus) {
		this.meetingAttendieStatus = meetingAttendieStatus;
	}

	public String getMeetingAttendie() {
		return meetingAttendie;
	}

	public void setMeetingAttendie(String meetingAttendie) {
		this.meetingAttendie = meetingAttendie;
	}

	public String getMeetingDuration() {
		return meetingDuration;
	}

	public void setMeetingDuration(String meetingDuration) {
		this.meetingDuration = meetingDuration;
	}

	public String getMeetingTag() {
		return meetingTag;
	}

	public void setMeetingTag(String meetingTag) {
		this.meetingTag = meetingTag;
	}

	public String getMeetingReminder() {
		return meetingReminder;
	}

	public void setMeetingReminder(String meetingReminder) {
		this.meetingReminder = meetingReminder;
	}

	public String getMeetingLocation() {
		return meetingLocation;
	}

	public void setMeetingLocation(String meetingLocation) {
		this.meetingLocation = meetingLocation;
	}

	public String getMeetingDesc() {
		return meetingDesc;
	}

	public void setMeetingDesc(String meetingDesc) {
		this.meetingDesc = meetingDesc;
	}

	public Boolean getRecurrentStatus() {
		return recurrentStatus;
	}

	public void setRecurrentStatus(Boolean recurrentStatus) {
		this.recurrentStatus = recurrentStatus;
	}

	public Byte getRepetationNo() {
		return repetationNo;
	}

	public void setRepetationNo(Byte repetationNo) {
		this.repetationNo = repetationNo;
	}

	public String getRepetationUntill() {
		return repetationUntill;
	}

	public void setRepetationUntill(String repetationUntill) {
		this.repetationUntill = repetationUntill;
	}

	public String getRepetationUntillUnit() {
		return repetationUntillUnit;
	}

	public void setRepetationUntillUnit(String repetationUntillUnit) {
		this.repetationUntillUnit = repetationUntillUnit;
	}

	public String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public Boolean getAllDayStatus() {
		return allDayStatus;
	}

	public void setAllDayStatus(Boolean allDayStatus) {
		this.allDayStatus = allDayStatus;
	}

	public String getRepetationUnit() {
		return repetationUnit;
	}

	public void setRepetationUnit(String repetationUnit) {
		this.repetationUnit = repetationUnit;
	}

	public String getRepetationUntillEndDate() {
		return repetationUntillEndDate;
	}

	public void setRepetationUntillEndDate(String repetationUntillEndDate) {
		this.repetationUntillEndDate = repetationUntillEndDate;
	}

	public Boolean getMeetingDoneStatus() {
		return meetingDoneStatus;
	}

	public void setMeetingDoneStatus(Boolean meetingDoneStatus) {
		this.meetingDoneStatus = meetingDoneStatus;
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
