package nirmalya.aathithya.webmodule.user.controller;

import java.util.ArrayList;
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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.user.model.UserRoleAssignModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "user")
public class EmployeeStatusWebController {

	Logger logger = LoggerFactory.getLogger(EmployeeStatusWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "employee-status-update" })
	public String manageStatus(Model model, HttpSession session) {
		logger.info("Method : roleassign starts");

		logger.info("Method : roleassign starts");
		return "user/employeeStatus";
	}

	// view

	@SuppressWarnings("unchecked")

	@GetMapping("employee-status-update-view")
	public @ResponseBody List<UserRoleAssignModel> viewEmployeeMaster(HttpSession session) {
		logger.info("Method : viewEmployeeMaster starts");

		JsonResponse<List<UserRoleAssignModel>> resp = new JsonResponse<List<UserRoleAssignModel>>();
		List<UserRoleAssignModel> returnList = new ArrayList<UserRoleAssignModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restClient.getForObject(env.getUserUrl() + "viewEmployeeMasterView?org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<UserRoleAssignModel> empdata = mapper.convertValue(resp.getBody(),
				new TypeReference<List<UserRoleAssignModel>>() {
				});
 
		for (UserRoleAssignModel a : empdata) {
			if(a.getEmpStatus().equals(true)) {
				 a.setStatus("Active");
			}else {
				 a.setStatus("Inactive");
			}
		}
		resp.setBody(empdata);
		returnList = resp.getBody();
		logger.info("Method : viewEmployeeMaster ends");
		logger.info("VIEWWWW" + returnList);

		return returnList;
	}

	/*
	 *
	 * add
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "employee-status-update-save" })
	public @ResponseBody JsonResponse<Object> addEmployeeMaster(Model model, HttpSession session,
			@RequestBody UserRoleAssignModel data) {
		logger.info("Method : addEmployeeMaster starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.postForObject(env.getUserUrl() + "addEmployeeMasteradd", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Success");
		} else {
			res.setMessage("UnSuccess");
		}

		logger.info("Method : addEmployeeMaster ends");

		logger.info("ADDDDD" + res);
		return res;

	}
}