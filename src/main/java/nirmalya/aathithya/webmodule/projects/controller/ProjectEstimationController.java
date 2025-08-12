package nirmalya.aathithya.webmodule.projects.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

import nirmalya.aathithya.webmodule.common.utils.DropDownModel;
import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.projects.model.ProjectCreationWebModelV1;
import nirmalya.aathithya.webmodule.projects.model.ProjectProdDetailsModel;
import nirmalya.aathithya.webmodule.property.stakeholder.model.StackholderRentosSupportModel;


	
	@Controller
	@RequestMapping(value = "projects")
	public class ProjectEstimationController {
		Logger logger = LoggerFactory.getLogger(ProjectEstimationController.class);

		@Autowired
		RestTemplate restClient;

		@Autowired
		EnvironmentVaribles env;

		@GetMapping(value = { "estimation" })
		public String projectEstimation(Model model, HttpSession session) {
			logger.info("Method : projectEstimation starts");


			logger.info("Method : projectEstimation ends");
			return "projects/project-estimation";
		}
		
		/*view-all-project-estimation */
		@SuppressWarnings("unchecked")
		@GetMapping("view-all-project-estimation")
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
			}
			logger.info("Method :getAllPrioject ends" + resp);
			return resp;
		}
		
		/* view-all-category-by-type */
//		@SuppressWarnings("unchecked")
//		@GetMapping("view-all-category-by-type")
//		public @ResponseBody Object viewAllProjectByTypeId(HttpSession session, @RequestParam String id) {
//			logger.info("Method :viewAllProjectByTypeId starts");
//			JsonResponse<Object> resp = new JsonResponse<Object>();
//			String orgName = "";
//			String orgDivision = "";
//			try {
//				orgName = (String) session.getAttribute("ORGANIZATION");
//				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
//
//				resp = restClient.getForObject(env.getProjects() + "rest-get-all-category-by-typeId?orgName=" + orgName
//						+ "&orgDivision=" + orgDivision + "&id=" + id, JsonResponse.class);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			if (resp.getMessage() != "" && resp.getMessage() != null) {
//				resp.setCode(resp.getCode());;
//				resp.setMessage(resp.getMessage());
//			} else {
//				resp.setCode(resp.getCode());;
//				resp.setMessage(resp.getMessage());
//			}
//			logger.info("Method :viewAllProjectByTypeId ends" + resp);
//			return resp;
//		}
		
		/* add-project-all-categories */
		@SuppressWarnings("unchecked")
		@PostMapping("add-project-all-categories")
		public @ResponseBody JsonResponse<Object> saveAllCategories(HttpSession session,
				@RequestBody Map<String, Object> dataToSend) {
			logger.info("Method : saveAllCategories starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}
			try {
				resp = restClient.postForObject(env.getProjects() + "rest-save-all-categories?userId=" + userId + "&org="
						+ orgName + "&orgDiv=" + orgDivision, dataToSend, JsonResponse.class);
				
				resp.setMessage(resp.getMessage());
				resp.setCode(resp.getCode());
			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : saveAllCategories ends");
			return resp;
		}
		
		
		@SuppressWarnings("unchecked")

		@GetMapping("/view-all-product-sku")
		public @ResponseBody List<ProjectProdDetailsModel> viewSku(HttpSession session) {
			logger.info("Method : viewSku starts");

			JsonResponse<List<ProjectProdDetailsModel>> resp = new JsonResponse<List<ProjectProdDetailsModel>>();
			List<ProjectProdDetailsModel> returnList = new ArrayList<ProjectProdDetailsModel>();

			try {
				resp = restClient.getForObject(env.getProjects() + "rest-view-sku",
						JsonResponse.class);
				returnList = resp.getBody();
				resp.setCode(resp.getCode());
				resp.setMessage(resp.getMessage());
			} catch (RestClientException e) {
				e.printStackTrace();
			}
			//logger.info(returnList);
			
			logger.info("Method : viewSku ends"+returnList);
			return returnList;
		}
		
		/*view-all-category-details*/
		@SuppressWarnings("unchecked")
		@GetMapping("view-all-category-details")
		public @ResponseBody Object viewAllProjectDetailsByTypeId(HttpSession session, @RequestParam String id,@RequestParam String type) {
			logger.info("Method :viewAllProjectDetailsByTypeId starts");
			JsonResponse<Object> resp = new JsonResponse<Object>();
			String orgName = "";
			String orgDivision = "";
			try {
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");

				resp = restClient.getForObject(env.getProjects() + "rest-get-all-category-details-by-typeId?orgName=" + orgName
						+ "&orgDivision=" + orgDivision + "&id=" + id +"&type=" +type, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (resp.getMessage() != "" && resp.getMessage() != null) {
				resp.setCode(resp.getCode());;
				resp.setMessage(resp.getMessage());
			} else {
				resp.setCode(resp.getCode());;
				resp.setMessage(resp.getMessage());
			}
			logger.info("Method :viewAllProjectDetailsByTypeId ends" + resp);
			return resp;
		}
		
		/* add-project-estimation-details */
		@SuppressWarnings("unchecked")
		@PostMapping("add-project-estimation-details")
		public @ResponseBody JsonResponse<Object> saveAllEstimation(HttpSession session,
				@RequestBody Map<String, Object> dataToSend) {
			logger.info("Method : saveAllEstimation starts");

			JsonResponse<Object> resp = new JsonResponse<Object>();
			String userId = "";
			String orgName = "";
			String orgDivision = "";
			try {
				userId = (String) session.getAttribute("USER_ID");
				orgName = (String) session.getAttribute("ORGANIZATION");
				orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
			} catch (Exception e) {

			}
			try {
				resp = restClient.postForObject(env.getProjects() + "rest-save-all-estimation?userId=" + userId + "&org="
						+ orgName + "&orgDiv=" + orgDivision, dataToSend, JsonResponse.class);

			} catch (Exception e) {
				e.printStackTrace();
			}

			logger.info("Method : saveAllEstimation ends");
			return resp;
		}
}
