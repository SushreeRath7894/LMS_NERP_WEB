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
import nirmalya.aathithya.webmodule.pipeline.model.CrmCallModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmDealModel;

@Controller
@Component
@RequestMapping(value = "pipeline")
public class CrmCallController {
	Logger logger = LoggerFactory.getLogger(CrmCallController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	/*
	 * get mapping for view-crm-deals
	 */

	@GetMapping("/view-crm-calls")
	public String viewCRMCall(Model model, HttpSession session) {
		logger.info("Method : viewCRMCall start");
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
		
		/*
		 * try { DropDownModel[] lead = restClient.getForObject(env.getPipeline() +
		 * "/getLeadNameList", DropDownModel[].class);
		 * 
		 * List<DropDownModel> leadList = Arrays.asList(lead); logger.info("leadList" +
		 * leadList); model.addAttribute("leadList", leadList); } catch
		 * (RestClientException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		
		logger.info("Method : viewCRMCall end");
		return "pipeline/crm-calls";
	}
	
	
	
	/*
		 * post mapping for adding pipeline
		 */

		@SuppressWarnings("unchecked")

		@PostMapping("/view-crm-calls-add-dtls")
		public @ResponseBody JsonResponse<Object> addCalls(@RequestBody CrmCallModel callModel,
				HttpSession session) {

			logger.info("Method : addCalls starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			logger.info("web addCalls lead======================" + callModel);
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

				callModel.setCreatedBy(userId);
				callModel.setOrganization(orgName);
				callModel.setOrgDivision(orgDivision);

				resp = restClient.postForObject(env.getPipeline() + "/addCall", callModel, JsonResponse.class);

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

			logger.info("Method : addCalls ends");

			return resp;
		}
		
		
	
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-calls-throughAjax")
		public @ResponseBody List<CrmCallModel> viewCrmCall(HttpSession session,@RequestParam String pageno) {

			logger.info("Method : viewCrmCall starts");

			JsonResponse<List<CrmCallModel>> resp = new JsonResponse<List<CrmCallModel>>();

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
				
				resp = restClient.getForObject(env.getPipeline() + "/restViewCallDetails?pageno="+pageno+"&userId="+userId+
						"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			
			ObjectMapper mapper = new ObjectMapper();

			String date = "";
			String leadname="";
			String contactname="";
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			
			List<CrmCallModel> callModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<CrmCallModel>>() {
					});
			
			for (CrmCallModel i : callModel) {
				if (i.getCallStartDate() != null && i.getCallStartDate() != "") {
					date = DateFormatter.dateFormat(i.getCallStartDate(), dateFormat);
					i.setCallStartDate(date);
				}
				leadname=i.getLeadName();
				contactname=i.getContactName();
				String CallToWhom=i.getCallToWhom();
				String lead="Lead";
				if(CallToWhom.equals(lead)) {
					i.setLeadOrContactName(leadname);
				}
				else {
					i.setLeadOrContactName(contactname);
				}
			}
			resp.setBody(callModel);
			
			logger.info("Method : viewCrmCall ends");
			return resp.getBody();
		}
		
		
		
		///view-crm-tasks-edit
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-calls-edit")
		public @ResponseBody JsonResponse<List<CrmCallModel>> editCallsInfo(Model model, @RequestParam String id,
				HttpSession session) {
	
			logger.info("Method : editCallsInfo starts" + id);
			
			JsonResponse<List<CrmCallModel>> jsonResponse = new JsonResponse<List<CrmCallModel>>();
			try {
				jsonResponse = restClient.getForObject(env.getPipeline() + "editCallInfo?id=" + id,
						JsonResponse.class);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	
			ObjectMapper mapper = new ObjectMapper();
			
			String dueDate = "";
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			
			List<CrmCallModel> callModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<CrmCallModel>>() {
					});
			
			for (CrmCallModel i : callModel) {
			/*	if (i.getCallStartDate() != null && i.getCallStartDate() != "") {
					dueDate = DateFormatter.dateFormat(i.getCallStartDate(), dateFormat);
					i.setCallStartDate(dueDate);
					logger.info("Due date---------------"+dueDate);
				}*/
			}
			
			logger.info("###" + callModel);
			jsonResponse.setBody(callModel);
	
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
	
			} else {
				jsonResponse.setMessage("Success");
			}
	
			logger.info("RESP" + jsonResponse);
			logger.info("Method : editCallsInfo ends");
			return jsonResponse;
		}
		
		
		///view-crm-tasks-delete  
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-calls-delete-id")
		public @ResponseBody JsonResponse<Object> deleteCrmCallsDetails(@RequestParam String id,
				 HttpSession session) {
			logger.info("Method : deleteCrmCallsDetails function starts"+id);

			JsonResponse<Object> res = new JsonResponse<Object>();

			

			try {
				res = restClient.getForObject(env.getPipeline() + "delete-call-Details?id=" + id  , JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteCrmCallsDetails function Ends");
			
			logger.info("Response"+res);
			return res;
		}
		
		// auto search
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-crm-calls-autosearchDetailsLead" })
		public @ResponseBody JsonResponse<DropDownModel> getNameAutoSearchList(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getNameAutoSearchList starts");
			JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

			try {
				res = restClient.getForObject(env.getPipeline() + "getNameAutoSearchList?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

		/*	if (res.getMessage() != null) {

				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}*/
			logger.info("Method : getNameAutoSearchList ends");
			logger.info("AUTOSEARCHHH" + res);
			return res;
		}

		// auto search
		
				@SuppressWarnings("unchecked")
				@PostMapping(value = { "view-crm-calls-autosearchDetailsContact" })
				public @ResponseBody JsonResponse<DropDownModel> getNameAutoSearchListContact(Model model,
						@RequestBody String searchVal, BindingResult result) {
					logger.info("Method : getNameAutoSearchListContact starts");
					JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

					try {
						res = restClient.getForObject(env.getPipeline() + "getNameAutoSearchListContact?id=" + searchVal,
								JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}

		/*
		 * if (res.getMessage() != null) {
		 * 
		 * res.setCode(res.getMessage()); res.setMessage("Unsuccess"); } else {
		 * res.setMessage("success"); }
		 */
					logger.info("Method : getNameAutoSearchListContact ends");
					logger.info("AUTOSEARCHHH" + res);
					return res;
				}	
				
				
				
				// auto search
				
				@SuppressWarnings("unchecked")
				@GetMapping(value = { "view-crm-calls-autosearchDetailsRelated" })
				public @ResponseBody JsonResponse<DropDownModel> getNameAutoSearchDetailsRelated(
						@RequestParam String id1, @RequestParam String id2) {
					logger.info("Method : getNameAutoSearchDetailsRelated starts");
					JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
					logger.info("VIEWW"+res);
					try {
						logger.info("relatedType------"+id1);
						logger.info("searchVal------"+id2);
						//res = restClient.getForObject(env.getPipeline() + "getNameAutoSearchDetailsRelateds?id1=" + searchValue + "&id2=" + id,JsonResponse.class);
						res = restClient.getForObject(env.getPipeline() + "getNameAutoSearchDetailsRelateds?relatedType=" + id1 + "&searchVal=" + id2,
								 JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (res.getMessage() != null) {

						res.setCode(res.getMessage());
						res.setMessage("Unsuccess");
					} else {
						res.setMessage("success");
					}
					logger.info("Method : getNameAutoSearchDetailsRelated ends");
					logger.info("AUTOSEARCHHH" + res);
					return res;
				}	
				
				@SuppressWarnings("unchecked")
				@GetMapping("view-crm-calls-get-all-attendees")
				public @ResponseBody Object getAllAttendees(@RequestParam String hostId, HttpSession session) {

					logger.info("Method : getAllAttendees starts");
					JsonResponse<Object> resp = new JsonResponse<>();
					String organization = "";
					String orgDivision = "";
					try {
						organization = (String) session.getAttribute("ORGANIZATION");
						orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
						 
					} catch (Exception e) {
						logger.error("Error retrieving session attributes: ", e);
					}

					try {
						resp = restClient.getForObject(env.getPipeline() + "rest-get-all-attendees?orgName=" + organization
								+ "&orgDiv=" + orgDivision + "&hostId=" + hostId, JsonResponse.class);

						 
					} catch (Exception e) {
						logger.error("Error in getAllAttendees: ", e);
					}

					logger.info("Method : getAllAttendees ends");
					return resp;
				}

}
