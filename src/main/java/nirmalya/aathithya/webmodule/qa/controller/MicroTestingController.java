package nirmalya.aathithya.webmodule.qa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.procurment.model.InventoryVendorDocumentModel;
import nirmalya.aathithya.webmodule.qa.model.MicroTestingModel;
import nirmalya.aathithya.webmodule.sales.model.QuotationNewModel;

@Controller

@RequestMapping(value = { "qa/" })
public class MicroTestingController {
	Logger logger = LoggerFactory.getLogger(MicroTestingController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "micro-testing" })

	public String microTesting(Model model, HttpSession session) {
		logger.info("Method :microTesting starts");
		String userId = "";
		String userName = "";
		String userRole = "";
		String organization = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		try {
			DropDownModel[] year = restTemplate.getForObject(env.getMasterUrl()+ "getYearList-attendance?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> yearList = Arrays.asList(year);
			
			model.addAttribute("yearList", yearList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : microTesting ends");

		return "qa/micro-testing";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("micro-testing-save")
	public @ResponseBody JsonResponse<Object> saveAllData(HttpSession session,
			@RequestBody List<MicroTestingModel> microTestingModel) {
		logger.info("Method : saveAllData starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (MicroTestingModel m : microTestingModel) {
			logger.info("saddddddddddddddddd" + m);
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		//	m.setMonthYear(DateFormatter.inputDateFormat(m.getMonthYear(), dateFormat));
			m.setDate(DateFormatter.inputDateFormat(m.getDate(), dateFormat));
			m.setDateOfIssue(DateFormatter.inputDateFormat(m.getDateOfIssue(), dateFormat));
		}
		

		try {
			resp = restTemplate.postForObject(env.getQa() + "saveAllMtData", microTestingModel,
					JsonResponse.class);
			logger.info("response**********************" + resp);

		} catch (Exception e) {

			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveAllData ends");

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("micro-testing-view")
	public @ResponseBody Object viewMtData(HttpSession session) {
		logger.info("Method :viewMtData starts");
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
					env.getQa() + "viewMtData?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewMtData ends");
		return resp;
	}

	@GetMapping("micro-testing-edit")
	public @ResponseBody Object editMtData(@RequestParam String mictotestingId, HttpSession session) {

		logger.info("Method :editMtData starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getQa() + "rest-editMtData?mictotestingId=" + mictotestingId + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@GetMapping("micro-testing-delete")
	public @ResponseBody Object deleteMtdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteMtdata starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getQa() + "rest-deleteMtdata?mictotestingId=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	@GetMapping("micro-testing-approve")
	public @ResponseBody Object approveMtdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :approveMtdata starts");
		@SuppressWarnings("rawtypes")
		JsonResponse resp = new JsonResponse();
		String orgName = "";
		String orgDivision = "";

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resp = restTemplate.getForObject(env.getQa() + "rest-approveMtdata?mictotestingId=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	//PDF

		@SuppressWarnings("unchecked")
		@GetMapping("micro-testing-pdf-downloads")
		public void mtPdf(HttpServletResponse response, Model model, HttpSession session,
		        @RequestParam("id") String encodedParam1) {

		    logger.info("Method : mtPdf starts");
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
		        resp = restTemplate.getForObject(env.getQa() + "rest-mtPdf?id=" + id + "&orgName="
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
		        JsonNode pdfMtNode = rootNode.path("pdfMt");

		        if (pdfMtNode.isArray() && pdfMtNode.size() > 0) {
		            JsonNode firstElement = pdfMtNode.get(0);
		            data.put("pdfMt", objectMapper.convertValue(firstElement, Map.class));
		            
		            // Extracting pdfVb1arDetails
		            JsonNode pdfMtDetailsNode = firstElement.path("pdfMtDetails");
		            if (pdfMtDetailsNode.isArray()) {
		                List<Map<String, Object>> pdfMtDetailsList = objectMapper.convertValue(
		                		pdfMtDetailsNode,
		                        new TypeReference<List<Map<String, Object>>>() {});
		                data.put("pdfMtDetails", pdfMtDetailsList);
		            }
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
		    response.setHeader("Content-disposition", "inline; filename=reportMtPdf.pdf");
		    File file;
		    byte[] fileData = null;
		    try {
		        file = pdfGeneratorUtil.createPdf("qa/reportMtPdf.html", data);
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

		    logger.info("Method : mtPdf ends");
		}
}
