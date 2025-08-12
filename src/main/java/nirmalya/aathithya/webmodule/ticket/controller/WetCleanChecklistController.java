package nirmalya.aathithya.webmodule.ticket.controller;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;

@Controller
@RequestMapping(value = "ticket")
public class WetCleanChecklistController {
	Logger logger = LoggerFactory.getLogger(WetCleanChecklistController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("/wet-clean-checklist")
	public String wetCleanChecklistView(Model model, HttpSession session) {
		logger.info("Method : wetCleanChecklistView starts");
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

		logger.info("Method : wetCleanChecklistView ends");
		return "ticket/wet-clean-checklist";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("wet-clean-checklist-view")
	public @ResponseBody Object getWetCleanChecklistMasterData(@RequestParam String selectedPhase, HttpSession session,
			Model model) {

		logger.info("Method : getWetCleanChecklistMasterData starts");
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-wet-clean-master-data?orgName=" + organization
					+ "&orgDiv=" + orgDivision + "&selectedPhase=" + selectedPhase, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in getWetCleanChecklistMasterData: ", e);
			e.printStackTrace();
		}

		logger.info("Method : getWetCleanChecklistMasterData ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/wet-clean-checklist-save-data")
	public @ResponseBody JsonResponse<Object> addWetCleanChecklistData(
			@RequestBody Map<String, Object> checklistJsonData, HttpSession session) {
		logger.info("Method : addWetCleanChecklistData starts");

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
			String url = env.getTicketUrl() + "rest-add-wet-checklist";

			String checklistId = (String) checklistJsonData.get("checklistId");
			String issueDate = (String) checklistJsonData.get("issueDate");
			String selectedPhase = (String) checklistJsonData.get("selectedPhase");

			List<Map<String, Object>> allRowData = (List<Map<String, Object>>) checklistJsonData.get("allRowData");
			List<Map<String, Object>> allRowData2 = (List<Map<String, Object>>) checklistJsonData.get("allRowData2");
			List<Map<String, Object>> allRowData3 = (List<Map<String, Object>>) checklistJsonData.get("allRowData3");

			Map<String, Object> requestPayload = new HashMap<>();
			requestPayload.put("checklistId", checklistId);
			requestPayload.put("orgName", organization);
			requestPayload.put("orgDiv", orgDivision);
			requestPayload.put("createdById", createdById);
			requestPayload.put("issueDate", issueDate);
			requestPayload.put("selectedPhase", selectedPhase);
			requestPayload.put("allRowData", allRowData);
			requestPayload.put("allRowData2", allRowData2);
			requestPayload.put("allRowData3", allRowData3);

			logger.info("Sending checklist data to the service: " + requestPayload);

			resp = restTemplate.postForObject(url, requestPayload, JsonResponse.class);
		} catch (Exception e) {
			logger.error("Error in addWetCleanChecklistData: ", e);
			e.printStackTrace();
		}

		logger.info("Method : addWetCleanChecklistData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("wet-clean-checklist-data-view")
	public @ResponseBody Object viewWetCleanChecklist(HttpSession session) {

		logger.info("Method : viewWetCleanChecklist starts");
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

			resp = restTemplate.getForObject(
					env.getTicketUrl() + "rest-view-wet-clean-data?orgName=" + organization + "&orgDiv=" + orgDivision,
					JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in viewWetCleanChecklist: ", e);
			e.printStackTrace();
		}

		logger.info("Method : viewWetCleanChecklist ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("wet-clean-checklist-edit")
	public @ResponseBody Object wetCleanChecklistEdit(@RequestParam String checklistId,
			@RequestParam String phaseStatus, HttpSession session) {

		logger.info("Method : wetCleanChecklistEdit starts");
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

			resp = restTemplate
					.getForObject(
							env.getTicketUrl() + "rest-wet-clean-data-edit?orgName=" + organization + "&orgDiv="
									+ orgDivision + "&phaseStatus=" + phaseStatus + "&checklistId=" + checklistId,
							JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in wetCleanChecklistEdit: ", e);
			e.printStackTrace();
		}

		logger.info("Method : wetCleanChecklistEdit ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("wet-clean-checklist-delete")
	public @ResponseBody Object deleteWetCleanChecklist(@RequestParam String checklistId, HttpSession session) {

		logger.info("Method : deleteWetCleanChecklist starts");
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-delete-wet-checklist-data?checklistId="
					+ checklistId + "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in deleteWetCleanChecklist: ", e);
			e.printStackTrace();
		}

		logger.info("Method : deleteWetCleanChecklist ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("wet-clean-checklist-approve")
	public @ResponseBody Object approveWetCleanChecklist(@RequestParam String checklistId, HttpSession session) {

		logger.info("Method : approveWetCleanChecklist starts");
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

			resp = restTemplate.getForObject(env.getTicketUrl() + "rest-approve-wet-checklist-data?checklistId="
					+ checklistId + "&orgName=" + organization + "&orgDiv=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			logger.error("Error in approveWetCleanChecklist: ", e);
			e.printStackTrace();
		}

		logger.info("Method : approveWetCleanChecklist ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("wet-clean-checklist-employee-list")
	public @ResponseBody Object getEmployeeListforJob(HttpSession session) {

		logger.info("Method : getEmployeeListforJob starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String organization = "";
		String orgDivision = "";
		String userId="";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restTemplate.getForObject(env.getTicketUrl() + "getEmployeeListforJob?org="
					+ organization + "&orgDiv=" + orgDivision + "&userId=" + userId,
					JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : getEmployeeListforJob ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
    @GetMapping(value = { "wet-clean-checklist-Pdf" })
    public void generatePdfForWetChecklisr(@RequestParam String checklistId,@RequestParam String phaseStatus, HttpServletResponse response, HttpSession session) {
        logger.info("Method : generatePdfForWetChecklisr starts");

        String orgName = (String) session.getAttribute("ORGANIZATION");
        String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

        JsonResponse<Object> resp = new JsonResponse<>();

        try {
            String url = env.getTicketUrl() + "rest-wet-checklist-pdf-data?orgName=" + orgName + "&orgDivision=" + orgDivision + "&checklistId=" + checklistId + "&phaseStatus=" +phaseStatus;
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
                // Get the first object in the response
                Map<String, Object> firstEntry = responseData.get(0);
                // Extract issueDate
                data.put("formattedDate", firstEntry.get("issueDate"));
                data.put("phaseStatus", firstEntry.get("phaseStatus"));
                // Extract grid data and store it in the data map
                data.put("grid1Data", firstEntry.get("grid1Data"));
                data.put("grid2Data", firstEntry.get("grid2Data"));
                data.put("grid3Data", firstEntry.get("grid3Data"));
            }
        } catch (IOException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }

        String filename = "fe-checklist.pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=" + filename);
        File file;
        byte[] fileData = null;
        try {
            file = pdfGeneratorUtil.createPdf("ticket/wet-checklist-pdf", data);
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

        logger.info("Method : generatePdfForWetChecklisr ends");
    }

}
