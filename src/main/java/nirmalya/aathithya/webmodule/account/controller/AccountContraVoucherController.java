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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountBankModel;
import nirmalya.aathithya.webmodule.account.model.AccountBranchModel;
import nirmalya.aathithya.webmodule.account.model.AccountJournalVoucherModel;
import nirmalya.aathithya.webmodule.account.model.AccountModel;
import nirmalya.aathithya.webmodule.account.model.ContraVoucherModel;
import nirmalya.aathithya.webmodule.account.model.ManageLeadgerModel;
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
public class AccountContraVoucherController {
	Logger logger = LoggerFactory.getLogger(AccountContraVoucherController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	/**
	 * Add Account
	 */

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "view-account-contra-voucher-add", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<DropDownModel> addJournalVoucher(
			@RequestBody List<AccountJournalVoucherModel> journalVoucherModel, Model model, HttpSession session) {
		logger.info("Method : addJournalVoucher function starts");
		System.out.println(journalVoucherModel + "journalVoucherModel");
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();
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
		//journalVoucherModel.get(0).setCreatedBy(userId);
		for (AccountJournalVoucherModel m : journalVoucherModel) {
			
			m.setOrganization(orgName);
			m.setCreatedBy(userId);
			m.setOrgDivision(orgDivision);
		}
		
		try {

			res = restClient.postForObject(env.getAccountUrl() + "addContraVoucher", journalVoucherModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : addJournalVoucher function Ends");
		return res;
	}
	
	/** 
	 * View Contra Voucher
	 * 
	 */
	
	
	@GetMapping("/view-account-contra-voucher")
	public String viewManageContraVoucher(Model model, HttpSession session) {
		logger.info("Method : viewManageContraVoucher start");	
		try {
			DropDownModel[] voucher = restClient.getForObject(env.getAccountUrl() + "/getVoucherTypeList",
					DropDownModel[].class);

			List<DropDownModel> voucherList = Arrays.asList(voucher);
			model.addAttribute("voucherList", voucherList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] country= restClient.getForObject(env.getAccountUrl() + "/getCountryList",
					DropDownModel[].class);

			List<DropDownModel> countryList = Arrays.asList(country);
			System.out.println("countryList" + countryList);
			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] ledger = restClient.getForObject(env.getAccountUrl() + "/getLedgerList",
					DropDownModel[].class);

			List<DropDownModel> ledgerList = Arrays.asList(ledger);
			model.addAttribute("ledgerList", ledgerList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewManageContraVoucher end");
		return "account/manage-contra-voucher";
	}
	
	
	

	/**
	 * Delete Account Record   
	 */
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-contra-voucher-deleteId")
	public @ResponseBody JsonResponse<Object> deleteContraDetails(@RequestParam String id,HttpSession session) {
		logger.info("Method : deleteContraDetails function starts"+id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
       try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteContraDetails?id="+id + "&orgName=" + orgName+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteContraDetails function Ends");
		return res;
	}

	//JSON View Account Ajax
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-contra-voucher-throughAjax")
	public @ResponseBody Object viewJournalVoucher(HttpSession session) {

		logger.info("Method :viewJournalVoucher starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewContraVouDetails?orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		//System.out.println("QA-Request-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewJournalVoucher ends"+resp);

		return resp;
	}
	
	
	/**
	 * Edit Account Record
	 */
	
	//view-account-contra-voucher-edit

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-contra-voucher-edit")
	public @ResponseBody JsonResponse<List<AccountJournalVoucherModel>> editContraVouInfo(Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method : editAccountInfo starts" + id);
		JsonResponse<List<AccountJournalVoucherModel>> jsonResponse = new JsonResponse<List<AccountJournalVoucherModel>>();
		
		String orgName = "";
		String orgDivision = "";
       try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editContraInfo?id=" + id + "&orgName=" + orgName+ "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountJournalVoucherModel> accountModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountJournalVoucherModel>>() {
				});

		System.out.println("###" + accountModel);
		jsonResponse.setBody(accountModel);

		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editContraVouInfo ends");
		return jsonResponse;
	}
	
	
	//view-account-contra-voucher-debit-list
	


		/*
		 * debit account Auto search      1
		 */
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-debit" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getDebitAccountSearch(Model model,
				@RequestBody String searchValue, BindingResult result,HttpSession session) {
			logger.info("Method : getDebitAccountSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();
			String orgName = "";
			String orgDivision = "";
	       try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
			} catch (Exception e) {

			}
			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getDebitAccountSearch?id=" + searchValue+ "&orgName=" + orgName+ "&orgDivision=" + orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getDebitAccountSearch ends");
			return res;
		}
		
		//view-account-contra-voucher-credit     2
	
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-credit" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getCreditAccountSearch(Model model,
				@RequestBody String searchValue, BindingResult result,HttpSession session) {
			logger.info("Method : getCreditAccountSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();
			String orgName = "";
			String orgDivision = "";
	       try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
			} catch (Exception e) {

			}
			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getCreditAccountSearch?id=" + searchValue+ "&orgName=" + orgName+ "&orgDivision=" + orgDivision,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getCreditAccountSearch ends");
			return res;
		}
		
		
		//view-account-contra-voucher-fromAccB2B        3
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-fromAccB2B" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getFromAccountB2BSearch(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getFromAccountB2BSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getFromAccountB2BSearch?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getFromAccountB2BSearch ends");
			return res;
		}
		
		
		//view-account-contra-voucher-toAccountB2B     4
		
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-toAccountB2B" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getToAccountB2BSearch(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getToAccountB2BSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getFromAccountB2BSearch?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getToAccountB2BSearch ends");
			return res;
		}
		
		
		//view-account-contra-voucher-fromBankB2C      5
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-fromBankB2C" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getFromAccountB2CSearch(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getFromAccountB2CSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getFromAccountB2BSearch?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getFromAccountB2CSearch ends");
			return res;
		}
		
		//view-account-contra-voucher-toBankC2B    6
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-toBankC2B" })
		public @ResponseBody JsonResponse<ContraVoucherModel> getToAccountC2BSearch(Model model,
				@RequestBody String searchValue, BindingResult result) {
			logger.info("Method : getToAccountC2BSearch starts");
			JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();

			try {
				res = restClient.getForObject(env.getAccountUrl()+ "/getFromAccountB2BSearch?id=" + searchValue,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : getToAccountC2BSearch ends");
			return res;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-account-contra-voucher-vouchernumber")
		public @ResponseBody JsonResponse<List<DropDownModel>> contraVouchernumber(HttpSession session) {
			logger.info("Method : contraVouchernumber starts");
			JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
			String orgName = "";
			String orgDivision = "";
	       try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
			} catch (Exception e) {

			}
			try {
				resp = restClient.getForObject(env.getAccountUrl() + "/getContravoucherNumber?orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			logger.info("Method : contraVouchernumber ends"+resp);
			return resp;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping(value = { "view-account-contra-voucher-stateList" })
		public @ResponseBody JsonResponse<Object> getStateListLedgerModal(@RequestParam String id) {
			logger.info("Method : getStateListLedgerModal starts" + id);
			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restClient.getForObject(env.getAccountUrl() + "ledgerGetStateLists?id=" + id, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}
			System.out.println("state" + res);
			logger.info("Method : getStateListLedgerModal ends");
			return res;
		}
		
		@SuppressWarnings("unchecked")
		@PostMapping(value = { "view-account-contra-voucher-group-list" })
		public @ResponseBody JsonResponse<ManageLeadgerModel> getUnderGroupAutoSearchList(Model model,
				@RequestBody String searchValue, BindingResult result, HttpSession session) {
			logger.info("Method : getUnderGroupAutoSearchList starts");
			// System.out.println("QuotationNewModel"+searchValue);
			JsonResponse<ManageLeadgerModel> res = new JsonResponse<ManageLeadgerModel>();
			String userId = (String) session.getAttribute("USER_ID");
			String userType = (String) session.getAttribute("USER_ROLETYPE");
			String orgName = "";
			String orgDiv = "";
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
			try {

				res = restClient.getForObject(env.getAccountUrl() + "getundergrouplist?id=" + searchValue + "&orgName=" + orgName + "&orgDiv=" + orgDiv,
						JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			

			if (res.getCode() != null) {
				res.setCode("success");
			} else {
				res.setCode("Unsuccess");
			}
			System.out.println("RESPONSE@@" + res);
			logger.info("Method : getUnderGroupAutoSearchList ends");
			return res;
		}
		
		@SuppressWarnings("unchecked")
		@PostMapping("view-account-contra-voucher-group-addLedger")
		public @ResponseBody JsonResponse<Object> addLedgerModalContra(@RequestBody ManageLeadgerModel manageleadgermodel, Model model,
				HttpSession session) {

			logger.info("Method : addLedgerModalContra starts" + manageleadgermodel);

			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			manageleadgermodel.setOrganization(orgName);
			manageleadgermodel.setOrgDivision(orgDivision);
			try {

				resp = restClient.postForObject(env.getAccountUrl() + "addLedgerModal", manageleadgermodel, JsonResponse.class);

			} catch (RestClientException e) {

				e.printStackTrace();
			}

			if (resp.getMessage() == "") {
				resp.setMessage("Success");
			}
			//System.out.println("hello BUlet");
			logger.info("Method : addLedgerModalContra ends" + resp);

			return resp;
			
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("view-account-contra-voucher-filteredData")
		public @ResponseBody Object viewContraVoucherFilteredData(HttpSession session,@RequestParam String fromDate, String toDate) {

			logger.info("Method :viewContraVoucherFilteredData starts**"+fromDate+"**"+toDate);
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				resp = restClient.getForObject(env.getAccountUrl() + "/restViewContraFilter?orgName=" + orgName + "&orgDivision=" + orgDivision + 
						"&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			logger.info("Method :viewJournalVoucher ends"+resp);
			return resp;
		}
		
		@SuppressWarnings("unchecked")

		@GetMapping(value = { "view-account-contra-voucher-banklist" })
		public @ResponseBody JsonResponse<Object> getBankAccounts() {
			logger.info("Method : getBankAccounts starts" );
			JsonResponse<Object> res = new JsonResponse<Object>();
			try {
				res = restClient.getForObject(env.getAccountUrl() + "/getBankNameList", JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (res.getMessage() != null) {
				res.setCode(res.getMessage());
				res.setMessage("Unsuccess");
			} else {
				res.setMessage("success");
			}
			System.out.println("state" + res);
			logger.info("Method : getBankAccounts ends");
			return res;
		}
		
	
}
