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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.user.model.UserRoleAssignModel;
/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = "user")
public class EmployeePasswordResetController {

	Logger logger = LoggerFactory.getLogger(EmployeePasswordResetController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "employee-password-reset" })
	public String manageEmployeePassword(Model model, HttpSession session) {
		logger.info("Method : manageEmployeePassword starts");

		logger.info("Method : manageEmployeePassword starts");
		return "user/employeePasswordReset";
	}
	// view

	@SuppressWarnings("unchecked")
	@GetMapping("employee-password-reset-viewEmployee")
	public @ResponseBody List<UserRoleAssignModel> viewEmployeeMasterDetails(HttpSession session) {
		logger.info("Method : viewEmployeeMasterDetails starts");

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
			resp = restClient.getForObject(env.getUserUrl() + "viewEmployeeMasterView?org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : viewEmployeeMasterDetails ends");
		logger.info("VIEWWWW"+returnList);
		
		return returnList;
	}
	/**
	 * Function for save new password
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/employee-password-reset-details")
	public @ResponseBody JsonResponse<Object> saveNewPassword(HttpSession session,
			@RequestBody DropDownModel dropDownModel) {
		logger.info("Method : saveResetPassword starts");

		JsonResponse<Object> resp = new JsonResponse<Object>(); 
  
		try {
				resp = restClient.postForObject(env.getUserUrl() + "reset-employee-password", dropDownModel,
						JsonResponse.class);
				ObjectMapper mapper = new ObjectMapper();

				DropDownModel seat = mapper.convertValue(resp.getBody(), new TypeReference<DropDownModel>() {
				});
				resp.setBody(seat);
		 
		} catch (Exception e) {

			e.printStackTrace();
		}

		logger.info("Method : saveResetPassword ends");
		logger.info("resp===" + resp);
		return resp;
	}
	// clear password
	@SuppressWarnings("unchecked")
	@GetMapping("employee-password-reset-clearpassword")
	public @ResponseBody JsonResponse<Object> clearPassword(@RequestParam String id, Model model,HttpSession session) {
		logger.info("Method : clearpassword function starts");
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getUserUrl() + "rest-clearPassword?id=" + id+"&org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : clearPassword function Ends");

		logger.info("RESPPPPPPP" + res);
		return res;
	}
	// clear IMEI
	@SuppressWarnings("unchecked")
	@GetMapping("employee-password-reset-clearimei")
	public @ResponseBody JsonResponse<Object> clearIMEI(@RequestParam String id, Model model,HttpSession session) {
		logger.info("Method : clearIMEI function starts");
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getUserUrl() + "rest-clearIMEI?id=" + id+"&org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : clearIMEI function Ends");

		logger.info("RESPPPPPPP" + res);
		return res;
	}
}
