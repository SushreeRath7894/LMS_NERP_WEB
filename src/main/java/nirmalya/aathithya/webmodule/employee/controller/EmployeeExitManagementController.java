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

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.employee.model.EmployeeResignationModel;
import nirmalya.aathithya.webmodule.employee.model.ExitFinancialSettelmentModel;
import nirmalya.aathithya.webmodule.employee.model.ExtendExitManagementModel;
import nirmalya.aathithya.webmodule.master.model.PayslipModel;
import nirmalya.aathithya.webmodule.recruitment.model.OfferletterModel;
import nirmalya.aathithya.webmodule.user.model.UserRoleAssignModel;

@Controller
@RequestMapping(value = { "employee/" })
public class EmployeeExitManagementController {
	Logger logger = LoggerFactory.getLogger(EmployeeExitManagementController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("exit-management")
	public String exitManagement(Model model, HttpSession session) {
		logger.info("Method : exitManagement starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String userRoles = "";
		String userManager = "";
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			userManager = (String) session.getAttribute("MANAGER_ID");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] emplist = restClient.getForObject(
					env.getEmployeeUrl() + "getEmployeeLists?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplists", emplists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] emplist = restClient
					.getForObject(
							env.getEmployeeUrl() + "getEmployeeListsCC?orgName=" + orgName + "&orgDivision="
									+ orgDivision + "&managerId=" + userManager + "&userId=" + userId,
							DropDownModel[].class);
			List<DropDownModel> emplists = Arrays.asList(emplist);
			model.addAttribute("emplistscc", emplists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;
			if (data.contentEquals("rol001") || data.contentEquals("rol003")) {
				model.addAttribute("hrRole", data);
			}
		}
		try {
			DropDownModel[] name = restClient.getForObject(env.getEmployeeUrl() + "getNamelist", DropDownModel[].class);
			List<DropDownModel> namelist = Arrays.asList(name);
			model.addAttribute("namelist", namelist);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] dept = restClient.getForObject(
					env.getEmployeeUrl() + "getDeptlist?org=" + orgName + "&orgDiv=" + orgDivision,
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
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("userRoles", userRoles);
		model.addAttribute("userManager", userManager);

		logger.info("Method : exitManagement ends");
		return "his_master/exit-management.html";
	}

// view Resignation All Data	
	@SuppressWarnings("unchecked")
	@GetMapping("exit-management-view-through-ajax")
	public @ResponseBody JsonResponse<Object> viewExitManagement(HttpSession session, @RequestParam String roleid, @RequestParam String selftype) {

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
					+ roleid + "&organization=" + organization + "&orgDivision=" + orgDivision + "&selftype=" + selftype, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewExitManagement ends");
		return resp;
	}

// emplist for resignation
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "employee-management-get-employee-list" })
	public @ResponseBody JsonResponse<DropDownModel> employeeAutoSearchResign(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : employeeAutoSearchResign starts");
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
			res = restClient.getForObject(env.getEmployeeUrl() + "employee-autosearch-resignation?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : employeeAutoSearchResign ends");
		return res;
	}

// edit resignation	
	@SuppressWarnings("unchecked")
	@GetMapping("exit-management-draft-edit")
	public @ResponseBody JsonResponse<Object> editResignationApplyDraft(@RequestParam String id, HttpSession session) {

		logger.info("Method : editResignationApplyDraft starts");

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
		}
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "rest-editResignationApplyDraft?id=" + id + "&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		logger.info("Method : editResignationApplyDraft ends");

		return resp;
	}

// apply draft
	@SuppressWarnings("unchecked")
	@PostMapping(value = "exit-management-draft-apply")
	public @ResponseBody JsonResponse<Object> resinationApplyDraft(
			@RequestBody EmployeeResignationModel employeeResignationModel, HttpSession session, Model model) {

		logger.info("Method : resinationApplyDraft function starts");
		System.out.println("employeeResignationModel>>" + employeeResignationModel);
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
			e.printStackTrace();
		}
		employeeResignationModel.setCreatedBy(userId);
		employeeResignationModel
				.setResignDate(DateFormatter.inputDateFormat(employeeResignationModel.getResignDate(), dateFormat));
		employeeResignationModel
				.setReleaseDate(DateFormatter.inputDateFormat(employeeResignationModel.getReleaseDate(), dateFormat));
		employeeResignationModel.setOrganization(organization);
		employeeResignationModel.setOrgDivision(orgDivision);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.postForObject(env.getEmployeeUrl() + "rest-resignation-apply-draft",
					employeeResignationModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : resinationApply function Ends");
		return res;
	}

