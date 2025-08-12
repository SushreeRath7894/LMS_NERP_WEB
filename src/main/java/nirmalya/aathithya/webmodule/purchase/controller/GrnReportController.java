package nirmalya.aathithya.webmodule.purchase.controller;

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

@RequestMapping(value = { "purchase/" })
public class GrnReportController {
	
	Logger logger = LoggerFactory.getLogger(GrnReportController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "grn-report" })

	public String grnReport(Model model, HttpSession session) {
		logger.info("Method :grnReport starts");

		logger.info("Method : grnReport ends");

		return "purchase/grn-report";
	}
	
	// View Data.

		@SuppressWarnings("unchecked")

		@GetMapping("grn-report-view")
		public @ResponseBody Object gateReceivedDataView(HttpSession session) {

			logger.info("Method :gateReceivedDataView starts");
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

				resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-grnReportView?orgName=" + orgName
						+ "&orgDivision=" + orgDivision , JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :gateReceivedDataView ends");

			// logger.info(">>>-----"+resp);

			return resp;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("grn-report-view-filtered")
		public @ResponseBody Object grnFilteredView(@RequestParam String fromDate,
				String toDate, String searchData, HttpSession session) {

			logger.info("Method :grnFilteredView starts");
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

				resp = restTemplate.getForObject(env.getPurchaseUrl() + "rest-grn-report-view-filtered?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate + "&searchData=" + searchData, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :grnFilteredView ends");
			return resp;
		}

}
