package nirmalya.aathithya.webmodule.his_ticket.controller;


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
@RequestMapping(value = "ticket-system/")
public class ViewDepatmentTicketController {

	Logger logger = LoggerFactory.getLogger(ViewDepatmentTicketController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("view-department-view")
	public String departmentview(Model model, HttpSession session, @RequestParam String id) {
		logger.info("Method : view-department-view starts");

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
			DropDownModel[] ticket_type = restClient.getForObject(
					env.getTicketUrl() + "getTicketTypeList?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> ticketTypeList = Arrays.asList(ticket_type);

			model.addAttribute("ticketTypeList", ticketTypeList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] emp_list = restClient.getForObject(
					env.getTicketUrl() + "getEmployeeList?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> empList = Arrays.asList(emp_list);
			model.addAttribute("empList", empList);
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
		logger.info("Method : view-department-view ends");
		return "his_ticket/ticket-management";
	}

	// All employee by department

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

		// Proirity List

		@SuppressWarnings("unchecked")
		@GetMapping("view-department-view-priority-list")
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
				resp = restClient.getForObject(env.getTicketSystemUrl() + "deptViewPriorityList?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&modName=" + modName, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :priorityList ends");
			return resp;
		}

		// Ticket priority

		
		  @SuppressWarnings({ "unchecked" })
		  
		  @GetMapping("view-department-view-tk-prioritywise-view") 
		  public @ResponseBody Object getCurrentTicketDetails(@RequestParam String id, @RequestParam String pageno, @RequestParam String activity, HttpSession session) {
		  logger.info("Method : getCurrentTicketDetails starts");
		 
		  String organization = ""; String orgDivision = ""; String userId = "";
		  
		  JsonResponse<Object> resp = new JsonResponse<Object>();
		  
		  try { 
			  organization = (String) session.getAttribute("ORGANIZATION");
			  orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION"); 
			  userId= (String) session.getAttribute("USER_ID"); 
		} catch (Exception e) {
			e.printStackTrace(); 
		} 
		  try {
			  resp = restClient.getForObject( env.getTicketSystemUrl() + "deptViewTicketList?&userid=" + userId + "&org=" +
					  	organization + "&orgDiv=" + orgDivision + "&id=" + id + "&pageno=" + pageno +
					  			"&activity=" + activity, JsonResponse.class); 
		} catch (Exception e) {
			e.printStackTrace(); 
		} logger.info("Method : getCurrentTicketDetails ends");
		  return resp;
		  
		  }
		 
		// Ticket priority search

		  @SuppressWarnings({ "unchecked" })
			@GetMapping("view-department-view-prioritywise-view-search")
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
							env.getTicketSystemUrl() + "rest-deptViewTicketList-search?&userid=" + userId + "&org=" + organization
									+ "&orgDiv=" + orgDivision + "&id=" + id + "&search=" + search + "&activity=" + activity+ "&date=" + date,
							JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Method : getCurrentTicketDetailsSearch ends"+resp);
				return resp;

			}

		@SuppressWarnings("unchecked")
		@PostMapping("view-department-view-save-details")
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
				resp = restClient.postForObject(env.getTicketSystemUrl() + "add-agent-dtls", category, JsonResponse.class);
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

		// History log
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

		@GetMapping("view-department-view-maintenance-policylist")
		public @ResponseBody Object getEmergencyList(@RequestParam String aid, String pid, HttpSession session) {
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

				resp = restClient.getForObject(env.getMaintenance() + "asset-maintenance-policylist?aid=" + aid
						+ "&pid=" + pid + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
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
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-department-view-ticket-close")
		public @ResponseBody Object closeTicketByAdmin(@RequestParam String id, HttpSession session) {
			logger.info("Method :closeTicketByAdmin starts");
			
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
				
				resp = restClient.getForObject(env.getTicketSystemUrl() + "close-ticket?ticketId=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision+ "&userId=" + userId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :closeTicketByAdmin ends");
			return resp;
		}
		@SuppressWarnings({ "unchecked" })
		@GetMapping("/view-department-view-tk-getcategory")
		public @ResponseBody JsonResponse<List<DropDownModel>> getCategoryDetails(@RequestParam String id,
				HttpSession session) {
			logger.info("Method : getCategoryDetails starts");

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
				res = restClient.getForObject(env.getTicketUrl() + "rest-category-dtls?&id=" + id + "&org=" + organization
						+ "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : getCategoryDetails ends");
			return res;

		}
		@SuppressWarnings({ "unchecked" })
		@GetMapping("/view-department-view-tk-getSubcategory")
		public @ResponseBody JsonResponse<List<DropDownModel>> getSubCategoryDetails(@RequestParam String id,
				HttpSession session) {
			logger.info("Method : getSubCategoryDetails starts");

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
				res = restClient.getForObject(env.getTicketUrl() + "rest-subcategory-dtls?&id=" + id + "&org="
						+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : getSubCategoryDetails ends");
			return res;

		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-department-view-tk-edit")
		public @ResponseBody Object editTicket(@RequestParam String id, HttpSession session) {
			logger.info("Method :editTicket starts");
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

				resp = restClient.getForObject(env.getTicketUrl() + "rest-ticket-edit?id=" + id + "&orgName=" + orgName
						+ "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getCode().contentEquals("success")) {
				resp.setMessage("Success");
			} else {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			}
			/*
			 * if (resp.getMessage() != "" && resp.getMessage() != null) {
			 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
			 * resp.setMessage("Success"); }
			 */
			logger.info("Method :editTicket ends");
			return resp;
		}
		@SuppressWarnings("unchecked")
		@PostMapping("/view-department-view-tk-save-data")
		public @ResponseBody JsonResponse<Object> saveTicketDtls(@RequestBody List<TicketManagementModel> category,
				HttpSession session) {
			logger.info("Method : saveTicketDtls starts");

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
				resp = restClient.postForObject(env.getTicketUrl() + "rest-add-ticket-dtls", category, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : saveTicketDtls starts");
			return resp;
		}
		@SuppressWarnings("unchecked")
		@GetMapping("view-department-view-tk-rate-save")
		public @ResponseBody JsonResponse<Object> saveTicketRating(@RequestParam String id,String rate, Model model, HttpSession session) {
			logger.info("Method : saveTicketRating function starts");
			JsonResponse<Object> res = new JsonResponse<Object>();
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restClient.getForObject(env.getTicketUrl() + "rest-view-agentTicket-rate-save?id=" + id + "&userId=" + userId+ "&rate=" + rate
						+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : saveTicketRating function Ends");

			return res;
		}
		// Delete Ticket.

		@SuppressWarnings("unchecked")
		@GetMapping("view-department-view-tk-delete")
		public @ResponseBody JsonResponse<Object> deleteTicket(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : deleteTicket function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			// JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restClient.getForObject(env.getTicketSystemUrl() + "rest-ticket-delete?id=" + id + "&userId=" + userId
						+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : deleteTicket function Ends");

			return res;
		}
		// Reopen Ticket.
		@SuppressWarnings("unchecked")
		@GetMapping("view-department-view-tk-reopen")
		public @ResponseBody JsonResponse<Object> reopenTicket(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : reopenTicket function starts");
			
			JsonResponse<Object> res = new JsonResponse<Object>();
			
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			try {
				res = restClient.getForObject(env.getTicketSystemUrl() + "rest-ticket-reopen?id=" + id + "&userId=" + userId
						+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : reopenTicket function Ends");
			
			return res;
		}
}
