package nirmalya.aathithya.webmodule.pipeline.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.CallCrmReportModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmVisitHistoryReportModel;
import nirmalya.aathithya.webmodule.pipeline.model.DealCrmReportModel;
import nirmalya.aathithya.webmodule.pipeline.model.DirecterManagerCrmReportModel;
import nirmalya.aathithya.webmodule.pipeline.model.MeetingCrmReportModel;
import nirmalya.aathithya.webmodule.pipeline.model.RegistrationCrmReportModel;
import nirmalya.aathithya.webmodule.pipeline.model.billingCrmReportModel;
import nirmalya.aathithya.webmodule.pipeline.model.taskCrmReportModel;

@Controller
@RequestMapping(value = "pipeline")
public class VisitHistoryReportController {
	Logger logger = LoggerFactory.getLogger(VisitHistoryReportController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	// Summary
	@GetMapping("/view-crm-reports")
	public String crmReport(Model model, HttpSession session) {

		logger.info("Method : crmReport starts");
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
// Customers List

		try {
			DropDownModel[] customerList = restTemplate.getForObject(env.getApiUrl() + "getCustomerLists?organization="
					+ organization + "&orgDivision=" + orgDivision + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> customer = Arrays.asList(customerList);
			model.addAttribute("customer", customer);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

// Sales Team List

		try {
			DropDownModel[] salesTeamList = restTemplate
					.getForObject(env.getApiUrl() + "getSalesTeamLists?organization=" + organization + "&orgDivision="
							+ orgDivision + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> salesTeam = Arrays.asList(salesTeamList);
			model.addAttribute("salesTeam", salesTeam);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : crmReport ends");
		return "pipeline/crmVisitHistoryReport.html";
	}

	/****************************** Customer Report ****************************/
	// view Customer details

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-customerDetails")
	public @ResponseBody List<RegistrationCrmReportModel> viewCustomerReports(HttpSession session) {
		logger.info("Method : viewCustomerReports");

		JsonResponse<List<RegistrationCrmReportModel>> resp = new JsonResponse<List<RegistrationCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "getClientDetailsReport?createdby=" + userId + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewCustomerReports ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}
	// view customer details search

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-customerDetailsReportSearch")
	public @ResponseBody List<RegistrationCrmReportModel> viewCustomerReportSearch(HttpSession session, @RequestParam String customer,
			String saleTeam, String fromDate, String toDate) {
		logger.info("Method : viewCustomerReportSearch");

		JsonResponse<List<RegistrationCrmReportModel>> resp = new JsonResponse<List<RegistrationCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCustomerReportSearch?createdBy=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate + "&customer=" + customer + "&saleTeam=" + saleTeam, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewCustomerReportSearch ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}
	// view decision maker details search
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-decisionMakerDeatils")
	public @ResponseBody List<DirecterManagerCrmReportModel> viewDecisionMakerDeatils(HttpSession session) {
		logger.info("Method : viewDecisionMakerDeatils");
		
		JsonResponse<List<DirecterManagerCrmReportModel>> resp = new JsonResponse<List<DirecterManagerCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewDecisionMakerDeatils?createdBy=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewDecisionMakerDeatils ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}
	// view executive details search
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-executiveReport")
	public @ResponseBody List<DirecterManagerCrmReportModel> viewExecutiveDeatils(HttpSession session) {
		logger.info("Method : viewExecutiveDeatils");
		
		JsonResponse<List<DirecterManagerCrmReportModel>> resp = new JsonResponse<List<DirecterManagerCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewExecutiveDeatils?createdBy=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewExecutiveDeatils ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}
	/****************************** Call Report ****************************/
	// view call details

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-callReport")
	public @ResponseBody List<CallCrmReportModel> viewCallReports(HttpSession session, @RequestParam String fromDate,
			String toDate) {
		logger.info("Method : viewCallReports");

		JsonResponse<List<CallCrmReportModel>> resp = new JsonResponse<List<CallCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCrmCall-visitHistoryReport?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<CallCrmReportModel> call = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CallCrmReportModel>>() {
				});
		if (call != null) {
			for (CallCrmReportModel a : call) {
				if (a.getCallStatus().equals("0")) {
					a.setCallStatus("Pending");
				} else {
					a.setCallStatus("VISITED");
				}
			}
		}
		logger.info("Method : viewCallReports ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return call;
	}
	// view decision maker details search

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-decisionMakerDeatilsSearch")
	public @ResponseBody List<DirecterManagerCrmReportModel> decisionMakerDeatilsSearch(HttpSession session, @RequestParam String customer,
			String saleTeam, String fromDate, String toDate) {
		logger.info("Method : decisionMakerDeatilsSearch");

		JsonResponse<List<DirecterManagerCrmReportModel>> resp = new JsonResponse<List<DirecterManagerCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewDecisionMakerDeatilsSearch?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate + "&customer=" + customer + "&saleTeam=" + saleTeam, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
 
		logger.info("Method : decisionMakerDeatilsSearch ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}
	// view visit details

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-visitHistoryReport")
	public @ResponseBody List<CrmVisitHistoryReportModel> visitHistoryReport(HttpSession session,
			@RequestParam String fromDate, String toDate) {
		logger.info("Method : visitHistoryReport");

		JsonResponse<List<CrmVisitHistoryReportModel>> resp = new JsonResponse<List<CrmVisitHistoryReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCrm-visitHistoryReport?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
 
		logger.info("Method : visitHistoryReport ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}

	// view visit details search

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-visitHistoryReportSearch")
	public @ResponseBody List<CrmVisitHistoryReportModel> visitHistoryReportSearch(HttpSession session,
			@RequestParam String customer, String saleTeam, String fromDate, String toDate) {
		logger.info("Method : visitHistoryReportSearch");

		JsonResponse<List<CrmVisitHistoryReportModel>> resp = new JsonResponse<List<CrmVisitHistoryReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCrm-visitHistoryReportSearch?userId="
					+ userId + "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate + "&customer=" + customer + "&saleTeam=" + saleTeam, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : visitHistoryReportSearch ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}
	// view visit details search ByType
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-visitHistoryReportSearchByType")
	public @ResponseBody List<CrmVisitHistoryReportModel> visitHistoryReportSearchByType(HttpSession session,
			@RequestParam String customer, String saleTeam, String type, String fromDate, String toDate) {
		logger.info("Method : visitHistoryReportSearchByType");
		
		JsonResponse<List<CrmVisitHistoryReportModel>> resp = new JsonResponse<List<CrmVisitHistoryReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCrm-visitHistoryReportSearchByType?userId="
					+ userId + "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate + "&customer=" + customer + "&saleTeam=" + saleTeam+"&type="+type, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : visitHistoryReportSearchByType ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}
	// view visit plan details
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-viewVisitPlanReport")
	public @ResponseBody List<CrmVisitHistoryReportModel> visitPlanReport(HttpSession session,
			@RequestParam String fromDate, String toDate) {
		logger.info("Method : visitPlanReport");
		
		JsonResponse<List<CrmVisitHistoryReportModel>> resp = new JsonResponse<List<CrmVisitHistoryReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCrm-visitPlanReport?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : visitPlanReport ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}
	// view visit plan details search
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-visitPlanReportSearch")
	public @ResponseBody List<CrmVisitHistoryReportModel> visitPlanReportSearch(HttpSession session,
			@RequestParam String customer, String saleTeam, String fromDate, String toDate) {
		logger.info("Method : visitPlanReportSearch");
		
		JsonResponse<List<CrmVisitHistoryReportModel>> resp = new JsonResponse<List<CrmVisitHistoryReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCrm-visitPlanReportSearch?userId="
					+ userId + "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate + "&customer=" + customer + "&saleTeam=" + saleTeam, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : visitHistoryReportSearch ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}
	// view visit plan details search ByType
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-visitPlanReportSearchByType")
	public @ResponseBody List<CrmVisitHistoryReportModel> visitPlanReportSearchByType(HttpSession session,
			@RequestParam String customer, String saleTeam, String type, String fromDate, String toDate) {
		logger.info("Method : visitPlanReportSearchByType");
		
		JsonResponse<List<CrmVisitHistoryReportModel>> resp = new JsonResponse<List<CrmVisitHistoryReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCrm-visitPlanReportSearchByType?userId="
					+ userId + "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate + "&customer=" + customer + "&saleTeam=" + saleTeam+"&type="+type, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : visitPlanReportSearchByType ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}
	/****************************** Meeting Report ****************************/
	// view meeting details

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-meetingReport")
	public @ResponseBody List<MeetingCrmReportModel> viewMeetingReports(HttpSession session,
			@RequestParam String fromDate, String toDate) {
		logger.info("Method : viewMeetingReports");

		JsonResponse<List<MeetingCrmReportModel>> resp = new JsonResponse<List<MeetingCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCrmMeeting-visitHistoryReport?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<MeetingCrmReportModel> meeting = mapper.convertValue(resp.getBody(),
				new TypeReference<List<MeetingCrmReportModel>>() {
				});
		if (meeting != null) {
			for (MeetingCrmReportModel a : meeting) {
				if (a.getMeetingStatus().equals("0")) {
					a.setMeetingStatus("Pending");
				} else {
					a.setMeetingStatus("VISITED");
				}
			}
		}
		logger.info("Method : viewMeetingReports ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return meeting;
	}
	// view meeting details search

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-meetingReportSearch")
	public @ResponseBody List<MeetingCrmReportModel> viewMeetingReportsSearch(HttpSession session,
			@RequestParam String customer, String saleTeam, String fromDate, String toDate) {
		logger.info("Method : viewMeetingReportsSearch");

		JsonResponse<List<MeetingCrmReportModel>> resp = new JsonResponse<List<MeetingCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCrmMeeting-visitHistoryReportSearch?userId="
					+ userId + "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate + "&customer=" + customer + "&saleTeam=" + saleTeam, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<MeetingCrmReportModel> meeting = mapper.convertValue(resp.getBody(),
				new TypeReference<List<MeetingCrmReportModel>>() {
				});
		if (meeting != null) {
			for (MeetingCrmReportModel a : meeting) {
				if (a.getMeetingStatus().equals("0")) {
					a.setMeetingStatus("Pending");
				} else {
					a.setMeetingStatus("VISITED");
				}
			}
		}
		logger.info("Method : viewMeetingReportsSearch ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return meeting;
	}

	/****************************** Task Report ****************************/
	// view task details

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-taskReport")
	public @ResponseBody List<taskCrmReportModel> viewTaskReports(HttpSession session, @RequestParam String fromDate,
			String toDate) {
		logger.info("Method : viewTaskReports");

		JsonResponse<List<taskCrmReportModel>> resp = new JsonResponse<List<taskCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCrmTask-visitHistoryReport?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<taskCrmReportModel> task = mapper.convertValue(resp.getBody(),
				new TypeReference<List<taskCrmReportModel>>() {
				});
		if (task != null) {
			for (taskCrmReportModel a : task) {
				if (a.getTaskStatus().equals("0")) {
					a.setTaskStatus("Pending");
				} else {
					a.setTaskStatus("VISITED");
				}
				if (a.getTaskRepeate().equals("0")) {
					a.setTaskRepeate("No");
				} else {
					a.setTaskRepeate("Yes");
				}
			}
		}
		logger.info("Method : viewTaskReports ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return task;
	}
	// view task details search

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-taskReportSearch")
	public @ResponseBody List<taskCrmReportModel> viewTaskReportsSearch(HttpSession session,
			@RequestParam String customer, String saleTeam, String fromDate, String toDate) {
		logger.info("Method : viewTaskReportsSearch");

		JsonResponse<List<taskCrmReportModel>> resp = new JsonResponse<List<taskCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewCrmTask-visitHistoryReportSearch?userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate + "&customer=" + customer + "&saleTeam=" + saleTeam, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<taskCrmReportModel> task = mapper.convertValue(resp.getBody(),
				new TypeReference<List<taskCrmReportModel>>() {
				});
		if (task != null) {
			for (taskCrmReportModel a : task) {
				if (a.getTaskStatus().equals("0")) {
					a.setTaskStatus("Pending");
				} else {
					a.setTaskStatus("VISITED");
				}
				if (a.getTaskRepeate().equals("0")) {
					a.setTaskRepeate("No");
				} else {
					a.setTaskRepeate("Yes");
				}
			}
		}
		logger.info("Method : viewTaskReportsSearch ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return task;
	}

	/****************************** Deal Report ****************************/
	// view deal details
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-dealReport")
	public @ResponseBody List<DealCrmReportModel> viewDealReports(HttpSession session, @RequestParam String fromDate,
			String toDate) {
		logger.info("Method : viewDealReports");

		JsonResponse<List<DealCrmReportModel>> resp = new JsonResponse<List<DealCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(
					env.getApiUrl() + "viewDealStatusReport?createdBy=" + userId + "&organization=" + organization
							+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewDealReports ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}

	// view deal details search

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-dealReportSearch")
	public @ResponseBody List<DealCrmReportModel> viewDealReportsSearch(HttpSession session,
			@RequestParam String customer, String saleTeam, String fromDate, String toDate) {
		logger.info("Method : viewDealReportsSearch");

		JsonResponse<List<DealCrmReportModel>> resp = new JsonResponse<List<DealCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(
					env.getApiUrl() + "viewDealStatusReportSearch?createdBy=" + userId + "&organization=" + organization
							+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate+ "&customer=" + customer + "&saleTeam=" + saleTeam,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewDealReportsSearch ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}

	/****************************** Billing Report ****************************/
	// view Billing
	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-billingReport")
	public @ResponseBody List<billingCrmReportModel> viewBillingReports(HttpSession session,
			@RequestParam String fromDate, String toDate) {
		logger.info("Method : viewBillingReports");

		JsonResponse<List<billingCrmReportModel>> resp = new JsonResponse<List<billingCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(
					env.getApiUrl() + "viewClassifiedBillApi?createdBy=" + userId + "&organization=" + organization
							+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewBillingReports ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
		
		
	}
	// view Billing search

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-reports-billingReportSearch")
	public @ResponseBody List<billingCrmReportModel> viewBillingReportsSearch(HttpSession session,
			@RequestParam String saleTeam, String fromDate, String toDate) {
		logger.info("Method : viewBillingReportsSearch");

		JsonResponse<List<billingCrmReportModel>> resp = new JsonResponse<List<billingCrmReportModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getApiUrl() + "viewClassifiedBillSearchApi?createdBy=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate=" + fromDate
					+ "&toDate=" + toDate + "&salesTeam=" + saleTeam, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewBillingReportsSearch ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());
		return resp.getBody();
	}
	
	
	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-reports-deal-bar")
	public @ResponseBody JsonResponse<List<Object>> dashboarddistrictcategoriseBarDetails(Model model,
			@RequestParam String salesTeam,@RequestParam String fdate,@RequestParam String tdate, HttpSession session) {

		logger.info("Method : dashboarddistrictcategoriseBarDetails starts"+salesTeam+tdate+fdate);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(salesTeam);
		
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			jsonResponse = restTemplate.getForObject(
					env.getApiUrl() + "deal-reportcategorise-bar?salesTeam="+salesTeam+"&fdate="+fdate+"&tdate="+tdate+"&orgname="+organization+"&orgdiv="+orgDivision, JsonResponse.class);

		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : dashboarddistrictcategoriseBarDetails ends");
		return jsonResponse;
		
	}

	
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-reports-view-history-bar")
	public @ResponseBody JsonResponse<List<Object>> viewHistory(Model model,
			@RequestParam String salesTeam,@RequestParam String fdate,@RequestParam String tdate, HttpSession session) {

		logger.info("Method : viewHistory starts"+salesTeam+tdate+fdate);

		JsonResponse<List<Object>> jsonResponse = new JsonResponse<List<Object>>();
		logger.info(salesTeam);
		
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			jsonResponse = restTemplate.getForObject(
					env.getApiUrl() + "viewhistory-reportcategorise-bar?salesTeam="+salesTeam+"&fdate="+fdate+"&tdate="+tdate+"&orgname="+organization+"&orgdiv="+orgDivision, JsonResponse.class);

		
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewHistory ends");
		return jsonResponse;
		
	}
	
	
}
