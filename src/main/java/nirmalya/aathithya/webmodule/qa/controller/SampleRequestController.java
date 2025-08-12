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

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;

@Controller

@RequestMapping(value = { "production/" })
public class SampleRequestController {

	Logger logger = LoggerFactory.getLogger(SampleRequestController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "sample-request" })

	public String qaRequest(Model model, HttpSession session) {
		logger.info("Method :qaRequest starts");

		logger.info("Method : qaRequest ends");

		return "qa/sample-request";
	}

	// View sample-Request-Data

	@SuppressWarnings("unchecked")
	@GetMapping("sample-request-view")
	public @ResponseBody Object viewSampleRequestData(HttpSession session) {

		logger.info("Method :viewSampleRequestData starts");
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

			resp = restTemplate.getForObject(env.getProduction() + "rest-viewSampleRequestData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("viewSampleRequestData>>>>>>>>>>>>>>>>>-----" + resp);
		logger.info("Method :viewSampleRequestData ends");

		return resp;
	}

	// Child_view.

	@SuppressWarnings("unchecked")
	@GetMapping("sample-request-detls")
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

	// Add Amount.
	@SuppressWarnings({ "unchecked", "deprecation" })
	@GetMapping(value = "sample-request-sampleAmt")
	public @ResponseBody JsonResponse<Object> sampleAmt(@RequestParam String id, String sampleAmt, String reqId,
			String itemName, String tQuant, String itemUnit, HttpSession session) {
		logger.info("Method : sampleAmt function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String Qrfile = "QR" + new Date().getTime() + ".png";

		try {
			res = restTemplate.getForObject(
					env.getProduction() + "rest-add-sampleAmt?id=" + id + "&sampleAmt=" + sampleAmt + "&reqId=" + reqId
							+ "&QrCode=" + Qrfile + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (res.getCode().equals("success")) {
			 res.setMessage("Success");

			String qrdata = "Request Id : " + reqId + "\nSKU Id : " + id + "\nItem Name : " + itemName
					+ "\nItem Quantity : " + tQuant + " " + itemUnit + "\nSample Quantity : " + sampleAmt;
			try {
				String qrCodeData = qrdata;
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

		} else {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		}

		logger.info("Method : sampleAmt function Ends");

		return res;

	}

}
