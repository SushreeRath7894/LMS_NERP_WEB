package nirmalya.aathithya.webmodule.account.controller;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountCreditorLedgerModel;
import nirmalya.aathithya.webmodule.account.model.AccountPurchaseOrderWebModel;
import nirmalya.aathithya.webmodule.account.model.AccountPurchaseProductWebModel;
import nirmalya.aathithya.webmodule.account.model.PurchaseRegisterExcelModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.QaPcroModel;
import nirmalya.aathithya.webmodule.qa.model.QaRCFTMModel;
import nirmalya.aathithya.webmodule.common.utils.IndianNumberFormat;

/**
 * @author Nirmalya Labs
 *
 */
@Controller
@RequestMapping(value = "account")
public class AccountPurchaseVoucherController {
	Logger logger = LoggerFactory.getLogger(AccountPurchaseVoucherController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	/**
	 * Add Account
	 */

	@GetMapping("/purchase-voucher")
	public String purchaseVoucher(Model model, HttpSession session) {
		logger.info("Method : purchaseVoucher start");

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
					env.getAccountUrl() + "/getTdsLedgerList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> tdsLedgerList = Arrays.asList(tdsLedger);
			model.addAttribute("tdsLedgerList", tdsLedgerList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String type = "Purchase";
		
		try {
			DropDownModel[] item = restClient.getForObject(
					env.getAccountUrl() + "/getProductListDD?org=" + orgName + "&orgDiv=" + orgDivision + "&type=" + type,
					DropDownModel[].class);
			
			List<DropDownModel> itemList = Arrays.asList(item);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		try {
			DropDownModel[] tdsLedger = restClient.getForObject(env.getAccountUrl()
					+ "/getCurrentVoucherTypeList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> voucherTypeList = Arrays.asList(tdsLedger);
			model.addAttribute("voucherTypeList", voucherTypeList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try {
			DropDownModel[] ledger = restClient.getForObject(env.getMasterUrl() + "get-account-ledger-list?org="
					+ orgName + "&orgDiv=" + orgDivision + "&type=" + type, DropDownModel[].class);
			List<DropDownModel> ledgerList = Arrays.asList(ledger);
			model.addAttribute("ledgerList", ledgerList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : purchaseVoucher end");
		return "account/manage-purchase-voucher";
	}

	// JSON View purchase Ajax
	@SuppressWarnings("unchecked")
	@GetMapping("purchase-voucher-view")
	public @ResponseBody Object viewPurchaseOrder(HttpSession session) {

		logger.info("Method :viewPurchaseOrder starts");
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
					env.getAccountUrl() + "rest-viewPurchaseVoucher?org=" + orgName + "&orgDiv=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println("QA-Request-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewPurchaseOrder ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("purchase-voucher-update-item-ledger")
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
			resp = restClient.postForObject(env.getAccountUrl() + "rest-updateItemLedger?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId, data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :updateItemLedger ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("purchase-voucher-add-item-ledger")
	public @ResponseBody Object addItemLedger(HttpSession session, @RequestBody String data) {
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
			resp = restClient.postForObject(env.getAccountUrl() + "rest-addItemLedger?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId, data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :updateItemLedger ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("purchase-voucher-update-narration")
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
			resp = restClient.postForObject(env.getAccountUrl() + "rest-updateNarration?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId, data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :updateNarration ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("purchase-voucher-update-invoice")
	public @ResponseBody Object updatePurchaseInvoice(HttpSession session, @RequestBody String data) {
		logger.info("Method : updatePurchaseInvoice starts");

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
			resp = restClient.postForObject(env.getAccountUrl() + "rest-updateInvoice?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId, data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : updatePurchaseInvoice ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("purchase-voucher-approve-voucher")
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
			resp = restClient.postForObject(env.getAccountUrl() + "rest-approveVoucher?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId, data, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :approveVoucher ends");
		return resp;
	}

	/*
	 * view purchase voucher
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("purchase-voucher-edit")
	public @ResponseBody JsonResponse<Object> viewPurchaseVoucher(HttpSession session, @RequestParam String id,
			String vtype) {
		logger.info("Method : viewPurchaseVoucher starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes", e);
		}

		try {
			resp = restClient.getForObject(env.getAccountUrl() + "viewPurchaseVoucher?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision + "&vtype=" + vtype, JsonResponse.class);
		} catch (RestClientException e) {
			logger.error("Error fetching purchase voucher details", e);
		}
		logger.info("Method : viewPurchaseVoucher ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("payment-history-report-getPaymentList")
	public @ResponseBody Object viewgetPaymentListReport(@RequestParam String ledgerId, String vendorId,
			HttpSession session) {

		logger.info("Method :viewgetPaymentListReport starts");
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
							env.getAccountUrl() + "rest-viewgetPaymentListReport?ledgerId=" + ledgerId + "&vendorId="
									+ vendorId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewgetPaymentListReport endsssssssssssssssss" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("purchase-voucher-view-filteredData")
	public @ResponseBody Object viewPurchaseFilteredData(HttpSession session, @RequestParam String fromDate,
			String toDate, String vtype) {

		logger.info("Method :viewPurchaseFilteredData starts");
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
					env.getAccountUrl() + "rest-viewPurchaseFilteredData?orgName=" + orgName + "&orgDivision="
							+ orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate + "&vtype=" + vtype,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewPurchaseFilteredData ends" + resp);

		return resp;
	}

	@GetMapping("/purchase-register")
	public String purchaseRegister(Model model, HttpSession session) {
		logger.info("Method : purchaseRegister start");

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

		logger.info("Method : purchaseVoucher end");
		return "account/purchase-register";
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/purchase-register-Pdf" })
	public void purchaseRegisterPdf(HttpServletResponse response, Model model, @RequestParam String voucherType,
			@RequestParam String activityType, String fromDate, String toDate, HttpSession session) {
		logger.info("Method :purchaseRegisterPdf starts");

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
		String filename = "purchase-register-pdf_" + fromDate + "_" + toDate + ".pdf";

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

	// Excel.

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/purchase-register-Excel" })
	public ModelAndView purchaseRegisterExcel(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam String voucherType, String fYear, String fromDate, String toDate) {
		logger.info("Method : purchaseRegisterExcel starts");

		/*
		 * byte[] encodeByte1 = Base64.getDecoder().decode(fromDate.getBytes()); String
		 * fromDate1 = (new String(encodeByte1));
		 * 
		 * byte[] encodeByte2 = Base64.getDecoder().decode(toDate.getBytes()); String
		 * toDate1 = (new String(encodeByte2));
		 */

		JsonResponse<Object> resp = new JsonResponse<Object>();
		Map<String, Object> data = new HashMap<String, Object>();
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
		System.out.println(
				"voucherType>>" + voucherType + "fYear>>" + fYear + "fromDate>>" + fromDate + "toDate>>" + toDate);
		try {
			resp = restClient.getForObject(
					env.getAccountUrl() + "accountRegisterExcel?voucherType=" + voucherType + "&fromDate=" + fromDate
							+ "&toDate=" + toDate + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
			System.out.println("resp>>>>>" + resp.getBody());
			List<PurchaseRegisterExcelModel> listModel = new ArrayList<PurchaseRegisterExcelModel>();
			ObjectMapper mapper = new ObjectMapper();

			try {
				listModel = mapper.readValue(resp.getBody().toString(),
						new TypeReference<List<PurchaseRegisterExcelModel>>() {
						});
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("listModel>>>>>>>" + listModel);

			data.put("listModel", listModel);

			response.setContentType("application/ms-excel");
			response.setHeader("Content-disposition",
					"attachment; filename=" + "Purchase Voucher-" + " " + new Date().getTime() + ".xls");

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : purchaseRegisterExcel ends");
		return new ModelAndView(new PurchaseRegisterExcelController(), data);
		// return null;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("purchase-voucher-view-TdsAmount")
	public @ResponseBody Object getTdsAmountOfVendor(HttpSession session, @RequestParam String voucherid,
			String invoiceid, String vtype) {

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
			resp = restClient.getForObject(
					env.getAccountUrl() + "rest-getVendorTdsAmount?orgName=" + orgName + "&orgDivision=" + orgDivision
							+ "&voucherid=" + voucherid + "&invoiceid=" + invoiceid + "&vtype=" + vtype,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getTdsAmountOfVendor ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("purchase-voucher-view-TdsUpdate")
	public @ResponseBody Object tdsAmountEdit(HttpSession session, @RequestParam String tdsAmount, String vendorid,
			String invoiceId, String voucherId, String tdsRate, String finalPayableAmt, String tdsLedgerId,
			String tdsTransactionDate, String tdsNarration, String roundOffAmount, String tdsSection, String tdsInvList) {

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
			resp = restClient.getForObject(env.getAccountUrl() + "rest-tdsAmountEdit?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&tdsAmount=" + tdsAmount + "&vendorid=" + vendorid + "&tdsRate="
					+ tdsRate + "&invoiceId=" + invoiceId + "&voucherId=" + voucherId + "&userId=" + userId
					+ "&finalPayableAmt=" + finalPayableAmt + "&tdsLedgerId=" + tdsLedgerId + "&tdsTransactionDate="
					+ tdsTransactionDate + "&tdsNarration=" + tdsNarration + "&roundOffAmount=" + roundOffAmount +
					"&tdsSection="+ tdsSection +"&tdsInvList=" + tdsInvList,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Response-->" + resp);
		logger.info("Method :tdsAmountEdit ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "purchase-voucher-get-current-voucher" })
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
			res = restClient.getForObject(env.getAccountUrl() + "getcurrentvoucher?id=" + id + "&orgName=" + orgName
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
		logger.info("Method : getcurrentvoucher ends");

		return res;
	}

	@SuppressWarnings("unchecked")

	@GetMapping(value = { "purchase-voucher-get-voucherClass-List" })
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
	@GetMapping(value = { "purchase-voucher-get-ledger-voucher" })
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
	@GetMapping(value = { "purchase-voucher-get-tds-section" })
	public @ResponseBody JsonResponse<Object> getTdsSection(HttpSession session, @RequestParam String id, @RequestParam String type) {
		logger.info("Method : getTdsSection starts");
		
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
			res = restClient.getForObject(env.getAccountUrl() + "getTdsSection?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getTdsSection ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("purchase-voucher-save-voucher-details")
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
			resp = restClient.getForObject(env.getAccountUrl() + "rest-saveVoucherDetails?orgName=" + orgName
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
