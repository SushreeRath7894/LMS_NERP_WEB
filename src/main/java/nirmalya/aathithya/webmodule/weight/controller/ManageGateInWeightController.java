package nirmalya.aathithya.webmodule.weight.controller;

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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DateFormatter;
import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.gatepass.model.GatePassDetailsModel;
import nirmalya.aathithya.webmodule.master.model.EmpRoleModel;
import nirmalya.aathithya.webmodule.qa.model.QaWcrModel;
import nirmalya.aathithya.webmodule.weight.model.WebWeightBridgeModel;

@Controller
@RequestMapping(value = "weight/")
public class ManageGateInWeightController {

	Logger logger = LoggerFactory.getLogger(ManageGateInWeightController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	// page return
	@GetMapping("weight-bridge")
	public String getGateInWeight(Model model, HttpSession session) {
		logger.info("Method : getGateInWeight starts");

		try {
			DropDownModel[] unit = restClient.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(unit);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// String userId = "";
		// String userName = "";
		String userRole = "";
		// String organization="";
		// String orgDivision="";

		try {
			// userId = (String) session.getAttribute("USER_ID");
			// userName = (String) session.getAttribute("USER_NAME");
			userRole = (String) session.getAttribute("USER_ROLES_STRING");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		String splitData[] = userRole.split("r");
		String[] removedNull = Arrays.stream(splitData).filter(value -> value != "" && value.length() > 0)
				.toArray(size -> new String[size]);

		for (String part : removedNull) {
			String data = "r" + part;

			if (data.contentEquals("rol001") || data.contentEquals("rol006")) {
				model.addAttribute("adRole", "admin");
				//System.out.println("data>>>>----" + data);
			}

		}

		logger.info("Method : getGateInWeight ends");
		return "weight/weight-bridge";
	}

	// gate out
	@GetMapping("weight-bridge-report")
	public String getReport(Model model, HttpSession session) {
		logger.info("Method : getReport starts");

		logger.info("Method : getReport ends");
		return "weight/weight-bridge-report";
	}

	// view weight-bridge
	@SuppressWarnings("unchecked")
	@GetMapping("weight-bridge-view")
	public @ResponseBody Object viewGatePassInWeight(HttpSession session, @RequestParam String pageno) {

		logger.info("Method :viewGatePassInWeight starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDiv = "";
		
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		
		System.out.println("pageno>>>>>"+pageno);
		
		try {
			resp = restClient.getForObject(env.getWeightUrl() + "viewGatePassInWeight?pageno=" + pageno + "&orgName=" + orgName + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * ObjectMapper mapper = new ObjectMapper();
		 * 
		 * List<WebWeightBridgeModel> salesInvoiceNewModel =
		 * mapper.convertValue(resp.getBody(), new
		 * TypeReference<List<WebWeightBridgeModel>>() { });
		 * 
		 * resp.setBody(salesInvoiceNewModel);
		 */
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewGatePassInWeight ends");
		System.out.println("RESPONSEview" + resp);
		return resp;
	}

	// gate pass in view
//	@SuppressWarnings("unchecked")
//	@GetMapping("gate-pass-in-weight-view")
//	public @ResponseBody List<GatePassDetailsModel> viewGatePassInWeight(HttpSession session) {
//
//		logger.info("Method : viewGatePassInWeight starts");
//		JsonResponse<List<GatePassDetailsModel>> resp = new JsonResponse<List<GatePassDetailsModel>>();
//
//		String userid = "";
//		String organization = "";
//		String orgDivision = "";
//		try {
//			userid = (String) session.getAttribute("USER_ID");
//			organization = (String) session.getAttribute("ORGANIZATION");
//			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		EmpRoleModel empModel = new EmpRoleModel();
//
//		empModel.setUserId(userid);
//		empModel.setType("WEB");
//		empModel.setOrganization(organization);
//		empModel.setOrgDivision(orgDivision);
//
//		try {
//			resp = restClient.postForObject(env.getWeightUrl() + "viewGatePassInWeight", empModel, JsonResponse.class);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//		ObjectMapper mapper = new ObjectMapper();
//		List<GatePassDetailsModel> gatePassDetailsModel = mapper.convertValue(resp.getBody(),
//				new TypeReference<List<GatePassDetailsModel>>() {
//				});
//		String dateFormat = "";
//
//		try {
//			dateFormat = (String) session.getAttribute("DATEFORMAT");
//			if (dateFormat == null) {
//				dateFormat = "yyyy-MM-dd";
//			}
//
//		} catch (Exception e) {
//		}
//
//		if (gatePassDetailsModel != null)
//			for (GatePassDetailsModel a : gatePassDetailsModel) {
//				if (a.getEntrydate() != null && a.getEntrydate() != "") {
//					a.setEntrydate(DateFormatter.dateFormat(a.getEntrydate(), dateFormat));
//				}
//
//			}
//
//		logger.info("Method : viewGatePassInWeight ends");
//		return gatePassDetailsModel;
//
//	}

	// add weight-bridge
	@SuppressWarnings("unchecked")
	@PostMapping("weight-bridge-add")
	public @ResponseBody JsonResponse<Object> addWeightBridgeGateIn(@RequestBody WebWeightBridgeModel weightBridgeModel,
			Model model, HttpSession session) {

		logger.info("Method : addWeightBridgeGateIn starts");

		String orgName = "";
		String orgDiv = "";
		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		weightBridgeModel.setOrganizationName(orgName);
		weightBridgeModel.setOrganizationDivision(orgDiv);

		try {

			resp = restClient.postForObject(env.getWeightUrl() + "addWeightBridgeGateIn", weightBridgeModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getCode() == "") {
			resp.setCode("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addWeightBridgeGateIn ends");

		return resp;
	}

	// gate pass out view
	@SuppressWarnings("unchecked")
	@GetMapping("weight-bridge-exit-view")
	public @ResponseBody List<WebWeightBridgeModel> viewGatePassOutWeight(HttpSession session, Model model,@RequestParam String pageno) {

		logger.info("Method :viewGatePassOutWeight starts");
		JsonResponse<List<WebWeightBridgeModel>> resp = new JsonResponse<List<WebWeightBridgeModel>>();
		
		String orgName = "";
		String orgDiv = "";
		

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {

			resp = restClient.getForObject(env.getWeightUrl() + "viewGatePassOutWeight?orgName=" + orgName + "&orgDiv=" + orgDiv + "&pageno=" + pageno, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<WebWeightBridgeModel> salesInvoiceNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<WebWeightBridgeModel>>() {
				});

		resp.setBody(salesInvoiceNewModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewGatePassOutWeight ends");
		System.out.println("RESPONSEview" + resp);
		return resp.getBody();
	}

	// add gate in weight
	@SuppressWarnings("unchecked")
	@PostMapping("manage-gate-out-weight-add")
	public @ResponseBody JsonResponse<Object> addWeightBridgeGateOut(
			@RequestBody WebWeightBridgeModel weightBridgeModel, Model model, HttpSession session) {

		logger.info("Method : addWeightBridgeGateOut starts" + weightBridgeModel);

		JsonResponse<Object> resp = new JsonResponse<Object>();

		try {

			resp = restClient.postForObject(env.getWeightUrl() + "addWeightBridgeGateOut", weightBridgeModel,
					JsonResponse.class);

		} catch (RestClientException e) {

			e.printStackTrace();
		}

		if (resp.getCode() == "") {
			resp.setCode("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method : addWeightBridgeGateOut ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("weight-bridge-entry-approval")
	public @ResponseBody JsonResponse<WebWeightBridgeModel> entryApproval(HttpSession session,
			@RequestParam String entryId, String userId) {

		logger.info("Method : entryApproval starts");
		JsonResponse<WebWeightBridgeModel> response = new JsonResponse<WebWeightBridgeModel>();
		try {
			response = restClient.getForObject(
					env.getWeightUrl() + "entryApproval?entryId=" + entryId + "&userId=" + userId, JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		System.out.println("response=====" + response);
		logger.info("Method : entryApproval ends");
		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("weight-bridge-exit-approval")
	public @ResponseBody JsonResponse<WebWeightBridgeModel> exitApproval(HttpSession session,
			@RequestParam String registerId, String userId) {

		logger.info("Method : exitApproval starts");
		JsonResponse<WebWeightBridgeModel> response = new JsonResponse<WebWeightBridgeModel>();
		try {
			response = restClient.getForObject(
					env.getWeightUrl() + "exitApproval?registerId=" + registerId + "&userId=" + userId,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}
		if (response.getCode().equals("success")) {
			response.setMessage("Success");
		} else {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");
		}
		System.out.println("response=====" + response);
		logger.info("Method : exitApproval ends");
		return response;
	}

	// gate pass out view
	@SuppressWarnings("unchecked")
	@GetMapping("weight-bridge-total-view")
	public @ResponseBody List<WebWeightBridgeModel> viewGatePassTotalWeight(HttpSession session, Model model, @RequestParam String pageno) {

		logger.info("Method :viewGatePassTotalWeight starts");
		JsonResponse<List<WebWeightBridgeModel>> resp = new JsonResponse<List<WebWeightBridgeModel>>();
		
		String orgName = "";
		String orgDiv = "";
		

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {

			resp = restClient.getForObject(env.getWeightUrl() + "viewGatePassTotalWeight?orgName=" + orgName + "&orgDiv=" + orgDiv + "&pageno=" + pageno, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<WebWeightBridgeModel> salesInvoiceNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<WebWeightBridgeModel>>() {
				});

		resp.setBody(salesInvoiceNewModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :viewGatePassTotalWeight ends");
		System.out.println("RESPONSEview" + resp);
		return resp.getBody();
	}

	// gate pass report view
	@SuppressWarnings("unchecked")
	@GetMapping("weight-bridge-report-view")
	public @ResponseBody Object viewGatePassReport(@RequestParam String fromdate, String todate, String type,
			HttpSession session) {

		logger.info("Method :viewGatePassReport starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		// String Date = DateFormatter.getStringDate(date);
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			resp = restClient
					.getForObject(
							env.getWeightUrl() + "viewGatePassReport?orgName=" + orgName + "&orgDivision=" + orgDivision
									+ "&fromdate=" + fromdate + "&todate=" + todate + "&type=" + type,
							JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getCode() == "success") {
			resp.setMessage("Success");
		} else {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		}

		logger.info("Method :viewGatePassReport ends");
		System.out.println("RESPONSEview" + resp);
		return resp.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-slip-pdf-downloads")
	public void getWeighSlipPdfDetails(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("dcId") String encodedParam1) {

		logger.info("Method : getWeighSlipPdfDetails starts");
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
			System.out.println("orgName====" + orgName);
			System.out.println("orgDivision====" + orgDivision);
			resp = restClient.getForObject(env.getWeightUrl() + "rest-slip-pdf-downloads?id=" + dcId + "&orgName="
					+ orgName + "&orgDivision=" + orgDivision, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("JsonResponse====" + resp.getBody());

		List<QaWcrModel> listModel = new ArrayList<QaWcrModel>();
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
		data.put("slip", listModel);

		System.out.println("data====" + data);
		String logo = "classpath:static/assets/images/invoice-banner.jpg";
		data.put("logo", logo);

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=warehouseCheckingRegister.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("weight/weigh-slip-pdf.html", data);
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

		logger.info("Method : getWeighSlipPdfDetails ends");
	}

	// Weigh In Delete

	@SuppressWarnings("unchecked")
	@GetMapping("weight-bridge-details-delete")
	public @ResponseBody Object weighInDelete(@RequestParam String id, HttpSession session) {
		logger.info("Method :weighInDelete starts");
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
			resp = restClient.getForObject(env.getWeightUrl() + "rest-weighInDelete?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :weighInDelete ends");
		return resp;
	}

	// Weight Out Delete.

	@SuppressWarnings("unchecked")
	@GetMapping("weight-bridge-exit-delete")
	public @ResponseBody Object weighOutDelete(@RequestParam String id, HttpSession session) {
		logger.info("Method :weighOutDelete starts");
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
			resp = restClient.getForObject(env.getWeightUrl() + "rest-weighOutDelete?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method :weighOutDelete ends");
		return resp;
	}
	
	
	// Search
	@SuppressWarnings("unchecked")
	@GetMapping("weight-bridge-view-search")
	public @ResponseBody Object gatePassInWeightSearch(HttpSession session, @RequestParam String sValue) {

		logger.info("Method :gatePassInWeightSearch starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();

		String orgName = "";
		String orgDiv = "";
		
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}
		
		
		try {
			resp = restClient.getForObject(env.getWeightUrl() + "rest-gatePassInWeightSearch?sValue=" + sValue + "&orgName=" + orgName + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * ObjectMapper mapper = new ObjectMapper();
		 * 
		 * List<WebWeightBridgeModel> salesInvoiceNewModel =
		 * mapper.convertValue(resp.getBody(), new
		 * TypeReference<List<WebWeightBridgeModel>>() { });
		 * 
		 * resp.setBody(salesInvoiceNewModel);
		 */
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :gatePassInWeightSearch ends");
		System.out.println("RESPONSEview" + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("weight-bridge-exit-view-search")
	public @ResponseBody List<WebWeightBridgeModel> gatePassOutWeightSearch(HttpSession session, Model model,@RequestParam String sValue) {

		logger.info("Method :gatePassOutWeightSearch starts");
		JsonResponse<List<WebWeightBridgeModel>> resp = new JsonResponse<List<WebWeightBridgeModel>>();
		
		String orgName = "";
		String orgDiv = "";
		

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {

			resp = restClient.getForObject(env.getWeightUrl() + "rest-gatePassOutWeightSearch?orgName=" + orgName + "&orgDiv=" + orgDiv + "&sValue=" + sValue, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<WebWeightBridgeModel> salesInvoiceNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<WebWeightBridgeModel>>() {
				});

		resp.setBody(salesInvoiceNewModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :gatePassOutWeightSearch ends");
		System.out.println("RESPONSEview" + resp);
		return resp.getBody();
	}
	@SuppressWarnings("unchecked")
	@GetMapping("weight-bridge-total-view-search")
	public @ResponseBody List<WebWeightBridgeModel> gatePassTotalWeightSearch(HttpSession session, Model model, @RequestParam String sValue) {

		logger.info("Method :gatePassTotalWeightSearch starts");
		JsonResponse<List<WebWeightBridgeModel>> resp = new JsonResponse<List<WebWeightBridgeModel>>();
		
		String orgName = "";
		String orgDiv = "";
		

		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {

		}

		try {

			resp = restClient.getForObject(env.getWeightUrl() + "rest-gatePassTotalWeightSearch?orgName=" + orgName + "&orgDiv=" + orgDiv + "&sValue=" + sValue, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();

		List<WebWeightBridgeModel> salesInvoiceNewModel = mapper.convertValue(resp.getBody(),
				new TypeReference<List<WebWeightBridgeModel>>() {
				});

		resp.setBody(salesInvoiceNewModel);
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}

		logger.info("Method :gatePassTotalWeightSearch ends");
		System.out.println("RESPONSEview" + resp);
		return resp.getBody();
	}
}
