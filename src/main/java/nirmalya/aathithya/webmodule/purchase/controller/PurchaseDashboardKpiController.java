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
public class PurchaseDashboardKpiController {

	Logger logger = LoggerFactory.getLogger(PurchaseDashboardKpiController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-kpi-cost-saving")
	public @ResponseBody Object kpiCostSaving1(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :kpiCostSaving1 starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "kpi-cost-saving?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :kpiCostSaving1 ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-department-kpis
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-department-kpis")
	public @ResponseBody Object departmentKpis(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :departmentKpis starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "department-kpis?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :departmentKpis ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-kpi-supplier-performance
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-kpi-supplier-performance")
	public @ResponseBody Object supplierPerformance(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :supplierPerformance starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "supplier-performance?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :supplierPerformance ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-kpi-operational
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-kpi-operational")
	public @ResponseBody Object operationalKPIs(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :operationalKPIs starts ");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "kpi-operational?orgName=" + org + "&orgDivision=" + orgDiv + "&fromDate="
					+ fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :operationalKPIs ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-kpi-spend-under-management
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-kpi-spend-under-management")
	public @ResponseBody Object spendUnderManagement(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :spendUnderManagement starts ");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "kpi-spend-under-management?orgName=" + org + "&orgDivision=" + orgDiv + "&fromDate="
					+ fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :spendUnderManagement ends - response: {}", res);

		return res;
	}

	// procurement-dashboard-kpi-maverick
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-kpi-maverick")
	public @ResponseBody Object kpiMaverick(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :kpiMaverick starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
	
		try {
			String url = env.getPurchaseUrl() + "kpi-maverick?orgName=" + org + "&orgDivision=" + orgDiv + "&fromDate="
					+ fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :kpiMaverick ends - response: {}", res);

		return res;
	}
}
