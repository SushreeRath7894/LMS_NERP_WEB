package nirmalya.aathithya.webmodule.employee.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.employee.model.EmployeeTaxDeclarationModel;

@Controller
@RequestMapping(value = "employee")
public class EmployeeTaxDeclarationController {
	Logger logger = LoggerFactory.getLogger(EmployeeTaxDeclarationController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping("/tax-declaration")
	public String employeeTaxDeclaration(Model model, HttpSession session) {

		logger.info("Method : employeeTaxDeclaration starts");

		String userId = "";
		String userName = "";
		String userRole = "";
		String organization=""; 
		String orgDivision="";
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
			DropDownModel[] financialYrType = restClient
					.getForObject(env.getMasterUrl() + "getFinancialYrForSalaryRevision?organization="+organization+"&orgDivision="+orgDivision, DropDownModel[].class);
			List<DropDownModel> financialYr = Arrays.asList(financialYrType);
			model.addAttribute("financialYr", financialYr);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
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

		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userRole", userRole);

		logger.info("Method : employeeTaxDeclaration ends");

		return "employee/employee-tax-declaration";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "tax-declaration-date-ajax" })
	public @ResponseBody JsonResponse<Object> getDateList(@RequestParam String finanacialid) {
		logger.info("Method : getDateList starts");

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getMasterUrl() + "get-dateList?id=" + finanacialid, JsonResponse.class);
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
		return res;

	}

	// view income tax declaration

	@SuppressWarnings("unchecked")
	@GetMapping("tax-declaration-incometax-view")
	public @ResponseBody List<EmployeeTaxDeclarationModel> viewTaxDeclaration(HttpSession session,
			@RequestParam String userid, @RequestParam String startYr, @RequestParam String endYr) {

		logger.info("Method : viewTaxDeclaration starts");
		logger.info("userid" + userid + "startYr" + startYr + "endYr" + endYr);
		JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "viewtaxdeclaration?userid=" + userid + "&startYr="
					+ startYr + "&endYr=" + endYr, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewTaxDeclaration ends");

		return resp.getBody();
	}

	// view Adhoc tax declaration

	@SuppressWarnings("unchecked")
	@GetMapping("tax-declaration-monthly-adhoc-view")
	public @ResponseBody List<EmployeeTaxDeclarationModel> viewAdhocIncome(HttpSession session,
			@RequestParam String userid, @RequestParam String startYr, @RequestParam String endYr) {

		logger.info("Method : viewAdhocIncome starts");
		logger.info("userid" + userid + "startYr" + startYr + "endYr" + endYr);
		JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "viewadhocincome?userid=" + userid + "&startYr="
					+ startYr + "&endYr=" + endYr, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewAdhocIncome ends");

		return resp.getBody();
	}

	// view Other tax declaration

	@SuppressWarnings("unchecked")
				@GetMapping("tax-declaration-monthly-otheritem-view")
				public @ResponseBody List<EmployeeTaxDeclarationModel> viewOtherItemDetails(HttpSession session,
						@RequestParam String userid,@RequestParam String startYr,@RequestParam String endYr) {

					logger.info("Method : viewOtherItemDetails starts");
					logger.info("userid"+userid+"startYr"+startYr+"endYr"+endYr);
					JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
					try {
						resp = restClient.getForObject(env.getEmployeeUrl() + "viewOtherItemDetails?userid="+userid+"&startYr="+startYr+"&endYr="+endYr,
								JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}
					
					logger.info("Method : viewAdhoviewOtherItemDetailscIncome ends");

					return resp.getBody();
				}

	// view Perquisite

	@SuppressWarnings("unchecked")
	@GetMapping("tax-declaration-perquisite-view")
	public @ResponseBody List<EmployeeTaxDeclarationModel> viewPerquisites(HttpSession session,
			@RequestParam String userid, @RequestParam String startYr, @RequestParam String endYr) {
		logger.info("Method : viewPerquisites starts");
		logger.info("userid" + userid + "startYr" + startYr + "endYr" + endYr);
		JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "viewPerquisiteDetails?userid=" + userid + "&startYr="
					+ startYr + "&endYr=" + endYr, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewPerquisites ends");

		return resp.getBody();
	}

	// view Income Excluded from tax 

		@SuppressWarnings("unchecked")@GetMapping("tax-declaration-excludedtax-view")

		public @ResponseBody List<EmployeeTaxDeclarationModel> viewExcludedFromTax(HttpSession session,
				@RequestParam String userid, @RequestParam String startYr, @RequestParam String endYr) {

			logger.info("Method : viewExcludedFromTax starts");
			logger.info("userid" + userid + "startYr" + startYr + "endYr" + endYr);
			JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
			try {
				resp = restClient.getForObject(env.getEmployeeUrl() + "view-tax-exluded?userid=" + userid + "&startYr="
						+ startYr + "&endYr=" + endYr, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : viewExcludedFromTax ends");

			return resp.getBody();
		}
		
		// view LESS EXEMPTION UNDER SECTION 10 details
		
		@SuppressWarnings("unchecked")
		@GetMapping("tax-declaration-less-exemption-details")

		public @ResponseBody List<EmployeeTaxDeclarationModel> viewLessExemptionUnderSec10(HttpSession session,
				@RequestParam String userid, @RequestParam String startYr, @RequestParam String endYr) {

			logger.info("Method : viewLessExemptionUnderSec10 starts");

			JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
			try {
				resp = restClient.getForObject(env.getEmployeeUrl() + "tax-less-exemption-details?userid=" + userid + "&startYr="
						+ startYr + "&endYr=" + endYr, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : viewLessExemptionUnderSec10 ends");

			return resp.getBody();
		}
		
		
		// view Previous employer tax declaration details

		@SuppressWarnings("unchecked")
		@GetMapping("tax-declaration-prev-employer-view")

		public @ResponseBody List<EmployeeTaxDeclarationModel> viewPrevEmployerTaxDetails(HttpSession session,
				@RequestParam String userid, @RequestParam String startYr, @RequestParam String endYr) {

			logger.info("Method : viewPrevEmployerTaxDetails starts");

			JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
			try {
				resp = restClient.getForObject(env.getEmployeeUrl() + "view-prevemployer-tax-details?userid=" + userid
						+ "&startYr=" + startYr + "&endYr=" + endYr, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method : viewPrevEmployerTaxDetails ends");

			return resp.getBody();
		}
				
				// view Less Deduction Under Section 16
				
		@SuppressWarnings("unchecked")
		@GetMapping("tax-declaration-less-deduction-section16")

		public @ResponseBody List<EmployeeTaxDeclarationModel> viewLessDeductionSection(HttpSession session,
			    @RequestParam String userid, @RequestParam String startYr, @RequestParam String endYr) {

		logger.info("Method : viewLessDeductionSection starts");

		JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
			try {
					resp = restClient.getForObject(env.getEmployeeUrl() + "rest-viewLessDeductionSection?userid="
								+ userid + "&startYr=" + startYr + "&endYr=" + endYr, JsonResponse.class);
				} catch (RestClientException e) {
						e.printStackTrace();
					}

		logger.info("Method : viewLessDeductionSection ends" + resp.getBody());

		return resp.getBody();
		}
				
	// view any other income by employee
				
	@SuppressWarnings("unchecked")
	@GetMapping("tax-declaration-any-other-income-by-employee")

	public @ResponseBody List<EmployeeTaxDeclarationModel> viewAnyOtherIncomeByEmp(HttpSession session,
			@RequestParam String userid, @RequestParam String startYr, @RequestParam String endYr) {

		logger.info("Method : viewAnyOtherIncomeByEmp starts");

		JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
		try {
			resp = restClient.getForObject(env.getEmployeeUrl() + "rest-viewAnyOtherIncomeByEmp?userid=" + userid
					+ "&startYr=" + startYr + "&endYr=" + endYr, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : viewAnyOtherIncomeByEmp ends" + resp.getBody());

		return resp.getBody();
	}
				
				// view DEDUCTION UNDER CHAPTER  VI A

				@SuppressWarnings("unchecked")
				@GetMapping("tax-declaration-view-deduction-underviA")
				public @ResponseBody List<EmployeeTaxDeclarationModel> viewDeductionViA(HttpSession session,
						@RequestParam String userid, @RequestParam String startYr, @RequestParam String endYr) {

					logger.info("Method : viewOtherItemDetails starts");
					logger.info("userid" + userid + "startYr" + startYr + "endYr" + endYr);
					JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
					try {
						resp = restClient.getForObject(env.getEmployeeUrl() + "rest-viewDeductionViA?userid=" + userid
								+ "&startYr=" + startYr + "&endYr=" + endYr, JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					logger.info("Method : viewDeductionViA ends" + resp.getBody());

					return resp.getBody();
				}

				// view Surchage On Tax

				@SuppressWarnings("unchecked")
				@GetMapping("tax-declaration-view-surchage-tax")
				public @ResponseBody List<EmployeeTaxDeclarationModel> viewSurchageTax(HttpSession session,
						@RequestParam String userid, @RequestParam String startYr, @RequestParam String endYr) {

					logger.info("Method : viewSurchageTax starts");
					JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
					try {
						resp = restClient.getForObject(env.getEmployeeUrl() + "rest-viewSurchageTax?userid=" + userid
								+ "&startYr=" + startYr + "&endYr=" + endYr, JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					logger.info("Method : viewSurchageTax ends" + resp.getBody());

					return resp.getBody();
				}
				
				// view Tax Paid Till

				@SuppressWarnings("unchecked")
				@GetMapping("tax-declaration-view-tax-paidtill")
				public @ResponseBody List<EmployeeTaxDeclarationModel> viewTaxPaidTill(HttpSession session,
						@RequestParam String userid, @RequestParam String startYr, @RequestParam String endYr) {

					logger.info("Method : viewTaxPaidTill starts");
					logger.info("userid" + userid + "startYr" + startYr + "endYr" + endYr);
					JsonResponse<List<EmployeeTaxDeclarationModel>> resp = new JsonResponse<List<EmployeeTaxDeclarationModel>>();
					try {
						resp = restClient.getForObject(env.getEmployeeUrl() + "rest-viewTaxPaidTill?userid=" + userid
								+ "&startYr=" + startYr + "&endYr=" + endYr, JsonResponse.class);
					} catch (RestClientException e) {
						e.printStackTrace();
					}

					logger.info("Method : viewTaxPaidTill ends" + resp.getBody());

					return resp.getBody();
				}

}
