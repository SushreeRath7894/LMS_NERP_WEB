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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.QaDarModel;
import nirmalya.aathithya.webmodule.qa.model.QaWcrModel;

@Controller

@RequestMapping(value = { "production/" })
public class QaDarController {

	Logger logger = LoggerFactory.getLogger(QaWcrController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "dar" })
	public String qadar(Model model, HttpSession session) {
		logger.info("Method :qadar starts");

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
			DropDownModel[] shift = restTemplate.getForObject(env.getProduction() + "getShiftListForProduction?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] brand = restTemplate.getForObject(
					env.getProduction() + "brandList?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> brandList = Arrays.asList(brand);
			model.addAttribute("brandList", brandList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : qadar ends");

		return "qa/dar";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dar-machineList")
	public @ResponseBody Object machineList(@RequestParam String id, HttpSession session) {
		logger.info("Method :machineList starts");
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

			resp = restTemplate.getForObject(env.getProduction() + "rest-dar-machineList?id=" + id + "&orgName="
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

		logger.info("Method :machineList ends");
		return resp;
	}

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "dar-add-details" })
	public @ResponseBody JsonResponse<Object> addDAR(@RequestBody List<QaDarModel> av, HttpSession session) {
		logger.info("Method : addDAR function starts");
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
		for (QaDarModel m : av) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getProduction() + "rest-dar-details-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addDAR function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}

	// view

	@SuppressWarnings("unchecked")

	@GetMapping("dar-view-data")
	public @ResponseBody Object darView(HttpSession session) {
		logger.info("Method :darView starts");
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
					env.getProduction() + "rest-dar-details-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
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
		logger.info("Method :darView ends");
		return resp;
	}

	// edit

	@SuppressWarnings("unchecked")
	@GetMapping("dar-edit-details")
	public @ResponseBody Object editDar(@RequestParam String id, HttpSession session) {
		logger.info("Method :editDar starts");
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

			resp = restTemplate.getForObject(env.getProduction() + "rest-dar-reqst-edit?id=" + id + "&orgName="
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
		System.out.println("edit>>>-----" + resp);
		logger.info("Method :editDar ends");
		return resp;
	}

	// approveDAR

	@SuppressWarnings("unchecked")
	@GetMapping("dar-approve-data")
	public @ResponseBody JsonResponse<Object> approveDAR(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : approveDAR function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		// JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getProduction() + "rest-dar-approve-data?id=" + id + "&userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		System.out.println("orgDivision>>>>>>>" + orgDivision);
		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : approveDAR function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("dar-detls-delete")
	public @ResponseBody JsonResponse<Object> deleteDAR(@RequestParam String id, Model model, HttpSession session) {
		logger.info("Method : deleteDAR function starts");

		JsonResponse<Object> res = new JsonResponse<Object>();

		// JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		try {
			res = restTemplate.getForObject(env.getProduction() + "rest-dar-detls-delete?id=" + id + "&userId=" + userId
					+ "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		System.out.println("orgDivision>>>>>>>" + orgDivision);
		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteDAR function Ends");

		System.out.println("RESPPPPPPP" + res);
		return res;
	}
	
	// dar PDF Download
	
	@SuppressWarnings("unchecked")
	@GetMapping("view-dar-pdf-downloads")
	public void getDARPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("dcId") String encodedParam1) {

		logger.info("Method : getDARPdfDetails starts");
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
		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-dar-reqst-download?id=" + dcId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("JsonResponse====" +resp.getBody());

		List<QaDarModel> listModel = new ArrayList<QaDarModel>();
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
		data.put("dar", listModel);
 
		System.out.println("data====" + data);
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);
 
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=warehouseCheckingRegister.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("qa/dar-details-pdf.html", data);
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

		logger.info("Method : getDARPdfDetails ends");
	}

}
