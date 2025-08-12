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
import nirmalya.aathithya.webmodule.qa.model.QaRCFTMModel;
import nirmalya.aathithya.webmodule.qa.model.RmPmReleaseStatusModel;

@Controller

@RequestMapping(value = { "qa/" })
public class RmPmReleaseStatusController {
	Logger logger = LoggerFactory.getLogger(RmPmReleaseStatusController.class);

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

	@GetMapping(value = { "rm-pm-release-status" })

	public String rmPmReleaseStatus(Model model, HttpSession session) {
	
		logger.info("Method :rmPmReleaseStatus starts");

		
		logger.info("Method : rmPmReleaseStatus ends");

		return "qa/rm-pm-release-status";
	}
	

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "rm-pm-release-status-addrmpm" })
	public @ResponseBody JsonResponse<Object> addRCFTM(@RequestBody List<RmPmReleaseStatusModel> av, HttpSession session) {
		logger.info("Method : addRCFTM function starts");
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
		for (RmPmReleaseStatusModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setIssuedDate(DateFormatter.getStringDate(m.getIssuedDate()));
			//m.setDateOfReceipt(DateFormatter.getStringDate(m.getDateOfReceipt()));;
			

		}
		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-addrmpm", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addRCFTM function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("rm-pm-release-status-view")
	public @ResponseBody Object viewRmpmData(HttpSession session) {
		logger.info("Method :viewRmpmData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String restUrl = env.getQa() + "viewRmpmData?orgName="+ orgName + "&orgDivision=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "viewRmpmData");
		return resp;
	}
	
	
	@GetMapping("rm-pm-release-status-edit")
	public @ResponseBody Object editRmpmData(@RequestParam String rmPmId, HttpSession session) {

		logger.info("Method :editRmpmData starts");
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
		String restUrl = env.getQa() + "rest-editRmpmData?rmPmId=" + rmPmId + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-editRmpmData");
		return resp;
	}
	
	@GetMapping("rm-pm-release-status-approve")
	public @ResponseBody Object approveRmpmdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :approveRmpmdata starts");
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
		String restUrl = env.getQa() + "rest-approveRmpmdata?rmPmId=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-approveRmpmdata");
		return resp;
	}
	@GetMapping("rm-pm-release-status-delete")
	public @ResponseBody Object deleteRmpmdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteRmpmdata starts");
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
		
		String restUrl = env.getQa() + "rest-deleteRmpmdata?rmPmId=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-deleteRmpmdata");
		return resp;
	}
//
	@SuppressWarnings("unchecked")
	@GetMapping("rm-pm-release-status-pdf-downloads")
	public void rmpmpdf(HttpServletResponse response, Model model, HttpSession session,
	        @RequestParam("id") String encodedParam1) {

	    logger.info("Method : rmpmpdf starts");
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
	        resp = restTemplate.getForObject(env.getQa() + "rest-rmpmPdf?id=" + id + "&orgName="
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
	        JsonNode pdfRmPmNode = rootNode.path("pdfRmpm");

	        if (pdfRmPmNode.isArray() && pdfRmPmNode.size() > 0) {
	            JsonNode firstElement = pdfRmPmNode.get(0);
	            data.put("pdfRmpm", objectMapper.convertValue(firstElement, Map.class));
	            
	            // Extracting pdfVb1arDetails
	            JsonNode pdfRmPmDetailsNode = firstElement.path("pdfRmpmDetails");
	            if (pdfRmPmDetailsNode.isArray()) {
	                List<Map<String, Object>> pdfRmPmDetailsNodeList = objectMapper.convertValue(
	                		pdfRmPmDetailsNode,
	                        new TypeReference<List<Map<String, Object>>>() {});
	                data.put("pdfRmpmDetails", pdfRmPmDetailsNodeList);
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
	    response.setHeader("Content-disposition", "inline; filename=reportRmPmPdf.pdf");
	    File file;
	    byte[] fileData = null;
	    try {
	        file = pdfGeneratorUtil.createPdf("qa/rmpmpdf.html", data);
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

	    logger.info("Method : rmpmpdf ends");
	}
}
