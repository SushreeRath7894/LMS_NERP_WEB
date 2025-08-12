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

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "asset/")
public class AssetDashboardController {

	Logger logger = LoggerFactory.getLogger(AssetDashboardController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CommonUtil commonUtil;

	@GetMapping("/dashboard")
	public String employee(Model model, HttpSession session) {

		String orgName = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getAssetUrl() + "getOrganization?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> Organization = Arrays.asList(dropDownModel);
			model.addAttribute("Organization", Organization);

			logger.info("Organization" + Organization);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getAssetUrl() + "getDivision?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> orgDivisionList = Arrays.asList(dropDownModel);
			model.addAttribute("orgDivisionList", orgDivisionList);

			logger.info("orgDivisionList" + orgDivisionList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : assetDashboardController starts and ends");
		return "asset/asset-dashboard";
	}


	@GetMapping("dashboard-asset")
	public @ResponseBody JsonResponse<Object> assetcategorydashboard(HttpSession session) {
		logger.info("Method :assetcategorydashboard starts");
		String restUrl = buildDashboardUrl(session, "assetcategorydashboard", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "assetcategorydashboard");
	}

	@GetMapping("dashboard-assetEndOfLife")
	public @ResponseBody JsonResponse<Object> assetEndOfLife(HttpSession session) {
		logger.info("Method :assetEndOfLife starts");
		String restUrl = buildDashboardUrl(session, "assetEndOfLife-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "assetEndOfLife-asset");
	}

	@GetMapping("dashboard-assetVerificationStatus")
	public @ResponseBody JsonResponse<Object> assetVerificationStatus(HttpSession session) {
		logger.info("Method :assetVerificationStatus starts");
		String restUrl = buildDashboardUrl(session, "assetVerificationStatus-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "assetVerificationStatus-asset");
	}

	@GetMapping("dashboard-assetTotalSpend")
	public @ResponseBody JsonResponse<Object> assetTotalSpend(HttpSession session) {
		logger.info("Method :assetTotalSpend starts");
		String restUrl = buildDashboardUrl(session, "assetTotalSpend-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "assetTotalSpend");
	}

	// assetBreakupHardwareAssets
	@GetMapping("dashboard-assetBreakupHardwareAssets")
	public @ResponseBody JsonResponse<Object> assetBreakupHardwareAssets(HttpSession session) {
		logger.info("Method :assetBreakupHardwareAssets-asset starts");
		String restUrl = buildDashboardUrl(session, "assetBreakupHardwareAssets-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "assetBreakupHardwareAssets-asset");
	}

	// assetAssetCountByLocation
	@GetMapping("dashboard-assetAssetCountByLocation")
	public @ResponseBody JsonResponse<Object> assetAssetCountByLocation(HttpSession session) {
		logger.info("Method :assetAssetCountByLocation starts");
		String restUrl = buildDashboardUrl(session, "assetAssetCountByLocation-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "assetAssetCountByLocation-asset");
	}

	// assetAssetCountByLifeState
	@GetMapping("dashboard-assetAssetCountByLifeState")
	public @ResponseBody JsonResponse<Object> assetAssetCountByLifeState(HttpSession session) {
		logger.info("Method :assetAssetCountByLifeState starts");
		String restUrl = buildDashboardUrl(session, "assetAssetCountByLifeState-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "assetAssetCountByLifeState-asset");
	}

	// assetAssetValueByCategory
	@GetMapping("dashboard-assetAssetValueByCategory")
	public @ResponseBody JsonResponse<Object> assetAssetValueByCategory(HttpSession session) {
		logger.info("Method :assetAssetValueByCategory-asset starts");
		String restUrl = buildDashboardUrl(session, "assetAssetValueByCategory-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "assetAssetValueByCategory-asset");
	}

	// assetAssetValueByCategory
	@GetMapping("dashboard-assetAssetsPulledFromPool")
	public @ResponseBody JsonResponse<Object> assetAssetsPulledFromPool(HttpSession session) {
		logger.info("Method :assetAssetValueByCategory-asset starts");
		String restUrl = buildDashboardUrl(session, "assetAssetsPulledFromPool-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "assetAssetsPulledFromPool-asset");
	}

	// assetAssetFulfillmentTime
	@GetMapping("dashboard-assetAssetFulfillmentTime")
	public @ResponseBody JsonResponse<Object> assetAssetFulfillmentTime(HttpSession session) {
		logger.info("Method :assetAssetFulfillmentTime-asset starts");
		String restUrl = buildDashboardUrl(session, "assetAssetFulfillmentTime-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "assetAssetFulfillmentTime-asset");
	}

	// assetAssetFulfillmentTime
	@GetMapping("dashboard-assetValuation")
	public @ResponseBody JsonResponse<Object> assetValuation(HttpSession session) {
		logger.info("Method :assetAssetFulfillmentTime-asset starts");
		String restUrl = buildDashboardUrl(session, "assetValuation-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "assetValuation-asset");
	}

	// assetAssetFulfillmentTime
	@GetMapping("dashboard-scrapedValuation")
	public @ResponseBody JsonResponse<Object> scrapedValuation(HttpSession session) {
		logger.info("Method :assetAssetFulfillmentTime-asset starts");
		String restUrl = buildDashboardUrl(session, "scrapedValuation-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "scrapedValuation-asset");
	}

	@GetMapping("dashboard-netAssetValuation")
	public @ResponseBody JsonResponse<Object> netAssetValuation(HttpSession session) {
		logger.info("Method :assetAssetFulfillmentTime-asset starts");
		String restUrl = buildDashboardUrl(session, "netAssetValuation-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "netAssetValuation-asset");
	}

	@GetMapping("dashboard-maenTimeToRepair")
	public @ResponseBody JsonResponse<Object> maenTimeToRepair(HttpSession session) {
		logger.info("Method :dashboard-maenTimeToRepair starts");
		String restUrl = buildDashboardUrl(session, "maenTimeToRepair-asset", null, null, null);
		return commonUtil.getRestAPIcall(restUrl, "maenTimeToRepair-asset");
	}

	// Helper method to build URL
	private String buildDashboardUrl(HttpSession session, String endpoint, String id, String month, String year) {
		String orgName = (String) session.getAttribute("ORGANIZATION");
		String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		StringBuilder urlBuilder = new StringBuilder(env.getGatepassUrl()).append(endpoint).append("?orgName=")
				.append(orgName).append("&orgDivision=").append(orgDivision);
		if (id != null)
			urlBuilder.append("&id=").append(id);
		if (month != null)
			urlBuilder.append("&month=").append(month);
		if (year != null)
			urlBuilder.append("&year=").append(year);
		return urlBuilder.toString();
	}

////////////////////////////////////////////////////Start maintainance

//dashboard-maintainance-monthly-backlog-analysis
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-monthly-backlog-analysis")
	public @ResponseBody Object maintainancemonthlybackloganalysis(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :maintainancemonthlybackloganalysis starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "monthlybackloganalysis?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :maintainancemonthlybackloganalysis ends" + resp);

		return resp;
	}

//dashboard-maintainance-scheduled-assigned
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-scheduled-assigned")
	public @ResponseBody Object maintainancescheduledassigned(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :maintainancescheduledassigned starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "maintainancescheduledassigned?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :maintainancescheduledassigned ends" + resp);

		return resp;
	}

	// dashboard-maintainance-preventive-maintainance
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-preventive-maintainance")
	public @ResponseBody Object maintainancepreventivemaintainance(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :maintainancepreventivemaintainance starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "maintainancepreventivemaintainance?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :maintainancepreventivemaintainance ends" + resp);

		return resp;
	}

//dashboard-maintainance-planned-maintainance-percentage
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-planned-maintainance-percentage")
	public @ResponseBody Object maintainanceplannedmaintainancepercentage(HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String org,
			@RequestParam String orgDiv) {

		logger.info("Method :maintainanceplannedmaintainancepercentage starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient
					.getForObject(
							env.getAssetUrl() + "maintainanceplannedmaintainancepercentage?orgName=" + org
									+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :maintainanceplannedmaintainancepercentage ends" + resp);

		return resp;
	}

//dashboard-maintainance-asset-group-assets
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-asset-group-assets")
	public @ResponseBody Object maintainanceassetgroupassets(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :maintainanceassetgroupassets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "maintainanceassetgroupassets?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :maintainanceassetgroupassets ends" + resp);

		return resp;
	}

//dashboard-maintainance-departments-assets-criticality
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-departments-assets-criticality")
	public @ResponseBody Object departmentsassetscriticality(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :departmentsassetscriticality starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "departmentsassetscriticality?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :departmentsassetscriticality ends" + resp);

		return resp;
	}

//dashboard-maintainance-work-order-overview
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-work-order-overview")
	public @ResponseBody Object workorderoverview(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :workorderoverview starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "workorderoverview?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :workorderoverview ends" + resp);

		return resp;
	}

//dashboard-maintainance-work-orders-type
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-work-orders-type")
	public @ResponseBody Object workorderstype(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :workorderstype starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "workorderstype?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :workorderstype ends" + resp);

		return resp;
	}

//dashboard-maintainance-unplanned-labor-hours
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-unplanned-labor-hours")
	public @ResponseBody Object unplannedlaborhours(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :unplannedlaborhours starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "unplannedlaborhours?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :unplannedlaborhours ends" + resp);

		return resp;
	}

	// dashboard-maintainance-work-orders-status
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-work-orders-status")
	public @ResponseBody Object workordersstatus(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :workordersstatus starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "workordersstatus?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :workordersstatus ends" + resp);

		return resp;
	}

//dashboard-maintainance-workOrder-actual-estimated-costs
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-workOrder-actual-estimated-costs")
	public @ResponseBody Object workOrderactualestimatedcosts(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :workOrderactualestimatedcosts starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "workOrderactualestimatedcosts?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :workOrderactualestimatedcosts ends" + resp);

		return resp;
	}

	// dashboard-maintainance-count-percentage-unsolved-tickets
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-count-percentage-unsolved-tickets")
	public @ResponseBody Object countpercentageunsolvedtickets(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :countpercentageunsolvedtickets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "countpercentageunsolvedtickets?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :countpercentageunsolvedtickets ends" + resp);

		return resp;
	}

	// dashboard-maintainance-mean-time-repair
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-mean-time-repair")
	public @ResponseBody Object meantimerepair(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :meantimerepair starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "meantimerepair?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :meantimerepair ends" + resp);

		return resp;
	}

//dashboard-maintainance-mean-time-detect
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-mean-time-detect")
	public @ResponseBody Object meantimedetect(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :meantimedetect starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "meantimedetect?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :meantimedetect ends" + resp);

		return resp;
	}

	// dashboard-maintainance-reopened-tickets
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-reopened-tickets")
	public @ResponseBody Object reopenedtickets(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :reopenedtickets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "reopenedtickets?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :reopenedtickets ends" + resp);

		return resp;
	}

	// dashboard-maintainance-total-ratio-percentage
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-total-ratio-percentage")
	public @ResponseBody Object totalratiopercentage(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :totalratiopercentage starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "totalratiopercentage?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :totalratiopercentage ends" + resp);

		return resp;
	}

//dashboard-maintainance-totat-ratio-per-year
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-maintainance-totat-ratio-per-year")
	public @ResponseBody Object totatratioperyear(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :totatratioperyear starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		try {

			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getAssetUrl() + "totatratioperyear?orgName=" + org + "&orgDivision="
					+ orgDiv + "&userid=" + userId + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :totatratioperyear ends" + resp);

		return resp;
	}

/////////////////////////////////////////////////////////End maintainance

///////////////////////////////////////////////////////// Start Assets

//dashboard-asset-asset-count-by-category
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-asset-count-by-category")
	public @ResponseBody Object assetcountbycategory(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :assetcountbycategory starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "assetcountbycategory?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :assetcountbycategory ends" + resp);

		return resp;
	}

	// dashboard-asset-count-by-Life-state
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-asset-count-by-Life-state")
	public @ResponseBody Object countbyLifestate(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :countbyLifestate starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "countbyLifestate?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :countbyLifestate ends" + resp);

		return resp;
	}

