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
import nirmalya.aathithya.webmodule.pipeline.model.CrmDealModel;
import nirmalya.aathithya.webmodule.pipeline.model.crmMeetingModel;

@Controller
@Component
@RequestMapping(value = "pipeline")
public class CrmMeetingController {
	Logger logger = LoggerFactory.getLogger(CrmMeetingController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	/*
	 * get mapping for view-crm-deals
	 */

	@GetMapping("/view-crm-meetings")
	public String viewCRMMeeting(Model model, HttpSession session) {
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
		
		/*try {
			DropDownModel[] lead = restClient.getForObject(env.getMasterUrl() + "/getLeadNameList",
					DropDownModel[].class);

			List<DropDownModel> leadList = Arrays.asList(lead);
			logger.info("leadList" + leadList);
			model.addAttribute("leadList", leadList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		logger.info("Method : viewCRMMeeting end");
		return "pipeline/crm-meetings";
	}
	
	
	
	/*
		 * post mapping for adding pipeline
		 */

		@SuppressWarnings("unchecked")

		@PostMapping("/view-crm-meetings-add-dtls")
		public @ResponseBody JsonResponse<Object> addMeetings(@RequestBody crmMeetingModel meetingModel,
				HttpSession session) {

			logger.info("Method : addMeetings starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			logger.info("ADDDDDDDDDDDD======================" + meetingModel);
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

				meetingModel.setCreatedBy(userId);
				meetingModel.setOrganization(orgName);
				meetingModel.setOrgDivision(orgDivision);

				meetingModel.setCreatedBy(userId);

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

			logger.info("Method : addMeetings ends");
logger.info("ADDDDDD"+resp);
			return resp;
		}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-meetings-throughAjax")
		public @ResponseBody List<crmMeetingModel> viewCrmMeetings(HttpSession session,@RequestParam String pageno) {

			logger.info("Method : viewCrmMeetings starts");

			JsonResponse<List<crmMeetingModel>> resp = new JsonResponse<List<crmMeetingModel>>();

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
				
				//resp = restClient.getForObject(env.getPipeline() + "/restViewMeetingDetails?pageno="+pageno, JsonResponse.class);
				 resp = restClient.getForObject(env.getPipeline() + "/restViewMeetingDetails?pageno="+pageno+"&userId="+userId+
						"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			
			ObjectMapper mapper = new ObjectMapper();
			
			

			String meetingFromDate = "";
			String meetingFromTime = "";
			String meetingToDate = "";
			String meetingToTime = "";
			
			String meetingRepeatFromDate = "";
			String meetingRepeatFromTime = "";
			String meetingRepeattoDate = "";
			String meetingRepeatToTime = "";
			
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			
			List<crmMeetingModel> taskModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<crmMeetingModel>>() {
					});
			
			for (crmMeetingModel i : taskModel) {
				if (i.getMeetingFromDate() != null && i.getMeetingFromDate() != "") {
					meetingFromDate = DateFormatter.dateFormat(i.getMeetingFromDate(), dateFormat);
					i.setMeetingFromDate(meetingFromDate);
					logger.info("meetingFromDate date---------------"+meetingFromDate);
				}
				
			/*	if (i.getMeetingFromTime() != null && i.getMeetingFromTime() != "") {
					meetingFromTime = DateFormatter.dateFormat(i.getMeetingFromTime(), dateFormat);
					i.setMeetingFromTime(meetingFromTime);
					logger.info("Reminder date time---------------"+meetingFromTime);
				}*/
				
				
				if (i.getMeetingToDate() != null && i.getMeetingToDate() != "") {
					meetingToDate = DateFormatter.dateFormat(i.getMeetingToDate(), dateFormat);
					i.setMeetingToDate(meetingToDate);
					logger.info("meetingtoDate date---------------"+meetingToDate);
				}
				
				/*if (i.getMeetingToTime() != null && i.getMeetingToTime() != "") {
					meetingToTime = DateFormatter.dateFormat(i.getMeetingToTime(), dateFormat);
					i.setMeetingToTime(meetingToTime);
					logger.info("Reminder To time---------------"+meetingToTime);
				}*/
				
				
				if (i.getMeetingRepeatFromDate() != null && i.getMeetingRepeatFromDate() != "") {
					meetingRepeatFromDate = DateFormatter.dateFormat(i.getMeetingRepeatFromDate(), dateFormat);
					i.setMeetingRepeatFromDate(meetingRepeatFromDate);
					logger.info("meetingRepeatFromDate date---------------"+meetingRepeatFromDate);
				}
				
			/*	if (i.getMeetingRepeatFromTime() != null && i.getMeetingRepeatFromTime() != "") {
					meetingRepeatFromTime = DateFormatter.dateFormat(i.getMeetingRepeatFromTime(), dateFormat);
					i.setMeetingRepeatFromTime(meetingRepeatFromTime);
					logger.info("meetingRepeatFromTime date time---------------"+meetingRepeatFromTime);
				}*/
				
				
				if (i.getMeetingRepeatToDate() != null && i.getMeetingRepeatToDate() != "") {
					meetingRepeattoDate = DateFormatter.dateFormat(i.getMeetingRepeatToDate(), dateFormat);
					i.setMeetingRepeatToDate(meetingRepeattoDate);
					logger.info("meetingRepeattoDate date---------------"+meetingRepeattoDate);
				}
				
			/*	if (i.getMeetingCalendarRepeatToTime() != null && i.getMeetingCalendarRepeatToTime() != "") {
					meetingRepeatToTime = DateFormatter.dateFormat(i.getMeetingCalendarRepeatToTime(), dateFormat);
					i.setMeetingCalendarRepeatToTime(meetingRepeatToTime);
					logger.info("MeetingCalendarRepeatToTime To time---------------"+meetingRepeatToTime);
				}*/
				
			}
			
			resp.setBody(taskModel);
			logger.info("resp.getBody()-----------"+resp.getBody());
			
			logger.info("Method : viewCrmMeetings ends");
			return resp.getBody();
		}
		
		
		///view-crm-tasks-edit
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-meetings-edit")
		public @ResponseBody JsonResponse<List<crmMeetingModel>> editMeetingInfo(Model model, @RequestParam String id,
				HttpSession session) {
	
			logger.info("Method : editMeetingInfo starts" + id);
			
		
	
			JsonResponse<List<crmMeetingModel>> jsonResponse = new JsonResponse<List<crmMeetingModel>>();
			try {
				jsonResponse = restClient.getForObject(env.getPipeline() + "editMeetingInfo?id=" + id,
						JsonResponse.class);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	
			ObjectMapper mapper = new ObjectMapper();
			
			String meetingFromDate = "";
			String meetingFromTime = "";
			String meetingToDate = "";
			String meetingToTime = "";
			
			String meetingRepeatFromDate = "";
			String meetingRepeatFromTime = "";
			String meetingRepeattoDate = "";
			String meetingRepeatToTime = "";
			
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			
			List<crmMeetingModel> taskModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<crmMeetingModel>>() {
					});
			
			for (crmMeetingModel i : taskModel) {
			/*	if (i.getMeetingFromDate() != null && i.getMeetingFromDate() != "") {
					meetingFromDate = DateFormatter.dateFormat(i.getMeetingFromDate(), dateFormat);
					i.setMeetingFromDate(meetingFromDate);
					logger.info("meetingFromDate date---------------"+meetingFromDate);
				}
				
			
				
				if (i.getMeetingToDate() != null && i.getMeetingToDate() != "") {
					meetingToDate = DateFormatter.dateFormat(i.getMeetingToDate(), dateFormat);
					i.setMeetingToDate(meetingToDate);
					logger.info("meetingtoDate date---------------"+meetingToDate);
				}
				
			
				
				
				if (i.getMeetingRepeatFromDate() != null && i.getMeetingRepeatFromDate() != "") {
					meetingRepeatFromDate = DateFormatter.dateFormat(i.getMeetingRepeatFromDate(), dateFormat);
					i.setMeetingRepeatFromDate(meetingRepeatFromDate);
					logger.info("meetingRepeatFromDate date---------------"+meetingRepeatFromDate);
				}
			
				
				if (i.getMeetingRepeatToDate() != null && i.getMeetingRepeatToDate() != "") {
					meetingRepeattoDate = DateFormatter.dateFormat(i.getMeetingRepeatToDate(), dateFormat);
					i.setMeetingRepeatToDate(meetingRepeattoDate);
					logger.info("meetingRepeattoDate date---------------"+meetingRepeattoDate);
				}
				
			*/
				
			}
			
			logger.info("###" + taskModel);
			jsonResponse.setBody(taskModel);
	
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
	
			} else {
				jsonResponse.setMessage("Success");
			}
	
			logger.info("REsp" + jsonResponse);
			logger.info("Method : editMeetingInfo ends");
			return jsonResponse;
		}
		
		
		///view-crm-tasks-delete  
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-meetings-delete-id")
		public @ResponseBody JsonResponse<Object> deleteCrmMeetingsDetails(@RequestParam String id,
				 HttpSession session) {
			logger.info("Method : deleteCrmMeetingsDetails function starts"+id);

			JsonResponse<Object> res = new JsonResponse<Object>();

			

			try {
				res = restClient.getForObject(env.getPipeline() + "delete-meeting-Details?id=" + id  , JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteCrmMeetingsDetails function Ends");
			
			logger.info("Response"+res);
			return res;
		}
		
		// auto search
		
		/*
		 * @SuppressWarnings("unchecked")
		 * 
		 * @PostMapping(value = { "view-crm-meetings-autosearchLead" })
		 * public @ResponseBody JsonResponse<DropDownModel> getNameMeetingLeadList(Model
		 * model,
		 * 
		 * @RequestBody String searchValue, BindingResult result) {
		 * logger.info("Method : getNameContact starts"); JsonResponse<DropDownModel>
		 * res = new JsonResponse<DropDownModel>();
		 * 
		 * try { res = restClient.getForObject(env.getPipeline() +
		 * "getNameAutoSearchList?id=" + searchValue , JsonResponse.class);
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * logger.info("Method : getNameContact ends"); logger.info("AUTOSEARCHHH" +
		 * res); return res; }
		 */
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-crm-meetings-autosearchLead" })
		public @ResponseBody JsonResponse<DropDownModel> getAutosearchLeaD(Model model,
				@RequestBody String searchValue, BindingResult result,HttpSession session) {
			logger.info("Method : getNameContact starts");
			JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

			try {
				
				String userId = "";
				
				try {
					userId = (String) session.getAttribute("USER_ID");
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
				res = restClient.getForObject(env.getPipeline() + "getNameAutoSearchList?id=" + searchValue + "&userId=" + userId, 
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getAutosearchLeaD ends");
			logger.info("AUTOSEARCHHH" + res);
			return res;
		}
		
	
		// auto search
		
				@SuppressWarnings("unchecked")
				@PostMapping(value = { "view-crm-meetings-autosearchContact" })
				public @ResponseBody JsonResponse<DropDownModel> getNameContact(Model model,HttpSession session,
						@RequestBody String searchVal, BindingResult result) {
					logger.info("Method : getNameContact starts");
					JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

					try {
						String userId = "";
						
						
						try {
							userId = (String) session.getAttribute("USER_ID");
						}
						catch (Exception e){
							e.printStackTrace();
						}
						res = restClient.getForObject(env.getPipeline() + "getNameAutoSearchListContact?id=" + searchVal + "&userId="+userId,
								JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					logger.info("Method : getNameContact ends");
					logger.info("AUTOSEARCHHH" + res);
					return res;
				}	
				
		/*get-account-data-from-contact-autosearch*/		
				@SuppressWarnings("unchecked")
				@GetMapping(value = { "view-crm-meetings-get-deal-account-data" })
				public @ResponseBody JsonResponse<DropDownModel> getAccountData(Model model,
						@RequestParam String id) {
					logger.info("Method : getAccountData starts");
					JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

					try {
						res = restClient.getForObject(env.getPipeline() + "getAccountData?id=" + id,
								JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					String message = res.getMessage();
					if (message != null && message != "") {

					} else {
						res.setMessage("Success");
					}
					logger.info("Method : getAccountData ends");
					logger.info("AUTOSEARCHHH" + res);
					return res;
				}
				
				
		
		
		
		
		// auto search
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-crm-meetings-autosearchDetailsContact" })
		public @ResponseBody JsonResponse<DropDownModel> getNameAutoSearchListPartitipentContact(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getNameAutoSearchListContact starts");
			JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

			try {
				res = restClient.getForObject(env.getPipeline() + "getNameAutoSearchListContact?id=" + searchValue,
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
			logger.info("Method : getNameAutoSearchListContact ends");
			logger.info("AUTOSEARCHHH" + res);
			return res;
		}	
		
		/*---------Coomon Api For Lead & Contact Autosearching--------------*/
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-crm-lead-contact-autosearch" })
		public @ResponseBody JsonResponse<DropDownModel> getLeadContactList(Model model,
				@RequestBody String searchValue,@RequestParam String searchId,@RequestParam String type, BindingResult result) {
			logger.info("Method : getLeadContactListAutoSearchList starts");
			JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

			try {
				res = restClient.getForObject(env.getPipeline() + "getAutosearchData?id=" + searchValue + "&type="+type + "&searchId="+searchId, 
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

				if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setCode("Success");
		} else {
			res.setCode("Unsuccess");
		}
			logger.info("Method : getLeadContactListAutoSearchList ends");
			logger.info("AUTOSEARCHHH" + res);
			return res;
		}
		
	/*---------Coomon Api For Mail Autosearching--------------*/
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-crm-lead-mail-autosearch" })
		public @ResponseBody JsonResponse<DropDownModel> getMailAutoSeaarch(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getMailAutoSeaarch starts");
			JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

			try {
				res = restClient.getForObject(env.getPipeline() + "getMailAutoSeaarch?id=" + searchValue, 
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

				if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setCode("Success");
		} else {
			res.setCode("Unsuccess");
		}
			logger.info("Method : getMailAutoSeaarch ends");
			logger.info("AUTOSEARCHHH" + res);
			return res;
		}

}
