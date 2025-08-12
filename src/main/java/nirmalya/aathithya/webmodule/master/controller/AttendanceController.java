
package nirmalya.aathithya.webmodule.master.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.DataFormatter;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.pagination.DataTableRequest;
import nirmalya.aathithya.webmodule.common.pagination.DataTableResponse;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.AttendanceModel;

@Controller

@RequestMapping(value = { "master/" })
public class AttendanceController {
	Logger logger = LoggerFactory.getLogger(AttendanceController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "employee-attendance" })

	public String employeeAttendance(Model model, HttpSession session) {
		logger.info("Method : Attendance starts");

		String userId = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] shift = restTemplate.getForObject(
					env.getMasterUrl() + "getShiftLists?org=" + orgDivision + "&orgDiv=" + organization + "&userId=" + userId,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", data);
				logger.info("data ==" + data);

			} else if (data.contentEquals("rol003")) {

				model.addAttribute("mrRole", data);
				logger.info("data 1==" + data);
			} else {
				model.addAttribute("empRole", data);
				logger.info("data 2==" + data);
			}
		}
		AttendanceModel attendenceDetails = new AttendanceModel();

		AttendanceModel attendenceDetailsSession = (AttendanceModel) session.getAttribute("attendenceDetails");
		Date d = new Date();
		Object newdate = DateFormatter.returnStringDate(d);
		String x = (String) newdate;
		attendenceDetails.setPunchinDate(x);
		String date = attendenceDetails.getPunchinDate();

		String empId = "";
		attendenceDetails.getIsOut();
		try {
			empId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String message = (String) session.getAttribute("message");

		if (message != null && message != "") {
			model.addAttribute("message", message);
		}

		session.setAttribute("message", "");

		if (attendenceDetailsSession != null) {
			model.addAttribute("attendenceDetails", attendenceDetailsSession);
			session.setAttribute("attendenceDetails", null);

		} else {
			model.addAttribute("attendenceDetails", attendenceDetails);
		}

		date = LocalDate.now().toString();
		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getMasterUrl() + "get-details-punchout?empId=" + empId + "&date=" + date,
					DropDownModel[].class);
			List<DropDownModel> details = Arrays.asList(dropDownModel);
			model.addAttribute("details", details);
			logger.info("details====" + details);
			String isOut = null;
			if (!details.isEmpty()) {
				isOut = details.get(0).getName();
			}
			model.addAttribute("isOut", isOut);
			boolean arr = details.isEmpty();

			if (!arr) {
				return "master/employee-attendance";
			}

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : Attendance ends");
		return "master/employee-attendance";
	}
	// get punch

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/employee-attendance-getPunch" })
	public @ResponseBody JsonResponse<DropDownModel> getPunch(Model model, HttpSession session,@RequestParam String empids) {
		logger.info("Method : getemployeeList starts");

		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		String date = "";
		date = LocalDate.now().toString();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "rest-getPunch?empId=" + empids + "&date=" + date+"&org="+org+"&orgDiv="+orgDiv,
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
		logger.info("res==" + res);
		logger.info("Method : getPunch ends");
		return res;
	}

	// add punch in
	@SuppressWarnings("unchecked")
	@PostMapping("employee-attendance-add")
	public @ResponseBody JsonResponse<Object> addEmployeeAttendance(HttpSession session,

			@RequestBody AttendanceModel attendanceModel) {

		logger.info("Method : addEmployeeAttendance");

		String empId = "";
		String organization = "";
		String orgDivision = "";
		try {
			empId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		attendanceModel.setCreatedBy(empId);
		attendanceModel.setOrganization(organization);
		attendanceModel.setOrgDivision(orgDivision);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addEmployeeAttendances", attendanceModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		// logger.info("ERTYUK"+resp);
		logger.info("Method : addEmployeeAttendance ends");
		return resp;
	}

	// View Attendance

	@SuppressWarnings("unchecked")

	@GetMapping("/employee-attendance-view")
	public @ResponseBody List<AttendanceModel> viewEmployeeAttendance(HttpSession session,
			@RequestParam String fromDate, String toDate) {

		logger.info("Method : viewEmployeestarts");

		JsonResponse<List<AttendanceModel>> resp = new JsonResponse<List<AttendanceModel>>();

		String userId = "";
		String userRole = "";
		String isHr = "";
		String organization = "";
		String orgDivision = "";
		try {
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001")) {
				isHr = isHr + "HR";
			} else if (data.contentEquals("rol010")) {
				isHr = isHr + "HR";
			} else if (data.contentEquals("rol020")) {
				isHr = isHr + "HR";
			} else {
			}
		}
 
		try {
			userId = (String) session.getAttribute("USER_ID");

			resp = restTemplate.getForObject(env.getMasterUrl() + "viewNewEmployeeAttendances?userId=" + userId
					+ "&isHr=" + isHr + "&organization=" + organization + "&orgDivision=" + orgDivision + "&fromDate="
					+ fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : viewEmployeeAttendance ends");
		logger.info("respwwwwwwwwwwwwwwwweb view" + resp.getBody());

		// logger.info("studentModelwebview"+studentModel);
		return resp.getBody();
	}

	/**
	 * View Attendance Details through ajax
	 *
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("/employee-attendance-details-throughAjax")
	public @ResponseBody DataTableResponse viewAttendencePunchoutThroughAjax(Model model, HttpServletRequest request,

			@RequestParam String param1) {
		logger.info("Method : viewAttendencePunchoutThroughAjax starts");
		DataTableResponse response = new DataTableResponse();
		DataTableRequest tableRequest = new DataTableRequest();
		try {
			String start = request.getParameter("start");
			String length = request.getParameter("length");
			String draw = request.getParameter("draw");

			tableRequest.setStart(Integer.parseInt(start));
			tableRequest.setLength(Integer.parseInt(length));
			tableRequest.setParam1(param1);

			JsonResponse<List<AttendanceModel>> jsonResponse = new JsonResponse<List<AttendanceModel>>();

			jsonResponse = restTemplate.postForObject(env.getMasterUrl() + "viewAttendencePunchoutThroughAjax",
					tableRequest, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();

			List<AttendanceModel> attendenceDetails = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<List<AttendanceModel>>() {
					});

			/*
			 * for (AttendanceModel m : attendenceDetails) {
			 * 
			 * if (m.getPunchinLocation() == 1) { m.setStatus("office"); } else {
			 * m.setStatus("other"); }
			 * 
			 * if (m.getPunchOutLocation() == 1) { m.setAction("office"); } else {
			 * m.setAction("other"); }
			 * 
			 * }
			 */
			response.setRecordsTotal(jsonResponse.getTotal());
			response.setRecordsFiltered(jsonResponse.getTotal());
			response.setDraw(Integer.parseInt(draw));
			response.setData(attendenceDetails);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewAttendenceDetailsThroughAjax ends");
		return response;

	}

	/*
	 * Add Punch Out Form Post
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("employee-attendance-punchout-add")

	public @ResponseBody JsonResponse<Object> addEmployeeAttendancePunchOut(HttpSession session,

			@RequestBody AttendanceModel attendanceModel) {

		logger.info("Method : addEmployeeAttendancePunchOut");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "addEmployeeAttendancePunchOut", attendanceModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addEmployeeAttendancePunchOut ends");
		logger.info("rrrrrrrrrrrrrrrrrrrrrrrrrrrreturn" + resp);
		return resp;
	}
	// View Attendance location

	@SuppressWarnings("unchecked")

	@GetMapping("/employee-attendance-view-location")
	public @ResponseBody List<AttendanceModel> viewEmployeeLocation(HttpSession session) {

		logger.info("Method : viewEmployeeLocation starts");

		JsonResponse<List<AttendanceModel>> resp = new JsonResponse<List<AttendanceModel>>();

		String userId = "";

		try {
			userId = (String) session.getAttribute("USER_ID");

			resp = restTemplate.getForObject(env.getMasterUrl() + "viewEmployeeLocation?userId=" + userId,
					JsonResponse.class);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("resp====" + resp);
		logger.info("Method : viewEmployeeLocation ends");

		return resp.getBody();
	}
	@PostMapping("/employee-attendance-upload-file")
	public @ResponseBody JsonResponse<Object> uploadAttendanceData(@RequestParam("file") MultipartFile attendance,
			HttpSession session) {
		logger.info("Method : uploadAttendanceData controller  starts");

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
		logger.info("Method : uploadAttendanceData controller ' ends");
		return response;
	}
	// Save Upload Attendance Data

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/employee-attendance-save-excelData")
	public @ResponseBody JsonResponse<Object> addAttendaneData(@RequestBody List<AttendanceModel> attendanceModel,
			Model model, HttpSession session) {
		logger.info("Method :addAttendaneData starts");
		
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {

			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (RestClientException e) {
			e.printStackTrace();

		}
        List<AttendanceModel> attendanceList = new ArrayList<>();
        XSSFWorkbook workbook =(XSSFWorkbook)session.getAttribute("attendance");
        XSSFSheet worksheet = workbook.getSheetAt(0);
 
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
            	AttendanceModel atten = new AttendanceModel();

                XSSFRow row = worksheet.getRow(index);
                //Integer id = (int) row.getCell(0).getNumericCellValue();
                DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
                
                atten.setEmployeeId(formatter.formatCellValue(row.getCell(0)));
               // atten.setPunchinDate(row.getCell(1).toString());
               // atten.setPunchinTime(formatter.formatCellValue(row.getCell(2)));
        		if (row.getCell(1).toString() != null && row.getCell(1).toString() != "") {
					atten.setPunchinDate(DateFormatter.getStringNewDate(formatter.formatCellValue(row.getCell(1))));
        		}
        		if (row.getCell(2).toString() != null && row.getCell(2).toString() != "") {
        			atten.setPunchinTime(DateFormatter.getStringNewDateTime(formatter.formatCellValue(row.getCell(2))));
        		}
               // atten.setPunchinLocation(formatter.formatCellValue(row.getCell(3)));
               // atten.setLongitude(formatter.formatCellValue(row.getCell(4)));
               // atten.setLatitude(formatter.formatCellValue(row.getCell(5)));
                //atten.setPunchoutTime(formatter.formatCellValue(row.getCell(6)));
        		if (row.getCell(3).toString() != null && row.getCell(3).toString() != "") {
        			atten.setPunchoutTime(DateFormatter.getStringNewDateTime(formatter.formatCellValue(row.getCell(3))));
        		}
               // atten.setPunchOutLocation(formatter.formatCellValue(row.getCell(7)));
               // atten.setPunchOutLongitude(formatter.formatCellValue(row.getCell(8)));
               // atten.setPunchOutLatitude(formatter.formatCellValue(row.getCell(9)));
        		 atten.setShift(formatter.formatCellValue(row.getCell(4)));
                
                atten.setCreatedBy(userId);
                atten.setOrganization(orgName);
                atten.setOrgDivision(orgDivision);
                attendanceList.add(atten);
            }
        }
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("attendanceList add 11====="+attendanceList);
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-addUloadedAttendanceData", attendanceList,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();

		}

		String message = resp.getMessage();
		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addAttendaneData ends");
		return resp;
	}
	/*
	 * Employee autosearch
	 */
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "employee-attendance-get-employee-list" })
	public @ResponseBody JsonResponse<DropDownModel> EmployeeAutoSearchForAttendance(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : EmployeeAutoSearchForAttendance starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "employee-autosearch-forAttendance?id=" + searchValue+"&org="+org+"&orgDiv="+orgDiv,
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
		logger.info("Method : EmployeeAutoSearchForAttendance ends");
		return res;
	}
}
