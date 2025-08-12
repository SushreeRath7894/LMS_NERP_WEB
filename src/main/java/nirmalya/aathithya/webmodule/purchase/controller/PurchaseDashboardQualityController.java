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
public class PurchaseDashboardQualityController {
	Logger logger = LoggerFactory.getLogger(PurchaseDashboardQualityController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	// procurement-dashboard-spend-under-management
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-spend-under-management")
	public @ResponseBody Object spendUnderManagement(HttpSession session, @RequestParam String org,
			@RequestParam String orgDiv) {

		logger.info("Method :spendUnderManagement starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {

			res = restTemplate.getForObject(
					env.getPurchaseUrl() + "spend-under-management?orgName=" + org + "&orgDivision=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :spendUnderManagement ends" + res);

		return res;
	}

// procurement-dashboard-return-cost-analysis
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-return-cost-analysis")
	public @ResponseBody Object returnCostAnalysis(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :returnCostAnalysis starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "return-cost-analysis?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :returnostAnalysis ends" + res);

		return res;
	}

// procurement-vendor-count-vendor-month-wise
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-vendor-count-vendor-month-wise")
	public @ResponseBody Object CountVendorMonthWise(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :CountVendorMonthWise starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "count-vendor-month-wise?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :CountVendorMonthWise ends" + res);

		return res;
	}

// procurement-vendor-supplier-quality-rating
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-vendor-supplier-quality-rating")
	public @ResponseBody Object supplierQualityRating(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :supplierQualityRating starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "supplier-quality-rating?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :supplierQualityRating ends" + res);

		return res;
	}

// procurement-dashboard-loss-defect-products
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-loss-defect-products")
	public @ResponseBody Object lossDefectProducts(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :lossDefectProducts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		String url = env.getPurchaseUrl() + "loss-defectProducts?orgName=" + org + "&orgDivision=" + orgDiv
				+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
		res = restTemplate.getForObject(url, JsonResponse.class);

		logger.info("Method :lossDefectProducts ends - response: {}", res);

		return res;
	}

// procurement-dashboard-loss-defect-suppliers
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-loss-defect-suppliers")
	public @ResponseBody Object lossDefectSuppliers(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :lossDefectSuppliers starts ");
		JsonResponse<Object> res = new JsonResponse<Object>();

		String url = env.getPurchaseUrl() + "loss-defect-suppliers?orgName=" + org + "&orgDivision=" + orgDiv
				+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
		res = restTemplate.getForObject(url, JsonResponse.class);
		logger.info("Method :lossDefectSuppliers ends - response: {}", res);

		return res;
	}

// procurement-dashboard-state-wise-loss
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-state-wise-loss")
	public @ResponseBody Object stateWiseLoss(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :stateWiseLoss starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {

			String url = env.getPurchaseUrl() + "state-wise-loss?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :stateWiseLoss ends - response: {}", res);

		return res;
	}

	/* for tabs of summary */

// procurement-dashboard-trend-of-loss
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-trend-of-loss")
	public @ResponseBody Object trendOfLoss(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :trendOfLoss starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "trend-of-loss?orgName=" + org + "&orgDivision=" + orgDiv + "&fromDate="
					+ fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :trendOfLoss ends - response: {}", res);

		return res;
	}

// procurement-dashboard-trend-of-defect
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-trend-of-defect")
	public @ResponseBody Object trendOfDefect(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :trendOfDefect starts ");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "trend-of-defect?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :trendOfDefect ends - response: {}", res);

		return res;
	}

// procurement-dashboard-trend-of-inbound
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-trend-of-inbound")
	public @ResponseBody Object trendOfInbound(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :trendOfInbound");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "trend-of-inbound?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :trendOfInbound ends - response: {}", res);

		return res;
	}

// procurement-dashboard-trend-of-outbound
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-trend-of-outbound")
	public @ResponseBody Object trendOfOutbound(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :trendOfOutbound starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "trend-of-outbound?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :trendOfOutbound ends - response: {}", res);

		return res;
	}

// procurement-dashboard-trend-of-frequency
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-trend-of-frequency")
	public @ResponseBody Object trendOfFrequency(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :trendOfFrequency starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "trend-of-frequency?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :trendOfFrequency ends - response: {}", res);

		return res;
	}

	/* profile tab of quality */

// procurement-dashboard-loss-by-suppliers
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-loss-by-suppliers")
	public @ResponseBody Object lossBySuppliers(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :lossBySuppliers starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "loss-by-suppliers?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :lossBySuppliers ends - response: {}", res);

		return res;
	}

// procurement-dashboard-defect-type-distribution
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-defect-type-distribution")
	public @ResponseBody Object defectTypeDistribution(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :defectTypeDistribution starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "defect-type-distribution?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :defectTypeDistribution ends - response: {}", res);

		return res;
	}

// procurement-dashboard-distribution-issues-remarks
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-distribution-issues-remarks")
	public @ResponseBody Object distributionIssuesRemarks(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :distributionIssuesRemarks starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "distribution-issues-remarks?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :distributionIssuesRemarks ends - response: {}", res);

		return res;
	}

// procurement-dashboard-supplier-summary-product
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-supplier-summary-product")
	public @ResponseBody Object supplierSummary(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :supplierSummary starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "supplier-summary-product?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :supplierSummary ends - response: {}", res);

		return res;
	}

	/* summary tab of quality */
// procurement-dashboard-distribution-defects
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-distribution-defects")
	public @ResponseBody Object defectTypeDistributionInDefects(@RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :defectTypeDistributionInDefects starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "distribution-defects?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :defectTypeDistributionInDefects ends - response: {}", res);

		return res;
	}

// procurement-dashboard-defect-rate-trends
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-defect-rate-trends")
	public @ResponseBody Object defectRateTrends(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :defectRateTrends starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			String url = env.getPurchaseUrl() + "defect-rate-trends?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :defectRateTrends ends - response: {}", res);

		return res;
	}

// procurement-dashboard-defect-type-distribution-table
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-defect-type-distribution-table")
	public @ResponseBody Object defectDistTable(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :defectDistTable starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "distribution-table?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :defectDistTable ends - response: {}", res);

		return res;
	}

// procurement-dashboard-bottom-suppliers-defect
	@SuppressWarnings("unchecked")
	@GetMapping("procurement-dashboard-bottom-suppliers-defect")
	public @ResponseBody Object bottomSuppliersDefect(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :bottomSuppliersDefect starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			String url = env.getPurchaseUrl() + "bottom-suppliers-defect?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			res = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :bottomSuppliersDefect ends - response: {}", res);

		return res;
	}
}
