/*@Author: Pankaj Kumar*/
package nirmalya.aathithya.webmodule.pipeline.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

@Controller
@RequestMapping(value = "pipeline")
public class CrmReportsController {

	Logger logger = LoggerFactory.getLogger(CrmReportsController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/crm-reports")
	public String viewAllReports(Model model, HttpSession session) {
		logger.info("Method : viewAllReports Strat");

		// LeadStatus
		try {
			logger.info(env.getPipeline());
			DropDownModel[] status = restTemplate.getForObject(env.getPipeline() + "/getLeadStatusList",
					DropDownModel[].class);

			List<DropDownModel> statusList = Arrays.asList(status);
			model.addAttribute("statusList", statusList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// LeadSource

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getLeadList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("leadList", sourceList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			String org = "";
			String orgDiv = "";
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			DropDownModel[] accountName = restTemplate.getForObject(env.getPipeline() + "/getAccountNameList?userId="+userId+"&org="+org+"&orgDiv="+orgDiv,
					DropDownModel[].class);

			List<DropDownModel> accountNameList = Arrays.asList(accountName);
			
			model.addAttribute("accountNameList", accountNameList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] stage = restTemplate.getForObject(env.getPipeline() + "/getDealStageList",
					DropDownModel[].class);

			List<DropDownModel> stageList = Arrays.asList(stage);
			model.addAttribute("stageList", stageList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewAllReports Ends");
		return "pipeline/crm-all-reports";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/crm-leads-data")
	public @ResponseBody JsonResponse getLeads(@RequestBody Map<String, Object> filterObj,
	                                           @RequestParam String fromDate,
	                                           @RequestParam String toDate,
	                                           HttpSession session) {
	    logger.info("Method : Leads_Reports_Data starts");
	    JsonResponse jsonResponse = new JsonResponse();

	    try {
	    	String userId = "";
	        String orgName = "";
	        String orgDivision = "";
	        try {
	        	userId = (String) session.getAttribute("USER_ID");
	            orgName = (String) session.getAttribute("ORGANIZATION");
	            orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        jsonResponse = restTemplate.postForObject(
	            env.getPipeline() + "getLeadsData?" +
	            "userId=" + userId +
	            "&orgName=" + orgName + "&orgDivision=" + orgDivision +
	            "&fromDate=" + fromDate + "&toDate=" + toDate, filterObj,
	            JsonResponse.class
	        );
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    logger.info("Method : Leads_Reports_Data Ends");
	    return jsonResponse;
	}


}
