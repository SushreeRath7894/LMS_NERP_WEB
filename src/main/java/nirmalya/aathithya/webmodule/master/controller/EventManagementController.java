package nirmalya.aathithya.webmodule.master.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.util.IOUtils;

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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.master.model.EventActivityModel;
import nirmalya.aathithya.webmodule.master.model.EventManagementModel;

@Controller
@RequestMapping(value = "master")
public class EventManagementController {
	Logger logger = LoggerFactory.getLogger(EventManagementController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("/event-management")
	public String getMapManageEvent(Model model, HttpSession session) {
		logger.info("Method : getMapManageEvent starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String userRoles = "";
		String userManager = "";
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			userManager = (String) session.getAttribute("MANAGER_ID");
		
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		// dropdown of event manage page starts here
		try {
			DropDownModel[] organiser = restTemplate.getForObject(env.getMasterUrl() + "rest-getOrganiserList",
					DropDownModel[].class);
			List<DropDownModel> organiserList = Arrays.asList(organiser);
			model.addAttribute("organiserList", organiserList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] eventType = restTemplate.getForObject(env.getMasterUrl() + "rest-getEventTypeList",
					DropDownModel[].class);
			List<DropDownModel> eventTypeList = Arrays.asList(eventType);
			model.addAttribute("eventTypeList", eventTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] responsible = restTemplate.getForObject(env.getMasterUrl() + "rest-getResponsibleList",
					DropDownModel[].class);
			List<DropDownModel> responsibleList = Arrays.asList(responsible);
			model.addAttribute("responsibleList", responsibleList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] vanue = restTemplate.getForObject(env.getMasterUrl() + "rest-getVanueList",
					DropDownModel[].class);
			List<DropDownModel> vanueList = Arrays.asList(vanue);
			model.addAttribute("vanueList", vanueList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		
		
		try {
			DropDownModel[] emplist = restTemplate.getForObject(env.getMasterUrl() + "rest-getEmployeeListEvent?orgName="+orgName+"&orgDivision="+orgDivision+"&managerId="+userManager+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplistsEvent", emplists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] traininglist = restTemplate.getForObject(env.getMasterUrl() + "rest-getTrainingList?orgName="+orgName+"&orgDivision="+orgDivision,
					DropDownModel[].class);
			List<DropDownModel> traininglists = Arrays.asList(traininglist);
			model.addAttribute("traininglists", traininglists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;
			System.out.println("data"+data);

			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
			
			if (data.contentEquals("rol001") || data.contentEquals("rol053")) {
				model.addAttribute("mechanicalRole", data);
			}
		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("userRoles", userRoles);
		model.addAttribute("userManager", userManager);
		// dropdown of event manage page ends here
		logger.info("Method : getMapManageEvent starts"+userManager);
		return "master/event-management";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("event-management-details-view")
	public @ResponseBody List<EventManagementModel> viewEventManagement(HttpSession session,
			@RequestParam String userid, @RequestParam String roleid) {

		logger.info("Method : viewEventManagement starts");
		JsonResponse<List<EventManagementModel>> resp = new JsonResponse<List<EventManagementModel>>();

		List<String> roleList = new ArrayList<String>();
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (roleid != null && roleid != "") {
			String[] arr = roleid.split(",");
			for (int i = 0; i < arr.length; i++) {
				roleList.add(arr[i]);
			}
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userid);
		empModel.setUserRole(roleList);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "viewEventManagement", empModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<EventManagementModel> eventManagementModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<EventManagementModel>>() {
				});
		String dateFormat = "";

		try {
			logger.info("#####" + (String) session.getAttribute("DATEFORMAT"));
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			if (dateFormat == null) {
				dateFormat = "yyyy-MM-dd";
			}

		} catch (Exception e) {
		}
		// logger.info(eventManagementModel.get(0).getAdvanceApplyDate());
		// logger.info(eventManagementModel.get(0).getApprovedDate());

		if (eventManagementModel != null)
			for (EventManagementModel a : eventManagementModel) {
				if (a.getFromDate() != null && a.getFromDate() != "") {
					a.setFromDate(DateFormatter.dateFormat(a.getFromDate(), dateFormat));
				}
				if (a.getToDate() != null && a.getToDate() != "") {
					a.setToDate(DateFormatter.dateFormat(a.getToDate(), dateFormat));
				}
				if (a.getRegdStartDate() != null && a.getRegdStartDate() != "") {
					a.setRegdStartDate(DateFormatter.dateFormat(a.getRegdStartDate(), dateFormat));
				}
				if (a.getRegdEndDate() != null && a.getRegdEndDate() != "") {
					a.setRegdEndDate(DateFormatter.dateFormat(a.getRegdEndDate(), dateFormat));
				}
				if (a.getCreatedOn() != null && a.getCreatedOn() != "") {
					a.setCreatedOn(DateFormatter.dateFormat(a.getCreatedOn(), dateFormat));
				}
			}

		logger.info("EventManagement VIEWWW" + eventManagementModel);
		logger.info("Method : viewEventManagement ends");
		return eventManagementModel;
	}

	// method to save data for event management
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/event-management-add")
	public @ResponseBody JsonResponse<Object> addEvant(@RequestBody EventManagementModel eventManagementModel,
			Model model, HttpSession session) {
		logger.info("Method :addEvant starts");
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (eventManagementModel.getFromDate() != null && eventManagementModel.getFromDate() != "") {
			eventManagementModel
					.setFromDate(DateFormatter.inputDateFormat(eventManagementModel.getFromDate(), dateFormat));
		}
		if (eventManagementModel.getToDate() != null && eventManagementModel.getToDate() != "") {
			eventManagementModel.setToDate(DateFormatter.inputDateFormat(eventManagementModel.getToDate(), dateFormat));
		}
		

		List<EventActivityModel> activity = eventManagementModel.getActivity();
		for (EventActivityModel m : activity) {

			if (m.getEventActivityDate() != null && m.getEventActivityDate() != "") {
				m.setEventActivityDate(DateFormatter.inputDateFormat(m.getEventActivityDate(), dateFormat));
			}
		}
		eventManagementModel.setEventCreatedBy(userId);
		eventManagementModel.setOrganization(organization);
		eventManagementModel.setOrgDivision(orgDivision);
		try {
			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "rest-add-evant", eventManagementModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = jsonResponse.getMessage();
		if (message != null && message != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : addEvant ends");
		return jsonResponse;
	}

	// approve leave

	@SuppressWarnings("unchecked")
	@GetMapping("event-management-details-approve")
	public @ResponseBody JsonResponse<EventManagementModel> approveEventManagement(@RequestParam String approveId,
			String name, String comment, String roleid) {

		logger.info("Method : approveEventManagement starts");
		JsonResponse<EventManagementModel> response = new JsonResponse<EventManagementModel>();
		try {
			response = restTemplate.getForObject(env.getMasterUrl() + "approveEventManagement?id=" + approveId
					+ "&name=" + name + "&comment=" + comment + "&roleid=" + roleid, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}
		logger.info("Method : approveEventManagement ends");
		logger.info("APPROVE" + response);
		return response;
	}

	// reject leave

	@SuppressWarnings("unchecked")
	@GetMapping("event-management-details-reject")
	public @ResponseBody JsonResponse<EventManagementModel> rejectEventManagement(@RequestParam String rejectId,
			String name, String comment) {

		logger.info("Method : rejectEventManagement starts");
		JsonResponse<EventManagementModel> response = new JsonResponse<EventManagementModel>();
		try {
			response = restTemplate.getForObject(env.getMasterUrl() + "rejectEventManagement?id=" + rejectId + "&name="
					+ name + "&comment=" + comment, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}
		logger.info("Method : rejectEventManagement ends");
		logger.info("APPROVE" + response);
		return response;
	}
	// edit

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("event-management-details-edit") public @ResponseBody
	 * JsonResponse<EventManagementModel> editEventManagement(@RequestParam String
	 * id, HttpSession session) {
	 * 
	 * logger.info("Method : editEventManagement starts");
	 * JsonResponse<EventManagementModel> jsonResponse = new
	 * JsonResponse<EventManagementModel>(); logger.info("id====" + id); try {
	 * jsonResponse = restTemplate.getForObject(env.getMasterUrl() +
	 * "editEventManagement?id=" + id, JsonResponse.class); } catch
	 * (RestClientException e) { e.printStackTrace(); } ObjectMapper mapper = new
	 * ObjectMapper(); EventManagementModel eventModel =
	 * mapper.convertValue(jsonResponse.getBody(), new
	 * TypeReference<EventManagementModel>() { }); jsonResponse.setBody(eventModel);
	 * if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
	 * jsonResponse.setCode(jsonResponse.getMessage());
	 * jsonResponse.setMessage("Unsuccess"); } else {
	 * jsonResponse.setMessage("Success"); }
	 * 
	 * 
	 * logger.info("Method : editEventManagement ends");
	 * logger.info("editEventManagement=====" + jsonResponse); return jsonResponse;
	 * }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("event-management-details-edit")
	public @ResponseBody List<EventManagementModel> editEventManagement(HttpSession session,
			@RequestParam String userid) {

		logger.info("Method : editEventManagement starts");
		JsonResponse<List<EventManagementModel>> resp = new JsonResponse<List<EventManagementModel>>();

		// List<String> roleList = new ArrayList<String>();
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		EmpRoleModel empModel = new EmpRoleModel();

		empModel.setUserId(userid);
		// empModel.setUserRole(roleList);
		empModel.setType("WEB");
		empModel.setOrganization(organization);
		empModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "editEventManagement", empModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<EventManagementModel> eventManagementModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<EventManagementModel>>() {
				});
		String dateFormat = "";

		try {
			logger.info("#####" + (String) session.getAttribute("DATEFORMAT"));
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			if (dateFormat == null) {
				dateFormat = "yyyy-MM-dd";
			}

		} catch (Exception e) {
		}
		// logger.info(eventManagementModel.get(0).getAdvanceApplyDate());
		// logger.info(eventManagementModel.get(0).getApprovedDate());

		/*
		 * if (eventManagementModel != null) for (EventManagementModel a :
		 * eventManagementModel) { if (a.getFromDate() != null && a.getFromDate() != "")
		 * { a.setFromDate(DateFormatter.dateFormat(a.getFromDate(), dateFormat)); } if
		 * (a.getToDate() != null && a.getToDate() != "") {
		 * a.setToDate(DateFormatter.dateFormat(a.getToDate(), dateFormat)); } if
		 * (a.getRegdStartDate() != null && a.getRegdStartDate() != "") {
		 * a.setRegdStartDate(DateFormatter.dateFormat(a.getRegdStartDate(),
		 * dateFormat)); } if (a.getRegdEndDate() != null && a.getRegdEndDate() != "") {
		 * a.setRegdEndDate(DateFormatter.dateFormat(a.getRegdEndDate(), dateFormat)); }
		 * }
		 */
		logger.info("EventManagement EDITTTT" + eventManagementModel);
		logger.info("Method : editEventManagement ends");
		return eventManagementModel;
	}

	// delete
	@SuppressWarnings("unchecked")
	@GetMapping("event-management-delete")
	public @ResponseBody JsonResponse<Object> deleteEventManagement(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteEventManagement function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "deleteEventManagement?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteEventManagement function Ends");

		logger.info("deleteEventManagement" + res);
		return res;
	}

	
	@SuppressWarnings("unchecked")
	@GetMapping("event-management-getempname")
	public @ResponseBody JsonResponse<Object> getEVentEmpName(@RequestParam String id,
			 HttpSession session) {

		logger.info("Method : getEVentEmpName starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}

		try {
			String url = env.getMasterUrl() + "rest-event-getempname?id=" + id + "&organization=" + organization + "&orgDivision=" + orgDivision;

			logger.info("Calling REST service: " + url);

			
			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in getEVentEmpName: ", e);
		}

		logger.info("Method : getEVentEmpName ends"+resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "event-management-adddtls")
	public @ResponseBody JsonResponse<Object> addEventDtls(@RequestBody Map<String, Object> itm) {
		
		logger.info("Method : addEventDtls function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String eventId = (String) itm.get("eventId");
		String eventType = (String) itm.get("eventType");
		
		// Convert the received JSON data to a JSON string

		// Rest api call
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-add-newEvent?eventId=" + eventId+"&eventType="+eventType, itm, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addEventDtls function starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("event-management-getview-dtls")
	public @ResponseBody JsonResponse<Object> getEventviewDtls(@RequestParam String id,
			 HttpSession session) {

		logger.info("Method : getEventviewDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
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
			String url = env.getMasterUrl() + "rest-event-viewDtls?id=" + userId + "&organization=" + organization + "&orgDivision=" + orgDivision;

			logger.info("Calling REST service: " + url);

			
			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in getEventviewDtls: ", e);
		}

		logger.info("Method : getEventviewDtls ends"+resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("event-management-edit-list")
	public @ResponseBody Object editCodeAsset(@RequestParam String id, HttpSession session) {
		logger.info("Method :editeventList starts");
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

			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-edit-eventList?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision +"&userId="+userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :editeventList ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "event-management-execution")
	public @ResponseBody JsonResponse<Object> addExecution(@RequestBody Map<String, Object> itm) {
		
		logger.info("Method : addExecution function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String eventId = (String) itm.get("eventId");

		// Rest api call
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addExecution?eventId=" + eventId, itm, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addExecution function starts");
		return resp;
	}
	
	@GetMapping("/event-manage-report")
	public String getEventReport(Model model, HttpSession session) {
		logger.info("Method : getEventReport starts");
	
		logger.info("Method : getEventReport starts");
		return "master/event-report";
	}
	
	// view Training Report

		@SuppressWarnings("unchecked")
		@GetMapping("event-manage-report-view")
		public @ResponseBody Object viewTrainging(@RequestParam String fromDate, @RequestParam String toDate,
				 HttpSession session) {
			logger.info("Method :viewTrainging starts");
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
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewTrainging?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
						+ toDate, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :viewTrainging ends"+resp);
			return resp;
		}
		
		
		
	
		
		@SuppressWarnings("unchecked")
		@GetMapping("event-training-pdf")
		public void getEventTrainingPdf(HttpServletResponse response, Model model, HttpSession session,
		        @RequestParam("eventId") String encodedParam1,@RequestParam("trainingId") String encodedParam2) {

		    logger.info("Method : getEventTrainingPdf starts");
		    String orgName = "";
		    String orgDivision = "";
		    String userId = "";
		    try {
		        orgName = (String) session.getAttribute("ORGANIZATION");
		        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		        userId = (String) session.getAttribute("USER_ID");
		    } catch (Exception e) {
		        logger.error(e.getMessage());
		    }

		    byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		    String eventId = (new String(encodeByte1));
		    
		    byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		    String trainingId = (new String(encodeByte2));

		    System.out.println("JSON===== === " + eventId +" "+trainingId);
		    JsonResponse<Object> resp = new JsonResponse<Object>();

		    try {
		        resp = restTemplate.getForObject(env.getMasterUrl() + "rest-eventTrainingPdf?eventId=" + eventId
		        		+"&trainingId="+trainingId + "&orgName="+ orgName + "&orgDivision=" + orgDivision+"&userId="+userId, JsonResponse.class);
		        System.out.println("JSON Obj === " + resp);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    logger.info("tefeg" + resp);

		    Map<String, Object> data = new HashMap<String, Object>();

		    System.out.println("Get Data === " + resp.getBody().toString());

		    // Parsing the JSON data
		    ObjectMapper objectMapper = new ObjectMapper();
		    try {
		    	JsonNode rootNode = objectMapper.readTree(resp.getBody().toString());
		    	JsonNode trainingDataNode = rootNode.path("trainingData");

		    	// Convert the JSON node into a List of Maps
		    	List<Map<String, Object>> trainingDataList = objectMapper.convertValue(
		    	    trainingDataNode, new TypeReference<List<Map<String, Object>>>() {}
		    	);

		    	// Put the extracted data into the map
		    	data.put("trainingData", trainingDataList);
		    	data.put("orgDivision", orgDivision);

		    	// Print the extracted data for debugging purposes
		    	System.out.println("trainingData: " + trainingDataList);

		            
		        
		      
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
			URL getUrl = null;
			try {
				getUrl = new URL(logo);
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
			data.put("logo", "data:image/png;base64," + encodedLogoUrl);
		    response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "inline; filename=eventPdf.pdf");
		    File file;
		    byte[] fileData = null;
		    try {
		        file = pdfGeneratorUtil.createPdf("master/training-report-pdf.html", data);
		        InputStream in = new FileInputStream(file);
		        fileData = IOUtils.toByteArray(in);
		        response.setContentLength(fileData.length);
		        response.getOutputStream().write(fileData);
		        response.getOutputStream().flush();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (Exception e1) {
		        e1.printStackTrace();
		    }

		    logger.info("Method : getEventTrainingPdf ends");
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("event-management-previous-meetinglist")
		public @ResponseBody Object getPreviousMeetingList(@RequestParam String meetingDate, 
				@RequestParam String type,@RequestParam String fromDate,@RequestParam String toDate,HttpSession session) {
			logger.info("Method :getPreviousMeetingList starts");
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

				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getPrevMeetingList?meetingDate=" + meetingDate+"&type="+type +"&userId="+userId + "&orgName=" + orgName
						+ "&orgDivision=" + orgDivision +"&fromDate="+fromDate +"&toDate="+toDate, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :getPreviousMeetingList ends");
			return resp;
		}
		
		@SuppressWarnings("unchecked")
		@PostMapping("event-management-employeeAutosearch")
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
		@GetMapping("event-meeting-pdf")
		public void getEventMeetingPdf(HttpServletResponse response, Model model, HttpSession session,
		        @RequestParam("eventId") String encodedParam1,@RequestParam("trainingId") String encodedParam2
		        ,@RequestParam("startDate") String encodedParam3) {

		    logger.info("Method : getEventMeetingPdf starts");
		    String orgName = "";
		    String orgDivision = "";
		    String userId = "";
		    try {
		        orgName = (String) session.getAttribute("ORGANIZATION");
		        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		        userId = (String) session.getAttribute("USER_ID");
		    } catch (Exception e) {
		        logger.error(e.getMessage());
		    }

		    byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		    String eventId = (new String(encodeByte1));
		    
		    byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		    String trainingId = (new String(encodeByte2));
		    
		    byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		    String startDate = (new String(encodeByte3));

		    System.out.println("JSON===== === " + eventId +" "+trainingId);
		    JsonResponse<Object> resp = new JsonResponse<Object>();

		    try {
		    	resp = restTemplate.getForObject(env.getMasterUrl() + "rest-eventMeetingPdf?eventId=" + eventId + "&orgName=" + orgName
						+ "&orgDivision=" + orgDivision +"&userId="+userId +"&startDate="+startDate, JsonResponse.class);
		        System.out.println("JSON Obj === " + resp);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    logger.info("tefeg" + resp);

		    Map<String, Object> data = new HashMap<String, Object>();

		    System.out.println("Get Data === " + resp.getBody().toString());

		    // Parsing the JSON data
		    ObjectMapper objectMapper = new ObjectMapper();
		    try {
		    	JsonNode rootNode = objectMapper.readTree(resp.getBody().toString());
		    	System.out.println("rootNode"+rootNode);
		    	//JsonNode trainingDataNode = rootNode.path("dailyData");
		    	 // Navigate to the "dailyData" array
	            JsonNode dailyDataArray = rootNode.path("Event").get(0).path("dailyData");
	            JsonNode PreviousDateData = rootNode.path("Event").get(0).path("PreviousDateData");
	            JsonNode eventData = rootNode.path("Event");

	            System.out.println("eventData"+eventData);
	            System.out.println("dailyDataArray"+dailyDataArray);
		    	// Convert the JSON node into a List of Maps
		    	List<Map<String, Object>> dailyDataArrayList = objectMapper.convertValue(
		    			dailyDataArray, new TypeReference<List<Map<String, Object>>>() {}
		    	);
		    	List<Map<String, Object>> PreviousDateDataList = objectMapper.convertValue(
		    			PreviousDateData, new TypeReference<List<Map<String, Object>>>() {}
		    			);
		    	
		     	List<Map<String, Object>> eventDataList = objectMapper.convertValue(
		     			eventData, new TypeReference<List<Map<String, Object>>>() {}
		    			);
		    	
		    	

		    	// Put the extracted data into the map
		    	data.put("dailyDataArray", dailyDataArrayList);
		    	data.put("PreviousDateData", PreviousDateDataList);
		    	data.put("eventDataList", eventDataList);
		    	data.put("orgDivision", orgDivision);

		    	// Print the extracted data for debugging purposes
		    	System.out.println("dailyDataArrayList: " + dailyDataArrayList);
		    	System.out.println("PreviousDateDataList: " + PreviousDateDataList);
		            
		        
		      
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
			URL getUrl = null;
			try {
				getUrl = new URL(logo);
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
			data.put("logo", "data:image/png;base64," + encodedLogoUrl);
		    response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "inline; filename=eventPdf.pdf");
		    File file;
		    byte[] fileData = null;
		    try {
		        file = pdfGeneratorUtil.createPdf("master/meeting-report-pdf.html", data);
		        InputStream in = new FileInputStream(file);
		        fileData = IOUtils.toByteArray(in);
		        response.setContentLength(fileData.length);
		        response.getOutputStream().write(fileData);
		        response.getOutputStream().flush();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (Exception e1) {
		        e1.printStackTrace();
		    }

		    logger.info("Method : getEventMeetingPdf ends");
		}
		
	
		
		// Approve Event
		@SuppressWarnings("unchecked")
		@GetMapping("event-management-approve")
		public @ResponseBody JsonResponse<Object> approveEvent(@RequestParam String id, Model model,
				HttpSession session) {
			logger.info("Method : approveEvent function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();
			String userId = "";
			String organization = "";
			String orgDivision = "";

			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				res = restTemplate.getForObject(env.getMasterUrl() + "rest-approveEvent?id=" + id
						+"&userId="+userId +"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : approveEvent function Ends");

			logger.info("approveEvent" + res);
			return res;
		}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("event-management-geteventid")
		public @ResponseBody JsonResponse<List<DropDownModel>> getEvenetId(HttpSession session) {
			logger.info("Method : getEvenetId starts");
			JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
			String orgName = "";
			String orgDivision = "";
	       try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
			} catch (Exception e) {

			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "/getEvenetId?orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : getEvenetId ends"+resp);
			return resp;
		}
}
