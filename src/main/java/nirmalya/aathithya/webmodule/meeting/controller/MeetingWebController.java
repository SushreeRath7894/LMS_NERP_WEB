package nirmalya.aathithya.webmodule.meeting.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = "meeting")
public class MeetingWebController {

	Logger logger = LoggerFactory.getLogger(MeetingWebController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("/meeting-calendar")
	public String meetingCalendar(Model model, HttpSession session) {

		logger.info("Method : meeting-calendar starts");

		String userId = "";
		String userManager = "";
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			userManager = (String) session.getAttribute("MANAGER_ID");

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] emplist = restTemplate
					.getForObject(
							env.getMeetingUrl() + "rest-getEmployeeList?orgName=" + orgName + "&orgDivision="
									+ orgDivision + "&managerId=" + userManager + "&userId=" + userId,
							DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplistsEvent", emplists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : meeting-calendar ends");

		return "meeting/meeting-calendar";
	}
	
	@GetMapping("/meeting-mom")
	public String meetingCalendarMom(Model model, HttpSession session) {

		logger.info("Method : meeting-mom starts");
		
		String userId = "";
		String userManager = "";
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			userManager = (String) session.getAttribute("MANAGER_ID");

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] emplist = restTemplate
					.getForObject(
							env.getMeetingUrl() + "rest-getEmployeeList?orgName=" + orgName + "&orgDivision="
									+ orgDivision + "&managerId=" + userManager + "&userId=" + userId,
							DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplistsEvent", emplists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : meeting-calendar ends");

		return "meeting/meeting-mom";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/meeting-calendar-save-data")
	public @ResponseBody JsonResponse<Object> addMeetingInCalendar(@RequestBody Map<String, Object> meetingJsonData,
			HttpSession session) {
		logger.info("Method : addMeetingInCalendar starts");

		JsonResponse<Object> resp = new JsonResponse<>();
		String organization = "";
		String orgDivision = "";
		String createdById = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdById = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes", e);
		}

