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
public class AdjustmentBudgetController {
	
	Logger logger = LoggerFactory.getLogger(AdjustmentBudgetController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "adjustment-budget" })
	public String adjustmentBudget(Model model, HttpSession session) {
		logger.info("Method : adjustmentBudget starts");
		
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
		return "budget/adjustment-budget";
	}
	
	//update-income-budget-amount
	
	@GetMapping("update-income-budget-amount")
	public @ResponseBody Object updateIncomeBudgetAmount(@RequestParam String deptId,String groupId,String financialYear,
			String oldBudgetAmnt,String updatedBudgetAmnt,HttpSession session) {

		logger.info("Method :updateIncomeBudgetAmount starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
	
		String orgName = "";
		String orgDivision = "";
		String createdBy = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdBy = (String) session.getAttribute("USER_ID");
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getBudgetUrl()+ "rest-updateIncomeBudgetAmnt?deptId=" + deptId + "&groupId="
					+ groupId+ "&financialYear="+ financialYear+ "&oldBudgetAmnt="+ oldBudgetAmnt+
					"&updatedBudgetAmnt="+ updatedBudgetAmnt+"&createdBy="+createdBy+
					"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	//update-income-actual-amount
	
	@GetMapping("update-income-actual-amount")
	public @ResponseBody Object updateIncomeActualAmount(@RequestParam String deptId,String groupId,String financialYear,
			String oldBudgetAmnt,String updatedBudgetAmnt,HttpSession session) {

		logger.info("Method :updateIncomeActualAmount starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		
		String orgName = "";
		String orgDivision = "";
		String createdBy = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdBy = (String) session.getAttribute("USER_ID");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
						
			resp = restClient.getForObject(env.getBudgetUrl()+ "rest-updateIncomeActualAmnt?deptId=" + deptId + "&groupId="
					+ groupId+ "&financialYear="+ financialYear+ "&oldBudgetAmnt="+ oldBudgetAmnt+
					"&updatedBudgetAmnt="+ updatedBudgetAmnt+"&createdBy="+createdBy+
					"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	//update-expense-actual-amount
	
	@GetMapping("update-expense-actual-amount")
	public @ResponseBody Object updateExpenseActualAmount(@RequestParam String deptId,String groupId,String financialYear,
			String oldBudgetAmnt,String updatedBudgetAmnt,HttpSession session) {

		logger.info("Method :updateExpenseActualAmount starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		
		String orgName = "";
		String orgDivision = "";
		String createdBy = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdBy = (String) session.getAttribute("USER_ID");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		try {
		
			
			resp = restClient.getForObject(env.getBudgetUrl()+ "rest-updateExpenseActualAmnt?deptId=" + deptId + "&groupId="
					+ groupId+ "&financialYear="+ financialYear+ "&oldBudgetAmnt="+ oldBudgetAmnt+
					"&updatedBudgetAmnt="+ updatedBudgetAmnt+"&createdBy="+createdBy+
					"&orgName="+orgName+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	//update-expense-budget-amount
	
	@GetMapping("update-expense-budget-amount")
	public @ResponseBody Object updateExpenseBudgetAmount(@RequestParam String deptId,String groupId,String financialYear,
			String oldBudgetAmnt,String updatedBudgetAmnt,HttpSession session) {

		logger.info("Method :updateIncomeBudgetAmount starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		
		String orgName = "";
		String orgDivision = "";
		String createdBy = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdBy = (String) session.getAttribute("USER_ID");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		try {
			resp = restClient.getForObject(env.getBudgetUrl()+ "rest-updateExpenseBudgetAmnt?deptId=" + deptId + "&groupId="
					+ groupId+ "&financialYear="+ financialYear+ "&oldBudgetAmnt="+ oldBudgetAmnt+ "&updatedBudgetAmnt="+ updatedBudgetAmnt
					+ "&createdBy="+ createdBy+ "&orgName="+ orgName+ "&orgDivision="+ orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	//adjustment-budget-income-adjustment-history
	@SuppressWarnings("unchecked")
	@GetMapping("adjustment-budget-income-adjustment-history")
	public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> adjustmentBudgetIncomeHistory(Model model,
			 HttpSession session,@RequestParam String deptId, @RequestParam String groupId, @RequestParam String financialYear) {
		logger.info("Method : adjustmentIncomeHistory starts");
		JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "adjustmentBudgetIncomeHistory?deptId="+deptId+ "&groupId="
					+ groupId+ "&financialYear="+ financialYear,JsonResponse.class);
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
		logger.info("Method :adjustmentIncomeHistory ends");
		return jsonResponse;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("adjustment-actual-income-adjustment-history")
	public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> adjustmentActualIncomeHistory(Model model,
			 HttpSession session,@RequestParam String deptId, @RequestParam String groupId, @RequestParam String financialYear) {
		logger.info("Method : adjustmentActualIncomeHistory starts");
		JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "adjustmentActualIncomeHistory?deptId="+deptId+ "&groupId="
					+ groupId+ "&financialYear="+ financialYear,JsonResponse.class);
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
		logger.info("Method :adjustmentActualIncomeHistory ends");
		return jsonResponse;
	}
	
	//adjustment-actual-expense-adjustment-history
	
	@SuppressWarnings("unchecked")
	@GetMapping("adjustment-actual-expense-adjustment-history")
	public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> adjustmentActualExpenseHistory(Model model,
			 HttpSession session,@RequestParam String deptId, @RequestParam String groupId, @RequestParam String financialYear) {
		logger.info("Method : adjustmentActualExpenseHistory starts");
		JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "adjustmentActualExpenseHistory?deptId="+deptId+ "&groupId="
					+ groupId+ "&financialYear="+ financialYear,JsonResponse.class);
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
		logger.info("Method :adjustmentActualExpenseHistory ends");
		return jsonResponse;
	}
	
	//adjustment-budget-expense-adjustment-history

	@SuppressWarnings("unchecked")
	@GetMapping("adjustment-budget-expense-adjustment-history")
	public @ResponseBody JsonResponse<List<AssignDeptBudgetModel>> adjustmentBudgetExpenseHistory(Model model,
			 HttpSession session,@RequestParam String deptId, @RequestParam String groupId, @RequestParam String financialYear) {
		logger.info("Method : adjustmentExpenseHistory starts");
		JsonResponse<List<AssignDeptBudgetModel>> jsonResponse = new JsonResponse<List<AssignDeptBudgetModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "adjustmentBudgetExpenseHistory?deptId="+deptId+ "&groupId="
					+ groupId+ "&financialYear="+ financialYear,JsonResponse.class);
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
		logger.info("Method :adjustmentExpenseHistory ends");
		return jsonResponse;
	}
}
