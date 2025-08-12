package nirmalya.aathithya.webmodule.projects.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.BudgetPlanMasterModel;
import nirmalya.aathithya.webmodule.master.model.CCAccountMapModel;
import nirmalya.aathithya.webmodule.master.model.FiscalYearModel;
import nirmalya.aathithya.webmodule.projects.model.PlanningSchedulesubModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCategoryModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectExecutionModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectPlanningSchedulingWebModel;

@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "projects/")

public class PlanningSchedulingWebController {

	Logger logger = LoggerFactory.getLogger(PlanningSchedulingWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("planning-scheduling")
	public String planningScheduling(Model model, HttpSession session) {

		logger.info("Method : planningScheduling starts");
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

			DropDownModel[] projectName = restClient.getForObject(env.getProjects() + "get-projectName-list",
					DropDownModel[].class);
			List<DropDownModel> projectNameList = Arrays.asList(projectName);

			model.addAttribute("projectNameList", projectNameList);
		} catch (Exception e) {
			e.printStackTrace();

		}

		logger.info("Method : planningScheduling ends");
		return "projects/project-planning-scheduling";
		//return "projects/project-planning";

	}

	// view
	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-view")
	public @ResponseBody List<ProjectCreationWebModel> viewProjectCreation(HttpSession session) {
		logger.info("Method : viewProjectCreation starts");

		JsonResponse<List<ProjectCreationWebModel>> resp = new JsonResponse<List<ProjectCreationWebModel>>();
		List<ProjectCreationWebModel> returnList = new ArrayList<ProjectCreationWebModel>();

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
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewProjectCreation ends"+returnList);
		return returnList;
	}
	//
	@GetMapping(value = { "planning-scheduling-getProjectDetails" })
	public @ResponseBody List<ProjectPlanningSchedulingWebModel> getProjectNmDetails(@RequestParam String id,
			@RequestParam String rowid, HttpSession session) {
		logger.info("Method : getProjectNameDetails starts");
		List<ProjectPlanningSchedulingWebModel> productList = new ArrayList<ProjectPlanningSchedulingWebModel>();

		if (id != null && id != "") {

			try {
				ProjectPlanningSchedulingWebModel[] ProjectPlanningSchedulingWebModel = restClient.getForObject(
						env.getProjects() + "rest-getProjectName-Details?id=" + id + "&id1=" + rowid,
						ProjectPlanningSchedulingWebModel[].class);
				productList = Arrays.asList(ProjectPlanningSchedulingWebModel);
				productList.forEach(s -> s.setProjectplanId(s.getProjectplanId()));
//				int count = 0;
//				for (ProjectPlanningSchedulingWebModel m : ProjectPlanningSchedulingWebModel) {
//					count++;
//					m.setProjectplanId(count);
//
//				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getProjectNameDetails ends" + productList);
		return productList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-projectlist")
	public @ResponseBody JsonResponse<List<ProjectCreationWebModel>> getProductBrandList(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : getProductBrandList starts" + id);
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		JsonResponse<List<ProjectCreationWebModel>> response = new JsonResponse<List<ProjectCreationWebModel>>();
		try {
			response = restClient.getForObject(
					env.getProjects() + "getProjectList-list?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : getProductBrandList ends" + response);
		return response;
	}

	@PostMapping(value = { "planning-scheduling-copyproject" })
	public @ResponseBody List<ProjectPlanningSchedulingWebModel> copyProjectDetails(
			@RequestBody ProjectPlanningSchedulingWebModel model, HttpSession session) {
		logger.info("Method : copyProjectDetails starts" + model);
		List<ProjectPlanningSchedulingWebModel> productList = new ArrayList<ProjectPlanningSchedulingWebModel>();

		try {
			ProjectPlanningSchedulingWebModel[] ProjectPlanningSchedulingWebModel = restClient.postForObject(
					env.getProjects() + "rest-copyproject-Details", model, ProjectPlanningSchedulingWebModel[].class);
			productList = Arrays.asList(ProjectPlanningSchedulingWebModel);
			productList.forEach(s -> s.setProjectplanId(s.getProjectplanId()));
//			int count = 0;
//			for (ProjectPlanningSchedulingWebModel m : ProjectPlanningSchedulingWebModel) {
//				count++;
//				m.setProjectplanId(count);
//
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : copyProjectDetails ends" + productList);
		return productList;
	}

	@GetMapping(value = { "planning-scheduling-getfirst-row-data" })
	public @ResponseBody List<ProjectPlanningSchedulingWebModel> getFirstRowData(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : getFirstRowData starts" + id);
		List<ProjectPlanningSchedulingWebModel> productList = new ArrayList<ProjectPlanningSchedulingWebModel>();

		if (id != null && id != "") {

			try {
				ProjectPlanningSchedulingWebModel[] ProjectPlanningSchedulingWebModel = restClient.getForObject(
						env.getProjects() + "rest-getfirstProjectName-Details?id=" + id,
						ProjectPlanningSchedulingWebModel[].class);
				productList = Arrays.asList(ProjectPlanningSchedulingWebModel);
				productList.forEach(s -> s.setProjectplanId(s.getProjectplanId()));
//				int count = 0;
//				for (ProjectPlanningSchedulingWebModel m : ProjectPlanningSchedulingWebModel) {
//					count++;
//					m.setProjectplanId(count);
//
//				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getFirstRowData ends" + productList);
		return productList;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("/planning-scheduling-get-total-list") public @ResponseBody
	 * JsonResponse<List<ProjectCategoryModel>>
	 * getAllProjectCategoryListPlanningSchedule(HttpSession session) {
	 * logger.info("Method : getAllProjectCategoryListPlanningSchedule starts");
	 * 
	 * JsonResponse<List<ProjectCategoryModel>> resp = new
	 * JsonResponse<List<ProjectCategoryModel>>();
	 * 
	 * try { // resp = restClient.getForObject(env.getMasterUrl() +
	 * "getAllProjectCategoryList", // JsonResponse.class);
	 * 
	 * resp = restClient.getForObject(env.getProjects() +
	 * "getProductCategoryDataListModallist", JsonResponse.class); } catch
	 * (RestClientException e) { e.printStackTrace(); }
	 * 
	 * String message = resp.getMessage();
	 * 
	 * if (message != null && message != "") {
	 * 
	 * } else { resp.setMessage("Success"); }
	 * 
	 * logger.info("Method : getAllProjectCategoryListPlanningSchedule starts"+resp)
	 * ; return resp; }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("/planning-scheduling-get-total-list")
	public @ResponseBody JsonResponse<List<ProjectCategoryModel>> getAllPlanningScheduleCategoryList(
			@RequestParam String id, HttpSession session) {
		logger.info("Method : getAllPlanningScheduleCategoryList starts" + id);

		JsonResponse<List<ProjectCategoryModel>> resp = new JsonResponse<List<ProjectCategoryModel>>();

		try {
			resp = restClient.getForObject(env.getProjects() + "getAllPlanningCategoryList?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getAllPlanningScheduleCategoryList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/planning-scheduling-getscheduledtls")
	public @ResponseBody JsonResponse<List<ProjectPlanningSchedulingWebModel>> getAllProjectIdDetls(
			@RequestParam String id,@RequestParam String id2, HttpSession session) {
		logger.info("Method : getAllProjectIdDetls starts" + id);

		JsonResponse<List<ProjectPlanningSchedulingWebModel>> resp = new JsonResponse<List<ProjectPlanningSchedulingWebModel>>();
		
		
		try {
			resp = restClient.getForObject(env.getProjects() + "getAllPlanningSchDtls?id=" + id + "&id2=" + id2, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getAllProjectIdDetls Ends"+resp);
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("planning-scheduling-datasavebrowseparent")
	public @ResponseBody JsonResponse<Object> savePlanningScheduleBrowse(@RequestBody PlanningSchedulesubModel eventModel,
			HttpSession session) {

		logger.info("Method : savePlanningSchedule function starts" + eventModel);
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

		
		eventModel.setCreatedBy(userId);
		eventModel.setOrganizationName(organization);
		eventModel.setOrganizationDivision(orgDivision);

		
		try {
			resp = restClient.postForObject(env.getProjects() + "save-savePlanningScheduleBrowse", eventModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : savePlanningSchedule function Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-child-edit")
	public @ResponseBody JsonResponse<ProjectPlanningSchedulingWebModel> editItemMaster(@RequestParam String id,HttpSession session) {

		logger.info("Method : editItemMaster starts" + id);

		JsonResponse<ProjectPlanningSchedulingWebModel> response = new JsonResponse<ProjectPlanningSchedulingWebModel>();
		try {
			response = restClient.getForObject(env.getProjects() + "rest-getplanschdata?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : editItemMaster endsssss"+response);
		return response;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("planning-scheduling-childdata")
	public @ResponseBody JsonResponse<Object> modifyChildData(@RequestBody ProjectPlanningSchedulingWebModel org,
			HttpSession session) {

		logger.info("Method : modifyChildData function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId;
		userId = (String) session.getAttribute("USER_ID");
		org.setCreatedBy(userId);

		try {
			resp = restClient.postForObject(env.getProjects() + "modifychilddata", org, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : modifyChildData function Ends");
		return resp;
	}

	// auto search preced date
	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-categoryData")
	public @ResponseBody JsonResponse<ProjectPlanningSchedulingWebModel> getcategoryData(Model model, @RequestParam String id,
			HttpServletRequest request, HttpSession session) {
		logger.info("Method : getcategoryData starts  " + id);
		JsonResponse<ProjectPlanningSchedulingWebModel> res = new JsonResponse<ProjectPlanningSchedulingWebModel>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-getcategoryData?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
		}
		logger.info("Method : getcategoryData ends");
		return res;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("planning-scheduling-masterdata")
	public @ResponseBody JsonResponse<Object> saveParentData(@RequestBody ProjectPlanningSchedulingWebModel eventModel,
			HttpSession session) {

		logger.info("Method : saveParentData  starts" + eventModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId;
		userId = (String) session.getAttribute("USER_ID");


		try {
			resp = restClient.postForObject(env.getProjects() + "save-saveParentData", eventModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : saveParentData Ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("planning-scheduling-parent-add")
	public @ResponseBody JsonResponse<Object> saveparentDataSchedule(
			@RequestBody List<ProjectPlanningSchedulingWebModel> eventModel, HttpSession session) {

		logger.info("Method : saveparentDataSchedule function starts" + eventModel);
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

		for (ProjectPlanningSchedulingWebModel m : eventModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);

		}


		try {
			resp = restClient.postForObject(env.getProjects() + "save-parentDataSchedule", eventModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : saveparentDataSchedule function Ends");
		return resp;
	}
	//
	@SuppressWarnings({ "unchecked" })
	@PostMapping("planning-scheduling-childdata-add")
	public @ResponseBody JsonResponse<Object> savechildDataSchedule(
			@RequestBody List<ProjectPlanningSchedulingWebModel> eventModel, HttpSession session) {

		logger.info("Method : savechildDataSchedule function starts" + eventModel);
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

		for (ProjectPlanningSchedulingWebModel m : eventModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);

		}


		try {
			resp = restClient.postForObject(env.getProjects() + "save-childDataSchedule", eventModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		if (message != null && message != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : savechildDataSchedule function Ends");
		return resp;
	}
	
	//autoserach for AssignedTo
	// auto search preced name
		@SuppressWarnings("unchecked")
		@GetMapping("planning-scheduling-autosearch-assignTo")
		public @ResponseBody JsonResponse<DropDownModel> getAssignedToAutoSearchList(Model model,
				@RequestParam String searchValue, HttpServletRequest request, HttpSession session) {
			logger.info("Method : getAssignedToAutoSearchList starts  " + searchValue);
			JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

			try {
				res = restClient.getForObject(env.getProjects() + "rest-getAssignedToAutoSearchList?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (res.getMessage() != null) {

				res.setCode(res.getMessage());
				res.setMessage("success");
			} else {
				res.setMessage("Unsuccess");
			}
			logger.info("Method : getAssignedToAutoSearchList ends");
			return res;
		}
		@SuppressWarnings("unchecked")
		@GetMapping("planning-scheduling-SubCategoryList")
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

		@SuppressWarnings("unchecked")
		@GetMapping("planning-scheduling-autosearch-preced")
		public @ResponseBody JsonResponse<DropDownModel> getPrecedAutoSearchList(Model model,
				@RequestParam String searchValue,String id, HttpServletRequest request, HttpSession session) {
			logger.info("Method : getPrecedAutoSearchList starts  " + searchValue);
			JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

			try {
				res = restClient.getForObject(env.getProjects() + "rest-getPrecedAutoSearchList-planning?id=" + searchValue +"&projectId="+id,
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
			logger.info("Method : getPrecedAutoSearchList ends");
			
			return res;
		}
		
//
		//delete
				@SuppressWarnings("unchecked")
				@PostMapping("planning-scheduling-delete")
				public @ResponseBody JsonResponse<Object> deleteProjectPlan(@RequestParam String id,@RequestParam String exeId, Model model,
						HttpSession session) {
					logger.info("Method : deleteProjectPlan function starts");

					JsonResponse<Object> res = new JsonResponse<Object>();

					JsonResponse<Object> resp = new JsonResponse<Object>();

					try {
						res = restClient.getForObject(env.getProjects() + "deleteProjectPlan?id=" + id+"&exeId="+exeId,
								JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}

					String message = res.getMessage();
					if (message != null && message != "") {

					} else {
						res.setMessage("Success");
					}
					logger.info("Method : deleteProjectPlan function Ends");

					return res;
				}
				//
				@SuppressWarnings("unchecked")
				@PostMapping("planning-scheduling-delete-child-data")
				public @ResponseBody JsonResponse<Object> deleteProjectPlanChild(@RequestParam String id,@RequestParam String pId,
						@RequestParam String proId,Model model,
						HttpSession session) {
					logger.info("Method : deleteProjectPlanChild function starts");

					JsonResponse<Object> res = new JsonResponse<Object>();

					JsonResponse<Object> resp = new JsonResponse<Object>();

					try {
						res = restClient.getForObject(env.getProjects() + "deleteProjectPlanChild?id=" + id + "&pId="+pId + "&proId="+proId,
								JsonResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}

					String message = res.getMessage();
					if (message != null && message != "") {

					} else {
						res.setMessage("Success");
					}
					logger.info("Method : deleteProjectPlanChild function Ends");

					return res;
				}
}
