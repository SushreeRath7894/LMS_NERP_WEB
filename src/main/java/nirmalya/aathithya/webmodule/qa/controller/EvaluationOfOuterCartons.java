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
import org.json.JSONArray;
import org.json.JSONException;
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
import nirmalya.aathithya.webmodule.qa.model.EvaluationOfLaminatesModel;
import nirmalya.aathithya.webmodule.qa.model.EvaluationOfOuterCartonModel;

@Controller

@RequestMapping(value = { "qa/" })
public class EvaluationOfOuterCartons {

	Logger logger = LoggerFactory.getLogger(EvaluationOfOuterCartons.class);
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

	@GetMapping(value = { "evaluation-of-outer-cartons" })

	public String evalOfOuterCartons(Model model, HttpSession session) {
		logger.info("Method :evalOfOuterCartons starts");

		// String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			// userId = (String) session.getAttribute("USER_ID");
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

		logger.info("Method : evalOfOuterCartons ends");

		return "qa/evaluation-of-outer-cartons";
	}

	// add.

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "evaluation-of-outer-cartons-add" })
	public @ResponseBody JsonResponse<Object> addOuterCartons(@RequestBody EvaluationOfOuterCartonModel av,
			HttpSession session) {
		logger.info("Method : addLaminates function starts");
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
			resp = restTemplate.postForObject(env.getQa() + "rest-addOuterCartons", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addOuterCartons function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}
	
	// view
	
	@SuppressWarnings("unchecked")
	@GetMapping("evaluation-of-outer-cartons-view")
	public @ResponseBody Object getOuterCartonsView(HttpSession session) {
		logger.info("Method :getOuterCartonsView starts");
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
					env.getQa() + "rest-getOuterCartonsView?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getOuterCartonsView ends");
		return resp;
	}
	
	// Edit
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("evaluation-of-outer-cartons-edit")
	public @ResponseBody Object editOuterCartons(@RequestParam String id, HttpSession session) {
		logger.info("Method :editOuterCartons starts");
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

			resp = restTemplate.getForObject(env.getQa()  + "rest-editOuterCartons?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :editOuterCartons ends");
		return resp;
	}
	
	// Delete.
		@SuppressWarnings("unchecked")
		@GetMapping("evaluation-of-outer-cartons-delete")
		public @ResponseBody Object deleteOuterCartons(@RequestParam String id, HttpSession session) {
			logger.info("Method :deleteOuterCartons starts");
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

				resp = restTemplate.getForObject(env.getQa()  + "rest-deleteOuterCartons?id=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :deleteOuterCartons ends");
			return resp;
		}
		
		// Approve.
		@SuppressWarnings("unchecked")
		@GetMapping("evaluation-of-outer-cartons-approve")
		public @ResponseBody Object approveOuterCartons(@RequestParam String id, HttpSession session) {
			logger.info("Method :approveOuterCartons starts");
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

				resp = restTemplate.getForObject(env.getQa()  + "rest-approveOuterCartons?id=" + id + "&orgName="
						+ orgName + "&orgDivision=" + orgDivision + "&approvedBy=" + userId, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			logger.info("Method :approveOuterCartons ends");
			return resp;
		}
		

		// getaggridData

		@SuppressWarnings("unchecked")

		@GetMapping("evaluation-of-outer-cartons-aggrid-show")
		public @ResponseBody Object getAggridDatas(@RequestParam String sku,HttpSession session) {
			logger.info("Method :getAggridDatas starts");
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
				resp = restTemplate.getForObject(env.getQa() + "rest-getAggridOuterCartonsDatas?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&sku=" + sku, JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("view===" + resp);
			logger.info("Method :getAggridDatas ends");
			return resp;
		}

		
		// Suppress warnings for unchecked operations
		@SuppressWarnings("unchecked")
		@GetMapping("evaluation-of-outer-cartons-record-pdf-downloads")
		public void outerCartonPdf(HttpServletResponse response, Model model, HttpSession session,
		                           @RequestParam("id") String encodedParam3)
		        throws JsonParseException, JsonMappingException, JSONException, IOException {

		    logger.info("Method : outerCartonPdf starts");
		    String orgName = "";
		    String orgDivision = "";
		    try {
		        orgName = (String) session.getAttribute("ORGANIZATION");
		        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    byte[] encodeByte5 = Base64.getDecoder().decode(encodedParam3.getBytes());
		    String id = new String(encodeByte5);

		    JsonResponse<Object> resp = new JsonResponse<Object>();
		    try {
		        resp = restTemplate.getForObject(env.getQa() + "rest-outerCartonPdf?id=" + id, JsonResponse.class);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    logger.info("Response: " + resp);
		    Map<String, Object> data = new HashMap<String, Object>();

		    System.out.println("Get Data === " + resp.getBody());

		    if (resp.getBody() == null) {
		        System.out.println("JSON Obj === " + resp.getBody());

		        response.setContentType("application/pdf");
		        response.setHeader("Content-disposition", "inline; filename=outerCartonPdf.pdf");
		        File file;
		        byte[] fileData = null;

		        try {
		            file = pdfGeneratorUtil.createPdf("qa/outerCartonPdf.html", data);
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
		        return;  // exit the method as there's no data to process
		    }

		    String responseBody = resp.getBody().toString();
		    System.out.println("Response Body === " + responseBody);

		    ObjectMapper mapper = new ObjectMapper();
		    JsonNode jsonNode = mapper.readTree(responseBody);

		    if (jsonNode.isArray()) {
		        List<Map<String, Object>> dataa = mapper.readValue(responseBody,
		                new TypeReference<List<Map<String, Object>>>() {});
		        logger.info("dataa" + dataa);
		        data.put("respdata", dataa);
		    } else {
		        // Handle case where response is not an array
		        System.out.println("Response is not a JSON array: " + responseBody);
		        // Additional handling if needed
		    }

		    String logo = (String) session.getAttribute("ORGANIZATION_LOGO");
		    URL getUrl = null;
		    try {
		        getUrl = new URL(logo);
		    } catch (MalformedURLException e2) {
		        e2.printStackTrace();
		    }
		    String encodedLogoUrl = DownloadDocumentUrl.downloadDocumentUrl(getUrl);
		    data.put("logo", "data:image/png;base64," + encodedLogoUrl);

		    response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "inline; filename=outerCartonPdf.pdf");
		    File file;
		    byte[] fileData = null;
		    try {
		        file = pdfGeneratorUtil.createPdf("qa/outerCartonPdf.html", data);
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

		    logger.info("Method : outerCartonPdf ends");
		}
}
