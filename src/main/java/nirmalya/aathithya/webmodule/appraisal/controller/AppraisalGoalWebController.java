package nirmalya.aathithya.webmodule.appraisal.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nirmalya.aathithya.webmodule.appraisal.model.AppraisalGoalModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "master")
public class AppraisalGoalWebController {
	Logger logger = LoggerFactory.getLogger(AppraisalGoalWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/goal")
	public String goalV2(Model model, HttpSession session) {
		logger.info("Method: goalV2 starts here");

		try {

			DropDownModel[] departmentType = restTemplate.getForObject(env.getAppraisalUrl() + "departmentList",
					DropDownModel[].class);
			List<DropDownModel> departmentList = Arrays.asList(departmentType);
			model.addAttribute("departmentList", departmentList);

			DropDownModel[] orgGoalLists = restTemplate.getForObject(env.getAppraisalUrl() + "orgGoalList",
					DropDownModel[].class);
			List<DropDownModel> orgGoalList = Arrays.asList(orgGoalLists);
			model.addAttribute("orgGoalList", orgGoalList);

			DropDownModel[] depGoalLists = restTemplate.getForObject(env.getAppraisalUrl() + "depGoalList",
					DropDownModel[].class);
			List<DropDownModel> depGoalList = Arrays.asList(depGoalLists);
			model.addAttribute("depGoalList", depGoalList);

			DropDownModel[] designationLists = restTemplate.getForObject(env.getAppraisalUrl() + "designationList",
					DropDownModel[].class);
			List<DropDownModel> designationList = Arrays.asList(designationLists);
			model.addAttribute("designationList", designationList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method: goalV2 ends here");

		return "appraisal-v2/goal-v2";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("save-appraisal-details")
	public @ResponseBody JsonResponse<Object> saveGoal(@RequestBody AppraisalGoalModel goal, HttpSession session) {
		logger.info("Method : saveGoal starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		goal.setCreatedBy(userId);
		goal.setOrganizationName(orgName);
		goal.setOrganizationDivision(orgDiv);

		try {
			resp = restTemplate.postForObject(env.getAppraisalUrl() + "rest-save-goal", goal, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setMessage(resp.getMessage());
		resp.setCode(resp.getCode());

		logger.info("Method : saveGoal starts");
		return resp;
	}

	/* get-appraisal-details */
	@SuppressWarnings("unchecked")
	@GetMapping("get-appraisal-details")
	public @ResponseBody Object getAllGoal(HttpSession session) {
		logger.info("Method :getAllGoal starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(
					env.getAppraisalUrl() + "rest-get-all-goal?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :getAllGoal ends" + resp);
		return resp;
	}

	/* save-appraisal-kra-details */
	@SuppressWarnings("unchecked")
	@PostMapping("save-appraisal-kra-details")
	public @ResponseBody JsonResponse<Object> saveApppraisalKraDetails(HttpSession session,
			@RequestBody Map<String, Object> appraisalData) {
		logger.info("Method : saveApppraisalKraDetails starts");

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
			resp = restTemplate.postForObject(env.getAppraisalUrl() + "rest-appraisal-kra-details?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, appraisalData, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveApppraisalKraDetails ends");
		return resp;
	}

	/* get-all-goal-details */
	@SuppressWarnings("unchecked")
	@GetMapping("get-all-goal-details")
	public @ResponseBody Object getAllGoalDetails(HttpSession session, @RequestParam String id) {
		logger.info("Method :getAllGoalDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "rest-get-all-goal-details?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

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
		logger.info("Method :getAllGoalDetails ends" + resp);
		return resp;
	}

	/* save-appraisal-dep-details */
	@SuppressWarnings("unchecked")
	@PostMapping("save-appraisal-dep-details")
	public @ResponseBody JsonResponse<Object> saveDepGoal(@RequestBody AppraisalGoalModel goal, HttpSession session) {
		logger.info("Method : saveDepGoal starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		goal.setCreatedBy(userId);
		goal.setOrganizationName(orgName);
		goal.setOrganizationDivision(orgDiv);

		try {
			resp = restTemplate.postForObject(env.getAppraisalUrl() + "rest-save-dep-goal", goal, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setMessage(resp.getMessage());
		resp.setCode(resp.getCode());

		logger.info("Method : saveDepGoal starts");
		return resp;
	}

	/* get-appraisal-dep-goal */
	@SuppressWarnings("unchecked")
	@GetMapping("get-appraisal-dep-details")
	public @ResponseBody Object getAllDepGoal(HttpSession session) {
		logger.info("Method :getAllDepGoal starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(
					env.getAppraisalUrl() + "rest-get-all-dep-goal?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :getAllDepGoal ends" + resp);
		return resp;
	}

	/* save-appraisal-dep-kra-details */
	@SuppressWarnings("unchecked")
	@PostMapping("save-appraisal-dep-kra-details")
	public @ResponseBody JsonResponse<Object> saveApppraisalDepKraDetails(HttpSession session,
			@RequestBody Map<String, Object> deptAppraisalData) {
		logger.info("Method : saveApppraisalDepKraDetails starts");

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
			resp = restTemplate.postForObject(env.getAppraisalUrl() + "rest-appraisal-dep-kra-details?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, deptAppraisalData, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveApppraisalDepKraDetails ends");
		return resp;
	}

	/* get-all-dep-goal-details */
	@SuppressWarnings("unchecked")
	@GetMapping("get-all-dep-goal-details")
	public @ResponseBody Object getAllDepGoalDetails(HttpSession session, @RequestParam String id) {
		logger.info("Method :getAllDepGoalDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "get-all-dep-goal-details?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setBody(resp.getBody());
		resp.setCode(resp.getCode());
		resp.setMessage(resp.getMessage());
		logger.info("Method :getAllDepGoalDetails ends" + resp);
		return resp;
	}

	/* get-appraisal-dep-details */
	@SuppressWarnings("unchecked")
	@GetMapping("get-appraisal-dep-goal-details")
	public @ResponseBody Object getDepartmentGoalDetails(HttpSession session, @RequestParam String id) {
		logger.info("Method :getDepartmentGoalDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "rest-get-all-dep-goal-details?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

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
		logger.info("Method :getDepartmentGoalDetails ends" + resp);
		return resp;
	}

	/* save-appraisal-desig-details */
	@SuppressWarnings("unchecked")
	@PostMapping("save-appraisal-desig-details")
	public @ResponseBody JsonResponse<Object> saveDesigGoal(@RequestBody AppraisalGoalModel goal, HttpSession session) {
		logger.info("Method : saveDesigGoal starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		goal.setCreatedBy(userId);
		goal.setOrganizationName(orgName);
		goal.setOrganizationDivision(orgDiv);

		try {
			resp = restTemplate.postForObject(env.getAppraisalUrl() + "rest-save-desig-goal", goal, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setMessage(resp.getMessage());
		resp.setCode(resp.getCode());

		logger.info("Method : saveDesigGoal starts");
		return resp;
	}

	/* get-appraisal-desig-details */

	@SuppressWarnings("unchecked")
	@GetMapping("get-appraisal-desig-details")
	public @ResponseBody Object getAllDesigGoal(HttpSession session) {
		logger.info("Method :getAllDesigGoal starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "rest-get-all-desig-goal?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);

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
		logger.info("Method :getAllDesigGoal ends" + resp);
		return resp;
	}

	/* save-appraisal-desig-kra-details */

	@SuppressWarnings("unchecked")
	@PostMapping("save-appraisal-desig-kra-details")
	public @ResponseBody JsonResponse<Object> saveApppraisalDesigKraDetails(HttpSession session,
			@RequestBody Map<String, Object> desigAppraisalData) {
		logger.info("Method : saveApppraisalDesigKraDetails starts");

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
			resp = restTemplate.postForObject(env.getAppraisalUrl() + "rest-appraisal-desig-kra-details?userId="
					+ userId + "&org=" + orgName + "&orgDiv=" + orgDivision, desigAppraisalData, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveApppraisalDesigKraDetails ends");
		return resp;
	}

	/* get-all-desig-goal-details */
	@SuppressWarnings("unchecked")
	@GetMapping("get-all-desig-goal-details")
	public @ResponseBody Object getAllDesigGoalDetails(HttpSession session, @RequestParam String id) {
		logger.info("Method :getAllDesigGoalDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "get-all-desig-goal-details?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setBody(resp.getBody());
		resp.setCode(resp.getCode());
		resp.setMessage(resp.getMessage());
		logger.info("Method :getAllDesigGoalDetails ends" + resp);
		return resp;
	}

	@GetMapping(value = { "get-all-org-goal-list" })
	@ResponseBody
	public List<DropDownModel> getAllOrgList(HttpSession session) {
	    logger.info("Method : getAllOrgList starts");

	    DropDownModel[] orgGoalLists = restTemplate.getForObject(
	        env.getAppraisalUrl() + "orgGoalList",
	        DropDownModel[].class
	    );

	    logger.info("Method : getAllOrgList ends");

	    return Arrays.asList(orgGoalLists);
	}

}
