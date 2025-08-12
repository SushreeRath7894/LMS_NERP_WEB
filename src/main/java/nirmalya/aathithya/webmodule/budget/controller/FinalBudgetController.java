package nirmalya.aathithya.webmodule.budget.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.budget.model.AssignDeptBudgetModel;
import nirmalya.aathithya.webmodule.budget.model.MonitoringBudgetModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = {"budget/"})
public class FinalBudgetController {
	
	Logger logger = LoggerFactory.getLogger(FinalBudgetController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "final-budget" })
	public String finalBudget(Model model, HttpSession session) {
		logger.info("Method : finalBudget starts");
		
		try {
			DropDownModel[] fiscalyear= restClient.getForObject(env.getBudgetUrl() + "/getFiscalYear",
					DropDownModel[].class);

			List<DropDownModel> fiscalYearList = Arrays.asList(fiscalyear);
			System.out.println("fiscalYearList" + fiscalYearList);
			model.addAttribute("fiscalYearList", fiscalYearList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			DropDownModel[] currency= restClient.getForObject(env.getBudgetUrl() + "/getCurrencyList",
					DropDownModel[].class);

			List<DropDownModel> currencyList = Arrays.asList(currency);
			System.out.println("currencyList" + currencyList);
			model.addAttribute("currencyList", currencyList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		logger.info("Method : finalBudget ends");
		return "budget/final-budget";
	}
	
	//provisional-department-budget-data-deptByAllIncomeExpense
	
			@SuppressWarnings("unchecked")
			@GetMapping("final-budget-data-deptByAllIncomeExpense")
			public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> allFinalBudgetIncomExpenseData(Model model,
					 HttpSession session,@RequestParam String fyId) {
				logger.info("Method : allFinalBudgetIncomExpenseData starts");
				JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
				
				String orgName = "";
				String orgDivision = "";
				try {
					
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					jsonResponse = restClient.getForObject(env.getBudgetUrl() + "allFyIncomExpenseData?fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision,JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ObjectMapper mapper = new ObjectMapper();
				List<AssignDeptBudgetModel> departmentData = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<List<AssignDeptBudgetModel>>() {
				});
				System.out.println("###" + departmentData);
				jsonResponse.setBody(departmentData);
				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
				} else {
					jsonResponse.setMessage("Success");
				}
				System.out.println("REsp" + jsonResponse);
				logger.info("Method :allFinalBudgetIncomExpenseData ends");
				return jsonResponse;
			}
			
			//final-budget-fy-inflation-currency-dataByFy
			
			@SuppressWarnings("unchecked")
			@GetMapping("final-budget-fy-inflation-currency-dataByFy")
			public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> finalBudgetFyCurrInflationDataByFy(Model model,
					 HttpSession session, @RequestParam String fyId) {
				logger.info("Method : finalBudgetFyCurrInflationDataByFy starts");
				JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
				try {
					jsonResponse = restClient.getForObject(env.getBudgetUrl() + "fyBudgetCurrInflationDataByFy?fyId="+fyId,JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ObjectMapper mapper = new ObjectMapper();
				List<AssignDeptBudgetModel> departmentData = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<List<AssignDeptBudgetModel>>() {
				});
				System.out.println("###" + departmentData);
				jsonResponse.setBody(departmentData);
				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
				} else {
					jsonResponse.setMessage("Success");
				}
				System.out.println("REsp" + jsonResponse);
				logger.info("Method :finalBudgetFyCurrInflationDataByFy ends");
				return jsonResponse;
			}
			
			//provisional-budget-details-for-income-all
			
			@SuppressWarnings("unchecked")
			@GetMapping("final-budget-details-for-income-all")
			public @ResponseBody List<AssignDeptBudgetModel> viewFinalBudgetIncomeDtlsForAllDept(HttpSession session, @RequestParam String fyId) {
				System.out.println("dept web--------------------------------------"+fyId);
				logger.info("Method : viewFinalBudgetIncomeDtlsForAllDept starts");

				JsonResponse<List<AssignDeptBudgetModel>> resp = new JsonResponse<List<AssignDeptBudgetModel>>();
				
				String orgName = "";
				String orgDivision = "";
				try {
					
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					resp = restClient.getForObject(env.getBudgetUrl() + "viewFyBudIncomeDtlsForAllDept?fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				ObjectMapper mapper = new ObjectMapper();

				List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
						new TypeReference<List<AssignDeptBudgetModel>>() {
						});

				resp.setBody(assignDeptBudgetModel);
				System.out.println("resp.getBody()-----------" + resp.getBody());

				logger.info("Method : viewFinalBudgetIncomeDtlsForAllDept ends");
				return resp.getBody();
			}
			
			//provisional-budget-details-for-expense-all
			
			@SuppressWarnings("unchecked")
			@GetMapping("final-budget-details-for-expense-all")
			public @ResponseBody List<AssignDeptBudgetModel> viewFinalbudgetExpenseDtlsForAllDept(HttpSession session, @RequestParam String fyId) {
				System.out.println("dept web--------------------------------------"+fyId);
				logger.info("Method : viewFinalbudgetExpenseDtlsForAllDept starts");

				JsonResponse<List<AssignDeptBudgetModel>> resp = new JsonResponse<List<AssignDeptBudgetModel>>();
				
				String orgName = "";
				String orgDivision = "";
				try {
					
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					resp = restClient.getForObject(env.getBudgetUrl() + "viewFyBudExpenseDtlsForAllDept?fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				ObjectMapper mapper = new ObjectMapper();

				List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
						new TypeReference<List<AssignDeptBudgetModel>>() {
						});

				resp.setBody(assignDeptBudgetModel);
				System.out.println("resp.getBody()-----------" + resp.getBody());

				logger.info("Method : viewFinalbudgetExpenseDtlsForAllDept ends");
				return resp.getBody();
			}
			
			//aggregation-budget-fy-inflation-currency-data
			
			@SuppressWarnings("unchecked")
			@GetMapping("final-budget-fy-inflation-currency-data")
			public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> finalyearCurrInflationData(Model model,
					 HttpSession session, @RequestParam String fyId) {
				logger.info("Method : finalyearCurrInflationData starts");
				JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
				
				String orgName = "";
				String orgDivision = "";
				try {
					
					orgName = (String) session.getAttribute("ORGANIZATION");
					orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				try {
					jsonResponse = restClient.getForObject(env.getBudgetUrl() + "fyBudgetCurrInflationData?fyId"+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision,JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ObjectMapper mapper = new ObjectMapper();
				List<AssignDeptBudgetModel> departmentData = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<List<AssignDeptBudgetModel>>() {
				});
				System.out.println("###" + departmentData);
				jsonResponse.setBody(departmentData);
				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
				} else {
					jsonResponse.setMessage("Success");
				}
				System.out.println("REsp" + jsonResponse);
				logger.info("Method :finalyearCurrInflationData ends");
				return jsonResponse;
			}
			
			
			//final-budget-quarterly-dataById
			
			@SuppressWarnings("unchecked")
			@GetMapping("final-budget-quarterly-dataById")
			public @ResponseBody JsonResponse<List<MonitoringBudgetModel>> quarterlyDataByGroupId(Model model,
					 HttpSession session,@RequestParam String groupId, @RequestParam String fyId) {
				logger.info("Method : quarterlyDataByGroupId starts");
				JsonResponse<List<MonitoringBudgetModel>> jsonResponse = new JsonResponse<List<MonitoringBudgetModel>>();
				try {
					jsonResponse = restClient.getForObject(env.getBudgetUrl() + "quarterlyDataByGroupId?groupId="+groupId+"&fyId="+fyId,JsonResponse.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ObjectMapper mapper = new ObjectMapper();
				List<MonitoringBudgetModel> departmentData = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<List<MonitoringBudgetModel>>() {
				});
				System.out.println("###" + departmentData);
				jsonResponse.setBody(departmentData);
				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
				} else {
					jsonResponse.setMessage("Success");
				}
				System.out.println("REsp" + jsonResponse);
				logger.info("Method :quarterlyDataByGroupId ends");
				return jsonResponse;
			}

}
