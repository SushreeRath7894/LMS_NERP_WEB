package nirmalya.aathithya.webmodule.master.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberToWordsConverter;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.AdvanceManagementModel;
import nirmalya.aathithya.webmodule.master.model.AttendanceDateModel;
import nirmalya.aathithya.webmodule.master.model.EmployeeBonusModel;
import nirmalya.aathithya.webmodule.master.model.PayrollApprovalModel;
import nirmalya.aathithya.webmodule.master.model.PayrollModel;
import nirmalya.aathithya.webmodule.master.model.PayslipModel;
import nirmalya.aathithya.webmodule.master.model.SalaryRevisionModel;

@Controller
@RequestMapping(value = "master/")
public class PayrollReportController {

	Logger logger = LoggerFactory.getLogger(PayrollReportController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("/payroll-report")
	public String report(@RequestParam Optional<String> id,Model model, HttpSession session) {
		logger.info("Method : report starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
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
		
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getRecruitment() + "bandList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
			model.addAttribute("bandType", bandType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] dropDown = restTemplate.getForObject(env.getMasterUrl()
					+ "rest-getDesignationDropDown?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> Designation = Arrays.asList(dropDown);
			model.addAttribute("Designation", Designation);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl()
					+ "getEmployedByList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);
			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] type = restTemplate.getForObject(env.getMasterUrl()
					+ "getStaffTypeMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			
			model.addAttribute("typeList", typeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl()+ "getYearList-attendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] fYear = restTemplate.getForObject(env.getMasterUrl() + "getFinancialYearLists",
					DropDownModel[].class);
			List<DropDownModel> financialYear = Arrays.asList(fYear);
			model.addAttribute("financialYearList", financialYear);
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
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("hrRole", data);
			} else {
				model.addAttribute("empRole", data);
			}
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : report ends");
	
		return "master/payroll-reportNew";
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("payroll-report-emp-view")
	public @ResponseBody Object viewEmployeeList(HttpSession session) {
		logger.info("Method :viewEmployeeList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	System.out.println("orgDivision===="+orgDivision);
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getEmployeeList?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method :viewEmployeeList ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("report-view-details")
	public @ResponseBody Object reportApprove(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String staff, @RequestParam String employedBy, HttpSession session) {
		logger.info("Method :reportApprove starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewReport?fromDate=" + fromDate + "&toDate="
					+ toDate + "&staff=" + staff + "&employedBy=" + employedBy + "&userId=" + userId + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("reportApprove>>>>>----" + resp);
		logger.info("Method :reportApprove ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("report-pivot-details")
	public @ResponseBody Object reportPivot(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String staff, @RequestParam String employedBy, HttpSession session) {
		logger.info("Method :reportPivot starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "reportPivot?fromDate=" + fromDate + "&toDate="
					+ toDate + "&staff=" + staff + "&userId=" + userId + "&employedBy=" + employedBy + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :reportPivot ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("report-summary-details")
	public @ResponseBody Object reportSummary(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String staff, @RequestParam String employedBy, HttpSession session) {
		logger.info("Method :reportSummary starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "reportSummary?fromDate=" + fromDate + "&toDate="
					+ toDate + "&staff=" + staff + "&employedBy=" + employedBy + "&userId=" + userId + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :reportSummary ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("report-attendance-details")
	public @ResponseBody Object reportAttendance(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String staff, @RequestParam String employedBy, HttpSession session) {
		logger.info("Method :reportAttendance starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "reportAttendance?fromDate=" + fromDate + "&toDate="
					+ toDate + "&staff=" + staff + "&employedBy=" + employedBy + "&userId=" + userId + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :reportAttendance ends");
		return resp;
	}

// view Leave report 	
	@SuppressWarnings("unchecked")
	@GetMapping("report-leave-details")
	public @ResponseBody Object reportLeave(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String staff, @RequestParam String employedBy, HttpSession session) {
		logger.info("Method :reportLeave starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "reportLeave?fromDate=" + fromDate + "&toDate="
					+ toDate + "&staff=" + staff + "&employedBy=" + employedBy + "&userId=" + userId + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :reportLeave ends");
		return resp;
	}

// advance report	
	@GetMapping("view-advance-report")
	public String advanceReport(Model model, HttpSession session) {
		logger.info("Method : advanceReport starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(
					env.getMasterUrl() + "getYearLists?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getMasterUrl() + "getStaffType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> staffType = Arrays.asList(dropDownModel);
			model.addAttribute("staffType", staffType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl() + "getEmployedByList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);
			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
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
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;
			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
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
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : advanceReport ends");
		return "master/view-advance-report";
	}

//advance report view 	
	@SuppressWarnings("unchecked")
	@GetMapping("view-advance-report-view")
	public @ResponseBody Object reportAdvance(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String staff, @RequestParam String employedBy, HttpSession session) {
		logger.info("Method :reportAdvance starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "reportAdvance?fromDate=" + fromDate + "&toDate="
					+ toDate + "&staff=" + staff + "&employedBy=" + employedBy + "&userId=" + userId + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :reportAdvance ends");
		return resp;
	}

//advance report emi details view 	
	@SuppressWarnings("unchecked")
	@GetMapping("view-advance-report-view-emiAllDetails")
	public @ResponseBody Object emiAllDetails(HttpSession session, @RequestParam String empId) {
		logger.info("Method :emiAllDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "emiAllDetails?empId=" + empId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :emiAllDetails ends");
		return resp;
	}

//advance report add 
	@SuppressWarnings("unchecked")
	@PostMapping("view-advance-report-add")
	public @ResponseBody JsonResponse<Object> saveAdvanceEMI(@RequestBody List<AdvanceManagementModel> advanceModel,
			HttpSession session) {
		logger.info("Method : saveAdvanceEMI starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (AdvanceManagementModel m : advanceModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "saveAdvanceEMI", advanceModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : saveAdvanceEMI ends");
		return resp;
	}

// attendance report	
	@GetMapping("view-attendance-report")
	public String attendanceReport(Model model, HttpSession session) {
		logger.info("Method : attendanceReport starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(
					env.getMasterUrl() + "getYearLists?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getMasterUrl() + "getStaffType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> staffType = Arrays.asList(dropDownModel);
			model.addAttribute("staffType", staffType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl() + "getEmployedByList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);
			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
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
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;
			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
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
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);

			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : attendanceReport ends");
		return "master/view-attendance-report";
	}

// view attendance report			
	@SuppressWarnings("unchecked")
	@GetMapping("view-attendance-report-view")
	public @ResponseBody Object reportAttendanceView(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String staff, @RequestParam String employedBy, HttpSession session) {
		logger.info("Method :reportAttendanceView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "reportAttendanceView?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&staff=" + staff + "&employedBy=" + employedBy + "&userId=" + userId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :reportAttendanceView ends");
		return resp;
	}

// lic report	
	@GetMapping("view-lic-report")
	public String licReport(Model model, HttpSession session) {
		logger.info("Method : attendanceReport starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(
					env.getMasterUrl() + "getYearLists?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getMasterUrl() + "getStaffType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> staffType = Arrays.asList(dropDownModel);
			model.addAttribute("staffType", staffType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl() + "getEmployedByList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);
			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
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
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;
			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
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
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : attendanceReport ends");
		return "master/view-lic-report";
	}

	// view lic report
	@SuppressWarnings("unchecked")
	@GetMapping("view-lic-report-view")
	public @ResponseBody Object reportLicView(@RequestParam String fromDate, @RequestParam String toDate,
			@RequestParam String staff, @RequestParam String employedBy, HttpSession session) {
		logger.info("Method :reportLicView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "reportLicView?fromDate=" + fromDate + "&toDate="
					+ toDate + "&staff=" + staff + "&employedBy=" + employedBy + "&userId=" + userId + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :reportLicView ends");
		return resp;
	}

	// leave wages report
	@GetMapping("view-leave-wages-report")
	public String leaveWagesReport(Model model, HttpSession session) {
		logger.info("Method : leaveWagesReport starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(
					env.getMasterUrl() + "getYearLists?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] month = restTemplate.getForObject(env.getMasterUrl() + "getMonthLists",
					DropDownModel[].class);
			List<DropDownModel> monthLists = Arrays.asList(month);
			model.addAttribute("monthLists", monthLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getMasterUrl() + "getStaffType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> staffType = Arrays.asList(dropDownModel);
			model.addAttribute("staffType", staffType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] employe = restTemplate.getForObject(env.getMasterUrl() + "getEmployedByList?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> employedByList = Arrays.asList(employe);
			model.addAttribute("employedByList", employedByList);
		} catch (Exception e) {
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
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;
			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
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
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			model.addAttribute("yearList1", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		logger.info("Method : leaveWagesReport ends");
		return "master/view-leave-wages-report";
	}

	// view lic report
	@SuppressWarnings("unchecked")
	@GetMapping("view-leave-wages-report-employee")
	public @ResponseBody Object reportLeaveEmpData(HttpSession session) {
		logger.info("Method :reportLeaveEmpData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "reportLeaveEmpData?organization=" + organization
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :reportLeaveEmpData ends");
		return resp;
	}

	// view wages report
	@SuppressWarnings("unchecked")
	@GetMapping("view-leave-wages-report-employee-data")
	public @ResponseBody Object reportLeaveWagesReportData(HttpSession session,@RequestParam String id) {
		logger.info("Method :reportLeaveWagesReportData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "reportLeaveWagesData?id=" + id + "&organization=" + organization   
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :reportLeaveWagesReportData ends");
		return resp;
	}
	//get LeapYear
		public Boolean getLeapYear(String year) {
			Boolean leapyear = false;

			Integer yr = Integer.parseInt(year);

			if (((yr % 4 == 0) && (yr % 100 != 0)) || (yr % 400 == 0))
				leapyear = true;
			else
				leapyear = false;

			return leapyear;
		}
//
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "payroll-report-view-goal-attendance-get-attnd-date" })
	public @ResponseBody JsonResponse<Object> getAttendanceDate(Model model, @RequestBody DropDownModel data,
			BindingResult result, HttpSession session) {
		logger.info("Method : getAttendanceDate starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String year = data.getName();
		Boolean leapyr = false;
		if (year != null && year != "") {
			leapyr = getLeapYear(year);
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getAttendanceDate?month=" + data.getKey() + "&lyear="
					+ leapyr.toString() + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("RESSUULTTTT===" + res);
		logger.info("Method : getAttendanceDate ends");
		return res;

	}
//
	@SuppressWarnings("unchecked")
	@GetMapping("payroll-report-view-goal-attendance-get-listing-data")
	public @ResponseBody JsonResponse<List<AttendanceDateModel>> getEmployeeAttendanceList(Model model,
			@RequestParam String fromDate,String toDate,String employedBy,String stafftype,String id,HttpServletRequest request, HttpSession session) {
		logger.info("Method :getEmployeeAttendanceList starts"+id);

		JsonResponse<List<AttendanceDateModel>> resp = new JsonResponse<List<AttendanceDateModel>>();

		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getEmployeeAttendanceList?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
					+ "&orgDivision=" + orgDivision+ "&stafftype=" + stafftype+ "&id=" + id, JsonResponse.class);
			logger.info("res====" + resp);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : getEmployeeAttendanceList ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("payroll-report-view-process-view-details")
	public @ResponseBody List<PayrollModel> viewProcess(@RequestParam String fromDate, String toDate, String stafftype,
			String employedBy,String id, HttpSession session, Model model) {
		logger.info("Method : viewProcess starts");

		JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewProcess?fromDate=" + fromDate + "&toDate="
					+ toDate + "&userId=" + userId + "&organization=" + organization + "&orgDivision=" + orgDivision
					+ "&employedBy=" + employedBy + "&stafftype=" + stafftype + "&id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("resp.getBody()=====" + resp.getBody());
		logger.info("Method : viewProcess ends");
		return resp.getBody();
	}
//
	//attendance save as draft
		@SuppressWarnings({ "unchecked" })
		@PostMapping(value = "payroll-report-view-goal-attendance-save-draft")
		public @ResponseBody JsonResponse<Object> saveDraftEmployeeAttendance(@RequestBody List<AttendanceDateModel> attendModel,
				HttpSession session) {
			logger.info("Method : saveDraftEmployeeAttendance function starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (AttendanceDateModel m : attendModel) {
				m.setApprovedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
				
			}
			try {
				resp = restTemplate.postForObject(env.getMasterUrl() + "saveDraftEmployeeAttendance", attendModel,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != null && resp.getMessage() != "") {
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method : saveDraftEmployeeAttendance function Ends");
			return resp;
		}
		//attendance approve
		@SuppressWarnings({ "unchecked" })
		@PostMapping(value = "payroll-report-view-goal-attendance-approve-attnd")
		public @ResponseBody JsonResponse<Object> approveEmployeeAttendance(@RequestBody List<AttendanceDateModel> attendModel,
				HttpSession session) {
			logger.info("Method : approveEmployeeAttendance function starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (AttendanceDateModel m : attendModel) {
				m.setApprovedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
			}
			try {
				resp = restTemplate.postForObject(env.getMasterUrl() + "approveEmployeeAttendance", attendModel,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != null && resp.getMessage() != "") {
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method : approveEmployeeAttendance function Ends");
			return resp;
		}
//
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "payroll-report-view-process-approve-details" })
		public @ResponseBody JsonResponse<Object> approveProcessDetails(Model model, HttpSession session,
				@RequestBody List<PayrollApprovalModel> data, BindingResult result) {
			logger.info("Method : approveProcessDetails starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (PayrollApprovalModel m : data) {
				m.setApprovedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
			}
			logger.info("data=======" + data);
			try {
				res = restTemplate.postForObject(env.getMasterUrl() + "approveProcessDetails", data, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}

			logger.info("Method : approveProcessDetails ends");
			return res;

		}
		
//
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "payroll-report-view-process-saveAsDraft-details" })
		public @ResponseBody JsonResponse<Object> saveAsDraftProcessDetails(Model model, HttpSession session,
				@RequestBody List<PayrollApprovalModel> data, BindingResult result) {
			logger.info("Method : saveAsDraftProcessDetails starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (PayrollApprovalModel m : data) {
				m.setApprovedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);
			}
			logger.info("data=======" + data);
			try {
				res = restTemplate.postForObject(env.getMasterUrl() + "saveAsDraftProcessDetails", data,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}

			logger.info("Method : saveAsDraftProcessDetails ends");
			return res;

		}
//
		@SuppressWarnings("unchecked")
		@GetMapping("payroll-report-view-approve-view-details")
		public @ResponseBody List<PayrollModel> viewApprove(@RequestParam String fromDate, String toDate, String employedBy,
				String stafftype,String id, HttpSession session, Model model) {

			logger.info("Method : viewApprove starts");

			JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewApprove?fromDate=" + fromDate + "&toDate="
						+ toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
						+ "&orgDivision=" + orgDivision + "&stafftype=" + stafftype + "&id=" + id, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : viewApprove ends");
			return resp.getBody();
		}
//
		@SuppressWarnings("unchecked")
		@GetMapping("payroll-report-view-salary-advice-view-details")
		public @ResponseBody List<PayrollModel> viewSalaryAdvice(@RequestParam String fromDate, String toDate,
				String employedBy, String stafftype,String id, HttpSession session, Model model) {

			logger.info("Method : viewSalaryAdvice starts");

			JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewSalaryAdvice?fromDate=" + fromDate
						+ "&toDate=" + toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization="
						+ organization + "&orgDivision=" + orgDivision + "&stafftype=" + stafftype + "&id=" + id, 
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : viewSalaryAdvice ends");
			return resp.getBody();
		}
		@SuppressWarnings("unchecked")
		@GetMapping("payroll-report-view-epf-view-details")
		public @ResponseBody List<PayrollModel> viewEpf(@RequestParam String fromDate, String toDate, String employedBy,
				String stafftype,String id, HttpSession session, Model model) {

			logger.info("Method : viewEpf starts");

			JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewEpf?fromDate=" + fromDate + "&toDate="
						+ toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
						+ "&orgDivision=" + orgDivision + "&stafftype=" + stafftype + "&id=" + id, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : viewEpf ends");
			return resp.getBody();
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("payroll-report-view-esi-view-details")
		public @ResponseBody List<PayrollModel> viewEsi(@RequestParam String fromDate, String toDate, String employedBy,
				String stafftype,String id, HttpSession session, Model model) {

			logger.info("Method : viewEsi starts");

			JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewEsi?fromDate=" + fromDate + "&toDate="
						+ toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
						+ "&orgDivision=" + orgDivision + "&stafftype=" + stafftype + "&id=" + id, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : viewEsi ends");
			return resp.getBody();
		}
	//
		@SuppressWarnings("unchecked")
		@GetMapping("payroll-report-view-proffesional-tax-view-details")
		public @ResponseBody List<PayrollModel> viewProffesionalTax(@RequestParam String fromDate, String toDate,
				String employedBy, String stafftype,String id, HttpSession session, Model model) {

			logger.info("Method : viewTax starts");

			JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-proffesional-tax?fromDate=" + fromDate
						+ "&toDate=" + toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization="
						+ organization + "&orgDivision=" + orgDivision + "&stafftype=" + stafftype + "&id=" + id, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : viewTax ends");
			return resp.getBody();
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "payroll-report-professional-tax-Pdf" })
		public void generatePdfForDemo(HttpServletResponse response, HttpSession session, @RequestParam String stafftype,
				@RequestParam String employedBy, @RequestParam String fromDate, @RequestParam String toDate,@RequestParam String id) {
			logger.info("Method : generatePdfForDemo starts");

			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			JsonResponse<Object> resp = new JsonResponse<>();

			try {
				String url = env.getMasterUrl() + "rest-tax-Pdf?orgName=" + orgName + "&orgDivision=" + orgDivision
						+ "&stafftype=" + stafftype + "&employedBy=" + employedBy + "&fromDate=" + fromDate + "&toDate="
						+ toDate + "&userId=" + userId + "&id=" + id;
				resp = restTemplate.getForObject(url, JsonResponse.class);
			} catch (Exception e) {
				logger.error("Error fetching data for PDF generation", e);
			}

			logger.info("Fetched response data: ");

			// Assuming resp.getBody() returns a JSON string
			String responseBody = (String) resp.getBody();

			Map<String, Object> data = new HashMap<>();

			// Convert JSON string to Map
			try {
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> responseData = mapper.readValue(responseBody, Map.class);
				List<Map<String, Object>> employeeData = (List<Map<String, Object>>) responseData.get("data");
				Map<String, Object> totals = (Map<String, Object>) responseData.get("totals");

				data.put("employeeData", employeeData);
				
				System.out.println("employeeData====================>"+employeeData);
				
				//data.put("totals", totals);

				String sumNetPay = (String) totals.get("sumNetPay");
				String sumProfTax = (String) totals.get("sumProfTax");
				String date = (String) totals.get("date");
				
		        data.put("date" ,date);
				System.out.println("date ========================>"+ date);
				
				
				
	            data.put("sumNetPay" ,sumNetPay);
	            System.out.println("sumNetPay ========================>"+ sumNetPay);
	            
	            data.put( "sumProfTax",sumProfTax);
	            System.out.println("sumProfTax ========================>"+ sumProfTax);
	            
	            String sumNetPayInWords = NumberToWordsConverter.convert(sumNetPay);
	            System.out.println("sumNetPayInWords ========================>"+ sumNetPayInWords);
	            data.put( "sumNetPayInWords",sumNetPayInWords);
	            
	            
	            
	            String sumProfTaxinWord =NumberToWordsConverter.convert(sumProfTax);
	            String UpperCaseSumProfTaxinWord=sumProfTaxinWord.toUpperCase();
	            System.out.println("UpperCaseSumProfTaxinWord ========================>"+ sumProfTaxinWord);
	            data.put( "sumProfTaxinWord",UpperCaseSumProfTaxinWord);
	           
	            data.put("orgDivision", orgDivision);
	            System.out.println("orgDivision ========================>"+ orgDivision);
	         

			} catch (IOException e) {
				logger.error("Error parsing JSON response", e);
			}

			String filename = "professional-tax.pdf";
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=" + filename);
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("master/professional-tax_pdf.html", data);
				InputStream in = new FileInputStream(file);
				fileData = IOUtils.toByteArray(in);
				response.setContentLength(fileData.length);
				response.getOutputStream().write(fileData);
				response.getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			logger.info("Method : generatePdfForDemo ends");
		}
		
//
		@SuppressWarnings("unchecked")
		@GetMapping("payroll-report-view-tax-view-details")
		public @ResponseBody List<PayrollModel> viewTax(@RequestParam String fromDate, String toDate, String employedBy,
				String stafftype,String id, HttpSession session, Model model) {

			logger.info("Method : viewTax starts");

			JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewTax?fromDate=" + fromDate + "&toDate="
						+ toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
						+ "&orgDivision=" + orgDivision + "&stafftype=" + stafftype + "&id=" + id, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : viewTax ends");
			return resp.getBody();
		}
		
//
		@GetMapping("payroll-report-view-lic-view-details")
		public @ResponseBody Object viewLic(@RequestParam String fromDate, String toDate, String employedBy,
				String stafftype,String id, HttpSession session, Model model) {

			logger.info("Method :viewLic starts");
			@SuppressWarnings("rawtypes")
			JsonResponse resp = new JsonResponse();
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewLic?fromDate=" + fromDate + "&toDate="
						+ toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
						+ "&orgDivision=" + orgDivision + "&stafftype=" + stafftype + "&id=" + id, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewLic ends  " + resp);
			return resp;
		}
//
	
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "payroll-report-view-goal-attendance-download-excel-employee-attendance" })
		public ModelAndView downloadExcelEmployeeAttendance(HttpServletResponse response, Model model, HttpSession session,
		        @RequestParam String fromDate, String toDate, String employedBy, String monthYear,
		        String days, String stafftype, String id) {

		    logger.info("Method : downloadExcelEmployeeAttendance starts");

		    byte[] encodeByte1 = Base64.getDecoder().decode(fromDate.getBytes());
		    String fromDate1 = new String(encodeByte1);

		    byte[] encodeByte2 = Base64.getDecoder().decode(toDate.getBytes());
		    String toDate1 = new String(encodeByte2);

		    JsonResponse<List<AttendanceDateModel>> resp = new JsonResponse<List<AttendanceDateModel>>();
		    Map<String, Object> data = new HashMap<String, Object>();
		    String userId = "";
		    String organization = "";
		    String orgDivision = "";

		    try {
		        userId = (String) session.getAttribute("USER_ID");
		        organization = (String) session.getAttribute("ORGANIZATION");
		        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    List<AttendanceDateModel> attendance = new ArrayList<>(); // Initialize an empty list

		    try {
		        resp = restTemplate.getForObject(env.getMasterUrl() + "getEmployeeAttendanceList?fromDate=" + fromDate1
		                + "&toDate=" + toDate1 + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
		                + "&orgDivision=" + orgDivision + "&stafftype=" + stafftype + "&id=" + id, JsonResponse.class);

		        ObjectMapper mapper = new ObjectMapper();
		        
		        if (resp.getBody() != null && !resp.getBody().isEmpty()) {
		            attendance = mapper.convertValue(resp.getBody(), new TypeReference<List<AttendanceDateModel>>() {});
		            attendance.get(0).setDays(days);
		            attendance.get(0).setMonth(monthYear);
		        }

		    } catch (RestClientException e) {
		        e.printStackTrace();
		    }

		    logger.info("attendance==" + attendance);

		    data.put("attendance", attendance);

		    response.setContentType("application/ms-excel");
		    response.setHeader("Content-disposition", "attachment; filename=" + "Employee Attendance Details-" + monthYear + ".xls");

		    logger.info("Method : downloadExcelEmployeeAttendance ends");
		    return new ModelAndView(new EmployeePayrollAttendanceExcelModel(), data);
		}

//
	
		@SuppressWarnings("unchecked")
		@GetMapping("payroll-report-view-approve-view-details-excel")
		public @ResponseBody ModelAndView viewApproveExcel(HttpServletResponse response, @RequestParam String fromDate,
		        String toDate, String employedBy, String stafftype, String monthYear, String days, String id, HttpSession session,
		        Model model) {

		    logger.info("Method : viewApproveExcel starts");

		    JsonResponse<List<PayrollModel>> resp = new JsonResponse<List<PayrollModel>>();
		    Map<String, Object> data = new HashMap<String, Object>();
		    String userId = "";
		    String organization = "";
		    String orgDivision = "";

		    try {
		        userId = (String) session.getAttribute("USER_ID");
		        organization = (String) session.getAttribute("ORGANIZATION");
		        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    List<PayrollModel> attendance = new ArrayList<>(); // Initialize empty list

		    try {
		        resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewApprove?fromDate=" + fromDate + "&toDate="
		                + toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
		                + "&orgDivision=" + orgDivision + "&stafftype=" + stafftype + "&id=" + id, JsonResponse.class);
		        
		        logger.info("resp.getBody()-----" + resp.getBody());

		        ObjectMapper mapper = new ObjectMapper();
		        
		        if (resp.getBody() != null && !resp.getBody().isEmpty()) {
		            attendance = mapper.convertValue(resp.getBody(), new TypeReference<List<PayrollModel>>() {});
		            attendance.get(0).setDays(days);
		        }

		    } catch (RestClientException e) {
		        e.printStackTrace();
		    }

		    logger.info("attendance==" + attendance);

		    data.put("attendance", attendance);

		    response.setContentType("application/ms-excel");
		    response.setHeader("Content-disposition", "attachment; filename=" + "Employee Appove Details-" + monthYear + ".xls");

		    logger.info("Method : viewApproveExcel ends==" + data);
		    return new ModelAndView(new EmployeePayrollApproveExcelModel(), data);
		}

		
//
		@SuppressWarnings({ "unchecked", "deprecation" })
		@GetMapping("payroll-report-employee-bonus-exgratia-view")
		public @ResponseBody Object viewEmployeeBonusExgratia(@RequestParam String fromDate, String toDate,String id, HttpSession session) {

			logger.info("Method :viewEmployeeBonusExgratia starts");
			@SuppressWarnings("rawtypes")
			JsonResponse resp = new JsonResponse();
			String org = "";
			String orgDiv = "";

			try {
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(
						env.getMasterUrl() + "viewEmployeeBonusExgratia?fromDate=" + fromDate + "&toDate=" + toDate 
						+ "&org=" + org + "&orgDiv=" + orgDiv + "&id=" + id,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewEmployeeBonusExgratia ends  ");
			return resp;
		}
//
		@PostMapping("payroll-report-employee-bonus-exgratia-upload-file")
		public @ResponseBody JsonResponse<Object> uploadEmployeeBonusExgratia(@RequestParam("file") MultipartFile attendance,
				HttpSession session) {
			logger.info("Method : uploadEmployeeBonusExgratia controller  starts");

			JsonResponse<Object> response = new JsonResponse<Object>();

			try {
				XSSFWorkbook workbook = new XSSFWorkbook(attendance.getInputStream());
				response.setMessage(attendance.getOriginalFilename());
				session.setAttribute("attendance", workbook);

			} catch (RestClientException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("response########" + response);
			logger.info("Method : uploadEmployeeBonusExgratia controller ' ends");
			return response;
		}
//
		@SuppressWarnings({ "unchecked" })
		@PostMapping("payroll-report-employee-bonus-exgratia-save-excelData")
		public @ResponseBody JsonResponse<Object> addEmployeeBonusExgratia(@RequestBody List<EmployeeBonusModel> model,
				HttpSession session) {
			logger.info("Method :addEmployeeBonusExgratia starts==="+model);
			
			String userId = "";
			String dateFormat = "";
			String orgName = "";
			String orgDivision = "";
			try {

				userId = (String) session.getAttribute("USER_ID");
				dateFormat = (String) session.getAttribute("DATEFORMAT");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (RestClientException e) {
				e.printStackTrace();

			}
			String date = DateFormatter.inputDateFormat(model.get(0).getDate(), dateFormat) ; 
			
			
			
	        List<EmployeeBonusModel> attendanceList = new ArrayList<>();
	        XSSFWorkbook workbook =(XSSFWorkbook)session.getAttribute("attendance");
	        XSSFSheet worksheet = workbook.getSheetAt(0);
			System.out.println("worksheet.getPhysicalNumberOfRows()===="+worksheet.getPhysicalNumberOfRows());
			for (int index = 4; index < worksheet.getPhysicalNumberOfRows(); index++) {
	            if (index > 0) {
	            	EmployeeBonusModel atten = new EmployeeBonusModel();

	                XSSFRow row = worksheet.getRow(index);
	                //Integer id = (int) row.getCell(0).getNumericCellValue();
	                DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
	                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

	                if (row.getCell(1) != null && !formatter.formatCellValue(row.getCell(1)).trim().isEmpty()) {
	                	
		                atten.setEmployeeId(formatter.formatCellValue(row.getCell(0)));
		                atten.setEmployeeName(formatter.formatCellValue(row.getCell(1)));
		                atten.setDate(date);
		                atten.setAttendance(formatter.formatCellValue(evaluator.evaluateInCell(row.getCell(2))));
		                atten.setBasicSal(formatter.formatCellValue(evaluator.evaluateInCell(row.getCell(3))));
		                atten.setBonus(formatter.formatCellValue(evaluator.evaluateInCell(row.getCell(4))));
		                atten.setExgratia(formatter.formatCellValue(evaluator.evaluateInCell(row.getCell(5))));
		                atten.setTotal(formatter.formatCellValue(evaluator.evaluateInCell(row.getCell(6))));
		                
		                atten.setDetails(formatter.formatCellValue(row.getCell(7)));     
		                atten.setCreatedBy(userId);
		                atten.setOrganization(orgName);
		                atten.setOrgDivision(orgDivision);
		                attendanceList.add(atten);
	                }
	            }
	        }
			
			JsonResponse<Object> resp = new JsonResponse<Object>();
			logger.info("attendanceList add 11====="+attendanceList);
			try {
				resp = restTemplate.postForObject(env.getMasterUrl() +
					"addUloadedEmployeeBonusExgratia", attendanceList, JsonResponse.class); 
			} catch(RestClientException e) {
				e.printStackTrace();
			 }

			String message = resp.getMessage();
			if (message != null && message != "") {

			} else {
				resp.setMessage("Success");
			}
			logger.info("Method : addEmployeeBonusExgratia ends");
			return resp;
		}
//
		@SuppressWarnings("unchecked")

		@GetMapping("payroll-report-view-salary-revision-view")
		public @ResponseBody List<SalaryRevisionModel> viewSalaryMaster(HttpSession session, @RequestParam String userid,@RequestParam String id ) {
			logger.info("Method : viewSalaryMaster starts");

			JsonResponse<List<SalaryRevisionModel>> resp = new JsonResponse<List<SalaryRevisionModel>>();
			String organization = "";
			String orgDivision = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "viewSalaryMaster?userid=" + userid + "&organization="
						+ organization + "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();
			List<SalaryRevisionModel> Model = mapper.convertValue(resp.getBody(),
					new TypeReference<List<SalaryRevisionModel>>() {
					});
			String dateFormat = "";
			try {
				dateFormat = (String) session.getAttribute("DATEFORMAT");
			} catch (Exception e) {

			}
			for (SalaryRevisionModel a : Model) {
				/*
				 * if (a.getEffectiveDate() != null && a.getEffectiveDate() != "") {
				 * a.setEffectiveDate(DateFormatter.dateFormat(a.getEffectiveDate(),
				 * dateFormat)); }
				 */
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method : viewSalaryMaster  ends");
			return Model;

		}
//
		@SuppressWarnings("unchecked")

		@GetMapping("payroll-report-view-salary-revision-view-by-year")
		public @ResponseBody List<SalaryRevisionModel> viewSalaryMasterByYear(HttpSession session,
				@RequestParam String userid, String startDate, String endDate, String id) {
			logger.info("Method : viewSalaryMasterByYear starts");

			JsonResponse<List<SalaryRevisionModel>> resp = new JsonResponse<List<SalaryRevisionModel>>();
			String organization = "";
			String orgDivision = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate
						.getForObject(
								env.getMasterUrl() + "viewSalaryMasterByYear?startDate=" + startDate + "&endDate=" + endDate
										+ "&organization=" + organization + "&orgDivision=" + orgDivision + "&id=" + id,
								JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();
			List<SalaryRevisionModel> Model = mapper.convertValue(resp.getBody(),
					new TypeReference<List<SalaryRevisionModel>>() {
					});
			String dateFormat = "";
			try {
				dateFormat = (String) session.getAttribute("DATEFORMAT");
			} catch (Exception e) {
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method : viewSalaryMasterByYear  ends");
			return Model;

		}
//
		@SuppressWarnings("unchecked")
		@GetMapping("payroll-report-view-salary-revision-edit")
		public @ResponseBody Object editSalaryRevision(@RequestParam String Id, Model model, HttpSession session) {

			logger.info("Method :editSalaryRevision starts");
			@SuppressWarnings("rawtypes")
			JsonResponse resp = new JsonResponse();
			String org = "";
			String orgDiv = "";

			try {
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-salary-revision-edit?id=" + Id,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :editSalaryRevision ends  ");
			return resp;
		}
//
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "payroll-report-view-salary-revision-save" })
		public @ResponseBody JsonResponse<Object> addsalaryrevision(HttpSession session,
				@RequestBody SalaryRevisionModel data) {
			logger.info("Method : salary-revision starts");

			JsonResponse<Object> res = new JsonResponse<Object>();
			String dateFormat = "";
			String userId = "";
			String organization = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				dateFormat = (String) session.getAttribute("DATEFORMAT");
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				data.setEffectiveFromDate(DateFormatter.inputDateFormat(data.getEffectiveFromDate(), dateFormat));
				//data.setEffectiveToDate(DateFormatter.inputDateFormat(data.getEffectiveToDate(), dateFormat));
				data.setJoiningDate(DateFormatter.inputDateFormat(data.getJoiningDate(), dateFormat));
			} catch (Exception e) {
				e.printStackTrace();
			}
			data.setCreatedBy(userId);
			data.setOrganization(organization);
			data.setOrgDivision(orgDivision);
			logger.info("data=======" + data);
			try {
				res = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-salary-revision", data,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * if (res.getMessage() != null) { res.setCode(res.getMessage());
			 * res.setMessage("Unsuccess"); } else { res.setMessage("success"); }
			 */

			logger.info("Method : salary-revision ends");
			logger.info("adddd=======" + res);
			return res;

		}
//
		@SuppressWarnings("unchecked")
		@GetMapping("payroll-report-view-salary-revision-approve")
		public @ResponseBody Object approveEmpolyeeReview(@RequestParam String id, String sts, HttpSession session) {

			logger.info("Method :approveEmpolyeeReview starts");
			@SuppressWarnings("rawtypes")
			JsonResponse resp = new JsonResponse();
			String org = "";
			String orgDiv = "";

			try {
				org = (String) session.getAttribute("ORGANIZATION");
				orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getMasterUrl() + "approveSalaryStatus?id=" + id + "&sts=" + sts
						+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :approveEmpolyeeReview ends" + resp);
			return resp;
		}
//
		@SuppressWarnings("unchecked")
		@PostMapping("payroll-report-view-salary-revision-delete")
		public @ResponseBody JsonResponse<Object> deleteSalaryRevision(@RequestParam String id, Model model,
				HttpSession session) {
			logger.info("Method : deleteSalaryRevision function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();
			String organization = "";
			String orgDivision = "";
			try {
				organization = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				res = restTemplate.getForObject(env.getMasterUrl() + "rest-SalaryRevision-delete?id=" + id
						+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteSalaryRevision function Ends");

			logger.info("RESPPPPPPP" + res);
			return res;
		}
//
		@SuppressWarnings("unchecked")
		@GetMapping("payroll-report-view-salary-revision-bandcalc")
		public @ResponseBody Object bandCalculation(@RequestParam String band, HttpSession session) {
			logger.info("Method :bandCalculation starts");
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

				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-salary-revision-bandcalc?band=" + band
						+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

			logger.info("Method :bandCalculation ends");
			return resp;
		}
//
		@SuppressWarnings("unchecked")
		@GetMapping("payroll-report-view-payslip-list")
		public @ResponseBody List<PayslipModel> viewPayslipList(@RequestParam String empId, @RequestParam String fromDate,
				@RequestParam String toDate,@RequestParam String id, HttpSession session, Model model) {

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
				resp = restTemplate.getForObject(
						env.getMasterUrl() + "view-employe-paySlip-api?userId=" + empId + "&fromDate=" + fromDate
								+ "&toDate=" + toDate + "&organization=" + organization + "&orgDivision=" + orgDivision + "&id=" + id,
						JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : viewPayslipList ends");
			return resp.getBody();
		}
}
