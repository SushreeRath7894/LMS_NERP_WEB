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
import nirmalya.aathithya.webmodule.account.model.TransactionLockModel;
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
public class ManageTransactionLockingController {
	Logger logger = LoggerFactory.getLogger(ManageTransactionLockingController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add view-transaction-locking-add-sales
	 */
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-transaction-locking-add-sales")
	public @ResponseBody JsonResponse<Object> addSalesLock(@RequestBody TransactionLockModel transactionLockModel,
			HttpSession session) {

		logger.info("Method : addAccount starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web AccountModel ======================" + transactionLockModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			transactionLockModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addTransactionSaleLock", transactionLockModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addAccount ends");

		return resp;
	}
	
	//view-transaction-locking-add-sales-unlock
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-transaction-locking-add-sales-unlock")
	public @ResponseBody JsonResponse<Object> addSalesUnlock(@RequestBody TransactionLockModel transactionLockModel,
			HttpSession session) {

		logger.info("Method : addSalesUnlock starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web Transaction model ======================" + transactionLockModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			transactionLockModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addTransactionSaleUnlock", transactionLockModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addSalesUnlock ends");

		return resp;
	}
	
	//view-transaction-locking-add-purchase
	
	/**
	 * view-transaction-locking-add-purchase
	 */
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-transaction-locking-add-purchase")
	public @ResponseBody JsonResponse<Object> addPurchaseLock(@RequestBody TransactionLockModel transactionLockModel,
			HttpSession session) {

		logger.info("Method : addPurchaseLock starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web addPurchaseLock Model ======================" + transactionLockModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			transactionLockModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addTransactionPurchaseLock", transactionLockModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addPurchaseLock ends");

		return resp;
	}
	
	//view-transaction-unlocking-add-purchase
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-transaction-locking-add-purchase-unlock")
	public @ResponseBody JsonResponse<Object> addPurchaseUnlock(@RequestBody TransactionLockModel transactionLockModel,
			HttpSession session) {

		logger.info("Method : view-transaction-unlocking-add-purchase starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web Transaction model ======================" + transactionLockModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			transactionLockModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addTransactionPurchaseUnlock", transactionLockModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : view-transaction-unlocking-add-purchase ends");

		return resp;
	}
	
//view-transaction-locking-add-banking
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-transaction-locking-add-banking")
	public @ResponseBody JsonResponse<Object> addBankingLock(@RequestBody TransactionLockModel transactionLockModel,
			HttpSession session) {

		logger.info("Method : addBankingLock starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web addBankingLock Model ======================" + transactionLockModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			transactionLockModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addTransactionBankingLock", transactionLockModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addBankingLock ends");

		return resp;
	}
	
//view-transaction-locking-add-banking-unlock
	@SuppressWarnings("unchecked")
	@PostMapping("/view-transaction-locking-add-banking-unlock")
	public @ResponseBody JsonResponse<Object> addBankingUnlock(@RequestBody TransactionLockModel transactionLockModel,
			HttpSession session) {

		logger.info("Method : view-transaction-unlocking-add-banking starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web Transaction model ======================" + transactionLockModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			transactionLockModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addTransactionBankingUnlock", transactionLockModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : view-transaction-unlocking-add-banking ends");

		return resp;
	}
	//view-transaction-locking-add-accountant
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-transaction-locking-add-accountant")
	public @ResponseBody JsonResponse<Object> addAccountantLock(@RequestBody TransactionLockModel transactionLockModel,
			HttpSession session) {

		logger.info("Method : addAccountantLock starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web addAccountantLock Model ======================" + transactionLockModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			transactionLockModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addTransactionAccountLock", transactionLockModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addAccountantLock ends");

		return resp;
	}
	
	//view-transaction-locking-add-accountant-unlock
	
	@SuppressWarnings("unchecked")
	@PostMapping("/view-transaction-locking-add-accountant-unlock")
	public @ResponseBody JsonResponse<Object> addAccountUnlock(@RequestBody TransactionLockModel transactionLockModel,
			HttpSession session) {

		logger.info("Method : addAccountUnlock");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("web Transaction model ======================" + transactionLockModel);
		try {
			String userId = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("created by id-------------------------------"+userId);

			transactionLockModel.setCreatedBy(userId);

			resp = restClient.postForObject(env.getAccountUrl() + "/addTransactionAccountUnlock", transactionLockModel, JsonResponse.class);

		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : addAccountUnlock");

		return resp;
	}

	/** 
	 * View Account
	 * 
	 */
	
	
	@GetMapping("/view-transaction-locking")
	public String viewManageTransactionLock(Model model, HttpSession session) {
		logger.info("Method : viewManageTransactionLock start");	
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

		logger.info("Met    hod : viewManageTransactionLock end");
		return "account/transaction-locking";
	}
	

	/**
	 * Delete Account Record   
	 */
	
	/*@SuppressWarnings("unchecked")
	@GetMapping("view-account-delete-id")
	public @ResponseBody JsonResponse<Object> deleteAccoutDetails(@RequestParam String id,
			 HttpSession session) {
		logger.info("Method : deleteAccoutDetails function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteAccountDetails?id="+id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteAccoutDetails function Ends");
		
		System.out.println("Response"+res);
		return res;
	}
	
	*/
	
	/**
	 * View Account Ajax
	 */
	
	/*@SuppressWarnings("unchecked")
	@GetMapping("view-account-throughAjax")
	public @ResponseBody List<AccountModel> viewAccount(HttpSession session) {

		logger.info("Method : viewAccount starts");

		JsonResponse<List<AccountModel>> resp = new JsonResponse<List<AccountModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewAccountDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<AccountModel> accountModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<AccountModel>>() {
				});
		
		for (AccountModel i : accountModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
				System.out.println("date for creation------------------"+date);
			}
		}
		
		resp.setBody(accountModel);
		
		logger.info("Method : viewAccount ends");
		return resp.getBody();
	}
	*/
	/**
	 *view-transaction-locking-date
	 */
	
	//view-transaction-locking-date
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-transaction-locking-data")
	public @ResponseBody JsonResponse<List<TransactionLockModel>> getTransactionLockingInfo(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : getTransactionLockingInfo starts" + id);
		
		JsonResponse<List<TransactionLockModel>> jsonResponse = new JsonResponse<List<TransactionLockModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/getTransactionData?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		String saleLockDate = "";
		String purchaseLockDate = "";
		String bankingLockDate = "";
		String accountantLockDate = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<TransactionLockModel> transactionLockModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<TransactionLockModel>>() {
				});
		
		for (TransactionLockModel i : transactionLockModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
			}	
			
			if (i.getSalesLockDate() != null && i.getSalesLockDate() != "") {
				saleLockDate = DateFormatter.dateFormat(i.getSalesLockDate(), dateFormat);
				i.setSalesLockDate(saleLockDate);
			}	
			
			if (i.getPurchaseLockDate() != null && i.getPurchaseLockDate() != "") {
				purchaseLockDate = DateFormatter.dateFormat(i.getPurchaseLockDate(), dateFormat);
				i.setPurchaseLockDate(purchaseLockDate);
			}
			
			if (i.getBankingLockDate() != null && i.getBankingLockDate() != "") {
				bankingLockDate = DateFormatter.dateFormat(i.getBankingLockDate(), dateFormat);
				i.setBankingLockDate(bankingLockDate);
			}
			
			if (i.getAccountantLockDate() != null && i.getAccountantLockDate() != "") {
				accountantLockDate = DateFormatter.dateFormat(i.getAccountantLockDate(), dateFormat);
				i.setAccountantLockDate(accountantLockDate);
			}	
			
		}
		
		System.out.println("###" + transactionLockModel);
		jsonResponse.setBody(transactionLockModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : getTransactionLockingInfo ends");
		return jsonResponse;
	}
	
	//view-transaction-locking-sales-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-transaction-locking-sales-edit")
	public @ResponseBody JsonResponse<List<TransactionLockModel>> getTransactionSalesEdit(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : getTransactionSalesEdit starts" + id);
		
		JsonResponse<List<TransactionLockModel>> jsonResponse = new JsonResponse<List<TransactionLockModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/getTransactionSalesData?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<TransactionLockModel> transactionLockModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<TransactionLockModel>>() {
				});
		
		for (TransactionLockModel i : transactionLockModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
			}	
				
			
		}
		
