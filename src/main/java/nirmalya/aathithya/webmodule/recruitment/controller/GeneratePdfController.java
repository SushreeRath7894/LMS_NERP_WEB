package nirmalya.aathithya.webmodule.recruitment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.NumberToWordsConverter;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.recruitment.model.OfferletterModel;

@Controller
@RequestMapping(value = "recruitment/")
public class GeneratePdfController {

	Logger logger = LoggerFactory.getLogger(GeneratePdfController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/offer-letter-pdf" })
	public void generateOfferletter(HttpServletResponse response, Model model, HttpSession session,
	        @RequestParam("candId") String encodedParam1, @RequestParam("bandid") String encodedParam2, @RequestParam("offerLetter") String encodedParam3) {
	    
	    logger.info("Method : generateOfferletter starts");

	    byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
	    String candId = (new String(encodeByte1));

	    byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
	    String bandid = (new String(encodeByte2));

	    byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
	    String offerLetterId = (new String(encodeByte3));
	    
	    String org = ""; 
	    String orgDiv = "";
	    try {
	        org = (String) session.getAttribute("ORGANIZATION"); 
	        orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
	    try {
	        jsonResponse = restTemplate.getForObject(
	                env.getRecruitment() + "viewpdf?candId=" + candId + "&bandid=" + bandid+ "&offerLetterId=" + offerLetterId+"&org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
	    } catch (RestClientException e) {
	        e.printStackTrace();
	    }
	    
	    logger.info("JsonResponse: {}", jsonResponse);

	    ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> offerLetterMap = new HashMap<>();
	    
	    try {
	        // Parsing 'body' field
	        List<String> bodyList = (List<String>) jsonResponse.getBody();
	        
	        if (bodyList != null && !bodyList.isEmpty()) {
	            List<Map<String, Object>> offerLetterList = mapper.readValue(bodyList.get(0), new TypeReference<List<Map<String, Object>>>() {});
	            if (offerLetterList != null && !offerLetterList.isEmpty()) {
	                offerLetterMap = offerLetterList.get(0); // Get first object
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    Map<String, Object> data = new HashMap<>();

	    // Fetch values from parsed JSON
	    String logo = "";
	    String sign = "";
	    String stamp = "";
	    String companyName = (String) offerLetterMap.getOrDefault("orgDivName", "");
		/*
		 * String orgAddress = (String) offerLetterMap.getOrDefault("org_address", "");
		 * String orgMail = (String) offerLetterMap.getOrDefault("org_email", "");
		 * String orgMob = (String) offerLetterMap.getOrDefault("orgMob", "");
		 */
	    String offerLetterType = (String) offerLetterMap.getOrDefault("letterType", "");
	    String ctc = offerLetterMap.get("ctc") != null ? String.valueOf(offerLetterMap.get("ctc")) : "";
	    String candidateName = offerLetterMap.get("candidateName") != null ? String.valueOf(offerLetterMap.get("candidateName")) : "";
	    String address = offerLetterMap.get("address") != null ? String.valueOf(offerLetterMap.get("address")) : "";
	    String candPhone = offerLetterMap.get("candPhone") != null ? String.valueOf(offerLetterMap.get("candPhone")) : "";
	    String jobDesignation = offerLetterMap.get("jobDesignation") != null ? String.valueOf(offerLetterMap.get("jobDesignation")) : "";
	    String joiningDate = offerLetterMap.get("joiningDate") != null ? String.valueOf(offerLetterMap.get("joiningDate")) : "";
	    String offerReleaseData = offerLetterMap.get("offerReleaseData") != null ? String.valueOf(offerLetterMap.get("offerReleaseData")) : "";
	    String orgMob = offerLetterMap.get("orgMob") != null ? String.valueOf(offerLetterMap.get("orgMob")) : "";
	    String org_email = offerLetterMap.get("org_email") != null ? String.valueOf(offerLetterMap.get("org_email")) : "";
	    String org_address = offerLetterMap.get("org_address") != null ? String.valueOf(offerLetterMap.get("org_address")) : "";
	    String orgDivName = offerLetterMap.get("orgDivName") != null ? String.valueOf(offerLetterMap.get("orgDivName")) : "";
	    String orgMsme = offerLetterMap.get("orgMsme") != null ? String.valueOf(offerLetterMap.get("orgMsme")) : "";
	    String orgCin = offerLetterMap.get("orgCin") != null ? String.valueOf(offerLetterMap.get("orgCin")) : "";
	    String orgUrl = offerLetterMap.get("orgUrl") != null ? String.valueOf(offerLetterMap.get("orgUrl")) : "";
	    String orgName = offerLetterMap.get("orgName") != null ? String.valueOf(offerLetterMap.get("orgName")) : "";

	    String baseURL = (env != null) ? env.getBaseURL() : "";

	    if (offerLetterMap != null) {
	        // Check for orgLogo
	        String orgLogo = (String) offerLetterMap.get("orgLogo");
	        if (orgLogo != null && !orgLogo.trim().isEmpty() && !"null".equals(orgLogo)) {
	            logo = baseURL + "document/document/" + orgLogo;
	        }

	        // Check for orgSign
	        String orgSign = (String) offerLetterMap.get("orgSign");
	        if (orgSign != null && !orgSign.trim().isEmpty() && !"null".equals(orgSign)) {
	            sign = baseURL + "document/document/" + orgSign;
	        }

	        // Check for orgStamp
	        String orgStamp = (String) offerLetterMap.get("orgStamp");
	        if (orgStamp != null && !orgStamp.trim().isEmpty() && !"null".equals(orgStamp)) {
	            stamp = baseURL + "document/document/" + orgStamp;
	        }
	    }

	    data.put("logo", logo);
	    data.put("sign", sign);
	    data.put("stamp", stamp);
	    data.put("companyName", companyName);
	    data.put("orgDivName", orgDivName);
	    data.put("org_address", org_address);
	    data.put("orgMob", orgMob);
	    data.put("org_email", org_email);
	    data.put("offerLetter", offerLetterMap); 
	    data.put("letterType", offerLetterType);
	    data.put("address", address);
	    data.put("candidateName", candidateName);
	    data.put("ctc", ctc);
	    data.put("candPhone", candPhone);
	    data.put("jobDesignation", jobDesignation);
	    data.put("joiningDate", joiningDate);
	    data.put("offerReleaseData", offerReleaseData);
	    data.put("orgMsme", orgMsme);
	    data.put("orgUrl", orgUrl);
	    data.put("orgCin", orgCin);
	    data.put("orgName", orgName);

	    response.setContentType("application/pdf");

	    String fileName = "GenerateOfferLetter.pdf";  
	    String templateName = "recruitment/generateOfferLetterPdf"; 

	    if ("APIL".equalsIgnoreCase(offerLetterType)) {
	    	System.out.println(offerLetterType);
	        fileName = "GenerateOfferLetterAPIL.pdf";
	        templateName = "recruitment/generateApilOfferLetterPdf"; 
	    }

	    response.setHeader("Content-disposition", "inline; filename=" + fileName);

	    File file;
	    byte[] fileData = null;
	    try {
	        file = pdfGeneratorUtil.createPdf(templateName, data); 
	        InputStream in = new FileInputStream(file);
	        fileData = IOUtils.toByteArray(in);
	        response.setContentLength(fileData.length);
	        response.getOutputStream().write(fileData);
	        response.getOutputStream().flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    logger.info("Method : generateOfferletter ends");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = { "/joining-letter-pdf","/nda-letter-pdf" })
	public void generateJoiningletter(HttpServletResponse response, Model model, HttpSession session,
	        @RequestParam("candId") String encodedParam1, @RequestParam("bandid") String encodedParam2, 
	        @RequestParam("offerLetter") String encodedParam3, HttpServletRequest request) {
	    
	    logger.info("Method : generateJoiningletter starts");

	    byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
	    String candId = (new String(encodeByte1));

	    byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
	    String bandid = (new String(encodeByte2));

	    byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
	    String offerLetterId = (new String(encodeByte3));
	    
	    String org = ""; 
	    String orgDiv = "";
	    try {
	        org = (String) session.getAttribute("ORGANIZATION"); 
	        orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    JsonResponse<Object> jsonResponse = new JsonResponse<Object>();
	    try {
	        jsonResponse = restTemplate.getForObject(
	                env.getRecruitment() + "viewpdf?candId=" + candId + "&bandid=" + bandid+ "&offerLetterId=" + offerLetterId+"&org="+org+"&orgDiv="+orgDiv, JsonResponse.class);
	    } catch (RestClientException e) {
	        e.printStackTrace();
	    }
	    
	    logger.info("JsonResponse: {}", jsonResponse);

	    ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> offerLetterMap = new HashMap<>();
	    
	    try {
	        // Parsing 'body' field
	        List<String> bodyList = (List<String>) jsonResponse.getBody();
	        
	        if (bodyList != null && !bodyList.isEmpty()) {
	            List<Map<String, Object>> offerLetterList = mapper.readValue(bodyList.get(0), new TypeReference<List<Map<String, Object>>>() {});
	            if (offerLetterList != null && !offerLetterList.isEmpty()) {
	                offerLetterMap = offerLetterList.get(0); // Get first object
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    Map<String, Object> data = new HashMap<>();

	    // Fetch values from parsed JSON
	    String logo = "";
	    String sign = "";
	    String stamp = "";
	    String companyName = (String) offerLetterMap.getOrDefault("orgDivName", "");
	    String offerLetterType = (String) offerLetterMap.getOrDefault("letterType", "");
	    String ctc = offerLetterMap.get("ctc") != null ? String.valueOf(offerLetterMap.get("ctc")) : "";
	    String candidateName = offerLetterMap.get("candidateName") != null ? String.valueOf(offerLetterMap.get("candidateName")) : "";
	    String address = offerLetterMap.get("address") != null ? String.valueOf(offerLetterMap.get("address")) : "";
	    String candPhone = offerLetterMap.get("candPhone") != null ? String.valueOf(offerLetterMap.get("candPhone")) : "";
	    String jobDesignation = offerLetterMap.get("jobDesignation") != null ? String.valueOf(offerLetterMap.get("jobDesignation")) : "";
	    String joiningDate = offerLetterMap.get("joiningDate") != null ? String.valueOf(offerLetterMap.get("joiningDate")) : "";
	    String offerReleaseData = offerLetterMap.get("offerReleaseData") != null ? String.valueOf(offerLetterMap.get("offerReleaseData")) : "";
	    String orgMob = offerLetterMap.get("orgMob") != null ? String.valueOf(offerLetterMap.get("orgMob")) : "";
	    String org_email = offerLetterMap.get("org_email") != null ? String.valueOf(offerLetterMap.get("org_email")) : "";
	    String org_address = offerLetterMap.get("org_address") != null ? String.valueOf(offerLetterMap.get("org_address")) : "";
	    String orgDivName = offerLetterMap.get("orgDivName") != null ? String.valueOf(offerLetterMap.get("orgDivName")) : "";
	    String orgMsme = offerLetterMap.get("orgMsme") != null ? String.valueOf(offerLetterMap.get("orgMsme")) : "";
	    String orgCin = offerLetterMap.get("orgCin") != null ? String.valueOf(offerLetterMap.get("orgCin")) : "";
	    String orgUrl = offerLetterMap.get("orgUrl") != null ? String.valueOf(offerLetterMap.get("orgUrl")) : "";
	    String orgName = offerLetterMap.get("orgName") != null ? String.valueOf(offerLetterMap.get("orgName")) : "";
	    String jobLocation = offerLetterMap.get("jobLocation") != null ? String.valueOf(offerLetterMap.get("jobLocation")) : "";
	    
	    String basic = offerLetterMap.get("basic") != null ? String.valueOf(offerLetterMap.get("basic")) : "";
	    String hra = offerLetterMap.get("hra") != null ? String.valueOf(offerLetterMap.get("hra")) : "";
	    String addAllowance = offerLetterMap.get("addAllowance") != null ? String.valueOf(offerLetterMap.get("addAllowance")) : "";
	    String lta = offerLetterMap.get("lta") != null ? String.valueOf(offerLetterMap.get("lta")) : "";
	    String medical = offerLetterMap.get("medical") != null ? String.valueOf(offerLetterMap.get("medical")) : "";
	    String professionalTax = offerLetterMap.get("professionalTax") != null ? String.valueOf(offerLetterMap.get("professionalTax")) : "";
	    String empEpf = offerLetterMap.get("empEpf") != null ? String.valueOf(offerLetterMap.get("empEpf")) : "";
	    String employerEpf = offerLetterMap.get("employerEpf") != null ? String.valueOf(offerLetterMap.get("employerEpf")) : "";
	    String empEsi = offerLetterMap.get("empEsi") != null ? String.valueOf(offerLetterMap.get("empEsi")) : "";
	    String incomeTax = offerLetterMap.get("incomeTax") != null ? String.valueOf(offerLetterMap.get("incomeTax")) : "";
	    String totalEarning = offerLetterMap.get("totalEarning") != null ? String.valueOf(offerLetterMap.get("totalEarning")) : "";
	    String totalDeduction = offerLetterMap.get("totalDeduction") != null ? String.valueOf(offerLetterMap.get("totalDeduction")) : "";
	    String netPay = offerLetterMap.get("netPay") != null ? String.valueOf(offerLetterMap.get("netPay")) : "";
	    String totalContribution = offerLetterMap.get("totalContribution") != null ? String.valueOf(offerLetterMap.get("totalContribution")) : "";
	    String specialAllowance = offerLetterMap.get("specialAllowance") != null ? String.valueOf(offerLetterMap.get("specialAllowance")) : "";
	    String washingAllowance = offerLetterMap.get("washingAllowance") != null ? String.valueOf(offerLetterMap.get("washingAllowance")) : "";
	    String conveyanceAllowance = offerLetterMap.get("conveyanceAllowance") != null ? String.valueOf(offerLetterMap.get("conveyanceAllowance")) : "";
	    
	    String candidatePan = offerLetterMap.get("candidatePan") != null ? String.valueOf(offerLetterMap.get("candidatePan")) : "";
	    
	    if (offerLetterMap.get("orgLogo") != null && !((String) offerLetterMap.get("orgLogo")).trim().isEmpty()) {
	        logo = env.getBaseURL() + "document/document/" + offerLetterMap.get("orgLogo");
	    }
	    if (offerLetterMap.get("orgSign") != null && !((String) offerLetterMap.get("orgSign")).trim().isEmpty() && !"null".equals(offerLetterMap.get("orgSign"))) {
	        sign = env.getBaseURL() + "document/document/" + offerLetterMap.get("orgSign");
	    }
	    if (offerLetterMap.get("orgStamp") != null && !((String) offerLetterMap.get("orgStamp")).trim().isEmpty() && !"null".equals(offerLetterMap.get("orgStamp"))) {
	        stamp = env.getBaseURL() + "document/document/" + offerLetterMap.get("orgStamp");
	    }

	    
	    String ctcInWords = "";

	    try {
	        if (!ctc.isEmpty()) {
	           // double ctcAmount = Double.parseDouble(ctc);
	            ctcInWords = NumberToWordsConverter.convert(ctc) + " Rupees Only";
	        }
	    } catch (NumberFormatException e) {
	        logger.error("Error converting CTC to words", e);
	        ctcInWords = "";
	    }
	    
	    data.put("logo", logo);
	    data.put("sign", sign);
	    data.put("stamp", stamp);
	    data.put("companyName", companyName);
	    data.put("orgDivName", orgDivName);
	    data.put("org_address", org_address);
	    data.put("orgMob", orgMob);
	    data.put("org_email", org_email);
	    data.put("offerLetter", offerLetterMap); 
	    data.put("letterType", offerLetterType);
	    data.put("address", address);
	    data.put("candidateName", candidateName);
	    data.put("candidatePan", candidatePan);
	    data.put("ctc", ctc);
	    data.put("candPhone", candPhone);
	    data.put("jobDesignation", jobDesignation);
	    data.put("joiningDate", joiningDate);
	    data.put("offerReleaseData", offerReleaseData);
	    data.put("orgMsme", orgMsme);
	    data.put("orgUrl", orgUrl);
	    data.put("orgCin", orgCin);
	    data.put("orgName", orgName);
	    data.put("ctcInWords", ctcInWords);
	    data.put("jobLocation", jobLocation);
	    data.put("basic", basic);
	    data.put("hra", hra);
	    data.put("addAllowance", addAllowance);
	    data.put("lta", lta);
	    data.put("medical", medical);
	    data.put("professionalTax", professionalTax);
	    data.put("empEpf", empEpf);
	    data.put("employerEpf", employerEpf);
	    data.put("empEsi", empEsi);
	    data.put("incomeTax", incomeTax);
	    data.put("totalEarning", totalEarning);
	    data.put("totalDeduction", totalDeduction);
	    data.put("netPay", netPay);
	    data.put("totalContribution", totalContribution);
	    data.put("conveyanceAllowance", conveyanceAllowance);
	    data.put("washingAllowance", washingAllowance);
	    data.put("specialAllowance", specialAllowance);
	    
	    data.put("ybasic", parseDoubleOrZero(basic) * 12);
	    data.put("yhra", parseDoubleOrZero(hra) * 12);
	    data.put("yaddAllowance", parseDoubleOrZero(addAllowance) * 12);
	    data.put("ylta", parseDoubleOrZero(lta) * 12);
	    data.put("ymedical", parseDoubleOrZero(medical) * 12);
	    data.put("yprofessionalTax", parseDoubleOrZero(professionalTax) * 12);
	    data.put("yempEpf", parseDoubleOrZero(empEpf) * 12);
	    data.put("yempEsi", parseDoubleOrZero(empEsi) * 12);
	    data.put("yincomeTax", parseDoubleOrZero(incomeTax) * 12);
	    data.put("ytotalEarning", parseDoubleOrZero(totalEarning) * 12);
	    data.put("ytotalDeduction", parseDoubleOrZero(totalDeduction) * 12);
	    data.put("ynetPay", parseDoubleOrZero(netPay) * 12);
	    data.put("ytotalContribution", parseDoubleOrZero(totalContribution) * 12);
	    data.put("yconveyanceAllowance", parseDoubleOrZero(conveyanceAllowance) * 12);
	    data.put("ywashingAllowance", parseDoubleOrZero(washingAllowance) * 12);
	    data.put("yspecialAllowance", parseDoubleOrZero(specialAllowance) * 12);
	    data.put("yemployerEpf", parseDoubleOrZero(employerEpf) * 12);
	    data.put("mctc", parseDoubleOrZero(ctc) / 12);
	    response.setContentType("application/pdf");
	    
	    String requestURI = request.getRequestURI();
	    boolean isJoiningLetter = requestURI.endsWith("/joining-letter-pdf");
	    boolean isNdaLetter = requestURI.endsWith("/nda-letter-pdf");

	    if (isJoiningLetter) {
	    	String fileName = "GenerateJoiningLetter.pdf";  
		    String templateName = "recruitment/generateJoiningLetterPdf"; 

		    if ("APIL".equalsIgnoreCase(offerLetterType)) {
		    	System.out.println(offerLetterType);
		        fileName = "GenerateJoiningLetterAPIL.pdf";
		        templateName = "recruitment/generateApilJoiningLetter"; 
		    }

		    response.setHeader("Content-disposition", "inline; filename=" + fileName);

		    File file;
		    byte[] fileData = null;
		    try {
		        file = pdfGeneratorUtil.createPdf(templateName, data); 
		        InputStream in = new FileInputStream(file);
		        fileData = IOUtils.toByteArray(in);
		        response.setContentLength(fileData.length);
		        response.getOutputStream().write(fileData);
		        response.getOutputStream().flush();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

	    } else if (isNdaLetter) {
	    	String fileName = "GenerateNdaLetter.pdf";  
		    String templateName = "recruitment/generateApilNdaLetter"; 


		    response.setHeader("Content-disposition", "inline; filename=" + fileName);

		    File file;
		    byte[] fileData = null;
		    try {
		        file = pdfGeneratorUtil.createPdf(templateName, data); 
		        InputStream in = new FileInputStream(file);
		        fileData = IOUtils.toByteArray(in);
		        response.setContentLength(fileData.length);
		        response.getOutputStream().write(fileData);
		        response.getOutputStream().flush();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	    }
	    
	    logger.info("Method : generateJoiningletter ends");
	}
	
	private double parseDoubleOrZero(String value) {
	    if (value == null || value.trim().isEmpty()) {
	        return 0.0;
	    }
	    try {
	        return Double.parseDouble(value);
	    } catch (NumberFormatException e) {
	        return 0.0; // Fallback if value is not a valid number
	    }
	}

}
