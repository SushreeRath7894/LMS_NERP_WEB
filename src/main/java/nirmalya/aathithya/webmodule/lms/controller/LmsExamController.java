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
	public @ResponseBody JsonResponse<Object> saveQuiz(HttpSession session,
	        @RequestParam("quizId") String quizId,
	        @RequestParam("quiz_code") String quizCode,
	        @RequestParam("section_title") String sectionTitle,
	        @RequestParam("question_text") String questionText,
	        @RequestParam("option_a") String optionA,
	        @RequestParam("option_b") String optionB,
	        @RequestParam("option_c") String optionC,
	        @RequestParam("option_d") String optionD,
	        @RequestParam("rationale_a") String rationaleA,
	        @RequestParam("rationale_b") String rationaleB,
	        @RequestParam("rationale_c") String rationaleC,
	        @RequestParam("rationale_d") String rationaleD,
	        @RequestParam("right_answer") String rightAnswer,
	        @RequestParam("syllabus_ref") String syllabusRef) {

	    logger.info("Method : saveQuiz starts");

	    JsonResponse<Object> resp = new JsonResponse<Object>();
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
	        Map<String, Object> quizData = new HashMap<>();
	        quizData.put("quizId", quizId);
	        quizData.put("quiz_code", quizCode);
	        quizData.put("section_title", sectionTitle);
	        quizData.put("question_text", questionText);
	        quizData.put("option_a", optionA);
	        quizData.put("option_b", optionB);
	        quizData.put("option_c", optionC);
	        quizData.put("option_d", optionD);
	        quizData.put("rationale_a", rationaleA);
	        quizData.put("rationale_b", rationaleB);
	        quizData.put("rationale_c", rationaleC);
	        quizData.put("rationale_d", rationaleD);
	        quizData.put("right_answer", rightAnswer);
	        quizData.put("syllabus_ref", syllabusRef);

	        // --- Log JSON Payload ---
	        ObjectMapper mapper = new ObjectMapper();
	        logger.info("JSON Payload: {}", mapper.writeValueAsString(quizData));

	        // --- Call REST API ---
	        resp = restClient.postForObject(
	                env.getMasterUrl() + "rest-quiz-config-add?userId=" + userId
	                        + "&org=" + orgName + "&orgDiv=" + orgDivision,
	                quizData, JsonResponse.class);

	    } catch (Exception e) {
	        logger.error("Error saving quiz", e);
	        resp.setMessage("Error saving quiz: " + e.getMessage());
	        resp.setCode("Failed");
	    }

	    logger.info("Method : saveQuiz ends");
	    return resp;
	}

}
