package nirmalya.aathithya.webmodule.account.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
@RequestMapping(value = "account")
public class ManageAccountController {
	Logger logger = LoggerFactory.getLogger(ManageAccountController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Account
	 */
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-account-add")
	public @ResponseBody JsonResponse<Object> addAccount(@RequestBody AccountModel accountModel,
			HttpSession session) {

		logger.info("Method : addAccount starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web AccountModel ======================" + accountModel);
		try {
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("created by id-------------------------------"+userId);

			accountModel.setCreatedBy(userId);
			accountModel.setOrganization(orgName);
			accountModel.setOrgDivision(orgDivision);

			resp = restClient.postForObject(env.getAccountUrl() + "/addAccount", accountModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addAccount ends");

		return resp;
	}

	/** 
	 * View Account
	 * 
	 */
	
	
	@GetMapping("/view-account")
	public String viewManageAccount(Model model, HttpSession session) {
		logger.info("Method : viewManageAccount start");	
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

		logger.info("Met    hod : viewBranch end");
		return "account/manage-account";
	}
	

	/**
	 * Delete Account Record   
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-delete-id")
	public @ResponseBody JsonResponse<Object> deleteAccoutDetails(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deleteAccoutDetails function starts"+id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteAccountDetails?id=" + id + "&orgName=" + orgName + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteAccoutDetails function Ends");
		
		logger.info("Response"+res);
		return res;
	}
	
	
	
	/**
	 * View Account Ajax
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-throughAjax")
	public @ResponseBody List<AccountModel> viewAccount(HttpSession session) {
		logger.info("Method : viewAccount starts");
		JsonResponse<List<AccountModel>> resp = new JsonResponse<List<AccountModel>>();
		
		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewAccountDetails?orgName=" + orgName + "&orgDiv=" + orgDiv , JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountModel> accountModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountModel>>() {
				});
		
		/*for (AccountModel i : accountModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("date for creation------------------"+date);
			}
		}*/
		
		resp.setBody(accountModel);
		
		logger.info("Method : viewAccount ends");
		return resp.getBody();
	}
	
	/**
	 * Edit Account Record
	 */
	
	//view-account-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-edit")
	public @ResponseBody JsonResponse<List<AccountModel>> editAccountInfo(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : editAccountInfo starts" + id);
		JsonResponse<List<AccountModel>> jsonResponse = new JsonResponse<List<AccountModel>>();
		
		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editAccountInfo?id=" + id + "&orgName=" + orgName + "&orgDiv=" + orgDiv,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		/*
		 * List<AccountModel> accountModel = mapper.convertValue(jsonResponse.getBody(),
		 * new TypeReference<List<AccountModel>>() { });
		 */
		
		/*
		 * for (AccountModel i : accountModel) { if (i.getCreatedDate() != null &&
		 * i.getCreatedDate() != "") { date =
		 * DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
		 * i.setCreatedDate(date); System.out.println("start date---------------"+date);
		 * }
		 * 
		 * }
		 * 
		 * logger.info("###" + accountModel); jsonResponse.setBody(accountModel);
		 */
		
		logger.info("REsp" + jsonResponse);
		logger.info("Method : editAccountInfo ends");
		return jsonResponse;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-account-addBank")
	public @ResponseBody JsonResponse<Object> addBankModalAccount(@RequestBody AccountBankModel accountBankModel,
			HttpSession session) {

		logger.info("Method : addBankModalAccount starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web AccountBankModel ======================" + accountBankModel);
		try {
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//System.out.println("created by id-------------------------------"+userId);

			accountBankModel.setCreatedBy(userId);
			accountBankModel.setOrganization(orgName);
			accountBankModel.setOrgDivision(orgDivision);
			resp = restClient.postForObject(env.getAccountUrl() + "/addBank", accountBankModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resp.getCode().equals("1062")) {
		    resp.setMessage("Duplicate Bank Entry");
		}
		logger.info("Method : addBankModalAccount ends"+resp);

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-account-getBankList" })
	public @ResponseBody JsonResponse<Object> getBankListAccount() {
		logger.info("Method : getBankListAccount starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "getBankListModal", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getBankListAccount ends" + res);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-account-getBranchList" })
	public @ResponseBody JsonResponse<AccountBranchModel> getBranchAutoSearch(Model model,
			@RequestBody String searchValue, BindingResult result,HttpSession session) {
		logger.info("Method : getBranchAutoSearch starts");
		JsonResponse<AccountBranchModel> res = new JsonResponse<AccountBranchModel>();
		String orgName = "";
		String orgDivision = "";
       try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getAccountUrl()+ "/getBranchAutoSearch?id=" + searchValue+ "&orgName=" + orgName+ "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getBranchAutoSearch ends==="+res);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-account-branchStateList" })
	public @ResponseBody JsonResponse<Object> branchStateList(@RequestParam String id) {
		logger.info("Method : branchStateList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getPipeline() + "getStateLists?id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		//System.out.println("state" + res);
		logger.info("Method : branchStateList ends==" + res);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-account-branchAddModal")
	public @ResponseBody JsonResponse<Object> addBranchAccountModal(@RequestBody AccountBranchModel accountBranchModel,
			HttpSession session) {

		logger.info("Method : addBranchAccountModal starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		logger.info("web AccountBranchModel ======================" + accountBranchModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			accountBranchModel.setCreatedBy(userId);
			resp = restClient.postForObject(env.getAccountUrl() + "/addBranch", accountBranchModel, JsonResponse.class);
			
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addBranchAccountModal ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-account-get-branch" })
	public @ResponseBody JsonResponse<Object> getbranchlist(@RequestParam String id,HttpSession session) {
		logger.info("Method : getbranchlist starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
       try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getAccountUrl() + "getbranchlist?id=" + id + "&orgName="+ orgName + "&orgDivision="+orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("state" + res);
		logger.info("Method : getbranchlist ends");
		return res;
	}
	
	
	
//	@SuppressWarnings("unchecked")
//	@PostMapping(value = { "view-account-get-bankName" })
//	public @ResponseBody JsonResponse<AccountBankModel> getBankNameDetails(Model model,
//			@RequestBody String searchValue, BindingResult result, HttpSession session) {
//		logger.info("Method : getBankNameDetails starts");
//		JsonResponse<AccountBankModel> res = new JsonResponse<AccountBankModel>();
//		String orgName = "";
//		String orgDivision = "";
//		try {
//			orgName = (String) session.getAttribute("ORGANIZATION");
//			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
//
//		} catch (Exception e) {
//
//
//		}
//		try {
//			res = restClient.getForObject(env.getAccountUrl() + "getBankNameDetails?id=" + searchValue
//					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		logger.info("Method : getBankNameDetails ends");
//		return res;
//	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-account-get-bankName" })
	public @ResponseBody JsonResponse<AccountBankModel> getBankNameDetails(Model model, @RequestBody String searchValue) {
		logger.info("Method : getBankNameDetails starts" + searchValue);
		JsonResponse<AccountBankModel> res = new JsonResponse<AccountBankModel>();

		try {
			res = restClient.getForObject(env.getAccountUrl() + "getAutoSearchBank?id=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getBankNameDetails ends");
		return res;
	}

	
}
