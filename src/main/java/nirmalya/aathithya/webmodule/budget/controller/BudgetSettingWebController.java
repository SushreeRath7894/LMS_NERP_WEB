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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.budget.model.BudgetSettingWebModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = { "/budget" })
public class BudgetSettingWebController {
	Logger logger = LoggerFactory.getLogger(BudgetSettingWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	
	@GetMapping(value = { "manage-budget-year" })
	public String budgetSetting(Model model, HttpSession session) {
		logger.info("Method : manageFY starts");
	
		try {
			DropDownModel[] fiscalyear= restClient.getForObject(env.getBudgetUrl() + "/getBudgetFiscalYear",
					DropDownModel[].class);

			List<DropDownModel> fiscalYearList = Arrays.asList(fiscalyear);
			System.out.println("fiscalYearList" + fiscalYearList);
			model.addAttribute("fiscalYearList", fiscalYearList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("Method : manageFY ends");
		return "budget/budget-setting";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("budget-setting-edit")
	public @ResponseBody JsonResponse<List<BudgetSettingWebModel>> viewBudgetsetting(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : viewBudgetsetting starts" + id);

		JsonResponse<List<BudgetSettingWebModel>> jsonResponse = new JsonResponse<List<BudgetSettingWebModel>>();
		
		
		try {
			jsonResponse = restClient.getForObject(env.getBudgetUrl() + "viewBudgetsetting?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<BudgetSettingWebModel> budgetSettingWebModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<BudgetSettingWebModel>>() {
				});

	

		System.out.println("###" + budgetSettingWebModel);
		jsonResponse.setBody(budgetSettingWebModel);

		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

		} else {
			jsonResponse.setMessage("Success");
		}
		if (jsonResponse.getCode() != null && jsonResponse.getCode() != "") {

		} else {
			jsonResponse.setCode("Success");
		}

		System.out.println("REsp" + jsonResponse);
		logger.info("Method : ViewProfilePosCounter ends");
		return jsonResponse;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/budget-setting-update")
	public @ResponseBody JsonResponse<Object> updateBudgetYear(
			@RequestBody BudgetSettingWebModel budgetSettingWebModel, Model model, HttpSession session) {

		logger.info("Method : updateBudgetYear starts" + budgetSettingWebModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {

			resp = restClient.postForObject(env.getBudgetUrl() + "updateBudget", budgetSettingWebModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getCode() == "") {
			resp.setCode("Success");
		}
		logger.info("Method : updateProfilePosCounter ends" + resp);

		return resp;

}
}