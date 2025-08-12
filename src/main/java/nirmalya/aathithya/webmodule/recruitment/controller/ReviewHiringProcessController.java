package nirmalya.aathithya.webmodule.recruitment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import nirmalya.aathithya.webmodule.common.utils.ConstantMailConfig;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EmailCalendarEvent;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.recruitment.model.ActionEmployeeDetailsModel;
import nirmalya.aathithya.webmodule.recruitment.model.OfferletterModel;
import nirmalya.aathithya.webmodule.recruitment.model.ReviewHiringProcessModel;

@Controller
@RequestMapping(value = "recruitment")
public class ReviewHiringProcessController {

	Logger logger = LoggerFactory.getLogger(ReviewHiringProcessController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;
    
	@GetMapping("/review-hiring")
	public String reviewHiringProcess(Model model, HttpSession session) {

		logger.info("Method : reviewHiringProcess starts");
		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		try {
			DropDownModel[] ratingCat = restTemplate.getForObject(env.getRecruitment() + "ratingCategoryList",
					DropDownModel[].class);
			List<DropDownModel> ratingCatList = Arrays.asList(ratingCat);
			model.addAttribute("ratingCatList", ratingCatList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			ActionEmployeeDetailsModel[] manager = restTemplate.getForObject(
					env.getRecruitment() + "interviewr-listing?org=" + organization + "&orgDiv=" + orgDivision,
					ActionEmployeeDetailsModel[].class);

			List<ActionEmployeeDetailsModel> managerList = Arrays.asList(manager);
			model.addAttribute("interviewrList", managerList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] location = restTemplate.getForObject(
					env.getRecruitment() + "jobLocationList?orgName=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> jobLocationList = Arrays.asList(location);
			model.addAttribute("jobLocationList", jobLocationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		model.addAttribute("userId", userId);

		logger.info("Method : reviewHiringProcess ends");

		return "recruitment/review-hiring-process";
	}

	/*
	 * View Resignation Details Draft
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("review-hiring-requisition-list")
	public @ResponseBody JsonResponse<Object> viewReuisitions(HttpSession session) {

		logger.info("Method : viewReuisitions starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {

		}

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-view-requisitions?userId=" + userId
					+ "&orgName=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewReuisitions ends");
		return resp;
	}

	/* Function for view candidate with requisition id */
	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-candidate-list")
	public @ResponseBody JsonResponse<Object> getCandidateListingWithRequisitions(@RequestParam String exp, Model model,
			HttpSession session) {

		logger.info("Method : getCandidateListingWithRequisitions starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "get-candidate-listing?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&exp=" + exp, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getCandidateListingWithRequisitions ends");

		return resp;
	}

	// Shortlist Candidates by Requisition id

	@SuppressWarnings("unchecked")
	@PostMapping("/review-hiring-candidate-shortlist")
	public @ResponseBody JsonResponse<Object> shortlistCandidates(@RequestBody ReviewHiringProcessModel data,
			HttpSession session) {
		logger.info("Method : shortlistCandidates starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCreatedBy(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getRecruitment() + "rest-shortlist-candidate", data,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : shortlistCandidates starts");
		return resp;
	}

	/*
	 * Get all shortlisted candidates
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-candidate-shortlistd")
	public @ResponseBody JsonResponse<Object> getAllShortlistedCandidates(Model model, HttpSession session) {

		logger.info("Method : getAllShortlistedCandidates starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "get-all-shortlisted-candidates?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getAllShortlistedCandidates ends");

		return resp;
	}

	/* Function for add schedule interview */
	@SuppressWarnings("unchecked")
	@PostMapping("/review-hiring-schedule-interview")
	public @ResponseBody JsonResponse<Object> scheduleInterview(Model model, HttpSession session,
			@RequestBody ReviewHiringProcessModel action, BindingResult result) throws MessagingException {
		logger.info("Method : scheduleInterview starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";
		String userName = "";
		String userEmail = "";
		String userPhone = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userName = (String) session.getAttribute("USER_NAME");
			userEmail = (String) session.getAttribute("USER_EMAIL");
			userPhone = (String) session.getAttribute("USER_MOBILE");

		} catch (Exception e) {
			e.printStackTrace();
		}
		action.setCreatedBy(userId);
		action.setOrganization(orgName);
		action.setOrgDivision(orgDivision);

		try {
			res = restTemplate.postForObject(env.getRecruitment() + "schedule-interview", action, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		res.setCode("success");
		if (res.getCode().equals("success") && (!action.getEmail().equals(""))) {

			EmailCalendarEvent emailEvent = new EmailCalendarEvent();

			List<String> candMail = new ArrayList<>();

			String canMail[] = action.getEmail().split(",");

			for (String a : canMail) {
				candMail.add(a);
			}

			List<String> interviewerMail = new ArrayList<>();
			List<String> bccAddress = new ArrayList<>();

			String inter[] = action.getInterviewerEmail().split(",");

			for (String a : inter) {
				interviewerMail.add(a);
			}

			String subject = "We're looking forward to your interview on " + action.getFromDate() + "!.";
			String attachedUrl = action.getMeetingURL();

			/*
			 * String message = "Hi " + action.getCandidateName() + ",\n\n" +
			 * "Your interview has been scheduled for " + action.getFromDate() + " at " +
			 * action.getFromTime() + " India Standard Time (Kolkata).\n\n" +
			 * "You can join the meeting using the following link: " +
			 * action.getMeetingURL() + "\n\n" + "Best regards,\n" +
			 * action.getOrganization();
			 */

			String message = "<html>" + "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>"
					+ "<div style='margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
					+ "<div style='text-align: center;'>"
					+ "<img src='https://employee.nerp.in/document/document/1711914334491.png' alt='Nirmalya Labs' style='max-width: 200px; margin-bottom: 20px;'>"
					+ "</div>" + "<p>Dear <strong>" + action.getCandidateName() + "</strong>,</p>"
					+ "<p>We are pleased to inform you that your interview for the <strong>" + action.getTitle()
					+ "</strong> position at <strong>" + action.getOrganization() + "</strong> has been scheduled.</p>"
					+ "<h3 style='color: #0056b3;'>Interview Details:</h3>" + "<ul>" + "<li><strong>Date:</strong> "
					+ action.getFromDate() + "</li>" + "<li><strong>Time:</strong> " + action.getFromTime()
					+ " (India Standard Time/Kolkata)</li>" + "<li><strong>Mode:</strong> " + action.getModeOfInt()
					+ "</li>" + "<li><strong>Duration:</strong> " + action.getTotalDuration() + "</li>" + "</ul>"
					+ "<p>You can join the meeting using the following link:</p>" + "<p><a href='"
					+ action.getMeetingURL()
					+ "' style='color: #007bff; text-decoration: none; font-weight: bold;'>Join Interview</a></p>"
					+ "<h3 style='color: #0056b3;'>Important Instructions:</h3>" + "<ul>"
					+ "<li>Ensure a stable internet connection and a quiet environment.</li>"
					+ "<li>Join the meeting at least 5 minutes before the scheduled time.</li>"
					+ "<li>Keep your resume and any required documents handy.</li>" + "</ul>"
					+ "<p>If you have any questions or need to reschedule, please reply to this email or contact <strong>"
					+ userEmail + "</strong>.</p>" + "<p>Looking forward to speaking with you!</p>"
					+ "<p>Best regards,</p>" + "<p><strong>" + userName + "</strong><br>" + action.getOrganization()
					+ "<br>" + userEmail + " | " + userPhone + "<br>" + "<a href='" + "www.nerp.in"
					+ "' style='color: #007bff; text-decoration: none;'>Visit Our Website</a></p>" + "</div>"
					+ "</body>" + "</html>";

			try {
				EmailAttachmentSender.sendEmailWithAttachments(host, port, username, password, candMail,
						interviewerMail, subject, message, null);

			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		logger.info("Method : scheduleInterview ends");
		return res;
	}

	/*
	 * Get all scheduled interviews
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-schedule-interviews")
	public @ResponseBody JsonResponse<Object> getAllSceduledInterviews(Model model, HttpSession session) {

		logger.info("Method : getAllSceduledInterviews starts");

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
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-all-scheduled-interviews?userId=" + userId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getAllSceduledInterviews ends");

		return resp;
	}

	/*
	 * Function for Add FeedBack Details
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("review-hiring-save-feedback")
	public @ResponseBody JsonResponse<Object> addFeedback(HttpSession session,
			@RequestBody ReviewHiringProcessModel feedBackModel) {
		logger.info("Method : addFeedback starts");

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

		feedBackModel.setCreatedBy(userId);
		feedBackModel.setOrganization(orgName);
		feedBackModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getRecruitment() + "rest-add-feedback", feedBackModel,
					JsonResponse.class);
		} catch (Exception e) {

			e.printStackTrace();
		}

		logger.info("Method : addFeedback ends");

		return resp;
	}

	/*
	 * Get all scheduled interviews
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-feedback-details")
	public @ResponseBody JsonResponse<Object> getFeedBackByInteriview(Model model, HttpSession session,
			@RequestParam String candId, String requiId, String type) {

		logger.info("Method : getFeedBackByInteriview starts");

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
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-feedbacks?userId=" + userId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&candId=" + candId + "&requiId=" + requiId + "&type="
					+ type, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getFeedBackByInteriview ends");

		return resp;
	}

	/*
	 * Get all Selected Candidates
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-selected-candidates")
	public @ResponseBody JsonResponse<Object> getAllSelectedCandidate(Model model, HttpSession session,
			@RequestParam String reqId) {

		logger.info("Method : getAllSelectedCandidate starts");

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
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-selected-candidates?userId=" + userId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&reqId=" + reqId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getAllSelectedCandidate ends");

		return resp;
	}

	/*
	 * Get all Hold Candidates
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-hold-candidates")
	public @ResponseBody JsonResponse<Object> getAllHoldCandidate(Model model, HttpSession session,
			@RequestParam String reqId) {

		logger.info("Method : getAllHoldCandidate starts");

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
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-hold-candidates?userId=" + userId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&reqId=" + reqId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getAllHoldCandidate ends");

		return resp;
	}

	/*
	 * Get all Rejcted Candidates
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-rejected-candidates")
	public @ResponseBody JsonResponse<Object> getAllRejectedCandidate(Model model, HttpSession session,
			@RequestParam String reqId) {

		logger.info("Method : getAllRejectedCandidate starts");

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
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-rejected-candidates?userId=" + userId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&reqId=" + reqId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getAllRejectedCandidate ends");

		return resp;
	}

	/*
	 * Get all Accepted Candidates
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-accepted-candidates")
	public @ResponseBody JsonResponse<Object> getAllAcceptedCandidate(Model model, HttpSession session,
			@RequestParam String reqId) {

		logger.info("Method : getAllAcceptedCandidate starts");

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
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-accepted-candidates?userId=" + userId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&reqId=" + reqId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getAllAcceptedCandidate ends");

		return resp;
	}

	/*
	 * Get all Declined Candidates
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-decline-candidates")
	public @ResponseBody JsonResponse<Object> getAllDeclinedCandidate(Model model, HttpSession session,
			@RequestParam String reqId) {

		logger.info("Method : getAllDeclinedCandidate starts");

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
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-declined-candidates?userId=" + userId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&reqId=" + reqId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getAllDeclinedCandidate ends");

		return resp;
	}

	/* Function for add schedule interview */
	@SuppressWarnings("unchecked")
	@PostMapping("/review-hiring-select-candidate")
	public @ResponseBody JsonResponse<Object> selectCandidate(Model model, HttpSession session,
			@RequestBody ReviewHiringProcessModel action, BindingResult result) {
		logger.info("Method : selectCandidate starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		action.setCreatedBy(userId);
		action.setOrganization(orgName);
		action.setOrgDivision(orgDivision);
		try {
			res = restTemplate.postForObject(env.getRecruitment() + "select-candidate", action, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : selectCandidate ends");
		return res;
	}

	/*
	 * Get all Declined Candidates
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/review-candidate-offer-acceptance")
	public @ResponseBody JsonResponse<Object> candidateOfferAcceptance(@RequestParam String candId, String reqId,
			String status, Model model, HttpSession session) {

		logger.info("Method : candidateOfferAcceptance starts");

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
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-candidate-offer-acceptance?userId=" + userId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&candId=" + candId + "&requiId=" + reqId
					+ "&status=" + status, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : candidateOfferAcceptance ends");

		return resp;
	}

	/*
	 * Get all Declined Candidates
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-update-rounds")
	public @ResponseBody JsonResponse<Object> updateRounds(@RequestParam String candId, String reqId, String status,
			String roundId, Model model, HttpSession session) {

		logger.info("Method : updateRounds starts");

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
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-update-rounds?userId=" + userId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&candId=" + candId + "&reqId=" + reqId + "&status="
					+ status + "&roundId=" + roundId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : updateRounds ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-get-employee-list")
	public @ResponseBody Object getEmployeeList(HttpSession session) {

		logger.info("Method :getEmployeeList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(
					env.getRecruitment() + "rest-get-employee-list?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :getEmployeeList ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("review-hiring-save-interview-rounds")
	public @ResponseBody JsonResponse<Object> saveRequisitionRounds(HttpSession session,
			@RequestBody Map<String, Object> roundData) {
		logger.info("Method : saveRequisitionRounds starts");

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
			resp = restTemplate.postForObject(env.getRecruitment() + "rest-save-job-rounds?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, roundData, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveRequisitionRounds ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("review-hiring-update-round-order")
	public @ResponseBody JsonResponse<Object> updateRequisitionRoundsOrder(HttpSession session,
			@RequestBody Map<String, Object> roundData) {
		logger.info("Method : updateRequisitionRoundsOrder starts");

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
			resp = restTemplate.postForObject(env.getRecruitment() + "rest-update-job-rounds-orders?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, roundData, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : updateRequisitionRoundsOrder ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-get-rounds-list")
	public @ResponseBody Object getRoundsList(@RequestParam String requisitionId, @RequestParam String candId,
			HttpSession session) {

		logger.info("Method :getRoundsList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "rest-get-rounds-list?requisitionId=" + requisitionId + "&candId="
									+ candId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :getRoundsList ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-delete-rounds")
	public @ResponseBody Object deleteRoundDetails(@RequestParam String roundId, HttpSession session) {

		logger.info("Method :deleteRoundDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getRecruitment() + "rest-delete-rounds?roundId=" + roundId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :deleteRoundDetails ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-get-rounds-deails")
	public @ResponseBody Object getInterviewRoundsDetails(@RequestParam String roundId,
			@RequestParam String requisitionId, @RequestParam String candidateId, HttpSession session) {

		logger.info("Method :getInterviewRoundsDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-rounds-details?roundId=" + roundId
					+ "&requisitionId=" + requisitionId + "&candidateId=" + candidateId + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :getInterviewRoundsDetails ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("review-hiring-get-candidate-evaluation")
	public @ResponseBody JsonResponse<Object> getCandidateListingForEvaluation(Model model, HttpSession session) {
		logger.info("Method : getCandidateListingForEvaluation starts");

		JsonResponse<Object> resp = new JsonResponse<>();
		String orgName = "", orgDivision = "", userId = "";
		String baseUrl = env.getBaseURL() + "document/employee/";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-candidate-evaluation?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);

			if (resp.getBody() != null && resp.getBody() instanceof List) {
				List<?> bodyList = (List<?>) resp.getBody();
				if (!bodyList.isEmpty() && bodyList.get(0) instanceof String) {
					ObjectMapper objectMapper = new ObjectMapper();

					List<Map<String, Object>> candidateList = objectMapper.readValue(bodyList.get(0).toString(),
							new TypeReference<List<Map<String, Object>>>() {
							});

					for (Map<String, Object> candidate : candidateList) {
						if (candidate.containsKey("candidateImage") && candidate.get("candidateImage") != null) {
							candidate.put("candidateImage", baseUrl + candidate.get("candidateImage").toString());
						}
					}

					/* resp.setBody(List.of(objectMapper.writeValueAsString(candidateList))); */
					resp.setBody(objectMapper.writeValueAsString(candidateList));
				}
			}
		} catch (RestClientException | IOException e) {
			e.printStackTrace();
		}

		logger.info("Method : getCandidateListingForEvaluation ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("review-hiring-get-candidate-evaluation-using-id")
	public @ResponseBody JsonResponse<Object> getCandidateListingForEvaluationById(Model model, HttpSession session,
			@RequestParam String candidateId, @RequestParam String requisitionId) {

		logger.info("Method : getCandidateListingForEvaluationById starts");

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
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-candidate-evaluation-by-id?orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&userId=" + userId + "&candidateId=" + candidateId
					+ "&requisitionId=" + requisitionId, JsonResponse.class);

			System.out.println(resp);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getCandidateListingForEvaluationById ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("review-hiring-save-interviwer-resp")
	public @ResponseBody JsonResponse<Object> saveInterviwerResponse(Model model, HttpSession session,
			@RequestParam String type, String roundId, String reqId, String candidateId) {

		logger.info("Method : saveInterviwerResponse starts");

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
			resp = restTemplate.getForObject(
					env.getRecruitment() + "rest-save-interviewer-response?userId=" + userId + "&type=" + type
							+ "&roundId=" + roundId + "&reqId=" + reqId + "&candidateId=" + candidateId,
					JsonResponse.class);

			System.out.println(resp);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveInterviwerResponse ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("review-hiring-requisition-candidate-selection-list")
	public @ResponseBody JsonResponse<Object> getReuisitionListForCandidateSelection(HttpSession session) {

		logger.info("Method : getReuisitionListForCandidateSelection starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {

		}

		try {
			resp = restTemplate
					.getForObject(env.getRecruitment() + "rest-get-requisitions-for-candidates-selection?userId="
							+ userId + "&orgName=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getReuisitionListForCandidateSelection ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("review-hiring-requisition-candidate-list")
	public @ResponseBody JsonResponse<Object> getReuisitionCandidateList(HttpSession session,
			@RequestParam String reqId) {

		logger.info("Method : getReuisitionAllCandidateList starts");
		JsonResponse<Object> resp = new JsonResponse<>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Session attribute error: " + e.getMessage());
		}

		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "rest-get-requisitions-candidates?userId=" + userId + "&orgName="
									+ organization + "&orgDivision=" + orgDivision + "&reqId=" + reqId,
							JsonResponse.class);

			if (resp != null && resp.getBody() != null) {
				String baseUrl = env.getBaseURL() + "document/employee/";
				ObjectMapper objectMapper = new ObjectMapper();

				try {
					List<Map<String, Object>> responseList = objectMapper.readValue(resp.getBody().toString(),
							List.class);

					if (!responseList.isEmpty()) {
						Map<String, Object> responseMap = responseList.get(0);

						List<Map<String, Object>> candidateData = (List<Map<String, Object>>) responseMap
								.get("candidateData");
						if (candidateData != null) {
							for (Map<String, Object> candidate : candidateData) {
								String imagePath = (String) candidate.get("candidateImage");
								if (imagePath != null && !imagePath.isEmpty()) {
									String fileName = Paths.get(imagePath).getFileName().toString();
									candidate.put("candidateImage", baseUrl + fileName);
								}
							}
						}

						resp.setBody(objectMapper.writeValueAsString(responseList));
					}

				} catch (IOException e) {
					logger.error("Error processing JSON: " + e.getMessage());
				}
			}

		} catch (RestClientException e) {
			logger.error("Error fetching candidate data: " + e.getMessage());
		}

		logger.info("Method : getReuisitionAllCandidateList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("review-hiring-forwarded-candidate-list")
	public @ResponseBody JsonResponse<Object> getForwardedCandidateList(HttpSession session,
			@RequestParam String reqId) {

		logger.info("Method : getForwardedCandidateList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			logger.error("Session attribute error: " + e.getMessage());
		}

		try {
			resp = restTemplate
					.getForObject(
							env.getRecruitment() + "rest-get-forwarded-candidates?userId=" + userId + "&orgName="
									+ organization + "&orgDivision=" + orgDivision + "&reqId=" + reqId,
							JsonResponse.class);

			if (resp != null && resp.getBody() != null) {
				ObjectMapper objectMapper = new ObjectMapper();
				String baseUrl = env.getBaseURL() + "document/employee/";

				try {
					List<String> bodyList = (List<String>) resp.getBody();
					if (!bodyList.isEmpty()) {
						Map<String, Object> responseMap = objectMapper.readValue(bodyList.get(0), Map.class);

						// Process hiredCandidates
						List<Map<String, Object>> hiredCandidates = (List<Map<String, Object>>) responseMap
								.get("hiredCandidates");
						if (hiredCandidates != null) {
							for (Map<String, Object> candidate : hiredCandidates) {
								String imagePath = (String) candidate.get("candidateImage");
								if (imagePath != null && !imagePath.isEmpty()) {
									candidate.put("candidateImage", baseUrl + imagePath);
								}
							}
						}

						// Process rejectedCandidates
						List<Map<String, Object>> rejectedCandidates = (List<Map<String, Object>>) responseMap
								.get("rejectedCandidates");
						if (rejectedCandidates != null) {
							for (Map<String, Object> candidate : rejectedCandidates) {
								String imagePath = (String) candidate.get("candidateImage");
								if (imagePath != null && !imagePath.isEmpty()) {
									candidate.put("candidateImage", baseUrl + imagePath);
								}
							}
						}

						List<String> jsonList = new ArrayList<>();
						jsonList.add(objectMapper.writeValueAsString(responseMap));
						resp.setBody(jsonList);
					}

				} catch (IOException e) {
					logger.error("Error processing JSON: " + e.getMessage());
				}
			}
		} catch (RestClientException e) {
			logger.error("Error fetching forwarded candidate data: " + e.getMessage());
		}

		logger.info("Method : getForwardedCandidateList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("review-hiring-shortlist-candidate")
	public @ResponseBody JsonResponse<Object> shortlistCandidate(HttpSession session,
			@RequestBody Map<String, Object> data, @RequestParam String reqId) {
		logger.info("Method : shortlistCandidate starts");

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
			resp = restTemplate.postForObject(env.getRecruitment() + "rest-shortlist-final-candidate?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision + "&reqId=" + reqId, data, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : shortlistCandidate ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("review-hiring-shortlist-final-candidate")
	public @ResponseBody JsonResponse<Object> shortlistFinalCandidate(HttpSession session,
			@RequestBody Map<String, Object> data) {
		logger.info("Method : shortlistFinalCandidate starts");

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
			resp = restTemplate
					.postForObject(env.getRecruitment() + "rest-shortlist-final-candidate-by-moderator?userId=" + userId
							+ "&org=" + orgName + "&orgDiv=" + orgDivision, data, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : shortlistFinalCandidate ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("review-hiring-shortlist-hold-candidate")
	public @ResponseBody JsonResponse<Object> shortlistHoldCandidate(HttpSession session,
			@RequestBody Map<String, Object> data) {
		logger.info("Method : shortlistHoldCandidate starts");

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
			resp = restTemplate
					.postForObject(env.getRecruitment() + "rest-shortlist-hold-candidate?userId=" + userId
							+ "&org=" + orgName + "&orgDiv=" + orgDivision, data, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : shortlistHoldCandidate ends");
		return resp;
	}
	
	

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("review-shortlist-candidate-offer-release") public @ResponseBody
	 * JsonResponse<Object> releaseCandidateOffer(HttpSession session,
	 * 
	 * @RequestBody Map<String, Object> data) {
	 * logger.info("Method : releaseCandidateOffer starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>(); String userId = "";
	 * String orgName = ""; String orgDivision = "";
	 * 
	 * try { userId = (String) session.getAttribute("USER_ID"); orgName = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * logger.error("Error fetching session attributes: " + e.getMessage()); }
	 * 
	 * try { // Extracting individual values from request data String candidateId =
	 * (String) data.get("candidateId"); String requisitionId = (String)
	 * data.get("requisitionId"); String candidateName = (String)
	 * data.get("candidateName"); String candidatePhone = (String)
	 * data.get("candidatePhone"); String candidateEmail = (String)
	 * data.get("candidateEmail"); String finalCtc = (String) data.get("finalCtc");
	 * String jobTitle = (String) data.get("jobTitle"); String joinDate = (String)
	 * data.get("joinDate"); String designation = (String) data.get("designation");
	 * String department = (String) data.get("department"); String workHour =
	 * (String) data.get("workHour"); String bandId = (String) data.get("bandId");
	 * String offerLetterId = (String) data.get("offerLetterId");
	 * 
	 * // Convert candidateEmail into a List<String> List<String> emailRecipients =
	 * Arrays.asList(candidateEmail);
	 * 
	 * // Log extracted values for debugging logger.info("Processing candidate: " +
	 * candidateName + ", Email: " + candidateEmail);
	 * 
	 * // Generate the PDF and retrieve it as a byte array byte[] pdfData =
	 * fetchOfferLetterPdf(candidateId, bandId, offerLetterId);
	 * 
	 * if (pdfData == null) {
	 * logger.error("Failed to generate offer letter PDF for candidate: " +
	 * candidateName); resp.setMessage("Failed to generate offer letter PDF");
	 * return resp; }
	 * 
	 * // Save the PDF to a temporary location and get the file path String
	 * pdfFilePath = savePdfToFile(pdfData, "Offer_Letter.pdf");
	 * 
	 * // Convert file path into an array String[] attachmentFiles = { pdfFilePath
	 * };
	 * 
	 * // New URL with necessary parameters
	 * 
	 * String url = env.getRecruitment() + "rest-finalize-candidate-selection";
	 * 
	 * // Send request resp = restTemplate.postForObject(url, data,
	 * JsonResponse.class);
	 * 
	 * 
	 * // Send email with PDF attachment if
	 * ("Success".equalsIgnoreCase(resp.getMessage())) { String subject =
	 * "Offer Letter for " + candidateName; String host = ConstantMailConfig.host;
	 * String port = ConstantMailConfig.port; String addresses =
	 * ConstantMailConfig.mailFrom; String password = ConstantMailConfig.password;
	 * String message = "Dear " + candidateName + ",\n\n" +
	 * "Congratulations! We are pleased to offer you the position of " + designation
	 * + " in the " + department + " department. Your joining date is " + joinDate +
	 * ".\n\n" + "Best regards,\nRecruitment Team";
	 * 
	 * EmailAttachmentSender.sendEmailWithAttachments(host, port, addresses,
	 * password, emailRecipients, null, subject, message, attachmentFiles); }
	 * 
	 * resp.setBody(resp.getBody()); resp.setMessage(resp.getMessage());
	 * resp.setCode(resp.getCode());
	 * 
	 * } catch (Exception e) { logger.error("Error in releaseCandidateOffer: ", e);
	 * }
	 * 
	 * logger.info("Method : releaseCandidateOffer ends"); return resp; }
	 * 
	 * private String savePdfToFile(byte[] pdfData, String fileName) throws
	 * IOException { String filePath = "/tmp/" + fileName; // Change path as needed
	 * Files.write(Paths.get(filePath), pdfData); return filePath; }
	 * 
	 * private byte[] fetchOfferLetterPdf(String candidateId, String bandId, String
	 * offerLetterId) { try { String encodedCandId =
	 * Base64.getEncoder().encodeToString(candidateId.getBytes()); String
	 * encodedBandId = Base64.getEncoder().encodeToString(bandId.getBytes()); String
	 * encodedOfferLetterId =
	 * Base64.getEncoder().encodeToString(offerLetterId.getBytes());
	 * 
	 * String pdfUrl = env.getBaseURL() + "/offer-letter-pdf?candId=" +
	 * encodedCandId + "&bandid=" + encodedBandId + "&offerLetter=" +
	 * encodedOfferLetterId;
	 * 
	 * ResponseEntity<byte[]> response = restTemplate.exchange(pdfUrl,
	 * HttpMethod.GET, null, byte[].class);
	 * 
	 * return response.getBody(); } catch (Exception e) {
	 * logger.error("Error fetching offer letter PDF: ", e); return null; } }
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("review-shortlist-candidate-offer-release")
	public @ResponseBody JsonResponse<Object> releaseCandidateOffer(HttpSession session,
			@RequestBody Map<String, Object> data) {
		logger.info("Method : releaseCandidateOffer starts");

		JsonResponse<Object> resp = new JsonResponse<>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error("Error fetching session attributes: " + e.getMessage());
		}

		try {
			// Extracting individual values from request data
			String candidateId = (String) data.get("candidateId");
			String requisitionId = (String) data.get("requisitionId");
			String candidateName = (String) data.get("candidateName");
			String candidatePhone = (String) data.get("candidatePhone");
			String candidateEmail = (String) data.get("candidateEmail");
			String finalCtc = (String) data.get("finalCtc");
			String jobTitle = (String) data.get("jobTitle");
			String joinDate = (String) data.get("joinDate");
			String designation = (String) data.get("designation");
			String department = (String) data.get("department");
			String workHour = (String) data.get("workHour");
			String bandId = (String) data.get("bandId");
			String offerLetterId = (String) data.get("offerLetterId");

			// Convert candidateEmail into a List<String>
			List<String> emailRecipients = Collections.singletonList(candidateEmail);

			// Log extracted values for debugging
			logger.info("Processing candidate: {}, Email: {}", candidateName, candidateEmail);

			// Fetch the Offer Letter PDF as a byte array directly
			byte[] pdfData = fetchOfferLetterPdf(candidateId, bandId, offerLetterId, session);

			if (pdfData == null || pdfData.length == 0) {
				logger.error("Failed to generate offer letter PDF for candidate: {}", candidateName);
				resp.setMessage("Failed to generate offer letter PDF");
				return resp;
			}

			String url = env.getRecruitment() + "rest-release-candidate-offer?candidateId=" + candidateId
					+ "&requisitionId=" + requisitionId + "&userId=" + userId;
			resp = restTemplate.getForObject(url, JsonResponse.class);

			if ("Success".equalsIgnoreCase(resp.getCode())) {

				// Send email with PDF as an attachment (without saving it)
				String subject = "Congratulations " + candidateName + " on Your New Journey with " + orgName + "!";
				String message = "<html>"
						+ "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>"
						+ "<div style='font-family: Arial, sans-serif; color: #333; line-height: 1.6;'>"
						+ "<div style='text-align: center;'>"
						+ "<img src='https://employee.nerp.in/document/document/1711914334491.png' alt='Nirmalya Labs' style='max-width: 200px; margin-bottom: 20px;'>"
						+ "</div>" + "<h2 style='color: #2c3e50;'>Offer of Employment</h2>" + "<p>Dear " + candidateName
						+ ",</p>" + "<p>We are pleased to extend an offer of employment for the position of <strong>"
						+ designation + "</strong> " + "in the <strong>" + department
						+ "</strong> department at <strong>" + orgName + "</strong>. "
						+ "We believe your skills and experience will be a great addition to our team.</p>"

						+ "<h3 style='color: #2c3e50;'>Offer Details:</h3>" + "<ul>"
						+ "<li><strong>Job Title:</strong> " + jobTitle + "</li>" + "<li><strong>Designation:</strong> "
						+ designation + "</li>" + "<li><strong>Department:</strong> " + department + "</li>"
						+ "<li><strong>Joining Date:</strong> " + joinDate + "</li>"
						+ "<li><strong>Work Hours:</strong> " + workHour + " hours per week</li>"
						+ "<li><strong>Compensation:</strong> " + finalCtc + "</li>" + "</ul>"

						+ "<p>Please find your official <strong>Offer Letter</strong> attached with this email for your review.</p>"

						+ "<h3 style='color: #2c3e50;'>Next Steps:</h3>"
						+ "<p>Kindly review the attached offer letter carefully and confirm your acceptance by replying to this email at your earliest convenience. "
						+ "Should you have any questions, please feel free to contact us.</p>"

						+ "<p>We look forward to welcoming you to our team and working together towards shared success.</p>"

						+ "<p>Best Regards,</p>" + "<p><strong>Recruitment Team</strong><br>" + orgName + "<br>"
						+ orgDivision + "</p>" + "</div>" + "</body>" + "</html>";

				EmailAttachmentSender.sendEmailWithByteAttachment(host,port,username,password, emailRecipients, null, subject,message, pdfData, "Offer_Letter.pdf");

			}
		} catch (Exception e) {
			logger.error("Error in releaseCandidateOffer: ", e);
			resp.setMessage("Error in processing request");
			resp.setCode("500");
		}

		logger.info("Method : releaseCandidateOffer ends");
		return resp;
	}

	/**
	 * Fetch Offer Letter PDF directly as a byte array.
	 */
	@SuppressWarnings("unchecked")
	private byte[] fetchOfferLetterPdf(String candidateId, String bandId, String offerLetterId, HttpSession session) {
		try {
			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			JsonResponse<Object> jsonResponse = restTemplate
					.getForObject(
							env.getRecruitment() + "viewpdf?candId=" + candidateId + "&bandid=" + bandId
									+ "&offerLetterId=" + offerLetterId + "&org=" + org + "&orgDiv=" + orgDiv,
							JsonResponse.class);
			
			System.out.println("jsonResponse:::::::::::::::::::::::::::::::::::::::"+jsonResponse);
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> offerLetterMap = new HashMap<>();
			 
			 try {
			        // Parsing 'body' field
			        List<String> bodyList = (List<String>) jsonResponse.getBody();
			        
			        if (bodyList != null && !bodyList.isEmpty()) {
			            List<Map<String, Object>> offerLetterList = mapper.readValue(bodyList.get(0), new TypeReference<List<Map<String, Object>>>() {});
			            if (offerLetterList != null && !offerLetterList.isEmpty()) {
			                offerLetterMap = offerLetterList.get(0); 
			            }
			        }
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			 
			 Map<String, Object> data = new HashMap<>();

		    // Fetch values from parsed JSON
		    String logo = "";
		    String sign = "";
		    String stamp = "";
		    String companyName = (String) offerLetterMap.getOrDefault("orgDivName", "");
			/*
			 * String orgAddress = (String) offerLetterMap.getOrDefault("org_address", "");
			 * String orgMail = (String) offerLetterMap.getOrDefault("org_email", "");
			 * String orgMob = (String) offerLetterMap.getOrDefault("orgMob", "");
			 */
		    String offerLetterType = (String) offerLetterMap.getOrDefault("letterType", "");
		    String ctc = offerLetterMap.get("ctc") != null ? String.valueOf(offerLetterMap.get("ctc")) : "";
		    String candidateName = offerLetterMap.get("candidateName") != null ? String.valueOf(offerLetterMap.get("candidateName")) : "";
		    String address = offerLetterMap.get("address") != null ? String.valueOf(offerLetterMap.get("address")) : "";
		    String candPhone = offerLetterMap.get("candPhone") != null ? String.valueOf(offerLetterMap.get("candPhone")) : "";
		    String jobDesignation = offerLetterMap.get("jobDesignation") != null ? String.valueOf(offerLetterMap.get("jobDesignation")) : "";
		    String joiningDate = offerLetterMap.get("joiningDate") != null ? String.valueOf(offerLetterMap.get("joiningDate")) : "";
		    String offerReleaseData = offerLetterMap.get("offerReleaseData") != null ? String.valueOf(offerLetterMap.get("offerReleaseData")) : "";
		    String orgMob = offerLetterMap.get("orgMob") != null ? String.valueOf(offerLetterMap.get("orgMob")) : "";
		    String org_email = offerLetterMap.get("org_email") != null ? String.valueOf(offerLetterMap.get("org_email")) : "";
		    String org_address = offerLetterMap.get("org_address") != null ? String.valueOf(offerLetterMap.get("org_address")) : "";
		    String orgDivName = offerLetterMap.get("orgDivName") != null ? String.valueOf(offerLetterMap.get("orgDivName")) : "";
		    String orgMsme = offerLetterMap.get("orgMsme") != null ? String.valueOf(offerLetterMap.get("orgMsme")) : "";
		    String orgCin = offerLetterMap.get("orgCin") != null ? String.valueOf(offerLetterMap.get("orgCin")) : "";
		    String orgUrl = offerLetterMap.get("orgUrl") != null ? String.valueOf(offerLetterMap.get("orgUrl")) : "";
		    String orgName = offerLetterMap.get("orgName") != null ? String.valueOf(offerLetterMap.get("orgName")) : "";

		    if (offerLetterMap.get("orgLogo") != null && !((String) offerLetterMap.get("orgLogo")).trim().isEmpty()) {
		        logo = env.getBaseURL() + "document/document/" + offerLetterMap.get("orgLogo");
		    }
		    if (offerLetterMap.get("orgSign") != null && !((String) offerLetterMap.get("orgSign")).trim().isEmpty() && !"null".equals(offerLetterMap.get("orgSign"))) {
		        sign = env.getBaseURL() + "document/document/" + offerLetterMap.get("orgSign");
		    }
		    if (offerLetterMap.get("orgStamp") != null && !((String) offerLetterMap.get("orgStamp")).trim().isEmpty() && !"null".equals(offerLetterMap.get("orgStamp"))) {
		        stamp = env.getBaseURL() + "document/document/" + offerLetterMap.get("orgStamp");
		    }
		    
		    data.put("logo", logo);
		    data.put("sign", sign);
		    data.put("stamp", stamp);
		    data.put("companyName", companyName);
		    data.put("orgDivName", orgDivName);
		    data.put("org_address", org_address);
		    data.put("orgMob", orgMob);
		    data.put("org_email", org_email);
		    data.put("offerLetter", offerLetterMap); 
		    data.put("letterType", offerLetterType);
		    data.put("address", address);
		    data.put("candidateName", candidateName);
		    data.put("ctc", ctc);
		    data.put("candPhone", candPhone);
		    data.put("jobDesignation", jobDesignation);
		    data.put("joiningDate", joiningDate);
		    data.put("offerReleaseData", offerReleaseData);
		    data.put("orgMsme", orgMsme);
		    data.put("orgUrl", orgUrl);
		    data.put("orgCin", orgCin);
		    data.put("orgName", orgName);
		    String templateName = "recruitment/generateOfferLetterPdf"; 

		    if ("APIL".equalsIgnoreCase(offerLetterType)) {
		        templateName = "recruitment/generateApilOfferLetterPdf"; 
		    }
			// Generate PDF and return as byte array
			return pdfGeneratorUtil.createPdfAsBytes(templateName, data);

		} catch (Exception e) {
			logger.error("Error fetching offer letter PDF: ", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("review-hiring-job-details")
	public @ResponseBody JsonResponse<Object> getJobInfoDetails(HttpSession session, @RequestParam("id") String reqId) {

		logger.info("Method : getJobInfoDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {

		}

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-requsition-info-details?orgName="
					+ organization + "&orgDivision=" + orgDivision + "&reqId=" + reqId, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getJobInfoDetails ends");
		return resp;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("/review-hiring-get-candidate-analysis") public @ResponseBody
	 * JsonResponse<Object> candidatesAnalysis(@RequestParam String
	 * candId, @RequestParam String reqId, HttpSession session) {
	 * logger.info("Method : candidatesAnalysis starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>();
	 * 
	 * try { // 1. Get organization details from session String organization =
	 * (String) session.getAttribute("ORGANIZATION"); String orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION");
	 * 
	 * // 2. Get Requisition Data JsonResponse<Object> requisitionResp =
	 * restTemplate.getForObject( env.getRecruitment() +
	 * "rest-get-requsition-info-details?orgName=" + organization + "&orgDivision="
	 * + orgDivision + "&reqId=" + reqId, JsonResponse.class);
	 * 
	 * // 3. Get Candidate Data JsonNode candidateData =
	 * getStaticCandidateData(candId); if (candidateData == null) { throw new
	 * Exception("Failed to parse candidate data"); }
	 * 
	 * // 4. Build the analysis prompt with exact JSON structure request
	 * JobDescription jobDescription = extractJobDescription(requisitionResp);
	 * String prompt = buildExactJsonFormatPrompt(candidateData, jobDescription);
	 * 
	 * // 5. Call OpenAI API String openAiResponse = callOpenAiApi(prompt); if
	 * (openAiResponse == null || openAiResponse.isEmpty()) { throw new
	 * Exception("Empty response from OpenAI"); }
	 * 
	 * // 6. Parse and return the structured JSON response Map<String, Object>
	 * analysisResult = parseExactFormatResponse(openAiResponse);
	 * resp.setBody(analysisResult); resp.setCode("success");
	 * resp.setMessage("Analysis completed successfully");
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * resp.setMessage("Analysis failed: " + e.getMessage()); resp.setCode("error");
	 * resp.setBody(createErrorResponse(e)); }
	 * 
	 * logger.info("Method : candidatesAnalysis ends"); return resp; }
	 * 
	 * private String buildExactJsonFormatPrompt(JsonNode candidateData,
	 * JobDescription jobDescription) { StringBuilder prompt = new StringBuilder();
	 * 
	 * prompt.append("Analyze this candidate for the ").append(jobDescription.
	 * getJobTitle())
	 * .append(" position and provide analysis in EXACTLY this JSON format:\n\n")
	 * .append("{\n") .append("  \"overallScore\": number (0-100),\n")
	 * .append("  \"experienceScore\": number (0-100),\n")
	 * .append("  \"skillsScore\": number (0-100),\n")
	 * .append("  \"educationScore\": number (0-100),\n")
	 * .append("  \"achievementsScore\": number (0-100),\n")
	 * .append("  \"strengths\": [\"string\"],\n")
	 * .append("  \"weaknesses\": [\"string\"],\n")
	 * .append("  \"skillAnalysis\": [\n")
	 * .append("    {\"skillName\": \"string\", \"score\": number, \"assessment\": \"string\"}\n"
	 * ) .append("  ],\n") .append("  \"experienceAnalysis\": \"string\",\n")
	 * .append("  \"educationAnalysis\": \"string\",\n")
	 * .append("  \"culturalFit\": \"string\",\n")
	 * .append("  \"recommendedSalary\": \"string\",\n")
	 * .append("  \"developmentAreas\": [\"string\"]\n") .append("}\n\n")
	 * .append("Candidate Profile:\n")
	 * .append(convertCandidateToText(candidateData))
	 * .append("\n\nJob Requirements:\n")
	 * .append(convertJobDescriptionToText(jobDescription))
	 * .append("\n\nImportant: Return ONLY the JSON object with no additional text or explanation. "
	 * )
	 * .append("The JSON must be valid and parsable. Use the exact field names specified."
	 * );
	 * 
	 * return prompt.toString(); }
	 * 
	 * private String convertCandidateToText(JsonNode candidateData) { StringBuilder
	 * text = new StringBuilder();
	 * text.append("Name: ").append(candidateData.path("name").asText()).append("\n"
	 * ); text.append("Summary: ").append(candidateData.path("summary").asText()).
	 * append("\n\n");
	 * 
	 * text.append("Work Experience:\n"); JsonNode workExperience =
	 * candidateData.path("workExperience"); for (JsonNode exp : workExperience) {
	 * text.append("- ").append(exp.path("position").asText())
	 * .append(" at ").append(exp.path("company").asText())
	 * .append(" (").append(exp.path("duration").asText()).append(")\n")
	 * .append("  ").append(exp.path("description").asText()).append("\n"); }
	 * 
	 * text.append("\nEducation:\n"); JsonNode education =
	 * candidateData.path("education"); for (JsonNode edu : education) {
	 * text.append("- ").append(edu.path("degree").asText())
	 * .append(", ").append(edu.path("institution").asText())
	 * .append(" (").append(edu.path("year").asText()).append(")\n")
	 * .append("  ").append(edu.path("score").asText()).append("\n"); }
	 * 
	 * text.append("\nSkills:\n"); JsonNode skills = candidateData.path("skills");
	 * for (JsonNode skill : skills) {
	 * text.append("- ").append(skill.path("name").asText())
	 * .append(" (").append(skill.path("experience").asText()).append(")\n"); }
	 * 
	 * text.append("\nAchievements:\n"); if (candidateData.has("achievements")) {
	 * JsonNode achievements = candidateData.path("achievements"); for (JsonNode
	 * achievement : achievements) {
	 * text.append("- ").append(achievement.asText()).append("\n"); } }
	 * 
	 * return text.toString(); }
	 * 
	 * private String convertJobDescriptionToText(JobDescription jobDescription) {
	 * StringBuilder text = new StringBuilder();
	 * text.append("Position: ").append(jobDescription.getJobTitle()).append("\n");
	 * text.append("Department: ").append(jobDescription.getDepartment()).append(
	 * "\n");
	 * text.append("Location: ").append(jobDescription.getLocation()).append("\n");
	 * text.append("Required Skills: ").append(String.join(", ",
	 * jobDescription.getRequiredSkills())).append("\n");
	 * text.append("Experience Required: ").append(jobDescription.
	 * getExperienceRequired()).append("\n");
	 * text.append("Education Required: ").append(jobDescription.
	 * getEducationRequired()).append("\n");
	 * 
	 * text.append("Responsibilities:\n"); for (String responsibility :
	 * jobDescription.getResponsibilities()) {
	 * text.append("- ").append(responsibility).append("\n"); }
	 * 
	 * text.append("Qualifications:\n"); for (String qualification :
	 * jobDescription.getQualifications()) {
	 * text.append("- ").append(qualification).append("\n"); }
	 * 
	 * text.append("Desired Achievements:\n"); if
	 * (jobDescription.getDesiredAchievements() != null) { for (String achievement :
	 * jobDescription.getDesiredAchievements()) {
	 * text.append("- ").append(achievement).append("\n"); } }
	 * 
	 * return text.toString(); }
	 * 
	 * @SuppressWarnings("unchecked") private Map<String, Object>
	 * parseExactFormatResponse(String openAiResponse) throws IOException {
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * // First parse the OpenAI API response JsonNode rootNode =
	 * mapper.readTree(openAiResponse); String content =
	 * rootNode.path("choices").get(0).path("message").path("content").asText();
	 * 
	 * // Then parse the actual analysis content return mapper.readValue(content,
	 * Map.class); }
	 * 
	 * private Map<String, Object> createErrorResponse(Exception e) { Map<String,
	 * Object> errorResponse = new HashMap<>(); errorResponse.put("overallScore",
	 * 0); errorResponse.put("error", e.getMessage()); return errorResponse; }
	 */

	private JsonNode getStaticCandidateData(String candId) throws IOException {
		// In a real application, you would fetch this from your database
		String candidateJson = "{\n" + "  \"candidateId\": \"" + candId + "\",\n" + "  \"name\": \"Pankaj Kumar\",\n"
				+ "  \"email\": \"pankaj.kr@gmail.com\",\n"
				+ "  \"summary\": \"To work in a firm with a professional work-driven environment where I can utilize and apply my knowledge and skills, enabling me as a fresh graduate to grow while fulfilling the organizational goals.\",\n"
				+ "  \"workExperience\": [\n" + "    {\n"
				+ "      \"position\": \"Software Development Engineer Intern\",\n"
				+ "      \"company\": \"Nirmalya Labs\",\n" + "      \"duration\": \"March 2023 – June 2023\",\n"
				+ "      \"description\": \"Developed reliable back-end systems using Spring Boot and MySQL for mattress order management, inventory control, and user authentication. Integrated Razorpay Payment Gateway for secure transactions.\"\n"
				+ "    },\n" + "    {\n" + "      \"position\": \"Full Stack Development Intern\",\n"
				+ "      \"company\": \"Syllogistic Systems Private Limited\",\n"
				+ "      \"duration\": \"March 2020 – May 2020\",\n"
				+ "      \"description\": \"Contributed to the development of a user-friendly mattress industry web application, offering seamless online shopping experiences for customers.\"\n"
				+ "    }\n" + "  ],\n" + "  \"education\": [\n" + "    {\n"
				+ "      \"degree\": \"Bachelor of Technology\",\n"
				+ "      \"institution\": \"Silicon Institute of Technology, Sambalpur\",\n"
				+ "      \"year\": \"June 2023\",\n" + "      \"score\": \"CGPA: 8.6\"\n" + "    }\n" + "  ],\n"
				+ "  \"skills\": [\n" + "    {\"name\": \"Java\", \"experience\": \"2 years\"},\n"
				+ "    {\"name\": \"Spring Boot\", \"experience\": \"1 year\"},\n"
				+ "    {\"name\": \"MySQL\", \"experience\": \"2 years\"},\n"
				+ "    {\"name\": \"HTML/CSS\", \"experience\": \"3 years\"},\n"
				+ "    {\"name\": \"JavaScript\", \"experience\": \"2 years\"}\n" + "  ],\n" + "  \"achievements\": [\n"
				+ "    \"Won hackathon for best e-commerce solution\",\n"
				+ "    \"Published paper on AI in education\"\n" + "  ],\n" + "  \"projects\": [\n" + "    {\n"
				+ "      \"name\": \"Shoukeen\",\n"
				+ "      \"description\": \"Developed a user-friendly mattress industry web application offering seamless online shopping experiences for customers.\"\n"
				+ "    }\n" + "  ]\n" + "}";

		try {
			return new ObjectMapper().readTree(candidateJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * private JobDescription extractJobDescription(JsonResponse<Object>
	 * requisitionResp) { JobDescription jobDescription = new JobDescription(); try
	 * { ObjectMapper mapper = new ObjectMapper(); String jsonStr =
	 * mapper.writeValueAsString(requisitionResp.getBody()); JsonNode rootNode =
	 * mapper.readTree(jsonStr);
	 * 
	 * jobDescription.setJobTitle(rootNode.path("jobTitle").asText());
	 * jobDescription.setDepartment(rootNode.path("department").asText());
	 * jobDescription.setLocation(rootNode.path("location").asText());
	 * jobDescription.setRequiredSkills(mapper.convertValue(rootNode.path(
	 * "requiredSkills"), new TypeReference<List<String>>(){}));
	 * jobDescription.setResponsibilities(mapper.convertValue(rootNode.path(
	 * "responsibilities"), new TypeReference<List<String>>(){}));
	 * jobDescription.setQualifications(mapper.convertValue(rootNode.path(
	 * "qualifications"), new TypeReference<List<String>>(){}));
	 * jobDescription.setExperienceRequired(rootNode.path("experienceRequired").
	 * asText());
	 * jobDescription.setEducationRequired(rootNode.path("educationRequired").asText
	 * ()); jobDescription.setDesiredAchievements(mapper.convertValue(rootNode.path(
	 * "desiredAchievements"), new TypeReference<List<String>>(){}));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return jobDescription; }
	 * 
	 * private String callOpenAiApi(String prompt) { String apiKey =
	 * "";
	 * String url = "";
	 * 
	 * RestTemplate restTemplate = new RestTemplate(); HttpHeaders headers = new
	 * HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON);
	 * headers.set("Authorization", "Bearer " + apiKey);
	 * 
	 * // Escape special characters in the prompt String escapedPrompt =
	 * prompt.replace("\"", "\\\"") .replace("\n", "\\n") .replace("\r", "\\r");
	 * 
	 * String requestBody = "{ " + "\"model\": \"gpt-3.5-turbo\", " +
	 * "\"messages\": [{\"role\": \"user\", \"content\": \"" + escapedPrompt +
	 * "\"}], " + "\"temperature\": 0.7, " + "\"max_tokens\": 2000, " +
	 * "\"response_format\": { \"type\": \"json_object\" } }";
	 * 
	 * HttpEntity<String> entity = new HttpEntity<>(requestBody, headers); try {
	 * ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
	 * entity, String.class); return response.getBody(); } catch
	 * (RestClientException e) { e.printStackTrace(); return null; } }
	 * 
	 * // Inner class for Job Description private static class JobDescription {
	 * private String jobTitle; private String department; private String location;
	 * private List<String> requiredSkills; private List<String> responsibilities;
	 * private List<String> qualifications; private String experienceRequired;
	 * private String educationRequired; private List<String> desiredAchievements;
	 * 
	 * // Getters and setters public String getJobTitle() { return jobTitle; }
	 * public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; } public
	 * String getDepartment() { return department; } public void
	 * setDepartment(String department) { this.department = department; } public
	 * String getLocation() { return location; } public void setLocation(String
	 * location) { this.location = location; } public List<String>
	 * getRequiredSkills() { return requiredSkills; } public void
	 * setRequiredSkills(List<String> requiredSkills) { this.requiredSkills =
	 * requiredSkills; } public List<String> getResponsibilities() { return
	 * responsibilities; } public void setResponsibilities(List<String>
	 * responsibilities) { this.responsibilities = responsibilities; } public
	 * List<String> getQualifications() { return qualifications; } public void
	 * setQualifications(List<String> qualifications) { this.qualifications =
	 * qualifications; } public String getExperienceRequired() { return
	 * experienceRequired; } public void setExperienceRequired(String
	 * experienceRequired) { this.experienceRequired = experienceRequired; } public
	 * String getEducationRequired() { return educationRequired; } public void
	 * setEducationRequired(String educationRequired) { this.educationRequired =
	 * educationRequired; } public List<String> getDesiredAchievements() { return
	 * desiredAchievements; } public void setDesiredAchievements(List<String>
	 * desiredAchievements) { this.desiredAchievements = desiredAchievements; } }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/review-hiring-get-candidate-analysis")
	public @ResponseBody JsonResponse<Object> candidatesAnalysis(@RequestParam String candId,
			@RequestParam String reqId, HttpSession session) {
		logger.info("Method : candidatesAnalysis starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			// 1. Get organization details from session
			String organization = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			// 2. Get Requisition Data
			JsonResponse<Object> requisitionResp = restTemplate
					.getForObject(env.getRecruitment() + "rest-get-requsition-info-details?orgName=" + organization
							+ "&orgDivision=" + orgDivision + "&reqId=" + reqId, JsonResponse.class);

			// 3. Get Candidate Data
			JsonNode candidateData = getStaticCandidateData(candId);

			// Parse requisition data
			ObjectMapper mapper = new ObjectMapper();
			JsonNode requisitionData = mapper.readTree((String) requisitionResp.getBody());
			JsonNode reqInfo = requisitionData.get("requisitionData").get(0);

			// 4. Construct the prompt for OpenAI
			StringBuilder prompt = new StringBuilder();
			prompt.append("You are an expert HR technical recruiter analyzing a candidate for a specific job role. ")
					.append("Perform a thorough analysis with these strict rules:\n")
					.append("1. Only consider skills explicitly mentioned in the job description\n")
					.append("2. Include a comprehensive CV summary with bullet points\n")
					.append("3. Be brutally honest in assessments\n\n")
					.append("Return analysis in EXACTLY this JSON format:\n\n").append("{\n")
					.append("  \"cvAnalysisSummary\": {\n")
					.append("    \"overview\": \"Provide a 2-3 sentence opening assessment summarizing candidate suitability and first impressions.\",\n")
					.append("    \"technicalExpertise\": {\n")
					.append("      \"summary\": \"Provide a detailed evaluation of technical competencies aligned with the job role. Mention technologies, tools, frameworks, and problem-solving ability.\",\n")
					.append("      \"strengths\": [\n")
					.append("        \"Use 3-5 bullet points to highlight key technical skills, certifications, or relevant accomplishments\",\n")
					.append("        \"Each bullet must be precise and supported with examples or achievements if available\"\n")
					.append("      ],\n").append("      \"gaps\": [\n")
					.append("        \"Use 2-4 bullet points to mention missing tools, insufficient experience, or outdated technologies\",\n")
					.append("        \"Justify why each gap is important for this specific role\"\n")
					.append("      ]\n").append("    },\n").append("    \"leadershipTeamManagement\": {\n")
					.append("      \"summary\": \"Analyze the candidate’s experience in leadership, mentoring, project ownership, and cross-functional collaboration.\",\n")
					.append("      \"strengths\": [\n")
					.append("        \"Use 2-4 bullet points to show team management, leadership impact, stakeholder collaboration, or hiring responsibilities\"\n")
					.append("      ],\n").append("      \"gaps\": [\n")
					.append("        \"List 1-3 areas where the candidate lacks leadership exposure or maturity\"\n")
					.append("      ]\n").append("    },\n").append("    \"growthPerformanceFocus\": {\n")
					.append("      \"summary\": \"Evaluate the candidate’s career progression, adaptability, and hunger for learning or growth.\",\n")
					.append("      \"strengths\": [\n")
					.append("        \"Mention career milestones, upskilling, promotions, or side-projects that show growth\"\n")
					.append("      ],\n").append("      \"gaps\": [\n")
					.append("        \"Include any stagnation signs, lack of certifications, or lack of diversified roles\"\n")
					.append("      ]\n").append("    },\n").append("    \"areasForDevelopment\": {\n")
					.append("      \"priorityAreas\": [\n")
					.append("        \"List 2-3 specific areas that need immediate development to fit the job requirements\"\n")
					.append("      ],\n").append("      \"developmentStrategies\": [\n")
					.append("        \"Recommend actionable learning strategies like online courses, mentorships, hands-on projects, or certifications\"\n")
					.append("      ]\n").append("    },\n").append("    \"keyStrengths\": [\n")
					.append("        \"3 concise bullet points summarizing the most impactful strengths\",\n")
					.append("        \"Should be unique, not repeated from other sections\"\n").append("    ],\n")
					.append("    \"criticalGaps\": [\n")
					.append("        \"2 critical weaknesses that could significantly affect the job fit\",\n")
					.append("        \"Should align with earlier listed gaps\"\n").append("    ],\n")
					.append("    \"finalRecommendation\": \"Give a clear and actionable hiring recommendation such as 'Hire with mentorship on X', 'Reject due to Y', etc.\"\n")
					.append("  },\n").append("  \"analysisSummary\": {\n")
					.append("    \"overallScore\": number (0-100),\n")
					.append("    \"recommendationStatus\": \"Strong Recommend/Recommend/Neutral/Not Recommended\",\n")
					.append("    \"recommendationSummary\": \"3-4 sentence detailed summary\",\n")
					.append("    \"topStrengths\": [\"string\"],\n").append("    \"criticalGaps\": [\"string\"],\n")
					.append("    \"culturalFit\": {\n").append("      \"score\": number (0-100),\n")
					.append("      \"assessment\": \"string\"\n").append("    }\n").append("  },\n")
					.append("  \"skillsEvaluation\": {\n").append("    \"skillsScore\": number (average 0-100),\n")
					.append("    \"skillsMatchPercentage\": number (%),\n").append("    \"skillsDetails\": [\n")
					.append("      {\n").append("        \"skillName\": \"string\",\n")
					.append("        \"required\": boolean,\n").append("        \"candidateExperience\": \"string\",\n")
					.append("        \"experienceMatch\": boolean,\n")
					.append("        \"skillScore\": number (0-100),\n")
					.append("        \"skillAssessment\": \"detailed analysis\",\n")
					.append("        \"developmentRecommendation\": \"specific advice\"\n").append("      }\n")
					.append("    ]\n").append("  },\n").append("  \"experienceEvaluation\": {\n")
					.append("    \"score\": number (0-100),\n").append("    \"yearsMatch\": boolean,\n")
					.append("    \"domainRelevance\": \"High/Medium/Low\",\n")
					.append("    \"keyGaps\": [\"string\"],\n").append("    \"keyStrengths\": [\"string\"]\n")
					.append("  },\n").append("  \"developmentPlan\": {\n")
					.append("    \"priorityAreas\": [\"string\"],\n")
					.append("    \"trainingRecommendations\": [\"specific courses/skills\"],\n")
					.append("    \"timeToProductivity\": \"realistic estimate\"\n").append("  },\n")
					.append("    \"notableAchievements\": {\n").append("        \"score\": number (0-100),\n")
					.append("        \"items\": [\n")
					.append("            \"Highlight 3-5 key accomplishments that demonstrate exceptional impact, innovation, or recognition\",\n")
					.append("            \"Achievements must be measurable or clearly valuable (e.g., awards, patents, major project wins)\"\n")
					.append("        ]\n").append("    },\n").append("  \"educationEvaluation\": {\n")
					.append("    \"score\": number (0-100),\n")
					.append("    \"summary\": \"Assess the candidate's academic background including degree relevance, institution quality, additional certifications, and continuous education. Comment on how well it supports the role requirements\"\n")
					.append("  },\n").append("  \"cvAnalyzeData\": [\n")
					.append("    \"Summarize the complete candidate evaluation in 12-15 bullet points, each written as a short paragraph.\",\n")
					.append("    \"Each bullet must reflect a distinct dimension of the candidate’s profile such as technical expertise, domain relevance, leadership, education, achievements, experience match, and overall recommendation.\",\n")
					.append("    \"Use complete thoughts for each bullet (1-2 sentences), keeping a natural tone and concise clarity.\",\n")
					.append("    \"Avoid repetition from previous sections but capture their essence into paragraph-style insights.\"\n")
					.append("  ]\n")

					.append("  \"compensationAnalysis\": {\n").append("    \"recommendedRange\": \"string\",\n")
					.append("    \"rationale\": \"market comparison analysis\"\n").append("  }\n").append("}\n\n")
					.append("Candidate Profile:\n").append(convertCandidateToText(candidateData))
					.append("\n\nJob Requirements:\n").append(convertJobDescriptionToText(reqInfo))
					.append("\n\nImportant:\n").append("- Return ONLY valid JSON with no additional text\n")
					.append("- All scores must be justified in their assessments\n")
					.append("- Bullet points should be concise but complete thoughts\n")
					.append("- Development recommendations must be actionable");

			// 5. Call OpenAI API
			String openAiResponse = callOpenAiApi(prompt.toString());

			// 6. Parse the OpenAI response and set it in the response object
			JsonNode analysisResult = mapper.readTree(openAiResponse);
			resp.setBody(analysisResult);
			resp.setMessage("Analysis completed successfully");
			resp.setCode("success");

		} catch (Exception e) {
			e.printStackTrace();
			resp.setMessage("Analysis failed: " + e.getMessage());
			resp.setCode("error");
		}

		logger.info("Method : candidatesAnalysis ends");
		return resp;
	}

	private String callOpenAiApi(String prompt) throws Exception {
		// Configure your OpenAI API key and endpoint
		String apiKey = "";
		String endpoint = "";

		// Create the request body
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode requestBody = mapper.createObjectNode();
		requestBody.put("model", "gpt-3.5-turbo");

		ArrayNode messages = mapper.createArrayNode();
		ObjectNode message = mapper.createObjectNode();
		message.put("role", "user");
		message.put("content", prompt);
		messages.add(message);

		requestBody.set("messages", messages);
		requestBody.put("temperature", 0.7);

		// Create HTTP headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(apiKey);

		// Create HTTP entity
		HttpEntity<String> entity = new HttpEntity<>(mapper.writeValueAsString(requestBody), headers);

		// Make the API call
		ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, String.class);

		// Parse the response to get the content
		JsonNode responseJson = mapper.readTree(response.getBody());
		return responseJson.path("choices").get(0).path("message").path("content").asText();
	}

	private String convertCandidateToText(JsonNode candidateData) {
		StringBuilder sb = new StringBuilder();

		sb.append("Name: ").append(candidateData.path("name").asText()).append("\n");
		sb.append("Email: ").append(candidateData.path("email").asText()).append("\n\n");

		sb.append("Summary:\n").append(candidateData.path("summary").asText()).append("\n\n");

		sb.append("Work Experience:\n");
		for (JsonNode exp : candidateData.path("workExperience")) {
			sb.append("- Position: ").append(exp.path("position").asText()).append("\n");
			sb.append("  Company: ").append(exp.path("company").asText()).append("\n");
			sb.append("  Duration: ").append(exp.path("duration").asText()).append("\n");
			sb.append("  Description: ").append(exp.path("description").asText()).append("\n\n");
		}

		sb.append("Education:\n");
		for (JsonNode edu : candidateData.path("education")) {
			sb.append("- Degree: ").append(edu.path("degree").asText()).append("\n");
			sb.append("  Institution: ").append(edu.path("institution").asText()).append("\n");
			sb.append("  Year: ").append(edu.path("year").asText()).append("\n");
			sb.append("  Score: ").append(edu.path("score").asText()).append("\n\n");
		}

		sb.append("Skills:\n");
		for (JsonNode skill : candidateData.path("skills")) {
			sb.append("- ").append(skill.path("name").asText()).append(" (").append(skill.path("experience").asText())
					.append(")\n");
		}
		sb.append("\n");

		sb.append("Achievements:\n");
		for (JsonNode achievement : candidateData.path("achievements")) {
			sb.append("- ").append(achievement.asText()).append("\n");
		}
		sb.append("\n");

		sb.append("Projects:\n");
		for (JsonNode project : candidateData.path("projects")) {
			sb.append("- ").append(project.path("name").asText()).append(": ")
					.append(project.path("description").asText()).append("\n");
		}

		return sb.toString();
	}

	private String convertJobDescriptionToText(JsonNode jobDescription) {
		StringBuilder sb = new StringBuilder();

		sb.append("Job Title: ").append(jobDescription.path("jobTitle").asText()).append("\n");
		sb.append("Designation: ").append(jobDescription.path("designation").asText()).append("\n");
		sb.append("Department: ").append(jobDescription.path("departmentName").asText()).append("\n");
		sb.append("Job Band: ").append(jobDescription.path("jobBand").asText()).append("\n");
		sb.append("Experience Required: ").append(jobDescription.path("minExp").asText()).append(" to ")
				.append(jobDescription.path("maxExp").asText()).append(" years\n");
		sb.append("Salary Range: Starts from ").append(jobDescription.path("minSalary").asText()).append("\n\n");

		sb.append("Summary:\n").append(jobDescription.path("summary").asText()).append("\n\n");

		sb.append("Responsibilities:\n").append(jobDescription.path("responsibility").asText()).append("\n\n");

		sb.append("Required Skills:\n");
		for (JsonNode skill : jobDescription.path("skillsReq")) {
			sb.append("- ").append(skill.path("skillName").asText()).append(" (Experience: ")
					.append(skill.path("skillExp").asText()).append(" years, Rating: ")
					.append(skill.path("skillRatings").asText()).append("/10)\n");
		}

		return sb.toString();
	}

	// save ai analyzed data

	@SuppressWarnings("unchecked")
	@PostMapping("review-hiring-save-analyze-data")
	public @ResponseBody JsonResponse<Object> saveaiAnalysiseData(HttpSession session,
			@RequestBody Map<String, Object> data) {
		logger.info("Method : saveaiAnalysiseData starts");

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
			resp = restTemplate.postForObject(env.getRecruitment() + "rest-save-analysis-data?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, data, JsonResponse.class);

			resp.setBody(resp.getBody());
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveaiAnalysiseData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("review-hiring-sendPreScreeningMail")
	public @ResponseBody JsonResponse<Object> shareSlotBookingEmail(HttpSession session,
	        @RequestBody Map<String, Object> data) {
	    logger.info("Method : shareSlotBookingEmail starts");

	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    String userId = "";
	    String orgName = "";
	    String orgDivision = "";
	    try {
	        userId = (String) session.getAttribute("USER_ID");
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        logger.error("Error getting session attributes", e);
	    }
	    
	    try {
	        String candMail = (String) data.get("email");
	        String candidateId = (String) data.get("candidateId");
	        String jobId = (String) data.get("jobId");
	        String candidate = (String) data.get("candidateName");
	        String jobName = (String) data.get("jobName");
	        
	        if (candMail == null || candMail.isEmpty()) {
	            resp.setCode("error");
	            resp.setMessage("Candidate email is required");
	            return resp;
	        }
	        
	        List<String> candidateEmail = new ArrayList<String>();
	        candidateEmail.add("kpankaj0297@gmail.com");
	        
	        String candidateName = candidate; 
	        JSONObject obj = new JSONObject();
	        obj.put("candidateId", candidateId);
	        obj.put("jobId", jobId);
	        
	        String encodedData = Base64.getUrlEncoder().encodeToString(obj.toString().getBytes());

	        String bookingLink = env.getBaseURL() + "interview-slot-booking/" + encodedData;
	        
	        String subject = "Schedule Your"+ jobName +" Pre-Screening";
	        try {
	            String message = getBookingEmailTemplate(candidateName, bookingLink,jobName);
	            EmailAttachmentSender.sendEmailWithAttachments(host, port, username, password, candidateEmail,
	                    null, subject, message, null);
	            
	            
	            resp = restTemplate.postForObject(env.getRecruitment() + "rest-cand-pre-inv-access?userId=" + userId
						+ "&org=" + orgName + "&orgDiv=" + orgDivision, data, JsonResponse.class);
	            resp.setCode("success");
	            resp.setMessage("Email sent successfully");
	        } catch (AddressException e) {
	            logger.error("Error sending email", e);
	            resp.setCode("error");
	            resp.setMessage("Failed to send email: invalid email address");
	        } catch (Exception e) {
	            logger.error("Error sending email", e);
	            resp.setCode("error");
	            resp.setMessage("Failed to send email");
	        }

	    } catch (Exception e) {
	        logger.error("Error in shareSlotBookingEmail", e);
	        resp.setCode("error");
	        resp.setMessage("An unexpected error occurred");
	    }

	    logger.info("Method : shareSlotBookingEmail ends");
	    return resp;
	}

	private String getBookingEmailTemplate(String candidateName, String bookingLink,String jobName) {
	    return "<!DOCTYPE html>\n" + "<html>\n" + "  <head>\n"
	            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + "    <style>\n"
	            + "      @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap');\n"
	            + "    </style>\n" + "  </head>\n"
	            + "  <body style=\"font-family: 'Poppins', Arial, sans-serif; margin: 0; padding: 0; background-color: #f8f9fa;\">\n"
	            + "    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"margin: 0 auto;\">\n"
	            + "      <!-- Header -->\n" + "      <tr>\n"
	            + "        <td style=\"padding: 20px 0; text-align: center;\">\n"
	            + "        </td>\n" + "      </tr>\n" + "      \n" + "      <!-- Main Card -->\n" + "      <tr>\n"
	            + "        <td style=\"background: white; border-radius: 16px; padding: 40px; box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);\">\n"
	            + "          <h1 style=\"color: #2a2f3d; font-size: 24px; font-weight: 700; margin-top: 0;\">Schedule Your AI-Powered Pre-Screening</h1>\n"
	            + "          \n" + "          <p style=\"color: #5a6475; font-size: 16px; line-height: 1.6;\">Dear "
	            + candidateName + ",</p>\n" + "          \n"
	            + "          <p style=\"color: #5a6475; font-size: 16px; line-height: 1.6;\">Thank you for applying to our <strong>"+ jobName +"</strong> position! We're excited to learn more about your experience through our innovative AI-powered project discussion.</p>\n"
	            + "          \n" + "          <!-- Interview Details -->\n"
	            + "          <div style=\"background-color: #f3f6ff; border-radius: 12px; padding: 20px; margin: 20px 0;\">\n"
	            + "            <p style=\"color: #4361ee; font-weight: 600; margin-top: 0; margin-bottom: 10px;\">Assessment Details:</p>\n"
	            + "            <ul style=\"color: #5a6475; font-size: 15px; line-height: 1.6; padding-left: 20px; margin: 0;\">\n"
	            + "              <li style=\"margin-bottom: 8px;\"><strong>Format:</strong> Interactive AI discussion about your projects</li>\n"
	            + "              <li style=\"margin-bottom: 8px;\"><strong>Duration:</strong> 45-60 minutes</li>\n"
	            + "              <li style=\"margin-bottom: 8px;\"><strong>Focus Areas:</strong> Your technical decisions, problem-solving, and achievements</li>\n"
	            + "              <li><strong>Preparation:</strong> Review your key projects</li>\n"
	            + "            </ul>\n" + "          </div>\n" + "          \n" + "          <!-- CTA Button -->\n"
	            + "          <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n" + "            <tr>\n"
	            + "              <td align=\"center\" style=\"padding: 25px 0 15px;\">\n" + "                <a href=\""
	            + bookingLink
	            + "\" target=\"_blank\" style=\"background: linear-gradient(90deg, #4CAF50 0%, #2E7D32 100%); color: white; padding: 16px 32px; text-decoration: none; border-radius: 50px; font-weight: 600; font-size: 16px; display: inline-block; box-shadow: 0 4px 12px rgba(76, 175, 80, 0.25);\">Schedule AI Assessment</a>\n"
	            + "              </td>\n" + "            </tr>\n" + "          </table>\n" + "          \n"
	            + "          <p style=\"color: #5a6475; font-size: 16px; line-height: 1.6; text-align: center;\">Slots are available for the next 7 days</p>\n"
	            + "          \n" + "          <!-- Tips Section -->\n"
	            + "          <div style=\"border-top: 1px dashed #e0e0e0; margin: 25px 0; padding-top: 20px;\">\n"
	            + "            <p style=\"color: #4361ee; font-weight: 600; margin-bottom: 10px;\">How to Prepare:</p>\n"
	            + "            <ul style=\"color: #5a6475; font-size: 15px; line-height: 1.6; padding-left: 20px; margin: 0;\">\n"
	            + "              <li style=\"margin-bottom: 8px;\">Select 1-2 significant Java projects to discuss</li>\n"
	            + "              <li style=\"margin-bottom: 8px;\">Refresh your memory on architectural decisions</li>\n"
	            + "              <li>Prepare to explain challenges and solutions</li>\n" + "            </ul>\n"
	            + "          </div>\n" + "          \n"
	            + "          <p style=\"color: #5a6475; font-size: 16px; line-height: 1.6;\">Our AI will engage in a natural conversation about your experience - no coding required during this session.</p>\n"
	            + "          <p style=\"color: #5a6475; font-size: 16px; line-height: 1.6;\">Best regards,<br><strong style=\"color: #2a2f3d;\">The Tech Recruitment Team</strong></p>\n"
	            + "        </td>\n" + "      </tr>\n" + "      \n" + "      <!-- Footer -->\n" + "      <tr>\n"
	            + "        <td style=\"padding: 30px 0; text-align: center;\">\n"
	            + "          <p style=\"color: #a0a7b5; font-size: 14px; line-height: 1.5; margin: 0;\">Need help? <a href=\"mailto:nirmalyalabs508@gmail.com\" style=\"color: #4361ee; text-decoration: none;\">Contact our support team</a></p>\n"
	            + "          <p style=\"color: #a0a7b5; font-size: 14px; line-height: 1.5; margin: 10px 0 0;\">© 2025 Nirmalya Labs Private Limited. All rights reserved.</p>\n"
	            + "        </td>\n" + "      </tr>\n" + "    </table>\n" + "  </body>\n" + "</html>";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("review-hiring-get-interviwers-data")
	public @ResponseBody JsonResponse<Object> getInterviewersDashboardData(HttpSession session) {

		logger.info("Method : getInterviewersDashboardData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {

		}

		try {
			resp = restTemplate
					.getForObject(env.getRecruitment() + "rest-get-interviwers-dashboard-data?userId="
							+ userId + "&orgName=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getInterviewersDashboardData ends");
		return resp;
	}

}
