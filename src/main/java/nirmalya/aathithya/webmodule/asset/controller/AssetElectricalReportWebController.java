package nirmalya.aathithya.webmodule.asset.controller;


import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "asset/")
public class AssetElectricalReportWebController {

	Logger logger = LoggerFactory.getLogger(AssetElectricalReportWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	
	@GetMapping("/emergency-light")
	public String viewReport(Model model, HttpSession session) {
		logger.info("Method : viewxReport starts");
		
		
		 
		logger.info("Method : viewReport ends");
		return "asset/emergency-light";
	}
	
	@GetMapping("/earthing-checklist")
	public String earthingChecklist(Model model, HttpSession session) {
		logger.info("Method : earthingChecklist starts");
		
		
		 
		logger.info("Method : earthingChecklist ends");
		return "asset/earthing-checklist";
	}
	
	@GetMapping("/control-area-record")
	public String controlAreaRecord(Model model, HttpSession session) {
		logger.info("Method : controlAreaRecord starts");
		
		
		 
		logger.info("Method : controlAreaRecord ends");
		return "asset/control-area-record";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("emergency-light-data-get")
	public @ResponseBody JsonResponse<Object> getEmergencyLightData(@RequestParam String selectedMonth,HttpSession session) {

	    logger.info("Method : getEmergencyLightData starts");

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
	        String url = env.getAssetUrl() + "rest-asset-assign-get-emergrncy-light?selectedMonth=" + selectedMonth+"&orgName=" + organization + "&orgDiv=" + orgDivision;

	        logger.info("Calling REST service: " + url);

	        resp = restTemplate.getForObject(url, JsonResponse.class);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("Error in getEmergencyLightData: ", e);
	    }

	    logger.info("Method : getEmergencyLightData ends");

	    return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("earthing-checklist-data-get")
	public @ResponseBody JsonResponse<Object> getEarthingChecklistData(@RequestParam String selectedMonth,  HttpSession session) {

	    logger.info("Method : getEarthingChecklistData starts");

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
	        String url = env.getAssetUrl() + "rest-asset-assign-get-earthing-checklist?selectedMonth=" + selectedMonth + "&orgName=" + organization + "&orgDiv=" + orgDivision;

	        logger.info("Calling REST service: " + url);

	        resp = restTemplate.getForObject(url, JsonResponse.class);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("Error in getEarthingChecklistData: ", e);
	    }

	    logger.info("Method : getEarthingChecklistData ends");

	    return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("control-area-records-data-get")
	public @ResponseBody JsonResponse<Object> getControlAreaData(@RequestParam String fromDate,@RequestParam String toDate, HttpSession session) {

	    logger.info("Method : getControlAreaData starts");

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
	        String url = env.getAssetUrl() + "rest-asset-assign-get-control-area?fromDate=" + fromDate + "&toDate=" + toDate + "&orgName=" + organization + "&orgDiv=" + orgDivision;

	        logger.info("Calling REST service: " + url);

	        resp = restTemplate.getForObject(url, JsonResponse.class);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        logger.error("Error in getControlAreaData: ", e);
	    }

	    logger.info("Method : getControlAreaData ends");

	    return resp;
	}
	
	@GetMapping("electrical-reports")
	public String viewReportElectrocal(Model model, HttpSession session) {
		logger.info("Method : viewReport starts");

		try {

			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			String policyId = "POLICY/2024-25/0003";

			DropDownModel[] policyLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + org + "&orgDivision=" + orgDiv + "&policyId=" + policyId, DropDownModel[].class);
			List<DropDownModel> policyList = Arrays.asList(policyLists);
			model.addAttribute("policyList", policyList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewReport ends");
		return "asset/checkPointOfTransformer";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("asset-assign-reports-gen")
	public @ResponseBody JsonResponse<Object> transformerCheckPoint(HttpSession session, @RequestParam String mon,
			@RequestParam String assetId) {

		logger.info("Method : transformerCheckPoint starts");
		JsonResponse<Object> resp = new JsonResponse<>();

		try {

			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			String url = env.getAssetUrl() + "rest-reports-gen?orgName=" + org + "&orgDivision=" + orgDiv + "&mon="
					+ mon + "&assetId=" + assetId;
			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {

			logger.error("Error occurred in transformerCheckPoint: " + e.getMessage(), e);
			resp.setCode("failed");
			resp.setMessage("Error fetching data: " + e.getMessage());
		}

		logger.info("Method : transformerCheckPoint ends - response: {}", resp);

		return resp;
	}

	// For generator

	@GetMapping("electrical-reports-gen")
	public String viewReportGen(Model model, HttpSession session) {
		logger.info("Method : viewReportGen starts");

		try {

			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			String policyId = "POLICY/2024-25/0007";

			DropDownModel[] policyLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + org + "&orgDivision=" + orgDiv + "&policyId=" + policyId, DropDownModel[].class);
			List<DropDownModel> policyList = Arrays.asList(policyLists);
			model.addAttribute("policyList", policyList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewReportGen ends");
		return "asset/check-disel-gen";
	}

	// asset-assign-reports-disel-gen
	@SuppressWarnings("unchecked")
	@GetMapping("asset-assign-reports-disel-gen")
	public @ResponseBody JsonResponse<Object> diselGenerator(HttpSession session, @RequestParam String mon,
			@RequestParam String assetId) {

		logger.info("Method : diselGenerator starts");
		JsonResponse<Object> resp = new JsonResponse<>();

		try {

			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			String url = env.getAssetUrl() + "reports-disel-gen?orgName=" + org + "&orgDivision=" + orgDiv + "&mon="
					+ mon + "&assetId=" + assetId;
			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {

			logger.error("Error occurred in transformerCheckPoint: " + e.getMessage(), e);
			resp.setCode("failed");
			resp.setMessage("Error fetching data: " + e.getMessage());
		}

		logger.info("Method : diselGenerator ends - response: {}", resp);

		return resp;
	}

	// APFC

	@GetMapping("electrical-reports-apfc")
	public String viewReportapfc(Model model, HttpSession session) {
		logger.info("Method : viewReportapfc starts");

		try {

			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			String policyId = "POLICY/2024-25/0006";

			DropDownModel[] policyLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + org + "&orgDivision=" + orgDiv + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> policyList = Arrays.asList(policyLists);
			model.addAttribute("policyList", policyList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewReportapfc ends");
		return "asset/reports-apfc";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("asset-assign-reports-apfc")
	public @ResponseBody JsonResponse<Object> viewApfcReport(HttpSession session, @RequestParam String mon,
			@RequestParam String assetId) {

		logger.info("Method : viewApfcReport starts");
		JsonResponse<Object> resp = new JsonResponse<>();

		try {

			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String url = env.getAssetUrl() + "rest-reports-apfc?orgName=" + org + "&orgDivision=" + orgDiv + "&mon="
					+ mon + "&assetId=" + assetId;
			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {

			logger.error("Error occurred in viewApfcReport: " + e.getMessage(), e);
			resp.setCode("failed");
			resp.setMessage("Error fetching data: " + e.getMessage());
		}

		logger.info("Method : viewApfcReport ends - response: {}", resp);

		return resp;
	}
	
	@GetMapping("/ups-checklist")
	public String viewUpsCheckList(Model model, HttpSession session) {
		logger.info("Method : viewUpsCheckList starts");
		String organization = "";
	    String orgDivision = "";
	    String policyId="POLICY/2024-25/0004";
	    String userId="";
	    
	    try {
	        organization = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	        userId= (String) session.getAttribute("USER_ID");
	    } catch (Exception e) {
	        logger.error("Error retrieving session attributes: ", e);
	    }
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + organization + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
	                   DropDownModel[].class);
	           List<DropDownModel> assetList = Arrays.asList(assetLists);
	           model.addAttribute("assetListss", assetList);
	       } catch (RestClientException e) {
	           e.printStackTrace();
	       }
		
		logger.info("Method : viewUpsCheckList ends");
		return "asset/ups-checkList";
	}
	
	@GetMapping("/portable-report")
	public String portableToolReport(Model model, HttpSession session) {
		logger.info("Method : portableToolReport starts");
		 
		
		logger.info("Method : portableToolReport ends");
		return "asset/portableTool-report";
	}
	
	@GetMapping("/panel-check-record")
	public String panelCheckRecord(Model model, HttpSession session) {
		logger.info("Method : panelCheckRecord starts");
		 
		
		logger.info("Method : panelCheckRecord ends");
		return "asset/pannel-check-record";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("ups-checklist-get-filter-data")
	public @ResponseBody Object getPolicyFilterData(@RequestParam String month,  HttpSession session) {

	    logger.info("Method : getPolicyFilterData starts");

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
	        String url = env.getAssetUrl() + "rest-filter-policy-data?month=" + month + 
	                     "&orgName=" + organization + 
	                     "&orgDiv=" + orgDivision;

	        logger.info("Calling REST service: " + url);

	        resp = restTemplate.getForObject(url, JsonResponse.class);
	    } catch (Exception e) {
	        logger.error("Error in getPolicyFilterData: ", e);
	    }

	    logger.info("Method : getPolicyFilterData ends");

	    return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("portable-report-get-filter-data")
	public @ResponseBody Object getPortalToolReport(@RequestParam String month, HttpSession session) {

	    logger.info("Method : getPortalToolReport starts");

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
	        String url = env.getAssetUrl() + "rest-filter-portal-data?month=" + month + 
	                     "&orgName=" + organization + 
	                     "&orgDiv=" + orgDivision;

	        logger.info("Calling REST service: " + url);

	        resp = restTemplate.getForObject(url, JsonResponse.class);
	    } catch (Exception e) {
	        logger.error("Error in getPortalToolReport: ", e);
	    }

	    logger.info("Method : getPortalToolReport ends");

	    return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("panel-check-record-filter-data")
	public @ResponseBody Object getPanelCheckRecord(@RequestParam String selectedMonth,
			HttpSession session) {

		logger.info("Method : getPanelCheckRecord starts");

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
			String url = env.getAssetUrl() + "rest-filter-panel-report?selectedMonth=" + selectedMonth + "&orgName=" + organization + "&orgDiv=" + orgDivision;

			logger.info("Calling REST service: " + url);

			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in getPanelCheckRecord: ", e);
		}

		logger.info("Method : getPanelCheckRecord ends");

		return resp;
	}
	
}
