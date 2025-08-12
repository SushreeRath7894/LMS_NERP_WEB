package nirmalya.aathithya.webmodule.account.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountCreditorLedgerModel;
import nirmalya.aathithya.webmodule.account.model.AccountLedgerReportWebModel;
import nirmalya.aathithya.webmodule.account.model.AccountTrailBalanceModel;
import nirmalya.aathithya.webmodule.account.model.DataSetAccountTree;
import nirmalya.aathithya.webmodule.account.model.ManageLeadgerModel;
import nirmalya.aathithya.webmodule.account.model.WebAccountReportModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.common.utils.IndianNumberFormat;

@Controller
@RequestMapping(value = "account")
public class AccountReportsWebController {
	Logger logger = LoggerFactory.getLogger(AccountReportsWebController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	// view balance sheet page

	@GetMapping("balance-sheet")
	public String balanceSheet(Model model, HttpSession session) {
		logger.info("Method : balanceSheet starts");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : balanceSheet end");
		return "account/manageBalanceSheet";
	}

	// view profit and loss page
	@GetMapping("profile-and-loss")
	public String profileAndLoss(Model model, HttpSession session) {
		logger.info("Method : profileAndLoss starts");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : profileAndLoss end");
		return "account/profit-loss";
	}

	// view trail-balance page
	@GetMapping("trial-balance")
	public String trailBalance(Model model, HttpSession session) {
		logger.info("Method : trailBalance starts");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : trailBalance end");
		return "account/trial-balance";
	}

	// view day-book page
	@GetMapping("day-book")
	public String dayBook(Model model, HttpSession session) {
		logger.info("Method : dayBook starts");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : dayBook end");
		return "account/day-book";
	}

	// view cash-flow page
	@GetMapping("cash-flow")
	public String cashFlow(Model model, HttpSession session) {
		logger.info("Method : cashFlow starts");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : cashFlow end");
		return "account/cash-flow";
	}

	// view fund-flow page
	@GetMapping("fund-flow")
	public String fundFlow(Model model, HttpSession session) {
		logger.info("Method : fundFlow starts");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : fundFlow end");
		return "account/fund-flow";
	}

	// view account-statement page
	@GetMapping("account-statement")
	public String accountStatement(Model model, HttpSession session) {
		logger.info("Method : accountStatement starts");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : accountStatement end");
		return "account/account-statement";
	}

	// view manage-ledger page
	@GetMapping("ledger-voucher")
	public String ledgerVoucher(Model model, HttpSession session) {
		logger.info("Method : ledgerVoucher starts");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : ledgerVoucher end");
		return "account/ledger-voucher";
	}

	// view manage-ledger page
	@GetMapping("payment-planning")
	public String paymentPlanningReport(Model model, HttpSession session) {
		logger.info("Method : paymentPlanningReport starts");
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);
			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : paymentPlanningReport end");
		return "account/payment-planning";
	}

	// ledger voucher report

	@SuppressWarnings("unchecked")
	@GetMapping("ledger-voucher-details")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> viewLedgerVoucherReport(Model model,
			@RequestParam String id, HttpSession session) {
		logger.info("Method : viewLedgerVoucherReport starts" + id);
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(
					env.getAccountUrl() + "viewLedgerVoucherReport?id=" + id + "&orgName=" + orgName + "&orgDivision="
							+ orgDivision,

					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :viewLedgerVoucherReport ends");
		return jsonResponse;
	}

	// day book report

	@SuppressWarnings("unchecked")
	@GetMapping("day-book-report")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> dayBookReport(Model model, HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : dayBookReport starts");
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(
					env.getAccountUrl() + "viewDayBookReport?fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :dayBookReport ends");
		return jsonResponse;
	}

	// cash-flow-details
	@SuppressWarnings("unchecked")
	@GetMapping("cash-flow-details")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> cashFlowReport(Model model,
			HttpSession session) {
		logger.info("Method : cashFlowReport starts");
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewCashFlowReport", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :cashFlowReport ends");
		return jsonResponse;
	}

	// monthly summary details
	// view monthly account statement

