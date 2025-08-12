package nirmalya.aathithya.webmodule.training.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.training.model.ManageTrainingDetailsModel;
import nirmalya.aathithya.webmodule.training.model.ViewStudyMaterialsModel;

@Controller
@RequestMapping(value = "training")
public class ViewStudyMaterialsController {

	Logger logger = LoggerFactory.getLogger(ViewStudyMaterialsController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/assign-materials")
	public String viewEmployeeTrainingDetails(Model model, HttpSession session) {

		logger.info("Method : viewEmployeeTrainingDetails starts");

		logger.info("Method : viewEmployeeTrainingDetails ends");
		return "training/assign-training";
	}

	/*
	 * get all employee Lists to assign training
	 * 
	 */
	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/assign-materials-get-employee-listing")
	public @ResponseBody JsonResponse viewEmpolyeeListing(Model model, HttpSession session) {
		logger.info("Method : viewEmpolyeeListing starts");

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
			jsonResponse = restClient.getForObject(env.getTrainingUrl() + "view-employee-list?&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : viewEmpolyeeListing ends");
		return jsonResponse;
	}

	/*
	 * get Training Category Lists
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/assign-materials-training-category-list")
	public @ResponseBody JsonResponse<List<ManageTrainingDetailsModel>> getTrainingCategoryListModal(
			HttpSession session) {
		logger.info("Method : getTrainingCategoryListModal starts");

		JsonResponse<List<ManageTrainingDetailsModel>> resp = new JsonResponse<List<ManageTrainingDetailsModel>>();

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getTrainingUrl() + "get-training-category-dataListModal?organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : getTrainingCategoryListModal starts");
		return resp;
	}

	/*
	 * get study materials by category id
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/assign-materials-get-training-materials")
	public @ResponseBody JsonResponse<List<ManageTrainingDetailsModel>> getStudyMaterialByCategory(
			@RequestBody String index, BindingResult result, HttpSession session) {
		logger.info("Method : getStudyMaterialByCategory starts");

		JsonResponse<List<ManageTrainingDetailsModel>> resp = new JsonResponse<List<ManageTrainingDetailsModel>>();

		String organization = "";
		String orgDivision = "";
		String indexValue = index.substring(0, index.length() - 1);
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getTrainingUrl() + "getStudyMaterialByCategory?organization="
					+ organization + "&orgDivision=" + orgDivision + "&id=" + indexValue, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : getStudyMaterialByCategory starts");
		return resp;
	}

	/*
	 * this method is used to get all employee list by their joining date
	 * 
	 */

	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/assign-materials-get-employee-list")
	public @ResponseBody JsonResponse getEmployeeList(@RequestParam String frmDate, @RequestParam String toDate,
			HttpSession session) {
		logger.info("Method : viewEmpolyeeListing starts");

		JsonResponse jsonResponse = new JsonResponse();
		String organization = "";
		String orgDivision = "";

		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		String date1 = DateFormatter.inputDateFormat(frmDate, dateFormat);
		String date2 = DateFormatter.inputDateFormat(toDate, dateFormat);

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			jsonResponse = restClient
					.getForObject(
							env.getTrainingUrl() + "view-employee-list-bydt?date1=" + date1 + "&date2=" + date2
									+ "&organization=" + organization + "&orgDivision=" + orgDivision,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : viewEmpolyeeListing ends");
		return jsonResponse;

	}

	/*
	 * This post method is used to assign training category into a draft to all
	 * selected employees
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/assign-materials-save-employee-training")
	public @ResponseBody JsonResponse<List<ViewStudyMaterialsModel>> assignStudyMaterialToEmp(
			@RequestBody List<ViewStudyMaterialsModel> alldataTraining, BindingResult result, HttpSession session) {
		logger.info("Method : assignStudyMaterialToEmp111 starts");

		JsonResponse<List<ViewStudyMaterialsModel>> resp = new JsonResponse<List<ViewStudyMaterialsModel>>();

		String organization = "";
		String orgDivision = "";
		String userId = "";
		String message = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (ViewStudyMaterialsModel a : alldataTraining) {
			a.setOrg(organization);
			a.setOrgDiv(orgDivision);
			a.setEmpName(userId);
		}
		try {
			resp = restClient.postForObject(env.getTrainingUrl() + "rest-save-emp-training", alldataTraining,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		 message = resp.getMessage();
		if (message != null && message != "") {
			if (message.equals("Duplicate entry 'x' for  'tbl_assign_training.PRIMARY'")) {
				resp.setMessage("The category is already assigned to selected employee");
			} else {
				resp.setMessage("Something went wrong");
			}
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : assignStudyMaterialToEmp111 starts");
		return resp;
	}

	/*
	 * This method is used to get all employee list by their joining date
	 * 
	 */

	@SuppressWarnings({ "rawtypes" })
	@GetMapping("/assign-materials-assigned-emp-listing")
	public @ResponseBody JsonResponse getAssignedEmployeeList(HttpSession session) {
		logger.info("Method : getAssignedEmployeeList starts");

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

			jsonResponse = restClient.getForObject(env.getTrainingUrl() + "get-assigned-emp-list?organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getAssignedEmployeeList ends");
		return jsonResponse;

	}

	/*
	 * This post method is used to assign training category into a draft to all
	 * selected employees
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("/assign-materials-schedule-emp-training")
	public @ResponseBody JsonResponse<List<ViewStudyMaterialsModel>> scheduleStudyMaterialToEmp(
			@RequestBody List<ViewStudyMaterialsModel> alldataTraining, BindingResult result, HttpSession session) {
		logger.info("Method : scheduleStudyMaterialToEmp starts");

		JsonResponse<List<ViewStudyMaterialsModel>> resp = new JsonResponse<List<ViewStudyMaterialsModel>>();

		String organization = "";
		String orgDivision = "";
		String userId = "";
		String dateFormat = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");

			for (ViewStudyMaterialsModel a : alldataTraining) {
				a.setOrg(organization);
				a.setOrgDiv(orgDivision);
				a.setEmpName(userId);
				if (a.getScheduledFromDate() != null && a.getScheduledFromDate() != "") {
					a.setScheduledFromDate(DateFormatter.inputDateFormat(a.getScheduledFromDate(), dateFormat));
				}

				if (a.getScheduledToDate() != null && a.getScheduledToDate() != "") {
					a.setScheduledToDate(DateFormatter.inputDateFormat(a.getScheduledToDate(), dateFormat));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.postForObject(env.getTrainingUrl() + "rest-schedule-emp-training", alldataTraining,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();

		if (message != null && message != "") {
			if (message.equals("Duplicate entry 'x' for  'PRIMARY'")) {
				resp.setMessage("The category is already assigned to this employee");
			} else {
				resp.setMessage("Something went wrong");
			}
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : scheduleStudyMaterialToEmp starts");
		return resp;
	}

}
