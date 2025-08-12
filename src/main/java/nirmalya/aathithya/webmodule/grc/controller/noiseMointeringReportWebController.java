package nirmalya.aathithya.webmodule.grc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.util.IOUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.grc.model.dcaMonitoringReportWebModel;
import nirmalya.aathithya.webmodule.grc.model.noiseMonitorReportModel;
import nirmalya.aathithya.webmodule.ticket.model.DigitalLogBookModel;
@Controller
@RequestMapping(value = "grc")
public class noiseMointeringReportWebController {
	
	Logger logger = LoggerFactory.getLogger(noiseMointeringReportWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	noiseMointeringReportWebController noiseMointeringReportWebController;

	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping(value= { "/noise-monitor" })
	public String pageIntialize(Model model, HttpSession session) {
		
		return "grc/noise-monitoring-report";
		
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("noise-monitor-data")
	public @ResponseBody Object noiseMonitorData(HttpSession session) {
		logger.info("Method :Get noiseMonitorData starts");
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
					env.getGrcUrl() + "rest-getNoiseMoniteringData?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :Get noiseMonitorData ends");
		return resp;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "monitorReport-activity-add" })
	public @ResponseBody JsonResponse<Object> monitorReportAdd(@RequestBody List<noiseMonitorReportModel> av,
			HttpSession session) {
		logger.info("Method : monitorReportAdd function starts"+av);
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
		for (noiseMonitorReportModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			
			
			
			   // Validating fields
	        if (m.getMonitorId() == null || m.getMonitorId().isEmpty()) {
	            logger.warn("Monitor ID is missing in one of the entries.");
	            // Handle missing value (skip, log, or throw an error)
	        }
	        if (m.getMonth() == null || m.getMonth().isEmpty()) {
	            logger.warn("Month is missing in one of the entries.");
	            // Handle missing value (skip, log, or throw an error)
	        }
	        if (m.getRemarks() == null || m.getRemarks().isEmpty()) {
	            logger.warn("Remarks is missing in one of the entries.");
	            // Handle missing value
	        }
	        // Add similar checks for other fields like location, persons, avghours, etc.
	        
	        // Example: checking for grid1List and grid2List
	        if (m.getGrid1List() == null || m.getGrid1List().isEmpty()) {
	            logger.warn("Grid1List is empty for Monitor ID: " + m.getGrid1List());
	        }
	        if (m.getGrid2List() == null || m.getGrid2List().isEmpty()) {
	            logger.warn("Grid2List is empty for Monitor ID: " + m.getGrid1List());
	        }

		}
		try {
			resp = restTemplate.postForObject(env.getGrcUrl() + "rest-monitorReportAdd", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : monitorReportAdd function Ends"+resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("monitorReport-activity-view")
	public @ResponseBody Object viewNoiseMonitor(HttpSession session) {
		logger.info("Method :viewNoiseMonitor starts");
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
					env.getGrcUrl() + "rest-viewNoiseMonitor?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :viewNoiseMonitor ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("monitorReport-activity-edit")
	public @ResponseBody Object editNoiseMonitor(@RequestParam String id, HttpSession session) {
		logger.info("Method :editNoiseMonitor starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-editNoiseMonitor?id=" + id + "&orgName="
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
		logger.info("Method :editNoiseMonitor ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("monitorReport-activity-delete")
	public @ResponseBody JsonResponse<Object> deleteNoiseMOnitorReport(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteNoiseMOnitorReport function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "rest-monitorReport-delete?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteNoiseMOnitorReport function Ends");
		return res;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("monitorReport-pdf-downloads")
	public void monitorReportPdf(HttpServletResponse response, Model model, HttpSession session,
	                           @RequestParam("id") String encodedParam3,@RequestParam String month,@RequestParam String year,@RequestParam String inputDate)
	        throws JsonParseException, JsonMappingException, JSONException, IOException {

	    logger.info("Method : monitorReportPdf starts");
	    String orgName = "";
	    String orgDivision = "";
	    try {
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam3.getBytes());
	    String id = new String(encodeByte5);

	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    try {
	        resp = restTemplate.getForObject(env.getGrcUrl() + "rest-monitorReportPdf?id=" + id
	        		+ "&orgName="
	                + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    logger.info("Response: " + resp);
	    Map<String, Object> data = new HashMap<String, Object>();

	    System.out.println("Get Data === " + resp.getBody());

	    if (resp.getBody() == null) {
	        System.out.println("JSON Obj === " + resp.getBody());

	        response.setContentType("application/pdf");
	        response.setHeader("Content-disposition", "inline; filename=Noise_Monitor_Report.pdf");
	        File file;
	        byte[] fileData = null;

	        try {
	            file = pdfGeneratorUtil.createPdf("grc/noise-monitorPdf.html", data);
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
	        return;  // exit the method as there's no data to process
	    }

	    String responseBody = resp.getBody().toString();
	    System.out.println("Response Body === " + responseBody);

	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode jsonNode = mapper.readTree(responseBody);

	    List<Map<String, Object>> grid1Data = new ArrayList<>();
	    List<Map<String, Object>> grid2Data = new ArrayList<>();
	    JsonNode completionData = null;
	    String orgAddress = null;

	
	    // Check if "completionData" is present and is an array
	    if (jsonNode.has("completionData")) {
	        completionData = jsonNode.get("completionData");

	        if (completionData.isArray()) {
	            // Iterate over each object inside "completionData"
	            for (JsonNode dataNode : completionData) {
	            	
	            	 if (dataNode.has("orgAddress")) {
	                     orgAddress = dataNode.get("orgAddress").asText();
	                 }
	                JsonNode grid1 = dataNode.get("grid1");
	                JsonNode grid2 = dataNode.get("grid2");

	                // Check and process the grid arrays
	                if (grid1 != null && grid1.isArray()) {
	                    grid1Data = mapper.convertValue(grid1, new TypeReference<List<Map<String, Object>>>() {});
	                }

	                if (grid2 != null && grid2.isArray()) {
	                    grid2Data = mapper.convertValue(grid2, new TypeReference<List<Map<String, Object>>>() {});
	                }
	            }
	        }
	    } else {
	        System.out.println("Completion data not found or not an array: " + responseBody);
	    }
	    
	    
	    logger.info("month Name is-->" , month);

	    String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
	    URL getUrl = null;
	    try {
	        getUrl = new URL(logo);
	    } catch (MalformedURLException e2) {
	        e2.printStackTrace();
	    }
	    String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
	    data.put("logo", "data:image/png;base64," + encodedLogoUrl);
	    data.put("grid1Data", grid1Data);
	    data.put("grid2Data", grid2Data);
	    data.put("logo", "data:image/png;base64," + encodedLogoUrl);
	    data.put("orgDivision", orgDivision);
	    data.put("completionData", completionData);
	    
	    String yearAndMonth = inputDate + " " + month + " " + year;
	    data.put("yearAndMonth", yearAndMonth);
	    data.put("orgAddress", orgAddress);
	   
	    

	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "inline; filename=microLabPdf.pdf");
	    File file;
	    byte[] fileData = null;
	    try {
	        file = pdfGeneratorUtil.createPdf("grc/noise-monitorPdf.html", data);
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

	    logger.info("Method : monitorReportPdf ends");
	}
	
	
	
	/*DCA MONITORING REPORT START*/
	
	@GetMapping(value= { "/dca-monitor" })
	public String pageIntializeForDcaMonitoring(Model model, HttpSession session) {
		
		return "grc/dca-Monitoring_Report";
		
	}

	
	@SuppressWarnings("unchecked")
	@GetMapping("dcaMonitor-activity-view")
	public @ResponseBody Object dcaMonitorData(HttpSession session) {
		logger.info("Method :Get dcaMonitorData starts");
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
					env.getGrcUrl() + "rest-getDcaMoniteringData?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :Get dcaMonitorData ends");
		return resp;
	}

	
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "dcaMonitorDataAdd-activity-add" })
	public @ResponseBody JsonResponse<Object> dcaMonitorDataAdd(@RequestBody List<dcaMonitoringReportWebModel> av,
			HttpSession session) {
		logger.info("Method : dcaMonitorDataAdd function starts"+av);
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
		for (dcaMonitoringReportWebModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
			
			

		}
		try {
			resp = restTemplate.postForObject(env.getGrcUrl() + "rest-dcamonitorReportAdd", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : dcaMonitorDataAdd function Ends"+resp);
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("dcaMonitor-report-view")
	public @ResponseBody Object dcaMonitorView(HttpSession session) {
		logger.info("Method :dcaMonitorView starts");
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
					env.getGrcUrl() + "rest-viewDcaMonitor?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :dcaMonitorView ends");
		return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("dcaMonitorReport-activity-edit")
	public @ResponseBody Object editDcaMonitor(@RequestParam String id, HttpSession session) {
		logger.info("Method :editDcaMonitor starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-editDcaMonitor?id=" + id + "&orgName="
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
		logger.info("Method :editDcaMonitor ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("dcaMonitorReport-activity-delete")
	public @ResponseBody JsonResponse<Object> deleteDcaMOnitorReport(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteDcaMOnitorReport function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		String orgName = "";
		String orgDivision = "";
		try {

			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "dcaMonitorReport-activity-delete?id=" + id + "&org=" + orgName
					+ "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteDcaMOnitorReport function Ends");
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("dcaMonitorReport-pdf-downloads")
	public void dcaMonitorReportPdf(HttpServletResponse response, Model model, HttpSession session,
	                           @RequestParam("id") String encodedParam3,@RequestParam String month,@RequestParam String year,@RequestParam String dateString)
	        throws JsonParseException, JsonMappingException, JSONException, IOException {

	    logger.info("Method : dcaMonitorReportPdf starts");
	    String orgName = "";
	    String orgDivision = "";
	    try {
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam3.getBytes());
	    String id = new String(encodeByte5);

	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    try {
	        resp = restTemplate.getForObject(env.getGrcUrl() + "rest-dcaMonitorReportPdf?id=" + id
	        		+ "&orgName="
	                + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    logger.info("Response: " + resp);
	    Map<String, Object> data = new HashMap<String, Object>();

	    System.out.println("Get Data === " + resp.getBody());

	    if (resp.getBody() == null) {
	        System.out.println("JSON Obj === " + resp.getBody());

	        response.setContentType("application/pdf");
	        response.setHeader("Content-disposition", "inline; filename=Dca Monitor_Report.pdf");
	        File file;
	        byte[] fileData = null;

	        try {
	            file = pdfGeneratorUtil.createPdf("grc/DCA-Monitor-ReportPdf.html", data);
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
	        return;  // exit the method as there's no data to process
	    }

	    String responseBody = resp.getBody().toString();
	    System.out.println("Response Body === " + responseBody);

	    ObjectMapper mapper = new ObjectMapper();
	    JsonNode jsonNode = mapper.readTree(responseBody);

	    List<Map<String, Object>> grid1Data = new ArrayList<>();
	    JsonNode completionData = null;
        int totalDeepCompliance = 0;
        int totalDeepComplianceAllocated = 0;

        int totalDeepComplianceCount = 0;
        String inputData = null;
        


	
	   
	    if (jsonNode.has("completionData")) {
	        completionData = jsonNode.get("completionData");

	        if (completionData.isArray()) {
	        	
	          
	            for (JsonNode dataNode : completionData) {
	                JsonNode grid1 = dataNode.get("grid1");
	                
	                if (dataNode.has("inputData")) {
		        		inputData = dataNode.get("inputData").asText();
	                }

	             
	                if (grid1 != null && grid1.isArray()) {
	                    grid1Data = mapper.convertValue(grid1, new TypeReference<List<Map<String, Object>>>() {});
	                    
	                    for(Map<String,Object> entry: grid1Data) {
	                    	
	                    	String deepCompliancestr = (String) entry.get("deepcompliance");
	                    	String totalDeepComplianceStr  = (String) entry.get("totaldeepcompliance");
	                    	
	                    	if (deepCompliancestr != null && !deepCompliancestr.isEmpty()) {
	                            totalDeepCompliance += Integer.parseInt(deepCompliancestr);
	                            totalDeepComplianceCount++;
	                        }
	                    	
	                    	if(totalDeepComplianceStr != null && !totalDeepComplianceStr.isEmpty()) {
	                    		totalDeepComplianceAllocated += Integer.parseInt(totalDeepComplianceStr);
	                    	}
	                    }
	                }

	               
	            }
	            
	            
	        }
	    } else {
	        System.out.println("Completion data not found or not an array: " + responseBody);
	    }
	    
	    
	    
	    logger.info("totalDeepComplianceAllocated",totalDeepComplianceAllocated);
	    logger.info("totalDeepCompliance",totalDeepCompliance);
	    
	   

	    String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
	    URL getUrl = null;
	    try {
	        getUrl = new URL(logo);
	    } catch (MalformedURLException e2) {
	        e2.printStackTrace();
	    }
	    String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
	    data.put("logo", "data:image/png;base64," + encodedLogoUrl);
	    data.put("grid1Data", grid1Data);
	    data.put("logo", "data:image/png;base64," + encodedLogoUrl);
	    data.put("orgDivision", orgDivision);
	    data.put("completionData", completionData);
	    data.put("dateString", inputData);	    
	    String [] arr = inputData.split("-");
	    String monitoryear = arr[2];
	    data.put("monitorYear", monitoryear);
	    
	    
	    data.put("totalDeepComplianceAllocated", totalDeepComplianceAllocated);
	    data.put("totalDeepCompliance", totalDeepCompliance);
	  
	    logger.info("array" + arr);
	    
	    for(String s:arr) {
	    	System.out.println(s);
	    }


	    response.setContentType("application/pdf");
	    response.setHeader("Content-disposition", "inline; filename=DCA-Monitor-Report.pdf");
	    File file;
	    byte[] fileData = null;
	    try {
	        file = pdfGeneratorUtil.createPdf("grc/dca-Monitor-ReportPdf.html", data);
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

	    logger.info("Method : dcaMonitorReportPdf ends");
	}
	@SuppressWarnings("unchecked")
	@GetMapping("noise-monitor-approve")
	public @ResponseBody Object approveNoiseReport(@RequestParam String noiseId, HttpSession session) {

		logger.info("Method : approveNoiseReport starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-approve-noise-monitor?noiseId="
					+ noiseId + "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in approveNoiseReport: ", e);
			e.printStackTrace();
		}

		logger.info("Method : approveNoiseReport ends");

		return resp;
	}
}
