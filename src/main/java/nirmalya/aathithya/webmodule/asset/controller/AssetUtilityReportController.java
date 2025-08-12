package nirmalya.aathithya.webmodule.asset.controller;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "asset/")
public class AssetUtilityReportController {

	Logger logger = LoggerFactory.getLogger(AssetUtilityReportController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/hvac-monitoring")
	public String getHvacMonitoring(Model model, HttpSession session) {
		logger.info("Method : getHvacMonitoring starts");

		logger.info("Method : getHvacMonitoring ends");
		return "asset/hvacMonitoring";
	}

	// view Cage Rope

	@SuppressWarnings("unchecked")
	@GetMapping("hvac-monitoring-view")
	public @ResponseBody Object viewHvacMonitoring(@RequestParam String fromDate, @RequestParam String toDate,
			HttpSession session) {
		logger.info("Method :viewCageRope starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		//String policyId = "PPID/2024-25/0003";
		String policyId = "POLICY/2024-25/0048";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewHvacMonitoring?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&fromDate=" + fromDate + "&toDate="
					+ toDate + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewHvacMonitoring ends");
		return resp;
	}

	@GetMapping("/air-compreessor")
	public String airCompreessorView(Model model, HttpSession session) {
		logger.info("Method : airCompreessorView starts");
		String organization = "";
		String orgDivision = "";
		String policyId = "POLICY/2024-25/0036";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + organization + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : airCompreessorView ends");
		return "asset/air-compreessor";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("air-compreessor-filter-report")
	public @ResponseBody JsonResponse<Object> getAirCompreessor(@RequestParam String month,
			@RequestParam String assetId, HttpSession session) {

		logger.info("Method : getAirCompreessor starts");

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
			String url = env.getAssetUrl() + "rest-filter-air-compreessor?month=" + month + "&assetId=" + assetId
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision;

			logger.info("Calling REST service: " + url);

			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in getAirCompreessor: ", e);
		}

		logger.info("Method : getAirCompreessor ends");

		return resp;
	}

	@GetMapping("/annexture-filter-review")
	public String annextureFilterReview(Model model, HttpSession session) {
		logger.info("Method : annextureFilterReview starts");

		logger.info("Method : annextureFilterReview ends");
		return "asset/annexture-filter-review";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("annexture-filter-review-data-get")
	public @ResponseBody JsonResponse<Object> getAnnextureFilterData(@RequestParam String yearName,
			@RequestParam String fromDate,@RequestParam String toDate, HttpSession session) {

		logger.info("Method : getAnnextureFilterData starts");

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
			String url = env.getAssetUrl() + "rest-asset-assign-get-annexture-filter?yearName=" + yearName
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&orgName=" + organization + "&orgDiv=" + orgDivision;

			logger.info("Calling REST service: " + url);

			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getControlAreaData: ", e);
		}

		logger.info("Method : getAnnextureFilterData ends");

		return resp;
	}

	@GetMapping("/filter-cleaning-checklist")
	public String filterCleaningView(Model model, HttpSession session) {
		logger.info("Method : filterCleaningView starts");
		String organization = "";
		String orgDivision = "";
		String policyId = "POLICY/2024-25/0036";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + organization + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : filterCleaningView ends");
		return "asset/filter-cleaning-checklist";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("filter-cleaning-checklist-view")
	public @ResponseBody JsonResponse<Object> getfilterCleaningView(@RequestParam String month,
			@RequestParam String assetId, HttpSession session) {

		logger.info("Method : getfilterCleaningView starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String policyId = "POLICY/2024-25/0030";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}

		try {
			String url = env.getAssetUrl() + "rest-filter-cleaning-checklist?month=" + month + "&assetId=" + assetId
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&policyId=" + policyId;

			logger.info("Calling REST service: " + url);

			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in getfilterCleaningView: ", e);
		}

		logger.info("Method : getfilterCleaningView ends");

		return resp;
	}

	@GetMapping("/ahuair-filter-cleaning")
	public String ahuairCleaningView(Model model, HttpSession session) {
		logger.info("Method : ahuairCleaningView starts");
		String organization = "";
		String orgDivision = "";
		String policyId = "POLICY/2024-25/0036";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + organization + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : ahuairCleaningView ends");
		return "asset/ahu-filter-cleaning";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("ahuair-filter-cleaning-view")
	public @ResponseBody JsonResponse<Object> getAhufilterCleaningView(@RequestParam String month,
			@RequestParam String assetId, HttpSession session) {

		logger.info("Method : getAhufilterCleaningView starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String policyId = "POLICY/2024-25/0027";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}

		try {
			String url = env.getAssetUrl() + "rest-filter-cleaning-checklist?month=" + month + "&assetId=" + assetId
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&policyId=" + policyId;

			logger.info("Calling REST service: " + url);

			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in getAhufilterCleaningView: ", e);
		}

		logger.info("Method : getAhufilterCleaningView ends");

		return resp;
	}

	@GetMapping("/ahu-fine-filter-cleaning")
	public String ahufineCleaningView(Model model, HttpSession session) {
		logger.info("Method : ahufineCleaningView starts");
		String organization = "";
		String orgDivision = "";
		String policyId = "POLICY/2024-25/0036";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + organization + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : ahufineCleaningView ends");
		return "asset/ahu-fine-filter-cleaning";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("ahu-fine-filter-cleaning-view")
	public @ResponseBody JsonResponse<Object> getAhuFinefilterCleaningView(@RequestParam String month,
			@RequestParam String assetId, HttpSession session) {

		logger.info("Method : getAhuFinefilterCleaningView starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String policyId = "POLICY/2024-25/0049";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}

		try {
			String url = env.getAssetUrl() + "rest-filter-cleaning-checklist?month=" + month + "&assetId=" + assetId
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&policyId=" + policyId;

			logger.info("Calling REST service: " + url);

			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in getAhuFinefilterCleaningView: ", e);
		}

		logger.info("Method : getAhuFinefilterCleaningView ends");

		return resp;
	}

	@GetMapping("/fdvSuction-filter-cleaning")
	public String fdvSuctionCleaningView(Model model, HttpSession session) {
		logger.info("Method : fdvSuctionCleaningView starts");
		String organization = "";
		String orgDivision = "";
		String policyId = "POLICY/2024-25/0036";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}
		try {
			DropDownModel[] assetLists = restClient.getForObject(env.getAssetUrl() + "rest-getAssetLists?userId="
					+ userId + "&orgName=" + organization + "&orgDivision=" + orgDivision + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(assetLists);
			model.addAttribute("assetList", assetList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : fdvSuctionCleaningView ends");
		return "asset/fdv-seduction-cleaning";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("fdvSuction-filter-cleaning-view")
	public @ResponseBody JsonResponse<Object> getfdvSuctionfilterCleaningView(@RequestParam String month,
			@RequestParam String assetId, HttpSession session) {

		logger.info("Method : getfdvSuctionfilterCleaningView starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String policyId = "POLICY/2024-25/0028";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}

		try {
			String url = env.getAssetUrl() + "rest-filter-cleaning-checklist?month=" + month + "&assetId=" + assetId
					+ "&orgName=" + organization + "&orgDiv=" + orgDivision + "&policyId=" + policyId;

			logger.info("Calling REST service: " + url);

			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in getfdvSuctionfilterCleaningView: ", e);
		}

		logger.info("Method : getfdvSuctionfilterCleaningView ends");

		return resp;
	}

	@GetMapping("/ahu-ductCleaning")
	public String getAhuDutCleaning(Model model, HttpSession session) {
		logger.info("Method : airCompreessorView starts");

		logger.info("Method : airCompreessorView ends");
		return "asset/ahu-dutCleaning";
	}

	// View Ahu Dut Cleaning

	@SuppressWarnings("unchecked")
	@GetMapping("ahu-ductCleaning-view")
	public @ResponseBody Object viewAhuDutCleaning(@RequestParam String month, @RequestParam String currentYear,
			HttpSession session) {
		logger.info("Method :viewAhuDutCleaning starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getAssetUrl() + "rest-viewAhuDutCleaning?orgName=" + orgName + "&orgDivision=" + orgDivision
							+ "&userId=" + userId + "&month=" + month + "&currentYear=" + currentYear,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewAhuDutCleaning ends" + resp);
		return resp;
	}

	@GetMapping("/log-sheetforChiller")
	public String getLogSheetforChiller(Model model, HttpSession session) {
		logger.info("Method : getLogSheetforChiller starts");

		logger.info("Method : getLogSheetforChiller ends");
		return "asset/log-sheetChiller";
	}

	// View Chiller1

	@SuppressWarnings("unchecked")
	@GetMapping("log-sheetforChiller-viewChiller1")
	public @ResponseBody Object viewChiller1(@RequestParam String date, HttpSession session) {
		logger.info("Method :viewChiller1 starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-viewChiller1?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&date=" + date, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewChiller1 ends" + resp);
		return resp;
	}

	@GetMapping("/air-compressor")
	public String getAirCompressor(Model model, HttpSession session) {
		logger.info("Method : getAirCompressor starts");

		try {

			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			String policyId = "POLICY/2024-25/0030";

			DropDownModel[] typeLiists = restClient.getForObject(env.getAssetUrl() + "rest-getAirCompTypList?userId="
					+ userId + "&orgName=" + org + "&orgDivision=" + orgDiv + "&policyId=" + policyId,
					DropDownModel[].class);
			List<DropDownModel> typeLiist = Arrays.asList(typeLiists);
			model.addAttribute("typeLiist", typeLiist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getAirCompressor ends");
		return "asset/air-compressor-dryer.html";
	}

	// get-aircompressor-dryer-data
	@SuppressWarnings("unchecked")
	@GetMapping("air-compressor-dry")
	public @ResponseBody Object vewAirCompDryData(@RequestParam String date, @RequestParam String type,
			HttpSession session) {
		logger.info("Method :vewAirCompDryData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "POLICY/2024-25/0026";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-view-aircomDry?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&date=" + date + "&type="
					+ type + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :vewAirCompDryData ends");
		return resp;
	}
	
	//rh-temprature
	@GetMapping("/rh-temprature")
	public String getRHTemprature(Model model, HttpSession session) {
		logger.info("Method : getRHTemprature starts");

		
		logger.info("Method : getRHTemprature ends");
		return "asset/rh-temprature.html";
	}
	
	//rh-temp-data
	
	@SuppressWarnings("unchecked")
	@GetMapping("rh-temp-data")
	public @ResponseBody Object getRhTempData(@RequestParam String date, @RequestParam String type,
			HttpSession session) {
		logger.info("Method :getRhTempData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		String policyId = "PPID/2024-25/0009";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getAssetUrl() + "rest-view-rhTemp?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&date=" + date + "&type="
					+ type + "&policyId=" + policyId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :getRhTempData ends");
		return resp;
	}
// RH & TEMPERATURE
	
	@GetMapping("/rh-temp")
	public String getRhData(Model model, HttpSession session) {
		logger.info("Method : getRhData starts");

		logger.info("Method : getRhData ends");
		return "asset/rh-temp";
	}
//	
	@SuppressWarnings("unchecked")
	@GetMapping("rh-temp-view")
	public @ResponseBody Object viewRHtemp(@RequestParam String month, @RequestParam String currentYear,
			HttpSession session) {
		logger.info("Method :viewRHtemp starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getAssetUrl() + "rest-viewRHtemp?orgName=" + orgName + "&orgDivision=" + orgDivision
							+ "&userId=" + userId + "&month=" + month + "&currentYear=" + currentYear,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewRHtemp ends" + resp);
		return resp;
	}
}
