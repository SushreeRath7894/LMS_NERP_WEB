package nirmalya.aathithya.webmodule.user.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.user.model.AvailableFunctionModel;
import nirmalya.aathithya.webmodule.user.model.HeaderAccessModel;
import nirmalya.aathithya.webmodule.user.model.RolesAccessModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "user")
public class PermissionsAccessController {

	Logger logger = LoggerFactory.getLogger(PermissionsAccessController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "manage-permissions" })
	public String managePermissions(Model model, HttpSession session) {
		logger.info("Method : managePermissions starts");
		
		try {
			DropDownModel[] plevel = restClient.getForObject(env.getUserUrl()+ "getPermissionLevelList", DropDownModel[].class);
			List<DropDownModel> permissionLevelList = Arrays.asList(plevel);
			
			model.addAttribute("permissionLevelList", permissionLevelList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] data = restClient.getForObject(env.getUserUrl()+ "getDataFilterList", DropDownModel[].class);
			List<DropDownModel> dataFilterList = Arrays.asList(data);
			
			model.addAttribute("dataFilterList", dataFilterList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : managePermissions ends");
		return "user/managePermissions";
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "manage-permissions-list" })
	public @ResponseBody JsonResponse<List<HeaderAccessModel>> getPermissionsList(Model model, @RequestBody String id) {
		logger.info("Method : getPermissionsList starts");

		JsonResponse<List<HeaderAccessModel>> res = new JsonResponse<List<HeaderAccessModel>>();

		try {
			res = restClient.getForObject(env.getUserUrl() + "getPermissionsList", JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<HeaderAccessModel> dataList = mapper.convertValue(res.getBody(),
					new TypeReference<List<HeaderAccessModel>>() {
					});

			for (HeaderAccessModel m : dataList) {
				try {
					HeaderAccessModel[] child = restClient.getForObject(env.getUserUrl()+"getCompChildrenList?id="+m.getField(), HeaderAccessModel[].class);
					List<HeaderAccessModel> children = Arrays.asList(child);
					
					for(HeaderAccessModel a : children) {
						a.setColumnGroupShow("closed");
						String value = a.getField();
						a.setField(null);
						
						HeaderAccessModel[] child1 = restClient.getForObject(env.getUserUrl()+"getAvlFuncList?id="+value, HeaderAccessModel[].class);
						List<HeaderAccessModel> children1 = Arrays.asList(child1);
						
						a.setChildren(children1);
					}
					
					m.setChildren(children);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			res.setBody(dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("success");
		}

		logger.info("Method : getPermissionsList ends");
		return res;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "manage-permissions-edit" })
	public @ResponseBody JsonResponse<AvailableFunctionModel> editPermissionData(Model model, @RequestBody DropDownModel id, HttpSession session) {
		logger.info("Method : editPermissionData starts");
		
		JsonResponse<AvailableFunctionModel> res = new JsonResponse<AvailableFunctionModel>();
		
		try {
			res = restClient.getForObject(env.getUserUrl() + "editPermissionData?id="+id.getKey() + "&name=" + id.getName(), JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String message = res.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			res.setMessage("success");
		}
		
		logger.info("Method : editPermissionData ends");
		return res;
	}
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "manage-permissions-save-data" })
	public @ResponseBody JsonResponse<Object> assignRoleAvlFunction(Model model, @RequestBody AvailableFunctionModel id, HttpSession session) {
		logger.info("Method : editPermissionData starts");
		
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			id.setCreatedBy(userId);
			res = restClient.postForObject(env.getUserUrl() + "assignRoleAvlFunction",id, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String message = res.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			res.setMessage("Success");
		}
		
		logger.info("Method : assignRoleAvlFunction ends");
		return res;
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "manage-permissions-get-listing" })
	public @ResponseBody List<RolesAccessModel> getRolesList(Model model) {
		logger.info("Method : getRolesList starts");

		JsonResponse<List<RolesAccessModel>> res = new JsonResponse<List<RolesAccessModel>>();

		try {
			res = restClient.getForObject(env.getUserUrl() + "getRolesList", JsonResponse.class);

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
				
				try {
					AvailableFunctionModel[] avlFunc = restClient.getForObject(env.getUserUrl()+"getAvlFuncPermissionList?id="+m.getRoleId(), AvailableFunctionModel[].class);
					List<AvailableFunctionModel> avlFuncList = Arrays.asList(avlFunc);
					
					m.setAvlFuncList(avlFuncList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			res.setBody(dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getRolesList ends");
		return res.getBody();
	}
}