	// dashboard-asset-asset-value-by-category
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-asset-value-by-category")
	public @ResponseBody Object valuebycategory(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :valuebycategory starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "valuebycategory?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :valuebycategory ends" + resp);

		return resp;
	}

	// dashboard-asset-asset-count-by-location
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-asset-count-by-location")
	public @ResponseBody Object countbylocation(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :countbylocation starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "countbylocation?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :countbylocation ends" + resp);

		return resp;
	}

//dashboard-asset-asset-fulfillment-time
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-asset-fulfillment-time")
	public @ResponseBody Object assetfulfillmenttime(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :assetfulfillmenttime starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "assetfulfillmenttime?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :assetfulfillmenttime ends" + resp);

		return resp;
	}

	// dashboard-asset-total-spend
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-total-spend")
	public @ResponseBody Object totalspend(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :totalspend starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "totalspend?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :totalspend ends" + resp);

		return resp;
	}

	// dashboard-asset-asset-status
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-asset-status")
	public @ResponseBody Object assetstatus(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :assetstatus starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "assetstatus?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :assetstatus ends" + resp);

		return resp;
	}

	// dashboard-asset-end-life-next-days
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-end-life-next-days")
	public @ResponseBody Object endlifenextdays(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :endlifenextdays starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "endlifenextdays?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :endlifenextdays ends" + resp);

		return resp;
	}

