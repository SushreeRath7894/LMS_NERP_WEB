package nirmalya.aathithya.webmodule.asset.controller;

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
@RequestMapping(value = "asset/")
public class MaintenanceReportWebController {
	Logger logger = LoggerFactory.getLogger(MaintenanceReportWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/sanitary-cleaning")
	public String viewMaintenanceReport(Model model, HttpSession session) {
		logger.info("Method : viewMaintenanceReport starts");
		
		
		 
		logger.info("Method : viewMaintenanceReport ends");
		return "asset/sanitary-cleaning";
	}
	
	@GetMapping("/change-room-report")
	public String viewChangeRoomReport(Model model, HttpSession session) {
		logger.info("Method : viewChangeRoomReport starts");
		
		
		 
		logger.info("Method : viewChangeRoomReport ends");
		return "asset/change-room-report";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("sanitary-cleaning-data")
	public @ResponseBody JsonResponse<Object> getSanitaryCleanData(@RequestParam String fromDate,@RequestParam String toDate, HttpSession session) {

	    logger.info("Method : getSanitaryCleanData starts");

	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    String organization = "";
	    String orgDivision = "";

	    try {
	        organization = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        logger.error("Error retrieving session attributes: ", e);
	    }

	    try {
	        String url = env.getAssetUrl() + "rest-sanitary-clean-data?fromDate=" + fromDate + "&toDate=" + toDate + "&orgName=" + organization + "&orgDiv=" + orgDivision;

	        logger.info("Calling REST service: " + url);

	        resp = restTemplate.getForObject(url, JsonResponse.class);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("Error in getSanitaryCleanData: ", e);
	    }

	    logger.info("Method : getSanitaryCleanData ends");

	    return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("change-room-report-data")
	public @ResponseBody JsonResponse<Object> getChangeRoomData(@RequestParam String fromDate,@RequestParam String toDate, HttpSession session) {

	    logger.info("Method : getChangeRoomData starts");

	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    String organization = "";
	    String orgDivision = "";

	    try {
	        organization = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        logger.error("Error retrieving session attributes: ", e);
	    }

	    try {
	        String url = env.getAssetUrl() + "rest-change-room-data?fromDate=" + fromDate + "&toDate=" + toDate + "&orgName=" + organization + "&orgDiv=" + orgDivision;

	        logger.info("Calling REST service: " + url);

	        resp = restTemplate.getForObject(url, JsonResponse.class);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("Error in getChangeRoomData: ", e);
	    }

	    logger.info("Method : getChangeRoomData ends");

	    return resp;
	}
}
