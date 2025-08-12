package nirmalya.aathithya.webmodule.projects.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.BudgetPlanMasterModel;
import nirmalya.aathithya.webmodule.master.model.CCAccountMapModel;
import nirmalya.aathithya.webmodule.master.model.FiscalYearModel;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCategoryModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectExecutionModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectMessageModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectPlanningSchedulingWebModel;
import nirmalya.aathithya.webmodule.purchase.model.ManageInvoiceModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseIndentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseOrderModel;
import nirmalya.aathithya.webmodule.purchase.model.VendorNewModel;

/**
 * @author NirmalyaLabs
 *
 */
@Controller
@RequestMapping(value = { "projects/" })
public class ProjectExecutionWebController {

	Logger logger = LoggerFactory.getLogger(ProjectExecutionWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "project-execution" })
	public String executionPlan(Model model, HttpSession session) {
		logger.info("Method : executionPlan starts");
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
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		// String rolesToCheck = "rol031,rol025,rol026,rol001,rol002,rol003";
		// List<String> rolesList = Arrays.asList(rolesToCheck.split(","));
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001")) {

				model.addAttribute("adRole", data);
				logger.info("data ==" + data);

			}
		}
		try {

			DropDownModel[] department = restTemplate.getForObject(env.getRecruitment() + "DepartmentList",
					DropDownModel[].class);
			List<DropDownModel> DepartmentList = Arrays.asList(department);

			
			model.addAttribute("DepartmentList", DepartmentList);
		} catch (RestClientException e) {
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
			DropDownModel[] uom = restTemplate.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
			logger.info("unitList" + unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] department = restTemplate.getForObject(
					env.getPurchaseUrl() + "ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : executionPlan ends");
		// return "projects/projrct-execution";
	//	return "projects/project-execution-new";
		return "projects/projectExecutionNew";
	}

	@GetMapping(value = { "/project-execution-chart" })
	public String projectexecutionchart(Model model, HttpSession session) {
		logger.info("Method : projectexecutionchart starts");

		logger.info("Method : projectexecutionchart ends");
		return "projects/projectexecutionchart.html";
	}

	@GetMapping(value = { "/project-execution-monitoring" })
	public String projectexecutionmonitoring(Model model, HttpSession session, @RequestParam String id) {
		logger.info("Method : projectexecutionmonitoring starts");

		logger.info("Method : projectexecutionmonitoring ends");
		return "projects/projectexecutionmonitoring.html";
	}

	@GetMapping(value = { "/project-execution-invoiceDetails" })
	public String projectInvoiceDetails(Model model, HttpSession session, @RequestParam String id) {
		logger.info("Method : projectInvoiceDetails starts");

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
		try {

			DropDownModel[] department = restTemplate.getForObject(env.getRecruitment() + "DepartmentList",
					DropDownModel[].class);
			List<DropDownModel> DepartmentList = Arrays.asList(department);
			model.addAttribute("DepartmentList", DepartmentList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] department = restTemplate.getForObject(
					env.getPurchaseUrl() + "ProjectList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> ProjectList = Arrays.asList(department);
			model.addAttribute("ProjectList", ProjectList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : projectInvoiceDetails ends");
		return "projects/projectexecutionInvoice.html";
	}

	// category list in tree structure

	@SuppressWarnings("unchecked")
	@GetMapping("/project-execution-category-get-total-list")
	public @ResponseBody JsonResponse<List<ProjectCategoryModel>> getAllProductCategoryList(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : getAllProjectCategoryList starts" + id);

		JsonResponse<List<ProjectCategoryModel>> resp = new JsonResponse<List<ProjectCategoryModel>>();

		try {
			resp = restTemplate.getForObject(env.getProjects() + "getAllProjectCategoryList?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getAllProductCategoryList ends" + resp);
		return resp;
	}

	// copy save
	@SuppressWarnings("unchecked")
	@PostMapping("/project-execution-tasks-save")
	public @ResponseBody JsonResponse<Object> saveProjectCategory(@RequestBody List<ProjectExecutionModel> category,
			HttpSession session) {
		logger.info("Method : saveProjectCategory starts" + category);

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

		for (ProjectExecutionModel m : category) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);

		}

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-saveProjectCategory", category,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<ProjectExecutionModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<ProjectExecutionModel>>() {
					});

			resp.setBody(quotation);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveProjectCategory starts" + resp);
		return resp;
	}

