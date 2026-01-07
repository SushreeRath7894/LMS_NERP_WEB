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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.controller.MasterDataApiController;
import nirmalya.aathithya.webmodule.pipeline.model.CrmContactModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmDealModel;
import nirmalya.aathithya.webmodule.user.service.CustomAuthenticationSuccessHandler;

@Controller
@Component
@RequestMapping(value = "pipeline")
public class CrmDealController {
	Logger logger = LoggerFactory.getLogger(CrmDealController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	MasterDataApiController master;

	@Autowired
	public CrmDealController(MasterDataApiController master) {
		this.master = master;
	}

	/*
	 * get mapping for view-crm-deals
	 */

	@GetMapping("/view-crm-deals")
	public String viewCRMDeals(Model model, HttpSession session) {
		logger.info("Method : viewCRMDeals start");
		CrmDealModel CrmDealModel = new CrmDealModel();
		CrmDealModel form = (CrmDealModel) session.getAttribute("sCrmDealModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}
		session.setAttribute("message", "");
		if (form != null) {
			model.addAttribute("CrmDealModel", form);
			session.setAttribute("sCrmDealModel", null);

		} else {
			model.addAttribute("CrmDealModel", CrmDealModel);
		}

		try {
			List<DropDownModel> ownerList = master.getOwnerList(session);;
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
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

		try {
			DropDownModel[] dealtype = restClient.getForObject(env.getPipeline() + "/getDealTypeList",
					DropDownModel[].class);

			List<DropDownModel> dealtypeList = Arrays.asList(dealtype);
			logger.info("dealtype--------" + dealtypeList);
			model.addAttribute("dealtypeList", dealtypeList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] stage = restClient.getForObject(env.getPipeline() + "/getDealStageList",
					DropDownModel[].class);

			List<DropDownModel> stageList = Arrays.asList(stage);
			model.addAttribute("stageList", stageList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] campaign = restClient.getForObject(env.getPipeline() + "/getDealCampaignList",
					DropDownModel[].class);

			List<DropDownModel> campaignList = Arrays.asList(campaign);
			model.addAttribute("campaignList", campaignList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewCRMDeals end");
		return "pipeline/crm-deal";
	}

	@GetMapping("/view-crm-deals-detail")
	public String viewCrmDealsDetails(Model model, @RequestParam String id, HttpSession session) {
		logger.info("Method : viewCrmDealsDetails start");
		model.addAttribute("dealId", id);

		try {
			String role = (String) session.getAttribute("IS_SALES_MANAGER");
			String userId = "";
			if (role != null  && role != "") {
				userId = (String) session.getAttribute("USER_ID");
			} else {
				userId = "";
			}
			DropDownModel[] owner = restClient.getForObject(env.getMasterUrl() + "/getOwnerList?userId=" + userId,
					DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			// logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * try { DropDownModel[] stage = restClient.getForObject(env.getPipeline() +
		 * "/getDealStageListWONew", DropDownModel[].class);
		 * 
		 * List<DropDownModel> dealStageList = Arrays.asList(stage);
		 * logger.info("dealStageList-------------------------------------------" +
		 * dealStageList); model.addAttribute("dealStageList", dealStageList); } catch
		 * (RestClientException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		// leadList

		try {
			DropDownModel[] lead = restClient.getForObject(env.getPipeline() + "/getLeadNameList",
					DropDownModel[].class);

			List<DropDownModel> leadList = Arrays.asList(lead);
			// logger.info("leadList" + leadList);
			model.addAttribute("leadList", leadList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewCrmDealsDetails end");
		return "pipeline/crm-deals-detail";
	}

	/*
	 * post mapping for adding pipeline
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-deals-add")
	public @ResponseBody JsonResponse<Object> addDeal(@RequestBody CrmDealModel crmDealModel, HttpSession session) {

		logger.info("Method : addDeal starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addDeal lead======================" + crmDealModel.getDealLeadSource());
		try {

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

			crmDealModel.setCreatedBy(userId);
			crmDealModel.setOrganization(orgName);
			crmDealModel.setOrgDivision(orgDivision);

			resp = restClient.postForObject(env.getPipeline() + "/addDeal", crmDealModel, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addDeal ends");

		return resp;
	}

	/*
	 * Post Mapping for search view-crm-deals-view-Data-search
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-deals-view-Data-filter")
	public @ResponseBody JsonResponse<List<CrmDealModel>> viewDealSearchDetails(@RequestBody CrmDealModel crmModel,
			Model model, HttpSession session) {

		logger.info("Method : viewDealSearchDetails starts" + crmModel);

		logger.info("VIEWWWWWWWWWWWDATA" + crmModel);
		JsonResponse<List<CrmDealModel>> resp = new JsonResponse<List<CrmDealModel>>();

		try {

			crmModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			crmModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());
			resp = restClient.postForObject(env.getPipeline() + "viewDealSearchDetails", crmModel, JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CrmDealModel> dealModel = mapper.convertValue(resp.getBody(), new TypeReference<List<CrmDealModel>>() {
		});

		String date = "";
		String date1 = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

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

		logger.info("VIEWMESSAGE" + resp.getMessage());

		if (resp.getMessage() != null || resp.getMessage() != "") {
			resp.setMessage("Success");
		}
		logger.info("VIEWWWWWeeeeee" + resp);
		logger.info("Method : viewDealSearchDetails ends");
		return resp;
	}

	/*
	 * View all pipeline
	 *
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-deals-throughAjax4535")
	public @ResponseBody List<CrmContactModel> viewAllContact(HttpSession session) {

		logger.info("Method : viewAllContact starts");

		// logger.info("fvsjkbhkfvsjk");

		JsonResponse<List<CrmContactModel>> resp = new JsonResponse<List<CrmContactModel>>();

		try {
			// logger.info(env.getPipeline() + "getAllPipeLine");
			resp = restClient.getForObject(env.getPipeline() + "/getAllContact", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		/*
		 * List<CrmContactModel> dealModel = mapper.convertValue(resp.getBody(), new
		 * TypeReference<List<CrmContactModel>>() { });
		 * 
		 * for (CrmContactModel i : dealModel) { if (i.getD != null && i.getDueDate() !=
		 * "") { date = DateFormatter.dateFormat(i.getDueDate(), dateFormat);
		 * i.setDueDate(date); logger.info("Due date---------------"+date); } }
		 */

		// logger.info("vcxfvfcvf " + resp.getBody());
		return resp.getBody();
	}

	// edit contact

	/**
	 * get mapping for edit pipeline
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("view-crm-deals-edit")
	public @ResponseBody JsonResponse<CrmContactModel> editContact(@RequestParam String id, HttpSession session) {

		logger.info("Method : edit Contact starts");

		JsonResponse<CrmContactModel> jsonResponse = new JsonResponse<CrmContactModel>();

		try {
			logger.info(id + "IDIDIDIDID");
			jsonResponse = restClient.getForObject(env.getPipeline() + "/editContact?id=" + id, JsonResponse.class);

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

		logger.info("Method : edit Contacts ends");
		logger.info("web Edit==================" + jsonResponse);
		return jsonResponse;

	}

	// view-crm-deals-view-Data

	/// view

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("view-crm-deals-view-Data") public @ResponseBody
	 * JsonResponse<List<CrmDealModel>> viewCrmDeal(HttpSession
	 * session,@RequestParam String pageno) {
	 * logger.info("Method : View viewCrmDeal starts");
	 * 
	 * JsonResponse<List<CrmDealModel>> resp = new
	 * JsonResponse<List<CrmDealModel>>();
	 * 
	 * try { resp = restClient.getForObject(env.getPipeline() +
	 * "restViewDealDetails?pageno="+pageno, JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * List<CrmDealModel> dealModel = mapper.convertValue(resp.getBody(), new
	 * TypeReference<List<CrmDealModel>>() { });
	 * 
	 * 
	 * String date = ""; String dateFormat = (String)
	 * (session).getAttribute("DATEFORMAT");
	 * 
	 * String date1 ="";
	 * 
	 * for (CrmDealModel i : dealModel) { if (i.getDealClosingDate() != null &&
	 * i.getDealClosingDate() != "") { date =
	 * DateFormatter.dateFormat(i.getDealClosingDate(), dateFormat);
	 * i.setDealClosingDate(date); logger.info("Due date---------------"+date); }
	 * 
	 * if (i.getCreatedDate() != null && i.getCreatedDate() != "") { date1 =
	 * DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
	 * i.setCreatedDate(date1); logger.info("start date---------------"+date1); }
	 * 
	 * if (i.getOwnerImage() != null && i.getOwnerImage() != "") { String
	 * fileOwnerImage = env.getBaseURL() + "document/crm/" + i.getOwnerImage();
	 * 
	 * logger.info("fileOwnerImage---------" + fileOwnerImage);
	 * i.setOwnerImage(fileOwnerImage); } }
	 * 
	 * 
	 * logger.info("###" + dealModel); resp.setBody(dealModel);
	 * 
	 * 
	 * if (resp.getMessage() == null) { resp.setMessage("Success"); }
	 * 
	 * logger.info("views" + resp); logger.info("Method : View viewCrmDeal ends");
	 * return resp; }
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("view-crm-deals-view-Data")
	public @ResponseBody JsonResponse viewCrmDeal(HttpSession session, @RequestParam String pageno) {

		logger.info("Method : viewLeadsDetails starts");
		JsonResponse jsonResponse = new JsonResponse();
		try {

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
			jsonResponse = restClient.getForObject(env.getPipeline() + "/restViewDealDetails?pageno=" + pageno
					+ "&userId=" + userId + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewCrmDeal ends");
		return jsonResponse;
	}

	// view-crm-deals-deleteDetails

	// delete
	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-deals-deleteDetails")
	public @ResponseBody JsonResponse<Object> deleteDealDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteDealDetails function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getPipeline() + "delete-deal-Details?id=" + id + "&org="
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
		logger.info("Method : deleteDealDetails function Ends");

		logger.info("Response" + res);
		return res;
	}

	/// view-crm-deals-ViewDetailPage

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-deals-ViewDetailPage")
	public @ResponseBody JsonResponse<List<CrmDealModel>> viewDealDetailsPage(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editAccountInfo starts" + id);

		JsonResponse<List<CrmDealModel>> jsonResponse = new JsonResponse<List<CrmDealModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "viewDealDetailsPage?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CrmDealModel> dealModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmDealModel>>() {
				});

		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		if (dealModel.get(0).getDealClosingDate() != null && dealModel.get(0).getDealClosingDate() != "") {
			date = DateFormatter.dateFormat(dealModel.get(0).getDealClosingDate(), dateFormat);
			dealModel.get(0).setDealClosingDate(date);

		}

		logger.info("###" + dealModel);
		jsonResponse.setBody(dealModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewDealDetailsPage ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-deals-detail-stage")
	public @ResponseBody JsonResponse<List<CrmDealModel>> viewDealStageInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : viewDealStageInfo starts" + id);

		JsonResponse<List<CrmDealModel>> jsonResponse = new JsonResponse<List<CrmDealModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "view-rest-dealStageInfo?id=" + id + "&org="
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
		logger.info("Method : viewDealStageInfo ends");
		return jsonResponse;
	}

	// auto search

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-crm-deals-autosearchCampaignDetails" })
	public @ResponseBody JsonResponse<DropDownModel> autosearchCampaignDetails(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : autosearchCampaignDetails starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getPipeline() + "getCampaignDetailsSearchList?id=" + searchValue + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : autosearchCampaignDetails ends");
		logger.info("AUTOSEARCHHH" + res);
		return res;
	}

	/// view-crm-deals-editDetails

	@SuppressWarnings("unchecked")
	@GetMapping("view-crm-deals-editDetails")
	public @ResponseBody JsonResponse<List<CrmDealModel>> editDealInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editAccountInfo starts" + id);

		JsonResponse<List<CrmDealModel>> jsonResponse = new JsonResponse<List<CrmDealModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getPipeline() + "editDealInfo?id=" + id + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CrmDealModel> dealModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CrmDealModel>>() {
				});

		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		/*
		 * if (dealModel.get(0).getDealClosingDate() != null &&
		 * dealModel.get(0).getDealClosingDate() != "") { date =
		 * DateFormatter.dateFormat(dealModel.get(0).getDealClosingDate(), dateFormat);
		 * dealModel.get(0).setDealClosingDate(date);
		 * 
		 * }
		 */

		logger.info("###" + dealModel);
		jsonResponse.setBody(dealModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : editDealInfo ends");
		return jsonResponse;
	}

	//////////////// view-crm-deals-detail-update-stage

	//////////////////////////// stage

	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-deals-detail-update-stage")
	public @ResponseBody JsonResponse<Object> addUpdateStageDealDtls(@RequestBody CrmDealModel crmDealModel,
			HttpSession session) {

		logger.info("Method : addDeal starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web previous stage lead======================" + crmDealModel.getPreStageName());
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}

			crmDealModel.setCreatedBy(userId);
			crmDealModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
			crmDealModel.setOrgDivision(CustomAuthenticationSuccessHandler.orgDiv.toString());

			resp = restClient.postForObject(env.getPipeline() + "/addUpdateStageDealDtls", crmDealModel,
					JsonResponse.class);

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

		logger.info("Method : addUpdateStageDealDtls ends");

		return resp;
	}

}