//dashboard-asset-assets-pulled-from-pool-net-new-purchase
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-assets-pulled-from-pool-net-new-purchase")
	public @ResponseBody Object assetspulledfrompoolnetnewpurchase(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :assetspulledfrompoolnetnewpurchase starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "assetspulledfrompoolnetnewpurchase?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :assetspulledfrompoolnetnewpurchase ends" + resp);

		return resp;
	}

//dashboard-asset-verification-status
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-verification-status")
	public @ResponseBody Object verificationstatus(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :verificationstatus starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "verificationstatus?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :verificationstatus ends" + resp);

		return resp;
	}

/////////////////////////////////////////////////////////End Assets

////////////////////////////////////////////////////////Start Performance

//dashboard-performance-top-mean-time-repair-april
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-performance-top-mean-time-repair-april")
	public @ResponseBody Object topmeantimerepairapril(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :topmeantimerepairapril starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "topmeantimerepairapril?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :topmeantimerepairapril ends" + resp);

		return resp;
	}

	// dashboard-performance-maint-expenses-since-april
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-performance-maint-expenses-since-april")
	public @ResponseBody Object maintexpensessinceapril(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :maintexpensessinceapril starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "maintexpensessinceapril?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :maintexpensessinceapril ends" + resp);

		return resp;
	}