	@SuppressWarnings("unchecked")
	@GetMapping("account-statement-mothlySummary")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> viewLedgerMonthlySummary(Model model,
			@RequestParam String id, HttpSession session) {
		logger.info("Method : viewLedgerMonthlySummary starts" + id);
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewLedgerMonthlySummary?id=" + id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :viewLedgerMonthlySummary ends");
		return jsonResponse;
	}

	// view monthly account statement

	@SuppressWarnings("unchecked")
	@GetMapping("account-statement-mothlyDetails")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> viewMothlyDetails(Model model,
			@RequestParam String month, @RequestParam String ledgerId, HttpSession session) {
		logger.info("Method : viewMothlyDetails starts" + ledgerId);
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			System.out.println("month====>>>" + month);
			System.out.println("ledgerId====>>>" + ledgerId);

			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewMothlyDetails?month=" + month
					+ "&ledgerId=" + ledgerId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :viewMothlyDetails ends");
		return jsonResponse;
	}

	// trial balance report

	@SuppressWarnings("unchecked")
	@GetMapping("trial-balance-report")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> trialBalance(Model model, HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : trialBalance starts");
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(
					env.getAccountUrl() + "trialBalanceReport?fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :trialBalance ends");
		return jsonResponse;
	}

	// profile-and-loss-data

	@SuppressWarnings("unchecked")

	@GetMapping("profile-and-loss-data")
	public @ResponseBody JsonResponse<List<DataSetAccountTree>> profitLossData(Model model, HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : profitLossData starts");
		JsonResponse<List<DataSetAccountTree>> jsonResponse = new JsonResponse<List<DataSetAccountTree>>();
		try {
			jsonResponse = restClient.getForObject(
					env.getAccountUrl() + "profitLossReport?fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<DataSetAccountTree> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<DataSetAccountTree>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :profitLossData ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("ledger-voucher-details-wrtVoucherType")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> viewLedgerReportWrtVoucherType(Model model,
			@RequestParam String id, String voucherType, HttpSession session) {
		logger.info("Method : viewLedgerReportWrtVoucherType starts" + id);
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(
					env.getAccountUrl() + "viewLedgerReportWrtVoucherType?id=" + id + "&voucherType=" + voucherType,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		// System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method :viewLedgerReportWrtVoucherType ends----" + jsonResponse);
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("ledger-voucher-details-wrtDate")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> viewLedgerReportWrtDate(Model model,
			@RequestParam String id, String voucherType, String fromDate, String toDate, HttpSession session) {
		logger.info("Method : viewLedgerReportWrtDate starts" + id);
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "viewLedgerReportWrtDate?id=" + id
					+ "&voucherType=" + voucherType + "&fromDate=" + fromDate + "&toDate=" + toDate + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		// System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method :viewLedgerReportWrtDate ends----" + jsonResponse);
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("day-book-report-getVoucher")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> dayBookReportVoucher(Model model,
			HttpSession session, @RequestParam String fromDate, String toDate, String voucherType) {
		logger.info("Method : dayBookReportVoucher starts");
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "dayBookReportVoucher?fromDate=" + fromDate
					+ "&toDate=" + toDate + "&voucherType=" + voucherType, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		// System.out.println("REsp" + jsonResponse);
		logger.info("Method :dayBookReportVoucher ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dashboard-profitLossSheetAct")
	public @ResponseBody Object profitLossSheetAct(HttpSession session) {

		logger.info("Method :profitLossSheetAct starts");
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
					env.getAccountUrl() + "profitLossSheetAct?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :profitLossSheetAct ends" + resp);

		return resp;
	}

	// Payment Planning Main List
	@SuppressWarnings("unchecked")
	@GetMapping("payment-planning-throughAjax")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> viewPaymentPlanning(Model model,
			HttpSession session) {
		logger.info("Method : viewPaymentPlanning starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(
					env.getAccountUrl() + "/restViewPaymentPlanning?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method :viewPaymentPlanning ends");
		return jsonResponse;
	}

	// invoice lists by vendor
	@SuppressWarnings("unchecked")
	@GetMapping("payment-planning-getInvoiceList")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> getInvoiceListByLedger(Model model,
			@RequestParam String ledgerId, String vendorId, HttpSession session) {
		logger.info("Method : getInvoiceListByLedger starts==" + ledgerId + "===" + vendorId);
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		try {
			jsonResponse = restClient.getForObject(
					env.getAccountUrl() + "getInvoiceListByLedger?ledgerId=" + ledgerId + "&vendorId=" + vendorId,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :getInvoiceListByLedger ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "balance-sheet-getChild" })
	public @ResponseBody JsonResponse<Object> getChildList(HttpSession session, @RequestParam String parentid) {
		logger.info("Method : getChildList starts" + parentid);
		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		logger.info("orgname" + orgName);
		logger.info("division" + orgDiv);

		try {
			res = restClient.getForObject(
					env.getAccountUrl() + "getChildList?id=" + parentid + "&orgName=" + orgName + "&orgDiv=" + orgDiv,
					JsonResponse.class);
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
		logger.info("Method : getChildList ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "balance-sheet-getSubchild" })
	public @ResponseBody JsonResponse<Object> getSubChild(HttpSession session, @RequestParam String parentid) {
		logger.info("Method : getSubChild starts" + parentid);
		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restClient.getForObject(
					env.getAccountUrl() + "getChildList?id=" + parentid + "&orgName=" + orgName + "&orgDiv=" + orgDiv,
					JsonResponse.class);
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
		logger.info("Method : getSubChild ends");
		return res;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping(value = { "balance-sheet-getparentAmount" }) public @ResponseBody
	 * JsonResponse<Object> getParentAmount() {
	 * logger.info("Method : getParentAmount starts"); JsonResponse<Object> res =
	 * new JsonResponse<Object>(); try { res =
	 * restClient.getForObject(env.getAccountUrl() + "getParentAmount",
	 * JsonResponse.class); } catch (Exception e) { e.printStackTrace(); } if
	 * (res.getMessage() != null) { res.setCode(res.getMessage());
	 * res.setMessage("Unsuccess"); } else { res.setMessage("success"); }
	 * System.out.println("state" + res);
	 * logger.info("Method : getParentAmount ends"); return res; }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("balance-sheet-getparentAmount")
	public @ResponseBody Object getParentAmount(HttpSession session) {
		logger.info("Method :getParentAmount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		try {

			resp = restClient.getForObject(
					env.getAccountUrl() + "getParentAmount?orgName=" + orgName + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getParentAmount ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("profile-and-loss-reportData")
	public @ResponseBody Object profitLosssAccountReport(HttpSession session) {
		logger.info("Method :profitLosssAccountReport starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restClient.getForObject(
					env.getAccountUrl() + "Rest-profile-and-loss-reportData?orgName=" + orgName + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :profitLosssAccountReport ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "trial-balance-report-getChild" })
	public @ResponseBody JsonResponse<Object> trailBalanceChild(@RequestParam String parentid, HttpSession session,
			@RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : trailBalanceChild starts" + parentid);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {
			res = restClient
					.getForObject(
							env.getAccountUrl() + "trailBalanceChild?id=" + parentid + "&orgName=" + orgName
									+ "&orgDiv=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate,
							JsonResponse.class);
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
		logger.info("Method : trailBalanceChild ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "trial-balance-report-getSubChild" })
	public @ResponseBody JsonResponse<Object> trailBalancegetSubChild(@RequestParam String parentid,
			HttpSession session, @RequestParam String fromDate, @RequestParam String toDate) {
		logger.info("Method : trailBalancegetSubChild starts" + parentid);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		try {
			res = restClient
					.getForObject(
							env.getAccountUrl() + "trailBalanceChild?id=" + parentid + "&orgName=" + orgName
									+ "&orgDiv=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate,
							JsonResponse.class);
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
		logger.info("Method : trailBalancegetSubChild ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("trial-balance-report-getAmountData")
	public @ResponseBody Object getTrailBalAmount(HttpSession session) {
		logger.info("Method :getTrailBalAmount starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {

			resp = restClient.getForObject(env.getAccountUrl() + "getTrailBalAmount", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getTrailBalAmount ends" + resp);

		return resp;
	}

	@SuppressWarnings("unused")
	@GetMapping(value = { "payment-planning-duePayment-pdf" })
	public void paymentRegisterPdf(HttpServletResponse response, Model model, @RequestParam String selectedRowsData,
			@RequestParam String ledgerName, HttpSession session)
			throws JsonParseException, JsonMappingException, IOException {
		logger.info("Method :paymentRegisterPdf starts");

		String decodedSelectedRowsData = URLDecoder.decode(selectedRowsData, "UTF-8");
		String decodedLedgerName = URLDecoder.decode(ledgerName, "UTF-8");
		List<AccountLedgerReportWebModel> dataList = new ObjectMapper().readValue(decodedSelectedRowsData,
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		JsonResponse<List<AccountLedgerReportWebModel>> report = new JsonResponse<List<AccountLedgerReportWebModel>>();

		report.setBody(dataList);

		ObjectMapper mapper = new ObjectMapper();

		List<AccountLedgerReportWebModel> reportcard = mapper.convertValue(report.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});

		double totalAmt = reportcard.stream()
				.mapToDouble(item -> Double.parseDouble(item.getTotalAmt().replace(",", ""))).sum();
		String totalAmtString = IndianNumberFormat.formatIndianNumber(totalAmt);

		double paidAmt = reportcard.stream().mapToDouble(item -> Double.parseDouble(item.getPaidAmt().replace(",", "")))
				.sum();
		String paidAmtString = IndianNumberFormat.formatIndianNumber(paidAmt);

		double remainAmt = reportcard.stream()
				.mapToDouble(item -> Double.parseDouble(item.getRemainAmt().replace(",", ""))).sum();
		String remainAmtString = IndianNumberFormat.formatIndianNumber(remainAmt);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("reportcard", reportcard);
		data.put("ledgerName", ledgerName);
		data.put("totalAmtString", totalAmtString);
		data.put("paidAmtString", paidAmtString);
		data.put("remainAmtString", remainAmtString);
		String filename = "Due-Payment-pdf" + ledgerName + ".pdf";

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/due-payment-pdf", data);
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
	@GetMapping("balance-sheet-dynamicparentlist")
	public @ResponseBody Object dynamicparentlist(HttpSession session) {
		logger.info("Method :dynamicparentlist starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		try {

			resp = restClient.getForObject(
					env.getAccountUrl() + "getDynamicparentlist?orgName=" + orgName + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :dynamicparentlist ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("profile-and-loss-dynamicparentlist")
	public @ResponseBody Object profitlossParentList(HttpSession session) {
		logger.info("Method :profitlossParentList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restClient.getForObject(
					env.getAccountUrl() + "getProfitLossParentList?orgName=" + orgName + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :profitlossParentList ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("trial-balance-report-parentlist")
	public @ResponseBody Object trialbalancelist(HttpSession session, @RequestParam String fromDate,
			@RequestParam String toDate) {
		logger.info("Method :trialbalance parent list  starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDiv = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		try {

			resp = restClient.getForObject(env.getAccountUrl() + "getTrialBalanceParentList?orgName=" + orgName
					+ "&orgDiv=" + orgDiv + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :trialbalance parent list ends" + resp);

		return resp;
	}

	/* Method For combine the two json data start */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @GetMapping("trial-balance-report-parentlist") public @ResponseBody
	 * JsonResponse<Map<String, Object>> trialbalancelist(HttpSession
	 * session, @RequestParam String fromDate, @RequestParam String toDate) {
	 * logger.info("Method : trialbalance parent list starts");
	 * 
	 * JsonResponse<Map<String, Object>> resp = new JsonResponse<>(); Map<String,
	 * Object> combinedResult = new HashMap<>(); String orgName = ""; String orgDiv
	 * = "";
	 * 
	 * try { orgName = (String) session.getAttribute("ORGANIZATION"); orgDiv =
	 * (String) session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e)
	 * { e.printStackTrace(); }
	 * 
	 * try { JsonResponse<Object[]> jsonResp1 =
	 * restClient.getForObject(env.getAccountUrl() +
	 * "getTrialBalanceParentList?orgName=" + orgName + "&orgDiv=" + orgDiv +
	 * "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
	 * List<Map<String, Object>> result1 = convertToMapList(jsonResp1.getBody());
	 * 
	 * JsonResponse<Object[]> jsonResp2 =
	 * restClient.getForObject(env.getAccountUrl() +
	 * "getTrialBalanceParentListForParent?orgName=" + orgName + "&orgDiv=" + orgDiv
	 * + "&fromDate=" + fromDate + "&toDate=" + toDate, JsonResponse.class);
	 * List<Map<String, Object>> result2 = convertToMapList(jsonResp2.getBody());
	 * 
	 * List<Map<String, Object>> combinedJson = combineJson(result1, result2);
	 * 
	 * combinedResult.put("combinedData", combinedJson);
	 * resp.setBody(combinedResult); resp.setCode("success");
	 * resp.setMessage("Data fetched successfully");
	 * 
	 * } catch (Exception e) { resp.setCode("failed");
	 * resp.setMessage(e.getMessage()); e.printStackTrace(); }
	 * 
	 * logger.info("Method : trialbalance parent list ends");
	 * 
	 * return resp; }
	 * 
	 * // convert the object[] to list<map<string,object>>
	 * 
	 * @SuppressWarnings("unchecked") private List<Map<String, Object>>
	 * convertToMapList(Object[] objectArray) { List<Map<String, Object>> mapList =
	 * new ArrayList<>(); for (Object obj : objectArray) { if (obj instanceof Map) {
	 * mapList.add((Map<String, Object>) obj); } else {
	 * System.err.println("Unexpected object type: " + obj.getClass().getName()); }
	 * } return mapList; }
	 * 
	 * 
	 * 
	 * private List<Map<String, Object>> combineJson(List<Map<String, Object>>
	 * result1, List<Map<String, Object>> result2) { Map<String, Map<String,
	 * Object>> combinedMap = new HashMap<>();
	 * 
	 * // Iterate through the first result for (Map<String, Object> entry : result1)
	 * { String accountGroup = (String) entry.get("TAGM_AccountGroup");
	 * combinedMap.put(accountGroup, new HashMap<>(entry)); }
	 * 
	 * for (Map<String, Object> entry : result2) { String accountGroup = (String)
	 * entry.get("TAGM_AccountGroup");
	 * 
	 * if (combinedMap.containsKey(accountGroup)) { Map<String, Object>
	 * existingEntry = combinedMap.get(accountGroup); double debitAmount = (Double)
	 * existingEntry.get("DebitAmount") + (Double) entry.get("DebitAmount"); double
	 * creditAmount = (Double) existingEntry.get("CreditAmount") + (Double)
	 * entry.get("CreditAmount");
	 * 
	 * existingEntry.put("DebitAmount", debitAmount);
	 * existingEntry.put("CreditAmount", creditAmount); } else {
	 * combinedMap.put(accountGroup, new HashMap<>(entry)); } }
	 * 
	 * return new ArrayList<>(combinedMap.values()); }
	 */

	/* method for combine the two json data ends */

	/*
	 * @GetMapping(value = { "payment-planning-proposeAmt-pdf" }) public void
	 * paymentplanningPdf(HttpServletResponse response, Model model, @RequestParam
	 * String selectedRowsData, HttpSession session) throws JsonParseException,
	 * JsonMappingException, IOException {
	 * logger.info("Method :paymentplanningPdf starts");
	 * 
	 * String decodedSelectedRowsData = URLDecoder.decode(selectedRowsData,
	 * "UTF-8"); List<AccountLedgerReportWebModel> dataList = new
	 * ObjectMapper().readValue(decodedSelectedRowsData, new
	 * TypeReference<List<AccountLedgerReportWebModel>>() {});
	 * JsonResponse<List<AccountLedgerReportWebModel>> report = new
	 * JsonResponse<List<AccountLedgerReportWebModel>>();
	 * logger.info("Method :paymentplanningPdf starts" + decodedSelectedRowsData);
	 * report.setBody(dataList);
	 * 
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * List<AccountLedgerReportWebModel> reportcard =
	 * mapper.convertValue(report.getBody(), new
	 * TypeReference<List<AccountLedgerReportWebModel>>() { });
	 * 
	 * double totalAmt = reportcard.stream() .filter(item ->
	 * item.getOutstandingAmt() != null) .mapToDouble(item ->
	 * Double.parseDouble(item.getOutstandingAmt())) .sum(); String totalAmtString =
	 * String.format("%.2f", totalAmt);
	 * 
	 * double paidAmt = reportcard.stream() .filter(item -> item.getPayableAmt() !=
	 * null) .mapToDouble(item -> Double.parseDouble(item.getPayableAmt())) .sum();
	 * String paidAmtString = String.format("%.2f", paidAmt);
	 * 
	 * double remainAmt = reportcard.stream() .filter(item -> item.getProposeAmt()
	 * != null) .mapToDouble(item -> Double.parseDouble(item.getProposeAmt()))
	 * .sum(); String proposeAmount = String.format("%.2f", remainAmt);
	 * 
	 * Map<String, Object> data = new HashMap<String, Object>();
	 * data.put("reportcard", reportcard); data.put("totalAmtString",
	 * totalAmtString); data.put("paidAmtString", paidAmtString);
	 * data.put("proposeAmount", proposeAmount); String filename =
	 * "Payment Planning "+ ".pdf";
	 * 
	 * response.setContentType("application/pdf");
	 * response.setHeader("Content-disposition", "inline; filename=" + filename);
	 * File file; byte[] fileData = null; try { file =
	 * pdfGeneratorUtil.createPdf("account/payment-planning-pdf.html", data);
	 * InputStream in = new FileInputStream(file); fileData =
	 * IOUtils.toByteArray(in); response.setContentLength(fileData.length);
	 * response.getOutputStream().write(fileData);
	 * response.getOutputStream().flush(); } catch (IOException e) {
	 * e.printStackTrace(); } catch (Exception e1) { e1.printStackTrace(); }
	 * 
	 * }
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("balance-sheet-getLedgerList")
	public @ResponseBody Object getLedgerListBSheet(@RequestParam String groupId, HttpSession session) {

		logger.info("Method :getLedgerListBSheet starts");
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

			resp = restClient.getForObject(env.getAccountUrl() + "rest-getLedgerListBSheet?groupId=" + groupId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getLedgerListBSheet ends==" + resp);
		return resp;

	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @PostMapping(value = { "payment-planning-proposeAmt-pdf" })
	 * public @ResponseBody ResponseEntity<byte[]> paymentPlanningPdf(@RequestBody
	 * List<AccountLedgerReportWebModel> dataList, HttpSession session) {
	 * logger.info("Method : paymentPlanningPdf starts");
	 * 
	 * ObjectMapper objectMapper = new ObjectMapper();
	 * JsonResponse<List<AccountLedgerReportWebModel>> report = new
	 * JsonResponse<>(); report.setBody(dataList);
	 * 
	 * List<AccountLedgerReportWebModel> reportcard =
	 * objectMapper.convertValue(report.getBody(), new
	 * TypeReference<List<AccountLedgerReportWebModel>>() {});
	 * 
	 * logger.info("DataList--->" + dataList); double totalAmt = reportcard.stream()
	 * .filter(item -> item.getOutstandingAmt() != null) .mapToDouble(item ->
	 * Double.parseDouble(item.getOutstandingAmt().replace(",", ""))) .sum(); String
	 * totalAmtString = IndianNumberFormat.formatIndianNumber(totalAmt);
	 * 
	 * double paidAmt = reportcard.stream() .filter(item -> item.getPayableAmt() !=
	 * null) .mapToDouble(item ->
	 * Double.parseDouble(item.getPayableAmt().replace(",", ""))) .sum(); String
	 * paidAmtString = IndianNumberFormat.formatIndianNumber(paidAmt);
	 * 
	 * double remainAmt = reportcard.stream() .filter(item -> item.getProposeAmt()
	 * != null) .mapToDouble(item ->
	 * Double.parseDouble(item.getProposeAmt().replace(",", ""))) .sum(); String
	 * proposeAmount = IndianNumberFormat.formatIndianNumber(remainAmt);
	 * 
	 * Map<String, Object> data = new HashMap<>(); data.put("reportcard",
	 * reportcard); data.put("totalAmtString", totalAmtString);
	 * data.put("paidAmtString", paidAmtString); data.put("proposeAmount",
	 * proposeAmount);
	 * 
	 * byte[] fileData = null; try { File file =
	 * pdfGeneratorUtil.createPdf("account/payment-planning-pdf.html", data);
	 * InputStream in = new FileInputStream(file); fileData =
	 * IOUtils.toByteArray(in); } catch ( Exception e) {
	 * logger.error("Error while generating PDF", e); return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); }
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * //headers.setContentType(MediaType.APPLICATION_PDF);
	 * headers.setContentDispositionFormData("inline", "Payment Planning.pdf");
	 * headers.setContentLength(fileData.length);
	 * 
	 * return new ResponseEntity<>(fileData, headers, HttpStatus.OK); }
	 */

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/payment-planning-proposeAmt-pdf" })
	public void paymentPlanningPdf(HttpServletResponse response, Model model, @RequestParam String selectedRowsData,
			HttpSession session) throws UnsupportedEncodingException {
		logger.info("Method :paymentPlanningPdf starts");

		AccountLedgerReportWebModel product = new AccountLedgerReportWebModel();
		JsonResponse<AccountLedgerReportWebModel> jsonResponse = new JsonResponse<AccountLedgerReportWebModel>();

		JsonResponse<List<AccountLedgerReportWebModel>> report = new JsonResponse<List<AccountLedgerReportWebModel>>();

		String orgName = "";
		String orgDivision = "";
		String decodedSelectedRowsData = URLDecoder.decode(selectedRowsData, "UTF-8");
		logger.info("Method :Selected Rows Data -> " + decodedSelectedRowsData);
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String encodedSelectedRowsData = URLEncoder.encode(decodedSelectedRowsData, "UTF-8");
			report = restClient.getForObject(env.getAccountUrl() + "paymentPlaningPdfDetails?rowData="
					+ encodedSelectedRowsData + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("responseReport=>>>>>>" + report);

		ObjectMapper mapper = new ObjectMapper();

		List<AccountLedgerReportWebModel> reportcard = mapper.convertValue(report.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});

		jsonResponse.setBody(product);
		double totalAmt = reportcard.stream().filter(item -> item.getOutstandingAmt() != null)
				.mapToDouble(item -> Double.parseDouble(item.getOutstandingAmt().replace(",", ""))).sum();
		String totalAmtString = IndianNumberFormat.formatIndianNumber(totalAmt);

		double paidAmt = reportcard.stream().filter(item -> item.getPayableAmt() != null)
				.mapToDouble(item -> Double.parseDouble(item.getPayableAmt().replace(",", ""))).sum();
		String paidAmtString = IndianNumberFormat.formatIndianNumber(paidAmt);

		double remainAmt = reportcard.stream().filter(item -> item.getProposeAmt() != null)
				.mapToDouble(item -> Double.parseDouble(item.getProposeAmt().replace(",", ""))).sum();
		String proposeAmount = IndianNumberFormat.formatIndianNumber(remainAmt);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("reportcard", reportcard);
		data.put("totalAmtString", totalAmtString);
		data.put("paidAmtString", paidAmtString);
		data.put("proposeAmount", proposeAmount);
		response.setContentType("application/pdf");
		String filename = "Payment Planning " + ".pdf";
		response.setHeader("Content-disposition", "inline; filename=" + filename);
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/payment-planning-pdf", data);
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
	@GetMapping("account-statement-FilterData")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> viewMothlyDetailsFilterData(Model model,
			@RequestParam String ledgerId, @RequestParam String fromDate, @RequestParam String toDate,
			HttpSession session) {
		logger.info("Method : viewMothlyDetailsFilterData starts" + ledgerId);
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			System.out.println("ledgerId====>>>" + ledgerId);

			jsonResponse = restClient.getForObject(
					env.getAccountUrl() + "viewMothlyDetailsFilter?fromDate=" + fromDate + "&toDate=" + toDate
							+ "&ledgerId=" + ledgerId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :viewMothlyDetailsFilterData ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("day-book-report-getFilterData")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> dayBookReportFilterData(Model model,
			HttpSession session, @RequestParam String inputDate) {
		logger.info("Method : dayBookReportFilterData starts");
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(env.getAccountUrl() + "dayBookReportFilterData?inputDate="
					+ inputDate + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		// System.out.println("REsp" + jsonResponse);
		logger.info("Method :dayBookReportFilterData ends");
		return jsonResponse;
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/day-book-Pdf" })
	public void dayBookPdf(HttpServletResponse response, Model model, @RequestParam String inputDate,
			HttpSession session) {
		logger.info("Method :dayBookPdf starts");

		WebAccountReportModel dayBook = new WebAccountReportModel();
		// JsonResponse<WebAccountReportModel> jsonResponse = new
		// JsonResponse<WebAccountReportModel>();
		JsonResponse<List<WebAccountReportModel>> report = new JsonResponse<List<WebAccountReportModel>>();

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			report = restClient.getForObject(env.getAccountUrl() + "dayBookPdfData?inputDate=" + inputDate + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Report------->>>>" + report);

		ObjectMapper mapper = new ObjectMapper();

		List<WebAccountReportModel> reportcardDayBook = mapper.convertValue(report.getBody(),
				new TypeReference<List<WebAccountReportModel>>() {
				});
		// jsonResponse.setBody(dayBook);

		double totalDebit = reportcardDayBook.stream()
				.mapToDouble(item -> Double.parseDouble(item.getDebitAmount().replace(",", ""))).sum();
		String totalDebitString = IndianNumberFormat.formatIndianNumber(totalDebit);

		double totalCredit = reportcardDayBook.stream()
				.mapToDouble(item -> Double.parseDouble(item.getCreditAmount().replace(",", ""))).sum();
		String totalCreditString = IndianNumberFormat.formatIndianNumber(totalCredit);

		String inputDateString = inputDate;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("vendorCard", reportcardDayBook);
		data.put("inputDateString", inputDateString);
		data.put("orgDivision", orgDivision);
		data.put("totalDebitAmt", totalDebitString);
		data.put("totalCreditAmt", totalCreditString);
		String filename = "day-book-Pdf_" + inputDate + ".pdf";

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);

		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/Day-Book-pdf", data);
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
	@GetMapping(value = { "/ledger-voucher-Pdf" })
	public void ledgerVoucherPdf(HttpServletResponse response, Model model, @RequestParam String ledgerId,
			String voucherType, String fromDate, String toDate, HttpSession session) {
		logger.info("Method :ledgerVoucherPdf starts");
		WebAccountReportModel ledgerVoucher = new WebAccountReportModel();
		JsonResponse<List<WebAccountReportModel>> ledgerVoucherReport = new JsonResponse<List<WebAccountReportModel>>();

		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ledgerVoucherReport = restClient.getForObject(env.getAccountUrl() + "ledgerVoucherPdfData?ledgerId="
					+ ledgerId + "&voucherType=" + voucherType + "&fromDate=" + fromDate + "&toDate=" + toDate
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<WebAccountReportModel> ledgerVoucherList = mapper.convertValue(ledgerVoucherReport.getBody(),
				new TypeReference<List<WebAccountReportModel>>() {
				});

		double totalDebit = ledgerVoucherList.stream()
				.mapToDouble(item -> Double.parseDouble(item.getDebitAmount().replace(",", ""))).sum();
		String totalDebitString = IndianNumberFormat.formatIndianNumber(totalDebit);

		double totalCredit = ledgerVoucherList.stream()
				.mapToDouble(item -> Double.parseDouble(item.getCreditAmount().replace(",", ""))).sum();
		String totalCreditString = IndianNumberFormat.formatIndianNumber(totalCredit);

		String fromToDate = fromDate + " TO " + toDate;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("ledgerVoucher", ledgerVoucherList);
		data.put("fromToDate", fromToDate);
		data.put("voucherType", voucherType);
		data.put("orgDivision", orgDivision);
		data.put("totalDebitAmt", totalDebitString);
		data.put("totalCreditAmt", totalCreditString);
		String filename = "ledger-voucher-pdf_" + fromDate + "_" + toDate + ".pdf";

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/ledger-voucher-pdf", data);
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

		logger.info("Method : ledgerVoucherPdf end");
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/account-statement-Pdf" })
	public void accountStatementPdf(HttpServletResponse response, Model model, @RequestParam String ledgerid,
			String fromDate, String toDate, String month, HttpSession session) {
		logger.info("Method :accountStatementPdf starts");
		WebAccountReportModel accountStatement = new WebAccountReportModel();
		JsonResponse<List<WebAccountReportModel>> accountStatementReport = new JsonResponse<List<WebAccountReportModel>>();

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			accountStatementReport = restClient.getForObject(env.getAccountUrl() + "accountStatementPdfData?ledgerid="
					+ ledgerid + "&fromDate=" + fromDate + "&toDate=" + toDate + "&orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&month=" + month, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		List<WebAccountReportModel> accountStatementt = mapper.convertValue(accountStatementReport.getBody(),
				new TypeReference<List<WebAccountReportModel>>() {
				});

		double totalDebit = accountStatementt.stream()
				.mapToDouble(item -> Double.parseDouble(item.getDebitAmount().replace(",", ""))).sum();
		String totalDebitString = IndianNumberFormat.formatIndianNumber(totalDebit);

		double totalCredit = accountStatementt.stream()
				.mapToDouble(item -> Double.parseDouble(item.getCreditAmount().replace(",", ""))).sum();
		String totalCreditString = IndianNumberFormat.formatIndianNumber(totalCredit);

		String fromToDate = fromDate + " TO " + toDate;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("accountStatement", accountStatementt);
		data.put("fromToDate", fromToDate);
		data.put("orgDivision", orgDivision);
		data.put("totalDebitAmt", totalDebitString);
		data.put("totalCreditAmt", totalCreditString);
		String filename = "account-statement-pdf_" + fromDate + "_" + toDate + ".pdf";

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);

		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/account-statement-pdf", data);
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

		logger.info("Method : accountStatementPdf end");

	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "balance-sheet-getVoucherDetails" })
	public @ResponseBody JsonResponse<Object> getVoucherDetailsBSheet(HttpSession session,
			@RequestParam String voucherId, String voucherType) {
		logger.info("Method : getVoucherDetailsBSheet starts" + voucherId);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restClient
					.getForObject(
							env.getAccountUrl() + "getVoucherDetailsBSheet?voucherId=" + voucherId + "&voucherType="
									+ voucherType + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
		}
		logger.info("resp==>" + res);
		logger.info("Method : getVoucherDetailsBSheet ends");
		return res;
	}

	@PostMapping(value = { "/payment-planning-proposeAmt-pdf" })
	public @ResponseBody ResponseEntity<byte[]> paymentPlanningPdf(
			@RequestBody List<AccountLedgerReportWebModel> dataList, HttpSession session) {
		logger.info("Method : paymentPlanningPdf starts");

		ObjectMapper objectMapper = new ObjectMapper();
		JsonResponse<List<AccountLedgerReportWebModel>> report = new JsonResponse<>();
		report.setBody(dataList);

		List<AccountLedgerReportWebModel> reportcard = objectMapper.convertValue(report.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});

		logger.info("DataList--->" + dataList);
		double totalAmt = reportcard.stream().filter(item -> item.getOutstandingAmt() != null)
				.mapToDouble(item -> Double.parseDouble(item.getOutstandingAmt().replace(",", ""))).sum();
		String totalAmtString = IndianNumberFormat.formatIndianNumber(totalAmt);

		double paidAmt = reportcard.stream().filter(item -> item.getPayableAmt() != null)
				.mapToDouble(item -> Double.parseDouble(item.getPayableAmt().replace(",", ""))).sum();
		String paidAmtString = IndianNumberFormat.formatIndianNumber(paidAmt);

		double remainAmt = reportcard.stream().filter(item -> item.getProposeAmt() != null)
				.mapToDouble(item -> Double.parseDouble(item.getProposeAmt().replace(",", ""))).sum();
		String proposeAmount = IndianNumberFormat.formatIndianNumber(remainAmt);

		Map<String, Object> data = new HashMap<>();
		data.put("reportcard", reportcard);
		data.put("totalAmtString", totalAmtString);
		data.put("paidAmtString", paidAmtString);
		data.put("proposeAmount", proposeAmount);

		logger.info("data--->" + data);
		byte[] fileData = null;
		try {
			File file = pdfGeneratorUtil.createPdf("account/payment-planning-pdf.html", data);
			InputStream in = new FileInputStream(file);
			fileData = IOUtils.toByteArray(in);
		} catch (Exception e) {
			logger.error("Error while generating PDF", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("inline", "Payment Planning.pdf");
		headers.setContentLength(fileData.length);

		return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "payment-planning-proposeAmt-add" })
	public @ResponseBody JsonResponse<Object> proposeAmountAdd(@RequestBody JsonNode selectedRowsData,
			@RequestParam("vendorId") String vendorId, HttpSession session) {
		logger.info("Method : proposeAmountAdd starts" + selectedRowsData);
		logger.info("Method : proposeAmountAdd starts" + vendorId);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			String url = env.getAccountUrl() + "proposeAmountAdd?vendorId=" + vendorId + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision;
			res = restClient.postForObject(url, selectedRowsData, JsonResponse.class);

			// res = restClient.postForObject(env.getAccountUrl() + "proposeAmountAdd" ,
			// selectedRowsData, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
		}
		logger.info("resp==>" + res);
		logger.info("Method : proposeAmountAdd ends");
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "payment-planning-proposeAmt-details" })
	public @ResponseBody JsonResponse<Object> proposeAmtDetails(@RequestParam("vendorId") String vendorId,
			HttpSession session) {
		logger.info("Method : proposeAmtDetails starts-->" + vendorId);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			res = restClient.getForObject(env.getAccountUrl() + "getProposeAmountDetails?vendorId=" + vendorId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("Success");
		}
		logger.info("resp==>" + res);
		logger.info("Method : proposeAmtDetails ends");
		return res;
	}

	/* Trail Balance Controller */

//	@SuppressWarnings("unchecked")
//	@PostMapping("reports-get-trial-balance-rows")
//	public @ResponseBody JsonResponse<Object> getTrialBalanceRows(HttpSession session,
//			@RequestBody AccountTrailBalanceModel tBModel) {
//		logger.info("Method : getTrialBalanceRows starts");
//		
//		String orgName = "";
//		String orgDivision = "";
//
//		JsonResponse<Object> resp = new JsonResponse<Object>();
//
//		try {
//			orgName = (String) session.getAttribute("ORGANIZATION");
//			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		try {
//			resp = restClient.postForObject(
//					env.getAccountUrl() + "get-trial-balance-rows?orgName=" + orgName + "&orgDivision=" + orgDivision,
//					tBModel, JsonResponse.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		logger.info("Method : getTrialBalanceRows ends");
//		return resp;
//	}

	@SuppressWarnings("unchecked")
	@PostMapping("get-level-one")
	public @ResponseBody JsonResponse<Object> getOtp(HttpSession session,
			@RequestBody AccountTrailBalanceModel tBModel) {
		logger.info("Method : getLevelOne starts");
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.postForObject(
					env.getAccountUrl() + "get-level-one?orgName=" + orgName + "&orgDivision=" + orgDivision, tBModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getLevelOne ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("get-level-others")
	public @ResponseBody JsonResponse<Object> getLevelOthers(HttpSession session,
			@RequestBody AccountTrailBalanceModel tBModel) {
		logger.info("Method : getLevelOthers starts");
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("orgName-->" + orgName);
		logger.info("orgDivision-->" + orgDivision);

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp = restClient.postForObject(
					env.getAccountUrl() + "get-level-others?orgName=" + orgName + "&orgDivision=" + orgDivision,
					tBModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method : getLevelOthers ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("trail-balance-ledger-details")
	public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> trailBalanceLedgerDetails(Model model,
			@RequestParam String id, @RequestParam String fromdate, @RequestParam String todate, HttpSession session) {
		logger.info("Method : viewLedgerVoucherReport starts" + id);
		JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restClient.getForObject(
					env.getAccountUrl() + "viewTrailBalanceLedger?id=" + id + "&orgName=" + orgName + "&orgDivision="
							+ orgDivision + "&fromdate=" + fromdate + "&todate=" + todate,

					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		List<AccountLedgerReportWebModel> manageleadger = mapper.convertValue(jsonResponse.getBody(),
				new TypeReference<List<AccountLedgerReportWebModel>>() {
				});
		System.out.println("###" + manageleadger);
		jsonResponse.setBody(manageleadger);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
		} else {
			jsonResponse.setMessage("Success");
		}
		System.out.println("REsp" + jsonResponse);
		logger.info("Method :viewLedgerVoucherReport ends");
		return jsonResponse;
	}

	/** Excel Download 19-06-2025 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException **/

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/reports-get-trial-balance-pdf" })
	public void trialBalancePDFCreationFromJson(HttpServletResponse response, Model model,
			@RequestParam String enc_start_date, @RequestParam String enc_end_date, HttpSession session)
			throws JsonParseException, JsonMappingException, IOException {
		logger.info("Method :trialBalancePDFCreationFromJson starts");
		
		Map<String, Object> data = new HashMap<>();
		
		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] decodedBytes_1 = Base64.getDecoder().decode(enc_start_date);
		String start_date = new String(decodedBytes_1);

		byte[] decodedBytes_2 = Base64.getDecoder().decode(enc_end_date);
		String end_date = new String(decodedBytes_2);

		AccountTrailBalanceModel tBModel = new AccountTrailBalanceModel();

		tBModel.setStart_date(start_date);
		tBModel.setEnd_date(end_date);

		try {
			resp = restClient.postForObject(
					env.getAccountUrl() + "get-trial-balance-rows?orgName=" + orgName + "&orgDivision=" + orgDivision,
					tBModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = (String) resp.getBody();

		List<Map<String, String>> dataList = mapper.readValue(jsonString,
				new TypeReference<List<Map<String, String>>>() {
				});
		
		double totalDebit = 0.0;
        double totalCredit = 0.0;

        for (Map<String, String> row : dataList) {
            if ("L1".equals(row.get("AccGroupLevel"))) {
                totalDebit += Double.parseDouble(row.get("DebitAmount"));
                totalCredit += Double.parseDouble(row.get("CreditAmount"));
            }
        }

        double closingBalance = totalDebit - totalCredit;
        String closingBalanceStr;

        if (closingBalance > 0) {
            closingBalanceStr = String.format("%.2f DR", closingBalance);
        } else if (closingBalance < 0) {
            closingBalanceStr = String.format("%.2f CR", Math.abs(closingBalance));
        } else {
            closingBalanceStr = "0.00";
        }
		
		data.put("data", dataList);
		data.put("totalDebit", totalDebit);
		data.put("totalCredit", totalCredit);
		data.put("closingBalance", closingBalanceStr);

		response.setContentType("application/pdf");
		String filename = "Trial Balance Report" + ".pdf";
		response.setHeader("Content-disposition", "inline; filename=" + filename);
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("account/trial-balance-report-pdf", data);
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
	@GetMapping("reports-get-trial-balance-excel")
	public ResponseEntity<byte[]> trialBalanceExcelCreationFromJson(@RequestParam String enc_start_date,
			@RequestParam String enc_end_date, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.info("Method :viewLedgerVoucherReport ends");

		String orgName = "";
		String orgDivision = "";

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] decodedBytes_1 = Base64.getDecoder().decode(enc_start_date);
		String start_date = new String(decodedBytes_1);

		byte[] decodedBytes_2 = Base64.getDecoder().decode(enc_end_date);
		String end_date = new String(decodedBytes_2);

		AccountTrailBalanceModel tBModel = new AccountTrailBalanceModel();

		tBModel.setStart_date(start_date);
		tBModel.setEnd_date(end_date);

		try {
			resp = restClient.postForObject(
					env.getAccountUrl() + "get-trial-balance-rows?orgName=" + orgName + "&orgDivision=" + orgDivision,
					tBModel, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = (String) resp.getBody();

		List<Map<String, String>> dataList = mapper.readValue(jsonString,
				new TypeReference<List<Map<String, String>>>() {
				});

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Accounts");

		// Header style
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setUnderline(Font.U_SINGLE);

		DataFormat format = workbook.createDataFormat();
		short indianFormat = format.getFormat("#\\,##\\,##0.00");

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);

		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);

		Font fontSize9 = workbook.createFont();
		fontSize9.setFontHeightInPoints((short) 9);

		CellStyle dataStyle = workbook.createCellStyle();
		dataStyle.setFont(fontSize9);
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setBorderBottom(BorderStyle.THIN);
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setBorderRight(BorderStyle.THIN);

		CellStyle rightAlignStyle = workbook.createCellStyle();
		rightAlignStyle.setFont(fontSize9);
		rightAlignStyle.setDataFormat(indianFormat);
		rightAlignStyle.setAlignment(HorizontalAlignment.RIGHT);
		rightAlignStyle.setBorderTop(BorderStyle.THIN);
		rightAlignStyle.setBorderBottom(BorderStyle.THIN);
		rightAlignStyle.setBorderLeft(BorderStyle.THIN);
		rightAlignStyle.setBorderRight(BorderStyle.THIN);

		Font boldDataFont = workbook.createFont();
		boldDataFont.setBold(true);
		boldDataFont.setFontHeightInPoints((short) 9);

		CellStyle leftBoldStyle = workbook.createCellStyle();
		leftBoldStyle.setFont(boldDataFont);
		leftBoldStyle.setBorderTop(BorderStyle.THIN);
		leftBoldStyle.setBorderBottom(BorderStyle.THIN);
		leftBoldStyle.setBorderLeft(BorderStyle.THIN);
		leftBoldStyle.setBorderRight(BorderStyle.THIN);
		leftBoldStyle.setAlignment(HorizontalAlignment.LEFT);

		// Right-aligned, bold, bordered style
		CellStyle rightBoldStyle = workbook.createCellStyle();
		rightBoldStyle.setFont(boldDataFont);
		rightBoldStyle.setDataFormat(indianFormat);
		rightBoldStyle.setAlignment(HorizontalAlignment.RIGHT);
		rightBoldStyle.setBorderTop(BorderStyle.THIN);
		rightBoldStyle.setBorderBottom(BorderStyle.THIN);
		rightBoldStyle.setBorderLeft(BorderStyle.THIN);
		rightBoldStyle.setBorderRight(BorderStyle.THIN);

		Font boldItalicFont = workbook.createFont();
		boldItalicFont.setBold(true);
		boldItalicFont.setItalic(true);
		boldItalicFont.setFontHeightInPoints((short) 9);

		CellStyle leftBoldItalicStyle = workbook.createCellStyle();
		leftBoldItalicStyle.setFont(boldItalicFont);
		leftBoldItalicStyle.setBorderTop(BorderStyle.THIN);
		leftBoldItalicStyle.setBorderBottom(BorderStyle.THIN);
		leftBoldItalicStyle.setBorderLeft(BorderStyle.THIN);
		leftBoldItalicStyle.setBorderRight(BorderStyle.THIN);
		leftBoldItalicStyle.setAlignment(HorizontalAlignment.LEFT);

		// Right-aligned, bold + italic, with borders
		CellStyle rightBoldItalicStyle = workbook.createCellStyle();
		rightBoldItalicStyle.setFont(boldItalicFont);
		rightBoldItalicStyle.setBorderTop(BorderStyle.THIN);
		rightBoldItalicStyle.setDataFormat(indianFormat);
		rightBoldItalicStyle.setBorderBottom(BorderStyle.THIN);
		rightBoldItalicStyle.setBorderLeft(BorderStyle.THIN);
		rightBoldItalicStyle.setBorderRight(BorderStyle.THIN);
		rightBoldItalicStyle.setAlignment(HorizontalAlignment.RIGHT);

		Font boldItalicUnderlineFont = workbook.createFont();
		boldItalicUnderlineFont.setBold(true);
		boldItalicUnderlineFont.setItalic(true);
		boldItalicUnderlineFont.setUnderline(Font.U_SINGLE);
		boldItalicUnderlineFont.setFontHeightInPoints((short) 9);

		// Left-aligned: bold + italic + underline
		CellStyle leftBoldItalicUnderlineStyle = workbook.createCellStyle();
		leftBoldItalicUnderlineStyle.setFont(boldItalicUnderlineFont);
		leftBoldItalicUnderlineStyle.setAlignment(HorizontalAlignment.LEFT);
		leftBoldItalicUnderlineStyle.setBorderTop(BorderStyle.THIN);
		leftBoldItalicUnderlineStyle.setBorderBottom(BorderStyle.THIN);
		leftBoldItalicUnderlineStyle.setBorderLeft(BorderStyle.THIN);
		leftBoldItalicUnderlineStyle.setBorderRight(BorderStyle.THIN);

		// Right-aligned: bold + italic + underline
		CellStyle rightBoldItalicUnderlineStyle = workbook.createCellStyle();
		rightBoldItalicUnderlineStyle.setFont(boldItalicUnderlineFont);
		rightBoldItalicUnderlineStyle.setDataFormat(indianFormat);
		rightBoldItalicUnderlineStyle.setAlignment(HorizontalAlignment.RIGHT);
		rightBoldItalicUnderlineStyle.setBorderTop(BorderStyle.THIN);
		rightBoldItalicUnderlineStyle.setBorderBottom(BorderStyle.THIN);
		rightBoldItalicUnderlineStyle.setBorderLeft(BorderStyle.THIN);
		rightBoldItalicUnderlineStyle.setBorderRight(BorderStyle.THIN);

		Font boldUnderlineFont = workbook.createFont();
		boldUnderlineFont.setBold(true);
		boldUnderlineFont.setUnderline(Font.U_SINGLE);
		boldUnderlineFont.setFontHeightInPoints((short) 9);

		// Left-aligned: bold + underline + borders
		CellStyle leftBoldUnderlineStyle = workbook.createCellStyle();
		leftBoldUnderlineStyle.setFont(boldUnderlineFont);
		leftBoldUnderlineStyle.setAlignment(HorizontalAlignment.LEFT);
		leftBoldUnderlineStyle.setBorderTop(BorderStyle.THIN);
		leftBoldUnderlineStyle.setBorderBottom(BorderStyle.THIN);
		leftBoldUnderlineStyle.setBorderLeft(BorderStyle.THIN);
		leftBoldUnderlineStyle.setBorderRight(BorderStyle.THIN);

		// Right-aligned: bold + underline + borders
		CellStyle rightBoldUnderlineStyle = workbook.createCellStyle();
		rightBoldUnderlineStyle.setFont(boldUnderlineFont);
		rightBoldUnderlineStyle.setDataFormat(indianFormat);
		rightBoldUnderlineStyle.setAlignment(HorizontalAlignment.RIGHT);
		rightBoldUnderlineStyle.setBorderTop(BorderStyle.THIN);
		rightBoldUnderlineStyle.setBorderBottom(BorderStyle.THIN);
		rightBoldUnderlineStyle.setBorderLeft(BorderStyle.THIN);
		rightBoldUnderlineStyle.setBorderRight(BorderStyle.THIN);

		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		boldFont.setFontHeightInPoints((short) 9);

		// Left-aligned bold style (for column 0)
		CellStyle leftBoldStyle1 = workbook.createCellStyle();
		leftBoldStyle1.setFont(boldFont);
		leftBoldStyle1.setAlignment(HorizontalAlignment.LEFT);
		leftBoldStyle1.setBorderTop(BorderStyle.THIN);
		leftBoldStyle1.setBorderBottom(BorderStyle.THIN);
		leftBoldStyle1.setBorderLeft(BorderStyle.THIN);
		leftBoldStyle1.setBorderRight(BorderStyle.THIN);

		// Right-aligned bold style (for columns 1–4)
		CellStyle rightBoldStyle1 = workbook.createCellStyle();
		rightBoldStyle1.setFont(boldFont);
		rightBoldStyle1.setDataFormat(indianFormat);
		rightBoldStyle1.setAlignment(HorizontalAlignment.RIGHT);
		rightBoldStyle1.setBorderTop(BorderStyle.THIN);
		rightBoldStyle1.setBorderBottom(BorderStyle.THIN);
		rightBoldStyle1.setBorderLeft(BorderStyle.THIN);
		rightBoldStyle1.setBorderRight(BorderStyle.THIN);

		// Header names
		String[] headers = { "Account Description", "Opening Balance", "Transaction Debit", "Transaction Credit",
				"Closing Balance" };
		String[] keys = { "AccGroupName", "OpenBalance", "DebitAmount", "CreditAmount", "CloseBalance" };

		// Create header
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle);
		}

		Double totalDebit = 0.0;
		Double totalCredit = 0.0;

		for (int i = 0; i < dataList.size(); i++) {
			Row row = sheet.createRow(i + 1);
			Map<String, String> rowData = dataList.get(i);
			String level = rowData.get("AccGroupLevel");
			if (level.equals("L1")) {
				Object debitVal = rowData.get("DebitAmount");
				Object creditVal = rowData.get("CreditAmount");
				totalDebit += debitVal != null ? Double.parseDouble(debitVal.toString()) : 0.0;
				totalCredit += creditVal != null ? Double.parseDouble(creditVal.toString()) : 0.0;
			}
			for (int j = 0; j < keys.length; j++) {
				Cell cell = row.createCell(j);
				Object value = rowData.get(keys[j]);

				cell.setCellValue(value != null ? value.toString() : "");
				if (j >= 1) {
					if (level.equals("L1")) {
						cell.setCellStyle(rightBoldStyle);
					} else if (level.equals("L2")) {
						cell.setCellStyle(rightBoldItalicStyle);
					} else if (level.equals("L3")) {
						cell.setCellStyle(rightBoldItalicUnderlineStyle);
					} else if (level.equals("L4")) {
						cell.setCellStyle(rightBoldUnderlineStyle);
					} else if (level.equals("L5")) {
						cell.setCellStyle(rightBoldStyle1);
					} else {
						cell.setCellStyle(rightAlignStyle);
					}
				} else {
					if (level.equals("L1")) {
						cell.setCellStyle(leftBoldStyle);
					} else if (level.equals("L2")) {
						cell.setCellStyle(leftBoldItalicStyle);
					} else if (level.equals("L3")) {
						cell.setCellStyle(leftBoldItalicUnderlineStyle);
					} else if (level.equals("L4")) {
						cell.setCellStyle(leftBoldUnderlineStyle);
					} else if (level.equals("L5")) {
						cell.setCellStyle(leftBoldStyle1);
					} else {
						cell.setCellStyle(dataStyle);
					}
				}
			}
		}

		double closingDiff = totalDebit - totalCredit;
		String closingBalanceFormatted = String.format("%.2f", Math.abs(closingDiff));
		if (closingDiff > 0) {
			closingBalanceFormatted += " DR";
		} else if (closingDiff < 0) {
			closingBalanceFormatted += " CR";
		}

		int totalRowIndex = dataList.size() + 1; // after data rows
		Row totalRow = sheet.createRow(totalRowIndex);

		Font totalFont = workbook.createFont();
		totalFont.setBold(true);
		totalFont.setFontHeightInPoints((short) 12);

		// Left-aligned style for "Total" label
		CellStyle totalLeftStyle = workbook.createCellStyle();
		totalLeftStyle.setFont(totalFont);
		totalLeftStyle.setAlignment(HorizontalAlignment.LEFT);
		totalLeftStyle.setBorderTop(BorderStyle.THIN);
		totalLeftStyle.setBorderBottom(BorderStyle.THIN);
		totalLeftStyle.setBorderLeft(BorderStyle.THIN);
		totalLeftStyle.setBorderRight(BorderStyle.THIN);
		totalLeftStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		totalLeftStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Right-aligned style for numbers
		CellStyle totalRightStyle = workbook.createCellStyle();
		totalRightStyle.setFont(totalFont);
		totalRightStyle.setDataFormat(indianFormat);
		totalRightStyle.setAlignment(HorizontalAlignment.RIGHT);
		totalRightStyle.setBorderTop(BorderStyle.THIN);
		totalRightStyle.setBorderBottom(BorderStyle.THIN);
		totalRightStyle.setBorderLeft(BorderStyle.THIN);
		totalRightStyle.setBorderRight(BorderStyle.THIN);
		totalRightStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		totalRightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Total label
		Cell descCell = totalRow.createCell(0);
		descCell.setCellValue("Total");
		descCell.setCellStyle(totalLeftStyle);

		// Opening Balance = 0.00
		Cell openCell = totalRow.createCell(1);
		openCell.setCellValue("0.00");
		openCell.setCellStyle(totalRightStyle);

		// Debit
		Cell debitCell = totalRow.createCell(2);
		debitCell.setCellValue(String.format("%.2f", totalDebit));
		debitCell.setCellStyle(totalRightStyle);

		// Credit
		Cell creditCell = totalRow.createCell(3);
		creditCell.setCellValue(String.format("%.2f", totalCredit));
		creditCell.setCellStyle(totalRightStyle);

		// Closing balance with suffix
		Cell closeCell = totalRow.createCell(4);
		closeCell.setCellValue(closingBalanceFormatted);
		closeCell.setCellStyle(totalRightStyle);

		// Autosize
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}

		String fileName = "Trial Balance Report.xlsx";

		// Write to file
		try (FileOutputStream out = new FileOutputStream(fileName)) {
			workbook.write(out);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		workbook.close();
		System.out.println("Excel generated: " + fileName);

		try {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(bos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
