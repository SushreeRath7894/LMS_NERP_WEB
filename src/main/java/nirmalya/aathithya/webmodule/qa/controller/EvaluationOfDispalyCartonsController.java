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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.EvaluationOfDisplayCartonsModel;
import org.apache.poi.util.IOUtils;

@Controller

@RequestMapping(value = { "qa/" })
public class EvaluationOfDispalyCartonsController {
	Logger logger = LoggerFactory.getLogger(EvaluationOfDispalyCartonsController.class);
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	FileUpload fileUpload;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@Autowired
	CommonUtil commonUtil;

	@GetMapping(value = { "evaluation-of-display-cartons" })

	public String evalOfDispCartons(Model model, HttpSession session) {
		logger.info("Method :evalOfDispCartons starts");

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
			DropDownModel[] item = restTemplate.getForObject(
					env.getQa() + "qa-of-get-Item-lists-cartons?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			System.out.println("itemlist" + itemList);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : evalOfDispCartons ends");

		return "qa/evaluation-of-display-cartons";
	}

// add.

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "evaluation-of-display-cartons-add" })
	public @ResponseBody JsonResponse<Object> addDispCartons(@RequestBody EvaluationOfDisplayCartonsModel av,
			HttpSession session) {
		logger.info("Method : addDispCartons function starts");
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
		av.setCreatedBy(userId);
		av.setOrganization(organization);
		av.setOrgDivision(orgDivision);

		System.out.println("qc= ==" + av);
		try {
			resp = restTemplate.postForObject(env.getQa() + "rest-addDispCartons", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addDispCartons function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}

// View.

	@SuppressWarnings("unchecked")

	@GetMapping("evaluation-of-display-cartons-view")
	public @ResponseBody Object getDispCartonsView(HttpSession session) {
		logger.info("Method :getDispCartonsView starts");
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
					env.getQa() + "rest-getDispCartonsView?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getDispCartonsView ends");
		return resp;
	}

// Edit.

	@SuppressWarnings("unchecked")

	@GetMapping("evaluation-of-display-cartons-edit")
	public @ResponseBody Object editDispCartons(@RequestParam String id, HttpSession session) {
		logger.info("Method :editDispCartons starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-editDispCartons?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :editDispCartons ends");
		return resp;
	}

// Delete.

	@SuppressWarnings("unchecked")

	@GetMapping("evaluation-of-display-cartons-delete")
	public @ResponseBody Object deleteDispCartons(@RequestParam String id, HttpSession session) {
		logger.info("Method :deleteDispCartons starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-deleteDispCartons?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :deleteDispCartons ends");
		return resp;
	}

// Approve.

	@SuppressWarnings("unchecked")

	@GetMapping("evaluation-of-display-cartons-approve")
	public @ResponseBody Object approveDispCartons(@RequestParam String id, HttpSession session) {
		logger.info("Method :approveDispCartons starts");
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
		try {

			resp = restTemplate.getForObject(env.getQa() + "rest-approveDispCartons?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&approvedBy=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :approveDispCartons ends");
		return resp;
	}
	
	

	// getaggridData

	@SuppressWarnings("unchecked")

	@GetMapping("evaluation-of-display-cartons-aggrid-show")
	public @ResponseBody Object getAggridDispCartonsDatas(HttpSession session) {
		logger.info("Method :getAggridDispCartonsDatas starts");
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
			resp = restTemplate.getForObject(env.getQa() + "rest-getAggridDispCartonsDatas?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("view===" + resp);
		logger.info("Method :getAggridDispCartonsDatas ends");
		return resp;
	}
	
	
//PDF
	@SuppressWarnings("unchecked")
	@GetMapping("evaluation-of-display-cartons-pdf-downloads")
	public void oriFoodBevPdf(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam3)
			throws JsonParseException, JsonMappingException, JSONException, IOException {

		logger.info("Method : getOpenStockPdfDetails starts");
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String id = (new String(encodeByte5));
		 

		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			System.out.println("aaaaaaaaaaaaaaaaaaaaaa------------" + id);
			
			resp = restTemplate.getForObject(env.getQa() + "rest-viewPdf?id="
					+ id , JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("tefeg" + resp);
		Map<String, Object> data = new HashMap<String, Object>();

		System.out.println("Get Data === " + resp.getBody());

		if (resp.getBody() == null) {
			System.out.println("JSON Obj === " + resp.getBody());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline; filename=oriFoodBevPdf.pdf");
			File file;
			byte[] fileData = null;

			try {
				file = pdfGeneratorUtil.createPdf("qa/oriFoodBevPdf.html", data);
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

		JSONArray jsonArray = new JSONArray(resp.getBody().toString());

		System.out.println("JSON Array === " + jsonArray);

		if (jsonArray != null) {
			System.out.println("JSON Array === " + jsonArray);

			ObjectMapper mapper = new ObjectMapper();

			List<Map<String, Object>> dataa = mapper.readValue(jsonArray.toString(),
					new TypeReference<List<Map<String, Object>>>() {
					});
			logger.info("dataa" + dataa);

			data.put("respdata", dataa);
		}
		String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
		System.out.println("ASSSSSSSSSSSSSSSSSSSSSSSS"+logo);
		URL getUrl = null;
		try {
			getUrl = new URL(logo);
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
		data.put("logo", "data:image/png;base64," + encodedLogoUrl);
		
		System.out.println("JSON Obj === " + jsonArray);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=oriFoodBevPdf.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("qa/oriFoodBevPdf.html", data);
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

		logger.info("Method : oriFoodBevPdf ends");
	}  

}
