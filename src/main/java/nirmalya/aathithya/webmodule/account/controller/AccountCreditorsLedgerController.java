package nirmalya.aathithya.webmodule.account.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountCreditorLedgerModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = "/account")
public class AccountCreditorsLedgerController {

	Logger logger = LoggerFactory.getLogger(AccountCreditorsLedgerController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("/creditors-ledger")

	public String viewCreditorsLedger(Model model, HttpSession session) {

		logger.info("Method : creditors-ledger starts");

		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method :creditors-ledger");
		return "account/creditors-ledger";

	}

	// view-account-creditors-ledger

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-creditors-ledger")
	public @ResponseBody Object viewCreditorsLedger(HttpSession session) {

		logger.info("Method :viewCreditorsLedger starts");
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

			res = restClient.getForObject(
					env.getAccountUrl() + "viewCreditorsLedger?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewCreditorsLedger ends" + res);

		return res;
	}

	// view-account-creditors-ledgerFilter

	@SuppressWarnings("unchecked")
	@GetMapping("view-account-creditors-ledgerFilter")
	public @ResponseBody Object creditorsLedgerFilter(HttpSession session, @RequestParam String financialYear,
			String fromDate, String toDate) {

		logger.info("Method :creditorsLedgerFilter starts");
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

			res = restClient.getForObject(
					env.getAccountUrl() + "creditorsLedgerFilter?orgName=" + orgName + "&orgDivision=" + orgDivision
							+ "&financialYear=" + financialYear + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :creditorsLedgerFilter ends" + res);

		return res;
	}

	// view-account-creditors-ledger-vendorList
	@SuppressWarnings("unchecked")
	@PostMapping(value = { "view-account-creditors-ledger-vendorList" })
	public @ResponseBody JsonResponse<AccountCreditorLedgerModel> getVendorNameAutoList(Model model,
			@RequestBody String searchValue, BindingResult result) {
		logger.info("Method : getVendorNameAutoList starts");
		JsonResponse<AccountCreditorLedgerModel> res = new JsonResponse<AccountCreditorLedgerModel>();

		try {
			res = restClient.getForObject(env.getAccountUrl() + "getVendorNameAutoList?id=" + searchValue,
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

		logger.info("Method : getVendorNameAutoList ends");
		return res;
	}

	// vendor-creditor-ledger-view-pdf

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/creditors-ledger-receipt-Pdf" })
	public void vendorCreditorsLedgerPdf(HttpServletResponse response, Model model, @RequestParam String id,
			String fromDate, String toDate) {
		logger.info("Method :vendorCreditorsLedgerPdf starts");

		AccountCreditorLedgerModel product = new AccountCreditorLedgerModel();
		JsonResponse<AccountCreditorLedgerModel> jsonResponse = new JsonResponse<AccountCreditorLedgerModel>();

		JsonResponse<List<AccountCreditorLedgerModel>> report = new JsonResponse<List<AccountCreditorLedgerModel>>();

		try {
			report = restClient.getForObject(env.getAccountUrl() + "vendorCreditorsLedgerPdf?id=" + id + "&fromDate="
					+ fromDate + "&toDate=" + toDate, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("responseReport=>>>>>>" + report);
		if (report.getBody().isEmpty()) {

			ObjectMapper mapper = new ObjectMapper();

			List<AccountCreditorLedgerModel> reportcard = mapper.convertValue(report.getBody(),
					new TypeReference<List<AccountCreditorLedgerModel>>() {
					});

			jsonResponse.setBody(product);

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("vendorCard", reportcard);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=creditor-ledger-stmt");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("account/creditors-ledger-receipt-Pdf-noData", data);
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

		} else {
			ObjectMapper mapper = new ObjectMapper();

			List<AccountCreditorLedgerModel> reportcard = mapper.convertValue(report.getBody(),
					new TypeReference<List<AccountCreditorLedgerModel>>() {
					});

			jsonResponse.setBody(product);

			String fromToDate = fromDate + " TO " + toDate;
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("vendorCard", reportcard);
			data.put("fromToDate", fromToDate);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=creditor-ledger-stmt");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("account/creditors-ledger-receipt-Pdf", data);
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

	}

}
