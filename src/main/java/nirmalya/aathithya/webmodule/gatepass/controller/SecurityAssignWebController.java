package nirmalya.aathithya.webmodule.gatepass.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.gatepass.model.SecurityAssignWebModel;

@Controller
@RequestMapping(value = "gatepass/")
public class SecurityAssignWebController {

	Logger logger = LoggerFactory.getLogger(SecurityAssignWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("/gate-security-assign")
	public String gatePassSecurityAssign(Model model, HttpSession session) {
		logger.info("Method : GatePassReport add starts");

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
		try {
			DropDownModel[] shift = restTemplate.getForObject(env.getGatepassUrl() + "getShiftLists?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);
			System.out.println("list>>>>>>>>>>>>" + shiftLists);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] list = restTemplate.getForObject(
					env.getGatepassUrl() + "getpostGateLists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);

			List<DropDownModel> postGateLists = Arrays.asList(list);

			model.addAttribute("postGateLists", postGateLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {
			DropDownModel[] namelist = restTemplate.getForObject(
					env.getGatepassUrl() + "getSecurityName?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> securityNameLists = Arrays.asList(namelist);

			model.addAttribute("securityNameLists", securityNameLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : GatePassReport ends");
		return "gatepass/securityAssign";
	}

	/*
	 * post Mapping for add
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "gate-security-assign-save-th-ajax")
	public @ResponseBody JsonResponse<Object> saveSecurityAssign(
			@RequestBody List<SecurityAssignWebModel> securityAssignWebModel, HttpSession session) {
		logger.info("Method : saveSecurityAssign function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();
		String userId = "";
		String dateFormat = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			dateFormat = (String) session.getAttribute("DATEFORMAT");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		for (SecurityAssignWebModel m : securityAssignWebModel) {
			m.setCreatedBy(userId);
			m.setOrganizationName(orgName);
			m.setOrganizationDivision(orgDivision);
		}
		try {

			res = restTemplate.postForObject(env.getGatepassUrl() + "rest-saveSecurityAssign", securityAssignWebModel,
					JsonResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			List<SecurityAssignWebModel> model = mapper.convertValue(res.getBody(),
					new TypeReference<List<SecurityAssignWebModel>>() {
					});
			res.setBody(model);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();

		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : saveSecurityAssign function Ends");
		return res;
	}

	/*
	 * view
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("gate-security-assign-trough-ajax")
	public @ResponseBody Object viewSecurityAssign(HttpSession session) {
		logger.info("Method :viewSecurityAssign starts");
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
					env.getGatepassUrl() + "viewSecurityAssign?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewSecurityAssign ends");
		return resp;

	}

	// viewBinAllocationdata

	@GetMapping("gate-security-assign-edit-trough-ajax")
	public @ResponseBody Object editSecurityAssign(@RequestParam String assignId, HttpSession session) {

		logger.info("Method :editSecurityAssign starts");
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
			resp = restClient.getForObject(env.getGatepassUrl() + "rest-editSecurityAssign?assignId=" + assignId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("gate-security-assign-pdf-downloads")
	public void getAssignPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1) {

		logger.info("Method : getAssignPdfDetails starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		byte[] encodeByte1 = Base64.getDecoder().decode(encodedParam1.getBytes());
		String id = (new String(encodeByte1));

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-assign-pdf?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("JsonResponse====" + resp.getBody());

		List<SecurityAssignWebModel> listModel = new ArrayList<SecurityAssignWebModel>();
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
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("register", listModel);
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String date = formatter.format(new Date());
		data.put("date", date);
		System.out.println("data====" + data);
		String logo = "classpath:static/assets/images/oriclean_org_logo.png";
		data.put("logo", logo);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=SecurityAssignRegister.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("gatepass/securityAssignpdf.html", data);
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

		logger.info("Method : getAssignPdfDetails ends");
	}
	// approve

	@SuppressWarnings("unchecked")
	@GetMapping("gate-security-assign-approve-th-ajax")
	public @ResponseBody JsonResponse<DropDownModel> approveAssign(HttpSession session,
			@RequestParam String approveStatus, String securityAssignId) {

		logger.info("Method : approveAssign starts");
		JsonResponse<DropDownModel> response = new JsonResponse<DropDownModel>();
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
		System.out.println("orgName" + orgName);
		System.out.println("orgDivision" + orgDivision);
		try {
			response = restTemplate.getForObject(env.getGatepassUrl() + "approveAssign?approveStatus=" + approveStatus
					+ "&securityAssignId=" + securityAssignId + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != "" && response.getMessage() != null) {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		} else {
			response.setMessage("Success");
		}

		System.out.println("response=====" + response);
		logger.info("Method : approveAssign ends");
		return response;
	}

	// delete
	@SuppressWarnings("unchecked")
	@PostMapping("gate-security-assign-delete")
	public @ResponseBody JsonResponse<Object> deleteAssignDetails(@RequestParam String id, Model model,
			HttpSession session) {
		logger.info("Method : deleteAssignDetails function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			res = restTemplate.getForObject(env.getGatepassUrl() + "deleteAssignDetails?id=" + id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteAssignDetails function Ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")

	@GetMapping(value = { "gate-security-assign-securityname" })
	public @ResponseBody JsonResponse<Object> getSecurityNameList(@RequestParam String id,HttpSession session) {
		logger.info("Method : getSecurityNameList starts" + id);
		JsonResponse<Object> res = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			res = restTemplate.getForObject(env.getGatepassUrl() + "getSecurityNameList?id=" + id + "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (res.getMessage() != null) {
			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		System.out.println("state" + res);
		logger.info("Method : getSecurityNameList ends");
		return res;
	}
}