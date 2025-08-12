package nirmalya.aathithya.webmodule.his.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.employee.model.ManageEmployeeModel;
import nirmalya.aathithya.webmodule.his.model.HisBookingAmbulanceModel;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.purchase.model.PurchaseQuotationModel;
import nirmalya.aathithya.webmodule.his.model.HISPathoLabModel;

@Controller
@RequestMapping(value = "his")
public class HISPatholabNewController {

	Logger logger = LoggerFactory.getLogger(HISPatholabNewController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("his-patholab")
	public String hisPatholab(Model model, HttpSession session) {
		logger.info("Method : hisPatholab starts");
		
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] gender = restTemplate.getForObject(env.getHisUrl() + "/genderList", DropDownModel[].class);
			List<DropDownModel> genderList = Arrays.asList(gender);
			model.addAttribute("genderList", genderList);
			
			DropDownModel[] country = restTemplate.getForObject(env.getHisUrl() + "/countryList",
					DropDownModel[].class);
			List<DropDownModel> countryList = Arrays.asList(country);
			model.addAttribute("countryList", countryList);
			
			DropDownModel[] patient = restTemplate.getForObject(env.getHisUrl()+ "/getAllPatientList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> patList = Arrays.asList(patient);
			model.addAttribute("patList", patList);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			DropDownModel[] bankAccountList = restTemplate.getForObject(env.getAccountUrl()
					+ "getBankAccountPaymentList?orgName=" + orgName + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> bankPaymentAccountList = Arrays.asList(bankAccountList);
			model.addAttribute("bankPaymentAccountList", bankPaymentAccountList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			DropDownModel[] costCenter = restTemplate.getForObject(env.getAccountUrl() + "/getCostCenterList",
					DropDownModel[].class);

			List<DropDownModel> ccList = Arrays.asList(costCenter);
			model.addAttribute("ccList", ccList);
		} catch (RestClientException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Method : hisPatholab ends");
		return "his/hisPatholab.html";
	}

	// view
	@SuppressWarnings("unchecked")
	@GetMapping("his-patholab-view")
	public @ResponseBody Object viewPatient(HttpSession session) {
		logger.info("Method :viewPatient starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewIPDOPDlist?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&type=PATH", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :viewPatient ends" + resp);
		return resp;
	}

//view blood sample test names

	@SuppressWarnings("unchecked")
	@GetMapping("his-patholab-bloodtest-view")
	public @ResponseBody Object viewbloodtest(HttpSession session, @RequestParam String id) {
		logger.info("Method :viewbloodtest starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-bloodtestName?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId + "&id=" + id + "&type=PATH", JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewbloodtest ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("his-patholab-sampleTest-add")
	public @ResponseBody JsonResponse<Object> saveSampleTests(HttpSession session,
			@RequestBody HISPathoLabModel addSample) {
		logger.info("Method : saveSampleTests starts" + addSample);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}

		addSample.setCreatedBy(userId);
		addSample.setOrg(orgName);
		addSample.setDiv(orgDiv);

		JSONObject jsonQr = new JSONObject();
		if (addSample.getBloddSampleId() == null || addSample.getBloddSampleId() == "") {
			logger.info("Method : QR code NOT Generated");
		} else {
			logger.info("Method : QR code function Starts");

			String testId = addSample.getBloddSampleId();
			String pId = addSample.getPatientId();
			String orderId = addSample.getOrderId();
			jsonQr.put("Test", testId);
			jsonQr.put("Patient", pId);
			jsonQr.put("Order", orderId);

			String qrdata = jsonQr.toString();
			try {
				String qrCodeData = qrdata;
				String qrName = "QR" + new Date().getTime() + ".png";
				String filePath = env.getStaffQrCode() + qrName;
				String charset = "UTF-8";// "ISO-8859-1";

				Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
				hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
				BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
						BarcodeFormat.QR_CODE, 200, 200, hintMap);
				MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
						new File(filePath));
				addSample.setQrCode(qrName);
				jsonQr.keySet().clear();
				logger.info("Method : QR code function Ends");

			} catch (Exception e) {
				System.err.println(e);
			}

		}

		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-saveSampleTests", addSample, JsonResponse.class);

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		logger.info("Method : saveSampleTests ends");

		return resp;
	}

	@PostMapping("his-patholab-upload-file")
	public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : uploadFile controller function 'post-mapping' starts");
		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("quotationPFile", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("uploadFile: " + e.getMessage());
		}

		logger.info("Method : uploadFile controller function 'post-mapping' ends");
		return response;
	}

	// view All Test Names

	@SuppressWarnings("unchecked")
	@GetMapping("his-patholab-getTestNameList")
	public @ResponseBody Object getTestNameList(HttpSession session, @RequestParam String id) {
		logger.info("Method :getTestNameList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-getTestNameList?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :getTestNameList ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("his-patholab-testInvoice-view")
	public @ResponseBody Object testInvoice(HttpSession session, @RequestParam String id) {
		logger.info("Method :testInvoice starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-testInvoice?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :testInvoice ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("his-patholab-testnmaelist-view")
	public @ResponseBody Object viewTestnmaelist(HttpSession session, @RequestParam String id) {
		logger.info("Method :viewbloodtest starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restTemplate.getForObject(env.getHisUrl() + "rest-viewTestnmaelist?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :viewTestnmaelist ends" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("his-patholab-lab-report-view")
	public @ResponseBody JsonResponse<Object> getLabTestReport(HttpSession session, @RequestParam String id) {
		logger.info("Method :getLabTestReport starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		try {
			String userId = (String) session.getAttribute("USER_ID");
			String orgName = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			
			resp = restTemplate.getForObject(env.getHisUrl() + "rest-getLabTestReportData?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :getLabTestReport ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("his-patholab-testNames-qrcode-add")
	public @ResponseBody JsonResponse<Object> saveSampleTests(HttpSession session,
			@RequestBody List<HISPathoLabModel> addSample) {
		logger.info("Method : savetestNames starts" + addSample);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		List<HISPathoLabModel> add1 = new ArrayList<HISPathoLabModel>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		for (HISPathoLabModel m : addSample) {
			m.setCreatedBy(userId);
			m.setOrg(orgName);
			m.setDiv(orgDiv);

		}
		add1 = addSample.get(0).getBloodDataList();
		JSONObject jsonQr = new JSONObject();
		if (add1 == null) {
			logger.info("Method : QR code NOT Generated");
		} else {
			for (HISPathoLabModel m : add1) {
				logger.info("Method : QR code function Starts");

				String TestName = m.getTestName();
				String patientId = m.getPatientId();
				jsonQr.put("Test Name", TestName);
				jsonQr.put("Patient", patientId);
				jsonQr.put("Id", m.getBloddSampleId());

				String qrdata = jsonQr.toString();
				try {
					String qrCodeData = qrdata;
					String qrName = "QR" + new Date().getTime() + ".png";
					String filePath = env.getStaffQrCode() + qrName;
					String charset = "UTF-8";// "ISO-8859-1";

					Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
					hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
					BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
							BarcodeFormat.QR_CODE, 200, 200, hintMap);
					MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
							new File(filePath));
					m.setQrCode(qrName);
					jsonQr.keySet().clear();
					logger.info("Method : QR code function Ends" + addSample);

				} catch (Exception e) {
					System.err.println(e);
				}

			}

		}

		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-savetestNames", addSample, JsonResponse.class);

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		logger.info("Method : savetestNames ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("his-patholab-actualValue-add")
	public @ResponseBody JsonResponse<Object> saveActualValue(HttpSession session,
			@RequestBody List<HISPathoLabModel> addSample) {
		logger.info("Method : saveActualValue starts" + addSample);
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDiv = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {

		}
		for (HISPathoLabModel m : addSample) {
			m.setCreatedBy(userId);
			m.setOrg(orgName);
			m.setDiv(orgDiv);

		}

		try {
			resp = restTemplate.postForObject(env.getHisUrl() + "rest-saveActualValue", addSample, JsonResponse.class);

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		logger.info("Method : saveActualValue ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("his-patholab-report-pdf")
	public void getInvoicePdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1)
			throws JsonParseException, JsonMappingException, JSONException, IOException {

		logger.info("Method : getItemPdfDetails starts");
		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String patientId = (new String(encodeByte3));
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();

		try {
			jsonResponse = restTemplate.getForObject(env.getHisUrl() + "view-patient-report-pdf?id=" + patientId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("tefeg" + jsonResponse.getBody());

		Map<String, Object> data = new HashMap<String, Object>();

		JSONObject jsonObject = new JSONObject(jsonResponse.getBody().toString());

		System.out.println("JSON Array === " + jsonObject);

		if (jsonObject != null) {

			JSONObject jsonObject1 = jsonObject.getJSONObject("mainDetails");

			System.out.println("ddddddddd" + jsonObject1);
			JSONArray jsonArray = jsonObject.getJSONArray("viewReportData");

			logger.info("JSON Array: " + jsonArray);

			ObjectMapper mapper1 = new ObjectMapper();

			List<HISPathoLabModel> dataa = mapper1.readValue(jsonArray.toString(),
					new TypeReference<List<HISPathoLabModel>>() {
					});
			logger.info("Parsed Data List: " + dataa);

			ObjectMapper mapper2 = new ObjectMapper();

			HISPathoLabModel dataa2 = mapper2.readValue(jsonObject1.toString(), new TypeReference<HISPathoLabModel>() {
			});

			data.put("respdata", dataa);
			data.put("maindata", dataa2);

			logger.info("Parsed Data List: " + dataa2);

//					dataa.forEach(s -> s.setSlNo(s.getSlNo()));
//					int count = 0;
//					for (VendorNewModel m : dataa) {
//						count++;
//						m.setSlNo(count);
//					}

		}

		// data.put("quotationslip", quotationslip);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=report.pdf");

		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("his/his-report-pdf", data);
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
		logger.info("Method : getItemPdfDetails ends");
	}
}
