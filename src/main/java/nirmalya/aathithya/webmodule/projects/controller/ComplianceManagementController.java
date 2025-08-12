package nirmalya.aathithya.webmodule.projects.controller;

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
import nirmalya.aathithya.webmodule.projects.model.ComplianceManagementModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectExecutionModel;

@Controller
@RequestMapping(value = "projects")
public class ComplianceManagementController {
	Logger logger = LoggerFactory.getLogger(ComplianceManagementController.class);

	@Autowired
	RestTemplate restClient;
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	EnvironmentVaribles env;
	
	@GetMapping(value = { "compliance-management" })
	public String compliancemanagement(Model model, HttpSession session) {
		logger.info("Method : compliancemanagement starts");
		try {
			DropDownModel[] complianceName = restClient.getForObject(env.getProjects() + "get-complianceName",
					DropDownModel[].class);
			List<DropDownModel> complianceNameList = Arrays.asList(complianceName);
			model.addAttribute("complianceNameList", complianceNameList);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Method : compliancemanagement ends");
		return "projects/complianceManagement.html";
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("compliance-management-view-project")
	public @ResponseBody Object viewProjectCreation(HttpSession session) {

		logger.info("Method :viewProjectCreation starts");
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
		try {
			resp = restClient.getForObject(env.getProjects() + "rest-view-project-creation" + "?userid=" + userId
					+ "&org=" + organization + "&div=" + orgDivision, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("viewProjectCreation--" + resp);
		logger.info("Method :viewProjectCreation ends");

		return resp;
	}
	
	
	// upload docs
		@PostMapping("compliance-management-upload-file")
		public @ResponseBody JsonResponse<Object> uploadFile(@RequestParam("file") MultipartFile inputFile,
				HttpSession session) {
			logger.info("Method : uploadFile controller function 'post-mapping' starts");
			JsonResponse<Object> response = new JsonResponse<Object>();

			try {
				response.setMessage(inputFile.getOriginalFilename());
				session.setAttribute("quotationPFile", inputFile);

			} catch (RestClientException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("uploadFile: " + e.getMessage());
			}
			logger.info("Method : uploadFile controller function 'post-mapping' ends" + response);
			return response;
		}
		
		
		@SuppressWarnings("unchecked")
		@PostMapping("compliance-management-add")
		public @ResponseBody JsonResponse<Object> saveComplianceManagement(@RequestBody List<ComplianceManagementModel> compliance,
				HttpSession session) {
			logger.info("Method : saveComplianceManagement starts" + compliance);
			MultipartFile inputFile = (MultipartFile) session.getAttribute("quotationPFile");
			byte[] bytes;
			String imageName = null;

			if (inputFile != null) {
				try {
					bytes = inputFile.getBytes();
					String[] fileType = inputFile.getContentType().split("/");
					imageName = saveAllImage(bytes, fileType[1]);

					compliance.get(0).setFileAttach(imageName);
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

			for (ComplianceManagementModel m : compliance) {
				m.setCreatedBy(userId);
				m.setOrganizationName(organization);
				m.setOrganizationDivision(orgDivision);

			}

			try {
				resp = restClient.postForObject(env.getProjects() + "rest-saveComplianceManagement", compliance,
						JsonResponse.class);
				ObjectMapper mapper = new ObjectMapper();

				List<ComplianceManagementModel> quotation = mapper.convertValue(resp.getBody(),
						new TypeReference<List<ComplianceManagementModel>>() {
						});

				resp.setBody(quotation);
			} catch (Exception e) {

				e.printStackTrace();
			}
			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getMessage());
				resp.setMessage("Success");
				session.removeAttribute("quotationPFile");
			} else {
				resp.setMessage("Unsuccess");
			}

			logger.info("Method : saveComplianceManagement ends"+resp);
			return resp;
		}
		
		private String saveAllImage(byte[] imageBytes, String ext) {
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
				Path path = Paths.get(env.getFileUploadDocumenttUrl() + imageName);
				if (imageBytes != null) {
					Files.write(path, imageBytes);
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("saveAllImage: " + e.getMessage());
			}
			logger.info("Method : saveAllImage ends");
			return imageName;
		}
		
		@SuppressWarnings("unchecked")
		@GetMapping("compliance-management-view")
		public @ResponseBody Object viewCompliance(HttpSession session,@RequestParam String id) {

			logger.info("Method :viewCompliance starts");
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
			try {
				resp = restClient.getForObject(env.getProjects() + "rest-viewCompliance" + "?userid=" + userId
						+ "&org=" + organization + "&div=" + orgDivision + "&id="  + id , JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("viewCompliance--" + resp);
			logger.info("Method :viewCompliance ends");

			return resp;
		}
		
		
		@SuppressWarnings("unchecked")
		@GetMapping("compliance-management-edit")
		public @ResponseBody Object editCompliance(HttpSession session,@RequestParam String id) {

			logger.info("Method :editCompliance starts");
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
			try {
				resp = restClient.getForObject(env.getProjects() + "rest-editCompliance" + "?userid=" + userId
						+ "&org=" + organization + "&div=" + orgDivision + "&id="  + id , JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("editCompliance--" + resp);
			logger.info("Method :editCompliance ends");

			return resp;
		}
		

		@SuppressWarnings("unchecked")
		@PostMapping("compliance-management-delete")
		public @ResponseBody JsonResponse<Object> deleteCompliance(@RequestParam String id, Model model,
				HttpSession session) {
			logger.info("Method : deleteCompliance function starts");

			JsonResponse<Object> res = new JsonResponse<Object>();

			try {
				res = restClient.getForObject(env.getProjects() + "rest-deleteCompliance?id=" + id,
						JsonResponse.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String message = res.getMessage();
			if (message != null && message != "") {

			} else {
				res.setMessage("Success");
			}
			logger.info("Method : deleteCompliance function Ends");

			return res;
		}
}
