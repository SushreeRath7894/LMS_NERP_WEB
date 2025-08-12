package nirmalya.aathithya.webmodule.budget.controller;

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
import org.springframework.web.client.RestTemplate;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;


@Controller
@RequestMapping(value = { "budget/" })
public class DepartmentBudgetController {
	
	Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	RestTemplate restClient;
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "manage-budget" })
	public String manageBudget(Model model, HttpSession session) {
		logger.info("Method : manageBudget starts");
	
		logger.info("Method : manageBudget ends");
		return "budget/manage-budget";
	}
	
	@GetMapping(value = { "manage-expense-budget" })
	public String expenseBudget(Model model, HttpSession session) {
		logger.info("Method : expenseBudget starts");
	
		logger.info("Method : expenseBudget ends");
		return "budget/expense-budget";
	}
	
	//manage-department-budget-through-json

	@SuppressWarnings("unchecked")

	@GetMapping("manage-department-budget-through-json")
	public @ResponseBody Object viewQc(HttpSession session) {
		logger.info("Method :viewBudget starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
	
		String id="";
		String orgName = "";
		String orgDivision = "";
		try {
			id = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("checkId -------------------------------------------===" + id);
		try {
			resp = restTemplate.getForObject(
					env.getBudgetUrl() + "rest-viewBudget?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		System.out.println("view===" + resp);
		logger.info("Method :viewBudget ends");
		return resp;
	}
	
/*--------------------Budget Ammount Added By Pankaj--------------------------------*/
	
	@GetMapping("add-budget-amount")
	public @ResponseBody Object addBudgetAmount(@RequestParam String dept_id,String group_id,String financialYear,
			String budgetAmount,String incmRemak,HttpSession session) {

		logger.info("Method :addBudgetAmount starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		
		
		try {
			resp = restTemplate.getForObject(env.getBudgetUrl()+ "rest-addBudget?dept_id=" + dept_id + "&group_id="
					+ group_id+ "&financialYear="+ financialYear+ "&budgetAmount="+ budgetAmount+ "&incmRemak="+ incmRemak, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@GetMapping("add-actual-budget-amount")
	public @ResponseBody Object addActualBudgetAmount(@RequestParam String dept_id,String group_id,String financialYear,
			String actualBudgetAmount,String actualIncmRemak,HttpSession session) {

		logger.info("Method :addActualBudgetAmount starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		
		
		try {
			resp = restTemplate.getForObject(env.getBudgetUrl()+ "rest-addActualBudget?dept_id=" + dept_id + "&group_id="
					+ group_id+ "&financialYear="+ financialYear+ "&actualBudgetAmount="+ actualBudgetAmount+ "&actualIncmRemak="+ actualIncmRemak, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/*--------------------Expense----Budget --------------------------------*/
	

	@SuppressWarnings("unchecked")

	@GetMapping("manage-expense-budget-through-json")
	public @ResponseBody Object viewExpense(HttpSession session) {
		logger.info("Method :viewExpense starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
	
		String id="";
		String orgName = "";
		String orgDivision = "";
		try {
			id = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("checkId -------------------------------------------===" + id);
		try {
			resp = restTemplate.getForObject(
					env.getBudgetUrl() + "rest-viewExpense?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		System.out.println("view===" + resp);
		logger.info("Method :viewExpense ends");
		return resp;
	}
	
/*--------------------Budget Ammount Added By Pankaj--------------------------------*/
	
	@GetMapping("add-expense-amount")
	public @ResponseBody Object addExpenseAmount(@RequestParam String dept_id,String group_id,String financialYear,
			String budgetAmount,String incmRemak,HttpSession session) {

		logger.info("Method :addExpenseAmount starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		
		
		try {
			resp = restTemplate.getForObject(env.getBudgetUrl()+ "rest-addExpense?dept_id=" + dept_id + "&group_id="
					+ group_id+ "&financialYear="+ financialYear+ "&budgetAmount="+ budgetAmount+ "&incmRemak="+ incmRemak, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@GetMapping("add-actual-epns-budget-amount")
	public @ResponseBody Object addActualExpnsBudgetAmount(@RequestParam String dept_id,String group_id,String financialYear,
			String actualBudgetAmount,String actualExpnsRemak,HttpSession session) {

		logger.info("Method :addActualBudgetAmount starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		
		
		try {
			resp = restTemplate.getForObject(env.getBudgetUrl()+ "rest-addActualExpnxBudget?dept_id=" + dept_id + "&group_id="
					+ group_id+ "&financialYear="+ financialYear+ "&actualBudgetAmount="+ actualBudgetAmount+ "&actualExpnsRemak="+ actualExpnsRemak, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

}
