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
import nirmalya.aathithya.webmodule.master.model.GoalMasterModel;
import nirmalya.aathithya.webmodule.master.model.SalaryRevisionModel;

@Controller
@RequestMapping(value = "master/")
public class GoalMasterController {
	Logger logger = LoggerFactory.getLogger(GoalMasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	/*********************** Goal **********************/
	@GetMapping("view-goal")
	public String masterGoal(Model model, HttpSession session) {
		logger.info("Method : goalMaster starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(env.getMasterUrl() + "getBandMasterAttendance?organization=" + organization+"&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
			model.addAttribute("bandType", bandType);
			logger.info("DZSGh" + bandType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : goalMaster ends");
		return "master/goalMaster";
	}
	// View Travel Reimbrusiement

	@SuppressWarnings("unchecked")

	@GetMapping("/view-goal-master")
	public @ResponseBody List<GoalMasterModel> viewGoalMaster(HttpSession session) {

		logger.info("Method : viewReimbursementOther");

		JsonResponse<List<GoalMasterModel>> resp = new JsonResponse<List<GoalMasterModel>>();
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewGoalMaster", JsonResponse.class);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<GoalMasterModel> viewGoalMaster = mapper.convertValue(resp.getBody(),
				new TypeReference<List<GoalMasterModel>>() {
				});

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : viewGoalMaster ends");
		logger.info("VIEWWW"+viewGoalMaster);
		return viewGoalMaster;

	}
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("/view-goal-master-add") public @ResponseBody
	 * JsonResponse<Object> addGoalMaster(@RequestBody List<GoalMasterModel>
	 * goalMasterModel, HttpSession session) {
	 * logger.info("Method : addGoalMaster starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>();
	 * 
	 * String userId = ""; try { userId = (String) session.getAttribute("USER_ID");
	 * } catch (Exception e) { e.printStackTrace(); } for (GoalMasterModel a :
	 * goalMasterModel) { a.getBandId(); a.setCreatedBy(userId);
	 * 
	 * } try {
	 * 
	 * resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addGoalMaster",
	 * goalMasterModel, JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * if (resp.getMessage() != "" && resp.getMessage() != null) {
	 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
	 * resp.setMessage("Success"); }
	 * 
	 * logger.info("Method : addGoalMaster ends");
	 * 
	 * logger.info("ADDDDDDD"+resp); return resp; }
	 */

	/*
	 *
	 * add
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-goal-master-add" })
	public @ResponseBody JsonResponse<Object> addGoalMaster(HttpSession session, @RequestBody GoalMasterModel data) {
		logger.info("Method : addGoalMaster starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		logger.info("data=======" + data);
		String dateFormat = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			/*
			 * if (data.getEffectiveDate() != null && data.getEffectiveDate() != "") {
			 * data.setEffectiveDate(DateFormatter.dateFormat(data.getEffectiveDate(),
			 * dateFormat)); }
			 */
			// data.setEffectiveDate(DateFormatter.inputDateFormat(data.getEffectiveDate(),
			// dateFormat));
		} catch (Exception e) {
			e.printStackTrace();
		}

		data.setCreatedBy(userId);
		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "rest-addGoalMaster", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method :addGoalMaster ends");
		logger.info("adddd=======" + res);
		return res;

	}

	
	// edit

	@SuppressWarnings("unchecked")

	@GetMapping("view-goal-edit")
	public @ResponseBody JsonResponse<GoalMasterModel> editGoalMaster(@RequestParam String Id, HttpSession session) {

		logger.info("Method : editGoalMaster starts");
		JsonResponse<GoalMasterModel> jsonResponse = new JsonResponse<GoalMasterModel>();
		logger.info("id====" + Id);

		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			jsonResponse = restTemplate.getForObject(env.getMasterUrl() + "rest-GoalMaster-edit?id=" + Id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		GoalMasterModel Model = mapper.convertValue(jsonResponse.getBody(), new TypeReference<GoalMasterModel>() {
		});
		
		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editGoalMaster ends");
		logger.info("edit=====" + jsonResponse);
		return jsonResponse;
	}

	
	// delete

	@SuppressWarnings("unchecked")

	@PostMapping("view-goal-delete")
	public @ResponseBody JsonResponse<Object> deleteGoalMaster(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteGoalMaster function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "rest-GoalMaster-delete?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteGoalMaster function Ends");

		logger.info("RESPPPPPPP" + res);
		return res;
	}
	 
	 
}
