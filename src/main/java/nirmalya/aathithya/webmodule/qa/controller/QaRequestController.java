package nirmalya.aathithya.webmodule.qa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.LaminateModel;
import nirmalya.aathithya.webmodule.qa.model.QaPcroModel;
import nirmalya.aathithya.webmodule.qa.model.QaRequestModel;
import nirmalya.aathithya.webmodule.qa.model.QaSackModel;

@Controller

@RequestMapping(value = { "production/" })
public class QaRequestController {

	Logger logger = LoggerFactory.getLogger(QaRequestController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "qa-request" })

	public String qaRequest(Model model, HttpSession session) {
		logger.info("Method :qaRequest starts");

		logger.info("Method : qaRequest ends");

		return "qa/qa-requsted";
	}

	// View QA-Request-Data

	@SuppressWarnings("unchecked")
	@GetMapping("qa-request-view")
	public @ResponseBody Object viewQaRequestData(HttpSession session) {

		logger.info("Method :viewQaRequestData starts");
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
					env.getProduction() + "rest-viewQaRequstedData?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("QA-Request-Data>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewQaRequestData ends");

		return resp;
	}

	// Child_view.

	@SuppressWarnings("unchecked")
	@GetMapping("qa-request-detls")
	public @ResponseBody Object qaRequestDtls(@RequestParam String id, HttpSession session) {

		logger.info("Method :qaRequestDtls starts");
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
			resp = restTemplate.getForObject(env.getProduction() + "rest-qaRequestDtls?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("edit>>>-----" + resp);
		logger.info("Method :qaRequestDtls ends");

		return resp;
	}

	// Change Status.

	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping("qa-request-status-change")
	public @ResponseBody Object qaRequestChangeStatus(@RequestParam String id, String sku, String itemName,
			String totalAmt, String sampleAmt, String testStatus, HttpSession session) {

		logger.info("Method :qaRequestChangeStatus starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String Qrfile = "QR" + new Date().getTime() + ".png";
		System.out.println("sku>>>>>--------" + sku + " " + sampleAmt);
		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-qaRequestChangeStatus?id=" + id + "&QrCode="
					+ Qrfile + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getCode().equals("success")) {
			String qrdata = "Qa Id : " + id + "\nSKU / Material Id : " + sku + "\nMaterial Name : " + itemName
					+ "\nTotal Quantity : " + totalAmt + "\nSample Quantity : " + sampleAmt + "\nTest Result : "
					+ testStatus;
			try {
				String qrCodeData = qrdata;
				System.out.println("qrdata>>>>>--------" + qrdata);
				String filePath = env.getUserQrCode() + Qrfile;

				String charset = "UTF-8";// "ISO-8859-1";

				Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();

				hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
				BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
						BarcodeFormat.QR_CODE, 200, 200, hintMap);
				MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
						new File(filePath));

				logger.info("Method : qrcode function Ends");

			} catch (Exception e) {
				System.err.println(e);
			}
		}

		System.out.println("edit>>>-----" + resp);
		logger.info("Method :qaRequestChangeStatus ends");

		return resp;
	}

	// Test Dtls.

	@SuppressWarnings("unchecked")
	@GetMapping("qa-request-sampAmt-dtls")
	public @ResponseBody Object qaRequestTestDtls(@RequestParam String rid, String id, String sampleAmt,String testSts,
			HttpSession session) {

		logger.info("Method :qaRequestTestDtls starts");
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
			resp = restTemplate
					.getForObject(
							env.getProduction() + "rest-qaRequestTestDtls?rid=" + rid + "&id=" + id + "&sampleAmt="
									+ sampleAmt + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&testSts=" + testSts,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("edit>>>-----" + resp);
		logger.info("Method :qaRequestTestDtls ends");

		return resp;
	}

	// For Lami

	@SuppressWarnings("unchecked")
	@GetMapping("qa-request-sampAmt-dtls-lami")
	public @ResponseBody Object qaRequestTestDtlsLami(@RequestParam String rid, String id, String sampleAmt,
			HttpSession session) {

		logger.info("Method :qaRequestTestDtlsLami starts");
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
			resp = restTemplate
					.getForObject(
							env.getProduction() + "rest-qaRequestTestDtlsLami?rid=" + rid + "&id=" + id + "&sampleAmt="
									+ sampleAmt + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("edit>>>-----" + resp);
		logger.info("Method :qaRequestTestDtlsLami ends");

		return resp;
	}

	// For Sack

	@SuppressWarnings("unchecked")
	@GetMapping("qa-request-sampAmt-dtls-sack")
	public @ResponseBody Object qaRequestTestDtlsSack(@RequestParam String rid, String id, String sampleAmt,
			HttpSession session) {

		logger.info("Method :qaRequestTestDtlsSack starts");
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
			resp = restTemplate
					.getForObject(
							env.getProduction() + "rest-qaRequestTestDtlsSack?rid=" + rid + "&id=" + id + "&sampleAmt="
									+ sampleAmt + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
							JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("edit>>>-----" + resp);
		logger.info("Method :qaRequestTestDtlsSack ends");

		return resp;
	}

	// Test Result Submit.

	@SuppressWarnings("unchecked")
	@PostMapping("qa-request-test-Result")
	public @ResponseBody JsonResponse<Object> qaTestResultSubmit(@RequestBody QaRequestModel data,
			HttpSession session) {

		logger.info("Method :qaTestResultSubmit starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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

		data.setCreatedBy(userId);
		data.setOrgName(orgName);
		data.setOrgDiv(orgDivision);
		System.out.println("data>>>-----" + data);
		try {
			resp = restTemplate.postForObject(env.getProduction() + "rest-qaTestResultSubmit", data,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("edit>>>-----" + resp);
		logger.info("Method :qaTestResultSubmit ends");

		return resp;
	}

	// Test Result For Lami

	@SuppressWarnings("unchecked")
	@PostMapping("qa-request-test-Result-for-lami")
	public @ResponseBody JsonResponse<Object> qaTestResultSubmitForLami(@RequestBody List<LaminateModel> av,
			HttpSession session) {

		logger.info("Method :qaTestResultSubmitForLami starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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

		for (LaminateModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(orgName);
			m.setOrgDivision(orgDivision);
		}
		System.out.println("data>>>-----" + av);
		try {

			resp = restTemplate.postForObject(env.getProduction() + "rest-qaTestResultForLamiSubmit", av,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("edit>>>-----" + resp);
		logger.info("Method :qaTestResultSubmitForLami ends");

		return resp;
	}

	// Test Result For Sack

	@SuppressWarnings("unchecked")
	@PostMapping("qa-request-test-Result-for-sack")
	public @ResponseBody JsonResponse<Object> qaTestResultSubmitForSack(@RequestBody List<QaSackModel> av,
			HttpSession session) {

		logger.info("Method :qaTestResultSubmitForSack starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
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

		for (QaSackModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(orgName);
			m.setOrgDivision(orgDivision);
		}
		System.out.println("data>>>-----" + av);
		try {

			resp = restTemplate.postForObject(env.getProduction() + "rest-qaTestResultForSackSubmit", av,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("edit>>>-----" + resp);
		logger.info("Method :qaTestResultSubmitForSack ends");

		return resp;
	}

	// Pdf

	@SuppressWarnings("unchecked")
	@GetMapping("view-qa-request-pdf-downloads")
	public void getDcPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("dcId") String encodedParam1) {

		logger.info("Method : getDcPdfDetails starts");
		String orgName = "";
		String orgDivision = "";
		String logo = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			logo = (String) session.getAttribute("ORGANIZATION_LOGO");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String dcId = (new String(encodeByte1));

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-qa-test-pdf?id=" + dcId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("JsonResponse====" +resp.getBody());

		List<QaPcroModel> listModel = new ArrayList<QaPcroModel>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			listModel = mapper.readValue(resp.getBody().toString(), List.class);
		} catch (JsonParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (JsonMappingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
 
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("testResult", listModel);
 
		System.out.println("data====" + data);
		URL getUrl = null;
		try {
			getUrl = new URL(logo);
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
		data.put("logo", "data:image/png;base64," + encodedLogoUrl);
		data.put("logo", logo);
 
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=SampleTestResult.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("qa/sampleTestResultPdf.html", data);
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
		 

		logger.info("Method : getDcPdfDetails ends");
	}

	// Lami Pdf.

	@SuppressWarnings("unchecked")
	@GetMapping("view-qa-request-lami-pdf-downloads")
	public void getDcPdfDetailsLami(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("dcId") String encodedParam1) {

		logger.info("Method : getDcPdfDetailsLami starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String dcId = (new String(encodeByte1));

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-qa-test-pdf-Lami?id=" + dcId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("JsonResponse====" + resp.getBody());

		List<QaPcroModel> listModel = new ArrayList<QaPcroModel>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			listModel = mapper.readValue(resp.getBody().toString(), List.class);
		} catch (JsonParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (JsonMappingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
 
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("testResult", listModel);
 
		System.out.println("data====" + data);
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);
 
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=SampleTestResultForLami.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("qa/sampleTestResultLamiPdf.html", data);
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

		logger.info("Method : getDcPdfDetailsLami ends");
	}

	// Sack Pdf.

	@SuppressWarnings("unchecked")
	@GetMapping("view-qa-request-sack-pdf-downloads")
	public void getDcPdfDetailsSack(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("dcId") String encodedParam1) {

		logger.info("Method : getDcPdfDetailsSack starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String dcId = (new String(encodeByte1));

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-qa-test-pdf-Sack?id=" + dcId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("JsonResponse====" + resp.getBody());

		List<QaPcroModel> listModel = new ArrayList<QaPcroModel>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			listModel = mapper.readValue(resp.getBody().toString(), List.class);
		} catch (JsonParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (JsonMappingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
 
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("testResult", listModel);
 
		System.out.println("data====" + data);
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);
 
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=SampleTestResultForSack.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("qa/sampleTestResultSackPdf.html", data);
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

		logger.info("Method : getDcPdfDetailsSack ends");
	}
	

	// Search

	@SuppressWarnings("unchecked")

	@GetMapping("view-qa-request-search")
	public @ResponseBody Object qaRequestSearch(@RequestParam String searchValue,
			HttpSession session) {

		logger.info("Method :qaRequestSearch starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
System.out.println("searchValue>>>>>>>>>>>>>>>>" + searchValue);
		try {

			resp = restTemplate.getForObject(env.getProduction() + "rest-qaRequestSearch?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&searchValue=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :qaRequestSearch ends");

		// logger.info(">>>-----"+resp);

		return resp;
	}

}
