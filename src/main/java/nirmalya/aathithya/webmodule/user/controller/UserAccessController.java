package nirmalya.aathithya.webmodule.user.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.MailService;
import nirmalya.aathithya.webmodule.master.controller.EmailConfigController;
import nirmalya.aathithya.webmodule.master.model.EmailConfigModel;
import nirmalya.aathithya.webmodule.user.model.ModulesAccessModel;
import nirmalya.aathithya.webmodule.user.model.RolesAccessModel;
import nirmalya.aathithya.webmodule.user.model.UserAccessModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "user")
public class UserAccessController {

	Logger logger = LoggerFactory.getLogger(UserAccessController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PasswordEncoder pw;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	EmailConfigController emailController;

	@GetMapping(value = { "manage-users" })
	public String manageUsers(Model model, HttpSession session) {
		logger.info("Method : manageUsers starts");
		
		try {
			DropDownModel[] userType = restClient.getForObject(env.getUserUrl()+"getUserTypeForUser", DropDownModel[].class);
			List<DropDownModel> userTypeList = Arrays.asList(userType);
			
			model.addAttribute("userTypeList", userTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : manageUsers starts");
		return "user/manageUsers";
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "manage-users-get-listing" })
	public @ResponseBody List<UserAccessModel> getUsersList(Model model, HttpSession session) {
		logger.info("Method : getUsersList starts");
		
		String dateFormat = "";

		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<UserAccessModel>> res = new JsonResponse<List<UserAccessModel>>();

		try {
			res = restClient.getForObject(env.getUserUrl() + "getUsersList", JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<UserAccessModel> dataList = mapper.convertValue(res.getBody(),
					new TypeReference<List<UserAccessModel>>() {
					});

			for (UserAccessModel m : dataList) {
				if (m.getUserStatus()) {
					m.setStatus("Active");
				} else {
					m.setStatus("Inactive");
				}

				if (m.getCreatedBy() == null || m.getCreatedBy() == "") {
					m.setCreatedBy("SYSTEM");
				}
				
				m.setCreatedDate(DateFormatter.dateFormat(m.getCreatedDate(), dateFormat));
			}

			res.setBody(dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getUsersList ends");
		return res.getBody();
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "manage-users-get-roles-listing" })
	public @ResponseBody List<RolesAccessModel> getUserRolesList(Model model, HttpSession session) {
		logger.info("Method : getUserRolesList starts");

		JsonResponse<List<RolesAccessModel>> res = new JsonResponse<List<RolesAccessModel>>();
		String organization = "";
		String orgDivision = "";

		try {
			organization= (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			res = restClient.getForObject(env.getUserUrl() + "getRolesList?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<RolesAccessModel> dataList = mapper.convertValue(res.getBody(),
					new TypeReference<List<RolesAccessModel>>() {
					});

			for (RolesAccessModel m : dataList) {
				if (m.getRoleStatus()) {
					m.setStatus("Active");
				} else {
					m.setStatus("Inactive");
				}

				if (m.getCreatedBy() == null || m.getCreatedBy() == "") {
					m.setCreatedBy("SYSTEM");
				}
			}

			res.setBody(dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getUserRolesList ends");
		return res.getBody();
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "manage-users-get-employee-listing" })
	public @ResponseBody List<DropDownModel> getUserEmployeeList(Model model) {
		logger.info("Method : getUserEmployeeList starts");
		
		JsonResponse<List<DropDownModel>> res = new JsonResponse<List<DropDownModel>>();
		
		try {
			res = restClient.getForObject(env.getUserUrl() + "getUserEmployeeList", JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getUserEmployeeList ends");
		return res.getBody();
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "manage-users-get-emplist-by-name" })
	public @ResponseBody JsonResponse<List<ModulesAccessModel>> getUserEmployeeListByName(Model model, @RequestBody String id) {
		logger.info("Method : getUserEmployeeListByName starts");
		
		JsonResponse<List<ModulesAccessModel>> res = new JsonResponse<List<ModulesAccessModel>>();
		
		try {
			res = restClient.getForObject(env.getUserUrl() + "getUserEmployeeListByName?id="+id, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("success");
		}
		
		logger.info("Method : getUserEmployeeListByName ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-users-save-data")
	public @ResponseBody JsonResponse<Object> saveUserMaster(@RequestBody UserAccessModel data, HttpSession session) {
		logger.info("Method : saveUserMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCreatedBy(userId);
		
		if(data.getPassword() != null && data.getPassword() != "") {
			String password = pw.encode(data.getPassword());
			data.setPassword(password);
		}
 
		try {
			resp = restClient.postForObject(env.getUserUrl() + "saveUserMaster", data, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
			String subject = "From :-" + "Sagar Anchal" + System.lineSeparator() + "Subject:-"
					+ "Demo Email";

			String emailMessage = "Demo Email Message";
			
			List<String> emailIds = new ArrayList<String>();
			List<String> ccUsers = new ArrayList<String>();
			String[] attachFiles = null;
			
			emailIds.add(data.getUserEmail());
			
			EmailConfigModel emailDtls = emailController.getMailConfigurationDtls();
			try {
				EmailAttachmentSender.sendEmailWithAttachments(emailDtls.getHostUrl(), emailDtls.getHostPort(), emailDtls.getEmailId(),
						emailDtls.getEmailPassword(), emailIds, ccUsers, subject, emailMessage, attachFiles);
				logger.info("Email sent.");
			} catch (Exception ex) {
				logger.info("Could not send email.");
				ex.printStackTrace();
			}
		}

		logger.info("Method : saveUserMaster ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-users-edit-data")
	public @ResponseBody JsonResponse<UserAccessModel> editUserMaster(Model model, @RequestBody String data,
			HttpSession session, BindingResult result) {
		logger.info("Method : editUserMaster starts");

		JsonResponse<UserAccessModel> resp = new JsonResponse<UserAccessModel>();

		try {
			resp = restClient.getForObject(env.getUserUrl() + "editUserMaster?id=" + data, JsonResponse.class);
			
			ObjectMapper mapper = new ObjectMapper();
			
			UserAccessModel userData = mapper.convertValue(resp.getBody(),
					new TypeReference<UserAccessModel>() {
					});

			if(userData.getUserRole()!=null && userData.getUserRole()!="") {
				String[] arr = userData.getUserRole().split(",");
				
				List<String> roleList = new ArrayList<String>();
				
				for(int i = 0; i < arr.length; i++) {
					roleList.add(arr[i]);
				}
				
				userData.setRoleList(roleList);
			}
			if(userData.getRoleName()!=null && userData.getRoleName()!="") {
				String[] arr = userData.getRoleName().split(",");
				
				List<String> roleNameList = new ArrayList<String>();
				
				for(int i = 0; i < arr.length; i++) {
					roleNameList.add(arr[i]);
				}
				
				userData.setRoleNameList(roleNameList);
			}
			
			resp.setBody(userData);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : editUserMaster starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-users-delete")
	public @ResponseBody JsonResponse<Object> deleteUserMaster(@RequestBody List<DropDownModel> roleList, HttpSession session) {
		logger.info("Method : deleteUserMaster starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(DropDownModel m : roleList) {
			m.setName(userId);
		}
		
		try {
			resp = restClient.postForObject(env.getUserUrl() + "deleteUserMaster",roleList,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : deleteUserMaster starts");
		return resp;
	}
}
