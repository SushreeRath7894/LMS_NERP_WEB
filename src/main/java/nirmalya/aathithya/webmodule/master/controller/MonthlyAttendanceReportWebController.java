package nirmalya.aathithya.webmodule.master.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.AttendanceDateModel;

@Controller
@RequestMapping(value = "master")

public class MonthlyAttendanceReportWebController {

	Logger logger = LoggerFactory.getLogger(PaySlipAttendanceController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("/view-monthly-attendance-report")
	public String viewLeave(Model model, HttpSession session) {
		logger.info("Method : viewLeave starts");

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
			DropDownModel[] startDay = restTemplate.getForObject(env.getMasterUrl()
					+ "getStartDayForAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			model.addAttribute("startDayForAtten", startDay[0].getKey());
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
			DropDownModel[] type = restTemplate.getForObject(env.getMasterUrl()
					+ "getStaffTypeMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> typeList = Arrays.asList(type);
			model.addAttribute("typeList", typeList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		// model.addAttribute("organization", organization);

		logger.info("Method : viewAttendance ends");
		return "master/monthlyAttendanceReport";
	}	
	
	// for get Approver Status
	
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "/view-monthly-attendance-report-getApproverStatus" })
		public @ResponseBody JsonResponse<Object> getAttenApproverStatuss(HttpSession session, @RequestParam String fromDate,
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
						env.getMasterUrl() + "rest-getMonthlyAttendanceApproverStatus?fromDate=" + fromDate + "&process=" + process
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

		public Boolean getLeapYear(String year) {
			Boolean leapyear = false;

			Integer yr = Integer.parseInt(year);

			if (((yr % 4 == 0) && (yr % 100 != 0)) || (yr % 400 == 0))
				leapyear = true;
			else
				leapyear = false;

			return leapyear;
		}

		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-monthly-attendance-report-get-attnd-date" })
		public @ResponseBody JsonResponse<Object> getAttendanceDates(Model model, @RequestBody DropDownModel data,
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
				res = restTemplate.getForObject(env.getMasterUrl() + "getAttendanceReportDate?month=" + data.getKey() + "&lyear="
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
		
		@SuppressWarnings({ "unchecked" })
		@PostMapping(value = "view-monthly-attendance-report-approve-attnd")
		public @ResponseBody JsonResponse<Object> approveEmployeeAttendances(@RequestBody List<AttendanceDateModel> approve,
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
			for (AttendanceDateModel m : approve) {
				m.setApprovedBy(userId);
				m.setOrganization(organization);
				m.setOrgDivision(orgDivision);

			}
			logger.info("data=======" + approve);
			try {
				resp = restTemplate.postForObject(env.getMasterUrl() + "approveMonthlyEmployeeAttendance", approve,
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

			logger.info("ADDDDDDD" + resp);
			return resp;
		}


		@SuppressWarnings("unchecked")

		@GetMapping("view-monthly-attendance-report-get-listing-data")
		public @ResponseBody JsonResponse<List<AttendanceDateModel>> getEmployeeAttendanceLists(Model model,
				@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String attendanceType,@RequestParam String stafftype,
				HttpServletRequest request, HttpSession session) {
			logger.info("Method :viewEmployeeShiftDetails starts");

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
				resp = restTemplate.getForObject(env.getMasterUrl() + "getEmployeeMonthlyAttendanceList?fromDate=" + fromDate
						+ "&toDate=" + toDate + "&attendanceType=" + attendanceType + "&userId=" + userId + "&organization=" + organization
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
			logger.info("VIEWWWWAJAX" + resp);
			return resp;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "/view-monthly-attendance-report-download-excel-employee-attendance" })
		public ModelAndView downloadExcelEmployeeAttendances(HttpServletResponse response, Model model, HttpSession session,
				@RequestParam String fromDate, @RequestParam String toDate, @RequestParam String attendanceType, String monthYear,
				String days) {
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
				resp = restTemplate.getForObject(env.getMasterUrl() + "getEmployeeMonthlyAttendanceList?fromDate=" + fromDate1
						+ "&toDate=" + toDate1 + "&attendanceType=" + attendanceType + "&userId=" + userId + "&organization=" + organization
						+ "&orgDivision=" + orgDivision, JsonResponse.class);

				ObjectMapper mapper = new ObjectMapper();
				List<AttendanceDateModel> attendance = mapper.convertValue(resp.getBody(),
						new TypeReference<List<AttendanceDateModel>>() {
						});
				attendance.get(0).setDays(days);
				logger.info("attendance==" + attendance);

				data.put("attendance", attendance);

				response.setContentType("application/ms-excel");
				response.setHeader("Content-disposition", "attachment; filename=" + "Employee Attendance Details-"+ monthYear + " " + new Date().getTime() + ".xls");

			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : downloadExcelEmployeeAttendance ends");
			return new ModelAndView(new EmployeePayrollAttendanceExcelModel(), data);
		}
		
		@SuppressWarnings("unchecked")

		@GetMapping("view-monthly-attendance-report-type")
		public @ResponseBody JsonResponse<List<DropDownModel>> viewshiftdetailsAttnds(Model model, HttpSession session) {

			logger.info("Method : viewshiftdetailsAttnd ");

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
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-attendanceTypes?organization=" + organization
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
			logger.info("Method : viewshiftdetailsAttnd  ends");
			logger.info("respwwwwwwwwwwwwwwwweb view" + resp);

			return resp;

		}
		
		/*
		 * Shift type autoSearch
		 */

		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-monthly-attendance-report-autosearch-attenType" })
		public @ResponseBody JsonResponse<DropDownModel> getAttndShiftAutoSearchList(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getAttndShiftAutoSearchList starts");
			JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

			try {
				res = restTemplate.getForObject(env.getMasterUrl() + "getAttndTypeMonthlyReportAutoSearchList?id=" + searchValue,
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
			logger.info("Method : getAttndShiftAutoSearchList ends");
			logger.info("AUTOSEARCHHH" + res);
			return res;
		}
		// monthly Report pdf
		@SuppressWarnings("unchecked")
		@GetMapping("view-monthly-attendance-report-pdf-downloads")
		public void getReportPdfDetails(HttpServletResponse response, Model model, HttpSession session,
				@RequestParam("empId") String encodedParam3, @RequestParam("fromDate") String encodedParam2,
				@RequestParam("toDate") String encodedParam1)
				throws JsonParseException, JsonMappingException, JSONException, IOException {

			logger.info("Method : getOpenStockPdfDetails starts");
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam3.getBytes());
			String empIdString = (new String(encodeByte5));
			byte[] encodeByte6 = Base64.getDecoder().decode(encodedParam2.getBytes());
			String fromDate = (new String(encodeByte6));
			byte[] encodeByte7 = Base64.getDecoder().decode(encodedParam1.getBytes());
			String toDate = (new String(encodeByte7));
			String[] employeeIds = empIdString.split(",");
		    SimpleDateFormat formDate = new SimpleDateFormat("dd-MM-yyyy");
		    String curDate = formDate.format(new Date());  

			JsonResponse<Object> resp = new JsonResponse<Object>();
			try {
				System.out.println("aaaaaaaaaaaaaaaaaaaaaa------------" + employeeIds);
				System.out.println("aaaaaaaaaaaaaaaaaaaaaa------------" + fromDate);
				System.out.println("aaaaaaaaaaaaaaaaaaaaaa------------" + toDate);
				resp = restTemplate.getForObject(env.getMasterUrl() + "rest-viewMonthlyAttReport?empId="
						+ Arrays.asList(employeeIds) + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("tefeg" + resp);
			Map<String, Object> data = new HashMap<String, Object>();

			System.out.println("Get Data === " + resp.getBody());

			if (resp.getBody() == null) {
				System.out.println("JSON Obj === " + resp.getBody());

				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline; filename=salesInvoice.pdf");
				File file;
				byte[] fileData = null;

				try {
					file = pdfGeneratorUtil.createPdf("master/monthlyAttReportPdf.html", data);
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
			}

			JSONArray jsonArray = new JSONArray(resp.getBody().toString());

			System.out.println("JSON Array === " + jsonArray);

			if (jsonArray != null) {
				System.out.println("JSON Array === " + jsonArray);

				ObjectMapper mapper = new ObjectMapper();

				List<Map<String, Object>> dataa = mapper.readValue(jsonArray.toString(),
						new TypeReference<List<Map<String, Object>>>() {
						});
				logger.info("dataa" + dataa);

				data.put("respdata", dataa);
			}
			data.put("fromdate", fromDate);
			data.put("todata", toDate);
			data.put("printdata", curDate);
			System.out.println("JSON Obj === " + jsonArray);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=monthlyAttendanceReportPdf.pdf");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("master/monthlyAttReportPdf.html", data);
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

			logger.info("Method : getOpenStockPdfDetails ends");
		}

}
