package nirmalya.aathithya.webmodule.projects.controller;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import groovyjarjarpicocli.CommandLine.Model;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "projects")
public class ProjectDashboardWebController {
	Logger logger = LoggerFactory.getLogger(ProjectDashboardWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "operational-dashboard" })
	public String createProject(Model model, HttpSession session) {
		logger.info("Method : ProjectDashboard starts");

		logger.info("Method : createProject ends");
		return "projects/operational-dashboard";
	}

	// =================================

	@SuppressWarnings("unchecked")

	@GetMapping("create-project-view-projectHeadData")
	public @ResponseBody Object getCount(HttpSession session) {

		logger.info("Method :projectHeadData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			resp = restClient.getForObject(
					env.getProjects() + "projectHeadData?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :projectHeadData ends" + resp);

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("create-project-view-getAllReport")
	public @ResponseBody Object projectDashoardAllReport(HttpSession session, @RequestParam String id) {

		logger.info("Method :projectDashoardAllReport starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			resp = restClient.getForObject(env.getProjects() + "projectDashoardAllReport?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :projectDashoardAllReport ends" + resp);

		return resp;
	}
	
}