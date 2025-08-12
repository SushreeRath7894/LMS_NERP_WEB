package nirmalya.aathithya.webmodule.gatepass.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DownloadDocumentUrl;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.gatepass.model.ShiftRegisterReportModel;

@Controller
@RequestMapping(value = "gatepass/")
public class ShiftRegisterReportController {

	Logger logger = LoggerFactory.getLogger(ShiftRegisterReportController.class);
 
	@Autowired
	RestTemplate restClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping("/report")
	public String gatePassReport(Model model, HttpSession session) {
		logger.info("Method : GatePassReport add starts");

		String userId = "";
		String org = "";
		String orgDiv = "";

		try {
			userId = (String) session.getAttribute("USER_ID");
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			//logger.error(ex.getMessage());
		}
		
		try {
			DropDownModel[] shift = restTemplate.getForObject(env.getGatepassUrl() + "getShiftLists?org=" + org
					+ "&orgDiv=" + orgDiv + "&userId=" + userId, DropDownModel[].class);
			System.out.println("shift>>>>>----" + shift.toString());
			List<DropDownModel> shiftLists = Arrays.asList(shift);
			System.out.println(shiftLists);
			model.addAttribute("shiftLists", shiftLists);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : GatePassReport ends");
		return "gatepass/shift-register-report";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("report_category_list")
	public @ResponseBody Object getCategoryList(@RequestParam String shift, String date,HttpSession session) {
		logger.info("Method :getCategoryList starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("shift>>>>>>>>>>>>" + shift);
		try {

			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-gatePass-report_category_list?shift=" + shift
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&date=" + date, JsonResponse.class);
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
		logger.info("Method :getCategoryList ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("report-gate-data-add")
	public @ResponseBody JsonResponse<Object> saveGateReportData(HttpSession session,
			@RequestBody List<ShiftRegisterReportModel> gatePassReportModel) {

		logger.info("Method : saveGateReportData starts");
		System.out.println(gatePassReportModel);
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

		/*
		 * for (GatePassReportModel a : gatePassReportModel) { if (a.getPdate() != null
		 * && a.getPdate() != "") {
		 * a.setPdate(DateFormatter.inputDateFormat(a.getPdate(), dateFormat)); } }
		 */
		for (ShiftRegisterReportModel m : gatePassReportModel) {
			m.setCreatedBy(userId);
			m.setOrganization(organization);
			m.setOrgDivision(orgDivision);
		}
		try {
			resp = restTemplate.postForObject(env.getGatepassUrl() + "saveGateReportData", gatePassReportModel,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveGateReportData ends");
		System.out.println("saveGateReportDataaaaaaa" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("report-view-data")
	public @ResponseBody Object viewReportData(HttpSession session) {
		logger.info("Method :viewReportData starts");
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
					env.getGatepassUrl() + "viewReportData?orgName=" + orgName + "&orgDivision=" + orgDivision,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method :viewReportData ends");
		return resp;
	}

	// Emp List.
	
	@SuppressWarnings("unchecked")
	@GetMapping("report-view-empList")
	public @ResponseBody Object empListData(HttpSession session, String empCategory) {
		logger.info("Method :empListData starts");
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
					env.getGatepassUrl() + "rest-empListData?orgName=" + orgName + "&orgDivision=" + orgDivision + "&empCategory=" + empCategory,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :empListData ends" + resp);
		return resp;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("report-data-edit")
	public @ResponseBody Object editReportData(@RequestParam String id, HttpSession session) {
		logger.info("Method :editReportData starts");
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

			resp = restTemplate.getForObject(env.getGatepassUrl() + "editReportData?id=" + id + "&orgName=" + orgName
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
		logger.info("Method :editReportData ends");
		return resp;
	}

	@GetMapping("report-details-delete")
	public @ResponseBody Object deleteReport(@RequestParam String id, HttpSession session) {

		logger.info("Method :deleteReport starts");
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
			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-deleteReport?registerId=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@GetMapping("report-details-approve")
	public @ResponseBody Object approveReportdata(@RequestParam String id, HttpSession session) {

		logger.info("Method :approveReportdata starts");
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
			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-approveReportdata?registerId=" + id + "&org="
					+ orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("report-pdf-downloads")
	public void getReportPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("registerId") String encodedParam1,@RequestParam("shift") String encodedParam2,@RequestParam("sdate") String encodedParam3) {

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
		String registerId = (new String(encodeByte1));
		byte[] encodeByte2 = Base64.getDecoder().decode(encodedParam2.getBytes());
		String shift=(new String(encodeByte2));
		byte[] encodeByte3 = Base64.getDecoder().decode(encodedParam3.getBytes());
		String shiftdate=(new String(encodeByte3));
		JsonResponse<Object> resp = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-report-pdf?registerId=" + registerId
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("JsonResponse====" + resp.getBody());

		List<ShiftRegisterReportModel> listModel = new ArrayList<ShiftRegisterReportModel>();
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

		System.out.println("data====" + data);
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + "ShiftRegister ("+ shift + ") " + shiftdate + ".pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("gatepass/shift-register-report-pdf.html", data);
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

	// Category Autosearch
	@GetMapping("report-category-get-list")
	public @ResponseBody Object getCategoryAutoSearchListForItem(@RequestParam String searchValue,
			HttpSession session) {

		logger.info("Method :approveReportdata starts");
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
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "getCategoryAutoSearchListForItem?id=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	// ManPower Autosearch
	@GetMapping("report-manpower-get-list")
	public @ResponseBody Object getManPowerAutoSearchListForItem(@RequestParam String searchValue,
			HttpSession session) {

		logger.info("Method :getManPowerAutoSearchListForItem starts");
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
			resp = restTemplate.getForObject(
					env.getGatepassUrl() + "getManPowerAutoSearchListForItem?id=" + searchValue, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	/*--------------------Categories Added By Pankaj--------------------------------*/
	
	@GetMapping("report-add-new-categories")
	public @ResponseBody Object addCategoriesData(@RequestParam String categories,String type, String shift,HttpSession session) {

		logger.info("Method :addCategoriesData starts");
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
			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-categoriesdata?categories=" + categories + "&type="
					+ type  + "&shift=" + shift + "&org=" + orgName + "&orgDiv=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
	
	
	// Delete CAtegory.
	
	@SuppressWarnings("unchecked")
	@GetMapping("report_category_delete")
	public @ResponseBody Object deleteCategory(@RequestParam String id, HttpSession session) {
		logger.info("Method :deleteCategory starts");
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

			resp = restTemplate.getForObject(env.getGatepassUrl() + "rest-gatePass-report_category_delete?id=" + id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("edit>>>-----" + resp);
		logger.info("Method :deleteCategory ends");
		return resp;
	}
	
	
}
