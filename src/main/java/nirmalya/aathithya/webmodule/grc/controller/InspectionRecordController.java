package nirmalya.aathithya.webmodule.grc.controller;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.grc.model.ScheduledAuditPlanModel;

@Controller
@RequestMapping(value = { "grc/" })
public class InspectionRecordController {

	Logger logger = LoggerFactory.getLogger(InspectionRecordController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restClient;

	@GetMapping(value = { "inspection-record" })
	public String auditRecord(Model model, HttpSession session) {
		logger.info("Method : auditRecord starts");
 
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {

			DropDownModel[] auditeeList = restTemplate.getForObject(
					env.getGrcUrl() + "getAuditeeList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditeeList = Arrays.asList(auditeeList);
			model.addAttribute("AuditeeList", AuditeeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : auditRecord ends");
		return "grc/inspection-record";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("inspection-record-view")
	public @ResponseBody Object viewScheduledPlan(@RequestParam String date,HttpSession session) {
		logger.info("Method :viewScheduledPlan starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		String type = "Record";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(
					env.getGrcUrl() + "rest-inspection-record-view?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&userId=" + userId 
					+ "&type=" + type + "&date=" + date,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewScheduledPlan ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("inspection-record-progressDetails")
	public @ResponseBody Object getScheduledInspectionDetails(@RequestParam String scheduledId,String auditInstId,HttpSession session) {
		logger.info("Method :getScheduledInspectionDetails starts");
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
			resp = restClient.getForObject(
					env.getGrcUrl() + "rest-inspection-record-progressDetails?scheduledId=" + scheduledId +"&auditInstId=" + auditInstId+"&orgName=" + orgName
					+ "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :getScheduledInspectionDetails ends");
		return resp;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "inspection-record-submit-progress" })
	public @ResponseBody JsonResponse<Object> addInspectionProgress( @RequestBody List<ScheduledAuditPlanModel> sapList,
	        HttpSession session) {
	    logger.info("Method : addInspectionProgress function starts");
	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    String userId = "";
	    String organization = "";
	    String orgDivision = "";

	    try {
	        userId = (String) session.getAttribute("USER_ID");
	        organization = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        logger.error("Error retrieving session attributes: ", e);
	    }

	    for (ScheduledAuditPlanModel sap : sapList) {
	        sap.setCreatedBy(userId);
	        sap.setOrgName(organization);
	        sap.setOrgDivision(orgDivision);
	    }

	    try {
	        resp = restTemplate.postForObject(env.getGrcUrl() + "rest-inspection-record-submit-progress", sapList, JsonResponse.class);
	    } catch (RestClientException e) {
	        logger.error("Error posting inspection progress: ", e);
	    }

	    logger.info("Method : addInspectionProgress function Ends");
	    return resp;
	}
}
