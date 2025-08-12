package nirmalya.aathithya.webmodule.employee.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
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
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.MasterOrganisationalModel;

@Controller
@RequestMapping(value = "employee")
public class EmployeeTransferController {
	Logger logger = LoggerFactory.getLogger(EmployeeTransferController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("/employee-transfer")
	public String employeeTransfer(Model model, HttpSession session) {

		logger.info("Method : employeeTransfer starts");

		logger.info("Method : employeeTransfer ends");
		return "employee/employee-transfer";
	}

	@GetMapping("/employee-transfer-dashboard")
	public String employeeTransferDashboard(Model model, HttpSession session) {

		logger.info("Method : employee-transfer-dashboard starts");
		
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			DropDownModel[] Gender = restClient.getForObject(env.getEmployeeUrl() + "getgenderList1",
					DropDownModel[].class);
			List<DropDownModel> genderTypeList = Arrays.asList(Gender);

			model.addAttribute("genderTypeList", genderTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Nationality = restClient.getForObject(env.getEmployeeUrl() + "getnationalityList1",
					DropDownModel[].class);
			List<DropDownModel> nationalityList = Arrays.asList(Nationality);

			model.addAttribute("nationalityList", nationalityList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] BloodGroup = restClient.getForObject(env.getEmployeeUrl() + "getbloodgroupList1",
					DropDownModel[].class);
			List<DropDownModel> bloodgroupList = Arrays.asList(BloodGroup);

			model.addAttribute("bloodgroupList", bloodgroupList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] MaritalStatus = restClient.getForObject(env.getEmployeeUrl() + "getmaritalstatusList1",
					DropDownModel[].class);
			List<DropDownModel> maritalstatusList = Arrays.asList(MaritalStatus);

			model.addAttribute("maritalstatusList", maritalstatusList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Country = restClient.getForObject(env.getEmployeeUrl() + "getCountryList",
					DropDownModel[].class);
			List<DropDownModel> counntryList = Arrays.asList(Country);

			model.addAttribute("counntryList", counntryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] employmentType = restClient.getForObject(env.getEmployeeUrl()
					+ "getemployedByList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> employmentstatusList = Arrays.asList(employmentType);
			model.addAttribute("employedByList", employmentstatusList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : employee-transfer-dashboard ends");
		return "employee/employee-transfer-dashboard";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/employee-transfer-organisationl-details")
	public @ResponseBody List<MasterOrganisationalModel> viewJobType(HttpSession session) {
		logger.info("Method : vieworg starts");

		JsonResponse<List<MasterOrganisationalModel>> resp = new JsonResponse<List<MasterOrganisationalModel>>();
		List<MasterOrganisationalModel> returnList = new ArrayList<MasterOrganisationalModel>();

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("O___" + organization);
		logger.info("D___" + orgDivision);
		try {
			// resp = restTemplate.getForObject(env.getMasterUrl() +
			// "rest-view-organiserdetails"+ "&organization=" + organization +
			// "&orgDivision=" + orgDivision, JsonResponse.class);
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-organiserdetails?organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
			returnList = resp.getBody();
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : vieworg ends");
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("employee-transfer-save-data")
	public @ResponseBody JsonResponse<Object> saveTransferEmployeeData(HttpSession session,
			@RequestBody Map<String, Object> employeeData) {
		logger.info("Method : saveTransferEmployeeData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.postForObject(env.getEmployeeUrl() + "save-employee-transfer-data?userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, employeeData, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveTransferEmployeeData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("employee-transfer-data")
	public @ResponseBody JsonResponse<Object> getAllEmpTransferData(HttpSession session) {
		logger.info("Method : getAllEmpTransferData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.getForObject(env.getEmployeeUrl() + "get-AllEmpTransferData?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getAllEmpTransferData ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/employee-transfer-Pdf" })
	public void generateTransferReport(HttpServletResponse response, HttpSession session, @RequestParam String requestedName,
			@RequestParam String oldOrg,@RequestParam String newOrg) {
		logger.info("Method : generate-transfer-pdf starts");

		String userId = (String) session.getAttribute("USER_ID");
		String orgName = (String) session.getAttribute("ORGANIZATION");
		String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		JsonResponse<Object> resp = new JsonResponse<>();

		Map<String, Object> data = new HashMap<>();
		data.put("requestedName", requestedName);
		data.put("oldOrg", oldOrg);
		data.put("newOrg", newOrg);

		String filename = "employee-transfer.pdf";
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("employee/employee-transfer-pdf.html", data);
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

		logger.info("Method : generate-transfer-pdf ends");
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/employee-transfer-edit")
	public @ResponseBody JsonResponse<Object> editmanageEmployeemaster(@RequestParam String employeeId, @RequestParam String orgDiv,
			HttpSession session) {
		logger.info("Method : editmanageEmployeemaster starts");

		String organization = "";
		

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "editmanageEmployeemasterById?id=" + employeeId
					+ "&organization=" + organization + "&orgDivision=" + orgDiv, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}
		logger.info("Method : editmanageEmployeemaster end");
		return resp;
	}


}
