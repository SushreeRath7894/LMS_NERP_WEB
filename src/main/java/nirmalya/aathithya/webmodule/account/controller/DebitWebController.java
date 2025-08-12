package nirmalya.aathithya.webmodule.account.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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

import nirmalya.aathithya.webmodule.account.model.DebitLedgerModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import org.apache.commons.io.IOUtils;

@Controller
@RequestMapping(value = "account")
public class DebitWebController {

	Logger logger = LoggerFactory.getLogger(DebitWebController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	// view balance sheet page

	@GetMapping("/debit")
	public String viewCreditNote(Model model, HttpSession session) {

		logger.info("Method : viewDebit starts");

		try {
			DropDownModel[] orderStatus = restClient.getForObject(env.getAccountUrl() + "/getfiscalList",
					DropDownModel[].class);

			List<DropDownModel> fiscalYearList = Arrays.asList(orderStatus);
			System.out.println("fiscalYearList" + fiscalYearList);
			model.addAttribute("fiscalYearList", fiscalYearList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : viewDebit ends");
		return "account/debit";

	}

	// view
	@SuppressWarnings("unchecked")
	@GetMapping("debit-view")
	public @ResponseBody Object viewInvoiceDetails(HttpSession session) {
		logger.info("Method :viewInvoiceDetails starts");
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
			resp = restTemplate.getForObject(
					env.getAccountUrl() + "rest-viewDebit?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :viewInvoiceDetails ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("debit-view-Filter")
	public @ResponseBody Object creditorsLedgerFilter(HttpSession session, @RequestParam String financialYear,
			String fromDate, String toDate) {

		logger.info("Method :creditorsLedgerFilter starts" + fromDate);
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
					env.getAccountUrl() + "debit-view-Filter?orgName=" + orgName + "&orgDivision=" + orgDivision
							+ "&financialYear=" + financialYear + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :creditorsLedgerFilter ends" + res);

		return res;
	}

	/*
	 * Debit Auto search
	 */

	@SuppressWarnings("unchecked")
	@PostMapping("debit-view-get-vender-list")
	public @ResponseBody JsonResponse<DebitLedgerModel> DebitgetVenderList(Model model, @RequestBody String searchValue,
			BindingResult result) {
		logger.info("Method : getDebitgetVenderList starts");
		JsonResponse<DebitLedgerModel> res = new JsonResponse<DebitLedgerModel>();

		try {
			res = restClient.getForObject(env.getAccountUrl() + "debitgetvenderlist?id=" + searchValue,
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

		logger.info("Method : getDebitgetVenderList ends" + res);
		return res;
	}

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/debit-ledger-pdf-admin" })
	public void dealerAdminAccountStatement(HttpServletResponse response, Model model, @RequestParam String id,
			String fromDate, String toDate) {
		logger.info("Method :dealerAdminAccountStatement starts");

		// System.out.println("fromDate===>>" + fromDate);
		// System.out.println("toDate===>>" + toDate);

		DebitLedgerModel product = new DebitLedgerModel();
		JsonResponse<DebitLedgerModel> jsonResponse = new JsonResponse<DebitLedgerModel>();

		JsonResponse<List<DebitLedgerModel>> report = new JsonResponse<List<DebitLedgerModel>>();

		try {
			report = restClient.getForObject(
					env.getAccountUrl() + "debitledgerpdf?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("responseReport=>>>>>>" + report);

		if (report.getBody().isEmpty()) {

			ObjectMapper mapper = new ObjectMapper();

			List<DebitLedgerModel> reportcard = mapper.convertValue(report.getBody(),
					new TypeReference<List<DebitLedgerModel>>() {
					});

			jsonResponse.setBody(product);

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("dealerCard", reportcard);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=dealer-accnt-stmt");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("account/debit-ledger-pdf-noData", data);
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

			List<DebitLedgerModel> reportcard = mapper.convertValue(report.getBody(),
					new TypeReference<List<DebitLedgerModel>>() {
					});

			jsonResponse.setBody(product);

			String fromToDate = fromDate + " TO " + toDate;
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("dealerCard", reportcard);
			data.put("fromToDate", fromToDate);

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=dealer-accnt-stmt");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("account/debit-ledger-Pdf", data);
				System.out.println("data" + data);
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