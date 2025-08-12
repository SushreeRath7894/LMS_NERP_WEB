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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;

@Controller
@RequestMapping(value = { "budget/" })
public class DepartmentController {
	Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	/*
	 * @GetMapping(value = { "manage-department" }) public String
	 * manageDepartment(Model model, HttpSession session) {
	 * logger.info("Method : manageDepartment starts");
	 * 
	 * logger.info("Method : manageDepartment ends"); return
	 * "budget/view-department"; }
	 */
	
	/*
	 * @GetMapping(value = { "manage-financial-year" }) public String manageFY(Model
	 * model, HttpSession session) { logger.info("Method : manageFY starts");
	 * 
	 * logger.info("Method : manageFY ends"); return "budget/financial-year"; }
	 */
	
	
	/*
	 * @GetMapping(value = { "manage-currency" }) public String manageCurrency(Model
	 * model, HttpSession session) { logger.info("Method : manageCurrency starts");
	 * 
	 * logger.info("Method : manageCurrency ends"); return "budget/manage-currency";
	 * }
	 */
	
	
/*	@GetMapping(value = { "manage-budget" })
	public String manageBudget(Model model, HttpSession session) {
		logger.info("Method : manageBudget starts");
	
		logger.info("Method : manageBudget ends");
		return "budget/manage-budget";
	} */
	
	
	@GetMapping(value = { "aggregation-budget" })
	public String aggregationBudget(Model model, HttpSession session) {
		logger.info("Method : aggregationBudget starts");
		
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
	
		logger.info("Method : aggregationBudget ends");
		return "budget/aggregation-budget";
	}
	
	/*
	 * @GetMapping(value = { "provisional-budget" }) public String
	 * provisionalBudget(Model model, HttpSession session) {
	 * logger.info("Method : provisionalBudget starts");
	 * 
	 * logger.info("Method : provisionalBudget ends"); return
	 * "budget/provisional-budget"; }
	 */
	
	
	/*
	 * @GetMapping(value = { "final-budget" }) public String finalBudget(Model
	 * model, HttpSession session) { logger.info("Method : finalBudget starts");
	 * 
	 * logger.info("Method : finalBudget ends"); return "budget/final-budget"; }
	 */
	
	
	@GetMapping(value = { "budget-monitoring" })
	public String budgetMonitoring(Model model, HttpSession session) {
		logger.info("Method : budgetMonitoring starts");
		
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
		
	
		logger.info("Method : budgetMonitoring ends");
		return "budget/monitoring-budget";
	}
	
	
	
	@GetMapping(value = { "department-budget" })
	public String departmentBudget(Model model, HttpSession session) {
		logger.info("Method : manageBudget starts");
		
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
		
		//currencyList
	
	
		logger.info("Method : manageBudget ends");
		return "budget/department-budget";
	}
	
	
	
	

}
