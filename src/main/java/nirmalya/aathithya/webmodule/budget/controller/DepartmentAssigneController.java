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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.budget.model.AssignDeptBudgetModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

import com.fasterxml.jackson.core.type.TypeReference;
@Controller
@RequestMapping(value = {"budget/"})
public class DepartmentAssigneController {
	Logger logger = LoggerFactory.getLogger(DepartmentAssigneController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	
	
	@GetMapping(value = {"department-budget-assign" })
	public String assignedDept(Model model, HttpSession session) {
		logger.info("Method : assignedDept starts");
		
	
		
		
		try {
			DropDownModel[] department= restClient.getForObject(env.getBudgetUrl() + "/getDepartmentList",
					DropDownModel[].class);

			List<DropDownModel> departmentList = Arrays.asList(department);
			System.out.println("departmentList" + departmentList);
			model.addAttribute("departmentList", departmentList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
		
	
		logger.info("Method : assignedDept ends");
		return "budget/assign-department";
	}
	
	//department-budget-assign-get-income
	@SuppressWarnings("unchecked")
	@GetMapping("department-budget-assign-get-income")
	public @ResponseBody List<AssignDeptBudgetModel> viewAssignIncome(HttpSession session,@RequestParam String dept) {

		logger.info("Method : viewAssignIncome starts");

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
			resp = restClient.getForObject(env.getBudgetUrl() + "restAssignIncomeDept?dept="+ dept+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AssignDeptBudgetModel>>() {
				});

		resp.setBody(assignDeptBudgetModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewAssignIncome ends");
		return resp.getBody();
	}

	
	//department-budget-assign-get-expenses
	
	@SuppressWarnings("unchecked")
	@GetMapping("department-budget-assign-get-expense")
	public @ResponseBody List<AssignDeptBudgetModel> viewAssignExpense(HttpSession session,@RequestParam String dept) {

		logger.info("Method : viewAssignExpense starts");
		
		System.out.println("dept web-------------"+dept);

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
			resp = restClient.getForObject(env.getBudgetUrl() + "restAssignExpenseDept?dept="+ dept+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		
		

		ObjectMapper mapper = new ObjectMapper();

		List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AssignDeptBudgetModel>>() {
				});

		resp.setBody(assignDeptBudgetModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewAssignExpense ends");
		return resp.getBody();
	}
	
	//department-budget-assigned-for-income
	
	@SuppressWarnings("unchecked")
	@GetMapping("department-budget-assigned-for-income")
	public @ResponseBody List<AssignDeptBudgetModel> viewAssignedIncome(HttpSession session, @RequestParam String dept, @RequestParam String fyId) {
		System.out.println("dept web--------------------------------------"+dept);
		logger.info("Method : viewAssignedIncome starts");

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
			resp = restClient.getForObject(env.getBudgetUrl() + "restAssignedIncomeDept?dept="+dept+"&fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AssignDeptBudgetModel>>() {
				});

		resp.setBody(assignDeptBudgetModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewAssignedIncome ends");
		return resp.getBody();
	}
	
	//department-budget-assigned-for-expense
	
	@SuppressWarnings("unchecked")
	@GetMapping("department-budget-assigned-for-expense")
	public @ResponseBody List<AssignDeptBudgetModel> viewAssignedExpense(HttpSession session, @RequestParam String dept, @RequestParam String fyId) {

		logger.info("Method : viewAssignedExpense starts");

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
			resp = restClient.getForObject(env.getBudgetUrl() + "restAssignedExpenseDept?dept="+dept+"&fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AssignDeptBudgetModel>>() {
				});

		resp.setBody(assignDeptBudgetModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewAssignedExpense ends");
		return resp.getBody();
	}
	
	//department-budget-assign-dept-data
	

	/*@SuppressWarnings("unchecked")
	@GetMapping("department-budget-assign-dept-data")
	public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> departmentData(Model model,
			 HttpSession session) {
		logger.info("Method : departmentData starts");
		JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "departmentData",JsonResponse.class);
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
	}*/
	
	@SuppressWarnings("unchecked")

	@GetMapping("department-budget-assign-dept-data")
	public @ResponseBody Object departmentData(HttpSession session) {
		logger.info("Method :departmentData starts");
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
			resp = restClient.getForObject(
					env.getBudgetUrl() + "departmentData?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (resp.getCode().equals("Success")) {
			resp.setMessage("Success");
		} else {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		}
		
		logger.info("Method :departmentData ends");
		return resp;
	}

	
	

	
	//department-budget-assign-dept-dataById
	@SuppressWarnings("unchecked")
	@GetMapping("department-budget-assign-dept-dataById")
	public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> departmentDataById(Model model,
			 HttpSession session,@RequestParam String dept) {
		logger.info("Method : departmentDataById starts");
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
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "departmentDataById?dept="+dept+"&orgName="+orgName+"&orgDivision="+orgDivision,JsonResponse.class);
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
	
	
	//department-budget-assign-assign-dept-income
	
	@SuppressWarnings("unchecked")
	@PostMapping("department-budget-assign-assign-dept-income")
	public @ResponseBody JsonResponse<Object> assignIncomeDept(
			@RequestBody AssignDeptBudgetModel assignDeptBudgetModel, Model model, HttpSession session) {

		logger.info("Method : assignIncomeDept starts" + assignDeptBudgetModel);

	//	System.out.println("resp web controller-----------------------------------" + assignDeptBudgetModel);

		String orgName = "";
		String orgDivision = "";
		String createdBy = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdBy = (String) session.getAttribute("USER_ID");
			
			assignDeptBudgetModel.setCreatedBy(createdBy);
			assignDeptBudgetModel.setOrgName(orgName);
			assignDeptBudgetModel.setOrgDivision(orgDivision);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			
			System.out.println("resp web controller-----------------------------------" + assignDeptBudgetModel);


			resp = restClient.postForObject(env.getBudgetUrl() + "assignIncomeDept", assignDeptBudgetModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getCode() == "") {
			resp.setCode("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : assignIncomeDept ends" + resp);

		return resp;
	}
	
	//department-budget-assign-assign-dept-expense

	@SuppressWarnings("unchecked")
	@PostMapping("department-budget-assign-assign-dept-expense")
	public @ResponseBody JsonResponse<Object> assignExpenseDept(
			@RequestBody AssignDeptBudgetModel assignDeptBudgetModel, Model model, HttpSession session) {

		logger.info("Method : assignExpenseDept starts" + assignDeptBudgetModel);

	//	System.out.println("resp web controller-----------------------------------" + assignDeptBudgetModel);

		String orgName = "";
		String orgDivision = "";
		String createdBy = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdBy = (String) session.getAttribute("USER_ID");
			
			assignDeptBudgetModel.setCreatedBy(createdBy);
			assignDeptBudgetModel.setOrgName(orgName);
			assignDeptBudgetModel.setOrgDivision(orgDivision);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			
			System.out.println("resp web controller-----------------------------------" + assignDeptBudgetModel);


			resp = restClient.postForObject(env.getBudgetUrl() + "assignExpenseDept", assignDeptBudgetModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getCode() == "") {
			resp.setCode("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : assignExpenseDept ends" + resp);

		return resp;
	}
	//department-budget-details-for-income
	
	@SuppressWarnings("unchecked")
	@GetMapping("department-budget-details-for-income")
	public @ResponseBody List<AssignDeptBudgetModel> viewIncomeDtlsbyId(HttpSession session, @RequestParam String dept,@RequestParam String fyId) {
		System.out.println("dept web--------------------------------------"+dept);
		logger.info("Method : viewIncomeDtlsbyId starts");

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
			resp = restClient.getForObject(env.getBudgetUrl() + "viewIncomeDtlsbyId?dept="+dept+"&fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AssignDeptBudgetModel>>() {
				});

		resp.setBody(assignDeptBudgetModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewIncomeDtlsbyId ends");
		return resp.getBody();
	}
	
	//department-budget-details-for-expense
	
	@SuppressWarnings("unchecked")
	@GetMapping("department-budget-details-for-expense")
	public @ResponseBody List<AssignDeptBudgetModel> viewExpenseDtlsbyId(HttpSession session, 
			@RequestParam String dept,@RequestParam String fyId) {
		System.out.println("dept web--------------------------------------"+dept);
		logger.info("Method : viewExpenseDtlsbyId starts");

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
			resp = restClient.getForObject(env.getBudgetUrl() + "viewExpnsDtlsbyId?dept="+dept+"&fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AssignDeptBudgetModel>>() {
				});

		resp.setBody(assignDeptBudgetModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewExpenseDtlsbyId ends");
		return resp.getBody();
	}
	
	//department-budget-data-deptByIncomeExpense
	
	@SuppressWarnings("unchecked")
	@GetMapping("department-budget-data-deptByIncomeExpense")
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
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "deptByAllDataIncomExpense?dept="+dept+"&fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision,JsonResponse.class);
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

	//aggregation-budget-fy-inflation-currency-data
	
	@SuppressWarnings("unchecked")
	@GetMapping("aggregation-budget-fy-inflation-currency-data")
	public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> aggregationFyCurrInflationData(Model model,
			 HttpSession session) {
		logger.info("Method : aggregationFyCurrInflationData starts");
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
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "aggregationFyCurrInflationData?orgName="+orgName+"&orgDivision="+orgDivision,JsonResponse.class);
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
		logger.info("Method :aggregationFyCurrInflationData ends");
		return jsonResponse;
	}
	
	//department-budget-details-for-income-all
	
	@SuppressWarnings("unchecked")
	@GetMapping("department-budget-details-for-income-all")
	public @ResponseBody List<AssignDeptBudgetModel> viewIncomeDtlsForAllDept(HttpSession session, @RequestParam String fyId) {
		System.out.println("dept web--------------------------------------"+fyId);
		logger.info("Method : viewIncomeDtlsForAllDept starts");

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
			resp = restClient.getForObject(env.getBudgetUrl() + "viewIncomeDtlsForAllDept?fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AssignDeptBudgetModel>>() {
				});

		resp.setBody(assignDeptBudgetModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewIncomeDtlsForAllDept ends");
		return resp.getBody();
	}
	
	//department-budget-details-for-expense-all
	
	@SuppressWarnings("unchecked")
	@GetMapping("department-budget-details-for-expense-all")
	public @ResponseBody List<AssignDeptBudgetModel> viewExpenseDtlsForAllDept(HttpSession session, @RequestParam String fyId) {
		System.out.println("dept web--------------------------------------"+fyId);
		logger.info("Method : viewExpenseDtlsForAllDept starts");

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
			resp = restClient.getForObject(env.getBudgetUrl() + "viewExpenseDtlsForAllDept?fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<AssignDeptBudgetModel> assignDeptBudgetModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AssignDeptBudgetModel>>() {
				});

		resp.setBody(assignDeptBudgetModel);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewExpenseDtlsForAllDept ends");
		return resp.getBody();
	}
	
	//department-budget-data-deptByAllIncomeExpense
	
	@SuppressWarnings("unchecked")
	@GetMapping("department-budget-data-deptByAllIncomeExpense")
	public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> allDeptIncomExpenseData(Model model,
			 HttpSession session,@RequestParam String fyId) {
		logger.info("Method : allDeptIncomExpenseData starts");
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
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "allDeptIncomExpenseData?fyId="+fyId+"&orgName="+orgName+"&orgDivision="+orgDivision,JsonResponse.class);
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
		logger.info("Method :allDeptIncomExpenseData ends");
		return jsonResponse;
	}
	
	//aggregation-budget-fy-inflation-currency-dataByFy
	
	@SuppressWarnings("unchecked")
	@GetMapping("aggregation-budget-fy-inflation-currency-dataByFy")
	public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> aggregationFyCurrInflationDataByFy(Model model,
			 HttpSession session, @RequestParam String fyId) {
		logger.info("Method : aggregationFyCurrInflationDataByFy starts");
		JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "aggregationFyCurrInflationDataByFy?fyId="+fyId,JsonResponse.class);
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
		logger.info("Method :aggregationFyCurrInflationDataByFy ends");
		return jsonResponse;
	}
	
	//-----------------------------Assign Dublicate Check By Pankaj------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	@GetMapping("department-assign-income-checkDuplicate")
	public @ResponseBody JsonResponse<Object>  assignDuplicateIncomeCheck(Model model, @RequestParam String id,
			@RequestParam String deptId,
			@RequestParam String financialYear,
			HttpSession session) {

		logger.info("Method : assignDuplicateIncomeCheck starts-->" + id);
	
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>(); 
		
		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "assignDuplicateIncomeCheck?id=" + id 
					+"&deptId=" + deptId +"&financialYear=" + financialYear,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}

		System.out.println("REsp--->" + jsonResponse);
		logger.info("Method :assignDuplicateIncomeCheck ends");
		return jsonResponse;
	}
	
	//-----------------------------Assign Dublicate Check By Pankaj------------------------------------------------------------
	
		@SuppressWarnings("unchecked")
		@GetMapping("department-assign-expenses-checkDuplicate")
		public @ResponseBody JsonResponse<Object>  assignDuplicateExpensesCheck(Model model, @RequestParam String id,
				@RequestParam String deptId,
				@RequestParam String financialYear,
				HttpSession session) {

			logger.info("Method : assignDuplicateExpensesCheck starts-->" + id);
		
			JsonResponse<Object> jsonResponse = new JsonResponse<Object>(); 
			
			try {
				jsonResponse = restClient.getForObject(env.getBudgetUrl() + "assignDuplicateExpensesCheck?id=" + id 
						+"&deptId=" + deptId +"&financialYear=" + financialYear,
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			

		
			if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

			} else {
				jsonResponse.setMessage("Success");
			}

			System.out.println("REsp--->" + jsonResponse);
			logger.info("Method :assignDuplicateExpensesCheck ends");
			return jsonResponse;
		}
}
