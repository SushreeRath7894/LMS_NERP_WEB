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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.ExtendExitManagementModel;
import nirmalya.aathithya.webmodule.master.model.EmployeeReportsModel;

@Controller
@RequestMapping(value = "master")
public class EmployeeReportController {
	Logger logger = LoggerFactory.getLogger(EmployeeReportController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	// Summary
	@GetMapping("/view-employee-report")
	public String employeeReport(Model model, HttpSession session) {

		logger.info("Method : employeeReport starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] departmentType = restTemplate.getForObject(env.getMasterUrl()
					+ "getDepartmentTypeForShiftType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> department = Arrays.asList(departmentType);
			model.addAttribute("department", department);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : employeeReport ends");

		return "master/view-employee-report";
	}
	
  // view employee details
 
	@SuppressWarnings("unchecked")
	@GetMapping("/view-employee-report-empDetails")
	public @ResponseBody JsonResponse<Object> viewEmployeeReports(HttpSession session,@RequestParam String fromDate,String toDate) {

		logger.info("Method : viewEmployeeReports");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewEmployeeReport?userId="+userId+"&organization="+organization+"&orgDivision="+orgDivision
					+"&fromDate="+fromDate+"&toDate="+toDate, JsonResponse.class);
		}catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewEmployeeReports ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp); 
		return resp;
	}
	
	//View Attendance Report
	@SuppressWarnings("unchecked")
	@GetMapping("/view-employee-report-empAttendanceDetails")
	public @ResponseBody Object viewEmployeeAttendanceReports(HttpSession session,@RequestParam String fromDate,String toDate) {
		
		logger.info("Method : viewEmployeeAttendanceReports=="+fromDate+" @@@ "+toDate);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		logger.info("userId=="+userId);
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewEmployeeAttendanceReports?userId="+userId+"&organization="+organization+"&orgDivision="+orgDivision
					+ "&fromDate="+fromDate+"&toDate="+toDate, JsonResponse.class);
		}catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : viewEmployeeAttendanceReports ends");
		logger.info("respww atten view" + resp); 
		return resp;
	}
	
	//View Resignation Report
			@SuppressWarnings("unchecked")
			@GetMapping("view-employee-report-resgin")
			public @ResponseBody Object viewEmployeeResignationReports(HttpSession session,@RequestParam String fromDate,String toDate) {
				
				logger.info("Method : viewEmployeeResignationReports starts");
				JsonResponse<Object> resp = new JsonResponse<Object>();
				String roleid="report"; 
				String userid=""; 
				String organization=""; 
				String orgDivision="";
				try {
					userid = (String) session.getAttribute("USER_ID");
					organization = (String) session.getAttribute("ORGANIZATION"); 
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("userId=="+userid);
				try {
					resp = restTemplate.getForObject(env.getMasterUrl() + "viewExitdetails?userId=" + userid +"&userRole="+ roleid+"&organization="+organization+"&orgDivision="+orgDivision
							+ "&fromDate="+fromDate+"&toDate="+toDate, JsonResponse.class);
				}catch (RestClientException e) {
					e.printStackTrace();
				}
				
				logger.info("Method : viewEmployeeResignationReports ends");
				logger.info("respww viewEmployeeResignationReports view" + resp); 
				return resp;
			}
			

	/*
	 * get shiftSubDepartment
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-employee-report-getSubDepartmentByDept" })
	public @ResponseBody JsonResponse<Object> getSubDepartmentByDept(HttpSession session,@RequestParam String shiftDeptId) {
		logger.info("Method : getSubDepartmentByDept starts");
		String orgName = "";
		String orgDivision = "";

		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-shiftSubDepartment?shiftDeptId=" + shiftDeptId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getSubDepartmentByDept ends");
		
		logger.info("LISTTTT" + res);
		return res;
	}
	
	//View Attendance Report By Department
	@SuppressWarnings("unchecked")
	@GetMapping("/view-employee-report-empAttendanceDetailsDepartmentWise")
	public @ResponseBody Object empAttendanceDetailsDepartmentWise(HttpSession session, @RequestParam String dept,
			String subDept,String fromDate,String toDate) {
		
		logger.info("Method : empAttendanceDetailsDepartmentWise");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-empAttendanceDetailsDepartmentWise?dept="+dept+"&subDept="+subDept+"&fromDate="+fromDate
					+"&toDate="+toDate+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		}catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : empAttendanceDetailsDepartmentWise ends");
		return resp;
	}
	
	
	//View Attendance Report Status Wise
	@SuppressWarnings("unchecked")
	@GetMapping("/view-employee-report-empAttendanceDetailsStatusWise")
	public @ResponseBody Object empAttendanceDetailsStatusWise(HttpSession session, @RequestParam String dept,
			String subDept,String attndate,String status) {
		
		logger.info("Method : empAttendanceDetailsStatusWise");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-empAttendanceDetailsStatusWise?dept="+dept+"&subDept="+subDept
					+"&attndate="+attndate+"&organization="+organization+"&orgDivision="+orgDivision+"&status="+status, JsonResponse.class);
		}catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method : empAttendanceDetailsStatusWise ends");
		logger.info("respww atten view" + resp); 
		return resp;
	}

	//View Leave Report
		@SuppressWarnings("unchecked")
		@GetMapping("view-employee-report-leave")
		public @ResponseBody Object viewEmployeeLeaveReports(HttpSession session,@RequestParam String fromDate,String toDate) {
			
			logger.info("Method : viewEmployeeLeaveReports=="+fromDate+" @@@ "+toDate);
			
			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			logger.info("userId=="+userId);
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewEmployeeLeaveReports?userId="+userId+"&organization="+organization+"&orgDivision="+orgDivision
						+ "&fromDate="+fromDate+"&toDate="+toDate, JsonResponse.class);
			}catch (RestClientException e) {
				e.printStackTrace();
			}
			
			logger.info("Method : viewEmployeeLeaveReports ends");
			logger.info("respww atten view" + resp); 
			return resp;
		}

	//View Leave Report By Department
		@SuppressWarnings("unchecked")
		@GetMapping("/view-employee-report-leaveDepartmentWise")
		public @ResponseBody Object empLeaveDepartmentWise(HttpSession session, @RequestParam String dept,
				String subDept,String fromDate,String toDate) {
			
			logger.info("Method : empLeaveDepartmentWise");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			
			String organization = "";
			String orgDivision = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-empLeaveDepartmentWise?dept="+dept+"&subDept="+subDept+"&fromDate="+fromDate
						+"&toDate="+toDate+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
			}catch (RestClientException e) {
				e.printStackTrace();
			}
			
			logger.info("Method : empLeaveDepartmentWise ends");
			return resp;
		}
		//View Leave Report By Department
	@SuppressWarnings("unchecked")
	@GetMapping("/view-employee-report-resginDepartmentWise")
	public @ResponseBody Object resginDepartmentWise(HttpSession session, @RequestParam String dept,
		String subDept,String fromDate,String toDate) {
			
		logger.info("Method : empLeaveDepartmentWise");
		JsonResponse<Object> resp = new JsonResponse<Object>();
			
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-resginDepartmentWise?dept="+dept+"&subDept="+subDept+"&fromDate="+fromDate
					+"&toDate="+toDate+"&organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		}catch (RestClientException e) {
			e.printStackTrace();
		}
					
		logger.info("Method : resginDepartmentWise ends");
		return resp;
	}
}
