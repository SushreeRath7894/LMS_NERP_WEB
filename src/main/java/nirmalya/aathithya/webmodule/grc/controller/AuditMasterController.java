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

public class AuditMasterController {

	Logger logger = LoggerFactory.getLogger(AuditMasterController.class);

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;
	@GetMapping(value = { "audit-master" })
	public String auditMaster(Model model, HttpSession session) {
		logger.info("Method : auditMaster starts");
		String organization = "";
		String orgDivision = "";

		try {			
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		try {

			DropDownModel[] auditType = restTemplate.getForObject(env.getGrcUrl()
					+ "getAuditType?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditType = Arrays.asList(auditType);
			model.addAttribute("auditType", AuditType);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		try {

			DropDownModel[] agencyList = restClient.getForObject(env.getGrcUrl()
					+ "getAgencyList?organization=" + organization + "&orgDivision=" + orgDivision,
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

			DropDownModel[] auditorList = restTemplate.getForObject(env.getGrcUrl()
					+ "getAuditorList?organization=" + organization + "&orgDivision=" + orgDivision,
					DropDownModel[].class);
			List<DropDownModel> AuditorList = Arrays.asList(auditorList);
			model.addAttribute("auditorList", AuditorList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		 
		logger.info("Method : auditMaster ends");
		return "grc/audit-master";
	}
	// add
	@SuppressWarnings("unchecked")
	@PostMapping("audit-master-savedata")
	public @ResponseBody JsonResponse<Object> addAuditMasterData(HttpSession session,
			@RequestBody AuditMasterModel model) {
		logger.info("Method : addAuditMasterData starts   " + model);
		
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
		//String dateFormat="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			//dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setCreatedBy(userId);
		model.setOrganizationName(organization);
		model.setOrganizationDivision(orgDivision); 
		
		/*if (model.getStartDate() != null && model.getStartDate() != "") {
			model.setStartDate(DateFormatter.dateFormat(model.getStartDate(), dateFormat));
		}*/
		logger.info("Method : addAuditMasterData to add"+model);
		try {
				resp = restClient.postForObject(env.getGrcUrl() + "rest-addAuditMasterData", model,
					JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("UnSuccess");
		}

		logger.info("Method : addAuditMasterData ends"+resp);

		return resp;
	}
	//View Internal Auditor Data
	@SuppressWarnings("unchecked")
	@GetMapping("audit-master/internal-auditor-view")
	public @ResponseBody JsonResponse<List<AuditMasterModel>> viewInternalData(HttpSession session) {
		logger.info("Method : viewInternalData starts   ");
		JsonResponse<List<AuditMasterModel>> response = new JsonResponse<List<AuditMasterModel>>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getGrcUrl() + "rest-getInternalAuditorData?organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (
		RestClientException e) {
			e.printStackTrace();
		}
 
		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : viewInternalData ends"+response);

		return response;
	}
	//View External Auditor Data
	@SuppressWarnings("unchecked")
	@GetMapping("audit-master/external-auditor-view")
	public @ResponseBody JsonResponse<List<AuditMasterModel>> viewExternalData(HttpSession session) {
		logger.info("Method : viewExternalData starts   ");
		JsonResponse<List<AuditMasterModel>> response = new JsonResponse<List<AuditMasterModel>>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getGrcUrl() + "rest-getExternalAuditorData?organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (
		RestClientException e) {
			e.printStackTrace();
		}
 
		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : viewExternalData ends"+response);

		return response;
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
	// add audit-category-savedata
	@SuppressWarnings("unchecked")
	@PostMapping("audit-master/audit-category-savedata")
	public @ResponseBody JsonResponse<Object> addAuditCategorySavedata(HttpSession session,
			@RequestBody AuditMasterModel model) {
		logger.info("Method : addAuditCategorySavedata starts   " + model); 
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String userId = "";
		String organization = "";
		String orgDivision = "";
		//String dateFormat="";
		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			//dateFormat = (String) session.getAttribute("DATEFORMAT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setCreatedBy(userId);
		model.setOrganizationName(organization);
		model.setOrganizationDivision(orgDivision);  
		logger.info("Method : addAuditCategorySavedata to add"+model);
		try {
				resp = restClient.postForObject(env.getGrcUrl() + "rest-addAuditCategorySavedata", model,
					JsonResponse.class);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Success");
		} else {
			resp.setMessage("UnSuccess");
		}

		logger.info("Method : addAuditCategorySavedata ends"+resp);

		return resp;
	}
	//View audit-category-view Data
	@SuppressWarnings("unchecked")
	@GetMapping("audit-master/audit-category-view")
	public @ResponseBody JsonResponse<List<AuditMasterModel>> viewAuditCategoryData(HttpSession session) {
		logger.info("Method : viewAuditCategoryData starts   ");
		JsonResponse<List<AuditMasterModel>> response = new JsonResponse<List<AuditMasterModel>>();
		String organization=""; 
		String orgDivision="";
		try {
			organization = (String) session.getAttribute("ORGANIZATION"); 
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response = restTemplate.getForObject(env.getGrcUrl() + "rest-getViewAuditCategoryData?organization="+organization+"&orgDivision="+orgDivision, JsonResponse.class);
		} catch (
		RestClientException e) {
			e.printStackTrace();
		}
 
		if (response.getMessage() != null && response.getMessage() != "") {
			response.setCode(response.getMessage());
			response.setMessage("Unsuccess");

		} else {
			response.setMessage("Success");
		}

		logger.info("Method : viewAuditCategoryData ends"+response);

		return response;
	}

	//delete audit-master/audit-category-master-delete
	@SuppressWarnings("unchecked")
	@GetMapping("audit-master/audit-category-master-delete")
	public @ResponseBody JsonResponse<Object> deleteAuditACategoryMaster(@RequestParam String id, Model model,HttpSession session) {
		logger.info("Method : deleteAuditACategoryMaster function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();		
		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "audit-category-master-delete?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteAuditACategoryMaster function Ends");

		return res;
	}
	
	//delete audit-master/audit-internal-master-delete
	@SuppressWarnings("unchecked")
	@GetMapping("audit-master/audit-internal-master-delete")
	public @ResponseBody JsonResponse<Object> deleteAuditInternalMasterDelete(@RequestParam String id, Model model,HttpSession session) {
		logger.info("Method : deleteAuditInternalMasterDelete function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();		
		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "audit-internal-master-delete?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteAuditInternalMasterDelete function Ends");

		return res;
	}
	
	//delete audit-master/audit-external-master-delete
	@SuppressWarnings("unchecked")
	@GetMapping("audit-master/audit-external-master-delete")
	public @ResponseBody JsonResponse<Object> deleteAuditExternalMasterDelete(@RequestParam String id, Model model,HttpSession session) {
		logger.info("Method : deleteAuditExternalMasterDelete function starts");
		JsonResponse<Object> res = new JsonResponse<Object>();		
		try {
			res = restTemplate.getForObject(env.getGrcUrl() + "audit-external-master-delete?id=" + id, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		String message = res.getMessage();
		if (message != null && message != "") {

		} else {
			res.setMessage("Success");
		}
		logger.info("Method : deleteAuditExternalMasterDelete function Ends");

		return res;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("audit-master/audit-master-categoryedit")
	public @ResponseBody JsonResponse<AuditMasterModel> editAuditMasterCategory(@RequestParam String Id, HttpSession session) {

		logger.info("Method : editAuditMasterCategory starts");
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
			jsonResponse = restTemplate.getForObject(env.getGrcUrl() + "rest-audit-master-categoryedit?id=" + Id+
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
		logger.info("Method : editAuditMasterCategory ends");
		return jsonResponse;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("audit-master/audit-master-edit")
	public @ResponseBody JsonResponse<AuditMasterModel> editAuditMaster(@RequestParam String Id, HttpSession session) {

		logger.info("Method : editAuditMaster starts");
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
			jsonResponse = restTemplate.getForObject(env.getGrcUrl() + "rest-audit-master-edit?id=" + Id+
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
		logger.info("Method : editAuditMaster ends");
		return jsonResponse;
	}
	
	@PostMapping("audit-master/view-audit-master-upload-file")
	public @ResponseBody JsonResponse<Object> uploadDocFile(@RequestParam("file") MultipartFile inputFile,
			HttpSession session) {
		logger.info("Method : employee uploadAttachement controller  starts");

		JsonResponse<Object> response = new JsonResponse<Object>();

		try {
			response.setMessage(inputFile.getOriginalFilename());
			session.setAttribute("attachment", inputFile);

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : employee uploadAttachement controller ' ends");
		return response;
	}
	
}

