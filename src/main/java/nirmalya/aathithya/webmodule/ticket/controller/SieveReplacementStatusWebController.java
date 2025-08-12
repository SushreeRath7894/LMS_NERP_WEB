package nirmalya.aathithya.webmodule.ticket.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = "ticket")
public class SieveReplacementStatusWebController {
	Logger logger = LoggerFactory.getLogger(SieveReplacementStatusWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("/sieve-replacement-status")
	public String sieveStatusView(Model model, HttpSession session) {
		logger.info("Method : sieveStatusView starts");
 

		logger.info("Method : sieveStatusView ends");
		return "ticket/sieve-replacement-status";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/sieve-replacement-status-save-data")
	public @ResponseBody JsonResponse<Object> addSieveStatusData(@RequestBody Map<String, Object> sieveStatusJsonData, HttpSession session) {
	    logger.info("Method : addSieveStatusData starts");

	    JsonResponse<Object> resp = new JsonResponse<>();
	    String organization = "";
	    String orgDivision = "";
	    String createdById = "";

	    try {
	        organization = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	        createdById = (String) session.getAttribute("USER_ID");
	    } catch (Exception e) {
	        logger.error("Error retrieving session attributes", e);
	    }

	    try {
	        String url = env.getTicketUrl() + "rest-add-sieve-status";

	        Map<String, Object> requestPayload = new HashMap<>(sieveStatusJsonData);
	        requestPayload.put("orgName", organization);
	        requestPayload.put("orgDiv", orgDivision);
	        requestPayload.put("createdById", createdById);

	        logger.info("Sending sieve status data to the service: " + requestPayload);

	        resp = restTemplate.postForObject(url, requestPayload, JsonResponse.class);
	    } catch (Exception e) {
	        logger.error("Error in addSieveStatusData: ", e);
	    }

	    logger.info("Method : addSieveStatusData ends");
	    return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("sieve-replacement-status-view")
	public @ResponseBody Object sieveStatusView(HttpSession session) {

		logger.info("Method : sieveStatusView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
           
		try {

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-view-sieve-status?orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in sieveStatusView: ", e);
			e.printStackTrace();
		}

		logger.info("Method : sieveStatusView ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("sieve-replacement-status-edit")
	public @ResponseBody Object editSieveStatus(@RequestParam String slNo,HttpSession session) {

		logger.info("Method : editSieveStatus starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
	 
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		 

		} catch (Exception e) {
			e.printStackTrace();
		}
           
		try {

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-edit-sieve-status?orgName=" + organization + "&orgDiv=" + orgDivision +"&slNo="+slNo, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in editSieveStatus: ", e);
			e.printStackTrace();
		}

		logger.info("Method : editSieveStatus ends");

		return resp;
	}
	
	 @SuppressWarnings("unchecked")
	 @GetMapping("sieve-replacement-status-delete")
		public @ResponseBody Object deleteSieveStatus(@RequestParam String serialNo, HttpSession session) {

			logger.info("Method : deleteSieveStatus starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String organization = "";
			String orgDivision = "";

			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				resp = restTemplate.getForObject(env.getTicketUrl() + "rest-delete-sieve-status?serialNo="
						+ serialNo + "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				logger.error("Error in deleteSieveStatus: ", e);
				e.printStackTrace();
			}

			logger.info("Method : deleteSieveStatus ends");

			return resp;
		}
	 
	 @SuppressWarnings("unchecked")
	 @GetMapping("sieve-replacement-status-approve")
		public @ResponseBody Object approveSieveStatus(@RequestParam String serialNo, HttpSession session) {

			logger.info("Method : approveSieveStatus starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String organization = "";
			String orgDivision = "";

			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {
				e.printStackTrace();
			}

			try {

				resp = restTemplate.getForObject(env.getTicketUrl() + "rest-approve-sieve-status?serialNo="
						+ serialNo + "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				logger.error("Error in approveSieveStatus: ", e);
				e.printStackTrace();
			}

			logger.info("Method : approveSieveStatus ends");

			return resp;
		}

}
