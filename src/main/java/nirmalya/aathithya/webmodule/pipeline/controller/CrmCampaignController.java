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
import nirmalya.aathithya.webmodule.pipeline.model.CrmCampaignModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmDealModel;

@Controller
@Component
@RequestMapping(value = "pipeline")
public class CrmCampaignController {
	Logger logger = LoggerFactory.getLogger(CrmCampaignController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	/*
	 * get mapping for view-crm-deals
	 */

	@GetMapping("/view-crm-campaigns")
	public String viewCRMCampaign(Model model, HttpSession session) {
		logger.info("Method : viewCRMCampaign start");
		CrmDealModel CrmTaskModel = new CrmDealModel();
		CrmDealModel form = (CrmDealModel) session.getAttribute("sCrmTaskModel");
		String message = (String) session.getAttribute("message");
		if (message != null && message != "") {
			model.addAttribute("message", message);
		}
		session.setAttribute("message", "");
		if (form != null) {
			model.addAttribute("CrmTaskModel", form);
			session.setAttribute("sCrmTaskModel", null);

		} else {
			model.addAttribute("CrmTaskModel", CrmTaskModel);
		}

		try {
			String role = (String) session.getAttribute("IS_SALES_MANAGER");
			String userId = "";
			if (role != null  && role != "") {
				userId = (String) session.getAttribute("USER_ID");
			} else {
				userId = "";
			}
			DropDownModel[] owner = restClient.getForObject(env.getMasterUrl() + "getOwnerList?userId=" + userId,
					DropDownModel[].class);

			List<DropDownModel> ownerList = Arrays.asList(owner);
			logger.info("ownerList" + ownerList);
			model.addAttribute("ownerList", ownerList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			DropDownModel[] lead = restClient.getForObject(env.getPipeline() + "/getLeadNameList?userId="+userId+"&org="+org+"&orgDiv="+orgDiv,
					DropDownModel[].class);

			List<DropDownModel> leadList = Arrays.asList(lead);
			logger.info("leadList" + leadList);
			model.addAttribute("leadList", leadList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : viewCRMCampaign end");
		return "pipeline/crm-campaigns";
	}
	
	
	
	/*
		 * post mapping for adding pipeline
		 */

		@SuppressWarnings("unchecked")

		@PostMapping("/view-crm-campaigns-add-dtls")
		public @ResponseBody JsonResponse<Object> addCampaigns(@RequestBody CrmCampaignModel crmCampaignModel,
				HttpSession session) {

			logger.info("Method : addCampaigns starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			logger.info("web addCampaigns lead======================" + crmCampaignModel);
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

				crmCampaignModel.setCreatedBy(userId);
				crmCampaignModel.setOrganization(orgName);
				crmCampaignModel.setOrgDivision(orgDivision);

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

			logger.info("Method : addCampaigns ends");

			return resp;
		}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-campaigns-throughAjax")
		public @ResponseBody List<CrmCampaignModel> viewCrmCampaigns(HttpSession session, @RequestParam String pageno) {

			logger.info("Method : viewCrmCampaigns starts");

			JsonResponse<List<CrmCampaignModel>> resp = new JsonResponse<List<CrmCampaignModel>>();

			try {
				
				String userId = "";
				String orgName = "";
				String orgDivision = "";
				
				try {
					userId = (String) session.getAttribute("USER_ID");
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");	
				}
				catch (Exception e){
					e.printStackTrace();
				}
				//resp = restClient.getForObject(env.getPipeline() + "/restViewCampaignDetails?pageno="+pageno, JsonResponse.class);
				resp = restClient.getForObject(env.getPipeline() + "/restViewCampaignDetails?pageno="+ pageno +"&userId="+userId+
						"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String date = "";
			String date1 = "";
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			
			List<CrmCampaignModel> campaignModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<CrmCampaignModel>>() {
					});
			
			for (CrmCampaignModel i : campaignModel) {
				if (i.getStartDate() != null && i.getStartDate() != "") {
					date = DateFormatter.dateFormat(i.getStartDate(), dateFormat);
					i.setStartDate(date);
					logger.info("start date---------------"+date);
				}
				
				if (i.getEndDate() != null && i.getEndDate() != "") {
					date1 = DateFormatter.dateFormat(i.getEndDate(), dateFormat);
					i.setEndDate(date1);
					logger.info("end date---------------"+date1);
				}
			}
			
			resp.setBody(campaignModel);
			logger.info("resp.getBody()-----------"+resp.getBody());
			
			logger.info("Method : viewCrmCampaigns ends");
			return resp.getBody();
		}
		
		
		///view-crm-tasks-edit
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-campaigns-edit")
		public @ResponseBody JsonResponse<List<CrmCampaignModel>> editCampaignsInfo(Model model, @RequestParam String id,
				HttpSession session) {
	
			logger.info("Method : editCampaignsInfo starts" + id);
			
			JsonResponse<List<CrmCampaignModel>> jsonResponse = new JsonResponse<List<CrmCampaignModel>>();
			try {
				jsonResponse = restClient.getForObject(env.getPipeline() + "editCampaignsInfo?id=" + id,
						JsonResponse.class);
	
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
				/*if (i.getStartDate() != null && i.getStartDate() != "") {
					date = DateFormatter.dateFormat(i.getStartDate(), dateFormat);
					i.setStartDate(date);
					logger.info("start date---------------"+date);
				}
				
				if (i.getEndDate() != null && i.getEndDate() != "") {
					date1 = DateFormatter.dateFormat(i.getEndDate(), dateFormat);
					i.setEndDate(date1);
					logger.info("end date---------------"+date1);
				}*/
			}
			
			logger.info("###" + campaignModel);
			jsonResponse.setBody(campaignModel);
	
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
	
			} else {
				jsonResponse.setMessage("Success");
			}
	
			logger.info("REsp" + jsonResponse);
			logger.info("Method : editCampaignsInfo ends");
			return jsonResponse;
		}
		
		
		///view-crm-tasks-delete  
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-campaigns-delete-id")
		public @ResponseBody JsonResponse<Object> deleteCrmCampaignsDetails(@RequestParam String id,
				 HttpSession session) {
			logger.info("Method : deleteCrmCampaignsDetails function starts"+id);

			JsonResponse<Object> res = new JsonResponse<Object>();

			

			try {
				res = restClient.getForObject(env.getPipeline() + "delete-campaign-Details?id=" + id  , JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteCrmCampaignsDetails function Ends");
			
			logger.info("Response"+res);
			return res;
		}

}
