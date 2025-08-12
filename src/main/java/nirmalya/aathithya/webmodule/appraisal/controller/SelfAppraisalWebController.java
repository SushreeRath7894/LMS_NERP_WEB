package nirmalya.aathithya.webmodule.appraisal.controller;

import java.util.Map;

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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "master")
public class SelfAppraisalWebController {

	Logger logger = LoggerFactory.getLogger(SelfAppraisalWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/self-appraisal")
	public String selfAppraisal(Model model, HttpSession session) {
		logger.info("Method: goalV2 starts here");

		logger.info("Method: goalV2 ends here");

		return "appraisal-v2/self-appraisal-v2";
	}

	/* get-all-employee-list */
	@SuppressWarnings("unchecked")
	@GetMapping("get-all-employee-list")
	public @ResponseBody Object getAllEmployee(HttpSession session,@RequestParam String finYear) {
		logger.info("Method :getAllEmployee starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId="";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			

			resp = restTemplate.getForObject(
					env.getAppraisalUrl() + "rest-get-all-employee?orgName=" + orgName + "&orgDivision=" + orgDivision +"&id=" +userId + "&finYear=" +finYear ,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		}
		logger.info("Method :getAllEmployee ends" + resp);
		return resp;
	}
	
	/*get-all-desig-goal-list*/
	@SuppressWarnings("unchecked")
	@GetMapping("get-all-desig-goal-list")
	public @ResponseBody Object getAllDesigGoalDLists(HttpSession session, @RequestParam String id) {
		logger.info("Method :getAllDesigGoalDLists starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "rest-all-desig-goal-list?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setBody(resp.getBody());
		resp.setCode(resp.getCode());
		resp.setMessage(resp.getMessage());
		logger.info("Method :getAllDesigGoalDLists ends" + resp);
		return resp;
	}
	
	/* get-all-desig-goal-detail */
	@SuppressWarnings("unchecked")
	@GetMapping("get-all-desig-goal-details-self")
	public @ResponseBody Object getAllDesigGoalDetails(HttpSession session, @RequestParam String id,@RequestParam String empId,@RequestParam String finYear) {
		logger.info("Method :getAllDesigGoalDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "rest-all-desig-goal-details?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id + "&empId=" + empId + "&finYear=" +finYear, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setBody(resp.getBody());
		resp.setCode(resp.getCode());
		resp.setMessage(resp.getMessage());
		logger.info("Method :getAllDesigGoalDetails ends" + resp);
		return resp;
	}
	
	/*save-self-appraisal-goal*/
	@SuppressWarnings("unchecked")
	@PostMapping("save-self-appraisal-goal")
	public @ResponseBody JsonResponse<Object> saveSelfAppraisal(HttpSession session,
			@RequestBody Map<String, Object> depGoalData) {
		logger.info("Method : saveSelfAppraisal starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.postForObject(env.getAppraisalUrl() + "rest-save-self-appraisal-goal?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, depGoalData, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveSelfAppraisal ends");
		return resp;
	}
	
	/* get-all-desig-goal-by-emp */
	@SuppressWarnings("unchecked")
	@GetMapping("get-all-desig-goal-by-emp")
	public @ResponseBody Object getAllDesigGoalByEMpId(HttpSession session, @RequestParam String id,@RequestParam String empId,@RequestParam String finYear) {
		logger.info("Method :getAllDesigGoalByEMpId starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "rest-all-desig-goal-by-emp?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id + "&empId=" + empId + "&finYear=" + finYear, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setBody(resp.getBody());
		resp.setCode(resp.getCode());
		resp.setMessage(resp.getMessage());
		logger.info("Method :getAllDesigGoalByEMpId ends" + resp);
		return resp;
	}
}