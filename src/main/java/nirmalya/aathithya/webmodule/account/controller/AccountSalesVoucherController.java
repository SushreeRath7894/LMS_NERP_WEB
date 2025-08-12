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

import nirmalya.aathithya.webmodule.account.model.AccountCreditorLedgerModel;
import nirmalya.aathithya.webmodule.account.model.AccountPurchaseOrderWebModel;
import nirmalya.aathithya.webmodule.account.model.AccountPurchaseProductWebModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.common.utils.IndianNumberFormat;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
@RequestMapping(value = "account")
public class AccountSalesVoucherController {
	Logger logger = LoggerFactory.getLogger(AccountSalesVoucherController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	/**
	 * Add Account
	 */

	@GetMapping("/sales-voucher")
	public String salesVoucher(Model model, HttpSession session) {
		logger.info("Method : salesVoucher start");

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

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] tdsLedger = restClient.getForObject(
					env.getAccountUrl() + "/getTdsLedgerListCustmer?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> tdsLedgerList = Arrays.asList(tdsLedger);
			model.addAttribute("tdsLedgerList", tdsLedgerList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] tdsLedger = restClient.getForObject(env.getAccountUrl()
					+ "/getCurrentVoucherTypeListForSale?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> voucherTypeList = Arrays.asList(tdsLedger);
			model.addAttribute("voucherTypeList", voucherTypeList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		String type = "Sale";
		try {
			DropDownModel[] ledger = restClient.getForObject(env.getMasterUrl() + "get-account-ledger-list?org="
					+ orgName + "&orgDiv=" + orgDivision + "&type=" + type, DropDownModel[].class);
			List<DropDownModel> ledgerList = Arrays.asList(ledger);
			model.addAttribute("ledgerList", ledgerList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : salesVoucher end");
		return "account/manage-sales-voucher";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("sales-voucher-update-item-ledger")
	public @ResponseBody Object updateItemLedger(HttpSession session, @RequestBody String data) {
		logger.info("Method :updateItemLedger starts");

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
					env.getAccountUrl() + "rest-salesUpdateItemLedger?org=" + orgName + "&orgDiv=" + orgDivision + "&userId=" + userId, data,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :updateItemLedger ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("sales-voucher-update-narration")
	public @ResponseBody Object updateNarration(HttpSession session, @RequestBody String data) {
		logger.info("Method :updateNarration starts");
		
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
					env.getAccountUrl() + "rest-updateNarrationSales?org=" + orgName + "&orgDiv=" + orgDivision + "&userId=" + userId, data,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :updateNarration ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("sales-voucher-approve-voucher")
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
					env.getAccountUrl() + "rest-approveVoucherSales?org=" + orgName + "&orgDiv=" + orgDivision + "&userId=" + userId, data,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :approveVoucher ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("sales-voucher-view")
	public @ResponseBody Object viewSalesOrder(HttpSession session) {

		logger.info("Method :viewSalesOrder starts");
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
					env.getAccountUrl() + "rest-viewSalesVoucher?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("QA-Request-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewSalesOrder ends" + resp);

		return resp;
	}

	/*
	 * view sales-voucher-edit
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("sales-voucher-edit")
	public @ResponseBody JsonResponse<List<AccountPurchaseOrderWebModel>> viewEditSalesVoucher(Model model,
			@RequestParam String id, HttpSession session) {
		logger.info("Method : viewEditSalesVoucher starts" + id);
		JsonResponse<List<AccountPurchaseOrderWebModel>> jsonResponse = new JsonResponse<List<AccountPurchaseOrderWebModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
		}
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewEditSalesVoucher?id=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountPurchaseOrderWebModel> accountModel = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountPurchaseOrderWebModel>>() {
				});

		if (accountModel.get(0).getProductList().size() > 0) {
			int c = 0;
			for (AccountPurchaseProductWebModel a : accountModel.get(0).getProductList()) {
				c = c + 1;
				a.setSlNo(c);
			}
		}

		System.out.println("###" + accountModel);
		jsonResponse.setBody(accountModel);

		System.out.println("REsp" + jsonResponse);
		logger.info("Method : viewEditSalesVoucher ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("sales-voucher-view-filteredData")
	public @ResponseBody Object viewSalesFilteredData(HttpSession session, @RequestParam String fromDate,
			String toDate) {

		logger.info("Method :viewSalesFilteredData starts");
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
			resp = restClient.getForObject(env.getAccountUrl() + "rest-viewSalesFilteredData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewSalesFilteredData ends" + resp);

		return resp;
	}

	@GetMapping("/sales-register")
	public String salesRegister(Model model, HttpSession session) {
		logger.info("Method : salesRegister start");

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

		logger.info("Method : salesRegister end");
		return "account/sales-register";
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/sales-register-Pdf" })
	public void salesRegisterPdf(HttpServletResponse response, Model model, @RequestParam String voucherType,
			@RequestParam String activityType, String fromDate, String toDate, HttpSession session) {
		logger.info("Method :salesRegisterPdf starts");

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
			report = restClient.getForObject(
					env.getAccountUrl() + "accountRegisterPdf?voucherType=" + voucherType + "&fromDate=" + fromDate
							+ "&toDate=" + toDate + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		data.put("voucherType", activityType);
		data.put("orgDivision", orgDivision);
		data.put("totalDebitAmt", totalDebitString);
		data.put("totalCreditAmt", totalCreditString);
		String filename = "sales-register-pdf_" + fromDate + "_" + toDate + ".pdf";

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

	@SuppressWarnings("unchecked")
	@GetMapping("sales-voucher-view-TdsAmount")
	public @ResponseBody Object getTdsAmountOfCustmer(HttpSession session, @RequestParam String voucherid,
			String invoiceid) {

		logger.info("Method :getTdsAmountOfVendor starts");
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
			resp = restClient
					.getForObject(
							env.getAccountUrl() + "rest-getCustmerTdsAmount?orgName=" + orgName + "&orgDivision="
									+ orgDivision + "&voucherid=" + voucherid + "&invoiceid=" + invoiceid,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getTdsAmountOfVendor ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("sale-voucher-view-TdsUpdate")
	public @ResponseBody Object tdsAmountEdit(HttpSession session, @RequestParam String tdsAmount, String vendorid,
			String invoiceId, String voucherId, String tdsRate, String finalPayableAmt, String tdsLedgerId,
			String tdsTransactionDate, String tdsNarration, String roundOffAmount) {

		logger.info("Method :tdsAmountEdit starts");
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
			resp = restClient.getForObject(
					env.getAccountUrl() + "rest-tdsAmountEditCustomer?orgName=" + orgName + "&orgDivision="
							+ orgDivision + "&tdsAmount=" + tdsAmount + "&vendorid=" + vendorid + "&tdsRate=" + tdsRate
							+ "&invoiceId=" + invoiceId + "&voucherId=" + voucherId + "&userId=" + userId
							+ "&finalPayableAmt=" + finalPayableAmt + "&tdsLedgerId=" + tdsLedgerId
							+ "&tdsTransactionDate=" + tdsTransactionDate + "&tdsNarration=" + tdsNarration + "&roundOffAmount=" + roundOffAmount,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Response-->" + resp);
		logger.info("Method :tdsAmountEdit ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "sale-voucher-get-current-voucher" })
	public @ResponseBody JsonResponse<Object> getcurrentvoucher(HttpSession session, @RequestParam String id) {
		JsonResponse<Object> res = new JsonResponse<Object>();
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
			res = restClient.getForObject(env.getAccountUrl() + "getcurrentvoucherForSale?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getcurrentvoucher ends");

		return res;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "sale-voucher-get-voucherClass-List" })
	public @ResponseBody JsonResponse<Object> getvoucherClassList(HttpSession session, @RequestParam String id) {
		logger.info("Method : getvoucherClassList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
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
			res = restClient.getForObject(env.getAccountUrl() + "getvoucherClassList?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}

		logger.info("Method : getvoucherClassList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "sale-voucher-get-ledger-voucher" })
	public @ResponseBody JsonResponse<Object> getLedgervoucher(HttpSession session, @RequestParam String id) {
		JsonResponse<Object> res = new JsonResponse<Object>();
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
			res = restClient.getForObject(env.getAccountUrl() + "getLedgervoucher?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getLedgervoucher ends");

		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("sales-voucher-save-voucher-details")
	public @ResponseBody Object saveVoucherDetails(HttpSession session, @RequestParam String voucherTypeId,
			String voucherClassId, String ledgerName, String ledgerId, String pVoucherId, String invoiceId) {

		logger.info("Method :saveVoucherDetails starts");
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
			resp = restClient.getForObject(env.getAccountUrl() + "rest-saveVoucherDetailsforSale?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&voucherTypeId=" + voucherTypeId + "&voucherClassId="
					+ voucherClassId + "&ledgerName=" + ledgerName + "&ledgerId=" + ledgerId + "&pVoucherId="
					+ pVoucherId + "&invoiceId=" + invoiceId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Response-->" + resp);
		logger.info("Method :saveVoucherDetails ends");
		return resp;
	}
}
