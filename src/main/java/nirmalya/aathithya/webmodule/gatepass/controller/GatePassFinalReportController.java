package nirmalya.aathithya.webmodule.gatepass.controller;



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
@RequestMapping(value = "gatepass/")
public class GatePassFinalReportController {
	
	Logger logger = LoggerFactory.getLogger(GatePassFinalReportController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	
	
	@GetMapping("/gatepass-report")
	public String gatePassFinalReport(Model model, HttpSession session) {
		logger.info("Method : gatePassFinalReport add starts");
		
		

		logger.info("Method : gatePassFinalReport ends");
		return "gatepass/final-report";
	}
	
	
	// Gate_Pass_Report_View.

		@SuppressWarnings("unchecked")
		@GetMapping("gatepass-report-view")
		public @ResponseBody Object viewGatePassFinalReportData(@RequestParam String sec, String fromdate, String todate, String type, HttpSession session) {

			logger.info("Method :viewGatePassFinalReportData starts");
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

				resp = restClient.getForObject(env.getGatepassUrl() + "rest-viewGatePassFinalReportData?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&sec=" + sec + "&fromdate=" + fromdate + "&todate=" + todate + "&type=" + type , JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewGatePassFinalReportData ends");
			return resp;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("gatepass-report-view-dload")
		public @ResponseBody Object viewGatePassFinalReportDataDload(@RequestParam String sec, String fromdate, String todate, String type, HttpSession session) {

			logger.info("Method :viewGatePassFinalReportDataDload starts");
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

				resp = restClient.getForObject(env.getGatepassUrl() + "rest-viewGatePassFinalReportDataDload?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&sec=" + sec + "&fromdate=" + fromdate + "&todate=" + todate + "&type=" + type , JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :viewGatePassFinalReportDataDload ends");

			return resp;
		}
		
		// Gate_Pass_Report_Entry_Dtls.

				@SuppressWarnings("unchecked")
				@GetMapping("gatepass-report-gateEntryDetails")
				public @ResponseBody Object viewGatePassFinalReportEntryDtls(@RequestParam String id, HttpSession session) {

					logger.info("Method :viewGatePassFinalReportEntryDtls starts");
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

						resp = restClient.getForObject(env.getGatepassUrl() + "rest-viewGatePassFinalReportEntryDtls?orgName=" + orgName
								+ "&orgDivision=" + orgDivision + "&id=" + id  , JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("Method :viewGatePassFinalReportEntryDtls ends");

					return resp;
				}
				
				
				// Gate_Pass_Report_Out_Dtls.

				@SuppressWarnings("unchecked")
				@GetMapping("gatepass-report-gateOutDetails")
				public @ResponseBody Object viewGatePassFinalReportOutDtls(@RequestParam String id, HttpSession session) {

					logger.info("Method :viewGatePassFinalReportOutDtls starts");
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

						resp = restClient.getForObject(env.getGatepassUrl() + "rest-gateOutDetails?orgName=" + orgName
								+ "&orgDivision=" + orgDivision + "&id=" + id  , JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("Method :viewGatePassFinalReportOutDtls ends");

					return resp;
				}

}
