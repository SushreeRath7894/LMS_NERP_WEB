package nirmalya.aathithya.webmodule.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.EmployeeCCRModel;

@Controller
@RequestMapping(value = "employee/")
public class AttendanceByDepartmentController {
	Logger logger = LoggerFactory.getLogger(AttendanceByDepartmentController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping("attendance-by-dept")
	public String attendanceByDept(Model model, HttpSession session) {

		logger.info("Method : attendanceByDept starts");
		
		logger.info("Method : attendanceByDept ends");
		return "employee/attendance-by-dept";
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping("attendance-by-dept-view")
	public @ResponseBody Object viewEmpolyee(Model model, HttpSession session) {

			logger.info("Method :viewEmpolyee starts");
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
				resp = restClient.getForObject(
						env.getEmployeeUrl() + "viewAttandance?org=" + org + "&orgDiv=" + orgDiv,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Method :viewAttandance ends  ");
			return resp;
		}
	@SuppressWarnings("unchecked")
	@PostMapping("attendance-by-dept-add")
	public @ResponseBody JsonResponse<Object> addAttendanceByDepartment(HttpSession session,
	        @RequestBody List<Map<String, String>> data) {
	    logger.info("Method: addAttendanceByDepartment starts"+data);
	    JsonResponse<Object> resp = new JsonResponse<>();

	    String userId = "";
	    String organization = "";
	    String orgDivision = "";
	    String dateFormat = "";
	    try {
	        userId = (String) session.getAttribute("USER_ID");
	        organization = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	        dateFormat = (String) (session).getAttribute("DATEFORMAT");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    for (Map<String, String> item : data) {
	        // Create a new object to represent the data
	        Map<String, Object> vitamin = new HashMap<>();
	        vitamin.put("empId", item.get("empId"));
	        vitamin.put("date", DateFormatter.inputDateFormat(item.get("date"), dateFormat));
	        vitamin.put("time", item.get("time"));
	        vitamin.put("type", item.get("type"));

	        // Set additional fields
	        vitamin.put("createdBy", userId);
	        vitamin.put("organization", organization);
	        vitamin.put("orgDivision", orgDivision);
	        logger.info("Method: addAttendanceByDepartment vitamin-----"+vitamin);
	        // Perform the operation using the extracted data
	        try {
	            JsonResponse<Object> response = restClient.postForObject(env.getEmployeeUrl() + "addAttendanceByDepartment", vitamin, JsonResponse.class);
	            if (response.getMessage() != null && !response.getMessage().isEmpty()) {
	                resp.setCode(response.getMessage());
	                resp.setMessage("Success");
	            } else {
	                resp.setMessage("Unsuccess");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            resp.setMessage("Unsuccess");
	        }
	    }

	    logger.info("Method: addAttendanceByDepartment ends");
	    return resp;
	}

}
