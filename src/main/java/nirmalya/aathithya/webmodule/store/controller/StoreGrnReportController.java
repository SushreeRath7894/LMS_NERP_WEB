package nirmalya.aathithya.webmodule.store.controller;

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
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;


@Controller
@RequestMapping(value = { "store/" })
public class StoreGrnReportController {

	Logger logger = LoggerFactory.getLogger(StoreGrnReportController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	StoreGrnReportController storeGrnReportController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/store-grnreport" })

	public String manageStoreGrnReport(Model model, HttpSession session) {
		logger.info("Method : manageStoreGrnReport starts");

		logger.info("Method : manageStoreGrnReport ends");
		return "store/store-grnreport";
	}
	
	// purchase_Report_View.

		@SuppressWarnings("unchecked")
		@GetMapping("store-grnreport-materialarrival-view")
		public @ResponseBody Object viewPurchaseMtrlArrvReportData(HttpSession session) {

			logger.info("Method :viewPurchaseMtrlArrvReportData starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			String type = "store";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				resp = restClient.getForObject(env.getMasterUrl() + "rest-viewPurchaseMtrlArrvReportData?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&type=" + type , JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("viewPurchaseMtrlArrvReportData>>>>>----" + resp);
			logger.info("Method :viewPurchaseMtrlArrvReportData ends");

			return resp;
		}

		@SuppressWarnings("unchecked")

		@GetMapping("store-grnreport-filter-view-forMaterialArrival")
		public @ResponseBody Object viewPurchaseReportFilterData(@RequestParam String fdate, String tdate,
				HttpSession session) {

			logger.info("Method :viewPurchaseReportFilterData starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			String type = "store";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {

				resp = restClient.getForObject(env.getMasterUrl() + "rest-viewPurchaseReportFilterData?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&fdate=" + fdate + "&tdate=" + tdate + "&type=" + type , JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Report-Filter-Data>>>>>>--" + resp);
			logger.info("Method :viewPurchaseReportFilterData ends");

			return resp;
		}
		// purchase_Report_View.

		@SuppressWarnings("unchecked")
		@GetMapping("store-grnreport-RMPM-materialArrival-view")
		public @ResponseBody Object viewPurchaseRMPMMtrlArrvReportData(HttpSession session) {

			logger.info("Method :viewPurchaseRMPMMtrlArrvReportData starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			String type = "store";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				resp = restClient.getForObject(env.getMasterUrl() + "rest-viewPurchaseRMPMMtrlArrvReportData?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&type=" + type, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("viewPurchaseRMPMMtrlArrvReportData>>>>>----" + resp);
			logger.info("Method :viewPurchaseRMPMMtrlArrvReportData ends");

			return resp;
		}
		
}
