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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountBankModel;
import nirmalya.aathithya.webmodule.account.model.AccountBranchModel;
import nirmalya.aathithya.webmodule.account.model.AccountCreditorLedgerModel;
import nirmalya.aathithya.webmodule.account.model.AccountJournalVoucherModel;
import nirmalya.aathithya.webmodule.account.model.AccountModel;
import nirmalya.aathithya.webmodule.account.model.AccountPaymentVoucherDetailsModel;
import nirmalya.aathithya.webmodule.account.model.ContraVoucherModel;
import nirmalya.aathithya.webmodule.account.model.SalesInvoicePaymentModel;
import nirmalya.aathithya.webmodule.account.model.VendorListModel;
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
public class AccountPaymentVoucherController {
	Logger logger = LoggerFactory.getLogger(AccountPaymentVoucherController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	/**
	 * Add Account
	 */

	@GetMapping("/payment-voucher")
	public String paymentVoucher(Model model, HttpSession session) {
		logger.info("Method : paymentVoucher start");

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
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
			DropDownModel[] bankAccountList = restClient.getForObject(env.getAccountUrl()
					+ "/getBankAccountPaymentList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> bankPaymentAccountList = Arrays.asList(bankAccountList);
			model.addAttribute("bankPaymentAccountList", bankPaymentAccountList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] country = restClient.getForObject(env.getAccountUrl() + "/getCountryList",
					DropDownModel[].class);

			List<DropDownModel> countryList = Arrays.asList(country);
			System.out.println("countryList" + countryList);
			model.addAttribute("countryList", countryList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : paymentVoucher end");
		return "account/manage-payment-voucher";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "view-account-payment-voucher-add", method = { RequestMethod.POST })
	public @ResponseBody JsonResponse<DropDownModel> addPaymentVoucher(
			@RequestBody List<AccountJournalVoucherModel> journalVoucherModel, Model model, HttpSession session) {
		logger.info("Method : addPaymentVoucher function starts" + journalVoucherModel);
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
		for (AccountJournalVoucherModel m : journalVoucherModel) {

			m.setOrganization(orgName);
			m.setCreatedBy(userId);
			m.setOrgDivision(orgDivision);
		}
		try {

			res = restClient.postForObject(env.getAccountUrl() + "addPaymentVoucher", journalVoucherModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : addPaymentVoucher function Ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-payment-voucher")
	public @ResponseBody Object viewPaymentVoucher(HttpSession session) {

		logger.info("Method :viewPaymentVoucher starts");
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
					env.getAccountUrl() + "/restViewPaymentDetails?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// System.out.println("QA-Request-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewPaymentVoucher ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")

	@GetMapping("view-account-payment-voucher-edit")
	public @ResponseBody JsonResponse<List<AccountJournalVoucherModel>> editPaymentInfo(Model model,
			@RequestParam String id, HttpSession session) {

		logger.info("Method : editPaymentInfo starts" + id);

		JsonResponse<List<AccountJournalVoucherModel>> jsonResponse = new JsonResponse<List<AccountJournalVoucherModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "/editPaymentInfo?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

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
		logger.info("Method : editPaymentInfo ends");
		return jsonResponse;
	}

	/**
	 * Delete Journal Voucher Record
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("payment-voucher-delete-id")
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
			res = restClient.getForObject(env.getAccountUrl() + "deletePaymentDetails?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteJournalDetails function Ends");

		System.out.println("Response" + res);
		return res;
	}

	// view-account-journal-voucher-approve-id

	@SuppressWarnings("unchecked")
	@GetMapping("payment-voucher-vouchernumber")
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
			resp = restClient.getForObject(
					env.getAccountUrl() + "/getPaymentvoucherNumber?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : vouchernumber ends" + resp);
		return resp;
	}

	/*
	 * debit account Auto search 1
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "payment-voucher-getAccountDebitGroup" })
	public @ResponseBody JsonResponse<ContraVoucherModel> getDebitAccountJournalSearch(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
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
			res = restClient.getForObject(env.getAccountUrl() + "/getDebitPaymentAccountSearch?id=" + searchValue
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getDebitAccountJournalSearch ends");
		return res;
	}

	// view-account-journal-voucher-getAccountCreditGroup

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "payment-voucher-getAccountCreditGroup" })
	public @ResponseBody JsonResponse<ContraVoucherModel> getCreditAccountJournalSearch(Model model,
			@RequestBody String searchValue, BindingResult result, HttpSession session) {
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
			res = restClient.getForObject(env.getAccountUrl() + "/getCreditPaymentAccountSearch?id=" + searchValue
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getCreditAccountJournalSearch ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-payment-voucher-filteredData")
	public @ResponseBody Object viewPaymentVoucherFilteredData(HttpSession session, @RequestParam String fromDate,
			String toDate) {

		logger.info("Method :viewPaymentVoucherFilteredData starts");
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
			resp = restClient.getForObject(env.getAccountUrl() + "/restViewPaymentFilter?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method :viewPaymentVoucherFilteredData ends" + resp);

		return resp;
	}

	/* Get All Invoice List */
	@SuppressWarnings("unchecked")
	@GetMapping("payment-voucher-get-invoiceList")
	public @ResponseBody Object invoiceList(HttpSession session, String id) {
		logger.info("Method :invoiceList starts");
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
			resp = restClient.getForObject(env.getAccountUrl() + "rest-invoiceList?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :invoiceList ends");
		return resp;
	}

	/* Auto Search */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "payment-voucher-vendorList" })
	public @ResponseBody JsonResponse<VendorListModel> getVendorNameList(Model model, @RequestBody String searchValue,
			BindingResult result, HttpSession session) {
		logger.info("Method : getVendorNameList starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<VendorListModel> res = new JsonResponse<VendorListModel>();

		try {
			res = restClient.getForObject(env.getAccountUrl() + "getVendorNameList?id=" + searchValue + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getVendorNameList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("view-account-payment-voucher-addDebitNote")
	public @ResponseBody JsonResponse<Object> addDebitNoteMethodAdj(
			@RequestBody SalesInvoicePaymentModel salesInvoicePaymentModel, Model model, HttpSession session) {
		logger.info("Method : addDebitNoteMethodAdj function starts");

		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		salesInvoicePaymentModel.setCreatedBy(userId);
		salesInvoicePaymentModel.setOrganization(orgName);
		salesInvoicePaymentModel.setOrgDivision(orgDivision);
		JsonResponse<Object> res = new JsonResponse<Object>();

		try {
			res = restClient.postForObject(env.getAccountUrl() + "addDebitNoteMethodAdj", salesInvoicePaymentModel,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

//		String message = res.getMessage();
//		if (message != null && message != "") {
//
//		} else {
//			res.setMessage("Success");
//		}

		if (res.getCode() == "") {
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
		}
		logger.info("Method : addDebitNoteMethodAdj function Ends");

		System.out.println("Response" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-payment-voucher-getDebitNoteList")
	public @ResponseBody Object getDebitNoteListPayment(HttpSession session, @RequestParam String vendorId) {

		logger.info("Method :getDebitNoteListPayment starts");
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
			resp = restClient.getForObject(env.getAccountUrl() + "/restGetDebitNoteListPayment?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&vendorId=" + vendorId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// System.out.println("QA-Request-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :getDebitNoteListPayment ends" + resp);

		return resp;
	}

	@GetMapping("/payment-register")
	public String paymentRegister(Model model, HttpSession session) {
		logger.info("Method : paymentRegister start");

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
			DropDownModel[] bankAccountList = restClient
					.getForObject(env.getAccountUrl() + "/getBankAccountPaymentList", DropDownModel[].class);

			List<DropDownModel> bankPaymentAccountList = Arrays.asList(bankAccountList);
			model.addAttribute("bankPaymentAccountList", bankPaymentAccountList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : paymentRegister end");
		return "account/payment-register";
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/payment-register-Pdf" })
	public void paymentRegisterPdf(HttpServletResponse response, Model model, @RequestParam String voucherType,
			String fromDate, String toDate, HttpSession session) {
		logger.info("Method :paymentRegisterPdf starts");

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
			report = restClient
					.getForObject(
							env.getAccountUrl() + "accountRegisterPdf?voucherType=" + voucherType + "&fromDate="
									+ fromDate + "&toDate=" + toDate + "&orgDivision=" + orgDivision,
							JsonResponse.class);
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
				.mapToDouble(item -> Double.parseDouble(item.getTransactionDebitAmt().replace(",", ""))).sum();
		String totalDebitString = IndianNumberFormat.formatIndianNumber(totalDebit);

		double totalCredit = reportcard.stream()
				.mapToDouble(item -> Double.parseDouble(item.getTransactionCreditAmt().replace(",", ""))).sum();
		String totalCreditString = IndianNumberFormat.formatIndianNumber(totalCredit);

		String fromToDate = fromDate + " TO " + toDate;

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("vendorCard", reportcard);
		data.put("fromToDate", fromToDate);
		data.put("voucherType", voucherType);
		data.put("orgDivision", orgDivision);
		data.put("totalDebitAmt", totalDebitString);
		data.put("totalCreditAmt", totalCreditString);
		String filename = "payment-register-pdf_" + fromDate + "_" + toDate + ".pdf";

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

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/payment-voucher-pdf" })
	public void paymentRegisterPdf(HttpServletResponse response, Model model, @RequestParam String fromDate,
			String toDate, HttpSession session) {
		logger.info("Method :paymentVoucherPdf starts");

		AccountCreditorLedgerModel product = new AccountCreditorLedgerModel();

		JsonResponse<List<AccountCreditorLedgerModel>> paymentReport = new JsonResponse<List<AccountCreditorLedgerModel>>();

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			paymentReport = restClient.getForObject(env.getAccountUrl() + "paymentVoucherPdf?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&orgDivision=" + orgDivision + "&orgName=" + orgName, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();

		List<AccountCreditorLedgerModel> paymentReportcard = mapper.convertValue(paymentReport.getBody(),
				new TypeReference<List<AccountCreditorLedgerModel>>() {
				});

		double totalDebit = paymentReportcard.stream()
				.mapToDouble(item -> Double.parseDouble(item.getTransactionDebitAmt().replace(",", ""))).sum();
		String totalDebitString = IndianNumberFormat.formatIndianNumber(totalDebit);

		double totalCredit = paymentReportcard.stream()
				.mapToDouble(item -> Double.parseDouble(item.getTransactionCreditAmt().replace(",", ""))).sum();
		String totalCreditString = IndianNumberFormat.formatIndianNumber(totalCredit);

		String fromToDate = fromDate + " TO " + toDate;

		Map<String, Object> data = new HashMap<String, Object>();

		data.put("paymentReport", paymentReportcard);
		data.put("fromToDate", fromToDate);
		data.put("orgDivision", orgDivision);
		data.put("totalDebitAmt", totalDebitString);
		data.put("totalCreditAmt", totalCreditString);

		String filename = "payment-voucher-pdf_" + fromDate + "_" + toDate + ".pdf";

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);

		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/account-voucher-pdf", data);
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

		logger.info("Method :paymentVoucherPdf End");

	}

	@SuppressWarnings("unchecked")
	@GetMapping("payment-voucher-getTDSVoucherList")
	public @ResponseBody Object getTdsVoucherList(HttpSession session, @RequestParam String id) {

		logger.info("Method :getTdsVoucherList starts");
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
			resp = restClient.getForObject(env.getAccountUrl() + "/restGetTdsVoucherList?id=" + id + "&orgName="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		// System.out.println("QA-Request-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :getTdsVoucherList ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("payment-voucher-get-invoiceListInfo")
	public @ResponseBody Object getPayInvoiceInfo(HttpSession session, @RequestParam String ledgerid,
			@RequestParam String voucherid) {

		logger.info("Method :getPayInvoiceInfo starts");
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
			resp = restClient.getForObject(env.getAccountUrl() + "/getPayInvoiceInfo?ledgerid=" + ledgerid
					+ "&voucherid=" + voucherid + "&orgName=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method :getPayInvoiceInfo ends" + resp);

		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/payment-voucher-details-pdf" })
	public void paymentVoucherDetailsPDF(HttpServletResponse response, Model model, @RequestParam String id,
			HttpSession session) {
		logger.info("Method :paymentVoucherDetailsPDF starts");

		AccountCreditorLedgerModel product = new AccountCreditorLedgerModel();

		JsonResponse<?> paymentReport = new JsonResponse<>();
		ObjectMapper mapper = new ObjectMapper();

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			paymentReport = restClient.getForObject(env.getAccountUrl() + "PaymentVoucherDetailsPdf?id=" + id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :paymentVoucherDetailsPDF --->" + paymentReport);
		logger.info("Raw JSON: " + paymentReport.getBody());
		List<AccountPaymentVoucherDetailsModel> voucherList = new ArrayList<>();

		try {
			// Step 1: Convert the body string to a JsonNode
			JsonNode root = mapper.readTree((String) paymentReport.getBody());

			// Step 2: Extract viewPaymentVoucher node
			JsonNode viewPaymentVoucher = root.path("viewPaymentVoucher");

			// Step 3: Map to Java object list
			voucherList = mapper.readValue(viewPaymentVoucher.toString(),
					new TypeReference<List<AccountPaymentVoucherDetailsModel>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> data = new HashMap<>();
		data.put("voucher", voucherList.get(0)); // assuming only 1 record for PDF
		logger.info("Method :paymentVoucherDetailsPDF ---->" + voucherList);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + "demo");

		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/paymentVoucher_Details_PDF.html", data);
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

		logger.info("Method :paymentVoucherDetailsPDF End");

	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("payment-voucher-approve-voucher")
	public @ResponseBody Object approveVoucher(HttpSession session, @RequestBody String data) {
		logger.info("Method :approveVoucher starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.postForObject(
					env.getAccountUrl() + "rest-approvePayVoucher?org=" + orgName + "&orgDiv=" + orgDivision + "&userId=" + userId, data,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :approveVoucher ends");
		return resp;
	}
}