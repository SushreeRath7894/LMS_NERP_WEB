package nirmalya.aathithya.webmodule.employee.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.employee.model.ExitFinancialSettelmentModel;
import nirmalya.aathithya.webmodule.employee.model.ExtendExitManagementModel;
import nirmalya.aathithya.webmodule.master.model.PayslipModel;
import nirmalya.aathithya.webmodule.recruitment.model.OfferletterModel;
import nirmalya.aathithya.webmodule.user.model.UserRoleAssignModel;

@Controller
@RequestMapping(value = "employee/")
public class ExtendExitManagementController {

	Logger logger = LoggerFactory.getLogger(ExtendExitManagementController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("exit")
	public String addExitManagement(Model model, HttpSession session) {

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : addExitManagement starts");
		try {
			DropDownModel[] name = restClient.getForObject(env.getEmployeeUrl() + "getNamelist", DropDownModel[].class);
			List<DropDownModel> namelist = Arrays.asList(name);
			model.addAttribute("namelist", namelist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] emplist = restClient.getForObject(
					env.getEmployeeUrl() + "getEmployeeLists?orgName=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplists", emplists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dept = restClient.getForObject(
					env.getEmployeeUrl() + "getDeptlist?org=" + organization + "&orgDiv=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> deptlist = Arrays.asList(dept);
			model.addAttribute("deptlist", deptlist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] clrncPerson = restClient.getForObject(env.getEmployeeUrl() + "getClrncPersonList",
					DropDownModel[].class);
			List<DropDownModel> clrncPList = Arrays.asList(clrncPerson);
			model.addAttribute("clrncPList", clrncPList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String userId = "";
		String userName = "";
		String userRole = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", data);
				logger.info("data ==" + data);
			} else if (data.contentEquals("rol003")) {
				model.addAttribute("mrRole", data);
				logger.info("data 1==" + data);
			} else {
				model.addAttribute("empRole", data);
				logger.info("data 2==" + data);
			}
			if (data.contentEquals("rol001") || data.contentEquals("rol002") || data.contentEquals("rol025")) {
				model.addAttribute("hrRole", data);
			}
		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : addExitManagement ends");

		return "employee/exit-management";
	}

	/*
	 * dropdown for job through ajax
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "exit-emp-job-ajax" })
	public @ResponseBody JsonResponse<Object> getDesignationList(@RequestParam String name) {
		logger.info("Method : getDesignationList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "rest-get-designationList?id=" + name,
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

		logger.info("Method : getDesignationList ends");
		return res;

	}

	/*
	 * Add Exit Management
	 * 
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("exit-add-details")
	public @ResponseBody JsonResponse<Object> addExitManagement(@RequestBody ExtendExitManagementModel exitModel,
			HttpSession session) {
		logger.info("Method : addExitManagement starts");

		String dateFormat = "";
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}

		exitModel.setCreatedBy(userId);
		exitModel.setOrganization(organization);
		exitModel.setOrgDivision(orgDivision);

		if (exitModel.getReleaseDate() != null && exitModel.getReleaseDate() != "") {
			exitModel.setReleaseDate(DateFormatter.inputDateFormat(exitModel.getReleaseDate(), dateFormat));
		}
		if (exitModel.getResignDate() != null && exitModel.getResignDate() != "") {
			exitModel.setResignDate(DateFormatter.inputDateFormat(exitModel.getResignDate(), dateFormat));
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "addExitdetails", exitModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addExitManagement ends");
		return resp;
	}

	/*
	 * Add Clearance Details
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("exit-save-initiate")
	public @ResponseBody JsonResponse<Object> addClearanceDetails(@RequestBody ExtendExitManagementModel exitModel,
			HttpSession session) {

		logger.info("Method : addClearanceDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		exitModel.setCreatedBy(userId);
		exitModel.setOrganization(organization);
		exitModel.setOrgDivision(orgDivision);

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "addinitiatedata", exitModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : addClearanceDetails ends");

		return resp;
	}

	/*
	 * View Exit Management
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("exit-view-through-ajax")
	public @ResponseBody JsonResponse<Object> viewExitManagement(HttpSession session, @RequestParam String roleid) {

		logger.info("Method : viewExitClearanceEmployee starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userid = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userid = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "viewExitdetails?userId=" + userid + "&userRole="
					+ roleid + "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewExitManagement ends");
		return resp;
	}

	/*
	 * Edit Exit Management details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("exit-details-edit")
	public @ResponseBody JsonResponse<ExtendExitManagementModel> editExitManagement(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : editExitManagement starts");

		JsonResponse<ExtendExitManagementModel> jsonResponse = new JsonResponse<ExtendExitManagementModel>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "editExitManagement?id=" + id
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		ExtendExitManagementModel exitModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<ExtendExitManagementModel>() {
				});

		jsonResponse.setBody(exitModel);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : editExitManagement ends");
		return jsonResponse;

	}

	/*
	 * Delete exit details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("exit-details-delete")
	public @ResponseBody JsonResponse<ExtendExitManagementModel> deleteExitDetails(@RequestParam String deleteId) {

		logger.info("Method : deleteExitDetails starts");

		JsonResponse<ExtendExitManagementModel> jsonResponse = new JsonResponse<ExtendExitManagementModel>();
		logger.info("deleteId===" + deleteId);
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "exitManagementdelete?id=" + deleteId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : deleteExitDetails ends");
		return jsonResponse;
	}

	/*
	 * Add Finance details
	 */
	@SuppressWarnings("unchecked")

	@PostMapping("exit-add-finance-details")
	public @ResponseBody JsonResponse<Object> addFinanceDetails(@RequestBody ExtendExitManagementModel exitModel,
			HttpSession session) {
		logger.info("Method : addFinanceDetails starts");
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

		exitModel.setCreatedBy(userId);
		exitModel.setOrganization(organization);
		exitModel.setOrgDivision(orgDivision);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "addFinancedetails", exitModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : addFinanceDetails ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "exit-get-deptClearanceDetails" })
	public @ResponseBody JsonResponse<Object> viewdeptClearanceDetails(HttpSession session,
			@RequestParam String userid) {
		logger.info("Method : viewdeptClearanceDetails starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "get-deptClearanceDetails?userid=" + userid,
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

		logger.info("Method : viewdeptClearanceDetails ends");

		logger.info("LISTTTT" + res);
		return res;

	}
	/*
	 * View Exit Clear
	 */

	@SuppressWarnings("unchecked")

	@GetMapping("exit-view-clearance-details")
	public @ResponseBody List<ExtendExitManagementModel> viewExitClearance(HttpSession session,
			@RequestParam String exitid) {

		logger.info("Method : viewExitClearance starts");
		JsonResponse<List<ExtendExitManagementModel>> resp = new JsonResponse<List<ExtendExitManagementModel>>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "viewExitClearance?userId=" + userId + "&exitid="
					+ exitid + "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewExitClearance ends");
		logger.info("viewExitClearance==" + resp.getBody());
		return resp.getBody();
	}

	/*
	 * Edit Exit clearance details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("exit-details-edit-clearance")
	public @ResponseBody JsonResponse<ExtendExitManagementModel> editClearance(@RequestParam String id,
			HttpSession session) {
		logger.info("Method : editClearance starts");

		JsonResponse<ExtendExitManagementModel> jsonResponse = new JsonResponse<ExtendExitManagementModel>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "editClearance?id=" + id + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");

		} else {
			jsonResponse.setMessage("Success");
		}

		logger.info("Method : editClearance ends");
		return jsonResponse;

	}

	/*
	 *
	 * Add Exit Proceed Details
	 *
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("exit-view-proceed-notice")
	public @ResponseBody JsonResponse<Object> addExitProceed(@RequestBody ExtendExitManagementModel exitModel,
			HttpSession session) {
		logger.info("Method : addExitProceed starts");
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

		exitModel.setCreatedBy(userId);
		exitModel.setOrganization(organization);
		exitModel.setOrgDivision(orgDivision);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "add-notice-proceed", exitModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addExitProceed ends");
		return resp;
	}

	/*
	 * Get Department Detail for choosen Js
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "exit-view-getdeptname" })
	public @ResponseBody JsonResponse<List<DropDownModel>> getDeptName(Model model, @RequestParam("id") String id,
			HttpSession session) {
		logger.info("Method :getDeptName starts");

		String organization = "";
		String orgDivision = "";

		organization = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		JsonResponse<List<DropDownModel>> res = new JsonResponse<List<DropDownModel>>();

		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "rest-exit-view-getdeptname?id=" + id
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getDeptName ends");
		return res;
	}

	/*
	 * Get clearance details
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "exit-view-get-clearanceStatus" })
	public @ResponseBody JsonResponse<Object> getClearanceDetails(Model model, @RequestParam String exitId,
			HttpSession session) {
		logger.info("Method :getClearanceDetails starts");

		String organization = "";
		String orgDivision = "";

		organization = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "rest-view-clearance-details?exitId=" + exitId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getClearanceDetails ends");
		return res;
	}

	/*
	 * View And Download Experience Letter
	 * 
	 */

	@SuppressWarnings({ "unchecked", "unused" })
	@GetMapping("/exit-view-experince-letter")
	public void generateExpLetterPDF(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("exitId") String encodedParam1, @RequestParam("type") String encodedParam2) {

		logger.info("Method: generateExpLetterPDF starts");

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String exitId = new String(encodeByte3);

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String type = new String(encodeByte1);

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper objectMapper = new ObjectMapper();
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "get-emp-expLetter?exitId=" + exitId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String employeeId = "";
		String pdfDetails = jsonResponse.getBody().toString();

		Map<String, Object> hashMapObject = null;

		try {
			List<Map<String, Object>> list = objectMapper.readValue(pdfDetails,
					new TypeReference<List<Map<String, Object>>>() {
					});

			hashMapObject = list.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<>();

		if (hashMapObject != null) {
			String candidateImage = (String) hashMapObject.get("candidateImage");

			String fileEmployeeimg = (candidateImage != null && !candidateImage.isEmpty())
					? env.getBaseURL() + "document/employee/" + candidateImage
					: null;
			String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
			URL getUrl = null;
			try {
				getUrl = new URL(logo);
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
			
			employeeId =  (String) hashMapObject.get("empID");
			
			data.put("logo", "data:image/png;base64," + encodedLogoUrl);

			data.put("empID", hashMapObject.get("empID"));
			data.put("empName", hashMapObject.get("empName"));
			data.put("empMotherName", hashMapObject.get("empMotherName"));
			data.put("empFatherName", hashMapObject.get("empFatherName"));
			data.put("designationId", hashMapObject.get("designationId"));
			data.put("designation", hashMapObject.get("designation"));
			data.put("joiningDate", hashMapObject.get("joiningDate"));
			data.put("releaseDate", hashMapObject.get("releaseDate"));
			data.put("releaseDateFormat", hashMapObject.get("releaseDateFormat"));
			data.put("deptName", hashMapObject.get("deptName"));
			data.put("orgDivision", hashMapObject.get("orgDivision"));
			data.put("directorName", hashMapObject.get("directorName"));
			data.put("gender", hashMapObject.get("gender"));
			
		} else {
			System.err.println("hashMapObject is null");
		}

		if (type.equals("SHOW")) {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=ExperienceCertificate_" + employeeId + ".pdf");
		} else {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"attachment; filename=ExperienceCertificate_" + exitId + ".pdf");
		}

		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("exitManagement/experienceCertificate", data);
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

		logger.info("Method: generateExpLetterPDF ends");
	}

	/*
	 * View And Download No Due Certificate
	 * 
	 */

	@SuppressWarnings({ "unchecked", "unused" })
	@GetMapping("/exit-view-no-due-certificate")
	public void generateNoDuePDF(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("exitId") String encodedParam1, @RequestParam("type") String encodedParam2) {

		logger.info("Method: generateNoDuePDF starts");

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String exitId = new String(encodeByte3);

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String type = new String(encodeByte1);

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper objectMapper = new ObjectMapper();
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		
		try {
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "get-no-due-certificate?exitId="
					+ exitId + "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String pdfDetails = jsonResponse.getBody().toString();

		Map<String, Object> hashMapObject = null;

		try {
			List<Map<String, Object>> list = objectMapper.readValue(pdfDetails,
					new TypeReference<List<Map<String, Object>>>() {
					});

			hashMapObject = list.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<>();

		if (hashMapObject != null) {
			String candidateImage = (String) hashMapObject.get("candidateImage");

			/*
			 * String fileEmployeeimg = (candidateImage != null &&
			 * !candidateImage.isEmpty()) ? env.getBaseURL() + "document/employee/" +
			 * candidateImage : null;
			 */

			String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
			URL getUrl = null;
			try {
				getUrl = new URL(logo);
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
			data.put("logo", "data:image/png;base64," + encodedLogoUrl);

			data.put("empID", hashMapObject.get("empID"));
			data.put("empName", hashMapObject.get("empName"));
			data.put("designation", hashMapObject.get("designation"));
			data.put("joiningDate", hashMapObject.get("joiningDate"));
			data.put("releaseDate", hashMapObject.get("releaseDate"));
			data.put("deptName", hashMapObject.get("deptName"));
			data.put("designation", hashMapObject.get("designation"));
			data.put("clearanceDetails", hashMapObject.get("clearanceDetails"));
			data.put("settlementDate", hashMapObject.get("settlementDate"));

		} else {
			System.err.println("hashMapObject is null");
		}

		if (type.equals("SHOW")) {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=No Dues Certificate.pdf");
		} else {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"attachment; filename=No Dues Certificate.pdf");
		}

		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("exitManagement/noDuesCertificate", data);
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

		logger.info("Method: generateNoDuePDF ends");
	}

	/*
	 * View-payslip-listing
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("exit-payslip-listing")
	public @ResponseBody JsonResponse<Object> viewPayslipList(@RequestParam String empId, @RequestParam String fromDate,
			@RequestParam String toDate, HttpSession session, Model model) {

		logger.info("Method : viewPayslipList starts");

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
			resp = restClient.getForObject(
					env.getEmployeeUrl() + "view-employe-paySlip-api?userId=" + empId + "&fromDate=" + fromDate
							+ "&toDate=" + toDate + "&organization=" + organization + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewPayslipList ends");
		return resp;
	}

	/**************** Pay slip pdf download *******************/

	@GetMapping(value = { "/exit-payslip-pdf-download" })
	public void payslipPdf(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("fromDate") String encodedParam1, @RequestParam("toDate") String encodedParam2,
			@RequestParam("empId") String encodedParam3) {
		logger.info("Method : payslipPdf starts");

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String fromDate = (new String(encodeByte1));

		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String toDate = (new String(encodeByte2));

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String empId = (new String(encodeByte3));

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<OfferletterModel> jsonResponse = new JsonResponse<OfferletterModel>();
		List<PayslipModel> productList = new ArrayList<PayslipModel>();
		try {
			PayslipModel[] data = restClient.getForObject(
					env.getMasterUrl() + "rest-viewpaySlipPersonal?fromDate=" + fromDate + "&toDate=" + toDate
							+ "&empId=" + empId + "&organization=" + organization + "&orgDivision=" + orgDivision,
					PayslipModel[].class);
			productList = Arrays.asList(data);
		} catch (

		RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("allData", productList);

		String logo = "";
		// String logo = "classpath:static/asssets/css/extend/NEWS_7.png";
		String sign = "";
		String stamp = "";

		for (PayslipModel a : productList) {
			if (a.getOrgLogo() != null && a.getOrgLogo() != "" && a.getOrgLogo() != " ") {
				logo = env.getBaseURL() + "document/document/" + a.getOrgLogo();
			}

			if (a.getOrgSign() != null && a.getOrgSign() != "" && a.getOrgSign() != " ") {
				sign = env.getBaseURL() + "document/document/" + a.getOrgSign();
			}
			if (a.getOrgStamp() != null && a.getOrgStamp() != "" && a.getOrgStamp() != " ") {
				stamp = env.getBaseURL() + "document/document/" + a.getOrgStamp();
			}
		}
		data.put("logo", logo);
		data.put("sign", sign);
		data.put("stamp", stamp);

		String variable = env.getBaseURL();
		String header = "oriFoodPDFHeader.png";
		String footer = "oriFoodPDFFooter.png";
		String watermark = "oriFoodWatermark-1.png";// "oriFoodWatermark.png";
		// assets/images/login_img/logo.png
		data.put("header", variable + "assets/images/" + header + "");
		data.put("footer", variable + "assets/images/" + footer + "");
		data.put("watermark", variable + "assets/images/" + watermark + "");

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=payslipPdfDownload.pdf");
		File file;
		byte[] fileData = null;

		try {
			if ("Orifood And Beverage Private Limited".equals(orgDivision)) {
				file = pdfGeneratorUtil.createPdf("master/payslipPdfDownload-orifood", data);
			} else {
				file = pdfGeneratorUtil.createPdf("master/payslipPdfDownload", data);
			}
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : payslipPdf ends");
	}

	/*
	 * 
	 * Get final settlement details
	 */ 
	
     
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "exit-view-final-settlement" })
	public @ResponseBody JsonResponse<Object> getFinalSettlementDetails(Model model, @RequestParam String employeeId,  
			String fromDate, String toDate,String releaseDt,
			HttpSession session) {
		logger.info("Method :getFinalSettlementDetails starts");

		String organization = "";
		String orgDivision = "";

		organization = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "rest-view-final-settlement?employeeId=" + employeeId
					+ "&fromDate=" + fromDate + "&toDate=" + toDate+ "&releaseDt=" + releaseDt + "&org=" + organization + "&orgDiv=" + 
					orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getFinalSettlementDetails ends");
		return res;
	}

	/*
	 * Add Final Settlement
	 * 
	 */

	@SuppressWarnings("unchecked")

	@PostMapping("exit-view-final-settlement1")
	public @ResponseBody JsonResponse<Object> addFinancialSettlement1(
			@RequestBody ExitFinancialSettelmentModel exitModel, HttpSession session) {
		logger.info("Method : addFinancialSettlement starts");

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

		exitModel.setCreatedBy(userId);
		exitModel.setOrganization(organization);
		exitModel.setOrgDivision(orgDivision);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "save-final-settlement", exitModel,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addFinancialSettlement ends");
		return resp;
	}

	@SuppressWarnings("unchecked")

	@PostMapping("exit-view-final-settlement")
	public @ResponseBody JsonResponse<Object> addFinancialSettlement(
			@RequestBody ExitFinancialSettelmentModel exitModel, HttpSession session) {
		logger.info("Method : addExitProceed starts");
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

		exitModel.setCreatedBy(userId);
		exitModel.setOrganization(organization);
		exitModel.setOrgDivision(orgDivision);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "save-final-settlement", exitModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addFinancialSettlement ends");
		return resp;
	}

	/*
	 * Get final settlement details By ID
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "exit-view-final-settlement-ByID" })
	public @ResponseBody JsonResponse<Object> getFinalSettlementByID(Model model, @RequestParam String settlementId,
			HttpSession session) {
		logger.info("Method :getFinalSettlementByID starts");

		String organization = "";
		String orgDivision = "";

		organization = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient
					.getForObject(
							env.getEmployeeUrl() + "rest-view-final-settlement-byId?settlementId=" + settlementId
									+ "&organization=" + organization + "&orgDivision=" + orgDivision,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getFinalSettlementByID ends");
		return res;
	}

	/*
	 * View And Download Final Settlement Letter
	 * 
	 */

	@SuppressWarnings({ "unchecked", "unused" })
	@GetMapping("/exit-view-finalSettlement-pdf")
	public void generateFinalSettlementPDF(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("settlementId") String encodedParam1, @RequestParam("type") String encodedParam2) {

		logger.info("Method: generateFinalSettlementPDF starts");

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String settlementId = new String(encodeByte3);

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String type = new String(encodeByte1);

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper objectMapper = new ObjectMapper();
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
		try {
			jsonResponse = restClient
					.getForObject(
							env.getEmployeeUrl() + "rest-view-final-settlement-byId?settlementId=" + settlementId
									+ "&organization=" + organization + "&orgDivision=" + orgDivision,
							JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		;
		String pdfDetails = jsonResponse.getBody().toString();

		Map<String, Object> hashMapObject = null;

		try {
			List<Map<String, Object>> list = objectMapper.readValue(pdfDetails,
					new TypeReference<List<Map<String, Object>>>() {
					});

			hashMapObject = list.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<>();

		if (hashMapObject != null) {

			String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
			URL getUrl = null;
			try {
				getUrl = new URL(logo);
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
			data.put("logo", "data:image/png;base64," + encodedLogoUrl);
			data.put("hra", hashMapObject.get("hra"));
			data.put("basic", hashMapObject.get("basic"));
			data.put("bonus", hashMapObject.get("bonus"));
			data.put("empID", hashMapObject.get("empID"));
			data.put("empName", hashMapObject.get("empName"));
			data.put("gender", hashMapObject.get("gender"));
			data.put("releaseDateFormat", hashMapObject.get("releaseDateFormat"));
			data.put("exitIdF", hashMapObject.get("exitIdF"));
			data.put("deptName", hashMapObject.get("deptName"));
			data.put("epfAmount", hashMapObject.get("epfAmount"));
			data.put("netAmount", hashMapObject.get("netAmount"));
			data.put("esicAmount", hashMapObject.get("esicAmount"));
			data.put("coAllowance", hashMapObject.get("coAllowance"));
			data.put("designation", hashMapObject.get("designation"));
			data.put("joiningDate", hashMapObject.get("joiningDate"));
			data.put("releaseDate", hashMapObject.get("releaseDate"));
			data.put("resignDate", hashMapObject.get("resignDate"));
			data.put("leaveAmount", hashMapObject.get("leaveAmount"));
			data.put("otherAmount", hashMapObject.get("otherAmount"));
			data.put("releaseDate", hashMapObject.get("releaseDate"));
			data.put("totalAmount", hashMapObject.get("totalAmount"));
			data.put("settlementID", hashMapObject.get("settlementID"));
			data.put("totalEarning", hashMapObject.get("totalEarning"));
			data.put("salaryAdvance", hashMapObject.get("salaryAdvance"));
			data.put("washAllowance", hashMapObject.get("washAllowance"));
			data.put("totalDeduction", hashMapObject.get("totalDeduction"));
			data.put("professionalTax", hashMapObject.get("professionalTax"));
			data.put("settlementStatus", hashMapObject.get("settlementStatus"));
			data.put("releaseDateFormat", hashMapObject.get("releaseDateFormat"));
			data.put("address", hashMapObject.get("address"));
			data.put("orgDivision", hashMapObject.get("orgDivision"));

		} else {
			System.err.println("hashMapObject is null");
		}

		if (type.equals("SHOW")) {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"inline; filename=FinalSettlementCertificate_" + hashMapObject.get("empID") + ".pdf");
		} else {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition",
					"attachment; filename=FinalSettlementCertificate_" + hashMapObject.get("empID") + ".pdf");
		}

		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("exitManagement/finalSettlementFormat", data);
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

		logger.info("Method: generateFinalSettlementPDF ends");
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "exit-view-inactive-employee" })
	public @ResponseBody JsonResponse<Object> employeeInAcrtive(Model model, @RequestParam String empId,
			HttpSession session) {
		logger.info("Method :employeeInAcrtive starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		UserRoleAssignModel data = new UserRoleAssignModel();

		data.setEmpId(empId);
		data.setEmpStatus(false);

		try {
			res = restClient.postForObject(env.getUserUrl() + "addEmployeeMasteradd", data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Success");
		} else {
			res.setMessage("UnSuccess");
		}

		logger.info("Method : employeeInAcrtive ends");

		return res;

	}

	/*
	 * Employee Autosearch For Clearance
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "exit-view-employee-list" })
	public @ResponseBody JsonResponse<DropDownModel> employeeAutoSearch(Model model, @RequestBody String searchValue,
			BindingResult result, HttpSession session) {
		logger.info("Method : employeeAutoSearch starts");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "employee-autosearch-clearance?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : employeeAutoSearch ends");
		return res;
	}

	/*
	 * update clearance by employee
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "exit-view-update-clearanceBy" })
	public @ResponseBody JsonResponse<Object> updateClearanceBy(Model model, @RequestParam String clearanceId,@RequestParam String empId,
			HttpSession session) {
		logger.info("Method :updateClearanceBy starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String org = "";
		String orgDiv = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "update-clearance-employee?empId=" + empId + "&clearanceId="
					+ clearanceId + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : updateClearanceBy ends");

		return resp;

	}

}