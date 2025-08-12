package nirmalya.aathithya.webmodule.grc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
@RequestMapping(value = { "grc/" })
public class FeChecklistWebController {
	Logger logger = LoggerFactory.getLogger(FeChecklistWebController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;
	
	@GetMapping(value = { "/fe-checklist" })
	public String feChecklist(Model model, HttpSession session) {
		logger.info("Method : feChecklist starts");
		 
		logger.info("Method : feChecklist ends");
		return "grc/fe-checklist";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("fe-checklist-get-master-data")
	public @ResponseBody Object getAllMasterData(HttpSession session) {

		logger.info("Method : getAllMasterData starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}
           
		try {

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-checklist-master-data?orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in getAllMasterData: ", e);
			e.printStackTrace();
		}

		logger.info("Method : getAllMasterData ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/fe-checklist-save-data")
	public @ResponseBody JsonResponse<Object> addFeChecklistData(@RequestBody Map<String, Object> checklistJsonData, HttpSession session) {
	    logger.info("Method : addFeChecklistData starts");

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
	        String url = env.getGrcUrl() + "rest-add-checklist";

	        String checklistId = (String) checklistJsonData.get("checklistId");
	        String refillingDate = (String) checklistJsonData.get("refillingDate");
	        String nextDueDate = (String) checklistJsonData.get("nextDueDate");
	        String inspectionDate = (String) checklistJsonData.get("inspectionDate");

	        List<Map<String, Object>> rows = (List<Map<String, Object>>) checklistJsonData.get("rows");

	        Map<String, Object> requestPayload = new HashMap<>();
	        requestPayload.put("checklistId", checklistId);   
	        requestPayload.put("orgName", organization);      
	        requestPayload.put("orgDiv", orgDivision);       
	        requestPayload.put("createdById", createdById);   
	        requestPayload.put("refillingDate", refillingDate);                
	        requestPayload.put("nextDueDate", nextDueDate);                
	        requestPayload.put("inspectionDate",inspectionDate);                
	        requestPayload.put("rows", rows);               

	        logger.info("Sending checklist data to the service: " + requestPayload);

	        resp = restTemplate.postForObject(url, requestPayload, JsonResponse.class);
	    } catch (Exception e) {
	        logger.error("Error in addFeChecklistData: ", e);
	        e.printStackTrace();
	    }

	    logger.info("Method : addFeChecklistData ends");
	    return resp;
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("fe-checklist-view")
	public @ResponseBody Object viewChecklistData(HttpSession session) {

		logger.info("Method : viewChecklistData starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-view-checklist-data?orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in viewChecklistData: ", e);
			e.printStackTrace();
		}

		logger.info("Method : viewChecklistData ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("fe-checklist-edit")
	public @ResponseBody Object editChecklistData(@RequestParam String checklistId,HttpSession session) {

		logger.info("Method : editChecklistData starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-edit-checklist-data?orgName=" + organization + "&orgDiv=" + orgDivision +"&checklistId="+checklistId, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in editChecklistData: ", e);
			e.printStackTrace();
		}

		logger.info("Method : editChecklistData ends");

		return resp;
	}
	
    @SuppressWarnings("unchecked")
    @GetMapping(value = { "fe-checklist-Pdf" })
    public void generatePdfForChecklist(@RequestParam String checklistId, HttpServletResponse response, HttpSession session) {
        logger.info("Method : generatePdfForChecklist starts");

        String orgName = (String) session.getAttribute("ORGANIZATION");
        String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

        JsonResponse<Object> resp = new JsonResponse<>();

        try {
            String url = env.getGrcUrl() + "rest-pdf-data?orgName=" + orgName + "&orgDivision=" + orgDivision + "&checklistId=" + checklistId;
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
                Object date = firstEntry.get("lastDateInspection");
                System.out.println("Dateeeee=====>" + date);

                // Format the date to get the month name and year
                String monthYear = null;
                if (date instanceof String) {
                    String dateString = (String) date;
                    SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");  
                    SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM, yyyy"); 

                    try {
                        Date parsedDate = inputFormat.parse(dateString);  
                        monthYear = outputFormat.format(parsedDate); 
                        System.out.println("Formatted Date: " + monthYear);  
                        data.put("formattedDate", monthYear);  
                        data.put("createdOn", date);  
                    } catch (ParseException e) {
                        logger.error("Error parsing date: " + e.getMessage());
                    }
                } else {
                    logger.error("Date is not a string: " + date);
                }

                // Extract the checklistGridData from the first entry
                if (firstEntry.containsKey("checklistGridData")) {
                    Object gridDataObject = firstEntry.get("checklistGridData");

                    // Check if gridDataObject is actually a List
                    if (gridDataObject instanceof List) {
                        List<Map<String, Object>> checklistGridData = (List<Map<String, Object>>) gridDataObject;
                        System.out.println("Checklist Data========>" + checklistGridData);
                        data.put("checklistGridData", checklistGridData);
                    } else {
                        logger.error("checklistGridData is not a List: " + gridDataObject);
                    }
                } else {
                    logger.error("checklistGridData not found in the first entry");
                }
            } else {
                logger.error("No entries found in responseData");
            }
        } catch (IOException e) {
            logger.error("Error parsing JSON response", e);
        }

        String filename = "fe-checklist.pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=" + filename);
        File file;
        byte[] fileData = null;
        try {
            file = pdfGeneratorUtil.createPdf("grc/fe-checklist-pdf", data);
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

        logger.info("Method : generatePdfForChecklist ends");
    }
    
    @SuppressWarnings("unchecked")
	@GetMapping("fe-checklist-delete")
	public @ResponseBody Object deleteChecklist(@RequestParam String checklistId, HttpSession session) {

		logger.info("Method : deleteChecklist starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-delete-checklist-data?checklistId="
					+ checklistId + "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in deleteChecklist: ", e);
			e.printStackTrace();
		}

		logger.info("Method : deleteChecklist ends");

		return resp;
	}
    
    @SuppressWarnings("unchecked")
	@GetMapping("fe-checklist-approve")
	public @ResponseBody Object approveChecklist(@RequestParam String checklistId, HttpSession session) {

		logger.info("Method : approveChecklist starts");
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

			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-approve-checklist-data?checklistId="
					+ checklistId + "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in approveChecklist: ", e);
			e.printStackTrace();
		}

		logger.info("Method : approveChecklist ends");

		return resp;
	}
}
 