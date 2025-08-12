package nirmalya.aathithya.webmodule.purchase.controller;

import java.util.Arrays;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "purchase/")
public class PurchaseDashboardController {
	Logger logger = LoggerFactory.getLogger(PurchaseDashboardController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	// view-dashboard

				@GetMapping("/view-dashboard")
				public String viewPurchaseDashboard(Model model, HttpSession session) {
					logger.info("Method : viewPurchaseDashboard start");

					String orgName = "";
					try {
						orgName = (String) session.getAttribute("ORGANIZATION");
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						DropDownModel[] dropDownModel = restTemplate
								.getForObject(env.getPurchaseUrl() + "getOrganization?orgName=" + orgName, DropDownModel[].class);
						List<DropDownModel> Organization = Arrays.asList(dropDownModel);
						model.addAttribute("Organization", Organization);

						logger.info("Organization" + Organization);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					try {
						DropDownModel[] dropDownModel = restTemplate
								.getForObject(env.getPurchaseUrl() + "getDivision?orgName=" + orgName, DropDownModel[].class);
						List<DropDownModel> orgDivisionList = Arrays.asList(dropDownModel);
						model.addAttribute("orgDivisionList", orgDivisionList);

						logger.info("orgDivisionList" + orgDivisionList);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					logger.info("Met    hod : viewPurchaseDashboard end");
					return "purchase/procurement-dashboard";
				}

	// manage-dashboard-getAllHeadCount
				
				//manage-dashboard-po-cityList
				@SuppressWarnings("unchecked")

				@GetMapping(value = { "manage-dashboard-po-cityList" })
				public @ResponseBody JsonResponse<Object> getAllPOCityList(@RequestParam String id) {
					logger.info("Method : getAllPOCityList starts" + id);
					JsonResponse<Object> res = new JsonResponse<Object>();
					try {
						res = restTemplate.getForObject(env.getPurchaseUrl() + "getAllPOCityList?id=" + id, JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (res.getMessage() != null) {
						res.setCode(res.getMessage());
						res.setMessage("Unsuccess");
					} else {
						res.setMessage("success");
					}

					logger.info("Method : getAllPOCityList ends");
					return res;
				}
				

			@SuppressWarnings("unchecked")
			@GetMapping("manage-dashboard-getAllHeadCount")
			public @ResponseBody Object getAllHeadCount(HttpSession session, @RequestParam String fromDate,
					@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
					@RequestParam String loc) {

				logger.info("Method :getAllHeadCount starts");
				JsonResponse<Object> resp = new JsonResponse<Object>();

				try {
					String url = env.getPurchaseUrl() + "getAllHeadCount?orgName=" + org + "&orgDivision=" + orgDiv
							+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
					resp = restTemplate.getForObject(url, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("Method :getAllHeadCount ends" + resp);

				return resp;
			}
			
			// operational of purchase
			@SuppressWarnings("unchecked")
			@GetMapping("manage-dashboard-getAllOperationalRecord")
			public @ResponseBody Object getAllRecordOperational(HttpSession session, @RequestParam String fromDate,
					@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String id,
					@RequestParam String loc) {

				logger.info("Method :getAllRecordOperational starts");
				JsonResponse<Object> resp = new JsonResponse<Object>();

				try {
					String url = env.getPurchaseUrl() + "getAllRecordOperational?orgName=" + org + "&orgDivision=" + orgDiv
							+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&id=" + id + "&loc=" + loc;

					resp = restTemplate.getForObject(url, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("Method :getAllRecordOperational ends" + resp);

				return resp;
			}
			
			@SuppressWarnings("unchecked")
			@GetMapping("view-dashboard-supllier-clasification")
			public @ResponseBody Object SupllierClasification(HttpSession session, @RequestParam String fromDate,
					@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
					@RequestParam String loc) {

				logger.info("Method :CountAvgTime starts");
				JsonResponse<Object> res = new JsonResponse<Object>();

				try {

					String url = env.getPurchaseUrl() + "SupllierClasification?orgName=" + org + "&orgDivision=" + orgDiv
							+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;

					res = restTemplate.getForObject(url, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("Method :SupllierClasification ends" + res);

				return res;
			}

			@SuppressWarnings("unchecked")
			@GetMapping("procurement-dashboard-suppliers")
			public @ResponseBody Object suppliers(HttpSession session, @RequestParam String div ,@RequestParam String org) {

				logger.info("Method :suppliersAllCount starts");
				JsonResponse<Object> res = new JsonResponse<Object>();
				
				try {

					res = restTemplate.getForObject(
							env.getPurchaseUrl() + "dashboardSuppliers?orgName=" + org + "&div=" + div, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("Method :suppliersAllCount ends" + res);

				return res;
			}

			// procurement-dashboard-contracted
			@SuppressWarnings("unchecked")
			@GetMapping("procurement-dashboard-contracted")
			public @ResponseBody Object contractedAllCount(HttpSession session, @RequestParam String div,@RequestParam String org) {

				logger.info("Method :contractedAllCount starts");
				JsonResponse<Object> res = new JsonResponse<Object>();
				
				try {

					res = restTemplate.getForObject(
							env.getPurchaseUrl() + "dashboardContracted?orgName=" + org + "&div=" + div,
							JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("Method :contractedAllCount ends" + res);

				return res;
			}
			// procurement-dashboard-service

			@SuppressWarnings("unchecked")
			@GetMapping("procurement-dashboard-service")
			public @ResponseBody Object serviceAllCount(HttpSession session, @RequestParam String div,@RequestParam String org) {

				logger.info("Method :serviceAllCount starts");
				JsonResponse<Object> res = new JsonResponse<Object>();
				
				try {

					res = restTemplate.getForObject(
							env.getPurchaseUrl() + "dashboardServices?orgName=" + org + "&div=" + div, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("Method :serviceAllCount ends" + res);

				return res;
			}

			
			
			// procurement-dashboard-five-year-trend

			@SuppressWarnings("unchecked")
			@GetMapping("procurement-dashboard-five-year-trend")
			public @ResponseBody Object fiveYearTrend(HttpSession session, @RequestParam String fromDate,
					@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
					@RequestParam String loc) {

				logger.info("Method :fiveYearTrend starts");
				JsonResponse<Object> res = new JsonResponse<Object>();

				try {

					String url = env.getPurchaseUrl() + "fiveYearTrend?orgName=" + org + "&orgDivision=" + orgDiv + "&fromDate="
							+ fromDate + "&toDate=" + toDate + "&loc=" + loc;
					res = restTemplate.getForObject(url, JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("Method :fiveYearTrend ends" + res);

				return res;
			}

			// procurement-dashboard-five-year-trend1
			@SuppressWarnings("unchecked")
			@GetMapping("procurement-dashboard-five-year-trend1")
			public @ResponseBody Object fiveYearTrend1(HttpSession session, @RequestParam String fromDate,
					@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
					@RequestParam String loc) {

				logger.info("Method :fiveYearTrend1 starts");
				JsonResponse<Object> res = new JsonResponse<Object>();

				try {
					String url = env.getPurchaseUrl() + "fiveYearTrend1?orgName=" + org + "&orgDivision=" + orgDiv
							+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
					res = restTemplate.getForObject(url, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("Method :fiveYearTrend1 ends" + res);

				return res;
			}

			// procurement-dashboard-five-year-trend2

			@SuppressWarnings("unchecked")
			@GetMapping("procurement-dashboard-five-year-trend2")
			public @ResponseBody Object fiveYearTrend2(HttpSession session, @RequestParam String fromDate,
					@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
					@RequestParam String loc) {

				logger.info("Method :fiveYearTrend2 starts");
				JsonResponse<Object> res = new JsonResponse<Object>();

				try {
					String url = env.getPurchaseUrl() + "fiveYearTrend2?orgName=" + org + "&orgDivision=" + orgDiv
							+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
					res = restTemplate.getForObject(url, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				logger.info("Method :fiveYearTrend2 ends" + res);

				return res;
			}
			
			
}
