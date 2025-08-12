package nirmalya.aathithya.webmodule.master.controller;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeWorkdetailsModel;
import nirmalya.aathithya.webmodule.grc.model.GRCReportModel;
import nirmalya.aathithya.webmodule.master.model.SalaryRevisionModel;

@Controller
@RequestMapping(value = "master/")
public class SalaryRevisionandPromotionController {
	Logger logger = LoggerFactory.getLogger(SalaryRevisionandPromotionController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("view-salary-revision")
	public String leaveApply(Model model, HttpSession session) {
		logger.info("Method : salary starts");
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] departmentType = restTemplate.getForObject(env.getMasterUrl()
					+ "getDepartmentTypeForShiftType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> department = Arrays.asList(departmentType);
			model.addAttribute("department", department);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] subdepartmentType = restTemplate.getForObject(env.getMasterUrl()
					+ "getSubDepartmentTypeForShiftType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> subdepartment = Arrays.asList(subdepartmentType);
			model.addAttribute("subdepartment", subdepartment);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DropDownModel[] financialYrType = restTemplate.getForObject(env.getMasterUrl()
					+ "getFinancialYrForSalaryRevision?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> financialYr = Arrays.asList(financialYrType);
			model.addAttribute("financialYr", financialYr);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getRecruitment() + "bandList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandType = Arrays.asList(dropDownModel);
			model.addAttribute("bandType", bandType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDown = restTemplate.getForObject(env.getMasterUrl()
					+ "rest-getDesignationDropDown?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> Designation = Arrays.asList(dropDown);
			model.addAttribute("Designation", Designation);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {

			DropDownModel[] manager = restTemplate.getForObject(
					env.getEmployeeUrl() + "EmployeeList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> managerList = Arrays.asList(manager);
			model.addAttribute("EmployeeList", managerList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		DropDownModel[] fYear = restTemplate.getForObject(env.getMasterUrl() + "getFinancialYearLists",
				DropDownModel[].class);
		List<DropDownModel> financialYear = Arrays.asList(fYear);
		model.addAttribute("financialYearList", financialYear);

		String userId = "";
		/*
		 * String organization=""; String orgDivision="";
		 */
		try {
			userId = (String) session.getAttribute("USER_ID");
			// organization = (String) session.getAttribute("ORGANIZATION");
			// orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		// logger.info("organization=="+organization+"orgDivision=="+orgDivision);
		model.addAttribute("userId", userId);

		logger.info("Method : salary ends");
		return "master/salaryRevisionPromotion";
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-salary-revision-getSubDepartmentByDept" })
	public @ResponseBody JsonResponse<Object> getSubDepartmentByDepts(HttpSession session,
			@RequestParam String DeptId) {
		logger.info("Method : getSubDepartmentByDepts starts");
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-shiftSubDepartment?shiftDeptId=" + DeptId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getSubDepartmentByDepts ends");

		logger.info("LISTTTT" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-salary-revision-getEmpListBySubDept")
	public @ResponseBody JsonResponse<List<DropDownModel>> getEmpListBySubDept(Model model,
			@RequestParam String subDeptId, HttpSession session) {
		logger.info("Method : getEmpListBySubDept starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "get-EmployeeId?subDeptId=" + subDeptId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method :  getEmpListBySubDept ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-salary-revision-subanddepartment")
	public @ResponseBody JsonResponse<List<DropDownModel>> getDeptAndSubDept(Model model, @RequestParam String empid,
			HttpSession session) {
		logger.info("Method : getDeptAndSubDept starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "get-getDeptAndSubDept?empid=" + empid + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method :  getDeptAndSubDept ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-salary-revision-getEmployeeList")
	public @ResponseBody JsonResponse<List<DropDownModel>> getEmployeeList(Model model, HttpSession session) {
		logger.info("Method : getEmployeeList starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "get-employee-list?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}
		logger.info("Method :  getEmployeeList ends");
		return resp;
	}
	/*
	 *
	 * add salary revision
	 * 
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-salary-revision-save" })
	public @ResponseBody JsonResponse<Object> addsalaryrevision(HttpSession session,
			@RequestBody SalaryRevisionModel data) {
		logger.info("Method : salary-revision starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String dateFormat = "";
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			data.setEffectiveFromDate(DateFormatter.inputDateFormat(data.getEffectiveFromDate(), dateFormat));
			data.setEffectiveToDate(DateFormatter.inputDateFormat(data.getEffectiveToDate(), dateFormat));
			data.setJoiningDate(DateFormatter.inputDateFormat(data.getJoiningDate(), dateFormat));
		} catch (Exception e) {
			e.printStackTrace();
		}
		data.setCreatedBy(userId);
		data.setOrganization(organization);
		data.setOrgDivision(orgDivision);
		logger.info("data=======" + data);
		try {
			res = restTemplate.postForObject(env.getMasterUrl() + "rest-addnew-salary-revision", data,
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

		logger.info("Method : salary-revision ends");
		logger.info("adddd=======" + res);
		return res;

	}

	/*
	 * get date list
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-salary-revision-date-ajax" })
	public @ResponseBody JsonResponse<Object> getDateList(@RequestParam String name) {
		logger.info("Method : getDateList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-dateList?id=" + name, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getDateList ends");
		logger.info("LISTTTT" + res);
		return res;

	}

	// view

	@SuppressWarnings("unchecked")

	@GetMapping("view-salary-revision-view")
	public @ResponseBody List<SalaryRevisionModel> viewSalaryMaster(HttpSession session, @RequestParam String userid) {
		logger.info("Method : viewSalaryMaster starts");

		JsonResponse<List<SalaryRevisionModel>> resp = new JsonResponse<List<SalaryRevisionModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "viewSalaryMaster?userid=" + userid + "&organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<SalaryRevisionModel> Model = mapper.convertValue(resp.getBody(),
				new TypeReference<List<SalaryRevisionModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (SalaryRevisionModel a : Model) {
			/*
			 * if (a.getEffectiveDate() != null && a.getEffectiveDate() != "") {
			 * a.setEffectiveDate(DateFormatter.dateFormat(a.getEffectiveDate(),
			 * dateFormat)); }
			 */
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : viewSalaryMaster  ends");
		return Model;

	}

	// edit

	@SuppressWarnings("unchecked")
	@GetMapping("view-salary-revision-edit")
	public @ResponseBody Object editSalaryRevision(@RequestParam String Id, Model model, HttpSession session) {

		logger.info("Method :editSalaryRevision starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-salary-revision-edit?id=" + Id,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editSalaryRevision ends  ");
		return resp;
	}

	/*
	 * get name and designation list
	 */
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "view-salary-revision-nameDesignation-ajax" })
	public @ResponseBody JsonResponse<Object> getnameAndDesignationList(HttpSession session,
			@RequestParam String name) {
		logger.info("Method : getnameAndDesignationList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "get-nameandDesignationList?id=" + name
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getnameAndDesignationList ends");

		logger.info("LISTTTT" + res);
		return res;

	}

	// delete details
	@SuppressWarnings("unchecked")
	@PostMapping("view-salary-revision-delete")
	public @ResponseBody JsonResponse<Object> deleteSalaryRevision(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteSalaryRevision function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getMasterUrl() + "rest-SalaryRevision-delete?id=" + id
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteSalaryRevision function Ends");

		logger.info("RESPPPPPPP" + res);
		return res;
	}

	/*
	 * Employee autosearch
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-salary-revision-get-employee-list" })
	public @ResponseBody JsonResponse<DropDownModel> EmployeeAutoSearchForAttendance(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : EmployeeAutoSearchForAttendance starts");
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
			res = restTemplate.getForObject(env.getMasterUrl() + "employee-autosearch-forAttendance?id=" + searchValue
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : EmployeeAutoSearchForAttendance ends");
		return res;
	}

	// editPolicy
	@SuppressWarnings("unchecked")
	@GetMapping("view-salary-revision-bandcalc")
	public @ResponseBody Object bandCalculation(@RequestParam String band, HttpSession session) {
		logger.info("Method :bandCalculation starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-view-salary-revision-bandcalc?band=" + band
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :bandCalculation ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-salary-revision-approve")
	public @ResponseBody Object approveEmpolyeeReview(@RequestParam String id, String sts, HttpSession session) {

		logger.info("Method :approveEmpolyeeReview starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String org = "";
		String orgDiv = "";

		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "approveSalaryStatus?id=" + id + "&sts=" + sts
					+ "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :approveEmpolyeeReview ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("view-salary-revision-view-by-year")
	public @ResponseBody List<SalaryRevisionModel> viewSalaryMasterByYear(HttpSession session,
			@RequestParam String userid, String startDate, String endDate) {
		logger.info("Method : viewSalaryMasterByYear starts");

		JsonResponse<List<SalaryRevisionModel>> resp = new JsonResponse<List<SalaryRevisionModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate
					.getForObject(
							env.getMasterUrl() + "viewSalaryMasterByYear?startDate=" + startDate + "&endDate=" + endDate
									+ "&organization=" + organization + "&orgDivision=" + orgDivision,
							JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<SalaryRevisionModel> Model = mapper.convertValue(resp.getBody(),
				new TypeReference<List<SalaryRevisionModel>>() {
				});
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : viewSalaryMasterByYear  ends");
		return Model;

	}

	/*
	 * All Report (PDF) Download
	 * 
	 */

	@SuppressWarnings({ "unchecked", "unused" })
	@GetMapping("/view-salary-revision-pdf-download")
	public void generateReportPDF(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("empId") String encodedParam1, @RequestParam("effectiveFromDate") String encodedParam2,
			@RequestParam("effectiveToDate") String encodedParam3) {

		logger.info("Method: generateReportPDF starts");

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String empId = new String(encodeByte1);

		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String effectiveFromDate = new String(encodeByte2);

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String effectiveToDate = new String(encodeByte3);

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
			jsonResponse = restTemplate.getForObject(env.getMasterUrl() + "salary-revision-pdf?empId=" + empId
					+ "&effectiveFromDate=" + effectiveFromDate + "&effectiveToDate=" + effectiveToDate
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		System.out.println("jsonResponse" + jsonResponse);

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
			data.put("orgName", orgDivision);

			
			  data.put("empId", hashMapObject.get("empId"));
			  data.put("empName",hashMapObject.get("empName"));
			  data.put("empDesgn", hashMapObject.get("empDesgn")); 
			  data.put("effectiveFromDate",hashMapObject.get("effectiveFromDate")); 
			  data.put("basic",hashMapObject.get("basic"));
			  data.put("hra",hashMapObject.get("hra"));
			  data.put("conv",hashMapObject.get("conv")); 
			  data.put("washingAllow",hashMapObject.get("washingAllow"));
			  data.put("orgAddress",hashMapObject.get("orgAddress"));
			  data.put("empLastName",hashMapObject.get("empLastName"));
			  data.put("total",hashMapObject.get("total"));
			  data.put("gender",hashMapObject.get("gender"));
			  data.put("totalInWord",hashMapObject.get("totalInWord"));
			  data.put("effectivedateWords",hashMapObject.get("effectivedateWords"));
			  data.put("grossIncrease",hashMapObject.get("grossIncrease"));
			  data.put("grossIncreaseInWord",hashMapObject.get("grossIncreaseInWord"));
			  
			  System.out.println("Dataaa>>>"+data);
			  
			 
		} else {
			System.err.println("hashMapObject is null");
		}

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=SalaryRevision&Promotion_" + empId + ".pdf");

		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("salaryRevisionAndPromotion/salaryRevisionPDF", data);
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
}
