package nirmalya.aathithya.webmodule.user.controller;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.user.model.RolesAccessModel;
import nirmalya.aathithya.webmodule.user.model.UserRoleAssignModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "user")
public class UserRoleAssignController {

	Logger logger = LoggerFactory.getLogger(UserRoleAssignController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "user-role-assign" })
	public String manageRole(Model model, HttpSession session) {
		logger.info("Method : roleassign starts");

		logger.info("Method : roleassign starts");
		return "user/userRoleAssign";
	}
	
	// view

	@SuppressWarnings("unchecked")

	@GetMapping("user-role-assign-view")
	public @ResponseBody List<UserRoleAssignModel> viewUserMaster(HttpSession session) {
		logger.info("Method : viewUserMaster starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<UserRoleAssignModel>> resp = new JsonResponse<List<UserRoleAssignModel>>();
		List<UserRoleAssignModel> returnList = new ArrayList<UserRoleAssignModel>();
		logger.info("Method : viewUserMaster starts"+organization+orgDivision);
		try {
			resp = restClient.getForObject(env.getUserUrl() + "viewUserMaster?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : viewUserMaster ends");
		return returnList;
	}
	

	
// Role assign 
	
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "user-role-assign-roles-listing" })
	public @ResponseBody List<RolesAccessModel> getUserRolesList(Model model, HttpSession session) {
		logger.info("Method : getUserRolesList starts");

		JsonResponse<List<RolesAccessModel>> res = new JsonResponse<List<RolesAccessModel>>();
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
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
	
	//edit

	@SuppressWarnings("unchecked")
	@PostMapping("/user-role-assign-edit-data")
	public @ResponseBody JsonResponse<UserRoleAssignModel> editUserMaster(Model model, @RequestBody String data,
			HttpSession session, BindingResult result) {
		logger.info("Method : editUserMaster starts");

		JsonResponse<UserRoleAssignModel> resp = new JsonResponse<UserRoleAssignModel>();

		try {
			resp = restClient.getForObject(env.getUserUrl() + "editUserAssignMaster?id=" + data, JsonResponse.class);
			
			ObjectMapper mapper = new ObjectMapper();
			
			UserRoleAssignModel userData = mapper.convertValue(resp.getBody(),
					new TypeReference<UserRoleAssignModel>() {
					});

			if(userData.getRole()!=null && userData.getRole()!="") {
				String[] arr = userData.getRole().split(",");
				
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
	
	/*
	 *
	 * add 
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "user-role-assign-save-data" })
	public @ResponseBody JsonResponse<Object> addRoleassignMaster(Model model, HttpSession session,
			@RequestBody UserRoleAssignModel data) {
		logger.info("Method : addRoleassignMaster starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
	
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);

		logger.info("Method : addRoleassignMaster starts"+data);
		
		try {
			res = restClient.postForObject(env.getUserUrl() + "addRoleassignMaster", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage()== null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
		}

		logger.info("Method : addRoleassignMaster ends");
		
		logger.info("ADDDDD" + res);
		return res;

	}

	
}