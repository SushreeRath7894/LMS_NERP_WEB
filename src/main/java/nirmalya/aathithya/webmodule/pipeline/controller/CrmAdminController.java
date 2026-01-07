package nirmalya.aathithya.webmodule.pipeline.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.controller.MasterDataApiController;
import nirmalya.aathithya.webmodule.pipeline.model.AdminTaskAssignModel;
import nirmalya.aathithya.webmodule.pipeline.service.NotificationService;
import nirmalya.aathithya.webmodule.user.service.CustomAuthenticationSuccessHandler;

@Controller
@RequestMapping(value = "pipeline")
public class CrmAdminController {

	Logger logger = LoggerFactory.getLogger(CrmAdminController.class);

	RestTemplate restTemplate;

	EnvironmentVaribles env;

	MasterDataApiController master;
	
	@Autowired
	private NotificationService notificationService;

	public CrmAdminController() {

	}

	@Autowired
	public CrmAdminController(MasterDataApiController master, EnvironmentVaribles env, RestTemplate restTemplate) {
		this.master = master;
		this.env = env;
		this.restTemplate = restTemplate;
	}

	public static String org = "";
	public static String orgDiv = "";
	public static String userId = "";

	@GetMapping(value = "admin-executive-report")
	public String viewExecutivePage(Model model, HttpSession session) {

		logger.info("Method: viewExecutivePage starts");

		List<DropDownModel> executiveList = master.getOwnerList(session);
		model.addAttribute("executive", executiveList);
		
		org = (String) session.getAttribute("ORGANIZATION");
		orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		userId = (String) session.getAttribute("USER_ID");

		

		logger.info("Method: viewExecutivePage ends");

		return "pipeline/admin-executive-report";
	}

