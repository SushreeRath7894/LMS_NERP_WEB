package nirmalya.aathithya.webmodule.master.controller;

import java.util.Arrays;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = "master")
public class EventFeedbackController {

	Logger logger = LoggerFactory.getLogger(EventFeedbackController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("/event-feedback")
	public String getEventFeedBack(Model model, HttpSession session) {
		logger.info("Method : getEventFeedBack starts");
	
		logger.info("Method : getEventFeedBack starts");
		return "master/eventStatus";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("event-feedback-view")
	public @ResponseBody Object getEventFeedback(HttpSession session) {
		logger.info("Method :getEventFeedback starts");
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

			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getEventFeedback?userId=" + userId+"&orgName="+orgName +"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :getEventFeedback ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "event-feedback-save")
	public @ResponseBody JsonResponse<Object> addFeedback(@RequestBody Map<String, Object> itm,HttpSession session) {
		
		logger.info("Method : addFeedback function starts");
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
		String eventId = (String) itm.get("eventId");
		String attendeesId = (String) itm.get("attendeesId");
		String status = (String) itm.get("status");
		String eventType = (String) itm.get("eventType");
		String rating = (String) itm.get("rating");
		
		// Rest api call
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addFeedback?eventId=" + eventId+"&attendeesId="+attendeesId
					+"&userId="+userId +"&orgName="+orgName +"&orgDivision="+orgDivision+"&status="+status+"&eventType="+eventType
					+"&rating="+rating, itm, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addFeedback function starts");
		return resp;
	}
}
