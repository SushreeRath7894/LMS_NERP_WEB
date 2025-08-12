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
import nirmalya.aathithya.webmodule.qa.model.LtmrRecordModel;
import nirmalya.aathithya.webmodule.qa.model.VbarModel;

@Controller

@RequestMapping(value = { "qa/" })
public class VbarController {
	Logger logger = LoggerFactory.getLogger(VbarController.class);
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

	@GetMapping(value = { "vb1ar-record" })

	public String ltmrReport(Model model, HttpSession session) {
		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
		/*
		 * try { DropDownModel[] item = restTemplate.getForObject( env.getProduction() +
		 * "get-Item-lists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
		 * List<DropDownModel> itemList = Arrays.asList(item);
		 * System.out.println("itemlist"+itemList); model.addAttribute("itemList",
		 * itemList); } catch (RestClientException e) { e.printStackTrace(); }
		 */
		logger.info("Method :vb1ar starts");

		
		logger.info("Method : vb1ar ends");

		return "qa/vb1ar-record";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("vb1ar-record-save")
	public @ResponseBody JsonResponse<Object> saveVb1arData(HttpSession session,
			@RequestBody List<VbarModel> vbarModel) {
		logger.info("Method : saveVb1arData starts");

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

		for (VbarModel m : vbarModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			m.setDate(DateFormatter.inputDateFormat(m.getDate(), dateFormat));
			m.setDateOfIssue(DateFormatter.inputDateFormat(m.getDateOfIssue(), dateFormat));
		}
		

		try {
			resp = restTemplate.postForObject(env.getQa() + "saveVb1arData", vbarModel,
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

		logger.info("Method : saveVb1arData ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("vb1ar-record-view")
	public @ResponseBody Object viewVb1arData(HttpSession session) {
		logger.info("Method :viewVb1arData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String restUrl = env.getQa() + "viewVb1arData?orgName="+ orgName + "&orgDivision=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "viewVb1arData");
		return resp;
	}
	
	@GetMapping("vb1ar-record-edit")
	public @ResponseBody Object editVb1arData(@RequestParam String vb1arId, HttpSession session) {

		logger.info("Method :editVb1arData starts");
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
		String restUrl = env.getQa() + "rest-editVb1arData?vb1arId=" + vb1arId + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-editVb1arData");
		return resp;
	}
	

	@GetMapping("vb1ar-record-approve")
	public @ResponseBody Object approvevb1ardata(@RequestParam String id, HttpSession session) {

		logger.info("Method :approvevb1ardata starts");
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
		String restUrl = env.getQa() + "rest-approvevb1ardata?vb1arId=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-approvevb1ardata");
		return resp;
	}
	

	@GetMapping("vb1ar-record-delete")
	public @ResponseBody Object deletevb1ardata(@RequestParam String id, HttpSession session) {

		logger.info("Method :deletevb1ardata starts");
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
		
		String restUrl = env.getQa() + "rest-deletevb1ardata?vb1arId=" + id + "&org="+ orgName + "&orgDiv=" + orgDivision;
		resp = commonUtil.getRestAPIcall(restUrl, "rest-deletevb1ardata");
		return resp;
	}
//
	//PDF

	@SuppressWarnings("unchecked")
	@GetMapping("vb1ar-record-pdf-downloads")
	public void vitaminb1pdf(HttpServletResponse response, Model model, HttpSession session,
	        @RequestParam("id") String encodedParam1) {

	    logger.info("Method : vitaminb1pdf starts");
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
	        resp = restTemplate.getForObject(env.getQa() + "rest-vitaminB1Pdf?id=" + id + "&orgName="
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
	        JsonNode pdfVb1arNode = rootNode.path("pdfVb1ar");

	        if (pdfVb1arNode.isArray() && pdfVb1arNode.size() > 0) {
	            JsonNode firstElement = pdfVb1arNode.get(0);
	            data.put("pdfVb1ar", objectMapper.convertValue(firstElement, Map.class));
	            
	            // Extracting pdfVb1arDetails
	            JsonNode pdfVb1arDetailsNode = firstElement.path("pdfVb1arDetails");
	            if (pdfVb1arDetailsNode.isArray()) {
	                List<Map<String, Object>> pdfVb1arDetailsList = objectMapper.convertValue(
	                        pdfVb1arDetailsNode,
	                        new TypeReference<List<Map<String, Object>>>() {});
	                data.put("pdfVb1arDetails", pdfVb1arDetailsList);
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
	    response.setHeader("Content-disposition", "inline; filename=reportVitaminB1pdf.pdf");
	    File file;
	    byte[] fileData = null;
	    try {
	        file = pdfGeneratorUtil.createPdf("qa/reportVitaminB1pdf.html", data);
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

	    logger.info("Method : vitaminb1pdf ends");
	}
	
	// Product List

	@SuppressWarnings("unchecked")
	@GetMapping("vb1ar-record-productList")
	public @ResponseBody Object productList(@RequestParam String type, HttpSession session) {
		logger.info("Method :productList starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-productList?type=" + type + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		logger.info("Method :productList ends");
		return resp;
	}

}
