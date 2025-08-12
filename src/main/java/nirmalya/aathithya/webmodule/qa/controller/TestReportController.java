package nirmalya.aathithya.webmodule.qa.controller;

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

@RequestMapping(value = { "production/" })
public class TestReportController {

	Logger logger = LoggerFactory.getLogger(SampleRequestController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "test-report" })

	public String testReport(Model model, HttpSession session) {
		logger.info("Method : testReport starts");

		// String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			// userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] item = restTemplate.getForObject(
					env.getProduction() + "getItemList?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);

			model.addAttribute("itemList", itemList);
			System.out.println("itemList>>>>-----"+itemList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : testReport ends");

		return "qa/test-report";
	}

	// view.

	@SuppressWarnings("unchecked")
	@GetMapping("test-report-view")
	public @ResponseBody Object viewTestReportData(HttpSession session) {

		logger.info("Method :viewTestReportData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		// String Date = DateFormatter.getStringDate(date);
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(
					env.getProduction() + "rest-viewTestReportData?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("viewTestReportData-Report-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewTestReportData ends");

		return resp;
	}

	// view details.

	@SuppressWarnings("unchecked")
	@GetMapping("test-report-dtls-view")
	public @ResponseBody Object viewTestReportDtlsData(@RequestParam String sku, String fdate, String tdate, HttpSession session) {

		logger.info("Method :viewTestReportDtlsData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		// String Date = DateFormatter.getStringDate(date);
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(env.getProduction() + "rest-viewTestReportDtlsData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&sku=" + sku + "&fdate=" + fdate + "&tdate=" + tdate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("viewTestReportDtlsData-Report-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewTestReportDtlsData ends");

		return resp;
	}

}
