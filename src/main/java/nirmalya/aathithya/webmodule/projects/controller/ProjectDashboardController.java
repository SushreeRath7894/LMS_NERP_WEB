package nirmalya.aathithya.webmodule.projects.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.projects.model.ProjectBudgetHourGraph;
import nirmalya.aathithya.webmodule.projects.model.ProjectDashboardModel;

@Controller
@RequestMapping(value = "projects/")
public class ProjectDashboardController {
	Logger logger = LoggerFactory.getLogger(ProjectDashboardController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping("/project-dashboard")
	public String projectDashboard(Model model, HttpSession session) {

	logger.info("Method : projectDashboard starts");
	
	DropDownModel[] projectCount = restClient.getForObject(env.getProjects() + "projectCount", DropDownModel[].class);
	List<DropDownModel> totalProjects = Arrays.asList(projectCount);
	model.addAttribute("totalProjects", totalProjects);
	

	DropDownModel[] internalCount = restClient.getForObject(env.getProjects() + "internalCount", DropDownModel[].class);
	List<DropDownModel> internalProjects = Arrays.asList(internalCount);
	model.addAttribute("internalProjects", internalProjects);
	

	DropDownModel[] externalcount = restClient.getForObject(env.getProjects() + "externalcount", DropDownModel[].class);
	List<DropDownModel> externalProjects = Arrays.asList(externalcount);
	model.addAttribute("externalProjects", externalProjects);
	/*
	 * priority table
	 */
	DropDownModel[] projectPriority = restClient.getForObject(env.getProjects() + "projectPriority", DropDownModel[].class);
	List<DropDownModel> projectPriorityList = Arrays.asList(projectPriority);
	model.addAttribute("projectPriorityList", projectPriorityList);
	
	ProjectDashboardModel[] projecttable = restClient.getForObject(env.getProjects() + "projecttable",
			ProjectDashboardModel[].class);
	List<ProjectDashboardModel> projectTable = Arrays.asList(projecttable);
	model.addAttribute("projectTable", projectTable);
	
	/* total Budget */
	DropDownModel[] totalBudgetCount= restClient.getForObject(env.getProjects() + "totalBudgetCount", DropDownModel[].class);
	List<DropDownModel> totalBudget = Arrays.asList(totalBudgetCount);
	model.addAttribute("totalBudget", totalBudget);
	/* total no resource */
	DropDownModel[] noResourceCount = restClient.getForObject(env.getProjects() + "noResourceCount", DropDownModel[].class);
	List<DropDownModel> noResource = Arrays.asList(noResourceCount);
	model.addAttribute("noResource", noResource);
	
	logger.info("Method : projectDashboard ends");
	return "projects/project-dashboard";
	}
	/*
	 * Priority pie For Graph
	 */

	@GetMapping("project-dashboard-priority-pie")
	public @ResponseBody JsonResponse<Object> getPriorityPieChart(HttpSession session) {

		logger.info("Method : getPriorityPieChart starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			ProjectDashboardModel[] auditTypes = restClient.getForObject(env.getProjects() + "getProjectPriority",
					ProjectDashboardModel[].class);
			List<ProjectDashboardModel> dataList = Arrays.asList(auditTypes);
			
			resp.setBody(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(resp);
		logger.info("Method : getPriorityPieChart ends");

		return resp;
	}
	/*
	 * Department pie For Graph
	 */
	
	@GetMapping("project-dashboard-department-graph")
	public @ResponseBody JsonResponse<Object> getDepartmentGraph(HttpSession session) {
		
		logger.info("Method : getDepartmentGraph starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			ProjectDashboardModel[] auditTypes = restClient.getForObject(env.getProjects() + "getDepartmentGraph",
					ProjectDashboardModel[].class);
			List<ProjectDashboardModel> dataList = Arrays.asList(auditTypes);
			
			resp.setBody(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(resp);
		logger.info("Method : getDepartmentGraph ends");
		
		return resp;
	}
	/*
	 * Hour  For Graph
	 */
	
	@GetMapping("project-dashboard-hours-graph")
	public @ResponseBody JsonResponse<Object> getBudgetHourGraph(HttpSession session) {
		
		logger.info("Method : getBudgetHourGraph starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			ProjectBudgetHourGraph[] auditTypes = restClient.getForObject(env.getProjects() + "getHourGraph",
					ProjectBudgetHourGraph[].class);
			List<ProjectBudgetHourGraph> dataList = Arrays.asList(auditTypes);
			
			resp.setBody(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(resp);
		logger.info("Method : getBudgetHourGraph ends");
		
		return resp;
	}
}
