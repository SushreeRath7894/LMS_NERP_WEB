package nirmalya.aathithya.webmodule.projects.controller;

import java.util.Arrays;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.projects.model.BudgetEstimationWebModel;
import nirmalya.aathithya.webmodule.projects.model.ExtraExpenseModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCategoryModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel;

@Controller
@RequestMapping(value = "projects")
public class JobCardProjectController {
	Logger logger = LoggerFactory.getLogger(JobCardProjectController.class);

	@Autowired
	RestTemplate restClient;
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	

	@GetMapping(value = { "jobcard" })
	public String jobcard(Model model, HttpSession session) {
		logger.info("Method : jobcard starts");
		try {
			DropDownModel[] budgetCatList = restClient.getForObject(env.getProjects() + "get-budgetCategoryList",
					DropDownModel[].class);
			List<DropDownModel> budgetCategoryList = Arrays.asList(budgetCatList);
			model.addAttribute("budgetCategoryList", budgetCategoryList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] projectCrtList = restClient.getForObject(env.getProjects() + "get-projectList",
					DropDownModel[].class);
			List<DropDownModel> projectList = Arrays.asList(projectCrtList);
			model.addAttribute("projectList", projectList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		/*
		 * try { DropDownModel[] dropDownModel =
		 * restClient.getForObject(env.getProjects() + "getExtraExpenses",
		 * DropDownModel[].class); List<DropDownModel> ExtraExpenseList =
		 * Arrays.asList(dropDownModel); model.addAttribute("ExtraExpenseList",
		 * ExtraExpenseList); } catch (RestClientException e) { e.printStackTrace(); }
		 */

		try {
			DropDownModel[] uom = restClient.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : jobcard ends");
		return "projects/jobCard.html";
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("jobcard-SubCategoryList")
	public @ResponseBody JsonResponse<Object> getsubCatList(@RequestParam String id) {
		logger.info("Method : getsubCatList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "rest-get-budgetSubCategoryList?id=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("state" + res);
		logger.info("Method : getsubCatList ends");
		return res;
	}

//
	// add budget
	@SuppressWarnings("unchecked")
	@PostMapping("jobcard-add")
	public @ResponseBody JsonResponse<Object> addBudget(@RequestBody BudgetEstimationWebModel budget,
			HttpSession session) {

		logger.info("Method : addBudget starts" + budget);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		System.out.println("web addBudget ======================" + budget);

		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		budget.setCreatedBy(userId);
		budget.setOrganizationName(organization);
		budget.setOrganizationDivision(orgDivision);

		try {

			resp = restClient.postForObject(env.getProjects() + "rest-jobcard-add", budget,
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(resp.getMessage());
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : addBudget ends");

		return resp;
	}
//
//view-budget

	@SuppressWarnings("unchecked")
	@GetMapping("jobcard-view")
	public @ResponseBody Object estimateBudgetView(HttpSession session, @RequestParam String id) {

		logger.info("Method :estimateBudgetView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-jobcard-view" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectCreation--" + resp);
		logger.info("Method :estimateBudgetView ends");

		return resp;
	}

	// view-budget project

	@SuppressWarnings("unchecked")
	@GetMapping("jobcard-view-project")
	public @ResponseBody Object viewProjectCreation(HttpSession session) {

		logger.info("Method :viewProjectCreation starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-view-project-creation" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectCreation--" + resp);
		logger.info("Method :viewProjectCreation ends");

		return resp;
	}

	// view-budget-edit
	@SuppressWarnings("unchecked")
	@GetMapping("jobcard-edit")
	public @ResponseBody Object estimateBudgetEdit(HttpSession session, @RequestParam String id) {

		logger.info("Method :estimateBudgetEdit starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-jobcard-edit" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("estimateBudgetEdit--" + resp);
		logger.info("Method :estimateBudgetEdit ends");

		return resp;
	}

// delete budget
	@SuppressWarnings("unchecked")
	@GetMapping("jobcard-delete")
	public @ResponseBody JsonResponse<Object> estimateBudgetDelete(@RequestParam String id, HttpSession session) {
		logger.info("Method : estimateBudgetDelete function starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "rest-jobcard-delete?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : estimateBudgetDelete function Ends");

		System.out.println("Response" + res);
		return res;
	}

	
	@SuppressWarnings("unchecked")
	@GetMapping("jobcard-sink")
	public @ResponseBody JsonResponse<Object> estimateSink(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : estimateSink function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-estimateSink?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : estimateSink function Ends"+res);

		return res;
	}
	
	
}
