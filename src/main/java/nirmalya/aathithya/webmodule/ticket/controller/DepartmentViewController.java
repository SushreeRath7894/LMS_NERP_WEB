package nirmalya.aathithya.webmodule.ticket.controller;

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
import nirmalya.aathithya.webmodule.maintenance.model.AllotedMaintenanceModel;
import nirmalya.aathithya.webmodule.ticket.model.TicketManagementModel;

@Controller
@RequestMapping(value = "ticket/")
public class DepartmentViewController {

	Logger logger = LoggerFactory.getLogger(DepartmentViewController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("departmentview")
	public String departmentView(Model model, HttpSession session) {
		logger.info("Method : departmentView starts");

		String org = "";
		String orgDiv = "";
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] ticket_priority = restClient.getForObject(
					env.getTicketUrl() + "getPriorityList?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);

			List<DropDownModel> priorityList = Arrays.asList(ticket_priority);

			model.addAttribute("priorityList", priorityList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] result_List = restClient.getForObject(
					env.getTicketUrl() + "get-result-status?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);

			List<DropDownModel> resultList = Arrays.asList(result_List);

			model.addAttribute("resultList", resultList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] departmentList = restClient.getForObject(
					env.getTicketUrl() + "get-department-list?org=" + org + "&orgDiv=" + orgDiv+ "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> deptList = Arrays.asList(departmentList);

			model.addAttribute("deptList", deptList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] sList = restClient.getForObject(
					env.getTicketUrl() + "get-vendor-service-list?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> serviceList = Arrays.asList(sList);

			model.addAttribute("serviceList", serviceList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// for vendor drop down

		try {
			DropDownModel[] vendorListitem = restClient.getForObject(
					env.getTicketUrl() + "get-vendor-list?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(vendorListitem);

			model.addAttribute("vendorList", vendorList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] policyListitem = restClient.getForObject(
					env.getTicketUrl() + "get-policy-list?org=" + org + "&orgDiv=" + orgDiv+ "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> policyList = Arrays.asList(policyListitem);

			model.addAttribute("policyList", policyList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] shift = restClient.getForObject(
					env.getProduction() + "getShiftListForProduction?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : departmentView ends");
		return "ticket/department-view";
	}

	// All employee by department

	@SuppressWarnings({ "unchecked" })
	@GetMapping("departmentview-getemployee")
	public @ResponseBody JsonResponse<List<DropDownModel>> getEmployeeDetails(@RequestParam String deptid,
			HttpSession session) {
		logger.info("Method : getEmployeeDetails starts");

		String organization = "";
		String orgDivision = "";
		JsonResponse<List<DropDownModel>> res = new JsonResponse<List<DropDownModel>>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getTicketUrl() + "rest-employee-dtls?deptid=" + deptid + "&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getEmployeeDetails ends");
		return res;

	}

	// Proirity List

	@SuppressWarnings("unchecked")
	@GetMapping("departmentview-priority-list")
	public @ResponseBody Object priorityList(@RequestParam String modName, HttpSession session) {
		logger.info("Method :priorityList starts");
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
			resp = restClient.getForObject(env.getTicketUrl() + "rest-deptViewPriorityList?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&modName=" + modName, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :priorityList ends"+resp);
		return resp;
	}

	// Ticket priority

	@SuppressWarnings({ "unchecked" })
	@GetMapping("view-agentTicket-prioritywise-view")
	public @ResponseBody Object getCurrentTicketDetails(@RequestParam String id, @RequestParam String pageno,
			@RequestParam String activity, HttpSession session) {
		logger.info("Method : getCurrentTicketDetails starts"+id);

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(
					env.getTicketUrl() + "rest-deptViewTicketList?&userid=" + userId + "&org=" + organization
							+ "&orgDiv=" + orgDivision + "&id=" + id + "&pageno=" + pageno + "&activity=" + activity,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getCurrentTicketDetails ends"+resp);
		return resp;

	}
	// Ticket priority search

	@SuppressWarnings({ "unchecked" })
	@GetMapping("view-agentTicket-prioritywise-view-search")
	public @ResponseBody Object getCurrentTicketDetailsSearch(@RequestParam String id,String date, @RequestParam String search,
			@RequestParam String activity, HttpSession session) {
		logger.info("Method : getCurrentTicketDetailsSearch starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(
					env.getTicketUrl() + "rest-deptViewTicketList-search?&userid=" + userId + "&org=" + organization
							+ "&orgDiv=" + orgDivision + "&id=" + id + "&search=" + search + "&activity=" + activity+ "&date=" + date,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getCurrentTicketDetailsSearch ends"+resp);
		return resp;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("departmentview-save-details")
	public @ResponseBody JsonResponse<Object> saveAgentDtls(@RequestBody TicketManagementModel category,
			HttpSession session) {
		logger.info("Method : saveAgentDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		category.setOrganization(organization);
		category.setCreatedBy(userId);
		category.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getTicketUrl() + "rest-add-agent-dtls", category, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveAgentDtls starts");
		return resp;
	}

	// Tracking history of a ticket

	@SuppressWarnings({ "unchecked" })
	@GetMapping("departmentview-ticket-history")
	public @ResponseBody Object getTicketHistory(@RequestParam String id, HttpSession session) {
		logger.info("Method : getTicketHistory starts");

		String organization = "";
		String orgDivision = "";
		// String userId = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			// userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getTicketUrl() + "rest-ticket-history?&id=" + id + "&org=" + organization
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getTicketHistory ends");
		return resp;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("departmentview-save-action-details")
	public @ResponseBody JsonResponse<Object> saveAgentActionDtls(@RequestBody TicketManagementModel category,
			HttpSession session) {
		logger.info("Method : saveAgentActionDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		category.setOrganization(organization);
		category.setCreatedBy(userId);
		category.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getTicketUrl() + "rest-add-agent-action-dtls", category,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveAgentActionDtls starts"+resp);
		return resp;
	}

	// History log
	@SuppressWarnings("unchecked")
	@GetMapping("departmentview-assigned-result")
	public @ResponseBody Object historyResult(@RequestParam String id, HttpSession session) {
		logger.info("Method :historyResult starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			resp = restClient.getForObject(env.getTicketUrl() + "rest-departmentview-assigned-result?id=" + id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :historyResult ends");
		return resp;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("departmentview-maintenance-policylist")
	public @ResponseBody Object getEmergencyList(@RequestParam String aid, String pid,String shift, HttpSession session) {
		logger.info("Method :getEmergencyList starts");
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

			resp = restClient.getForObject(env.getTicketUrl() + "rest-departmentview-maintenance-policylist?aid=" + aid
					+ "&pid=" + pid + "&orgName=" + orgName + "&orgDivision=" + orgDivision+ "&shift=" + shift, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :getEmergencyList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("departmentview-feedback-save")
	public @ResponseBody JsonResponse<Object> saveTicketChatDtls(@RequestBody List<TicketManagementModel> category,
			HttpSession session) {
		logger.info("Method : saveTicketChatDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (TicketManagementModel m : category) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restClient.postForObject(env.getTicketUrl() + "rest-view-agentTicket-feedback-save", category,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveTicketChatDtls starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("departmentview-chat-view")
	public @ResponseBody Object viewChatData(@RequestParam String id,@RequestParam String type, HttpSession session) {
		logger.info("Method :viewChatData starts");
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

			resp = restClient.getForObject(env.getTicketUrl() + "rest-view-agentTicket-chat-view?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision+ "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewChatData ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("departmentview-ticket-close")
	public @ResponseBody Object closeTicketByAdmin(@RequestParam String id, HttpSession session) {
		logger.info("Method :closeTicketByAdmin starts"+id);
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			
			resp = restClient.getForObject(env.getTicketUrl() + "rest-close-ticket?ticketId=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision+ "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :closeTicketByAdmin ends"+resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("departmentview-ticket-hold")
	public @ResponseBody Object holdTicketByAdmin(@RequestParam String id, HttpSession session) {
		logger.info("Method :holdTicketByAdmin starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			
			resp = restClient.getForObject(env.getTicketUrl() + "rest-departmentview-ticket-hold?ticketId=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision+ "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :holdTicketByAdmin ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("departmentview-ticket-release")
	public @ResponseBody Object releaseTicketByAdmin(@RequestParam String id, HttpSession session) {
		logger.info("Method :releaseTicketByAdmin starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			
			resp = restClient.getForObject(env.getTicketUrl() + "rest-departmentview-ticket-release?ticketId=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision+ "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :releaseTicketByAdmin ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping("departmentview-getVendorListOnService")
	public @ResponseBody JsonResponse<List<DropDownModel>> getVendorDetailsOnService(@RequestParam String serviceId,
			HttpSession session) {
		logger.info("Method : getVendorDetailsOnService starts");

		String organization = "";
		String orgDivision = "";
		JsonResponse<List<DropDownModel>> res = new JsonResponse<List<DropDownModel>>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getTicketUrl() + "rest-getVendorListOnService?serviceId=" + serviceId + "&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getVendorDetailsOnService ends");
		return res;

	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping("view-agentTicket-policywise-view")
	public @ResponseBody Object getTicketPolicyWise(@RequestParam String id, HttpSession session) {
		logger.info("Method : getTicketPolicyWise starts"+id);

		String organization = "";
		String orgDivision = "";
		String userId = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(
					env.getTicketUrl() + "rest-view-agentTicket-policywise-view?&userid=" + userId + "&org=" + organization
							+ "&orgDiv=" + orgDivision + "&id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getTicketPolicyWise ends"+resp);
		return resp;

	}
	
	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "view-agentTicket-maintenance-progress" })
	public @ResponseBody JsonResponse<Object> addPolicyProgress(@RequestBody List<AllotedMaintenanceModel> av, HttpSession session) {
		logger.info("Method : addPolicyProgress function starts"+av);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (AllotedMaintenanceModel m : av) {
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("INPUT DATA::::::"+av);
		try {
			resp = restClient.postForObject(env.getMaintenance() + "rest-asset-maintenance-progress", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addPolicyProgress function Ends");
		return resp;
	}
}
