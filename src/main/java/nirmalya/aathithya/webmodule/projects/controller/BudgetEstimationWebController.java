package nirmalya.aathithya.webmodule.projects.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.projects.model.BudgetEstimationWebModel;
import nirmalya.aathithya.webmodule.projects.model.BudgetEstimationWebSubModel;
import nirmalya.aathithya.webmodule.projects.model.ExtraExpenseModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCategoryModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseQuotationModel;

@Controller
@RequestMapping(value = "projects/")
public class BudgetEstimationWebController {

	Logger logger = LoggerFactory.getLogger(BudgetMasterWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "view-budget-estimate" })
	public String estimateBudget(Model model, HttpSession session) {
		logger.info("Method : manageBudget starts");

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

		logger.info("Method : manageBudget ends");
	//	return "projects/budgetEstimate";
		return "projects/budget-estimate-new";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-budget-estimate-SubCategoryList")
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
		logger.info("Method : getsubCatList ends");
		return res;
	}
//
	// add budget
	@SuppressWarnings("unchecked")
	@PostMapping("view-budget-estimate-add")
	public @ResponseBody JsonResponse<Object> addBudget(@RequestBody BudgetEstimationWebModel budget,
			HttpSession session) {

		logger.info("Method : addBudget starts"+budget);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		
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

			resp = restClient.postForObject(env.getProjects() + "rest-view-budget-estimate-add", budget,
					JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	@GetMapping("view-budget-estimate-view")
	public @ResponseBody Object estimateBudgetView(HttpSession session,@RequestParam String id) {

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
			resp = restClient.getForObject(env.getProjects() + "rest-view-budget-estimate-view"
					+ "?userid=" + userId+"&org="+organization+"&div="+orgDivision+"&id="+id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectCreation--" + resp);
		logger.info("Method :estimateBudgetView ends");

		return resp;
	}

	// view-budget project
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-budget-estimate-view-project")
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
			resp = restClient.getForObject(env.getProjects() + "rest-view-project-creation"
					+ "?userid=" + userId+"&org="+organization+"&div="+orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectCreation--" + resp);
		logger.info("Method :viewProjectCreation ends");

		return resp;
	}

	// view-budget-edit
	@SuppressWarnings("unchecked")
	@GetMapping("view-budget-estimate-edit")
	public @ResponseBody Object estimateBudgetEdit(HttpSession session,@RequestParam String id,@RequestParam String id2) {
	
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
			resp = restClient.getForObject(env.getProjects() + "rest-view-budget-estimate-edit"
					+ "?userid=" + userId+"&org="+organization+"&div="+orgDivision+"&id="+id +"&id2="+id2,
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
		logger.info("estimateBudgetEdit--" + resp);
		logger.info("Method :estimateBudgetEdit ends");

		return resp;
	}
	

// delete budget
	@SuppressWarnings("unchecked")
	@GetMapping("view-budget-estimate-delete")
	public @ResponseBody JsonResponse<Object> estimateBudgetDelete(@RequestParam String id, HttpSession session) {
		logger.info("Method : estimateBudgetDelete function starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "rest-view-budget-estimate-delete?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : estimateBudgetDelete function Ends");

		return res;
	}
	
	// view-budget
	@SuppressWarnings("unchecked")
	@GetMapping("view-budget-estimate-view-data")
	public @ResponseBody List<BudgetEstimationWebModel> estimateBudgetViewData(@RequestParam String id,
			HttpSession session) {

		logger.info("Method : estimateBudgetViewData starts" + id);

		JsonResponse<List<BudgetEstimationWebModel>> resp = new JsonResponse<List<BudgetEstimationWebModel>>();

		try {
			resp = restClient.getForObject(env.getProjects() + "rest-view-budget-estimate-view-data?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<BudgetEstimationWebModel> BudgetEstimationWebModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<BudgetEstimationWebModel>>() {
				});
		resp.setBody(BudgetEstimationWebModel);

		logger.info("Method : estimateBudgetViewData ends");
		return resp.getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-budget-estimate-add-data")
	public @ResponseBody JsonResponse<Object> addBudgetData(HttpSession session,
			@RequestBody List<BudgetEstimationWebModel> model) {
		logger.info("Method : addBudgetData starts   " + model);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
				resp = restClient.postForObject(env.getProjects() + "rest-view-add-data", model,
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

		logger.info("Method : addBudgetData ends");

		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("view-budget-estimate-projectlist")
	public @ResponseBody JsonResponse<List<ProjectCreationWebModel>> getProductList(Model model,
			@RequestParam String id, HttpSession session) {
		logger.info("Method : getProductList starts"+id);
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
		}
		JsonResponse<List<ProjectCreationWebModel>> response = new JsonResponse<List<ProjectCreationWebModel>>();
		try {
			response = restClient.getForObject(env.getProjects() + "getProjectList-list?id=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response.getMessage() != null && response.getMessage() != "") {
		} else {
			response.setMessage("Success");
		}
		logger.info("Method : getProductList ends" + response);
		return response;
	}
	//
	@SuppressWarnings("unchecked")
	@PostMapping("view-budget-estimate-extra-expense-add")
	public @ResponseBody JsonResponse<Object> addExpenses(@RequestBody ExtraExpenseModel extraExpenseModel,
			HttpSession session) {
		logger.info("Method : addExpenses starts");
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
	
		extraExpenseModel.setCreatedBy(userId);
		extraExpenseModel.setOrgName(orgName);
		extraExpenseModel.setOrgDivision(orgDivision);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restClient.postForObject(env.getProjects() + "rest-addExpenses", extraExpenseModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :addExpenses ends"+resp);
		return resp;
	}

	//
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-budget-estimate-getExpenseList" })
	public @ResponseBody JsonResponse<Object> getExtraExpenses(Model model, @RequestBody String item1,
			BindingResult result) {
		logger.info("Method : getExtraExpenses starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-project-expense-list?id=" + item1,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getCode().equals("success")) {
			res.setMessage("success");
		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getExtraExpenses ends" + res);
		return res;

	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-budget-estimate-getExpenseList")
	public @ResponseBody Object getExtraExpenses(HttpSession session,@RequestBody String item1) {

		logger.info("Method :getExtraExpenses starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-project-expense-list?id=" + item1,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectCreation--" + resp);
		logger.info("Method :getExtraExpenses ends");

		return resp;
	}
	
	//
	//getting project state id on edit
			@SuppressWarnings("unchecked")
			@GetMapping(value = { "view-budget-estimate-expense-list-edit" })
			public @ResponseBody JsonResponse<Object> getExpenseStateList(@RequestParam String id) {
				logger.info("Method : getProjectStateList starts" + id);
				JsonResponse<Object> res = new JsonResponse<Object>();
				try {
					res = restClient.getForObject(env.getProjects() + "rest-getProjectExpenseStateList?id=" + id, JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (res.getMessage() != null) {
					res.setCode(res.getMessage());
					res.setMessage("Unsuccess");
				} else {
					res.setMessage("success");
				}
				logger.info("Method : getExpenseStateList ends");
				return res;
			}
			//
			@SuppressWarnings("unchecked")
			@GetMapping("view-budget-estimate-get-Expense-List")
			public @ResponseBody JsonResponse<Object> getextraExp(@RequestParam String id) {
				logger.info("Method : getextraExp starts" + id);
				JsonResponse<Object> res = new JsonResponse<Object>();
				try {
					res = restClient.getForObject(env.getProjects() + "rest-get-budget-extra-expenses?id=" + id,
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
				logger.info("Method : getextraExp ends");
				return res;
			}
			
			//add SUbcategory
			
			@SuppressWarnings("unchecked")
			@PostMapping("view-budget-estimate-save-subcategory")
			public @ResponseBody JsonResponse<Object> savesubcategory(
					@RequestBody ProjectCategoryModel category, HttpSession session) {
				logger.info("Method : savesubcategory starts");

				JsonResponse<Object> resp = new JsonResponse<Object>();

				String userId = "";
				String orgName = "";
				String orgDiv = "";

				try {
					userId = (String) session.getAttribute("USER_ID");
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
				} catch (Exception e) {
					e.printStackTrace();
				}

				category.setCreatedBy(userId);
				category.setOrganizationName(orgName);
				category.setOrganizationDivision(orgDiv);

				try {
					resp = restClient.postForObject(env.getProjects() + "rest-savesubcategory", category,
							JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (resp.getMessage() != null && resp.getMessage() != "") {
					resp.setCode(resp.getMessage());
					resp.setMessage("Unsuccess");
				} else {
					resp.setMessage("Success");
				}

				logger.info("Method : saveCategory starts");
				return resp;
			}
			
}
