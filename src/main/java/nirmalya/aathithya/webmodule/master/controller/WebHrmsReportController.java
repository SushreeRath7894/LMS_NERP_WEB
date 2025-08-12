package nirmalya.aathithya.webmodule.master.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Collectors;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

	@Controller
	@RequestMapping(value = { "" })
	public class WebHrmsReportController {

		Logger logger = LoggerFactory.getLogger(WebHrmsReportController.class);

		@Autowired
		RestTemplate restTemplate;

		@Autowired
		EnvironmentVaribles env;
		
		@Autowired
		PdfGeneratatorUtil pdfGeneratorUtil;

		@GetMapping(value = { "/hrmsReport" })
		public String hrmsReport(Model model, HttpSession session) {
			logger.info("Method : hrmsReport starts");
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
			logger.info("Method : hrmsReport ends");
			return "hrms-report/report-list";
		}
		
		
		
	@SuppressWarnings("unchecked")
	@GetMapping("/hrmsReport-get-data")
	public @ResponseBody JsonResponse<Object> getData(HttpSession session) {
		logger.info("Method : getData starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getData?org="+organization+"&orgDiv="+orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getData ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("get-attendance-list-data")
	public @ResponseBody JsonResponse<Object> getAttendanceReport(@RequestParam String fromDate, @RequestParam String toDate, HttpSession session) {
		logger.info("Method : getAttendanceReport starts"+fromDate + toDate);
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String organization = "";
		String orgDivision = "";
		
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-getAttendanceReport?org="+organization+"&orgDiv="+orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : getAttendanceReport ends");
		return resp;
	}

//
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("get-attendance-Pdf")
	public void getAttendancePdf(HttpServletResponse response, HttpSession session,
	                             @RequestParam("fromDate") String fromDate,
	                             @RequestParam("toDate") String toDate) {

	    logger.info("Method : getAttendancePdf starts");

	    String orgName = "";
	    String orgDivision = "";

	    try {
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        logger.error("Session attributes missing: " + e.getMessage());
	    }

	    JsonResponse<Object> resp = new JsonResponse<>();
	    ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> data = new HashMap<>();

	    try {
	        // Call backend API
	        resp = restTemplate.getForObject(
	            env.getMasterUrl() + "rest-pdf-attendance?orgName=" + orgName +
	            "&orgDivision=" + orgDivision + 
	            "&fromDate=" + fromDate + 
	            "&toDate=" + toDate, 
	            JsonResponse.class
	        );
	    } catch (Exception e) {
	        logger.error("API call error: " + e.getMessage());
	        e.printStackTrace();
	    }

	    try {
	        Map<String, Object> responseData = mapper.readValue(
	            resp.getBody().toString(),
	            new TypeReference<Map<String, Object>>() {}
	        );

	        logger.info("Response parsed: " + responseData);

	     //   data.put("employees", responseData.get("employees"));
	        data.put("logo", responseData.get("logo"));
	        data.put("orgName", orgDivision); // You can use orgName if needed
	        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate startDate = LocalDate.parse(fromDate, formatter);
	        YearMonth yearMonth = YearMonth.from(startDate);
	        int daysInMonth = yearMonth.lengthOfMonth(); // 28, 29, 30, 31

	     
	        List<Integer> days = IntStream.rangeClosed(1, daysInMonth)
	                                      .boxed()
	                                      .collect(Collectors.toList());
	        data.put("daysInMonth", days);
	        
	        
	        
	        List<Map<String, Object>> rawEmployees = (List<Map<String, Object>>) responseData.get("employees");
	        List<Map<String, Object>> processedEmployees = new ArrayList<>();

	        for (Map<String, Object> emp : rawEmployees) {
	            Map<String, Object> processed = new HashMap<>(emp);
	            List<String> statuses = new ArrayList<>();
	            List<String> punchIns = new ArrayList<>();
	            List<String> punchOuts = new ArrayList<>();
	            List<String> lateBy = new ArrayList<>();

	            for (int i = 1; i <= 31; i++) {
	                statuses.add((String) emp.getOrDefault("sts_" + i, ""));
	                punchIns.add((String) emp.getOrDefault("punchIn_" + i, ""));
	                punchOuts.add((String) emp.getOrDefault("punchOut_" + i, ""));
	                lateBy.add((String) emp.getOrDefault("lateBy_" + i, ""));
	            }

	            processed.put("statuses", statuses);
	            processed.put("punchIns", punchIns);
	            processed.put("punchOuts", punchOuts);
	            processed.put("lateBy", lateBy);
	            processedEmployees.add(processed);
	        }

	        data.put("employees", processedEmployees);


	    } catch (IOException e) {
	        logger.error("Error parsing JSON response: " + e.getMessage());
	        e.printStackTrace();
	    }

	    // Generate PDF
	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "inline; filename=Attendance-Report.pdf");

	    try {
	        File file = pdfGeneratorUtil.createPdf("hrms-report/attendance-pdf.html", data);
	        try (InputStream in = new FileInputStream(file)) {
	            byte[] fileData = IOUtils.toByteArray(in);
	            response.setContentLength(fileData.length);
	            response.getOutputStream().write(fileData);
	            response.getOutputStream().flush();
	        }
	    } catch (Exception e) {
	        logger.error("PDF generation error: " + e.getMessage());
	        e.printStackTrace();
	    }

	    logger.info("Method : getAttendancePdf ends");
	}

}
