package nirmalya.aathithya.webmodule.pipeline.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberFormatter;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.employee.model.EmployeeDocumentModel;
import nirmalya.aathithya.webmodule.master.controller.MasterDataApiController;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmActivityModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCallModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCampaignModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmContactModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmDealModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmLeadTaskModel;
import nirmalya.aathithya.webmodule.pipeline.model.crmMeetingModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesInvoiceNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;
import nirmalya.aathithya.webmodule.user.service.CustomAuthenticationSuccessHandler;

@Controller
@Component
@RequestMapping(value = "pipeline")
public class ContactController {
	Logger logger = LoggerFactory.getLogger(ContactController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

    @Value("${spring.mail.username}")
    private String username;
    
	MasterDataApiController master;

	@Autowired
	public ContactController(MasterDataApiController master, EnvironmentVaribles env) {
		this.master = master;
		this.env = env;

	}

	/*
	 * get mapping for adding campaign name
	 */

	@GetMapping("/view-crm-contacts")
	public String viewCRMContacts(Model model, HttpSession session) {
		logger.info("Method : viewCRMContacts start");
		CrmContactModel ContactModel = new CrmContactModel();
		CrmContactModel form = (CrmContactModel) session.getAttribute("sContactModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}
		session.setAttribute("message", "");
		if (form != null) {
			model.addAttribute("ContactModel", form);
			session.setAttribute("sContactModel", null);

		} else {
			model.addAttribute("ContactModel", ContactModel);
		}

		try {

			DropDownModel[] owner = restClient.getForObject(env.getMasterUrl() + "/getOwnerList",
					DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] states = restClient.getForObject(env.getPipeline() + "/getStatesList",
					DropDownModel[].class);

			List<DropDownModel> statesList = Arrays.asList(states);
			model.addAttribute("statesList", statesList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		try {
			DropDownModel[] countries = restClient.getForObject(env.getPipeline() + "/getCountryList",
					DropDownModel[].class);

			List<DropDownModel> countryList = Arrays.asList(countries);
			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restClient.getForObject(env.getPipeline() + "/getLeadSourceList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("sourceList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();

		}

		logger.info("Method : viewCrmPipeLine end");
		return "pipeline/crm-contacts";
	}

	@GetMapping("/view-crm-contacts-detail")
	public String viewCrmContactsDetails(Model model, @RequestParam String id, HttpSession session) {
		logger.info("Method : viewCrmContactsDetails start");
		logger.info("id url -----------------" + id);
		model.addAttribute("Leadidval", id);

		try {
			String role = (String) session.getAttribute("IS_SALES_MANAGER");
			String userId = "";
			if (role != null && role != "") {
				userId = (String) session.getAttribute("USER_ID");
			} else {
				userId = "";
			}
			DropDownModel[] owner = restClient.getForObject(env.getMasterUrl() + "/getOwnerList?userId=" + userId,
					DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerListtt", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] brand = restTemplate.getForObject(env.getMasterUrl() + "getBrandListForProduct?org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), DropDownModel[].class);
			List<DropDownModel> brandList = Arrays.asList(brand);

			model.addAttribute("brandList", brandList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String Type = "Sales";
			DropDownModel[] mode = restTemplate.getForObject(env.getMasterUrl() + "getModeListForProduct?type=" + Type
					+ "&org=" + CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), DropDownModel[].class);
			List<DropDownModel> modeList = Arrays.asList(mode);

			model.addAttribute("modeList", modeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] variationType = restTemplate
					.getForObject(env.getMasterUrl() + "getVariationTypeListtForProduct", DropDownModel[].class);
			List<DropDownModel> variationTypeList = Arrays.asList(variationType);

			model.addAttribute("variationTypeList", variationTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] countries = restClient.getForObject(env.getPipeline() + "/getCountryList",
					DropDownModel[].class);

			List<DropDownModel> countryList = Arrays.asList(countries);
			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		// leadList

		try {
			String org = "";
			String orgDiv = "";
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			DropDownModel[] lead = restTemplate.getForObject(
					env.getPipeline() + "/getLeadNameList?userId=" + userId + "&org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);

			List<DropDownModel> leadList = Arrays.asList(lead);
			logger.info("leadList" + leadList);
			model.addAttribute("leadList", leadList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("username==="+username);
		model.addAttribute("from_email", username);
		
		logger.info("Method : viewCrmContactsDetails end");
		return "pipeline/crm-contacts-detail";
	}

	/*
	 * post mapping for adding pipeline
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-contacts-add")
	public @ResponseBody JsonResponse<Object> addContact(@RequestBody CrmContactModel contactModel,
			HttpSession session) {

		logger.info("Method : addContact starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addContact ======================" + contactModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			contactModel.setCreatedBy(userId);
			contactModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			contactModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());

			resp = restClient.postForObject(env.getPipeline() + "/addContact", contactModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addContact ends");

		return resp;
	}

	/*
	 * Post Mapping for search view-crm-contact-view-Data-search
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-contacts-view-Data-filter")
	public @ResponseBody JsonResponse<List<CrmContactModel>> viewContactSearchDetails(
			@RequestBody CrmContactModel crmModel, Model model, HttpSession session) {

		logger.info("Method : viewContactSearchDetails starts" + crmModel);

		logger.info("VIEWWWWWWWWWWWDATA" + crmModel);
		JsonResponse<List<CrmContactModel>> resp = new JsonResponse<List<CrmContactModel>>();

		try {

			crmModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			crmModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());

			resp = restClient.postForObject(env.getPipeline() + "viewContactSearchDetails", crmModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CrmContactModel> contactModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmContactModel>>() {
				});
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		for (CrmContactModel i : contactModel) {

			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				logger.info("start date---------------" + date);
			}

			if (i.getOwnerImage() != null && i.getOwnerImage() != "") {
				String fileOwnerImage = env.getBaseURL() + "document/crm/" + i.getOwnerImage();

				logger.info("fileOwnerImage---------" + fileOwnerImage);
				i.setOwnerImage(fileOwnerImage);
			}

		}
		resp.setBody(contactModel);

		logger.info("VIEWMESSAGE" + resp.getMessage());

		if (resp.getMessage() != null || resp.getMessage() != "") {
			resp.setMessage("Success");
		}
		logger.info("CONTACT  VIEWWWWWW" + resp);
		logger.info("Method : viewContactSearchDetails ends");
		return resp;
	}

	/*
	 * View all pipeline
	 *
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-contacts-throughAjax")
	public @ResponseBody List<CrmContactModel> viewAllContact() {

		logger.info("Method : viewAllContact starts");

		// logger.info("fvsjkbhkfvsjk");

		JsonResponse<List<CrmContactModel>> resp = new JsonResponse<List<CrmContactModel>>();

		try {
			// logger.info(env.getPipeline() + "getAllPipeLine");
			resp = restClient.getForObject(
					env.getPipeline() + "/getAllContact?orgName=" + CustomAuthenticationSuccessHandler.org.toString()
							+ "&orgDivision=" + CustomAuthenticationSuccessHandler.orgDiv.toString(),
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		// logger.info("vcxfvfcvf " + resp.getBody());
		return resp.getBody();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("view-crm-contacts-view-Data")
	public @ResponseBody JsonResponse viewCrmContact(HttpSession session, @RequestParam String pageno,
			@RequestParam String userId) {

		logger.info("Method : viewCrmContact starts");
		JsonResponse jsonResponse = new JsonResponse();
		try {

			String orgName = "";
			String orgDivision = "";

			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "getAllContact?pageno=" + pageno + "&userId="
					+ userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewCrmContact ends");
		return jsonResponse;
	}

	/*---------------------------------------------------------------------------*/

	// edit contact

	/**
	 * get mapping for edit pipeline
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-contacts-edit")
	public @ResponseBody JsonResponse<CrmContactModel> editContact(@RequestParam String id, HttpSession session) {

		logger.info("Method : edit Contact starts");

		JsonResponse<CrmContactModel> jsonResponse = new JsonResponse<CrmContactModel>();

		try {
			logger.info(id + "IDIDIDIDID");
			jsonResponse = restClient.getForObject(env.getPipeline() + "/editContact?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		CrmContactModel contactModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<CrmContactModel>() {
				});

		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		/*
		 * if (contactModel.getDateBirth() != null && contactModel.getDateBirth() != "")
		 * { date = DateFormatter.dateFormat(contactModel.getDateBirth(), dateFormat);
		 * contactModel.setDateBirth(date); }
		 */

		jsonResponse.setBody(contactModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : edit Contacts ends");
		logger.info("EDITTTTT==================" + jsonResponse);
		return jsonResponse;

	}

	// view contact

	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-contacts-detailsview")
	public @ResponseBody JsonResponse<CrmContactModel> ViewContact(@RequestParam String id, HttpSession session) {

		logger.info("Method : ViewContact starts");

		JsonResponse<CrmContactModel> jsonResponse = new JsonResponse<CrmContactModel>();

		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "/viewContact?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		CrmContactModel contactModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<CrmContactModel>() {
				});

		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		if (contactModel.getDateBirth() != null && contactModel.getDateBirth() != "") {
			date = DateFormatter.dateFormat(contactModel.getDateBirth(), dateFormat);
			contactModel.setDateBirth(date);
		}

		jsonResponse.setBody(contactModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : ViewContact ends");
		return jsonResponse;

	}

	// Delete Contact Details
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-delete-id")
	public @ResponseBody JsonResponse<Object> deleteContactDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteContactDetails function starts" + id);
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPipeline() + "delete-contact-Details?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteContactDetails function Ends");

		return res;
	}

	/// view-crm-contacts-get-customer-list

	/*
	 * customer Auto search
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-crm-contacts-get-account-list" })
	public @ResponseBody JsonResponse<SalesOrderNewModel> getAccountNameAutoSearchNewList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getAccountNameAutoSearchNewList starts");
		JsonResponse<SalesOrderNewModel> res = new JsonResponse<SalesOrderNewModel>();

		try {

			res = restClient.getForObject(env.getPipeline() + "getAccountNameAutoSearchNewList?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getAccountNameAutoSearchNewList ends");
		return res;
	}

	// view-crm-contacts-view-detail-task

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-view-detail-task")
	public @ResponseBody JsonResponse<List<CrmLeadTaskModel>> viewcontactsTaskInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewcontactsTaskInfo starts" + id);

		JsonResponse<List<CrmLeadTaskModel>> jsonResponse = new JsonResponse<List<CrmLeadTaskModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-ContactTaskInfo?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<CrmLeadTaskModel> taskModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmLeadTaskModel>>() {
				});

		for (CrmLeadTaskModel i : taskModel) {
			if (i.getDueDate() != null && i.getDueDate() != "") {
				date = DateFormatter.dateFormat(i.getDueDate(), dateFormat);
				i.setDueDate(date);
				logger.info("Due date---------------" + date);
			}

		}

		jsonResponse.setBody(taskModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewcontactsTaskInfo ends");
		return jsonResponse;
	}
	// view-crm-contacts-view-detail-meeting

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-view-detail-meeting")
	public @ResponseBody JsonResponse<List<crmMeetingModel>> viewContactMeetingInfo(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : viewContactMeetingInfo starts" + id);

		JsonResponse<List<crmMeetingModel>> jsonResponse = new JsonResponse<List<crmMeetingModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-ContactMeetingInfo?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String date1 = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<crmMeetingModel> meetingModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<crmMeetingModel>>() {
				});

		for (crmMeetingModel i : meetingModel) {
			if (i.getMeetingFromDate() != null && i.getMeetingFromDate() != "") {
				date = DateFormatter.dateFormat(i.getMeetingFromDate(), dateFormat);
				i.setMeetingFromDate(date);
				logger.info("Meeting from date---------------" + date);
			}

			if (i.getMeetingToDate() != null && i.getMeetingToDate() != "") {
				date1 = DateFormatter.dateFormat(i.getMeetingToDate(), dateFormat);
				i.setMeetingToDate(date1);
				logger.info("Meeting to date---------------" + date1);
			}

		}

		jsonResponse.setBody(meetingModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewContactMeetingInfo ends");
		return jsonResponse;
	}

	// ----------------------invited Meetings
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contact-invitedmeetings-detail")
	public @ResponseBody JsonResponse<List<crmMeetingModel>> viewLeadInvitedMeetingInfo(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : viewLeadInvitedMeetingInfo starts" + id);

		JsonResponse<List<crmMeetingModel>> jsonResponse = new JsonResponse<List<crmMeetingModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "invitedMeetingInfo?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<crmMeetingModel> meetingModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<crmMeetingModel>>() {
				});

		jsonResponse.setBody(meetingModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewLeadInvitedMeetingInfo ends");
		return jsonResponse;
	}

	// view-crm-contacts-view-detail-call

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-view-detail-call")
	public @ResponseBody JsonResponse<List<CrmCallModel>> viewcontactsCallInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewcontactsCallInfo starts" + id);

		JsonResponse<List<CrmCallModel>> jsonResponse = new JsonResponse<List<CrmCallModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-ContactCallInfo?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<CrmCallModel> callModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmCallModel>>() {
				});

		for (CrmCallModel i : callModel) {
			if (i.getCallStartDate() != null && i.getCallStartDate() != "") {
				date = DateFormatter.dateFormat(i.getCallStartDate(), dateFormat);
				i.setCallStartDate(date);
				logger.info("Call from date---------------" + date);
			}
		}

		jsonResponse.setBody(callModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewcontactsCallInfo ends");
		return jsonResponse;
	}

	// view-crm-leads-detail-add-task-dtls

	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-contacts-add-task-dtls")
	public @ResponseBody JsonResponse<Object> addTaskByContactsDtls(@RequestBody CrmLeadTaskModel crmLeadTaskModel,
			HttpSession session) {

		logger.info("Method : addTaskByContactsDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addTaskByContactsDtls======================" + crmLeadTaskModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			crmLeadTaskModel.setCreatedBy(userId);
			crmLeadTaskModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			crmLeadTaskModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());

			resp = restClient.postForObject(env.getPipeline() + "/addTask", crmLeadTaskModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addTaskByContactsDtls ends");

		return resp;
	}

	// view-crm-leads-detail-add-meeting-dtls

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-contacts-detail-add-meeting-dtls")
	public @ResponseBody JsonResponse<Object> addMeetingsByContactsDtls(@RequestBody crmMeetingModel meetingModel,
			HttpSession session) {

		logger.info("Method : addMeetingsByContactsDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addMeetingsByContactsDtls lead======================" + meetingModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			meetingModel.setCreatedBy(userId);
			meetingModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			meetingModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());

			resp = restClient.postForObject(env.getPipeline() + "/addMeeting", meetingModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addMeetingsByContactsDtls ends");

		return resp;
	}

	// view-crm-leads-detail-add-call-dtls

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-contacts-detail-add-call-dtls")
	public @ResponseBody JsonResponse<Object> addCallsByContactsDtls(@RequestBody CrmCallModel callModel,
			HttpSession session) {

		logger.info("Method : addCallsByContactsDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addCallsByContactsDtls lead======================" + callModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			callModel.setCreatedBy(userId);
			callModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			callModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());

			resp = restClient.postForObject(env.getPipeline() + "/addCall", callModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : addCallsByContactsDtls ends");

		return resp;
	}

	// view-crm-leads-detail-add-campaign-dtls

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-contacts-detail-add-campaign-dtls")
	public @ResponseBody JsonResponse<Object> addCampaignsByContactDtls(@RequestBody CrmCampaignModel crmCampaignModel,
			HttpSession session) {

		logger.info("Method : addCampaignsByContactDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addCampaignsByContactDtls lead======================" + crmCampaignModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			crmCampaignModel.setCreatedBy(userId);

			crmCampaignModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			crmCampaignModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());

			resp = restClient.postForObject(env.getPipeline() + "/addCampaign", crmCampaignModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addCampaignsByContactDtls ends");

		return resp;
	}

	// view-crm-contact-deals-through-ajax

	/// view
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contact-deals-through-ajax")
	public @ResponseBody JsonResponse<List<CrmDealModel>> viewCrmDealContact(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : View viewCrmDealContact starts" + id);

		JsonResponse<List<CrmDealModel>> resp = new JsonResponse<List<CrmDealModel>>();

		try {
			resp = restClient.getForObject(env.getPipeline() + "restViewDealContact?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CrmDealModel> dealModel = mapper.convertValue(resp.getBody(), new TypeReference<List<CrmDealModel>>() {
		});

		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		String date1 = "";

		for (CrmDealModel i : dealModel) {
			if (i.getDealClosingDate() != null && i.getDealClosingDate() != "") {
				date = DateFormatter.dateFormat(i.getDealClosingDate(), dateFormat);
				i.setDealClosingDate(date);
				logger.info("Due date---------------" + date);
			}

			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date1 = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date1);
				logger.info("start date---------------" + date1);
			}

			if (i.getOwnerImage() != null && i.getOwnerImage() != "") {
				String fileOwnerImage = env.getBaseURL() + "document/crm/" + i.getOwnerImage();

				logger.info("fileOwnerImage---------" + fileOwnerImage);
				i.setOwnerImage(fileOwnerImage);
			}
		}

		logger.info("###" + dealModel);
		resp.setBody(dealModel);

		if (resp.getMessage() == null) {
			resp.setMessage("Success");
		}

		logger.info("views" + resp);
		logger.info("Method :  viewCrmDealContact ends");
		return resp;
	}

//Upcoming deals action

	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-contact-upcoming-deals-actions")
	public @ResponseBody JsonResponse<List<CrmDealModel>> getDealsActions(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : View getDealsActions starts" + id);

		JsonResponse<List<CrmDealModel>> resp = new JsonResponse<List<CrmDealModel>>();

		try {
			resp = restClient.getForObject(env.getPipeline() + "get-deals-actions?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == null) {
			resp.setMessage("Success");
		}

		logger.info("views" + resp);
		logger.info("Method :  getDealsActions ends");
		return resp;
	}

	// view-crm-leads-view-detail-campaign

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-view-detail-campaign")
	public @ResponseBody JsonResponse<List<CrmCampaignModel>> viewContactCampaignInfo(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : viewContactCampaignInfo starts" + id);

		JsonResponse<List<CrmCampaignModel>> jsonResponse = new JsonResponse<List<CrmCampaignModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-viewContactCampaignInfo?id=" + id
					+ "&org=" + CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String date1 = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<CrmCampaignModel> campaignModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmCampaignModel>>() {
				});

		for (CrmCampaignModel i : campaignModel) {
			if (i.getStartDate() != null && i.getStartDate() != "") {
				date = DateFormatter.dateFormat(i.getStartDate(), dateFormat);
				i.setStartDate(date);
			}

			if (i.getEndDate() != null && i.getEndDate() != "") {
				date1 = DateFormatter.dateFormat(i.getEndDate(), dateFormat);
				i.setEndDate(date1);
			}
		}

		jsonResponse.setBody(campaignModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : viewContactCampaignInfo ends");
		return jsonResponse;
	}

/////////////////////////////////// Document save for mail /////////////////////////////////////////////////

	public String saveAllImage(byte[] imageBytes) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				imageName = nowTime + ".png";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + imageName);
			logger.info("path for image write----------" + path);
			if (imageBytes != null) {
				Files.write(path, imageBytes);

				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
				Integer height = 50;
				Integer width = 50;

				try {
					BufferedImage img = ImageIO.read(in);
					if (height == 0) {
						height = (width * img.getHeight()) / img.getWidth();
					}
					if (width == 0) {
						width = (height * img.getWidth()) / img.getHeight();
					}

					Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

					BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

					ByteArrayOutputStream buffer = new ByteArrayOutputStream();

					ImageIO.write(imageBuff, "png", buffer);

					byte[] thumb = buffer.toByteArray();

					Path pathThumb = Paths.get(env.getFileUploadCrmUrl() + "thumb/" + imageName);
					logger.info("thumb image path--------------" + pathThumb);
					Files.write(pathThumb, thumb);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllImage ends");
		return imageName;
	}

	/*
	 * for save all pdf in folder and return name
	 */

	public String saveAllPdf(byte[] imageBytes) {
		logger.info("Method : saveAllPdf starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".pdf";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + pdfName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
				logger.info("path for pdf write----------" + path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllPdf ends");
		return pdfName;
	}

	public String saveAllDocx(byte[] imageBytes) {
		logger.info("Method : saveAllDocx starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".docx";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + pdfName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDocx ends");
		return pdfName;
	}

	public String saveAllDoc(byte[] imageBytes) {
		logger.info("Method : saveAllDoc starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".doc";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + pdfName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDoc ends");
		return pdfName;
	}

	public String saveAllXls(byte[] imageBytes) {
		logger.info("Method : saveAllDoc starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".xls";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + pdfName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDoc ends");
		return pdfName;
	}

	public String saveAllXlsx(byte[] imageBytes) {
		logger.info("Method : saveAllDoc starts");

		String pdfName = null;

		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				pdfName = nowTime + ".xlsx";
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + pdfName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDoc ends");
		return pdfName;
	}

	/*-------------------------------Add product By Pankaj Start------------------------------------*/

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-product-get-sku-listing" })
	public @ResponseBody List<ProductMasterModel> getProductSKUListing(Model model, HttpServletRequest request,
			@RequestParam String type, HttpSession session) {
		logger.info("Method : getProductSKUListing starts");

		JsonResponse<List<ProductMasterModel>> res = new JsonResponse<List<ProductMasterModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getPipeline() + "getProductSKUListing?type=" + type + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ProductMasterModel> product = mapper.convertValue(res.getBody(),
					new TypeReference<List<ProductMasterModel>>() {
					});
			for (ProductMasterModel m : product) {

				if (m.getProductStatus().contentEquals("1")) {
					m.setProductStatus("Active");
				} else {
					m.setProductStatus("Inactive");
				}
				m.setpPrice(NumberFormatter.doubleToStringWithComma(m.getPurchasePrice()));
				m.setsPrice(NumberFormatter.doubleToStringWithComma(m.getSalePrice()));
			}

			res.setBody(product);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getProductSKUListing ends" + res.getBody());
		return res.getBody();

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-product-get-total-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryDataListModal(HttpSession session) {
		logger.info("Method : getProductCategoryDataListModal starts");
		List<Integer> Pankaj = new ArrayList<>();
		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();
		try {
			resp = restTemplate.getForObject(env.getInventoryUrl() + "getProductCategoryDataListModal",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : getProductCategoryDataListModal starts");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-product-save")
	public @ResponseBody JsonResponse<Object> saveProductMaster(@RequestBody ProductMasterModel product,
			HttpSession session) {
		logger.info("Method : saveProductMaster starts");

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

		product.setCreatedBy(userId);
		product.setOrganizationName(orgName);
		product.setOrganizationDivision(orgDivision);
		MultipartFile inputFile = (MultipartFile) session.getAttribute("productPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				product.setpImgName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveProductMaster", product, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
			resp.setCode("");
		}

		logger.info("Method : saveProductMaster starts");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/view-product-save-sku-dtls")
	public @ResponseBody JsonResponse<Object> saveProductDetails(@RequestBody ProductDetailsModel product,
			HttpSession session) {
		logger.info("Method : saveProductDetails starts");

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

		product.setCreatedBy(userId);
		product.setOrganizationName(orgName);
		product.setOrganizationDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveProductDetails", product, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveProductDetails starts");
		return resp;
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadMaster() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);

				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
				Integer height = 280;
				Integer width = 474;

				try {
					BufferedImage img = ImageIO.read(in);
					if (height == 0) {
						height = (width * img.getHeight()) / img.getWidth();
					}
					if (width == 0) {
						width = (height * img.getWidth()) / img.getHeight();
					}

					BufferedImage outputImage = new BufferedImage(width, height, img.getType());

					Graphics2D g2d = outputImage.createGraphics();
					g2d.drawImage(img, 0, 0, width, height, null);
					g2d.dispose();
					String outputImagePath = env.getFileUploadMaster() + "thumb/" + imageName;
					ImageIO.write(outputImage, ext, new File(outputImagePath));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-contacts-add-emails-ajax")
	public @ResponseBody JsonResponse<Object> addContactEmailDoc(
			@RequestBody EmployeeDocumentModel employeeDocumentModel, HttpSession session) throws AddressException {
		logger.info("Method : addContactEmailDoc starts");

		JsonResponse<Object> resp = new JsonResponse<>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error getting user ID from session", e);
		}

		employeeDocumentModel.setCreatedBy(userId);
		employeeDocumentModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
		employeeDocumentModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());

		for (InventoryVendorDocumentModel a : employeeDocumentModel.getDocumentList()) {
			if (a.getImageNameEdit() != null && !a.getImageNameEdit().isEmpty()) {
				logger.info("hello doc getImageNameEdit---------" + a.getImageNameEdit());
				a.setFileName(a.getImageNameEdit());
				String fileUrl = env.getBaseURL() + "document/crm/" + a.getImageNameEdit();
				a.setAction(fileUrl);
			} else {
				logger.info("hello doc list---------" + employeeDocumentModel.getDocumentList());
				if (a.getFileName() != null && !a.getFileName().isEmpty()) {
					String delimiters = "\\.";
					String[] x = a.getFileName().split(delimiters);

					String fileUrl = null;
					String fileName = null;
					for (String s1 : a.getDocumentFile()) {
						if (s1 != null) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								fileName = saveAllDocuments(bytes, x[1].toString(), userId);
								a.setFileName(fileName);
							} catch (Exception e) {
								logger.error("Error decoding Base64 and saving documents", e);
							}
							fileUrl = env.getBaseURL() + "document/crm/" + fileName;
							System.out.println("@@@@fileUrl" + fileUrl);
							a.setAction(fileUrl);
						}
					}
				}
			}
		}

		try {
			// logger.info("ADDDDOCCCC" + employeeDocumentModel);
			resp = restClient.postForObject(env.getPipeline() + "addContactEmailDoc", employeeDocumentModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			logger.error("Error calling addContactEmailDoc API", e);
			resp.setMessage("Error calling addContactEmailDoc API");
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}

		logger.info("Method : addContactEmailDoc end");
		logger.info("ADDDDDOCCC" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-contacts-add-drafts")
	public @ResponseBody JsonResponse<Object> addContactDraftDoc(
			@RequestBody EmployeeDocumentModel employeeDocumentModel, HttpSession session) {
		logger.info("Method : addContactDraftDoc");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		employeeDocumentModel.setCreatedBy(userId);
		employeeDocumentModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
		employeeDocumentModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());
		for (InventoryVendorDocumentModel a : employeeDocumentModel.getDocumentList()) {
			if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
				a.setFileName(a.getImageNameEdit());
				String fileUrl = env.getBaseURL() + "document/crm/" + a.getImageNameEdit();

				a.setAction(fileUrl);
			} else {
				if (a.getFileName() != null && a.getFileName() != "") {
					String delimiters = "\\.";
					String[] x = a.getFileName().split(delimiters);

					String fileUrl = null;
					String fileName = null;
					for (String s1 : a.getDocumentFile()) {
						if (s1 != null) {
							try {
								byte[] bytes = Base64.getDecoder().decode(s1);
								fileName = saveAllDocuments(bytes, x[1].toString(), userId);
								a.setFileName(fileName);
							} catch (Exception e) {
								e.printStackTrace();
							}
							fileUrl = env.getBaseURL() + "document/crm/" + fileName;
							System.out.println("@@@@fileUrl --" + fileUrl);
							a.setAction(fileUrl);
						}
					}

				}
			}
		}

		try {
			resp = restClient.postForObject(env.getPipeline() + "addContactDraftDoc", employeeDocumentModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("success");
		}
		logger.info("Method : addContactDraftDoc end");
		return resp;
	}

	public String saveAllDocuments(byte[] imageBytes, String ext, String user_id) {
		logger.info("Method : saveAllDocuments starts");
		String imageName = null;
		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();

				if (ext.contentEquals("jpeg") || ext.contentEquals("jpg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}
			}

			Path path = Paths.get(env.getFileUploadCrmUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDocuments ends");
		return imageName;
	}
	/// view-crm-contacts-view-detail-activity

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-contacts-view-detail-activity")
	public @ResponseBody JsonResponse<List<CrmActivityModel>> viewLeadActivityInfo(Model model, @RequestParam String id,
			@RequestParam String type, HttpSession session) {

		logger.info("Method : viewLeadActivityInfo starts" + id);

		JsonResponse<List<CrmActivityModel>> jsonResponse = new JsonResponse<List<CrmActivityModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-LeadActivityInfo?id=" + id + "&type="
					+ type + "&org=" + CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CrmActivityModel> activityModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmActivityModel>>() {
				});

		jsonResponse.setBody(activityModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewLeadActivityInfo ends");
		return jsonResponse;
	}

	/**
	 * Customer Creation
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/customer-page-modal-adds")
	public @ResponseBody JsonResponse<Object> addAccountCustomer(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {

		logger.info("Method : addAccountCustomer starts");
		String organization = "";
		String orgDivision = "";
		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		customerNewModel.setOrganization(organization);
		customerNewModel.setOrgDivision(orgDivision);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web AccountModel ======================" + customerNewModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("created by id-------------------------------" + userId);

			customerNewModel.setCreatedBy(userId);

			resp = restTemplate.postForObject(env.getSalesUrl() + "/addAccountCustomer", customerNewModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addAccountCustomer ends");

		return resp;
	}

	// view-customer-stateList
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-customer-stateList" })
	public @ResponseBody JsonResponse<Object> getstateCusList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getStateLists1?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : getstateCusList ends");
		return res;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/customer-modal-save-shipping-address")
	public @ResponseBody JsonResponse<Object> saveShippingAddressDetails(@RequestBody CustomerNewModel customerNewModel,
			HttpSession session) {
		logger.info("Method : saveAddressDetails starts");

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

		customerNewModel.setCreatedBy(userId);
		customerNewModel.setOrganization(orgName);
		customerNewModel.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getSalesUrl() + "saveAddressDetails", customerNewModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveAddressDetails starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("customer-modal-shippingdetails")
	public @ResponseBody Object viewShippingDetails(HttpSession session, @RequestParam String customerIdd) {
		logger.info("Method :viewShippingDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+customerId);
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewShippingDetails?customerIdd=" + customerIdd
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewShippingDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("customer-modal-edit-address")
	public @ResponseBody Object editShippingDetails(HttpSession session, @RequestParam String addressId) {
		logger.info("Method :editShippingDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-editShippingDetails?addressId=" + addressId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editShippingDetails ends");
		return resp;
	}

	@GetMapping("customer-modal-address-delete")
	public @ResponseBody Object deleteaddressdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteaddressdata starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-deleteaddressdata?deleteId=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-pdf-downloads")
	public void getInvoicePdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("saleInvoice") String encodedParam1, @RequestParam("organization") String encodedParam3,
			@RequestParam("orgDivision") String encodedParam4) {

		logger.info("Method : getInvoicePdfDetails starts");

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String invIdd = (new String(encodeByte1));

		/*
		 * byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		 * String copytype = (new String(encodeByte2));
		 */

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String organization = (new String(encodeByte3));

		byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam4.getBytes());
		String orgDivision = (new String(encodeByte4));

		logger.info("invIdd====" + invIdd);

		List<SalesInvoiceNewModel> productList = new ArrayList<SalesInvoiceNewModel>();
		try {
			SalesInvoiceNewModel[] salesInvoiceNewModel = restTemplate
					.getForObject(env.getSalesUrl() + "viewsales-invoice-viewPdf?id=" + invIdd + "&organization="
							+ organization + "&orgDivision=" + orgDivision, SalesInvoiceNewModel[].class);
			productList = Arrays.asList(salesInvoiceNewModel);
			productList.forEach(s -> s.setSlNo(s.getSlNo()));
			int count = 0;
			for (SalesInvoiceNewModel m : salesInvoiceNewModel) {
				count++;
				m.setSlNo(count);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String id = productList.get(0).getCustId();
		logger.info("id====" + id);
		JsonResponse<CustomerNewModel> jsonResponse = new JsonResponse<CustomerNewModel>();
		try {
			jsonResponse = restTemplate.getForObject(env.getSalesUrl() + "getCustomerAddressById?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		CustomerNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<CustomerNewModel>() {
		});
		logger.info("JsonResponse====" + jsonResponse);
		logger.info("reimModel====" + reimModel);
		logger.info("productList====" + productList);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("invoice", productList);

		// String logo = "";
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);
		// data.put("copytype", copytype);
		data.put("buyer", reimModel);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=salesInvoice.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("sales/sales-invoice-pdf", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// logger.info("REsp" + jsonResponse);
		logger.info("Method : getInvoicePdfDetails ends");
	}

}
