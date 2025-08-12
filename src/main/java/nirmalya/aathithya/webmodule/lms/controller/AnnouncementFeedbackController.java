package nirmalya.aathithya.webmodule.lms.controller;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping("academic")
public class AnnouncementFeedbackController {
	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(AnnouncementFeedbackController.class);

	@GetMapping(value = { "/announcement" })
	public String announcement(Model model, HttpSession session) {
		logger.info("Mothod:view announcement page started...");

		logger.info("Mothod: view announcement page ends...");
		return "lms/announcement";
	}

	// academic-course-add
	@SuppressWarnings("unchecked")
	@PostMapping("announcement-add")
	public @ResponseBody JsonResponse<Object> saveAnnouncement(HttpSession session,
			@RequestParam("announcementId") String announcementId, @RequestParam("subject") String subject,
			@RequestParam("description") String description, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestParam("employeeList") String employeeListJson,
			@RequestParam("studentList") String studentListJson, @RequestParam("assignStudId") String assignStudId,
			@RequestParam("studentId") String studentId) {

		logger.info("Method : saveAnnouncement starts");

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
			ObjectMapper mapper = new ObjectMapper();

			// Parse employeeList JSON into List
			List<Map<String, Object>> employeeList = mapper.readValue(employeeListJson, List.class);

			// Parse studentList JSON into List
			List<Map<String, Object>> studentList = mapper.readValue(studentListJson, List.class);

			logger.info("Employee List: " + employeeList);
			logger.info("Student List: " + studentList);

			// Prepare data to send to REST client
			Map<String, Object> announceData = new HashMap<>();
			announceData.put("announcementId", announcementId);
			announceData.put("subject", subject);
			announceData.put("description", description);
			announceData.put("startDate", startDate);
			announceData.put("endDate", endDate);
			announceData.put("employeeList", employeeList);
			announceData.put("studentList", studentList);
			announceData.put("assignStudId", assignStudId);
			announceData.put("studentId", studentId);

			resp = restClient.postForObject(env.getHisUrl() + "rest-academic-announcement-add?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, announceData, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in saveAnnouncement", e);
			resp.setMessage("Error saveAnnouncement");
			resp.setCode("Failed");
		}

