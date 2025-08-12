package nirmalya.aathithya.webmodule.master.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.EventManagementModel;

@Controller
@RequestMapping(value = "master")
public class EventApplyController {
	Logger logger = LoggerFactory.getLogger(EventApplyController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping("/event-apply")
	public String getMapApplyEvent(Model model, HttpSession session) {
		logger.info("Method : getMapApplyEvent starts");

		logger.info("Method : getMapApplyEvent starts");
		return "master/event-apply";
	}
	@GetMapping("/event-report")
	public String getMapReportingEvent(Model model, HttpSession session) {
		logger.info("Method : getMapReportingEvent starts");
		
		logger.info("Method : getMapReportingEvent starts");
		return "master/event-report";
	}
	
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("/event-apply-view-all-event") public @ResponseBody
	 * List<EventManagementModel> viewAllEvent(Model model, HttpSession session) {
	 * logger.info("Method : viewAllEvent starts");
	 * JsonResponse<List<EventManagementModel>> resp = new
	 * JsonResponse<List<EventManagementModel>>();
	 * 
	 * 
	 * 
	 * 
	 * try { resp = restTemplate.getForObject(env.getMasterUrl() +
	 * "rest-viewAllEvent", JsonResponse.class); logger.info(); } catch
	 * (RestClientException e) { e.printStackTrace(); } String dateFormat = ""; try
	 * { dateFormat = (String) session.getAttribute("DATEFORMAT"); } catch
	 * (Exception e) { e.printStackTrace(); } for (EventManagementModel a : sample)
	 * { if (a.getFromDate() != null && a.getFromDate() != "") {
	 * a.setFromDate(DateFormatter.dateFormat(a.getFromDate(), dateFormat)); } }
	 * 
	 * logger.info("Method : viewAllEvent starts"); return resp.getBody(); }
	 */
	
	// view

		@SuppressWarnings("unchecked")

		@GetMapping("/event-apply-view-all-event")
		public @ResponseBody List<EventManagementModel> viewAllEvent(HttpSession session) {
			logger.info("Method : viewAllEvent starts");

			JsonResponse<List<EventManagementModel>> resp = new JsonResponse<List<EventManagementModel>>();
			List<EventManagementModel> List = new ArrayList<EventManagementModel>();
			String organization=""; 
			String orgDivision="";
			try {
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewAllEvent?organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
				List = resp.getBody();
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();
			List<EventManagementModel> Model = mapper.convertValue(resp.getBody(),
					new TypeReference<List<EventManagementModel>>() {
					});
			String dateFormat = "";
			try {
				dateFormat = (String) session.getAttribute("DATEFORMAT");
			} catch (Exception e) {

			}
			for (EventManagementModel a : Model) {
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
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method : viewAllEvent  ends");
			logger.info("respwwwwwwwwwwwwwwwweb view" + resp);
			logger.info("Viewwww " + Model);
			return Model;

		}

	
	  @SuppressWarnings("unchecked")
	  
	 @GetMapping("/event-apply-request-event") public @ResponseBody
	 JsonResponse<Object> saveEventRequest(@RequestParam String eventId,Model
	 model, HttpSession session) {
	 logger.info("Method : saveEventRequest starts"); JsonResponse<Object> resp =
	 new JsonResponse<Object> (); String userId= ""; try { userId = (String)
	 session.getAttribute("USER_ID");
	 
	  } catch (Exception e) { 
		  e.printStackTrace(); 
		  }
	  logger.info(env.getMasterUrl() + "rest-saveEventRequest?eventId="+eventId+"&userId="+userId); 
	  try { 
		  resp = restTemplate.getForObject(env.getMasterUrl() + "rest-saveEventRequest?eventId="+eventId+"&userId="+userId,
				  JsonResponse.class);
	  } 
	  catch (RestClientException e) { 
		  e.printStackTrace();
	  } 
	  if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
	  logger.info("Method : saveEventRequest starts"); 
	  logger.info("###########################" +resp);
	  return resp; 
	  }
	 
}