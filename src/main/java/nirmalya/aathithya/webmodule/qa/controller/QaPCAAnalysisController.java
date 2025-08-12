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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.AdvanceManagementModel;
import nirmalya.aathithya.webmodule.qa.model.PACAnalysisRecordModel;
import nirmalya.aathithya.webmodule.qa.model.QaCrqsModel;
import nirmalya.aathithya.webmodule.qa.model.QaDarModel;
import nirmalya.aathithya.webmodule.qa.model.QaPcroModel;
import nirmalya.aathithya.webmodule.qa.model.QaRCFTMModel;

@Controller

@RequestMapping(value = { "qa/" })
public class QaPCAAnalysisController {

	Logger logger = LoggerFactory.getLogger(QaPCAAnalysisController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "pca-analysis-record" })

	public String qaRequest(Model model, HttpSession session) {
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
		try {
			DropDownModel[] item = restTemplate.getForObject(
					env.getProduction() + "get-Item-lists?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			System.out.println("itemlist"+itemList);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method :qaRequest starts");

		logger.info("Method : qaRequest ends");

		return "qa/pcaAnalysisRecord";
	}

	/*
	 * //Main save for Advance apply
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/pca-analysis-record-details-save")
	public @ResponseBody JsonResponse<Object> savePCAAnalysisRecord(@RequestBody PACAnalysisRecordModel model,
			HttpSession session) {
		logger.info("Method : savePCAAnalysisRecord starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		// String dateFormat = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			// dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setCreatedBy(userId);
		model.setOrganization(organization);
		model.setOrgDivision(orgDivision);
		try {
			logger.info(env.getQa() + "savePCAAnalysisRecord?" + model);
			resp = restTemplate.postForObject(env.getQa() + "savePCAAnalysisRecord", model, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (resp.getCode().equals("success")) {
			resp.setMessage("Success");
		} else {
			resp.setMessage("Unsuccess");
		}

		logger.info("Method : savePCAAnalysisRecord ends");
		logger.info("rrrrrrrrrrrrrrrrrrrrrrrrrrrreturn" + resp);
		return resp;
	}

	// getTotalPcaAnalysisView

	@SuppressWarnings("unchecked")

	@GetMapping("pca-analysis-record-details-view")
	public @ResponseBody Object getTotalPcaAnalysisView(HttpSession session) {
		logger.info("Method :getTotalPcaAnalysisView starts");
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
					env.getQa() + "getTotalPcaAnalysisView?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		System.out.println("view===" + resp);
		logger.info("Method :getTotalPcaAnalysisView ends");
		return resp;
	}

	// editCodeCrqs
	@SuppressWarnings("unchecked")
	@GetMapping("pca-analysis-record-edit")
	public @ResponseBody Object editAnalysisRecord(@RequestParam String id, HttpSession session) {
		logger.info("Method :editAnalysisRecord starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-editAnalysisRecord?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		System.out.println("edit>>>-----" + resp);
		logger.info("Method :editAnalysisRecord ends");
		return resp;
	}

	// deleteCrqs

	@SuppressWarnings("unchecked")
	@PostMapping("pca-analysis-record-delete")
	public @ResponseBody JsonResponse<Object> deleteAnalysisRecord(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteAnalysisRecord function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String org = "";
		String orgDiv = "";
		try {

			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getQa() + "rest-deleteAnalysisRecord?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteAnalysisRecord function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	// approveCRQS

	@SuppressWarnings("unchecked")
	@PostMapping("pca-analysis-record-approve-data")
	public @ResponseBody JsonResponse<Object> approvepcaanalysis(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : approvepcaanalysis function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String org = "";
		String orgDiv = "";
		try {

			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(
					env.getQa() + "rest-approvepcaanalysis?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : approvepcaanalysis function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}
//PDF

	
	@SuppressWarnings("unchecked")
	@GetMapping("pca-analysis-record-pdf-downloads")
	public void pcaAnalysisRecord(HttpServletResponse response, Model model, HttpSession session,
	        @RequestParam("id") String encodedParam1) {

	    logger.info("Method : pcaAnalysisRecord starts");
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
	        resp = restTemplate.getForObject(env.getQa() + "rest-pcaAnalysisRecordPdf?id=" + id + "&orgName="
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
	        JsonNode detailsNode = rootNode.path("details");

	        if (detailsNode.isArray() && detailsNode.size() > 0) {
	            List<Map<String, Object>> detailsList = objectMapper.convertValue(
	                    detailsNode,
	                    new TypeReference<List<Map<String, Object>>>() {});
	            data.put("details", detailsList);
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
	    response.setHeader("Content-disposition", "inline; filename=pcaAnalysisRecordPdf.pdf");
	    File file;
	    byte[] fileData = null;
	    try {
	        file = pdfGeneratorUtil.createPdf("qa/pcaAnalysisRecordPdf.html", data);
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

	    logger.info("Method : pcaAnalysisRecord ends");
	}
	

	// Product List

		@SuppressWarnings("unchecked")
		@GetMapping("pca-analysis-record-productList")
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
