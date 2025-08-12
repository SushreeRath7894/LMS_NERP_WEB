package nirmalya.aathithya.webmodule.edms.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.edms.model.DocumentControlModel;

@Controller
@RequestMapping(value = "edms")
public class WorkFlowApproveController {
	Logger logger = LoggerFactory.getLogger(WorkFlowApproveController.class);

	RestTemplate restClient;
	EnvironmentVaribles env;

	@Autowired
	public WorkFlowApproveController(EnvironmentVaribles EnvironmentVaribles, RestTemplate RestTemplate) {
		this.env = EnvironmentVaribles;
		this.restClient = RestTemplate;
	}

	@GetMapping(value = { "workflow-approval" })
	public String workflowApprove(HttpSession session, Model model) {
		logger.info("Method : workflowApprove starts");

		logger.info("Method : workflowApprove ends");
		return "edms/workflowApprove";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("workflow-approval-view")
	public @ResponseBody Object workFlowApproveView(HttpSession session) {

		logger.info("Method :workFlowApproveView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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
		try {
			resp = restClient.getForObject(env.getEdms() + "rest-workFlowApproveView?userId=" + userId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :workFlowApproveView ends");
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "workflow-approval-save" })
	public @ResponseBody JsonResponse<Object> approveWorkFlow(@RequestBody DocumentControlModel documentControlModel,
			HttpSession session) {
		logger.info("Method : approveWorkFlow function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		documentControlModel.setCreatedBy(userId);
		documentControlModel.setOrganization(organization);
		documentControlModel.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getEdms() + "rest-approveWorkFlow", documentControlModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : approveWorkFlow function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}
	
	
	//Notification Update
	
	@SuppressWarnings("unchecked")
	@GetMapping("workflow-approval-notificationUpdate")
	public @ResponseBody JsonResponse<Object> notificationUpdate(@RequestParam String id, HttpSession session) {
		logger.info("Method :notificationUpdate starts");
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
		try {

			resp = restClient.getForObject(env.getEdms() + "rest-notificationUpdate?id=" + id + "&userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :notificationUpdate ends" + resp);
		return resp;
	}
}