		try {
			String url = env.getMeetingUrl() + "rest-add-meeting";
			System.out.println("URL For Metting=====>" + url);
			Map<String, Object> requestPayload = new HashMap<>(meetingJsonData);
			requestPayload.put("orgName", organization);
			requestPayload.put("orgDiv", orgDivision);
			requestPayload.put("createdById", createdById);

			logger.info("Sending Meeting data to the service: " + requestPayload);

			resp = restTemplate.postForObject(url, requestPayload, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in addMeetingInCalendar: ", e);
		}

		logger.info("Method : addMeetingInCalendar ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-view-meeting")
	public @ResponseBody Object viewMeetingCalendar(@RequestParam String meetingStatus, HttpSession session) {

		logger.info("Method : viewMeetingCalendar starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(
					env.getMeetingUrl() + "rest-view-meeting-calendar?orgName=" + organization + "&orgDiv="
							+ orgDivision + "&userId=" + userId + "&meetingStatus=" + meetingStatus,
					JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in viewMeetingCalendar: ", e);
			e.printStackTrace();
		}

		logger.info("Method : viewMeetingCalendar ends");

		return resp;
	}
    
	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-get-all-meeting")
	public @ResponseBody Object getAllMeeting(HttpSession session) {

		logger.info("Method : getAllMeeting starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMeetingUrl() + "rest-get-all-meeting?orgName=" + organization + "&orgDiv="
							+ orgDivision + "&userId=" + userId,JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in getAllMeeting: ", e);
			e.printStackTrace();
		}

		logger.info("Method : getAllMeeting ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-edit-meeting")
	public @ResponseBody Object editMeetingCalendar(@RequestParam String meetingId, HttpSession session) {

		logger.info("Method : editMeetingCalendar starts");
		JsonResponse<Object> resp = new JsonResponse<>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}

		try {
			resp = restTemplate.getForObject(env.getMeetingUrl() + "rest-edit-meeting-calendar?orgName=" + organization
					+ "&orgDiv=" + orgDivision + "&userId=" + userId + "&meetingId=" + meetingId, JsonResponse.class);

			if (resp.getBody() != null) {
				// Deserialize the body from JSON string to List
				ObjectMapper objectMapper = new ObjectMapper();
				List<Map<String, Object>> meetings = objectMapper.readValue(resp.getBody().toString(),
						new TypeReference<List<Map<String, Object>>>() {
						});

				 String baseUrl = env.getBaseURL();
			//	String baseUrl = "https://origroup.nerp.in/";

				for (Map<String, Object> meeting : meetings) {
					// Update host images
					List<Map<String, Object>> hostDetails = (List<Map<String, Object>>) meeting.get("hostDetails");
					if (hostDetails != null) {
						for (Map<String, Object> host : hostDetails) {
							String hostImg = (String) host.get("hostImg");
							if (hostImg != null) {
								host.put("hostImg", baseUrl + "document/employee/" + hostImg);
							} else {
								host.put("hostImg", "https://static.vidnoz.com/system/asset/202408/66beeabb5fa40.jpg");
							}
						}
					}

					// Update attendee images
					List<Map<String, Object>> attendeesDetails = (List<Map<String, Object>>) meeting
							.get("attendeesDetails");
					if (attendeesDetails != null) {
						for (Map<String, Object> attendee : attendeesDetails) {
							String attendeeImg = (String) attendee.get("attendeeImg");
							if (attendeeImg != null) {
								attendee.put("attendeeImg", baseUrl + "document/employee/" + attendeeImg);
							} else {
								attendee.put("attendeeImg",
										"https://img.freepik.com/premium-photo/graphic-designer-digital-avatar-generative-ai_934475-9292.jpg");
							}
						}
					}
				}

				// Set the modified body back to the response
				resp.setBody(meetings);
			}
		} catch (Exception e) {
			logger.error("Error in editMeetingCalendar: ", e);
		}

		logger.info("Method : editMeetingCalendar ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-delete")
	public @ResponseBody Object deleteMeetingCalendar(@RequestParam String meetingId, HttpSession session) {

		logger.info("Method : deleteMeetingCalendar starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(env.getMeetingUrl() + "rest-delete-meeting-calendar?meetingId=" + meetingId
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in deleteMeetingCalendar: ", e);
			e.printStackTrace();
		}

		logger.info("Method : deleteMeetingCalendar ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-vital-meeting")
	public @ResponseBody Object getvitalMeeting(@RequestParam String currentDate, @RequestParam String currentTime,
			HttpSession session) {

		logger.info("Method : getvitalMeeting starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("URL FOR VITAL MEETING========>" + env.getMeetingUrl()
				+ "rest-vital-meeting-calendar?orgName=" + organization + "&orgDiv=" + orgDivision + "&userId=" + userId
				+ "&curentDate=" + currentDate + "&currentTime=" + currentTime);

		try {

			resp = restTemplate.getForObject(env.getMeetingUrl() + "rest-vital-meeting-calendar?orgName=" + organization
					+ "&orgDiv=" + orgDivision + "&userId=" + userId + "&currentDate=" + currentDate + "&currentTime="
					+ currentTime, JsonResponse.class);

			if (resp.getBody() != null) {
				// Deserialize the body from JSON string to List
				ObjectMapper objectMapper = new ObjectMapper();
				List<Map<String, Object>> meetings = objectMapper.readValue(resp.getBody().toString(),
						new TypeReference<List<Map<String, Object>>>() {
						});

				 String baseUrl = env.getBaseURL();
				//String baseUrl = "https://origroup.nerp.in/";

				for (Map<String, Object> meeting : meetings) {
					// Update host images
					List<Map<String, Object>> hostDetails = (List<Map<String, Object>>) meeting.get("hostDetails");
					if (hostDetails != null) {
						for (Map<String, Object> host : hostDetails) {
							String hostImg = (String) host.get("hostImg");
							if (hostImg != null) {
								host.put("hostImg", baseUrl + "document/employee/" + hostImg);
							} else {
								host.put("hostImg", "https://static.vidnoz.com/system/asset/202408/66beeabb5fa40.jpg");
							}
						}
					}
					// Update attendee images
					List<Map<String, Object>> attendeesDetails = (List<Map<String, Object>>) meeting
							.get("attendeesDetails");
					if (attendeesDetails != null) {
						for (Map<String, Object> attendee : attendeesDetails) {
							String attendeeImg = (String) attendee.get("attendeeImg");
							if (attendeeImg != null) {
								attendee.put("attendeeImg", baseUrl + "document/employee/" + attendeeImg);
							} else {
								attendee.put("attendeeImg",
										"https://img.freepik.com/premium-photo/graphic-designer-digital-avatar-generative-ai_934475-9292.jpg");
							}
						}
					}
				}

				// Set the modified body back to the response
				resp.setBody(meetings);
			}

		} catch (Exception e) {
			logger.error("Error in getvitalMeeting: ", e);
			e.printStackTrace();
		}

		logger.info("Method : viewMeetingCalendar ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-issue-raised-emp")
	public @ResponseBody Object issueRaisedEmployeeList(HttpSession session, @RequestParam String meetingId) {

		logger.info("Method : issueRaisedEmployeeList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(env.getMeetingUrl() + "rest-issue-raised-employeeList?meetingId="
					+ meetingId + "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in issueRaisedEmployeeList: ", e);
			e.printStackTrace();
		}

		logger.info("Method : issueRaisedEmployeeList ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("meeting-calendar-save-mom-dtls")
	public @ResponseBody JsonResponse<Object> saveMeetingDetails(@RequestBody Map<String, Object> momJsonData,
			HttpSession session) {
		logger.info("Method : saveMeetingDetails starts");

		JsonResponse<Object> resp = new JsonResponse<>();
		String organization = "";
		String orgDivision = "";
		String createdById = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdById = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes", e);
		}

		try {
			String url = env.getMeetingUrl() + "rest-save-mom-details";
			logger.info("URL For MOM Details=====>" + url);

			Map<String, Object> requestPayload = new HashMap<>(momJsonData);
			requestPayload.put("orgName", organization);
			requestPayload.put("orgDiv", orgDivision);
			requestPayload.put("createdById", createdById);

			logger.info("Sending MOM data to the service: " + requestPayload);

			resp = restTemplate.postForObject(url, requestPayload, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in saveMeetingDetails: ", e);
		}

		logger.info("Method : saveMeetingDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-get-moms-list")
	public @ResponseBody Object getMomsList(HttpSession session, @RequestParam String meetingId,@RequestParam String fromDate,
			@RequestParam String toDate) {

		logger.info("Method : getMomsList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(env.getMeetingUrl() + "rest-get-momsList?meetingId=" + meetingId
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in getMomsList: ", e);
			e.printStackTrace();
		}

		logger.info("Method : getMomsList ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-get-moms-details")
	public @ResponseBody Object getMomsDetails(HttpSession session, @RequestParam String meetingId,
			@RequestParam String momDate) {

		logger.info("Method : getMomsDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(
					env.getMeetingUrl() + "rest-get-momsDeatils?meetingId=" + meetingId + "&momDate=" + momDate
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&userId=" + userId,
					JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in getMomsList: ", e);
			e.printStackTrace();
		}

		logger.info("Method : getMomsDetails ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("meeting-calendar-employeeAutosearch")
	public @ResponseBody Object employeeAutoSearch(Model model, @RequestBody String searchValue, BindingResult result,
			HttpSession session) {

		logger.info("Method :employeeAutoSearch starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {

			resp = restTemplate.getForObject(env.getMasterUrl() + "get-all-employee-autosearch?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getCode().contentEquals("success")) {
			resp.setMessage("success");
		} else {
			resp.setMessage(" ");
		}

		logger.info("Method :employeeAutoSearch ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-get-open-issues")
	public @ResponseBody Object getOpenIssues(HttpSession session, @RequestParam String meetingId) {

		logger.info("Method : getOpenIssues starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(env.getMeetingUrl() + "rest-get-open-issues?meetingId=" + meetingId
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&userId=" + userId, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in getOpenIssues: ", e);
			e.printStackTrace();
		}

		logger.info("Method : getOpenIssues ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-get-meetings")
	public @ResponseBody Object getMeetingsForCalendars(HttpSession session, @RequestParam String startDate,
			@RequestParam String endtDate) {

		logger.info("Method : getMeetingsForCalendars starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(
					env.getMeetingUrl() + "rest-get-all-meetings-list?startDate=" + startDate + "&endtDate=" + endtDate
							+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&userId=" + userId,
					JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in getMeetingsForCalendars: ", e);
			e.printStackTrace();
		}

		logger.info("Method : getMeetingsForCalendars ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-remove-issues")
	public @ResponseBody Object removeOpenIssues(HttpSession session, @RequestParam String issueId) {

		logger.info("Method : removeOpenIssues starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(env.getMeetingUrl() + "rest-get-remove-open-issues?issueId=" + issueId
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&userId=" + userId, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in removeOpenIssues: ", e);
			e.printStackTrace();
		}

		logger.info("Method : removeOpenIssues ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("meeting-calendar-get-each-meeting-details")
	public @ResponseBody Object getCalendarMeetingDetails(HttpSession session, @RequestParam String id) {

		logger.info("Method : getCalendarMeetingDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(env.getMeetingUrl() + "rest-get-calendar-meeting-details?id=" + id
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&userId=" + userId, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in getCalendarMeetingDetails: ", e);
			e.printStackTrace();
		}

		logger.info("Method : getCalendarMeetingDetails ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/meeting-calendar-save-calendar-details")
	public @ResponseBody JsonResponse<Object> saveMeetingChangesDetails(@RequestBody Map<String, Object> calendarDetails,
			HttpSession session) {
		
		System.out.println("Dataaaa:::"+calendarDetails);
		logger.info("Method : saveMeetingChangesDetails starts");

		JsonResponse<Object> resp = new JsonResponse<>();
		String organization = "";
		String orgDivision = "";
		String createdById = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdById = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes", e);
		}

		try {
			/*
			 * List<Map<String, String>> meetingDocuments = (List<Map<String, String>>)
			 * calendarDetails.get("meetingDocuments");
			 * 
			 * if (meetingDocuments != null) { for (Map<String, String> document :
			 * meetingDocuments) { String filename = document.get("filename"); if (filename
			 * != null) { String fileUrl = env.getBaseURL() + "document/meetings/" +
			 * filename; document.put("filename", fileUrl); } } }
			 */
			
			String url = env.getMeetingUrl() + "rest-save-meeting-changes";
			Map<String, Object> requestPayload = new HashMap<>(calendarDetails);
			/* requestPayload.put("meetingDocuments", meetingDocuments); */
			requestPayload.put("orgName", organization);
			requestPayload.put("orgDiv", orgDivision);
			requestPayload.put("createdById", createdById);

			logger.info("Sending Meeting data to the service: " + requestPayload);

			resp = restTemplate.postForObject(url, requestPayload, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in saveMeetingChangesDetails: ", e);
			e.printStackTrace();
		}

		logger.info("Method : saveMeetingChangesDetails ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "meeting-calendar-mom-Pdf" })
	public void generatePdfForMomReport(
	        @RequestParam String momMeetingId, 
	        @RequestParam String momDate, 
	        HttpServletResponse response, 
	        HttpSession session) {

	    logger.info("Method : generatePdfForMomReport starts");

	    String orgName = (String) session.getAttribute("ORGANIZATION");
	    String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    String userId = (String) session.getAttribute("USER_ID");

	    JsonResponse<Object> resp = new JsonResponse<>();

	    try {
	        resp = restTemplate.getForObject(
	                env.getMeetingUrl() + "rest-get-momsDeatils?meetingId=" + momMeetingId + "&momDate=" + momDate
	                        + "&orgName=" + orgName + "&orgDiv=" + orgDivision + "&userId=" + userId,
	                JsonResponse.class);
	    } catch (Exception e) {
	        logger.error("Error in restTemplate call: ", e);
	        return;
	    }

	    logger.info("Fetched response data: " + resp);

	    // Parse the JSON response
	    String responseBody = (String) resp.getBody();
	    Map<String, Object> pdfData = new HashMap<>();

	    try {
	        ObjectMapper mapper = new ObjectMapper();
	        Map<String, Object> responseMap = mapper.readValue(responseBody, Map.class);
	        Map<String, Object> discussedData = (Map<String, Object>) responseMap.get("topicDiscussed");
 	        String formattedDate=(String)discussedData.get("formatted_date");
 	        String meetingSummary=(String)discussedData.get("topic");
 	        System.out.println("Discussion Summary"+meetingSummary);
 	        pdfData.put("formattedDate", formattedDate);
 	        pdfData.put("meetingSummary", meetingSummary);
	        List<Map<String, Object>> meetingDetailsList = (List<Map<String, Object>>) responseMap.get("details");
	        List<Map<String, String>> responsibilities = new ArrayList<>();
	        for (Map<String, Object> meetingDetails : meetingDetailsList) {
	 	        pdfData.put("meetingDetails", meetingDetails);
	 	        // Extract and combine meetingHost and meetingAttendees as "participants"
	 	        String meetingAttendees = (String) meetingDetails.get("meetingAttendees");
	 	        String orgAddress = (String) meetingDetails.get("orgAddress");
	 	        String orgNamee = (String) meetingDetails.get("orgName");
	 	        String orgLogo = (String) meetingDetails.get("orgLogo");
	 	        String orgLogoUrl = env.getBaseURL() +"document/document/"+ orgLogo;
	 	        System.out.println(orgLogoUrl);
	 	        String participants = meetingAttendees ;
	 	        pdfData.put("participants", participants);
	 	        pdfData.put("orgAddress", orgAddress);
	 	        pdfData.put("orgNamee", orgNamee);
	 	        pdfData.put("orgLogoUrl", orgLogoUrl);
	 	        // Extract meetingAgenda
	 	        String meetingAgenda = (String) meetingDetails.get("meetingAgenda");
	 	        pdfData.put("meetingAgenda", meetingAgenda);
	             
	 	        String discussionPointsJson = (String) meetingDetails.get("meetingDiscussionPoint");
	 	        List<Map<String, Object>> discussionPoints = new ArrayList<>();
	 	        discussionPoints = mapper.readValue(discussionPointsJson, new TypeReference<List<Map<String, Object>>>() {});
	 	        List<String> discussionSummary = new ArrayList<>();
	 	        for (Map<String, Object> point : discussionPoints) {
	 	            for (String key : point.keySet()) {
	 	                if (key.startsWith("point_")) {
	 	                    discussionSummary.add((String) point.get(key));
	 	                }
	 	            }
	 	        }
	 	        pdfData.put("discussionSummary", discussionSummary);

	 	        Map<String, String> responsibility = new HashMap<>();
		 	    responsibility.put("responsibility", (String) meetingDetails.get("issue"));
		 	    responsibility.put("assignedTo", (String) meetingDetails.get("issueResolveBy"));
		 	    responsibility.put("deadline", (String) meetingDetails.get("date"));
		 	    responsibility.put("priority", (String) meetingDetails.get("priority"));
		 	    responsibility.put("attendName", (String) meetingDetails.get("attendName"));
		 	    responsibility.put("curAction", (String) meetingDetails.get("curAction"));
		 	    responsibilities.add(responsibility);

		 	    // Check all keys' values
		 	    boolean allValuesPresent = responsibility.values().stream()
	 	          .allMatch(value -> value != null && !value.equals("null") && !value.isEmpty());

		 	    if (allValuesPresent) {
	 	          pdfData.put("responsibilities", responsibilities);
		 	    } else {
	 	          pdfData.put("responsibilities", "null");
		 	    }

                System.out.println(pdfData);
	        }
	    } catch (IOException e) {
	        logger.error("Error parsing JSON response: ", e);
	    }



	    String filename = "meeting-calendar-mom.pdf";
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "inline; filename=" + filename);

	    try {
	        File pdfFile = pdfGeneratorUtil.createPdf("meeting/mom-report-pdf", pdfData);
	        InputStream in = new FileInputStream(pdfFile);
	        byte[] fileData = IOUtils.toByteArray(in);
	        response.setContentLength(fileData.length);
	        response.getOutputStream().write(fileData);
	        response.getOutputStream().flush();
	    } catch (IOException e) {
	        logger.error("Error while generating or sending PDF", e);
	    } catch (Exception e1) {
	        logger.error("Unexpected error occurred", e1);
	    }

	    logger.info("Method : generatePdfForMomReport ends");
	}
	
	/*
	 * @PostMapping("meeting-calendar-upload-documents") public
	 * ResponseEntity<String> uploadFiles(@RequestParam("documents[]")
	 * MultipartFile[] files) { String location = env.getFileUploadMeetings();
	 * System.out.println(location); File uploadDir = new File(location); if
	 * (!uploadDir.exists()) { uploadDir.mkdirs(); }
	 * 
	 * for (MultipartFile file : files) { try { File destinationFile = new
	 * File(uploadDir, file.getOriginalFilename());
	 * file.transferTo(destinationFile);
	 * 
	 * } catch (IOException e) { e.printStackTrace(); return
	 * ResponseEntity.status(500).body("Error saving file: " +
	 * file.getOriginalFilename()); } }
	 * 
	 * return ResponseEntity.ok("Files uploaded successfully"); }
	 */

}
