package nirmalya.aathithya.webmodule.pipeline.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.CrmAccountsModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCampaignModel;
import nirmalya.aathithya.webmodule.user.service.CustomAuthenticationSuccessHandler;

@Controller
@RequestMapping(value = "pipeline")
public class CrmAccountController {
	Logger logger = LoggerFactory.getLogger(CrmLeadsController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
    @Value("${spring.mail.username}")
    private String username;

	@GetMapping("/view-crm-accounts")
	public String viewCrmLeads(Model model, HttpSession session) {
		logger.info("Method : viewCrmLeads start");

		try {
			logger.info(env.getPipeline());
			DropDownModel[] accountType = restTemplate.getForObject(env.getPipeline() + "/getAccountTypeList",
					DropDownModel[].class);

			List<DropDownModel> accountTypeList = Arrays.asList(accountType);
			model.addAttribute("accountTypeList", accountTypeList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			DropDownModel[] accountName = restTemplate.getForObject(env.getPipeline() + "/getAccountNameList?userId="+userId+"&org="+org+"&orgDiv="+orgDiv,
					DropDownModel[].class);

			List<DropDownModel> accountNameList = Arrays.asList(accountName);
			model.addAttribute("accountTypeList", accountNameList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] states = restTemplate.getForObject(env.getPipeline() + "/getStatesList",
					DropDownModel[].class);

			List<DropDownModel> statesList = Arrays.asList(states);
			model.addAttribute("statesList", statesList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		
		try {
			logger.info(env.getPipeline());
			DropDownModel[] ownership = restTemplate.getForObject(env.getPipeline() + "/getOwnershipList",
					DropDownModel[].class);

			List<DropDownModel> ownershipList = Arrays.asList(ownership);
			model.addAttribute("ownershipList", ownershipList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			DropDownModel[] owner = restTemplate.getForObject(env.getMasterUrl() + "/getOwnerList",
					DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
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
		
		logger.info("Method : viewCrmLeads end");
		return "pipeline/crm-account";
	}
	
	//view-crm-accounts-details
	
	
	@GetMapping("/view-crm-accounts-detail")
	public String viewCrmAccountsDetails(Model model,@RequestParam String id,HttpSession session){
		logger.info("Method : viewCrmAccountsDetails start");
		logger.info("id url -----------------"+id);
		model.addAttribute("Leadidval",id);
		//leadList
		
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
			session.getAttribute("accountMailTo");
			DropDownModel[] lead = restTemplate.getForObject(env.getPipeline() + "/getLeadNameList?userId="+userId+"&org="+org+"&orgDiv="+orgDiv,
					DropDownModel[].class);
			List<DropDownModel> leadList = Arrays.asList(lead);
			logger.info("leadList" + leadList);
			model.addAttribute("leadList", leadList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] owner = restTemplate.getForObject(env.getMasterUrl() + "/getOwnerList",
					DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("username==="+username);
		model.addAttribute("from_email", username);
		
		logger.info("Method : viewCrmAccountsDetails end");
		return "pipeline/crm-accounts-detail";
	}
	
	/*
	 * post mapping for adding account
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("/view-crm-accounts-add")
	public @ResponseBody JsonResponse<Object> addAccount(@RequestBody CrmAccountsModel accountModel,
			HttpSession session) {

		logger.info("Method : addAccount starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web addContact ======================" + accountModel);
		try {
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			accountModel.setCreatedBy(userId);
			accountModel.setOrganization(organization);
			accountModel.setOrgDivision(orgDivision);
			

			resp = restTemplate.postForObject(env.getPipeline() + "/addAccount", accountModel, JsonResponse.class);

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

		logger.info("Method : addAccount ends");

		return resp;
	}
	
	//view-crm-accounts-view-Data


	/*
	 * Post Mapping for search view-crm-account-view-Data-search
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/view-crm-accounts-view-Data-filter")
	public @ResponseBody JsonResponse<List<CrmAccountsModel>> viewAccountSearchDetails(@RequestBody CrmAccountsModel crmModel,
			Model model, HttpSession session) {

		logger.info("Method : viewAccountSearchDetails starts" + crmModel);

		logger.info("VIEWWWWWWWWWWWDATA" + crmModel);
		JsonResponse<List<CrmAccountsModel>> resp = new JsonResponse<List<CrmAccountsModel>>();

		crmModel.setOrganization(CustomAuthenticationSuccessHandler.org.toString());
		crmModel.setOrgDivision(CustomAuthenticationSuccessHandler.org.toString());
		
		try {

			resp = restTemplate.postForObject(env.getPipeline() + "viewAccountSearchDetails", crmModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<CrmAccountsModel> accountModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CrmAccountsModel>>() {
				});
		String drProfDoc = null;

		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		for (CrmAccountsModel i : accountModel) {
			if (i.getImageName() != null && i.getImageName() != "") {
			
				 String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();
				
				logger.info("Image" + fileEmployeeimg);
				i.setImageName(fileEmployeeimg); 
			}
			
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				logger.info("start date---------------"+date);
			}
			
			if (i.getOwnerImage() != null && i.getOwnerImage() != "") {
				 String fileOwnerImage = env.getBaseURL() + "document/crm/" + i.getOwnerImage();
				
				logger.info("fileOwnerImage---------" + fileOwnerImage);
				i.setOwnerImage(fileOwnerImage); 
			}
		}

		logger.info("###" + accountModel);
		resp.setBody(accountModel);

		logger.info("VIEWMESSAGE"+resp.getMessage());

		if (resp.getMessage() != null|| resp.getMessage() !="") {
			resp.setMessage("Success");
		}
		logger.info("VIEWWWWWeeeeee" + resp);
		logger.info("Method : viewAccountSearchDetails ends");
		return resp;
	}
	
	
	
	/// view
		@SuppressWarnings("unchecked")

		@GetMapping("view-crm-accounts-view-Data")
		public @ResponseBody JsonResponse<List<CrmAccountsModel>> viewCrmAccount(HttpSession session,@RequestParam String pageno,@RequestParam String userId) {
			logger.info("Method : View viewCrmAccount starts");

			JsonResponse<List<CrmAccountsModel>> resp = new JsonResponse<List<CrmAccountsModel>>();

			try {
				String orgName = "";
				String orgDivision = "";
				
				try {
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");	
				}
				catch (Exception e){
					e.printStackTrace();
				}
				resp = restTemplate.getForObject(env.getPipeline() + "/restViewAccountdetails?pageno="+pageno+"&userId="+userId+
						"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();

			List<CrmAccountsModel> accountModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<CrmAccountsModel>>() {
					});
			String drProfDoc = null;

			String date = "";
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			for (CrmAccountsModel i : accountModel) {
				if (i.getImageName() != null && i.getImageName() != "") {
				
					 String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();
					
					logger.info("Image" + fileEmployeeimg);
					i.setImageName(fileEmployeeimg); 
				}
				
				if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
					date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
					i.setCreatedDate(date);
					logger.info("start date---------------"+date);
				}
				
				if (i.getOwnerImage() != null && i.getOwnerImage() != "") {
					 String fileOwnerImage = env.getBaseURL() + "document/crm/" + i.getOwnerImage();
					
					logger.info("fileOwnerImage---------" + fileOwnerImage);
					i.setOwnerImage(fileOwnerImage); 
				}
			}

			logger.info("###" + accountModel);
			resp.setBody(accountModel);


			if (resp.getMessage() == null) {
				resp.setMessage("Success");
			}

			logger.info("VIEWWWWWWWWWWW" + resp);
			logger.info("Method : View viewCrmAccount ends");
			return resp;
		}

	
	//view-crm-accounts-deleteDetails
		
		//delete 
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-accounts-deleteDetails")
		public @ResponseBody JsonResponse<Object> deleteAccountDetails(@RequestParam String id,
				 HttpSession session) {
			logger.info("Method : deleteAccountDetails function starts"+id);

			JsonResponse<Object> res = new JsonResponse<Object>();

			

			try {
				res = restTemplate.getForObject(env.getPipeline() + "delete-account-Details?id=" + id + "&org=" + CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv=" + CustomAuthenticationSuccessHandler.orgDiv.toString()  , JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteAccountDetails function Ends");
			
			logger.info("Response"+res);
			return res;
		}
	
		//view-crm-accounts-editDetails
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-accounts-editDetails")
		public @ResponseBody JsonResponse<List<CrmAccountsModel>> editAccountInfo(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : editAccountInfo starts" + id);
			
		

			JsonResponse<List<CrmAccountsModel>> jsonResponse = new JsonResponse<List<CrmAccountsModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "editAccountInfo?id=" + id + "&org=" + CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv=" + CustomAuthenticationSuccessHandler.orgDiv.toString(),
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			

			ObjectMapper mapper = new ObjectMapper();

			List<CrmAccountsModel> accountmodel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<CrmAccountsModel>>() {
					});
			String drProfDoc = null;
			for (CrmAccountsModel i : accountmodel) {
				if (i.getImageName() != null && i.getImageName() != "") {
					 String fileEmployeeimg = env.getBaseURL() + "document/crm/" + i.getImageName();
					
					logger.info("Image" + fileEmployeeimg);
					i.setImageName(fileEmployeeimg); 
				}
			}

			logger.info("###" + accountmodel);
			jsonResponse.setBody(accountmodel);

			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : editAccountInfo ends");
			return jsonResponse;
		}

		// view-crm-leads-detail-add-campaign-dtls

		@SuppressWarnings("unchecked")

		@PostMapping("/view-crm-accounts-detail-add-campaign-dtls")
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

				resp = restTemplate.postForObject(env.getPipeline() + "/addCampaign", crmCampaignModel,
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

			logger.info("Method : addCampaignsByContactDtls ends");

			return resp;
		}
		
		//view-crm-accounts-ViewDetailPage
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-accounts-ViewDetailPage")
		public @ResponseBody JsonResponse<List<CrmAccountsModel>> viewAccountDetailsPage(Model model, @RequestParam String id,
				HttpSession session) {

			logger.info("Method : viewAccountDetailsPage starts" + id);
			
			;

			JsonResponse<List<CrmAccountsModel>> jsonResponse = new JsonResponse<List<CrmAccountsModel>>();
			try {
				jsonResponse = restTemplate.getForObject(env.getPipeline() + "viewAccountDetailsPage?id=" + id + "&org=" + CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv=" + CustomAuthenticationSuccessHandler.orgDiv.toString(),
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			

			ObjectMapper mapper = new ObjectMapper();

			List<CrmAccountsModel> dealModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<CrmAccountsModel>>() {
					});
			//System.out.println(dealModel.get(0).getContactsMailId());
			session.setAttribute("accountMailTo", dealModel.get(0).getContactsMailId());
			

			
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			logger.info("REsp" + jsonResponse);
			logger.info("Method : viewAccountDetailsPage ends");
			return jsonResponse;
		}

}
