package nirmalya.aathithya.webmodule.account.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountBankModel;
import nirmalya.aathithya.webmodule.account.model.AccountBranchModel;
import nirmalya.aathithya.webmodule.account.model.AccountModel;
import nirmalya.aathithya.webmodule.account.model.BudgetModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCustomerModel;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
@RequestMapping(value = "account")
public class ManageBudgetController {
	Logger logger = LoggerFactory.getLogger(ManageBudgetController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Budget in Account
	 */
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-budgets-add")
	public @ResponseBody JsonResponse<Object> addBudget(@RequestBody BudgetModel budgetModel,
			HttpSession session) {

		logger.info("Method : addBudget starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web AccountModel ======================" + budgetModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			budgetModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addBudget", budgetModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addBudget ends");

		return resp;
	}

	/** 
	 * View Customer Account
	 * 
	 */
	
	
	@GetMapping("/view-budgets")
	public String viewManageBudgets(Model model, HttpSession session) {
		logger.info("Method : viewManageCus start");	
		try {
			DropDownModel[] bank = restClient.getForObject(env.getAccountUrl() + "/getBankList",
					DropDownModel[].class);

			List<DropDownModel> bankList = Arrays.asList(bank);
			model.addAttribute("bankList", bankList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] source = restClient.getForObject(env.getPipeline() + "/getCountry",
					DropDownModel[].class);

			List<DropDownModel> sourceList = Arrays.asList(source);
			model.addAttribute("countryList", sourceList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Met    hod : viewManageBudgets end");
		return "account/manage-budgets";
	}
	

	/**
	 * Delete Budget Account Record   
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-budgets-delete-id")
	public @ResponseBody JsonResponse<Object> deleteBudgetDetails(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deleteBudgetDetails function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteBudgetDetails?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteBudgetDetails function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
	
	
	
	/**
	 * View Budget Ajax
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-budgets-throughAjax")
	public @ResponseBody List<BudgetModel> viewBudgets(HttpSession session) {

		logger.info("Method : viewBudgets starts");

		JsonResponse<List<BudgetModel>> resp = new JsonResponse<List<BudgetModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewBudgetDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<BudgetModel> budgetModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<BudgetModel>>() {
				});
		
		for (BudgetModel i : budgetModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("date for creation------------------"+date);
			}
		}
		
		resp.setBody(budgetModel);
		
		logger.info("Method : viewBudgets ends");
		return resp.getBody();
	}
	
	/**
	 * Edit Account Record
	 */
	
	//view-budget-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-budgets-edit")
	public @ResponseBody JsonResponse<List<BudgetModel>> editBudgetInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editBudgetInfo starts" + id);
		
		JsonResponse<List<BudgetModel>> jsonResponse = new JsonResponse<List<BudgetModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editBudgetInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<BudgetModel> budgetModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<BudgetModel>>() {
				});
		
		for (BudgetModel i : budgetModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("start date---------------"+date);
			}	
			
		}
		
		System.out.println("###" + budgetModel);
		jsonResponse.setBody(budgetModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editBudgetInfo ends");
		return jsonResponse;
	}
	
	
	
}
