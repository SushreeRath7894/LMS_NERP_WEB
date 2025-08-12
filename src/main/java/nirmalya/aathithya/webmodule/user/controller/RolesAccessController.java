package nirmalya.aathithya.webmodule.user.controller;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.user.model.RolesAccessModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "user")
public class RolesAccessController {

	Logger logger = LoggerFactory.getLogger(RolesAccessController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "manage-roles" })
	public String manageRoles(Model model, HttpSession session) {
		logger.info("Method : manageRoles starts");

		logger.info("Method : manageRoles starts");
		return "user/manageRoles";
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "manage-roles-get-listing" })
	public @ResponseBody List<RolesAccessModel> getRolesList(Model model,  HttpSession session) {
		logger.info("Method : getRolesList starts");
		String organization=""; 
		String orgDivision="";

		try {
		
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<RolesAccessModel>> res = new JsonResponse<List<RolesAccessModel>>();

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

		logger.info("Method : getRolesList ends");
		return res.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-roles-save-data")
	public @ResponseBody JsonResponse<Object> saveRoleMaster(@RequestBody RolesAccessModel data, HttpSession session) {
		logger.info("Method : saveRoleMaster starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
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


		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCreatedBy(userId);

		if (data.getStatus() != null) {
			if (data.getStatus().contentEquals("1")) {
				data.setRoleStatus(true);
			} else {
				data.setRoleStatus(false);
			}
		} else {
			data.setRoleStatus(false);
		}

		try {
			resp = restClient.postForObject(env.getUserUrl() + "saveRoleMaster", data, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveRoleMaster starts");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/manage-roles-edit-data")
	public @ResponseBody JsonResponse<RolesAccessModel> editRoleMaster(Model model, @RequestBody String data,
			HttpSession session, BindingResult result) {
		logger.info("Method : editRoleMaster starts");

		JsonResponse<RolesAccessModel> resp = new JsonResponse<RolesAccessModel>();

		try {
			resp = restClient.getForObject(env.getUserUrl() + "editRoleMaster?id=" + data, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : editRoleMaster starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-roles-delete")
	public @ResponseBody JsonResponse<Object> deleteRoleMaster(@RequestBody List<DropDownModel> roleList, HttpSession session) {
		logger.info("Method : deleteRoleMaster starts");
		
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
			resp = restClient.postForObject(env.getUserUrl() + "deleteRoleMaster",roleList,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : deleteRoleMaster starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/manage-roles/get-role-wise-mod-func-act")
	public @ResponseBody JsonResponse<Object> getModFuncActByRoleId(Model model, @RequestBody String data,
			HttpSession session, BindingResult result) {
		logger.info("Method : getModFuncActByRoleId starts");
 
		JsonResponse<Object> resp = new JsonResponse<Object>();
 
		try {
			resp = restClient.getForObject(env.getUserUrl() + "getModFuncActByRoleId?id=" + data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
 
		logger.info("Method : getModFuncActByRoleId starts");
		return resp;
	}
}
