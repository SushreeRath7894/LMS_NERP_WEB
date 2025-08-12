package nirmalya.aathithya.webmodule.projects.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import nirmalya.aathithya.webmodule.common.utils.EnvironmentVaribles;
import nirmalya.aathithya.webmodule.common.utils.JsonResponse;
import nirmalya.aathithya.webmodule.projects.controller.ProjectCategoryController;
import nirmalya.aathithya.webmodule.projects.model.ProjectCategoryModel;
@Controller
@RequestMapping(value = { "projects/" })
public class ProjectCategoryController {
	Logger logger = LoggerFactory.getLogger(ProjectCategoryController.class);

	@Autowired
	RestTemplate restClient;

	@Autowired
	EnvironmentVaribles env;

	@GetMapping(value = { "project-category" })
	public String ProjectCategory(Model model, HttpSession session) {
		logger.info("Method : ProjectCategory starts");
		
		logger.info("Method : ProjectCategory ends");
		return "projects/projectcategory";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/project-category-save")
	public @ResponseBody JsonResponse<Object> saveProjectCategory(@RequestBody ProjectCategoryModel category, HttpSession session) {
		logger.info("Method : saveProjectCategory starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		category.setCreatedBy(userId);
		
		try {
			resp = restClient.postForObject(env.getProjects() + "saveProjectCategory", category,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();

		if (message != null && message != "") {

		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveProjectCategory starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/project-category-save-subcat")
	public @ResponseBody JsonResponse<Object> saveProjectSubCategory(@RequestBody ProjectCategoryModel category, HttpSession session) {
		logger.info("Method : saveProjectSubCategory starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		String orgName = "";
		String orgDivision = "";
		
		try {
			orgName = (String) session.getAttribute("ORGANIZATION");
			orgDivision = (String) session.getAttribute("ORGANIZATION_DIVISION");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		category.setCreatedBy(userId);
		category.setOrganizationName(orgName);
		category.setOrganizationDivision(orgDivision);
		
		logger.info("method show data:"+category);		
		try {
			resp = restClient.postForObject(env.getProjects() + "saveProjectSubCategory", category,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : saveProjectSubCategory starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/project-category-get-total-list")
	public @ResponseBody JsonResponse<List<ProjectCategoryModel>> getAllProjectCategoryList(HttpSession session) {
		logger.info("Method : getAllProjectCategoryList starts");
		
		JsonResponse<List<ProjectCategoryModel>> resp = new JsonResponse<List<ProjectCategoryModel>>();
		
		try {
//			resp = restClient.getForObject(env.getMasterUrl() + "getAllProjectCategoryList",
//					JsonResponse.class);
			
			resp = restClient.getForObject(env.getProjects() + "getProductCategoryDataListModallist",
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getAllProjectCategoryList starts"+resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/project-category-get-category-list-by-id")
	public @ResponseBody JsonResponse<List<ProjectCategoryModel>> getProjectCategoryListById(@RequestBody String id,HttpSession session) {
		logger.info("Method : getProjectCategoryListById starts");
		
		JsonResponse<List<ProjectCategoryModel>> resp = new JsonResponse<List<ProjectCategoryModel>>();
		
		try {
			resp = restClient.getForObject(env.getProjects() + "getProjectCategoryListById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getProjectCategoryListById starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/project-category-get-category-dtls-by-id")
	public @ResponseBody JsonResponse<ProjectCategoryModel> getProjectCategoryById(@RequestBody String id,HttpSession session) {
		logger.info("Method : getProjectCategoryById starts");
		
		JsonResponse<ProjectCategoryModel> resp = new JsonResponse<ProjectCategoryModel>();
		
		try {
			resp = restClient.getForObject(env.getProjects() + "getProjectCategoryById?id="+id,
					JsonResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : getProjectCategoryById starts");
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/project-category-delete")
	public @ResponseBody JsonResponse<Object> deleteCategory(@RequestBody String id, HttpSession session) {
		logger.info("Method : deleteCategory starts");
		
		JsonResponse<Object> resp = new JsonResponse<Object>();
		
		String userId = "";
		
		try {
			userId = (String) session.getAttribute("USER_ID");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			resp = restClient.getForObject(env.getProjects() + "deleteCategory?id="+id+"&createdBy="+userId,
					JsonResponse.class);
			
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		String message = resp.getMessage();
		
		if (message != null && message != "") {
			
		} else {
			resp.setMessage("Success");
		}
		
		logger.info("Method : deleteCategory starts");
		return resp;
	}
}
