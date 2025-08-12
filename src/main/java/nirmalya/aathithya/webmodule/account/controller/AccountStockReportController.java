package nirmalya.aathithya.webmodule.account.controller;

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
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;


@Controller
@RequestMapping(value = "account")
public class AccountStockReportController {
	Logger logger = LoggerFactory.getLogger(AccountStockReportController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/stock-report")
	public String viewStockReport(Model model, HttpSession session) {
		logger.info("Method : viewStockReport starts");
		logger.info("Method : viewStockReport end");
		return "account/stock-report";
	}
	// viewRMPMStockReport.
		@SuppressWarnings("unchecked")
		@GetMapping("/stock-report-rmpm-view")
		public @ResponseBody Object viewRMPMStockReport(HttpSession session) {
			logger.info("Method :viewRMPMStockReport starts");
			
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			String type = "inventory";
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
			logger.info("Method :viewRMPMStockReport ends");
			return resp;
		}
	//viewFgStockReport	
		@SuppressWarnings("unchecked")
		@GetMapping("/stock-report-fg-view")
		public @ResponseBody Object viewFgStockReport(HttpSession session) {
			logger.info("Method :viewFgStockReport starts");
			
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
				resp = restClient.getForObject(env.getMasterUrl() + "rest-viewPurchaseFGMtrlArrvReportData?orgName=" + orgName
						+ "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewFgStockReport ends");
			return resp;
		}
	//viewStoreStockReport	
		@SuppressWarnings("unchecked")
		@GetMapping("/stock-report-store-view")
		public @ResponseBody Object viewStoreStockReport(HttpSession session) {
			logger.info("Method :viewStoreStockReport starts");
			
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
			logger.info("Method :viewStoreStockReport ends");
			return resp;
		}
		//stock-report-view	
		@SuppressWarnings("unchecked")
		@GetMapping("/stock-report-view")
		public @ResponseBody Object viewStockReport(HttpSession session,@RequestParam String type) {
			logger.info("Method :viewStockReport starts");
			
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
				resp = restClient.getForObject(env.getMasterUrl() + "rest-stock-report-view?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&type=" + type, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewStockReport ends");
			return resp;
		}
}
