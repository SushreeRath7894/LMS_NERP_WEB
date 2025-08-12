package nirmalya.aathithya.webmodule.ticket.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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
@RequestMapping(value = "ticket")
public class PerforationBladeRecordWebController {
	Logger logger = LoggerFactory.getLogger(PerforationBladeRecordWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping("/perforation-blade-record")
	public String perforationRecordView(Model model, HttpSession session) {
		logger.info("Method : perforationRecordView starts");
 

		logger.info("Method : perforationRecordView ends");
		return "ticket/perforation-blade-record";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/perforation-blade-record-save-data")
	public @ResponseBody JsonResponse<Object> addPerforationBladeRecord(@RequestBody Map<String, Object> perforationJsonData, HttpSession session) {
	    logger.info("Method : addPerforationBladeRecord starts");

	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    String organization = "";
	    String orgDivision = "";
	    String createdById = "";

	    try {
	        organization = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
	        createdById = (String) session.getAttribute("USER_ID");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    try {
	        String url = env.getTicketUrl() + "rest-add-perforation-data";
	        System.out.println("Perforation URL==========>>>>>>>"+url);

	        String recordId = (String) perforationJsonData.get("recordId");
	        String recordYear = (String) perforationJsonData.get("recordYear");
	        String recordMonth = (String) perforationJsonData.get("recordMonth");

	        List<Map<String, Object>> rows = (List<Map<String, Object>>) perforationJsonData.get("rows");

	        Map<String, Object> requestPayload = new HashMap<>();
	        requestPayload.put("recordId", recordId);   
	        requestPayload.put("orgName", organization);      
	        requestPayload.put("orgDiv", orgDivision);       
	        requestPayload.put("createdById", createdById);   
	        requestPayload.put("recordYear", recordYear);                
	        requestPayload.put("recordMonth",recordMonth);                
	        requestPayload.put("rows", rows);               

	        logger.info("Sending perforation record data to the service: " + requestPayload);

	        resp = restTemplate.postForObject(url, requestPayload, JsonResponse.class);
	    } catch (Exception e) {
	        logger.error("Error in addPerforationBladeRecord: ", e);
	        e.printStackTrace();
	    }

	    logger.info("Method : addPerforationBladeRecord ends");
	    return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("perforation-blade-record-view")
	public @ResponseBody Object viewPerforationRecord(HttpSession session) {

		logger.info("Method : viewPerforationRecord starts");
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-view-perforation-data?orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in viewPerforationRecord: ", e);
			e.printStackTrace();
		}

		logger.info("Method : viewPerforationRecord ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("perforation-blade-record-edit")
	public @ResponseBody Object editPerforationRecordData(@RequestParam String recordId,HttpSession session) {

		logger.info("Method : editPerforationRecordData starts");
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-edit-perforation-data?orgName=" + organization + "&orgDiv=" + orgDivision +"&recordId="+recordId, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in editPerforationRecordData: ", e);
			e.printStackTrace();
		}

		logger.info("Method : editPerforationRecordData ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("perforation-blade-record-delete")
	public @ResponseBody Object deletPerforationRecord(@RequestParam String recordId, HttpSession session) {

		logger.info("Method : deletPerforationRecord starts");
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-delete-perforation-data?recordId="
					+ recordId + "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in deletPerforationRecord: ", e);
			e.printStackTrace();
		}

		logger.info("Method : deletPerforationRecord ends");

		return resp;
	}
	
	    @SuppressWarnings("unchecked")
	    @GetMapping(value = { "perforation-blade-record-Pdf" })
	    public void generatePdfForPerforationRecord(@RequestParam String recordId, HttpServletResponse response, HttpSession session) {
	        logger.info("Method : generatePdfForPerforationRecord starts");

	        String orgName = (String) session.getAttribute("ORGANIZATION");
	        String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

	        JsonResponse<Object> resp = new JsonResponse<>();

	        try {
	            String url = env.getTicketUrl() + "rest-pdf-data?orgName=" + orgName + "&orgDivision=" + orgDivision + "&recordId=" + recordId;
	            resp = restTemplate.getForObject(url, JsonResponse.class);
	        } catch (Exception e) {
	            logger.error("Error fetching data for PDF generation", e);
	        }

	        logger.info("Fetched response data: " + resp);

	        // Assuming resp.getBody() returns a JSON string
	        String responseBody = (String) resp.getBody();
	        System.out.println("responseBody==================>" + responseBody);
	        Map<String, Object> data = new HashMap<>();

	        try {
	            ObjectMapper mapper = new ObjectMapper();
	            List<Map<String, Object>> responseData = mapper.readValue(responseBody, List.class);

	            if (!responseData.isEmpty()) {
	                // Get the first object
	                Map<String, Object> firstEntry = responseData.get(0);

	                // Retrieve month and year as strings
	                String year = (String) firstEntry.get("year");
	                String monthNumber = (String) firstEntry.get("month");

	                // Convert month number to month name
	                String monthName = null;
	                if (monthNumber != null) {
	                    DateFormatSymbols dfs = new DateFormatSymbols();
	                    int monthIndex = Integer.parseInt(monthNumber) - 1; // Month is 0-based in DateFormatSymbols
	                    monthName = dfs.getMonths()[monthIndex];
	                }

	                if (monthName != null && year != null) {
	                    String formattedMonthYear = monthName + ", " + year;
	                    System.out.println("Formatted Month and Year: " + formattedMonthYear);
	                    data.put("formattedDate", formattedMonthYear);
	                } else {
	                    logger.error("Month or year is missing in the response");
	                }

	                // Extract the checklistGridData from the first entry
	                if (firstEntry.containsKey("perforationGridData")) {
	                    Object gridDataObject = firstEntry.get("perforationGridData");

	                    // Check if gridDataObject is actually a List
	                    if (gridDataObject instanceof List) {
	                        List<Map<String, Object>> perforationGridData = (List<Map<String, Object>>) gridDataObject;
	                        System.out.println("Perforation Data========>" + perforationGridData);
	                        data.put("perforationGridData", perforationGridData);
	                    } else {
	                        logger.error("perforationGridData is not a List: " + gridDataObject);
	                    }
	                } else {
	                    logger.error("perforationGridData not found in the first entry");
	                }
	            } else {
	                logger.error("No entries found in responseData");
	            }
	        } catch (IOException e) {
	            logger.error("Error parsing JSON response", e);
	        }


	        String filename = "perforation-blade-record.pdf";
	        response.setContentType("application/pdf");
	        response.setHeader("Content-disposition", "inline; filename=" + filename);
	        File file;
	        byte[] fileData = null;
	        try {
	            file = pdfGeneratorUtil.createPdf("ticket/perforation-record-pdf", data);
	            InputStream in = new FileInputStream(file);
	            fileData = IOUtils.toByteArray(in);
	            response.setContentLength(fileData.length);
	            response.getOutputStream().write(fileData);
	            response.getOutputStream().flush();
	        } catch (IOException e) {
	            logger.error("Error while generating or sending PDF", e);
	        } catch (Exception e1) {
	            logger.error("Unexpected error occurred", e1);
	        }

	        logger.info("Method : generatePdfForPerforationRecord ends");
	    }
	    
	    @SuppressWarnings("unchecked")
		@GetMapping("perforation-blade-record-approve")
		public @ResponseBody Object approvePerforationRecord(@RequestParam String recordId, HttpSession session) {

			logger.info("Method : approvePerforationRecord starts");
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
				resp = restTemplate.getForObject(env.getTicketUrl() + "rest-approve-perforation-data?recordId="
						+ recordId + "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				logger.error("Error in approvePerforationRecord: ", e);
				e.printStackTrace();
			}

			logger.info("Method : approvePerforationRecord ends");

			return resp;
		}
}
