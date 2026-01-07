package nirmalya.aathithya.webmodule.sales.controller;

import java.util.ArrayList;
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
import nirmalya.aathithya.webmodule.sales.model.CustomerGraphModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerStoreGraphModel;

@Controller
@RequestMapping(value = { "sales/" })
public class CustomerDashBoardController {
	Logger logger = LoggerFactory.getLogger(CustomerDashBoardController.class);
	
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping(value = { "/customer-dashboard" })
	public String customerDetails(Model model, HttpSession session) {
		logger.info("Method : customerDetails starts");

		String orgName = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] dropDownModel = restClient
					.getForObject(env.getSalesUrl() + "getOrganization?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> Organization = Arrays.asList(dropDownModel);
			model.addAttribute("Organization", Organization);

			logger.info("Organization" + Organization);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restClient
					.getForObject(env.getSalesUrl() + "getDivision?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> orgDivisionList = Arrays.asList(dropDownModel);
			model.addAttribute("orgDivisionList", orgDivisionList);

			logger.info("orgDivisionList" + orgDivisionList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : customerDetails ends");
		return "sales/customer-dashboard";
	}


	
	

	@GetMapping("/customer-dashboard-graph")
	public @ResponseBody JsonResponse<Object> customerGraph(HttpSession session) {

		logger.info("Method : customerGraph starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			CustomerGraphModel[] auditTypes = restClient.getForObject(env.getSalesUrl() + "customerGraph",
					CustomerGraphModel[].class);
			List<CustomerGraphModel> dataList = Arrays.asList(auditTypes);
			List<Integer> totalInvoice = new ArrayList<Integer>();
			List<Integer> totalQuotation = new ArrayList<Integer>();

			List<String> monthList = new ArrayList<String>();

			for (CustomerGraphModel m : auditTypes) {

				totalInvoice.add(m.getTotalInvoice());
				totalQuotation.add(m.getTotalQuotation());
				monthList.add(m.getMonth());

			}

			dataList.get(0).setMonthList(monthList);

			dataList.get(0).setTotalInvoiceList(totalInvoice);
			dataList.get(0).setTotalQuotationList(totalQuotation);
			resp.setBody(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// logger.info(resp);
		logger.info("Method : customerGraph ends");

		return resp;
	}
	
	
	

	@GetMapping("/customer-dashboard-Store-graph")
	public @ResponseBody JsonResponse<Object> customerStoreGraph(HttpSession session, Model model) {

		logger.info("Method : customerStoreGraph starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			CustomerGraphModel[] auditTypes = restClient.getForObject(env.getSalesUrl() + "customerStoreGraph",
					CustomerGraphModel[].class);
			List<CustomerGraphModel> dataList = Arrays.asList(auditTypes);
			List<Integer> totalInvoice = new ArrayList<Integer>();
			List<Integer> totalQuotation = new ArrayList<Integer>();

			List<String> store = new ArrayList<String>();

			for (CustomerGraphModel m : auditTypes) {

				totalInvoice.add(m.getTotalInvoice());
				totalQuotation.add(m.getTotalQuotation());
				store.add(m.getStore());

			}

			dataList.get(0).setStoreList(store);
			;

			dataList.get(0).setTotalInvoiceList(totalInvoice);
			dataList.get(0).setTotalQuotationList(totalQuotation);
			resp.setBody(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// logger.info(resp);
		logger.info("Method : customerStoreGraph ends");

		return resp;
	}
	
	

	//customer-dashboard-cityList
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "customer-dashboard-cityList" })
	public @ResponseBody JsonResponse<Object> getAllSalesCityList(@RequestParam String id) {
		logger.info("Method : getAllSalesCityList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getSalesUrl() + "getAllSalesCityList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getAllSalesCityList ends");
		return res;
	}
	
	// View dashboard-oprationalHeadData   

		@SuppressWarnings("unchecked")
		@GetMapping("customer-dashboard-oprationalHeadData")
		public @ResponseBody Object dashboardHeadData(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

			logger.info("Method :dashboardHeadData starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			try {

				resp = restClient.getForObject(env.getSalesUrl() + "customer-dashboard-oprationalHeadData?fromDate=" + fromDate
						+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :dashboardHeadData ends" + resp);

			return resp;
		}
	
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-getAllInvoice")
	public @ResponseBody Object getAllInvoice(HttpSession session, @RequestParam String id,@RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {

		logger.info("Method :getAllInvoice starts" + id);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "customer-dashboard-getAllInvoice?id="+id +"&fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllInvoice ends" + resp);

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-performance-count")
	public @ResponseBody Object getAllPerformanceCount(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc,@RequestParam String  searchMode) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "customer-dashboard-performance-count?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc + "&searchMode=" + searchMode, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllPerformanceCount ends" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-performance-weekly-sales-revenue")
	public @ResponseBody Object performanceWeeklySalesRevenue(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc,@RequestParam String  searchMode) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "performanceWeeklySalesRevenue?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc + "&searchMode=" + searchMode, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :performanceWeeklySalesRevenue ends" + resp);
		return resp;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-performance-state-wise-performance")
	public @ResponseBody Object stateWiseSalesPerformance(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc,@RequestParam String  searchMode) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "stateWiseSalesPerformance?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc + "&searchMode=" + searchMode, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :stateWiseSalesPerformance ends" + resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-performance-accumulated-revenue")
	public @ResponseBody Object accumulatedRevenuePerformance(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc,@RequestParam String  searchMode) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "accumulatedRevenuePerformance?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc + "&searchMode=" + searchMode, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :accumulatedRevenuePerformance ends" + resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-performance-average-revenue-per-order")
	public @ResponseBody Object avgRevenuePerOrder(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc,@RequestParam String  searchMode) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "avgRevenuePerOrder?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc + "&searchMode=" + searchMode, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :avgRevenuePerOrder ends" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-performance-life-time-value")
	public @ResponseBody Object customerLifeTimeValue(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc,@RequestParam String  searchMode) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "customerLifeTimeValue?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc + "&searchMode=" + searchMode, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :customerLifeTimeValue ends" + resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-performance-acquisition-cost")
	public @ResponseBody Object acquisitionCostPerformance(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc,@RequestParam String  searchMode) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "acquisitionCostPerformance?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc + "&searchMode=" + searchMode, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :acquisitionCostPerformance ends" + resp);
		return resp;
	}
	
	
	//customer-dashboard-kpi-head-count
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-kpi-head-count")
	public @ResponseBody Object KPIHeadCount(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "KPIHeadCount?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :KPIHeadCount ends" + resp);
		return resp;
	}

	//customer-dashboard-kpi-sales-revenue
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-kpi-sales-revenue")
	public @ResponseBody Object KPISalesRevenueWithCross(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "KPISalesRevenueWithCross?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :KPISalesRevenueWithCross ends" + resp);
		return resp;
	}

	//customer-dashboard-kpi-accumulated-revenue
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-kpi-accumulated-revenue")
	public @ResponseBody Object KPIAccumulatedSalesRevenue(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc, @RequestParam String previousFromDate,@RequestParam String previousToDate) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "KPIAccumulatedSalesRevenue?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc+ "&previousFromDate=" + previousFromDate + "&previousToDate=" + previousToDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :KPIAccumulatedSalesRevenue ends" + resp);
		return resp;
	}

	//customer-dashboard-kpi-incremental-source-revenue
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-kpi-incremental-source-revenue")
	public @ResponseBody Object KPIIncrementalSourceRevenue(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "KPIIncrementalSourceRevenue?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :KPIIncrementalSourceRevenue ends" + resp);
		return resp;
	}
	
	
	//customer-dashboard-conversion-head-count
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-conversion-head-count")
	public @ResponseBody Object conversionSalesHeadCount(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "conversionSalesHeadCount?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :conversionSalesHeadCount ends" + resp);
		return resp;
	}
	
	
	//customer-dashboard-conversion-last-month
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-conversion-last-month")
	public @ResponseBody Object conversionLeadLastMonth(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "conversionLeadLastMonth?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :conversionLeadLastMonth ends" + resp);
		return resp;
	}


	//customer-dashboard-cycle-avg-sales-length-monthly
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-cycle-avg-sales-length-monthly")
	public @ResponseBody Object cycleAvgSalesLengthMonthly(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "cycleAvgSalesLengthMonthly?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cycleAvgSalesLengthMonthly ends" + resp);
		return resp;
	}
	
	
	//customer-dashboard-cycle-funnel-count
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-cycle-funnel-count")
	public @ResponseBody Object cycleFunnelCount(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "cycleFunnelCount?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cycleFunnelCount ends" + resp);
		return resp;
	}
	
	
	//customer-dashboard-cycle-avg-length-sales-count
	@SuppressWarnings("unchecked")
	@GetMapping("customer-dashboard-cycle-avg-length-sales-count")
	public @ResponseBody Object cycleAvgSalesLength(HttpSession session, @RequestParam String fromDate,@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv, @RequestParam String loc) {
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getSalesUrl() + "cycleAvgSalesLength?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv + "&loc=" + loc, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :cycleAvgSalesLength ends" + resp);
		return resp;
	}
	
}