// apply submit
	@SuppressWarnings("unchecked")
	@PostMapping(value = "exit-management-apply")
	public @ResponseBody JsonResponse<Object> resinationApply(@RequestBody EmployeeResignationModel data,
			HttpSession session, Model model) {
		logger.info("Method : resinationApply function starts");
		EmployeeResignationModel empReg = new EmployeeResignationModel();
		String userId = "";
		String userEmail = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			userEmail = (String) session.getAttribute("USER_EMAIL");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCreatedBy(userId);
		data.setResignDate(DateFormatter.inputDateFormat(data.getResignDate(), dateFormat));
		data.setReleaseDate(DateFormatter.inputDateFormat(data.getReleaseDate(), dateFormat));
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.postForObject(env.getEmployeeUrl() + "rest-resignation-apply", data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (res.getMessage() != "" && res.getMessage() != null) {
			res.setCode(res.getCode());
			res.setMessage(res.getMessage());
		} else {
			String tolist = data.getRegTo();
			String cclist = data.getRegCC();
			String comma = ",";
			String newlist = tolist.concat(comma).concat(cclist);
			String str[] = newlist.split(",");
			logger.info("str " + Arrays.toString(str));
			ArrayList<String> mylist = new ArrayList<String>();
			mylist.addAll(Arrays.asList(str));
			JsonResponse<Object> resp = new JsonResponse<Object>();
			List<String> toAddress = new ArrayList<String>();
			List<String> CcAddress = new ArrayList<String>();
			String userName = "";
			try {
				userName = (String) session.getAttribute("USER_NAME");
			} catch (Exception e) {
			}
			String subject = "From :-" + userName + System.lineSeparator() + "Subject:-" + data.getSubject();
			String message1 = data.getReason();
			for (int i = 0; i < mylist.size(); i++) {
				try {
					resp = restClient.getForObject(env.getEmployeeUrl() + "get_maildetails?mylist=" + mylist.get(i),
							JsonResponse.class);
					logger.info("emails:- " + (String) resp.getBody());
					toAddress.add((String) resp.getBody());
				} catch (RestClientException e) {
					e.printStackTrace();
				}
			}
			try {
				logger.info("message1 " + message1);
				EmailAttachmentSender.sendEmailWithAttachments("smtp.gmail.com", "587", userEmail, "Nirmalya@123",
						toAddress, CcAddress, subject, message1, null);
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			res.setBody(empReg);
		}
		logger.info("Method : resinationApply function Ends");
		return res;
	}

// delete application	
	@SuppressWarnings("unchecked")
	@GetMapping("exit-management-delete")
	public @ResponseBody JsonResponse<Object> deleteResignationApply(HttpSession session,
			@RequestParam String deleteId) {
		logger.info("Method : deleteResignationApply starts   " + deleteId);
		String organization = "";
		String orgDivision = "";
		organization = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		JsonResponse<Object> response = new JsonResponse<Object>();
		try {
			response = restClient.getForObject(env.getEmployeeUrl() + "rest-deleteResignation?id=" + deleteId + "&org="
					+ organization + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : deleteResignationApply ends");
		return response;
	}

// initaiate resignation
	@SuppressWarnings("unchecked")
	@PostMapping("exit-management-proceed-notice")
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

	// emp list for clearance
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "exit-management-employee-list-clear" })
	public @ResponseBody JsonResponse<DropDownModel> employeeAutoSearchClearance(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : employeeAutoSearchClearance starts");
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

		logger.info("Method : employeeAutoSearchClearance ends");
		return res;
	}

// get clearance details
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "exit-management-get-clearanceStatus" })
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

