package nirmalya.aathithya.webmodule.qa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.PhMeterCallibrationRecordModel;
import nirmalya.aathithya.webmodule.qa.model.RmPmReleaseStatusModel;

@Controller

@RequestMapping(value = { "qa/" })
public class PhMeterCallibrationRecordController {
	Logger logger = LoggerFactory.getLogger(PhMeterCallibrationRecordController.class);

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

	@GetMapping(value = { "ph-meter-record" })

	public String phMeterRecord(Model model, HttpSession session) {
	
		logger.info("Method :phMeterRecord starts");

		
		logger.info("Method : phMeterRecord ends");

		return "qa/ph-meter-record";
	}
	
	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "ph-meter-record-addph" })
	public @ResponseBody JsonResponse<Object> addPhDetails(@RequestBody List<PhMeterCallibrationRecordModel> av, HttpSession session) {
		logger.info("Method : addPhDetails function starts");
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
		for (PhMeterCallibrationRecordModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setIssuedDate(DateFormatter.inputDateFormat(m.getIssuedDate(), dateFormat));
			

		}
		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-addPhDetails", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addPhDetails function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("ph-meter-records-view")
	public @ResponseBody Object viewPhData(HttpSession session) {
		logger.info("Method :viewPhData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String restUrl = env.getQa() + "viewPhData?orgName="+ orgName + "&orgDivision=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "viewPhData");
		return resp;
	}
	
	@GetMapping("ph-meter-record-edit")
	public @ResponseBody Object editPhData(@RequestParam String phId, HttpSession session) {

		logger.info("Method :editPhData starts");
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
		String restUrl = env.getQa() + "rest-editPhData?phId=" + phId + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-editPhData");
		return resp;
	}
	
	@GetMapping("ph-meter-record-approve")
	public @ResponseBody Object approvePhdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :approvePhdata starts");
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
		String restUrl = env.getQa() + "rest-approvePhdata?phId=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-approvePhdata");
		return resp;
	}
	
	@GetMapping("ph-meter-record-delete")
	public @ResponseBody Object deletePhReport(@RequestParam String id, HttpSession session) {

		logger.info("Method :deletePhReport starts");
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
		
		String restUrl = env.getQa() + "rest-deletePhReport?phId=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-deletePhReport");
		return resp;
	}
//
	@SuppressWarnings("unchecked")
	@GetMapping("ph-meter-records-pdf-downloads")
	public void phMeterPdf(HttpServletResponse response, Model model, HttpSession session,
	        @RequestParam("id") String encodedParam1) {

	    logger.info("Method : phMeterPdf starts");
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
	        resp = restTemplate.getForObject(env.getQa() + "rest-phMeterPdf?id=" + id + "&orgName="
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
	        JsonNode pdfPhNode = rootNode.path("pdfPh");

	        if (pdfPhNode.isArray() && pdfPhNode.size() > 0) {
	            JsonNode firstElement = pdfPhNode.get(0);
	            data.put("pdfPh", objectMapper.convertValue(firstElement, Map.class));
	            
	            // Extracting pdfVb1arDetails
	            JsonNode pdfPhDetailsNode = firstElement.path("pdfPhDetails");
	            if (pdfPhDetailsNode.isArray()) {
	                List<Map<String, Object>> pdfPhDetailsNodeList = objectMapper.convertValue(
	                		pdfPhDetailsNode,
	                        new TypeReference<List<Map<String, Object>>>() {});
	                data.put("pdfPhDetails", pdfPhDetailsNodeList);
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
	    response.setHeader("Content-disposition", "inline; filename=phMeterPdf.pdf");
	    File file;
	    byte[] fileData = null;
	    try {
	        file = pdfGeneratorUtil.createPdf("qa/phMeterPdf.html", data);
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

	    logger.info("Method : phMeterPdf ends");
	}
	
}
