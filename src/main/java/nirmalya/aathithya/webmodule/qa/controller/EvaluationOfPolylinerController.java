package nirmalya.aathithya.webmodule.qa.controller;

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

import nirmalya.aathithya.webmodule.common.utils.CommonUtil;
import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.FileUpload;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.qa.model.EvaluationOfLaminatesModel;
import nirmalya.aathithya.webmodule.qa.model.EvaluationOfPolylinerModel;
import nirmalya.aathithya.webmodule.qa.model.QaDarModel;

@Controller

@RequestMapping(value = { "qa/" })
public class EvaluationOfPolylinerController {

	Logger logger = LoggerFactory.getLogger(EvaluationOfPolylinerController.class);
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

	@GetMapping(value = { "evaluation-of-polyliner" })

	public String evalOfPolyliner(Model model, HttpSession session) {
		logger.info("Method :evalOfPolyliner starts");

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
					env.getQa() + "qa-of-get-Item-lists?org=" + org + "&orgDiv=" + orgDiv, DropDownModel[].class);
			List<DropDownModel> itemList = Arrays.asList(item);
			System.out.println("itemlist" + itemList);
			model.addAttribute("itemList", itemList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : evalOfPolyliner ends");

		return "qa/evaluation-of-polyliner";
	}

	// add.

	@SuppressWarnings({ "unchecked" })

	@PostMapping(value = { "evaluation-of-polyliner-add" })
	public @ResponseBody JsonResponse<Object> addPolyliner(@RequestBody EvaluationOfPolylinerModel av,
			HttpSession session) {
		logger.info("Method : addPolyliner function starts");
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
			resp = restTemplate.postForObject(env.getQa() + "rest-addPolyliner", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addPolyliner function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}

	// View.

	@SuppressWarnings("unchecked")

	@GetMapping("evaluation-of-polyliner-view")
	public @ResponseBody Object getPolylinerView(HttpSession session) {
		logger.info("Method :getPolylinerView starts");
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
					env.getQa() + "rest-getPolylinerView?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :getPolylinerView ends");
		return resp;
	}

	// Edit.

	@SuppressWarnings("unchecked")

	@GetMapping("evaluation-of-polyliner-edit")
	public @ResponseBody Object editPolyliner(@RequestParam String id, HttpSession session) {
		logger.info("Method :editPolyliner starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-editPolyliner?id=" + id + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :editPolyliner ends");
		return resp;
	}

	// Delete.

	@SuppressWarnings("unchecked")

	@GetMapping("evaluation-of-polyliner-delete")
	public @ResponseBody Object deletePolyliner(@RequestParam String id, HttpSession session) {
		logger.info("Method :deletePolyliner starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-deletePolyliner?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :deletePolyliner ends");
		return resp;
	}

	// Approve.

	@SuppressWarnings("unchecked")

	@GetMapping("evaluation-of-polyliner-approve")
	public @ResponseBody Object approvePolyliner(@RequestParam String id, HttpSession session) {
		logger.info("Method :approvePolyliner starts");
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

			resp = restTemplate.getForObject(env.getQa() + "rest-approvePolyliner?id=" + id + "&orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&approvedBy=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :approvePolyliner ends");
		return resp;
	}
	
	
	// Pdf
	
	@SuppressWarnings("unchecked")
	@GetMapping("evaluation-of-polyliner-pdf-downloads")
	public void getReportPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1) {

		logger.info("Method : getReportPdfDetails starts");
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
			resp = restTemplate.getForObject(env.getQa() + "rest-pdfPolyliner?id=" + id + "&orgName="
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
		data.put("dtls", listModel);
 
		System.out.println("data====" + data);
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
		response.setHeader("Content-disposition", "inline; filename=warehouseCheckingRegister.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("qa/evaluation-of-polyliner-pdf.html", data);
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

		logger.info("Method : getReportPdfDetails ends");
	}
	

	// getaggridData

	@SuppressWarnings("unchecked")

	@GetMapping("evaluation-of-polyliner-aggrid-show")
	public @ResponseBody Object getAggridPolylinerDatas(HttpSession session) {
		logger.info("Method :getAggridPolylinerDatas starts");
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
			resp = restTemplate.getForObject(env.getQa() + "rest-getAggridPolylinerDatas?orgName=" + orgName
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("view===" + resp);
		logger.info("Method :getAggridPolylinerDatas ends");
		return resp;
	}

}
