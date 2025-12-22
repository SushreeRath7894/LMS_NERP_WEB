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
import nirmalya.aathithya.webmodule.pipeline.model.CrmLeadTaskModel;
import nirmalya.aathithya.webmodule.sales.model.SalesOrderNewModel;

@Controller
@Component
@RequestMapping(value = "pipeline")
public class CrmTaskController {
	Logger logger = LoggerFactory.getLogger(CrmTaskController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	/*
	 * get mapping for view-crm-deals
	 */

	@GetMapping("/view-crm-tasks")
	public String viewCRMTasks(Model model, HttpSession session) {
		logger.info("Method : viewCRMTasks start");
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
		
		logger.info("Method : viewCRMDeals end");
		return "pipeline/crm-task";
	}
	
	
	
	/*
		 * post mapping for adding pipeline
		 */

		@SuppressWarnings("unchecked")

		@PostMapping("/view-crm-tasks-add-dtls")
		public @ResponseBody JsonResponse<Object> addTask(@RequestBody CrmLeadTaskModel crmLeadTaskModel,
				HttpSession session) {

			logger.info("Method : addTask starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			logger.info("web addTask lead======================" + crmLeadTaskModel);
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

				crmLeadTaskModel.setCreatedBy(userId);
				crmLeadTaskModel.setOrganization(orgName);
				crmLeadTaskModel.setOrgDivision(orgDivision);
			
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

			logger.info("Method : addTask ends");

			return resp;
		}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-tasks-throughAjax")
		public @ResponseBody List<CrmLeadTaskModel> viewCrmTask(HttpSession session,@RequestParam String pageno) {

			logger.info("Method : viewAllTask starts");

			JsonResponse<List<CrmLeadTaskModel>> resp = new JsonResponse<List<CrmLeadTaskModel>>();

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
				resp = restClient.getForObject(env.getPipeline() + "/restViewTaskdetails?pageno="+pageno+"&userId="+userId+
						"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			
			ObjectMapper mapper = new ObjectMapper();

			String date = "";
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			
			List<CrmLeadTaskModel> taskModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<CrmLeadTaskModel>>() {
					});
			
			for (CrmLeadTaskModel i : taskModel) {
				if (i.getDueDate() != null && i.getDueDate() != "") {
					date = DateFormatter.dateFormat(i.getDueDate(), dateFormat);
					i.setDueDate(date);
					logger.info("Due date---------------"+date);
				}
			}
			
			resp.setBody(taskModel);
			logger.info("resp.getBody()-----------"+resp.getBody());
			
			logger.info("Method : viewCrmTask ends");
			return resp.getBody();
		}
		
		
		///view-crm-tasks-edit
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-tasks-edit")
		public @ResponseBody JsonResponse<List<CrmLeadTaskModel>> editTaskInfo(Model model, @RequestParam String id,
				HttpSession session) {
	
			logger.info("Method : editTaskInfo starts" + id);
			
		
	
			JsonResponse<List<CrmLeadTaskModel>> jsonResponse = new JsonResponse<List<CrmLeadTaskModel>>();
			try {
				jsonResponse = restClient.getForObject(env.getPipeline() + "editTaskInfo?id=" + id,
						JsonResponse.class);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	
			ObjectMapper mapper = new ObjectMapper();
			
			String dueDate = "";
			String reminderDate = "";
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			
			List<CrmLeadTaskModel> taskModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<CrmLeadTaskModel>>() {
					});
			
			for (CrmLeadTaskModel i : taskModel) {
			/*	if (i.getDueDate() != null && i.getDueDate() != "") {
					dueDate = DateFormatter.dateFormat(i.getDueDate(), dateFormat);
					i.setDueDate(dueDate);
					logger.info("Due date---------------"+dueDate);
				}
				
				if (i.getReminderDateid() != null && i.getReminderDateid() != "") {
					reminderDate = DateFormatter.dateFormat(i.getReminderDateid(), dateFormat);
					i.setReminderDateid(reminderDate);
					logger.info("Reminder date---------------"+reminderDate);
				}*/
				
			}
			
			logger.info("###" + taskModel);
			jsonResponse.setBody(taskModel);
	
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
	
			} else {
				jsonResponse.setMessage("Success");
			}
	
			logger.info("REsp" + jsonResponse);
			logger.info("Method : editTaskInfo ends");
			return jsonResponse;
		}
		
		
		///view-crm-tasks-delete  
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-tasks-delete-id")
		public @ResponseBody JsonResponse<Object> deleteCrmTaskDetails(@RequestParam String id,
				 HttpSession session) {
			logger.info("Method : deleteCrmTaskDetails function starts"+id);

			JsonResponse<Object> res = new JsonResponse<Object>();

			

			try {
				res = restClient.getForObject(env.getPipeline() + "delete-task-Details?id=" + id  , JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteCrmTaskDetails function Ends");
			
			logger.info("Response"+res);
			return res;
		}
		
		/*
		 * customer Auto search
		 */
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-crm-tasks-get-account-list" })
		public @ResponseBody JsonResponse<SalesOrderNewModel> getAccountNameAutoList(Model model,
				@RequestBody String searchValue, BindingResult result,HttpSession session) {
			logger.info("Method : getAccountNameAutoList starts");
			JsonResponse<SalesOrderNewModel> res = new JsonResponse<SalesOrderNewModel>();

			try {
				String userId = "";	
				try {
					userId = (String) session.getAttribute("USER_ID");
				}
				catch (Exception e){
					e.printStackTrace();
				}
				res = restClient.getForObject(env.getPipeline()+ "getAccountNameAutoSearchNewList?id=" + searchValue +"&userId="+userId,
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
			logger.info("Method : getAccountNameAutoList ends");
			return res;
		}

		// auto search
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-crm-tasks-autosearchDetailsContact" })
		public @ResponseBody JsonResponse<DropDownModel> getNameAutoSearchListTaskContact(Model model,
				@RequestBody String searchVal, BindingResult result) {
			logger.info("Method : getNameAutoSearchListTaskContact starts");
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
			logger.info("Method : getNameAutoSearchListTaskContact ends");
			logger.info("AUTOSEARCHHH" + res);
			return res;
		}	
		
		
		

}
