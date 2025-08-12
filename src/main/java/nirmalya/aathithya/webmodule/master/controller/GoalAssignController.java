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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.GoalAssignModel;
@Controller
@RequestMapping(value = "master")
public class GoalAssignController {
	Logger logger = LoggerFactory.getLogger(GoalAssignController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/view-goal-assign")
	public String asignPage(Model model, HttpSession session) {
		logger.info("Method : asignPage starts");
		String userId = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userRole", userRole);
		/*
		 * get name list
		 */
		try {
			DropDownModel[] name = restTemplate.getForObject(env.getMasterUrl() + "get-name-list?id=" + userId,
					DropDownModel[].class);
			List<DropDownModel> nameList = Arrays.asList(name);
			model.addAttribute("nameList", nameList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : asignPage ends");
		return "master/goalAssign.html";
	}
	/*
	 * get designation list
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-goal-assign-job-ajax" })
	public @ResponseBody JsonResponse<Object> getDesignationList(@RequestParam String name) {
		logger.info("Method : getDesignationList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-designationList?id=" + name,
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
	 * view explore data
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-goal-assign-explore")
	public @ResponseBody List<GoalAssignModel> viewExploreData(HttpSession session) {
		logger.info("Method : viewExploreData starts");
	
		JsonResponse<List<GoalAssignModel>> resp = new JsonResponse<List<GoalAssignModel>>();
		List<GoalAssignModel> returnList = new ArrayList<GoalAssignModel>();
	
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewExploreData", JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewExploreData ends");
		return returnList;
	}
	/*
	 * add goal to assigned table 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-goal-assign-add")
	public @ResponseBody JsonResponse<Object> addGoal(@RequestBody List<GoalAssignModel> goalAssignModel,
			HttpSession session) {
		logger.info("Method : addGoalMaster starts");
		logger.info("#################"+goalAssignModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (GoalAssignModel a : goalAssignModel) {
			a.getGoalName();
			a.setCreatedBy(userId);
		}
		
		try {
			logger.info("#################"+goalAssignModel);
			resp = restTemplate.postForObject(env.getMasterUrl() + "addGoalAssign", goalAssignModel,JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addGoalMaster ends");
		return resp;
	}
	/*
	 * view assigned data
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-goal-assign-by-emp")
	public @ResponseBody List<GoalAssignModel> viewAssignedData(HttpSession session,String name) {
		logger.info("Method : viewAssignedData starts");
	
		JsonResponse<List<GoalAssignModel>> resp = new JsonResponse<List<GoalAssignModel>>();
		List<GoalAssignModel> returnList = new ArrayList<GoalAssignModel>();
		logger.info("$$$$$$$$$$$$$"+name);
		
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewAssignData?name="+name, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewAssignedData ends");
		return returnList;
	}
}
