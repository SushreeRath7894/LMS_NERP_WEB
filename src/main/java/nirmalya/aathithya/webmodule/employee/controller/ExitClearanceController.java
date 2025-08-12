package nirmalya.aathithya.webmodule.employee.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.ExitFinancialSettelmentModel;


@Controller
@RequestMapping(value = "employee")
public class ExitClearanceController {
	Logger logger = LoggerFactory.getLogger(ExitClearanceController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("/exit-clearance")
	public String exitClearance(Model model, HttpSession session) {

		logger.info("Method : exitClearance starts");

		String userRole="";

		try {
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("userRole", userRole);
		logger.info("Method : exitClearance ends");

		return "employee/exit-clearance";
	}
	
	
	
	@SuppressWarnings("unchecked")

	@GetMapping("exit-clearance-view")
	public @ResponseBody JsonResponse<Object> viewExitClearanceEmployee(HttpSession session) {

		logger.info("Method : viewExitClearanceEmployee starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization=""; 
		String orgDivision="";
		String userid="";

		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userid = (String) session.getAttribute("USER_ID");
			
			System.out.println("userid>>"+userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "view-exit-clearance-details?userId=" + userid +"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewExitClearanceEmployee ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("exit-clearance-update")
	public @ResponseBody JsonResponse<Object> clearanceUpdate(HttpSession session,@RequestParam String id,String recoveryAmount,String remarks) {

		logger.info("Method : viewExitClearanceEmployee starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		ExitFinancialSettelmentModel modelData = new ExitFinancialSettelmentModel();
		
		String organization=""; 
		String orgDivision="";
		String userid="";

		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userid = (String) session.getAttribute("USER_ID");
			
			System.out.println("userid>>"+userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		modelData.setCreatedBy(userid);
		modelData.setOrganization(organization);
		modelData.setOrgDivision(orgDivision);
		modelData.setClearanceId(id);
		modelData.setRecovery(recoveryAmount);
		modelData.setComment(remarks);
		
		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "update-exit-clearance", modelData, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewExitClearanceEmployee ends");
		return resp;
	}
	
	
}
