package nirmalya.aathithya.webmodule.hotel.controller;

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

@Controller
@RequestMapping(value = "hotel")
public class AdminController {
	Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/admin")
	public String adminDashboard(Model model, HttpSession session) {
		logger.info("Method: admin-dashboard starts here");

		logger.info("Method: admin-dashboard ends here");

		return "hotel/admin-screens";
	}
	
	
	@GetMapping("/role-setting")
	public String superAdminDashboard(Model model, HttpSession session) {
		logger.info("Method: super-admin-dashboard starts here");

		logger.info("Method: super-admin-dashboard ends here");

		return "hotel/role-dashboard";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("get-employees")
	public @ResponseBody List<UserRoleAssignModel> viewUserMaster(HttpSession session) {
		logger.info("Method : get-employees starts");
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
		try {
			resp = restTemplate.getForObject(env.getUserUrl() + "viewUserMaster?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : get-employees ends");
		return returnList;
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "get-roles-listing" })
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
			res = restTemplate.getForObject(env.getUserUrl() + "getRolesList?org="+organization+"&orgDiv="+orgDivision, JsonResponse.class);

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
	@PostMapping("get-role-wise-mod-func-act")
	public @ResponseBody JsonResponse<Object> getModFuncActByRoleId(Model model, @RequestBody String data,
			HttpSession session, BindingResult result) {
		logger.info("Method : getModFuncActByRoleId starts");
 
		JsonResponse<Object> resp = new JsonResponse<Object>();
 
		try {
			resp = restTemplate.getForObject(env.getUserUrl() + "getModFuncActByRoleId?id=" + data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
 
		logger.info("Method : getModFuncActByRoleId starts");
		return resp;
	}
}
