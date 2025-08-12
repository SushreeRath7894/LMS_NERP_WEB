package nirmalya.aathithya.webmodule.qa.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.QaRequestModel;

@Controller

@RequestMapping(value = { "qa/" })
public class QaRequestedOfController {
	
	Logger logger = LoggerFactory.getLogger(QaRequestedOfController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@Autowired
	CommonUtil commonUtil;
	
	@GetMapping(value = { "qa-request-of" })

	public String qaRequestedForOf(Model model, HttpSession session) {
		logger.info("Method :qaRequestedForOf starts");

		

		logger.info("Method : qaRequestedForOf ends");

		return "qa/qa-requested-of";
	}
	
	// view
	
	@SuppressWarnings("unchecked")

	@GetMapping("qa-request-of-view")
	public @ResponseBody Object getQaRequstForOfView(HttpSession session) {
		logger.info("Method :getQaRequstForOfView starts");
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
					env.getQa() + "rest-getQaRequstForOfView?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getQaRequstForOfView ends");
		return resp;
	}
	
	// Add
	
	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "qa-request-of-add" })
	public @ResponseBody JsonResponse<Object> addOfQaData(@RequestBody QaRequestModel av, HttpSession session) {
		logger.info("Method : addOfQaData function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		av.setCreatedBy(userId);
		av.setOrgName(organization);
		av.setOrgDiv(orgDivision);
		
		//System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-addOfQaData", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addOfQaData function Ends");
		//System.out.println("Final Save>>>------" + resp);
		return resp;
	}
	
	

	// Change Status.

	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping("qa-request-of-status-change")
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

}
