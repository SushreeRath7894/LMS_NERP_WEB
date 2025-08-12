package nirmalya.aathithya.webmodule.productionplan.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.common.utils.PdfGeneratatorUtil;
import nirmalya.aathithya.webmodule.productionplan.model.ManufactureProcessModel;
import nirmalya.aathithya.webmodule.productionplan.model.PackingDetailsModel;

@Controller

@RequestMapping("production/")
public class PackingDetailsController {

	Logger logger = LoggerFactory.getLogger(PackingDetailsController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;

	@Autowired
	PdfGeneratatorUtil pdfGeneratorUtil;

	@GetMapping(value = { "packing-details" })

	public String packigDetails(Model model, HttpSession session) {
		logger.info("packigDetails Start");

		logger.info("packigDetails End");

		return "production_plan/packing-details";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("packing-details-data-view")
	public @ResponseBody Object viewPackingDetailsData(HttpSession session) {
		logger.info("Method :viewProcrssingData starts");
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
			resp = restTemplate.getForObject(env.getProduction() + "rest-viewPackingDetailsData?orgName=" + orgName
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
		
		logger.info("Method :viewPackingDetailsData ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("packing-details-edit-view")
	public @ResponseBody Object packingDetailsEdit(@RequestParam String id, HttpSession session) {
		logger.info("Method :packingPlanEdit starts");
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
			resp = restTemplate.getForObject(env.getProduction() + "rest-procrssingPlanEdit?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method :packingDetailsEdit ends");
		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("packing-details-machine-detls")
	public @ResponseBody Object machineDtls(@RequestParam String id, String bId, String pId, String shift, String date, HttpSession session) {
		logger.info("Method :machineDtls starts");
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

			resp = restTemplate.getForObject(env.getProduction() + "rest-machineDtls?id=" + id + "&bId=" + bId + "&pId="
					+ pId + "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&shift=" + shift + "&date=" + date, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method :machineDtls ends");
		return resp;
	}

	// add

	@SuppressWarnings({ "unchecked" })
	@PostMapping("/packing-details-add")
	public @ResponseBody JsonResponse<Object> addPackingDetails(@RequestBody PackingDetailsModel addPackingDetails,
			HttpSession session) {
		logger.info("Method : addPackingDetails Start");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String organization = "";
		String orgDivision = "";

		try {

			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		addPackingDetails.setOrganization(organization);
		addPackingDetails.setOrgDivision(orgDivision);
		
		

		try {

			resp = restTemplate.postForObject(env.getProduction() + "rest-addPackingDetails", addPackingDetails,
					JsonResponse.class);

		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addPackingDetails ends");
		
		return resp;
	}

	// pdf view

	

	@SuppressWarnings("unchecked")
	@GetMapping("packing-details-pdf-view")
	public void packingDetailsPdfView(HttpServletResponse response, Model model, HttpSession session,
			@RequestParam("id") String encodedParam1) {

		logger.info("Method : packingDetailsPdfView starts");
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
		JsonResponse<Object> resp1 = new JsonResponse<Object>();
		try {
			resp = restTemplate.getForObject(env.getProduction() + "rest-processMfgDetailsPdfView?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			resp1 = restTemplate.getForObject(env.getProduction() + "rest-packingDetailsPdfView?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		

		List<ManufactureProcessModel> listModel = new ArrayList<ManufactureProcessModel>();
		ObjectMapper mapper = new ObjectMapper();
		
		List<PackingDetailsModel> packinglistModel = new ArrayList<PackingDetailsModel>();
		ObjectMapper mapper1 = new ObjectMapper();

		try {
			listModel = mapper.readValue(resp.getBody().toString(), List.class);
			packinglistModel = mapper1.readValue(resp1.getBody().toString(), List.class);
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
		data.put("mfgdata", listModel);
		data.put("packingdata", packinglistModel);

		

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=logbook.pdf");
		File file;
		byte[] fileData = null;
		try {
			file = pdfGeneratorUtil.createPdf("production_plan/packing-details-pdf.html", data);
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

		logger.info("Method : packingDetailsPdfView ends");
	}
	
	//Approve
	
	@SuppressWarnings("unchecked")
	@GetMapping("packing-details-approve-data")
	public @ResponseBody Object packingDataApprove(@RequestParam String id, HttpSession session) {
		logger.info("Method :packingDataApprove starts");
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
			resp = restTemplate.getForObject(env.getProduction() + "rest-packingDataApprove?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id + "&userId=" + userId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		logger.info("Method :packingDataApprove ends");
		return resp;
	}
	
	// item-data
	
	@SuppressWarnings("unchecked")
	@GetMapping("packing-details-item-data-view")
	public @ResponseBody Object itemDetailsView(@RequestParam String id, String amt, String planId, HttpSession session) {
		logger.info("Method :itemDetailsView starts");
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
			resp = restTemplate.getForObject(env.getProduction() + "rest-itemDetailsView?orgName=" + orgName
					+ "&orgDivision=" + orgDivision + "&id=" + id + "&amt=" + amt + "&planId=" + planId, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		logger.info("Method :itemDetailsView ends");
		return resp;
	}

}
