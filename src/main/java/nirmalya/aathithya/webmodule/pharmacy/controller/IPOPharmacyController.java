package nirmalya.aathithya.webmodule.pharmacy.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = "pharmacy")
public class IPOPharmacyController {
	Logger logger = LoggerFactory.getLogger(IPOPharmacyController.class);

	@Autowired 
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "/pos-pharmacy" })

	
	public String getHomePage(Model model, HttpSession session) {

		logger.info("Method : Pharmacy starts");
		String userId = "";
		String organization = "";
		String orgDivision = "";
		
		
		
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		model.addAttribute("userId", userId);
		model.addAttribute("organization", organization);
		model.addAttribute("orgDivision", orgDivision);
        logger.info("Method: workpermit ends");
        
		try {

			String org = (String) session.getAttribute("ORGANIZATION");
			String orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");

			DropDownModel[] patientLists = restTemplate.getForObject(
					env.getPharmacyUrl() +"rest-get-patient-list?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			System.out.println("PatientList"+patientLists);
			List<DropDownModel> patientListt = Arrays.asList(patientLists);
			model.addAttribute("patientList", patientListt);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : Pharmacy ends");

		return "pharmacy/pos-pharmacy";
		
							
			}
	
	  @SuppressWarnings("unchecked")
	    @PostMapping("/pos-pharmacy-add")
	    public @ResponseBody JsonResponse<Object> pharmacyAdd(@RequestBody Map<String, Object> pharmacy, Model model, HttpSession session) {
	        logger.info("Method : pharmacyAdd Starts");
	        System.out.println(pharmacy);
	        JsonResponse<Object> resp = new JsonResponse<Object>();

	        String userId = "";
	        String organization = "";
	        String orgDivision = "";

	        try {

	            userId = (String) session.getAttribute("USER_ID");
	            organization = (String) session.getAttribute("ORGANIZATION");
	            orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {

	            resp = restTemplate.postForObject(env.getPharmacyUrl() + "savePharmacy", pharmacy,
	                    JsonResponse.class);
	            System.out.println(resp);
	            resp.setCode("Success");
	            resp.setMessage("pharmacyAdd added successfully"); 

	        } catch (RestClientException e) {
	            resp.setCode("Failed");
	            resp.setMessage("Error During pharmacyAdd add");
	            e.printStackTrace();
	        }
	        logger.info("Method : pharmacyAdd add End");
	        return resp;
	    }
		/*
		 * @SuppressWarnings("unchecked")
		 * 
		 * @GetMapping("pos-pharmacy-view") public @ResponseBody Object
		 * pharmacyView(HttpSession session) {
		 * logger.info("Method :pharmacyView starts"); JsonResponse<Object> resp = new
		 * JsonResponse<Object>(); String userId = ""; String organization = ""; String
		 * orgDivision = ""; try { userId = (String) session.getAttribute("USER_ID");
		 * organization = (String) session.getAttribute("ORGANIZATION"); orgDivision =
		 * (String) session.getAttribute("ORGANIZATION_DIVISION");
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } try { resp = restTemplate
		 * .getForObject(env.getPharmacyUrl() + "rest-pharmacyView?org=" + organization+
		 * "&orgDiv=" + orgDivision,JsonResponse.class);
		 * System.out.println("final view response"+resp.getBody()); } catch (Exception
		 * e) { e.printStackTrace(); } logger.info("Method :pharmacyView ends"); return
		 * resp.getBody(); }
		 */
	  
	  @SuppressWarnings("unchecked")

		@GetMapping("pos-pharmacy-view")
		public @ResponseBody Object pharmacyView(HttpSession session) {
			logger.info("Method :pharmacyView starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
	        String organization = "";
	        String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
	            organization = (String) session.getAttribute("ORGANIZATION");
	            orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				resp = restTemplate.getForObject(env.getPharmacyUrl() + "rest-pharmacyView?org=" + organization+ "&orgDiv="
                        + orgDivision,JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Unsuccess");
			} else {
				resp.setMessage("Success");
			}
			logger.info("Method :pharmacyView ends");
			return resp;
		}
	//
	  @SuppressWarnings("unchecked")
		@GetMapping("pharmacy-pdf-downloads")
		public void pharmacyPdf(HttpServletResponse response, Model model, HttpSession session,
		        @RequestParam("id") String encodedParam1) {

		    logger.info("Method : pharmacyPdf starts");
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
		        resp = restTemplate.getForObject(env.getPharmacyUrl() + "rest-pharmacyPdf?id=" + id + "&orgName="
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
		        JsonNode pdfMtNode = rootNode.path("pdfPharmacy");

		        if (pdfMtNode.isArray() && pdfMtNode.size() > 0) {
		            JsonNode firstElement = pdfMtNode.get(0);
		            data.put("pdfPharmacy", objectMapper.convertValue(firstElement, Map.class));
		            
		            // Extracting pdfVb1arDetails
		            JsonNode pdfMtDetailsNode = firstElement.path("pharmacyDetails");
		            if (pdfMtDetailsNode.isArray()) {
		                List<Map<String, Object>> pharmacyDetailsList = objectMapper.convertValue(
		                		pdfMtDetailsNode,
		                        new TypeReference<List<Map<String, Object>>>() {});
		                data.put("pharmacyDetails", pharmacyDetailsList);
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
//		    String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
//			URL getUrl = null;
//			try {
//				getUrl = new URL(logo);
//			} catch (MalformedURLException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//			String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
//			data.put("logo", "data:image/png;base64," + encodedLogoUrl);
		    response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "inline; filename=PharmacyReportPdf.pdf");
		    File file;
		    byte[] fileData = null;
		    try {
		        file = pdfGeneratorUtil.createPdf("pharmacy/pharmacyPdf.html", data);
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

		    logger.info("Method : pharmacyPdf ends");
		}
}