// add  clearanace emp
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "exit-management-update-clearanceBy" })
	public @ResponseBody JsonResponse<Object> updateClearanceBy(Model model, @RequestParam String clearanceId,
			@RequestParam String empId, HttpSession session) {
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
			resp = restClient.getForObject(env.getEmployeeUrl() + "update-clearance-employee?empId=" + empId
					+ "&clearanceId=" + clearanceId + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : updateClearanceBy ends");

		return resp;

	}

// get final settlement Data
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "exit-management-final-settlement" })
	public @ResponseBody JsonResponse<Object> getFinalSettlementDetails(Model model, @RequestParam String employeeId,
			String fromDate, String toDate, String releaseDt, HttpSession session) {
		logger.info("Method :getFinalSettlementDetails starts");
		String organization = "";
		String orgDivision = "";
		organization = (String) session.getAttribute("ORGANIZATION");
		orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getEmployeeUrl() + "rest-view-final-settlement?employeeId=" + employeeId
					+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&releaseDt=" + releaseDt + "&org=" + organization
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getFinalSettlementDetails ends");
		return res;
	}

// add financial settlement	
	@SuppressWarnings("unchecked")
	@PostMapping("exit-management-add-final-settlement")
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

// get updated final settlement
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "exit-management-final-settlement-ByID" })
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

// inactive employee
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "exit-management-inactive-employee" })
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

//View And Download No Due Certificate
	@SuppressWarnings({ "unchecked", "unused" })
	@GetMapping("exit-management-no-due-certificate")
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
			jsonResponse = restClient.getForObject(env.getEmployeeUrl() + "get-no-due-certificate?exitId=" + exitId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
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
			response.setHeader("Content-disposition", "attachment; filename=No Dues Certificate.pdf");
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

//View And Download Final Settlement Letter
	@SuppressWarnings({ "unchecked", "unused" })
	@GetMapping("exit-management-finalSettlement-pdf")
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

//View-payslip-listing
	@SuppressWarnings("unchecked")
	@GetMapping("exit-management-payslip-listing")
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
	@GetMapping(value = { "exit-management-payslip-pdf-download" })
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
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("allData", productList);
		String logo = "";
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

// View And Download Experience Letter
	@SuppressWarnings({ "unchecked", "unused" })
	@GetMapping("/exit-management-experince-letter")
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
			employeeId = (String) hashMapObject.get("empID");
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
			response.setHeader("Content-disposition", "attachment; filename=ExperienceCertificate_" + exitId + ".pdf");
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

// clear dept
	@SuppressWarnings("unchecked")
	@GetMapping("exit-management-clearance-update")
	public @ResponseBody JsonResponse<Object> clearanceUpdate(HttpSession session, @RequestParam String id,
			String recoveryAmount, String remarks) {
		logger.info("Method : viewExitClearanceEmployee starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		ExitFinancialSettelmentModel modelData = new ExitFinancialSettelmentModel();
		String organization = "";
		String orgDivision = "";
		String userid = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userid = (String) session.getAttribute("USER_ID");

			System.out.println("userid>>" + userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelData.setCreatedBy(userid);
		modelData.setOrganization(organization);
		modelData.setOrgDivision(orgDivision);
		modelData.setClearanceId(id);
		modelData.setRecovery(recoveryAmount);
		modelData.setComment(remarks);
		try {
			resp = restClient.postForObject(env.getEmployeeUrl() + "update-exit-clearance", modelData,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : viewExitClearanceEmployee ends");
		return resp;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "exit-management-emp-dept")
	public @ResponseBody JsonResponse<List<DropDownModel>> employeeDeptList(
	        HttpSession session) {

	    logger.info("Method : employeeDeptList starts");
	    JsonResponse<List<DropDownModel>> res = new JsonResponse<>();
	    String org = (String) session.getAttribute("ORGANIZATION");
	    String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

	    try {
	        DropDownModel[] dept = restClient.getForObject(
	                env.getEmployeeUrl() + "getDeptlist?org=" + org + "&orgDiv=" + orgDiv,
	                DropDownModel[].class);
	        
	        if (dept != null) {
	            res.setBody(Arrays.asList(dept));
	            res.setMessage("Data retrieved successfully");
	            res.setCode("200");
	        } else {
	            res.setMessage("No departments found");
	            res.setCode("204");
	        }
	    } catch (RestClientException e) {
	        res.setCode("500");
	    }

	    logger.info("Method : employeeDeptList ends");
	    return res;
	}
}