//view

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-details-list")
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

			resp = restClient.getForObject(env.getProjects() + "rest-getAllProjectExecutionDetails?id=" + id
					+ "&userid=" + userId + "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllProjectExecutionDetails ends");

		return resp;
	}

	// planningdata
	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-planning-list")
	public @ResponseBody Object getplanningList(@RequestParam String id, HttpSession session) {

		logger.info("Method :getplanningList starts");
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
			resp = restClient.getForObject(env.getProjects() + "rest-getplanningList?id=" + id + "&userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getplanningList ends");

		return resp;
	}

	// project view
	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-view-through-ajax")
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

		logger.info("Method :viewProjectCreation ends");

		return resp;
	}

	// Add execution Tasks

	@SuppressWarnings("unchecked")
	@PostMapping("/project-execution-add")
	public @ResponseBody JsonResponse<Object> saveProjectExecutions(@RequestBody List<ProjectExecutionModel> execution,
			HttpSession session) {
		logger.info("Method : saveProjectExecutions starts" + execution);
		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				execution.get(0).setFileAttach(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

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

		for (ProjectExecutionModel m : execution) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);

		}

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-saveProjectExecutions", execution,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<ProjectExecutionModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<ProjectExecutionModel>>() {
					});

			resp.setBody(quotation);
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
			session.removeAttribute("quotationPFile");
		} else {
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : saveProjectExecutions ends" + resp);
		return resp;
	}

	// Add main tasks
	@SuppressWarnings("unchecked")
	@PostMapping("/project-execution-parent-add")
	public @ResponseBody JsonResponse<Object> saveProjectParentCategory(
			@RequestBody List<ProjectExecutionModel> category, HttpSession session) {
		logger.info("Method : saveProjectParentCategory starts" + category);
		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				category.get(0).setFileAttach(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
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

		for (ProjectExecutionModel m : category) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);

		}

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-saveParentCategory", category,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			List<ProjectExecutionModel> quotation = mapper.convertValue(resp.getBody(),
					new TypeReference<List<ProjectExecutionModel>>() {
					});

			resp.setBody(quotation);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
			session.removeAttribute("quotationPFile");
		}

		logger.info("Method : saveProjectParentCategory starts" + resp);
		return resp;
	}

	// drpdown list
	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-projectlist")
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

	// edit Parent

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-parent-edit")
	public @ResponseBody Object getProjectParentEdit(@RequestParam String id, @RequestParam String exeId,
			HttpSession session) {

		logger.info("Method :getProjectParentEdit starts", id);
		logger.info("exeId", exeId);
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
			resp = restClient.getForObject(env.getProjects() + "rest-getProjectParentEdit?id=" + id + "&exeId=" + exeId
					+ "&userid=" + userId + "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getProjectParentEdit ends");

		return resp;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("/") public @ResponseBody JsonResponse<ProjectExecutionModel>
	 * getProjectParentEdit(
	 * 
	 * @RequestBody ProjectExecutionModel model, HttpSession session) {
	 * logger.info("Method : getProjectParentEdit starts");
	 * 
	 * JsonResponse<ProjectExecutionModel> resp = new
	 * JsonResponse<ProjectExecutionModel>();
	 * 
	 * try { resp = restClient.getForObject(env.getProjects() +
	 * "rest-getProjectParentEdit?id=" + model.getCategoryId() + "&pid=" +
	 * model.getProjectId(), JsonResponse.class); ObjectMapper mapper = new
	 * ObjectMapper(); ProjectExecutionModel customerNewModel =
	 * mapper.convertValue(resp.getBody(), new
	 * TypeReference<ProjectExecutionModel>() { }); String profile = null;
	 * 
	 * if (customerNewModel.getFileAttach() != null &&
	 * customerNewModel.getFileAttach() != "" &&
	 * !customerNewModel.getFileAttach().equals("null")) {
	 * 
	 * profile = env.getBaseURL() + "document/document/" +
	 * customerNewModel.getFileAttach(); customerNewModel.setFileAttach(profile); }
	 * resp.setBody(customerNewModel);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * String message = resp.getMessage();
	 * 
	 * if (message != null && message != "") {
	 * 
	 * } else { resp.setMessage("Success"); }
	 * 
	 * logger.info("Method : getProjectParentEdit ends"); return resp; }
	 */

	// edit child

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-child-edit")
	public @ResponseBody Object getProjectChildEdit(@RequestParam String id, @RequestParam String exeId,
			HttpSession session) {

		logger.info("Method :getProjectChildEdit starts");
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
			resp = restClient.getForObject(env.getProjects() + "rest-getProjectChildEdit?id=" + id + "&exeId=" + exeId
					+ "&userid=" + userId + "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getProjectChildEdit--" + resp);
		logger.info("Method :getProjectChildEdit ends");

		return resp;
	}

	// upload docs
	@PostMapping("project-execution-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("uploadFile: " + e.getMessage());
		}
		logger.info("Method : uploadFile controller function 'post-mapping' ends" + response);
		return response;
	}

	// image name
	private String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("png") || ext.contentEquals("jpg") || ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}
			}
			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("saveAllImage: " + e.getMessage());
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}

