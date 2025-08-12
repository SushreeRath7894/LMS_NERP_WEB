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
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.EvaluationOfLaminatesModel;
import nirmalya.aathithya.webmodule.qa.model.QaCrqsModel;
@Controller

@RequestMapping(value = { "qa/" })
public class EvaluationOfLaminatesController {
	
	Logger logger = LoggerFactory.getLogger(EvaluationOfLaminatesController.class);
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
	
	@GetMapping(value = { "evaluation-of-laminates" })
	
	public String evalOfLaminates(Model model, HttpSession session) {
		logger.info("Method :evalOfLaminates starts");
		
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
					env.getQa() + "qa-of-get-Item-lists-laminate?org=" + org + "&orgDiv=" + orgDiv,
					DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			System.out.println("itemlist"+itemList);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		
		logger.info("Method : evalOfLaminates ends");

		return "qa/evaluation-of-laiminates";
	}
	
	
	// add.
	
	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "evaluation-of-laminates-add" })
	public @ResponseBody JsonResponse<Object> addLaminates(@RequestBody EvaluationOfLaminatesModel av, HttpSession session) {
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
			resp = restTemplate.postForObject(env.getQa() + "rest-evaluation-of-laminates-add", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addLaminates function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}
	
	
	// View.
	
	@SuppressWarnings("unchecked")
	@GetMapping("evaluation-of-laminates-view")
	public @ResponseBody Object getLaminateView(HttpSession session) {
		logger.info("Method :getLaminateView starts");
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
					env.getQa() + "rest-evaluation-of-laminates-view?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getLaminateView ends");
		return resp;
	}
	
	// Edit.
	
	@SuppressWarnings("unchecked")
	@GetMapping("evaluation-of-laminates-edit")
	public @ResponseBody Object editLaminates(@RequestParam String id, HttpSession session) {
		logger.info("Method :editLaminates starts");
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

			resp = restTemplate.getForObject(env.getQa()  + "rest-evaluation-of-laminates-edit?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :editLaminates ends");
		return resp;
	}
	
	// Delete.
	@SuppressWarnings("unchecked")
	@GetMapping("evaluation-of-laminates-delete")
	public @ResponseBody Object deleteLaminates(@RequestParam String id, HttpSession session) {
		logger.info("Method :deleteLaminates starts");
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

			resp = restTemplate.getForObject(env.getQa()  + "rest-deleteLaminates?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :deleteLaminates ends");
		return resp;
	}
	
	// Approve.
	@SuppressWarnings("unchecked")
	@GetMapping("evaluation-of-laminates-approve")
	public @ResponseBody Object approveLaminates(@RequestParam String id, HttpSession session) {
		logger.info("Method :approveLaminates starts");
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

			resp = restTemplate.getForObject(env.getQa()  + "rest-approveLaminates?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision + "&approvedBy=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Method :approveLaminates ends");
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("evaluation-of-laminates-pdf-downloads")
	public void getLaminatePdf(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1) {
		logger.info("Method : getLaminatePdf starts");
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
		logger.info("id got-------------"+id);
		try {
			resp = restTemplate.getForObject(env.getQa() + "rest-getLaminatePdf?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = new HashMap<String, Object>();
		logger.info("id dataa  pdf -------------"+resp.getBody().toString());
		try {
			Map<String, Object> dataa = mapper.readValue(resp.getBody().toString(), new TypeReference<Map<String, Object>>() {});
			//data.put("getLaminatePdf", dataa.get("getLaminatePdf"));
		//	data.put("sku", ((Map<String, Object>) dataa.get(0)).get("sku"));
		//	System.out.println("Extracted SKU: " + data.get("sku"));
			logger.info("id dataa dataa pdf -------------" + dataa);
		    	data.put("invNo", dataa.get("invNo"));
		    	data.put("list", dataa.get("list"));
		    	data.put("sku", dataa.get("sku"));
		    	data.put("grrNo", dataa.get("grrNo"));
		    	data.put("remark", dataa.get("remark"));
		    	data.put("specNo", dataa.get("specNo"));
		    	data.put("supplier", dataa.get("supplier"));
		    	data.put("checkedby", dataa.get("checkedby"));
		    	data.put("receiptDt", dataa.get("receiptDt"));
		    	data.put("checkingDt", dataa.get("checkingDt"));
		    	data.put("quantRcved", dataa.get("quantRcved"));
		    	data.put("samplingDt", dataa.get("samplingDt"));
		    	data.put("evaluatedBy", dataa.get("evaluatedBy"));
		    	data.put("quntChecked", dataa.get("quntChecked"));
		   
		} catch (IOException e2) {
		    e2.printStackTrace();
		}
		System.out.println("dataa.get(\"list\")>>"+data.get("list"));
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
		data.put("orgName",orgDivision);
		System.err.println("data exit====" + data);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=ShiftRegister.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("qa/laminatePdf.html", data);
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
		logger.info("Method : getLaminatePdf ends");
	}
	
	

	// getaggridData

	@SuppressWarnings("unchecked")

	@GetMapping("evaluation-of-laminates-aggrid-show")
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
			resp = restTemplate.getForObject(env.getQa() + "rest-getAggridDatas?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&sku=" + sku, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("view===" + resp);
		logger.info("Method :getAggridDatas ends");
		return resp;
	}

}
