package nirmalya.aathithya.webmodule.qa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.AdvanceManagementModel;
import nirmalya.aathithya.webmodule.qa.model.AshAnalysisRawDataModel;
import nirmalya.aathithya.webmodule.qa.model.FatSolAnalysisModel;
import nirmalya.aathithya.webmodule.qa.model.PACAnalysisRecordModel;
import nirmalya.aathithya.webmodule.qa.model.QaCrqsModel;
import nirmalya.aathithya.webmodule.qa.model.QaDarModel;
import nirmalya.aathithya.webmodule.qa.model.QaEvaluationBoppTapeModel;
import nirmalya.aathithya.webmodule.qa.model.QaPcroModel;
import nirmalya.aathithya.webmodule.qa.model.QaRCFTMModel;

@Controller

@RequestMapping(value = { "qa/" })
public class QaSampleReceivingAnalysisController {

	Logger logger = LoggerFactory.getLogger(QaSampleReceivingAnalysisController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "sample-receiving-analysis" })

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
		logger.info("Method :qaRequest starts");

		logger.info("Method : qaRequest ends");

		return "qa/sample-receiving-analysis";
	}

	// getaggridData

	@SuppressWarnings("unchecked")

	@GetMapping("sample-receiving-analysis-aggrid-show")
	public @ResponseBody Object getaggridDatas(HttpSession session) {
		logger.info("Method :getaggridData starts");
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
			resp = restTemplate.getForObject(env.getQa() + "rest-sample-receiving-analysis-aggrid-show?orgName=" + orgName
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
		System.out.println("view===" + resp);
		logger.info("Method :getaggridDatas ends");
		return resp;
	}

	/*
	 * //Main save
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "sample-receiving-analysis-details-save" })
	public @ResponseBody JsonResponse<Object> addSamplereceiving(@RequestBody List<QaEvaluationBoppTapeModel> av, HttpSession session) {
		logger.info("Method : addSamplereceiving function starts");
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
		for (QaEvaluationBoppTapeModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-addSamplereceiving", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addSamplereceiving function Ends");
		return resp;
	}

	// getView

	@SuppressWarnings("unchecked")

	@GetMapping("sample-receiving-analysis-details-view")
	public @ResponseBody Object getTotalSamplereceivingView(HttpSession session,@RequestParam String type) {
		logger.info("Method :getTotalSamplereceivingView starts");
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
					env.getQa() + "getTotalSamplereceivingView?orgName=" + orgName + "&orgDivision=" + orgDivision + "&type=" + type,
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
		logger.info("Method :getTotalSamplereceivingView ends");
		return resp;
	}

	// editCodeCrqs
	@SuppressWarnings("unchecked")
	@GetMapping("sample-receiving-analysis-edit")
	public @ResponseBody Object editSamplereceivingViewRecord(@RequestParam String id, HttpSession session) {
		logger.info("Method :editSamplereceivingViewRecord starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-editSamplereceivingViewRecord?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
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
		logger.info("Method :editSamplereceivingViewRecord ends");
		return resp;
	}

	// deleteCrqs

	@SuppressWarnings("unchecked")
	@PostMapping("sample-receiving-analysis-delete")
	public @ResponseBody JsonResponse<Object> deleteSamplereceivingRecord(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteSamplereceivingRecord function starts");

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
					env.getQa() + "rest-deleteSamplereceivingRecord?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteSamplereceivingRecord function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	// approveCRQS

	@SuppressWarnings("unchecked")
	@PostMapping("sample-receiving-analysis-approve-data")
	public @ResponseBody JsonResponse<Object> approveSamplereceivingRecord(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : approveSamplereceivingRecord function starts");

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
					env.getQa() + "rest-approveSamplereceivingRecord?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : approveSamplereceivingRecord function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}
//PDF
	@SuppressWarnings("unchecked")
	@GetMapping("sample-receiving-analysis-pdf-downloads")
	public void sampleReceivingAnalysisRecord(HttpServletResponse response, Model model, HttpSession session,
	        @RequestParam("id") String encodedParam1) {

	    logger.info("Method : sampleReceivingAnalysisRecord starts");
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
	        resp = restTemplate.getForObject(env.getQa() + "rest-sampleReceivingAnalysisPdf?id=" + id + "&orgName="
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
	        JsonNode detailsNode = rootNode.path("TEMP");
	        JsonNode detailsNodeRTM = rootNode.path("RTM");
	        JsonNode detailsNodeIC = rootNode.path("IC");
	        if (detailsNode.isArray() && detailsNode.size() > 0) {
	            List<Map<String, Object>> detailsList = objectMapper.convertValue(
	                    detailsNode,
	                    new TypeReference<List<Map<String, Object>>>() {});
	            data.put("TEMP", detailsList);
	            
	        }
	        if (detailsNodeRTM.isArray() && detailsNodeRTM.size() > 0) {
	            List<Map<String, Object>> detailsList1 = objectMapper.convertValue(
	            		detailsNodeRTM,
	                    new TypeReference<List<Map<String, Object>>>() {});
	            data.put("RTM", detailsList1);
	            
	        }
	        if (detailsNodeIC.isArray() && detailsNodeIC.size() > 0) {
	            List<Map<String, Object>> detailsList2 = objectMapper.convertValue(
	            		detailsNodeIC,
	                    new TypeReference<List<Map<String, Object>>>() {});
	            data.put("IC", detailsList2);
	            
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    System.out.println("data>>>>>"+data);

	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "inline; filename=sampleReceivingAnalysisPdf.pdf");
	    File file;
	    byte[] fileData = null;
	    try {
	        file = pdfGeneratorUtil.createPdf("qa/sampleReceivingAnalysisPdf.html", data);
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

	    logger.info("Method : sampleReceivingAnalysisRecord ends");
	}
}
