package nirmalya.aathithya.webmodule.lms.controller;

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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping("student")
public class StudentEnrollCourseController {
	Logger logger = LoggerFactory.getLogger(StudentEnrollCourseController.class);

	@Autowired
	EnvironmentVaribles env;
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("enroll-courses")
	public String enrollCoursesDetails(Model model, HttpSession session) {
		logger.info("Mothod:view enrollCoursesDetails page started...");

		logger.info("Mothod: view enrollCoursesDetails page ends...");
		return "lms/enroll-course";
	}

	//
	@SuppressWarnings("unchecked")
	@GetMapping("enroll-course-view")
	public @ResponseBody Object viewEnrollCourses(HttpSession session) {
		logger.info("Method :viewEnrollCourses starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewEnrollCourses?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&userId=" + userId,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewEnrollCourses ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("save-enrollment-details")
	public @ResponseBody Object saveEnrollmentData(HttpSession session, @RequestBody String data) {
		logger.info("Method :saveEnrollmentData starts");

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
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-save-enrollment-data?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :saveEnrollmentData ends");
		return resp;
	}
}
