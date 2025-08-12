package nirmalya.aathithya.webmodule.workflow.contoller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.recruitment.model.AddRecruitentModel;
import nirmalya.aathithya.webmodule.workflow.modal.WorkflowProcessModal;

@Controller
@RequestMapping(value = "workflow")
public class WorkflowProcessController {
	
	Logger logger = LoggerFactory.getLogger(WorkflowProcessController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/workflow-management")
	public String viewCandidate(Model model, HttpSession session) {

		logger.info("Method : workflow-management starts");

		logger.info("Method : workflow-management ends");

		return "workflow/workflow-process";
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/save-workflow-process")
	public @ResponseBody JsonResponse<Object> saveWorkflowProcess(Model model, HttpSession session,
			@RequestBody WorkflowProcessModal workflowProcessModal) {

		logger.info("Method : saveWorkflowProcess starts" + workflowProcessModal);

		JsonResponse<Object> resp = new JsonResponse<Object>();
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
		workflowProcessModal.setUserId(userId);
		workflowProcessModal.setOrganization(organization);
		workflowProcessModal.setOrgDivision(orgDivision);
		
		logger.info("workflow Data:::::" + workflowProcessModal);
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveWorkflowData", workflowProcessModal, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : saveWorkflowProcess ends");

		return resp;
	}

}
