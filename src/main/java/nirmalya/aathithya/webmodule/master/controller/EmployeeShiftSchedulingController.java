package nirmalya.aathithya.webmodule.master.controller;

import java.util.Arrays;
import java.util.Calendar;
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
public class EmployeeShiftSchedulingController {

	Logger logger = LoggerFactory.getLogger(EmployeeShiftSchedulingController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	public int countDaysInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return daysInMonth;
	}

	// Summary employee-shift-scheduling
	@GetMapping("employee-shift-scheduling")
	public String employeeweekoff(Model model, HttpSession session) {
		logger.info("Method : employeeWeekoff starts");
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
		logger.info("Method : employeeWeekoff ends");
		return "master/employeeShiftScheduling";
	}

	// View by shift
	@SuppressWarnings("unchecked")
	@GetMapping("employee-shift-scheduling-view")
	public @ResponseBody Object viewShiftSchedulingData(@RequestParam String sec, HttpSession session) {

		logger.info("Method :viewShiftSchedulingData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		// String Date = DateFormatter.getStringDate(date);
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

		logger.info("Method :viewShiftSchedulingData ends");

		return resp;
	}

	// View diffGroups
	@SuppressWarnings("unchecked")
	@GetMapping("employee-shift-scheduling-view-diffGroups")
	public @ResponseBody Object viewShiftDiffGroupsData(@RequestParam String sec, String fromDate,
			String toDate, HttpSession session) {
		logger.info("Method :viewShiftDiffGroupsData starts");
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
		logger.info("Method :viewShiftDiffGroupsData ends");
		return resp;
	}

//ADD  Revised Shift
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "employee-shift-assign-add-revised-shift" })
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
	@GetMapping("employee-shift-assign-approve")
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


	@GetMapping("employee-shift-assign")
	public String employeeShiftAssign(Model model, HttpSession session) {
		logger.info("Method : employeeShiftAssign starts");
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
		logger.info("Method : employeeShiftAssign ends");
		return "master/employeeShiftAssign";
	}


	// View All Employee 
	@SuppressWarnings("unchecked")
	@GetMapping("employee-shift-assign-allEmployees")
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
	// View All Employee 
	@SuppressWarnings("unchecked")
	@GetMapping("employee-shift-assign-emp-dropDown")
	public @ResponseBody Object viewEmployeeDropDown(HttpSession session) {
		logger.info("Method :viewEmployeeDropDown starts");
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
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewEmployeeDropDown?orgName=" + orgName
				+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewEmployeeDropDown ends");
		return resp;
	}
// View by shift
	@SuppressWarnings("unchecked")
	@GetMapping("employee-shift-assign-view")
	public @ResponseBody Object viewAllShiftData(@RequestParam String sec, HttpSession session) {
		logger.info("Method :viewAllShiftData starts");
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
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewAllShiftData?orgName=" + orgName
				+ "&orgDivision=" + orgDivision + "&sec=" + sec, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewAllShiftData ends");
		return resp;
	}

	//  approve Wrong Shift

		@SuppressWarnings("unchecked")
		@GetMapping("employee-shift-scheduling-approve")
		public @ResponseBody JsonResponse<Object> approveWrongShift(HttpSession session, @RequestParam String slNo, String fromDate, String toDate) {

			logger.info("Method : approveWrongShift starts");
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
				response = restTemplate.getForObject(env.getMasterUrl() + "rest-approveWrongShift?slNo=" + slNo + "&fromDate=" + fromDate 
						+ "&toDate=" + toDate + "&orgName="+orgName+
						"&orgDivision="+orgDivision+"&userId="+userId,JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : approveWrongShift ends");
			return response;
		}
}
