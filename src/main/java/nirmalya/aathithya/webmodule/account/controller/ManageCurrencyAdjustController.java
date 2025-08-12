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
import nirmalya.aathithya.webmodule.account.model.CurrencyModel;
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
public class ManageCurrencyAdjustController {
	Logger logger = LoggerFactory.getLogger(ManageCurrencyAdjustController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Customer in Account
	 */
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-currency-adjustment-add")
	public @ResponseBody JsonResponse<Object> addCurrency(@RequestBody CurrencyModel currencyModel,
			HttpSession session) {

		logger.info("Method : addCurrency starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web AccountModel ======================" + currencyModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			currencyModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addCurrency", currencyModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addCurrency ends");

		return resp;
	}

	/** 
	 * View Customer Account
	 * 
	 */
	
	
	@GetMapping("/view-currency-adjustment")
	public String viewManageCurrency(Model model, HttpSession session) {
		logger.info("Method : viewManageCurrency start");	
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

		logger.info("Met    hod : viewManageCurrency end");
		return "account/currency-adjustment";
	}
	

	/**
	 * Delete Customer Account Record   
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-currency-adjustment-delete-id")
	public @ResponseBody JsonResponse<Object> deleteCurrencyDetails(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deleteCurrencyDetails function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteCurrencyDetails?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteCurrencyDetails function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
	
	
	
	/**
	 * View Currency Ajax
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-currency-adjustment-throughAjax")
	public @ResponseBody List<CurrencyModel> viewCurrency(HttpSession session) {

		logger.info("Method : viewCurrency starts");

		JsonResponse<List<CurrencyModel>> resp = new JsonResponse<List<CurrencyModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewCurrencyDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<CurrencyModel> currencyModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<CurrencyModel>>() {
				});
		
		for (CurrencyModel i : currencyModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("date for creation------------------"+date);
			}
		}
		
		resp.setBody(currencyModel);
		
		logger.info("Method : viewCurrency ends");
		return resp.getBody();
	}
	
	/**
	 * Edit Account Record
	 */
	
	//view-currency-adjustment-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-currency-adjustment-edit")
	public @ResponseBody JsonResponse<List<CurrencyModel>> editCurrencyInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editCurrencyInfo starts" + id);
		
		JsonResponse<List<CurrencyModel>> jsonResponse = new JsonResponse<List<CurrencyModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editCurrencyInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<CurrencyModel> currencyModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<CurrencyModel>>() {
				});
		
		for (CurrencyModel i : currencyModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("start date---------------"+date);
			}	
			
		}
		
		System.out.println("###" + currencyModel);
		jsonResponse.setBody(currencyModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editCurrencyInfo ends");
		return jsonResponse;
	}
	
	
	
}
