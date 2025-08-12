package nirmalya.aathithya.webmodule.employee.controller;

import java.util.Arrays;
import java.util.List;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.PayslipModel;

	@Controller
	@RequestMapping(value = { "employee/" })
	public class ViewSelfPaySlipController {

		Logger logger = LoggerFactory.getLogger(ViewSelfPaySlipController.class);

		@Autowired
		RestTemplate restClient;

		@Autowired
		EnvironmentVaribles env;

		@Autowired
		RestTemplate restTemplate;
		
		@GetMapping("/view-payslip-self")
		public String getSelfPaySlipDetails(Model model, HttpSession session) {
			logger.info("Method : getSelfPaySlipDetails starts");

			// drop down for month list
			try {
				DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
						DropDownModel[].class);
				List<DropDownModel> monthLists = Arrays.asList(month);
				model.addAttribute("monthLists", monthLists);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
			String userId = "";
			String userName = "";
			String userRole = "";
			String organization = "";
			String orgDivision = "";
			String isHr = "";

			try {
				userId = (String) session.getAttribute("USER_ID");
				userName = (String) session.getAttribute("USER_NAME");
				userRole = (String) session.getAttribute("USER_ROLES_STRING");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			
			// drop down for employee list
			try {
				DropDownModel[] emp = restTemplate
						.getForObject(
								env.getMasterUrl() + "getEmployeeListsSlip?userId=" + userId + "&isHr=" + isHr
										+ "&organization=" + organization + "&orgDivision=" + orgDivision,
								DropDownModel[].class);
				List<DropDownModel> employeeLists = Arrays.asList(emp);
				model.addAttribute("employeeLists", employeeLists);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
			try {
				DropDownModel[] emp = restTemplate
						.getForObject(
								env.getMasterUrl() + "getYearList-attendance?organization=" + organization + "&orgDivision=" + orgDivision,
								DropDownModel[].class);
				List<DropDownModel> employeeLists = Arrays.asList(emp);
				model.addAttribute("yearList", employeeLists);

			} catch (RestClientException e) {
				e.printStackTrace();
			}
			try {
				DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
						+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
						DropDownModel[].class);
				model.addAttribute("startDayForAtten", startDay[0].getKey());
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {

				DropDownModel[] manager = restTemplate.getForObject(
						env.getEmployeeUrl() + "EmployeeList?organization=" + organization + "&orgDivision=" + orgDivision,
						DropDownModel[].class);
				List<DropDownModel> managerList = Arrays.asList(manager);
				model.addAttribute("EmployeeList", managerList);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			model.addAttribute("userId", userId);
			model.addAttribute("userName", userName);
			model.addAttribute("userRole", userRole);
			model.addAttribute("organization", organization);
			model.addAttribute("orgDivision", orgDivision);
		
			logger.info("Method : getSelfPaySlipDetails ends");
			return "employee/view-payslip-self";
		}
		
		
		/*
		 * view-payslip-list
		 */
		@SuppressWarnings("unchecked")
		@GetMapping("view-payslip-self-byId")
		public @ResponseBody List<PayslipModel> viewPayslipList(@RequestParam String empId,@RequestParam String fromDate,@RequestParam String toDate,HttpSession session,
				Model model) {

			logger.info("Method : viewPayslipList starts");

			JsonResponse<List<PayslipModel>> resp = new JsonResponse<List<PayslipModel>>();
			String organization = "";
			String orgDivision = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "view-employe-paySlip-api-self?userId=" + empId+"&fromDate=" + fromDate
						+ "&toDate=" + toDate
						+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : viewPayslipList ends");
			return resp.getBody();
		}

}
