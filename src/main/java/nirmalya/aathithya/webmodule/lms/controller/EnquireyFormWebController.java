package nirmalya.aathithya.webmodule.lms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping("academic")
public class EnquireyFormWebController {

	Logger logger = LoggerFactory.getLogger(EnquireyFormWebController.class);

	@Autowired
	EnvironmentVaribles env;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("enquiry")
	public String getLmsEnquireyForm() {
		logger.info("Mothod:view Enquirey Form Template page started...");
		logger.info("Mothod:view Enquirey Form Template page started...");
		return "lms/enquiry-form-template";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/enquiry-save")
	public @ResponseBody JsonResponse<Object> SaveDataEnquiry(HttpSession session, @RequestBody String data) {
		logger.info("Method : SaveDataEnquiry starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		System.out.println("data-->" + data);
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.postForObject(env.getMasterUrl() + "save-enquiry-data?userId=" + userId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, data, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : SaveDataEnquiry ends");
		return resp;

	}

	@SuppressWarnings("unchecked")
	@GetMapping("/enquiry-details-data")
	public @ResponseBody JsonResponse<Object> getEnquiryData(HttpSession session) {
		logger.info("Method : getEnquiryData For Vendor starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getEnquiryData?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getEnquiryData For Vendor ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/enquiry-details-edit")
	public @ResponseBody JsonResponse<Object> getEnquiryDataById(HttpSession session, @RequestParam String id) {
		logger.info("Method : getEnquiryDataById For Vendor starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getEditEnquiryData?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getEnquiryDataById For Vendor ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/enquiry-details-delete")
	public @ResponseBody JsonResponse<Object> deleteEnquiryData(HttpSession session, @RequestParam String id) {
		logger.info("Method : deleteEnquiryData For Vendor starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "deleteEnquiryData?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : deleteEnquiryData For Vendor ends");
		return resp;
	}

	/*
	 * @SuppressWarnings({ "unchecked" })
	 * 
	 * @GetMapping(value = { "/enquiry-details-Pdf" }) public void
	 * enquiryPdf(HttpServletResponse response, Model model, @RequestParam String
	 * id, HttpSession session) { logger.info("Method :enquiryPdf starts --->" +
	 * id); JsonResponse<Object> resp = new JsonResponse<Object>(); String userId =
	 * ""; String orgName = ""; String orgDivision = "";
	 * 
	 * try { userId = (String) session.getAttribute("USER_ID"); orgName = (String)
	 * session.getAttribute("ORGANIZATION"); orgDivision = (String)
	 * session.getAttribute("ORGANIZATION_DIVISION"); } catch (Exception e) {
	 * logger.error("Error getting session attributes", e); }
	 * 
	 * try { resp = restTemplate.getForObject(env.getMasterUrl() +
	 * "getEditEnquiryData?org=" + orgName + "&orgDiv=" + orgDivision + "&userId=" +
	 * userId + "&id=" + id, JsonResponse.class); } catch (Exception e) {
	 * logger.error("Error fetching enquiry data", e); e.printStackTrace(); }
	 * 
	 * // Parse the response data Map<String, Object> data = new HashMap<String,
	 * Object>(); data.put("orgDivision", orgDivision);
	 * 
	 * if (resp != null && resp.getBody() != null) { try { String jsonString =
	 * ((List<String>) resp.getBody()).get(0);
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); List<Map<String, Object>>
	 * enquiryList = mapper.readValue(jsonString, List.class);
	 * 
	 * if (!enquiryList.isEmpty()) { Map<String, Object> enquiryData =
	 * enquiryList.get(0); data.putAll(enquiryData);
	 * 
	 * if (enquiryData.containsKey("Subject")) { data.put("Subject",
	 * mapper.readValue((String) enquiryData.get("Subject"), List.class)); } if
	 * (enquiryData.containsKey("Guidance")) { data.put("Guidance",
	 * mapper.readValue((String) enquiryData.get("Guidance"), List.class)); } if
	 * (enquiryData.containsKey("EducationDetails")) { Object eduDetailsObj =
	 * enquiryData.get("EducationDetails");
	 * 
	 * if (eduDetailsObj instanceof String && eduDetailsObj != null && !((String)
	 * eduDetailsObj).isEmpty()) { data.put("EducationDetails",
	 * mapper.readValue((String) eduDetailsObj, List.class)); } else if
	 * (eduDetailsObj instanceof List) { data.put("EducationDetails",
	 * eduDetailsObj); } else {
	 * System.out.println("EducationDetails not in expected format: " +
	 * eduDetailsObj); } } } System.out.println("Parsed EducationDetails: " +
	 * data.get("EducationDetails"));
	 * 
	 * } catch (Exception e) { logger.error("Error parsing JSON data", e);
	 * e.printStackTrace(); } }
	 * 
	 * String filename = "ENQUIRY_FORM" + ".pdf";
	 * response.setContentType("application/pdf");
	 * response.setHeader("Content-disposition", "inline; filename=" + filename);
	 * 
	 * System.out.println("data--->" + data);
	 * 
	 * try { File file = pdfGeneratorUtil.createPdf("lms/enquiry_form_pdf", data);
	 * InputStream in = new FileInputStream(file); byte[] fileData =
	 * IOUtils.toByteArray(in); response.setContentLength(fileData.length);
	 * response.getOutputStream().write(fileData);
	 * response.getOutputStream().flush(); } catch (IOException e) {
	 * logger.error("Error generating PDF", e); e.printStackTrace(); } catch
	 * (Exception e1) { logger.error("Error in PDF generation", e1);
	 * e1.printStackTrace(); } }
	 */

	@SuppressWarnings({ "unchecked" })
	@GetMapping(value = { "/enquiry-details-Pdf" })
	public void enquiryPdf(HttpServletResponse response, Model model, @RequestParam String id, HttpSession session) {
		logger.info("Method :enquiryPdf starts --->" + id);
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error("Error getting session attributes", e);
		}

		try {
			resp = restTemplate.getForObject(env.getMasterUrl() + "getEditEnquiryData?org=" + orgName + "&orgDiv="
					+ orgDivision + "&userId=" + userId + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error fetching enquiry data", e);
			e.printStackTrace();
		}

		// Parse the response data
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("orgDivision", orgDivision);
		data.put("orgName", orgName);

		if (resp != null && resp.getBody() != null) {
		    try {
		        String jsonString = ((List<String>) resp.getBody()).get(0);
		        
		        ObjectMapper mapper = new ObjectMapper();
		        // First parse the outer structure
		        List<Map<String, Object>> outerList = mapper.readValue(jsonString, List.class);
		        
		        if (!outerList.isEmpty()) {
		            // Get the first item which contains another enquiryData list
		            Map<String, Object> outerData = outerList.get(0);
		            List<Map<String, Object>> enquiryList = (List<Map<String, Object>>) outerData.get("enquiryData");
		            
		            if (enquiryList != null && !enquiryList.isEmpty()) {
		                // Now get the actual enquiry data
		                Map<String, Object> enquiryData = enquiryList.get(0);
		                data.putAll(enquiryData);
		                
		                // Parse nested JSON strings
		                if (enquiryData.containsKey("Subject")) {
		                    data.put("Subject", mapper.readValue((String) enquiryData.get("Subject"), List.class));
		                }
		                if (enquiryData.containsKey("Guidance")) {
		                    data.put("Guidance", mapper.readValue((String) enquiryData.get("Guidance"), List.class));
		                }
		                if (enquiryData.containsKey("EducationDetails")) {
		                    String eduDetailsStr = (String) enquiryData.get("EducationDetails");
		                    if (eduDetailsStr != null && !eduDetailsStr.isEmpty()) {
		                        List<Map<String, Object>> eduDetails = mapper.readValue(eduDetailsStr, List.class);
		                        data.put("EducationDetails", eduDetails);
		                    }
		                }
		                
		                if (enquiryData.containsKey("ExamList")) {
		                    String examStr = (String) enquiryData.get("ExamList");
		                    if (examStr != null && !examStr.isEmpty()) {
		                        List<Map<String, Object>> examDetails = mapper.readValue(examStr, List.class);
		                        data.put("ExamList", examDetails);
		                    }
		                }
		            }
		        }
		        System.out.println("Parsed ExamList: " + data.get("ExamList"));
		    } catch (Exception e) {
		        logger.error("Error parsing JSON data", e);
		        e.printStackTrace();
		    }
		}

		String filename = "ENQUIRY_FORM" + ".pdf";
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + filename);

		System.out.println("Final data model: " + data);

		try {
			File file = pdfGeneratorUtil.createPdf("lms/enquiry_form_pdf", data);
			InputStream in = new FileInputStream(file);
			byte[] fileData = IOUtils.toByteArray(in);
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData);
			response.getOutputStream().flush();
		} catch (IOException e) {
			logger.error("Error generating PDF", e);
			e.printStackTrace();
		} catch (Exception e1) {
			logger.error("Error in PDF generation", e1);
			e1.printStackTrace();
		}
	}
}
