package nirmalya.aathithya.webmodule.purchase.controller;

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
@RequestMapping(value = "purchase/")
public class PurchaseDashboardStockAnalysisController {

	Logger logger = LoggerFactory.getLogger(PurchaseDashboardStockAnalysisController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	// procurement-dashboard-analysis-head-data
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-analysis-head-data")
	public @ResponseBody Object analysisHeadData(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :analysisHeadData starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "analysis-head-data?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :analysisHeadData ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-analysis-stock-head-data
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-analysis-stock-head-data")
	public @ResponseBody Object stockHeadData(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :stockHeadData starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "analysis-stock-head-data?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :stockHeadData ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-analysis-most-view
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-analysis-most-viewed")
	public @ResponseBody Object mostViewed(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :mostViewed starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "analysis-most-view?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :mostViewed ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-analysis-least-viewed
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-analysis-least-viewed")
	public @ResponseBody Object leastViewed(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :leastViewed starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		try {
			String url = env.getPurchaseUrl() + "analysis-least-view?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :leastViewed ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-analysis-highest-inventory
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-analysis-highest-inventory")
	public @ResponseBody Object highInventory(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :highInventory starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		try {
			String url = env.getPurchaseUrl() + "analysis-highest-inventory?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :highInventory ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-prideced-day-outofstock

	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-prideced-day-outofstock")
	public @ResponseBody Object pridectedDayOutOfStock(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :pridectedDayOutOfStock starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		try {
			String url = env.getPurchaseUrl() + "prideced-day-outofstock?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :pridectedDayOutOfStock ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-analysis-bottom-sales
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-analysis-bottom-sales")
	public @ResponseBody Object bottomSales(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :bottomSales starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
	
		
		try {
			String url = env.getPurchaseUrl() + "analysis-bottom-sales?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :bottomSales ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-top-sales-running-outofstock
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-top-sales-running-outofstock")
	public @ResponseBody Object topRunningOutOfStock(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :topRunningOutOfStock starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		try {
			String url = env.getPurchaseUrl() + "top-sales-running-outofstock?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :topRunningOutOfStock ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-top-sales-outofstock
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-top-sales-outofstock")
	public @ResponseBody Object topSalesOutOfStock(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :topSalesOutOfStock starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
	
		try {
			String url = env.getPurchaseUrl() + "top-sales-outofstock?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :topSalesOutOfStock ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-simulated-day-outofstock
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-simulated-day-outofstock")
	public @ResponseBody Object simulatedDaysOutOfStock(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :simulatedDaysOutOfStock starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		try {
			String url = env.getPurchaseUrl() + "simulated-day-outofstock?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :simulatedDaysOutOfStock ends - response: {}", res);

		return res;
	}
}