//dashboard-performance-incident-resolution-response-time
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-performance-incident-resolution-response-time")
	public @ResponseBody Object incidentresolutionresponsetime(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :incidentresolutionresponsetime starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "incidentresolutionresponsetime?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :incidentresolutionresponsetime ends" + resp);

		return resp;
	}

//dashboard-performance-asset-utilization-since-april
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-performance-asset-utilization-since-april")
	public @ResponseBody Object assetutilizationsinceapril(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :assetutilizationsinceapril starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "assetutilizationsinceapril?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :assetutilizationsinceapril ends" + resp);

		return resp;
	}

//dashboard-performance-top-mean-time-failure-since-april
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-performance-top-mean-time-failure-since-april")
	public @ResponseBody Object topmeantimefailuresinceapril(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :topmeantimefailuresinceapril starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "topmeantimefailuresinceapril?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :topmeantimefailuresinceapril ends" + resp);

		return resp;
	}

//dashboard-performance-top-asset-labor-since-april
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-performance-top-asset-labor-since-april")
	public @ResponseBody Object topassetlaborsinceapril(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :topassetlaborsinceapril starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "topassetlaborsinceapril?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :topassetlaborsinceapril ends" + resp);

		return resp;
	}

//dashboard-performance-top-asset-downtime–since-april
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-performance-top-asset-downtime-april")
	public @ResponseBody Object topassetdowntimesinceapril(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :topassetdowntimesinceapril starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "topassetdowntimesinceapril?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :topassetdowntimesinceapril ends" + resp);

		return resp;
	}

