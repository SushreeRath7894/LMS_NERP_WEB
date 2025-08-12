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

public class KpiDashboardWebController {
	Logger logger = LoggerFactory.getLogger(KpiDashboardWebController.class);
	
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-kpi-first-response-time")
	public @ResponseBody Object kpiResponseTimeData(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :kpiResponseTimeData  starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-kpi-first-response-time?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+"&location="+location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :kpiResponseTimeData   ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-kpi-full-resolution-time")
	public @ResponseBody Object kpiResolutionTimeData(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :kpiResolutionTimeData  starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-kpi-full-resolution-time?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+"&location="+location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :kpiResolutionTimeData   ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-kpi-average-answer-time")
	public @ResponseBody Object kpiAverageAnswerTime(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :kpiAverageAnswerTime  starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-kpi-average-answer-time?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+"&location="+location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :kpiAverageAnswerTime   ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-kpi-ticket-by-type")
	public @ResponseBody Object kpiTicketByType(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :kpiTicketByType  starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-kpi-ticket-by-type?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+"&location="+location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :kpiTicketByType  ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-kpi-ticket-by-category")
	public @ResponseBody Object kpiTicketByCategory(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :kpiTicketByCategory  starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-kpi-ticket-by-category?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+"&location="+location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :kpiTicketByCategory  ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-kpi-call-answer-time")
	public @ResponseBody Object kpiCallAnswerTime(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :kpiCallAnswerTime  starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-kpi-call-answer-time?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+"&location="+location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :kpiCallAnswerTime  ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-kpi-net-promoter-score")
	public @ResponseBody Object kpiNetPromoterScore(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :kpiNetPromoterScore  starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-kpi-net-promoter-score?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+"&location="+location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :kpiNetPromoterScore  ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-kpi-customer-retention")
	public @ResponseBody Object kpiCustomerRetention(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :kpiCustomerRetention  starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-kpi-customer-retention?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+"&location="+location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :kpiCustomerRetention  ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-dashboard-kpi-customer-effort-score")
	public @ResponseBody Object kpiCustomerEffortScore(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate,@RequestParam String organization,@RequestParam String division,@RequestParam String location) {

		logger.info("Method :kpiCustomerEffortScore  starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		 
          try {
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "customer-effort-score?fromDate=" + fromDate + "&toDate=" + toDate + "&organization=" + organization 
					+ "&division=" + division+"&location="+location, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :kpiCustomerEffortScore  ends" + resp);

		return resp;
	}
}