		System.out.println("###" + transactionLockModel);
		jsonResponse.setBody(transactionLockModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : getTransactionSalesEdit ends");
		return jsonResponse;
	}
	
	
	//view-transaction-locking-purchase-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-transaction-locking-purchase-edit")
	public @ResponseBody JsonResponse<List<TransactionLockModel>> getTransactionPurchaseEdit(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : getTransactionPurchaseEdit starts" + id);
		
		JsonResponse<List<TransactionLockModel>> jsonResponse = new JsonResponse<List<TransactionLockModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/getTransactionPurchaseData?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<TransactionLockModel> transactionLockModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<TransactionLockModel>>() {
				});
		
		for (TransactionLockModel i : transactionLockModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
			}	
				
			
		}
		
		System.out.println("###" + transactionLockModel);
		jsonResponse.setBody(transactionLockModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : getTransactionPurchaseEdit ends");
		return jsonResponse;
	}
	
	
	//view-transaction-locking-banking-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-transaction-locking-banking-edit")
	public @ResponseBody JsonResponse<List<TransactionLockModel>> getTransactionBankingEdit(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : getTransactionBankingEdit starts" + id);
		
		JsonResponse<List<TransactionLockModel>> jsonResponse = new JsonResponse<List<TransactionLockModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/getTransactionBankingData?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<TransactionLockModel> transactionLockModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<TransactionLockModel>>() {
				});
		
		for (TransactionLockModel i : transactionLockModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
			}	
				
			
		}
		
		System.out.println("###" + transactionLockModel);
		jsonResponse.setBody(transactionLockModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : getTransactionBankingEdit ends");
		return jsonResponse;
	}
	
	//view-transaction-locking-account-edit
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-transaction-locking-account-edit")
	public @ResponseBody JsonResponse<List<TransactionLockModel>> getTransactionAccountEdit(Model model, @RequestParam String id,
			HttpSession session) {

		logger.info("Method : getTransactionAccountEdit starts" + id);
		
		JsonResponse<List<TransactionLockModel>> jsonResponse = new JsonResponse<List<TransactionLockModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/getTransactionAccountData?id=" + id,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		ObjectMapper mapper = new ObjectMapper();
		String date = "";
		
		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");
		
		List<TransactionLockModel> transactionLockModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<TransactionLockModel>>() {
				});
		
		for (TransactionLockModel i : transactionLockModel) {
			if (i.getCreatedDate() != null && i.getCreatedDate() != "") {
				date = DateFormatter.dateFormat(i.getCreatedDate(), dateFormat);
				i.setCreatedDate(date);
			}	
				
			
		}
		
		System.out.println("###" + transactionLockModel);
		jsonResponse.setBody(transactionLockModel);
		
		System.out.println("REsp" + jsonResponse);
		logger.info("Method : getTransactionAccountEdit ends");
		return jsonResponse;
	}
	
}
