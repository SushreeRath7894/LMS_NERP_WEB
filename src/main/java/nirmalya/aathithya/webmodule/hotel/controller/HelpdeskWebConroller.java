package nirmalya.aathithya.webmodule.hotel.controller;

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
@RequestMapping(value = "hotel")
public class HelpdeskWebConroller {
	Logger logger = LoggerFactory.getLogger(HelpdeskWebConroller.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/help-desk")
	public String viewHelpdesk(Model model, HttpSession session) {
		logger.info("Method: viewHelpdesk starts here");

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			DropDownModel[] loc = restTemplate.getForObject(env.getHotelUrl() + "getAllHotelsRoomDetails?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> locLists = Arrays.asList(loc);

			model.addAttribute("locLists", locLists);

			DropDownModel[] ticket_type = restTemplate.getForObject(
					env.getTicketUrl() + "getTicketTypeList?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> ticketTypeList = Arrays.asList(ticket_type);

			model.addAttribute("ticketTypeList", ticketTypeList);

			DropDownModel[] ticket_priority = restTemplate.getForObject(
					env.getTicketUrl() + "getPriorityList?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> priorityList = Arrays.asList(ticket_priority);

			model.addAttribute("priorityList", priorityList);

			DropDownModel[] assignTo = restTemplate.getForObject(
					env.getHotelUrl() + "getAllUserLists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> assignToList = Arrays.asList(assignTo);

			model.addAttribute("assignToList", assignToList);
			
			
			DropDownModel[] servicelist = restTemplate.getForObject(
					env.getHotelUrl() + "getAllServiceCategory?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> servicelists = Arrays.asList(servicelist);

			model.addAttribute("servicelists", servicelists);
			
			
			DropDownModel[] serviceListsAme = restTemplate.getForObject(
					env.getHotelUrl() + "getAllServiceCategoryAme?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> serviceListAme = Arrays.asList(serviceListsAme);

			model.addAttribute("serviceListAme", serviceListAme);


		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method: viewHelpdesk ends here");

		return "hotel/helpdesk";
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
			res = restTemplate.getForObject(env.getTicketUrl() + "rest-category-dtls?&id=" + id + "&org=" + organization
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getCategoryDetails ends");
		return res;

	}
	
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping("get-all-service-room-details")
	public @ResponseBody JsonResponse<List<DropDownModel>> getAllRoomDetails(HttpSession session) {
		logger.info("Method : getAllRoomDetails starts");

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
			res = restTemplate.getForObject(env.getHotelUrl() + "getAllRoomDetails?&org=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getAllRoomDetails ends");
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
			res = restTemplate.getForObject(env.getTicketUrl() + "rest-subcategory-dtls?&id=" + id + "&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getSubCategoryDetails ends");
		return res;

	}

	/* view-department-view-tk-save-data */
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
			resp = restTemplate.postForObject(env.getTicketUrl() + "rest-add-ticket-dtls", category,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : saveTicketDtls starts");
		return resp;
	}

	/* view-ticket-by */
	@SuppressWarnings({ "unchecked" })
	@GetMapping("view-ticket-by")
	public @ResponseBody Object viewAllTicketById(HttpSession session, @RequestParam String id) {
		logger.info("Method : viewAllTicketById starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String organization = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-view-ticket?&userid=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewAllTicketById ends");
		return resp;

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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-ticket-edit?id=" + id + "&orgName=" + orgName
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
	
		logger.info("Method :editTicket ends");
		return resp;
	}
	
	@GetMapping("/get-all-items-details")
	public @ResponseBody Object getAllItemList(HttpSession session,@RequestParam String id) {
		logger.info("Method :getAllItemList starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

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
		try {
			DropDownModel[] item = restTemplate.getForObject(
					env.getHotelUrl() + "getAllItemList?org=" + orgName + "&orgDiv=" + orgDivision + "&id=" + id, DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);

			if (itemList.size() > 0) {
				resp.setBody(itemList);
				resp.setCode("success");
				resp.setMessage("Data found");
			} else {
				resp.setBody(null);
				resp.setCode("failed");
				resp.setMessage("Data not found");
			}

		} catch (RestClientException e) {
			e.printStackTrace();
			resp.setBody(null);
			resp.setCode("failed");
			resp.setMessage(e.getMessage());
		}

		logger.info("Method :getAllItemList ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("save-all-hotel-services")
	public @ResponseBody Object saveHotelService(HttpSession session, @RequestBody String data) {
		logger.info("Method :saveHotelService starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

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
		try {
			resp = restTemplate.postForObject(env.getHotelUrl() + "rest-save-hotel-services?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :saveHotelService ends");
		return resp;
	}
	
	
	/* view-services-by */
	@SuppressWarnings({ "unchecked" })
	@GetMapping("view-services-by")
	public @ResponseBody Object viewAllServicesTypes(HttpSession session, @RequestParam String id,@RequestParam String tabId) {
		logger.info("Method : viewAllServicesTypes starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String organization = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-view-services-types?&userid=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision + "&id=" + id +"&tabId=" + tabId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewAllServicesTypes ends");
		return resp;

	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping("view-services-by-service-id")
	public @ResponseBody Object viewAllServicesTypesById(HttpSession session, @RequestParam String id) {
		logger.info("Method : viewAllServicesTypesById starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String organization = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			String userId = (String) session.getAttribute("USER_ID");

			resp = restTemplate.getForObject(env.getHotelUrl() + "rest-view-services-by-id?&userid=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewAllServicesTypesById ends");
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
			resp = restTemplate.postForObject(env.getTicketSystemUrl() + "add-agent-action-dtls", category,
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

}
