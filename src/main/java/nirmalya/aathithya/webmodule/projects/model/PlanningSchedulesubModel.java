package nirmalya.aathithya.webmodule.projects.model;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PlanningSchedulesubModel {
	private String slnoId;
	private String planschid;
	private String createdBy;
	private String OrganizationName;
	private String OrganizationDivision;
	
	public String getPlanschid() {
		return planschid;
	}

	public void setPlanschid(String planschid) {
		this.planschid = planschid;
	}
	private List <ProjectPlanningSchedulingWebModel> taskmodel;

	public List<ProjectPlanningSchedulingWebModel> getTaskmodel() {
		return taskmodel;
	}

	public void setTaskmodel(List<ProjectPlanningSchedulingWebModel> taskmodel) {
		this.taskmodel = taskmodel;
	}

	public String getSlnoId() {
		return slnoId;
	}

	public void setSlnoId(String slnoId) {
		this.slnoId = slnoId;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
