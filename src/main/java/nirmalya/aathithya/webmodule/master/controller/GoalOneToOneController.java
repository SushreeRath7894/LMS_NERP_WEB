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
import nirmalya.aathithya.webmodule.master.model.GoalOneToOneModel;

@Controller
@RequestMapping(value = "master/")
public class GoalOneToOneController {
	Logger logger = LoggerFactory.getLogger(GoalOneToOneController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	
	@GetMapping("/view-goal-onetoone")
	public String onetoone(Model model, HttpSession session) {
		logger.info("Method : onetoone starts");
		
		//name list
		try {
			DropDownModel[] name = restTemplate.getForObject(env.getMasterUrl() + "getNameListO",
					DropDownModel[].class);
			List<DropDownModel> nameList = Arrays.asList(name);
			model.addAttribute("nameList", nameList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}


		logger.info("Method : onetoone ends");
		return "master/oneToOne";
	}
	
	/*
	 * dropdown for designation list
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-goal-onetoone-ajax" })
	public @ResponseBody JsonResponse<Object> getDesignationList(@RequestParam String name) {
		logger.info("Method : getDesignationList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-designation-listO?id=" + name,
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
	 * view onetoone ag-grid data
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-goal-onetoone-result")
	public @ResponseBody List<GoalOneToOneModel> viewOneToOneData(HttpSession session,String name) {
		logger.info("Method : viewExploreData starts");
		
		JsonResponse<List<GoalOneToOneModel>> resp = new JsonResponse<List<GoalOneToOneModel>>();
		List<GoalOneToOneModel> returnList = new ArrayList<GoalOneToOneModel>();
	
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewOneToOneData?name="+name, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewExploreData ends");
		logger.info("1111111111111111"+returnList);
		return returnList;
	}
	/*
	 * final submit goal review 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-goal-onetoone-add")
	public @ResponseBody JsonResponse<Object> addGoalOneToOne(@RequestBody List<GoalOneToOneModel> onetoone, HttpSession session) {
		logger.info("Method : addGoalOneToOne starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addGoalOneToOne", onetoone, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addGoalOneToOne starts");
		return resp;
	}
}
