package nirmalya.aathithya.webmodule.qa.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.IncidentRegisterModel;
import nirmalya.aathithya.webmodule.qa.model.QaDarModel;
import nirmalya.aathithya.webmodule.qa.model.QaWcrModel;
import nirmalya.aathithya.webmodule.store.model.MaterialIssueDetailsWebModel;


@Controller
@RequestMapping(value = { "qa/" })
public class IncidentRegisterController {
	
	Logger logger = LoggerFactory.getLogger(IncidentRegisterController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "d-incidentregister" })

	public String Incident(Model model, HttpSession session) {
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
			DropDownModel[] shift = restTemplate.getForObject(
					env.getProduction() + "getShiftListForProduction?org=" + org + "&orgDiv=" + orgDiv+"&userId="+userId,
					DropDownModel[].class);
			List<DropDownModel> shiftLists = Arrays.asList(shift);

			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		logger.info("Method :incident starts");

		logger.info("Method : incident ends");

		return "qa/incident-register";
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("d-incidentregister-add")
	public @ResponseBody JsonResponse<Object> addIncident (
			@RequestBody List<IncidentRegisterModel> addIncident, Model model, HttpSession session) {

		logger.info("Method : addIncident starts" + addIncident);

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
		for (IncidentRegisterModel m : addIncident) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);

		}
		System.out.println("========" + addIncident);
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-d-incidentregister-add", addIncident,
					JsonResponse.class);
		} catch (Exception e) {

			e.printStackTrace();
		}

	
		System.out.println("RESP----------------" + resp);

		logger.info("Method : addIncident ends");

		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("d-incidentregister-view")
	public @ResponseBody Object viewIncident(HttpSession session) {
		logger.info("Method : viewIncident starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();
		

		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {
			resp = restTemplate.getForObject(env.getQa() + "rest-d-incidentregister-view?org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("viewIncident>>>>>>-------"+resp);
		logger.info("Method :viewIncident ends");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("d-incidentregister-edit")
	public @ResponseBody Object editIncident(@RequestParam String incidentId, HttpSession session) {
		logger.info("Method :editIncident starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-d-incidentregister-edit?incidentId=" + incidentId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :editIncident ends");
		return resp;
	}
	
	
	 @SuppressWarnings("unchecked")
		@PostMapping("d-incidentregister-delete")
		public @ResponseBody JsonResponse<Object> deleteIncident(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : deleteIncident function starts");

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
				res = restTemplate.getForObject(env.getQa() + "rest-deleteIncident?id=" + id + "&org=" + orgName
						+ "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

		
			logger.info("Method : deleteIncident function Ends");

			return res;
		}
	 
	 
	 @SuppressWarnings("unchecked")
		@PostMapping("d-incidentregister-approve")
		public @ResponseBody JsonResponse<Object> approveIncident(@RequestParam String id, Model model, HttpSession session) {
			logger.info("Method : approveIncident function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			String orgName = "";
			String orgDivision = "";
			String userId = "";
			try {

				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
				userId = (String) session.getAttribute("USER_ID");	
				} catch (Exception e) {
				logger.error(e.getMessage());
			}
		
			try {
				res = restTemplate.getForObject(env.getQa() + "rest-approveIncident?id=" + id + "&org=" + orgName
						+ "&orgDiv=" + orgDivision +"&userId=" + userId ,JsonResponse.class);
			} catch (RestClientException e) {
				e.printStackTrace();
			}

			
			logger.info("Method : approveIncident function Ends"+userId);

			return res;
		}
	 
	 
	 
	 //incedientRegisterPdf
	 
	 @SuppressWarnings("unchecked")
	 @GetMapping("view-incRegPdf-downloads")
		public void incRegPdf(HttpServletResponse response, Model model, HttpSession session,
				@RequestParam("dcId") String encodedParam1) {
		 

			logger.info("Method : incReg starts");
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
				resp = restTemplate.getForObject(env.getQa() + "rest-incedientRegisterPdf?id=" + dcId + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			List<IncidentRegisterModel> listModel = new ArrayList<IncidentRegisterModel>();
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				listModel = mapper.readValue(resp.getBody().toString(), List.class);
			} catch (JsonParseException e2) {
				e2.printStackTrace();
			} catch (JsonMappingException e2) {
				e2.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
	 
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("qa", listModel);
	 
			String logo = "classpath:static/assets/images/invoice-banner.jpg";
			data.put("logo", logo);
	 
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=warehouseCheckingRegister.pdf");
			File file;
			byte[] fileData = null;
			try {
				file = pdfGeneratorUtil.createPdf("qa/incedientRegisterPdf.html", data);
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

			logger.info("Method : incReg ends");
		}

}
