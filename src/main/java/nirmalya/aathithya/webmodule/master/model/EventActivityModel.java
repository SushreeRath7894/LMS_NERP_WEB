package nirmalya.aathithya.webmodule.master.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventActivityModel {
	private String eventSlNo;
	private String eventActivityDate;
	private String eventAvtivityStartTime;
	private String eventActivityEndTime;
	private String eventActivity;
	private String eventActivityRemark;
	
	public String getEventSlNo() {
		return eventSlNo;
	}

	public void setEventSlNo(String eventSlNo) {
		this.eventSlNo = eventSlNo;
	}

	public String getEventActivityDate() {
		return eventActivityDate;
	}

	public void setEventActivityDate(String eventActivityDate) {
		this.eventActivityDate = eventActivityDate;
	}

	public String getEventAvtivityStartTime() {
		return eventAvtivityStartTime;
	}

	public void setEventAvtivityStartTime(String eventAvtivityStartTime) {
		this.eventAvtivityStartTime = eventAvtivityStartTime;
	}

	public String getEventActivityEndTime() {
		return eventActivityEndTime;
	}

	public void setEventActivityEndTime(String eventActivityEndTime) {
		this.eventActivityEndTime = eventActivityEndTime;
	}

	public String getEventActivity() {
		return eventActivity;
	}

	public void setEventActivity(String eventActivity) {
		this.eventActivity = eventActivity;
	}

	public String getEventActivityRemark() {
		return eventActivityRemark;
	}

	public void setEventActivityRemark(String eventActivityRemark) {
		this.eventActivityRemark = eventActivityRemark;
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
