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
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectPlanningSchedulingWebModel;

@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "projects/")
public class ProjectPlanningController {
	Logger logger = LoggerFactory.getLogger(ProjectPlanningController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new")
	public String planningScheduling(Model model, HttpSession session) {

		logger.info("Method : planningScheduling starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		try {
			DropDownModel[] planningCatList = restClient.getForObject(env.getProjects() + "get-budgetCategoryList",
					DropDownModel[].class);
			List<DropDownModel> planningCategoryList = Arrays.asList(planningCatList);
			model.addAttribute("planningCategoryList", planningCategoryList);
		} catch (RestClientException e) {
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

		try {

			resp = restClient.getForObject(env.getProjects() + "get-projectPriority-list?userId=" + userId + "&org="
					+ organization + "&div=" + orgDivision, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<DropDownModel> projectPriorityList = mapper.convertValue(resp.getBody(),
					new TypeReference<List<DropDownModel>>() {
					});
			model.addAttribute("projectPriorityList", projectPriorityList);

		} catch (Exception e) {
			e.printStackTrace();

		}

		try {
			resp = restClient.getForObject(env.getProjects() + "get-planningStatus-list?userId=" + userId + "&org="
					+ organization + "&div=" + orgDivision, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<DropDownModel> planningStatusList = mapper.convertValue(resp.getBody(),
					new TypeReference<List<DropDownModel>>() {
					});
			model.addAttribute("planningStatusList", planningStatusList);

		} catch (Exception e) {
			e.printStackTrace();

		}
		
		try {
			DropDownModel[] uom = restClient.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : planningScheduling ends");
		//return "projects/project-planning";
		return "projects/project-planning-new";

	}

//Category
	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-catrgoryList")
	public @ResponseBody Object getcatrgoryList(HttpSession session, @RequestParam String id) {

		logger.info("Method :getcatrgoryList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getProjects() + "get-planningCategoryList" + "?userid=" + id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getcatrgoryList--" + resp);
		logger.info("Method :getcatrgoryList ends");

		return resp;
	}
	// view

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-view")
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

	// get First row planning data through project

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-getfirst-row-data")
	public @ResponseBody Object getFirstRowData(@RequestParam String id, HttpSession session) {

		logger.info("Method :getFirstRowData starts");
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
			resp = restClient.getForObject(env.getProjects() + "rest-getfirstProjectName-Details-new?id=" + id
					+ "&userid=" + userId + "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getFirstRowData--" + resp);
		logger.info("Method :getFirstRowData ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-SubCategoryList")
	public @ResponseBody JsonResponse<Object> getsubCatList(@RequestParam String id, @RequestParam String pjId) {
		logger.info("Method : getsubCatList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(
					env.getProjects() + "rest-get-palnningSubCategoryList?id=" + id + "&pjId=" + pjId,
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

	// auto search preced name
	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-autosearch-assignTo")
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

	@SuppressWarnings({ "unchecked" })
	@PostMapping("planning-scheduling-new-parent-add")
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
			resp = restClient.postForObject(env.getProjects() + "save-parentPlanningData", eventModel,
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
		logger.info("Method : saveparentDataSchedule function Ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-parentDatadtls")
	public @ResponseBody Object getAllProjectExecutionDetails(@RequestParam String id, HttpSession session) {

		logger.info("Method :getAllProjectExecutionDetails starts");
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

			resp = restClient.getForObject(env.getProjects() + "rest-getParentDataDtls?id=" + id + "&userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getAllProjectExecutionDetails--" + resp);
		logger.info("Method :getAllProjectExecutionDetails ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-parent-edit")
	public @ResponseBody Object getAllProjectIdDetls(@RequestParam String id, HttpSession session) {

		logger.info("Method :editParaentDetails starts");
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
			resp = restClient.getForObject(env.getProjects() + "rest-getParentEditDatas?id=" + id + "&userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("editParaentDetails--" + resp);
		logger.info("Method :editParaentDetails ends");

		return resp;
	}

	//
	@SuppressWarnings({ "unchecked" })
	@PostMapping("planning-scheduling-new-childdata-add")
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
			resp = restClient.postForObject(env.getProjects() + "save-childDataPlanning", eventModel,
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

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-child-edit-planning")
	public @ResponseBody Object editChildDetails(@RequestParam String id, HttpSession session) {

		logger.info("Method :editChildDetails starts");
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
			resp = restClient.getForObject(env.getProjects() + "rest-editChildPlanning?id=" + id + "&userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("editChildDetails--" + resp);
		logger.info("Method :editChildDetails ends");

		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("planning-scheduling-new-masterdata")
	public @ResponseBody JsonResponse<Object> saveParentData(@RequestBody ProjectPlanningSchedulingWebModel eventModel,
			HttpSession session) {

		logger.info("Method : saveParentData  starts" + eventModel);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId;
		userId = (String) session.getAttribute("USER_ID");

		try {
			resp = restClient.postForObject(env.getProjects() + "save-saveMasterDataPlanning", eventModel,
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
		logger.info("Method : saveParentData Ends");
		return resp;
	}

	// auto search preced date
	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-categoryData")
	public @ResponseBody JsonResponse<ProjectPlanningSchedulingWebModel> getcategoryData(Model model,
			@RequestParam String id, HttpServletRequest request, HttpSession session) {
		logger.info("Method : getcategoryData starts  " + id);
		JsonResponse<ProjectPlanningSchedulingWebModel> res = new JsonResponse<ProjectPlanningSchedulingWebModel>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-getcategoryDataPlanning?id=" + id,
					JsonResponse.class);
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

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-autosearch-preced")
	public @ResponseBody JsonResponse<DropDownModel> getPrecedAutoSearchList(Model model,
			@RequestParam String searchValue, String id, HttpServletRequest request, HttpSession session) {
		logger.info("Method : getPrecedAutoSearchList starts  " + searchValue);
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
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
			res = restClient.getForObject(env.getProjects() + "rest-getPrecedAutoSearchList-planning?id=" + searchValue
					+ "&projectId=" + id + "&userId=" + userId + "&org=" + organization + "&div=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() == null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getPrecedAutoSearchList ends" + res);

		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-calculationdatetime")
	public @ResponseBody Object calculationdatetime(@RequestParam String startDt, @RequestParam String endDt,
			HttpSession session) {

		logger.info("Method :calculationdatetime starts" + endDt);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(
					env.getProjects() + "rest-getcalculationdatetime?startDt=" + startDt + "&endDt=" + endDt,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("calculationdatetime--" + resp);
		logger.info("Method :calculationdatetime ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("planning-scheduling-new-SubCategoryListEdit")
	public @ResponseBody Object SubCategoryListEdit(@RequestParam String id, @RequestParam String planIdd,
			HttpSession session) {

		logger.info("Method :SubCategoryListEdit starts" + planIdd);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(
					env.getProjects() + "rest-SubCategoryListEdit?id=" + id + "&planIdd=" + planIdd,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("SubCategoryListEdit--" + resp);
		logger.info("Method :SubCategoryListEdit ends");

		return resp;
	}

}
