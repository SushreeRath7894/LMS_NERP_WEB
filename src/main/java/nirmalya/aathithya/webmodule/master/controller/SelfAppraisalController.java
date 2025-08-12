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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.SelfAppraisalModel;

@Controller
@RequestMapping(value = "master")
public class SelfAppraisalController {

	Logger logger = LoggerFactory.getLogger(SelfAppraisalController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping("/view-self-appraisal")
	public String selfAppraisal(Model model, HttpSession session) {
		logger.info("Method : selfAppraisal starts");
		
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
		try {
			DropDownModel[] name = restTemplate.getForObject(env.getMasterUrl() + "getNameList?id=" + userId,
					DropDownModel[].class);
			List<DropDownModel> nameList = Arrays.asList(name);
			model.addAttribute("nameList", nameList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : selfAppraisal ends");
		return "master/selfAppraisal";
	}
	
	/*
	 * dropdown for job through ajax
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-self-appraisal-job-ajax" })
	public @ResponseBody JsonResponse<Object> getDesignationList(@RequestParam String name) {
		logger.info("Method : getDesignationList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-designation-list?id=" + name,
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
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-self-appraisal-result")
	public @ResponseBody List<SelfAppraisalModel> viewExploreData(HttpSession session,String name) {
		logger.info("Method : viewExploreData starts");
	
		JsonResponse<List<SelfAppraisalModel>> resp = new JsonResponse<List<SelfAppraisalModel>>();
		List<SelfAppraisalModel> returnList = new ArrayList<SelfAppraisalModel>();
	
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewSelfAppraisalData?name="+name, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewExploreData ends");
		return returnList;
	}

	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-self-appraisal-save")
	public @ResponseBody JsonResponse<Object> viewSelfAppraisalSave(@RequestBody SelfAppraisalModel review, HttpSession session) {
		logger.info("Method : viewSelfAppraisalSave starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-save-selfReview", review, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : viewSelfAppraisalSave starts");
		return resp;
	}
	
	

	// edit

	@SuppressWarnings("unchecked")

	@GetMapping("view-self-appraisal-edit")
	public @ResponseBody JsonResponse<SelfAppraisalModel> editSelfAppraisal(@RequestParam String Id, HttpSession session) {

		logger.info("Method : editSelfAppraisal starts");
		JsonResponse<SelfAppraisalModel> jsonResponse = new JsonResponse<SelfAppraisalModel>();
		logger.info("id====" + Id);

		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			jsonResponse = restTemplate.getForObject(env.getMasterUrl() + "rest-editSelfAppraisal-edit?id=" + Id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		SelfAppraisalModel Model = mapper.convertValue(jsonResponse.getBody(), new TypeReference<SelfAppraisalModel>() {
		});
		
		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editSelfAppraisal ends");
		logger.info("edit=====" + jsonResponse);
		return jsonResponse;
	}

	
	
}
