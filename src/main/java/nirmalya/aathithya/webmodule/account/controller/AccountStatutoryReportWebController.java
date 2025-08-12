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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.account.model.AccountCreditorLedgerModel;
import nirmalya.aathithya.webmodule.account.model.AccountLedgerReportWebModel;
import nirmalya.aathithya.webmodule.account.model.AccountPurchaseOrderWebModel;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.IndianNumberFormat;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = "account")
public class AccountStatutoryReportWebController {
	Logger logger = LoggerFactory.getLogger(AccountReportsWebController.class);
	
	@Autowired
	RestTemplate restClient;
	
	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("manage-tds-report")
	public String tdsReport(Model model, HttpSession session) {
		logger.info("Method : tdsReport starts");
		
		try {
			DropDownModel[] costCenter = restClient.getForObject(env.getAccountUrl() + "/getFiscalYearList",
					DropDownModel[].class);

			List<DropDownModel> fiscalList = Arrays.asList(costCenter);
			model.addAttribute("fiscalList", fiscalList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String orgName = "";
			String orgDivision = "";

			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			DropDownModel[] receivableLedgerList = restClient.getForObject(env.getAccountUrl() + "/rest-getTdsReceivableLedgerList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> tdsReceivableLedgerList = Arrays.asList(receivableLedgerList);
			model.addAttribute("tdsReceivableLedgerList", tdsReceivableLedgerList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			String orgName = "";
			String orgDivision = "";

			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			DropDownModel[] payableLedgerList = restClient.getForObject(env.getAccountUrl() + "/rest-getTdsPayableLedgerList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);

			List<DropDownModel> tdsPayableLedgerList = Arrays.asList(payableLedgerList);
			model.addAttribute("tdsPayableLedgerList", tdsPayableLedgerList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : tdsReport end");
		return "account/manage-tds-report";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("manage-tds-report-filteredData")
	public @ResponseBody Object viewTdsFilteredData(HttpSession session, @RequestParam String fromDate, String toDate, String tdsLedgerId,String activeStatus) {

		logger.info("Method :viewTdsFilteredData starts");
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
			resp = restClient.getForObject(env.getAccountUrl() + "rest-viewTdsFilteredData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate + "&tdsLedgerId=" + tdsLedgerId + "&activeStatus=" + activeStatus,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewTdsFilteredData ends" + resp);

		return resp;
	}
	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "tds-amount-pdf" })
	public void receiptVoucherPdf(@RequestParam String fromDate,  @RequestParam String toDate, @RequestParam String tdsLedgerId,@RequestParam String activeStatus ,@RequestParam String reportType,
	 HttpServletResponse response,Model model, HttpSession session) throws IOException {
		 logger.info("Method :tdsAmountPdf starts");
		 
		 JsonResponse<Object> tdsAmountPdf = new JsonResponse<Object>();
		 //JsonResponse<List<AccountPurchaseOrderWebModel>> tdsAmountPdf = new JsonResponse<List<AccountPurchaseOrderWebModel>>();
		 String orgName = "";
		    String orgDivision = "";
		    
		    try {
		        orgName = (String) session.getAttribute("ORGANIZATION");
		        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		    try {
		        tdsAmountPdf = restClient.getForObject(env.getAccountUrl() + "rest-viewTdsFilteredData?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&fromDate=" + fromDate + "&toDate=" + toDate + "&tdsLedgerId=" + tdsLedgerId + "&activeStatus=" + activeStatus,
						JsonResponse.class);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			
			logger.info("idss-->>>" + tdsAmountPdf);	
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> bodyMap = mapper.readValue(tdsAmountPdf.getBody().toString(), Map.class);
			List<AccountPurchaseOrderWebModel> reportcard = mapper.convertValue(bodyMap.get("viewTdsList"),
					new TypeReference<List<AccountPurchaseOrderWebModel>>() {
					});
			
			double totalDebit = reportcard.stream()
					.mapToDouble(item -> Double.parseDouble(item.getTdsAmount().replace(",", ""))).sum();
			String totalTdsAmount = IndianNumberFormat.formatIndianNumber(totalDebit);

			logger.info("reportcard===",reportcard);
			String ReportDate = fromDate +  " To " + toDate;
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("reportcard", reportcard);
			data.put("totalTdsAmount", totalTdsAmount);
			data.put("orgName", orgName);
			data.put("orgDiv", orgDivision);
			data.put("ReportDate", ReportDate);
			data.put("reportType", reportType);
			response.setContentType("application/pdf");
			String filename =  ReportDate+" Report" + ".pdf";
			
			response.setHeader("Content-disposition", "inline; filename=" + filename);
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("account/account_tds_report_pdf", data);
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

        logger.info("Method :tdsAmountPdf ends");
	}
	
}
