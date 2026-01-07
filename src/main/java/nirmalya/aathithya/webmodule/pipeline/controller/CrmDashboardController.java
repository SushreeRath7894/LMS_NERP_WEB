package nirmalya.aathithya.webmodule.pipeline.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import nirmalya.aathithya.webmodule.master.controller.MasterDataApiController;

@Controller
@Component
@RequestMapping(value = "pipeline")
public class CrmDashboardController {

	Logger logger = LoggerFactory.getLogger(CrmTaskController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	@Autowired
	MasterDataApiController master;

	private String userId = "";

	@GetMapping("/crm-dashboard")
	public String viewCRMDashboard(Model model, HttpSession session) {
		logger.info("Method : viewCRMDashboard start");
		
		List<DropDownModel> executiveList = master.getOwnerList(session);
		model.addAttribute("executive", executiveList);
		
		String orgName = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			DropDownModel[] dropDownModel = restClient
					.getForObject(env.getPipeline() + "getOrganization?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> Organization = Arrays.asList(dropDownModel);
			model.addAttribute("Organization", Organization);

			logger.info("Organization" + Organization);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restClient
					.getForObject(env.getPipeline() + "getDivision?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> orgDivisionList = Arrays.asList(dropDownModel);
			model.addAttribute("orgDivisionList", orgDivisionList);

			logger.info("orgDivisionList" + orgDivisionList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		
		try {
			List<DropDownModel> ownerList = master.getOwnerList(session);
			
			logger.info("ownerList in lead page=================>" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewCRMDashboard ends");
		return "pipeline/ncrm-dashboard";

	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-getAllOperationalRecord")
	public @ResponseBody Object getAllRecordOperational(Model model, HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String id, @RequestParam String loc) {

		logger.info("Method :getAllRecordOperational starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		

		try {
			String url = env.getPipeline() + "getAllRecordOperational?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&id=" + id + "&loc=" + loc;

			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllRecordOperational ends" + resp);

		return resp;
	}
	
	//crm-dashboard-getAllOperationalRecord-with-executive
	
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-getAllOperationalRecord-with-executive")
	public @ResponseBody Object getAllRecordOperationalExecutive(Model model, HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String id, @RequestParam String loc, @RequestParam String executive) {

		logger.info("Method :getAllRecordOperationalExecutive starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		

		try {
			String url = env.getPipeline() + "getAllRecordOperationalExecutive?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&id=" + id + "&loc=" + loc + "&executive=" + executive;

			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllRecordOperationalExecutive ends" + resp);

		return resp;
	}
	
	
	
	
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "crm-dashboard-cityList" })
	public @ResponseBody JsonResponse<Object> getAllCRMCityList(@RequestParam String id) {
		logger.info("Method : getAllCRMCityList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getPipeline() + "getAllCRMCityList?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getAllCRMCityList ends");
		return res;
	}

	
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-getAllHeadCount")
	public @ResponseBody Object getAllCRMHeadCount(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc) {

		logger.info("Method :getAllCRMHeadCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getPipeline() + "getAllCRMHeadCount?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllCRMHeadCount ends" + resp);

		return resp;
	}
	
	//crm-dashboard-getAllHeadCount-with-executive
	
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-getAllHeadCount-with-executive")
	public @ResponseBody Object getAllCRMHeadCountExecutive(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc, @RequestParam String executive) {

		logger.info("Method :getAllCRMHeadCountExecutive starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getPipeline() + "getAllCRMHeadCountExecutive?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc+ "&executive=" + executive;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllCRMHeadCountExecutive ends" + resp);

		return resp;
	}
	
	
	
	//crm-dashboard-conversion-top-five-sales-executive
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-conversion-top-five-sales-executive")
	public @ResponseBody Object getTop5SalesExecutive(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc,@RequestParam String  executive) {

		logger.info("Method :getTop5SalesExecutive starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getPipeline() + "getTop5SalesExecutive?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc+ "&executive=" + executive;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getTop5SalesExecutive ends" + resp);

		return resp;
	}
	
	//crm-dashboard-conversion-count-all-ratios
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-conversion-count-all-ratios")
	public @ResponseBody Object getAllCountForLeadRatios(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc, @RequestParam String executive) {

		logger.info("Method :getAllCountForLeadRatios starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getPipeline() + "getAllCountForLeadRatios?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc+ "&executive=" + executive;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllCountForLeadRatios ends" + resp);

		return resp;
	}
	
	//crm-dashboard-conversion-last-30-days-leads-conversion
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-conversion-last-30-days-leads-conversion")
	public @ResponseBody Object getAllConvertedLeadCountInLast30Days(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc, @RequestParam String executive) {

		logger.info("Method :getAllConvertedLeadCountInLast30Days starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getPipeline() + "getAllConvertedLeadCountInLast30Days?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc+ "&executive=" + executive;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllConvertedLeadCountInLast30Days ends" + resp);

		return resp;
	}
	
	//crm-dashboard-conversion-count-funnel-and-avg-days
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-conversion-count-funnel-and-avg-days")
	public @ResponseBody Object getAllConvertedCountFunnelAndAvgDays(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc, @RequestParam String executive) {

		logger.info("Method :getAllConvertedCountFunnelAndAvgDays starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getPipeline() + "getAllConvertedCountFunnelAndAvgDays?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc+ "&executive=" + executive;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllConvertedCountFunnelAndAvgDays ends" + resp);

		return resp;
	}
	
	
	//crm-dashboard-activities-count-heading
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-activities-count-heading")
	public @ResponseBody Object getActivitiesCountHead(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc, @RequestParam String executive) {

		logger.info("Method :getActivitiesCountHead starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getPipeline() + "getActivitiesCountHead?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc+ "&executive=" + executive;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getActivitiesCountHead ends" + resp);

		return resp;
	}
	
	
	
	
	
	//crm-dashboard-activity-leadsCalls
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-activity-leadsCalls")
	public @ResponseBody Object getActivitiesLeadCalls(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc, @RequestParam String executive) {

		logger.info("Method :getActivitiesLeadCalls starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getPipeline() + "getActivitiesLeadCalls?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc+ "&executive=" + executive;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getActivitiesLeadCalls ends" + resp);

		return resp;
	}
	
	
	//crm-dashboard-activity-leadsMeetings
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-activity-leadsMeetings")
	public @ResponseBody Object getActivitiesLeadMeetings(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc, @RequestParam String executive) {

		logger.info("Method :getActivitiesLeadMeetings starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getPipeline() + "getActivitiesLeadMeetings?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc+ "&executive=" + executive;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getActivitiesLeadMeetings ends" + resp);

		return resp;
	}
	
	
	//crm-dashboard-activity-leadTasks
	@SuppressWarnings("unchecked")
	@GetMapping("crm-dashboard-activity-leadTasks")
	public @ResponseBody Object getActivitiesLeadTasks(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate, @RequestParam String org, @RequestParam String orgDiv,
			@RequestParam String loc, @RequestParam String executive) {

		logger.info("Method :getActivitiesLeadTasks starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String url = env.getPipeline() + "getActivitiesLeadTasks?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&loc=" + loc+ "&executive=" + executive;
			resp = restClient.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getActivitiesLeadTasks ends" + resp);

		return resp;
	}


}