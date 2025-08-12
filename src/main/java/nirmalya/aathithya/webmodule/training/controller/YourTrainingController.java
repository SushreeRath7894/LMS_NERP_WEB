package nirmalya.aathithya.webmodule.training.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = "training")
public class YourTrainingController {

	Logger logger = LoggerFactory.getLogger(YourTrainingController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/your-trainings")
	public String viewEmployeeTrainingDetails(Model model, HttpSession session) {
		logger.info("Method : viewEmployeeTrainingDetails starts");

		logger.info("Method : viewEmployeeTrainingDetails ends");
		return "training/your-training";
	}

	@GetMapping("/your-trainings-learn")
	public String viewEmployeeTrainingDetailsLearn(Model model, HttpSession session, @RequestParam String id,
			@RequestParam String sId) {
		logger.info("Method : viewEmployeeTrainingDetailsLearn starts");

		logger.info("Method : viewEmployeeTrainingDetailsLearn ends");
		return "training/learn";
	}

	/*
	 * get all employee Lists to assign training
	 * 
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/your-trainings-get-employee-listing")
	public @ResponseBody JsonResponse<Object> viewEmpolyeeListing(@RequestParam String state, Model model,
			HttpSession session) {
		logger.info("Method : viewEmpolyeeListing starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient
					.getForObject(
							env.getTrainingUrl() + "view-assigned-employee-list?&organization=" + organization
									+ "&orgDivision=" + orgDivision + "&state=" + state + "&userId=" + userId,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewEmpolyeeListing ends");
		return jsonResponse;
	}

	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/your-trainings-category-details")
	public @ResponseBody JsonResponse getAssignedCategoryDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : getAssignedCategoryDetails starts");

		JsonResponse jsonResponse = new JsonResponse();

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			jsonResponse = restClient.getForObject(env.getTrainingUrl() + "get-category-doc-details?organization="
					+ organization + "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getAssignedEmployeeList ends");
		return jsonResponse;

	}

	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/your-trainings-category-items")
	public @ResponseBody JsonResponse getCategoryItemsList(@RequestParam String id, HttpSession session) {
		logger.info("Method : getCategoryItemsList starts");

		JsonResponse jsonResponse = new JsonResponse();

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getTrainingUrl() + "get-category-doc-list?organization="
					+ organization + "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getAssignedCategoryDetails ends");
		return jsonResponse;

	}

	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/your-trainings-save-start")
	public @ResponseBody JsonResponse setTrainingStartData(@RequestParam String id, HttpSession session) {
		logger.info("Method : setTrainingStartData starts");

		JsonResponse jsonResponse = new JsonResponse();

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getTrainingUrl() + "set-training-start-data?organization="
					+ organization + "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : setTrainingStartData ends");
		return jsonResponse;

	}

	/*
	 * Post Training count
	 * 
	 */

	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/your-trainings-count-save")
	public @ResponseBody JsonResponse saveCount(@RequestParam String count, @RequestParam String sId,
			HttpSession session) {
		logger.info("Method : saveCount starts");

		JsonResponse jsonResponse = new JsonResponse();

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getTrainingUrl() + "training-count-save?organization="
					+ organization + "&orgDivision=" + orgDivision + "&count=" + count + "&sId=" + sId,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveCount ends");
		return jsonResponse;

	}

	/*
	 * Post training finish
	 * 
	 */

	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/your-trainings-finish-training")
	public @ResponseBody JsonResponse finishTraining(@RequestParam String schId, HttpSession session) {
		logger.info("Method : finishTraining starts");

		JsonResponse jsonResponse = new JsonResponse();

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getTrainingUrl() + "rest-finish-training?organization="
					+ organization + "&orgDivision=" + orgDivision + "&schId=" + schId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String message = jsonResponse.getMessage();

		if (message != null && message != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : finishTraining ends");
		return jsonResponse;

	}
}
