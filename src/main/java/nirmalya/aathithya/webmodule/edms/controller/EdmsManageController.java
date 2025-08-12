package nirmalya.aathithya.webmodule.edms.controller;


import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
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

import nirmalya.aathithya.webmodule.edms.model.DocumentManageAccessModel;
import nirmalya.aathithya.webmodule.edms.model.DocumentManageModel;
import nirmalya.aathithya.webmodule.edms.model.WorkFlowModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;



	@Controller
	@RequestMapping(value = "edms")
	public class EdmsManageController {

		Logger logger = LoggerFactory.getLogger(EdmsManageController.class);

		RestTemplate restClient;  

		EnvironmentVaribles env;
		
		@Autowired
		public EdmsManageController(EnvironmentVaribles EnvironmentVaribles,RestTemplate RestTemplate) {
			this.env = EnvironmentVaribles;
			this.restClient=RestTemplate;
		}
		
		
		@GetMapping(value = { "manage-control" })
		public String manageControl( HttpSession session,Model model) {
			logger.info("Method : manageControl starts");
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				
				userId = (String) session.getAttribute("USER_ID"); 
				orgName = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			
			// drop down for employee list
			try {
				DropDownModel[] emplist = restClient.getForObject(env.getEdms() + "getDMSEmpList?orgName="+orgName+"&orgDivision="+orgDivision + "&userId="+userId,
						DropDownModel[].class);
				System.out.println(emplist);
				
				  List<DropDownModel> emplists = Arrays.asList(emplist);
				  model.addAttribute("emplists", emplists);
				 
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			
			logger.info("Method : manageControl ends");
			return "edms/documentManage";
		}
		@GetMapping(value = { "manage-access" })
		public String manageAccess( HttpSession session,Model model) {
			logger.info("Method : manageAccess starts");
			
			logger.info("Method : manageAccess ends");
			return "edms/documentAccess";
		}
		@GetMapping(value = { "view-operation" })
		public String viewDashboard( HttpSession session,Model model) {
			logger.info("Method : viewDashboard starts");
			
			logger.info("Method : viewDashboard ends");
			return "edms/viewDashboard";
		}
		@GetMapping(value = { "view-document" })
		public String viewDocument( HttpSession session,Model model) {
			logger.info("Method : viewDocument starts");
			
			logger.info("Method : viewDocument ends");
			return "edms/viewDocument";
		}
		
		/******Get user employee listing******/
		@SuppressWarnings("rawtypes")
		@GetMapping("/manage-control-get-user-listing")
		public @ResponseBody JsonResponse viewUserListing(Model model, HttpSession session) {
			logger.info("Method : viewUserListing starts");

			JsonResponse jsonResponse = new JsonResponse();
			String organization = "";
			String orgDivision = "";
			String userId = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				jsonResponse = restClient.getForObject(env.getEdms() + "view-useremp-list?&organization="
						+ organization + "&orgDivision=" + orgDivision +"&userId="+userId, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : viewUserListing ends");
			return jsonResponse;
		}
		/*
		 * Add user Group 
		 */

		@SuppressWarnings("unchecked")
		@PostMapping("manage-control-add-usergroup")
		public @ResponseBody JsonResponse<Object> createUserGroup(HttpSession session, @RequestBody DocumentManageModel documentmanageModel ) 
		
		{
			logger.info("Method : createUserGroup starts");
			System.out.print("@@@@@@@@@@@@@@@@@@@@"+documentmanageModel);
			JsonResponse<Object> resp = new JsonResponse<Object>();

			String userId = "";
			String dateFormat = "";
			String organization=""; 
			String orgDivision="";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}
			documentmanageModel.setOrganization(organization);
			documentmanageModel.setOrganizationDivision(orgDivision);
			documentmanageModel.setCreatedBy(userId);
			logger.info("documentControlModel===" + documentmanageModel);
			try {
				resp = restClient.postForObject(env.getEdms() + "rest-add-usergroup", documentmanageModel,
					JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String message = resp.getMessage();

			if (message != null && message != "") {
				//resp.setMessage("Unsuccess");
			} else {
				//resp.setMessage("Success");
			}

				System.out.println("Resp ==="+ resp);
			
			logger.info("Method : createUserGroup ends");

			return resp;
		}
		/******Get user employee listing******/
		@SuppressWarnings("rawtypes")
		@GetMapping("/manage-control-get-usergroup-listing")
		public @ResponseBody JsonResponse viewUserGroupListing(Model model, HttpSession session) {
			logger.info("Method : viewUserGroupListing starts");

			JsonResponse jsonResponse = new JsonResponse();
			String organization = "";
			String orgDivision = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				jsonResponse = restClient.getForObject(env.getEdms() + "view-usergroup-list?&organization="
						+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : viewUserGroupListing ends");
			return jsonResponse;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@GetMapping("manage-control-edit-usergroup")
		public @ResponseBody JsonResponse editUserGroup(@RequestParam String id, HttpSession session) {
			logger.info("Method : editUserGroup starts");

			JsonResponse jsonResponse = new JsonResponse();
			String organization = "";
			String orgDivision = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				jsonResponse = restClient.getForObject(env.getEdms() + "rest-edit-usergroup?&id="+id+"&organization="
						+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : editUserGroup ends");
			return jsonResponse;
		}
		/******Get Access Document listing******/
		@SuppressWarnings({ "unchecked", "unused" })
		@GetMapping("manage-access-document-listing")
		public @ResponseBody Object getManageAccessListing(HttpSession session,String id) {
			logger.info("Method :getManageAccessListing starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			String date1 = "";
			try {
				
//				userId = (String) session.getAttribute("UESR_ID");
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {
				e.printStackTrace();
			}
		
			try {
				resp = restClient.getForObject(env.getEdms() + "rest-accessDocList?id="+id+"&organization="+organization +"&orgDivision="+orgDivision,
				JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :getManageAccessListing ends" + resp);
			return resp;
		}
		/******Get Access Document listing by document type& date******/
		@SuppressWarnings({ "unchecked", "unused" })
		@GetMapping("manage-access-document-list-by-type")
		public @ResponseBody Object getManageAccessListing(HttpSession session,String doctype,String date) {
			logger.info("Method :getManageAccessListing starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			String date1 = "";
			try {
			//	String dateFormat = (String) (session).getAttribute("DATEFORMAT");
				//date1 = DateFormatter.inputDateFormat(date, dateFormat);
//				userId = (String) session.getAttribute("UESR_ID");
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {
				e.printStackTrace();
			}
		
			try {
				resp = restClient.getForObject(env.getEdms() + "rest-accessDocListByType?doctype=" + doctype +"&date=" + date +"&organization="+organization +"&orgDivision="+orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :getManageAccessListing ends" + resp);
			return resp;
		}
		
		@SuppressWarnings("unchecked")
		@PostMapping("manage-access-save-user-access")
		public @ResponseBody JsonResponse<Object> saveUserAccess(HttpSession session, @RequestBody List<DocumentManageAccessModel> accessModel ) 
		
		{
			logger.info("Method : saveUserAccess starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();

			String userId = "";
			String organization=""; 
			String orgDivision="";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}
			System.out.print(accessModel+"@@@@@@@@accessModel");
			for (DocumentManageAccessModel m : accessModel) {
				m.setCreatedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
			}
			
			 logger.info("saveUserAccess===" + accessModel);
			try {
				resp = restClient.postForObject(env.getEdms() + "rest-saveUserAccessManage", accessModel,
			    JsonResponse.class);
					System.out.println("Resp ==="+ accessModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String message = resp.getMessage();

			if (message != null && message != "") {
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method : saveUserAccess ends");

			return resp;
		}
		/******Get Access Document listing******/
		
		@SuppressWarnings({ "unchecked", "unused" })
		@GetMapping("manage-control-get-access-listing")
		public @ResponseBody Object getAccessListing(HttpSession session) {
			logger.info("Method :getAccessListing starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			String date1 = "";
			try {
				
//				userId = (String) session.getAttribute("UESR_ID");
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {
				e.printStackTrace();
			}
		
			try {
				resp = restClient.getForObject(env.getEdms() + "rest-accessList?organization="+organization +"&orgDivision="+orgDivision,
				JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("resp============"+resp);
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :getAccessListing ends" + resp);
			return resp;
		}
		

		/******Get  Document Accessed listing******/
		@SuppressWarnings({ "unchecked", "unused" })
		@GetMapping("manage-access-document-accessed-list")
		public @ResponseBody Object getDocumentAccessedListing(HttpSession session,String id,String accessid) {
			logger.info("Method :getDocumentAccessedListing starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			String date1 = "";
			try {
				
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION"); 
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.print(id+"@@@@@@@@@@@@"+userId);
			try {
				resp = restClient.getForObject(env.getEdms() + "rest-document-accessedList?id="+id+"&accessid="+accessid+"&organization="+organization +"&orgDivision="+orgDivision,
				JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("resp============"+resp);
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :getDocumentAccessedListing ends" + resp);
			return resp;
		}
	
		/******Get user employee listing******/
		@SuppressWarnings("rawtypes")
		@GetMapping("/view-document-user-listing")
		public @ResponseBody JsonResponse viewDocumentuserListing(Model model, HttpSession session) {
			logger.info("Method : viewDocumentuserListing starts");

			JsonResponse jsonResponse = new JsonResponse();
			String organization = "";
			String orgDivision = "";
			String userid = "";
			

			try {
				userid = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(userid+"@@@@@@@@@@@@");
			try {
				jsonResponse = restClient.getForObject(env.getEdms() + "rest-view-userdocumentlist?userid="+userid+"&organization="
						+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : viewDocumentuserListing ends");
			return jsonResponse;
		}
		
		/******Get auditlog listing******/
		@SuppressWarnings("rawtypes")
		@GetMapping("/manage-control-get-auditlog-listing")
		public @ResponseBody JsonResponse viewAuditLogList(Model model, HttpSession session) {
			logger.info("Method : viewAuditLogList starts");

			JsonResponse jsonResponse = new JsonResponse();
			String organization = "";
			String orgDivision = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				jsonResponse = restClient.getForObject(env.getEdms() + "rest-viewauditlog-list?&organization="
						+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method : viewAuditLogList ends");
			return jsonResponse;
		}
		
		/******Get Access Document Workflow******/
		@SuppressWarnings({ "unchecked", "unused" })
		@GetMapping("manage-control-document-workflow")
		public @ResponseBody Object getManageAccessWorkflow(HttpSession session,@RequestParam String userId) {
			logger.info("Method :getManageAccessWorkflow starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String organization = "";
			String orgDivision = "";
			String date1 = "";
			try {

				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("userId"+userId);
			try {
				resp = restClient.getForObject(env.getEdms() + "rest-accessWorkflow?id=" + userId + "&organization="
						+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :getManageAccessWorkflow ends" + resp);
			return resp;
		}
		
		
		
		// View Start Wrok Flow
		@SuppressWarnings("unchecked")
		@GetMapping("manage-control-view-workFlow")
		public @ResponseBody Object documentControlViewworkFlow(HttpSession session, @RequestParam String docId) {
			logger.info("Method :documentControlViewworkFlow starts");
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
				resp = restClient
						.getForObject(env.getEdms() + "rest-documentworkFlowView?userId=" + userId + "&organization="
										+ organization + "&orgDivision=" + orgDivision + "&docId=" + docId,JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :documentControlViewworkFlow ends");
			return resp;
		}
		
		@SuppressWarnings("unchecked")
		@PostMapping("manage-control-workFlow-add")
		public @ResponseBody JsonResponse<Object> addWorkFlow(HttpSession session,
				@RequestBody WorkFlowModel workFlowModel) {
			logger.info("Method : addWorkFlow starts");
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
			workFlowModel.setUserId(userId);
			workFlowModel.setOrganization(organization);
			workFlowModel.setOrgDiv(orgDivision);
			try {
				resp = restClient.postForObject(env.getEdms() + "rest-addWorkFlow", workFlowModel, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String message = resp.getMessage();

			if (message != null && message != "") {
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method : addWorkFlow ends");

			return resp;
		}
}


