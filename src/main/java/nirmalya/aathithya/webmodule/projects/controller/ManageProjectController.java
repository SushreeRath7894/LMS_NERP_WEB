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

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.BudgetPlanMasterModel;
import nirmalya.aathithya.webmodule.master.model.CCAccountMapModel;
//import nirmalya.aathithya.webmodule.master.model.ChartOfAccountModel;
import nirmalya.aathithya.webmodule.master.model.ProductCategoryModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryRequisitionModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.projects.model.ManageProjectMasterModel;
import nirmalya.aathithya.webmodule.projects.model.NonResourcePlanModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectsChartOfAccountModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectsCostCenterModel;
import nirmalya.aathithya.webmodule.projects.model.ProjrctsTeamPlanModel;


@Controller
@RequestMapping(value = "projects/")
public class ManageProjectController {

	Logger logger = LoggerFactory.getLogger(ManageProjectController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	
	@GetMapping("view-projects")
	public String viewProjects(Model model, HttpSession session) {

	logger.info("Method : viewProjects starts");
	
	try {
		
		DropDownModel[] department = restTemplate.getForObject(env.getRecruitment() + "DepartmentList",
				DropDownModel[].class);
		List<DropDownModel> DepartmentList = Arrays.asList(department);
		model.addAttribute("DepartmentList", DepartmentList);
	} catch (RestClientException e) {
		e.printStackTrace();
	}
	
	try {
		
		DropDownModel[] location = restTemplate.getForObject(env.getRecruitment() + "jobLocationList",
				DropDownModel[].class);
		List<DropDownModel> jobLocationList = Arrays.asList(location);
		model.addAttribute("jobLocationList", jobLocationList);
	} catch (RestClientException e) {
		e.printStackTrace();
	}
	
	try {
		
		System.out.println(env.getMasterUrl() + "projectTypeList");
		DropDownModel[] projectType = restTemplate.getForObject(env.getProjects() + "projectTypeList",
				DropDownModel[].class);
		List<DropDownModel> projectTypeList = Arrays.asList(projectType);
		model.addAttribute("projectTypeList", projectTypeList);
	} catch (RestClientException e) {
		e.printStackTrace();
	}
	
	try {
		
		DropDownModel[] priority = restTemplate.getForObject(env.getProjects() + "priorityList",
				DropDownModel[].class);
		List<DropDownModel> priorityList = Arrays.asList(priority);
		model.addAttribute("priorityList", priorityList);
	} catch (RestClientException e) {
		e.printStackTrace();
	}
	
	try {
			
			DropDownModel[] manager = restTemplate.getForObject(env.getProjects() + "EmployeeList",
					DropDownModel[].class);
			List<DropDownModel> managerList = Arrays.asList(manager);
			model.addAttribute("EmployeeList", managerList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	
	//model.addAttribute("pageName", "Account Mapping Page Coming Soon");
	logger.info("Method : viewProjects ends");
	return "projects/view-projects";

	}
	
	/*@SuppressWarnings("unchecked")
	@PostMapping("/chart-account-get-total-list")
	public @ResponseBody JsonResponse<List<ChartOfAccountModel>> getAllChartOfAccList(HttpSession session) {
		logger.info("Method : getAllChartOfAccList starts");
		
		JsonResponse<List<ChartOfAccountModel>> resp = new JsonResponse<List<ChartOfAccountModel>>();
		
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getAllChartAccList",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getAllChartAccList starts");
		return resp;
	}*/
	
	/*@SuppressWarnings("unchecked")
	@PostMapping("/chart-account-get-leaf-list-by-id")
	public @ResponseBody JsonResponse<List<ChartOfAccountModel>> getChartLeafListById(@RequestBody String id,HttpSession session) {
		logger.info("Method : getChartLeafListById starts");
		
		JsonResponse<List<ChartOfAccountModel>> resp = new JsonResponse<List<ChartOfAccountModel>>();
		
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getChartLeafListById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getChartLeafListById starts");
		return resp;
	}*/
	
	/**
	 * Web Controller - Get Item List By AutoSearch
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-projects-get-product-list" })
	public @ResponseBody JsonResponse<InventoryRequisitionModel> getItemAutoSearchList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getItemAutoSearchList starts");

		JsonResponse<InventoryRequisitionModel> res = new JsonResponse<InventoryRequisitionModel>();

		try {
			res = restTemplate.getForObject(env.getInventoryUrl() + "getProductListByAutoSearch?id=" + searchValue,
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
		System.out.println(res);
		logger.info("Method : getItemAutoSearchList ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-projects-product-category-get-total-list")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getAllProductCategoryList(HttpSession session) {
		logger.info("Method : getAllProductCategoryList starts");

		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getAllProductCategoryList", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getAllProductCategoryList starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-projects-product-category-get-category-list-by-id")
	public @ResponseBody JsonResponse<List<ProductCategoryModel>> getProductCategoryListById(@RequestBody String id,
			HttpSession session) {
		logger.info("Method : getProductCategoryListById starts");

		JsonResponse<List<ProductCategoryModel>> resp = new JsonResponse<List<ProductCategoryModel>>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getProductCategoryListById?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getProductCategoryListById starts");
		return resp;
	}
	
	/*
	 * drop down for supervisor by job title
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/view-projects-get-product-by-cat" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getProduct(Model model, @RequestBody String index,
			BindingResult result) {
		logger.info("Method : getProduct starts");

		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(env.getInventoryUrl() + "getProductByCat?id=" + index,
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

		logger.info("Method : getProduct  ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-projects-add-ajax")
	public @ResponseBody JsonResponse<ManageProjectMasterModel> addProjects(Model model, HttpSession session, @RequestBody ManageProjectMasterModel project){
		
		logger.info("Method : addProjects starts");
		
		JsonResponse<ManageProjectMasterModel> resp = new JsonResponse<ManageProjectMasterModel>();
		String userId = "";
		String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");

		} catch (Exception e) {
			e.printStackTrace();
		}
		project.setCreatedBy(userId);
		
		if(project.getStartDate()!=null && project.getStartDate()!="") {
			project.setStartDate(DateFormatter.inputDateFormat(project.getStartDate(), dateFormat));
		}
		if(project.getEndDate()!=null && project.getEndDate()!="") {
			project.setEndDate(DateFormatter.inputDateFormat(project.getEndDate(), dateFormat));
		}
		
		try {
			resp = restTemplate.postForObject(env.getProjects() + "addProjects", project, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {
			
			resp.setMessage("Success");
		}

		
		logger.info("Method : addProjects ends");
		
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-projects-list")
	public @ResponseBody List<ManageProjectMasterModel> viewProjects(HttpSession session) {
		logger.info("Method : viewProjects starts");

		JsonResponse<List<ManageProjectMasterModel>> resp = new JsonResponse<List<ManageProjectMasterModel>>();
		
		try {
			resp = restTemplate.getForObject(env.getProjects() + "viewProjects", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		
		ObjectMapper mapper = new ObjectMapper();

		List<ManageProjectMasterModel> project = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ManageProjectMasterModel>>() {
				});
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		for(ManageProjectMasterModel m : project) {
			
			if(m.getProjectTypeName() == null || m.getProjectTypeName() == "null") {
				m.setProjectTypeName("");
			}
			if(m.getPriorityName() == null || m.getPriorityName() == "null") {
				m.setPriorityName("");
			}
			
			if(m.getStartDate() != null && m.getStartDate() != "") {
				m.setStartDate(DateFormatter.dateFormat(m.getStartDate(),dateFormat));
			}
			if(m.getEndDate() != null && m.getEndDate() != "") {
				m.setEndDate(DateFormatter.dateFormat(m.getEndDate(),dateFormat));
			}
			if(m.getCreatedOn() != null && m.getCreatedOn() != "") {
				m.setCreatedOn(DateFormatter.dateFormat(m.getCreatedOn(),dateFormat));
			}
		}
		resp.setBody(project);
		
		logger.info("Method : viewProjects ends");
		return resp.getBody();
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-projects-delete")
	public @ResponseBody JsonResponse<Object> deleteProjects(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : deleteProjects starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restTemplate.getForObject(
					env.getProjects() + "deleteProjects?id="+ id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method :  deleteProjects ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-projects-edit")
	public @ResponseBody JsonResponse<List<ManageProjectMasterModel>> editProjects(Model model, @RequestParam String id, HttpSession session) {
		
		logger.info("Method : editProjects starts");
		
		JsonResponse<List<ManageProjectMasterModel>> jsonResponse = new JsonResponse<List<ManageProjectMasterModel>>();
		System.out.println(id);
		try {
			jsonResponse = restTemplate.getForObject(env.getProjects() + "editProjects?id=" + id,
					JsonResponse.class);
			
		} catch(RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();

		List<ManageProjectMasterModel> project = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<ManageProjectMasterModel>>() {
				});
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		for(ManageProjectMasterModel m : project) {
			if(m.getStartDate() != null && m.getStartDate() != "") {
				m.setStartDate(DateFormatter.dateFormat(m.getStartDate(),dateFormat));
			}
			if(m.getEndDate() != null && m.getEndDate() != "") {
				m.setEndDate(DateFormatter.dateFormat(m.getEndDate(),dateFormat));
			}
			if(m.getCreatedOn() != null && m.getCreatedOn() != "") {
				m.setCreatedOn(DateFormatter.dateFormat(m.getCreatedOn(),dateFormat));
			}
		}
		
		String message = (String) session.getAttribute("message");

		if (message != null && message != "") {
			model.addAttribute("message", message);
		}
		 
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
			
		} else {
			jsonResponse.setMessage("Success");
		}
		jsonResponse.setBody(project);
		
		System.out.println("##@@@@"+jsonResponse);
		logger.info("Method : editProjects ends");
		return jsonResponse;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-projects-add-teamplan")
	public @ResponseBody JsonResponse<Object> addTeamPlan(Model model, HttpSession session, @RequestBody ProjrctsTeamPlanModel reqModel){
		
		logger.info("Method : addTeamPlan starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		reqModel.setCreatedBy(userId);
		
		try {
			resp = restTemplate.postForObject(env.getProjects() + "addTeamPlan", reqModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {
			
			resp.setMessage("Success");
		}

		
		logger.info("Method : addTeamPlan ends");
		
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-projects-teamplan-list")
	public @ResponseBody List<ProjrctsTeamPlanModel> viewTeamPlan(HttpSession session, @RequestParam String id) {
		logger.info("Method : viewTeamPlan starts");

		JsonResponse<List<ProjrctsTeamPlanModel>> resp = new JsonResponse<List<ProjrctsTeamPlanModel>>();
		
		try {
			resp = restTemplate.getForObject(env.getProjects() + "viewTeamPlan?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}
		
		ObjectMapper mapper = new ObjectMapper();

		List<ProjrctsTeamPlanModel> addTeamPlan = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ProjrctsTeamPlanModel>>() {
				});
		for(ProjrctsTeamPlanModel m : addTeamPlan) {
			
			if(m.getChartOfAccountName() == null || m.getChartOfAccountName() == "null") {
				m.setChartOfAccountName("");
			}
			if(m.getCostCenterName() == null || m.getCostCenterName() == "null") {
				m.setCostCenterName("");
			}
			if(m.getLocationName() == null || m.getLocationName() == "null") {
				m.setLocationName("");
			}
		}
		
		resp.setBody(addTeamPlan);

		logger.info("Method : viewTeamPlan ends");
		return resp.getBody();
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-projects-teamplan-delete")
	public @ResponseBody JsonResponse<Object> deleteTeamPlan(Model model, HttpSession session, @RequestParam String id){
		
		logger.info("Method : deleteTeamPlan starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restTemplate.getForObject(env.getProjects() + "deleteTeamPlan?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {
			
			resp.setMessage("Success");
		}

		
		logger.info("Method : deleteTeamPlan ends");
		
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-projects-add-nonResource")
	public @ResponseBody JsonResponse<Object> addNonResource(Model model, HttpSession session, @RequestBody NonResourcePlanModel reqModel){
		
		logger.info("Method : addNonResource starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		reqModel.setCreatedBy(userId);
		
		try {
			resp = restTemplate.postForObject(env.getProjects() + "addNonResource", reqModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {
			
			resp.setMessage("Success");
		}

		
		logger.info("Method : addNonResource ends");
		
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-projects-nonResource-list")
	public @ResponseBody List<NonResourcePlanModel> viewNonResource(HttpSession session, @RequestParam String id) {
		logger.info("Method : viewNonResource starts");

		JsonResponse<List<NonResourcePlanModel>> resp = new JsonResponse<List<NonResourcePlanModel>>();
		
		try {
			resp = restTemplate.getForObject(env.getProjects() + "viewNonResource?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		System.out.println(resp.getBody());
		logger.info("Method : viewNonResource ends");
		return resp.getBody();
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-projects-nonResource-delete")
	public @ResponseBody JsonResponse<Object> deleteNonResource(Model model, HttpSession session, @RequestParam String id){
		
		logger.info("Method : deleteNonResource starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			resp = restTemplate.getForObject(env.getProjects() + "deleteNonResource?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() == "" || resp.getMessage() == null) {
			
			resp.setMessage("Success");
		}

		
		logger.info("Method : deleteNonResource ends");
		
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-projects-get-cc-list")
	public @ResponseBody JsonResponse<List<CCAccountMapModel>> getCCAccountDataList(@RequestBody BudgetPlanMasterModel yearDtls, HttpSession session) {
		logger.info("Method : getCCAccountDataList starts");
		
		JsonResponse<List<CCAccountMapModel>> resp = new JsonResponse<List<CCAccountMapModel>>();
		
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getCCAccountDataList?id="+yearDtls.getYear()+"&mnth="+yearDtls.getId(),
					JsonResponse.class);
			
			ObjectMapper mapper = new ObjectMapper();
			List<CCAccountMapModel> ccDtls = mapper.convertValue(resp.getBody(),
					new TypeReference<List<CCAccountMapModel>>() {
					});
			
			for(CCAccountMapModel m : ccDtls) {
				CCAccountMapModel[] chart = restTemplate.getForObject(env.getMasterUrl() + "getChartAccountDetails?id="+m.getId()+"&year="+yearDtls.getYear()+"&mnth="+yearDtls.getId(), CCAccountMapModel[].class);
				List<CCAccountMapModel> chartAccList = Arrays.asList(chart);
				
				m.setChartAccList(chartAccList);
			}
			
			resp.setBody(ccDtls);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		
		logger.info("Method : getCCAccountDataList starts");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-projects-get-ca-list")
	public @ResponseBody JsonResponse<List<ProjectsChartOfAccountModel>> viewChartAccount() {
		logger.info("Method : viewChartAccount starts");

		JsonResponse<List<ProjectsChartOfAccountModel>> resp = new JsonResponse<List<ProjectsChartOfAccountModel>>();
		
		try {
			resp = restTemplate.getForObject(env.getProjects() + "viewChartAccount", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		System.out.println(resp.getBody());
		logger.info("Method : viewChartAccount ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-projects-get-chart-account-autocomplete")
	public @ResponseBody JsonResponse<List<ProjectsChartOfAccountModel>> viewChartAccountAutoComplete(@RequestParam String search) {
		logger.info("Method : viewChartAccountAutoComplete starts");

		JsonResponse<List<ProjectsChartOfAccountModel>> resp = new JsonResponse<List<ProjectsChartOfAccountModel>>();
		
		try {
			resp = restTemplate.getForObject(env.getProjects() + "viewChartAccountAutoComplete?search=" + search, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		System.out.println(resp.getBody());
		logger.info("Method : viewChartAccountAutoComplete ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/view-projects-get-cost-center-autocomplete")
	public @ResponseBody JsonResponse<List<ProjectsCostCenterModel>> viewCostCenterAutoComplete(@RequestParam String search) {
		logger.info("Method : viewCostCenterAutoComplete starts");

		JsonResponse<List<ProjectsCostCenterModel>> resp = new JsonResponse<List<ProjectsCostCenterModel>>();
		
		try {
			resp = restTemplate.getForObject(env.getProjects() + "viewCostCenterAutoComplete?search=" + search, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		} 
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {

			resp.setMessage("Success");
		}

		System.out.println(resp.getBody());
		logger.info("Method : viewChartAccountAutoComplete ends");
		return resp;
	}
	
	@GetMapping("view-plan")
	public String accountmapping(Model model, HttpSession session) {

		logger.info("Method : viewProduct starts");
		model.addAttribute("pageName", "Plan Page Coming Soon");
		logger.info("Method : viewProduct ends");
		return "recruitment/view-action";

	}
	
}
