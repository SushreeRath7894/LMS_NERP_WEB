package nirmalya.aathithya.webmodule.projects.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.master.model.EventActivityModel;
import nirmalya.aathithya.webmodule.master.model.EventManagementModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectAgendaModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectAttendeeModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectExecutionModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectMonitoringWebModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseIndentModel;
import nirmalya.aathithya.webmodule.qa.model.QcMasterModel;

@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "projects/")

public class ProjectMonitoringWebController {

	Logger logger = LoggerFactory.getLogger(ProjectMonitoringWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/monitoring-control")
	public String projectMontoringControl(Model model, HttpSession session) {

		logger.info("Method : projectMontoringControl starts");

		logger.info("Method : projectMontoringControl ends");
		return "projects/project-monitoring-control";

	}

	// ***************************************************PROJECT HEALTH CRUD
	// STARTS***************************************//

	// add monitoring-control

	@SuppressWarnings("unchecked")
	@PostMapping("monitoring-control-add")
	public @ResponseBody JsonResponse<Object> addMonitoringControl(
			@RequestBody ProjectMonitoringWebModel MonitoringControl, HttpSession session) {
		logger.info("Method : addMonitoringControl starts");

		// Print the received MonitoringControl object

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

		MonitoringControl.setCreatedBy(userId);
		MonitoringControl.setOrganizationName(orgName);
		MonitoringControl.setOrganizationDivision(orgDiv);

		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				MonitoringControl.setAttachmentId(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-monitoring-control-add", MonitoringControl,
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

		logger.info("Method : addMonitoringControl ends");
		return resp;
	}

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
		}
		logger.info("Method : saveAllImage ends");
		return imageName;

	}

	/*
	 * save image
	 */

