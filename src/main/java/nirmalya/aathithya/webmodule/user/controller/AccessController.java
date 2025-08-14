package nirmalya.aathithya.webmodule.user.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EmailAttachmentSender;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.MailService;
import nirmalya.aathithya.webmodule.recruitment.model.CandidateDetailsModel;
import nirmalya.aathithya.webmodule.user.model.Activity;
import nirmalya.aathithya.webmodule.user.model.ActivityAvlFunctionModel;
import nirmalya.aathithya.webmodule.user.model.Function;
import nirmalya.aathithya.webmodule.user.model.Module;
import nirmalya.aathithya.webmodule.user.model.SelfServiceActivityModel;
import nirmalya.aathithya.webmodule.user.model.User;
import nirmalya.aathithya.webmodule.user.model.UserRolesAndModuleIdModel;

/**
 * @author Nirmalya Labs
 *
 */


@Controller
public class AccessController {
	private static final String LOGIN_MESSAGE = "loginMessage";

	private static final String MESSAGE = "message";

	private static final String SUSER = "suser";

	public static final String MOBILE_WEB_VIEW = "webView";
	Logger logger = LoggerFactory.getLogger(AccessController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRolesAndModuleIdModel userModel;

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private String port;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;
	
	
	@Autowired
	MailService mailservice;

	/**
	 * Function show login form
	 *
	 */

	@GetMapping("/login")
	public String login(Model model, HttpSession session) {
		logger.info("Method : login starts");

		String message = (String) session.getAttribute(LOGIN_MESSAGE);

		if (StringUtils.isNotBlank(message)) {
			model.addAttribute(LOGIN_MESSAGE, message);
			session.setAttribute(LOGIN_MESSAGE, null);
		}

//		PasswordEncoder encoder = new BCryptPasswordEncoder();
//		String pass = "9871159601@28";
//		
//		System.out.println(encoder.encode(pass));

		logger.info("Method : login ends");
		return "nerp_hrms_login";
	}

	/**
	 * Function for home page
	 *
	 */
	@GetMapping({"/", "/home"})
	public String home(Model model) {
		logger.info("Method : / starts");

		logger.info("Method : / ends");

		return "lms_index";
	}
	
	

	/*
	 * @GetMapping("/candidate-profile") public String candidateprofile(Model model,
	 * HttpSession session) { logger.info("Method : candidateprofile starts");
	 * 
	 * logger.info("Method : candidateprofile ends"); return "candidate-profile"; }
	 */

	// ceo-dashboard
	@GetMapping("/ceo-dashboard")
	public String ceoDashboard(Model model) {
		logger.info("Method : ceoDashboard starts");

		logger.info("Method : ceoDashboard ends");
		return "ceo-dashboard";
	}

	/**
	 * Function to check connection
	 *
	 */
	@GetMapping("welcome")
	public String welcome(Model model, HttpSession session) {
		logger.info("Method : welcome starts");

		logger.info("Method : welcome ends");
		return "welcome";
	}

	/**
	 * Function to check connection
	 *
	 */
	/*
	 * @GetMapping("hrms-dashboard") public String hrmsDashboard(Model model,
	 * HttpSession session) { logger.info("Method : hrmsDashboard starts");
	 * 
	 * logger.info("Method : hrmsDashboard ends"); return "employee/hrms-dashboard";
	 * }
	 */

	@GetMapping("/hrms-index")
	public String hrmsIndex(Model model, HttpSession session) {
		logger.info("Method : hrmsIndex starts");

		logger.info("Method : hrmsIndex ends");
		return "hrms-index";
	}

	@GetMapping("/cart")
	public String cartPage(Model model, HttpSession session) {
		logger.info("Method : cartPage starts");

		logger.info("Method : cartPage ends");
		return "lms/course-cart";
	}

	@GetMapping("/checkout")
	public String checkoutPage(Model model, HttpSession session) {
		logger.info("Method : checkoutPage starts");

		logger.info("Method : checkoutPage ends");
		return "lms/course_checkout";
	}

	/**
	 * Function to show register user form
	 *
	 */

	@GetMapping("register")
	public String addUser(Model model, HttpSession session) {
		logger.info("Method : register starts");
		User user = new User();
		User form = (User) session.getAttribute(SUSER);
		String message = (String) session.getAttribute(MESSAGE);
		if (StringUtils.isNotBlank(message)) {
			model.addAttribute(MESSAGE, message);
		}
		session.setAttribute(MESSAGE, "");
		if (form != null) {
			form.setUserPassword(null);
			model.addAttribute("user", form);
			session.setAttribute(SUSER, null);
		} else {
			model.addAttribute("user", user);
		}
		logger.info("Method : register ends");
		return "register";
	}

	/**
	 * Function show login form
	 *
	 */

	@GetMapping("/term-and-condition")
	public String termAndCondition(Model model, HttpSession session) {
		logger.info("Method : termAndCondition starts");

		logger.info("Method : termAndCondition ends");
		return "term-and-condition.html";
	}

	/**
	 * Function for Forgot Password page
	 *
	 */
	@GetMapping("/forgot-password")
	public String forgotePasword(Model model) {
		logger.info("Method : forgotePasword starts");

		logger.info("Method : forgotePasword ends");
		return "hrmsForgotPwd";
	}

	/**
	 * Function for get otp
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/get-otp")
	public @ResponseBody JsonResponse<Object> getOtp(HttpSession session, @RequestBody DropDownModel dropDownModel) {
		logger.info("Method : getOtp starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restTemplate.postForObject(env.getApiUrl() + "forgot-password-get-otp", dropDownModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			DropDownModel seat = mapper.convertValue(resp.getBody(), new TypeReference<DropDownModel>() {
			});

			resp.setBody(seat);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getCode() == "" && resp.getCode() == null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");

		}
		logger.info("Method : getOtp ends");

		return resp;
	}

	/**
	 * Function for save new password
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/save-new-password")
	public @ResponseBody JsonResponse<Object> saveNewPassword(HttpSession session,
			@RequestBody DropDownModel dropDownModel) {
		logger.info("Method : saveNewPassword starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {

			resp = restTemplate.postForObject(env.getApiUrl() + "change-password", dropDownModel, JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();

			DropDownModel seat = mapper.convertValue(resp.getBody(), new TypeReference<DropDownModel>() {
			});

			resp.setBody(seat);
		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getCode() == "" && resp.getCode() == null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");

		}
		logger.info("Method : saveNewPassword ends");

		return resp;
	}

	/**
	 * Function show index page after login
	 *
	 */

	@GetMapping("access-denied")
	public String accessDenied(Model model, HttpSession session) {
		logger.info("Method : access-denied starts");

		logger.info("Method : access-denied ends");
		return "accessDenied";
	}

	/**
	 * Function to logout user
	 *
	 */
	@GetMapping("logout")
	public String logout(Model model, HttpSession session) {
		logger.info("Method : access-denied Starts");

		session.invalidate();

		logger.info("Method : access-denied ends");
		return "redirect:";
	}

	/**
	 * Function to post register user form
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("addUser")
	public String addUserForm(@ModelAttribute User user, Model model, HttpSession session) {
		logger.info("Method POST : addUser starts");

		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();

		try {
			String enc = user.getUserPassword();
			if (enc != null && enc != "") {
				enc = passwordEncoder.encode(enc);
				user.setUserPassword(enc);
			}

			jsonResponse = restTemplate.postForObject(env.getUserUrl() + "registerUser", user, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (jsonResponse.getMessage() != "") {
			session.setAttribute("message", jsonResponse.getMessage());
			session.setAttribute("suser", user);
			return "redirect:register";
		}

		logger.info("Method POST : addUser ends");
		return "redirect:login";
	}

	/**
	 * for dashboard index page
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/index")
	public String index(Model model, HttpSession session) {
		logger.info("Method : index starts");

		try {
			SelfServiceActivityModel[] selfService = restTemplate.getForObject(env.getUserUrl() + "getselfservicelist",
					SelfServiceActivityModel[].class);
			List<SelfServiceActivityModel> selfServiceList = Arrays.asList(selfService);

			model.addAttribute("selfServiceList", selfServiceList);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);
		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol010")) {
				model.addAttribute("adRole", data);
			} else if (data.contentEquals("rol003")) {
				model.addAttribute("mrRole", data);
			} else {
				model.addAttribute("empRole", data);
			}
		}
		try {
			DropDownModel[] Gender = restTemplate.getForObject(env.getEmployeeUrl() + "getgenderList1",
					DropDownModel[].class);
			List<DropDownModel> genderTypeList = Arrays.asList(Gender);
			model.addAttribute("genderTypeList", genderTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
//		try {
//			DropDownModel[] Nationality = restTemplate.getForObject(env.getEmployeeUrl() + "getnationalityList1",
//					DropDownModel[].class);
//			List<DropDownModel> nationalityList = Arrays.asList(Nationality);
//
//			model.addAttribute("nationalityList", nationalityList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

		try {
			DropDownModel[] BloodGroup = restTemplate.getForObject(env.getEmployeeUrl() + "getbloodgroupList1",
					DropDownModel[].class);
			List<DropDownModel> bloodgroupList = Arrays.asList(BloodGroup);

			model.addAttribute("bloodgroupList", bloodgroupList);
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
			DropDownModel[] Country = restTemplate.getForObject(env.getEmployeeUrl() + "getCountryList",
					DropDownModel[].class);
			List<DropDownModel> counntryList = Arrays.asList(Country);

			model.addAttribute("counntryList", counntryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] addressType = restTemplate.getForObject(env.getRecruitment() + "addressTypeList",
					DropDownModel[].class);
			List<DropDownModel> addressTypeList = Arrays.asList(addressType);
			model.addAttribute("addressTypeList", addressTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

//		try {
//			DropDownModel[] JobType = restTemplate.getForObject(
//					env.getEmployeeUrl() + "getJobType1?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//			List<DropDownModel> jobtypeList = Arrays.asList(JobType);
//
//			model.addAttribute("jobtypeList", jobtypeList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

//		try {
//			DropDownModel[] Department = restTemplate.getForObject(env.getEmployeeUrl()
//					+ "getDepartmentList1?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//			List<DropDownModel> DepartmentList = Arrays.asList(Department);
//
//			model.addAttribute("DepartmentList", DepartmentList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

//		try {
//			DropDownModel[] TimesheetType = restTemplate.getForObject(env.getEmployeeUrl()
//					+ "getTimesheetType1?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//			List<DropDownModel> TimesheetList = Arrays.asList(TimesheetType);
//
//			model.addAttribute("TimesheetList", TimesheetList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

//		try {
//
//			DropDownModel[] band = restTemplate.getForObject(
//					env.getRecruitment() + "bandList?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//			List<DropDownModel> bandList = Arrays.asList(band);
//			model.addAttribute("bandList", bandList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

//		try {
//			DropDownModel[] employmentType = restTemplate.getForObject(env.getEmployeeUrl()
//					+ "getemploymentType1?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//			List<DropDownModel> employmentstatusList = Arrays.asList(employmentType);
//
//			model.addAttribute("employmentstatusList", employmentstatusList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

//		try {
//
//			DropDownModel[] manager = restTemplate.getForObject(
//					env.getEmployeeUrl() + "EmployeeList?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//			List<DropDownModel> managerList = Arrays.asList(manager);
//			model.addAttribute("EmployeeList", managerList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

//		try {
//			DropDownModel[] Benefits = restTemplate.getForObject(
//					env.getEmployeeUrl() + "getBenefits?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//			List<DropDownModel> benefitsList = Arrays.asList(Benefits);
//
//			model.addAttribute("benefitsList", benefitsList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

//		try {
//			DropDownModel[] dependentType = restTemplate.getForObject(env.getEmployeeUrl() + "dependentTypeList",
//					DropDownModel[].class);
//			List<DropDownModel> dependentTypeList = Arrays.asList(dependentType);
//
//			model.addAttribute("dependentTypeList", dependentTypeList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

//		try {
//			DropDownModel[] relationship = restTemplate.getForObject(env.getEmployeeUrl() + "relationshipList",
//					DropDownModel[].class);
//			List<DropDownModel> relationshipList = Arrays.asList(relationship);
//
//			model.addAttribute("relationshipList", relationshipList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

//		try {
//			DropDownModel[] Bank = restTemplate.getForObject(env.getEmployeeUrl() + "getBankNameList",
//					DropDownModel[].class);
//			List<DropDownModel> BankNameList = Arrays.asList(Bank);
//
//			model.addAttribute("BankNameList", BankNameList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

//		try {
//			DropDownModel[] Bank = restTemplate.getForObject(env.getEmployeeUrl() + "insuranceCompanyList?organization="
//					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
//			List<DropDownModel> insuranceCompanyList = Arrays.asList(Bank);
//
//			model.addAttribute("insuranceCompanyList", insuranceCompanyList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

		try {
			DropDownModel[] Bank = restTemplate.getForObject(env.getEmployeeUrl() + "documentTypeList",
					DropDownModel[].class);
			List<DropDownModel> documentTypeList = Arrays.asList(Bank);

			model.addAttribute("documentTypeList", documentTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

//		try {
//			DropDownModel[] dropDown = restTemplate.getForObject(env.getEmployeeUrl() + "getDesignations?organization="
//					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
//			List<DropDownModel> Designation = Arrays.asList(dropDown);
//			model.addAttribute("Designation", Designation);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
		try {
			DropDownModel[] dropDown = restTemplate.getForObject(env.getEmployeeUrl()
					+ "getQualifyEduListdropdown?orgName=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> Designation = Arrays.asList(dropDown);
			model.addAttribute("qualifyList", Designation);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
//		try {
//			DropDownModel[] departmentType = restTemplate.getForObject(env.getMasterUrl()
//					+ "getDepartmentTypeForShiftType?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//
//			List<DropDownModel> department = Arrays.asList(departmentType);
//			model.addAttribute("department", department);
//
//		} catch (RestClientException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			DropDownModel[] subdepartmentType = restTemplate.getForObject(env.getMasterUrl()
//					+ "getSubDepartmentTypeForShiftType?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//
//			List<DropDownModel> subdepartment = Arrays.asList(subdepartmentType);
//			model.addAttribute("subdepartment", subdepartment);
//
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//		try {
//			DropDownModel[] financialYrType = restTemplate.getForObject(env.getMasterUrl()
//					+ "getFinancialYrForSalaryRevision?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//
//			List<DropDownModel> financialYr = Arrays.asList(financialYrType);
//			model.addAttribute("financialYr", financialYr);
//
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

//		try {
//			DropDownModel[] dropDown = restTemplate.getForObject(env.getMasterUrl()
//					+ "rest-getDesignationDropDown?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//			List<DropDownModel> Designation = Arrays.asList(dropDown);
//			model.addAttribute("Designation", Designation);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//		try {
//			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl() + "getYearList-attendance?organization="
//					+ organization + "&orgDivision=" + orgDivision, DropDownModel[].class);
//			List<DropDownModel> yearList = Arrays.asList(year);
//
//			model.addAttribute("yearList", yearList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			DropDownModel[] type = restTemplate.getForObject(env.getMasterUrl()
//					+ "getStaffTypeMasterAttendance?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//			List<DropDownModel> typeList = Arrays.asList(type);
//			model.addAttribute("typeList", typeList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//		try {
//			DropDownModel[] employmentType = restTemplate.getForObject(env.getEmployeeUrl()
//					+ "getemployedByList?organization=" + organization + "&orgDivision=" + orgDivision,
//					DropDownModel[].class);
//			List<DropDownModel> employmentstatusList = Arrays.asList(employmentType);
//			model.addAttribute("employedByList", employmentstatusList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//		try {
//			DropDownModel[] offerletter = restTemplate.getForObject(env.getEmployeeUrl() + "OfferletterList",
//					DropDownModel[].class);
//			List<DropDownModel> offerletterList = Arrays.asList(offerletter);
//			model.addAttribute("offerletterList", offerletterList);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		String dashboard = (String) session.getAttribute("DASHBOARD");

		logger.info("Method : index  extend-index ends");
		return "new-index";
//		return "nerp-hrms-index";
	}

//	@SuppressWarnings("unchecked")
//	@PostMapping(value = { "index-sales-report" })
//	public @ResponseBody JsonResponse<MapModel1> getSalesReportGraph(Model model) {
//		logger.info("Method : getSalesReportGraph starts");
//		JsonResponse<MapModel1> res = new JsonResponse<MapModel1>();
//
//		try {
//			res = restTemplate.getForObject(env.getRestaurantUrl() + "dbSalesReport", JsonResponse.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (res.getMessage() != null) {
//			res.setCode(res.getMessage());
//			res.setMessage("Unsuccess");
//		} else {
//			res.setMessage("success");
//		}
//		logger.info("Method : getSalesReportGraph ends");
//
//		return res;
//	}
//
//	@SuppressWarnings("unchecked")
//	@PostMapping(value = { "index-order-report" })
//	public @ResponseBody JsonResponse<MapModel1> getOrderReportGraph(Model model) {
//		logger.info("Method : getOrderReportGraph starts");
//		JsonResponse<MapModel1> res = new JsonResponse<MapModel1>();
//
//		try {
//			res = restTemplate.getForObject(env.getRestaurantUrl() + "dbOrderReport", JsonResponse.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if (res.getMessage() != null) {
//			res.setCode(res.getMessage());
//			res.setMessage("Unsuccess");
//		} else {
//			res.setMessage("success");
//		}
//		logger.info("Method : getOrderReportGraph ends");
//
//		return res;
//	}

	// for order status page

//	@GetMapping("/order-status")
//	public String dashboard(Model model) {
//		logger.info("Method : /dashboard starts");
//
//		try {
//			OrderStatusModel[] order = restTemplate.getForObject(env.getRestaurantUrl() + "getOrderStatus",
//					OrderStatusModel[].class);
//			List<OrderStatusModel> orderList1 = Arrays.asList(order);
//
//			model.addAttribute("orderList1", orderList1);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		try {
//			OrderStatusModel[] order2 = restTemplate.getForObject(env.getRestaurantUrl() + "getOrderStatusReady",
//					OrderStatusModel[].class);
//			List<OrderStatusModel> orderList2 = Arrays.asList(order2);
//
//			model.addAttribute("orderList2", orderList2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		try {
//			OrderStatusModel[] discount = restTemplate.getForObject(env.getRestaurantUrl() + "getDiscountDetails",
//					OrderStatusModel[].class);
//			List<OrderStatusModel> discountList = Arrays.asList(discount);
//
//			for (int i = 0; i < discountList.size(); i++) {
//				if (i % 4 == 0) {
//					discountList.get(i).setStatus("bg1.jpg");
//					discountList.get(i).setDiscountImage("offer.png");
//				} else if (i % 4 == 1) {
//					discountList.get(i).setStatus("bg2.jpg");
//					discountList.get(i).setDiscountImage("offer2.png");
//				} else if (i % 4 == 2) {
//					discountList.get(i).setStatus("bg3.jpg");
//					discountList.get(i).setDiscountImage("offer3.png");
//				} else {
//					discountList.get(i).setStatus("bg4.jpg");
//					discountList.get(i).setDiscountImage("offer4.png");
//				}
//			}
//
//			model.addAttribute("discountList", discountList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		logger.info("Method : /dashboard ends");
//		return "dashboard";
//		// return "app_index";
//	}

	/**
	 * Web Controller for view all assigned kitchen to restaurant
	 *
	 */
//	@SuppressWarnings("unchecked")
//	@GetMapping("/restaurant/kitchen-staff-order-details")
//	public String getKitchenStaffOrderDetails(@RequestParam String id, Model model) {
//
//		logger.info("Method : getKitchenStaffOrderDetails starts");
//
//		DataTableRequest tableRequest = new DataTableRequest(); 
//		 
//		try {
//			// String UserId = (String) session.getAttribute("USER_ID");
//
//			tableRequest.setParam1(id);
//			// tableRequest.setUserId(UserId);
//
//			JsonResponse<List<KitchenStaffFoodOrderListModel>> jsonResponse = new JsonResponse<List<KitchenStaffFoodOrderListModel>>();
//
//			jsonResponse = restTemplate.postForObject(env.getKitchenUrl() + "getFoodListForView", tableRequest,
//					JsonResponse.class);
//
//			ObjectMapper mapper = new ObjectMapper();
//
//			List<KitchenStaffFoodOrderListModel> assignTS = mapper.convertValue(jsonResponse.getBody(),
//					new TypeReference<List<KitchenStaffFoodOrderListModel>>() {
//					});
//
//			String s = "";
//
//			for (KitchenStaffFoodOrderListModel m : assignTS) {
//
//				byte[] pId = Base64.getEncoder().encode(m.getFoodOrderId().getBytes());
//
//				/*
//				 * m.
//				 * setFoodOrderId("<a data-toggle='modal' title='View' data-target='#myModal1' href='javascript:void(0)' onclick='viewInModel(\""
//				 * + new String(pId) + "\")'>"+m.getFoodOrderId()+"</a>");
//				 */
//
//				s = s + "<a href='javascript:void(0)'" + " onclick='printPDF(\"" + new String(pId)
//						+ "\")' ><i class=\"fa fa-download\" title=\"PDF\" style=\"color:#d00c08;font-size:24px;\"></i></a>&nbsp;&nbsp;";
//
//				if (m.getFoodPrepareStatus() == 1) {
//					s = s + "<a href='javascript:void(0)'" + " onclick='changePrepareStatus(\"" + new String(pId)
//							+ "\")' ><i class=\"fa fa-cutlery\" title=\"Receive\" style=\"color:#e30f0f;font-size:24px;\"></i></a>&nbsp;&nbsp;";
//				} else {
//					if (m.getKitchenStatus() == 1) {
//
//						s = s + "<a href='javascript:void(0)'" + " onclick='changeKitchenStatus(\"" + new String(pId)
//								+ "\")' ><i class=\"fa fa-times-circle\" title=\"In Progress\" style=\"color:#e30f0f;font-size:24px;\"></i></a>&nbsp;&nbsp;";
//
//					} else if (m.getKitchenStatus() == 2) {
//
//						s = s + "<a href='javascript:void(0)'"
//								+ "' onclick='' ><i class=\"fa fa-check-circle\" title=\"Ready To Delivered\" style=\"color:#090;cursor: context-menu;font-size:24px;\"></i></a>&nbsp;&nbsp;";
//					}
//				}
//
//				m.setAction(s);
//				s = "";
//
//			}
//			model.addAttribute("orderData", assignTS);
//			model.addAttribute("storeId", id);
//			JsonResponse<Object> res = new JsonResponse<Object>();
//
//			try {
//				res = restTemplate.getForObject(env.getKitchenUrl() + "getOrderSummary?id=" + id, JsonResponse.class);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			ObjectMapper mapper1 = new ObjectMapper();
//
//			List<KitchenItemDetailsModel> itemSummery = mapper1.convertValue(res.getBody(),
//					new TypeReference<List<KitchenItemDetailsModel>>() {
//					});
//			model.addAttribute("itemSummery", itemSummery);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		logger.info("Method : getKitchenStaffOrderDetails ends");
//		return "kitchen/gocool-get-kitchen-order-status";
//	}

	/**
	 * Web Controller - Get details For Modal
	 *
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "/restaurant/kitchen-staff-order-details-modal" })
	public @ResponseBody JsonResponse<Object> modalQuotation(Model model, @RequestBody String index,
			BindingResult result) {

		logger.info("Method : summaryModal starts");
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getKitchenUrl() + "getOrderSummary?id=" + index, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : summaryModal ends");
		return res;
	}

	/**
	 * for dashboard index page
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/index-get-function-list/{id}")
	public String getMenuDetails(Model model, HttpSession session, @PathVariable String id) {
		logger.info("Method : index starts");

		List<Module> module = new ArrayList<Module>();
		List<Function> funDetails = new ArrayList<Function>();
		String activityUrl = "";
		try {

			module = (List<Module>) session.getAttribute("MENU");
			if (module != null && module.size() > 0) {
				List<Module> fList = module.stream().filter(s -> s.getModuleId().equals(id))
						.collect(Collectors.toList());
				if (!fList.isEmpty()) {
					funDetails = fList.get(0).getModule();
					for (Function a : funDetails) {
						if (a.getFunction() != null) {
							a.setDefaultUrl(a.getFunction().get(0).getActivity());
						}

					}
				}

				for (Function f : funDetails) {
					logger.info("function list size: " + f.getName());
				}

				session.setAttribute("funList", funDetails);
				session.setAttribute("moduleId", id);
				if (!funDetails.isEmpty()) {
					activityUrl = funDetails.get(0).getFunction().get(0).getActivity();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : index  extend-index ends");

		logger.info("Act URL=====" + activityUrl);

		// return "extend-index2";
		return "redirect:" + activityUrl;

	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/index-get-activity-list" })
	public @ResponseBody JsonResponse<List<Activity>> getActivityList(@RequestParam String funId,
			@RequestParam String moduleId, HttpSession session) {
		logger.info("Method : getActivityList starts");

		JsonResponse<List<Activity>> res = new JsonResponse<List<Activity>>();
		List<Activity> activityList = new ArrayList<Activity>();

		try {
			List<Module> module = new ArrayList<Module>();
			try {

				module = (List<Module>) session.getAttribute("MENU");
				if (module != null && module.size() > 0) {
					List<Module> fList = module.stream().filter(s -> s.getModuleId().equals(moduleId))
							.collect(Collectors.toList());
					if (fList != null) {
						List<Function> funDetails = (List<Function>) fList.get(0).getModule().stream()
								.filter(a -> a.getFunctionId().equals(funId)).collect(Collectors.toList());
						activityList = funDetails.get(0).getFunction();
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			res.setBody(activityList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getActivityList ends");
		return res;
	}

	/**
	 * for dashboard index page
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/index-get-function-list-resp")
	public @ResponseBody JsonResponse<List<Activity>> getFunctionDetails(Model model, HttpSession session,
			@RequestParam String moduleId, @RequestParam String funcId, @RequestParam String activityId) {
		logger.info("Method : index getFunctionDetails starts");

		JsonResponse<List<Activity>> res = new JsonResponse<List<Activity>>();
		List<Activity> activityList = new ArrayList<Activity>();

		List<Module> module = new ArrayList<Module>();
		List<Function> funDetails = new ArrayList<Function>();
		List<Activity> activityDetails = new ArrayList<Activity>();
		String activityUrl = "";
		try {

			module = (List<Module>) session.getAttribute("MENU");
			if (module != null && module.size() > 0) {
				List<Module> fList = module.stream().filter(s -> s.getModuleId().equals(moduleId))
						.collect(Collectors.toList());
				if (!fList.isEmpty()) {
					funDetails = fList.get(0).getModule();
					for (Function a : funDetails) {
						if (a.getFunction() != null) {
							a.setDefaultUrl(a.getFunction().get(0).getActivity());
							a.setDefaultUrlId(a.getFunction().get(0).getActivityId());
						}
						List<Activity> act = a.getFunction();
						activityDetails = Stream.concat(activityDetails.stream(), act.stream())
								.collect(Collectors.toList());
					}
				}
				if (activityDetails.size() > 0) {
					List<String> name = activityDetails.stream()
							.filter(activity -> activityId.equals(activity.getActivityId())).map(a -> a.getActionName())
							.collect(Collectors.toList());
					if (name.size() > 0 && name.get(0) != null && !name.get(0).equals(null)
							&& !name.get(0).equals("null")) {
						String action = name.get(0);
						List<String> result = Arrays.asList(action.split(","));

						session.setAttribute("action_name_list", result);
					} else {
						session.setAttribute("action_name_list", null);
					}
				}

				session.setAttribute("funList", funDetails);
				session.setAttribute("moduleId", moduleId);
				if (!funDetails.isEmpty()) {
					activityUrl = funDetails.get(0).getFunction().get(0).getActivity();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.setBody(activityDetails);
		logger.info("Method : index getFunctionDetails ends");

		// return "extend-index2";
		return res;

	}

	/**
	 * for dashboard index page
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/index-get-breadcrumb-data")
	public @ResponseBody JsonResponse<Object> getBreadcrumbData(Model model, HttpSession session,
			@RequestParam("moduleId") String moduleId, @RequestParam("fun") String funId,
			@RequestParam("activity") String actId) {
		logger.info("Method : index getBreadcrumbData starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			// REST - MenuController
			res = restTemplate.getForObject(
					env.getUserUrl() + "get-breadcrumb-data?modId=" + moduleId + "&funId=" + funId + "&actId=" + actId,
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

		logger.info("Method : index  getBreadcrumbData ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/index-get-module-list" })
	public @ResponseBody JsonResponse<List<Module>> getModuleDetails(Model model, HttpSession session,
			@RequestParam String moduleId) {
		logger.info("Method : getModuleDetails starts");

		JsonResponse<List<Module>> res = new JsonResponse<List<Module>>();

		List<Module> module = (List<Module>) session.getAttribute("MENU");

		res.setBody(module);
		logger.info("Method : getModuleDetails ends");

		return res;

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/index-get-avl-function-list")
	public @ResponseBody JsonResponse<ActivityAvlFunctionModel> getAvlFunctionByActivityRole(
			@RequestBody List<DropDownModel> data, HttpSession session) {
		logger.info("Method : updateModule starts");

		JsonResponse<ActivityAvlFunctionModel> resp = new JsonResponse<ActivityAvlFunctionModel>();

		try {
			resp = restTemplate.postForObject(env.getUserUrl() + "getAvlFunctionByActivityRole", data,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : getAvlFunctionByActivityRole starts");
		return resp;
	}

	/**
	 * Function to check connection
	 *
	 */

	@GetMapping("error")
	public String error(Model model, HttpSession session, HttpServletRequest request) {
		logger.info("Method : error starts");
		System.out.println("Exception: " + request.getAttribute("javax.servlet.error.exception"));
		logger.info("Method : error ends");
		return "error";
	}

	/******************************************************************************************/

	/**
	 * This Function Used for Web View After the Login in the mobile
	 * 
	 * @Pankaj Kumar
	 *
	 */
	@GetMapping("/customlogin")
	public String CustomLogin(Model model, @RequestParam String username, @RequestParam String redirectUrl,
			@RequestParam String viewName, HttpSession session) {
		logger.info("Method : /customlogin starts");

		session.setAttribute(MOBILE_WEB_VIEW, viewName);
		// session.setAttribute("MENU", module);

		if (StringUtils.isEmpty(username)) {
			return "redirect:login";
		}
		try {

			model.addAttribute("username", username);
			model.addAttribute("password", "MasterPswd");
			model.addAttribute("redirectUrl", redirectUrl != null ? redirectUrl : "");
			session.setAttribute("redirectUrl", redirectUrl != null ? redirectUrl : "");
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:login";
		}

		logger.info("Method : /customlogin ends");
		return "nerp_custom_login";
	}

	@GetMapping("/login-width-height")
	public @ResponseBody JsonResponse<Object> setHeightWidth(HttpSession session, @RequestParam String width,
			@RequestParam String height) {
		logger.info("Method : setHeightWidth starts");

		JsonResponse<Object> res = new JsonResponse<>();

		try {
			session.setAttribute("width", width);
			session.setAttribute("height", height);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : setHeightWidth ends");
		return res;
	}

	/**
	 * This Function Used for Job Apply from other source
	 * 
	 * @Pankaj Kumar
	 *
	 */
	@GetMapping("/candidates-job-apply/{encodedData}")
	public String showJobApplyPage(@PathVariable String encodedData, Model model) {
		try {
			String paddedData = addBase64Padding(encodedData);

			byte[] decodedBytes = Base64.getUrlDecoder().decode(paddedData);
			String json = new String(decodedBytes);

			JSONObject obj = new JSONObject(json);
			String requisitionId = obj.getString("requisitionId");

			model.addAttribute("jobId", requisitionId);
			return "recruitment-new/candidates-job-apply";

		} catch (Exception e) {
			return "error/invalid-link";
		}
	}

	private String addBase64Padding(String str) {
		int padLength = (4 - str.length() % 4) % 4;
		StringBuilder sb = new StringBuilder(str);
		for (int i = 0; i < padLength; i++) {
			sb.append("=");
		}
		return sb.toString();
	}

	/**
	 * @Author: Pankaj Kr. Register New Candidate For Jobs
	 */
	@GetMapping("/candidate-profile")
	public String candidateProfileUrl(Model model, HttpSession session) {
		logger.info("Method : candidates-profile: ");

		logger.info("Method : candidates-profile: ");
		return "recruitment-new/candidate-job-profile";
	}

	@GetMapping("/candidate-portal")
	public String candidatePortal(Model model, HttpSession session) {
		logger.info("Method : candidatePortal: ");

		try {

			DropDownModel[] location = restTemplate.getForObject(
					env.getRecruitment() + "jobLocationList?orgName=" + "" + "&orgDivision=" + "",
					DropDownModel[].class);
			List<DropDownModel> jobLocationList = Arrays.asList(location);
			model.addAttribute("jobLocationList", jobLocationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] jobType = restTemplate.getForObject(env.getRecruitment() + "jobTypeList",
					DropDownModel[].class);
			List<DropDownModel> jobTypeList = Arrays.asList(jobType);
			model.addAttribute("jobTypeList", jobTypeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : candidates-profile: ");
		return "recruitment-new/candidate-portal";
	}

	/**
	 * @Author: Pankaj Kr. Register New Candidate For Jobs
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/candidates-job-apply/get-job-details")
	public @ResponseBody JsonResponse<Object> getCandidateJobDetailsForCandidate(HttpSession session,
			@RequestParam String jobId) {

		logger.info("Method : getCandidateJobDetailsForCandidate starts");
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
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-job-details-for-candidate?jobId=" + jobId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : getCandidateJobDetailsForCandidate ends");
		return resp;
	}

	/**
	 * @Author: Pankaj Kr. Register New Candidate For Jobs
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/get-job-list")
	public @ResponseBody JsonResponse<Object> getCandidatesAllJobList(@RequestParam(required = false) String search,
			@RequestParam(required = false) String location, HttpSession session) {

		logger.info("Method : getCandidatesAllJobList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";
		String userId = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error("Error fetching session attributes: " + e.getMessage());
		}

		try {
			String url = env.getRecruitment() + "rest-get-job-list-for-candidate";

			// Append search and location parameters if available
			List<String> params = new ArrayList<>();
			// if (search != null && !search.isEmpty()) {
			// params.add("search=" + URLEncoder.encode(search, StandardCharsets.UTF_8));
			// }
			// if (location != null && !location.isEmpty()) {
			// params.add("location=" + URLEncoder.encode(location,
			// StandardCharsets.UTF_8));
			// }

			if (!params.isEmpty()) {
				url += "?" + String.join("&", params);
			}

			resp = restTemplate.getForObject(url, JsonResponse.class);
		} catch (RestClientException e) {
			logger.error("Error calling recruitment API: " + e.getMessage());
		}

		logger.info("Method : getCandidatesAllJobList ends");
		return resp;
	}

	/**
	 * @Author: Pankaj Kr. Register New Candidate For Jobs
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/registerCandidate")
	public @ResponseBody JsonResponse<CandidateDetailsModel> registerCandidate(Model model, HttpSession session,
			@RequestBody CandidateDetailsModel reqModel) {

		logger.info("Method : registerCandidate starts");
		JsonResponse<CandidateDetailsModel> resp = new JsonResponse<CandidateDetailsModel>();
		String dateFormat = "";
		try {
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * if(reqModel.getDob()!=null && reqModel.getDob()!="") {
		 * reqModel.setDob(DateFormatter.inputDateFormat(reqModel.getDob(),
		 * dateFormat)); }
		 */
		try {
			resp = restTemplate.postForObject(env.getRecruitment() + "addCandidate", reqModel, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (resp.getCode() == "success" || resp.getCode().contains("success")) {

			try {
				session.removeAttribute("candidateProfileImg");
			} catch (RestClientException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("Method : registerCandidate ends");

		return resp;
	}

	/*
	 * @Author: Pankaj Kr. Save Otp to the server
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/sendOtp")
	public @ResponseBody JsonResponse<Object> sendCandidateOtp(HttpSession session,
			@RequestBody Map<String, String> requestData) {

		logger.info("Method : sendCandidateOtp starts");

		String email = requestData.get("email");
		logger.info("Received email: " + email);

		JsonResponse<Object> resp = new JsonResponse<>();

		try {
			int otp = (int) (Math.random() * 9000) + 1000;
			LocalDateTime currentTime = LocalDateTime.now();
			LocalDateTime expiryTime = currentTime.plusMinutes(3); // Add 3 minutes

			// Format Date and Time as "YYYY-MM-DD HH:mm:ss"
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = currentTime.format(formatter);
			String formattedExpiryTime = expiryTime.format(formatter);
			List<String> emailList = Collections.singletonList(email);

			String subject = "Your OTP for Registration";
			String message = "Dear Candidate,\n\nYour OTP for job registration is: " + otp
					+ "\n\nPlease enter this OTP to proceed.\n\nBest Regards,\nNirmalya Team";

			boolean emailSent = EmailAttachmentSender.sendEmailForOtp(host, port, username, password, emailList, null,
					subject, message, null);

			if (emailSent) {
				Map<String, Object> otpData = new HashMap<>();
				otpData.put("email", email);
				otpData.put("otp", otp);
				otpData.put("otpDate", formattedDateTime);
				otpData.put("expiryDate", formattedExpiryTime); // Expiry timestamp (+3 min)

				try {
					resp = restTemplate.postForObject(env.getMasterUrl() + "save-otp", otpData, JsonResponse.class);
				} catch (RestClientException e) {
					logger.error("Error calling save-otp API: ", e);
					resp.setMessage("Failed to save OTP.");
					resp.setCode("500");
					return resp;
				}

				resp.setMessage("OTP sent successfully to " + email);
				resp.setCode("200");
				session.setAttribute("candidateOtp", otp);
			} else {
				resp.setMessage("Failed to send OTP. Please try again.");
				resp.setCode("500");
			}
		} catch (Exception e) {
			logger.error("Error in sendCandidateOtp: ", e);
			resp.setMessage("Error occurred while sending OTP.");
			resp.setCode("500");
		}

		logger.info("Method : sendCandidateOtp ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/verifyOtp")
	public @ResponseBody JsonResponse<Object> verifyCandidateOtp(HttpSession session, @RequestParam String otp,
			@RequestParam String email) {

		logger.info("Method : verifyCandidateOtp starts");
		logger.info("Verifying OTP for email: " + email);

		JsonResponse<Object> resp = new JsonResponse<>();

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "fetch-otp?otp=" + otp + "&id=" + email,
					JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in verifyCandidateOtp: ", e);
			resp.setMessage("Error occurred while verifying OTP.");
			resp.setCode("500");
		}

		logger.info("Method : verifyCandidateOtp ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping("view-new-requi-mstr-skills")
	public @ResponseBody JsonResponse<List<DropDownModel>> getSkillList(HttpSession session) {

		logger.info("Get getSkillList Details Start");

		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();

		try {
			resp = restTemplate.getForObject(env.getRecruitment() + "rest-get-reqSkill", JsonResponse.class);
		} catch (RestClientException e) {

			e.printStackTrace();
		}

		logger.info("Get getSkillList Details End");
		return resp;
	}

	// Inside your method
	@GetMapping("/feedback-form")
	public String feedBack(Model model, HttpSession session, @RequestParam String id) {
		logger.info("Method: employee-appraisal-form starts here");

		logger.info("Method: employee-appraisal-form ends here");
		return "appraisal-v2/employee-appraisal-form";
	}

	// Inside your method
	@GetMapping("/sattkara-free-trail-registration")
	public String sattkaraFreeTrailRegistartion(Model model, HttpSession session) {
		logger.info("Method: sattkara-free-trail-registartion starts here");
		try {
			DropDownModel[] Country = restTemplate.getForObject(env.getEmployeeUrl() + "getCountryList",
					DropDownModel[].class);
			List<DropDownModel> counntryList = Arrays.asList(Country);

			model.addAttribute("counntryList", counntryList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method: sattkara-free-trail-registartion ends here");
		return "hotel/free-trail-registration";
	}

	/* get-all-review-details */
	@SuppressWarnings("unchecked")
	@GetMapping("/get-all-review-details")
	public @ResponseBody Object getAllReviewById(HttpSession session, @RequestParam String id,
			@RequestParam String email) {
		logger.info("Method :getAllReviewById starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getAppraisalUrl() + "rest-get-review-byId?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id + "&email=" + email, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setBody(resp.getBody());
		resp.setCode(resp.getCode());
		resp.setMessage(resp.getMessage());
		logger.info("Method :getAllReviewById ends" + resp);
		return resp;
	}

	/* save-manager-response */
	@SuppressWarnings("unchecked")
	@PostMapping("/save-manager-response")
	public @ResponseBody JsonResponse<Object> saveFeedback(HttpSession session,
			@RequestBody Map<String, Object> responseData) {
		logger.info("Method : saveFeedback starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		String baseUrl = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			baseUrl = env.getBaseURL();
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.postForObject(env.getAppraisalUrl() + "rest-save-feedback?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision + "&baseUrl=" + baseUrl, responseData, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : saveFeedback ends");
		return resp;
	}

	@GetMapping("/academic/application-program")
	public String showApplicationTemplatePage(@RequestParam String applicationId, Model model) {
		try {
			System.out.println("Id is Comming--" + applicationId);
			return "lms/aplication-program";

		} catch (Exception e) {
			return "error/invalid-link";
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("academic-course-view")
	public @ResponseBody Object viewCourse(HttpSession session) {
		logger.info("Method :viewCourse starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String orgName = "Nirmalya Labs Private Limited";
			String orgDivision = "Nirmalya Labs";

			resp = restTemplate.getForObject(
					env.getHisUrl() + "rest-viewCourse?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :viewCourse ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping("get-all-product-details")
	public @ResponseBody Object getAllProductDetails() {
		logger.info("Method : getAllProductDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String orgName = "Nirmalya Labs Private Limited";
			String orgDivision = "Nirmalya Labs";

			resp = restTemplate.getForObject(env.getMasterUrl() + "rest-get-all-product-details", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : getAllProductDetails ends");
		return resp;

	}

	@GetMapping("/all-courses")
	public String coursesPage(Model model, HttpSession session) {
		logger.info("Method : coursesPage starts");
 
		logger.info("Method : coursesPage ends");
		return "lms/all-courses";
	}
	
	@GetMapping("/courses-categories")
	public String coursesCategories(Model model, HttpSession session) {
		logger.info("Method : coursesCategories starts");
 
		logger.info("Method : coursesCategories ends");
		return "lms/course-categories";
	}
	
	@GetMapping("/aboutus")
	public String aboutUs(Model model, HttpSession session) {
		logger.info("Method : aboutUs starts");
 
		logger.info("Method : aboutUs ends");
		return "lms/lms-aboutus";
	}

	@GetMapping("/shop")
	public String shop(Model model, HttpSession session) {
		logger.info("Method : shop starts");
 
		logger.info("Method : shop ends");
		return "lms/shop";
	}

   @GetMapping("/consultancy")
	public String Consultancy(Model model, HttpSession session) {
		logger.info("Method : consultancy starts");
 
		logger.info("Method : consultancy ends");
		return "lms/consultancy";
	}

	@GetMapping("/atva")
	public String ATVA(Model model, HttpSession session) {
		logger.info("Method : atva starts");
 
		logger.info("Method : atva ends");
		return "lms/atva";
	}

	@GetMapping("/itil-maturity-model")
	public String ITVA(Model model, HttpSession session) {
		logger.info("Method : itil-maturity-model starts");
 
		logger.info("Method : itil-maturity-model ends");
		return "lms/itil-maturity-model";
	}

	@GetMapping("/iso-consulting")
	public String ISO(Model model, HttpSession session) {
		logger.info("Method : iso-consulting starts");
 
		logger.info("Method : iso-consulting ends");
		return "lms/iso-consulting";
	}

	@GetMapping("/generative-ai")
	public String Generative(Model model, HttpSession session) {
		logger.info("Method : generative-ai starts");
 
		logger.info("Method : generative-ai ends");
		return "lms/generative-ai";
	}

	@GetMapping("/blog")
	public String blog(Model model, HttpSession session) {
		logger.info("Method : blog starts");
 
		logger.info("Method : blog ends");
		return "lms/lms-blog";
	}

		@GetMapping("/resources")
		public String resources(Model model, HttpSession session) {
		logger.info("Method : resources starts");
		 
		logger.info("Method : resources ends");
		return "lms/resources";
		}
		 
		@GetMapping("/enterprise")
		public String enterprise(Model model, HttpSession session) {
		logger.info("Method : enterprise starts");
		 
		logger.info("Method : enterprise ends");
		return "lms/enterprise";
		}
		 
		@GetMapping("/upgrading")
		public String upgrading(Model model, HttpSession session) {
		logger.info("Method : upgrading starts");
		 
		logger.info("Method : upgrading ends");
		return "lms/upgrading";
		}

	


	
	@GetMapping("/contactus")
	public String contactUs(Model model, HttpSession session) {
		logger.info("Method : contactUs starts");
 
		logger.info("Method : contactUs ends");
		return "lms/lms-contactus";
	}
	@GetMapping("contactus-email-ajax")
	public @ResponseBody JsonResponse<Object> sendContactUsEmail(
	        @RequestParam String name,
	        @RequestParam String email,
	        @RequestParam String subject,
	        @RequestParam String message) {

	    logger.info("Method : sendContactUsEmail starts");

	    JsonResponse<Object> response = new JsonResponse<>();

	    try {
	        // Receiver (destination) email address
	        String receiverEmail = "nirmalyalabs508@gmail.com"; // Change as needed

	        // Email Content
	        StringBuilder mailBody = new StringBuilder();
	        mailBody.append("Contact Us Form Submission").append(System.lineSeparator())
	                .append("-------------------------------------------------").append(System.lineSeparator())
	                .append("Name     : ").append(name).append(System.lineSeparator())
	                .append("Email    : ").append(email).append(System.lineSeparator())
	                .append("Subject  : ").append(subject).append(System.lineSeparator())
	                .append("Message  : ").append(message).append(System.lineSeparator());

	        // Log only meta info, no sensitive content
	        logger.info("Preparing to send email to: {}", receiverEmail);

	        // Send the email via your mail service
	        mailservice.sendEmail(receiverEmail, subject, mailBody.toString());

	        response.setCode("success");
	        response.setMessage("Your message has been sent successfully.");

	        logger.info("Email sent successfully to: {}", receiverEmail);

	    } catch (Exception e) {
	        logger.error("Error sending email: ", e);
	        response.setCode("error");
	        response.setMessage("Failed to send your message. Please try again later.");
	    }

	    logger.info("Response from sendContactUsEmail: {}", response);
	    logger.info("Method : sendContactUsEmail ends");

	    return response;
	}

	
	@GetMapping("/wishlist")
	public String wishlistPage(Model model, HttpSession session) {
		logger.info("Method : wishlistPage starts");
 
		logger.info("Method : wishlistPage ends");
		return "lms/course-wishlist";
	}
	
	@GetMapping("/faqs")
	public String faqsPage(Model model, HttpSession session) {
		logger.info("Method : faqsPage starts");
 
		logger.info("Method : faqsPage ends");
		return "lms/lms-faqs";
	}
	
	@GetMapping("/terms-condition")
	public String termsConditioPage(Model model, HttpSession session) {
		logger.info("Method : termsConditioPage starts");
 
		logger.info("Method : termsConditioPage ends");
		return "lms/term-condition";
	}
	
	@GetMapping("/privacy-policy")
	public String privacyPolicyPage(Model model, HttpSession session) {
		logger.info("Method : privacyPolicyPage starts");
 
		logger.info("Method : privacyPolicyPage ends");
		return "lms/privacy-policy";
	}
	
	@GetMapping("/my-profile")
	public String myProfile(Model model, HttpSession session) {
		logger.info("Method : my-profile starts");
 
		logger.info("Method : my-profile ends");
		return "lms/my-profile";
	}
	
    //
	@SuppressWarnings("unchecked")
	@GetMapping("profile-course-view")
	public @ResponseBody Object viewEnrollCourses(HttpSession session) {
		logger.info("Method :viewEnrollCourses starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(
					env.getMasterUrl() + "rest-viewEnrollCourses?orgName=" + orgName + "&orgDivision=" + orgDivision+ "&userId=" + userId,
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
		logger.info("Method :viewEnrollCourses ends"+resp);
		return resp;
	}
//
	
//
	@GetMapping("/course-details")
	public String allCourseDetails(Model model, HttpSession session) {
		logger.info("Method : allCourseDetails starts");
 
		logger.info("Method : allCourseDetails ends");
		return "lms/allCourse-details";
	}

	

	
	@GetMapping("/refund-policy")
	public String refundPolicy(Model model, HttpSession session) {
		logger.info("Method : refundPolicy starts");
 
		logger.info("Method : refundPolicy ends");
		return "lms/lms-refund-policy";
	}
	
	@GetMapping("/help-center")
	public String helpCenter(Model model, HttpSession session) {
		logger.info("Method : help-center starts");
 
		logger.info("Method : help-center ends");
		return "lms/lms-help-center";
	}
	
	@GetMapping("/carrer")
	public String carrerPage(Model model, HttpSession session) {
		logger.info("Method : carrerPage starts");
 
		logger.info("Method : carrerPage ends");
		return "lms/lms-carrer";
	}
	
	@GetMapping("/testimonials")
	public String testimonialsPage(Model model, HttpSession session) {
		logger.info("Method : testimonialsPage starts");
 
		logger.info("Method : testimonialsPage ends");
		return "lms/lms-testimonials";
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("get-all-instructor-list")
	public @ResponseBody Object viewList(HttpSession session) {
		logger.info("Method :viewList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-get-all-instructor-list?orgName=" + orgName + "&orgDivision="
					+ orgDivision , JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewList ends");
		return resp;
	}

	@GetMapping("/instructors")
	public String instructorList(Model model, HttpSession session) {
		logger.info("Method : instructors starts");
		
		logger.info("Method : instructors ends");
		return "lms/lms-instructor";
	}
	
	@GetMapping("/course-catelog")
	public String courseCatelogPage(Model model, HttpSession session) {
		logger.info("Method : courseCatelogPage starts");
		
		logger.info("Method : courseCatelogPage ends");
		return "lms/lms-course-catelog";
	}
	@GetMapping("/setRedirectAndLogin")
	public void setRedirectAndLogin(@RequestParam("url") String url, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		session.setAttribute("redirectUrl", url);
		System.out.println("Redirect set to: " + url);
		response.sendRedirect("/login");
	}
	@GetMapping("/signup")
	public String signup(Model model, HttpSession session) {
		logger.info("Method : signup starts");
		
		logger.info("Method : signup ends");
		return "lms/signUp";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("get-announcements-and-feedback")
	public @ResponseBody Object getAnnounceAndFeedback(HttpSession session) {
		logger.info("Method :getAnnounceAndFeedback starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-get-announce-and-feedback-list?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId="+ userId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :getAnnounceAndFeedback ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/save-candidate-reg-details")
	public @ResponseBody Object saveUserData(HttpSession session, @RequestBody String data) {
		logger.info("Method :saveUserData starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

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
			resp = restTemplate.postForObject(env.getMasterUrl() + "rest-save-user-details?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, data, JsonResponse.class);
        } catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :saveUserData ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/feedback-submit")
	public @ResponseBody Object saveUserFeedback(HttpSession session, @RequestBody String data) {
		logger.info("Method :saveUserFeedback starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

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
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-save-user-feedback?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId, data, JsonResponse.class);
        } catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :saveUserFeedback ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("feedback-edit")
	public @ResponseBody Object editFeedback(@RequestParam String Id, HttpSession session) {
		logger.info("Method :editFeedback starts" + Id);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-editFeedback?Id=" + Id + "&organization=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :editFeedback ends");
		return resp;
	}
}