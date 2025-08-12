package nirmalya.aathithya.webmodule.maintenance.controller;

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
import nirmalya.aathithya.webmodule.ticket.model.TicketManagementModel;

@Controller
@RequestMapping(value = "maintenance/")
public class MaintainanceManagementController {
	Logger logger = LoggerFactory.getLogger(MaintainanceManagementController.class);
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("maintainance-management")
	public String maintanaceManagement(Model model, HttpSession session) {
		logger.info("Method : maintanaceManagement starts");

		String org = "";
		String orgDiv = "";
		String userId = "";
		String userRole = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
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
			DropDownModel[] spare_list = restClient.getForObject(
					env.getTicketUrl() + "get-sparepart-list?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> spareList = Arrays.asList(spare_list);

			model.addAttribute("sparePartList", spareList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restClient.getForObject(env.getAssetUrl() + "getCategoryListforSparePart?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> catLists = Arrays.asList(cat);
			model.addAttribute("catLists", catLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] emp = restClient.getForObject(env.getTicketUrl() + "getEmployeeListforJobview?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists", empLists);

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
			DropDownModel[] spare_list = restClient.getForObject(
					env.getTicketUrl() + "get-sparepart-list?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> spareList = Arrays.asList(spare_list);

			model.addAttribute("sparePartList", spareList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] cat = restClient.getForObject(env.getAssetUrl() + "getCategoryListforSparePart?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> catLists = Arrays.asList(cat);
			model.addAttribute("catLists", catLists);

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
		try {
			DropDownModel[] emp = restClient.getForObject(env.getTicketUrl() + "getEmployeeListforJobview?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> empLists = Arrays.asList(emp);

			model.addAttribute("empLists", empLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] ticket_type = restClient.getForObject(
					env.getTicketUrl() + "getTicketTypeList?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> ticketTypeList = Arrays.asList(ticket_type);

			model.addAttribute("ticketTypeList", ticketTypeList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] asset_list = restClient.getForObject(
					env.getTicketSystemUrl() + "getHISAssetList?org=" + org + "&orgDiv=" + orgDiv+ "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(asset_list);
			model.addAttribute("assetList", assetList);

		} catch (RestClientException e) {
			e.printStackTrace();
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
			DropDownModel[] ticket_source = restClient.getForObject(
					env.getTicketUrl() + "getSourceListForTicket?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> sourceList = Arrays.asList(ticket_source);

			model.addAttribute("sourceList", sourceList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] loc = restClient.getForObject(env.getAssetUrl() + "getLocationListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> locLists = Arrays.asList(loc);

			model.addAttribute("locLists", locLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] departmentList = restClient.getForObject(
					env.getTicketSystemUrl() + "get-department-list-his?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> deptList = Arrays.asList(departmentList);

			model.addAttribute("deptList", deptList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] vendorListitem = restClient.getForObject(
					env.getTicketUrl() + "get-vendor-list?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> vendorList = Arrays.asList(vendorListitem);

			model.addAttribute("vendorList", vendorList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);

		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol002") || data.contentEquals("rol010")) {
				model.addAttribute("adhrRole", "adhr");
			}

		}
		logger.info("Method : maintanaceManagement ends");
		return "his_ticket/maintanace-management";
	}
	
	// Tracking history of a ticket

	@SuppressWarnings({ "unchecked" })
	@GetMapping("view-department-view-tk-ticket-history")
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
			resp = restClient.getForObject(env.getTicketSystemUrl() + "ticket-history?&id=" + id + "&org=" + organization
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getTicketHistory ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-department-view-tk-feedback-save")
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

	@SuppressWarnings({ "unchecked" })
	@GetMapping("view-department-view-tk-getemployee")
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
			res = restClient.getForObject(env.getTicketSystemUrl() + "employee-dtls?deptid=" + deptid + "&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getEmployeeDetails ends");
		return res;

	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-department-view-tk-assigned-result")
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

			resp = restClient.getForObject(env.getTicketSystemUrl() + "departmentview-assigned-result?id=" + id
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
	@PostMapping("view-department-view-tk-save-action-details")
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
			resp = restClient.postForObject(env.getTicketSystemUrl() + "add-agent-action-dtls", category,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveAgentActionDtls starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-department-view-tk-chat-view")
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
	
}
