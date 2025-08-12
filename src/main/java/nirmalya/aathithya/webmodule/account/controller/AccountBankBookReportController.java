package nirmalya.aathithya.webmodule.account.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountLedgerReportWebModel;
import nirmalya.aathithya.webmodule.account.model.ManageLeadgerModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.common.utils.IndianNumberFormat;

@Controller
@RequestMapping(value = "account")
public class AccountBankBookReportController {
	
	Logger logger = LoggerFactory.getLogger(AccountBankBookReportController.class);
	@Autowired
	RestTemplate restClient;
	@Autowired
	EnvironmentVaribles env;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("/bank-report")
	public String viewBankBookReport(Model model, HttpSession session) {
		logger.info("Method : AccountBankBookReportController viewBankBookReport starts");
		logger.info("Method : AccountBankBookReportController viewBankBookReport end");
		return "account/Bank-Book-Report";
	}

	
	//view
		@SuppressWarnings("unchecked")
		@GetMapping("bank-report-view")
		public @ResponseBody List<ManageLeadgerModel> viewLeadger(HttpSession session) {

			logger.info("Method : viewManageLeadger starts");

			JsonResponse<List<ManageLeadgerModel>> resp = new JsonResponse<List<ManageLeadgerModel>>();
			String orgName = "";
			String orgDivision = "";
	       try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
			} catch (Exception e) {

			}
			
			try {
				resp = restClient.getForObject(env.getAccountUrl() + "restbankBookReportDetails?orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			ObjectMapper mapper = new ObjectMapper();

			List<ManageLeadgerModel> manageleadgermodel = mapper.convertValue(resp.getBody(),
					new TypeReference<List<ManageLeadgerModel>>() {
					});

			resp.setBody(manageleadgermodel);

			logger.info("Method : viewManageLeadger ends");
			return resp.getBody();
		}
		
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("bank-report-filterdata")
		public @ResponseBody JsonResponse<List<AccountLedgerReportWebModel>> bankFilterData(Model model,
				@RequestParam String id, String voucherType, String fromDate, String toDate, HttpSession session) {
			logger.info("Method : bankFilterData starts" + id);
			JsonResponse<List<AccountLedgerReportWebModel>> jsonResponse = new JsonResponse<List<AccountLedgerReportWebModel>>();
			String orgName = "";
			String orgDivision = "";
	       try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
			} catch (Exception e) {

			}
			try {
				jsonResponse = restClient.getForObject(env.getAccountUrl() + "RestBankReportFilterData?id=" + id
						+ "&voucherType=" + voucherType + "&fromDate=" + fromDate + "&toDate=" + toDate + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
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
			logger.info("Method :bankFilterData ends----" + jsonResponse);
			return jsonResponse;
		}
		
		
		@SuppressWarnings({ "unchecked" })
		@GetMapping(value = { "/bank-report-pdf" })
		public void ledgerVoucherPdf(HttpServletResponse response, Model model, @RequestParam String id,
				String voucherType,String fromDate, String toDate,String ledgerName,String openBalance,String finalBal,HttpSession session) {
			logger.info("Method :ledgerVoucherPdf starts");

			AccountLedgerReportWebModel product = new AccountLedgerReportWebModel();
			JsonResponse<AccountLedgerReportWebModel> jsonResponse = new JsonResponse<AccountLedgerReportWebModel>();
			JsonResponse<List<AccountLedgerReportWebModel>> report = new JsonResponse<List<AccountLedgerReportWebModel>>();
			
			String orgName = "";
			String orgDivision = "";
	       try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				
			} catch (Exception e) {

			}

			try {
				report = restClient.getForObject(env.getAccountUrl() + "Restbank-report-pdf?id=" + id+ "&voucherType=" +voucherType
						+ "&fromDate=" + fromDate + "&toDate=" + toDate + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("responseReport=>>>>>>" + report);
			if (report.getBody().isEmpty()) {

				ObjectMapper mapper = new ObjectMapper();

				List<AccountLedgerReportWebModel> reportcard = mapper.convertValue(report.getBody(),
						new TypeReference<List<AccountLedgerReportWebModel>>() {
						});

				jsonResponse.setBody(product);

				Map<String, Object> data = new HashMap<String, Object>();
				
				data.put("vendorCard", reportcard);
				data.put("ledgerName", ledgerName);
				data.put("fromDate", fromDate);
				data.put("toDate", toDate);
				data.put("openBalance",openBalance);
				data.put("finalBal",finalBal);
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline; filename=vendor-accnt-stmt");
				File file;
				byte[] fileData = null;
				try {
					file = pdfGeneratorUtil.createPdf("account/bank-Book-pdf.html", data);
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

				List<AccountLedgerReportWebModel> reportcard = mapper.convertValue(report.getBody(),
						new TypeReference<List<AccountLedgerReportWebModel>>() {
						});

				jsonResponse.setBody(product);
			/*
			 * String sumDebitAmount = "0"; String sumCreditAmount = "0";
			 */
				 float sumDebitAmount = 0.0f;
			     float sumCreditAmount = 0.0f;
			     
				for(int i=0;i<reportcard.size();i++) {
					 sumDebitAmount += Float.parseFloat(reportcard.get(i).getDebitAmount().replace(",", ""));
			            sumCreditAmount += Float.parseFloat(reportcard.get(i).getCreditAmount().replace(",", ""));
				}
				logger.info("sumDebitAmount==="+sumDebitAmount);
				logger.info("sumDebitAmount==="+sumCreditAmount);
				
				
				
			/*
			 * Locale indianLocale = new Locale("en", "IN"); DecimalFormatSymbols symbols =
			 * new DecimalFormatSymbols(indianLocale); symbols.setGroupingSeparator(',');
			 * DecimalFormat decimalFormat = new DecimalFormat("##,##,##,##0.00", symbols);
			 */
			     String formattedSumDebitAmount = IndianNumberFormat.formatIndianNumber(sumDebitAmount);
			     String formattedSumCreditAmount = IndianNumberFormat.formatIndianNumber(sumCreditAmount);
			     logger.info("formattedSumDebitAmount==="+formattedSumDebitAmount);
			     logger.info("formattedSumCreditAmount==="+formattedSumCreditAmount);
			
			     float difference = sumCreditAmount - sumDebitAmount;
			     String formatDifference = IndianNumberFormat.formatIndianNumber(difference);
			     logger.info("formatDifference===" + formatDifference);
				
			     float openBalanceFloat = Float.parseFloat(openBalance);
			     float finalBalFloat = Float.parseFloat(finalBal);
			     String formattedOpenBalance = IndianNumberFormat.formatIndianNumber(openBalanceFloat);
			     String formattedFinalBal = IndianNumberFormat.formatIndianNumber(finalBalFloat);
			/*
			 * String closingAmount; closingAmount = String.format("%.2f", difference);
			 */
			
			  logger.info("formatDifference==="+formatDifference);
					
				Map<String, Object> data = new HashMap<String, Object>();
				logger.info("reportcard==="+reportcard);
				data.put("vendorCard", reportcard);
				data.put("ledgerName", ledgerName);
				data.put("fromDate", fromDate);
				data.put("toDate", toDate);
				data.put("sumDebitAmount", formattedSumDebitAmount);
				data.put("sumCreditAmount", formattedSumCreditAmount);
			    data.put("closingAmount",formatDifference);
				data.put("openBalance",formattedOpenBalance);
				data.put("finalBal",formattedFinalBal);

				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline; filename=vendor-accnt-stmt");
				File file;
				byte[] fileData = null;
				try {
					file = pdfGeneratorUtil.createPdf("account/bank-Book-pdf.html", data);
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
