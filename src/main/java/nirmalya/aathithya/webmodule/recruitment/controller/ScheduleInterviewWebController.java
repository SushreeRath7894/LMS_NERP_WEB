package nirmalya.aathithya.webmodule.recruitment.controller;

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

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.recruitment.model.AddRecruitentModel;
import nirmalya.aathithya.webmodule.recruitment.model.HireActionModel;

@Controller
@RequestMapping(value = "recruitment")
public class ScheduleInterviewWebController {
	Logger logger = LoggerFactory.getLogger(ScheduleInterviewWebController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	CommonUtil commonUtil;
	
	@GetMapping(value = { "schedule-interview" })
	public String scheduleInterview(Model model, HttpSession session) {
		logger.info("Method : scheduleInterview starts");
		
		String restUrl = env.getRecruitment() + "ratingCategoryList";
		commonUtil.getDropshownList("ratingCatList", restUrl, model);
		
		String userId = "";
		String userName = "";
		String userJobId = "JOB002"; 
		String userJob = "SDEII";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userJobId", userJobId);
		model.addAttribute("userJob", userJob);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);

		logger.info("Method : scheduleInterview ends");
		return "recruitment/scheduleInterview";
	}
	//
	@SuppressWarnings("unchecked")
	@GetMapping("schedule-interview-get-schedule-list")
	public @ResponseBody List<HireActionModel> getScheduleDetails(Model model, HttpSession session) {

		logger.info("Method : getScheduleDetails starts");

		JsonResponse<List<HireActionModel>> resp = new JsonResponse<List<HireActionModel>>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "getScheduleInterview?orgName=" + orgName + "&orgDivision=" + orgDivision + "&userId=" + userId,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<HireActionModel> cand = mapper.convertValue(resp.getBody(), new TypeReference<List<HireActionModel>>() {
		});
		logger.info("cand======" + cand);
		for (HireActionModel m : cand) {

			String dateFormat = (String) session.getAttribute("DATEFORMAT");
			if (m.getFromDate() != null && m.getFromDate() != "") {
				m.setFromDate(DateFormatter.dateFormat(m.getFromDate(), dateFormat));
			}
			if (m.getToDate() != null && m.getToDate() != "") {
				m.setToDate(DateFormatter.dateFormat(m.getToDate(), dateFormat));
			}
		}
		resp.setBody(cand);

		if (resp.getMessage() == "" || resp.getMessage() == null) {

			resp.setMessage("Success");
		}
		logger.info("Method : getScheduleDetails ends");
		return resp.getBody();
	}
	//
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "schedule-interview-getRatingType" })
	public @ResponseBody JsonResponse<List<DropDownModel>> getRatingType(Model model,
			@RequestParam("category") String category, HttpSession session) {
		logger.info("Method : getRatingType starts");
		JsonResponse<List<DropDownModel>> res = new JsonResponse<List<DropDownModel>>();

		try {
			res = restTemplate.getForObject(env.getRecruitment() + "rest-getRatingType?category=" + category,
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
		logger.info("Method : getRatingType ends");
		return res;
	}
	//
	@SuppressWarnings("unchecked")
	@PostMapping("schedule-interview-add-feedback")
	public @ResponseBody JsonResponse<Object> addFeedback(HttpSession session,
			@RequestBody List<HireActionModel> feedBackModel) {
		logger.info("Method : addAssignDetails starts");

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
		for (HireActionModel m : feedBackModel) {
			m.setCreatedBy(userId);
			m.setOrgName(orgName);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restTemplate.postForObject(env.getRecruitment() + "rest-addFeedback", feedBackModel,
					JsonResponse.class);
		} catch (Exception e) {

			e.printStackTrace();
		}

		logger.info("Method : addFeedback ends"+resp);

		return resp;
	}
	//
	@SuppressWarnings("unchecked")
	@GetMapping("schedule-interview-get-feedback-list")
	public @ResponseBody List<HireActionModel> getFeedback(Model model, HttpSession session,@RequestParam String id,@RequestParam String candId) {

		logger.info("Method : getFeedback starts");

		JsonResponse<List<HireActionModel>> resp = new JsonResponse<List<HireActionModel>>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "getFeedbackList?id=" + id+ "&userId=" + userId + "&candId=" + candId,
		JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {

			resp.setMessage("Success");
		}
		logger.info("Method : getFeedback ends");
		return resp.getBody();
	}
	//
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "schedule-interview-edit-feedbackDetails" })
	public @ResponseBody List<HireActionModel> editFeedbackDetails(@RequestParam String feedId,
			@RequestParam String candId, HttpSession session) {
		logger.info("Method : editFeedbackDetails starts");
		JsonResponse<List<HireActionModel>> resp = new JsonResponse<List<HireActionModel>>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getRecruitment() + "rest-editScheduleFeedbackDetails?feedId=" + feedId + "&candId=" + candId+ "&userId=" + userId,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<HireActionModel> feedList = mapper.convertValue(resp.getBody(),
				new TypeReference<List<HireActionModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
		}
		int i = 0;
		for (HireActionModel a : feedList) {
			i = i + 1;
			a.setSlNo(i);
		}
		resp.setBody(feedList);
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : editFeedbackDetails ends");
		return feedList;
	}
	//
	
	@SuppressWarnings("unchecked")
	@GetMapping("schedule-interview-get-jobtittle")
	public @ResponseBody JsonResponse<DropDownModel> getJobTittleDetails(Model model, HttpSession session, @RequestParam String id) {

		logger.info("Method : getJobTittleDetails starts");
		JsonResponse<DropDownModel> jsonResponse = new JsonResponse<DropDownModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getRecruitment() + "rest-getjobtittle?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : getJobTittleDetails ends");
		logger.info("getJobTittleDetails=====" + jsonResponse);
		return jsonResponse;
	}
	//
	@SuppressWarnings("unchecked")
	@GetMapping("schedule-interview-submit")
	public @ResponseBody JsonResponse<AddRecruitentModel> submitApply(HttpSession session,
			@RequestParam String submitId) {

		logger.info("Method : submitApply starts");
		JsonResponse<AddRecruitentModel> response = new JsonResponse<AddRecruitentModel>();
		try {
			response = restTemplate.getForObject(env.getRecruitment() + "submitFeedbackApply?submitId=" + submitId, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		logger.info("response=====" + response);
		logger.info("Method : submitApply ends"+response);
		return response;
	}
	
	/*
	 * Get candidate details
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/schedule-interview-get-candidate")
	public @ResponseBody JsonResponse<Object> getAllCandidateDetails(@RequestParam String candId,String requiId,Model model, HttpSession session) {

		logger.info("Method : getAllCandidateDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		String userId = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-interview-candidate-details?userId=" + userId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision+ "&candidateId=" + candId + "&requisitionId=" + requiId, JsonResponse.class);
		
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getAllCandidateDetails ends");

		return resp;
	}
}
