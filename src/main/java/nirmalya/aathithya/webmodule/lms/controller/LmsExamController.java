package nirmalya.aathithya.webmodule.lms.controller;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping("academic")
public class LmsExamController {
	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	Logger logger = LoggerFactory.getLogger(LmsExamController.class);
	
	
	@GetMapping(value = { "/quiz-config" })
	public String quizconfig(Model model, HttpSession session) {
		logger.info("Mothod:view quizconfig starts...");
	

		logger.info("Mothod: view quizconfig ends...");
		return "lms/lms-quiz.html";
	}
	
	
	// Web Controller Method
	@SuppressWarnings("unchecked")
	@PostMapping("quiz-config-add")
	public @ResponseBody JsonResponse<Object> saveQuizBulk(HttpSession session,
	        @RequestBody List<Map<String, Object>> quizzes) {  // 👈 root is the array now
	    logger.info("Method : saveQuizBulk starts");

	    JsonResponse<Object> resp = new JsonResponse<>();

	    String userId = "";
	    String orgName = "";
	    String orgDivision = "";

	    try {
	        userId = (String) session.getAttribute("USER_ID");
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        logger.error("Error getting session attributes", e);
	    }

	    try {
	        if (quizzes == null || quizzes.isEmpty()) {
	            resp.setCode("Failed");
	            resp.setMessage("No quiz data found in request.");
	            return resp;
	        }

	        logger.info("📦 Received {} quiz records", quizzes.size());

	        // --- Build request payload for REST call ---
	        Map<String, Object> bodyToSend = new HashMap<>();
	        bodyToSend.put("userId", userId);
	        bodyToSend.put("org", orgName);
	        bodyToSend.put("orgDiv", orgDivision);
	        bodyToSend.put("quizzes", quizzes);

	        ObjectMapper mapper = new ObjectMapper();
	        logger.debug("🔹 Forwarding payload: {}", mapper.writeValueAsString(bodyToSend));

	        resp = restClient.postForObject(
	                env.getMasterUrl() + "rest-quiz-config-add",
	                bodyToSend,
	                JsonResponse.class);

	    } catch (Exception e) {
	        logger.error("Error saving quizzes", e);
	        resp.setCode("Failed");
	        resp.setMessage("Error saving quizzes: " + e.getMessage());
	    }

	    logger.info("Method : saveQuizBulk ends");
	    return resp;
	}


	    /**
	     * View All Quiz Data
	     */
	    @SuppressWarnings("unchecked")
	    @GetMapping("quiz-config-view")
	    public @ResponseBody Object viewQuizConfig(HttpSession session) {
	        logger.info("Method : viewQuizConfig starts");
	        JsonResponse<Object> resp = new JsonResponse<Object>();

	        try {
	            String orgName = (String) session.getAttribute("ORGANIZATION");
	            String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

	            resp = restClient.getForObject(
	                    env.getMasterUrl() + "rest-viewQuizConfig?orgName=" + orgName + "&orgDivision=" + orgDivision,
	                    JsonResponse.class);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        if (resp.getMessage() == "" && resp.getMessage() == null) {
	            resp.setCode(resp.getMessage());
	            resp.setMessage("Unsuccess");
	        } else {
	            resp.setMessage("Success");
	        }
	        logger.info("Method : viewQuizConfig ends"+resp);
	        return resp;
	    }

	    /**
	     * Edit Quiz Details
	     */
	    @SuppressWarnings("unchecked")
	    @GetMapping("quiz-config-edit")
	    public @ResponseBody Object editQuizConfig(@RequestParam String id,@RequestParam Integer id2, HttpSession session) {
	        logger.info("Method : editQuizConfig starts with Id: " + id2);
	        JsonResponse<Object> resp = new JsonResponse<Object>();

	        try {
	            String orgName = (String) session.getAttribute("ORGANIZATION");
	            String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
logger.info("url==="+
        env.getMasterUrl() + "rest-editQuizConfig?id=" + id + "&id2=" +id2 + "&organization=" + orgName + "&orgDivision=" + orgDivision,
        JsonResponse.class);

	            resp = restClient.getForObject(
	                    env.getMasterUrl() + "rest-editQuizConfig?id=" + id + "&id2=" +id2 + "&organization=" + orgName + "&orgDivision=" + orgDivision,
	                    JsonResponse.class);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        if (resp.getMessage() == "" && resp.getMessage() == null) {
	            resp.setCode(resp.getCode());
	            resp.setMessage(resp.getMessage());
	        } else {
	            resp.setMessage(resp.getMessage());
	        }

	        logger.info("Method : editQuizConfig ends");
	        return resp;
	    }
	

	
}
