package nirmalya.aathithya.webmodule.lms.controller;

 import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

 import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
 
@Controller
@RequestMapping("academic")
public class AdminCommonController {
	Logger logger = LoggerFactory.getLogger(lmsCommonController.class);

	@Autowired
	EnvironmentVaribles env;
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("admin-dashboard")
	public String adminDashboard(Model model, HttpSession session) {
		logger.info("Mothod:view admin dashboard page started...");

		logger.info("Mothod: view admin dashboard page ends...");
		return "lms/admin-dashboard";
	}
	
	@GetMapping("public-batches")
	public String publicBatches(Model model, HttpSession session) {
		logger.info("Mothod:view public batches page started...");

		logger.info("Mothod: view public batches page ends...");
		return "lms/lms-public-batches";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("admin-dashboard-getadminAllHeadCount")
	public @ResponseBody Object getadminAllHeadCount(HttpSession session) {

		logger.info("Method :getAllHeadCount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String org = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			String url = env.getHisUrl()+ "rest-getadminAllHeadCount?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&userId=" + userId;
			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getadminAllHeadCount ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("admin-dashboard-add-coupon")
	public @ResponseBody Object saveCouponDetails(
	        HttpSession session,
	        @RequestBody Map<String, Object> payload) {

	    logger.info("Method : saveCouponDetails starts");
	    JsonResponse<Object> resp = new JsonResponse<Object>();

	    try {
	        // 1️⃣ Fetch session attributes
	        String userId = (String) session.getAttribute("USER_ID");
	        String org = (String) session.getAttribute("ORGANIZATION");
	        String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

	        Map<String, Object> sessionData = new HashMap<>();
	        sessionData.put("orgName", org);
	        sessionData.put("orgDivision", orgDiv);
	        sessionData.put("loginUserId", userId);

	        payload.putAll(sessionData);  

	        logger.info("📦 Final payload to send: {}", payload);

	        String url = env.getHisUrl() + "rest-add-coupon";
	        resp = restTemplate.postForObject(url, payload, JsonResponse.class);
 

	    } catch (Exception e) {
	        logger.error("Error in saveCouponDetails:", e);
  	    }

	    logger.info("Method : saveCouponDetails ends");
	    return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("admin-dashboard-delete-coupon")
	public @ResponseBody Object deleteCoupon(
	        HttpSession session,
	        @RequestBody Map<String, Object> payload) {

	    logger.info("Method : deleteCoupon starts");
	    JsonResponse<Object> resp = new JsonResponse<Object>();

	    try {
	        // 1️⃣ Fetch session attributes
	        String userId = (String) session.getAttribute("USER_ID");
	        String org = (String) session.getAttribute("ORGANIZATION");
	        String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

	        Map<String, Object> sessionData = new HashMap<>();
	        sessionData.put("orgName", org);
	        sessionData.put("orgDivision", orgDiv);
	        sessionData.put("loginUserId", userId);

	        payload.putAll(sessionData);  

	        logger.info("📦 Final payload to send: {}", payload);

	        String url = env.getHisUrl() + "rest-delete-coupon";
	        resp = restTemplate.postForObject(url, payload, JsonResponse.class);
 

	    } catch (Exception e) {
	        logger.error("Error in deleteCoupon:", e);
  	    }

	    logger.info("Method : deleteCoupon ends");
	    return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("admin-dashboard-get-recent-courses")
	public @ResponseBody Object getRecentPurchaseCourses(HttpSession session) {

		logger.info("Method :getRecentPurchaseCourses starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String org = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			String url = env.getHisUrl()+ "rest-get-reccent-courses?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&userId=" + userId;
			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getRecentPurchaseCourses ends" + resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("admin-dashboard-get-all-excel-data")
	public @ResponseBody Object getExcelData(HttpSession session,@RequestParam String id) {

		logger.info("Method :getExcelData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
 		String org = "";
		String orgDiv = "";
		try {
 			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			String url = env.getHisUrl()+ "rest-get-excel-data?orgName=" + org + "&orgDivision=" + orgDiv
					+ "&id=" + id;
			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getExcelData ends" + resp);

		return resp;
	}
	
	@GetMapping("instructor-dashboard")
	public String facultyDashboard(Model model, HttpSession session) {
		logger.info("Mothod:view faculty dashboard page started...");

		logger.info("Mothod: view faculty dashboard page ends...");
		return "lms/faculty-dashboard";
	}

	/*
	 * @GetMapping("courses") public String academicCourses(Model model, HttpSession
	 * session) { logger.info("Mothod:view courses page started...");
	 * 
	 * logger.info("Mothod: view courses page ends..."); return
	 * "lms/academic-courses"; }
	 */

	@GetMapping("users-management")
	public String userManagement(Model model, HttpSession session) {
		logger.info("Mothod:view user-management page started...");

		logger.info("Mothod: view user-management page ends...");
		return "lms/user-management";
	}

	@GetMapping("certificate-builder")
	public String certificateBuilder(Model model, HttpSession session) {
		logger.info("Mothod:view certificateBuilder page started...");

		DropDownModel[] course = restTemplate.getForObject(env.getMasterUrl() + "/courseList",
				DropDownModel[].class);
		List<DropDownModel> courseList = Arrays.asList(course);
		model.addAttribute("courseList", courseList);

		logger.info("Mothod: certificateBuilder page ends...");
		return "lms/certificate-builder";
	}
	
	
	@GetMapping("subscription")
	public String subscription(Model model, HttpSession session) {
		logger.info("Mothod:view user-management page started...");

		logger.info("Mothod: view user-management page ends...");
		return "lms/lms-subscription";
	}
	
	//view
	@SuppressWarnings("unchecked")
	@GetMapping("subscription-student-view")
	public @ResponseBody Object viewStudent(HttpSession session,@RequestParam String id) {
		logger.info("Method :viewStudent starts"+id);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
logger.info("ss"+env.getHisUrl() + "rest-subscription-student-view?orgName=" + orgName + "&orgDivision="
		+ orgDivision + "&id=" + id);		
resp = restTemplate.getForObject(env.getHisUrl() + "rest-subscription-student-view?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method :viewStudent ends"+resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("subscription-get-course-details")
	public @ResponseBody Object getTheCourseDetails(HttpSession session,@RequestParam String enrollId) {
		logger.info("Method :getTheCourseDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-editCourseTrainingDetails?Id=" + enrollId + "&organization="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method :getTheCourseDetails ends"+resp);
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("public-batches-get-public-batches")
	public @ResponseBody Object getPublicBatches(HttpSession session) {
		logger.info("Method :getPublicBatches starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
 

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewPublicBatches?orgName=" + orgName + "&orgDivision=" + orgDivision +"&id="+userId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method :getPublicBatches ends"+resp);
		return resp;
	}
	//
	@SuppressWarnings("unchecked")
	@GetMapping("subscription-student-course-enable")
	public @ResponseBody JsonResponse<Object> enableCourse(@RequestParam String studentId, @RequestParam String status) {
	    logger.info("Method : enableCourse starts");
	    JsonResponse<Object> response = new JsonResponse<>();
	
	    try {
	        response = restTemplate.getForObject(
	            env.getHisUrl() + "rest-subscription-student-course-enable?id=" + studentId + "&status=" + status,
	            JsonResponse.class
	        );
	    } catch (RestClientException e) {
	        logger.error("Error calling REST service: ", e);
	        response.setCode("500");
	        response.setMessage("Exception occurred: " + e.getMessage());
	        return response;
	    }

	    logger.info("Method : enableCourse ends");
	    return response;
	}
	@SuppressWarnings("unchecked")
	@PostMapping("public-batches-delete")
	public @ResponseBody JsonResponse<Object> deletePublicBatches(
	        HttpSession session,
	        @RequestBody Map<String, Object> payload) {

	    logger.info("Method : deletePublicBatches starts");
	    JsonResponse<Object> response = new JsonResponse<>();

	    try {
	        String orgName = (String) session.getAttribute("ORGANIZATION");
	        String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	        String userId = (String) session.getAttribute("USER_ID");

	        // Add session data
	        payload.put("orgName", orgName);
	        payload.put("orgDivision", orgDivision);
	        payload.put("loginUserId", userId);

	        logger.info("📦 Final payload to send: {}", payload);

	        String url = env.getHisUrl() + "rest-delete-public-batches";
	        response = restTemplate.postForObject(url, payload, JsonResponse.class);

	    } catch (Exception e) {
	        logger.error("Error calling REST service:", e);
	    }

	    logger.info("Method : deletePublicBatches ends");
	    return response;
	}


}
