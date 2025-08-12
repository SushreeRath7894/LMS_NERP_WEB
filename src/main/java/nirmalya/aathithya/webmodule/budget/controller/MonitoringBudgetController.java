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

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.budget.model.AssignDeptBudgetModel;
import nirmalya.aathithya.webmodule.budget.model.MonitoringBudgetModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import com.fasterxml.jackson.core.type.TypeReference;

@Controller
@RequestMapping(value = { "budget/" })
public class MonitoringBudgetController {
	Logger logger = LoggerFactory.getLogger(MonitoringBudgetController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
/*
@SuppressWarnings("unchecked")
	@GetMapping("budget-monitoring-income-quarter-data")
	public @ResponseBody JsonResponse<List<MonitoringBudgetModel>> budgetMonitoringIncomwWithQuarterly(Model model,
			 HttpSession session,@RequestParam String dept) {
		logger.info("Method : budgetMonitoringIncomwWithQuarterly starts");
		JsonResponse<List<MonitoringBudgetModel>> jsonResponse = new JsonResponse<List<MonitoringBudgetModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "budgetMonitoringIncomeQuarterly?dept="+dept,JsonResponse.class);
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
		logger.info("Method :budgetMonitoringIncomwWithQuarterly ends");
		return jsonResponse;
	}*/

//budget-monitoring-income-quarter-data
	@SuppressWarnings("unchecked")
	@GetMapping("budget-monitoring-income-quarter-data")
	public @ResponseBody List<MonitoringBudgetModel> budgetMonitoringIncomwWithQuarterly(HttpSession session,@RequestParam String dept,@RequestParam String fyId) {

		logger.info("Method : budgetMonitoringIncomwWithQuarterly starts");

		JsonResponse<List<MonitoringBudgetModel>> resp = new JsonResponse<List<MonitoringBudgetModel>>();

		String orgName = "";
		String orgDivision = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getBudgetUrl() + "budgetMonitoringIncomeQuarterly?dept="+ dept+"&fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<MonitoringBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<MonitoringBudgetModel>>() {
				});

		resp.setBody(assignDeptBudgetModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : budgetMonitoringIncomwWithQuarterly ends");
		return resp.getBody();
	}


	/*
	//budget-monitoring-expense-quarter-data
	@SuppressWarnings("unchecked")
	@GetMapping("budget-monitoring-expense-quarter-data")
	public @ResponseBody JsonResponse<List<MonitoringBudgetModel>> budgetMonitoringExpensewWithQuarterly(Model model,
			 HttpSession session,@RequestParam String dept) {
		logger.info("Method : budgetMonitoringExpensewWithQuarterly starts");
		JsonResponse<List<MonitoringBudgetModel>> jsonResponse = new JsonResponse<List<MonitoringBudgetModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "budgetMonitoringExpenseQuarterly?dept="+dept,JsonResponse.class);
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
		System.out.println("REsp122222" + jsonResponse);
		logger.info("Method :budgetMonitoringExpensewWithQuarterly ends");
		return jsonResponse;
	}*/
	
	@SuppressWarnings("unchecked")
	@GetMapping("budget-monitoring-expense-quarter-data")
	public @ResponseBody List<MonitoringBudgetModel> budgetMonitoringExpensewWithQuarterly(HttpSession session,@RequestParam String dept,@RequestParam String fyId) {

		logger.info("Method : budgetMonitoringExpensewWithQuarterly starts");

		JsonResponse<List<MonitoringBudgetModel>> resp = new JsonResponse<List<MonitoringBudgetModel>>();

		String orgName = "";
		String orgDivision = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getBudgetUrl() + "budgetMonitoringExpenseQuarterly?dept="+ dept+"&fyId="+ fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<MonitoringBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<MonitoringBudgetModel>>() {
				});

		resp.setBody(assignDeptBudgetModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : budgetMonitoringExpensewWithQuarterly ends");
		return resp.getBody();
	}

	
	
	//budget-monitoring-dept-data
	

	@SuppressWarnings("unchecked")
	@GetMapping("budget-monitoring-dept-data")
	public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> departmentData(Model model,
			 HttpSession session) {
		logger.info("Method : departmentData starts");
		JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "departmentDataInMonitoring",JsonResponse.class);
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
		logger.info("Method :departmentData ends");
		return jsonResponse;
	}
	
	//budget-monitoring-dept-dataById
	
		@SuppressWarnings("unchecked")
		@GetMapping("budget-monitoring-dept-dataById")
		public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> departmentDataById(Model model,
				 HttpSession session,@RequestParam String dept) {
			logger.info("Method : departmentDataById starts");
			JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
			try {
				jsonResponse = restClient.getForObject(env.getBudgetUrl() + "departmentDataByIdMonitoring?dept="+dept,JsonResponse.class);
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
			logger.info("Method :departmentDataById ends");
			return jsonResponse;
		}

		//budget-monitoring-data-deptByIncomeExpense
		
		@SuppressWarnings("unchecked")
		@GetMapping("budget-monitoring-data-deptByIncomeExpense")
		public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> departmentByAllIncomExpenseData(Model model,
				 HttpSession session,@RequestParam String dept,@RequestParam String fyId) {
			logger.info("Method : departmentByAllIncomExpenseData starts");
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
				jsonResponse = restClient.getForObject(env.getBudgetUrl() + "deptByAllDataIncomExpenseMonitoring?dept="+dept+"&fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision,JsonResponse.class);
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
			logger.info("Method :departmentByAllIncomExpenseData ends");
			return jsonResponse;
		}
}
