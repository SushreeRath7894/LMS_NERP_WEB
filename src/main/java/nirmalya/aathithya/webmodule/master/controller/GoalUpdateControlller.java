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
import nirmalya.aathithya.webmodule.master.model.GoalUpdateModel;

@Controller
@RequestMapping(value = "master/")
public class GoalUpdateControlller {
	Logger logger = LoggerFactory.getLogger(GoalMasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/view-goal-update")
	public String update(Model model, HttpSession session) {
		logger.info("Method : update starts");

		String userId = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String splitData[] = userRole.split("r");

		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}

		}
		model.addAttribute("userId", userId);
		model.addAttribute("userRole", userRole);
		// name list
		try {
			DropDownModel[] name = restTemplate.getForObject(env.getMasterUrl() + "getNameListU?id=" + userId,
					DropDownModel[].class);
			List<DropDownModel> nameList = Arrays.asList(name);
			model.addAttribute("nameList", nameList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] designation = restTemplate.getForObject(env.getMasterUrl() + "getdesignationList",
					DropDownModel[].class);
			List<DropDownModel> designationList = Arrays.asList(designation);
			model.addAttribute("designationList", designationList);
			logger.info("designationList" + designationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : update ends");
		return "master/update";
	}

	/*
	 * dropdown for designation list
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-goal-update-ajax" })
	public @ResponseBody JsonResponse<Object> getDesignationList(@RequestParam String name) {
		logger.info("Method : getDesignationList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-designation-listU?id=" + name,
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
		logger.info("res====" + res);
		logger.info("Method : getDesignationList ends");
		return res;
	}
	/*
	 * view recommendation,final rating
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-goal-update-ajax-recommendation" })
	public @ResponseBody JsonResponse<Object> getRecommendation(@RequestParam String name) {
		logger.info("Method : getRecommendation starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-recommendation?id=" + name,
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
		logger.info("res====" + res);
		logger.info("Method : getRecommendation ends");
		return res;
	}

	/*
	 * view onetoone ag-grid data
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-goal-update-result")
	public @ResponseBody List<GoalUpdateModel> viewUpdateData(HttpSession session, String name) {
		logger.info("Method : viewUpdateData starts");

		JsonResponse<List<GoalUpdateModel>> resp = new JsonResponse<List<GoalUpdateModel>>();
		List<GoalUpdateModel> returnList = new ArrayList<GoalUpdateModel>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewUpdateData?name=" + name, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("1111111111111111" + returnList);
		logger.info("Method : viewUpdateData ends");
		return returnList;
	}

	/*
	 * final submit goal update
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-goal-update-add")
	public @ResponseBody JsonResponse<Object> addGoalUpdate(@RequestBody List<GoalUpdateModel> update,
			HttpSession session) {
		logger.info("Method : addGoalUpdate starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addGoalUpdate", update, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addGoalUpdate starts");
		return resp;
	}

	/*
	 * dropdown for designation list
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-goal-update-recommendation-show" })
	public @ResponseBody JsonResponse<Object> getRecommendationData(@RequestParam String name) {
		logger.info("Method : getRecommendationData starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-recommendation-list?id=" + name,
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
		logger.info("res====" + res);
		logger.info("Method : getRecommendationData ends");
		return res;
	}
}
