package nirmalya.aathithya.webmodule.master.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.EmployeeShiftSchedulingModel;

@Controller
@RequestMapping(value = "master/")
public class ShiftManagementController {
	Logger logger = LoggerFactory.getLogger(EmployeeShiftSchedulingController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping("shift-management")
	public String shiftManagement(Model model, HttpSession session) {
		logger.info("Method : shiftManagement starts");
		String userId = "";
		String org = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] shift = restTemplate.getForObject(env.getMasterUrl() + "getShiftLists?org=" + org + "&orgDiv=" 
					+ orgDiv + "&userId=" + userId,DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);
			model.addAttribute("shiftLists", shiftLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : shiftManagement ends");
		return "his_master/shift-management";
	}
// View shift
	@SuppressWarnings("unchecked")
	@GetMapping("shift-management-view-shift")
	public @ResponseBody Object viewShift( HttpSession session) {
		logger.info("Method :viewShift starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewShift?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewShift ends");
		return resp;
	}
// View employee By Shift
		@SuppressWarnings("unchecked")
	@GetMapping("shift-management-employee-view")

	public @ResponseBody Object viewEmployeeByShift(@RequestParam String sec, HttpSession session) {

		logger.info("Method :viewEmployeeByShift starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewShiftSchedulingData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&sec=" + sec, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewEmployeeByShift ends");
		return resp;
	}
// View wrong employee By Shift
	@SuppressWarnings("unchecked")
	@GetMapping("shift-management-view-wrong-emp")
	public @ResponseBody Object viewWrongEmployeeByShift(@RequestParam String sec, String fromDate,
			String toDate, HttpSession session) {
		logger.info("Method :viewWrongEmployeeByShift starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewShiftDiffGroupsData?orgName=" + orgName
				+ "&orgDivision=" + orgDivision + "&sec=" + sec + "&fromDate=" + fromDate 
				+ "&toDate=" + toDate , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewWrongEmployeeByShift ends");
		return resp;
	}
// View All Employee 
	@SuppressWarnings("unchecked")
	@GetMapping("shift-management-view-allEmployees")
	public @ResponseBody Object viewAllEmployee(HttpSession session) {
		logger.info("Method :viewAllEmploee starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-all-employee-dtls?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewAllEmployee ends");
		return resp;
	}
//ADD  Revised Shift
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "shift-management-add-shift" })
	public @ResponseBody JsonResponse<Object> addRevisedShift(HttpSession session,
			@RequestBody List<EmployeeShiftSchedulingModel> data) {
		logger.info("Method : addRevisedShift starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(EmployeeShiftSchedulingModel a : data) {
			a.setOrganization(orgName);
			a.setOrgDivision(orgDivision);
			a.setCreatedBy(userId);
		}
		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "rest-addRevisedShift", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :addRevisedShift ends");
		return res;
	}
//approveShift	
	@SuppressWarnings("unchecked")
	@GetMapping("shift-management-assign-approve")
	public @ResponseBody JsonResponse<Object> approveShift(HttpSession session, @RequestParam String slNo, String flag) {
		logger.info("Method : approveShift starts");
		JsonResponse<Object> response = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getMasterUrl() + "rest-approve-shift?slNo=" + slNo + "&flag="+flag+ "&orgName="+orgName+
					"&orgDivision="+orgDivision+"&userId="+userId,JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : approveShift ends");
		return response;
	}

}
