package nirmalya.aathithya.webmodule.appraisal.controller;

import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.appraisal.model.AppraisalGoalModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "master")
public class ReviewWebController {

	Logger logger = LoggerFactory.getLogger(ReviewWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/review")
	public String reviewV2(Model model, HttpSession session) {
		logger.info("Method: reviewV2 starts here");

		logger.info("Method: reviewV2 ends here");

		return "appraisal-v2/review-v2";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("get-all-employee-list-auto-search")
	public @ResponseBody JsonResponse<DropDownModel> autoSearchEmployeeList(Model model,
			@RequestParam String searchValue, HttpServletRequest request, HttpSession session) {
		logger.info("Method : autoSearchEmployeeList starts  " + searchValue);
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restTemplate.getForObject(env.getAppraisalUrl() + "rest-all-employee-list-auto?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("success");
		} else {
			res.setMessage("Unsuccess");
		}
		logger.info("Method : autoSearchEmployeeList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("get-all-360-employee-list")
	public @ResponseBody Object getAllFeedbackEmployee(HttpSession session,@RequestParam String finYear) {
		logger.info("Method :getAllFeedbackEmployee starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "rest-get-all-feedback-employee?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + userId +"&finYear=" + finYear, JsonResponse.class);

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
		logger.info("Method :getAllFeedbackEmployee ends" + resp);
		return resp;
	}

	/* save-manager-remark-details */
	@SuppressWarnings("unchecked")
	@PostMapping("save-manager-remark-details")
	public @ResponseBody JsonResponse<Object> savemanageRemarks(HttpSession session,
			@RequestBody Map<String, Object> managerRemarksData) {
		logger.info("Method : savemanageRemarks starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		String baseUrl = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			baseUrl = env.getBaseURL();
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.postForObject(env.getAppraisalUrl() + "rest-savemanager-details?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision +"&baseUrl=" + baseUrl, managerRemarksData, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : savemanageRemarks ends");
		return resp;
	}
	
	/*get-all-kra-lists*/
	@SuppressWarnings("unchecked")
	@GetMapping("get-all-kra-lists")
	public @ResponseBody Object getAllKraLists(HttpSession session, @RequestParam String id) {
		logger.info("Method :getAllKraLists starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "rest-get-kra-lists?orgName=" + orgName
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
		logger.info("Method :getAllKraLists ends" + resp);
		return resp;
	}
	
	
	/*get-feedback-details*/
	@SuppressWarnings("unchecked")
	@GetMapping("get-feedback-details")
	public @ResponseBody Object getAllFeedbackDetails(HttpSession session, @RequestParam String id,@RequestParam String finYear) {
		logger.info("Method :getAllFeedbackDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "rest-get-feedback-details?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id +"&finYear=" + finYear, JsonResponse.class);

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
		logger.info("Method :getAllFeedbackDetails ends" + resp);
		return resp;
	}
	
	
}
