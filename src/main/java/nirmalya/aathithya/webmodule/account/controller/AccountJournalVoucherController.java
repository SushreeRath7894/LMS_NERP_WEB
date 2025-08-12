package nirmalya.aathithya.webmodule.account.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
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
import nirmalya.aathithya.webmodule.account.model.AccountCreditorLedgerModel;
import nirmalya.aathithya.webmodule.account.model.AccountJournalVoucherModel;
import nirmalya.aathithya.webmodule.account.model.AccountModel;
import nirmalya.aathithya.webmodule.account.model.ContraVoucherModel;
import nirmalya.aathithya.webmodule.account.model.DataSetAccountTree;
import nirmalya.aathithya.webmodule.account.model.ManageLeadgerModel;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.pipeline.model.CrmCustomerModel;
import nirmalya.aathithya.webmodule.common.utils.IndianNumberFormat;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
@RequestMapping(value = "account")
public class AccountJournalVoucherController {
	Logger logger = LoggerFactory.getLogger(AccountJournalVoucherController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	/**
	 * Add Account
	 */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping("/view-account-add") public @ResponseBody JsonResponse<Object>
	 * addAccount(@RequestBody AccountModel accountModel, HttpSession session) {
	 * 
	 * logger.info("Method : addAccount starts");
	 * 
	 * JsonResponse<Object> resp = new JsonResponse<Object>();
	 * System.out.println("web AccountModel ======================" + accountModel);
	 * try { String userId = ""; try { userId = (String)
	 * session.getAttribute("USER_ID"); } catch (Exception e) { e.printStackTrace();
	 * }
	 * 
	 * System.out.println("created by id-------------------------------"+userId);
	 * 
	 * accountModel.setCreatedBy(userId);
	 * 
	 * resp = restClient.postForObject(env.getAccountUrl() + "/addAccount",
	 * accountModel, JsonResponse.class);
	 * 
	 * } catch (RestClientException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } logger.info("Method : addAccount ends");
	 * 
	 * return resp; }
	 */
	/**
	 * View Account
	 * 
	 */

	@GetMapping("/view-account-journal-voucher")
	public String viewJournalVoucher(Model model, HttpSession session) {
		logger.info("Method : viewJournalVoucher start");

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

		logger.info("Method : viewJournalVoucher end");
		return "account/manage-journal-voucher";
	}

	// view-account-journal-voucher-getAccountDebitGroup

	/*
	 * debit account Auto search 1
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-account-journal-voucher-getAccountDebitGroup" })
	public @ResponseBody JsonResponse<ContraVoucherModel> getDebitAccountJournalSearch(Model model,
			@RequestBody String searchValue, BindingResult result,HttpSession session) {
		logger.info("Method : getDebitAccountJournalSearch starts");
		JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();
		String orgName = "";
		String orgDivision = "";
       try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getAccountUrl() + "/getDebitJournalAccountSearch?id=" + searchValue + "&orgName=" + orgName+ "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getDebitAccountJournalSearch ends");
		return res;
	}

	// view-account-journal-voucher-getAccountCreditGroup

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-account-journal-voucher-getAccountCreditGroup" })
	public @ResponseBody JsonResponse<ContraVoucherModel> getCreditAccountJournalSearch(Model model,
			@RequestBody String searchValue, BindingResult result,HttpSession session) {
		logger.info("Method : getCreditAccountJournalSearch starts");
		JsonResponse<ContraVoucherModel> res = new JsonResponse<ContraVoucherModel>();
		String orgName = "";
		String orgDivision = "";
       try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getAccountUrl() + "/getCreditJournalAccountSearch?id=" + searchValue+ "&orgName=" + orgName+ "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getCreditAccountJournalSearch ends");
		return res;
	}

	// view-account-journal-voucher-add-journal
	// add-journal-voucher

	/*
	 * post Mapping for add SPA table staff assign
	 * 
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "view-account-journal-voucher-add-journal", method = { RequestMethod.POST })
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

			res = restClient.postForObject(env.getAccountUrl() + "addJournalVoucher", journalVoucherModel,
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
	 * Delete Journal Voucher Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-delete-id")
	public @ResponseBody JsonResponse<Object> deleteJournalDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : deleteAccoutDetails function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
       try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(env.getAccountUrl() + "deleteJournalDetails?id=" + id + "&orgName=" + orgName+ "&orgDivision=" + orgDivision,JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteJournalDetails function Ends");

		System.out.println("Response" + res);
		return res;
	}

	// view-account-journal-voucher-approve-id

	/**
	 * Approve Journal Voucher Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-approve-id")
	public @ResponseBody JsonResponse<Object> approveJournalDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : approveJournalDetails function starts" + id);

		String userId = (String) session.getAttribute("USER_ID");
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "approveJournalDetails?id=" + id + "&userId=" + userId,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : approveJournalDetails function Ends");

		System.out.println("Response" + res);
		return res;
	}
	// view-account-journal-voucher-reject-id

	/**
	 * Reject Journal Voucher Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-reject-id")
	public @ResponseBody JsonResponse<Object> rejectJournalDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : rejectJournalDetails function starts" + id);

		String userId = (String) session.getAttribute("USER_ID");
		System.out.println("==userid====" + userId);
		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "rejectJournalDetails?id=" + id + "&userId=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : rejectJournalDetails function Ends");

		System.out.println("Response" + res);
		return res;
	}
	// view-account-journal-voucher-return-id

	/**
	 * Return Journal Voucher Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-return-id")
	public @ResponseBody JsonResponse<Object> returnJournalDetails(@RequestParam String id, HttpSession session) {
		logger.info("Method : returnJournalDetails function starts" + id);

		JsonResponse<Object> res = new JsonResponse<Object>();
		try {
			res = restClient.getForObject(env.getAccountUrl() + "returnJournalDetails?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : returnJournalDetails function Ends");

		System.out.println("Response" + res);
		return res;
	}
	
	
	//JSON View Account Ajax
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-throughAjax")
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
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewJournalVoucher?orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
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

	// view-account-edit

	@SuppressWarnings("unchecked")

	@GetMapping("view-account-journal-voucher-edit")
	public @ResponseBody JsonResponse<List<AccountJournalVoucherModel>> editAccountInfo(Model model, @RequestParam String id,
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
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editJournalInfo?id=" + id + "&orgName=" + orgName+ "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String date = "";

		String drProfDoc = null;
		String dateFormat = (String) (session).getAttribute("DATEFORMAT");

		List<AccountJournalVoucherModel> accountModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountJournalVoucherModel>>() {
				});

		System.out.println("###" + accountModel);
		jsonResponse.setBody(accountModel);

		System.out.println("REsp" + jsonResponse);
		logger.info("Method : editAccountInfo ends");
		return jsonResponse;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("view-account-journal-edit") public @ResponseBody
	 * JsonResponse<AccountModel> editAccountInfo(Model model, @RequestParam String
	 * id, HttpSession session) { logger.info("Method : editAccountInfo starts" +
	 * id); List<AccountModel> productList = new ArrayList<AccountModel>();
	 * 
	 * JsonResponse<AccountModel> jsonResponse = new JsonResponse<AccountModel>();
	 * String dateFormat = (String) (session).getAttribute("DATEFORMAT");
	 * System.out.println("date" + dateFormat);
	 * 
	 * AccountModel product = new AccountModel(); ObjectMapper mapper = new
	 * ObjectMapper();
	 * 
	 * try {
	 * 
	 * jsonResponse = restClient.getForObject(env.getAccountUrl() +
	 * "editAccountInfo?id=" + id, JsonResponse.class);
	 * 
	 * product = mapper.convertValue(jsonResponse.getBody(), new
	 * TypeReference<AccountModel>() { });
	 * System.out.println("enter mapper controller" + product);
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * jsonResponse.setBody(product);
	 * 
	 * if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
	 * 
	 * } else { jsonResponse.setMessage("Success"); }
	 * 
	 * System.out.println("REsp" + jsonResponse);
	 * 
	 * logger.info("Method : editAccountInfo ends"); return jsonResponse; }
	 */
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-vouchernumber")
	public @ResponseBody JsonResponse<List<DropDownModel>> vouchernumber(HttpSession session) {
		logger.info("Method : vouchernumber starts");
		JsonResponse<List<DropDownModel>> resp = new JsonResponse<List<DropDownModel>>();
		
		String orgName = "";
		String orgDivision = "";
       try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
		} catch (Exception e) {

		}
		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/getJournalvouchernumber?orgName="+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : vouchernumber ends"+resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "view-account-journal-voucher-stateList" })
	public @ResponseBody JsonResponse<Object> getStateListLedgerModalJournal(@RequestParam String id) {
		logger.info("Method : getStateListLedgerModalJournal starts" + id);
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
		logger.info("Method : getStateListLedgerModalJournal ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-account-journal-voucher-group-list" })
	public @ResponseBody JsonResponse<ManageLeadgerModel> getUnderGroupAutoSearchListJournal(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
		logger.info("Method : getUnderGroupAutoSearchListJournal starts");
		// System.out.println("QuotationNewModel"+searchValue);
		JsonResponse<ManageLeadgerModel> res = new JsonResponse<ManageLeadgerModel>();
		String userId = (String) session.getAttribute("USER_ID");
		String userType = (String) session.getAttribute("USER_ROLETYPE");
		try {

			res = restClient.getForObject(env.getAccountUrl() + "getundergrouplist?id=" + searchValue,
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
		logger.info("Method : getUnderGroupAutoSearchListJournal ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("view-account-journal-voucher-group-addLedger")
	public @ResponseBody JsonResponse<Object> addLedgerModalJournal(@RequestBody ManageLeadgerModel manageleadgermodel, Model model,
			HttpSession session) {

		logger.info("Method : addLedgerModalJournal starts" + manageleadgermodel);

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
		logger.info("Method : addLedgerModalJournal ends" + resp);

		return resp;
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-filteredData")
	public @ResponseBody Object viewJournalVoucherFilteredData(HttpSession session,@RequestParam String fromDate, String toDate) {

		logger.info("Method :viewJournalVoucherFilteredData starts");
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
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewJournalFilter?orgName=" + orgName + "&orgDivision=" + orgDivision +
					"&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method :viewJournalVoucherFilteredData ends"+resp);

		return resp;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-getLedgerName")
	public @ResponseBody List<ManageLeadgerModel> getLedger(HttpSession session) {

		logger.info("Method : getLedger starts");

		JsonResponse<List<ManageLeadgerModel>> resp = new JsonResponse<List<ManageLeadgerModel>>();

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "/restGetLedgerDetails", JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<ManageLeadgerModel> ManageLeadgerModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<ManageLeadgerModel>>() {
				});
	
		resp.setBody(ManageLeadgerModel);
		
		logger.info("Method : getLedger ends");
		return resp.getBody();
	}
	
	@GetMapping("/journal-register")
	public String viewJournalRegister(Model model, HttpSession session) {
		logger.info("Method : viewJournalRegister start");

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

		logger.info("Method : viewJournalRegister end");
		return "account/journal-register";
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/journal-register-Pdf" })
	public void journalRegisterPdf(HttpServletResponse response, Model model, @RequestParam String voucherType,@RequestParam String activityType,
			String fromDate, String toDate, HttpSession session) {
		logger.info("Method :journalRegisterPdf starts");

		AccountCreditorLedgerModel product = new AccountCreditorLedgerModel();
		JsonResponse<AccountCreditorLedgerModel> jsonResponse = new JsonResponse<AccountCreditorLedgerModel>();

		JsonResponse<List<AccountCreditorLedgerModel>> report = new JsonResponse<List<AccountCreditorLedgerModel>>();
		
		String orgName = "";
		String orgDivision = "";
		
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			report = restClient.getForObject(env.getAccountUrl() + "accountRegisterPdf?voucherType=" + voucherType + "&fromDate="
					+ fromDate + "&toDate=" + toDate  + "&orgName=" + orgName+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("responseReport=>>>>>>" + report);
		
			ObjectMapper mapper = new ObjectMapper();

			List<AccountCreditorLedgerModel> reportcard = mapper.convertValue(report.getBody(),
					new TypeReference<List<AccountCreditorLedgerModel>>() {
					});

			jsonResponse.setBody(product);
			
			double totalDebit = reportcard.stream()
                    .mapToDouble(item -> Double.parseDouble(item.getTransactionDebitAmt().replace(",", "")))
                    .sum();
			String totalDebitString = IndianNumberFormat.formatIndianNumber(totalDebit);
			
			double totalCredit = reportcard.stream()
                    .mapToDouble(item -> Double.parseDouble(item.getTransactionCreditAmt().replace(",", "")))
                    .sum();
			String totalCreditString =IndianNumberFormat.formatIndianNumber(totalCredit);
			
			String fromToDate = fromDate + " TO " + toDate;
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("vendorCard", reportcard);
			data.put("fromToDate", fromToDate);
			data.put("voucherType", activityType);
			data.put("orgDivision", orgDivision);
			data.put("totalDebitAmt", totalDebitString);
			data.put("totalCreditAmt", totalCreditString);
			String filename = "journal-register-pdf_" + fromDate + "_" + toDate + ".pdf";

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=" + filename);
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("account/account-register-pdf", data);
				InputStream in = new FileInputStream(file);
				fileData = IOUtils.toByteArray(in);
				response.setContentLength(fileData.length);
				response.getOutputStream().write(fileData);
				response.getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		
	}
	
	
	/* To get the Account Group Parent Name LIst */
	
	@SuppressWarnings("unchecked")
	@PostMapping("view-account-journal-voucher-parentNameList")
	public @ResponseBody JsonResponse<List<DataSetAccountTree>> getAccountParentList(
			@RequestBody String yearDtls, HttpSession session) {
		logger.info("Method : getAccountParentList starts");

		JsonResponse<List<DataSetAccountTree>> resp = new JsonResponse<List<DataSetAccountTree>>();
		
		String orgName = "";
		String orgDivision = "";
		
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "getAccountParentNameList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != null && resp.getMessage() != "") {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("success");
		}

		logger.info("Method : getAccountParentList Ends");
		System.out.println(resp);
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/view-account-journal-voucher-pdf" })
	public void journalVoucherPdf(HttpServletResponse response, Model model, @RequestParam String fromDate, String toDate, HttpSession session) {
		logger.info("Method :journalVoucherPdf starts");
		AccountCreditorLedgerModel product = new AccountCreditorLedgerModel();
		
		JsonResponse<List<AccountCreditorLedgerModel>> journalVoucherReport = new JsonResponse<List<AccountCreditorLedgerModel>>();
		
		String orgName = "";
		String orgDivision = "";
		
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			journalVoucherReport = restClient.getForObject(env.getAccountUrl() + "journalVoucherPdf?fromDate="+ fromDate + "&toDate=" + toDate + "&orgDivision="
		+ orgDivision + "&orgName="+orgName, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 	
		ObjectMapper mapper = new ObjectMapper();

		List<AccountCreditorLedgerModel> journalVouchercard = mapper.convertValue(journalVoucherReport.getBody(),
				new TypeReference<List<AccountCreditorLedgerModel>>() {
				});
		
		double totalDebit = journalVouchercard.stream()
                .mapToDouble(item -> Double.parseDouble(item.getTransactionDebitAmt().replace(",", "")))
                .sum();
		String totalDebitString =IndianNumberFormat.formatIndianNumber(totalDebit);
		
		double totalCredit = journalVouchercard.stream()
                .mapToDouble(item -> Double.parseDouble(item.getTransactionCreditAmt().replace(",", "")))
                .sum();
		String totalCreditString =IndianNumberFormat.formatIndianNumber(totalCredit);
		
		String fromToDate = fromDate + " TO " + toDate;
		
		Map<String, Object> data = new HashMap<String, Object>();
		 
		data.put("journalVouchercard", journalVouchercard);
		data.put("fromToDate", fromToDate);
		data.put("orgDivision", orgDivision);
		data.put("totalDebitAmt", totalDebitString);
		data.put("totalCreditAmt", totalCreditString);
		 
		String filename = "journal-voucher-pdf_" + fromDate + "_" + toDate + ".pdf";
		
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);
		
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/journal-voucher-pdf", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		logger.info("Method :journalVoucherPdf End");
}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-account-journal-voucher-getNewRefData")
	public @ResponseBody Object getPayInvoiceInfo(HttpSession session , @RequestParam String ledgerid,@RequestParam String voucherid) {

		logger.info("Method :getNewReferenceData starts");
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
			resp = restClient.getForObject(
					env.getAccountUrl() + "/getNewReferenceData?ledgerid=" + ledgerid +"&voucherid="+voucherid+ "&orgName=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method :getNewReferenceData ends" + resp);

		return resp;
	}
}
