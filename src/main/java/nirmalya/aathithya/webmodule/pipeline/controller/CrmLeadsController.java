package nirmalya.aathithya.webmodule.pipeline.controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberFormatter;
import nirmalya.aathithya.webmodule.employee.model.EmployeeDocumentModel;
import nirmalya.aathithya.webmodule.master.controller.MasterDataApiController;
import nirmalya.aathithya.webmodule.master.model.ProductDetailsModel;
import nirmalya.aathithya.webmodule.pipeline.model.AdminTaskAssignModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmActivityModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCallModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCampaignModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmDocumentModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmLeadTaskModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmLeadsModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmProductModel;
import nirmalya.aathithya.webmodule.pipeline.model.crmMeetingModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectFileuploadModel;
import nirmalya.aathithya.webmodule.sales.controller.OpenProjectModalWebController;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;
import nirmalya.aathithya.webmodule.user.service.CustomAuthenticationSuccessHandler;

/*
 * Encryption and Decryption
 */
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;

@Controller
@RequestMapping(value = "pipeline")
public class CrmLeadsController {

	Logger logger = LoggerFactory.getLogger(CrmLeadsController.class);

	RestTemplate restTemplate;

	EnvironmentVaribles env;

	MasterDataApiController master;

    @Value("${spring.mail.username}")
    private String username;
    
	List<String> role = new ArrayList<String>();

