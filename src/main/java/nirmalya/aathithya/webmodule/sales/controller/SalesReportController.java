package nirmalya.aathithya.webmodule.sales.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.sales.model.CustomerNewModel;
import nirmalya.aathithya.webmodule.sales.model.SalesInvoiceNewModel;

@Controller
@RequestMapping(value = { "sales/" })
public class SalesReportController {
	Logger logger = LoggerFactory.getLogger(SalesReportController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@Autowired
	InvoiceController invoiceController;
	
	@GetMapping(value = { "/view-salesReport" })
	public String salesReport(Model model, HttpSession session) {
		logger.info("Method : invoiceDetails starts");
		String organization = "";
		String orgDivision = "";
		try {
		
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		logger.info("Method : invoiceDetails ends");
		return "sales/view-salesReport";
	}
	
	
	// Sales Invoice Report View.
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-salesReport-salseInvoice")
	public @ResponseBody Object viewSalesInvoiceReportData(@RequestParam String sec, String fromdate, String todate, HttpSession session) {

		logger.info("Method :viewSalesInvoiceReportData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		//String Date = DateFormatter.getStringDate(date);
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("formdate>>>>>>>>>>>>>>>>>-----" + fromdate);
		System.out.println("todate>>>>>>>>>>>>>>>>>-----" + todate);
		
		try {
			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewSalesInvoiceReportData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&sec=" + sec + "&fromdate=" + fromdate + "&todate=" + todate, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("viewSalesInvoiceReportData>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewSalesInvoiceReportData ends");

		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-saleInvoice-pdf-downloads-report")
	public void getInvoicePdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("saleInvoice") String encodedParam1,@RequestParam("organization") String encodedParam3,
			@RequestParam("orgDivision") String encodedParam4) {

		logger.info("Method : getInvoicePdfDetails starts");

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String invIdd = (new String(encodeByte1));
		
		/*
		 * byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		 * String copytype = (new String(encodeByte2));
		 */
		
		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String organization = (new String(encodeByte3));

		byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam4.getBytes());
		String orgDivision = (new String(encodeByte4));
		
		logger.info("invIdd====" + invIdd);

		List<SalesInvoiceNewModel> productList = new ArrayList<SalesInvoiceNewModel>();
		try {
			SalesInvoiceNewModel[] salesInvoiceNewModel = restTemplate.getForObject(
					env.getSalesUrl() + "viewsales-invoice-viewPdf?id=" + invIdd
					+ "&organization=" + organization + "&orgDivision=" + orgDivision , SalesInvoiceNewModel[].class);
			productList = Arrays.asList(salesInvoiceNewModel);
			productList.forEach(s -> s.setSlNo(s.getSlNo()));
			int count = 0;
			for (SalesInvoiceNewModel m : salesInvoiceNewModel) {
				count++;
				m.setSlNo(count);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String id = productList.get(0).getCustId();
		logger.info("id====" + id);
		JsonResponse<CustomerNewModel> jsonResponse = new JsonResponse<CustomerNewModel>();
		try {
			jsonResponse = restTemplate.getForObject(env.getSalesUrl() + "getCustomerAddressById?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		CustomerNewModel reimModel = mapper.convertValue(jsonResponse.getBody(), new TypeReference<CustomerNewModel>() {
		});
		logger.info("JsonResponse====" + jsonResponse);
		logger.info("reimModel====" + reimModel);
		logger.info("productList====" + productList);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("invoice", productList);

		// String logo = "";
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);
		//data.put("copytype", copytype);
		data.put("buyer", reimModel);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=salesInvoice.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("sales/sales-invoice-pdf", data);
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
		// logger.info("REsp" + jsonResponse);
		logger.info("Method : getInvoicePdfDetails ends");
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-salesReport-salseInvoice-dtls")
	public @ResponseBody Object viewSalesReportDtls(@RequestParam String id, HttpSession session) {

		logger.info("Method :viewSalesReportDtls starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		//String Date = DateFormatter.getStringDate(date);
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(id);
		
		try {

			resp = restTemplate.getForObject(env.getSalesUrl() + "rest-viewSalesReportDtls?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id  , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("viewSalesReportDtls>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewSalesReportDtls ends");

		return resp;
	}

}
