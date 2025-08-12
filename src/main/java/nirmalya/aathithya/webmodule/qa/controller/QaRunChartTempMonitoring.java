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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.master.model.ProductMasterModel;
import nirmalya.aathithya.webmodule.qa.model.QaCrqsModel;
import nirmalya.aathithya.webmodule.qa.model.QaPcroModel;
import nirmalya.aathithya.webmodule.qa.model.QaRCFTMModel;

@Controller

@RequestMapping(value = { "qa/" })
public class QaRunChartTempMonitoring {

	Logger logger = LoggerFactory.getLogger(QaRunChartTempMonitoring.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "rcftm" })

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

		return "qa/rcftm";
	}

	// getaggridData

	@SuppressWarnings("unchecked")

	@GetMapping("rcftm-aggrid-show")
	public @ResponseBody Object getaggridData(HttpSession session) {
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
			resp = restTemplate.getForObject(
					env.getQa() + "rest-rcftm-aggrid-show?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :getaggridData ends");
		return resp;
	}

	// addPCRO

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "rcftm-details-addRCFTM" })
	public @ResponseBody JsonResponse<Object> addRCFTM(@RequestBody List<QaRCFTMModel> av, HttpSession session) {
		logger.info("Method : addRCFTM function starts");
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
		for (QaRCFTMModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-rcftm-details-addRCFTM", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addRCFTM function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}

	// getTotalCRQSView

	@SuppressWarnings("unchecked")

	@GetMapping("rcftm-details-view")
	public @ResponseBody Object getTotalRCFTMViewView(HttpSession session) {
		logger.info("Method :getTotalRCFTMViewView starts");
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
					env.getQa() + "rest-rcftm-details-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :getTotalRCFTMViewView ends");
		return resp;
	}

	// editCodeCrqs
	@SuppressWarnings("unchecked")
	@GetMapping("rcftm-reqst-edit")
	public @ResponseBody Object editCodeRCFTM(@RequestParam String id, HttpSession session) {
		logger.info("Method :editCodeRCFTM starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-rcftm-reqst-edit?id=" + id + "&orgName=" + orgName
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
		System.out.println("edit>>>-----" + resp);
		logger.info("Method :editCodeRCFTM ends");
		return resp;
	}

	// deleteCrqs

	@SuppressWarnings("unchecked")
	@PostMapping("rcftm-detls-delete")
	public @ResponseBody JsonResponse<Object> deleteRCFTM(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteRCFTM function starts");

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
					env.getQa() + "rest-rcftm-detls-delete?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteRCFTM function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	// approveCRQS

	@SuppressWarnings("unchecked")
	@PostMapping("rcftm-detls-approve")
	public @ResponseBody JsonResponse<Object> approveRCFTM(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : approveRCFTM function starts");

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
					env.getQa() + "rest-rcftm-detls-approve?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : approveRCFTM function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-rcftm-pdf-downloads")
	public void getDcPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("dcId") String encodedParam1) {

		logger.info("Method : getDcPdfDetails starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String dcId = (new String(encodeByte1));

		JsonResponse<Object> resp = new JsonResponse<Object>();
		JsonResponse<Object> resp1 = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getQa() + "rest-rcftm-reqst-download?id=" + dcId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp1 = restTemplate.getForObject(env.getQa() + "rest-rcftm-inspect-download?id=" + dcId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("JsonResponse====" + resp.getBody());
		List<QaCrqsModel> listModel = new ArrayList<QaCrqsModel>();
		List<QaCrqsModel> listModel1 = new ArrayList<QaCrqsModel>();
		ObjectMapper mapper = new ObjectMapper();

		System.out.println("JsonResponse====" + resp.getBody() + resp1.getBody());
		try {
			listModel = mapper.readValue(resp.getBody().toString(), List.class);
			if (resp1.getBody() == null) {
				listModel1 = null;
			} else {
				listModel1 = mapper.readValue(resp1.getBody().toString(), List.class);
			}
			// listModel1 = mapper.readValue(resp1.getBody().toString(), List.class);
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
		data.put("crqs", listModel);
		data.put("crqss", listModel1);

		System.out.println("data====" + data);
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=crqsInvoice.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("qa/CrqsReciept.html", data);
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

		logger.info("Method : getDcPdfDetails ends");
	}

	// InspectCrqs
	@SuppressWarnings("unchecked")
	@GetMapping("rcftm-inspect-view")
	public @ResponseBody Object crqsInspectView(@RequestParam String id, HttpSession session) {
		logger.info("Method :crqsInspectView starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-rcftm-inspect-view?id=" + id + "&orgName=" + orgName
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
		System.out.println("edit>>>-----" + resp);
		logger.info("Method :crqsInspectView ends");
		return resp;
	}

	// addPCRO

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "rcftm-inspect-add" })
	public @ResponseBody JsonResponse<Object> addCrqsInspect(@RequestBody List<QaCrqsModel> av, HttpSession session) {
		logger.info("Method : addCrqsInspect function starts");
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
		for (QaCrqsModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-rcftm-inspect-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addCrqsInspect function Ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-rcftm-inspect-pdf-downloads")
	public void getInspectPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("crqsId") String encodedParam1) {

		logger.info("Method : getInspectPdfDetails starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String crqsId = (new String(encodeByte1));

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getQa() + "rest-rcftm-inspect-download?id=" + crqsId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("JsonResponse====" + resp.getBody());

		List<QaCrqsModel> listModel = new ArrayList<QaCrqsModel>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			listModel = mapper.readValue(resp.getBody().toString(), List.class);
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
		data.put("crqs", listModel);

		System.out.println("data====" + data);
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=crqsInspectReport.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("qa/crqsInspect.html", data);
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

		logger.info("Method : getInspectPdfDetails ends");
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/rcftm-save-details")
	public @ResponseBody JsonResponse<Object> saveMasterDetails(@RequestBody QaRCFTMModel product,
			HttpSession session) {
		logger.info("Method : saveMasterDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		product.setCreatedBy(userId);
		product.setOrganization(orgName);
		product.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getQa() + "saveMasterDetails", product, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();
		String code = resp.getCode();
		if (message != null && message != "") {
			resp.setCode(code);
		} else {
			resp.setMessage("Success");
			resp.setCode(code);
		}

		logger.info("Method : saveMasterDetails starts"+resp);
		return resp;
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/rcftm-save-item-details")
	public @ResponseBody JsonResponse<Object> saveItemDetails(@RequestBody QaRCFTMModel product,
			HttpSession session) {
		logger.info("Method : saveItemDetails starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String orgName = "";
		String orgDivision = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		product.setCreatedBy(userId);
		product.setOrganization(orgName);
		product.setOrgDivision(orgDivision);

		try {
			resp = restTemplate.postForObject(env.getQa() + "saveItemDetails", product, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}

		logger.info("Method : saveItemDetails starts");
		return resp;
	}
	@SuppressWarnings("unchecked")

	@GetMapping("rcftm-item-details-view")
	public @ResponseBody Object getTotalChildView(HttpSession session,@RequestParam String runId) {
		logger.info("Method :getTotalChildView starts");
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
					env.getQa() + "getTotalChildView?runId=" + runId +"&orgName=" + orgName+ "&orgDivision=" + orgDivision,
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
		logger.info("Method :getTotalChildView ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("rcftm-reqstfor-item-edit")
	public @ResponseBody Object editCodeForChildRCFTM(@RequestParam String id,String runChartId, HttpSession session) {
		logger.info("Method :editCodeForChildRCFTM starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-editCodeForChildRCFTM?id=" + id + "&runChartId=" + runChartId + "&orgName=" + orgName
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
		System.out.println("edit>>>-----" + resp);
		logger.info("Method :editCodeForChildRCFTM ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("rcftm-detls-delete-child")
	public @ResponseBody JsonResponse<Object> deleteRCFTMChild(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteRCFTMChild function starts");

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
					env.getQa() + "rest-deleteRCFTMChild?id=" + id + "&org=" + org + "&orgDiv=" + orgDiv,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteRCFTMChild function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("rcftm-detls-for-graphs")
	public @ResponseBody Object detailsForGraphs(@RequestParam String id,HttpSession session) {

		logger.info("Method :detailsForGraphs starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		String userId = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			resp = restTemplate.getForObject(env.getQa() + "rcftm-detailsForGraphs?id="
					+ id + "&orgName=" + orgName +"&orgDivision=" + orgDivision + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :detailsForGraphs ends" + resp);

		return resp;
	}
}
