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
import nirmalya.aathithya.webmodule.master.model.EmployeeGroupSchedulingModel;

@Controller
@RequestMapping(value = "master/")
public class EmployeeGroupSchedulingController {

	Logger logger = LoggerFactory.getLogger(EmployeeGroupSchedulingController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	public int countDaysInMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		// Note that month is 0-based in calendar, bizarrely.
		calendar.set(year, month - 1, 1);
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		return daysInMonth;
	}

	// Summary
	@GetMapping("employee-group-scheduling")
	public String employeeGroup(Model model, HttpSession session) {

		logger.info("Method : employeeGroup starts");

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
		logger.info("Method : employeeGroup ends");

		return "master/employeeGroupScheduling";
	}
	// View

		@SuppressWarnings("unchecked")
		@GetMapping("employee-group-scheduling-view")
		public @ResponseBody Object viewGroupSchedulingData(@RequestParam String sec, HttpSession session) {

			logger.info("Method :viewGroupSchedulingData starts");
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

				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewGroupSchedulingData?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&sec=" + sec, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :viewGroupSchedulingData ends");

			return resp;
		}

		// View All

		@SuppressWarnings("unchecked")
		@GetMapping("employee-group-scheduling-view-All")
		public @ResponseBody Object viewGroupSchedulingAllData(@RequestParam String sec, HttpSession session) {

			logger.info("Method :viewGroupSchedulingAllData starts");
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

				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewGroupSchedulingAllData?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&sec=" + sec, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :viewGroupSchedulingAllData ends");

			return resp;
		}
		// View All Employee 
		
		@SuppressWarnings("unchecked")
		@GetMapping("employee-group-scheduling-empdtls")
		public @ResponseBody Object viewAllEmploee(HttpSession session) {
			
			logger.info("Method :viewAllEmploee starts");
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
				
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-all-employee-dtls-group?orgName=" + orgName
						+ "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :viewAllEmploee ends");
			
			return resp;
		}

		// Revised Group

		@SuppressWarnings("unchecked")
		@PostMapping(value = { "employee-group-scheduling-add-revised-group" })
		public @ResponseBody JsonResponse<Object> addRevisedGroup(HttpSession session,
				@RequestBody List<EmployeeGroupSchedulingModel> data) {
			logger.info("Method : addRevisedGroup starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			
			String orgName = "";
			String orgDivision = "";
			String userId = "";
			
			// String Date = DateFormatter.getStringDate(date);
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				userId = (String) session.getAttribute("USER_ID");
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			for(EmployeeGroupSchedulingModel a : data) {
				a.setOrganization(orgName);
				a.setOrgDivision(orgDivision);
				a.setCreatedBy(userId);
			}
			System.err.println("data===="+data);
			
			try {
				res = restTemplate.postForObject(env.getMasterUrl() + "rest-addRevisedGroup", data, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method :addRevisedGroup ends");
			return res;

		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("employee-group-scheduling-approve")
		public @ResponseBody JsonResponse<Object> approveGroup(HttpSession session, @RequestParam String slNo, String flag) {

			logger.info("Method : approveGroup starts");
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
				response = restTemplate.getForObject(env.getMasterUrl() + "rest-approve-group?slNo=" + slNo + "&flag="+flag+ "&orgName="+orgName+
						"&orgDivision="+orgDivision+"&userId="+userId,JsonResponse.class);

			} catch (RestClientException e) {
				e.printStackTrace();
			}


			logger.info("Method : approveGroup ends");
			return response;
		}

}
