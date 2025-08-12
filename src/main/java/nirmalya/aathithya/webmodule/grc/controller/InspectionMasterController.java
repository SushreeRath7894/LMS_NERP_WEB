package nirmalya.aathithya.webmodule.grc.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.grc.model.AuditMasterModel;

@Controller
@RequestMapping(value = { "grc/" })
public class InspectionMasterController {

	Logger logger = LoggerFactory.getLogger(InspectionMasterController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "inspection-master" })
	public String inspectionMaster(Model model, HttpSession session) {
		logger.info("Method : inspectionMaster starts");

		String organization = "";
		String orgDivision = "";

		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] inspectType = restTemplate.getForObject(
					env.getGrcUrl() + "getInspectionTypeList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> InspectionType = Arrays.asList(inspectType);
			model.addAttribute("inspectType", InspectionType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] agencyList = restTemplate.getForObject(
					env.getGrcUrl() + "getAgencyList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AgencyList = Arrays.asList(agencyList);
			model.addAttribute("agencyList", AgencyList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] auditorSpecialisationList = restTemplate.getForObject(env.getGrcUrl()
					+ "getAuditorSpecialisationList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditorSpecialisationList = Arrays.asList(auditorSpecialisationList);
			model.addAttribute("auditorSpecialisationList", AuditorSpecialisationList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		try {

			DropDownModel[] auditorList = restTemplate.getForObject(
					env.getGrcUrl() + "getAuditorList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditorList = Arrays.asList(auditorList);
			model.addAttribute("auditorList", AuditorList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		logger.info("Method : inspectionMaster ends");
		return "grc/inspection-master";
	}

	// View Internal Auditor Data
	@SuppressWarnings("unchecked")
	@GetMapping("internal-inspection-view")
	public @ResponseBody JsonResponse<List<AuditMasterModel>> viewInternalInspectionData(HttpSession session) {
		logger.info("Method : viewInternalInspectionData starts");
		JsonResponse<List<AuditMasterModel>> response = new JsonResponse<List<AuditMasterModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getGrcUrl() + "viewInternalInspectionData?organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : viewInternalInspectionData ends" + response);

		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("inspection-master-savedata")
	public @ResponseBody JsonResponse<Object> addInspectionData(HttpSession session,
			@RequestBody AuditMasterModel model) {
		logger.info("Method : addInspectionData starts   " + model);

		MultipartFile inputFile = (MultipartFile) session.getAttribute("attachment");
		logger.info("inputFile=====" + inputFile);
		byte[] bytes;
		String imageName = null;
		if (inputFile != null) {
			try {
				bytes = inputFile.getBytes();
				String[] fileType = inputFile.getContentType().split("/");
				imageName = saveAllImage(bytes, fileType[1]);

				logger.info("imageName====" + imageName);

				model.setUploadedBillDiv(imageName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setCreatedBy(userId);
		model.setOrganizationName(organization);
		model.setOrganizationDivision(orgDivision);

		logger.info("Method : addInspectionData to add" + model);
		try {
			resp = restClient.postForObject(env.getGrcUrl() + "rest-addInspectionData", model, JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("UnSuccess");
		}

		logger.info("Method : addInspectionData ends" + resp);

		return resp;
	}

	public String saveAllImage(byte[] imageBytes, String ext) {
		logger.info("Method : saveAllImage starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("png") || ext.contentEquals("jpg") || ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}

			}

			Path path = Paths.get(env.getFileUploadAudit() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : saveAllImage ends");
		return imageName;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("inspection-category-view")
	public @ResponseBody JsonResponse<List<AuditMasterModel>> viewInspectionCategory(HttpSession session) {
		logger.info("Method : viewInspectionCategory starts   ");
		JsonResponse<List<AuditMasterModel>> response = new JsonResponse<List<AuditMasterModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getGrcUrl() + "viewInspectionCategory?organization=" + organization
					+ "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : viewInspectionCategory ends" + response);

		return response;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("external-inspection-view")
	public @ResponseBody JsonResponse<List<AuditMasterModel>> viewExternalInspectionData(HttpSession session) {
		logger.info("Method : viewExternalInspectionData starts   ");
		JsonResponse<List<AuditMasterModel>> response = new JsonResponse<List<AuditMasterModel>>();
		String organization = "";
		String orgDivision = "";
		try {
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getGrcUrl() + "viewExternalInspectionData?organization="
					+ organization + "&orgDivision=" + orgDivision, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : viewExternalInspectionData ends" + response);

		return response;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("inspect-category-savedata")
	public @ResponseBody JsonResponse<Object> addInspectCategorySavedata(HttpSession session,
			@RequestBody AuditMasterModel model) {
		logger.info("Method : addInspectCategorySavedata starts   " + model);

		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		// String dateFormat="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			// dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setCreatedBy(userId);
		model.setOrganizationName(organization);
		model.setOrganizationDivision(orgDivision);
		logger.info("Method : addInspectCategorySavedata to add" + model);
		try {
			resp = restClient.postForObject(env.getGrcUrl() + "addInspectCategorySavedata", model, JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("UnSuccess");
		}

		logger.info("Method : addInspectCategorySavedata ends" + resp);

		return resp;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("inspection-master-categoryedit")
	public @ResponseBody JsonResponse<AuditMasterModel> editInspectionMasterCategory(@RequestParam String Id,
			HttpSession session) {

		logger.info("Method : editInspectionMasterCategory starts");
		JsonResponse<AuditMasterModel> jsonResponse = new JsonResponse<AuditMasterModel>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USERID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restTemplate.getForObject(env.getGrcUrl() + "editInspectionMasterCategory?id=" + Id
					+ "&orgName=" + orgName + "&orgDivision=" + orgDivision + "&uId=" + userId, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		AuditMasterModel Model = mapper.convertValue(jsonResponse.getBody(), new TypeReference<AuditMasterModel>() {
		});

		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editInspectionMasterCategory ends");
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("inspection-master-edit")
	public @ResponseBody JsonResponse<AuditMasterModel> editInspectionMaster(@RequestParam String Id, HttpSession session) {

		logger.info("Method : editInspectionMaster starts");
		JsonResponse<AuditMasterModel> jsonResponse = new JsonResponse<AuditMasterModel>();
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		try {
			userId = (String) session.getAttribute("USERID");
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jsonResponse = restTemplate.getForObject(env.getGrcUrl() + "editInspectionMaster?id=" + Id+
					"&orgName=" +orgName+"&orgDivision=" +orgDivision + "&uId=" + userId, JsonResponse.class);				
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		AuditMasterModel Model = mapper.convertValue(jsonResponse.getBody(), new TypeReference<AuditMasterModel>() {
		});
		
		jsonResponse.setBody(Model);
		if (jsonResponse.getMessage() != null && jsonResponse.getMessage() != "") {
			jsonResponse.setCode(jsonResponse.getMessage());
			jsonResponse.setMessage("Unsuccess");
		} else {
			jsonResponse.setMessage("Success");
		}
		logger.info("Method : editInspectionMaster ends");
		return jsonResponse;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("inception-category-master-delete")
	public @ResponseBody JsonResponse<Object> deleteInceptionACategoryMaster(@RequestParam String id, Model model,HttpSession session) {
		logger.info("Method : deleteInceptionACategoryMaster function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();		
		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "deleteIncCatMaster?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteInceptionACategoryMaster function Ends");

		return res;
	}
	
	//delete audit-master/audit-internal-master-delete
	@SuppressWarnings("unchecked")
	@GetMapping("inception-internal-master-delete")
	public @ResponseBody JsonResponse<Object> deleteInceptionInternalMasterDelete(@RequestParam String id, Model model,HttpSession session) {
		logger.info("Method : deleteAuditInternalMasterDelete function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();		
		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "deleteIncInternalDelete?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteInceptionInternalMasterDelete function Ends");

		return res;
	}
	
	//delete audit-master/audit-external-master-delete
	@SuppressWarnings("unchecked")
	@GetMapping("inception-external-master-delete")
	public @ResponseBody JsonResponse<Object> deleteInceptionExternalMasterDelete(@RequestParam String id, Model model,HttpSession session) {
		logger.info("Method : deleteInceptionExternalMasterDelete function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();		
		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "deleteIncExternalDelete?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteInceptionExternalMasterDelete function Ends");

		return res;
	}
}
