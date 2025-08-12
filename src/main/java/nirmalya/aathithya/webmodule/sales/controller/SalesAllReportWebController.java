package nirmalya.aathithya.webmodule.sales.controller;

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
	@RequestMapping(value = { "sales/" })
	public class SalesAllReportWebController {
		Logger logger = LoggerFactory.getLogger(DeliveryChallanController.class);

		@Autowired
		RestTemplate restTemplate;

		@Autowired
		EnvironmentVaribles env;

		@Autowired
		SalesAllReportWebController salesAllReportWebController;

		

		@GetMapping(value = { "sales-report" })
		public String customerDetails(Model model, HttpSession session) {
			logger.info("Method : customerDetails starts");
			String organization = "";
			String orgDivision = "";
			try {

				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			try {
				DropDownModel[] employeeList = restTemplate.getForObject(env.getSalesUrl() + "getSaleExecutivesList?org="+organization+"&orgDiv="+orgDivision,
						DropDownModel[].class);
				List<DropDownModel> empList = Arrays.asList(employeeList);
				model.addAttribute("empList", empList);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : customerDetails ends");
			return "sales/sales-report";
		}
// SALES REPORT LIST
		
		@SuppressWarnings("unchecked")
		@GetMapping("sales-report-list")
		public @ResponseBody Object viewReportList(HttpSession session) {

			logger.info("Method :viewReportList starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewReportList?orgName=" + orgName
						+ "&orgDivision=" + orgDivision , JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :viewReportList ends");

			return resp;
		}
// Sales Report View		
		@SuppressWarnings("unchecked")
		@GetMapping("sales-report-view")
		public @ResponseBody Object viewsalesReport(@RequestParam String fDate,@RequestParam String tDate,@RequestParam String empId, 
				HttpSession session) {

			logger.info("Method :viewsalesReport starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesReport?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&fDate=" + fDate + "&tDate=" + tDate + "&empId=" + empId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :viewsalesReport ends");
			return resp;
		}
//
		@SuppressWarnings("unchecked")
		@GetMapping("sales-report-viewPO")
		public @ResponseBody Object viewsalesPOReport(@RequestParam String fDate,@RequestParam String tDate,@RequestParam String empId,
				HttpSession session) {

			logger.info("Method :viewsalesPOReport starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesPOReport?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&fDate=" + fDate + "&tDate=" + tDate + "&empId=" + empId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :viewsalesPOReport ends");
			return resp;
		}
//
		@SuppressWarnings("unchecked")
		@GetMapping("sales-report-viewSO")
		public @ResponseBody Object viewsalesSOReport(@RequestParam String fDate,@RequestParam String tDate,@RequestParam String empId, 
				HttpSession session) {

			logger.info("Method :viewsalesSOReport starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewsalesSOReport?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&fDate=" + fDate + "&tDate=" + tDate + "&empId=" + empId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :viewsalesSOReport ends");
			return resp;
		}
}
