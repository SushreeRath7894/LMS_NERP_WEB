package nirmalya.aathithya.webmodule.projects.model;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.projects.model.ProjectAgendaModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectAttendeeModel;

public class ProjectMonitoringWebModel {
	/* FOR PROJECT HEALTH */
	private String slnoId;
	private String phase1;
	private String assignedTo;
	private String plannedStartdate;
	private String plannedEnddate;
	private String actualStartdate;
	private String actualEnddate;
	private String slipageDays1;
	private String qualityNeeded;
	private String qualityRecieved;
	private String varianceQuantity;
	private String hoursPlanned;
	private String varianceHours;
	private String costPlanned;
	private String costActual;
	private String varianceRupees;
	private String dateNeeded;
	private String dateRecived;
	private String slipageDays2;
	private String attachmentId;
	private String notes;
	/* FOR RUNNING PROJECT */
	private String projectId;
	private String projectName;
	private String location2;
	private String customer;
	private String status;
	/* FOR FORECASTING */
	private String slnoId4;
	private String phase;
	private String plannedcompletationDate;
	private String expectedcompletationDate;
	private String budgetedCost;
	private String projectedCost;
	
	private String createdBy;
	private String CreatedOn;
	private String UpdatedBy;
	private String UpdatedOn;
	private String OrganizationName;
	private String OrganizationDivision;
	
	/* FOR CATCH UP */
	private String catchupId;
	private String host;
	private String location;
	private String date;
	private String time;
	
		
	/* FOR ATTENDEE */
	private String nameId;
	private String designation;


	/* FOR AGENDA */
	private String slnoId1;
	private String description;
	private String action;
	private String owner;
	
	
	private List<ProjectAttendeeModel> attendee;
	private List<ProjectAgendaModel> agenda;
	
