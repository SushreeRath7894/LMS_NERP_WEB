package nirmalya.aathithya.webmodule.qa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.MicroTestingModel;
import nirmalya.aathithya.webmodule.qa.model.QaDarModel;
import nirmalya.aathithya.webmodule.qa.model.RtmrRecordModel;

@Controller

@RequestMapping(value = { "qa/" })
public class RtmrRecordController {
	Logger logger = LoggerFactory.getLogger(RtmrRecordController.class);
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

	@GetMapping(value = { "rtmr-record" })

	public String rtmrReport(Model model, HttpSession session) {
		logger.info("Method :rtmrReport starts");

		
		logger.info("Method : rtmrReport ends");

		return "qa/rtmr-record";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("rtmr-record-save")
	public @ResponseBody JsonResponse<Object> saveRtmrData(HttpSession session,
			@RequestBody List<RtmrRecordModel> rtmrRecordModel) {
		logger.info("Method : saveRtmrData starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (RtmrRecordModel m : rtmrRecordModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setDate(DateFormatter.inputDateFormat(m.getDate(), dateFormat));
			m.setDateOfIssue(DateFormatter.inputDateFormat(m.getDateOfIssue(), dateFormat));
		}
		

		try {
			resp = restTemplate.postForObject(env.getQa() + "saveRtmrData", rtmrRecordModel,
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

		logger.info("Method : saveRtmrData ends");
System.out.println("dfgbfdcsxsdfgb"+resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("rtmr-record-view")
	public @ResponseBody Object viewRtmrData(HttpSession session , @RequestParam String type) {
		logger.info("Method :viewRtmrData starts");
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
					env.getQa() + "viewRtmrData?orgName=" + orgName + "&orgDivision=" + orgDivision + "&type=" + type,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewRtmrData ends");
		return resp;
	}
	@GetMapping("rtmr-record-edit")
	public @ResponseBody Object editRtmrData(@RequestParam String rtmrId, HttpSession session) {

		logger.info("Method :editRtmrData starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String restUrl = env.getQa() + "rest-editRtmrData?rtmrId=" + rtmrId + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-editRtmrData");
		return resp;
	}
	
	@GetMapping("rtmr-record-delete")
	public @ResponseBody Object deleteRtmrdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteRtmrdata starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String restUrl = env.getQa() + "rest-deleteRtmrdata?rtmrId=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-deleteRtmrdata");
		return resp;
	}
	
	
	@GetMapping("rtmr-record-approve")
	public @ResponseBody Object approveRtmrdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :approveRtmrdata starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String restUrl = env.getQa() + "rest-approveRtmrdata?rtmrId=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-approveRtmrdata");
		return resp;
	}
	
	
	// Pdf
	
	@SuppressWarnings("unchecked")
	@GetMapping("rtmr-record-pdf-downloads")
	public void getReportPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1) {

		logger.info("Method : getReportPdfDetails starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String id = (new String(encodeByte1));

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getQa() + "rest-pdfRtmrPdf?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("JsonResponse====" +resp.getBody());

		List<QaDarModel> listModel = new ArrayList<QaDarModel>();
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
			// TODO Auto-generated catch block spring security
			e2.printStackTrace();
		}
 
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("dtls", listModel);
 
		System.out.println("data====" + data);
		String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
		URL getUrl = null;
		try {
			getUrl = new URL(logo);
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
		data.put("logo", "data:image/png;base64," + encodedLogoUrl);
 
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=RefrigeratorTemperatureMonitoringRecord.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("qa/rtmr-pdf.html", data);
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

		logger.info("Method : getReportPdfDetails ends");
	}	
}