	@GetMapping(value = "admin-task-assign")
	public String viewTaskAssign(Model model, HttpSession session) {

		logger.info("Method: viewTaskAssign starts");

		List<DropDownModel> executiveList = master.getOwnerList(session);;

		model.addAttribute("executive", executiveList);

		logger.info("Method: viewTaskAssign ends");

		return "pipeline/admin-task-assign";
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "admin-task-assign-add-task")
	public @ResponseBody JsonResponse<Object> addTask(HttpSession session,@RequestBody AdminTaskAssignModel taskAssign)
			throws AddressException {

		logger.info("Method: addTask starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		org = (String) session.getAttribute("ORGANIZATION");
		orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		userId = (String) session.getAttribute("USER_ID");

		taskAssign.setCreatedBy(userId);
		taskAssign.setOrg(org);
		taskAssign.setOrgDiv(orgDiv);
		logger.info("Organization"+taskAssign);
		System.out.println("ORG========>>>"+org);
		System.out.println("Division========>>>"+orgDiv);
		logger.info("URL========>>>"+env.getPipeline() + "add-task", taskAssign, JsonResponse.class);
		try {
			resp = restTemplate.postForObject(env.getPipeline() + "add-task", taskAssign, JsonResponse.class);
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
		}

		ObjectMapper mapper = new ObjectMapper();

		logger.info("Method: addTask ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "admin-task-assign-all-tasks")
	public @ResponseBody JsonResponse<Object> fetchAllTasks(HttpSession session) {

		logger.info("Method: fetchAllTasks starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String org = "";
		String orgDiv = "";
		String role = "";
		
		org = (String) session.getAttribute("ORGANIZATION");
		orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		
			role = (String) session.getAttribute("IS_SALES_MANAGER");
			userId = (String)session.getAttribute("USER_ID");
		
		try {
			resp = restTemplate.getForObject(env.getPipeline() + "all-tasks?org=" + org + "&orgDiv=" + orgDiv +"&role="+role +"&userId="+userId,
					JsonResponse.class);
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
		}

		logger.info("Method: fetchAllTasks starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "admin-task-assign-user-tasks")
	public @ResponseBody JsonResponse<Object> fetchAssignedTasks(@RequestParam String userId,
			@RequestParam String type) {

		logger.info("Method: fetchAssignedTasks starts" + userId);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getPipeline() + "user-tasks?userId=" + userId + "&type=" + type,
					JsonResponse.class);
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
		}

		logger.info("Method: fetchAssignedTasks starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "admin-task-assign-task")
	public @ResponseBody JsonResponse<Object> fetchTask(@RequestParam String taskId) {

		logger.info("Method: fetchTask starts" + userId);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getPipeline() + "fetch-task?taskId=" + taskId, JsonResponse.class);
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
		}

		logger.info("Method: fetchTask starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "admin-task-assign-chnage-status")
	public @ResponseBody JsonResponse<Object> chnageStatus(@RequestParam String id, @RequestParam String taskId) {

		logger.info("Method: chnageStatus starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getPipeline() + "chnage-status?id=" + id + "&taskId=" + taskId,
					JsonResponse.class);
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
		}

		logger.info("Method: chnageStatus starts");
		return resp;
	}

	/*
	 * This Api for Executive Rejected the Task
	 * 
	 */

	@SuppressWarnings("unchecked")
	@GetMapping(value = "admin-task-assign-reject-status")
	public @ResponseBody JsonResponse<Object> rejectedStatus(@RequestParam String id, @RequestParam String taskStatus,
			@RequestParam String reasons, @RequestParam String executiveMail, @RequestParam String assignedMailId) {

		logger.info("Method: rejectedStatus starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(
					env.getPipeline() + "reject-status?id=" + id + "&taskStatus=" + taskStatus + "&reasons=" + reasons
							+ "&assignedMailId=" + assignedMailId + "&executiveMail=" + executiveMail,
					JsonResponse.class);
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
		}

		logger.info("Method: rejectedStatus starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "admin-task-assign-lead-trasfer")
	public @ResponseBody JsonResponse<Object> leadTransfer(@RequestParam String leadId, String leadNewOwner,
			String leadNewOwnerId, String fromDate, String toDate, String transferType, String adminMailId,
			String leadMailId, String tranExecutiveMailId, String leadOwnerMailId, String prevLeadsOwner) {

		logger.info("Method: leadTransfer starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getPipeline() + "lead-transfer?leadId=" + leadId + "&leadNewOwner="
					+ leadNewOwner + "&leadNewOwnerId=" + leadNewOwnerId + "&fromDate=" + fromDate + "&toDate=" + toDate
					+ "&transferType=" + transferType + "&tranExecutiveMailId=" + tranExecutiveMailId
					+ "&leadOwnerMailId=" + leadOwnerMailId + "&adminMailId=" + adminMailId + "&leadMailId="
					+ leadMailId + "&prevLeadsOwner=" + prevLeadsOwner + "&transferredBy=" + userId + "&org="
					+ CustomAuthenticationSuccessHandler.org.toString() + "&orgDiv="
					+ CustomAuthenticationSuccessHandler.orgDiv.toString(), JsonResponse.class);
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
			e.printStackTrace();
		}

		logger.info("Method: leadTransfer starts" + resp.getBody());
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "admin-lead-transferred-history")
	public @ResponseBody JsonResponse<Object> leadTransferhistory(@RequestParam String id) {

		logger.info("Method: leadTransferhistory starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(env.getPipeline() + "lead-transfer-history?userId=" + userId + "&id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
			e.printStackTrace();
		}

		logger.info("Method: leadTransferhistory starts");
		return resp;
	}

	/*
	 * @GetMapping(value = "admin-task-assign-notificaton") public @ResponseBody
	 * JsonResponse<Object> adminNotification(HttpSession session) {
	 * 
	 * logger.info("Method: adminNotification");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>();
	 * 
	 * List<String> role = (List<String>) session.getAttribute("USER_ROLES");
	 * 
	 * Optional<String> data = role.stream().filter(d ->
	 * d.equals("rol003")).findAny(); try { String userId = ""; try { userId =
	 * (String) session.getAttribute("USER_ID"); } catch (Exception e) {
	 * e.printStackTrace(); } if (data.isPresent()) {
	 * 
	 * resp = restTemplate.getForObject( env.getPipeline() +
	 * "admin-notification?org=" + org + "&orgDiv=" + orgDiv + "&userId=" + userId,
	 * JsonResponse.class);
	 * 
	 * } else { resp.setMessage("You Have No New Notifications!");
	 * resp.setCode("Success"); } } catch (Exception e) {
	 * resp.setMessage(e.getMessage()); resp.setCode("Failed"); e.printStackTrace();
	 * }
	 * 
	 * System.out.println("ASHSIHS MY NAME KHAN ----->>>> " + resp);
	 * 
	 * return resp; }
	 */

    //  Get All Admin Notifications - Addded By Pankaj Kumar	
	@GetMapping(value = "admin-task-assign-notificaton")
	public @ResponseBody JsonResponse<Object> adminNotification(HttpSession session) {
	    logger.info("Method: adminNotification");

	    JsonResponse<Object> resp = new JsonResponse<Object>();

	    List<String> role = (List<String>) session.getAttribute("USER_ROLES");

	    Optional<String> data = role.stream().filter(d -> d.equals("rol003")).findAny();
	    try {
	        String userId = "";
	        try {
	            userId = (String) session.getAttribute("USER_ID");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        if (data.isPresent()) {
	            resp = notificationService.getAdminNotification(userId, org, orgDiv);
	        } else {
	            resp.setMessage("You Have No New Notifications!");
	            resp.setCode("Success");
	        }
	    } catch (Exception e) {
	        resp.setMessage(e.getMessage());
	        resp.setCode("Failed");
	        e.printStackTrace();
	    }

	    return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "admin-approval")
	public @ResponseBody JsonResponse<Object> adminApproval(HttpSession session, @RequestParam String lead,
			@RequestParam String rejectStatus) {

		logger.info("Method: adminApproval");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		
		orgName = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		List<String> role = (List<String>) session.getAttribute("USER_ROLES");

		Optional<String> data = role.stream().filter(d -> d.equals("rol003")).findAny();
		System.out.println("DATA FOR ADMIN----->"+data);
System.out.println("URL FOR ADMIN=======>"+env.getPipeline() + "admin-approval?org=" + orgName + "&orgDiv=" + orgDivision + "&lead=" + lead
								+ "&status=" + rejectStatus + "&userId=" + (String) session.getAttribute("USER_ID"));
		try {
//			if (data.isPresent()) {

				resp = restTemplate.getForObject(
						env.getPipeline() + "admin-approval?orgName=" + orgName + "&orgDivision=" + orgDivision + "&lead=" + lead
								+ "&status=" + rejectStatus + "&userId=" + (String) session.getAttribute("USER_ID"),
						JsonResponse.class);

//			} else {
//				resp.setMessage("Only Admin can Approve lead!");
//				resp.setCode("Failed");
//			}
		} catch (Exception e) {
			resp.setMessage(e.getMessage());
			resp.setCode("Failed");
			e.printStackTrace();
		}

		System.out.println("ASHSIHS MY NAME KHAN ----->>>> " + resp);

		return resp;
	}

}
