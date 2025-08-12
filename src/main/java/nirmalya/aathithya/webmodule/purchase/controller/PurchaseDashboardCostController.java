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
public class PurchaseDashboardCostController {
	Logger logger = LoggerFactory.getLogger(PurchaseDashboardCostController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	// procurement-dashboard-procurement-roi
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-procurement-roi")
	public @ResponseBody Object procurementROI(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :procurementROI starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "procurement-roi?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :procurementROI ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-cost-fiveyear-trend
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-cost-fiveyear-trend")
	public @ResponseBody Object costFiveYearTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :costFiveYearTrend starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "cost-fiveyear-trend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :costFiveYearTrend ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-cost-fiveyear-trend1
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-cost-fiveyear-trend1")
	public @ResponseBody Object costFiveYearTrend1(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :costFiveYearTrend1 starts - fromDate:");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "cost-fiveyear-trend1?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :costFiveYearTrend1 ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-cost-saving-fiveyear-trend
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-cost-saving-fiveyear-trend")
	public @ResponseBody Object costSavingFiveYearTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :costSavingFiveYearTrend starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "cost-saving-fiveyear-trend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :costSavingFiveYearTrend ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-cost-avoidance-fiveyear-trend
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-cost-avoidance-fiveyear-trend")
	public @ResponseBody Object costAvoidanceFiveYearTrend(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :costAvoidanceFiveYearTrend starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "cost-avoidance-fiveyear-trend?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :costAvoidanceFiveYearTrend ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-cost-fiveyear-trend2
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-cost-fiveyear-trend2")
	public @ResponseBody Object costFiveYearTrend2(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :costFiveYearTrend2 starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "cost-fiveyear-trend2?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :costFiveYearTrend2 ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-cost-reduction-supplier

	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-cost-reduction-supplier")
	public @ResponseBody Object costReductionBySupplier(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :costReductionBySupplier starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "cost-reduction-supplier?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :costReductionBySupplier ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-cost-saving
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-cost-saving")
	public @ResponseBody Object costSavings(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :costSavings starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "cost-saving?orgName=" + org + "&orgDivision=" + orgDiv + "&fromDate="
					+ fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :costSavings ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-cost-avoidance
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-cost-avoidance")
	public @ResponseBody Object costAvoidance(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :costAvoidance starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "cost-avoidance?orgName=" + org + "&orgDivision=" + orgDiv + "&fromDate="
					+ fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :costAvoidance ends - response: {}", res);

		return res;
	}
}
