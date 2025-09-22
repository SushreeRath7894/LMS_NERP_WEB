package nirmalya.aathithya.webmodule.lms.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping("student")
public class lmsCommonController {
	Logger logger = LoggerFactory.getLogger(lmsCommonController.class);

	@Autowired
	EnvironmentVaribles env;
	@Autowired
	RestTemplate restTemplate;

	/*
	 * view all stores in the page
	 */
	@GetMapping("dashboard")
	public String studentDashboard(Model model, HttpSession session) {
		logger.info("Mothod:view student dashboard page started...");

		logger.info("Mothod: view student dashboard page ends...");
		return "lms/student-dashboard";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-getAllHeadCount")
	public @ResponseBody Object getAllHeadCount(HttpSession session) {

		logger.info("Method :getAllHeadCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String org = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			String url = env.getHisUrl()+ "rest-getAllHeadCount?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&userId=" + userId;
			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllHeadCount ends" + resp);

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-getAllOperationalRecord")
	public @ResponseBody Object getAllOperationalRecord(HttpSession session,@RequestParam String id) {

		logger.info("Method :getAllOperationalRecord starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String org = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			String url = env.getHisUrl()+ "rest-getAllOperationalRecord?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&userId=" + userId + "&id=" + id;
			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllOperationalRecord ends" + resp);

		return resp;
	}
	
	
	@GetMapping("classes")
	public String studentClasses(Model model, HttpSession session) {
		logger.info("Mothod:view student classes page started...");

		logger.info("Mothod: view student classes page ends...");
		return "lms/student-classes";
	}
	
	@GetMapping("reference")
	public String studentReferences(Model model, HttpSession session) {
		logger.info("Mothod:view student reference page started...");

		logger.info("Mothod: view student reference page ends...");
		return "lms/reference";
	}
	
	@GetMapping("certifications")
	public String studentCertification(Model model, HttpSession session) {
		logger.info("Mothod:view student certification page started...");

		logger.info("Mothod: view student certification page ends...");
		return "lms/certification";
	}
	
	@GetMapping("examination")
	public String studentExamination(Model model, HttpSession session) {
		logger.info("Mothod:view student examination page started...");

		logger.info("Mothod: view student examination page ends...");
		return "lms/examination";
	}
	@GetMapping("instructor")
	public String instructor(Model model, HttpSession session) {
		logger.info("Mothod:view student examination page started...");
		
		logger.info("Mothod: view student examination page ends...");
		return "lms/lms-instructor";
	}
	
	
	
	
	
	
}
