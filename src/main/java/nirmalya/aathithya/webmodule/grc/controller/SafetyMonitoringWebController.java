package nirmalya.aathithya.webmodule.grc.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.grc.model.SafetyPlanningWebModel;

@Controller
@RequestMapping(value = { "grc/" })
public class SafetyMonitoringWebController {

	Logger logger = LoggerFactory.getLogger(SafetyMonitoringWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	SafetyMonitoringWebController assesment;

	@Autowired
	FileUpload fileUpload;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "safety-monitoring" })
	public String SafetyMonitoring(Model model, HttpSession session) {
		logger.info("Method : SafetyMonitoring starts");

		logger.info("Method : SafetyMonitoring ends");
		return "grc/ehs_safety_monitoring";
	}
	@SuppressWarnings("unchecked")
	@GetMapping("safety-monitoring-view-projects")
	public @ResponseBody List<SafetyPlanningWebModel> viewProjectMonitoring(HttpSession session) {
		logger.info("Method : viewProjectMonitoring starts");

		JsonResponse<List<SafetyPlanningWebModel>> resp = new JsonResponse<List<SafetyPlanningWebModel>>();
		List<SafetyPlanningWebModel> returnList = new ArrayList<SafetyPlanningWebModel>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getGrcUrl() + "rest-viewProject?id=" + userId +"&org="+organization + "&orgDiv="+ orgDivision,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewProjectMonitoring ends"+returnList);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("safety-monitoring-view-safety")
	public @ResponseBody List<SafetyPlanningWebModel> viewSafetyAssess(@RequestParam String id,HttpSession session) {
		logger.info("Method : viewSafetyAssess starts"+id);

		JsonResponse<List<SafetyPlanningWebModel>> resp = new JsonResponse<List<SafetyPlanningWebModel>>();
		List<SafetyPlanningWebModel> returnList = new ArrayList<SafetyPlanningWebModel>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getGrcUrl() + "rest-viewSafetyAssess?id="+ id + "&userId=" + userId+"&org="+organization+"&orgDiv="+orgDivision,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewSafetyAssess ends"+returnList);
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("safety-monitoring-view-action")
	public @ResponseBody List<SafetyPlanningWebModel> viewSafetyMonitoring(@RequestParam String id,
			@RequestParam String pId, HttpSession session, Model model) {
		logger.info("Method : viewSafetyMonitoring starts" + id + "@@@ " + pId);

		JsonResponse<List<SafetyPlanningWebModel>> resp = new JsonResponse<List<SafetyPlanningWebModel>>();
		List<SafetyPlanningWebModel> returnList = new ArrayList<SafetyPlanningWebModel>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getGrcUrl() + "rest-viewSafetyPlanning?id="+ id + "&pid=" + pId + "&userId=" + userId+"&org="+organization+"&orgDiv="+orgDivision,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewSafetyMonitoring ends"+returnList);
		return returnList;
	}
}
