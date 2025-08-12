package nirmalya.aathithya.webmodule.recruitment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.reactive.function.client.WebClient;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.recruitment.model.ActionEmployeeDetailsModel;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(value = "recruitment")
public class RequisitionControllerV2 {
	Logger logger = LoggerFactory.getLogger(RequisitionControllerV2.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	private static final String OPENAI_API_URL = "";
	private static final String OPENAI_API_KEY = "";

	@GetMapping("/requisitions")
	public String requisition(Model model, HttpSession session) {
		logger.info("Method : requisition starts");

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {

			DropDownModel[] jobType = restTemplate.getForObject(env.getRecruitment() + "jobTypeList",
					DropDownModel[].class);
			List<DropDownModel> jobTypeList = Arrays.asList(jobType);
			model.addAttribute("jobTypeList", jobTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] requisitions = restTemplate.getForObject(
					env.getRecruitment() + "jobList?orgName=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> requisitionsList = Arrays.asList(requisitions);
			model.addAttribute("jobList", requisitionsList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] location = restTemplate.getForObject(
					env.getRecruitment() + "jobLocationList?orgName=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> jobLocationList = Arrays.asList(location);
			model.addAttribute("jobLocationList", jobLocationList);
		} catch (RestClientException e) {
			e.printStackTrace();
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
			ActionEmployeeDetailsModel[] manager = restTemplate.getForObject(
					env.getRecruitment() + "EmployeeFullList?org=" + organization + "&orgDiv=" + orgDivision,
					ActionEmployeeDetailsModel[].class);
			List<ActionEmployeeDetailsModel> managerList = Arrays.asList(manager);
			model.addAttribute("managerList", managerList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] band = restTemplate.getForObject(
					env.getRecruitment() + "bandList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bandList = Arrays.asList(band);
			model.addAttribute("bandList", bandList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] designation = restTemplate.getForObject(env.getRecruitment() + "designationLists",
					DropDownModel[].class);
			List<DropDownModel> designationList = Arrays.asList(designation);
			model.addAttribute("designationList", designationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] education = restTemplate.getForObject(env.getRecruitment() + "educationList",
					DropDownModel[].class);
			List<DropDownModel> educationList = Arrays.asList(education);
			model.addAttribute("educationList", educationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] workHour = restTemplate.getForObject(env.getRecruitment() + "workHourList",
					DropDownModel[].class);
			List<DropDownModel> workHourList = Arrays.asList(workHour);
			model.addAttribute("workHourList", workHourList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] benefits = restTemplate.getForObject(env.getRecruitment() + "benefitsList",
					DropDownModel[].class);
			List<DropDownModel> benefitsList = Arrays.asList(benefits);
			model.addAttribute("benefitsList", benefitsList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] aboutCompany = restTemplate.getForObject(env.getRecruitment() + "aboutCompany",
					DropDownModel[].class);
			List<DropDownModel> aboutCompanyData = Arrays.asList(aboutCompany);
			model.addAttribute("aboutComapany", aboutCompanyData);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String userRole = "";

		try {
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol002")) {
				model.addAttribute("hrRole", data);
			}
		}
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001")) {
				model.addAttribute("adminRole", data);
			}
		}

		model.addAttribute("userRole", userRole);
		logger.info("Method : requisition ends");

		return "recruitment-new/recruitment-requisition";
	}

	@GetMapping("/open-requisition")
	public String opensRequisition(Model model, HttpSession session) {
		logger.info("Method : opensRequisition starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		logger.info("Method : opensRequisition ends");
		return "recruitment-new/opens-requisition";
	}
	
	

	@GetMapping("/candidate-applications")
	public String candidateApplications(Model model, HttpSession session) {
		logger.info("Method : candidateApplications starts");
		logger.info("Method : candidateApplications ends");

		return "recruitment-new/candidate-applications";
	}

	@GetMapping("/candidates-evaluation")
	public String candidateEvaluations(Model model, HttpSession session) {
		logger.info("Method : candidateEvaluations starts");
		logger.info("Method : candidateEvaluations ends");

		return "recruitment-new/candidate-evaluation";
	}

	@GetMapping("/shortlisted-resumes")
	public String shortlistedApplications(Model model, HttpSession session) {
		logger.info("Method : shortlistedApplications starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			ActionEmployeeDetailsModel[] manager = restTemplate.getForObject(
					env.getRecruitment() + "interviewr-listing?org=" + organization + "&orgDiv=" + orgDivision,
					ActionEmployeeDetailsModel[].class);

			List<ActionEmployeeDetailsModel> managerList = Arrays.asList(manager);
			model.addAttribute("interviewrList", managerList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] location = restTemplate.getForObject(
					env.getRecruitment() + "jobLocationList?orgName=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> jobLocationList = Arrays.asList(location);
			model.addAttribute("jobLocationList", jobLocationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		model.addAttribute("userId", userId);

		logger.info("Method : shortlistedApplications ends");

		return "recruitment-new/shortlisted-candidate";
	}

	@GetMapping("/scheduled-candidates")
	public String scheduledCandidates(Model model, HttpSession session) {
		logger.info("Method : scheduledCandidates starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {
			ActionEmployeeDetailsModel[] manager = restTemplate.getForObject(
					env.getRecruitment() + "interviewr-listing?org=" + organization + "&orgDiv=" + orgDivision,
					ActionEmployeeDetailsModel[].class);

			List<ActionEmployeeDetailsModel> managerList = Arrays.asList(manager);
			model.addAttribute("interviewrList", managerList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] location = restTemplate.getForObject(
					env.getRecruitment() + "jobLocationList?orgName=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> jobLocationList = Arrays.asList(location);
			model.addAttribute("jobLocationList", jobLocationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] requisitions = restTemplate.getForObject(
					env.getRecruitment() + "jobList?orgName=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> requisitionsList = Arrays.asList(requisitions);
			model.addAttribute("jobList", requisitionsList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/*
		 * try {
		 * 
		 * DropDownModel[] candidates = restTemplate.getForObject( env.getRecruitment()
		 * + "candidatesList?orgName=" + organization + "&orgDivision=" + orgDivision,
		 * DropDownModel[].class); List<DropDownModel> candidatesList =
		 * Arrays.asList(candidates); model.addAttribute("candidatesList",
		 * candidatesList); } catch (RestClientException e) { e.printStackTrace(); }
		 */

		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
		model.addAttribute("userId", userId);

		logger.info("Method : scheduledCandidates ends");

		return "recruitment-new/scheduled-candidates";
	}

	@GetMapping("/review-hiring-get-candidates-list")
	@ResponseBody
	public List<DropDownModel> getCandidatesList(@RequestParam String requisitionId, Model model, HttpSession session) {
		logger.info("Method : getCandidatesList starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		List<DropDownModel> candidatesList = new ArrayList<>();
		try {
			DropDownModel[] candidates = restTemplate.getForObject(env.getRecruitment() + "candidatesList?orgName="
					+ organization + "&orgDivision=" + orgDivision + "&requisitionId=" + requisitionId,
					DropDownModel[].class);
			if (candidates != null) {
				candidatesList = Arrays.asList(candidates);
			}
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getCandidatesList ends");

		return candidatesList;
	}

	@GetMapping("/candidates-lists")
	public String allCandidatesList(Model model, HttpSession session) {
		logger.info("Method : allCandidatesList starts");

		try {
			DropDownModel[] Gender = restTemplate.getForObject(env.getEmployeeUrl() + "getgenderList1",
					DropDownModel[].class);
			List<DropDownModel> genderTypeList = Arrays.asList(Gender);

			model.addAttribute("genderTypeList", genderTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] Nationality = restTemplate.getForObject(env.getEmployeeUrl() + "getnationalityList1",
					DropDownModel[].class);
			List<DropDownModel> nationalityList = Arrays.asList(Nationality);

			model.addAttribute("nationalityList", nationalityList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] BloodGroup = restTemplate.getForObject(env.getEmployeeUrl() + "getbloodgroupList1",
					DropDownModel[].class);
			List<DropDownModel> bloodgroupList = Arrays.asList(BloodGroup);

			model.addAttribute("bloodgroupList", bloodgroupList);
			System.out.println("bloodgroupList::" + bloodgroupList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] MaritalStatus = restTemplate.getForObject(env.getEmployeeUrl() + "getmaritalstatusList1",
					DropDownModel[].class);
			List<DropDownModel> maritalstatusList = Arrays.asList(MaritalStatus);

			model.addAttribute("maritalstatusList", maritalstatusList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] country = restTemplate.getForObject(env.getMasterUrl() + "getCountryListForLocation",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);

			model.addAttribute("countryList", countryList);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		/*
		 * try { DropDownModel[] State = restTemplate.getForObject(env.getEmployeeUrl()
		 * + "getstateList1", DropDownModel[].class); List<DropDownModel> stateList =
		 * Arrays.asList(State);
		 * 
		 * model.addAttribute("stateList", stateList); } catch (RestClientException e) {
		 * e.printStackTrace(); }
		 */
		/*
		 * try { DropDownModel[] City = restTemplate.getForObject(env.getEmployeeUrl() +
		 * "getcityList1", DropDownModel[].class); List<DropDownModel> cityList =
		 * Arrays.asList(City);
		 * 
		 * model.addAttribute("cityList", cityList); } catch (RestClientException e) {
		 * e.printStackTrace(); }
		 */
		try {

			DropDownModel[] education = restTemplate.getForObject(env.getRecruitment() + "educationList",
					DropDownModel[].class);
			List<DropDownModel> educationList = Arrays.asList(education);
			model.addAttribute("educationList", educationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] addressType = restTemplate.getForObject(env.getRecruitment() + "addressTypeList-hire",
					DropDownModel[].class);
			List<DropDownModel> addressTypeListHire = Arrays.asList(addressType);
			model.addAttribute("addressTypeList", addressTypeListHire);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] Bank = restTemplate.getForObject(env.getEmployeeUrl() + "documentTypeList",
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(Bank);

			model.addAttribute("documentTypeList", documentTypeList);
			logger.info("DOCUMENTTTTT" + documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";
		try {

			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol002")) {
				model.addAttribute("hrRole", data);
			}
		}
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001")) {
				model.addAttribute("adminRole", data);
			}
		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);

		logger.info("Method : allCandidatesList ends");
		return "recruitment-new/candidates-lists";
	}

	/*
	 * @GetMapping("/candidates-job-apply") public String candidateJobApply(Model
	 * model, HttpSession session) {
	 * logger.info("Method : candidateJobApply starts");
	 * logger.info("Method : candidateJobApply ends");
	 * 
	 * return "recruitment-new/candidates-job-apply"; }
	 */
	@GetMapping("/dashboard")
	public String adminDashboard(Model model, HttpSession session) {
		logger.info("Method : adminDashboard starts");

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("interviwersDashboard in orgName------------" + orgName);

		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getEmployeeUrl() + "getOrganization?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> Organization = Arrays.asList(dropDownModel);
			model.addAttribute("Organization", Organization);

			logger.info("Organization" + Organization);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate
					.getForObject(env.getEmployeeUrl() + "getDivision?orgName=" + orgName, DropDownModel[].class);
			List<DropDownModel> orgDivisionList = Arrays.asList(dropDownModel);
			model.addAttribute("orgDivisionList", orgDivisionList);

			logger.info("orgDivisionList" + orgDivisionList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getEmployeeUrl() + "getDepartmentListDash?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> orgDepartmentList = Arrays.asList(dropDownModel);
			model.addAttribute("orgDepartmentList", orgDepartmentList);

			logger.info("orgDepartmentList----------------" + orgDepartmentList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getEmployeeUrl() + "getJobRoleList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> getJobRoleList = Arrays.asList(dropDownModel);
			model.addAttribute("getJobRoleList", getJobRoleList);

			logger.info("getJobRoleList-------------" + getJobRoleList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] dropDownModel = restTemplate.getForObject(
					env.getEmployeeUrl() + "getRecuiterList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> getRecuiterList = Arrays.asList(dropDownModel);
			model.addAttribute("getRecuiterList-----------------", getRecuiterList);

			logger.info("getRecuiterList" + getRecuiterList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : adminDashboard ends");
		return "recruitment-new/admin-dashboard";
	}

	@GetMapping("/selected-condidates")
	public String selectedCandidates(Model model, HttpSession session) {
		logger.info("Method : selectedCandidates starts");
		logger.info("Method : selectedCandidates ends");

		return "recruitment-new/selected-candidates";
	}

	@GetMapping("/hold-condidates")
	public String holdCandidates(Model model, HttpSession session) {
		logger.info("Method : holdCandidates starts");
		logger.info("Method : holdCandidates ends");

		return "recruitment-new/hold-candidates";
	}

	@GetMapping("/offe-accepted-condidates")
	public String offerAcceptedCandidates(Model model, HttpSession session) {
		logger.info("Method : offerAcceptedCandidates starts");
		logger.info("Method : offerAcceptedCandidates ends");

		return "recruitment-new/offer-accepted-candidates.html";
	}

	@GetMapping("/offe-declined-condidates")
	public String offerDeclinedCandidates(Model model, HttpSession session) {
		logger.info("Method : offerDeclinedCandidates starts");
		logger.info("Method : offerDeclinedCandidates ends");

		return "recruitment-new/offer-declined-candidates.html";
	}

	@GetMapping("/candidate-selection")
	public String candidatesSelection(Model model, HttpSession session) {
		logger.info("Method : candidatesSelection starts");

		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {

			DropDownModel[] requisitions = restTemplate.getForObject(
					env.getRecruitment() + "jobList?orgName=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> requisitionsList = Arrays.asList(requisitions);
			model.addAttribute("jobList", requisitionsList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] bandType = restTemplate.getForObject(env.getMasterUrl() + "getBandTypeList",
					DropDownModel[].class);
			List<DropDownModel> bandTypeList = Arrays.asList(bandType);

			model.addAttribute("bandTypeList", bandTypeList);
			System.out.println("Pk:::::::" + bandTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : candidatesSelection ends");

		return "recruitment-new/candidate-selection.html";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("review-hiring-get-application-log")
	public @ResponseBody JsonResponse<Object> getApplicationLogs(HttpSession session, @RequestParam String type) {

		logger.info("Method : getApplicationLogs starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {

		}

		try {
			resp = restTemplate
					.getForObject(env.getRecruitment() + "rest-get-requisitions-for-candidates-selection?userId="
							+ userId + "&orgName=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getApplicationLogs ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("payroll-report-view-salary-revision-bandcalc")
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

	/*@PostMapping("/generate-jd")
	public ResponseEntity<JsonResponse<Map<String, String>>> generateJobDescription(@RequestBody Map<String, Object> jobData) {
	    try {
	        String prompt = buildPrompt(jobData);

	        WebClient webClient = WebClient.builder()
	            .baseUrl(OPENAI_API_URL)
	            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + OPENAI_API_KEY)
	            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	            .build();

	        Map<String, Object> requestBody = Map.of(
	            "model", "gpt-4",
	            "messages", new Object[]{
	                Map.of("role", "system", "content", "You are an expert HR professional who creates perfectly formatted job descriptions. " +
	                      "Always use markdown-style formatting with **bold** for section headers and - for bullet points."),
	                Map.of("role", "user", "content", prompt)
	            },
	            "temperature", 0.7
	        );

	        String rawDescription = webClient.post()
	            .body(Mono.just(requestBody), Map.class)
	            .retrieve()
	            .bodyToMono(Map.class)
	            .map(response -> {
	                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
	                if (choices != null && !choices.isEmpty()) {
	                    Map<String, Object> firstChoice = choices.get(0);
	                    Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
	                    return (String) message.get("content");
	                }
	                return "No response generated.";
	            })
	            .block();

	        // Return both formatted HTML and raw markdown text
	        Map<String, String> responseData = new HashMap<>();
	        responseData.put("html", formatJobDescription(rawDescription));
	        responseData.put("raw", rawDescription);
	        responseData.put("markdown", convertToMarkdown(rawDescription));

	        JsonResponse<Map<String, String>> resp = new JsonResponse<>();
	        resp.setBody(responseData);
	        resp.setMessage("Job description generated successfully.");
	        resp.setCode("success");
	        
	        return ResponseEntity.ok(resp);
	    } catch (Exception e) {
	        JsonResponse<Map<String, String>> errorResp = new JsonResponse<>();
	        errorResp.setBody(null);
	        errorResp.setMessage("Failed to generate job description: " + e.getMessage());
	        errorResp.setCode("error");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResp);
	    }
	}*/

	private String formatJobDescription(String rawDescription) {
	    // Convert to clean HTML with bold headers but no markdown symbols
	    return rawDescription
	        .replaceAll("\\*\\*(.*?)\\*\\*", "<strong>$1</strong>")
	        .replaceAll("(?m)^#+ (.*)$", "<h3><strong>$1</strong></h3>")
	        .replaceAll("- (.*)", "<li>$1</li>")
	        .replaceAll("\n", "<br>");
	}

	private String convertToMarkdown(String rawDescription) {
	    // Generate clean plain text without markdown symbols
	    return rawDescription
	        .replaceAll("\\*\\*", "")
	        .replaceAll("#", "")
	        .replaceAll("(?m)^[=-]+$", "")
	        .replaceAll("^- ", "• ");
	}

	/*private String buildPrompt(Map<String, Object> jobData) {
	    // Format salary range
	    String salaryRange = "";
	    if (jobData.containsKey("minSalary") && jobData.containsKey("maxSalary")) {
	        salaryRange = String.format("₹%,.0f - ₹%,.0f per annum", 
	            Double.parseDouble(jobData.get("minSalary").toString()),
	            Double.parseDouble(jobData.get("maxSalary").toString()));
	    }

	    // Format experience range
	    String experienceRange = "";
	    if (jobData.containsKey("minExp") && jobData.containsKey("maxExp")) {
	        experienceRange = String.format("%s-%s years", 
	            jobData.get("minExp").toString(),
	            jobData.get("maxExp").toString());
	    }

	    // Format skills
	    StringBuilder skillsBuilder = new StringBuilder();
	    StringBuilder competencyBuilder = new StringBuilder();
	    if (jobData.containsKey("skillsData")) {
	        List<Map<String, Object>> skillsData = (List<Map<String, Object>>) jobData.get("skillsData");
	        for (Map<String, Object> skill : skillsData) {
	            skill.forEach((key, value) -> {
	                if (!"ratings".equals(key) && !"skillsCompetency".equals(key)) {
	                    skillsBuilder.append(String.format("- %s %s years experience \n",
	                            key, value, skill.get("ratings"), skill.get("skillsCompetency")));
	                }
	            });
	            // Append skillsCompetency in a new section
	            Object comp = skill.get("skillsCompetency");
	            if (comp != null && !comp.toString().isBlank()) {
	                competencyBuilder.append("- ").append(comp.toString()).append("\n");
	            }
	        }
	    }

	    // Format benefits
	    String benefits = "";
	    if (jobData.containsKey("benefits")) {
	        List<String> benefitList = (List<String>) jobData.get("benefits");
	        benefits = String.join("\n- ", benefitList);
	        if (!benefits.isEmpty()) {
	            benefits = "- " + benefits;
	        }
	    }

	    return String.format(
	        "Generate a professional job description using the following details. Follow this exact structure:\n\n" +
	        "<strong>Job Title</strong>: %s\n\n" +
	        "Company: Nirmalya Labs Private Limited\n" +
	        "Location: %s\n" +
	        "Job Type: %s\n" +
	        "Department: %s\n" +
	        "Designation: %s\n" +
	        "Band: %s\n" +
	        "Number of Positions: %s\n" +
	        "Expected Joining Date: %s\n" +
	        "Application Period: %s to %s\n\n" +
	        "About Us:\n%s\n\n" +
	        "Job Overview:\n[Provide a 2-3 sentence engaging overview of this %s role at %s location]\n\n" +
	        "Key Responsibilities:\n[Generate 5-7 bullet points of key responsibilities for a %s]\n\n" +
	        "Required Qualifications & Skills:\n" +
	        "- Education: %s\n" +
	        "- Experience: %s\n" +
	        "%s\n\n" +  // Skills
	        "Preferred Attributes:\n%s\n\n" +  // Competencies inserted here
	        "Work Hours: %s\n\n" +
	        "Compensation & Benefits:\n" +
	        "- Salary Range: %s\n" +
	        "%s\n\n" +
	        "Interview Process:\n[Describe the interview process stages]\n\n" +
	        "How to Apply:\n[Provide professional application instructions including application period %s to %s]\n\n" +
	        "Additional Instructions:\n" +
	        "- Use professional, corporate tone\n" +
	        "- Keep bullet points clear and concise\n" +
	        "- Structure the content for easy scanning\n" +
	        "- Include all relevant details from above",

	        // Data fields
	        jobData.getOrDefault("jobTitle", ""),
	        jobData.getOrDefault("jobLocation", ""),
	        jobData.getOrDefault("jobType", ""),
	        jobData.getOrDefault("department", ""),
	        jobData.getOrDefault("designation", ""),
	        jobData.getOrDefault("band", ""),
	        jobData.getOrDefault("noPosition", ""),
	        jobData.getOrDefault("joinDate", ""),
	        jobData.getOrDefault("applyStartDate", ""),
	        jobData.getOrDefault("applyEndDate", ""),
	        jobData.getOrDefault("about", ""),
	        jobData.getOrDefault("jobTitle", ""),
	        jobData.getOrDefault("jobLocation", ""),
	        jobData.getOrDefault("jobTitle", ""),
	        jobData.getOrDefault("minEducation", ""),
	        experienceRange,
	        skillsBuilder.toString(),
	        competencyBuilder.toString(),
	        jobData.getOrDefault("workHour", ""),
	        salaryRange,
	        benefits,
	        jobData.getOrDefault("applyStartDate", ""),
	        jobData.getOrDefault("applyEndDate", "")
	    );
	}*/
	
	//Generate Jd Pdf 
	
	@PostMapping("/generate-pdf")
	public ResponseEntity<byte[]> generatePdf(@RequestBody Map<String, String> payload) {
	    try {
	        String htmlContent = payload.get("html");

	        // Ensure well-formed HTML
	        if (!htmlContent.contains("<html")) {
	            htmlContent = "<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body>" + htmlContent + "</body></html>";
	        }

	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        HtmlConverter.convertToPdf(htmlContent, outputStream);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.add("Content-Disposition", "inline; filename=Job_Description.pdf");


	        return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(("Error generating PDF: " + e.getMessage()).getBytes());
	    }
	}
	
	
	@GetMapping("/interviewer-dashboard")
	public String interviwersDashboard(Model model, HttpSession session) {
		logger.info("Method : interviwersDashboard starts");

		logger.info("Method : candidatesSelection ends");

		return "recruitment-new/interviwers-dashboard";
	}
}
