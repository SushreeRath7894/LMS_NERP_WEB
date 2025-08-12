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
public class QualityDashboardWebController {
	Logger logger = LoggerFactory.getLogger(QualityDashboardWebController.class);
	
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-quality-gauge-data")
	public @ResponseBody Object qualityGaugeData(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :qualityGaugeData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-quality-gauge-data?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+ "&location=" + location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :qualityGaugeData  ends" );

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-quality-gauge-sub-data")
	public @ResponseBody Object qualityGaugeSubData(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :qualityGaugeSubData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-quality-gauge-sub-data?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+ "&location=" + location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :qualityGaugeSubData  ends" );

		return resp;
	}
	
	 
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-quality-average-resolution-time")
	public @ResponseBody Object qualityAverageResolutionData(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :qualityAverageResolution starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-quality-average-resolution-data?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+ "&location=" + location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :qualityAverageResolution  ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-quality-help-desk")
	public @ResponseBody Object qualityHelpDeskData(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :qualityHelpDeskData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-quality-help-desk-data?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+ "&location=" + location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :qualityHelpDeskData  ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-quality-abandon-rate")
	public @ResponseBody Object qualityAbandonData(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :qualityAbandonData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-quality-abandon-data?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+ "&location=" + location,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :qualityAbandonData  ends");

		return resp;
	}
}