//
	// add task details
	@SuppressWarnings("unchecked")
	@PostMapping("/project-execution-task-detail-add")
	public @ResponseBody JsonResponse<Object> saveTaskDetails(@RequestBody List<ProjectExecutionModel> execution,
			HttpSession session) {
		logger.info("Method : saveTaskDetails starts" + execution);
		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				execution.get(0).setFileAttach(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
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
		for (ProjectExecutionModel m : execution) {
			m.setCreatedBy(userId);
			m.setOrganizationName(organization);
			m.setOrganizationDivision(orgDivision);
		}

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-saveTaskDetails", execution, JsonResponse.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
			session.removeAttribute("quotationPFile");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : saveTaskDetails starts" + resp);
		return resp;
	}

	// view tasks

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-view-task")
	public @ResponseBody Object viewProjectTaskDetails(@RequestParam String id, HttpSession session) {

		logger.info("Method :getProjectParentEdit starts");
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
			resp = restClient.getForObject(env.getProjects() + "rest-viewProjectTaskDetails?id=" + id + "&userid="
					+ userId + "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getProjectParentEdit--" + resp);
		logger.info("Method :viewProjectTaskDetails ends");

		return resp;
	}

	// edit task
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("project-execution-edit-task") public @ResponseBody
	 * JsonResponse<ProjectExecutionModel> editTaskDetails(@RequestParam String id,
	 * HttpSession session) { logger.info("Method : editTaskDetails starts -- " +
	 * id);
	 * 
	 * JsonResponse<ProjectExecutionModel> resp = new
	 * JsonResponse<ProjectExecutionModel>();
	 * 
	 * try { resp = restClient.getForObject(env.getProjects() +
	 * "rest-editTaskDetails?id=" + id, JsonResponse.class); ObjectMapper mapper =
	 * new ObjectMapper(); ProjectExecutionModel customerNewModel =
	 * mapper.convertValue(resp.getBody(), new
	 * TypeReference<ProjectExecutionModel>() { }); String profile = null;
	 * 
	 * if (customerNewModel.getFileAttach() != null &&
	 * customerNewModel.getFileAttach() != "" &&
	 * !customerNewModel.getFileAttach().equals("null")) {
	 * 
	 * profile = env.getBaseURL() + "document/document/" +
	 * customerNewModel.getFileAttach(); customerNewModel.setFileAttach(profile); }
	 * resp.setBody(customerNewModel);
	 * 
	 * } catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * String message = resp.getMessage();
	 * 
	 * if (message != null && message != "") {
	 * 
	 * } else { resp.setMessage("Success"); }
	 * 
	 * logger.info("Method : editTaskDetails ends"); return resp; }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-edit-task")
	public @ResponseBody Object editTaskDetails(@RequestParam String id, @RequestParam String id2,
			HttpSession session) {

		logger.info("Method :editTaskDetails starts", id);
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
			resp = restClient.getForObject(env.getProjects() + "rest-editTaskDetails?id=" + id + "&id2=" + id2
					+ "&userid=" + userId + "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("editTaskDetails--" + resp);
		logger.info("Method :editTaskDetails ends");

		return resp;
	}

	// delete task
	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-delete-task")
	public @ResponseBody JsonResponse<Object> deleteTaskDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteTaskDetails function starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getProjects() + "rest-deleteTaskDetails?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteTaskDetails function Ends");

		return res;
	}

	// auto search preced name
	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-autosearch-preced")
	public @ResponseBody JsonResponse<DropDownModel> getPrecedAutoSearchList(Model model,
			@RequestParam String searchValue, String id, HttpServletRequest request, HttpSession session) {
		logger.info("Method : getPrecedAutoSearchList starts  " + searchValue);
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(
					env.getProjects() + "rest-getPrecedAutoSearchList?id=" + searchValue + "&projectId=" + id,
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

	// auto search preced name
	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-autosearch-assignTo")
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

	// auto search preced date
	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-category-get-dates")
	public @ResponseBody JsonResponse<DropDownModel> getPrecedDates(Model model, @RequestParam String id,
			HttpServletRequest request, HttpSession session) {
		logger.info("Method : getPrecedDates starts  " + id);
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-getPrecedDates?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getPrecedDates ends");

		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/project-execution-taskdetail")
	public @ResponseBody JsonResponse<ProjectExecutionModel> gettaskdetail(@RequestBody ProjectExecutionModel model,
			HttpSession session) {
		logger.info("Method : gettaskdetail starts" + model);

		JsonResponse<ProjectExecutionModel> resp = new JsonResponse<ProjectExecutionModel>();

		try {
			resp = restClient.getForObject(env.getProjects() + "rest-gettaskdetail?id=" + model.getCategoryId()
					+ "&pid=" + model.getExecutionId(), JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			ProjectExecutionModel customerNewModel = mapper.convertValue(resp.getBody(),
					new TypeReference<ProjectExecutionModel>() {
					});
			String profile = null;

			if (customerNewModel.getFileAttach() != null && customerNewModel.getFileAttach() != ""
					&& !customerNewModel.getFileAttach().equals("null")) {

				profile = env.getBaseURL() + "document/document/" + customerNewModel.getFileAttach();
				customerNewModel.setFileAttach(profile);
			}
			resp.setBody(customerNewModel);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getProjectParentEdit ends");
		return resp;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("/project-execution-getFromPlanning") public @ResponseBody
	 * JsonResponse<List<ProjectExecutionModel>> getFromPlanning(
	 * 
	 * @RequestBody ProjectExecutionModel category, HttpSession session) {
	 * logger.info("Method : getFromPlanning starts" + category);
	 * 
	 * JsonResponse<List<ProjectExecutionModel>> resp = new
	 * JsonResponse<List<ProjectExecutionModel>>();
	 * 
	 * try { resp = restTemplate.getForObject(env.getProjects() +
	 * "rest-getFromPlanning?id=" + category.getProjectId(), JsonResponse.class); }
	 * catch (RestClientException e) { e.printStackTrace(); }
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); List<ProjectExecutionModel> model =
	 * mapper.convertValue(resp.getBody(), new
	 * TypeReference<List<ProjectExecutionModel>>() { }); String profile = null; for
	 * (ProjectExecutionModel m : model) if (m.getFileAttach() != null &&
	 * m.getFileAttach() != "" && !model.get(0).getFileAttach().equals("null")) {
	 * 
	 * profile = env.getBaseURL() + "document/document/" + m.getFileAttach();
	 * m.setFileAttach(profile); }
	 * 
	 * resp.setBody(model);
	 * 
	 * String message = resp.getMessage();
	 * 
	 * if (message != null && message != "") {
	 * 
	 * } else { resp.setMessage("Success"); }
	 * logger.info("Method : getFromPlanning starts" + resp); return resp; }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-getFromPlanning")
	public @ResponseBody JsonResponse<Object> getFromPlanning(@RequestParam String id, HttpSession session) {
		logger.info("Method : getFromPlanning function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();

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
			res = restClient.getForObject(env.getProjects() + "rest-getFromPlanning?id=" + id + "&userId=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : getFromPlanning function Ends");

		return res;
	}
	// for requistion list

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping(value = { "project-execution-getReqList" }) public @ResponseBody
	 * JsonResponse<Object> getReqList(@RequestParam String id, HttpSession session)
	 * { logger.info("Method : getReqList starts" + id); JsonResponse<Object> res =
	 * new JsonResponse<Object>();
	 * 
	 * String userId = ""; String orgName = ""; String orgDivision = ""; try {
	 * userId = (String) session.getAttribute("USER_ID"); orgName = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * e.printStackTrace(); } try { res = restTemplate.getForObject(
	 * env.getPurchaseUrl() + "getReqList?id=" + id + "&org=" + orgName + "&orgDiv="
	 * + orgDivision, JsonResponse.class); } catch (Exception e) {
	 * e.printStackTrace(); } if (res.getMessage() != null) {
	 * res.setCode(res.getMessage()); res.setMessage("Unsuccess"); } else {
	 * res.setMessage("success"); } logger.info("Method : getReqList ends"); return
	 * res; }
	 */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping(value = { "project-execution-requisitionData" })
	 * public @ResponseBody JsonResponse<Object> getReqData(@RequestParam String id,
	 * HttpSession session) { logger.info("Method : getReqData starts" + id);
	 * JsonResponse<Object> res = new JsonResponse<Object>();
	 * 
	 * String userId = ""; String orgName = ""; String orgDivision = ""; try {
	 * userId = (String) session.getAttribute("USER_ID"); orgName = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * e.printStackTrace(); } try { res = restTemplate.getForObject(
	 * env.getProjects() + "get-requisitionData?id=" + id + "&org=" + orgName +
	 * "&orgDiv=" + orgDivision, JsonResponse.class); } catch (Exception e) {
	 * e.printStackTrace(); } if (res.getMessage() != null) {
	 * res.setCode(res.getMessage()); res.setMessage("Unsuccess"); } else {
	 * res.setMessage("success"); } logger.info("Method : getReqData ends"); return
	 * res; }
	 */

	@GetMapping(value = { "project-execution-get-createReq" })
	public @ResponseBody List<PurchaseOrderModel> getReqisition(@RequestParam String id, HttpSession session) {
		logger.info("Method : getReqisition starts");
//			String orgName = "";
//			String orgDivision = "";
//			try {
//
//				orgName = (String) session.getAttribute("ORGANIZATION");
//				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
//			} catch (Exception e) {
//
//			}

		List<PurchaseOrderModel> productList = new ArrayList<PurchaseOrderModel>();
		if (id != null && id != "") {
			logger.info("IDD" + id);
			try {
				PurchaseOrderModel[] purchaseOrderModel = restTemplate
						.getForObject(env.getProjects() + "rest-getReqisition?id=" + id, PurchaseOrderModel[].class);
				productList = Arrays.asList(purchaseOrderModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : getReqisition ends");
		logger.info("edit@@@@@@@@" + productList);
		return productList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-calculationdatetime")
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

	/*
	 * Item autosearch
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "project-execution-requisition-item-get-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchList(Model model,
			@RequestBody String searchValue,@RequestParam String projectId, BindingResult result) {
		logger.info("Method : getItemQuotationAutoSearchListForItem starts");
		logger.info("QuotationNewModel" + searchValue);
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restTemplate.getForObject(
					env.getSalesUrl() + "getItemQuotationAutoSearchListForItem?id=" + searchValue+"&projectId="+projectId,
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
		logger.info("RESPONSE@@@@@@@@@@@@@@@@@@@@" + res);
		logger.info("Method : getItemQuotationAutoSearchList ends");
		return res;
	}

	/*
	 * Add
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("project-execution-requisition-add")
	public @ResponseBody JsonResponse<Object> addIndentDetails(HttpSession session,
			@RequestBody List<PurchaseIndentModel> purchaseModel) {
		logger.info("Method : addIndentDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<PurchaseIndentModel> documentList = new ArrayList<PurchaseIndentModel>();
		List<InventoryVendorDocumentModel> docList = new ArrayList<InventoryVendorDocumentModel>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		for (PurchaseIndentModel m : purchaseModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}

		for (InventoryVendorDocumentModel a : purchaseModel.get(0).getDocumentList()) {

			if (a.getImageNameEdit() != null && a.getImageNameEdit() != "") {
				a.setFileName(a.getImageNameEdit());
			} else {
				if (a.getFileName() != null && a.getFileName() != "") {
					String[] extension = a.getFileName().split("\\.");
					int lastindex = extension.length - 1;
					for (String s1 : a.getDocumentFile()) {
						try {
							byte[] bytes = Base64.getDecoder().decode(s1);
							logger.info("bytes" + s1);
							String imageName = saveAllMultiImages(bytes, extension[lastindex]);
							a.setFileName(imageName);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}
				}
			}
		}

		try {

			resp = restTemplate.postForObject(env.getPurchaseUrl() + "addIndentDetails", purchaseModel,
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

		logger.info("Method : addIndentDetails ends");

		return resp;
	}

	public String saveAllMultiImages(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllMultiImages starts");
		String imageName1 = null;
		try {
			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName1 = nowTime + ".jpg";
				} else {
					imageName1 = nowTime + "." + ext;
				}
			}
			Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName1);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllMultiImages ends");
		return imageName1;
	}

	/*
	 * edit
	 */

	@GetMapping(value = { "project-execution-requisition-edit-new" })
	public @ResponseBody List<PurchaseIndentModel> viewPurchaseIndentEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewPurchaseIndentEdit starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<PurchaseIndentModel> productList = new ArrayList<PurchaseIndentModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				PurchaseIndentModel[] purchaseOrderModel = restTemplate.getForObject(env.getPurchaseUrl()
						+ "viewPurchaseIndentEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PurchaseIndentModel[].class);

				productList = Arrays.asList(purchaseOrderModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (PurchaseIndentModel m : purchaseOrderModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
						m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));

					}
//					if (m.getReceiveDate() != null && m.getReceiveDate() != "") {
//						m.setReceiveDate(DateFormatter.dateFormat(m.getReceiveDate(), dateFormat));
//
//					}

				}
				if (productList != null) {
					documentList = productList.get(0).getDocumentList();
					if (documentList != null) {
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getFileName() != null && m.getFileName() != "") {

								String[] extension = m.getFileName().split("\\.");
								if (extension.length == 2) {
									if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

										String docPath = "<i class=\"fa fa-file-excel-o excel\" title= "
												+ m.getFileName() + "></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("pdf")) {
										String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title="
												+ m.getFileName() + " ;></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("doc") || extension[1].equals("dox")
											|| extension[1].equals("docx")) {
										String docPath = " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title="
												+ m.getFileName() + "></i> ";
										m.setAction(docPath);
									}
									if (extension[1].equals("png") || extension[1].equals("jpg")
											|| extension[1].equals("jpeg")) {
										String docPath = " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title="
												+ m.getFileName() + "></i>  ";
										m.setAction(docPath);
									}
								} else {
									m.setAction("N/A");
								}
							} else {
								m.setAction("N/A");
							}
							m.setAction("<a href=\"/document/document/" + m.getFileName() + "\" target=\"_blank\" >"
									+ m.getAction() + "</a>");
							logger.info("m.setAction" + m);

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewPurchaseIndentEdit ends");

		return productList;
	}
//View Monitoring		

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-monitoring-view")
	public @ResponseBody Object monitoringView(HttpSession session, @RequestParam String id) {

		logger.info("Method :monitoringView starts");
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
			resp = restClient.getForObject(env.getProjects() + "rest-monitoringView" + "?userid=" + userId + "&org="
					+ organization + "&div=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("monitoringView--" + resp);
		logger.info("Method :monitoringView ends");

		return resp;
	}

	// call Reqisition Through Project

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-Requision")
	public @ResponseBody Object viewProjectRequision(HttpSession session, @RequestParam String id) {

		logger.info("Method :viewProjectRequision starts");
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
			resp = restClient.getForObject(env.getProjects() + "rest-viewProjectRequisions" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectRequision--" + resp);
		logger.info("Method :viewProjectRequision ends");

		return resp;
	}

	// call Po Through Project

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-Po")
	public @ResponseBody Object viewProjectPo(HttpSession session, @RequestParam String id) {

		logger.info("Method :viewProjectPo starts");
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
			resp = restClient.getForObject(env.getProjects() + "rest-viewProjectPos" + "?userid=" + userId + "&org="
					+ organization + "&div=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectPo--" + resp);
		logger.info("Method :viewProjectPo ends");

		return resp;
	}

	/*
	 * edit Po
	 */

	@GetMapping(value = { "project-execution-po-edit-new" })
	public @ResponseBody List<PurchaseOrderModel> viewPoEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewPoEdit starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		List<PurchaseOrderModel> productList = new ArrayList<PurchaseOrderModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				PurchaseOrderModel[] purchaseOrderModel = restTemplate.getForObject(
						env.getPurchaseUrl() + "viewPoEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PurchaseOrderModel[].class);

				productList = Arrays.asList(purchaseOrderModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (PurchaseOrderModel m : purchaseOrderModel) {
					// m.setQuantitynew(m.getQuantity());
					System.err.println("DATE" + m.getGrnId());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getGrnDate() != null && m.getGrnDate() != "") {
						m.setGrnDate(DateFormatter.dateFormat(m.getGrnDate(), dateFormat));

					}

				}
				if (productList != null) {
					documentList = productList.get(0).getDocumentList();
					if (documentList != null) {
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getFileName() != null && m.getFileName() != "") {

								String[] extension = m.getFileName().split("\\.");
								if (extension.length == 2) {
									if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

										String docPath = "<i class=\"fa fa-file-excel-o excel\" title= "
												+ m.getFileName() + "></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("pdf")) {
										String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title="
												+ m.getFileName() + " ;></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("doc") || extension[1].equals("dox")
											|| extension[1].equals("docx")) {
										String docPath = " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title="
												+ m.getFileName() + "></i> ";
										m.setAction(docPath);
									}
									if (extension[1].equals("png") || extension[1].equals("jpg")
											|| extension[1].equals("jpeg")) {
										String docPath = " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title="
												+ m.getFileName() + "></i>  ";
										m.setAction(docPath);
									}
								} else {
									m.setAction("N/A");
								}
							} else {
								m.setAction("N/A");
							}
							m.setAction("<a href=\"/document/document/" + m.getFileName() + "\" target=\"_blank\" >"
									+ m.getAction() + "</a>");
							logger.info("m.setAction" + m);

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Method : viewPoEdit ends");
		System.err.println("DATA" + productList);
		return productList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "project-execution-getReferenceList" })
	public @ResponseBody JsonResponse<Object> getReferenceList(@RequestParam String id, HttpSession session) {
		logger.info("Method : getReferenceList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();

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
		try {
			res = restTemplate.getForObject(
					env.getPurchaseUrl() + "getReferenceList?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
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
		logger.info("Method : getReferenceList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-get-address")
	public @ResponseBody JsonResponse<VendorNewModel> getVendorAddress(@RequestParam String id, HttpSession session) {

		logger.info("Method : getCustomerAddress starts");

		JsonResponse<VendorNewModel> jsonResponse = new JsonResponse<VendorNewModel>();

		try {
			jsonResponse = restTemplate.getForObject(env.getPurchaseUrl() + "getVendorAddressAddressById?id=" + id,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		VendorNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<VendorNewModel>() {
		});

		jsonResponse.setBody(reimModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : getVendorAddress ends");

		return jsonResponse;
	}

	// call Grn Through Project

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-grn")
	public @ResponseBody Object viewProjectGrn(HttpSession session, @RequestParam String id) {

		logger.info("Method :viewProjectGrn starts");
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
			resp = restClient.getForObject(env.getProjects() + "rest-viewProjectGrn" + "?userid=" + userId + "&org="
					+ organization + "&div=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectGrn--" + resp);
		logger.info("Method :viewProjectGrn ends");

		return resp;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "project-execution-polist" })
	public @ResponseBody JsonResponse<Object> getPoList(@RequestParam String id, String type, HttpSession session) {
		logger.info("Method : getPoList starts" + id);
		logger.info("Method : getPoList starts" + type);

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		logger.info("Method : getPoList starts" + orgName);
		logger.info("Method : getPoList starts" + orgDivision);
		try {
			res = restTemplate.getForObject(env.getPurchaseUrl() + "getPoList?id=" + id + "&type=" + type + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getPoList ends");
		return res;
	}

	/*
	 * edit GRn
	 */

	@GetMapping(value = { "project-execution-grn-edit-new" })
	public @ResponseBody List<ManageInvoiceModel> viewInvoiteEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method : viewInvoiteEdit starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		List<ManageInvoiceModel> productList = new ArrayList<ManageInvoiceModel>();
		List<InventoryVendorDocumentModel> documentList = new ArrayList<InventoryVendorDocumentModel>();
		if (id != null && id != "") {
			try {
				ManageInvoiceModel[] manageInvoiceModel = restTemplate.getForObject(env.getPurchaseUrl()
						+ "viewInvoiteEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						ManageInvoiceModel[].class);

				productList = Arrays.asList(manageInvoiceModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (ManageInvoiceModel m : manageInvoiceModel) {
					// m.setQuantitynew(m.getQuantity());
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					/*
					 * if (m.getReferenceDate() != null && m.getReferenceDate() != "") {
					 * m.setReferenceDate(DateFormatter.dateFormat(m.getReferenceDate(),
					 * dateFormat));
					 * 
					 * } if (m.getExptdeliveryDate() != null && m.getExptdeliveryDate() != "") {
					 * m.setExptdeliveryDate(DateFormatter.dateFormat(m.getExptdeliveryDate(),
					 * dateFormat));
					 * 
					 * } if (m.getChallanDate() != null && m.getChallanDate() != "") {
					 * m.setChallanDate(DateFormatter.dateFormat(m.getChallanDate(), dateFormat));
					 * 
					 * }
					 */
				}
				if (productList != null) {
					documentList = productList.get(0).getDocumentList();
					if (documentList != null) {
						for (InventoryVendorDocumentModel m : documentList) {
							if (m.getFileName() != null && m.getFileName() != "") {

								String[] extension = m.getFileName().split("\\.");
								if (extension.length == 2) {
									if (extension[1].equals("xls") || extension[1].equals("xlsx")) {

										String docPath = "<i class=\"fa fa-file-excel-o excel\" title= "
												+ m.getFileName() + "></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("pdf")) {
										String docPath = " <i class=\"fa fa-file-pdf-o excel pdf\"   title="
												+ m.getFileName() + " ;></i> ";

										m.setAction(docPath);
									}
									if (extension[1].equals("doc") || extension[1].equals("dox")
											|| extension[1].equals("docx")) {
										String docPath = " <i class=\"fa fa-file-word-o \" aria-hidden=\"true\"  title="
												+ m.getFileName() + "></i> ";
										m.setAction(docPath);
									}
									if (extension[1].equals("png") || extension[1].equals("jpg")
											|| extension[1].equals("jpeg")) {
										String docPath = " <i class=\"fa fa-picture-o \"\" aria-hidden=\"true\" title="
												+ m.getFileName() + "></i>  ";
										m.setAction(docPath);
									}
								} else {
									m.setAction("N/A");
								}
							} else {
								m.setAction("N/A");
							}
							m.setAction("<a href=\"/document/document/" + m.getFileName() + "\" target=\"_blank\" >"
									+ m.getAction() + "</a>");
							logger.info("m.setAction" + m);

						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("Method : viewInvoiteEdit ends" + productList);

		return productList;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-ganttChartList")
	public @ResponseBody Object ganttChartList(@RequestParam String id, HttpSession session) {

		logger.info("Method :ganttChartList starts");
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

			resp = restClient.getForObject(env.getProjects() + "rest-ganttChartList?id=" + id + "&userid=" + userId
					+ "&org=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("ganttChartList--" + resp);
		logger.info("Method :ganttChartList ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "project-report-view" })
	public String projectExecutionReportView(Model model, HttpSession session) {
		logger.info("Method : projectExecutionReportView starts");

		String user_name = "";
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			user_name = (String) session.getAttribute("USER_NAME");
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("user_short_name", getShortName(user_name));

		try {
			
			JsonResponse<Object> resp = new JsonResponse<Object>();
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
			JsonResponse<Object> resp = new JsonResponse<Object>();
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

		logger.info("Method : projectExecutionReportView ends");
		return "projects/projectReportView";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("project-report-view-list")
	public @ResponseBody Object viewProjectList(HttpSession session) {
		logger.info("Method :viewProjectList starts");

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

		logger.info("Method :viewProjectList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("project-report-view-task-list")
	public @ResponseBody Object getAllProjectTaskDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method :getAllProjectTaskDetails starts");

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

			resp = restClient.getForObject(env.getProjects() + "rest-getAllProjectExecutionDetails?id=" + id
					+ "&userid=" + userId + "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllProjectTaskDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("project-report-view-task-comment")
	public @ResponseBody Object getAllTaskComments(@RequestParam String id, HttpSession session) {
		logger.info("Method :getAllTaskComments starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getProjects() + "rest-getComments?id=" + id + "&org=" + organization
					+ "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getAllTaskComments ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("project-report-view-save-comment")
	public @ResponseBody Object saveTaskComments(@RequestBody ProjectMessageModel data, HttpSession session) {
		logger.info("Method :saveTaskComments starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userid = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userid = (String) session.getAttribute("USER_ID");

			data.setOrg(organization);
			data.setOrgdiv(orgDivision);
			data.setUserid(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-saveTaskComments", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :saveTaskComments ends");
		return resp;
	}

	public String getShortName(String name) {
		String val = "";
		String[] namelist = name.split(" ");
		String s = Arrays.stream(namelist).map(m -> m.length() > 1 ? m.substring(0, 1) : m)
				.collect(Collectors.joining());
		val = (s != "" && s != null) ? s.length() > 2 ? s.substring(0, 2).toUpperCase() : s.toUpperCase() : "";
		return val;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "project-execution-requisition-get-stockData" })
	public @ResponseBody JsonResponse<Object> getStockData(@RequestParam String id,String id1,HttpSession session) {
		logger.info("Method : getStockData starts"+ id);
		JsonResponse<Object> resp = new JsonResponse<Object>();


		try {		
		
			resp = restTemplate.getForObject(env.getInventoryUrl() + "rest-getStockDatas?id=" + id + "&id1=" + id1,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method : getStockData ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("project-execution-billDetails")
	public void getProjectBillPdfView(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("idd") String encodedParam1)
			throws JsonParseException, JsonMappingException, JSONException, IOException {

		logger.info("Method : getProjectBillPdfView starts");
		String orgName = "";
		String orgDivision = "";
		String userid = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userid = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String prjId = (new String(encodeByte3));


		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
logger.info("sssss"+env.getProjects() + "rest-getProjectBillPdfView?id=" + prjId + "&userid="
		+ userid + "&orgName=" + orgName + "&div=" + orgDivision, JsonResponse.class);

			resp = restClient.getForObject(env.getProjects() + "rest-getProjectBillPdfView?id=" + prjId + "&userid="
					+ userid + "&orgName=" + orgName + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("tefeg" + resp);
		Map<String, Object> data = new HashMap<String, Object>();

		System.out.println("Get Data === " + resp.getBody());
		
		
		

		if (resp.getBody() == null) {
			System.out.println("JSON Obj === " + resp.getBody());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=ProjectBill.pdf");
			File file;
			byte[] fileData = null;

			try {
				file = pdfGeneratorUtil.createPdf("projects/projects-billng.html", data);
				InputStream in = new FileInputStream(file);
				fileData = IOUtils.toByteArray(in);
				response.setContentLength(fileData.length);
				response.getOutputStream().write(fileData);
				response.getOutputStream().flush();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		JSONObject jsonResponseObject = new JSONObject(resp.getBody().toString());
		logger.info("jsonArray" + jsonResponseObject);

		// Extract the "viewIssueReportData" field as a JSONArray
		JSONArray jsonArray = jsonResponseObject.getJSONArray("viewReportData");

		System.out.println("JSON Array === " + jsonArray);

		// JSONObject jsonobj = jsonArray.getJSONObject(0);

		if (jsonArray != null) {
			System.out.println("ddddddddd" + jsonArray.toString());

			ObjectMapper mapper1 = new ObjectMapper();

			List<ProjectExecutionModel> dataa = mapper1.readValue(jsonArray.toString(),
					new TypeReference<List<ProjectExecutionModel>>() {
					});
			logger.info("dataa" + dataa);

			data.put("respdata", dataa);

		}
		System.out.println("JSON Obj === " + jsonArray);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=ProjectBill.pdf");
		File file;
		byte[] fileData = null;
		
		try {
			file = pdfGeneratorUtil.createPdf("projects/projects-billng.html", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		logger.info("Method : getProjectBillPdfView ends");
	}
}
