package nirmalya.aathithya.webmodule.recruitment.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import nirmalya.aathithya.webmodule.common.utils.ConstantMailConfig;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EmailCalendarEvent;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.recruitment.model.AddRecruitentModel;
import nirmalya.aathithya.webmodule.recruitment.model.CandidateDetailsModel;
import nirmalya.aathithya.webmodule.recruitment.model.HireActionModel;

@Controller
@RequestMapping(value = "recruitment")
public class HireActionController {

	Logger logger = LoggerFactory.getLogger(HireActionController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;
    
	/* Function for view Requisition master view data */
	@SuppressWarnings("unchecked")
	@GetMapping("review-requi-mstr-view-data")
	public @ResponseBody List<AddRecruitentModel> viewRequisitionThroughAjax(Model model, HttpServletRequest request,
			HttpSession session) {
		logger.info("Method : viewRequisitionThroughAjax starts");

		JsonResponse<List<AddRecruitentModel>> jsonResponse = new JsonResponse<List<AddRecruitentModel>>();

		int count = 0;
		try {

			jsonResponse = restTemplate.getForObject(env.getRecruitment() + "viewRequistion", JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<AddRecruitentModel> addreq = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<AddRecruitentModel>>() {
					});
			for (AddRecruitentModel m : addreq) {
			 
				count++;
				m.setCount(count);
				if (m.getActivityStatus().equals("1")) {

					m.setActivityStatus("Created");
				} else if (m.getActivityStatus().equals("2")) {
					m.setActivityStatus("Active");
				} else if (m.getActivityStatus().equals("3")) {
					m.setActivityStatus("Closed");
				}

				String dateFormat = (String) session.getAttribute("DATEFORMAT");
				if (m.getJoinDate() != null && m.getJoinDate() != "") {
					m.setJoinDate(DateFormatter.dateFormat(m.getJoinDate(), dateFormat));
				}
			
				if (m.getCreatedOn() != null && m.getCreatedOn() != "") {
					m.setCreatedOn(DateFormatter.dateFormat(m.getCreatedOn(), dateFormat));
				}
				

			}

			jsonResponse.setBody(addreq);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method ; viewRequisitionThroughAjax ends");

		return jsonResponse.getBody();
	}

	/* Function for view candidate as per requisition */
	@SuppressWarnings("unchecked")
	@GetMapping("/view-candidate-as-per-requisition")
	public @ResponseBody List<CandidateDetailsModel> getCandidateFromRequisition(Model model, HttpSession session,
			@RequestParam String id) {

		logger.info("Method : getCandidateFromRequisition starts");

		JsonResponse<List<CandidateDetailsModel>> resp = new JsonResponse<List<CandidateDetailsModel>>();

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "getCandidateFromRequisition?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CandidateDetailsModel> actionReq = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CandidateDetailsModel>>() {
				});

		for (CandidateDetailsModel m : actionReq) {
			
			String dateFormat = (String) session.getAttribute("DATEFORMAT");
			if(m.getDob() != null && m.getDob() != "") {
				m.setDob(DateFormatter.dateFormat(m.getDob(), dateFormat));
			}
			if(m.getCreatedOn() != null && m.getCreatedOn() != "") {
				m.setCreatedOn(DateFormatter.dateFormat(m.getCreatedOn(), dateFormat));
			}
		
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {
			resp.setMessage("Success");
		}
		resp.setBody(actionReq);
		logger.info("Method : getCandidateFromRequisition ends");

		return resp.getBody();
	}

	/* Function for add schedule interview */
	@SuppressWarnings("unchecked")
	@PostMapping("/review-add-schedule-interview")
	public @ResponseBody JsonResponse<Object> scheduleInterview(Model model, HttpSession session,
			@RequestBody HireActionModel action, BindingResult result) {
		logger.info("Method : scheduleInterview starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String userId = "";
		String dateFormater = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormater = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {
			e.printStackTrace();
		}
		action.setCreatedBy(userId);
		if (action.getFromDate() != null && action.getFromDate() != "") {
			action.setFromDate(DateFormatter.inputDateFormat(action.getFromDate(), dateFormater));
		}
		if (action.getToDate() != null && action.getToDate() != "") {
			action.setToDate(DateFormatter.inputDateFormat(action.getToDate(), dateFormater));
		}
		try {
			res = restTemplate.postForObject(env.getRecruitment() + "scheduleInterview", action, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() == "" || res.getMessage() == null) {

			res.setMessage("Success");
			EmailCalendarEvent emailEvent = new EmailCalendarEvent();

			List<String> candMail = action.getCandEmail();
			List<String> interviewerMail = action.getInterviewerMail();

			candMail.addAll(interviewerMail);

			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			String fromDateTime = DateFormatter.inputDateFormat(action.getFromDate(), dateFormat) + " "
					+ action.getFromTime() + ":00";
			String toDateTime = DateFormatter.inputDateFormat(action.getToDate(), dateFormat) + " " + action.getToTime()
					+ ":00";

			try {
				emailEvent.send(action.getTitle(), username, password, candMail, host, port, action.getLocationName(),
						action.getSummary(), action.getDescription(), fromDateTime, toDateTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.info("Method : scheduleInterview ends");
		return res;
	}

	/* Function for schedule list */
	@SuppressWarnings("unchecked")
	@GetMapping("/review-get-schedule-list")
	public @ResponseBody List<HireActionModel> getScheduleDetails(Model model, HttpSession session,
			@RequestParam String id) {

		logger.info("Method : getScheduleDetails starts");

		JsonResponse<List<HireActionModel>> resp = new JsonResponse<List<HireActionModel>>();

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "getScheduleDetails?id=" + id, JsonResponse.class);
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

	/* Function for edit schedule interview */
	@SuppressWarnings("unchecked")
	@PostMapping("/review-edit-schedule-interview")
	public @ResponseBody JsonResponse<Object> editScheduleInterview(Model model, HttpSession session,
			@RequestBody HireActionModel action, BindingResult result) {
		logger.info("Method : editScheduleInterview starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String userId = "";
		String dateFormater = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormater = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {
			e.printStackTrace();
		}
		action.setCreatedBy(userId);
		if (action.getFromDate() != null && action.getFromDate() != "") {
			action.setFromDate(DateFormatter.inputDateFormat(action.getFromDate(), dateFormater));
		}
		if (action.getToDate() != null && action.getToDate() != "") {
			action.setToDate(DateFormatter.inputDateFormat(action.getToDate(), dateFormater));
		}

		try {
			res = restTemplate.postForObject(env.getRecruitment() + "editScheduleInterview", action,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("res.getBody()=======" + res.getBody());
		logger.info("res=====" + res);
		if (res.getMessage() == "" || res.getMessage() == null) {

			res.setMessage("Success");
			EmailCalendarEvent emailEvent = new EmailCalendarEvent();
			List<String> candMail = action.getCandEmail();
			List<String> interviewerMail = action.getInterviewerMail();

			candMail.addAll(interviewerMail);

			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			String fromDateTime = DateFormatter.inputDateFormat(action.getFromDate(), dateFormat) + " "
					+ action.getFromTime() + ":00";
			String toDateTime = DateFormatter.inputDateFormat(action.getToDate(), dateFormat) + " " + action.getToTime()
					+ ":00";

			try {
				emailEvent.send(action.getTitle(), username, password, candMail, host, port, action.getLocationName(),
						action.getSummary(), action.getDescription(), fromDateTime, toDateTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		logger.info("Method : editScheduleInterview ends");
		return res;

	}

	/* Function For shortList Candidate */
	@SuppressWarnings("unchecked")
	@GetMapping("/review-shortlist-candidate")
	public @ResponseBody JsonResponse<Object> shortListCandidate(Model model, HttpSession session,
			@RequestParam String id, @RequestParam String reqId, @RequestParam String status) {

		logger.info("Method : shortListCandidate starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(
					env.getRecruitment() + "shortListCandidate?id=" + id + "&reqId=" + reqId + "&status=" + status,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() == "" || resp.getMessage() == null) {
			resp.setMessage("Success");
		}
		logger.info("Method : shortListCandidate ends");
		return resp;
	}

	/* Function For shortList Candidate hire */
	@SuppressWarnings("unchecked")
	@PostMapping("/review-shortlist-candidate-hire")
	public @ResponseBody JsonResponse<Object> shortListCandidateHire(Model model, HttpSession session,
			@RequestParam String id, @RequestParam String reqId, @RequestParam String bandid,@RequestParam String finalCtc, @RequestParam String joiningdate,
			@RequestBody Map<String, Object> salaryData) {

		logger.info("Method : shortListCandidateHire starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		userId = (String) session.getAttribute("USER_ID");
		orgName = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		try {
			resp = restTemplate.postForObject(
					env.getRecruitment() + "shortListCandidateHire?id=" + id + "&reqId=" + reqId + "&bandid=" + bandid
							+ "&finalCtc=" + finalCtc + "&createdBy=" + userId + "&joiningdate=" + joiningdate +"&orgName=" + orgName +"&orgDivision="+orgDivision,
							salaryData,JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() == "" || resp.getMessage() == null) {
			resp.setMessage("Success");
		}
		logger.info("Method : shortListCandidateHire ends");
		return resp;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("/review-interviewerName-forFeedback") public @ResponseBody
	 * JsonResponse<HireActionModel> getInterviewerName(Model model, HttpSession
	 * session, @RequestParam String candId, @RequestParam String reqId){
	 * 
	 * logger.info("Method : getInterviewerName starts");
	 * 
	 * JsonResponse<HireActionModel> resp = new JsonResponse<HireActionModel>();
	 * 
	 * try { resp = restTemplate.getForObject(env.getRecruitment() +
	 * "getInterviewerName?candId=" + candId + "&reqId=" + reqId,
	 * JsonResponse.class); } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * if (resp.getMessage() == "" || resp.getMessage() == null) {
	 * 
	 * resp.setMessage("Success"); }
	 * 
	 * logger.info("Method : getInterviewerName ends");
	 * 
	 * return resp; }
	 */

	/*
	 * Function for Add FeedBack Details
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("review-add-feedback")
	public @ResponseBody JsonResponse<Object> addFeedback(HttpSession session,
			@RequestBody List<HireActionModel> feedBackModel) {
		logger.info("Method : addAssignDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {

		}
		for (HireActionModel m : feedBackModel) {
			m.setCreatedBy(userId);
		}
		try {
			resp = restTemplate.postForObject(env.getRecruitment() + "rest-addFeedback", feedBackModel,
					JsonResponse.class);
		} catch (Exception e) {

			e.printStackTrace();
		}
 
		logger.info("Method : addFeedback ends");

		return resp;
	}

	// Function for View FeedBack Details
	@SuppressWarnings("unchecked")
	@GetMapping("/review-get-feedback-list")
	public @ResponseBody List<HireActionModel> getFeedback(Model model, HttpSession session, @RequestParam String id) {

		logger.info("Method : getFeedback starts");

		JsonResponse<List<HireActionModel>> resp = new JsonResponse<List<HireActionModel>>();

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "viewFeedbackDetails?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {

			resp.setMessage("Success");
		}
		logger.info("Method : getFeedback ends");
		return resp.getBody();
	}

	/*
	 * Function for Edit FeedBack Details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "review-edit-feedbackDetails" })
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

	/*****************************
	 * Joining Status Update
	 **************************************/
	/* Function For Joining Status Update */
	@SuppressWarnings("unchecked")
	@GetMapping("/review-shortlist-candidate-joinStatusUpdate")
	public @ResponseBody JsonResponse<Object> joinStatusUpdate(Model model, HttpSession session,
			@RequestParam String candId, @RequestParam String reqId, @RequestParam String status) {

		logger.info("Method : joinStatusUpdate starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-joinStatusUpdate?candId=" + candId + "&reqId="
					+ reqId + "&status=" + status, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() == "" || resp.getMessage() == null) {
			resp.setMessage("Success");
		}
		logger.info("Method : joinStatusUpdate ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("/view-candidate-get-jobtittle")
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
}
