package nirmalya.aathithya.webmodule.gatepass.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import nirmalya.aathithya.webmodule.gatepass.model.EmployeeAttendance;

@Controller
@RequestMapping(value = "gatepass/")
public class GatePassStaffRegisterController {

	Logger logger = LoggerFactory.getLogger(GatePassStaffRegisterController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/staff-register")
	public String gatePassStaffRegister(Model model, HttpSession session) {
		logger.info("Method : gatePassStaffRegister add starts");

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
			DropDownModel[] shift = restTemplate.getForObject(
					env.getMasterUrl() + "getShiftLists?org=" + org + "&orgDiv=" + orgDiv + "&userId=" + userId,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : gatePassStaffRegister ends");
		return "gatepass/staff-register";
	}

	// Staff Register View.
	@SuppressWarnings("unchecked")
	@GetMapping("staff-register-view")
	public @ResponseBody Object viewGatePassStaffRegister(@RequestParam String dept, String fromdate, String todate,
			HttpSession session) {

		logger.info("Method :viewGatePassStaffRegister starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String pageno = "0";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getGatepassUrl() + "rest-viewGatePassStaffRegister?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&dept=" + dept + "&fromdate=" + fromdate + "&todate=" + todate
					+ "&pageno=" + pageno, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewGatePassStaffRegister ends");
		return resp;
	}

	// Staff Register View.
	@SuppressWarnings("unchecked")
	@PostMapping("staff-register-update-attendance")
	public @ResponseBody Object updateAttendance(@RequestBody EmployeeAttendance employeeData, HttpSession session, HttpServletResponse response) throws IOException {

		logger.info("Method :updateAttendance starts");
//		logger.info("employeeData {}", employeeData.toString());
		JsonResponse<Object> resp = new JsonResponse<>();
		String orgName = "";
		String orgDivision = "";
		String userId = null;
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
//		logger.info("userId {}", userId);

		
		if(orgName !="null" && orgName != null) {
		try {
			resp = restClient.postForObject(env.getGatepassUrl() + "rest-updateAttendance?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, employeeData, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else {
			resp.setCode("sessionOut");
			resp.setMessage("Your session may have expired. Please log out and log back in to continue.");
		}
		logger.info("Method :updateAttendance ends");
		return resp;
	}
}
