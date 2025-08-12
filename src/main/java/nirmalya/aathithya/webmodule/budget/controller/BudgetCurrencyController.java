package nirmalya.aathithya.webmodule.budget.controller;

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


import nirmalya.aathithya.webmodule.budget.model.BudgetCurrencyWebModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller
@RequestMapping(value = { "budget/" })
public class BudgetCurrencyController {
	
	Logger logger = LoggerFactory.getLogger(BudgetCurrencyController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping(value = { "manage-currency" })
	public String manageCurrency(Model model, HttpSession session) {
		logger.info("Method : manageCurrency starts");
	
		logger.info("Method : manageCurrency ends");
		return "budget/manage-currency";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("manage-budget-currency-add")
	public @ResponseBody JsonResponse<Object> addCurrency(@RequestBody BudgetCurrencyWebModel manageCurrency, Model model,
			HttpSession session) {

		logger.info("Method : addCurrency starts" + manageCurrency);

		System.out.println("resp web controller-----------------------------------" + manageCurrency);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDivision = "";
		String createdBy = "";
		try {
			
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			createdBy = (String) session.getAttribute("USER_ID");
			
			manageCurrency.setCreatedBy(createdBy);
			manageCurrency.setOrgName(orgName);
			manageCurrency.setOrgDivision(orgDivision);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restClient.postForObject(env.getBudgetUrl() + "restaddcurrency", manageCurrency, JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getMessage() == "") {
			resp.setMessage("Success");
		}
		logger.info("Method : addCurrency ends" + resp);

		return resp;
	}
	
	/*
	@SuppressWarnings("unchecked")
	@GetMapping("manage-currency-view")
	public @ResponseBody List<BudgetCurrencyWebModel> viewCurrency(HttpSession session) {

		logger.info("Method : viewCurrency starts");

		JsonResponse<List<BudgetCurrencyWebModel>> resp = new JsonResponse<List<BudgetCurrencyWebModel>>();

		try {
			resp = restClient.getForObject(env.getBudgetUrl() + "restViewCurrency", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<BudgetCurrencyWebModel> manageCurrency = mapper.convertValue(resp.getBody(),
				new TypeReference<List<BudgetCurrencyWebModel>>() {
				});

		resp.setBody(manageCurrency);
		System.out.println("resp.getBody()-----------" + resp.getBody());

		logger.info("Method : viewCurrency ends");
		return resp.getBody();
	}  */
	
	//manage-department-budget-through-json

		@SuppressWarnings("unchecked")

		@GetMapping("manage-currency-view-through-json")
		public @ResponseBody Object viewQc(HttpSession session) {
			logger.info("Method :viewCurrencyBudget starts");
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
			System.out.println("checkId -------------------------------------------===" + env.getBudgetUrl() + "restBudgetViewCurrency");
			try {
				resp = restClient.getForObject(env.getBudgetUrl() + "restBudgetViewCurrency?orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			System.out.println("view===" + resp);
			logger.info("Method :viewCurrencyBudget ends");
			return resp;
		}
	
	// edit
			@SuppressWarnings("unchecked")
			@GetMapping("manage-currency-edit")
			public @ResponseBody JsonResponse<List<BudgetCurrencyWebModel>> editcurrencymanageInfo(Model model,
					@RequestParam String id, HttpSession session) {

				logger.info("Method : editcurrencymanageInfo starts" + id);

				JsonResponse<List<BudgetCurrencyWebModel>> jsonResponse = new JsonResponse<List<BudgetCurrencyWebModel>>();

				try {
					jsonResponse = restClient.getForObject(env.getBudgetUrl() + "edit-Currency-Info?id=" + id,
							JsonResponse.class);

				} catch (Exception e) {
					e.printStackTrace();
				}

				ObjectMapper mapper = new ObjectMapper();
				
				 String date = ""; String drProfDoc = null; String dateFormat = (String)
				 (session).getAttribute("DATEFORMAT");
				

				List<BudgetCurrencyWebModel> manageCurrenct = mapper.convertValue(jsonResponse.getBody(),
						new TypeReference<List<BudgetCurrencyWebModel>>() {
						});

				

				System.out.println("###" + manageCurrenct);
				jsonResponse.setBody(manageCurrenct);

				if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {

				} else {
					jsonResponse.setMessage("Success");
				}

				System.out.println("REsp" + jsonResponse);
				logger.info("Method :editeditcurrencymanageInfo ends");
				return jsonResponse;
			}

           //delete
			@SuppressWarnings("unchecked")
			@GetMapping("manage-currency-delete")
			public @ResponseBody JsonResponse<Object> deletecurrencyDetails(@RequestParam String id,
					 HttpSession session) {
				logger.info("Method : deletecurrencyDetails function starts"+id);

				JsonResponse<Object> res = new JsonResponse<Object>();

				

				try {
					res = restClient.getForObject(env.getBudgetUrl() + "delete-currency-Details?id=" + id  , JsonResponse.class);
				} catch (RestClientException e) {
					e.printStackTrace();
				}

				String message = res.getMessage();
				if (message != null && message != "") {

				} else {
					res.setMessage("Success");
				}
				logger.info("Method : deletecurrencyDetails function Ends");
				
				System.out.println("Response"+res);
				return res;
			}


}
