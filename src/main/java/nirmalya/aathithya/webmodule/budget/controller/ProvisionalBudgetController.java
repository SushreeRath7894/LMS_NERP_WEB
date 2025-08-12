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
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = {"budget/"})

public class ProvisionalBudgetController {
	
	Logger logger = LoggerFactory.getLogger(ProvisionalBudgetController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "provisional-budget" })
	public String provisionalBudget(Model model, HttpSession session) {
		logger.info("Method : provisionalBudget starts");
		
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
	
		logger.info("Method : provisionalBudget ends");
		return "budget/provisional-budget";
	}
	
	//provisional-department-budget-data-deptByAllIncomeExpense
	
		@SuppressWarnings("unchecked")
		@GetMapping("provisional-department-budget-data-deptByAllIncomeExpense")
		public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> allDeptProvisionalIncomExpenseData(Model model,
				 HttpSession session,@RequestParam String fyId) {
			logger.info("Method : allDeptProvisionalIncomExpenseData starts");
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
				jsonResponse = restClient.getForObject(env.getBudgetUrl() + "allPrIncomExpenseData?fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision,JsonResponse.class);
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
			logger.info("Method :allDeptProvisionalIncomExpenseData ends");
			return jsonResponse;
		}
		
		//provisional-budget-fy-inflation-currency-dataByFy
		
		@SuppressWarnings("unchecked")
		@GetMapping("provisional-budget-fy-inflation-currency-dataByFy")
		public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> provisionalFyCurrInflationDataByFy(Model model,
				 HttpSession session, @RequestParam String fyId) {
			logger.info("Method : provisionalFyCurrInflationDataByFy starts");
			JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
			try {
				jsonResponse = restClient.getForObject(env.getBudgetUrl() + "provisionalFyCurrInflationDataByFy?fyId="+fyId,JsonResponse.class);
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
			logger.info("Method :provisionalFyCurrInflationDataByFy ends");
			return jsonResponse;
		}
		
		//provisional-budget-details-for-income-all
		
		@SuppressWarnings("unchecked")
		@GetMapping("provisional-budget-details-for-income-all")
		public @ResponseBody List<AssignDeptBudgetModel> viewProvisionalIncomeDtlsForAllDept(HttpSession session, @RequestParam String fyId) {
			System.out.println("dept web--------------------------------------"+fyId);
			logger.info("Method : viewProvisionalIncomeDtlsForAllDept starts");

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
				resp = restClient.getForObject(env.getBudgetUrl() + "viewProvisionalIncomeDtlsForAllDept?fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();

			List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<AssignDeptBudgetModel>>() {
					});

			resp.setBody(assignDeptBudgetModel);
			System.out.println("resp.getBody()-----------" + resp.getBody());

			logger.info("Method : viewProvisionalIncomeDtlsForAllDept ends");
			return resp.getBody();
		}
		
		//provisional-budget-details-for-expense-all
		
		@SuppressWarnings("unchecked")
		@GetMapping("provisional-budget-details-for-expense-all")
		public @ResponseBody List<AssignDeptBudgetModel> viewProvisionalExpenseDtlsForAllDept(HttpSession session, @RequestParam String fyId) {
			System.out.println("dept web--------------------------------------"+fyId);
			logger.info("Method : viewProvisionalExpenseDtlsForAllDept starts");

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
				resp = restClient.getForObject(env.getBudgetUrl() + "viewProvExpenseDtlsForAllDept?fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();

			List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<AssignDeptBudgetModel>>() {
					});

			resp.setBody(assignDeptBudgetModel);
			System.out.println("resp.getBody()-----------" + resp.getBody());

			logger.info("Method : viewProvisionalExpenseDtlsForAllDept ends");
			return resp.getBody();
		}
		
		//aggregation-budget-fy-inflation-currency-data
		
		@SuppressWarnings("unchecked")
		@GetMapping("provisional-budget-fy-inflation-currency-data")
		public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> provFyCurrInflationData(Model model,
				 HttpSession session, @RequestParam String fyId) {
			logger.info("Method : provisionalFyCurrInflationData starts");
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
				jsonResponse = restClient.getForObject(env.getBudgetUrl() + "provFyCurrInflationData?fyId=" + fyId + "&orgName=" + orgName+ "&orgDivision=" + orgDivision,JsonResponse.class);
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
			logger.info("Method :provisionalFyCurrInflationData ends");
			return jsonResponse;
		}

}
