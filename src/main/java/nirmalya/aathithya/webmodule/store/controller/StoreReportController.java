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
public class StoreReportController {
	Logger logger = LoggerFactory.getLogger(StoreReportController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	StoreReportController storeReportController;

	@Autowired
	FileUpload fileUpload;

	@GetMapping(value = { "/store-report" })

	public String manageStoreReport(Model model, HttpSession session) {
		logger.info("Method : manageStoreReport starts");

		logger.info("Method : manageStoreReport ends");
		return "store/store-report";
	}
	
	// Warehouse_Report_View.

		@SuppressWarnings("unchecked")
		@GetMapping("store-report-view")
		public @ResponseBody Object viewStoreReportData(@RequestParam String state, HttpSession session) {

			logger.info("Method :viewStoreReportData starts");
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

				resp = restClient.getForObject(env.getMasterUrl() + "rest-viewStoreReportData?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&state=" + state, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Warehouse-Report-Data>>>>>>>>>>>>>>>>>-----" + resp);
			logger.info("Method :viewStoreReportData ends");

			return resp;
		}
		
		@SuppressWarnings("unchecked")

		@GetMapping("store-report-filter-view")
		public @ResponseBody Object viewStoreReportFilterData(@RequestParam String slno , HttpSession session) {

			logger.info("Method :viewStoreReportFilterData starts");
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

				resp = restClient.getForObject(env.getMasterUrl() + "rest-viewStoreReportFilterData?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&slno=" + slno, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Warehouse-Report-Filter-Data>>>>>>>>>>>>>>>>>-----" + resp);
			logger.info("Method :viewStoreReportFilterData ends");

			return resp;
		}

}