	@Autowired
	public CrmLeadsController(MasterDataApiController master, EnvironmentVaribles env, RestTemplate restTemplate) {
		this.master = master;
		this.env = env;
		this.restTemplate = restTemplate;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-leads-detail")
	public String viewCrmLeadsDetails(Model model, @RequestParam String id, HttpSession session) {
		logger.info("Method : viewCrmLeadsDetails start");
		logger.info("id url -----------------" + id);
		model.addAttribute("Leadidval", id);
		logger.info("Method : viewCrmLeadsDetails end");

		try {
			// org = (String) session.getAttribute("ORGANIZATION");
			// orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			role = (List<String>) session.getAttribute("USER_ROLES");

			Optional<String> data = role.stream().filter(d -> d.equals("rol001")).findAny();
			if (data.isPresent())
				model.addAttribute("adminRole", data.get());

			Optional<String> userroledata = role.stream().filter(d -> d.equals("rol037")).findAny();
			if (userroledata.isPresent())
				model.addAttribute("userRole", userroledata.get());

			final String userId = (String) session.getAttribute("USER_ID");
			model.addAttribute("userId", userId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			List<DropDownModel> ownerList = master.getOwnerList(session);
			;
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
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
			DropDownModel[] campaign = restTemplate.getForObject(env.getPipeline() + "/getDealCampaignList",
					DropDownModel[].class);

			List<DropDownModel> campaignList = Arrays.asList(campaign);
			model.addAttribute("campaignList", campaignList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			logger.info(env.getPipeline());
			DropDownModel[] status = restTemplate.getForObject(env.getPipeline() + "/getLeadStatusList",
					DropDownModel[].class);

			List<DropDownModel> statusList = Arrays.asList(status);
			model.addAttribute("statusList", statusList);
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
			e.printStackTrace();
		}
		System.out.println("username==="+username);
		model.addAttribute("from_email", username);
		return "pipeline/crm-leads-detail";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-product-get-sku-by-product" })
	public @ResponseBody List<ProductDetailsModel> getSKUListingById(Model model, @RequestParam String id,
			HttpServletRequest request, HttpSession session) {
		logger.info("Method : getSKUListingById starts");

		JsonResponse<List<ProductDetailsModel>> res = new JsonResponse<List<ProductDetailsModel>>();

		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getSKUListingById?id=" + id + "&orgName=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<ProductDetailsModel> product = mapper.convertValue(res.getBody(),
					new TypeReference<List<ProductDetailsModel>>() {
					});

			for (ProductDetailsModel m : product) {
				m.setCreatedDate(DateFormatter.dateFormat(m.getCreatedDate(), dateFormat));
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
		logger.info("Method : getSKUListingById ends");
		return res.getBody();

	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-product-get-purchase-by-product" })
	public @ResponseBody List<ProductDetailsModel> getSKUPurchaseListing(Model model, @RequestParam String id,
			HttpServletRequest request, HttpSession session) {
		logger.info("Method : getSKUPurchaseListing starts");

		JsonResponse<List<ProductDetailsModel>> res = new JsonResponse<List<ProductDetailsModel>>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getSKUPurchaseListingById?id=" + id + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<ProductDetailsModel> product = mapper.convertValue(res.getBody(),
					new TypeReference<List<ProductDetailsModel>>() {
					});

			String dateFormat = "";

			try {
				dateFormat = (String) session.getAttribute("DATEFORMAT");
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (ProductDetailsModel m : product) {
				m.setCreatedDate(DateFormatter.dateFormat(m.getCreatedDate(), dateFormat));
				m.setsPrice(NumberFormatter.doubleToStringWithComma(m.getSalePrice()));
				m.setsMoq(NumberFormatter.doubleToStringWithComma(m.getMoq()));
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
		logger.info("Method : getSKUPurchaseListing ends");
		return res.getBody();

	}

	/*
	 * get mapping for view-crm-leads
	 * 
	 */

	@GetMapping("/view-crm-leads")
	public String viewCrmLeads(Model model, HttpSession session) {
		logger.info("Method : viewCrmLeads start");
		// PipelineModel PipelineModel = new PipelineModel();
		// PipelineModel form = (PipelineModel) session.getAttribute("sPipelineModel");

		final String userId = (String) session.getAttribute("USER_ID");
		model.addAttribute("userId", userId);

		try {
			logger.info(env.getPipeline());
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getSourceList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("customerList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			logger.info(env.getPipeline());
			DropDownModel[] status = restTemplate.getForObject(env.getPipeline() + "/getLeadStatusList",
					DropDownModel[].class);

			List<DropDownModel> statusList = Arrays.asList(status);
			model.addAttribute("statusList", statusList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
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

		try {
			logger.info(env.getPipeline());
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getDocumentList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("documentTypeList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getLeadList",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("leadList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getindustrylist",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("industryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] rating = restTemplate.getForObject(env.getPipeline() + "/getRatingList",
					DropDownModel[].class);

			List<DropDownModel> ratingList = Arrays.asList(rating);
			model.addAttribute("ratingList", ratingList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restTemplate.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String org = "";
			String orgDiv = "";

			try {
				// userId = (String) session.getAttribute("USER_ID");
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			DropDownModel[] projectList = restTemplate.getForObject(
					env.getPipeline() + "getProjectAutoSearchList?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> projectListt = Arrays.asList(projectList);
			model.addAttribute("projectList", projectListt);
			System.out.println("Project List------>" + projectListt);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewCrmLeads end");
		return "pipeline/crm-leads";
	}

	@GetMapping("/crm-user-assign-tasks")
	public String viewTaskAssignedByAdmin(Model model, HttpSession session) {
		logger.info("Method : viewTaskAssignedByAdmin starts");

		final String userId = (String) session.getAttribute("USER_ID");
		model.addAttribute("userId", userId);

		List<DropDownModel> executiveList = master.getOwnerList(session);
		;
		model.addAttribute("executive", executiveList);

		List<DropDownModel> status = master.getCrmTaskStatus();
		model.addAttribute("status", status);

		logger.info("Method : viewTaskAssignedByAdmin end");

		return "pipeline/crm-user-tasks";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/crm-user-tasks/change-status")
	public @ResponseBody JsonResponse<Object> updateTaskStatus(@RequestBody AdminTaskAssignModel taskAssign) {

		logger.info("Method : updateTaskStatus starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.postForObject(env.getPipeline() + "update-task-status", taskAssign, JsonResponse.class);
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
		}

		logger.info("Method : updateTaskStatus ends");

		return resp;
	}

	// view-crm-leads-detail-add-task-dtls

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-leads-detail-add-task-dtls")
	public @ResponseBody JsonResponse<Object> addTaskByLeadDtls(@RequestBody CrmLeadTaskModel crmLeadTaskModel,
			HttpSession session) {

		logger.info("Method : addTaskByLeadDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addTaskByLeadDtls lead======================" + crmLeadTaskModel);
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

			resp = restTemplate.postForObject(env.getPipeline() + "/addTask", crmLeadTaskModel, JsonResponse.class);

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
		logger.info("Method : addTaskByLeadDtls ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-leads-detail-add-meeting-dtls")
	public @ResponseBody JsonResponse<Object> addMeetingsByLeadDtls(@RequestBody crmMeetingModel meetingModel,
			HttpSession session) {

		logger.info("Method : addMeetingsByLeadDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addMeetingsByLeadDtls lead======================" + meetingModel);
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

			String toMail = meetingModel.getToMail();
			if (toMail == null || toMail == "" || toMail == "null") {
				meetingModel.setToMail((String) session.getAttribute("accountMailTo"));
			}

			resp = restTemplate.postForObject(env.getPipeline() + "/addMeeting", meetingModel, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addMeetingsByLeadDtls ends");

		return resp;
	}

	// view-crm-leads-detail-add-call-dtls

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-leads-detail-add-call-dtls")
	public @ResponseBody JsonResponse<Object> addCallsByLeadDtls(@RequestBody CrmCallModel callModel,
			HttpSession session) {

		logger.info("Method : addCallsByLeadDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addCallsByLeadDtls lead======================" + callModel);
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
			callModel.setToMail((String) session.getAttribute("accountMailTo"));

			resp = restTemplate.postForObject(env.getPipeline() + "/addCall", callModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : addCallsByLeadDtls ends");

		return resp;
	}

	// view-crm-leads-detail-add-campaign-dtls

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-leads-detail-add-campaign-dtls")
	public @ResponseBody JsonResponse<Object> addCampaignsByLeadDtls(@RequestBody CrmCampaignModel crmCampaignModel,
			HttpSession session) {

		logger.info("Method : addCampaignsByLeadDtls starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addCampaignsByLeadDtls lead======================" + crmCampaignModel);
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

			resp = restTemplate.postForObject(env.getPipeline() + "/addCampaign", crmCampaignModel, JsonResponse.class);

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

		logger.info("Method : addCampaignsByLeadDtls ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-leads-view-Data-search")
	public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewLeadSearchDetails(@RequestBody CrmLeadsModel crmModel,
			Model model, HttpSession session) {

		logger.info("Method : viewLeadSearchDetails starts" + crmModel);

		logger.info("VIEWWWWWWWWWWWDATA" + crmModel);
		JsonResponse<List<CrmLeadsModel>> resp = new JsonResponse<List<CrmLeadsModel>>();

		try {

			resp = restTemplate.postForObject(env.getPipeline() + "viewLeadSearchDetails", crmModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CrmLeadsModel> quotationNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmLeadsModel>>() {
				});
		String drProfDoc = null;
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		for (CrmLeadsModel i : quotationNewModel) {
			if (i.getImageName() != null && i.getImageName() != "") {

				String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();

				logger.info("Image" + fileEmployeeimg);
				i.setImageName(fileEmployeeimg);
			}

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

		resp.setBody(quotationNewModel);

		logger.info("VIEWMESSAGE" + resp.getMessage());

		if (resp.getMessage() != null || resp.getMessage() != "") {
			resp.setMessage("Success");
		}
		logger.info("Method : viewLeadSearchDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-crm-leads-stateList" })
	public @ResponseBody JsonResponse<Object> getstateList(@RequestParam String id) {
		logger.info("Method : getstateListAJAX starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getPipeline() + "getStateLists?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getstateListAJAX ends");
		return res;
	}

	/*
	 * Post Mapping for adding new assignSkill
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-leads-add-lead-details")
	public @ResponseBody JsonResponse<Object> addassignSkillMasterPost(@RequestBody CrmLeadsModel crmModel, Model model,
			HttpSession session) {

		logger.info("Method : addassignSkillMasterPost starts" + crmModel);

		// Handling Image Upload (Existing Logic)
		MultipartFile inputFile = (MultipartFile) session.getAttribute("employeePFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				crmModel.setImageName(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		// Initialize Response Object
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			// Fetch user and organization details from the session
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

			// Setting values for crmModel
			crmModel.setCreatedBy(userId);
			crmModel.setOrganization(orgName);
			crmModel.setOrgDivision(orgDivision);

			// Document Handling (Newly Added)
			if (crmModel.getDocumentList() != null) {
				String fileUrl = null;
				for (InventoryVendorDocumentModel a : crmModel.getDocumentList()) {
					if (a.getImageNameEdit() != null && !a.getImageNameEdit().isEmpty()) {
						// If file name is already provided, use that
						logger.info("hello doc getImageNameEdit---------" + a.getImageNameEdit());
						a.setFileName(a.getImageNameEdit());
						fileUrl = env.getBaseURL() + "document/crm/" + a.getImageNameEdit();
						System.out.println("@@@@fileUrl" + fileUrl);
						a.setDocumnentName(fileUrl);
					} else {
						// Handling base64 encoded file data
						if (a.getFileName() != null && !a.getFileName().isEmpty()) {
							String[] x = a.getFileName().split("\\.");
							String fileName = null;

							for (String s1 : a.getDocumentFile()) {
								if (s1 != null) {
									try {
										byte[] fileBytes = Base64.getDecoder().decode(s1);
										fileName = saveAllDocuments(fileBytes, x[1], userId);
										a.setFileName(fileName);
									} catch (Exception e) {
										e.printStackTrace();
									}
									fileUrl = env.getBaseURL() + "document/crm/" + fileName;
									System.out.println("@@@@fileUrl" + fileUrl);
									a.setDocumnentName(fileUrl);
								}
							}
						}
					}
				}
			}
			System.out.println("Lead Detailsssss================>" + crmModel);
			// Calling the external service with the updated crmModel
			resp = restTemplate.postForObject(env.getPipeline() + "rest-add-lead-details", crmModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addassignSkillMasterPost ends" + resp);
		return resp;
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;
		Path imagePath = null;
		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			logger.info("imageNAme in save image------------------" + imageName);

			Path path = Paths.get(env.getFileUploadCrmUrl() + imageName);

			logger.info("path in save image------------------" + path);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
				// crm.setImagePath(path);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends" + imagePath);
		return imageName;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("view-crm-leads-view-Data")
	public @ResponseBody JsonResponse viewLeadsDetails(HttpSession session, @RequestParam String pageno,
			@RequestParam String userId ,@RequestParam String fromDate,@RequestParam String toDate) {

		logger.info("Method : viewLeadsDetails starts");
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
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "rest-viewLeadDet?pageno=" + pageno
					+ "&userId=" + userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision +"&fromDate="+fromDate+"&toDate="+toDate, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewLeadsDetails ends");
		return jsonResponse;
	}

	/*--------Check Duplicate Email And Phone For Lead----------------*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("view-crm-leads-duplicateentry-check")
	public @ResponseBody JsonResponse checkDuplicateEmailPhone(HttpSession session, @RequestParam String email,
			@RequestParam String phone, @RequestParam String mobile, @RequestParam String leadid) {

		logger.info("Method : checkDuplicateEmailPhone starts");
		JsonResponse jsonResponse = new JsonResponse();
		try {

			jsonResponse = restTemplate.getForObject(
					env.getPipeline() + "rest-duplicateCheck?email=" + email + "&phone=" + phone + "&mobile=" + mobile
							+ "&leadid=" + leadid + "&org=" + CustomAuthenticationSuccessHandler.org.toString()
							+ "&orgDiv=" + CustomAuthenticationSuccessHandler.orgDiv.toString(),
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : checkDuplicateEmailPhone ends");
		return jsonResponse;
	}

	/// edit
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-editDetails")
	public @ResponseBody JsonResponse<List<CrmLeadsModel>> editLeadInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editLeadInfo starts" + id);

		JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "edit-rest-LeadInfo?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<CrmLeadsModel> quotationNewModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmLeadsModel>>() {
				});
		String drProfDoc = null;
		for (CrmLeadsModel i : quotationNewModel) {
			if (i.getImageName() != null && i.getImageName() != "") {
				String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();

				logger.info("Image" + fileEmployeeimg);
				i.setImageName(fileEmployeeimg);
			}
		}

		logger.info("###" + quotationNewModel);
		jsonResponse.setBody(quotationNewModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editLeadInfo ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-add-customer")
	public @ResponseBody JsonResponse<List<CustomerNewModel>> addCustomer(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editLeadInfo starts" + id);

		JsonResponse<List<CustomerNewModel>> jsonResponse = new JsonResponse<List<CustomerNewModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "edit-rest-add-customer?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : addCustomer ends");
		return jsonResponse;
	}

	// view-crm-leads-view-Details

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-view-detail")
	public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewLeadInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewLeadInfo starts" + id);

		JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
		Base64.getDecoder().decode(id);
		try {

			jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadInfo?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewLeadInfo ends");
		return jsonResponse;
	}

	// view-crm-leads-detail-converted

	/*
	 * post mapping for converted lead to Contact Account
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-leads-detail-converted")
	public @ResponseBody JsonResponse<Object> leadConvertedToAccContDeal(@RequestBody CrmLeadsModel crmLeadsModel,
			HttpSession session) {

		logger.info("Method : leadConvertedToAccContDeal starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			crmLeadsModel.setCreatedBy(userId);
			crmLeadsModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			crmLeadsModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());
			System.out.println("crmLeadsModel=========" + crmLeadsModel);
			resp = restTemplate.postForObject(env.getPipeline() + "/convertToAccContDeal", crmLeadsModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		System.out.println("===============>>>>>>>>>>>>>>" + resp);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : leadConvertedToAccContDeal ends");

		return resp;
	}

	// view-crm-leads-view-note-Details

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-view-detail-note")
	public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewLeadNoteInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewLeadNoteInfo starts" + id);

		JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
		try {

			jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadNoteInfo?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<CrmLeadsModel> leadModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmLeadsModel>>() {
				});

		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		for (CrmLeadsModel i : leadModel) {
			if (i.getNoteDoc() != null && i.getNoteDoc() != "") {
				String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getNoteDoc();
				logger.info("image for document--------" + fileEmployeeimg);
				i.setNoteDocLink(fileEmployeeimg);
			}

			if (i.getLeadImage() != null && i.getLeadImage() != "") {
				String leadImage = env.getBaseURL() + "document/crm/" + i.getLeadImage();
				logger.info("image for getLeadImage--------" + leadImage);
				i.setLeadImageLink(leadImage);
			}

			if (i.getOwnerImage() != null && i.getOwnerImage() != "") {
				String ownerImage = env.getBaseURL() + "document/crm/" + i.getOwnerImage();
				logger.info("image for ownerImage--------" + ownerImage);
				i.setOwnerImageLink(ownerImage);
			}

			if (i.getContactOwnerImage() != null && i.getContactOwnerImage() != "") {
				String contactOwnerImage = env.getBaseURL() + "document/crm/" + i.getContactOwnerImage();
				logger.info("image for contactOwnerImage--------" + contactOwnerImage);
				i.setContactOwnerLink(contactOwnerImage);
			}

			if (i.getAccountOwnerImage() != null && i.getAccountOwnerImage() != "") {
				String accountOwnerImage = env.getBaseURL() + "document/crm/" + i.getAccountOwnerImage();
				logger.info("image for account owner Image--------" + accountOwnerImage);
				i.setAccountOwnerImageLink(accountOwnerImage);
			}

			if (i.getDealOwnerImage() != null && i.getDealOwnerImage() != "") {
				String dealOwnerImage = env.getBaseURL() + "document/crm/" + i.getDealOwnerImage();
				logger.info("image for deal owner Image--------" + dealOwnerImage);
				i.setDealOwnerImageLink(dealOwnerImage);
			}

			if (i.getQuoteOwnerImage() != null && i.getQuoteOwnerImage() != "") {
				String quoteOwnerImage = env.getBaseURL() + "document/crm/" + i.getQuoteOwnerImage();
				logger.info("image for quote owner Image--------" + quoteOwnerImage);
				i.setQuoteOwnerImageLink(quoteOwnerImage);
			}

			if (i.getSoOwnerImage() != null && i.getSoOwnerImage() != "") {
				String soOwnerImage = env.getBaseURL() + "document/crm/" + i.getSoOwnerImage();
				logger.info("image for so owner Image--------" + soOwnerImage);
				i.setSoOwnerImageLink(soOwnerImage);
			}

			if (i.getPoOwnerImage() != null && i.getPoOwnerImage() != "") {
				String poOwnerImage = env.getBaseURL() + "document/crm/" + i.getPoOwnerImage();
				logger.info("image for po owner Image--------" + poOwnerImage);
				i.setPoOwnerImageLink(poOwnerImage);
			}

			if (i.getInvOwnerImage() != null && i.getInvOwnerImage() != "") {
				String invOwnerImage = env.getBaseURL() + "document/crm/" + i.getInvOwnerImage();
				logger.info("image for invoice owner Image--------" + invOwnerImage);
				i.setInvOwnerImageLink(invOwnerImage);
			}

			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				logger.info("start date---------------" + date);
			}
		}

		logger.info("###" + leadModel);
		jsonResponse.setBody(leadModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewLeadNoteInfo ends");
		return jsonResponse;
	}

	//// view-crm-leads-view-detail-mail

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-view-detail-mail")
	public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewLeadMailInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewLeadMailInfo starts" + id);

		JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadMailInfo?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewLeadMailInfo ends");
		return jsonResponse;
	}

	// view-crm-leads-view-detail-product

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-view-detail-product")
	public @ResponseBody JsonResponse<List<CrmLeadsModel>> viewLeadProductInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewLeadProductInfo starts" + id);

		JsonResponse<List<CrmLeadsModel>> jsonResponse = new JsonResponse<List<CrmLeadsModel>>();
		try {

			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadProductInfo?id=" + id
					+ "&userId=" + userId + "&org=" + CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		/*
		 * String supportStartDate = ""; String supportEndDate = ""; String dateFormat =
		 * (String) (session).getAttribute("DATEFORMAT");
		 */

		List<CrmLeadsModel> crmLeadsModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmLeadsModel>>() {
				});

		jsonResponse.setBody(crmLeadsModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewLeadProductInfo ends");
		return jsonResponse;
	}

	// view-crm-leads-view-detail-campaign

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-view-detail-campaign")
	public @ResponseBody JsonResponse<List<CrmCampaignModel>> viewLeadCampaignInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewLeadCampaignInfo starts" + id);

		JsonResponse<List<CrmCampaignModel>> jsonResponse = new JsonResponse<List<CrmCampaignModel>>();
		try {

			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			jsonResponse = restTemplate
					.getForObject(env.getPipeline() + "view-rest-LeadCampaignInfo?id=" + id + "&userId=" + userId
							+ "&userId=" + userId + "&org=" + CustomAuthenticationSuccessHandler.org.toString()
							+ "&orgDiv=" + CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

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
				logger.info("start date---------------" + date);
			}

			if (i.getEndDate() != null && i.getEndDate() != "") {
				date1 = DateFormatter.dateFormat(i.getEndDate(), dateFormat);
				i.setEndDate(date1);
				logger.info("end date---------------" + date1);
			}
		}

		jsonResponse.setBody(campaignModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewLeadCampaignInfo ends");
		return jsonResponse;
	}

	// view-crm-leads-view-detail-task

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-view-detail-task")
	public @ResponseBody JsonResponse<List<CrmLeadTaskModel>> viewLeadTaskInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewLeadTaskInfo starts" + id);

		JsonResponse<List<CrmLeadTaskModel>> jsonResponse = new JsonResponse<List<CrmLeadTaskModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadTaskInfo?id=" + id + "&org="
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
		logger.info("Method : viewLeadTaskInfo ends");
		return jsonResponse;
	}

	/* Upcoming Task Actions API'S */

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-lead-upcoming-task-actions")
	public @ResponseBody JsonResponse<List<CrmLeadTaskModel>> getTaskActions(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : getTaskActions starts" + id);

		JsonResponse<List<CrmLeadTaskModel>> jsonResponse = new JsonResponse<List<CrmLeadTaskModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "get-Task-Actions?id=" + id + "&org="
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

		/*
		 * if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		 * 
		 * } else { jsonResponse.setMessage("Success"); }
		 */
		logger.info("REsp" + jsonResponse);
		logger.info("Method : getTaskActions ends");
		return jsonResponse;
	}

	// view-crm-leads-view-detail-meeting

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-view-detail-meeting")
	public @ResponseBody JsonResponse<List<crmMeetingModel>> viewLeadMeetingInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewLeadMeetingInfo starts" + id);

		JsonResponse<List<crmMeetingModel>> jsonResponse = new JsonResponse<List<crmMeetingModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadMeetingInfo?id=" + id + "&org="
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
		logger.info("Method : viewLeadMeetingInfo ends");
		return jsonResponse;
	}

	// ----------------------invited Meetings
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-invitedmeetings-detail")
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

	// view-crm-leads-view-detail-call

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-view-detail-call")
	public @ResponseBody JsonResponse<List<CrmCallModel>> viewLeadCallInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewLeadCallInfo starts" + id);

		JsonResponse<List<CrmCallModel>> jsonResponse = new JsonResponse<List<CrmCallModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadCallInfo?id=" + id + "&org="
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
		logger.info("Method : viewLeadCallInfo ends");
		return jsonResponse;
	}

	// view-crm-leads-view-detail-activity

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-view-detail-activity")
	public @ResponseBody ResponseEntity<JsonResponse<List<CrmActivityModel>>> viewLeadActivityInfo(Model model,
			@RequestParam String id, @RequestParam String type, HttpSession session) {

		JsonResponse<List<CrmActivityModel>> jsonResponse = new JsonResponse<List<CrmActivityModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "view-rest-LeadActivityInfo?id=" + id
					+ "&type=" + type + "&org=" + CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
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

		ResponseEntity<JsonResponse<List<CrmActivityModel>>> response = new ResponseEntity<JsonResponse<List<CrmActivityModel>>>(
				jsonResponse, HttpStatus.CREATED);

		logger.info("Method : viewLeadActivityInfo ends");
		return response;
	}

	/*
	 * upload image
	 */
	@PostMapping("/view-crm-leads-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : employee uploadimage controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			// logger.info(inputFile);
			session.setAttribute("employeePFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : employee uploadimage controller ' ends" + response);
		return response;
	}

	@PostMapping("/view-crm-leads-delete-file")
	public @ResponseBody JsonResponse<Object> deleteFile(HttpSession session) {
		logger.info("Method : deleteFile employee uploadimage controller starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			session.removeAttribute("employeePFile");
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteFile employee uploadimage controller ends");
		return response;
	}

	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("view-crm-leads-deleteDetails")
	public @ResponseBody JsonResponse<Object> deleteDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteDetails function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getPipeline() + "delete-Details?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteDetails function Ends");

		logger.info("RESPPPPPPP" + res);
		return res;
	}

	/*
	 * //Main save for Task
	 * 
	 */

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "view-crm-leads-save-task")
	public @ResponseBody JsonResponse<Object> saveCRMLeadTask(@RequestBody List<CrmLeadsModel> leadModel,
			HttpSession session) {
		logger.info("Method : saveCRMLeadTask function starts" + leadModel);

		// logger.info(purchaseOrder);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {

		}
		for (CrmLeadsModel m : leadModel) {
			m.setCreatedBy(userId);
			m.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			m.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());

		}
		try {

			resp = restTemplate.postForObject(env.getPipeline() + "saveleadtask", leadModel, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		// logger.info("WEBBB" + resp);
		logger.info("Method : saveLeadTask function Ends" + resp);

		return resp;
	}

	/*
	 * //Main save for macro
	 * 
	 */

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "view-crm-leads-save-macro")
	public @ResponseBody JsonResponse<Object> saveCRMLeadMacro(@RequestBody List<CrmLeadsModel> leadModel,
			HttpSession session) {
		logger.info("Method : saveCRMLeadMacro function starts" + leadModel);

		// logger.info(purchaseOrder);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {

		}
		for (CrmLeadsModel m : leadModel) {
			m.setCreatedBy(userId);
			m.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			m.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());
		}
		try {

			resp = restTemplate.postForObject(env.getPipeline() + "saveleadMacro", leadModel, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		// logger.info("WEBBB" + resp);
		logger.info("Method : saveCRMLeadMacro function Ends" + resp);

		return resp;
	}

	// view-crm-leads-save-campaigns

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "view-crm-leads-save-campaigns")
	public @ResponseBody JsonResponse<Object> saveCRMLeadCampaign(@RequestBody List<CrmLeadsModel> leadModel,
			HttpSession session) {
		logger.info("Method : saveCRMLeadCampaign function starts" + leadModel);

		// logger.info(purchaseOrder);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {

		}
		for (CrmLeadsModel m : leadModel) {
			m.setCreatedBy(userId);
			m.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			m.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());
		}

		try {

			resp = restTemplate.postForObject(env.getPipeline() + "saveleadCampaign", leadModel, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		// logger.info("WEBBB" + resp);
		logger.info("Method : saveCRMLeadCampaign function Ends" + resp);

		return resp;
	}

	/*
	 * //Main save for mail
	 * 
	 */

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "view-crm-leads-save-mail")
	public @ResponseBody JsonResponse<Object> saveCRMLeadMail(@RequestBody List<CrmLeadsModel> leadModel,
			HttpSession session) {
		logger.info("Method : saveCRMLeadMail function starts" + leadModel);

		// logger.info(purchaseOrder);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {

		}
		for (CrmLeadsModel m : leadModel) {
			m.setCreatedBy(userId);
			m.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			m.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());
		}
		try {

			resp = restTemplate.postForObject(env.getPipeline() + "saveleadmails", leadModel, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		// logger.info("WEBBB" + resp);
		logger.info("Method : saveCRMLeadMail function Ends" + resp);

		return resp;
	}

	// view-crm-leads-save-tags

	/*
	 * //Main save for Task
	 * 
	 */

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "view-crm-leads-save-tags")
	public @ResponseBody JsonResponse<Object> saveCRMLeadTags(@RequestBody List<CrmLeadsModel> leadModel,
			HttpSession session) {
		logger.info("Method : saveCRMLeadTags function starts" + leadModel);

		// logger.info(purchaseOrder);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {

		}
		for (CrmLeadsModel m : leadModel) {
			m.setCreatedBy(userId);
			m.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			m.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());
		}
		try {

			resp = restTemplate.postForObject(env.getPipeline() + "saveleadtags", leadModel, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		// logger.info("WEBBB" + resp);
		logger.info("Method : saveCRMLeadTags function Ends" + resp);

		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "view-crm-leads-add-macro")
	public @ResponseBody JsonResponse<Object> addOrganisation(@RequestBody CrmLeadTaskModel masterOrganisationalModel,
			Model model, HttpSession session) {
		logger.info("Method :addTask starts");
		logger.info("@@@@@@@@@@@@@@@@" + masterOrganisationalModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			masterOrganisationalModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			masterOrganisationalModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());

			resp = restTemplate.postForObject(env.getPipeline() + "rest-addTask-Leads", masterOrganisationalModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addTask ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-leads-massUpdate")
	public @ResponseBody JsonResponse<Object> massUpdate(@RequestBody CrmLeadsModel crmModel, Model model,
			HttpSession session) {

		logger.info("Method : massUpdate starts" + crmModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {

			crmModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			crmModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());

			resp = restTemplate.postForObject(env.getPipeline() + "rest-add-massUpdate", crmModel, JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getMessage() == "") {
			resp.setMessage("Success");
		}
		logger.info("Method : massUpdate ends" + resp);

		return resp;
	}
	/////////////////////////////////// Document save for
	/////////////////////////////////// note/////////////////////////////////////////////////

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
	@PostMapping("crm-lead-contacted-doc-upload-file")
	public @ResponseBody JsonResponse<Object> uploadDocumentFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : employee uploadDocumentFile controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			System.out.println("inputFile====="+inputFile.toString());
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("noteDocument", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : employee uploadDocumentFile controller ' ends");
		return response;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-leads-add-notes-ajax")
	public @ResponseBody JsonResponse<Object> addNoteDoc(@RequestBody EmployeeDocumentModel employeeDocumentModel,
			HttpSession session) {
		logger.info("Method : addNoteDoc starts"+employeeDocumentModel);

		logger.info("addNoteDoc===>>>>"+employeeDocumentModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String fileUrl = null;
		employeeDocumentModel.setCreatedBy(userId);
		employeeDocumentModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
		employeeDocumentModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());
		if(employeeDocumentModel.getDocumentList() != null && !employeeDocumentModel.getDocumentList().isEmpty()) {
			for (InventoryVendorDocumentModel a : employeeDocumentModel.getDocumentList()) {
				if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
					logger.info("hello doc getImageNameEdit---------" + a.getImageNameEdit());
					a.setFileName(a.getImageNameEdit());
					fileUrl = env.getBaseURL() + "document/crm/" + a.getImageNameEdit();
					System.out.println("@@@@fileUrl" + fileUrl);
					a.setDocumnentName(fileUrl);
				} else {
					logger.info("hello doc list---------" + employeeDocumentModel.getDocumentList());
					if (a.getFileName() != null && a.getFileName() != "") {
						String delimiters = "\\.";
						String[] x = a.getFileName().split(delimiters);
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
								System.out.println("@@@@fileUrl" + fileUrl);
								a.setDocumnentName(fileUrl);
							}
						}
					}
				}
			}
		}
		System.out.print("@@@@@@employeeDocumentModel" + employeeDocumentModel);
		logger.info("hello doc list---------" + employeeDocumentModel);
		try {
			resp = restTemplate.postForObject(env.getPipeline() + "addNoteDoc", employeeDocumentModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addNoteDoc end");
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
			System.out.println("File Path For CRM Document Upload=====>" + path);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveAllDocuments ends");
		return imageName;
	}

	// view-crm-leads-details-product-view

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-details-product-view")
	public @ResponseBody JsonResponse<List<CrmProductModel>> viewProductDetails(Model model, @RequestParam String id,
			@RequestParam String id2, @RequestParam String pageType, @RequestParam String productCode,
			HttpSession session) {

		logger.info("Method : viewProductDetails starts" + id);

		JsonResponse<List<CrmProductModel>> jsonResponse = new JsonResponse<List<CrmProductModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "viewProductDetailsView?id=" + id + "&id2="
					+ id2 + "&pageType=" + pageType + "&productCode=" + productCode + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewProductDetails ends");
		return jsonResponse;
	}

	// view-crm-leads-add-product

	/*
	 * //Main save for Task
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("view-crm-leads-add-product")
	public @ResponseBody JsonResponse<Object> saveCRMProductAdd(@RequestBody List<CrmLeadsModel> crmLeadsModel,
			Model model, HttpSession session) {

		logger.info("Method : saveCRMProductAdd starts" + crmLeadsModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			System.out.println("resp web controller-----------------------------------" + crmLeadsModel);

			// String userId = "";
			List<CrmLeadsModel> updatedList = new ArrayList<CrmLeadsModel>();

			try {
				final String userId = (String) session.getAttribute("USER_ID");

				updatedList = crmLeadsModel.stream().map(obj -> {
					obj.setCreatedBy(userId);
					obj.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
					obj.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());
					return obj;
				}).collect(Collectors.toList());
			} catch (Exception e) {
				e.printStackTrace();
			}

			// crmLeadsModel.get(0).setCreatedBy(userId);

			resp = restTemplate.postForObject(env.getPipeline() + "saveCRMProductAdd", updatedList, JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getCode() == "") {
			resp.setCode("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : saveCRMProductAdd ends" + resp);

		return resp;
	}

	// view-crm-leads-autosearchProduct

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-autosearchProduct")
	public @ResponseBody JsonResponse<List<DropDownModel>> viewProductSearch(Model model,
			@RequestParam String searchVal, @RequestParam String id, @RequestParam String assigRow,
			@RequestParam String pageType, HttpSession session) {

		logger.info("Method : searchVal starts" + searchVal);

		JsonResponse<List<DropDownModel>> jsonResponse = new JsonResponse<List<DropDownModel>>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "viewProductSearchView?searchVal=" + searchVal
					+ "&id=" + id + "&assigRow=" + assigRow + "&pageType=" + pageType, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : DropDownModel ends");
		return jsonResponse;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("view-crm-leads-view-detail-edit-note")
	public @ResponseBody JsonResponse editNoteDet(Model model, @RequestParam String id, HttpSession session) {

		logger.info("Method : editLeadInfo starts" + id);

		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "rest-editNote?id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editLeadInfo ends");
		return jsonResponse;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("view-crm-leads-view-editNoteDataByNoteId")
	public @ResponseBody JsonResponse editNoteDataByNoteId(Model model, @RequestParam String id, HttpSession session) {
		logger.info("Method : editNoteDataByNoteId starts" + id);
		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "rest-editNoteDataByNoteId?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("REsp" + jsonResponse);
		logger.info("Method : editNoteDataByNoteId ends");
		return jsonResponse;
	}
	

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-leads-view-detail-delete-note")
	public @ResponseBody JsonResponse<Object> deleteLeadNote(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteLeadNote starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		System.out.println(id);
		try {
			resp = restTemplate.getForObject(env.getPipeline() + "rest-deleteNote?id=" + id, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : deleteLeadNote starts");
		return resp;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("view-crm-leads-view-detail-view-note")
	public @ResponseBody JsonResponse viewNoteDet(Model model, @RequestParam String id, @RequestParam String pageno,
			@RequestParam String filterDate, @RequestParam String filterTitle, HttpSession session) {

		logger.info("Method : viewNoteDet starts" + id);

		JsonResponse jsonResponse = new JsonResponse();
		try {

			String userId = "";

			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			jsonResponse = restTemplate.getForObject(env.getPipeline() + "rest-viewNote?id=" + id + "&pageno=" + pageno
					+ "&filterDate=" + filterDate + "&filterTitle=" + filterTitle + "&userId=" + userId + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewNoteDet ends");
		return jsonResponse;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("view-crm-leads-view-draft")
	public @ResponseBody JsonResponse viewDraftDet(Model model, @RequestParam String id, HttpSession session) {

		logger.info("Method : viewDraftDet starts" + id);

		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "rest-viewDraft?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewDraftDet ends");
		return jsonResponse;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("view-crm-leads-view-mail")
	public @ResponseBody JsonResponse viewMailDet(Model model, @RequestParam String id, HttpSession session) {

		logger.info("Method : viewMailDet starts" + id);

		JsonResponse jsonResponse = new JsonResponse();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "rest-viewMail?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewMailDet ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-leads-delete-draft")
	public @ResponseBody JsonResponse<Object> deleteLeadDraft(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteLeadDraft starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		System.out.println(id);
		try {
			resp = restTemplate.getForObject(env.getPipeline() + "rest-deleteDraft?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : deleteLeadDraft starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-leads-details-edit-draft")
	public @ResponseBody JsonResponse<CrmLeadsModel> editLeadDraftDet(@RequestParam String id1,
			@RequestParam String id2, HttpSession session) {

		logger.info("Method : editLeadDraftDet starts");

		JsonResponse<CrmLeadsModel> res = new JsonResponse<CrmLeadsModel>();
		logger.info("VIEWW" + res);
		logger.info("id1====" + id1);
		logger.info("id2====" + id2);

		try {
			res = restTemplate.getForObject(env.getPipeline() + "rest-edit-draftdet?id1=" + id1 + "&id2=" + id2
					+ "&org=" + CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		CrmLeadsModel leadModel = mapper.convertValue(res.getBody(), new TypeReference<CrmLeadsModel>() {

		});
		System.out.println("AttachMent Name=======>" + leadModel.getAttachment());
		String profile = null;
		if (leadModel.getAttachment() != null && leadModel.getAttachment() != ""
				&& !leadModel.getAttachment().equals("null")) {

			profile = env.getBaseURL() + "document/crm/" + leadModel.getAttachment();
			System.out.println("Profile=======>" + profile);
			leadModel.setOwnerImageLink(profile);
			leadModel.setDocName(leadModel.getAttachment()); 

		}

		res.setBody(leadModel);
		logger.info("###" + leadModel.getAttachment());
		res.setBody((CrmLeadsModel) leadModel);

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : editLeadDraftDet ends");
		logger.info("editLeadDraftDet" + res);
		return res;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "view-crm-leads-save-csv")
	public @ResponseBody JsonResponse<Object> saveCRMLeadCsv(@RequestBody CrmDocumentModel data, HttpSession session)
			throws IOException {
		logger.info("Method : saveCRMLeadCsv function starts" + data.getLeadOwner());

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {

		}

		List<CrmLeadsModel> records = new ArrayList<CrmLeadsModel>();

		for (CrmDocumentModel a : data.getDocumentList()) {
			System.out.print(a);
			if (a.getFileName() != null && a.getFileName() != "") {
				String delimiters = "\\.";
				String[] x = a.getFileName().split(delimiters);

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

					}
				}
				String path = env.getFileUploadCrmUrl() + a.getFileName();
				String leadOwner = data.getLeadOwner();
				try (BufferedReader br = new BufferedReader(new FileReader(path))) {
					String line = "";
					int found = 0;
					while ((line = br.readLine()) != null) {
						String[] values = line.split(",");
						if (found == 0) {
							found++;
							continue;
						}
						System.out.println("---------------------..............." + values.toString());
						if (values.length > 0) {

							String org = CustomAuthenticationSuccessHandler.org.toString();
							String orgDiv = CustomAuthenticationSuccessHandler.orgDiv.toString();
							String createdBy = userId;

							CrmLeadsModel P = new CrmLeadsModel(values[0], values[1], values[2], values[3], values[4],
									values[5], values[6], values[7], values[8], values[9], values[10], values[11],
									values[12], values[13], values[14], values[15], values[16], values[17], values[18],
									values[19], values[20], values[21], values[22], values[23], values[24], values[25],
									values[26], values[27], values[28], values[29], values[30], org, orgDiv, createdBy);
							records.add(P);
						}

					}
				}
				System.out.println("records@@@@@@@@@" + records.toString());
			}
		}
		System.out.println("records@@@@@@@@@" + records.toString());
		try {

			resp = restTemplate.postForObject(env.getPipeline() + "rest-saveleadCSV", records, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : saveCRMLeadMacro function Ends" + resp);
		return resp;
	}

	/// Admin Review Notes by Pankaj

	@SuppressWarnings("unchecked")
	@GetMapping("/view-crm-leads-view-detail-admin-reviews-note")
	public @ResponseBody JsonResponse<Object> reveiwNoteByAdmin(@RequestParam String id, @RequestParam String desc,
			HttpSession session) {
		logger.info("Method : reveiwNoteByAdmin starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getPipeline() + "rest-reveiwNote?id=" + id + "&desc=" + desc + "&userId=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : reveiwNoteByAdmin starts");
		return resp;
	}

	/*
	 * Product Delete
	 */

	@PostMapping("view-crm-leads-delete-product")
	public @ResponseBody JsonResponse<Object> deleteLeadProductInfo(@RequestBody String encryptedData,
			HttpSession session) {

		logger.info("Method : deleteLeadProductInfo starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		try {

			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(env.getPipeline() + "deleteProduct")
					.queryParam("encryptedData", encryptedData).queryParam("userId", userId)
					.queryParam("org", CustomAuthenticationSuccessHandler.org.toString())
					.queryParam("orgDiv", CustomAuthenticationSuccessHandler.orgDiv.toString());

			ResponseEntity<JsonResponse<Object>> responseEntity = restTemplate.exchange(builder.toUriString(),
					HttpMethod.GET, null, new ParameterizedTypeReference<JsonResponse<Object>>() {
					});

			jsonResponse = responseEntity.getBody();

			System.out.println("Response For Deletion of Products ----.>>>>>" + jsonResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteLeadProductInfo ends");
		return jsonResponse;
	}

	// For lead status timeline

	@SuppressWarnings("unchecked")
	@GetMapping(value = "view-crm-leads-view-lead-status-timeline")
	public @ResponseBody JsonResponse<Object> leadStatusTimeline(@RequestParam String id) {

		logger.info("Method: leadStatusTimeline starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getPipeline() + "lead-status-timeline?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
			e.printStackTrace();
		}

		logger.info("Method: leadStatusTimeline starts" + resp.getBody());
		return resp;
	}

	/*
	 * Create Project
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-quotation-get-project-list" })
	public @ResponseBody JsonResponse<QuotationNewModel> getProjectAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getProjectAutoSearchList starts");
		// logger.info("QuotationNewModel"+searchValue);
		JsonResponse<QuotationNewModel> res = new JsonResponse<QuotationNewModel>();
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getSalesUrl() + "getProjectAutoSearchList?id=" + searchValue + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		// logger.info("RESPONSE@@" + res);
		logger.info("Method : getProjectAutoSearchList ends" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/project-modal-add")
	public @ResponseBody JsonResponse<Object> addPrjCreation(@RequestBody List<ProjectCreationWebModel> prjCreation,
			HttpSession session) {
		logger.info("Method : addPrjcreation starts" + prjCreation);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<ProjectCreationWebModel> documentList = new ArrayList<ProjectCreationWebModel>();
		List<ProjectFileuploadModel> docList = new ArrayList<ProjectFileuploadModel>();

		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (ProjectCreationWebModel m : prjCreation) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);
		}

		for (ProjectFileuploadModel a : prjCreation.get(0).getDocumentList()) {

			if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
				a.setFileName(a.getImageNameEdit());
			} else {
				if (a.getFileName() != null && a.getFileName() != "") {
					String[] extension = a.getFileName().split("\\.");
					int lastindex = extension.length - 1;
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							logger.info("bytes" + s1);
							String imageName = new OpenProjectModalWebController().saveAllMultiImages(bytes,
									extension[lastindex]);
							a.setFileName(imageName);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}
				}
			}
		}

		try {
			resp = restTemplate.postForObject(env.getProjects() + "rest-addPrjCreation", prjCreation,
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

		logger.info("Method : addPrjcreation end" + resp);
		return resp;
	}

	/*
	 * function for project drop down country
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "project-modal-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForProject(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForProject starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getProjects() + "rest-project-state-list?id=" + tCountry,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getStateNameForProject ends" + res);
		return res;

	}

	/*
	 * function for billing drop down country
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "project-modal-billing-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForBilling(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForBilling starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getProjects() + "rest-project-billing-state-list?id=" + tCountry,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getStateNameForBilling ends" + res);
		return res;

	}

	/*
	 * function for shipping drop down country
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "project-modal-shipping-state-list" })
	public @ResponseBody JsonResponse<Object> getStateNameForShipping(Model model, @RequestBody String tCountry,
			BindingResult result) {
		logger.info("Method : getStateNameForShipping starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getProjects() + "rest-project-shipping-state-list?id=" + tCountry,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getStateNameForShipping ends" + res);
		return res;

	}

}
