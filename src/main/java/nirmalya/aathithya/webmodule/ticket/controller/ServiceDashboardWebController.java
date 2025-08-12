package nirmalya.aathithya.webmodule.ticket.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

 

@Controller
@RequestMapping(value = "ticket/")
public class ServiceDashboardWebController {
	Logger logger = LoggerFactory.getLogger(ServiceDashboardWebController.class);
	
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-service-head-data")
	public @ResponseBody Object serviceHeadData(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,String location) {

		logger.info("Method :serviceHeadData  starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-service-head-data?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+ "&location=" + location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :serviceHeadData   ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-service-percentage-call")
	public @ResponseBody Object servicePercentageCall(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,String location) {

		logger.info("Method :servicePercentageCall Data starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-service-percentage-call?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+ "&location=" + location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :servicePercentageCall Data  ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-service-monthly-ticket")
	public @ResponseBody Object serviceMonthlyTicket(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,String location) {

		logger.info("Method :serviceMonthlyTicket Data starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-service-monthly-ticket?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+ "&location=" + location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :serviceMonthlyTicket Data  ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-service-call-response-time")
	public @ResponseBody Object serviceCallResponse(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,String location) {

		logger.info("Method :serviceCallResponse Data starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-service-call-response?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+ "&location=" + location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :serviceCallResponse Data  ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-service-top-performers")
	public @ResponseBody Object serviceTopPerformers(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,String location) {

		logger.info("Method :serviceTopPerformers Data starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-service-top-performers?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+ "&location=" + location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :serviceTopPerformers Data  ends" + resp);

		return resp;
	}
}