		logger.info("Method : saveAnnouncement ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("announcement-view")
	public @ResponseBody Object viewAnnouncement(HttpSession session) {
		logger.info("Method :viewAnnouncement starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(
					env.getHisUrl() + "rest-viewAnnouncement?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewAnnouncement ends");
		return resp;
	}

	// edit
	@SuppressWarnings("unchecked")
	@GetMapping("announcement-edit")
	public @ResponseBody Object editAnnouncement(@RequestParam String Id, HttpSession session) {
		logger.info("Method :editAnnouncement starts" + Id);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-editAnnouncement?Id=" + Id + "&organization="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editAnnouncement ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("announcement-employee-view")
	public @ResponseBody Object viewEmpolyee(HttpSession session) {
		logger.info("Method :viewEmpolyee starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-academic-course-employee-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewEmpolyee ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("announcement-student-view")
	public @ResponseBody Object viewStudent(HttpSession session) {
		logger.info("Method :viewStudent starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-academic-course-student-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewStudent ends");
		return resp;
	}

//
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("announcement-employee-add") public @ResponseBody
	 * JsonResponse<Object> saveAssignEmployee(HttpSession session,
	 * 
	 * @RequestBody Map<String, Object> data) {
	 * logger.info("Method : saveAssignEmployee starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>(); String userId = "";
	 * String orgName = ""; String orgDivision = ""; try { userId = (String)
	 * session.getAttribute("USER_ID"); orgName = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * 
	 * } try { resp = restClient.postForObject(env.getHisUrl() +
	 * "rest-academic-employee-assign?userId=" + userId + "&org=" + orgName +
	 * "&orgDiv=" + orgDivision, data, JsonResponse.class);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * logger.info("Method : saveAssignEmployee ends"); return resp; }
	 * 
	 * //
	 * 
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("announcement-student-add") public @ResponseBody
	 * JsonResponse<Object> saveAssignStudent(HttpSession session,
	 * 
	 * @RequestBody Map<String, Object> data) {
	 * logger.info("Method : saveAssignStudent starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>(); String userId = "";
	 * String orgName = ""; String orgDivision = ""; try { userId = (String)
	 * session.getAttribute("USER_ID"); orgName = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * 
	 * } try { resp = restClient.postForObject(env.getHisUrl() +
	 * "rest-academic-course-student-add?userId=" + userId + "&org=" + orgName +
	 * "&orgDiv=" + orgDivision, data, JsonResponse.class);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * logger.info("Method : saveAssignStudent ends"); return resp; }
	 */
//
	@GetMapping(value = { "/feedback" })
	public String feedback(Model model, HttpSession session) {
		logger.info("Mothod:view feedback page started...");

		logger.info("Mothod: view feedback page ends...");
		return "lms/feedback";
	}

	// academic-course-add
	@SuppressWarnings("unchecked")
	@PostMapping("feedback-add")
	public @ResponseBody JsonResponse<Object> saveFeedback(HttpSession session,
			@RequestParam("feedbackId") String feedbackId, @RequestParam("title") String title,
			@RequestParam("subject") String subject, @RequestParam("description") String description,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("employeeList") String employeeListJson, @RequestParam("studentList") String studentListJson,
			@RequestParam("assignStudId") String assignStudId, @RequestParam("studentId") String studentId,
			@RequestParam("questions") String questionsJson) {

		logger.info("Method : saveFeedback starts");

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
			ObjectMapper mapper = new ObjectMapper();

			// Parse employeeList JSON into List
			List<Map<String, Object>> employeeList = mapper.readValue(employeeListJson, List.class);

			// Parse studentList JSON into List
			List<Map<String, Object>> studentList = mapper.readValue(studentListJson, List.class);
			
			// Parse studentList JSON into List
			List<Map<String, Object>> questionList = mapper.readValue(questionsJson, List.class);

			logger.info("Employee List: " + employeeList);
			logger.info("Student List: " + studentList);

			// Prepare data to send to REST client
			Map<String, Object> feedbackData = new HashMap<>();
			feedbackData.put("feedbackId", feedbackId);
			feedbackData.put("title", title);
			feedbackData.put("subject", subject);
			feedbackData.put("description", description);
			feedbackData.put("startDate", startDate);
			feedbackData.put("endDate", endDate);
			feedbackData.put("employeeList", employeeList);
			feedbackData.put("studentList", studentList);
			feedbackData.put("assignStudId", assignStudId);
			feedbackData.put("studentId", studentId);
			feedbackData.put("questions", questionList);

			resp = restClient.postForObject(env.getHisUrl() + "rest-academic-feedback-add?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, feedbackData, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in saveFeedback", e);
			resp.setMessage("Error saveFeedback");
			resp.setCode("Failed");
		}

		logger.info("Method : saveFeedback ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("feedback-view")
	public @ResponseBody Object viewFeedback(HttpSession session) {
		logger.info("Method :viewFeedback starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(
					env.getHisUrl() + "rest-viewFeedback?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewFeedback ends");
		return resp;
	}

	// edit
	@SuppressWarnings("unchecked")
	@GetMapping("feedback-edit")
	public @ResponseBody Object editFeedback(@RequestParam String Id, HttpSession session) {
		logger.info("Method :editFeedback starts" + Id);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-editFeedback?Id=" + Id + "&organization=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editFeedback ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("feedback-employee-view")
	public @ResponseBody Object viewEmpolyeeFeedback(HttpSession session) {
		logger.info("Method :viewEmpolyeeFeedback starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-academic-course-employee-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewEmpolyeeFeedback ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("feedback-student-view")
	public @ResponseBody Object viewStudentFeedback(HttpSession session) {
		logger.info("Method :viewStudentFeedback starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-academic-course-student-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewStudentFeedback ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("get-all-given-feedback")
	public @ResponseBody Object getAllGivenFeedback(HttpSession session,@RequestParam String feedbackId) {
		logger.info("Method :getAllGivenFeedback starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getHisUrl() + "rest-get-all-users-feedbacks?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&feedbackId=" + feedbackId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :getAllGivenFeedback ends");
		return resp;
	}
}
