package nirmalya.aathithya.webmodule.master.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.GoalAssignModel;
import nirmalya.aathithya.webmodule.master.model.GoalFeedbackModel;
import nirmalya.aathithya.webmodule.master.model.GoalReviewModel;
import nirmalya.aathithya.webmodule.master.model.SelfAppraisalModel;

@Controller
@RequestMapping(value = "master")
public class GoalFeedbackController {
	Logger logger = LoggerFactory.getLogger(GoalAssignController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/view-goal-feedback")
	public String feedbackPage(Model model, HttpSession session) {
		logger.info("Method : feedbackPage starts");
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		model.addAttribute("userId", userId);
		//name list
		try {
			DropDownModel[] name = restTemplate.getForObject(env.getMasterUrl() + "getJobTitleList",
					DropDownModel[].class);
			List<DropDownModel> jobTitleList = Arrays.asList(name);
			model.addAttribute("jobTitleList", jobTitleList);
			logger.info("jobTitleList"+jobTitleList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : feedbackPage ends");
		return "master/feedback";
	}
	/*
	 * dropdown for designation list
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-goal-feedback-job-ajax" })
	public @ResponseBody JsonResponse<Object> getDesignationList(@RequestParam String name) {
		logger.info("Method : getDesignationList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-designation-listFB?id=" + name,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getDesignationList ends");
		return res;

	}
	//get designation list on change of name in sidenav
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-goal-feedback-designationlist" })
	public @ResponseBody JsonResponse<Object> getDesignation(@RequestParam String name) {
		logger.info("Method : getDesignationList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getDesignationListfb?id=" + name,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getDesignationList ends");
		return res;

	}
	/*
	 *	save feedback data
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-goal-feedback-add")
	public @ResponseBody JsonResponse<Object> saveFeedbackData(@RequestBody GoalFeedbackModel goalFeedbackModel, HttpSession session) {
		logger.info("Method : saveFeedbackData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
	//	logger.info("11111111111111"+goalFeedbackModel);
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		goalFeedbackModel.setCreatedBy(userId);
		logger.info("11111111111111"+goalFeedbackModel);
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addGoalFeedback", goalFeedbackModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : saveFeedbackData starts");
		return resp;
	}
	/*
	 *	view feedback data
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-goal-feedback-show")
	public @ResponseBody List<GoalFeedbackModel> viewFeedbackData(HttpSession session,@RequestParam String name) {
		logger.info("Method : viewFeedbackData starts");
	logger.info(name);
		JsonResponse<List<GoalFeedbackModel>> resp = new JsonResponse<List<GoalFeedbackModel>>();
		List<GoalFeedbackModel> returnList = new ArrayList<GoalFeedbackModel>();
	
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewFeedbackData?name="+name, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewFeedbackData ends");
		return returnList;
	}
	
	/*
	 * view review employee list
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-goal-feedback-employeelist")
	public @ResponseBody List<GoalFeedbackModel> viewEmployeeList(HttpSession session) {
		logger.info("Method : viewEmployeeList starts");
	
		JsonResponse<List<GoalFeedbackModel>> resp = new JsonResponse<List<GoalFeedbackModel>>();
		
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "view-employee-list?userId=" + userId,JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<GoalFeedbackModel> returnList = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GoalFeedbackModel>>() {
				});

		String dateFormat = "";
		try {

			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}

		if(returnList!= null)
		for (GoalFeedbackModel a : returnList) {
			if (a.getDob() != null && a.getDob() != "") {
				a.setDob(DateFormatter.dateFormat(a.getDob(), dateFormat));
			}
		}
		logger.info("returnList=="+returnList);
		logger.info("Method : viewEmployeeList ends");
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("/view-goal-feedback-empband")
	public @ResponseBody JsonResponse<List<GoalFeedbackModel>> viewEmployeeBandList(HttpSession session,String id) {
		logger.info("Method : viewEmployeeBandList starts");
	
		JsonResponse<List<GoalFeedbackModel>> resp = new JsonResponse<List<GoalFeedbackModel>>();
		
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "view-emp-band?id="+id, JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("returnList=="+resp);
		logger.info("Method : viewEmployeeBandList ends");
		return resp;
	}
	
}
