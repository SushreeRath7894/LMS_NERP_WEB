package nirmalya.aathithya.webmodule.qa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.HorlicksAnalysisWebModel;
import nirmalya.aathithya.webmodule.qa.model.RmPmReleaseStatusModel;

@Controller

@RequestMapping(value = { "qa/" })
public class HorlicksAnalysisRecordController {
	Logger logger = LoggerFactory.getLogger(HorlicksAnalysisRecordController.class);

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

	@GetMapping(value = { "horlicks-analysis-record" })

	public String horlicksAnalysisRecord(Model model, HttpSession session) {
	
		logger.info("Method :mediaDecontamination starts");
	

		logger.info("Method : horlicksAnalysisRecord ends");

		return "qa/horlicks-analysis-record";
	}
	//View
	@SuppressWarnings("unchecked")
	@GetMapping("horlicks-analysis-record-view")
	public @ResponseBody Object horlicksAnalysisView(HttpSession session) {
		logger.info("Method :horlicksAnalysisView starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String restUrl = env.getQa() + "horlicksAnalysisView?orgName="+ orgName + "&orgDivision=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "horlicksAnalysisView");
		return resp;
	}
//ADD
	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "horlicks-analysis-record-add" })
	public @ResponseBody JsonResponse<Object> addHorlicks(@RequestBody List<HorlicksAnalysisWebModel> av, HttpSession session) {
		logger.info("Method : addHorlicks function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		String dateFormat = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {

		}
		for (HorlicksAnalysisWebModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setIssuedDate(DateFormatter.getStringDate(m.getIssuedDate()));
			//m.setDateOfReceipt(DateFormatter.getStringDate(m.getDateOfReceipt()));;
			

		}
		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-addhorlicksAnalysis", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addHorlicks function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}
//EDIT
	@GetMapping("horlicks-analysis-record-edit")
	public @ResponseBody Object editHorlicks(@RequestParam String horlicksId, HttpSession session) {

		logger.info("Method :editHorlicks starts");
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
		String restUrl = env.getQa() + "rest-editHorlicks?horlicksId=" + horlicksId + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-editHorlicks");
		return resp;
	}
//Delete
	@GetMapping("horlicks-analysis-record-delete")
	public @ResponseBody Object deleteHorlicks(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteHorlicks starts");
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
		
		String restUrl = env.getQa() + "rest-deleteHorlicks?id=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-deleteHorlicks");
		return resp;
	}
//Approve
	@GetMapping("horlicks-analysis-record-approve")
	public @ResponseBody Object approveHorlicksData(@RequestParam String id, HttpSession session) {

		logger.info("Method :approveHorlicksData starts");
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
		String restUrl = env.getQa() + "rest-approveHorlicksData?id=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-approveHorlicksData");
		return resp;
	}
//
	@SuppressWarnings("unchecked")
	@GetMapping("horlicks-analysis-record-pdf-downloads")
	public void horlicsAnalysisPdf(HttpServletResponse response, Model model, HttpSession session,
	        @RequestParam("id") String encodedParam1) {

	    logger.info("Method : horlicsAnalysisPdf starts");
	    String orgName = "";
	    String orgDivision = "";
	    try {
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        logger.error(e.getMessage());
	    }

	    byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam1.getBytes());
	    String id = (new String(encodeByte3));

	    System.out.println("JSON===== === " + id);
	    JsonResponse<Object> resp = new JsonResponse<Object>();

	    try {
	        resp = restTemplate.getForObject(env.getQa() + "rest-horlicsAnalysisPdf?id=" + id + "&orgName="
	                + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
	        System.out.println("JSON Obj === " + resp);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    logger.info("tefeg" + resp);

	    Map<String, Object> data = new HashMap<String, Object>();

	    System.out.println("Get Data === " + resp.getBody().toString());

	    // Parsing the JSON data
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        JsonNode rootNode = objectMapper.readTree(resp.getBody().toString());
	        JsonNode pdfHorlicksdataNode = rootNode.path("pdfHorlicksdata");

	        if (pdfHorlicksdataNode.isArray() && pdfHorlicksdataNode.size() > 0) {
	            JsonNode firstElement = pdfHorlicksdataNode.get(0);
	            data.put("pdfHorlicksdata", objectMapper.convertValue(firstElement, Map.class));
	            
	            // Extracting pdfHorlicksDetails
	            JsonNode pdfHorlicksDetailsNode = firstElement.path("pdfHorlicksDetails");
	            if (pdfHorlicksDetailsNode.isArray()) {
	                List<Map<String, Object>> pdfHorlicksDetailsNodeList = objectMapper.convertValue(
	                		pdfHorlicksDetailsNode,
	                        new TypeReference<List<Map<String, Object>>>() {});
	                data.put("pdfHorlicksDetails", pdfHorlicksDetailsNodeList);
	            }

	            // Extracting pdfHorlicksDetailss
	            JsonNode pdfHorlicksDetailssNode = firstElement.path("pdfHorlicksDetailss");
	            if (pdfHorlicksDetailssNode.isArray()) {
	                List<Map<String, Object>> pdfHorlicksDetailssNodeList = objectMapper.convertValue(
	                		pdfHorlicksDetailssNode,
	                        new TypeReference<List<Map<String, Object>>>() {});
	                data.put("pdfHorlicksDetailss", pdfHorlicksDetailssNodeList);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
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
	    response.setHeader("Content-disposition", "inline; filename=horlicsAnalysisPdf.pdf");
	    File file;
	    byte[] fileData = null;
	    try {
	        file = pdfGeneratorUtil.createPdf("qa/horlicsAnalysisPdf.html", data);
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

	    logger.info("Method : horlicsAnalysisPdf ends");
	}

}
