package nirmalya.aathithya.webmodule.master.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.AttendanceDateModel;

@Controller
@RequestMapping(value = "master")
public class PaySlipAttendanceController {

	Logger logger = LoggerFactory.getLogger(PaySlipAttendanceController.class);

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

	public int daysBetweenDates(String date1, String date2) {
		LocalDate dt1 = LocalDate.parse(date1);
		LocalDate dt2 = LocalDate.parse(date2);

		long diffDays = ChronoUnit.DAYS.between(dt1, dt2);
		return Math.abs((int) diffDays);
	}
//view leave page
	@GetMapping("/view-leave")
	public String viewLeave(Model model, HttpSession session) {
		logger.info("Method : viewLeave starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
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
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			// List<DropDownModel> startDayForAtten = Arrays.asList(startDay);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewLeave ends");
		return "master/viewLeave";
	}
	//get leave attendance date
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-leave-get-attnd-date" })
	public @ResponseBody JsonResponse<Object> getAttendanceDateForLeave(Model model, @RequestBody DropDownModel data,
			BindingResult result, HttpSession session) {
		logger.info("Method : getAttendanceDateForLeave starts");

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

		logger.info("Method : getAttendanceDateForLeave ends");
		return res;

	}
//approve leave 
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-leave-approve-attnd" })
	public @ResponseBody JsonResponse<Object> approveEmployeeLeave(Model model, @RequestBody List<DropDownModel> data,
			BindingResult result) {
		logger.info("Method : approveEmployeeLeave starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "approveEmployeeLeave", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : approveEmployeeLeave ends");
		return res;
	}
	
/************************     Attendance Section     *************************/
//view attendance page
	@GetMapping("/view-goal-attendance")
	public String viewAttendance(Model model, HttpSession session) {
		logger.info("Method : viewAttendance starts");
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
		logger.info("Method : viewAttendance ends");
		return "master/viewAttendance";
	}

	// for get Approver Status
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/view-goal-attendance-getApproverStatus" })
	public @ResponseBody JsonResponse<Object> getAttenApproverStatus(HttpSession session, @RequestParam String fromDate,
			@RequestParam String userId) {
		logger.info("Method : getAttenApproverStatus starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String process = "upm002";
		try {
			res = restTemplate.getForObject(
					env.getMasterUrl() + "rest-getApproverStatus?fromDate=" + fromDate + "&process=" + process
							+ "&userId=" + userId + "&organization=" + organization + "&orgDivision=" + orgDivision,
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
		logger.info("res=====" + res);
		logger.info("Method : getAttenApproverStatus ends");
		return res;

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
//get attendance date
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-goal-attendance-get-attnd-date" })
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
//attendance approve
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "view-goal-attendance-approve-attnd")
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
//attendance save as draft
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "view-goal-attendance-save-draft")
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

	@SuppressWarnings("unchecked")
	@GetMapping("view-goal-attendance-get-listing-data")
	public @ResponseBody JsonResponse<List<AttendanceDateModel>> getEmployeeAttendanceList(Model model,
			@RequestParam String fromDate,String toDate,String employedBy,String stafftype,HttpServletRequest request, HttpSession session) {
		logger.info("Method :getEmployeeAttendanceList starts");

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
					+ "&orgDivision=" + orgDivision+ "&stafftype=" + stafftype, JsonResponse.class);
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

	/***************** Leave ********************/

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-leave-get-listing-data" })
	public @ResponseBody List<AttendanceDateModel> getEmployeeAttendanceListForLeave(Model model,
			@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String employedBy, String month,
			HttpServletRequest request, HttpSession session) {
		logger.info("Method : getEmployeeAttendanceListForLeave starts");

		JsonResponse<List<AttendanceDateModel>> res = new JsonResponse<List<AttendanceDateModel>>();

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
			res = restTemplate.getForObject(env.getMasterUrl() + "getEmployeeLeaveList?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
					+ "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getEmployeeAttendanceListForLeave ends");
		return res.getBody();

	}
	/*
	 * Shift type autoSearch
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-goal-attendance-autosearch-attenType" })
	public @ResponseBody JsonResponse<DropDownModel> getAttndTypeAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getAttndTypeAutoSearchList starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "getAttndTypeAutoSearchList?id=" + searchValue,
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
		logger.info("Method : getAttndTypeAutoSearchList ends");
		logger.info("AUTOSEARCHHH" + res);
		return res;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("view-goal-attendance-type")
	public @ResponseBody JsonResponse<List<DropDownModel>> viewsTypedetailsAttnd(Model model, HttpSession session) {

		logger.info("Method : viewsTypedetailsAttnd ");

		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-attendanceType?organization=" + organization
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : viewsTypedetailsAttnd  ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp);

		return resp;

	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/view-goal-attendance-download-excel-employee-attendance" })
	public ModelAndView downloadExcelEmployeeAttendance(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam String fromDate,String toDate,String employedBy, String monthYear,
			String days ,String stafftype) {
		logger.info("Method : downloadExcelEmployeeAttendance starts");

		byte[] encodeByte1 = Base64.getDecoder().decode(fromDate.getBytes());
		String fromDate1 = (new String(encodeByte1));

		byte[] encodeByte2 = Base64.getDecoder().decode(toDate.getBytes());
		String toDate1 = (new String(encodeByte2));

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
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getEmployeeAttendanceList?fromDate=" + fromDate1
					+ "&toDate=" + toDate1 + "&employedBy=" + employedBy + "&userId=" + userId + "&organization=" + organization
					+ "&orgDivision=" + orgDivision+"&stafftype="+stafftype, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			List<AttendanceDateModel> attendance = mapper.convertValue(resp.getBody(),
					new TypeReference<List<AttendanceDateModel>>() {
					});
			attendance.get(0).setDays(days);
			attendance.get(0).setMonth(monthYear);

			data.put("attendance", attendance);
			System.err.printf("attendance====",attendance);
			response.setContentType("application/ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=" +stafftype +" "+"Employee Attendance Details-"
					+ monthYear + " " + new Date().getTime() + ".xls");

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : downloadExcelEmployeeAttendance ends");
		return new ModelAndView(new EmployeePayrollAttendanceExcelModel(), data);
	}

}