	/* FOR PROJECT HEALTH NOTES */
	private String noteId;
	private String notesName;
	private String notesDate;
	private String task;
	private String dependancies;
	private String notesId1;
		
	
	public ProjectMonitoringWebModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/* FOR PROJECT HEALTH */
	public String getSlnoId() {
		return slnoId;
	}
	public void setSlnoId(String slnoId) {
		this.slnoId = slnoId;
	}
	public String getPhase1() {
		return phase1;
	}
	public void setPhase1(String phase1) {
		this.phase1 = phase1;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getPlannedStartdate() {
		return plannedStartdate;
	}
	public void setPlannedStartdate(String plannedStartdate) {
		this.plannedStartdate = plannedStartdate;
	}
	public String getPlannedEnddate() {
		return plannedEnddate;
	}
	public void setPlannedEnddate(String plannedEnddate) {
		this.plannedEnddate = plannedEnddate;
	}
	public String getActualStartdate() {
		return actualStartdate;
	}
	public void setActualStartdate(String actualStartdate) {
		this.actualStartdate = actualStartdate;
	}
	public String getActualEnddate() {
		return actualEnddate;
	}
	public void setActualEnddate(String actualEnddate) {
		this.actualEnddate = actualEnddate;
	}
	public String getSlipageDays1() {
		return slipageDays1;
	}
	public void setSlipageDays1(String slipageDays1) {
		this.slipageDays1 = slipageDays1;
	}
	public String getQualityNeeded() {
		return qualityNeeded;
	}
	public void setQualityNeeded(String qualityNeeded) {
		this.qualityNeeded = qualityNeeded;
	}
	public String getQualityRecieved() {
		return qualityRecieved;
	}
	public void setQualityRecieved(String qualityRecieved) {
		this.qualityRecieved = qualityRecieved;
	}
	public String getVarianceQuantity() {
		return varianceQuantity;
	}
	public void setVarianceQuantity(String varianceQuantity) {
		this.varianceQuantity = varianceQuantity;
	}
	public String getHoursPlanned() {
		return hoursPlanned;
	}
	public void setHoursPlanned(String hoursPlanned) {
		this.hoursPlanned = hoursPlanned;
	}
	public String getVarianceHours() {
		return varianceHours;
	}
	public void setVarianceHours(String varianceHours) {
		this.varianceHours = varianceHours;
	}
	public String getCostPlanned() {
		return costPlanned;
	}
	public void setCostPlanned(String costPlanned) {
		this.costPlanned = costPlanned;
	}
	public String getCostActual() {
		return costActual;
	}
	public void setCostActual(String costActual) {
		this.costActual = costActual;
	}
	public String getVarianceRupees() {
		return varianceRupees;
	}
	public void setVarianceRupees(String varianceRupees) {
		this.varianceRupees = varianceRupees;
	}
	public String getDateNeeded() {
		return dateNeeded;
	}
	public void setDateNeeded(String dateNeeded) {
		this.dateNeeded = dateNeeded;
	}
	public String getDateRecived() {
		return dateRecived;
	}
	public void setDateRecived(String dateRecived) {
		this.dateRecived = dateRecived;
	}
	public String getSlipageDays2() {
		return slipageDays2;
	}
	public void setSlipageDays2(String slipageDays2) {
		this.slipageDays2 = slipageDays2;
	}
	public String getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/* FOR RUNNING PROJECT */
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLocation2() {
		return location2;
	}

	public void setLocation2(String location2) {
		this.location2 = location2;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	/* FOR FORECASTING */
	public String getSlnoId4() {
		return slnoId4;
	}

	public void setSlnoId4(String slnoId4) {
		this.slnoId4 = slnoId4;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getPlannedcompletationDate() {
		return plannedcompletationDate;
	}

	public void setPlannedcompletationDate(String plannedcompletationDate) {
		this.plannedcompletationDate = plannedcompletationDate;
	}

	public String getExpectedcompletationDate() {
		return expectedcompletationDate;
	}

	public void setExpectedcompletationDate(String expectedcompletationDate) {
		this.expectedcompletationDate = expectedcompletationDate;
	}

	public String getBudgetedCost() {
		return budgetedCost;
	}

	public void setBudgetedCost(String budgetedCost) {
		this.budgetedCost = budgetedCost;
	}

	public String getProjectedCost() {
		return projectedCost;
	}

	public void setProjectedCost(String projectedCost) {
		this.projectedCost = projectedCost;
	}
	/* FOR CATCH UP */
	
	public String getCatchupId() {
		return catchupId;
	}

	public void setCatchupId(String catchupId) {
		this.catchupId = catchupId;
	}


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSlnoId1() {
		return slnoId1;
	}

	public void setSlnoId1(String slnoId1) {
		this.slnoId1 = slnoId1;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<ProjectAttendeeModel> getAttendee() {
		return attendee;
	}

	public void setAttendee(List<ProjectAttendeeModel> attendee) {
		this.attendee = attendee;
	}

	public List<ProjectAgendaModel> getAgenda() {
		return agenda;
	}

	public void setAgenda(List<ProjectAgendaModel> agenda) {
		this.agenda = agenda;
	}
	
	/* FOR PROJECT HEALTH NOTES */
	
	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	
	public String getNotesName() {
		return notesName;
	}

	public void setNotesName(String notesName) {
		this.notesName = notesName;
	}

	public String getNotesDate() {
		return notesDate;
	}

	public void setNotesDate(String notesDate) {
		this.notesDate = notesDate;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getDependancies() {
		return dependancies;
	}

	public void setDependancies(String dependancies) {
		this.dependancies = dependancies;
	}

	public String getNotesId1() {
		return notesId1;
	}

	public void setNotesId1(String notesId1) {
		this.notesId1 = notesId1;
	}

	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return CreatedOn;
	}
	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}
	public String getUpdatedBy() {
		return UpdatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}
	public String getUpdatedOn() {
		return UpdatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		UpdatedOn = updatedOn;
	}
	public String getOrganizationName() {
		return OrganizationName;
	}
	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}
	public String getOrganizationDivision() {
		return OrganizationDivision;
	}
	public void setOrganizationDivision(String organizationDivision) {
		OrganizationDivision = organizationDivision;
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