	@PostMapping("monitoring-control-upload-file")
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
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}

	// view monitoring-control

	// view
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("/monitoring-control-view") public @ResponseBody
	 * JsonResponse<List<ProjectExecutionModel>> getAllProjectMonitoringDetails(
	 * 
	 * @RequestBody ProjectExecutionModel category, HttpSession session) {
	 * logger.info("Method : getAllProjectMonitoringDetails starts" + category);
	 * 
	 * JsonResponse<List<ProjectExecutionModel>> resp = new
	 * JsonResponse<List<ProjectExecutionModel>>(); String userId = ""; String
	 * organization = ""; String orgDivision = ""; try { userId = (String)
	 * session.getAttribute("USER_ID"); organization = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * e.printStackTrace(); } try { resp = restClient .getForObject(
	 * env.getProjects() + "rest-getAllProjectMonitoringDetails?id=" +
	 * category.getProjectId() + "&userid=" + userId + "&org=" + organization +
	 * "&div=" + orgDivision, JsonResponse.class); } catch (RestClientException e) {
	 * e.printStackTrace(); }
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
	 * } else { resp.setCode("success"); }
	 * logger.info("Method : getAllProjectMonitoringDetails starts" + resp); return
	 * resp; }
	 */
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-view-execution")
	public @ResponseBody Object getAllProjectExecutionDetails(@RequestParam String id,
			HttpSession session) {

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
					+ "&userid=" + userId+"&org="+organization+"&div="+orgDivision,
					JsonResponse.class);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getAllProjectExecutionDetails--" + resp);
		logger.info("Method :getAllProjectExecutionDetails ends");

		return resp;
	}
	
	//planningdata
		@SuppressWarnings("unchecked")
		@GetMapping("monitoring-control-planning-list")
		public @ResponseBody Object getplanningList(@RequestParam String id,
				HttpSession session) {

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
				resp = restClient.getForObject(env.getProjects() + "rest-getplanningList?id=" + id
						+ "&userid=" + userId+"&org="+organization+"&div="+orgDivision,
						JsonResponse.class);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("getplanningList--" + resp);
			logger.info("Method :getplanningList ends");

			return resp;
		}
		

	// edit monitoring-control

	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-edit")
	public @ResponseBody JsonResponse<ProjectMonitoringWebModel> editMonitoringControl(@RequestParam String id,
			Model model, HttpSession session) {

		logger.info("Method : editMonitoringControl starts");

		JsonResponse<ProjectMonitoringWebModel> jsonResponse = new JsonResponse<ProjectMonitoringWebModel>();
		try {
			jsonResponse = restClient.getForObject(env.getProjects() + "rest-monitoring-control-edit?id=" + id,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			ProjectMonitoringWebModel editMonitoring = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<ProjectMonitoringWebModel>() {
					});
			String profile = null;
			if (editMonitoring.getAttachmentId() != null && editMonitoring.getAttachmentId() != ""
					&& !editMonitoring.getAttachmentId().equals("null")) {

				profile = env.getBaseURL() + "document/document/" + editMonitoring.getAttachmentId();
				editMonitoring.setAttachmentId(profile);
			}

			jsonResponse.setBody(editMonitoring);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : editMonitoringControl ends" + jsonResponse);
		return jsonResponse;
	}

	// delete monitoring-control

	@SuppressWarnings("unchecked")
	@PostMapping("monitoring-control-delete")
	public @ResponseBody JsonResponse<Object> deleteMonitoringControl(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteMonitoringControl function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-monitoring-control-delete?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteMonitoringControl function Ends");

		return res;
	}

	// ***********************************PROJECT HEALTH CRUD
	// ENDS************************************//

	// ***********************************RUNNING PROJECT VIEW
	// STARTS********************************//

	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-project-view")
	public @ResponseBody List<ProjectMonitoringWebModel> viewProjectTbl(HttpSession session) {
		logger.info("Method : viewProjectTbl starts");

		JsonResponse<List<ProjectMonitoringWebModel>> resp = new JsonResponse<List<ProjectMonitoringWebModel>>();
		List<ProjectMonitoringWebModel> returnList = new ArrayList<ProjectMonitoringWebModel>();

		String userid = (String) session.getAttribute("USER_ID");

		try {
			resp = restClient.getForObject(env.getProjects() + "rest-monitoring-control-project-view?userid=" + userid,
					JsonResponse.class);
			returnList = resp.getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewProjectTbl ends" + returnList);
		return returnList;
	}

	// ***********************************RUNNING PROJECT VIEW
	// ENDS********************************//

	// ***********************************FORECASTING CRUD
	// STARTS**********************************//

	// add monitoring-control-forecasting

	@SuppressWarnings("unchecked")
	@PostMapping("monitoring-control-forecasting-add")
	public @ResponseBody JsonResponse<Object> addMonitoringControlforecasting(
			@RequestBody ProjectMonitoringWebModel MonitoringControlForecasting, HttpSession session) {
		logger.info("Method : addMonitoringControlforecasting starts");

		// Print the received MonitoringControl object

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

		MonitoringControlForecasting.setCreatedBy(userId);
		MonitoringControlForecasting.setOrganizationName(orgName);
		MonitoringControlForecasting.setOrganizationDivision(orgDiv);

		MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
		byte[] bytes;
		String imageName = null;

		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);
				MonitoringControlForecasting.setAttachmentId(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		try {
			resp = restClient.postForObject(env.getProjects() + "monitoring-control-forecasting-add",
					MonitoringControlForecasting, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addMonitoringControlforecasting ends");
		return resp;
	}

	// view monitoring-control-forecasting

	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-forecasting-view")
	public @ResponseBody List<ProjectMonitoringWebModel> viewMonitoringControlforecasting(HttpSession session) {
		logger.info("Method: viewMonitoringControlforecasting starts");
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<ProjectMonitoringWebModel>> resp = new JsonResponse<List<ProjectMonitoringWebModel>>();
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-monitoring-control-forecasting-view?org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<ProjectMonitoringWebModel> projectMonitoringControlforecasting = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ProjectMonitoringWebModel>>() {
				});

		resp.setBody(projectMonitoringControlforecasting);

		if (resp.getMessage() != null && !resp.getMessage().isEmpty()) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method: viewMonitoringControlforecasting ends");
		return projectMonitoringControlforecasting;
	}

	// edit monitoring-control-forecasting

	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-forecasting-edit")
	public @ResponseBody JsonResponse<ProjectMonitoringWebModel> editMonitoringControlforecasting(
			@RequestParam String id, Model model, HttpSession session) {

		logger.info("Method : editMonitoringControlforecasting starts");

		JsonResponse<ProjectMonitoringWebModel> jsonResponse = new JsonResponse<ProjectMonitoringWebModel>();
		try {
			jsonResponse = restClient.getForObject(
					env.getProjects() + "rest-monitoring-control-forecasting-edit?id=" + id, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			ProjectMonitoringWebModel editMonitoringForecasting = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<ProjectMonitoringWebModel>() {
					});

			jsonResponse.setBody(editMonitoringForecasting);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : editMonitoringControlforecasting ends" + jsonResponse);
		return jsonResponse;
	}

	// delete monitoring-control

	@SuppressWarnings("unchecked")
	@PostMapping("monitoring-control-forecasting-delete")
	public @ResponseBody JsonResponse<Object> deleteMonitoringControlForecasting(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteMonitoringControlforecasting function starts");

		// Print the 'id' parameter value to the console

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-monitoring-control-forecasting-delete?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteMonitoringControlforecasting function Ends");

		return res;
	}

	// ***********************************FORECASTING CRUD
	// ENDS**********************************//

	// *******************************PROJECT CATCH-UP PLAN
	// STARTS******************************//

	// add catchUp

	@SuppressWarnings("unchecked")
	@PostMapping(value = "monitoring-control-catch-up-add")
	public @ResponseBody JsonResponse<Object> addCatchUp(@RequestBody ProjectMonitoringWebModel projectManagementModel,
			Model model, HttpSession session) {
		logger.info("Method :addCatchUp starts");
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organizationName = "";
		String organizationDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organizationName = (String) session.getAttribute("ORGANIZATION");
			organizationDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<ProjectAttendeeModel> attendee = projectManagementModel.getAttendee();
		for (ProjectAttendeeModel attendeeModel : attendee) {
		}
		List<ProjectAgendaModel> agenda = projectManagementModel.getAgenda();
		for (ProjectAgendaModel agendaModel : agenda) {
		}

		projectManagementModel.setCreatedBy(userId);
		projectManagementModel.setOrganizationName(organizationName);
		projectManagementModel.setOrganizationDivision(organizationDivision);

		try {
			jsonResponse = restClient.postForObject(env.getProjects() + "rest-add-catchup", projectManagementModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = jsonResponse.getMessage();
		if (message != null && message != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : addCatchUp ends");
		return jsonResponse;
	}

	// view catchUp
	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-catchup-view")
	public @ResponseBody List<ProjectMonitoringWebModel> viewCatchUp(HttpSession session) {
		logger.info("Method: viewCatchUp starts");
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<ProjectMonitoringWebModel>> resp = new JsonResponse<List<ProjectMonitoringWebModel>>();
		try {
			resp = restClient.getForObject(
					env.getProjects() + "rest-view-catchup?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<ProjectMonitoringWebModel> projectCatchUp = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ProjectMonitoringWebModel>>() {
				});

		resp.setBody(projectCatchUp);

		if (resp.getMessage() != null && !resp.getMessage().isEmpty()) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method: viewCatchUp ends");
		return projectCatchUp;
	}

	// edit catchUp
	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-catchup-edit")
	public @ResponseBody JsonResponse<ProjectMonitoringWebModel> editCatchUp(@RequestParam String id, Model model,
			HttpSession session) {

		logger.info("Method : editCatchUp starts>>>>>>>>>>>>>>>>");

		// Print the retrieved id

		JsonResponse<ProjectMonitoringWebModel> jsonResponse = new JsonResponse<ProjectMonitoringWebModel>();
		try {
			jsonResponse = restClient.getForObject(env.getProjects() + "rest-edit-catchup?id=" + id,
					JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			ProjectMonitoringWebModel editCatchUp = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<ProjectMonitoringWebModel>() {
					});

			jsonResponse.setBody(editCatchUp);

			// Print the retrieved data

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : editCatchUp ends>>>>>>>>>>>>>>>>>>>>>>>" + jsonResponse);
		return jsonResponse;
	}

	// delete catchUp
	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-catchup-delete")
	public @ResponseBody JsonResponse<Object> deleteCatchUp(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteCatchUp function starts");

		// Print the received 'id' parameter

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-delete-catchup?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {


		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteCatchUp function Ends");


		return res;
	}

	// *******************************PROJECT CATCH-UP PLAN
	// ENDS******************************//

	// *******************************PROJECT HEALTH NOTES CRUD
	// STARTS******************************//

	// add monitoring-control-project-health-notes-add

	@SuppressWarnings("unchecked")
	@PostMapping("monitoring-control-projecthealth-notes-add")
	public @ResponseBody JsonResponse<Object> addMonitoringControlNotes(
			@RequestBody ProjectMonitoringWebModel MonitoringControlNotes, HttpSession session) {
		logger.info("Method : addMonitoringControlNotes starts");

		// Print the received MonitoringControl object

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

		MonitoringControlNotes.setCreatedBy(userId);
		MonitoringControlNotes.setOrganizationName(orgName);
		MonitoringControlNotes.setOrganizationDivision(orgDiv);

		try {
			resp = restClient.postForObject(env.getProjects() + "rest-notes-add", MonitoringControlNotes,
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

		logger.info("Method : addMonitoringControlNotes ends");
		return resp;
	}

	// view notes

	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-projecthealth-notes-view")
	public @ResponseBody List<ProjectMonitoringWebModel> viewNotes(HttpSession session) {
		logger.info("Method: viewNotes starts");
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<List<ProjectMonitoringWebModel>> resp = new JsonResponse<List<ProjectMonitoringWebModel>>();
		try {
			resp = restClient.getForObject(
					env.getProjects() + "rest-notes-view?org=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<ProjectMonitoringWebModel> projectNotes = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ProjectMonitoringWebModel>>() {
				});

		resp.setBody(projectNotes);

		if (resp.getMessage() != null && !resp.getMessage().isEmpty()) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		// Log the retrieved projectNotes
		logger.info("Retrieved projectNotes: " + projectNotes);

		logger.info("Method: viewNotes ends");
		return projectNotes;
	}

	// edit notes

	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-notes-edit")
	public @ResponseBody JsonResponse<ProjectMonitoringWebModel> editNotes(@RequestParam String id, Model model,
			HttpSession session) {

		logger.info("Method : editNotes starts");

		JsonResponse<ProjectMonitoringWebModel> jsonResponse = new JsonResponse<ProjectMonitoringWebModel>();
		try {
			jsonResponse = restClient.getForObject(env.getProjects() + "rest-notes-edit?id=" + id, JsonResponse.class);

			ObjectMapper mapper = new ObjectMapper();
			ProjectMonitoringWebModel editProjectNotes = mapper.convertValue(jsonResponse.getBody(),
					new TypeReference<ProjectMonitoringWebModel>() {
					});

			jsonResponse.setBody(editProjectNotes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}


		logger.info("Method : editNotes ends" + jsonResponse);
		return jsonResponse;
	}

	// delete notes
	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-notes-delete")
	public @ResponseBody JsonResponse<Object> deleteNotes(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteNotes function starts");

		// Print the received 'id' parameter

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-notes-delete?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

			// Print the message field

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteNotes function Ends");

		// Print the whole response object

		return res;
	}

	// *******************************PROJECT HEALTH NOTES CRUD
	// ENDS******************************//

	// edit Parent
	@SuppressWarnings("unchecked")
	@PostMapping("/monitoring-control-category-dtls-by-id")
	public @ResponseBody JsonResponse<ProjectExecutionModel> getMonitorParentEdit(
			@RequestBody ProjectExecutionModel model, HttpSession session) {
		logger.info("Method : getProjectParentEdit starts");

		JsonResponse<ProjectExecutionModel> resp = new JsonResponse<ProjectExecutionModel>();

		try {
			resp = restClient.getForObject(env.getProjects() + "rest-getMonitorParentEdit?id=" + model.getCategoryId()
					+ "&pid=" + model.getProjectId(), JsonResponse.class);
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

	// edit child
	@SuppressWarnings("unchecked")
	@PostMapping("/monitoring-control-get-category-dtls-by-id-child")
	public @ResponseBody JsonResponse<ProjectExecutionModel> getMonitorChildEdit(
			@RequestBody ProjectExecutionModel model, HttpSession session) {
		logger.info("Method : getMonitorChildEdit starts");

		JsonResponse<ProjectExecutionModel> resp = new JsonResponse<ProjectExecutionModel>();
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
			resp = restClient.getForObject(env.getProjects() + "rest-getMonitorChildEdit?id=" + model.getCategoryId()
					+ "&pid=" + model.getProjectId() + "&userId=" + userId + "&orgName=" + organization + "&orgDiv="
					+ orgDivision, JsonResponse.class);
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

		logger.info("Method : getMonitorChildEdit ends" + resp);
		return resp;
	}
//view project

	@SuppressWarnings("unchecked")
	@GetMapping("monitoring-control-project-view-data")
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
	
	@GetMapping(value = { "monitoring-control-view-requisitionData" })
	public @ResponseBody List<PurchaseIndentModel> viewPurchaseRequisitionEdit(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : viewPurchaseRequisitionEdit starts");
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
				PurchaseIndentModel[] purchaseOrderModel = restClient.getForObject(env.getPurchaseUrl()
						+ "viewPurchaseRequisitionEdit?id=" + id + "&org=" + orgName + "&orgDiv=" + orgDivision,
						PurchaseIndentModel[].class);

				productList = Arrays.asList(purchaseOrderModel);

				productList.forEach(s -> s.setSlNo(s.getSlNo()));

				int count = 0;

				for (PurchaseIndentModel m : purchaseOrderModel) {
					count++;
					m.setSlNo(count);
					String dateFormat = (String) session.getAttribute("DATEFORMAT");

					if (m.getUpdatedOn() != null && m.getUpdatedOn() != "") {
						m.setUpdatedOn(DateFormatter.dateFormat(m.getUpdatedOn(), dateFormat));

					}
					if (m.getReceiveDate() != null && m.getReceiveDate() != "") {
						m.setReceiveDate(DateFormatter.dateFormat(m.getReceiveDate(), dateFormat));

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
		logger.info("Method : viewPurchaseRequisitionEdit ends");

		return productList;
		
	}

}