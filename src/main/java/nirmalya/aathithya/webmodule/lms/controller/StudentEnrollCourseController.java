package nirmalya.aathithya.webmodule.lms.controller;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
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
		logger.info("url"+env.getMasterUrl() + "rest-save-enrollment-data?orgName=" + orgName
				+ "&orgDivision=" + orgDivision + "&userId=" + userId, data, JsonResponse.class);
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-save-enrollment-data?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :saveEnrollmentData ends");
		return resp;
	}


	@PostMapping("subscription-remove-enrollment-training")
public @ResponseBody Object removeEnrollmentTraining(HttpSession session, @RequestBody String data) {
    logger.info("Method : removeEnrollmentTraining starts, raw data: {}", data);

    JsonResponse<Object> resp = new JsonResponse<>();

    String orgName = (String) session.getAttribute("ORGANIZATION");
    String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

    // Admin/user performing action (best effort)
    String updatedBy = null;
    Object u1 = session.getAttribute("USER_ID");
    if (u1 == null) u1 = session.getAttribute("USERID");
    if (u1 == null) u1 = session.getAttribute("USER");
    if (u1 != null) updatedBy = String.valueOf(u1);

    try {
        JSONObject json = new JSONObject(data);

        String enrolledBy = json.optString("enrolledBy", "").trim(); // student id
        if (enrolledBy.isEmpty()) {
            resp.setCode("failed");
            resp.setMessage("enrolledBy (student ID) is missing");
            return resp;
        }

        String modifiedData = json.toString();

        // Build master URL: userId=enrolledBy, updatedBy=admin session user (optional)
        String url = env.getMasterUrl()
                + "rest-remove-enrollment-training?orgName=" + orgName
                + "&orgDivision=" + orgDivision
                + "&userId=" + enrolledBy;

        if (updatedBy != null && !updatedBy.trim().isEmpty()) {
            url += "&updatedBy=" + updatedBy.trim();
        }

        resp = restTemplate.postForObject(url, modifiedData, JsonResponse.class);

    } catch (JSONException e) {
        logger.error("Invalid JSON format", e);
        resp.setCode("failed");
        resp.setMessage("Invalid JSON format");
        return resp;
    } catch (Exception e) {
        logger.error("Error calling master service", e);
        resp.setCode("failed");
        resp.setMessage("Failed to remove training");
        return resp;
    }

    logger.info("Method : removeEnrollmentTraining ends");
    return resp;
}
}