////////////////////////////////////////////////////////End Performance

////////////////////////////////////////////////////////Start Valueassets	

//dashboard-valuation-value-assets
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-valuation-value-assets")
	public @ResponseBody Object valueassets(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :valueassets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "valueassets?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :valueassets ends" + resp);

		return resp;
	}

//dashboard-valuation-value-assets
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-valuation-net-asset-value")
	public @ResponseBody Object netassetvalue(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :netassetvalue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "netassetvalue?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :netassetvalue ends" + resp);

		return resp;
	}

	// dashboard-valuation-net-purchase-value
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-valuation-net-purchase-value")
	public @ResponseBody Object netpurchasevalue(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :netpurchasevalue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "netpurchasevalue?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :netpurchasevalue ends" + resp);

		return resp;
	}

//dashboard-valuation-net-scrap-value
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-valuation-net-scrap-value")
	public @ResponseBody Object netscrapvalue(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :netscrapvalue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "netscrapvalue?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :netscrapvalue ends" + resp);

		return resp;
	}

	// dashboard-valuation-asset-depriciation-value
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-valuation-asset-depriciation-value")
	public @ResponseBody Object assetdepriciationvalue(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :assetdepriciationvalue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "assetdepriciationvalue?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :assetdepriciationvalue ends" + resp);

		return resp;
	}

//dashboard-valuation-scrap-value
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-valuation-scrap-value-month-wise")
	public @ResponseBody Object scrapvalue(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :scrapvalue starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "scrapvalueMonthWise?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :scrapvalue ends" + resp);

		return resp;
	}

//dashboard-valuation-net-asset-value
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-valuation-net-asset-values-month-wise")
	public @ResponseBody Object netassetvaluesMonthWise(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :netassetvalues starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "netassetvaluesMonthtWise?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :netassetvaluesMonthWise ends" + resp);

		return resp;
	}

