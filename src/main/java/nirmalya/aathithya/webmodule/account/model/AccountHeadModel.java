/**
 * 
 */
package nirmalya.aathithya.webmodule.account.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nirmalya Labs
 *
 */
public class AccountHeadModel {
	
	private String accountHeadId;
	private String accountHeadType;
	private String description;
	private String status;
	private String createdDate;	
	private String createdTime;
	private String createdBy;
	
	

	public String getAccountHeadId() {
		return accountHeadId;
	}



	public void setAccountHeadId(String accountHeadId) {
		this.accountHeadId = accountHeadId;
	}



	public String getAccountHeadType() {
		return accountHeadType;
	}



	public void setAccountHeadType(String accountHeadType) {
		this.accountHeadType = accountHeadType;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}



	public String getCreatedTime() {
		return createdTime;
	}



	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
