package nirmalya.aathithya.webmodule.grc.controller;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.grc.model.GRCReportModel;
import nirmalya.aathithya.webmodule.grc.model.ScheduledAuditPlanModel;

@Controller
@RequestMapping(value = { "grc/" })

public class AuditReviewController {
	Logger logger = LoggerFactory.getLogger(AuditMasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "audit-review" })
	public String auditMaster(Model model, HttpSession session) {
		logger.info("Method : auditReview starts");

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {

			DropDownModel[] auditeeList = restTemplate.getForObject(
					env.getGrcUrl() + "getAuditeeList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditeeList = Arrays.asList(auditeeList);
			model.addAttribute("AuditeeList", AuditeeList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : auditReview ends");
		return "grc/audit-review";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("audit-review-view")
	public @ResponseBody Object viewScheduledPlan(HttpSession session) {
		logger.info("Method :viewScheduledPlan starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		String type = "Review";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-audit-plan-schedule?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&userId=" + userId + "&type=" + type, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewScheduledPlan ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("audit-review-instance-view")
	public @ResponseBody Object viewInstanceFromSchedule(@RequestParam String scheduledId, HttpSession session) {
		logger.info("Method :viewInstanceFromSchedule starts");
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
			resp = restTemplate.getForObject(env.getGrcUrl() + "rest-audit-review-instance-view?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&scheduledId=" + scheduledId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewInstanceFromSchedule ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("audit-review-getScheduledAuditDetails")
	public @ResponseBody Object getScheduledAuditDetails(@RequestParam String scheduledId, String auditInstId,
			HttpSession session) {
		logger.info("Method :viewScheduledPlan starts");
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
					env.getGrcUrl() + "rest-get-scheduled-audit-details?scheduledId=" + scheduledId + "&auditInstId="
							+ auditInstId + "&orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewScheduledPlan ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "audit-review-submit" })
	public @ResponseBody JsonResponse<Object> addAuditProgress(@RequestBody List<ScheduledAuditPlanModel> sapList,
			HttpSession session) {
		logger.info("Method : addAuditProgress function starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error("Error retrieving session attributes: ", e);
		}

		for (ScheduledAuditPlanModel sap : sapList) {
			sap.setCreatedBy(userId);
			sap.setOrgName(organization);
			sap.setOrgDivision(orgDivision);
		}

		try {
			resp = restTemplate.postForObject(env.getGrcUrl() + "rest-audit-review-submit", sapList,
					JsonResponse.class);
		} catch (RestClientException e) {
			logger.error("Error posting audit progress: ", e);
		}

		logger.info("Method : addAuditProgress function Ends");
		return resp;
	}

	/*
	 * All Report (PDF) Download
	 * 
	 */

	@SuppressWarnings({ "unchecked", "unused" })
	@GetMapping("/audit-review-pdf-download")
	public void generateReportPDF(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("planId") String encodedParam1, @RequestParam("scheduledId") String encodedParam2,
			@RequestParam("category") String encodedParam3, @RequestParam("instanceId") String encodedParam4) {

		logger.info("Method: generateReportPDF starts");

		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String planId = new String(encodeByte1);

		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String scheduledId = new String(encodeByte2);

		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String category = new String(encodeByte3);

		byte[] encodeByte4 = Base64.getDecoder().decode(encodedParam4.getBytes());
		String instanceId = new String(encodeByte4);

		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper objectMapper = new ObjectMapper();
		JsonResponse<Object> jsonResponse = new JsonResponse<Object>();

		try {
			jsonResponse = restTemplate.getForObject(env.getGrcUrl() + "get-all-audit-report-data?planId=" + planId
					+ "&scheduledId=" + scheduledId + "&category=" + category + "&instanceId=" + instanceId
					+ "&organization=" + organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		System.out.println("jsonResponse" + jsonResponse);
		List<GRCReportModel> listModel = new ArrayList<GRCReportModel>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			listModel = mapper.readValue(jsonResponse.getBody().toString(), List.class);
		} catch (JsonParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (JsonMappingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block spring security
			e2.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("allData", listModel);
		String logo = (String) session.getAttribute("ORGANIZATION_LOGO");

		System.out.println("Data" + data);
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

		if (category.equals("AUDCAT00004")) {
			response.setHeader("Content-disposition", "inline; filename=MonthlyAuditReport.pdf");

			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("grc/monthlyAuditReportPdf.html", data);
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
		} else if (category.equals("AUDCAT00003")) {
			response.setHeader("Content-disposition", "inline; filename=StfHarzadAuditReport.pdf");

			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("grc/StfHarzadAuditPdf.html", data);
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
		}

		logger.info("Method: generateReportPDF ends");
	}

}