//////////////////////////////////////////////////////// End Valueassets

	//////////////////////////////////////////////////////// Start analytics_reports

	// dashboard-analytics-reports-incomplete-assets
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-incomplete-assets")
	public @ResponseBody Object incompleteassets(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :incompleteassets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "incompleteassets?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :incompleteassets ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-table-incomplete-assets
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-table-incomplete-assets")
	public @ResponseBody Object tableincompleteassets(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :tableincompleteassets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "tableincompleteassets?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :tableincompleteassets ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-eligible-for-refresh-assets
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-eligible-for-refresh-assets")
	public @ResponseBody Object eligibleforrefreshassets(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :eligibleforrefreshassets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "eligibleforrefreshassets?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :eligibleforrefreshassets ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-table-eligible-for-refresh-assets
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-table-eligible-for-refresh-assets")
	public @ResponseBody Object tableeligibleforrefreshassets(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :tableeligibleforrefreshassets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "tableeligibleforrefreshassets?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :tableeligibleforrefreshassets ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-active-assets-not-discovered
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-active-assets-not-discovered")
	public @ResponseBody Object activeassetsnotdiscovered(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :activeassetsnotdiscovered starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "activeassetsnotdiscovered?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :activeassetsnotdiscovered ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-table-active-assets-not-discovered
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-table-active-assets-not-discovered")
	public @ResponseBody Object tableactiveassetsnotdiscovered(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :tableactiveassetsnotdiscovered starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "tableactiveassetsnotdiscovered?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :tableactiveassetsnotdiscovered ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-asset-incident-frequency
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-asset-incident-frequency")
	public @ResponseBody Object assetincidentfrequency(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :assetincidentfrequency starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "assetincidentfrequency?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :assetincidentfrequency ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-table-asset-incident-frequency
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-table-asset-incident-frequency")
	public @ResponseBody Object tableassetincidentfrequency(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :tableassetincidentfrequency starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "tableassetincidentfrequency?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :tableassetincidentfrequency ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-lifecycle-overview
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-lifecycle-overview")
	public @ResponseBody Object lifecycleoverview(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :lifecycleoverview starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "lifecycleoverview?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :lifecycleoverview ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-table-lifecycle-overview
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-table-lifecycle-overview")
	public @ResponseBody Object tablelifecycleoverview(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate,@RequestParam String org,@RequestParam String orgDiv) {

		logger.info("Method :tablelifecycleoverview starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restClient.getForObject(env.getAssetUrl() + "tablelifecycleoverview?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :tablelifecycleoverview ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-table-asset-disposal-status
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-table-asset-disposal-status")
	public @ResponseBody Object tableassetdisposalstatus(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate,@RequestParam String org,@RequestParam String orgDiv) {

		logger.info("Method :tableassetdisposalstatus starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restClient.getForObject(env.getAssetUrl() + "tableassetdisposalstatus?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :tableassetdisposalstatus ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-table-end-life-method
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-table-end-life-method")
	public @ResponseBody Object tableendlifemethod(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate,@RequestParam String org,@RequestParam String orgDiv) {

		logger.info("Method :tableendlifemethod starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restClient.getForObject(env.getAssetUrl() + "tableendlifemethod?orgName=" + org + "&orgDivision="
					+ orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :tableendlifemethod ends" + resp);

		return resp;
	}

	// dashboard-analytics-reports-table-eligible-refresh-assets
	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-analytics-reports-table-eligible-refresh-assets")
	public @ResponseBody Object tableeligiblerefreshassets(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate,@RequestParam String org,@RequestParam String orgDiv) {

		logger.info("Method :tableeligiblerefreshassets starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restClient.getForObject(env.getAssetUrl() + "tableeligiblerefreshassets?orgName=" + org
					+ "&orgDivision=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :tableeligiblerefreshassets ends" + resp);

		return resp;
	}

//////////////////////////////////////////////////////// End analytics_reports

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-oprational-count")
	public @ResponseBody Object oprationalHeadData(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String activity, @RequestParam String org,
			@RequestParam String orgDiv) {

		logger.info("Method :oprationalHeadData11 starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient
					.getForObject(
							env.getAssetUrl() + "oprationalAssetCount?orgName=" + org + "&orgDivision=" + orgDiv
									+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&activity=" + activity,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :oprationalHeadData11 ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-asset-oprational-count-for-mean-tab")
	public @ResponseBody Object oprationalHeadData2(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String activity, @RequestParam String org,
			@RequestParam String orgDiv) {

		logger.info("Method :oprationalHeadData2 starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient
					.getForObject(
							env.getAssetUrl() + "oprationalAssetCount2?orgName=" + org + "&orgDivision=" + orgDiv
									+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&activity=" + activity,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :oprationalHeadData2 ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-get-all-data")
	public @ResponseBody Object getAllData(HttpSession session, @RequestParam String fromDate, @RequestParam String id,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv) {

		logger.info("Method :getAllData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getAssetUrl() + "dashboard-getAllData?orgName=" + org + "&orgDivision="
					+ orgDiv + "&id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(env.getAssetUrl());

		logger.info("Method :getAllData ends" + resp);

		return resp;
	}

}
