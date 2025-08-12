package nirmalya.aathithya.webmodule.projects.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.procurment.model.InventorySkuProductModel;
import nirmalya.aathithya.webmodule.productionplan.model.RmPmRequisitionModel;
import nirmalya.aathithya.webmodule.projects.model.ProjectPlanningModel;

@Controller
@RequestMapping(value = "projects")
public class ProjectPlanningWebController {
	Logger logger = LoggerFactory.getLogger(ProjectPlanningWebController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "project-planning" })
	public String projectPlanning(Model model, HttpSession session) {
		logger.info("Method : projectPlanning starts");

		try {
			DropDownModel[] priority = restClient.getForObject(env.getProjects() + "get-projectPriority-list-v1",
					DropDownModel[].class);
			List<DropDownModel> priorityList = Arrays.asList(priority);

			model.addAttribute("projectPriorityList", priorityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DropDownModel[] uom = restClient.getForObject(env.getMasterUrl() + "getUOMListForProduct",
					DropDownModel[].class);
			List<DropDownModel> unitList = Arrays.asList(uom);

			model.addAttribute("unitList", unitList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Method : projectPlanning ends");
		return "projects/project-planning-v1";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("view-all-task-project")
	public @ResponseBody Object getAllPrioject(HttpSession session, @RequestParam String type) {
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

	@SuppressWarnings("unchecked")
	@PostMapping("/add-project-task-details")
	public @ResponseBody JsonResponse<Object> addProjectPlanning(
			@RequestBody List<ProjectPlanningModel> projectPlanning, HttpSession session) {
		logger.info("Method : addProjectPlanning starts");

		JsonResponse<Object> resp = new JsonResponse<>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String organization = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			for (ProjectPlanningModel model : projectPlanning) {
				model.setCreatedBy(userId);
				model.setOrganizationName(organization);
				model.setOrganizationDivision(orgDivision);
			}
			resp = restClient.postForObject(env.getProjects() + "rest-add-project-planning", projectPlanning,
					JsonResponse.class);
			System.out.printf(env.getProjects() + "rest-add-project-planning", projectPlanning, JsonResponse.class);
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

		logger.info("Method : addProjectPlanning ends with response: " + resp);
		return resp;
	}

	// planning-new-autosearch-assignTo
	@SuppressWarnings("unchecked")
	@GetMapping("planning-new-autosearch-assignTo")
	public @ResponseBody JsonResponse<DropDownModel> getAssignedToAutoSearchList(Model model,
			@RequestParam String searchValue, HttpServletRequest request, HttpSession session) {
		logger.info("Method : getAssignedToAutoSearchList starts  " + searchValue);
		JsonResponse<DropDownModel> res = new JsonResponse<DropDownModel>();

		try {
			res = restClient.getForObject(env.getProjects() + "rest-get-assigned-to-auto-list?id=" + searchValue,
					JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("success");
		} else {
			res.setMessage("Unsuccess");
		}
		logger.info("Method : getAssignedToAutoSearchList ends");
		return res;
	}

	/* view-all-task-details */
	@SuppressWarnings("unchecked")
	@GetMapping("view-all-task-details")
	public @ResponseBody Object getAllTaskByProjectId(HttpSession session, @RequestParam String id) {
		logger.info("Method :getAllTaskByProjectId starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getProjects() + "get-all-task-details?orgName=" + orgName
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
		logger.info("Method :getAllTaskByProjectId ends" + resp);
		return resp;
	}

	/* view-all-task-details-edit */
	@SuppressWarnings("unchecked")
	@GetMapping("view-all-task-details-edit")
	public @ResponseBody Object editTaskParentDetails(HttpSession session, @RequestParam String id) {
		logger.info("Method :editTaskParentDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getProjects() + "get-all-task-edit?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getMessage());
			resp.setMessage("Unsuccess");
		} else {
			resp.setMessage("Success");
		}
		logger.info("Method :editTaskParentDetails ends" + resp);
		return resp;
	}

	/* add-project-task-details-child */
	@SuppressWarnings("unchecked")
	@PostMapping("/add-project-task-details-child")
	public @ResponseBody JsonResponse<Object> addChildProjectPlanning(
			@RequestBody List<ProjectPlanningModel> projectPlanning, HttpSession session) {
		logger.info("Method : addChildProjectPlanning starts");

		JsonResponse<Object> resp = new JsonResponse<>();

		try {
			String userId = (String) session.getAttribute("USER_ID");
			String organization = (String) session.getAttribute("ORGANIZATION");
			String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			for (ProjectPlanningModel model : projectPlanning) {
				model.setCreatedBy(userId);
				model.setOrganizationName(organization);
				model.setOrganizationDivision(orgDivision);
			}
			resp = restClient.postForObject(env.getProjects() + "rest-add-project-planning-child", projectPlanning,
					JsonResponse.class);
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

		logger.info("Method : addChildProjectPlanning ends with response: " + resp);
		return resp;
	}

	/* view-all-task-version-details */
	@SuppressWarnings("unchecked")
	@GetMapping("view-all-task-version-details")
	public @ResponseBody Object taskVersionDetails(HttpSession session, @RequestParam String id) {
		logger.info("Method :taskVersionDetails starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getProjects() + "get-all-version?orgName=" + orgName + "&orgDivision="
					+ orgDivision + "&id=" + id, JsonResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (resp.getMessage() != "" && resp.getMessage() != null) {
			resp.setCode(resp.getCode());
			resp.setMessage(resp.getMessage());
		} else {
			resp.setMessage(resp.getMessage());
		}
		logger.info("Method :taskVersionDetails ends" + resp);
		return resp;
	}
	/*
	 * Item autosearch
	 */

	@SuppressWarnings("unchecked")
	@PostMapping(value = { "rmpm-requisition-item-get-list" })
	public @ResponseBody JsonResponse<InventorySkuProductModel> getItemQuotationAutoSearchList(Model model,
			@RequestBody String searchValue, HttpSession session, BindingResult result) {
		logger.info("Method : getItemQuotationAutoSearchListForItem starts");
		String org = "";
		String orgDiv = "";
		String type = "";
		try {
			org = (String) session.getAttribute("ORGANIZATION");
			orgDiv = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		JsonResponse<InventorySkuProductModel> res = new JsonResponse<InventorySkuProductModel>();

		try {
			res = restClient.getForObject(env.getProduction() + "rest-getRmPmListForRequisition?id=" + searchValue
					+ "&type=" + type + "&org=" + org + "&orgDiv=" + orgDiv, JsonResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (res.getMessage() != null) {

			res.setCode(res.getMessage());
			res.setMessage("Unsuccess");
		} else {
			res.setMessage("success");
		}
		logger.info("Method : getItemQuotationAutoSearchList ends");
		return res;
	}

	/* rmpm-requisition-add-task */
	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = { "rmpm-requisition-add-task" })
	public @ResponseBody JsonResponse<Object> addRmPmRequisition(@RequestBody RmPmRequisitionModel av,
			HttpSession session) {
		logger.info("Method : addRmPmRequisition function starts");
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
			resp = restClient.postForObject(env.getProjects() + "rest-addRmPmRequisition-task", av, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : addRmPmRequisition function Ends");
		System.out.println("Final Save>>>------" + resp);
		return resp;
	}

	/* view-all-task-requisition-details */
	@SuppressWarnings("unchecked")
	@GetMapping("view-all-task-requisition-details")
	public @ResponseBody Object getAllRequisitionById(HttpSession session, @RequestParam String id) {
		logger.info("Method :getAllRequisitionById starts");
		JsonResponse<Object> resp = new JsonResponse<Object>();
		String orgName = "";
		String orgDivision = "";
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

			resp = restClient.getForObject(env.getProjects() + "get-all-requisition-byId?orgName=" + orgName
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
		logger.info("Method :getAllRequisitionById ends" + resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	@PostMapping("save-dpr")
	public @ResponseBody JsonResponse<Object> saveDPR(@RequestBody ProjectPlanningModel data, HttpSession session) {
		logger.info("Method : saveDPR starts");

		JsonResponse<Object> resp = new JsonResponse<Object>();

		String userId = "";
		String organization = "";
		String orgDivision = "";

		System.out.println("model data-->" + data);

		try {
			userId = (String) session.getAttribute("USER_ID");
			organization = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}

		data.setCreatedBy(userId);
		data.setOrganizationName(organization);
		data.setOrganizationDivision(orgDivision);

		if (data.getFileupload() != null && data.getFileupload() != "") {
			String imgUrl = null;
			try {
				byte[] bytes = decodeBase64Image(data.getFileupload());
				String ext = getImageExtension(data.getFileupload());
				imgUrl = uploadImageToFolder(bytes, ext);
				System.out.println("image url-->" + imgUrl);
				data.setwImg(imgUrl);
			} catch (Exception e) {
				e.printStackTrace();
				resp.setCode("failed");
				resp.setMessage("There is some problem while uploading image");
				return resp;
			}
		} else {
			if (data.getwImg() == null || data.getwImg() == "") {
				data.setwImg(null);
			}
		}

		try {
			resp = restClient.postForObject(env.getProjects() + "saveDprDetails", data, JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		logger.info("Method : saveDPR starts");
		return resp;
	}

	public static byte[] decodeBase64Image(String base64Image) {
		if (base64Image.startsWith("data:image")) {
			base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
		}
		return Base64.getDecoder().decode(base64Image);
	}

	public static String getImageExtension(String base64Image) {
		if (base64Image.startsWith("data:image/")) {
			String[] parts = base64Image.split(";")[0].split("/");
			return parts.length > 1 ? parts[1] : "jpg";
		}
		return "jpg";
	}

	public String uploadImageToFolder(byte[] imageBytes, String ext) {
		logger.info("Method : uploadImageToFolder starts");

		String imageName = null;

		try {

			if (imageBytes != null) {
				long nowTime = new Date().getTime();
				if (ext.contentEquals("jpeg")) {
					imageName = nowTime + ".jpg";
				} else {
					imageName = nowTime + "." + ext;
				}
			}

			Path path = Paths.get(env.getFileUploadMaster() + imageName);
			if (imageBytes != null) {
				Files.write(path, imageBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Method : uploadImageToFolder ends");
		return imageName;
	}

	/* view-all-task-dpr */
//	@SuppressWarnings("unchecked")
//	@GetMapping("view-all-task-dpr")
//	public @ResponseBody Object getAllTaskDpr(HttpSession session, @RequestParam String id) {
//		logger.info("Method :getAllTaskDpr starts");
//		JsonResponse<Object> resp = new JsonResponse<Object>();
//		String orgName = "";
//		String orgDivision = "";
//		
//		 ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			orgName = (String) session.getAttribute("ORGANIZATION");
//			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
//
//			resp = restClient.getForObject(env.getProjects() + "get-all-task-dpr?orgName=" + orgName + "&orgDivision="
//					+ orgDivision + "&id=" + id, JsonResponse.class);
//			
//			 List<String> bodyList = (List<String>) resp.getBody(); 
//		        if (bodyList != null && !bodyList.isEmpty()) {
//		            String responseBody = bodyList.get(0); 
//		            JsonNode responseData = objectMapper.readTree(responseBody); 
//
//		            String baseUrl = env.getBaseURL() + "document/image";
//
//		            for (JsonNode projectNode : responseData) {
//		                JsonNode docDetailsArray = projectNode.path("dprDetails");
//		                if (docDetailsArray.isArray()) {
//		                    for (JsonNode docNode : docDetailsArray) {
//		                        //String documentName = docNode.path("documentName").asText();
//		                        String fileName = docNode.path("docDtls").asText();
//
//		                        String modifiedFileName = fileName; 
//
//		                        String documentUrl = baseUrl + "/" + modifiedFileName;
//		                        
//		                        System.out.println("documentUrl=====================================>"+documentUrl);
//		                        ((ObjectNode) docNode).put("fileName", documentUrl);
//
//		                    }
//		                }
//		            }
//		            String updatedResponse = objectMapper.writeValueAsString(responseData);
//		            bodyList.set(0, updatedResponse);
//		        } else {
//		            System.out.println("Body is empty or null.");
//		        }
//			
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (resp.getMessage() != "" && resp.getMessage() != null) {
//			resp.setCode(resp.getMessage());
//			resp.setMessage("Unsuccess");
//		} else {
//			resp.setMessage("Success");
//		}
//		logger.info("Method :getAllTaskDpr ends" + resp);
//		return resp;
//	}
	
	
	/*
	 * import com.fasterxml.jackson.databind.JsonNode; import
	 * com.fasterxml.jackson.databind.ObjectMapper; import
	 * com.fasterxml.jackson.databind.node.ObjectNode; import org.slf4j.Logger;
	 * import org.slf4j.LoggerFactory; import
	 * org.springframework.web.bind.annotation.GetMapping; import
	 * org.springframework.web.bind.annotation.RequestParam; import
	 * org.springframework.web.bind.annotation.ResponseBody; import
	 * javax.servlet.http.HttpSession; import java.util.List;
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("view-all-task-dpr")
	public @ResponseBody Object getAllTaskDpr(HttpSession session, @RequestParam String id) {
	    Logger logger = LoggerFactory.getLogger(this.getClass());
	    logger.info("Method : getAllTaskDpr starts");
	    JsonResponse<Object> resp = new JsonResponse<>();
	    ObjectMapper objectMapper = new ObjectMapper();

	    try {
	        String orgName = (String) session.getAttribute("ORGANIZATION");
	        String orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

	        resp = restClient.getForObject(env.getProjects() + "get-all-task-dpr?orgName=" + orgName + "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);

	        logger.info("Response from API: " + resp);

	        if (resp.getBody() instanceof List) {
	            List<String> bodyList = (List<String>) resp.getBody();
	            if (bodyList != null && !bodyList.isEmpty()) {
	                String responseBody = bodyList.get(0);
	                JsonNode responseData = objectMapper.readTree(responseBody);
	                String baseUrl = env.getBaseURL() + "document/image";

	                if (responseData.has("dprDetails") && responseData.get("dprDetails").isArray()) {
	                    for (JsonNode docNode : responseData.get("dprDetails")) {
	                        if (docNode.has("docDtls")) {
	                            String fileName = docNode.get("docDtls").asText();
	                            String documentUrl = baseUrl + "/" + fileName;
	                            ((ObjectNode) docNode).put("docDtls", documentUrl);
	                        }
	                    }
	                }
	                bodyList.set(0, objectMapper.writeValueAsString(responseData));
	            }
	        }
	    } catch (Exception e) {
	        logger.error("Exception in getAllTaskDpr: ", e);
	    }

	    if (resp.getMessage() != null && !resp.getMessage().isEmpty()) {
	        resp.setCode(resp.getMessage());
	        resp.setMessage("Unsuccess");
	    } else {
	        resp.setMessage("Success");
	    }

	    logger.info("Method : getAllTaskDpr ends with response: " + resp);
	    return resp;
	}


}
