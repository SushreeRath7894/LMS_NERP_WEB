package nirmalya.aathithya.webmodule.projects.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModelV1;

@Controller
@RequestMapping(value = "projects")
public class ProjectCreationWebControllerV1 {
	Logger logger = LoggerFactory.getLogger(ProjectCreationWebControllerV1.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "create-project-v1" })
	public String projectCreationV1(Model model, HttpSession session, @RequestParam Optional<String> id) {
		logger.info("Method : projectCreationV1 starts");
		
		String typeValue = "";
		if (id.isPresent()) {
			typeValue = id.get();
		}

		try {
			DropDownModel[] crop = restClient.getForObject(env.getProjects() + "rest-project-type-list?type="+typeValue,
					DropDownModel[].class);
			List<DropDownModel> cropList = Arrays.asList(crop);

			model.addAttribute("projectList", cropList);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : projectCreationV1 ends");
		return "projects/create-project-v1";
	}

	// for upload files
	@PostMapping("upload-files-project")
	public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files) {
	    String location = env.getFileUploadDocumenttUrl();
	    System.out.println("Upload directory: " + location);
	    File uploadDir = new File(location);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdirs();
	    }

	    for (MultipartFile file : files) {
	        System.out.println("File received: " + file.getOriginalFilename() + ", Content Type: " + file.getContentType());
	        try {
	            File destinationFile = new File(uploadDir, file.getOriginalFilename());
	            file.transferTo(destinationFile);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body("Error saving file: " + file.getOriginalFilename());
	        }
	    }

	    return ResponseEntity.ok("Files uploaded successfully");
	}


	// project-ctreation-add-v1
	@SuppressWarnings("unchecked")
	@PostMapping("/add-project-creation")
	public @ResponseBody JsonResponse<Object> addProjectCreation(
			@RequestBody List<ProjectCreationWebModelV1> prjCreation, HttpSession session) {
		logger.info("Method : addProjectCreation starts");

		JsonResponse<Object> resp = new JsonResponse<>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String organization = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			for (ProjectCreationWebModelV1 model : prjCreation) {
				model.setCreatedBy(userId);
				model.setOrganizationName(organization);
				model.setOrganizationDivision(orgDivision);
			}
			resp = restClient.postForObject(env.getProjects() + "rest-addPrjCreation-v1", prjCreation,
					JsonResponse.class);
			System.out.printf(env.getProjects() + "rest-addPrjCreation-v1", prjCreation, JsonResponse.class);
			if (resp.getMessage() != null && !resp.getMessage().isEmpty()) {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			} else {
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : addProjectCreation ends with response: " + resp);
		return resp;
	}

	// view-all-project
	@SuppressWarnings("unchecked")
	@GetMapping("view-all-project")
	public @ResponseBody Object getAllPrioject(HttpSession session,@RequestParam String type) {
		logger.info("Method :getAllPrioject starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(
					env.getProjects() + "rest-get-all-project?orgName=" + orgName + "&orgDivision=" + orgDivision + "&type=" + type,
					JsonResponse.class);
		

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
			resp.setCode(resp.getCode());
		}
		logger.info("Method :getAllPrioject ends" + resp);
		return resp;
	}

	// view-all-project-by-id
	@SuppressWarnings("unchecked")
	@GetMapping("view-all-project-by-id")
	public @ResponseBody Object getAllPriojectById(HttpSession session, @RequestParam String id) {
	    logger.info("Method :getAllPriojectById starts");
	    JsonResponse<Object> resp = new JsonResponse<Object>();
	    String orgName = "";
	    String orgDivision = "";
	    ObjectMapper objectMapper = new ObjectMapper();

	    try {
	        orgName = (String) session.getAttribute("ORGANIZATION");
	        orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

	        resp = restClient.getForObject(env.getProjects() + "rest-get-all-project-by-id?orgName=" + orgName
	                + "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);


	        List<String> bodyList = (List<String>) resp.getBody(); 
	        if (bodyList != null && !bodyList.isEmpty()) {
	            String responseBody = bodyList.get(0); 
	            JsonNode responseData = objectMapper.readTree(responseBody); 

	            String baseUrl = env.getBaseURL() + "document/image";

	            for (JsonNode projectNode : responseData) {
	                JsonNode docDetailsArray = projectNode.path("docDtls");
	                if (docDetailsArray.isArray()) {
	                    for (JsonNode docNode : docDetailsArray) {
	                        String documentName = docNode.path("documentName").asText();
	                        String fileName = docNode.path("fileName").asText();

	                        String modifiedFileName = fileName; 

	                        String documentUrl = baseUrl + "/" + modifiedFileName;

	                        ((ObjectNode) docNode).put("fileName", documentUrl);

	                    }
	                }
	            }
	            String updatedResponse = objectMapper.writeValueAsString(responseData);
	            bodyList.set(0, updatedResponse);
	        } else {
	            System.out.println("Body is empty or null.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    if (resp.getMessage() != "" && resp.getMessage() != null) {
	        resp.setCode(resp.getMessage());
	        resp.setMessage("Unsuccess");
	    } else {
	        resp.setMessage("Success");
	    }

	    logger.info("Method :getAllPriojectById ends" + resp);
	    return resp;
	}




}
