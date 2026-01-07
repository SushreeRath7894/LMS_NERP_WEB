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
import nirmalya.aathithya.webmodule.pipeline.model.CrmDailyWorkModel;
import nirmalya.aathithya.webmodule.pipeline.model.CrmDealModel;

@Controller
@Component
@RequestMapping(value = "pipeline")
public class CrmDailyActivityController {
	Logger logger = LoggerFactory.getLogger(CrmDailyActivityController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	
	/*
	 * get mapping for view-crm-deals
	 */

	@GetMapping("/view-crm-daily-work")
	public String viewCRMDailyActivity(Model model, HttpSession session) {
		logger.info("Method : viewCRMDailyActivity start");
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
			DropDownModel[] owner = restClient.getForObject(env.getPipeline() + "/getOwnerList",
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
			DropDownModel[] lead = restClient.getForObject(env.getPipeline() + "/getLeadNameList",
					DropDownModel[].class);

			List<DropDownModel> leadList = Arrays.asList(lead);
			logger.info("leadList" + leadList);
			model.addAttribute("leadList", leadList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : viewCRMDailyActivity end");
		return "pipeline/crm-daily-activity";
	}
	
	
	
	/*
		 * post mapping for adding pipeline
		 */

		@SuppressWarnings("unchecked")

		@PostMapping("/view-crm-daily-work-add")
		public @ResponseBody JsonResponse<Object> addDailyWork(@RequestBody CrmDailyWorkModel crmDailyWorkModel,
				HttpSession session) {

			logger.info("Method : addDailyWork starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			logger.info("web addDailyWork======================" + crmDailyWorkModel);
			try {
				String userId = "";
				try {
					userId = (String) session.getAttribute("USER_ID");
				} catch (Exception e) {
					e.printStackTrace();
				}

				crmDailyWorkModel.setCreatedBy(userId);

				resp = restClient.postForObject(env.getPipeline() + "/addDailyWork", crmDailyWorkModel, JsonResponse.class);

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

			logger.info("Method : addDailyWork ends");

			return resp;
		}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-daily-work-throughAjax")
		public @ResponseBody List<CrmDailyWorkModel> viewCrmDailyAjax(HttpSession session) {

			logger.info("Method : viewCrmDailyAjax starts");

			JsonResponse<List<CrmDailyWorkModel>> resp = new JsonResponse<List<CrmDailyWorkModel>>();

			try {
				resp = restClient.getForObject(env.getPipeline() + "/restviewCrmDailyAjax", JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String date = "";
			String date1 = "";
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			
			List<CrmDailyWorkModel> crmDailyWorkModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<CrmDailyWorkModel>>() {
					});
			
			for (CrmDailyWorkModel i : crmDailyWorkModel) {
				if (i.getTodayDate() != null && i.getTodayDate() != "") {
					date = DateFormatter.dateFormat(i.getTodayDate(), dateFormat);
					i.setTodayDate(date);
					logger.info("start date---------------"+date);
				}
				
			}
			
			resp.setBody(crmDailyWorkModel);
			logger.info("resp.getBody()-----------"+resp.getBody());
			
			logger.info("Method : viewCrmDailyAjax ends");
			return resp.getBody();
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-daily-work-edit")
		public @ResponseBody JsonResponse<List<CrmDailyWorkModel>> editDailyWork(Model model, @RequestParam String id,
				HttpSession session) {
	
			logger.info("Method : editDailyWork starts" + id);
			
			JsonResponse<List<CrmDailyWorkModel>> jsonResponse = new JsonResponse<List<CrmDailyWorkModel>>();
			try {
				jsonResponse = restClient.getForObject(env.getPipeline() + "editDailyWork?id=" + id,
						JsonResponse.class);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	
			ObjectMapper mapper = new ObjectMapper();
			String date = "";
			String date1 = "";
			String dateFormat = (String) (session).getAttribute("DATEFORMAT");
			
			List<CrmDailyWorkModel> crmDailyWorkModel = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<CrmDailyWorkModel>>() {
					});
			
			for (CrmDailyWorkModel i : crmDailyWorkModel) {
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
			
			logger.info("###" + crmDailyWorkModel);
			jsonResponse.setBody(crmDailyWorkModel);
	
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
	
			} else {
				jsonResponse.setMessage("Success");
			}
	
			logger.info("REsp" + jsonResponse);
			logger.info("Method : editDailyWork ends");
			return jsonResponse;
		}
		
		
		///view-crm-tasks-delete  
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-crm-daily-work-delete-id")
		public @ResponseBody JsonResponse<Object> deleteCrmDailyWork(@RequestParam String id,
				 HttpSession session) {
			logger.info("Method : deleteCrmDailyWork function starts"+id);

			JsonResponse<Object> res = new JsonResponse<Object>();

			

			try {
				res = restClient.getForObject(env.getPipeline() + "deleteCrmDailyWork?id=" + id  , JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteCrmDailyWork function Ends");
			
			logger.info("Response"+res);
			return res;
		}

}
