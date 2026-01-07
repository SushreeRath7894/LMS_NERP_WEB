package nirmalya.aathithya.webmodule.pipeline.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.controller.MasterDataApiController;

@Controller
@RequestMapping(value = "pipeline")
public class CrmInvitedMeetingsController {

	Logger logger = LoggerFactory.getLogger(CrmInvitedMeetingsController.class);

	RestTemplate restTemplate;

	EnvironmentVaribles env;
	
	MasterDataApiController master;
	
	@Autowired
	public CrmInvitedMeetingsController(MasterDataApiController master, EnvironmentVaribles env, RestTemplate restTemplate) {
		this.master = master;
		this.env = env;
		this.restTemplate = restTemplate;
	}
	
	private String userId = "";
	
	@GetMapping("/crm-invited-meetings")
	public String viewInvitedMeetings(Model model, HttpSession session) {
		logger.info("Method : viewInvitedMeetings starts");
		
		userId = (String) session.getAttribute("USER_ID");
		model.addAttribute("userId", userId);
		
		List<DropDownModel> executiveList =  master.getOwnerList(session);;
		model.addAttribute("executive", executiveList);
		
		List<DropDownModel> status =  master.getCrmTaskStatus();
		model.addAttribute("status", status);
		
		logger.info("Method : viewInvitedMeetings end");
		
		return "pipeline/crm-invited-meetings";
	}
	
	
	
	//----------------------invited Meetings
	@SuppressWarnings("unchecked")
	@GetMapping("crm-invited-meetings-details")
	public @ResponseBody JsonResponse<Object> viewLeadInvitedMeetingInfo(Model model,
			HttpSession session) {

		logger.info("Method : viewLeadInvitedMeetingInfo starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		try {
			jsonResponse = restTemplate.getForObject(env.getPipeline() + "invitedMeetingDtls?id=" + userId,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("REsp" + jsonResponse);
		logger.info("Method : viewLeadInvitedMeetingInfo ends");
		return jsonResponse;
	}
}
