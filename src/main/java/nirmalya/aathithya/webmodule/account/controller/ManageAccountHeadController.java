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
import nirmalya.aathithya.webmodule.account.model.AccountHeadModel;
import nirmalya.aathithya.webmodule.account.model.AccountModel;
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
public class ManageAccountHeadController {
	Logger logger = LoggerFactory.getLogger(ManageAccountHeadController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Account
	 */
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-account-head-add")
	public @ResponseBody JsonResponse<Object> addAccountHead(@RequestBody AccountHeadModel accountHeadModel,
			HttpSession session) {

		logger.info("Method : addAccountHead starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web AccountModel ======================" + accountHeadModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			accountHeadModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addAccountHead", accountHeadModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addAccountHead ends");

		return resp;
	}

	/** 
	 * View Account
	 * 
	 */
	
	
	@GetMapping("/view-account-head")
	public String viewManageAccountHead(Model model, HttpSession session) {
		logger.info("Method : viewManageAccountHead start");	
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
		
		try {
			DropDownModel[] accountHeadType = restClient.getForObject(env.getAccountUrl() + "/headTypeList",
					DropDownModel[].class);

			List<DropDownModel> headTypeList = Arrays.asList(accountHeadType);
			model.addAttribute("headTypeList", headTypeList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewManageAccountHead end");
		return "account/manage-account-head";
	}
	

	/**
	 * Delete Account Record   
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-head-delete-id")
	public @ResponseBody JsonResponse<Object> deleteAccountHeadDetails(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deleteAccountHeadDetails function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteAccountHeadDetails?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteAccountHeadDetails function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
	
	
	
	/**
	 * View Account Ajax
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-head-throughAjax")
	public @ResponseBody List<AccountHeadModel> viewAccountHeadAjax(HttpSession session) {

		logger.info("Method : viewAccountHeadAjax starts");

		JsonResponse<List<AccountHeadModel>> resp = new JsonResponse<List<AccountHeadModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewAccountHeadDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountHeadModel> accountHeadModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountHeadModel>>() {
				});
		
		/*for (AccountHeadModel i : accountHeadModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("date for creation------------------"+date);
			}
		}*/
		
		resp.setBody(accountHeadModel);
		
		logger.info("Method : viewAccountHeadAjax ends");
		return resp.getBody();
	}
	
	/**
	 * Edit Account Record
	 */
	
	//view-account-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-head-edit")
	public @ResponseBody JsonResponse<List<AccountHeadModel>> editAccountHeadInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : editAccountHeadInfo starts" + id);
		
		JsonResponse<List<AccountHeadModel>> jsonResponse = new JsonResponse<List<AccountHeadModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editAccountHeadInfo?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountHeadModel> accountHeadModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountHeadModel>>() {
				});
		
		for (AccountHeadModel i : accountHeadModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("start date---------------"+date);
			}	
			
		}
		
		System.out.println("###" + accountHeadModel);
		jsonResponse.setBody(accountHeadModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editAccountHeadInfo ends");
		return jsonResponse;
	}
	
	
	
}
