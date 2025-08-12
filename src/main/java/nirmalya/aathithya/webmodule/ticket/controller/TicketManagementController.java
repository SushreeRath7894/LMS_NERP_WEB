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
import nirmalya.aathithya.webmodule.ticket.model.TicketManagementModel;

@Controller
@RequestMapping(value = "ticket")
public class TicketManagementController {

	Logger logger = LoggerFactory.getLogger(TicketManagementController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/view-agentTicket")
	public String ticketManagement(Model model, HttpSession session) {
		logger.info("Method : ticketManagement starts");

		String org = "";
		String orgDiv = "";
		String userId = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] ticket_type = restTemplate.getForObject(
					env.getTicketUrl() + "getTicketTypeList?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> ticketTypeList = Arrays.asList(ticket_type);

			model.addAttribute("ticketTypeList", ticketTypeList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] asset_list = restTemplate.getForObject(
					env.getTicketUrl() + "getAssetList?org=" + org + "&orgDiv=" + orgDiv+ "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> assetList = Arrays.asList(asset_list);

			model.addAttribute("assetList", assetList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] ticket_priority = restTemplate.getForObject(
					env.getTicketUrl() + "getPriorityList?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> priorityList = Arrays.asList(ticket_priority);

			model.addAttribute("priorityList", priorityList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] ticket_source = restTemplate.getForObject(
					env.getTicketUrl() + "getSourceListForTicket?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> sourceList = Arrays.asList(ticket_source);

			model.addAttribute("sourceList", sourceList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] loc = restTemplate.getForObject(env.getAssetUrl() + "getLocationListforAsset?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> locLists = Arrays.asList(loc);

			model.addAttribute("locLists", locLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : ticketManagement ends");
		return "ticket/grievance-Manage-Ticket";
	}
	
	
	
	/*
	 * @GetMapping("/view-agentTicketNew") public String viewManageTicket(Model
	 * model, HttpSession session) {
	 * logger.info("Method : ticketManagementNew starts");
	 * 
	 * String org = ""; String orgDiv = ""; String userId = "";
	 * 
	 * try { org = (String) session.getAttribute("ORGANIZATION"); orgDiv = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); userId = (String)
	 * session.getAttribute("USER_ID"); } catch (Exception ex) {
	 * logger.error(ex.getMessage()); }
	 * 
	 * try { DropDownModel[] ticket_type = restTemplate.getForObject(
	 * env.getTicketUrl() + "getTicketTypeList?org=" + org + "&orgDiv=" + orgDiv,
	 * DropDownModel[].class); List<DropDownModel> ticketTypeList =
	 * Arrays.asList(ticket_type);
	 * 
	 * model.addAttribute("ticketTypeList", ticketTypeList);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); } try {
	 * DropDownModel[] asset_list = restTemplate.getForObject( env.getTicketUrl() +
	 * "getAssetList?org=" + org + "&orgDiv=" + orgDiv+ "&userId=" + userId,
	 * DropDownModel[].class); List<DropDownModel> assetList =
	 * Arrays.asList(asset_list);
	 * 
	 * model.addAttribute("assetList", assetList);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * try { DropDownModel[] ticket_priority = restTemplate.getForObject(
	 * env.getTicketUrl() + "getPriorityList?org=" + org + "&orgDiv=" + orgDiv,
	 * DropDownModel[].class); List<DropDownModel> priorityList =
	 * Arrays.asList(ticket_priority);
	 * 
	 * model.addAttribute("priorityList", priorityList);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * try { DropDownModel[] ticket_source = restTemplate.getForObject(
	 * env.getTicketUrl() + "getSourceListForTicket?org=" + org + "&orgDiv=" +
	 * orgDiv, DropDownModel[].class); List<DropDownModel> sourceList =
	 * Arrays.asList(ticket_source);
	 * 
	 * model.addAttribute("sourceList", sourceList);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * try { DropDownModel[] loc = restTemplate.getForObject(env.getAssetUrl() +
	 * "getLocationListforAsset?org=" + org + "&orgDiv=" + orgDiv + "&userId=" +
	 * userId, DropDownModel[].class); List<DropDownModel> locLists =
	 * Arrays.asList(loc);
	 * 
	 * model.addAttribute("locLists", locLists);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * logger.info("Method : ticketManagementNew ends"); return
	 * "ticket/grievance-Manage-Ticket"; }
	 */

	@SuppressWarnings({ "unchecked" })
	@GetMapping("/view-agentTicket-getcategory")
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
	@GetMapping("/view-agentTicket-getSubcategory")
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

	// Add Ticket.

	@SuppressWarnings("unchecked")
	@PostMapping("/view-agentTicket-save-data")
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

		/*
		 * if (category.get(0).getDocumentList().size() > 0) { for
		 * (TicketDocumentManagementModel a : category.get(0).getDocumentList()) {
		 * String delimiters = "\\."; String[] x = a.getFileName().split(delimiters);
		 * 
		 * for (String s1 : a.getDocumentFile()) { if (s1 != null) { try { byte[] bytes
		 * = Base64.getDecoder().decode(s1); json = saveAllMediaDocuments(bytes,
		 * x[1].toString(), category.get(0).getCreatedBy());
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 * a.setDocumentURL(json.getString("fileurl")); } } } }
		 */
		try {
			resp = restClient.postForObject(env.getTicketUrl() + "rest-add-ticket-dtls", category, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		
		if (resp.getCode().contentEquals("success")) {
			resp.setMessage("Success");
		} else {
			//resp.setMessage("Success");
		}
		/*
		 * String message = resp.getMessage();
		 * 
		 * if (message != null && message != "") {
		 * 
		 * } else { resp.setMessage("Success"); }
		 */

		logger.info("Method : saveTicketDtls starts");
		return resp;
	}

	/*
	 * public JSONObject saveAllMediaDocuments(byte[] imageBytes, String ext, String
	 * user_id) { logger.info("Method : saveAllMedicalDocuments starts");
	 * 
	 * String imageName = null; try { if (imageBytes != null) { long nowTime = new
	 * Date().getTime();
	 * 
	 * 
	 * if (filetype.equals("Video")) { ext = "mp4"; }
	 * 
	 * if (ext.contentEquals("flv") || ext.contentEquals("avi") ||
	 * ext.contentEquals("3gp") || ext.contentEquals("mov") ||
	 * ext.contentEquals("cda") || ext.contentEquals("wav") ||
	 * ext.contentEquals("mkv") || ext.contentEquals("wma") ||
	 * ext.contentEquals("wpl") ) { ext = "mp4"; } if (ext.contentEquals("jpeg")) {
	 * imageName = user_id + "_" + nowTime + ".jpg"; } else { imageName = user_id +
	 * "_" + nowTime + "." + ext; } }
	 * 
	 * Path path = Paths.get(env.getFileUploadticketUrl() + imageName); if
	 * (imageBytes != null) { Files.write(path, imageBytes); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * String url = env.getTrainingUrl() + imageName;
	 * 
	 * JSONObject json = new JSONObject();
	 * 
	 * try { json.put("filename", imageName); } catch (JSONException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } try { json.put("fileurl",
	 * url); } catch (JSONException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * logger.info("Method : saveAllMediaDocuments ends"); return json; }
	 */

	// View Data.

	@SuppressWarnings("unchecked")

	@GetMapping("view-agentTicket-data-view")
	public @ResponseBody Object viewTicketData(@RequestParam String pageno,HttpSession session) {
		logger.info("Method :viewTicketData starts");
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
			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-view-ticket-data?orgName=" + orgName + "&orgDivision=" + orgDivision + "&userId=" + userId+ "&pageno=" + pageno,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getCode().contentEquals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		}

		logger.info("Method :viewTicketData ends");
		return resp;
	}

	
	// Search
	
		@SuppressWarnings("unchecked")

		@GetMapping("view-agentTicket-data-view-search")
		public @ResponseBody Object viewTicketDataSearch(@RequestParam String searchValue ,HttpSession session) {

			logger.info("Method :viewTicketDataSearch starts");
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
				resp = restTemplate.getForObject(
						env.getTicketUrl() + "rest-view-ticket-search?orgName=" + orgName + "&orgDivision="
						+ orgDivision + "&userId=" + userId + "&searchValue=" +searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :viewTicketDataSearch ends");
			
			logger.info(">>>-----"+resp);

			return resp;
		}

	// Edit Ticket.

	@SuppressWarnings("unchecked")
	@GetMapping("view-agentTicket-edit")
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
		/*
		 * if (resp.getMessage() != "" && resp.getMessage() != null) {
		 * resp.setCode(resp.getMessage()); resp.setMessage("Unsuccess"); } else {
		 * resp.setMessage("Success"); }
		 */
		logger.info("Method :editTicket ends");
		return resp;
	}

	// Delete Ticket.

	@SuppressWarnings("unchecked")
	@GetMapping("view-agentTicket-delete")
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
			res = restTemplate.getForObject(env.getTicketUrl() + "rest-ticket-delete?id=" + id + "&userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteTicket function Ends");

		return res;
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping("/view-agentTicket-get-currdept")
	public @ResponseBody JsonResponse<List<DropDownModel>> getCurrentDepartment(HttpSession session) {
		logger.info("Method : getCurrentDepartment starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";
		JsonResponse<List<DropDownModel>> res = new JsonResponse<List<DropDownModel>>();

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient.getForObject(env.getTicketUrl() + "rest-curr-dept?&userid=" + userId + "&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getCurrentDepartment ends");
		return res;

	}

	// Attachemnt View.

	@SuppressWarnings("unchecked")
	@GetMapping("view-agentTicket-attachment")
	public @ResponseBody Object attachmentTicket(@RequestParam String id, HttpSession session) {
		logger.info("Method :attachmentTicket starts");
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-view-agentTicket-attachment?id=" + id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
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
		logger.info("Method :attachmentTicket ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-agentTicket-feedback-save")
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
			resp = restClient.postForObject(env.getTicketUrl() + "rest-view-agentTicket-feedback-save", category, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : saveTicketChatDtls starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-agentTicket-chat-view")
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-view-agentTicket-chat-view?id=" + id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision+ "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewChatData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-agentTicket-rate-save")
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
			res = restTemplate.getForObject(env.getTicketUrl() + "rest-view-agentTicket-rate-save?id=" + id + "&userId=" + userId+ "&rate=" + rate
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : saveTicketRating function Ends");

		return res;
	}

}