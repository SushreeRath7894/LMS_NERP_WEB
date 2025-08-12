package nirmalya.aathithya.webmodule.master.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.AttendanceModel;
import nirmalya.aathithya.webmodule.master.model.EmployeeBonusModel;

@Controller
@RequestMapping(value = "master/")
public class EmployeeBonusController {
	
	Logger logger = LoggerFactory.getLogger(EmployeeBonusController.class);
	@Autowired
	RestTemplate rest;
	@Autowired
	EnvironmentVaribles env;
	@GetMapping("employee-bonus-exgratia")
	public String employeeBonusExgratia(Model model, HttpSession session) {
		logger.info("Method : employeeBonusExgratia starts");

		String userId = "";
		String organization=""; 
		String orgDivision="";
		try {
			userId = (String) session.getAttribute("USER_ID"); 
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}	
		try {
			DropDownModel[] shift = rest.getForObject(
					env.getMasterUrl() + "getShiftLists?org=" + orgDivision + "&orgDiv=" + organization + "&userId=" + userId,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		model.addAttribute("userId", userId);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		
		logger.info("Method : employeeBonusExgratia ends");
		return "master/employee_bonus_exgratia";
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping("employee-bonus-exgratia-view")
	public @ResponseBody Object viewEmployeeBonusExgratia(@RequestParam String fromDate, String toDate, HttpSession session) {

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
			resp = rest.getForObject(
					env.getMasterUrl() + "viewEmployeeBonusExgratia?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewEmployeeBonusExgratia ends  ");
		return resp;
	}
	@PostMapping("employee-bonus-exgratia-upload-file")
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
	@SuppressWarnings({ "unchecked" })
	@PostMapping("employee-bonus-exgratia-save-excelData")
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
			resp = rest.postForObject(env.getMasterUrl() +
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
}
